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

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventTracker {
    public static int TRACKER_NOT_STARTED_VAL = -1;
    private final Map<Event, Integer> trackerMap;

    public EventTracker() {
        this.trackerMap = new ConcurrentHashMap<>(Event.values().length);
        Arrays.stream(Event.values()).forEach(event -> {
            this.trackerMap.put(event, TRACKER_NOT_STARTED_VAL);
        });
    }

    public void start(Event ... events) {
        Arrays.stream(events).forEach(event -> {
            this.trackerMap.put(event, 0);
        });
    }

    public void track(Event event, boolean increment) {
        this.trackerMap.compute(event, (key, value) -> {
            if (value == null) {
                return null;
            }
            int step = increment ? 1 : -1;
            return value + step;
        });
    }

    public int getCount(Event event) {
        return this.trackerMap.get(event);
    }

    public int reset(Event event) {
        return this.trackerMap.put(event, TRACKER_NOT_STARTED_VAL);
    }

    public enum Event {
        DISABLE
    }
}
