/*******************************************************************************
 *  Copyright (C) 2008-2024 Intechcore GmbH - All Rights Reserved
 *
 *  This file is part of SComponents project
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *
 *  Proprietary and confidential
 *
 *  Written by Intechcore GmbH <info@intechcore.com>
 ******************************************************************************/

package com.intechcore.scomponents.toolbox.config;

import com.intechcore.scomponents.common.core.event.events.AbstractDataEvent;
import com.intechcore.scomponents.common.core.event.events.DisabledStateEvent;
import com.intechcore.scomponents.toolbox.command.ICommandInfo;
import com.intechcore.scomponents.toolbox.control.ITranslatedText;

import java.util.Map;
import java.util.stream.Collectors;

public class ToggleGroupCommandConfig<TEventData, TCommandTypeEnum extends ICommandInfo> {
    private final Map<ICommandInfo, IToolboxCommandConfig> commandInfoToConfigMap;
    private final Map<IToolboxCommandConfig, ICommandInfo> commandConfigToInfoMap;
    private final Class<? extends AbstractDataEvent<TEventData>> changeValueEventClass;
    private final Class<? extends DisabledStateEvent> disableEventClass;
    private final Map<TEventData, TCommandTypeEnum> configMap;

    public ToggleGroupCommandConfig(Map<IToolboxCommandConfig, ICommandInfo> commandInfoToConfigMap,
                                    Class<? extends AbstractDataEvent<TEventData>> changeValueEventClass,
                                    Map<TEventData, TCommandTypeEnum> configMap) {
        this(commandInfoToConfigMap, changeValueEventClass, null, configMap);
    }

    public ToggleGroupCommandConfig(Map<IToolboxCommandConfig, ICommandInfo> commandInfoToConfigMap,
                                    Class<? extends AbstractDataEvent<TEventData>> changeValueEventClass) {
        this(commandInfoToConfigMap, changeValueEventClass, null, null);
    }

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

    public Map<ICommandInfo, IToolboxCommandConfig> getCommandInfoToConfigMap() {
        return this.commandInfoToConfigMap;
    }

    public ITranslatedText getShortName(IToolboxCommandConfig command) {
        return this.commandConfigToInfoMap.get(command).getData().getShortName();
    }

    public ITranslatedText getFullName(IToolboxCommandConfig command) {
        return this.commandConfigToInfoMap.get(command).getData().getFullName();
    }

    public Class<? extends AbstractDataEvent<?>> getChangeValueEventClass() {
        return this.changeValueEventClass;
    }

    public Class<? extends DisabledStateEvent> getDisableEventClass() {
        return this.disableEventClass;
    }

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
