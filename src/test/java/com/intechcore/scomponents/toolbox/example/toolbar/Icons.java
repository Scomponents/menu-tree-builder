/*
 *  Copyright (C) 2008-2025 Intechcore GmbH - All Rights Reserved
 *
 *  This file is part of SComponents project
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *
 *  Proprietary and confidential
 *
 *  Written by Intechcore GmbH <info@intechcore.com>
 */

package com.intechcore.scomponents.toolbox.example.toolbar;

import com.intechcore.scomponents.toolbox.control.icon.IIcon;
import com.intechcore.scomponents.toolbox.control.icon.IIconBuilder;
import com.intechcore.scomponents.toolbox.control.icon.IIconSourceConfig;

public enum Icons implements IIcon, IIconSourceConfig {
    BRICK_WALL("brick-wall"),
    MENU("menu"),
    SQUARE_MENU("square-menu"),
    PAINT_ROLLER("paint-roller"),
    INFO("info"),
    WARNING("message-square-warning"),
    ALERT("message-square-warning"),
    MEGAPHONE("megaphone"),
    SQUARE_PERCENT("square-percent");

    private final String fileName;
    private final IIconBuilder iconBuilder = null; // new HevergirodFxsvqimageSvgIconBuilder();

    Icons(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String[] getData() {
        return new String[] {"/icons/" + this.fileName + ".svg"};
    }

    @Override
    public double getLeftOffset() {
        return 0;
    }

    @Override
    public IIconBuilder getBuilder() {
        return this.iconBuilder;
    }
}
