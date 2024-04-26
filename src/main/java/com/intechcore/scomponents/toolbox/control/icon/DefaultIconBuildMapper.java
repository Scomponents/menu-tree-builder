package com.intechcore.scomponents.toolbox.control.icon;

import javafx.geometry.Insets;
import javafx.scene.Node;

import java.util.Map;

public class DefaultIconBuildMapper implements IIconBuildMapper {

    private Map<IIcon, IIconSourceConfig> iconMap;
    private final Insets submenuInset;

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
