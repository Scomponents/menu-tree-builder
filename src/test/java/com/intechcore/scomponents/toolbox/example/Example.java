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

package com.intechcore.scomponents.toolbox.example;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.intechcore.scomponents.common.core.event.manager.EventManager;
import com.intechcore.scomponents.common.core.event.manager.IEventManager;
import com.intechcore.scomponents.fx.menubuilder.MenuItemFactoryBuilder;
import com.intechcore.scomponents.fx.menubuilder.control.icon.Util;
import com.intechcore.scomponents.toolbox.example.toolbar.AppState;
import com.intechcore.scomponents.toolbox.example.command.CommandFactory;
import com.intechcore.scomponents.toolbox.example.toolbar.ExampleMenuItem;
import com.intechcore.scomponents.toolbox.example.toolbar.ExampleSubmenuItem;
import com.intechcore.scomponents.toolbox.example.toolbar.Icons;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * An example application that demonstrates how to use the toolbox library
 */
public class Example extends Application {

    /**
     * The main entry point for the application
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        IEventManager eventManager = new EventManager();

        AppState service = new AppState();

        MenuItemFactoryBuilder factoryBuilder = new MenuItemFactoryBuilder(
                CompletableFuture.completedFuture(eventManager))
                .setIconMapper(Util.buildDefaultIconMapper(Icons.values(), Icons.class))
                .setShortLabel(true)
                .setHideSubmenuOnClick(true)
                .setIconScaleFactor(1);

        List<Node> menuButtons = factoryBuilder.buildMenuItems(
                new CommandFactory(service, primaryStage),
                Stream.of(
                        ExampleMenuItem.CONFIRMATION_STATE,
                        ExampleSubmenuItem.ALERT_MENU.add(
                                ExampleMenuItem.ALERT_INFO,
                                ExampleMenuItem.ALERT_WARNING,
                                ExampleSubmenuItem.SQUARE_MENU.add(
                                        ExampleMenuItem.ALERT_ERROR,
                                        ExampleMenuItem.TOGGLE1_STATE_1,
                                        ExampleMenuItem.TOGGLE1_STATE_2,
                                        ExampleMenuItem.TOGGLE1_STATE_3
                                )
                        )
                ));

        ToolBar toolBar = new ToolBar(menuButtons.toArray(new Node[0]));

        ToggleButton toggle1 = new ToggleButton("State 1");
        toggle1.setOnAction(actionEvent -> {
            service.setToolFirstEnabled(!service.isToolOneEnabled());
        });
        HBox appControls = new HBox(2, toggle1);

        Scene scene = new Scene(new VBox(toolBar, appControls), 1500, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
