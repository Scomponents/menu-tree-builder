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

package com.intechcore.scomponents.fx.menubuilder.control;

import javafx.scene.control.Control;

/**
 * A runtime handler for a control
 * @param <TControl> the type of the control
 */
public class RuntimeHandler<TControl extends Control> implements IRuntimeHandler {
    private final TControl target;
    private final EventTracker tracker = new EventTracker();

    /**
     * Constructs a new RuntimeHandler
     * @param target the target control
     */
    public RuntimeHandler(TControl target) {
        this.target = target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDisable(boolean disable) {
        this.target.setDisable(disable);
        this.tracker.track(EventTracker.Event.DISABLE, disable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startTracking() {
        this.tracker.start(EventTracker.Event.DISABLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTrackAndReset(EventTracker.Event event) {
        return this.tracker.reset(event);
    }
}
