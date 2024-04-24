package com.intechcore.scomponents.toolbox.control;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.text.Font;

public class FontComboboxBuilder extends ComboboxBuilder {

    @Override
    public ComboBox<Object> create(Node icon) {
        super.create(icon);

        this.result.setCellFactory(objectListView -> new ListCell<>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                String text = (String)item;

                setFont(new Font(text, getFont().getSize()));
                setText(text);
            }
        });

        return this.result;
    }
}
