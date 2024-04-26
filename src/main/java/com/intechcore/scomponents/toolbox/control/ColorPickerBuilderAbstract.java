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

import com.intechcore.scomponents.toolbox.command.AbstractCommand;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class ColorPickerBuilderAbstract<TColor> implements IControlBuilder<ColorPicker, TColor> {
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

    protected abstract Color createFxColor(TColor source);

    protected abstract TColor createColor(int r, int g, int b, int a);

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
}
