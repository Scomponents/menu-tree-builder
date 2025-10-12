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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * A storage for command factories
 */
public class CommandFactoryStorage {
    private final Map<IToolboxCommandConfig, Supplier<CompletableFuture<AbstractCommand<?>>>>
            singleCommandsMap = new HashMap<>();
    private final Map<IToolboxCommandConfig, Supplier<AbstractCommand<?>>>
            completedSingleCommandsMap = new HashMap<>();
    private final Map<ICommandGroup<?>, Supplier<CompletableFuture<AbstractCommand<?>>>>
            groupCommandsMap = new HashMap<>();
    private final Map<ICommandGroup<?>, Supplier<AbstractCommand<?>>>
            completedGroupCommandsMap = new HashMap<>();

    /**
     * Constructs a new CommandFactoryStorage
     */
    public CommandFactoryStorage() {
    }

    /**
     * Adds a factory for a single command
     * @param toolboxCommandConfig the command config
     * @param factory the factory to add
     * @return this instance
     */
    public CommandFactoryStorage addSingle(
            IToolboxCommandConfig toolboxCommandConfig,
            Supplier<CompletableFuture<AbstractCommand<?>>> factory) {
        this.singleCommandsMap.put(toolboxCommandConfig, factory);
        return this;
    }

    /**
     * Adds a factory for a group command
     * @param commandGroup the command group
     * @param factory the factory to add
     * @param <TGroupEnum> the type of the command group
     * @return this instance
     */
    public <TGroupEnum extends Enum<TGroupEnum>> CommandFactoryStorage addGroup(
            ICommandGroup<TGroupEnum> commandGroup,
            Supplier<CompletableFuture<AbstractCommand<?>>> factory) {
        this.groupCommandsMap.put(commandGroup, factory);
        return this;
    }

    /**
     * Adds a factory for a single command that is already completed
     * @param commandConfig the command config
     * @param factory the factory to add
     * @return this instance
     */
    public CommandFactoryStorage addCompletedSingle(
            IToolboxCommandConfig commandConfig,
            Supplier<AbstractCommand<?>> factory) {
        this.completedSingleCommandsMap.put(commandConfig, factory);
        return this;
    }

    /**
     * Adds a factory for a group command that is already completed
     * @param commandGroup the command group
     * @param factory the factory to add
     * @param <TGroupEnum> the type of the command group
     * @return this instance
     */
    public <TGroupEnum extends Enum<TGroupEnum>> CommandFactoryStorage createCompletedGroup(
            ICommandGroup<TGroupEnum> commandGroup,
            Supplier<AbstractCommand<?>> factory) {
        this.completedGroupCommandsMap.put(commandGroup, factory);
        return this;
    }

    /**
     * Creates a single command
     * @param commandConfig the command config
     * @param <TParam> the type of the command parameter
     * @return a {@link CompletableFuture} that will be completed with the created command
     */
    public <TParam> CompletableFuture<AbstractCommand<TParam>> createSingleCommand(IToolboxCommandConfig commandConfig) {
        Supplier<CompletableFuture<AbstractCommand<?>>> resultSupplier = this.singleCommandsMap.get(commandConfig);

        if (resultSupplier != null) {
            return resultSupplier.get().thenApply(abstractCommand -> (AbstractCommand<TParam>) abstractCommand);
        }

        Supplier<AbstractCommand<?>> completedResult = this.completedSingleCommandsMap.get(commandConfig);
        AbstractCommand<TParam> result = null;
        if (completedResult != null) {
            result = (AbstractCommand<TParam>) completedResult.get();
        }
        return CompletableFuture.completedFuture(result);
    }

    /**
     * Creates a group command
     * @param commandGroup the command group
     * @param <TGroupEnum> the type of the command group
     * @param <TParam> the type of the command parameter
     * @return a {@link CompletableFuture} that will be completed with the created command
     */
    public <TGroupEnum extends Enum<TGroupEnum>, TParam> CompletableFuture<AbstractCommand<TParam>> createGroupCommand(
            ICommandGroup<TGroupEnum> commandGroup) {
        Supplier<CompletableFuture<AbstractCommand<?>>> resultSupplier = this.groupCommandsMap.get(commandGroup);
        if (resultSupplier != null) {
            return resultSupplier.get().thenApply(abstractCommand -> (AbstractCommand<TParam>) abstractCommand);
        }

        Supplier<AbstractCommand<?>> completedResult = this.completedGroupCommandsMap.get(commandGroup);
        AbstractCommand<TParam> result = null;
        if (completedResult != null) {
            result = (AbstractCommand<TParam>) completedResult.get();
        }
        return CompletableFuture.completedFuture(result);
    }
}
