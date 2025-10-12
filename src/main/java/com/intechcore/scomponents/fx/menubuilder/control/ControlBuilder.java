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
 * An abstract base class for control builders
 * @param <TControl> the type of the control
 * @param <TActionResult> the type of the action result
 */
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

    /**
     * @return the target control
     */
    protected abstract TControl getTarget();
}
