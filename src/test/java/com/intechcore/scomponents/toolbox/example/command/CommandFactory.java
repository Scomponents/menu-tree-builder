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

import javafx.scene.control.Alert;
import javafx.stage.Window;

import com.intechcore.scomponents.fx.menubuilder.command.AbstractCommand;
import com.intechcore.scomponents.fx.menubuilder.command.CommandFactoryStorage;
import com.intechcore.scomponents.fx.menubuilder.command.ICommandFactory;
import com.intechcore.scomponents.fx.menubuilder.command.ICommandGroup;
import com.intechcore.scomponents.fx.menubuilder.config.IToolboxCommandConfig;
import com.intechcore.scomponents.toolbox.example.toolbar.AppState;
import com.intechcore.scomponents.toolbox.example.toolbar.ExampleMenuItem;
import com.intechcore.scomponents.toolbox.example.toolbar.Text;
import com.intechcore.scomponents.toolbox.example.toolbar.ToggleGroup;

import java.util.concurrent.CompletableFuture;

/**
 * A factory for creating commands for the example application
 */
public class CommandFactory implements ICommandFactory<AppState> {
    private final Window parent;
    private final CommandFactoryStorage factoryStorage = new CommandFactoryStorage();
    private final AppState service;

    /**
     * Constructs a new CommandFactory
     * @param service the application state
     * @param parent the parent window
     */
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
