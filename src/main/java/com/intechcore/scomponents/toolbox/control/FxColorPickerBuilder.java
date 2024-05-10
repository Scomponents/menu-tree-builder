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

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class FxColorPickerBuilder extends DefaultColorPickerBuilderAbstract<Color> {
    @Override
    protected Color createFxColor(Color source) {
        return source;
    }

    @Override
    protected Color createColor(int r, int g, int b, int a) {
        return new Color(r / 255.0, g / 255.0, b / 255.0, a / 255.0);
    }

    @Override
    protected void setDefaultValueToControl(ColorPicker result, Color source) {
        result.setValue(source);
    }
}
