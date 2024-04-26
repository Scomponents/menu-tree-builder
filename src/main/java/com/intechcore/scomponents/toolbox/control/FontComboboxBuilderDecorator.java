package com.intechcore.scomponents.toolbox.control;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.text.Font;

public class FontComboboxBuilderDecorator extends ControlBuilderDecoratorAbstract<ComboBox<Object>, Object> {

    public FontComboboxBuilderDecorator(IControlBuilder<ComboBox<Object>, Object> chainBuilder) {
        super(chainBuilder);
    }

    @Override
    public ComboBox<Object> create(Node icon) {
        ComboBox<Object> result = super.create(icon);
        this.setFontCellFactory(result);
        return result;
    }

    private void setFontCellFactory(ComboBox<Object> result) {
        result.setCellFactory(objectListView -> new ListCell<Object>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                String text = (String)item;

                setFont(new Font(text, getFont().getSize()));
                setText(text);
            }
        });
    }
}
