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

import java.util.List;
import java.util.stream.Stream;

public class CommandResultsSource<TResult> {
    private final List<TResult> items;
    private final TResult item;

    public CommandResultsSource(List<TResult> items, TResult item) {
        this.items = items;
        this.item = item;
    }

    public boolean isCollection() {
        return this.items != null;
    }

    public Stream<TResult> getItems() {
        return this.items.stream();
    }
}
