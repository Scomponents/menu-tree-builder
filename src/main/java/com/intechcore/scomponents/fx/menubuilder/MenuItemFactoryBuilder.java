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
package com.intechcore.scomponents.fx.menubuilder;

import com.intechcore.scomponents.common.core.event.manager.IEventManager;
import com.intechcore.scomponents.fx.menubuilder.command.ICommandFactory;
import com.intechcore.scomponents.fx.menubuilder.config.IToolboxCommandConfig;
import com.intechcore.scomponents.fx.menubuilder.control.ColorPickerBuilderAbstract;
import com.intechcore.scomponents.fx.menubuilder.control.FxColorPickerBuilder;
import com.intechcore.scomponents.fx.menubuilder.control.icon.DefaultIconBuildMapper;
import com.intechcore.scomponents.fx.menubuilder.control.icon.IIconBuildMapper;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.stage.Window;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A builder for creating a list of JavaFX Nodes (menu items) from a stream of {@link IToolboxCommandConfig}.
 * This class provides a fluent API for configuring the appearance and behavior of the menu items
 */
public class MenuItemFactoryBuilder {

    private final CompletableFuture<IEventManager> eventManagerFuture;

    private final Settings settings = new Settings();

    /**
     * Constructs a new MenuItemFactoryBuilder
     *
     * @param eventManagerFuture a CompletableFuture that will provide an {@link IEventManager} instance
     */
    public MenuItemFactoryBuilder(CompletableFuture<IEventManager> eventManagerFuture) {
        this.eventManagerFuture = eventManagerFuture;
    }

    /**
     * Builds a list of JavaFX Nodes from a stream of {@link IToolboxCommandConfig}
     *
     * @param commandFactory the factory to use for creating commands
     * @param source         the stream of {@link IToolboxCommandConfig} to build the menu items from
     * @param <TCommandParam> the type of the command parameter
     * @return a list of JavaFX Nodes
     */
    public <TCommandParam> List<Node> buildMenuItems(
            ICommandFactory<TCommandParam> commandFactory,
            Stream<IToolboxCommandConfig> source) {

        MenuItemFactory<TCommandParam> menuItemFactory = new MenuItemFactory<>(
                commandFactory,
                this.eventManagerFuture,
                this.settings);

        return menuItemFactory.createMenuItems(source);
    }

    /**
     * Sets the color picker builder to use for creating color picker controls
     *
     * @param value a supplier for the color picker builder
     * @return this builder instance
     */
    public MenuItemFactoryBuilder setColorPickerBuilder(Supplier<ColorPickerBuilderAbstract<?>> value) {
        this.settings.colorPickerBuilderSupplier = value;
        return this;
    }

    /**
     * Sets whether submenus should open on hover
     *
     * @param value true to open submenus on hover, false otherwise
     * @return this builder instance
     */
    public MenuItemFactoryBuilder setOpenSubmenuOnHover(boolean value) {
        this.settings.openSubmenuOnHover = value;
        return this;
    }

    /**
     * Sets whether to use the short label for the menu items
     *
     * @param value true to use the short label, false otherwise
     * @return this builder instance
     */
    public MenuItemFactoryBuilder setShortLabel(boolean value) {
        this.settings.setShortLabel = value;
        return this;
    }

    /**
     * Sets the initial value for the item ID counter
     *
     * @param value the initial value
     * @return this builder instance
     */
    public MenuItemFactoryBuilder setItemIdCounter(int value) {
        this.settings.resultItemsIdCounter = value;
        return this;
    }

    /**
     * Sets the CSS style for submenus
     *
     * @param value the CSS style
     * @return this builder instance
     */
    public MenuItemFactoryBuilder setSubmenuCssStyle(String value) {
        this.settings.submenuButtonStyle = value;
        return this;
    }

    /**
     * Sets whether submenus should hide on click
     *
     * @param value true to hide submenus on click, false otherwise
     * @return this builder instance
     */
    public MenuItemFactoryBuilder setHideSubmenuOnClick(boolean value) {
        this.settings.hideSubmenuOnClick = value;
        return this;
    }

    /**
     * Sets the scale factor for icons
     *
     * @param value the scale factor
     * @return this builder instance
     */
    public MenuItemFactoryBuilder setIconScaleFactor(double value) {
        this.settings.iconScaleFactor = value;
        return this;
    }

    /**
     * Sets the icon mapper to use for creating icons
     *
     * @param value the icon mapper
     * @return this builder instance
     */
    public MenuItemFactoryBuilder setIconMapper(IIconBuildMapper value) {
        this.settings.iconMapper = value;
        return this;
    }

    /**
     * Sets the parent window for any dialogs that may be opened
     *
     * @param value the parent window
     * @return this builder instance
     */
    public MenuItemFactoryBuilder setParentWindow(Window value) {
        this.settings.parentWindow = value;
        return this;
    }

    static class Settings {
        private Supplier<ColorPickerBuilderAbstract<?>> colorPickerBuilderSupplier;
        private boolean openSubmenuOnHover = false;
        private boolean hideSubmenuOnClick = true;
        private boolean setShortLabel;
        private int resultItemsIdCounter = 0;
        private String submenuButtonStyle = "-fx-accent: transparent; -fx-selection-bar: transparent;";
        private Double iconScaleFactor;
        private IIconBuildMapper iconMapper;
        private Window parentWindow;

        public Supplier<ColorPickerBuilderAbstract<?>> getColorPickerBuilderSupplier() {
            return this.colorPickerBuilderSupplier != null
                    ? this.colorPickerBuilderSupplier
                    : FxColorPickerBuilder::new;
        }

        public boolean isOpenSubmenuOnHover() {
            return this.openSubmenuOnHover;
        }

        public boolean isSetShortLabel() {
            return this.setShortLabel;
        }

        public int getResultItemsIdCounter() {
            return this.resultItemsIdCounter;
        }

        public String getSubmenuButtonStyle() {
            return this.submenuButtonStyle;
        }

        public boolean isHideSubmenuOnClick() {
            return this.hideSubmenuOnClick;
        }

        public Double getIconScaleFactor() {
            return this.iconScaleFactor;
        }

        public IIconBuildMapper getIconMapper() {
            return this.iconMapper != null
                    ? this.iconMapper
                    : new DefaultIconBuildMapper(new HashMap<>(), new Insets(0, 0, 0, 0));
        }

        public Window getParentWindow() {
            return this.parentWindow;
        }
    }
}
