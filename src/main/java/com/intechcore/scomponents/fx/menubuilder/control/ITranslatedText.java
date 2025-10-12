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

/**
 * Represents a translated text
 */
public interface ITranslatedText {
    /**
     * The default language
     */
    Language DEFAULT_LANGUAGE = Language.ENGLISH;

    /**
     * @param language the language to get the text for
     * @return the text for the given language
     */
    String getText(Language language);

    /**
     * @return the text for the default language
     */
    String getDefaultLangText();

    /**
     * The languages that can be used for translation
     */
    enum Language {
        /**
         * English
         */
        ENGLISH(0);

        private final int index;

        Language(int index) {
            this.index = index;
        }

        /**
         * @return the index of the language
         */
        public int getIndex() {
            return this.index;
        }
    }
}
