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

package com.intechcore.scomponents.toolbox.example.toolbar;

import com.intechcore.scomponents.fx.menubuilder.config.IToolboxCommandConfig;
import com.intechcore.scomponents.fx.menubuilder.control.icon.IIcon;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * An enum that represents a submenu item in the example application
 */
public enum ExampleSubmenuItem implements IToolboxCommandConfig {
    /**
     * The alert menu
     */
    ALERT_MENU(Icons.MENU, ControlType.SUBMENU),
    /**
     * The square menu
     */
    SQUARE_MENU(Icons.SQUARE_MENU, ControlType.SUBMENU);

    private final IIcon icon;
    private final ControlType controlType;
    private final Set<IToolboxCommandConfig> subItems = new LinkedHashSet<>();

    ExampleSubmenuItem(IIcon icon, ControlType controlType) {
        this.icon = icon;
        this.controlType = controlType;
    }

    /**
     * Adds the given command configs as sub-items
     * @param commandConfigs the command configs to add
     * @return this instance
     */
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
