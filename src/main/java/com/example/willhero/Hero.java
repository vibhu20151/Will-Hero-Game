package com.example.willhero;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;

public class Hero extends Game_Objects{
    private Image image;
    private Timeline t1;
    private Timeline t2;
    private TranslateTransition translateTransition;
    public Hero(int x, int y) {
        super(x, y);
        image=new Image(new File("src\\main\\resources\\Assests\\real_hero.png").toURI().toString());

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
    public void death()
    {
        translateTransition.setToY(200);
        translateTransition.setDuration(Duration.millis(100));
        translateTransition.setNode(imageView);
        translateTransition.setCycleCount(1);
        translateTransition.play();
    }
}
