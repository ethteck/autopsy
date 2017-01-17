/*
 * Autopsy Forensic Browser
 *
 * Copyright 2011-2016 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.keywordsearch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.apache.commons.io.FileUtils;
import org.openide.modules.InstalledFileLocator;
import org.openide.util.NbBundle;
import org.sleuthkit.autopsy.casemodule.Case;
import org.sleuthkit.autopsy.corecomponentinterfaces.AutopsyService;
import org.sleuthkit.autopsy.coreutils.ExecUtil;
import org.sleuthkit.autopsy.coreutils.Logger;
import org.sleuthkit.autopsy.coreutils.PlatformUtil;

/**
 * This class handles the task of upgrading old indexes to the latest supported
 * Solr version.
 */
public class IndexUpgrader {
    
    private static final Logger logger = Logger.getLogger(IndexFinder.class.getName());
    private final String JAVA_PATH;
    
    IndexUpgrader() {
        JAVA_PATH = PlatformUtil.getJavaPath();
    }
    
    void performIndexUpgrade(String newIndexDir, String tempResultsDir) throws AutopsyService.AutopsyServiceException {
        // ELTODO Check for cancellation at whatever points are feasible
        
        // Run the upgrade tools on the contents (core) in ModuleOutput/keywordsearch/data/solrX_schema_Y/index
        File tmpDir = Paths.get(tempResultsDir, "IndexUpgrade").toFile(); //NON-NLS
        tmpDir.mkdirs();
            
        boolean success = true;
        try {
            // upgrade from Solr 4 to 5. If index is newer than Solr 4 then the upgrade script will throw exception right away.
            upgradeSolrIndexVersion4to5(newIndexDir, tempResultsDir);
        } catch (Exception ex) {
            // catch-all firewall for exceptions thrown by the Solr 4 to 5 upgrade tool itself
            logger.log(Level.SEVERE, "Exception while running Sorl 4 to Solr 5 upgrade tool " + newIndexDir, ex); //NON-NLS
            success = false;
        }

        if (success) {
            try {
                // upgrade from Solr 5 to 6. This one must complete successfully in order to produce a valid Solr 6 index.
                upgradeSolrIndexVersion5to6(newIndexDir, tempResultsDir);
            } catch (Exception ex) {
                // catch-all firewall for exceptions thrown by Solr 5 to 6 upgrade tool itself
                logger.log(Level.SEVERE, "Exception while running Sorl 5 to Solr 6 upgrade tool " + newIndexDir, ex); //NON-NLS
                success = false;
            }
        }

        if (!success) {
            // delete the new directories
            new File(newIndexDir).delete();
            throw new AutopsyService.AutopsyServiceException("Failed to upgrade existing keyword search index");
        }
    }
    
    /**
     * Upgrades Solr index from version 4 to 5.
     *
     * @param solr4IndexPath Full path to Solr v4 index directory
     * @param tempResultsDir Path to directory where to store log output
     *
     * @return True is index upgraded successfully, false otherwise
     */
    void upgradeSolrIndexVersion4to5(String solr4IndexPath, String tempResultsDir) throws AutopsyService.AutopsyServiceException, SecurityException, IOException {

        String outputFileName = "output.txt";
        logger.log(Level.INFO, "Upgrading KWS index {0} from Sorl 4 to Solr 5", solr4IndexPath); //NON-NLS

        // find the index upgrade tool
        final File upgradeToolFolder = InstalledFileLocator.getDefault().locate("Solr4to5IndexUpgrade", IndexFinder.class.getPackage().getName(), false); //NON-NLS
        if (upgradeToolFolder == null) {
            logger.log(Level.SEVERE, "Unable to locate Sorl 4 to Solr 5 upgrade tool"); //NON-NLS
            throw new AutopsyService.AutopsyServiceException("Unable to locate Sorl 4 to Solr 5 upgrade tool");
        }

        // full path to index upgrade jar file
        File upgradeJarPath = Paths.get(upgradeToolFolder.getAbsolutePath(), "Solr4IndexUpgrade.jar").toFile();
        if (!upgradeJarPath.exists() || !upgradeJarPath.isFile()) {
            logger.log(Level.SEVERE, "Unable to locate Sorl 4 to Solr 5 upgrade tool's JAR file at {0}", upgradeJarPath); //NON-NLS
            throw new AutopsyService.AutopsyServiceException("Unable to locate Sorl 4 to Solr 5 upgrade tool's JAR file");
        }

        // create log output directory if it doesn't exist
        new File(tempResultsDir).mkdirs();

        final String outputFileFullPath = Paths.get(tempResultsDir, outputFileName).toString();
        final String errFileFullPath = Paths.get(tempResultsDir, outputFileName + ".err").toString(); //NON-NLS
        List<String> commandLine = new ArrayList<>();
        commandLine.add(JAVA_PATH);
        commandLine.add("-jar");
        commandLine.add(upgradeJarPath.getAbsolutePath());
        commandLine.add(solr4IndexPath);
        ProcessBuilder processBuilder = new ProcessBuilder(commandLine);
        processBuilder.redirectOutput(new File(outputFileFullPath));
        processBuilder.redirectError(new File(errFileFullPath));
        ExecUtil.execute(processBuilder);

        // alternatively can execute lucene upgrade command from the folder where lucene jars are located
        // java -cp ".;lucene-core-5.5.1.jar;lucene-backward-codecs-5.5.1.jar;lucene-codecs-5.5.1.jar;lucene-analyzers-common-5.5.1.jar" org.apache.lucene.index.IndexUpgrader \path\to\index
    }

