package com.intechcore.scomponents.toolbox.control.icon;

import javafx.geometry.Insets;
import javafx.scene.Node;

import java.util.Map;

public interface IIconBuildMapper {
    Node createIcon(IIcon icon);
    Insets getSubmenuPadding(IIcon icon);
    Map<IIcon, IIconSourceConfig> getIconMap();
    void setIconMap(Map<IIcon, IIconSourceConfig> iconMap);
}
