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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ComboBoxBuilder implements IControlBuilder<ComboBox<Object>, Object> {
    protected ActionComboBox result;

    @Override
    public ComboBox<Object> create(Node icon) {
        ActionComboBox comboBox = new ActionComboBox();
        comboBox.setEditable(false);

        comboBox.setCellFactory(objectListView -> {
            ListCell<Object> cell = getListCell();
            cell.setOnMousePressed(event -> {
                EventHandler<ActionEvent> action = this.result.getAction();

                if (action != null) {
                    action.handle(new ActionEvent(event.getSource(), event.getTarget()));
                }
            });

            return cell;
        });

        this.result = comboBox;
        this.result.setMaxHeight(Double.MAX_VALUE);
        return this.result;
    }

    protected ListCell<Object> getListCell() {
        return new ListCell<Object>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                setText((String)item);
            }
        };
    }

    @Override
    public void configureForCommand(final AbstractCommand<?> command) {
        this.result.getItems().addAll(command.getDataSource().getItems().collect(Collectors.toList()));
    }

    public Supplier<Object> getCommandParameterValueFactory() {
        return () -> this.result.getSelectionModel().getSelectedItem();
    }

    @Override
    public Consumer<Object> getExternalChangeValueConsumer() {
        return newValue -> {
            EventHandler<ActionEvent> value = this.result.getAction();
            this.result.setAction(null);

            this.result.getSelectionModel().select(newValue);

            this.result.setAction(value);
        };
    }

    @Override
    public void setOnAction(EventHandler<ActionEvent> value) {
        this.result.setAction(value);
    }

    @Override
    public void setDefaultValue(Object value) {
        this.result.setValue(value);
    }
}
