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

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class for tracking events
 */
public class EventTracker {
    /**
     * The value indicating that the tracker has not been started
     */
    public static int TRACKER_NOT_STARTED_VAL = -1;
    private final Map<Event, Integer> trackerMap;

    /**
     * Constructs a new EventTracker
     */
    public EventTracker() {
        this.trackerMap = new ConcurrentHashMap<>(Event.values().length);
        Arrays.stream(Event.values()).forEach(event -> {
            this.trackerMap.put(event, TRACKER_NOT_STARTED_VAL);
        });
    }

    /**
     * Starts tracking the given events
     * @param events the events to track
     */
    public void start(Event ... events) {
        Arrays.stream(events).forEach(event -> {
            this.trackerMap.put(event, 0);
        });
    }

    /**
     * Tracks the given event
     * @param event the event to track
     * @param increment true to increment the count, false to decrement
     */
    public void track(Event event, boolean increment) {
        this.trackerMap.compute(event, (key, value) -> {
            if (value == null) {
                return null;
            }
            int step = increment ? 1 : -1;
            return value + step;
        });
    }

    /**
     * @param event the event to get the count for
     * @return the count for the given event
     */
    public int getCount(Event event) {
        return this.trackerMap.get(event);
    }

    /**
     * Resets the count for the given event
     * @param event the event to reset
     * @return the previous count for the event
     */
    public int reset(Event event) {
        return this.trackerMap.put(event, TRACKER_NOT_STARTED_VAL);
    }

    /**
     * The events that can be tracked
     */
    public enum Event {
        /**
         * The disable event
         */
        DISABLE
    }
}
