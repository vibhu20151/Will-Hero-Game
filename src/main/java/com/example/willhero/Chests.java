package com.example.willhero;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

public class Chests implements Collision, Serializable {
    private int x;
    private int y;
    private transient Image[] image=new Image[3];
    Random random=new Random();

    private int number;

    public int getNumber() {
        return number;
    }

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

    private void setnumber()
    {
        number=getNumber();
    }

    public Chests(int x,int y)
    {
        this.x=x;
        this.y=y;
        number=random.nextInt(2);

        setnumber();

        image[0]=(new Image(new File("src\\main\\resources\\Assests\\Treasure.png").toURI().toString()));

        image[1]=(new Image(new File("src\\main\\resources\\Assests\\Treasure_coin.png").toURI().toString()));

        image[2]=(new Image(new File("src\\main\\resources\\Assests\\Open_chest.png").toURI().toString()));

        imageView=new ImageView(image[number]);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        imageView.setX(x);
        imageView.setY(y);
    }

    @Override
    public void collision() {
        collided=true;

        KeyFrame keyFrame2On = new KeyFrame(Duration.seconds(0.1), new KeyValue(imageView.imageProperty(), image[2]));
        Timeline k = new Timeline(keyFrame2On);
        k.setCycleCount(1);
        k.play();

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
    public boolean collided;

}
