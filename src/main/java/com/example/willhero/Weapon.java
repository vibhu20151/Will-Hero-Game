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
import java.util.Random;

public class Weapon extends Game_Objects implements Serializable{

    private int number;

    public int getNumber() {
        return number;
    }

    private transient Image []image1=new Image[3];
    Random random;

    private transient Timeline t1;
    private transient Timeline t2;
    private transient TranslateTransition translateTransition;
    public Weapon(int x, int y) {
        super(x, y);
        random=new Random();
        number=2;
        image1[0]=new Image(new File("src\\main\\resources\\Assests\\Sword.png").toURI().toString());
        image1[1]=new Image(new File("src\\main\\resources\\Assests\\Hammer.png").toURI().toString());
        image1[2]=null;

        imageView=new ImageView(image1[number]);
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

    private void setnumber(int k) {
        number=k;
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

        pane.setOnMouseClicked(null);

    }

    public void gotweapon(int i) {
        if(i==0){
            setnumber(2);
        }
        else {

            setnumber(random.nextInt(2));
        }
        KeyFrame keyFrame2On = new KeyFrame(Duration.millis(0.1), new KeyValue(imageView.imageProperty(), image1[number]));
        Timeline k = new Timeline(keyFrame2On);
        k.setCycleCount(1);
        k.play();
    }

    public void kill() {
        RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setByAngle(360);
        rotate.setCycleCount(2);
        rotate.setDuration(Duration.millis(300));
        rotate.setNode(imageView);
        rotate.play();
    }

    public void hidde() {
        setnumber(2);
        KeyFrame keyFrame2On = new KeyFrame(Duration.seconds(0.1), new KeyValue(imageView.imageProperty(), image1[number]));
        Timeline k = new Timeline(keyFrame2On);
        k.setCycleCount(1);
        k.play();
    }
}
