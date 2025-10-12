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
package com.intechcore.scomponents.fx.menubuilder.control.icon;

import javafx.geometry.Insets;
import javafx.scene.Node;

import java.util.Map;

/**
 * An interface for mapping an icon to an icon builder
 */
public interface IIconBuildMapper {
    /**
     * Creates an icon
     * @param icon the icon to create
     * @return the created icon
     */
    Node createIcon(IIcon icon);

    /**
     * @param icon the icon to get the padding for
     * @return the padding for the given icon
     */
    Insets getSubmenuPadding(IIcon icon);

    /**
     * @return the icon map
     */
    Map<IIcon, IIconSourceConfig> getIconMap();

    /**
     * Sets the icon map
     * @param iconMap the icon map to set
     */
    void setIconMap(Map<IIcon, IIconSourceConfig> iconMap);
}
