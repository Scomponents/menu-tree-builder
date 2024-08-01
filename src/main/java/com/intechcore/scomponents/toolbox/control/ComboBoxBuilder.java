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
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ComboBoxBuilder implements IControlBuilder<ComboBox<Object>, Object> {
    private ComboBox<Object> result;
    private EventHandler<ActionEvent> eventHandler;
    private final List<Object> unsupportedItems = new ArrayList<>();

    private final boolean tryToSetFontToLabelFromItemValue;

    public ComboBoxBuilder() {
        this(false);
    }

    public ComboBoxBuilder(boolean tryToSetFontToLabelFromItemValue) {
        this.tryToSetFontToLabelFromItemValue = tryToSetFontToLabelFromItemValue;
    }

    @Override
    public ComboBox<Object> create(Node icon) {
        this.result = new ComboBox<Object>();
        this.result.setEditable(false);

        this.result.setCellFactory(objectListView -> {
            ListCell<Object> cell = this.createListCell();
            cell.setOnMousePressed(event -> {
                if (this.eventHandler != null) {
                    this.eventHandler.handle(new ActionEvent(event.getSource(), event.getTarget()));
                }
            });

            return cell;
        });

        this.result.setMaxHeight(Double.MAX_VALUE);
        return this.result;
    }

    public ListCell<Object> createListCell() {
        return new ListCell<Object>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);

                this.setText((String)item);
                this.setMinHeight(0);

                double maxHeight = ComboBoxBuilder.this.unsupportedItems.contains(item) ? 0 : Double.MAX_VALUE;
                this.setMaxHeight(maxHeight);

                if (ComboBoxBuilder.this.tryToSetFontToLabelFromItemValue) {
                    try {
                        this.setFont(new Font((String)item, this.getFont().getSize()));
                    } catch (Exception ignored) { }
                }
            }
        };
    }

    @Override
    public void configureForCommand(final AbstractCommand<?> command) {
        this.result.getItems().addAll(command.getDataSource().getItems().collect(Collectors.toList()));
    }

    @Override
    public Supplier<Object> getCommandParameterValueFactory() {
        return () -> this.result.getSelectionModel().getSelectedItem();
    }

    @Override
    public Consumer<Object> getExternalChangeValueConsumer() {
        return newValue -> {
            List<Object> items = this.result.getItems();

            if (!items.contains(newValue)) {
                this.unsupportedItems.add(newValue);
                items.add(newValue);
            }

            this.result.getSelectionModel().select(newValue);
        };
    }

    @Override
    public void setOnAction(EventHandler<ActionEvent> value) {
        this.eventHandler = value;
    }

    @Override
    public void setDefaultValue(Object value) {
        this.result.setValue(value);
    }
}
