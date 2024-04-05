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

import java.util.concurrent.CompletableFuture;

public interface ICommand<TValue> {
    CompletableFuture<Void> execute(ICommandParameter<TValue> data);
    CommandResultsSource<Object> getDataSource();
    boolean initiallyDisabled();
}
