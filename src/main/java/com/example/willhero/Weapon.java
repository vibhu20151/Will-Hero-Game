package com.example.willhero;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.util.Random;

public class Weapon {

    Random random;
    private Image[] image=new Image[2];
    private int weapon_type;
    public Weapon(int x,int y)
    {
        random=new Random();
        weapon_type=random.nextInt(2)+1;
        image[0]=(new Image(new File("src\\main\\resources\\Assests\\Hammer.png").toURI().toString()));
        image[1]=(new Image(new File("src\\main\\resources\\Assests\\WeaponSword.png").toURI().toString()));


        imageView=new ImageView(image[weapon_type]);
        imageView.setFitWidth(250);
        imageView.setFitHeight(160);
        imageView.setX(x);
        imageView.setY(y);
    }
    transient ImageView imageView;
}
