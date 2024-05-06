package com.intechcore.scomponents.toolbox.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

/**
 * Allows an action to be called even if the element was already selected
 * before.
 */
public class ActionComboBox extends ComboBox<Object> {

    private EventHandler<ActionEvent> action;

    public EventHandler<ActionEvent> getAction() {
        return action;
    }

    public void setAction(EventHandler<ActionEvent> action) {
        this.action = action;
    }
}
