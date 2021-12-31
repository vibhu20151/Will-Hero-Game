package com.example.willhero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Random;

public class Red_Orchs extends Green_Orchs{

    public Red_Orchs(int x, int y) {
        super(x, y);
        image=new Image(new File("src\\main\\resources\\Assests\\red_orchs.png").toURI().toString());
        imageView=new ImageView(image);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);
    }
}
