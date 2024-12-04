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

    private final Data data = new Data();

    public MenuItemFactoryBuilder(CompletableFuture<IEventManager> eventManagerFuture) {
        this.eventManagerFuture = eventManagerFuture;
    }

    public <TCommandParam> List<Node> buildMenuItems(
            ICommandFactory<TCommandParam> commandFactory,
            Stream<IToolboxCommandConfig> source) {

        MenuItemFactory<TCommandParam> menuItemFactory = new MenuItemFactory<>(
                commandFactory,
                this.eventManagerFuture,
                this.data);

        return menuItemFactory.createMenuItems(source);
    }

    public MenuItemFactoryBuilder setColorPickerBuilder(Supplier<ColorPickerBuilderAbstract<?>> value) {
        this.data.colorPickerBuilderSupplier = value;
        return this;
    }

    public MenuItemFactoryBuilder setOpenSubmenuOnHover(boolean value) {
        this.data.openSubmenuOnHover = value;
        return this;
    }

    public MenuItemFactoryBuilder setIconScaleFactor(double value) {
        this.data.iconScaleFactor = value;
        return this;
    }

    public MenuItemFactoryBuilder setIconMapper(IIconBuildMapper value) {
        this.data.iconMapper = value;
        return this;
    }

    public MenuItemFactoryBuilder setParentWindow(Window value) {
        this.data.parentWindow = value;
        return this;
    }

    static class Data {
        private Supplier<ColorPickerBuilderAbstract<?>> colorPickerBuilderSupplier;
        private boolean openSubmenuOnHover;
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
