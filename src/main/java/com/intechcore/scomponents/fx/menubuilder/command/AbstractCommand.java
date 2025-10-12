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

package com.intechcore.scomponents.fx.menubuilder.command;

import com.intechcore.scomponents.fx.menubuilder.config.CommandUiData;
import com.intechcore.scomponents.fx.menubuilder.config.ToggleGroupCommandConfig;

/**
 * An abstract base class for commands
 * @param <TParam> the type of the command parameter
 */
public abstract class AbstractCommand<TParam> implements ICommand<TParam> {
    private final CommandUiData commandInfo;
    private final ToggleGroupCommandConfig<?, ? extends ICommandInfo> groupCommandInfo;

    /**
     * Constructs a new AbstractCommand
     * @param commandInfo the command UI data
     */
    protected AbstractCommand(CommandUiData commandInfo) {
        this(commandInfo, null);
    }

    /**
     * Constructs a new AbstractCommand
     * @param commandInfo the toggle group command config
     */
    protected AbstractCommand(ToggleGroupCommandConfig<?, ? extends ICommandInfo> commandInfo) {
        this(null, commandInfo);
    }

    private AbstractCommand(CommandUiData commandInfo,
                            ToggleGroupCommandConfig<?, ? extends ICommandInfo> groupCommandInfo) {
        this.commandInfo = commandInfo;
        this.groupCommandInfo = groupCommandInfo;
    }

    @Override
    public boolean initiallyDisabled() {
        return false;
    }

    @Override
    public CommandResultsSource<Object> getDataSource() {
        return null;
    }

    /**
     * @return the command UI data
     */
    public CommandUiData getCommandInfo() {
        return this.commandInfo;
    }

    /**
     * @return the toggle group command config
     */
    public ToggleGroupCommandConfig<?, ? extends ICommandInfo> getGroupCommandInfo() {
        return this.groupCommandInfo;
    }
}
