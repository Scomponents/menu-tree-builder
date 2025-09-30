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

package com.intechcore.scomponents.toolbox.example.toolbar;

import com.intechcore.scomponents.toolbox.command.AbstractCommand;
import com.intechcore.scomponents.toolbox.command.ICommandInfo;
import com.intechcore.scomponents.toolbox.command.ICommandParameter;
import com.intechcore.scomponents.toolbox.config.CommandUiData;
import com.intechcore.scomponents.toolbox.config.IToolboxCommandConfig;
import com.intechcore.scomponents.toolbox.config.ToggleGroupCommandConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ToggleOneCommand extends AbstractCommand<AppState> {
    private static Text state1Text = new Text("State 1");
    private static Text state2Text = new Text("State 2");
    private static Text state3Text = new Text("State 3");

    public ToggleOneCommand() {
        super(new ToggleGroupCommandConfig<>(commandToTypeMap, null));
    }

    @Override
    public CompletableFuture<Void> execute(ICommandParameter<AppState> data) {
        return CompletableFuture.completedFuture(null);
    }

    private static final Map<IToolboxCommandConfig, ICommandInfo> commandToTypeMap = new HashMap<IToolboxCommandConfig, ICommandInfo>() {
        {
            put(ExampleMenuItem.TOGGLE1_STATE_1, () -> new CommandUiData(state1Text.getFullText(), state1Text.getShortText(), null, null));
            put(ExampleMenuItem.TOGGLE1_STATE_2, () -> new CommandUiData(state2Text.getFullText(), state2Text.getShortText(), null, null));
            put(ExampleMenuItem.TOGGLE1_STATE_3, () -> new CommandUiData(state3Text.getFullText(), state3Text.getShortText(), null, null));
        }
    };
}
