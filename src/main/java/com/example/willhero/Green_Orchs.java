package com.example.willhero;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.File;
import java.io.Serializable;
import java.util.Random;

public class Green_Orchs extends Game_Objects  implements Collision {
    private transient Random random = new Random();

    private transient Timeline t1;
    private transient Timeline t2;
    private transient SequentialTransition s;


    public Green_Orchs(int x, int y) {
        super(x, y);
        image = new Image(new File("src\\main\\resources\\Assests\\green_orchs.png").toURI().toString());
        imageView = new ImageView(image);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);
        jumping();
    }


    public void jump_up() {
        t1 = new Timeline(new KeyFrame(Duration.millis(5.71), e -> {

            imageView.setY(imageView.getY() - 1);
            setY(getY() - 1);
        }));
        t1.setCycleCount(175);

    }

    public void jump_down() {
        t2 = new Timeline(new KeyFrame(Duration.millis(5.71), e -> {

            imageView.setY(imageView.getY() + 1);
            setY(getY() + 1);

        }));
        t2.setCycleCount(175);
    }

    public void jumping() {
        jump_up();
        jump_down();
        s = new SequentialTransition(t1, t2);
        s.setCycleCount(Timeline.INDEFINITE);
        s.setDelay(Duration.millis(random.nextInt(4000)));
        s.play();
    }


    @Override
    public void if_collides() {
        collision();
    }

    @Override
    public void collision() {

        collided = true;
        s.stop();

        Image image2;

        image2 = new Image(new File("src\\main\\resources\\Assests\\orchs_death.png").toURI().toString());


        KeyFrame keyFrame2On = new KeyFrame(Duration.seconds(0.1), new KeyValue(imageView.imageProperty(), image2));
        Timeline k = new Timeline(keyFrame2On);
        k.setCycleCount(1);
        k.play();

        KeyFrame moveforward = new KeyFrame(Duration.millis(5), e ->
        {
            imageView.setX(imageView.getX() + 1);
        });
        Timeline k1 = new Timeline(moveforward);
        k1.setCycleCount(300);
        k1.play();

        KeyFrame movedown = new KeyFrame(Duration.millis(5), e ->
        {
            imageView.setY(imageView.getY() + 1);
        });
        Timeline k2 = new Timeline(movedown);
        k2.setDelay(Duration.millis(100));
        k2.setCycleCount(300);
        k2.play();

        RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setByAngle(360);
        rotate.setCycleCount(500);
        rotate.setDuration(Duration.millis(300));
        rotate.setAutoReverse(false);
        rotate.setNode(imageView);
        rotate.play();
    }
}
