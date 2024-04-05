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

package com.intechcore.scomponents.toolbox.command;

public class ToolbarCommandParameter<TCustomParam> implements ICommandParameter<TCustomParam> {
    private final TCustomParam customParam;
    private final Object parameter;

    private boolean cancelExecution;

    public ToolbarCommandParameter(TCustomParam customParam, Object parameter) {
        this.customParam = customParam;
        this.parameter = parameter;
    }

    @Override
    public CommandSource getSource() {
        return CommandSource.TOOLBAR;
    }

    @Override
    public TCustomParam getCustom() {
        return this.customParam;
    }

    @Override
    public Object getResult() {
        return this.parameter;
    }

    @Override
    public void cancelExecution() {
        this.cancelExecution = true;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelExecution;
    }
}
