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

package com.intechcore.scomponents.toolbox.example.toolbar;

import com.intechcore.scomponents.fx.menubuilder.control.ITranslatedText;

/**
 * A class that represents a text with a short and a full version
 */
public class Text {

    private final ITranslatedText shortText;
    private final ITranslatedText fullText;

    /**
     * Constructs a new Text
     * @param text the text to use for both the short and the full version
     */
    public Text(String text) {
        this(text, text);
    }

    /**
     * Constructs a new Text
     * @param shortText the short version of the text
     * @param fullText the full version of the text
     */
    public Text(String shortText, String fullText) {
        this.shortText = new TranslatedText(shortText);
        this.fullText = new TranslatedText(fullText);
    }

    /**
     * @return the short version of the text
     */
    public ITranslatedText getShortText() {
        return this.shortText;
    }

    /**
     * @return the full version of the text
     */
    public ITranslatedText getFullText() {
        return this.fullText;
    }

    /**
     * A simple implementation of {@link ITranslatedText}
     */
    public static class TranslatedText implements ITranslatedText {
        private final String text;

        /**
         * Constructs a new TranslatedText
         * @param text the text
         */
        public TranslatedText(String text) {
            this.text = text;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getText(Language language) {
            return this.text;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDefaultLangText() {
            return this.text;
        }
    }
}
