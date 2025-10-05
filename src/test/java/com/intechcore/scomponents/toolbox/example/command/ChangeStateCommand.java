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

import com.intechcore.scomponents.toolbox.command.AbstractCommand;
import com.intechcore.scomponents.toolbox.command.ICommandInfo;
import com.intechcore.scomponents.toolbox.command.ICommandParameter;
import com.intechcore.scomponents.toolbox.config.CommandUiData;
import com.intechcore.scomponents.toolbox.example.toolbar.AppState;
import com.intechcore.scomponents.toolbox.example.toolbar.Text;

import java.util.concurrent.CompletableFuture;

public class ChangeStateCommand extends AbstractCommand<AppState> {
    private final Type commandType;

    public ChangeStateCommand(Type commandType) {
        super(commandType.getData());
        this.commandType = commandType;
    }

    @Override
    public CompletableFuture<Void> execute(ICommandParameter<AppState> data) {
        switch (this.commandType) {
            case CHANGE_TOOL_ONE:
                data.getCustom().setToolFirstEnabled(true);
                break;
            case CHANGE_TOOL_TWO:
                data.getCustom().setToolSecondEnabled(true);
                break;
            case CHANGE_TOOL_THREE:
                data.getCustom().setToolThirdEnabled(true);
                break;
        }
        return CompletableFuture.completedFuture(null);
    }

    public enum Type implements ICommandInfo {
        CHANGE_TOOL_ONE(new CommandUiData(new Text.TranslatedText("Tool ONE"), new Text.TranslatedText("Tool One Full"), null, null)),
        CHANGE_TOOL_TWO(new CommandUiData(new Text.TranslatedText("Tool TWO"), new Text.TranslatedText("Tool Two Full"), null, null)),
        CHANGE_TOOL_THREE(new CommandUiData(new Text.TranslatedText("Tool THREE"), new Text.TranslatedText("Tool Three Full"), null, null));

        public final CommandUiData info;

        Type(CommandUiData info) {
            this.info = info;
        }

        @Override
        public CommandUiData getData() {
            return this.info;
        }
    }
}
