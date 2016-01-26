/*
 * Autopsy Forensic Browser
 * 
 * Copyright 2013 Basis Technology Corp.
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
package org.sleuthkit.autopsy.modules.stix;

import java.util.logging.Level;
import org.openide.util.NbBundle;
import org.sleuthkit.autopsy.casemodule.Case;
import org.sleuthkit.autopsy.casemodule.services.Blackboard;
import org.sleuthkit.autopsy.coreutils.Logger;
import org.sleuthkit.autopsy.coreutils.MessageNotifyUtil;
import org.sleuthkit.datamodel.AbstractFile;
import org.sleuthkit.datamodel.BlackboardArtifact;
import org.sleuthkit.datamodel.BlackboardAttribute;
import org.sleuthkit.datamodel.SleuthkitCase;
import org.sleuthkit.datamodel.TskCoreException;

/**
 *
 */
class StixArtifactData {

    private AbstractFile file;
    private final String observableId;
    private final String objType;
    private static final Logger logger = Logger.getLogger(StixArtifactData.class.getName());

    public StixArtifactData(AbstractFile a_file, String a_observableId, String a_objType) {
        file = a_file;
        observableId = a_observableId;
        objType = a_objType;
    }

    public StixArtifactData(long a_objId, String a_observableId, String a_objType) {
        Case case1 = Case.getCurrentCase();
        SleuthkitCase sleuthkitCase = case1.getSleuthkitCase();
        try {
            file = sleuthkitCase.getAbstractFileById(a_objId);
        } catch (TskCoreException ex) {
            file = null;
        }
        observableId = a_observableId;
        objType = a_objType;
    }

    public void createArtifact(String a_title) throws TskCoreException {
        Blackboard blackboard = Case.getCurrentCase().getServices().getBlackboard();

        String setName;
        if (a_title != null) {
            setName = "STIX Indicator - " + a_title; //NON-NLS
        } else {
            setName = "STIX Indicator - (no title)"; //NON-NLS
        }

        BlackboardArtifact bba = file.newArtifact(BlackboardArtifact.ARTIFACT_TYPE.TSK_INTERESTING_FILE_HIT);
        bba.addAttribute(new BlackboardAttribute(BlackboardAttribute.ATTRIBUTE_TYPE.TSK_SET_NAME, "Stix", setName)); //NON-NLS
        bba.addAttribute(new BlackboardAttribute(BlackboardAttribute.ATTRIBUTE_TYPE.TSK_TITLE, "Stix", observableId)); //NON-NLS
        bba.addAttribute(new BlackboardAttribute(BlackboardAttribute.ATTRIBUTE_TYPE.TSK_CATEGORY, "Stix", objType)); //NON-NLS
        
        try {
            // index the artifact for keyword search
            blackboard.indexArtifact(bba);
        } catch (Blackboard.BlackboardException ex) {
            logger.log(Level.SEVERE, NbBundle.getMessage(Blackboard.class, "Blackboard.unableToIndexArtifact.error.msg", bba.getDisplayName()), ex); //NON-NLS
            MessageNotifyUtil.Notify.error(
                    NbBundle.getMessage(Blackboard.class, "Blackboard.unableToIndexArtifact.exception.msg"), bba.getDisplayName());
        }
    }

    public void print() {
        System.out.println("  " + observableId + " " + file.getName());
    }
}
