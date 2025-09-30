package com.intechcore.scomponents.toolbox.control.icon;

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

public class SvgFileIconBuilder implements IIconBuilder {

    public static final float ICON_SIZE = 20.0f;

    private final String resourceParentFolderName;

    public SvgFileIconBuilder() {
        this(null);
    }

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