    /**
     * Upgrades Solr index from version 5 to 6.
     *
     * @param solr5IndexPath Full path to Solr v5 index directory
     * @param tempResultsDir Path to directory where to store log output
     *
     * @return True is index upgraded successfully, false otherwise
     */
    void upgradeSolrIndexVersion5to6(String solr5IndexPath, String tempResultsDir) throws AutopsyService.AutopsyServiceException, SecurityException, IOException {

        String outputFileName = "output.txt";
        logger.log(Level.INFO, "Upgrading KWS index {0} from Sorl 5 to Solr 6", solr5IndexPath); //NON-NLS

        // find the index upgrade tool
        final File upgradeToolFolder = InstalledFileLocator.getDefault().locate("Solr5to6IndexUpgrade", IndexFinder.class.getPackage().getName(), false); //NON-NLS
        if (upgradeToolFolder == null) {
            logger.log(Level.SEVERE, "Unable to locate Sorl 5 to Solr 6 upgrade tool"); //NON-NLS
            throw new AutopsyService.AutopsyServiceException("Unable to locate Sorl 5 to Solr 6 upgrade tool");
        }

        // full path to index upgrade jar file
        File upgradeJarPath = Paths.get(upgradeToolFolder.getAbsolutePath(), "Solr5IndexUpgrade.jar").toFile();
        if (!upgradeJarPath.exists() || !upgradeJarPath.isFile()) {
            logger.log(Level.SEVERE, "Unable to locate Sorl 5 to Solr 6 upgrade tool's JAR file at {0}", upgradeJarPath); //NON-NLS
            throw new AutopsyService.AutopsyServiceException("Unable to locate Sorl 5 to Solr 6 upgrade tool's JAR file");
        }

        // create log output directory if it doesn't exist
        new File(tempResultsDir).mkdirs();

        final String outputFileFullPath = Paths.get(tempResultsDir, outputFileName).toString();
        final String errFileFullPath = Paths.get(tempResultsDir, outputFileName + ".err").toString(); //NON-NLS
        List<String> commandLine = new ArrayList<>();
        commandLine.add(JAVA_PATH);
        commandLine.add("-jar");
        commandLine.add(upgradeJarPath.getAbsolutePath());
        commandLine.add(solr5IndexPath);
        ProcessBuilder processBuilder = new ProcessBuilder(commandLine);
        processBuilder.redirectOutput(new File(outputFileFullPath));
        processBuilder.redirectError(new File(errFileFullPath));
        ExecUtil.execute(processBuilder);

        // alternatively can execute lucene upgrade command from the folder where lucene jars are located
        // java -cp ".;lucene-core-6.2.1.jar;lucene-backward-codecs-6.2.1.jar;lucene-codecs-6.2.1.jar;lucene-analyzers-common-6.2.1.jar" org.apache.lucene.index.IndexUpgrader \path\to\index
    }
    
}
