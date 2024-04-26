package com.intechcore.scomponents.toolbox.control.icon;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class PngIconBuilder implements IIconBuilder {

    private final String resourceParentFolderName;

    public PngIconBuilder() {
        this(null);
    }

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
