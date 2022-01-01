package com.example.willhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Random;

public class Island implements Serializable {

    private int x;
    private int y;
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

    private transient Image[] image=new Image[5];
    private Random random=new Random();
    private int number;

    public int getNumber() {
        return number;
    }

    public Island(int x, int y, int ii) {
        this.x=x;
        this.y=y;
        image[0] = (new Image(new File("src\\main\\resources\\Assests\\Islands1.png").toURI().toString()));

        image[1] = (new Image(new File("src\\main\\resources\\Assests\\Islands2.png").toURI().toString()));

        image[2] = (new Image(new File("src\\main\\resources\\Assests\\Islands3.png").toURI().toString()));

        image[3] = (new Image(new File("src\\main\\resources\\Assests\\Islands4.png").toURI().toString()));

        image[4] = (new Image(new File("src\\main\\resources\\Assests\\box_island.jpg").toURI().toString()));

        if(ii==0) {
            number = random.nextInt(4);
            imageView = new ImageView(image[number]);
            imageView.setFitWidth(250);
            imageView.setFitHeight(160);
            imageView.setX(x);
            imageView.setY(y);
        }
        else
        {
            number=0;
            imageView = new ImageView(image[4]);
            imageView.setFitWidth(100);
            imageView.setFitHeight(25);
            imageView.setX(x);
            imageView.setY(y);
        }
    }
    transient ImageView imageView;

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
    public void move_screen_back()
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
    public void window_sliding()
    {
        move_back();
        move_screen_back();
    }
}
