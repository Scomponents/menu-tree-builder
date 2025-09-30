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
import com.intechcore.scomponents.toolbox.command.AbstractCommand;
import com.intechcore.scomponents.toolbox.command.ICommandFactory;
import com.intechcore.scomponents.toolbox.command.ICommandGroup;
import com.intechcore.scomponents.toolbox.command.ICommandInfo;
import com.intechcore.scomponents.toolbox.command.ToolbarCommandParameter;
import com.intechcore.scomponents.toolbox.common.Utils;
import com.intechcore.scomponents.toolbox.config.IToolboxCommandConfig;
import com.intechcore.scomponents.toolbox.config.ToggleGroupCommandConfig;
import com.intechcore.scomponents.toolbox.control.ComboBoxBuilder;
import com.intechcore.scomponents.toolbox.control.FxButtonBuilder;
import com.intechcore.scomponents.toolbox.control.FxToggleButtonBuilder;
import com.intechcore.scomponents.toolbox.control.IControlBuilder;
import com.intechcore.scomponents.toolbox.control.icon.IIcon;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Separator;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MenuItemFactory<TCustomParam> {

    private static final Insets SEPARATOR_HORIZONTAL_PADDING = new Insets(0, 0, 0, 3);

    private static final EventHandler<MouseEvent> consumeMouseEventFilterForToggle = (MouseEvent mouseEvent) -> {
        if (((Toggle) mouseEvent.getSource()).isSelected()) {
            mouseEvent.consume();
        }
    };

    private final ICommandFactory<TCustomParam> commandFactory;
    private final CompletableFuture<IEventManager> eventManagerFuture;
    private final ToolbarCommandParameter<TCustomParam> nullParameter;

    private final MenuItemFactoryBuilder.Settings settings;

    private final Map<ICommandGroup<? extends Enum<?>>, ToggleGroupData> radioButtons = new HashMap<>();
    private final Map<Integer, MenuButton> openedSubMenus = new HashMap<>();

    MenuItemFactory(ICommandFactory<TCustomParam> commandFactory,
                    CompletableFuture<IEventManager> eventManagerFuture,
                    MenuItemFactoryBuilder.Settings settings) {

        this.commandFactory = commandFactory;
        this.eventManagerFuture = eventManagerFuture;
        this.settings = settings;

        this.nullParameter = new ToolbarCommandParameter<>(this.commandFactory.createCommandParameter(), null);
    }

    public List<Node> createMenuItems(Stream<IToolboxCommandConfig> source) {
        List<Node> result = new ArrayList<>();

        if (source == null) {
            return result;
        }

        source.forEach(commandConfig -> {
            Node menuItem = this.createItem(commandConfig, new NodeProps());
            if (menuItem instanceof MenuButton) {
                ((MenuButton) menuItem).setPadding(Insets.EMPTY);
            }

            result.add(menuItem);
        });

        this.radioButtons.entrySet().forEach(entry -> {
            final ICommandGroup<? extends Enum<?>> toggleGroupCommandData = entry.getKey();

            this.eventManagerFuture.thenCompose(eventManager ->
                    this.commandFactory.createGroupCommand(toggleGroupCommandData).thenAccept(command -> {
                        if (command == null) {
                            return;
                        }
                        final ToggleGroupData toggleGroupData = entry.getValue();
                        final ToggleGroupCommandConfig<?, ? extends ICommandInfo> groupInfo = command.getGroupCommandInfo();
                        if (groupInfo == null) {
                            return;
                        }
                        if (this.settings.isSetShortLabel()) {
                            toggleGroupData.toggleGroup.getToggles()
                                    .filtered(Control.class::isInstance)
                                    .forEach(node -> Utils.setLabel(
                                            (Control) node,
                                            groupInfo.getShortName((IToolboxCommandConfig) node.getUserData()))
                                    );
                        }

                        boolean[] disableRunCommand = new boolean[] { false };

                        if (command.getGroupCommandInfo().getChangeValueEventClass() != null) {
                            eventManager.subscribe(command.getGroupCommandInfo().getChangeValueEventClass(), event -> {
                                disableRunCommand[0] = true;
                                IToolboxCommandConfig commandConfig = command.getGroupCommandInfo()
                                        .eventToCommandType(event, event.getClass());

                                ToggleButton target = toggleGroupData.getButtonByConfig(commandConfig);
                                if (target != null) {
                                    target.setSelected(true);
                                }
                                disableRunCommand[0] = false;
                            });
                        }

                        toggleGroupData.toggleGroup.getToggles().stream().map(ToggleButton.class::cast).forEach(toggleButton -> {
                            toggleButton.setDisable(false);
                            toggleButton.addEventFilter(MouseEvent.MOUSE_PRESSED, consumeMouseEventFilterForToggle);
                            toggleButton.addEventFilter(MouseEvent.MOUSE_RELEASED, consumeMouseEventFilterForToggle);
                            toggleButton.addEventFilter(MouseEvent.MOUSE_CLICKED, consumeMouseEventFilterForToggle);

                            ToggleGroupCommandConfig<?, ? extends ICommandInfo> commandData = command.getGroupCommandInfo();
                            if (commandData == null) {
                                return;
                            }
                            this.setDisableEvent(
                                    toggleButton,
                                    null,
                                    commandData.getDisableEventClass()
                            );
                            Utils.setTooltip(
                                    toggleButton,
                                    commandData.getFullName((IToolboxCommandConfig) toggleButton.getUserData())
                            );
                        });

                        this.handleToggleGroupCommandAction(command, toggleGroupData, disableRunCommand);
                    }));
        });

        return result;
    }

    private static class NodeProps {
        private final List<MenuButton> parentPopups;

        public NodeProps() {
            this(new ArrayList<>());
        }

        private NodeProps(List<MenuButton> parentPopups) {
            this.parentPopups = parentPopups;
        }

        public void addOwnerSubmenu(MenuButton owner) {
            this.parentPopups.add(owner);
        }

        public boolean ownerPopupIsVertical() {
            return this.parentPopups.size() % 2 != 0;
        }

        public NodeProps createChild() {
            return new NodeProps(new ArrayList<>(this.parentPopups));
        }

        public List<MenuButton> getParentPopups() {
            return new ArrayList<>(this.parentPopups);
        }

        public int getParentsCount() {
            return this.parentPopups.size();
        }
    }

    private static class ToggleGroupData {
        public final ToggleGroup toggleGroup = new ToggleGroup();
        private final Map<ToggleButton, ToggleItemData> controlItemMap = new HashMap<>();
        private final Map<IToolboxCommandConfig, ToggleButton> configMap = new HashMap<>();

        public void addToggleItem(ToggleButton itemButton, ToggleItemData itemData) {
            itemButton.setToggleGroup(this.toggleGroup);
            this.controlItemMap.put(itemButton, itemData);
            this.configMap.put(itemData.commandConfig, itemButton);
        }

        public ToggleItemData getItemData(ToggleButton item) {
            return this.controlItemMap.get(item);
        }

        public ToggleButton getButtonByConfig(IToolboxCommandConfig config) {
            return this.configMap.get(config);
        }

        public static class ToggleItemData {
            public final IControlBuilder<? extends Control, ?> controlBuilder;
            public final NodeProps props;
            public final IToolboxCommandConfig commandConfig;

            public ToggleItemData(IControlBuilder<? extends Control, ?> controlBuilder,
                                  NodeProps props,
                                  IToolboxCommandConfig commandConfig) {
                this.controlBuilder = controlBuilder;
                this.props = props;
                this.commandConfig = commandConfig;
            }
        }
    }

    private Node createItem(IToolboxCommandConfig data, NodeProps props) {
        if (data.getControlType() == IToolboxCommandConfig.ControlType.EMPTY) {
            Separator result = new Separator();
            result.setOrientation(props.ownerPopupIsVertical() ? Orientation.HORIZONTAL : Orientation.VERTICAL);
            result.setPadding(SEPARATOR_HORIZONTAL_PADDING);
            return result;
        }

        if (data.getControlType() == IToolboxCommandConfig.ControlType.SUBMENU) {
            return this.createSubmenu(data, props);
        }

        final IControlBuilder<? extends Control, ?> controlFactory = this.getControlFactory(data.getControlType());
        final Control resultControl = controlFactory.create(this.getIcon(data.getIcon()));
        Utils.setId(resultControl, data, this.settings.getResultItemsIdCounter());
        resultControl.setDisable(true);

//        if (!this.parentsStack.isEmpty() && resultControl instanceof ComboBox) {
//            this.comboBoxBehavior((ComboBox<?>)resultControl);
//        }

        ICommandGroup<?> toggleGroupParent = data.getToggleGroup();
        if (toggleGroupParent != null && data.getControlType() == IToolboxCommandConfig.ControlType.TOGGLE_BUTTON) {
            ToggleGroupData groupData = this.radioButtons
                    .computeIfAbsent(toggleGroupParent, unused -> new ToggleGroupData());
            groupData.addToggleItem(
                    (ToggleButton) resultControl,
                    new ToggleGroupData.ToggleItemData(controlFactory, props, data)
            );
            resultControl.setUserData(data);
        }

        this.setCommand(resultControl, controlFactory, data, props.getParentPopups());

        return resultControl;
    }

    private MenuButton createSubmenuPopup(IToolboxCommandConfig data, NodeProps props) {
        GridPane result = new GridPane();
        result.setHgap(3);
        result.setVgap(3);

        CustomMenuItem subPanel = new CustomMenuItem(result);
        subPanel.setHideOnClick(false);

        Node icon = this.getIcon(data.getIcon());

        MenuButton submenuButton = new MenuButton(icon != null ? "": data.toString(), icon, subPanel);

        props.addOwnerSubmenu(submenuButton);

        int[] rowIndex = new int[] {0};
        boolean vertical = props.ownerPopupIsVertical();
        data.getNestedCommands().forEach(toolbarItem -> {
            Node menuItem = this.createItem(toolbarItem, props.createChild());
            int itemPosition = rowIndex[0]++;
            int column = vertical ? 0 : itemPosition;
            int row = vertical ? itemPosition : 0;
            result.add(menuItem, column, row);
        });

        return submenuButton;
    }

    private Node createSubmenu(IToolboxCommandConfig data, NodeProps props) {

        MenuButton submenuButton = this.createSubmenuPopup(data, props);

        Utils.setId(submenuButton, data, this.settings.getResultItemsIdCounter());
        String submenuCss = this.settings.getSubmenuButtonStyle();
        if (submenuCss != null) {
            submenuButton.setStyle(submenuCss);
        }
        submenuButton.setPopupSide(props.ownerPopupIsVertical() ? Side.BOTTOM : Side.RIGHT);
//        int currentDepth = this.parentsStack.size();
        int currentDepth = props.getParentsCount();
        if (currentDepth > 1) {
            submenuButton.setOnMouseClicked(event -> submenuButton.show());
            if (this.settings.isOpenSubmenuOnHover()) {
                submenuButton.setOnMouseMoved(event -> {
                    EventTarget target = event.getTarget();
                    if (target instanceof MenuButton) {
                        MenuButton targetSubmenu = (MenuButton) target;
                        targetSubmenu.show();
                        MenuButton previousOpened = this.openedSubMenus.get(currentDepth);
                        if (previousOpened != null) {
                            if (!targetSubmenu.equals(previousOpened)) {
                                this.openedSubMenus.remove(currentDepth);
                                previousOpened.hide();
                            }
                        } else {
                            this.openedSubMenus.put(currentDepth, targetSubmenu);
                        }
                    }
                });
//                result.setOnMouseExited(event -> submenuButton.hide());
            }
        } else {
            submenuButton.addEventHandler(MenuButton.ON_SHOWN, event -> {
                this.openedSubMenus.clear();
            });
        }

        submenuButton.setMaxWidth(Double.MAX_VALUE);
        submenuButton.setMaxHeight(Double.MAX_VALUE);

        Insets submenuInsets = this.settings.getIconMapper().getSubmenuPadding(data.getIcon());
        if (submenuInsets != null) {
            submenuButton.setPadding(submenuInsets);
        }

        Utils.setTooltip(submenuButton, this.commandFactory.createTooltip(data));

        return submenuButton;
    }

    private Node getIcon(IIcon icon) {
        Node result = this.settings.getIconMapper().createIcon(icon);
        if (result == null) {
            return null;
        }

        if (this.settings.getIconScaleFactor() != null) {
            result.setScaleX(this.settings.getIconScaleFactor());
            result.setScaleY(this.settings.getIconScaleFactor());
            // return group, so scale is considered for layout
            result = new Group(result);
        }
        return result;
    }

    private void setCommand(final Control resultControl,
                            final IControlBuilder<? extends Control, ?> controlFactory,
                            final IToolboxCommandConfig commandConfig,
                            final List<MenuButton> parentsToClose) {
        this.eventManagerFuture.thenCompose(eventManager ->
                this.commandFactory.create(commandConfig).thenAcceptAsync(command -> {
                    if (command == null) {
                        return;
                    }

                    controlFactory.configureForCommand(command);

                    if (this.settings.isSetShortLabel()) {
                        Utils.setLabel(resultControl, command.getCommandInfo().getShortName());
                    }

                    controlFactory.setOnAction(event -> {
                        this.handleCommandAction(
                                command,
                                controlFactory,
                                controlFactory.getCommandParameterValueFactory(),
                                parentsToClose,
                                disable -> controlFactory.getHandler().setDisable(disable)
                        );
                    });

                    if (command.getCommandInfo().getChangeValueEventClass() != null) {
                        final Consumer<Object> newValueConsumer =
                                (Consumer<Object>) controlFactory.getExternalChangeValueConsumer();
                        eventManager.subscribe(
                                command.getCommandInfo().getChangeValueEventClass(),
                                event -> newValueConsumer.accept(event.getNewValue()));
                    }

                    this.setDisableEvent(null, controlFactory, command.getCommandInfo().getDisableEventClass());
                    Utils.setTooltip(resultControl, command.getCommandInfo().getFullName());

                    if (!command.initiallyDisabled()) {
                        resultControl.setDisable(false);
                    }

                    Object defaultValue = command.getCommandInfo().getDefaultValue();
                    if (defaultValue != null) {
                        ((IControlBuilder<? extends Control, Object>) controlFactory).setDefaultValue(defaultValue);
                    }

                }, Platform::runLater));
    }

    private void handleToggleGroupCommandAction(
            final AbstractCommand<TCustomParam> command,
            final ToggleGroupData toggleGroupData,
            final boolean[] disableRunCommand) {
        toggleGroupData.toggleGroup.selectedToggleProperty().addListener((unusd, previousToggle, newToggle) -> {
            if (disableRunCommand[0]) {
                return;
            }

            ToggleGroupData.ToggleItemData toggleItemData = toggleGroupData.getItemData((ToggleButton) newToggle);

            this.handleCommandAction(
                    command,
                    toggleItemData.controlBuilder,
                    () -> toggleItemData.commandConfig,
                    toggleItemData.props.getParentPopups(),
                    disable -> toggleGroupData.toggleGroup.getToggles().forEach(node -> ((Node) node).setDisable(disable))
            );
        });
    }

    private void handleCommandAction(
            final AbstractCommand<TCustomParam> command,
            final IControlBuilder<? extends Control, ?> controlFactory,
            final Supplier<?> commandValueFactory,
            final List<MenuButton> parentsToClose,
            final Consumer<Boolean> disableConsumer) {
        disableConsumer.accept(true);

        Object parameter = commandValueFactory.get();
        ToolbarCommandParameter<TCustomParam> commandParameter = parameter == null
                ? this.nullParameter
                : new ToolbarCommandParameter<TCustomParam>(
                        this.commandFactory.createCommandParameter(), parameter);

        controlFactory.getHandler().startTracking();

        Consumer<Throwable> finishCommand = throwable -> {
            Utils.finishCommand(
                    throwable,
                    (IControlBuilder<? extends Control, Object>) controlFactory,
                    commandParameter,
                    disableConsumer,
                    this.settings.getParentWindow());

            if (this.settings.isHideSubmenuOnClick() && !parentsToClose.isEmpty()) {
                Platform.runLater(() -> {
                    parentsToClose.forEach(MenuButton::hide);
                    this.openedSubMenus.clear();
                });
            }
        };

        CompletableFuture<Void> commandFuture = null;
        try {
            commandFuture = command.execute(commandParameter);
        } catch (Exception e) {
            Platform.runLater(() -> finishCommand.accept(e));
        }

        if (commandFuture != null) {
            commandFuture.whenCompleteAsync((unused, throwable) -> {
                finishCommand.accept(throwable);
            }, Platform::runLater);
        }
    }

    private IControlBuilder<? extends Control, ?> getControlFactory(IToolboxCommandConfig.ControlType controlType) {
        switch (controlType) {
            case COMBOBOX:
            case FONT_COMBOBOX:
                boolean setCurrentFont = controlType == IToolboxCommandConfig.ControlType.FONT_COMBOBOX;
                return new ComboBoxBuilder(setCurrentFont);
            case COLOR_PICKER:
                return this.settings.getColorPickerBuilderSupplier().get();
            case BUTTON:
                return new FxButtonBuilder();
            case TOGGLE_BUTTON:
                return new FxToggleButtonBuilder();

            default:
                throw new RuntimeException("The following factory have not implemented: " + controlType);
        }
    }

    private void setDisableEvent(Node result,
                                 IControlBuilder<? extends Control, ?> controlFactory,
                                 Class<? extends DisabledStateEvent> disableEventClass) {
        if (disableEventClass == null) {
            return;
        }

        this.eventManagerFuture.thenAccept(eventManager -> eventManager.subscribe(disableEventClass,
                eventData -> {
                    if (result != null) {
                        result.setDisable(eventData.getDisabled());
                    }
                    if (controlFactory != null) {
                        controlFactory.getHandler().setDisable(eventData.getDisabled());
                    }
                }));
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
