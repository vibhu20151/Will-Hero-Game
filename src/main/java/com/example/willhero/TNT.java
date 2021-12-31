package com.example.willhero;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;

public class TNT extends Game_Objects implements Collision {


    public TNT(int x, int y) {
        super(x, y);
        image = new Image(new File("src\\main\\resources\\Assests\\TNT.png").toURI().toString());
        imageView = new ImageView(image);
        imageView.setX(x);
        imageView.setY(y - 23);
        imageView.setFitWidth(50);
        imageView.setFitHeight(70);
        imageView.setPreserveRatio(false);
        collided = false;
    }

    @Override
    public void if_collides() {
        collision();
    }


    @Override
    public void collision() {
        collided = true;
        ScaleTransition st = new ScaleTransition();
        st.setNode(imageView);
        st.setByX(0.8f);
        st.setByY(0.8f);
        st.setDuration(Duration.millis(1000));
        st.setCycleCount(1);
        st.setAutoReverse(false);
        st.play();

        Image image2;

        image2 = new Image(new File("src\\main\\resources\\Assests\\blast.png").toURI().toString());
        KeyFrame endFadeOut = new KeyFrame(Duration.seconds(0.2), new KeyValue(imageView.opacityProperty(), 0.0));

        Timeline l = new Timeline(endFadeOut);
        l.setDelay(Duration.millis(1000));
        l.setCycleCount(1);
        l.play();

        KeyFrame keyFrame2On = new KeyFrame(Duration.seconds(0.1), new KeyValue(imageView.imageProperty(), image2));
        Timeline k = new Timeline(keyFrame2On);
        k.setDelay(Duration.millis(1200));
        k.setCycleCount(1);
        k.play();

        KeyFrame endFadeIn = new KeyFrame(Duration.seconds(0.4), new KeyValue(imageView.opacityProperty(), 1.0));

        Timeline sss = new Timeline(endFadeIn);
        sss.setDelay(Duration.millis(1500));
        sss.setCycleCount(1);
        sss.play();

        KeyFrame sizeincrease = new KeyFrame(Duration.seconds(0.1), e ->
        {
            imageView.setFitHeight(100);
            imageView.setFitWidth(150);
            imageView.setY(350);
        });

        Timeline pppp = new Timeline(sizeincrease);
        pppp.setDelay(Duration.millis(1300));
        pppp.setCycleCount(1);
        pppp.play();

    }
}
