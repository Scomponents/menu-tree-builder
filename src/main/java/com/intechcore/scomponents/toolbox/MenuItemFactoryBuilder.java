package com.intechcore.scomponents.toolbox;

import com.intechcore.scomponents.common.core.event.manager.IEventManager;
import com.intechcore.scomponents.toolbox.command.ICommandFactory;
import com.intechcore.scomponents.toolbox.config.IToolboxCommandConfig;
import com.intechcore.scomponents.toolbox.control.ColorPickerBuilderAbstract;
import com.intechcore.scomponents.toolbox.control.FxColorPickerBuilder;
import com.intechcore.scomponents.toolbox.control.icon.DefaultIconBuildMapper;
import com.intechcore.scomponents.toolbox.control.icon.IIconBuildMapper;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.stage.Window;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MenuItemFactoryBuilder {

    private final CompletableFuture<IEventManager> eventManagerFuture;

    private final Settings settings = new Settings();

    public MenuItemFactoryBuilder(CompletableFuture<IEventManager> eventManagerFuture) {
        this.eventManagerFuture = eventManagerFuture;
    }

    public <TCommandParam> List<Node> buildMenuItems(
            ICommandFactory<TCommandParam> commandFactory,
            Stream<IToolboxCommandConfig> source) {

        MenuItemFactory<TCommandParam> menuItemFactory = new MenuItemFactory<>(
                commandFactory,
                this.eventManagerFuture,
                this.settings);

        return menuItemFactory.createMenuItems(source);
    }

    public MenuItemFactoryBuilder setColorPickerBuilder(Supplier<ColorPickerBuilderAbstract<?>> value) {
        this.settings.colorPickerBuilderSupplier = value;
        return this;
    }

    public MenuItemFactoryBuilder setOpenSubmenuOnHover(boolean value) {
        this.settings.openSubmenuOnHover = value;
        return this;
    }

    public MenuItemFactoryBuilder setShortLabel(boolean value) {
        this.settings.setShortLabel = value;
        return this;
    }

    public MenuItemFactoryBuilder setItemIdCounter(int value) {
        this.settings.resultItemsIdCounter = value;
        return this;
    }

    public MenuItemFactoryBuilder setSubmenuCssStyle(String value) {
        this.settings.submenuButtonStyle = value;
        return this;
    }

    public MenuItemFactoryBuilder setHideSubmenuOnClick(boolean value) {
        this.settings.hideSubmenuOnClick = value;
        return this;
    }

    public MenuItemFactoryBuilder setIconScaleFactor(double value) {
        this.settings.iconScaleFactor = value;
        return this;
    }

    public MenuItemFactoryBuilder setIconMapper(IIconBuildMapper value) {
        this.settings.iconMapper = value;
        return this;
    }

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
