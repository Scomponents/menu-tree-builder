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

public class RuntimeHandler<TControl extends Control> implements IRuntimeHandler {
    private final TControl target;
    private final EventTracker tracker = new EventTracker();

    public RuntimeHandler(TControl target) {
        this.target = target;
    }

    @Override
    public void setDisable(boolean disable) {
        this.target.setDisable(disable);
        this.tracker.track(EventTracker.Event.DISABLE, disable);
    }

    @Override
    public void startTracking() {
        this.tracker.start(EventTracker.Event.DISABLE);
    }

    @Override
    public int getTrackAndReset(EventTracker.Event event) {
        return this.tracker.reset(event);
    }
}
