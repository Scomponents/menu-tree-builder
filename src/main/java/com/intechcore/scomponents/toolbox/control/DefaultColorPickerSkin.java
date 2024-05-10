package com.intechcore.scomponents.toolbox.control;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.skin.ColorPickerSkin;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class DefaultColorPickerSkin extends ColorPickerSkin {

    private static final Map<Color, String> COLOR_TO_NAME_MAP = new HashMap<>() {
        {
            put(Color.TRANSPARENT, "Transparent");
            put(Color.BLACK, "Black");
        }
    };

    private final Color defaultColor;

    private Button defaultColorButton;

    private final ColorPicker colorPicker;

    public DefaultColorPickerSkin(ColorPicker control, Color defaultColor) {
        super(control);

        this.defaultColor = defaultColor;
        this.defaultColorButton = null;
        this.colorPicker = control;
    }

    @Override
    protected Node getPopupContent() {
        Node content = super.getPopupContent();

        if (defaultColorButton == null) {
            String colorName = COLOR_TO_NAME_MAP.getOrDefault(
                    defaultColor, defaultColor.toString());

            defaultColorButton = new Button("Default Color (" + colorName + ")");
            defaultColorButton.getStyleClass().add("default-color-button");
            defaultColorButton.setOnAction(event -> {
                colorPicker.setValue(defaultColor);
                colorPicker.fireEvent(new ActionEvent());
                colorPicker.hide();
            });

            VBox vbox = (VBox)content.lookup(".color-palette");

            // remove custom color stuff
            vbox.getChildren().remove(2, 6);
            vbox.getChildren().add(defaultColorButton);
        }

        return content;
    }
}