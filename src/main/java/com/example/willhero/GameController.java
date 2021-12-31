package com.example.willhero;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class GameController implements Initializable {

    Player player=new Player();

    Label score=new Label(Integer.toString(player.getCurrentscore()));
    Font font = Font.font("Brush Script MT", FontWeight.BOLD, FontPosture.REGULAR, 35);
    Label coin=new Label(Integer.toString(player.getCurrentcoins()));


    ArrayList<Game_Objects>gameObjects;
    ArrayList<Island>islands;
    ArrayList<Chests>chests;
    private Boss boss;
    private Random random;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button pause;

    @FXML
    private Group pausegroup;

    @FXML
    void pause_game(MouseEvent event) {

    }
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);



    private Hero hero = new Hero(50, 400);

    private EventHandler<MouseEvent> screenclicked;

    private AnimationTimer collision_objects=new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            collision_check();
            collision_chest_check();
        }
    };
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boss=new Boss(550, -240);
        chests=new ArrayList<>();
        random=new Random();
        islands=new ArrayList<>();
        gameObjects=new ArrayList<>();
        score.setText(Integer.toString(player.getCurrentscore()));
        coin.setText(Integer.toString(player.getCurrentcoins()));

        add_islands();
        add_game_objects();
        screenclicked=new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                player.setCurrentscore();
                score.setText(Integer.toString(player.getCurrentscore()));
                coin.setText(Integer.toString(player.getCurrentcoins()));
                hero.window_sliding_forward();
                for(int i=0;i<gameObjects.size();i++)
                {
                    gameObjects.get(i).objects_move_Back();
                }
                for(int i =0;i<islands.size();i++)
                {
                    islands.get(i).window_sliding();
                }
                for(int i =0;i<chests.size();i++)
                {
                    chests.get(i).window_sliding();
                }
                if(player.getCurrentscore()==104)
                {
//                    add_boss();
                    collision_objects.stop();
                    executorService.shutdownNow();
                }
            }
        };
        pane.setOnMouseClicked(screenclicked);
        collision_objects.start();
        add_hero();
        score.setLayoutX(500);
        score.setFont(font);
        coin.setLayoutX(1125);
        coin.setLayoutY(coin.getLayoutY()-3);
        coin.setFont(font);
        pane.getChildren().add(score);
        pane.getChildren().add(coin);

    }

    public void add_hero()
    {
        pane.getChildren().add(hero.imageView);
        hero.move_up_hero();
        hero.set_exact_y();
        executorService.scheduleAtFixedRate(hasfallen, 0, 2, TimeUnit.SECONDS);
    }
    public void add_islands()
    {
        Island a= new Island(70,473,0);
        islands.add(a);
        for (int i = 0; i < 29; i++) {
            Island island = new Island(islands.get(i).getX() + random.nextInt(200)+250, 470,0);
            islands.add(island);
            pane.getChildren().add(islands.get(i).imageView);
        }
        for(int i=0;i<20;i++)
        {
            if(i==0) {
                Island island1 = new Island(islands.get(29).getX()+250+random.nextInt(25),470,1);
                islands.add(island1);
            }
            else{
                Island island2 = new Island(islands.get(29+i).getX()+100,470,1);
                islands.add(island2);
            }
            pane.getChildren().add(islands.get(i+29).imageView);
        }
    }
    Runnable hasfallen = new Runnable() {
        @Override
        public void run() {
            if (!check_hero_x()) {
                hero.stop_up_transitions();
                hero.death();
            }
        }
    };
    private boolean check_hero_x() {
        for (int i = 0; i < 31; i++) {
            if (islands.get(i).getX() >= -160 && islands.get(i).getX() <= 100) {
                return true;
            }
        }
        return false;
    }

    private void add_game_objects() {
        int n;
        n = random.nextInt(20) + 10;

        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 29; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        for (int i = 0; i <= n; i++) {
                int number_of_obstacle = random.nextInt(2)+1;
                if (number_of_obstacle == 1) {
                    int x = islands.get(numbers.get(i)).getX();
                    int y = 425;
                    int zz=random.nextInt(200);
                    if(zz<=50){
                        int anynumber=random.nextInt(2);
                        if(anynumber==1) {
                            int pp = random.nextInt(90) + 60;
                            Chests chests1 = new Chests(x + pp, y - 35);
                            chests.add(chests1);
                        }
                    }
                    else if(zz>=140) {
                        int anynumber1 = random.nextInt(2);
                        if (anynumber1 == 1) {
                            int pp = random.nextInt(50);
                            Chests chests1 = new Chests(x + pp, y - 35);
                            chests.add(chests1);
                        }
                    }
                    x = x + zz;
                    int which = random.nextInt(4);

                    if (which == 0) {
                        TNT tnt = new TNT(x, y);
                        gameObjects.add(tnt);
                    } else if (which == 1) {
                        Red_Orchs r = new Red_Orchs(x, y);
                        gameObjects.add(r);
                    } else if (which==2){
                        Green_Orchs g = new Green_Orchs(x, y);
                        gameObjects.add(g);
                    }
                    else
                    {
                        Coin coin =new Coin(x,y);
                        gameObjects.add(coin);
                    }
                }
                else {
                    int x = islands.get(numbers.get(i)).getX();
                    int y = 425;
                    x = x + random.nextInt(60);


                    int which = random.nextInt(4);

                    if (which == 0) {
                        TNT tnt = new TNT(x, y);
                        gameObjects.add(tnt);
                    } else if (which == 1) {
                        Red_Orchs r = new Red_Orchs(x, y);
                        gameObjects.add(r);
                    } else if (which==2){
                        Green_Orchs g = new Green_Orchs(x, y);
                        gameObjects.add(g);
                    }
                    else
                    {
                        Coin coin =new Coin(x,y);
                        gameObjects.add(coin);
                    }
                        x=x+random.nextInt(60)+65;
                    which = random.nextInt(4);

                    if (which == 0) {
                        TNT tnt = new TNT(x, y);
                        gameObjects.add(tnt);
                    } else if (which == 1) {
                        Red_Orchs r = new Red_Orchs(x, y);
                        gameObjects.add(r);
                    } else if (which==2){
                        Green_Orchs g = new Green_Orchs(x, y);
                        gameObjects.add(g);
                    }
                    else
                    {
                        Coin coin =new Coin(x,y);
                        gameObjects.add(coin);
                    }
                }
        }
        for (int i = 0; i < gameObjects.size(); i++) {
            if (gameObjects.get(i) instanceof TNT)
                pane.getChildren().add(((TNT) gameObjects.get(i)).getImageView());
            else if (gameObjects.get(i) instanceof Red_Orchs)
            {
                pane.getChildren().add(((Red_Orchs)gameObjects.get(i)).getImageView());
            }
            else if(gameObjects.get(i) instanceof Green_Orchs){
                pane.getChildren().add(((Green_Orchs) gameObjects.get(i)).getImageView());
            }
            else{
                pane.getChildren().add(((Coin) gameObjects.get(i)).getImageView());
            }
        }
        for(int i=0;i<chests.size();i++)
        {
            pane.getChildren().add(chests.get(i).imageView);
        }
    }

    public void collision_chest_check()
    {
        for(int i=0;i<chests.size();i++)
        {
            if(hero.imageView.getBoundsInParent().intersects(chests.get(i).imageView.getBoundsInParent()) && chests.get(i).collided==false)
            {
                chests.get(i).collision();
                if(chests.get(i).getNumber()==1) {
                    player.setCurrentcoins(10);
                    coin.setText(Integer.toString(player.getCurrentcoins()));
                }
            }
        }
    }
    public void add_boss()
    {
        pane.getChildren().add(boss.imageView);
        Timeline t=new Timeline(new KeyFrame(Duration.millis(1), ee->
        {
            boss.imageView.setY(boss.imageView.getY()+1);
            boss.setY(hero.getY()+1);
        }));
        t.setCycleCount(395);
        t.play();
    }
    public void collision_check()
    {
        for(int i=0;i<gameObjects.size();i++)
        {
            if(gameObjects.get(i) instanceof TNT)
            {
                if(hero.imageView.getBoundsInParent().intersects(gameObjects.get(i).getImageView().getBoundsInParent()) &&  gameObjects.get(i).collided== false)
                {

                    ((TNT) gameObjects.get(i)).collision();
                }
            }
            else if(gameObjects.get(i) instanceof Green_Orchs)
            {
                if(hero.imageView.getBoundsInParent().intersects(gameObjects.get(i).getImageView().getBoundsInParent()) &&  gameObjects.get(i).collided== false) {

                    if (gameObjects.get(i).getImageView().getY() <= hero.getY()-10) {
                        hero.stop_up_transitions();
                        hero.death();
                    } else {
                        ((Green_Orchs) gameObjects.get(i)).collision();
                    }
                }
            }

            else if(gameObjects.get(i) instanceof Red_Orchs)
            {
                if(hero.imageView.getBoundsInParent().intersects(gameObjects.get(i).getImageView().getBoundsInParent()) &&  gameObjects.get(i).collided== false) {

                    if (gameObjects.get(i).getImageView().getY() <= hero.getY()-10) {
                        hero.stop_up_transitions();
                        hero.death();

                    } else {
                        ((Red_Orchs) gameObjects.get(i)).collision();
                    }
                }
            }
            else if(gameObjects.get(i) instanceof Coin)
            {
                if(hero.imageView.getBoundsInParent().intersects(gameObjects.get(i).getImageView().getBoundsInParent()) &&  gameObjects.get(i).collided== false)
                {
                        ((Coin) gameObjects.get(i)).collision();
                        player.setCurrentcoins();
                        coin.setText(Integer.toString(player.getCurrentcoins()));
                }
                }
            else
            {

            }
            }
    }


}

