package com.intechcore.scomponents.toolbox.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.text.Font;

public class FontComboBoxBuilderDecorator extends ControlBuilderDecoratorAbstract<ComboBox<Object>, Object> {

    public FontComboBoxBuilderDecorator(IControlBuilder<ComboBox<Object>, Object> chainBuilder) {
        super(chainBuilder);
    }

    @Override
    public ActionComboBox create(Node icon) {
        ActionComboBox result = (ActionComboBox)super.create(icon);
        result.setCellFactory(objectListView -> {
            ListCell<Object> cell = new ActionComboBoxListCell(result) {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    setFont(new Font((String)item, getFont().getSize()));
                }
            };

            cell.setOnMousePressed(event -> {
                EventHandler<ActionEvent> action = result.getAction();

                if (action != null) {
                    action.handle(new ActionEvent(event.getSource(), event.getTarget()));
                }
            });

            return cell;
        });

        return result;
    }
}
