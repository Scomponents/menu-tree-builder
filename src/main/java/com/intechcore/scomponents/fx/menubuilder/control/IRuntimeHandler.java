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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, a or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intechcore.scomponents.fx.menubuilder.control;

/**
 * A handler for runtime events
 */
public interface IRuntimeHandler {
    /**
     * Disables or enables the control
     * @param disabled true to disable, false to enable
     */
    void setDisable(boolean disabled);

    /**
     * Starts tracking events
     */
    void startTracking();

    /**
     * Gets the count for the given event and resets it
     * @param event the event to get the count for
     * @return the count for the given event
     */
    int getTrackAndReset(EventTracker.Event event);
}
