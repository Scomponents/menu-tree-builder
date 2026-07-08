/*
 * Copyright (c) 2026-present, Intechcore GmbH
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

import com.intechcore.scomponents.common.core.i18n.II18nKey;

/**
 * A class that represents a text with a short and a full version
 */
public class Text {

    private final II18nKey shortText;
    private final II18nKey fullText;

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
        this.shortText = () -> shortText;
        this.fullText = () -> fullText;
    }

    /**
     * @return the short version of the text
     */
    public II18nKey getShortText() {
        return this.shortText;
    }

    /**
     * @return the full version of the text
     */
    public II18nKey getFullText() {
        return this.fullText;
    }
}
