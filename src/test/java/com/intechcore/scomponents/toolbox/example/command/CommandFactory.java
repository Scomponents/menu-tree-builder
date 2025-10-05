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

import javafx.scene.control.Alert;
import javafx.stage.Window;

import com.intechcore.scomponents.toolbox.command.AbstractCommand;
import com.intechcore.scomponents.toolbox.command.CommandFactoryStorage;
import com.intechcore.scomponents.toolbox.command.ICommandFactory;
import com.intechcore.scomponents.toolbox.command.ICommandGroup;
import com.intechcore.scomponents.toolbox.config.IToolboxCommandConfig;
import com.intechcore.scomponents.toolbox.example.toolbar.AppState;
import com.intechcore.scomponents.toolbox.example.toolbar.ExampleMenuItem;
import com.intechcore.scomponents.toolbox.example.toolbar.Text;
import com.intechcore.scomponents.toolbox.example.toolbar.ToggleGroup;

import java.util.concurrent.CompletableFuture;

public class CommandFactory implements ICommandFactory<AppState> {
    private final Window parent;
    private final CommandFactoryStorage factoryStorage = new CommandFactoryStorage();
    private final AppState service;

    public CommandFactory(AppState service, Window parent) {
        this.service = service;
        this.parent = parent;

        this.factoryStorage
                .addCompletedSingle(ExampleMenuItem.CONFIRMATION_STATE, () ->
                        new AlertStateCommand(
                                new Text("Confirmation", "Show State in confirmation"),
                                Alert.AlertType.CONFIRMATION, this.parent))
                .addCompletedSingle(ExampleMenuItem.ALERT_INFO, () ->
                        new ChangeStateCommand(ChangeStateCommand.Type.CHANGE_TOOL_ONE))
                .addCompletedSingle(ExampleMenuItem.ALERT_WARNING, () ->
                        new ChangeStateCommand(ChangeStateCommand.Type.CHANGE_TOOL_TWO))
                .addCompletedSingle(ExampleMenuItem.ALERT_ERROR, () ->
                        new ChangeStateCommand(ChangeStateCommand.Type.CHANGE_TOOL_THREE))
        ;

        this.factoryStorage
                .createCompletedGroup(ToggleGroup.TOGGLE_GROUP1, ToggleOneCommand::new);
    }

    @Override
    public CompletableFuture<AbstractCommand<AppState>> create(IToolboxCommandConfig commandType) {
        return this.factoryStorage.createSingleCommand(commandType);
    }

    @Override
    public CompletableFuture<AbstractCommand<AppState>> createGroupCommand(ICommandGroup<?> config) {
        return this.factoryStorage.createGroupCommand(config);
    }

    @Override
    public AppState createCommandParameter() {
        return this.service;
    }
}
