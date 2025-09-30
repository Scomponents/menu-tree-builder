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
import com.intechcore.scomponents.toolbox.MenuItemFactoryBuilder;
import com.intechcore.scomponents.toolbox.control.icon.Util;
import com.intechcore.scomponents.toolbox.example.toolbar.AppState;
import com.intechcore.scomponents.toolbox.example.toolbar.CommandFactory;
import com.intechcore.scomponents.toolbox.example.toolbar.ExampleMenuItem;
import com.intechcore.scomponents.toolbox.example.toolbar.ExampleSubmenuItem;
import com.intechcore.scomponents.toolbox.example.toolbar.Icons;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class Example extends Application {
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

//        URL url = Example.class.getResource("/icons/paint-roller.svg");
//        LoaderParameters parameters = LoaderParameters.createWidthParameters(200);
//        LoaderParameters parameters = LoaderParameters.createScaleParameters(3);
//        parameters.centerImage = true;
//        parameters.scale = 3;

//        SVGImage ewe = SVGLoader.load(url, parameters);
//        ewe.setStyle("-fx-color: yellow;");
//        VBox.setVgrow(ewe, Priority.ALWAYS);
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
