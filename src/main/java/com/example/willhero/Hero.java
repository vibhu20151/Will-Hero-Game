package com.example.willhero;

import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Hero extends Game_Objects implements Serializable{
    private int death;

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    private boolean islife;


    private transient Timeline t1;
    private transient Timeline t2;
    private transient TranslateTransition translateTransition;
    public Hero(int x, int y) {
        super(x, y);
        death=0;
        image=new Image(new File("src\\main\\resources\\Assests\\real_hero.png").toURI().toString());
        islife=true;
        imageView=new ImageView(image);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitHeight(75);
        imageView.setFitWidth(75);
        imageView.setPreserveRatio(true);
        translateTransition=new TranslateTransition();
    }
    @Override
    public void if_collides() {

    }
    public void move_up_hero()
    {
        translateTransition.setDuration(Duration.millis(1000));
        translateTransition.setNode(imageView);
        translateTransition.setToY(-175);
        translateTransition.setCycleCount(Timeline.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }
    public void jump_up()
    {
        t1=new Timeline(new KeyFrame(Duration.millis(5.71), e->{

            setY((getY()-1));
        }));
        t1.setCycleCount(175);
    }
    public void jump_down()
    {
        t2=new Timeline(new KeyFrame(Duration.millis(5.71),e->{

            setY((getY()+1));
        }));
        t2.setCycleCount(175);
    }
    public void set_exact_y() {
        jump_up();
        jump_down();
        SequentialTransition s=new SequentialTransition(t1,t2);
        s.setCycleCount(Timeline.INDEFINITE);
        s.play();
    }
    public void stop_up_transitions()
    {
        translateTransition.stop();
    }
    public void death(AnchorPane pane)
    {
        RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setByAngle(360);
        rotate.setCycleCount(500);
        rotate.setDuration(Duration.millis(300));
        rotate.setAutoReverse(false);
        rotate.setNode(imageView);
        rotate.play();
        translateTransition.setToY(400);
        translateTransition.setDuration(Duration.millis(200));
        translateTransition.setNode(imageView);
        translateTransition.setCycleCount(1);
        translateTransition.play();
        pane.setOnMouseClicked(null);

    }
}
