package com.example.willhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.File;
import java.util.Random;

public class Extra_Backgroud1 {
    private int x;
    private int y;
    private Image[] image=new Image[5];
    transient ImageView imageView;

    public Extra_Backgroud1(int x, int y) {
        this.x = x;
        this.y = y;
        image[0] = (new Image(new File("src\\main\\resources\\Assests\\Islands1.png").toURI().toString()));

        image[1] = (new Image(new File("src\\main\\resources\\Assests\\Islands2.png").toURI().toString()));

        image[2] = (new Image(new File("src\\main\\resources\\Assests\\Island3.png").toURI().toString()));

        image[3] = (new Image(new File("src\\main\\resources\\Assests\\extra_clouds.jpg").toURI().toString()));

        image[4] = (new Image(new File("src\\main\\resources\\Assests\\extra_clouds2.jpg").toURI().toString()));


        Random random = new Random();
        int number = random.nextInt(5);
        imageView = new ImageView(image[number]);

        if (number <= 2) {
            imageView.setX(x);
            imageView.setY(y);
            imageView.setFitWidth(random.nextInt(200)+100);
            imageView.setFitHeight(random.nextInt(100)+70);
            imageView.setPreserveRatio(true);
            imageView.setOpacity(0.9);
        }
        else
        {
            imageView.setX(x);
            imageView.setY(y);
            imageView.setFitWidth(random.nextInt(500)+200);
            imageView.setFitHeight(random.nextInt(200)+50);
            imageView.setPreserveRatio(true);
            imageView.setOpacity(0.7);
        }
    }

    public ImageView getImageView() {
        return imageView;
    }
    public void display(AnchorPane gamePane) {
        gamePane.getChildren().add(imageView);
    }

    public void move_back()
    {
        Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(5), e ->
        {
            imageView.setX(imageView.getX()-1);

        }));
        timeline2.setCycleCount(50);
        timeline2.play();
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
    }
    public void cloud_movements()
    {
        TranslateTransition translateTransition=new TranslateTransition();
        translateTransition.setDuration(Duration.millis(4000));
        translateTransition.setNode(imageView);
        translateTransition.setToX(-175);
        translateTransition.setCycleCount(Timeline.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }

}
