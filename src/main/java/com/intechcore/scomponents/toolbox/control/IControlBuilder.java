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
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface IControlBuilder<TControl extends Control, TActionResult> {

    TControl create(Node icon);

    void configureForCommand(AbstractCommand<?> command);

    Supplier<TActionResult> getCommandParameterValueFactory();

    Consumer<TActionResult> getExternalChangeValueConsumer();

    void setOnAction(EventHandler<ActionEvent> value);

    default void actionCancelled(TActionResult commandParameter) { }

    void setDefaultValue(TActionResult value);

    default Skin<?> createCustomSkin(TControl control, AbstractCommand<?> command) {
        return null;
    }

    IRuntimeHandler getHandler();
}
