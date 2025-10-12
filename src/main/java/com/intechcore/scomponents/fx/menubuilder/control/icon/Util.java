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

import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for icons
 */
public final class Util {
    /**
     * Builds a default icon mapper
     * @param enumValues the enum values
     * @param enumType the enum type
     * @param <TIconEnum> the type of the icon enum
     * @param <TIconConfigEnum> the type of the icon config enum
     * @return the created icon mapper
     */
    public static <TIconEnum extends Enum<TIconEnum>, TIconConfigEnum extends Enum<TIconConfigEnum>>
    IIconBuildMapper buildDefaultIconMapper(TIconEnum[] enumValues, Class<TIconConfigEnum> enumType) {

        Map<IIcon, IIconSourceConfig> iconMap = new HashMap<>();

        if (enumValues == null || enumValues.length == 0) {
            return new DefaultIconBuildMapper(iconMap, new Insets(0, 0, 0, 0));
        }

        for (TIconEnum icon : enumValues) {
            if (!(icon instanceof IIcon)) {
                throw new RuntimeException("icon must be " + IIcon.class.getSimpleName());
            }

            IIconSourceConfig iconConfig = (IIconSourceConfig)Enum.valueOf(enumType, icon.name());
            iconMap.put((IIcon)icon, iconConfig);
        }

        double inset = iconMap.entrySet().iterator().next().getValue().getLeftOffset();

        return new DefaultIconBuildMapper(iconMap, new Insets(0, 0, 0, inset));
    }

    private Util() {}
}
