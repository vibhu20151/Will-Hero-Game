package com.example.willhero;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
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
    private ArrayList<Extra_Backgroud1> extra_backgroud1s;

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
    private Random random=new Random();
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
    private AnimationTimer death=new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            if(hero.getDeath()==1)
            {
                hero.death(pane);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Game_Over.fxml"));
                try {
                    gameoverpane=(AnchorPane) loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pane.getChildren().add(gameoverpane);
                gameoverpane.getChildren().get(5).setOnMouseClicked(playagain);                ;  //lags a littl.....!!

                gameoverpane.getChildren().get(4).setOnMouseClicked(playwithcoins);                ;  //lags a littl.....!!

                gameoverpane.getChildren().get(6).setOnMouseClicked(exit);                ;  //lags a littl.....!!
                gameoverpane.setLayoutX(200);
                gameoverpane.setLayoutY(200);
                scoreafterdeath=new Label(Integer.toString(player.getCurrentscore()));
                coinafterdeath=new Label(Integer.toString(player.getCurrentcoins()));
                scoreafterdeath.setLayoutX(325);
                scoreafterdeath.setFont(font);
                scoreafterdeath.setLayoutY(160);
                coinafterdeath.setLayoutY(200);
                coinafterdeath.setFont(font);
                coinafterdeath.setLayoutX(325);
                gameoverpane.getChildren().add(scoreafterdeath);
                gameoverpane.getChildren().add(coinafterdeath);
                death.stop();

                TranslateTransition t=new TranslateTransition();
                t.setToY(800);
                t.setDuration(Duration.millis(400));
                t.setNode(hero.imageView);
                t.setCycleCount(1);
                t.play();
                pause.setOnMouseClicked(null);
            }
        }
    };
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        score=new Label(Integer.toString(player.getCurrentscore()));
        coin=new Label(Integer.toString(player.getCurrentcoins()));
        setPlayer(GameController.playerlaoding);
        extra_backgroud1s = new ArrayList<>();
        add_backgroud();

        boss=new Boss(550, -240);

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
        pauseitnow=new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader  loader = new FXMLLoader(getClass().getResource("PauseMenu.fxml"));
                try {
                    panel=(AnchorPane) loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pane.getChildren().add(panel);
                panel.getChildren().get(1).setOnMouseClicked(resumeGame);
                panel.getChildren().get(2).setOnMouseClicked(saveGame);
                panel.getChildren().get(3).setOnMouseClicked(exitgame);


                pane.setOnMouseClicked(null);

            }
        };

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
                for (int i = 0; i < extra_backgroud1s.size(); i++) {
                    extra_backgroud1s.get(i).move_back();
                    extra_backgroud1s.get(i).move_screen_back();
                }
                if(player.getCurrentscore()==100)
                {
                    collision_objects.stop();
                    executorService.shutdownNow();
                }
                if(player.getCurrentscore()==105)
                {
                    add_boss();
                    boss.translation_of_boss();
                    boss_collision.start();
                }
                if(player.getCurrentscore()>105)
                {
                    boss.objects_move_Back();
                }
                if(player.getCurrentscore()==122)
                {
                    FXMLLoader  loader = new FXMLLoader(getClass().getResource("Game_Won.fxml"));
                    try {
                        panel=(AnchorPane) loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    panel.setLayoutX(60);
                    panel.setLayoutY(200);
                    pane.getChildren().add(panel);
                    panel.getChildren().get(2).setOnMouseClicked(playagain);
                    panel.getChildren().get(3).setOnMouseClicked(exitgame);
                    pane.setOnMouseClicked(null);
                    pause.setOnMouseClicked(null);
                    death.stop();
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
        exit=new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Platform.exit();
                System.exit(0);
            }
        };
        playwithcoins=new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(player.getGames_played()==0)
                {
                    if(player.getCurrentcoins() >= 3)
                    {
                        death.stop();
                        collision_objects.stop();

                        pane.getChildren().remove(gameoverpane);
                        player.setCurrentcoins(player.getCurrentcoins()-3);
                        coin.setText(Integer.toString(player.getCurrentcoins()));
                        pause.setOnMouseClicked(pauseitnow);
                        player.setGames_played(1);

                        pane.getChildren().remove(hero);

                        death.stop();
                        pane.setOnMouseClicked(screenclicked);
                        hero=new Hero(50,400);
                        hero.move_up_hero();
                        hero.set_exact_y();
                        pane.getChildren().add(hero.imageView);
                        pane.getChildren().add(hero.imageView);
                        ScheduledExecutorService executorService1 = Executors.newScheduledThreadPool(1);
                        executorService1.scheduleAtFixedRate(hasfallen, 0, 2, TimeUnit.SECONDS);
                        Timeline t=new Timeline(new KeyFrame(Duration.millis(1),e->
                        {
                            executorService1.scheduleAtFixedRate(hasfallen, 2, 2, TimeUnit.SECONDS);
                            collision_objects.start();
                            death.start();
                        }));
                        t.setCycleCount(1);
                        t.setDelay(Duration.seconds(4));
                        t.play();
                    }
                    else
                    {
                        Label message=new Label("Sorry You are not eligible to Revive..!");
                        gameoverpane.getChildren().add(message);
                        message.setFont(font);
                        message.setLayoutX(25);
                        message.setLayoutY(335);
                    }
                }
                else
                {
                    Label message=new Label("Sorry You are not eligible to Revive..!");
                    gameoverpane.getChildren().add(message);
                    message.setFont(font);
                    message.setLayoutX(25);
                    message.setLayoutY(335);
                }
            }
        };

        playagain=new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader fxmlLoader1=new FXMLLoader(GameOpen.class.getResource("Game.fxml"));
                Scene scene1= null;
                try {
                    scene1 = new Scene(fxmlLoader1.load());
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
        death.start();
        saveGame=new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Label l =new Label("Sorry,we supports only 1 Save for each Game!!..");
                panel.getChildren().add(l);
                l.setLayoutY(475);
                l.setLayoutX(175);
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
                hero.setDeath(1);
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
                    int k =i;

                    Timeline yt=new Timeline(new KeyFrame(Duration.millis(1),e->
                    {
                        if(gameObjects.get(k).getX()<110 & gameObjects.get(k).getX()>-50){
                            hero.stop_up_transitions();
                            hero.setDeath(1);
                        }
                    }));
                    yt.setCycleCount(1);
                    yt.setDelay(Duration.seconds(2));
                    yt.play();
                }
            }
            else if(gameObjects.get(i) instanceof Green_Orchs)
            {
                if(hero.imageView.getBoundsInParent().intersects(gameObjects.get(i).getImageView().getBoundsInParent()) &&  gameObjects.get(i).collided== false) {

                    if (gameObjects.get(i).getImageView().getY() <= hero.getY()-10) {
                        hero.stop_up_transitions();
                        hero.setDeath(1);
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
                        hero.setDeath(1);
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


    Label scoreafterdeath;
    Label coinafterdeath;
    private AnchorPane gameoverpane;

    private EventHandler<MouseEvent>playagain;
    private EventHandler<MouseEvent>playwithcoins;
    private EventHandler<MouseEvent>pauseitnow;
    private EventHandler<MouseEvent> exit;

    private AnimationTimer boss_collision=new AnimationTimer() {
        @Override
        public void handle(long l) {
            boss_collision();
        }
    };
    public void boss_collision()
    {
        if(hero.imageView.getBoundsInParent().intersects(boss.imageView.getBoundsInParent()) )
        {
            if(boss.imageView.getY()<=hero.getY()-240)
            {
                System.out.println(boss.imageView.getY());
                System.out.println(hero.getY()-20);
                hero.stop_up_transitions();
//                hero.death();
            }
            else
            {
                if(boss.getNo_of_collision()<20)
                {
                    boss.simple_collision();
                }
                else
                {
                    boss.death();
                }
            }
        }
    }
    private void add_backgroud() {
        Extra_Backgroud1 initial = new Extra_Backgroud1(random.nextInt(10), 100);
        extra_backgroud1s.add(initial);
        for (int i = 0; i <= 60; i++) {
            Extra_Backgroud1 hello = new Extra_Backgroud1(extra_backgroud1s.get(i).getX() + random.nextInt(100) + 200, random.nextInt(70) +100 );
            extra_backgroud1s.add(hello);
        }
        int i = 0;

        for (; i < extra_backgroud1s.size(); i++) {
            pane.getChildren().add(extra_backgroud1s.get(i).imageView);
        }
    }
}
