package com.example.willhero;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Regenerate_Game implements Initializable {
    ScheduledExecutorService executorService22 = Executors.newScheduledThreadPool(1);

    private Player player = new Player();

    public void setPlayer(Player player) {
        this.player = player;
        score.setText(Integer.toString(player.getCurrentscore()));
        coin.setText(Integer.toString(player.getCurrentcoins()));
//        add_islands();
//        add_game_objects();
    }
    Label score;
    Font font = Font.font("Brush Script MT", FontWeight.BOLD, FontPosture.REGULAR, 35);
    Label coin;

    ArrayList<Game_Objects>gameObjects;
    ArrayList<Island>islands;
    ArrayList<Chests>chests;
    private Boss boss;
    private Random random;
    private AnchorPane panel ;

    private AnchorPane saveit ;

    @FXML
    private AnchorPane pane;
    @FXML
    private Button pause;

    private EventHandler<MouseEvent> screenclicked;
    private EventHandler<MouseEvent> resumeGame;
    private EventHandler<MouseEvent> saveGame;
    private EventHandler<MouseEvent> exitgame;


    @FXML
    void pause_game(MouseEvent event) throws IOException {
        FXMLLoader  loader = new FXMLLoader(getClass().getResource("PauseMenu.fxml"));
        panel=(AnchorPane) loader.load();
        pane.getChildren().add(panel);
        panel.getChildren().get(1).setOnMouseClicked(resumeGame);
        panel.getChildren().get(2).setOnMouseClicked(saveGame);
        panel.getChildren().get(3).setOnMouseClicked(exitgame);
        pane.setOnMouseClicked(null);
    }

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    Hero hero;

    private AnimationTimer collision_objects=new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            collision_check();
            collision_chest_check();
        }
    };
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        score=new Label(Integer.toString(player.getCurrentscore()));
        coin=new Label(Integer.toString(player.getCurrentcoins()));
        setPlayer(GameController.playerlaoding);

        chests=new ArrayList<>();
        random=new Random();
        islands=new ArrayList<>();
        gameObjects=new ArrayList<>();
//        add_hero();
        add_islands();
        add_chests();
        add_game_objects();
        boss=new Boss(550, -240);
        score.setText(Integer.toString(player.getCurrentscore()));
        coin.setText(Integer.toString(player.getCurrentcoins()));
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
                    collision_objects.stop();
                    executorService.shutdownNow();
                }
            }
        };
        resumeGame=new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                pane.getChildren().remove(panel);
                pane.getChildren().remove(panel);
                pane.setOnMouseClicked(screenclicked);
            }
        };

        exitgame=new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader fxmlLoader=new FXMLLoader(GameOpen.class.getResource("MainMenu.fxml"));
                Scene scene1= null;
                try {
                    scene1 = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                GameOpen.mystage.setScene(scene1);
                GameOpen.mystage.show();
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

        saveGame=new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                player.name_increase();

                String filename = filename("C:\\Users\\S K R\\Desktop\\iiit delhi\\Advanced Pragramming\\src\\main\\GamesSaved");
                try {
                    SerializePlayer(filename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FXMLLoader fxmlLoader = new FXMLLoader(GameOpen.class.getResource("MainMenu.fxml"));
                Scene scene1 = null;
                try {
                    scene1 = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                GameOpen.mystage.setScene(scene1);
                GameOpen.mystage.show();
            }
        };

    }

    private String filename(String s) {

        s=s.concat("\\");
        String name_of_player=null;
        name_of_player= String.valueOf(player.getName());
        s=s.concat(name_of_player);
        s=s.concat("player");
        s=s.concat(".txt");
        return s;
    }
    public void SerializePlayer(String fileName) throws IOException {
        ObjectOutputStream out = null;
        try {
            player.add_objects_to_player(gameObjects,hero,islands,chests);
            out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(player);

        }finally {
            assert out != null;
            out.close();
        }
    }
    Runnable hasfallen = new Runnable() {
        @Override
        public void run() {
            if (!check_hero_x()) {
                hero.stop_up_transitions();
                hero.death(pane);
                executorService.shutdownNow();
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
                        hero.death(pane);
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
                        hero.death(pane);

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
    private AnchorPane gameoverpane;

    public void loadthegame(String finalloader_file) throws IOException, ClassNotFoundException {
        deserialize(finalloader_file);
    }
    public static Player playerlaoding=null;
    private void deserialize(String finalloader_file) throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        try{
            in=new ObjectInputStream(new FileInputStream(finalloader_file));
            playerlaoding=(Player)in.readObject();
        }
        finally {
            in.close();
        }


        FXMLLoader fxmlLoader1=new FXMLLoader(GameOpen.class.getResource("Game_Scene_Blank.fxml"));
        Scene scene1= null;
        try {
            scene1 = new Scene(fxmlLoader1.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        GameOpen.mystage.setScene(scene1);
        Regenerate_Game a=fxmlLoader1.getController();
//        a.setPlayer(playerlaoding);
//        fxmlLoader1.setController(a);

    }

    private void add_hero() {
        hero= new Hero(player.getHero().getX(), 400);
        pane.getChildren().add(hero.imageView);
        hero.move_up_hero();
        hero.set_exact_y();
        executorService22.scheduleAtFixedRate(hasfallen, 0, 2, TimeUnit.SECONDS);
    }
    public void add_islands()
    {
        for(int i=0;i<player.getIslands().size();i++)
        {
            Island island=new Island(player.getIslands().get(i).getX(),player.getIslands().get(i).getY(),0);
            islands.add(island);
        }
        for(int i=0;i<islands.size();i++)
        {
            pane.getChildren().add(islands.get(i).imageView);
        }
    }
    public void add_game_objects()
    {
        for(int i =0;i<player.getGameObjects().size();i++)
        {
            if(player.getGameObjects().get(i) instanceof TNT)
            {
                TNT tnt=new TNT(player.getGameObjects().get(i).getX(),player.getGameObjects().get(i).getY());
                gameObjects.add(tnt);
            }
            else if (player.getGameObjects().get(i) instanceof Red_Orchs)
            {
                Red_Orchs red_orchs=new Red_Orchs(player.getGameObjects().get(i).getX(),425);
                gameObjects.add(red_orchs);
            }
            else if(player.getGameObjects().get(i) instanceof Green_Orchs)
            {
                Green_Orchs green_orchs=new Green_Orchs(player.getGameObjects().get(i).getX(),425);
                gameObjects.add(green_orchs);
            }
            else if (player.getGameObjects().get(i) instanceof Coin)
            {
                Coin coin=new Coin(player.getGameObjects().get(i).getX(),player.getGameObjects().get(i).getY());
                gameObjects.add(coin);
            }
            else{

            }
        }
        for (int i =0;i<gameObjects.size();i++)
        {
            pane.getChildren().add(gameObjects.get(i).imageView);
        }
    }
    private void add_chests() {
        for(int i=0;i<player.getChests().size();i++)
        {
            Chests chests1=new Chests(player.getChests().get(i).getX(),player.getChests().get(i).getY());
            chests.add(chests1);
        }
        for(int i=0;i<chests.size();i++)
        {
            pane.getChildren().add(chests.get(i).imageView);
        }
    }

}
