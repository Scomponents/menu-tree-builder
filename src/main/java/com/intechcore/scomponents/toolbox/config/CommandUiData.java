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
import com.intechcore.scomponents.toolbox.control.ITranslatedText;

public class CommandUiData {
    public final ITranslatedText fullName;
    public final ITranslatedText shortName;
    public final Class<? extends AbstractDataEvent<?>> changeValueEventClass;
    public final Class<? extends DisabledStateEvent> disableEventClass;
    public final Object defaultValue;

    public CommandUiData(ITranslatedText fullName, ITranslatedText shortName,
                         Class<? extends AbstractDataEvent<?>> changeValueEventClass,
                         Class<? extends DisabledStateEvent> disableEventClass,
                         Object defaultValue) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.changeValueEventClass = changeValueEventClass;
        this.disableEventClass = disableEventClass;
        this.defaultValue = defaultValue;
    }

    public CommandUiData(ITranslatedText fullName, ITranslatedText shortName,
                         Class<? extends AbstractDataEvent<?>> changeValueEventClass,
                         Class<? extends DisabledStateEvent> disableEventClass) {
        this(fullName, shortName, changeValueEventClass, disableEventClass, null);
    }

    public CommandUiData(ITranslatedText fullName, ITranslatedText shortName) {
        this(fullName, shortName, null, null);
    }

    public CommandUiData(Class<? extends AbstractDataEvent<?>> changeValueEventClass,
                         Class<? extends DisabledStateEvent> disableEventClass) {
        this(null, null, changeValueEventClass, disableEventClass);
    }

    public ITranslatedText getFullName() {
        return this.fullName;
    }

    public ITranslatedText getShortName() {
        return this.shortName;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public Class<? extends AbstractDataEvent<?>> getChangeValueEventClass() {
        return this.changeValueEventClass;
    }

    public Class<? extends DisabledStateEvent> getDisableEventClass() {
        return this.disableEventClass;
    }
}
