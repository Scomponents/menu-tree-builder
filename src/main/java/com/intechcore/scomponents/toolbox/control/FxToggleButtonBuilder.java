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

import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ToggleButton;

import java.util.function.Consumer;
import java.util.function.Supplier;

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
