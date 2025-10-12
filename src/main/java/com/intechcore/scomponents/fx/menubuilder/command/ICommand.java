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

import java.util.concurrent.CompletableFuture;

/**
 * Represents a command that can be executed
 * @param <TValue> the type of the command parameter
 */
public interface ICommand<TValue> {
    /**
     * Executes the command
     * @param data the command parameter
     * @return a {@link CompletableFuture} that will be completed when the command has finished executing
     */
    CompletableFuture<Void> execute(ICommandParameter<TValue> data);

    /**
     * @return the data source for this command. This is used for commands that need to populate a control with data, such as a combo box
     */
    CommandResultsSource<Object> getDataSource();

    /**
     * @return true if the command should be initially disabled, false otherwise
     */
    boolean initiallyDisabled();
}
