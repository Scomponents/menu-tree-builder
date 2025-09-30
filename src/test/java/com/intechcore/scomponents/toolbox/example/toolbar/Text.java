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

package com.intechcore.scomponents.toolbox.example.toolbar;

import com.intechcore.scomponents.toolbox.control.ITranslatedText;

public class Text {

    private final ITranslatedText shortText;
    private final ITranslatedText fullText;

    public Text(String text) {
        this(text, text);
    }

    public Text(String shortText, String fullText) {
        this.shortText = new TranslatedText(shortText);
        this.fullText = new TranslatedText(fullText);
    }

    public ITranslatedText getShortText() {
        return this.shortText;
    }

    public ITranslatedText getFullText() {
        return this.fullText;
    }

    public static class TranslatedText implements ITranslatedText {
        private final String text;

        public TranslatedText(String text) {
            this.text = text;
        }

        @Override
        public String getText(Language language) {
            return this.text;
        }

        @Override
        public String getDefaultLangText() {
            return this.text;
        }
    }
}
