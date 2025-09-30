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

package com.intechcore.scomponents.toolbox.example.toolbar;

import com.intechcore.scomponents.toolbox.config.IToolboxCommandConfig;
import com.intechcore.scomponents.toolbox.control.icon.IIcon;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

public enum ExampleSubmenuItem implements IToolboxCommandConfig {
    ALERT_MENU(Icons.MENU, ControlType.SUBMENU),
    SQUARE_MENU(Icons.SQUARE_MENU, ControlType.SUBMENU);

    private final IIcon icon;
    private final ControlType controlType;
    private final Set<IToolboxCommandConfig> subItems = new LinkedHashSet<>();

    ExampleSubmenuItem(IIcon icon, ControlType controlType) {
        this.icon = icon;
        this.controlType = controlType;
    }

    public IToolboxCommandConfig add(IToolboxCommandConfig... commandConfigs) {
        this.subItems.addAll(Arrays.asList(commandConfigs));
        return this;
    }

    @Override
    public IIcon getIcon() {
        return this.icon;
    }

    @Override
    public ControlType getControlType() {
        return this.controlType;
    }

    @Override
    public Stream<IToolboxCommandConfig> getNestedCommands() {
        return this.subItems.stream();
    }
}
