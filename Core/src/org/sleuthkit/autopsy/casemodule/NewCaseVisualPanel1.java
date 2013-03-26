/*
 * Autopsy Forensic Browser
 *
 * Copyright 2011 Basis Technology Corp.
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

package org.sleuthkit.autopsy.casemodule;

import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The wizard panel for the new case creation.
 *
 * @author jantonius
 */
final class NewCaseVisualPanel1 extends JPanel implements DocumentListener{

    private JFileChooser fc = new JFileChooser();
    private NewCaseWizardPanel1 wizPanel;

    NewCaseVisualPanel1(NewCaseWizardPanel1 wizPanel) {
        initComponents();
                this.wizPanel = wizPanel;
        caseNameTextField.getDocument().addDocumentListener(this);
        caseParentDirTextField.getDocument().addDocumentListener(this);
    }

    /**
     * Returns the name of the this panel. This name will be shown on the left
     * panel of the "New Case" wizard panel.
     *
     * @return name  the name of this panel
     */
    @Override
    public String getName() {
        return "Case Info";
    }

    /**
     * Gets the case name that the user types on the case name text field.
     *
     * @return caseName  the case name from the case name text field
     */
    public String getCaseName(){
        return this.caseNameTextField.getText();
    }

    /**
     * Gets the base directory that the user typed on the base directory text field.
     *
     * @return baseDirectory  the base directory from the case dir text field
     */
    public String getCaseParentDir(){
        return this.caseParentDirTextField.getText();
    }
    
    public JTextField getCaseParentDirTextField(){
        return this.caseParentDirTextField;
    }

    /** This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        caseNameLabel = new javax.swing.JLabel();
        caseDirLabel = new javax.swing.JLabel();
        caseNameTextField = new javax.swing.JTextField();
        caseParentDirTextField = new javax.swing.JTextField();
        caseDirBrowseButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        caseDirTextField = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(NewCaseVisualPanel1.class, "NewCaseVisualPanel1.jLabel1.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(caseNameLabel, org.openide.util.NbBundle.getMessage(NewCaseVisualPanel1.class, "NewCaseVisualPanel1.caseNameLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(caseDirLabel, org.openide.util.NbBundle.getMessage(NewCaseVisualPanel1.class, "NewCaseVisualPanel1.caseDirLabel.text")); // NOI18N

        caseNameTextField.setText(org.openide.util.NbBundle.getMessage(NewCaseVisualPanel1.class, "NewCaseVisualPanel1.caseNameTextField.text_1")); // NOI18N

        caseParentDirTextField.setText(org.openide.util.NbBundle.getMessage(NewCaseVisualPanel1.class, "NewCaseVisualPanel1.caseParentDirTextField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(caseDirBrowseButton, org.openide.util.NbBundle.getMessage(NewCaseVisualPanel1.class, "NewCaseVisualPanel1.caseDirBrowseButton.text")); // NOI18N
        caseDirBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caseDirBrowseButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(NewCaseVisualPanel1.class, "NewCaseVisualPanel1.jLabel2.text_1")); // NOI18N

        caseDirTextField.setEditable(false);
        caseDirTextField.setText(org.openide.util.NbBundle.getMessage(NewCaseVisualPanel1.class, "NewCaseVisualPanel1.caseDirTextField.text_1")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(caseDirLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(caseParentDirTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(caseNameLabel)
                                .addGap(26, 26, 26)
                                .addComponent(caseNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(caseDirTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(caseDirBrowseButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(caseNameLabel)
                    .addComponent(caseNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(caseDirLabel)
                    .addComponent(caseParentDirTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(caseDirBrowseButton))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(caseDirTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * The action when the Browse button is pressed. The browse button will pop
     * up the file chooser window to choose where the user wants to save the 
     * case directory.
     *
     * @param evt  the action event
     */
    private void caseDirBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caseDirBrowseButtonActionPerformed
        // show the directory chooser where the case directory will be created
        fc.setDragEnabled(false);
        if(!caseParentDirTextField.getText().trim().equals("")){
            fc.setCurrentDirectory(new File(caseParentDirTextField.getText()));
        }
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setSelectedFile(new File("C:\\Program Files\\"));
        //disableTextField(fc); // disable all the text field on the file chooser

        int returnValue = fc.showDialog((Component)evt.getSource(), "Select");
        if(returnValue == JFileChooser.APPROVE_OPTION){
            String path = fc.getSelectedFile().getPath();
            caseParentDirTextField.setText(path); // put the path to the textfield
         }
    }//GEN-LAST:event_caseDirBrowseButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton caseDirBrowseButton;
    private javax.swing.JLabel caseDirLabel;
    private javax.swing.JTextField caseDirTextField;
    private javax.swing.JLabel caseNameLabel;
    private javax.swing.JTextField caseNameTextField;
    private javax.swing.JTextField caseParentDirTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables

    /**
     * Gives notification that there was an insert into the document.  The
     * range given by the DocumentEvent bounds the freshly inserted region.
     *
     * @param e  the document event
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        this.wizPanel.fireChangeEvent();
        updateUI(e);
    }

    /**
     * Gives notification that a portion of the document has been
     * removed.  The range is given in terms of what the view last
     * saw (that is, before updating sticky positions).
     *
     * @param e  the document event
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        this.wizPanel.fireChangeEvent();
        updateUI(e);
    }

    /**
     * Gives notification that an attribute or set of attributes changed.
     *
     * @param e  the document event
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        this.wizPanel.fireChangeEvent();
        updateUI(e);
    }

    /**
     * The "listener" that listens when the fields in this form are updated.
     * This method is used to determine when to enable / disable the "Finish" button.
     *
     * @param e  the document event
     */
    public void updateUI(DocumentEvent e) {

        String caseName = this.caseNameTextField.getText();
        String caseDir = this.caseParentDirTextField.getText();

        if(!caseName.equals("") && !caseDir.equals("")){
            caseDirTextField.setText( caseDir + File.separator + caseName);
            wizPanel.setIsFinish(true);
        }
        else{
            caseDirTextField.setText("");
            wizPanel.setIsFinish(false);
        }
    }
}
