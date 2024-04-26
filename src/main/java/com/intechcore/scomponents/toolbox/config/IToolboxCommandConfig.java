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

import com.intechcore.scomponents.toolbox.command.ICommandGroup;
import com.intechcore.scomponents.toolbox.control.icon.IIcon;

import java.util.stream.Stream;

public interface IToolboxCommandConfig {
    IIcon getIcon();
    ControlType getControlType();
    Stream<IToolboxCommandConfig> getNestedCommands();
    <TCommandGroup extends Enum<TCommandGroup>> ICommandGroup<TCommandGroup> getToggleGroup();

    enum ControlType {
        BUTTON,
        TOGGLE_BUTTON,
        COMBOBOX,
        FONT_COMBOBOX,
        COLOR_PICKER,
        SUBMENU,
        TOGGLE_GROUP
    }
}
