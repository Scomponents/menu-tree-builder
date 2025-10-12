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

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A class for creating an SVG icon
 */
public class SvgIcon extends Group {

    private SvgIcon(List<SVGPath> paths) {
        this.getStyleClass().add("icon");
        this.getChildren().addAll(paths);
    }

    /**
     * Loads an SVG icon
     * @param icon the icon source config
     * @return the loaded icon
     */
    public static SvgIcon load(IIconSourceConfig icon) {
        return load(icon, null);
    }

    /**
     * Loads an SVG icon
     * @param icon the icon source config
     * @param styler a consumer for styling the SVG paths
     * @return the loaded icon
     */
    public static SvgIcon load(IIconSourceConfig icon, Consumer<SVGPath> styler) {
        List<SVGPath> paths = produceShapes(icon, styler);
        return new SvgIcon(paths);
    }

    private static List<SVGPath> produceShapes(IIconSourceConfig icon, Consumer<SVGPath> styler) {
        List<SVGPath> result = new ArrayList<>();
        for (int i = 0; i < icon.getData().length; i += 2) {
            SVGPath shape = new SVGPath();
            shape.setContent(icon.getData()[i]);
            shape.setStyle(icon.getData()[i + 1]);
            shape.setFill(Color.color(91 / 255.0, 178 / 255.0, 123 / 255.0));
            if (styler != null) {
                styler.accept(shape);
            }
            result.add(shape);
        }

        return result;
    }
}