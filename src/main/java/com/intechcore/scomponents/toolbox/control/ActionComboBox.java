package com.intechcore.scomponents.toolbox.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows an action to be called even if the element was already selected
 * before.
 */
public class ActionComboBox extends ComboBox<Object> {

    private EventHandler<ActionEvent> action;

    private final List<Object> unsupportedItems;

    public ActionComboBox() {
        unsupportedItems = new ArrayList<>();
    }

    public EventHandler<ActionEvent> getAction() {
        return action;
    }

    public void setAction(EventHandler<ActionEvent> action) {
        this.action = action;
    }

    public void addUnsupportedItem(Object item) {
        unsupportedItems.add(item);
    }

    public boolean isUnsupportedItem(Object item) {
        return unsupportedItems.contains(item);
    }
}
