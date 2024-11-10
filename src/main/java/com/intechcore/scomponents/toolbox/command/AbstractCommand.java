/*******************************************************************************
 *  Copyright (C) 2008-2024 Intechcore GmbH - All Rights Reserved
 *
 *  This file is part of SComponents project
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *
 *  Proprietary and confidential
 *
 *  Written by Intechcore GmbH <info@intechcore.com>
 ******************************************************************************/

package com.intechcore.scomponents.toolbox.command;

import com.intechcore.scomponents.toolbox.config.CommandUiData;
import com.intechcore.scomponents.toolbox.config.ToggleGroupCommandConfig;

public abstract class AbstractCommand<TParam> implements ICommand<TParam> {
    private final CommandUiData commandInfo;
    private final ToggleGroupCommandConfig<?, ? extends ICommandInfo> groupCommandInfo;

    protected AbstractCommand(CommandUiData commandInfo) {
        this(commandInfo, null);
    }

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

    public CommandUiData getCommandInfo() {
        return this.commandInfo;
    }

    public ToggleGroupCommandConfig<?, ? extends ICommandInfo> getGroupCommandInfo() {
        return this.groupCommandInfo;
    }
}
