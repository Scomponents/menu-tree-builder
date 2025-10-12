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
import org.girod.javafx.svgimage.SVGLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A builder for creating an icon from an SVG file using the `fxsvgimage` library
 */
public class HevergirodFxsvqimageSvgIconBuilder implements IIconBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(HevergirodFxsvqimageSvgIconBuilder.class);

    @Override
    public Node createIcon(IIconSourceConfig icon) {
        try {
            return SVGLoader.load(icon.getClass().getResource(icon.getData()[0]));
        } catch (Exception e) {
            LOG.error("Unable to load SVG \"{}\"", icon.getData()[0], e);
        }
        return null;
    }
}
