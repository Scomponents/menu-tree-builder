package com.intechcore.scomponents.toolbox.control;

import com.intechcore.scomponents.toolbox.command.AbstractCommand;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Control;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class ControlBuilderDecoratorAbstract<TControl extends Control, TResultValue>
        implements IControlBuilder<TControl, TResultValue> {
    protected final IControlBuilder<TControl, TResultValue> chainBuilder;

    protected ControlBuilderDecoratorAbstract(IControlBuilder<TControl, TResultValue> chainBuilder) {
        this.chainBuilder = chainBuilder;
    }

    @Override
    public TControl create(Node icon) {
        return this.chainBuilder.create(icon);
    }

    @Override
    public void configureForCommand(AbstractCommand<?> command) {
        this.chainBuilder.configureForCommand(command);
    }

    @Override
    public Supplier<TResultValue> getCommandParameterValueFactory() {
        return this.chainBuilder.getCommandParameterValueFactory();
    }

    @Override
    public Consumer<TResultValue> getExternalChangeValueConsumer() {
        return this.chainBuilder.getExternalChangeValueConsumer();
    }

    @Override
    public void setOnAction(EventHandler<ActionEvent> value) {
        this.chainBuilder.setOnAction(value);
    }

    @Override
    public void actionCancelled(TResultValue commandParameter) {
        this.chainBuilder.actionCancelled(commandParameter);
    }

    @Override
    public void setDefaultValue(TResultValue value) {
        this.chainBuilder.setDefaultValue(value);
    }
}
