package com.intechcore.scomponents.toolbox.control;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class DefaultColorPicker extends ColorPicker {

    private Color defaultColor;

    public DefaultColorPicker() {
        defaultColor = Color.BLACK;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new DefaultColorPickerSkin(this, defaultColor);
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }
}
