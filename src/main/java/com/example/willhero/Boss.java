package com.example.willhero;

import com.example.willhero.Game_Objects;
import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.util.Random;

public class Boss extends Game_Objects {

    private int no_of_collision;
    transient Random random =new Random();
    transient Timeline t1;
    transient Timeline t2;
    transient SequentialTransition s;


    public Boss(int x, int y) {
        super(x, y);
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
    public void if_collides() {

    }


    @Override
    public ImageView getImageView() {
        return imageView;
    }

    public int getNo_of_collision() {
        return no_of_collision;
    }

    public void jump_up()
    {
        t1=new Timeline(new KeyFrame(Duration.millis(15), e->{

            imageView.setY(imageView.getY()-1);
        }));
        t1.setCycleCount(100);
    }
    public void jump_down()
    {
        t2=new Timeline(new KeyFrame(Duration.millis(15),e->{

            imageView.setY(imageView.getY()+1);
        }));
        t2.setCycleCount(100);
    }

    public void jumping()
    {
        jump_up();
        jump_down();
        s=new SequentialTransition(t1,t2);
        s.setCycleCount(Timeline.INDEFINITE);
        s.setDelay(Duration.millis(random.nextInt(4000)));
        s.play();
    }
    public void towards_left()
    {
        Timeline t=new Timeline(new KeyFrame(Duration.millis(30),e->
        {
            imageView.setX(imageView.getX()-1);
        }));
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }
    public void translation_of_boss()
    {
        jumping();
        towards_left();
    }

    public void simple_collision()
    {
        no_of_collision++;
        KeyFrame keyFrame2On = new KeyFrame(Duration.millis(5), e->
        {
            imageView.setX(imageView.getX()+1);
        });
        Timeline k = new Timeline(keyFrame2On);
        k.setCycleCount(40);
        k.play();

    }

    public void death() {
        Timeline ss = new Timeline(new KeyFrame(Duration.millis(10),e->
        {
            imageView.setY(imageView.getY()+1);
        }));
        ss.setCycleCount(200);
        ss.play();
    }
}

