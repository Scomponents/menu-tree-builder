/*******************************************************************************
 *  Copyright (C) 2008-2024 Intechcore GmbH - All Rights Reserved
 *
 *  This file is part of SComponents project
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *
 *  Proprietary and confidential
 *
 *  Written by Intechcore GmbH <info@intechcore.com>
 ******************************************************************************/

package com.intechcore.scomponents.toolbox.control;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SvgIcon extends Group {

    private SvgIcon(List<SVGPath> paths) {
        this.getStyleClass().add("icon");
        this.getChildren().addAll(paths);
    }

    public static SvgIcon load(ISvgIconContent icon) {
        return load(icon, null);
    }

    public static SvgIcon load(ISvgIconContent icon, Consumer<SVGPath> styler) {
        List<SVGPath> paths = produceShapes(icon, styler);
        return new SvgIcon(paths);
    }

    private static List<SVGPath> produceShapes(ISvgIconContent icon, Consumer<SVGPath> styler) {
        List<SVGPath> result = new ArrayList<>();
        for (int i = 0; i < icon.getContent().length; i += 2) {
            SVGPath shape = new SVGPath();
            shape.setContent(icon.getContent()[i]);
            shape.setStyle(icon.getContent()[i + 1]);
            shape.setFill(Color.color(91 / 255.0, 178 / 255.0, 123 / 255.0));
            if (styler != null) {
                styler.accept(shape);
            }
            result.add(shape);
        }

        return result;
    }
}
