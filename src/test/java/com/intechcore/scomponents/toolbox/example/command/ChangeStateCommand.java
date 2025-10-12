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
import com.intechcore.scomponents.toolbox.example.toolbar.AppState;
import com.intechcore.scomponents.toolbox.example.toolbar.Text;

import java.util.concurrent.CompletableFuture;

/**
 * A command that changes the state of the application
 */
public class ChangeStateCommand extends AbstractCommand<AppState> {
    private final Type commandType;

    /**
     * Constructs a new ChangeStateCommand
     * @param commandType the type of the command
     */
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

    /**
     * The type of the command
     */
    public enum Type implements ICommandInfo {
        /**
         * Changes the state of tool one
         */
        CHANGE_TOOL_ONE(new CommandUiData(new Text.TranslatedText("Tool ONE"), new Text.TranslatedText("Tool One Full"), null, null)),
        /**
         * Changes the state of tool two
         */
        CHANGE_TOOL_TWO(new CommandUiData(new Text.TranslatedText("Tool TWO"), new Text.TranslatedText("Tool Two Full"), null, null)),
        /**
         * Changes the state of tool three
         */
        CHANGE_TOOL_THREE(new CommandUiData(new Text.TranslatedText("Tool THREE"), new Text.TranslatedText("Tool Three Full"), null, null));

        /**
         * The UI data for the command
         */
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
