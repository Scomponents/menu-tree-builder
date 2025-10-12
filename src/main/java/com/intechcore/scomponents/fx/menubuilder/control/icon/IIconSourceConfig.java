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

package com.intechcore.scomponents.fx.menubuilder.control.icon;

/**
 * An interface for the source configuration of an icon
 */
public interface IIconSourceConfig {
    /**
     * @return the data for the icon
     */
    String[] getData();

    /**
     * @return the left offset of the icon
     */
    double getLeftOffset();

    /**
     * @return the builder for the icon
     */
    IIconBuilder getBuilder();
}
