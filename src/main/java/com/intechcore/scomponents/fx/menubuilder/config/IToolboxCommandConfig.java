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

import com.intechcore.scomponents.fx.menubuilder.command.ICommandGroup;
import com.intechcore.scomponents.fx.menubuilder.control.icon.IIcon;

import java.util.stream.Stream;

/**
 * Represents the configuration for a single toolbox item
 * This is a central interface for defining the structure and appearance of the toolbox
 */
public interface IToolboxCommandConfig {
    /**
     * @return the icon for this toolbox item
     */
    IIcon getIcon();

    /**
     * @return the type of control to create for this toolbox item
     */
    ControlType getControlType();

    /**
     * @return a stream of nested commands for this toolbox item. This is used for submenus
     */
    default Stream<IToolboxCommandConfig> getNestedCommands() {
        return Stream.empty();
    }

    /**
     * @return the toggle group for this toolbox item. This is used for toggle buttons that should be part of a group
     * @param <TCommandGroup> the type of the command group
     */
    default <TCommandGroup extends Enum<TCommandGroup>> ICommandGroup<TCommandGroup> getToggleGroup() {
        return null;
    }

    /**
     * The type of control to create for a toolbox item
     */
    enum ControlType {
        /**
         * A standard button
         */
        BUTTON,
        /**
         * A toggle button
         */
        TOGGLE_BUTTON,
        /**
         * A combo box
         */
        COMBOBOX,
        /**
         * A combo box for selecting fonts
         */
        FONT_COMBOBOX,
        /**
         * A color picker
         */
        COLOR_PICKER,
        /**
         * A submenu
         */
        SUBMENU,
        /**
         * A group of toggle buttons
         */
        TOGGLE_GROUP,
        /**
         * An empty space or separator
         */
        EMPTY
    }
}
