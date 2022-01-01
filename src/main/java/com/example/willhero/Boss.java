package com.example.willhero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Boss implements Collision{
    private int no_of_collision;
    private transient Image image;
    private int x,y;

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

    public Boss(int x, int y) {
        image=new Image(new File("src\\main\\resources\\Assests\\boss_orchs.png").toURI().toString());
        imageView=new ImageView(image);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitWidth(350);
        imageView.setFitHeight(350);
        imageView.setPreserveRatio(true);
        no_of_collision=0;
    }


    @Override
    public void collision() {

    }
    transient ImageView imageView;

}
