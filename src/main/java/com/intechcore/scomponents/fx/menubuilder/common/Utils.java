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

package com.intechcore.scomponents.fx.menubuilder.common;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.stage.Window;

import com.intechcore.scomponents.common.core.exceptions.ExceptionUtils;
import com.intechcore.scomponents.fx.menubuilder.command.ICommandParameter;
import com.intechcore.scomponents.fx.menubuilder.config.IToolboxCommandConfig;
import com.intechcore.scomponents.fx.menubuilder.control.EventTracker;
import com.intechcore.scomponents.fx.menubuilder.control.IControlBuilder;
import com.intechcore.scomponents.fx.menubuilder.control.ITranslatedText;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.function.Consumer;

/**
 * A utility class for the toolbox
 */
public final class Utils {

    private static final String MENU_BUTTON_ID_COUNTER_VAR_TEMPLATE = "{%_COUNTER_%}";
    private static final String MENU_BUTTON_ID_START_VALUE = "ITC_TOOLBOX" + MENU_BUTTON_ID_COUNTER_VAR_TEMPLATE
            + "_BUTTON_";

    private Utils() {
    }

    /**
     * Sets the ID for a node
     * @param target the target node
     * @param commandConfig the command config
     * @param callCounter the call counter
     */
    public static void setId(Node target, IToolboxCommandConfig commandConfig, int callCounter) {
        String start = MENU_BUTTON_ID_START_VALUE;
        String counterValue = "";
        if (callCounter > 0) {
            counterValue = String.valueOf(callCounter + 1);
        }
        start = start.replace(MENU_BUTTON_ID_COUNTER_VAR_TEMPLATE, counterValue);

        String end = commandConfig.getControlType() == IToolboxCommandConfig.ControlType.SUBMENU
                ? "_SUBMENU" : "";

        target.setId(start + commandConfig.toString().toUpperCase(Locale.ROOT) + end);
    }

    /**
     * Finishes a command
     * @param throwable the throwable that occurred during command execution
     * @param controlFactory the control factory
     * @param commandParameter the command parameter
     * @param disableConsumer a consumer for disabling the control
     * @param parentWindow the parent window
     * @param <TCustomParam> the type of the custom parameter
     */
    public static <TCustomParam> void finishCommand(
            Throwable throwable,
            IControlBuilder<? extends Control, Object> controlFactory,
            ICommandParameter<TCustomParam> commandParameter,
            final Consumer<Boolean> disableConsumer,
            Window parentWindow) {
        int disabledCallsCount = controlFactory.getHandler().getTrackAndReset(EventTracker.Event.DISABLE);
        boolean disabledWasCalls = disabledCallsCount != EventTracker.TRACKER_NOT_STARTED_VAL
                && disabledCallsCount > 0;
        if (!disabledWasCalls) {
            disableConsumer.accept(false);
        }
        if (throwable != null) {
            throwable = ExceptionUtils.getRootCause(throwable);

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);

            Alert alert = new Alert(Alert.AlertType.ERROR, throwable.getMessage(), ButtonType.CLOSE);
            if (parentWindow != null) {
                alert.initOwner(parentWindow);
            }
            TextArea textArea = new TextArea(sw.toString());
            textArea.setEditable(false);
            alert.getDialogPane().setExpandableContent(textArea);
            alert.setResizable(true);
            alert.showAndWait();
        }

        if (commandParameter.isCancelled()) {
            controlFactory.actionCancelled(commandParameter.getResult());
        }
    }

    /**
     * Sets the label for a control
     * @param result the control
     * @param text the text to set
     */
    public static void setLabel(Control result, ITranslatedText text) {
        if (!(result instanceof Labeled)) {
            return;
        }

        Labeled labeledControl = (Labeled) result;

        String existingLabel = labeledControl.getText();
        if (existingLabel == null || "".equals(existingLabel)) {
            labeledControl.setText(text.getDefaultLangText());
        }
    }

    /**
     * Sets the tooltip for a control
     * @param result the control
     * @param text the text to set
     */
    public static void setTooltip(Control result, ITranslatedText text) {
        if (text != null && !text.getDefaultLangText().isEmpty()) {
            result.setTooltip(new Tooltip(text.getDefaultLangText()));
        }
    }
}
