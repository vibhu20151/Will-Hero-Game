package com.example.willhero;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameOpen extends Application implements Initializable {

    public static Stage mystage;

    @FXML
    private ImageView cloud1;

    @FXML
    private ImageView cloud2;

    @FXML
    private ImageView hammer1;

    @FXML
    private ImageView hammer2;

    @FXML
    private ImageView hero;

    @FXML
    private ImageView island;

    @FXML
    private Button loginBtn;

    @FXML
    private Button loginBtn2;

    @FXML
    private Button loginBtn21;

    @FXML
    private ImageView orchs;

    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView queen;

    @FXML
    private ImageView sword1;

    @FXML
    private ImageView sword2;

    @FXML
    void exitgame(ActionEvent event) {

    }

    @FXML
    void jump(MouseEvent event) {

    }

    @FXML
    void load_game(ActionEvent event) {

    }

    @FXML
    void playgame(ActionEvent event) throws ClassCastException{
        FXMLLoader fxmlLoader1=new FXMLLoader(GameOpen.class.getResource("Game.fxml"));
        Scene scene1= null;
        try {
            scene1 = new Scene(fxmlLoader1.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mystage.setScene(scene1);
        mystage.show();
    }
    @Override
    public void start(Stage stage) throws IOException {
        mystage=stage;
        FXMLLoader fxmlLoader = new FXMLLoader(GameOpen.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ScaleTransition st = new ScaleTransition(Duration.millis(2000), hero);
        st.setByX(0.3f);
        st.setByY(0.3f);
        st.setCycleCount(Timeline.INDEFINITE);
        st.setAutoReverse(true);
        st.play();

        RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setByAngle(360);
        rotate.setCycleCount(500);
        rotate.setDuration(Duration.millis(3000));
        rotate.setAutoReverse(true);
        rotate.setNode(sword1);
        rotate.play();

        FadeTransition ft = new FadeTransition(Duration.millis(1000), hammer1);
        ft.setFromValue(1.0);
        ft.setToValue(0.5);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(false);
        ft.play();

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(800));
        translateTransition.setNode(orchs);
        translateTransition.setByY(150);
        translateTransition.setCycleCount(Timeline.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();


        TranslateTransition translate1Transition = new TranslateTransition();
        translate1Transition.setDuration(Duration.millis(1300));
        translate1Transition.setNode(cloud1);
        translate1Transition.setByX(40);
        translate1Transition.setCycleCount(Timeline.INDEFINITE);
        translate1Transition.setAutoReverse(true);
        translate1Transition.play();

        TranslateTransition translate2Transition = new TranslateTransition();
        translate2Transition.setDuration(Duration.millis(1500));
        translate2Transition.setNode(island);
        translate2Transition.setByX(150);
        translate2Transition.setCycleCount(Timeline.INDEFINITE);
        translate2Transition.setAutoReverse(true);
        translate2Transition.play();

    }
}