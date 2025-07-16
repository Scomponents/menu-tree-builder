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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FxButtonBuilder extends ControlBuilder<ButtonBase, Object> {
    protected ButtonBase result;
    protected boolean iconIsNull;

    @Override
    public ButtonBase create(Node icon) {
        this.result = new Button();
        this.iconIsNull = icon == null;

        if (!this.iconIsNull) {
            this.result.setGraphic(icon);
        }

        this.result.setMaxHeight(Double.MAX_VALUE);
        this.result.setMaxWidth(Double.MAX_VALUE);

        return this.result;
    }

    @Override
    public void configureForCommand(AbstractCommand<?> command) {
        if (this.iconIsNull) {
            String buttonTitle = command.getCommandInfo().getShortName().getDefaultLangText();
            this.result.setText(buttonTitle);
        }
    }

    @Override
    public Supplier<Object> getCommandParameterValueFactory() {
        return () -> null;
    }

    @Override
    public Consumer<Object> getExternalChangeValueConsumer() {
        return newValue -> {};
    }

    @Override
    public void setOnAction(EventHandler<ActionEvent> value) {
        this.result.setOnAction(value);
    }

    @Override
    public void setDefaultValue(Object o) {
    }

    @Override
    protected ButtonBase getTarget() {
        return this.result;
    }
}
