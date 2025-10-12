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

import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ToggleButton;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A builder for creating a JavaFX ToggleButton
 */
public class FxToggleButtonBuilder extends FxButtonBuilder {

    @Override
    public ButtonBase create(Node icon) {
        this.result = new ToggleButton();
        this.iconIsNull = icon == null;

        if (!this.iconIsNull) {
            this.result.setGraphic(icon);
        }

        this.result.setMaxHeight(Double.MAX_VALUE);
        this.result.setMaxWidth(Double.MAX_VALUE);

        return this.result;
    }

    @Override
    public Supplier<Object> getCommandParameterValueFactory() {
        return () -> ((ToggleButton)this.result).isSelected();
    }

    @Override
    public Consumer<Object> getExternalChangeValueConsumer() {
        return newValue -> {
            if (!(newValue instanceof Boolean)) {
                return;
            }

            ((ToggleButton)this.result).setSelected((Boolean)newValue);
        };
    }

    @Override
    public void actionCancelled(Object commandParameter) {
        ((ToggleButton)this.result).setSelected(!(Boolean)commandParameter);
    }

    @Override
    public void setDefaultValue(Object value) {
        if (!(value instanceof Boolean)) {
            return;
        }

        ((ToggleButton)this.result).setSelected((Boolean)value);
    }
}
