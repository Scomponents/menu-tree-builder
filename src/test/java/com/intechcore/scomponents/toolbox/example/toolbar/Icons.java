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

package com.intechcore.scomponents.toolbox.example.toolbar;

import com.intechcore.scomponents.fx.menubuilder.control.icon.HevergirodFxsvqimageSvgIconBuilder;
import com.intechcore.scomponents.fx.menubuilder.control.icon.IIcon;
import com.intechcore.scomponents.fx.menubuilder.control.icon.IIconBuilder;
import com.intechcore.scomponents.fx.menubuilder.control.icon.IIconSourceConfig;

/**
 * An enum that represents the icons in the example application
 */
public enum Icons implements IIcon, IIconSourceConfig {
    /**
     * The brick wall icon
     */
    BRICK_WALL("brick-wall"),
    /**
     * The menu icon
     */
    MENU("menu"),
    /**
     * The square menu icon
     */
    SQUARE_MENU("square-menu"),
    /**
     * The paint roller icon
     */
    PAINT_ROLLER("paint-roller"),
    /**
     * The info icon
     */
    INFO("info"),
    /**
     * The warning icon
     */
    WARNING("message-square-warning"),
    /**
     * The alert icon
     */
    ALERT("triangle-alert"),
    /**
     * The megaphone icon
     */
    MEGAPHONE("megaphone"),
    /**
     * The square percent icon
     */
    SQUARE_PERCENT("square-percent");

    private final String fileName;
    private final IIconBuilder iconBuilder = new HevergirodFxsvqimageSvgIconBuilder();

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
