/*
 *  Copyright (C) 2008-2025 Intechcore GmbH - All Rights Reserved
 *
 *  This file is part of SComponents project
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *
 *  Proprietary and confidential
 *
 *  Written by Intechcore GmbH <info@intechcore.com>
 */

package com.intechcore.scomponents.toolbox.example.command;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Window;

import com.intechcore.scomponents.common.core.event.events.DisabledStateEvent;
import com.intechcore.scomponents.toolbox.command.AbstractCommand;
import com.intechcore.scomponents.toolbox.command.ICommandParameter;
import com.intechcore.scomponents.toolbox.config.CommandUiData;
import com.intechcore.scomponents.toolbox.example.toolbar.AppState;
import com.intechcore.scomponents.toolbox.example.toolbar.Text;

import java.util.concurrent.CompletableFuture;

public class AlertStateCommand extends AbstractCommand<AppState> {
    private final Window ownerWindow;
    private final String title;
    private final Alert.AlertType alertType;

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
