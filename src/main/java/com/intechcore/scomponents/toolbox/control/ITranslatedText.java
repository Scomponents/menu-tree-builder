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

package com.intechcore.scomponents.toolbox.control;

public interface ITranslatedText {
    Language DEFAULT_LANGUAGE = Language.ENGLISH;

    String getText(Language language);
    String getDefaultLangText();

    enum Language {
        ENGLISH(0);

        private int index;

        Language(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }
    }
}
