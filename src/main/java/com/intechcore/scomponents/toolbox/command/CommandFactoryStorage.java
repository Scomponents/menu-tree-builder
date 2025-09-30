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

package com.intechcore.scomponents.toolbox.command;

import com.intechcore.scomponents.toolbox.config.IToolboxCommandConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CommandFactoryStorage {
    private final Map<IToolboxCommandConfig, Supplier<CompletableFuture<AbstractCommand<?>>>>
            singleCommandsMap = new HashMap<>();
    private final Map<IToolboxCommandConfig, Supplier<AbstractCommand<?>>>
            completedSingleCommandsMap = new HashMap<>();
    private final Map<ICommandGroup<?>, Supplier<CompletableFuture<AbstractCommand<?>>>>
            groupCommandsMap = new HashMap<>();
    private final Map<ICommandGroup<?>, Supplier<AbstractCommand<?>>>
            completedGroupCommandsMap = new HashMap<>();

    public CommandFactoryStorage() {
    }

    public CommandFactoryStorage addSingle(
            IToolboxCommandConfig toolboxCommandConfig,
            Supplier<CompletableFuture<AbstractCommand<?>>> factory) {
        this.singleCommandsMap.put(toolboxCommandConfig, factory);
        return this;
    }

    public <TGroupEnum extends Enum<TGroupEnum>> CommandFactoryStorage addGroup(
            ICommandGroup<TGroupEnum> commandGroup,
            Supplier<CompletableFuture<AbstractCommand<?>>> factory) {
        this.groupCommandsMap.put(commandGroup, factory);
        return this;
    }

    public CommandFactoryStorage addCompletedSingle(
            IToolboxCommandConfig commandConfig,
            Supplier<AbstractCommand<?>> factory) {
        this.completedSingleCommandsMap.put(commandConfig, factory);
        return this;
    }

    public <TGroupEnum extends Enum<TGroupEnum>> CommandFactoryStorage createCompletedGroup(
            ICommandGroup<TGroupEnum> commandGroup,
            Supplier<AbstractCommand<?>> factory) {
        this.completedGroupCommandsMap.put(commandGroup, factory);
        return this;
    }

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
