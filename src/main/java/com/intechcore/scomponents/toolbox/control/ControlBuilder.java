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

package com.intechcore.scomponents.toolbox.control;

import javafx.scene.control.Control;

public abstract class ControlBuilder<TControl extends Control, TActionResult>
        implements IControlBuilder<TControl, TActionResult> {

    private RuntimeHandler<TControl> runtimeHandler;

    @Override
    public IRuntimeHandler getHandler() {
        if (this.runtimeHandler == null) {
            TControl target = this.getTarget();
            if (target == null) {
                return null;
            }
            this.runtimeHandler = new RuntimeHandler<>(target);
        }
        return this.runtimeHandler;
    }

    protected abstract TControl getTarget();
}
