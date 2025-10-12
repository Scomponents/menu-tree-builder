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

/**
 * Represents a parameter for a command
 * @param <TCustomParameter> the type of the custom parameter
 */
public interface ICommandParameter<TCustomParameter> {
    /**
     * @return the source of the command
     */
    CommandSource getSource();

    /**
     * @return the result of the command
     */
    Object getResult();

    /**
     * Cancels the execution of the command
     */
    void cancelExecution();

    /**
     * @return true if the command has been cancelled, false otherwise
     */
    boolean isCancelled();

    /**
     * @return the custom parameter
     */
    default TCustomParameter getCustom() {
        return null;
    }
}
