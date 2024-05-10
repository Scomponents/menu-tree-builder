package com.intechcore.scomponents.toolbox.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.text.Font;

public class FontComboboxBuilderDecorator extends ControlBuilderDecoratorAbstract<ComboBox<Object>, Object> {

    public FontComboboxBuilderDecorator(IControlBuilder<ComboBox<Object>, Object> chainBuilder) {
        super(chainBuilder);
    }

    @Override
    public ActionComboBox create(Node icon) {
        ActionComboBox result = (ActionComboBox)super.create(icon);
        this.setFontCellFactory(result);
        return result;
    }

    private void setFontCellFactory(ActionComboBox result) {
        result.setCellFactory(objectListView -> {
            ListCell<Object> cell = new ListCell<Object>() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    String text = (String)item;

                    setFont(new Font(text, getFont().getSize()));
                    setText(text);
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
    }
}
