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

package com.intechcore.scomponents.fx.menubuilder.control;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 * A builder for creating a JavaFX ColorPicker that uses {@link Color} as the color type
 */
public class FxColorPickerBuilder extends ColorPickerBuilderAbstract<Color> {
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
