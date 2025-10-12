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
import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * A builder for creating an icon from an SVG file
 */
public class SvgFileIconBuilder implements IIconBuilder {

    /**
     * The default size of the icon
     */
    public static final float ICON_SIZE = 20.0f;

    private final String resourceParentFolderName;

    /**
     * Constructs a new SvgFileIconBuilder
     */
    public SvgFileIconBuilder() {
        this(null);
    }

    /**
     * Constructs a new SvgFileIconBuilder
     * @param resourceParentFolderName the parent folder name for the resource
     */
    public SvgFileIconBuilder(String resourceParentFolderName) {
        this.resourceParentFolderName = resourceParentFolderName == null ? "" : resourceParentFolderName;
    }

    @Override
    public Node createIcon(IIconSourceConfig data) {
        String svgFileName = data.getData()[0];
        BorderPane result = new BorderPane();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        TranscoderInput input = new TranscoderInput(
                this.getInputStream(this.resourceParentFolderName + svgFileName));
        TranscoderOutput output = new TranscoderOutput(bos);
        PNGTranscoder converter = new PNGTranscoder();

        converter.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, ICON_SIZE);
        converter.addTranscodingHint(SVGAbstractTranscoder.KEY_HEIGHT, ICON_SIZE);

        try {
            converter.transcode(input, output);
        } catch (TranscoderException e) {
            return result;
        }

        result.setCenter(new ImageView(new Image(
                new ByteArrayInputStream(bos.toByteArray()))));
        return result;
    }

    private InputStream getInputStream(String path) {
        try {
            URL url = getClass().getClassLoader().getResource(path);
            return url != null ? url.openStream() : null;
        } catch (IOException e) {
            return null;
        }
    }
}
