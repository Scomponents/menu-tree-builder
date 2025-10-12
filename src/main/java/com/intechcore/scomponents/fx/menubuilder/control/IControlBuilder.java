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
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * An interface for building a JavaFX control
 * @param <TControl> the type of the control to build
 * @param <TActionResult> the type of the result of the control's action
 */
public interface IControlBuilder<TControl extends Control, TActionResult> {

    /**
     * Creates the control
     * @param icon the icon to use for the control
     * @return the created control
     */
    TControl create(Node icon);

    /**
     * Configures the control for the given command
     * @param command the command to configure the control for
     */
    void configureForCommand(AbstractCommand<?> command);

    /**
     * @return a supplier for the value of the command parameter
     */
    Supplier<TActionResult> getCommandParameterValueFactory();

    /**
     * @return a consumer for the external change value
     */
    Consumer<TActionResult> getExternalChangeValueConsumer();

    /**
     * Sets the action to perform when the control is activated
     * @param value the action to perform
     */
    void setOnAction(EventHandler<ActionEvent> value);

    /**
     * Called when the action of the control is cancelled
     * @param commandParameter the command parameter
     */
    default void actionCancelled(TActionResult commandParameter) { }

    /**
     * Sets the default value for the control
     * @param value the default value
     */
    void setDefaultValue(TActionResult value);

    /**
     * Creates a custom skin for the control
     * @param control the control to create the skin for
     * @param command the command to create the skin for
     * @return the created skin
     */
    default Skin<?> createCustomSkin(TControl control, AbstractCommand<?> command) {
        return null;
    }

    /**
     * @return the runtime handler for the control
     */
    IRuntimeHandler getHandler();
}
