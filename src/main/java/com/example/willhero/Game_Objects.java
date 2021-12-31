package com.example.willhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.Serializable;

public abstract class Game_Objects implements Serializable {

    private int x;
    private int y;
    protected ImageView imageView;
    protected Image image;

    public ImageView getImageView() {
        return imageView;
    }

    protected boolean collided;

    public int getX() {
        return x;
    }


    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Game_Objects(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void move_forward()
    {
        Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(2), e ->
        {
            imageView.setX(imageView.getX()+1);

        }));
        timeline2.setCycleCount(50);
        timeline2.play();
    }
    private void move_screen_back() {
        Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(50), e ->
        {
            imageView.setX(imageView.getX() - 1);

        }));
        timeline2.setDelay(Duration.millis(200));
        timeline2.setCycleCount(50);
        timeline2.play();
    }
    public void move_back()
    {
        Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(5), e ->
        {
            imageView.setX(imageView.getX()-1);

        }));
        timeline2.setCycleCount(50);
        timeline2.play();
        setX(getX()-50);

    }
    public void move_screen_back1()
    {
        Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(50), e ->
        {
            imageView.setX(imageView.getX()-1);
        }));
        timeline2.setDelay(Duration.millis(200));
        timeline2.setCycleCount(50);
        timeline2.play();
        setX(getX()-50);
    }
    public void window_sliding_forward()
    {
        move_forward();
        move_screen_back();
    }
    public void objects_move_Back()
    {
        move_back();
        move_screen_back1();
    }

    public abstract void if_collides();

}
