package com.intechcore.scomponents.toolbox.command;

import com.intechcore.scomponents.toolbox.config.IToolboxCommandConfig;
import com.intechcore.scomponents.toolbox.control.icon.IIcon;

import java.util.stream.Stream;

public enum Defaults implements IToolboxCommandConfig {
    SEPARATOR

    ;

    @Override
    public IIcon getIcon() {
        return null;
    }

    @Override
    public ControlType getControlType() {
        return ControlType.EMPTY;
    }

    @Override
    public Stream<IToolboxCommandConfig> getNestedCommands() {
        return null;
    }

    @Override
    public <TCommandGroup extends Enum<TCommandGroup>> ICommandGroup<TCommandGroup> getToggleGroup() {
        return null;
    }
}
