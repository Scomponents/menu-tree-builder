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
 * A default implementation of {@link IIconBuildMapper}
 */
public class DefaultIconBuildMapper implements IIconBuildMapper {

    private Map<IIcon, IIconSourceConfig> iconMap;
    private final Insets submenuInset;

    /**
     * Constructs a new DefaultIconBuildMapper
     * @param iconMap a map from icon to icon source config
     * @param submenuInset the inset for submenus
     */
    public DefaultIconBuildMapper(Map<IIcon, IIconSourceConfig> iconMap, Insets submenuInset) {
        this.iconMap = iconMap;
        this.submenuInset = submenuInset;
    }

    @Override
    public Node createIcon(IIcon icon) {
        if (!this.iconMap.containsKey(icon)) {
            return null;
        }

        IIconSourceConfig iconConfig = this.iconMap.get(icon);

        IIconBuilder builder = iconConfig.getBuilder();

        if (builder == null) {
            return null;
        }

        return builder.createIcon(this.iconMap.get(icon));
    }

    @Override
    public Insets getSubmenuPadding(IIcon icon) {
        return this.submenuInset;
    }

    @Override
    public void setIconMap(Map<IIcon, IIconSourceConfig> iconMap) {
        this.iconMap = iconMap;
    }

    @Override
    public Map<IIcon, IIconSourceConfig> getIconMap() {
        return this.iconMap;
    }
}
