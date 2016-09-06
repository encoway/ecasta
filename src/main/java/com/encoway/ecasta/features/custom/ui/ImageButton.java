package com.encoway.ecasta.features.custom.ui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Custom button with an image.
 * 
 * @author azzouz
 */
public class ImageButton extends Button {

    public ImageButton(String image) {
        super();
        setGraphic(new ImageView(new Image(image)));
        getStyleClass().add("image_btn");
    }

}
