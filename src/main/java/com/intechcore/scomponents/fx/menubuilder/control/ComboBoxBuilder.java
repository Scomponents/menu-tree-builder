/*
 * Copyright 2008-2025 Intechcore GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intechcore.scomponents.fx.menubuilder.control;

import com.intechcore.scomponents.fx.menubuilder.command.AbstractCommand;
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

/**
 * A builder for creating a JavaFX ComboBox
 */
public class ComboBoxBuilder extends ControlBuilder<ComboBox<Object>, Object> {
    private ComboBox<Object> result;
    private EventHandler<ActionEvent> eventHandler;
    private final List<Object> unsupportedItems = new ArrayList<>();

    private final boolean tryToSetFontToLabelFromItemValue;

    /**
     * Constructs a new ComboBoxBuilder
     */
    public ComboBoxBuilder() {
        this(false);
    }

    /**
     * Constructs a new ComboBoxBuilder
     * @param tryToSetFontToLabelFromItemValue true to try to set the font of the label from the item value, false otherwise
     */
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

    /**
     * Creates a new ListCell for the ComboBox
     * @return the new ListCell
     */
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

    @Override
    protected ComboBox<Object> getTarget() {
        return this.result;
    }
}
