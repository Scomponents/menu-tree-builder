/*
 *  Copyright (C) 2008-2025 Intechcore GmbH - All Rights Reserved
 *
 *  This file is part of SComponents project
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *
 *  Proprietary and confidential
 *
 *  Written by Intechcore GmbH <info@intechcore.com>
 */

package com.intechcore.scomponents.toolbox.common;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.stage.Window;

import com.intechcore.scomponents.common.core.exceptions.ExceptionUtils;
import com.intechcore.scomponents.toolbox.command.ICommandInfo;
import com.intechcore.scomponents.toolbox.command.ICommandParameter;
import com.intechcore.scomponents.toolbox.config.IToolboxCommandConfig;
import com.intechcore.scomponents.toolbox.config.ToggleGroupCommandConfig;
import com.intechcore.scomponents.toolbox.control.EventTracker;
import com.intechcore.scomponents.toolbox.control.IControlBuilder;
import com.intechcore.scomponents.toolbox.control.ITranslatedText;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.stream.Stream;

public final class Utils {

    private static final String MENU_BUTTON_ID_COUNTER_VAR_TEMPLATE = "{%_COUNTER_%}";
    private static final String MENU_BUTTON_ID_START_VALUE = "ITC_TOOLBOX" + MENU_BUTTON_ID_COUNTER_VAR_TEMPLATE
            + "_BUTTON_";

    private Utils() {
    }

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

    public static <TCustomParam> void finishCommand(
            Throwable throwable,
            IControlBuilder<? extends Control, Object> controlFactory,
            ICommandParameter<TCustomParam> commandParameter,
            Window parentWindow) {
        int disabledCallsCount = controlFactory.getHandler().getTrackAndReset(EventTracker.Event.DISABLE);
        boolean disabledWasCalls = disabledCallsCount != EventTracker.TRACKER_NOT_STARTED_VAL
                && disabledCallsCount > 0;
        if (!disabledWasCalls) {
            controlFactory.getHandler().setDisable(false);
        }
        if (throwable != null) {
            throwable = ExceptionUtils.getFirstCause(throwable);

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

    public static void setTooltip(Stream<Control> result,
                                  ToggleGroupCommandConfig<?, ? extends ICommandInfo> commandData) {
        if (commandData == null) {
            return;
        }

        result.forEach(node -> setTooltip(node, commandData.getFullName((IToolboxCommandConfig) node.getUserData())));
    }

    public static void setTooltip(Control result, ITranslatedText text) {
        if (text != null && !text.getDefaultLangText().isEmpty()) {
            result.setTooltip(new Tooltip(text.getDefaultLangText()));
        }
    }
}
