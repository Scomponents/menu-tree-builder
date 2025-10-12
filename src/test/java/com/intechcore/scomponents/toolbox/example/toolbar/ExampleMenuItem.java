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

import java.util.stream.Stream;

/**
 * An enum that represents a menu item in the example application
 */
public enum ExampleMenuItem implements IToolboxCommandConfig {
    /**
     * Shows a confirmation alert
     */
    CONFIRMATION_STATE(Icons.BRICK_WALL, ControlType.BUTTON),
    /**
     * Shows an info alert
     */
    ALERT_INFO(Icons.INFO, ControlType.BUTTON),
    /**
     * Shows a warning alert
     */
    ALERT_WARNING(Icons.WARNING, ControlType.BUTTON),
    /**
     * Shows an error alert
     */
    ALERT_ERROR(Icons.ALERT, ControlType.BUTTON),
    /**
     * The first state of the first toggle group
     */
    TOGGLE1_STATE_1(Icons.SQUARE_PERCENT, ControlType.TOGGLE_BUTTON, ToggleGroup.TOGGLE_GROUP1),
    /**
     * The second state of the first toggle group
     */
    TOGGLE1_STATE_2(Icons.PAINT_ROLLER, ControlType.TOGGLE_BUTTON, ToggleGroup.TOGGLE_GROUP1),
    /**
     * The third state of the first toggle group
     */
    TOGGLE1_STATE_3(Icons.MEGAPHONE, ControlType.TOGGLE_BUTTON, ToggleGroup.TOGGLE_GROUP1),
    ;

    private final IIcon icon;
    private final ControlType controlType;
    private final ToggleGroup toggleGroup;

    ExampleMenuItem(IIcon icon, ControlType controlType) {
        this(icon, controlType, null);
    }

    ExampleMenuItem(IIcon icon, ControlType controlType, ToggleGroup toggleGroup) {
        this.icon = icon;
        this.controlType = controlType;
        this.toggleGroup = toggleGroup;
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
        return Stream.empty();
    }

    @Override
    public ToggleGroup getToggleGroup() {
        return this.toggleGroup;
    }
}
