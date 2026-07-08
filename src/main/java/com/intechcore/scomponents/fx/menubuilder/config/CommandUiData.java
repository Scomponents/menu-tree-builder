/*
 * Copyright (c) 2026-present, Intechcore GmbH
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

package com.intechcore.scomponents.fx.menubuilder.config;

import com.intechcore.scomponents.common.core.event.events.AbstractDataEvent;
import com.intechcore.scomponents.common.core.event.events.DisabledStateEvent;
import com.intechcore.scomponents.common.core.i18n.II18nKey;

/**
 * Represents the UI data for a command
 */
public class CommandUiData {
    /**
     * The full name of the command
     */
    private final II18nKey fullNameKey;
    /**
     * The short name of the command
     */
    private final II18nKey shortNameKey;
    /**
     * The event class for changing the value of the command
     */
    private final Class<? extends AbstractDataEvent<?>> changeValueEventClass;
    /**
     * The event class for disabling the command
     */
    private final Class<? extends DisabledStateEvent> disableEventClass;
    /**
     * The default value of the command
     */
    private final Object defaultValue;

    /**
     * Constructs a new CommandUiData
     * @param fullNameKey the full name of the command
     * @param shortNameKey the short name of the command
     * @param changeValueEventClass the event class for changing the value of the command
     * @param disableEventClass the event class for disabling the command
     * @param defaultValue the default value of the command
     */
    public CommandUiData(II18nKey fullNameKey,
                         II18nKey shortNameKey,
                         Class<? extends AbstractDataEvent<?>> changeValueEventClass,
                         Class<? extends DisabledStateEvent> disableEventClass,
                         Object defaultValue) {
        this.changeValueEventClass = changeValueEventClass;
        this.disableEventClass = disableEventClass;
        this.defaultValue = defaultValue;
        this.fullNameKey = fullNameKey;
        this.shortNameKey = shortNameKey;
    }

    /**
     * Constructs a new CommandUiData
     * @param fullName the full name of the command
     * @param shortName the short name of the command
     * @param changeValueEventClass the event class for changing the value of the command
     * @param disableEventClass the event class for disabling the command
     */
    public CommandUiData(II18nKey fullName,
                         II18nKey shortName,
                         Class<? extends AbstractDataEvent<?>> changeValueEventClass,
                         Class<? extends DisabledStateEvent> disableEventClass) {
        this(
                fullName,
                shortName,
                changeValueEventClass,
                disableEventClass,
                null
        );
    }

    /**
     * Constructs a new CommandUiData
     * @param fullName the full name of the command
     * @param shortName the short name of the command
     */
    public CommandUiData(II18nKey fullName, II18nKey shortName) {
        this(fullName, shortName, null, null);
    }

    /**
     * Constructs a new CommandUiData
     * @param changeValueEventClass the event class for changing the value of the command
     * @param disableEventClass the event class for disabling the command
     */
    public CommandUiData(Class<? extends AbstractDataEvent<?>> changeValueEventClass,
                         Class<? extends DisabledStateEvent> disableEventClass) {
        this(null, null, changeValueEventClass, disableEventClass);
    }

    /**
     * @return the full name of the command
     */
    public II18nKey getFullName() {
        return this.fullNameKey;
    }

    /**
     * @return the short name of the command
     */
    public II18nKey getShortName() {
        return this.shortNameKey;
    }

    /**
     * @return the default value of the command
     */
    public Object getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * @return the event class for changing the value of the command
     */
    public Class<? extends AbstractDataEvent<?>> getChangeValueEventClass() {
        return this.changeValueEventClass;
    }

    /**
     * @return the event class for disabling the command
     */
    public Class<? extends DisabledStateEvent> getDisableEventClass() {
        return this.disableEventClass;
    }
}
