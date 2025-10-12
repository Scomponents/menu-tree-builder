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

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * A builder for creating an icon from a PNG file
 */
public class PngIconBuilder implements IIconBuilder {

    private final String resourceParentFolderName;

    /**
     * Constructs a new PngIconBuilder
     */
    public PngIconBuilder() {
        this(null);
    }

    /**
     * Constructs a new PngIconBuilder
     * @param resourceParentFolderName the parent folder name for the resource
     */
    public PngIconBuilder(String resourceParentFolderName) {
        this.resourceParentFolderName = resourceParentFolderName == null ? "" : resourceParentFolderName;
    }

    @Override
    public Node createIcon(IIconSourceConfig data) {
        String pngFileName = data.getData()[0];
        BorderPane result = new BorderPane();
        result.setCenter(new ImageView(new Image(this.resourceParentFolderName + pngFileName)));
        return result;
    }
}
