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
 * A marker interface for a group of toolbox commands
 * @param <TCustomEnum> the type of the enum that implements this interface
 */
public interface ICommandGroup<TCustomEnum extends Enum<TCustomEnum>> {
    /**
     * Casts this instance to the enum type
     * @return this instance cast to the enum type
     */
    default TCustomEnum cast() {
        return (TCustomEnum)this;
    }
}
