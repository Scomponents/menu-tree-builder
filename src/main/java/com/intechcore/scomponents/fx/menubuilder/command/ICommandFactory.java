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

import com.intechcore.scomponents.fx.menubuilder.config.IToolboxCommandConfig;
import com.intechcore.scomponents.fx.menubuilder.control.ITranslatedText;

import java.util.concurrent.CompletableFuture;

/**
 * A factory for creating commands
 * @param <TCommandParam> the type of the command parameter
 */
public interface ICommandFactory<TCommandParam> {
    /**
     * Creates a command for the given command type
     * @param commandType the command type to create a command for
     * @return a {@link CompletableFuture} that will be completed with the created command
     */
    CompletableFuture<AbstractCommand<TCommandParam>> create(IToolboxCommandConfig commandType);

    /**
     * Creates a command parameter
     * @return the created command parameter
     */
    TCommandParam createCommandParameter();

    /**
     * Creates a group command for the given command group
     * @param config the command group to create a command for
     * @return a {@link CompletableFuture} that will be completed with the created command
     */
    default CompletableFuture<AbstractCommand<TCommandParam>> createGroupCommand(ICommandGroup<?> config) {
        return null;
    }

    /**
     * Creates a tooltip for the given command type
     * @param commandType the command type to create a tooltip for
     * @return the created tooltip
     */
    default ITranslatedText createTooltip(IToolboxCommandConfig commandType) {
        return null;
    }
}
