package com.intechcore.scomponents.toolbox.control.icon;

import javafx.geometry.Insets;

import java.util.HashMap;
import java.util.Map;

public final class Util {
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
