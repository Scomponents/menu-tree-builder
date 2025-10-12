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

import com.intechcore.scomponents.fx.menubuilder.command.AbstractCommand;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * An abstract builder for creating a JavaFX ColorPicker
 * @param <TColor> the type of the color
 */
public abstract class ColorPickerBuilderAbstract<TColor> extends ControlBuilder<ColorPicker, TColor> {
    private ColorPicker result;

    @Override
    public ColorPicker create(Node icon) {
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.getStyleClass().add(ColorPicker.STYLE_CLASS_BUTTON);
        colorPicker.setDisable(true);

        this.result = colorPicker;
        this.result.setMaxWidth(Double.MAX_VALUE);
        return this.result;
    }

    @Override
    public void configureForCommand(AbstractCommand<?> command) {
        this.result.setPromptText(command.getCommandInfo().getShortName().getDefaultLangText());

        Skin<?> customSkin = this.createCustomSkin(this.result, command);
        if (customSkin != null) {
            this.result.setSkin(customSkin);
        }
    }

    @Override
    public Supplier<TColor> getCommandParameterValueFactory() {
        return () -> {
            Color result = this.result.getValue();
            return this.createColor(
                    (int)(result.getRed() * 255),
                    (int)(result.getGreen() * 255),
                    (int)(result.getBlue() * 255),
                    (int)(result.getOpacity() * 255));
        };
    }

    @Override
    public Consumer<TColor> getExternalChangeValueConsumer() {
        return color -> {
            if (color == null) {
                color = createColor(0,0,0,0);
            }

            this.result.setValue(this.createFxColor(color));
        };
    }

    /**
     * Creates a JavaFX Color from the given color
     * @param source the color to create the JavaFX Color from
     * @return the created JavaFX Color
     */
    protected abstract Color createFxColor(TColor source);

    /**
     * Creates a color from the given RGB and alpha values
     * @param r the red value
     * @param g the green value
     * @param b the blue value
     * @param a the alpha value
     * @return the created color
     */
    protected abstract TColor createColor(int r, int g, int b, int a);

    /**
     * Sets the default value to the given ColorPicker
     * @param result the ColorPicker to set the default value to
     * @param source the default value
     */
    protected abstract void setDefaultValueToControl(ColorPicker result, TColor source);

    @Override
    public void setOnAction(EventHandler<ActionEvent> value) {
        this.result.setOnAction(event -> {
            value.handle(event);
        });
    }

    @Override
    public void setDefaultValue(TColor color) {
        if (color == null) {
            return;
        }

        this.setDefaultValueToControl(this.result, color);
    }

    @Override
    protected ColorPicker getTarget() {
        return this.result;
    }
}
