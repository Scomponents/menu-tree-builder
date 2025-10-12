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

package com.intechcore.scomponents.fx.menubuilder.command;

import java.util.List;
import java.util.stream.Stream;

/**
 * A source for command results
 * @param <TResult> the type of the result
 */
public class CommandResultsSource<TResult> {
    private final List<TResult> items;
    private final TResult item;

    /**
     * Constructs a new CommandResultsSource
     * @param items the items in the source
     * @param item the single item in the source
     */
    public CommandResultsSource(List<TResult> items, TResult item) {
        this.items = items;
        this.item = item;
    }

    /**
     * @return true if the source is a collection, false otherwise
     */
    public boolean isCollection() {
        return this.items != null;
    }

    /**
     * @return a stream of the items in the source
     */
    public Stream<TResult> getItems() {
        return this.items.stream();
    }
}
