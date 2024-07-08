package com.intechcore.scomponents.toolbox.control;

import javafx.scene.control.ListCell;

public class ActionComboBoxListCell extends ListCell<Object> {

    private final ActionComboBox comboBox;

    public ActionComboBoxListCell(ActionComboBox comboBox) {
        this.comboBox = comboBox;
    }

    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);
        setText((String)item);

        if (comboBox.isUnsupportedItem(item)) {
            setMinHeight(0);
            setMaxHeight(0);
        } else {
            setMinHeight(0);
            setMaxHeight(Double.MAX_VALUE);
        }
    }
}
