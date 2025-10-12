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

import com.intechcore.scomponents.fx.menubuilder.command.AbstractCommand;
import com.intechcore.scomponents.fx.menubuilder.command.ICommandInfo;
import com.intechcore.scomponents.fx.menubuilder.command.ICommandParameter;
import com.intechcore.scomponents.fx.menubuilder.config.CommandUiData;
import com.intechcore.scomponents.fx.menubuilder.config.IToolboxCommandConfig;
import com.intechcore.scomponents.fx.menubuilder.config.ToggleGroupCommandConfig;
import com.intechcore.scomponents.toolbox.example.toolbar.AppState;
import com.intechcore.scomponents.toolbox.example.toolbar.ExampleMenuItem;
import com.intechcore.scomponents.toolbox.example.toolbar.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * A command for the first toggle group
 */
public class ToggleOneCommand extends AbstractCommand<AppState> {
    private static Text state1Text = new Text("State 1");
    private static Text state2Text = new Text("State 2");
    private static Text state3Text = new Text("State 3");

    /**
     * Constructs a new ToggleOneCommand
     */
    public ToggleOneCommand() {
        super(new ToggleGroupCommandConfig<>(commandToTypeMap, null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<Void> execute(ICommandParameter<AppState> data) {
        return CompletableFuture.completedFuture(null);
    }

    /**
     * the configuration for a toggle group command (for convenience)
     */
    private static final Map<IToolboxCommandConfig, ICommandInfo> commandToTypeMap = new HashMap<IToolboxCommandConfig, ICommandInfo>() {
        {
            put(ExampleMenuItem.TOGGLE1_STATE_1, () -> new CommandUiData(state1Text.getFullText(), state1Text.getShortText(), null, null));
            put(ExampleMenuItem.TOGGLE1_STATE_2, () -> new CommandUiData(state2Text.getFullText(), state2Text.getShortText(), null, null));
            put(ExampleMenuItem.TOGGLE1_STATE_3, () -> new CommandUiData(state3Text.getFullText(), state3Text.getShortText(), null, null));
        }
    };
}
