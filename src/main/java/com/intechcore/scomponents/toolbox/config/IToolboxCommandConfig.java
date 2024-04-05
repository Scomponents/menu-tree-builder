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
import javafx.scene.Node;

import java.util.stream.Stream;

public interface IToolboxCommandConfig {
    Node createIcon();
    ControlType getControlType();
    Stream<IToolboxCommandConfig> getNestedCommands();
    <TCommandGroup extends Enum<TCommandGroup>> ICommandGroup<TCommandGroup> getToggleGroup();

    enum ControlType {
        BUTTON,
        TOGGLE_BUTTON,
        COMBOBOX,
        COLOR_PICKER,
        SUBMENU,
        TOGGLE_GROUP
    }
}
