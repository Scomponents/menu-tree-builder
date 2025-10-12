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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A builder for creating a JavaFX Button
 */
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
