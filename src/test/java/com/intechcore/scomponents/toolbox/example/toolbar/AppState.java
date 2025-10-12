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

/**
 * Represents the state of the application
 */
public class AppState {
    private boolean toolOneEnabled;
    private boolean toolSecondEnabled;
    private boolean toolThirdEnabled;

    /**
     * Constructs a new AppState
     */
    public AppState() {
    }

    /**
     * @return true if tool one is enabled, false otherwise
     */
    public boolean isToolOneEnabled() {
        return this.toolOneEnabled;
    }

    /**
     * Sets whether tool one is enabled
     * @param toolOneEnabled true to enable tool one, false to disable
     */
    public void setToolFirstEnabled(boolean toolOneEnabled) {
        this.toolOneEnabled = toolOneEnabled;
    }

    /**
     * @return true if tool two is enabled, false otherwise
     */
    public boolean isToolSecondEnabled() {
        return this.toolSecondEnabled;
    }

    /**
     * Sets whether tool two is enabled
     * @param toolSecondEnabled true to enable tool two, false to disable
     */
    public void setToolSecondEnabled(boolean toolSecondEnabled) {
        this.toolSecondEnabled = toolSecondEnabled;
    }

    /**
     * @return true if tool three is enabled, false otherwise
     */
    public boolean isToolThirdEnabled() {
        return this.toolThirdEnabled;
    }

    /**
     * Sets whether tool three is enabled.
     * @param toolThirdEnabled true to enable tool three, false to disable
     */
    public void setToolThirdEnabled(boolean toolThirdEnabled) {
        this.toolThirdEnabled = toolThirdEnabled;
    }

    @Override
    public String toString() {
        return "AppState {" +
                " toolOneEnabled=" + this.toolOneEnabled +
                ", toolSecondEnabled=" + this.toolSecondEnabled +
                ", toolThirdEnabled=" + this.toolThirdEnabled +
                " }";
    }
}
