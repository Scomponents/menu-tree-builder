package com.intechcore.scomponents.toolbox.control.icon;

import javafx.scene.Node;

public class SvgIconBuilder implements IIconBuilder {
    @Override
    public Node createIcon(IIconSourceConfig data) {
        return SvgIcon.load(data);
    }
}
