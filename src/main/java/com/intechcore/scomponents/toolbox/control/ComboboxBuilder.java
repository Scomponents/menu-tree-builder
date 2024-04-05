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

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ComboboxBuilder implements IControlBuilder<ComboBox<Object>, Object> {
    private ComboBox<Object> result;

    @Override
    public ComboBox<Object> create(Node icon) {
        ComboBox<Object> comboBox = new ComboBox<>();
        comboBox.setEditable(false);

        this.result = comboBox;
        this.result.setMaxHeight(Double.MAX_VALUE);
        return this.result;
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
            EventHandler<ActionEvent> value = this.result.getOnAction();
            this.result.setOnAction(null);

            this.result.getSelectionModel().select(newValue);

            this.result.setOnAction(value);
        };
    }

    @Override
    public void setOnAction(EventHandler<ActionEvent> value) {
        this.result.setOnAction(value);
    }

    @Override
    public void setDefaultValue(Object value) {
        this.result.setValue(value);
    }
}
