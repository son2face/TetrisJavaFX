/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderImage;
import javafx.scene.layout.BorderRepeat;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author SON
 */
public class MainMenu extends Menu {

    public static String[] name;
    public static int[] score;
    public static String[] bgString = {"bg15.jpg", "bg18.jpg", "bg3.jpg", "bg5.jpg", "bg9.jpg", "bg7.jpg"};
    public static String[] list = {"SINGLE PLAYER", "MULTIPLAYER", "HIGH SCORE", "SETTING", "ABOUT", "QUIT"};
    SinglePlayerMenu singlePlayerMenu;
    MultiPlayerMenu multiPlayerMenu;
    HighScore highScore;
    Setting setting;
    About about;
    TranslateTransition transTransition;

    public MainMenu(Stage primaryStage, String backgroundUrl, String label, String color, String font) {
        super(primaryStage, bgString, label, list, color, font);
        readHighScore();
        singlePlayerMenu = new SinglePlayerMenu(primaryStage, label, color, font);
        singlePlayerMenu.setMainMenu(scene);
        multiPlayerMenu = new MultiPlayerMenu(primaryStage, label, color, font);
        multiPlayerMenu.setMainMenu(scene);
        highScore = new HighScore(primaryStage, "bg2.jpg", "HIGH SCORE", color, font);
        highScore.setMainMenu(scene);
        setting = new Setting(primaryStage, "bg4.jpg", color, font);
        setting.setMainMenu(scene);
        about = new About(primaryStage, "bg2.jpg", "ABOUT", color, font);
        about.setMainMenu(scene);
        rule();
    }

    public static void readHighScore() {
        Scanner input = null;
        try {
            input = new Scanner(Paths.get("score.txt"));
        } catch (IOException ex) {
            PrintWriter writer = null;
            try {
                try {
                    writer = new PrintWriter("score.txt", "UTF-8");
                } catch (FileNotFoundException ex1) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex1);
                } catch (UnsupportedEncodingException ex1) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex1);
                }
                for (int i = 0; i < 30; i++) {
                    writer.println("-----");
                    writer.println(0);
                }
                writer.close();
            } finally {
                writer.close();
            }
            try {
                input = new Scanner(Paths.get("score.txt"));
            } catch (IOException ex1) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        name = new String[31];
        score = new int[31];
        for (int i = 0; i < 30; i++) {
            if (input.hasNextLine() == true) {
                name[i] = input.nextLine();
            } else {
                name[i] = "-----";
            }
            if (input.hasNextInt() == true) {
                score[i] = input.nextInt();
            } else {
                score[i] = 0;
            }
            if (input.hasNextLine() == true) {
                input.nextLine();
            }
        }
        input.close();
    }

    public static void recordHighScore() {
        File fold = new File("score.txt");
        fold.delete();
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("score.txt", "UTF-8");
            for (int i = 0; i < 30; i++) {
                writer.println(name[i]);
                writer.println(score[i]);
            }
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }

    @Override
    public void rule() {
        Vector<Runnable> role = new Vector<Runnable>(5);
        role.add(() -> {
            singlePlayerMenu.setMode();
            primaryStage.setScene(singlePlayerMenu.getScene());
        });
        role.add(() -> {
            primaryStage.setScene(multiPlayerMenu.getScene());
        });
        role.add(() -> {
            highScore.updateMenu(scene, color, font);
            primaryStage.setScene(highScore.getScene());
        });
        role.add(() -> {

            primaryStage.setScene(setting.getScene());
        });
        role.add(() -> {
            about.setAnimation(Setting.isAnimation);
            primaryStage.setScene(about.getScene());
        });
        role.add(() -> {
            recordHighScore();
            System.exit(0);
        });
        setRule(role);
    }

    @Override
    public void animation() {
        backGround[currentItem].setVisible(true);
        ParallelTransition parall = new ParallelTransition(nex, cur);
        busy = true;
        parall.playFromStart();
    }

    @Override
    public void setKeyHandle() {
        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.UP && busy == false) {
                if (currentItem > 0 && busy == false) {
                    if (Setting.isAnimation == true) {
                        menu[currentItem].setActive(false);
                        createCurrentAnimation(-1);
                        menu[--currentItem].setActive(true);
                        backGround[currentItem].setTranslateY(backGround[currentItem].getFitHeight());
                        createNextAnimation(0);
                        animation();
                    } else {
                        menu[currentItem].setActive(false);
                        menu[--currentItem].setActive(true);
                    }
                }
            }
            if (event.getCode() == KeyCode.DOWN && busy == false) {
                if (currentItem < menu.length - 1) {
                    if (Setting.isAnimation == true) {
                        menu[currentItem].setActive(false);
                        createCurrentAnimation(1);
                        menu[++currentItem].setActive(true);
                        backGround[currentItem].setTranslateY(-backGround[currentItem].getFitHeight());
                        createNextAnimation(0);
                        animation();
                    } else {
                        menu[currentItem].setActive(false);
                        menu[++currentItem].setActive(true);
                    }
                }
            }
            if (event.getCode() == KeyCode.ENTER && busy == false) {
                if (Setting.isAnimation == true) {
                    busy = true;
                    FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), menu[currentItem]);
                    fadeTransition.setFromValue(1.0f);
                    fadeTransition.setToValue(0.3f);
                    fadeTransition.setCycleCount(6);
                    fadeTransition.setAutoReverse(true);
                    fadeTransition.setOnFinished((ab) -> {
                        menu[currentItem].activate();
                        busy = false;
                    });
                    fadeTransition.playFromStart();
                } else {
                    menu[currentItem].activate();
                }
            }
        });
    }
}
