# Intechcore Menu-Tree-Builder

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A JavaFX library for creating dynamic and configurable toolbars and menu items using a command pattern.

## Features

-   **Declarative UI:** Define toolbar and menu items using enums and builders.
-   **Command Pattern:** Decouple UI components from their actions using a command pattern.
-   **SVG Support:** Use SVG icons for your UI components[^1].
-   **State Management:** Manage the state of your application and reflect it in the UI.
-   **Extensible:** Easily extend the library with your own commands and UI components.
-   **Cross-Java:** Compiled for Java 8 compatible with 11+.

[^1]: The [com.github.hervegirod.fxsvgimage](https://github.com/hervegirod/fxsvgimage) is using. This library for now (version 1.4) cannot draw SVG file with missing L or l command after move command, so you can fix the SVG from scratch to success drawing

## Installation

This project uses Maven for dependency management. To include it in your project, add the following to your `pom.xml`:

```xml
<dependency>
    <groupId>com.intechcore.scomponents</groupId>
    <artifactId>menu-tree-builder</artifactId>
    <version>1.5.0</version>
</dependency>
```

## Usage

The following is a basic example of how to use the library to create a toolbar with a few buttons and a submenu
(see full example in the test part).

### 1. Define Icons

Create an enum that implements `IIcon` and `IIconSourceConfig` to define the icons for your toolbar items.

```java
public enum Icons implements IIcon, IIconSourceConfig {
    BRICK_WALL("brick-wall"),
    MENU("menu"),
    // ... other icons

    private final String fileName;
    private final IIconBuilder iconBuilder = new HevergirodFxsvqimageSvgIconBuilder();

    Icons(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String[] getData() {
        return new String[] {"/icons/" + this.fileName + ".svg"};
    }

    @Override
    public double getLeftOffset() {
        return 0;
    }

    @Override
    public IIconBuilder getBuilder() {
        return this.iconBuilder;
    }
}
```

### 2. Define Menu Items

Create enums that implement `IToolboxCommandConfig` to define the menu items and submenus.

```java
public enum ExampleMenuItem implements IToolboxCommandConfig {
    CONFIRMATION_STATE(Icons.BRICK_WALL, ControlType.BUTTON),
    ALERT_INFO(Icons.INFO, ControlType.BUTTON),
    // ... other menu items
}

public enum ExampleSubmenuItem implements IToolboxCommandConfig {
    ALERT_MENU(Icons.MENU, ControlType.SUBMENU),
    // ... other submenus
}
```

### 3. Define Commands

Create classes that extend `AbstractCommand` to define the actions for your menu items.

```java
public class AlertStateCommand extends AbstractCommand<AppState> {
    // ... implementation
}

public class ChangeStateCommand extends AbstractCommand<AppState> {
    // ... implementation
}
```

### 4. Create a Command Factory

Create a class that implements `ICommandFactory` to create and store your commands.

```java
public class CommandFactory implements ICommandFactory<AppState> {
    // ... implementation
}
```

### 5. Build the Toolbar

Use the `MenuItemFactoryBuilder` to build the toolbar from your defined menu items and command factory.

```java
public class Example extends Application {

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

        Scene scene = new Scene(new VBox(toolBar), 1500, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
```

## Dependencies

-   [common-core](https://github.com/Scomponents/java-common-core)
-   [SLF4J](https://www.slf4j.org/)
-   [Apache Batik](https://xmlgraphics.apache.org/batik/)
-   [fxsvgimage](https://github.com/hervegirod/fxsvgimage)
-   [JUnit 5](https://junit.org/junit5/) (for testing)
-   [OpenJFX](https://openjfx.io/)

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.
