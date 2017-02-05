/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;

/**
 *
 * @author SON
 */
public class Tetris extends Application {

    static Application application;
    Stage primaryStage;
//    private Thread thread = Thread.currentThread();

    public static void faceBook() {
        application.getHostServices().showDocument("https://www.facebook.com/profile.php?id=100009673156084");
    }

    @Override
    public void start(Stage primaryStage) {
        application = this;
        this.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("TETRIS");
        
        Name test = new Name(primaryStage, "Yellow", "Times New Roman");
        customCursor(test.scene);
        primaryStage.setScene(test.scene);
        
//        customCursor(test.scene);
//        MainMenu test = new MainMenu(primaryStage, "bg1.jpg", "TETRIS", "Yellow", "Times New Roman");
//        customCursor(test.scene);
//        primaryStage.setScene(test.scene);

//        Scene play = TwoPlayer.createScene(primaryStage);
//        customCursor(play);
//        primaryStage.setScene(play);

//        Scene play = SinglePlayer.createScene(primaryStage);
//        customCursor(play);
//        primaryStage.setScene(play);

//        HighScore play = new HighScore(primaryStage, "bg1.jpg", "TETRIS", "Yellow", "Times New Roman");
//        customCursor(play.scene);
//        primaryStage.setScene(play.scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainMenu.readHighScore();
        launch(args);
    }

    public static void customCursor(Scene scene) {
        Image img = new Image("cursor.png");
        ImageCursor imageCursor = new ImageCursor(img);
        scene.setCursor(imageCursor);
    }
}
