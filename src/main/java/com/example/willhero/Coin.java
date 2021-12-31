package com.example.willhero;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.File;

public class Coin extends Game_Objects implements Collision{
    int opacity;
    RotateTransition rotate=new RotateTransition();

    public Coin(int x, int y) {
        super(x, y);
        image=new Image(new File("src\\main\\resources\\Assests\\coin.png").toURI().toString());
        imageView = new ImageView(image);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitHeight(40);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        rotate();
        opacity=1;
    }
    @Override
    public void collision() {
        collided=true;
        opacity=0;
        if_collides();
    }
    @Override
    public void if_collides() {
        imageView.setOpacity(0);
    }
    public void rotate()
    {
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setByAngle(360);
        rotate.setCycleCount(Timeline.INDEFINITE);
        rotate.setDuration(Duration.millis(1500));
        rotate.setNode(imageView);
        rotate.play();
    }

}
