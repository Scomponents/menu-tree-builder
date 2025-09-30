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

import java.util.stream.Stream;

public enum ExampleMenuItem implements IToolboxCommandConfig {
    CONFIRMATION_STATE(Icons.BRICK_WALL, ControlType.BUTTON),
    ALERT_INFO(Icons.INFO, ControlType.BUTTON),
    ALERT_WARNING(Icons.WARNING, ControlType.BUTTON),
    ALERT_ERROR(Icons.ALERT, ControlType.BUTTON),
    TOGGLE1_STATE_1(Icons.MENU, ControlType.TOGGLE_BUTTON, ToggleGroup.TOGGLE_GROUP1),
    TOGGLE1_STATE_2(Icons.PAINT_ROLLER, ControlType.TOGGLE_BUTTON, ToggleGroup.TOGGLE_GROUP1),
    TOGGLE1_STATE_3(Icons.BRICK_WALL, ControlType.TOGGLE_BUTTON, ToggleGroup.TOGGLE_GROUP1),
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
