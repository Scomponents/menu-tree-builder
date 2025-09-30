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

import com.intechcore.scomponents.toolbox.config.IToolboxCommandConfig;
import com.intechcore.scomponents.toolbox.control.ITranslatedText;

import java.util.concurrent.CompletableFuture;

public interface ICommandFactory<TCommandParam> {
    CompletableFuture<AbstractCommand<TCommandParam>> create(IToolboxCommandConfig commandType);
    TCommandParam createCommandParameter();
    default CompletableFuture<AbstractCommand<TCommandParam>> createGroupCommand(ICommandGroup<?> config) {
        return null;
    }
    default ITranslatedText createTooltip(IToolboxCommandConfig commandType) {
        return null;
    }
}
