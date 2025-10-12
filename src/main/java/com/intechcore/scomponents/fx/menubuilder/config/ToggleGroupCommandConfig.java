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

package com.intechcore.scomponents.fx.menubuilder.config;

import com.intechcore.scomponents.common.core.event.events.AbstractDataEvent;
import com.intechcore.scomponents.common.core.event.events.DisabledStateEvent;
import com.intechcore.scomponents.fx.menubuilder.command.ICommandInfo;
import com.intechcore.scomponents.fx.menubuilder.control.ITranslatedText;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents the configuration for a toggle group command
 * @param <TEventData> the type of the event data
 * @param <TCommandTypeEnum> the type of the command type enum
 */
public class ToggleGroupCommandConfig<TEventData, TCommandTypeEnum extends ICommandInfo> {
    private final Map<ICommandInfo, IToolboxCommandConfig> commandInfoToConfigMap;
    private final Map<IToolboxCommandConfig, ICommandInfo> commandConfigToInfoMap;
    private final Class<? extends AbstractDataEvent<TEventData>> changeValueEventClass;
    private final Class<? extends DisabledStateEvent> disableEventClass;
    private final Map<TEventData, TCommandTypeEnum> configMap;

    /**
     * Constructs a new ToggleGroupCommandConfig
     * @param commandInfoToConfigMap a map from command info to command config
     * @param changeValueEventClass the event class for changing the value of the command
     * @param configMap a map from event data to command type
     */
    public ToggleGroupCommandConfig(Map<IToolboxCommandConfig, ICommandInfo> commandInfoToConfigMap,
                                    Class<? extends AbstractDataEvent<TEventData>> changeValueEventClass,
                                    Map<TEventData, TCommandTypeEnum> configMap) {
        this(commandInfoToConfigMap, changeValueEventClass, null, configMap);
    }

    /**
     * Constructs a new ToggleGroupCommandConfig
     * @param commandInfoToConfigMap a map from command info to command config
     * @param changeValueEventClass the event class for changing the value of the command
     */
    public ToggleGroupCommandConfig(Map<IToolboxCommandConfig, ICommandInfo> commandInfoToConfigMap,
                                    Class<? extends AbstractDataEvent<TEventData>> changeValueEventClass) {
        this(commandInfoToConfigMap, changeValueEventClass, null, null);
    }

    /**
     * Constructs a new ToggleGroupCommandConfig
     * @param commandInfoToConfigMap a map from command info to command config
     * @param changeValueEventClass the event class for changing the value of the command
     * @param disableEventClass the event class for disabling the command
     * @param configMap a map from event data to command type
     */
    public ToggleGroupCommandConfig(Map<IToolboxCommandConfig, ICommandInfo> commandInfoToConfigMap,
                                    Class<? extends AbstractDataEvent<TEventData>> changeValueEventClass,
                                    Class<? extends DisabledStateEvent> disableEventClass,
                                    Map<TEventData, TCommandTypeEnum> configMap) {
        this.commandConfigToInfoMap = commandInfoToConfigMap;
        this.commandInfoToConfigMap = commandInfoToConfigMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        this.changeValueEventClass = changeValueEventClass;
        this.disableEventClass = disableEventClass;
        this.configMap = configMap;
    }

    /**
     * @return a map from command info to command config
     */
    public Map<ICommandInfo, IToolboxCommandConfig> getCommandInfoToConfigMap() {
        return this.commandInfoToConfigMap;
    }

    /**
     * @param command the command to get the short name for
     * @return the short name for the given command
     */
    public ITranslatedText getShortName(IToolboxCommandConfig command) {
        return this.commandConfigToInfoMap.get(command).getData().getShortName();
    }

    /**
     * @param command the command to get the full name for
     * @return the full name for the given command
     */
    public ITranslatedText getFullName(IToolboxCommandConfig command) {
        return this.commandConfigToInfoMap.get(command).getData().getFullName();
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

    /**
     * Converts an event to a command type
     * @param changedValueEventData the event data
     * @param eventClass the event class
     * @param <TEvent> the type of the event
     * @return the command type for the given event
     */
    public <TEvent extends AbstractDataEvent<?>> IToolboxCommandConfig eventToCommandType(
            AbstractDataEvent<?> changedValueEventData, Class<TEvent> eventClass) {

        if (this.configMap == null || this.getCommandInfoToConfigMap() == null) {
            return null;
        }

        if (!eventClass.isAssignableFrom(changedValueEventData.getClass())) {
            return null;
        }

        TEventData eventData = (TEventData)((TEvent)changedValueEventData).getNewValue();
        ICommandInfo resultConfig = this.configMap.get(eventData);

        if (resultConfig == null) {
            return null;
        }

        return this.getCommandInfoToConfigMap().get(resultConfig);
    }
}
