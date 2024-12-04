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

package com.intechcore.scomponents.toolbox;

import com.intechcore.scomponents.common.core.event.events.DisabledStateEvent;
import com.intechcore.scomponents.common.core.event.manager.IEventManager;
import com.intechcore.scomponents.toolbox.command.ICommandFactory;
import com.intechcore.scomponents.toolbox.command.ICommandGroup;
import com.intechcore.scomponents.toolbox.command.ICommandInfo;
import com.intechcore.scomponents.toolbox.command.ToolbarCommandParameter;
import com.intechcore.scomponents.toolbox.config.IToolboxCommandConfig;
import com.intechcore.scomponents.toolbox.config.ToggleGroupCommandConfig;
import com.intechcore.scomponents.toolbox.control.ComboBoxBuilder;
import com.intechcore.scomponents.toolbox.control.FxButtonBuilder;
import com.intechcore.scomponents.toolbox.control.FxToggleButtonBuilder;
import com.intechcore.scomponents.toolbox.control.IControlBuilder;
import com.intechcore.scomponents.toolbox.control.ITranslatedText;
import com.intechcore.scomponents.toolbox.control.icon.IIcon;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MenuItemFactory<TCustomParam> {
    private static final String MENU_BUTTON_ID_COUNTER_VAR_TEMPLATE = "{%_COUNTER_%}";
    private static final String MENU_BUTTON_ID_START_VALUE = "ITC_TOOLBOX" + MENU_BUTTON_ID_COUNTER_VAR_TEMPLATE
            + "_BUTTON_";

    private static final Insets SEPARATOR_HORIZONTAL_PADDING = new Insets(0, 0, 0, 3);

    private static int callCounter = 0;

    private static final EventHandler<MouseEvent> consumeMouseEventFilter = (MouseEvent mouseEvent) -> {
        if (((Toggle) mouseEvent.getSource()).isSelected()) {
            mouseEvent.consume();
        }
    };

    private final ICommandFactory<TCustomParam> commandFactory;
    private final CompletableFuture<IEventManager> eventManagerFuture;
    private final ToolbarCommandParameter<TCustomParam> nullParameter;

    private final MenuItemFactoryBuilder.Data data;

    private final Map<ICommandGroup<? extends Enum<?>>, MenuItemFactory.ToggleGroupData> radioButtons = new HashMap<>();

    private int recursiveCallDepth = 0;


    MenuItemFactory(ICommandFactory<TCustomParam> commandFactory,
                           CompletableFuture<IEventManager> eventManagerFuture,
                           MenuItemFactoryBuilder.Data data) {

        this.commandFactory = commandFactory;
        this.eventManagerFuture = eventManagerFuture;
        this.nullParameter = new ToolbarCommandParameter<TCustomParam>(this.commandFactory.createCommandParameter(), null);
        this.data = data;
    }

    public List<Node> createMenuItems(Stream<IToolboxCommandConfig> source, boolean printShortName) {
        List<Node> result = new ArrayList<>();

        if (source == null) {
            return result;
        }

        source.forEach(commandConfig -> {
            Node menuItem = this.createItem(commandConfig, printShortName);
            if (menuItem instanceof MenuButton) {
                ((MenuButton) menuItem).setPadding(Insets.EMPTY);
            }

            result.add(menuItem);
        });

        this.radioButtons.entrySet().forEach(entry -> {
            ICommandGroup<? extends Enum<?>> toggleGroupCommandData = entry.getKey();
            ToggleGroupData toggleGroupData = entry.getValue();

            this.eventManagerFuture.thenCompose(eventManager ->
                    this.commandFactory.createGroupCommand(toggleGroupCommandData).thenAccept(command -> {
                if (command == null) {
                    return;
                }

                if (printShortName) {
                    toggleGroupData.toggleGroup.getToggles()
                        .filtered(Control.class::isInstance)
                        .forEach(node -> this.setLabel((Control) node,
                            command.getGroupCommandInfo().getShortName((IToolboxCommandConfig) node.getUserData())));
                }

                boolean[] disableRunCommand = new boolean[]{false};

                toggleGroupData.toggleGroup.selectedToggleProperty().addListener((unusd, previousToggle, newToggle) -> {
                    if (disableRunCommand[0]) {
                        return;
                    }

                    toggleGroupData.toggleGroup.getToggles().forEach(node -> ((Node)node).setDisable(true));

                    IToolboxCommandConfig parameter = (IToolboxCommandConfig) newToggle.getUserData();
                    ToolbarCommandParameter<TCustomParam> commandParameter = parameter == null ? this.nullParameter :
                            new ToolbarCommandParameter<TCustomParam>(this.commandFactory.createCommandParameter(), parameter);
                    command.execute(commandParameter).whenComplete((alsoUnused, throwable) -> {
                        toggleGroupData.toggleGroup.getToggles().forEach(node -> ((Node)node).setDisable(false));
                    });
                });
                toggleGroupData.toggleGroup.getToggles().stream().map(ToggleButton.class::cast).forEach(node -> {
                    node.setDisable(false);
                    node.addEventFilter(MouseEvent.MOUSE_PRESSED, consumeMouseEventFilter);
                    node.addEventFilter(MouseEvent.MOUSE_RELEASED, consumeMouseEventFilter);
                    node.addEventFilter(MouseEvent.MOUSE_CLICKED, consumeMouseEventFilter);
                });

                if (command.getGroupCommandInfo().getChangeValueEventClass() != null) {
                    eventManager.subscribe(command.getGroupCommandInfo().getChangeValueEventClass(), event -> {
                        disableRunCommand[0] = true;
                        toggleGroupData.toggleGroup.getToggles().stream()
                                .map(ToggleButton.class::cast)
                                .filter(node -> node.getUserData().equals(
                                        command.getGroupCommandInfo().eventToCommandType(event, event.getClass())))
                                .forEach(node -> node.setSelected(true));
                        disableRunCommand[0] = false;
                    });
                }

                this.setDisableEvent(toggleGroupData.toggleGroup.getToggles().stream().map(ToggleButton.class::cast),
                        command.getGroupCommandInfo().getDisableEventClass());
                setTooltip(toggleGroupData.toggleGroup.getToggles().stream().map(ToggleButton.class::cast),
                        command.getGroupCommandInfo());
            }));
        });

        callCounter++;
        return result;
    }

    public List<Node> createMenuItems(Stream<IToolboxCommandConfig> source) {
        return this.createMenuItems(source, false);
    }

    private Node createItem(IToolboxCommandConfig data, boolean printShortName) {

        if (data.getControlType() == IToolboxCommandConfig.ControlType.EMPTY) {
            Separator result = new Separator();
            result.setOrientation(this.isHorizontalDirection() ? Orientation.HORIZONTAL : Orientation.VERTICAL);
            result.setPadding(SEPARATOR_HORIZONTAL_PADDING);
            return result;
        }

        if (data.getControlType() == IToolboxCommandConfig.ControlType.SUBMENU) {
            return this.createSubmenu(data, printShortName);
        }

        final IControlBuilder<? extends Control, ?> controlFactory = this.getControlFactory(data.getControlType());
        final Control resultControl = controlFactory.create(this.getIcon(data.getIcon()));
        setId(resultControl, data);
        resultControl.setDisable(true);

        if (this.recursiveCallDepth >= 1 && resultControl instanceof ComboBox) {
            this.comboBoxBehavior((ComboBox<?>)resultControl);
        }

        ICommandGroup<?> toggleGroupParent = data.getToggleGroup();

        if (toggleGroupParent != null && data.getControlType() == IToolboxCommandConfig.ControlType.TOGGLE_BUTTON) {
            ToggleGroupData currentGroup = this.getToggleData(toggleGroupParent);
            ((ToggleButton) resultControl).setToggleGroup(currentGroup.toggleGroup);
            currentGroup.addItem(data, (ToggleButton) resultControl);
            resultControl.setUserData(data);
        }

        this.setCommand(resultControl, controlFactory, data, printShortName);

        return resultControl;
    }

    private static void setId(Node target, IToolboxCommandConfig commandConfig) {
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

    private boolean isHorizontalDirection() {
        return this.recursiveCallDepth % 2 != 0;
    }

    private Node createSubmenu(IToolboxCommandConfig data, boolean printShortName) {
        this.recursiveCallDepth++;
        GridPane result = new GridPane();
        result.setHgap(3);
        result.setVgap(3);
        int[] rowIndex = new int[]{0};
        boolean verticalDirection = this.isHorizontalDirection();
        data.getNestedCommands().forEach(toolbarItem -> {
            Node menuItem = this.createItem(toolbarItem, printShortName);
            int itemPosition = rowIndex[0]++;
            result.add(menuItem, verticalDirection ? 0 : itemPosition, verticalDirection ? itemPosition : 0);
        });

        CustomMenuItem subPanel = new CustomMenuItem(result);
        subPanel.setHideOnClick(false);

        Node icon = this.getIcon(data.getIcon());

        MenuButton submenuButton = new MenuButton(icon != null ? "": data.toString(), icon, subPanel);
        setId(submenuButton, data);
        submenuButton.setStyle("-fx-accent: transparent; -fx-selection-bar: transparent;");
        submenuButton.setPopupSide(verticalDirection ? Side.BOTTOM : Side.RIGHT);
        if (this.recursiveCallDepth > 1) {
            submenuButton.setOnMouseClicked(event -> submenuButton.show());
            if (this.data.isOpenSubmenuOnHover()) {
                submenuButton.setOnMouseMoved(event -> submenuButton.show());
                result.setOnMouseExited(event -> submenuButton.hide());
            }
        }

        submenuButton.setMaxWidth(Double.MAX_VALUE);
        submenuButton.setMaxHeight(Double.MAX_VALUE);

        Insets submenuInsets = this.data.getIconMapper().getSubmenuPadding(data.getIcon());
        if (submenuInsets != null) {
            submenuButton.setPadding(submenuInsets);
        }

        setTooltip(submenuButton, this.commandFactory.createTooltip(data));

        this.recursiveCallDepth--;
        return submenuButton;
    }

    private Node getIcon(IIcon icon) {
        Node result = this.data.getIconMapper().createIcon(icon);
        if (result == null) {
            return null;
        }

        if (this.data.getIconScaleFactor() != null) {
            result.setScaleX(this.data.getIconScaleFactor());
            result.setScaleY(this.data.getIconScaleFactor());
            // return group, so scale is considered for layout
            result = new Group(result);
        }
        return result;
    }

    private ToggleGroupData getToggleData(final ICommandGroup<?> toggleGroupParentCommandConfig) {
        return this.radioButtons.computeIfAbsent(toggleGroupParentCommandConfig, unused -> {
            ToggleGroup toggleGroup = new ToggleGroup();
            return new ToggleGroupData(toggleGroup);
        });
    }

    private void setCommand(final Control resultControl, final IControlBuilder<? extends Control, ?> controlFactory,
                            final IToolboxCommandConfig data, boolean printShortNameAnyway) {

        final Supplier<?> commandValueFactory = controlFactory.getCommandParameterValueFactory();
        final Consumer<Object> newValueConsumer = (Consumer<Object>) controlFactory.getExternalChangeValueConsumer();
        this.eventManagerFuture.thenCompose(eventManager -> this.commandFactory.create(data)
                .thenAcceptAsync(command -> {
            if (command == null) {
                return;
            }

            controlFactory.configureForCommand(command);

            if (printShortNameAnyway) {
                this.setLabel(resultControl, command.getCommandInfo().getShortName());
            }

            controlFactory.setOnAction(event -> {
                resultControl.setDisable(true);

                Object parameter = commandValueFactory.get();
                ToolbarCommandParameter<TCustomParam> commandParameter = parameter == null
                        ? this.nullParameter
                        : new ToolbarCommandParameter<TCustomParam>(
                                this.commandFactory.createCommandParameter(), parameter);

                command.execute(commandParameter).whenCompleteAsync((unused, throwable) -> {
                    resultControl.setDisable(false);
                    if (throwable != null && throwable.getCause() != null) {

                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        throwable.printStackTrace(pw);

                        Alert alert = new Alert(Alert.AlertType.ERROR, throwable.getMessage(), ButtonType.CLOSE);
                        if (this.data.getParentWindow() != null) {
                            alert.initOwner(this.data.getParentWindow());
                        }
                        TextArea textArea = new TextArea(sw.toString());
                        textArea.setEditable(false);
                        alert.getDialogPane().setExpandableContent(textArea);
                        alert.setResizable(true);
                        alert.showAndWait();
                    }

                    if (commandParameter.isCancelled()) {
                        ((IControlBuilder<? extends Control, Object>) controlFactory)
                                .actionCancelled(commandParameter.getResult());
                    }
                }, Platform::runLater);
            });

            if (command.getCommandInfo().getChangeValueEventClass() != null) {
                eventManager.subscribe(
                    command.getCommandInfo().getChangeValueEventClass(),
                    event -> newValueConsumer.accept(event.getNewValue()));
            }

            this.setDisableEvent(resultControl, command.getCommandInfo().getDisableEventClass());
            setTooltip(resultControl, command.getCommandInfo().getFullName());

            if (!command.initiallyDisabled()) {
                resultControl.setDisable(false);
            }

            Object defaultValue = command.getCommandInfo().getDefaultValue();
            if (defaultValue != null) {
                 ((IControlBuilder<? extends Control, Object>) controlFactory).setDefaultValue(defaultValue);
            }

        }, Platform::runLater));
    }

    private void setLabel(Control result, ITranslatedText text) {
        if (!(result instanceof Labeled)) {
            return;
        }

        Labeled labeledControl = (Labeled) result;

        String existingLabel = labeledControl.getText();
        if (existingLabel == null || "".equals(existingLabel)) {
            labeledControl.setText(text.getDefaultLangText());
        }
    }

    private IControlBuilder<? extends Control, ?> getControlFactory(IToolboxCommandConfig.ControlType controlType) {
        switch (controlType) {
            case COMBOBOX:
            case FONT_COMBOBOX:
                boolean setCurrentFont = controlType == IToolboxCommandConfig.ControlType.FONT_COMBOBOX;
                return new ComboBoxBuilder(setCurrentFont);
            case COLOR_PICKER:
                return this.data.getColorPickerBuilderSupplier().get();
            case BUTTON:
                return new FxButtonBuilder();
            case TOGGLE_BUTTON:
                return new FxToggleButtonBuilder();

            default:
                throw new RuntimeException("The following factory have not implemented: " + controlType);
        }
    }

    private void setDisableEvent(Stream<Node> result, Class<? extends DisabledStateEvent> disableEventClass) {
        result.forEach(node -> this.setDisableEvent(node, disableEventClass));
    }

    private void setDisableEvent(Node result, Class<? extends DisabledStateEvent> disableEventClass) {
        if (disableEventClass == null) {
            return;
        }

        this.eventManagerFuture.thenAccept(eventManager -> eventManager.subscribe(disableEventClass,
                eventData -> result.setDisable(eventData.getDisabled())));
    }

    private static void setTooltip(Stream<Control> result,
                                   ToggleGroupCommandConfig<?, ? extends ICommandInfo> commandData) {
        if (commandData == null) {
            return;
        }

        result.forEach(node -> setTooltip(node, commandData.getFullName((IToolboxCommandConfig) node.getUserData())));
    }

    private static void setTooltip(Control result, ITranslatedText text) {
        if (text != null && !text.getDefaultLangText().isEmpty()) {
            result.setTooltip(new Tooltip(text.getDefaultLangText()));
        }
    }

    private static class ToggleGroupData {
        public final List<ToggleGroupItem> items = new ArrayList<>();
        public final ToggleGroup toggleGroup;

        public ToggleGroupData(ToggleGroup toggleGroup) {
            this.toggleGroup = toggleGroup;
        }

        public void addItem(IToolboxCommandConfig commandType, ToggleButton control) {
            this.items.add(new ToggleGroupItem(commandType, control));
        }

        private static class ToggleGroupItem {
            public final IToolboxCommandConfig commandType;
            public final ToggleButton control;

            private ToggleGroupItem(IToolboxCommandConfig commandType, ToggleButton control) {
                this.commandType = commandType;
                this.control = control;
            }
        }
    }

    private void comboBoxBehavior(ComboBox<?> resultControl) {
        resultControl.setOnMouseMoved(event -> resultControl.show());
        resultControl.setOnMouseClicked(event -> resultControl.show());

////        Callback<ListView<String>, ListCell<String>> existingFactory = resultControl.getCellFactory();
//        resultControl.setCellFactory((Callback<ListView<String>, ListCell<String>>) param -> new ListCell<String>() {
//            @Override
//            public void updateItem(String item, boolean empty) {
//                super.updateItem(item, empty);
//                if (item != null) {
//                    this.setText(item);
//                    this.setTextFill(Color.BLACK);
//
////                    this.setOnMouseMoved(event -> this.setTextFill(Color.RED)); // TODO: remove this
////                    this.setOnMouseExited(event -> this.setTextFill(Color.BLACK));
//                }
//
////                existingFactory.call(param);
//            }
//        });
    }
}
