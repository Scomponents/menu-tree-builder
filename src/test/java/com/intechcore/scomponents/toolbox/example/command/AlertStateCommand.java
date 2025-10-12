/*
 * Copyright 2008-2025 Intechcore GmbH
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

package com.intechcore.scomponents.toolbox.example.command;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Window;

import com.intechcore.scomponents.common.core.event.events.DisabledStateEvent;
import com.intechcore.scomponents.fx.menubuilder.command.AbstractCommand;
import com.intechcore.scomponents.fx.menubuilder.command.ICommandParameter;
import com.intechcore.scomponents.fx.menubuilder.config.CommandUiData;
import com.intechcore.scomponents.toolbox.example.toolbar.AppState;
import com.intechcore.scomponents.toolbox.example.toolbar.Text;

import java.util.concurrent.CompletableFuture;

/**
 * A command that shows an alert with the current state of the application
 */
public class AlertStateCommand extends AbstractCommand<AppState> {
    private final Window ownerWindow;
    private final String title;
    private final Alert.AlertType alertType;

    /**
     * Constructs a new AlertStateCommand
     * @param headers the headers for the alert
     * @param alertType the type of the alert
     * @param ownerWindow the owner window of the alert
     */
    public AlertStateCommand(Text headers, Alert.AlertType alertType, Window ownerWindow) {
        super(new CommandUiData(headers.getFullText(), headers.getShortText(), null, DisabledStateEvent.class));
        this.title = headers.getShortText().getText(null);
        this.alertType = alertType;
        this.ownerWindow = ownerWindow;
    }

    @Override
    public CompletableFuture<Void> execute(ICommandParameter<AppState> data) {
        Alert result = new Alert(this.alertType);
        result.initOwner(this.ownerWindow);
        result.setTitle(this.title);
        result.setContentText(data.getCustom().toString());
        return CompletableFuture.runAsync(result::showAndWait, Platform::runLater);
    }
}
