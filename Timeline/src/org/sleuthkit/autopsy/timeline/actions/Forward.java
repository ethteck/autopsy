/*
 * Autopsy Forensic Browser
 *
 * Copyright 2014 Basis Technology Corp.
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
package org.sleuthkit.autopsy.timeline.actions;

import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import org.controlsfx.control.action.AbstractAction;
import org.sleuthkit.autopsy.timeline.TimeLineController;

/**
 *
 */
public class Forward extends AbstractAction {

    private static final Image BACK_IMAGE = new Image("/org/sleuthkit/autopsy/timeline/images/arrow.png", 16, 16, true, true, true);

    private final TimeLineController controller;

    public Forward(TimeLineController controller) {
        super("Forward");
        setGraphic(new ImageView(BACK_IMAGE));
        setAccelerator(new KeyCodeCombination(KeyCode.RIGHT, KeyCodeCombination.ALT_DOWN));
        this.controller = controller;
        disabledProperty().bind(controller.getForwardStack().sizeProperty().isEqualTo(0));
    }

    @Override
    public void handle(ActionEvent ae) {
        controller.goForward();
    }
}
