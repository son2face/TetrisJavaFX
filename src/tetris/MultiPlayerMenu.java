/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.util.Vector;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author SON
 */
public class MultiPlayerMenu extends Menu {

    Scene mainMenu;
    Scene play;
    public static String[] bgString = {"bg15.jpg", "bg3.jpg", "bg3.jpg"};

    public static String[] list = {"TWO PLAYER", "EXHAUTED", "BACK"};

    public MultiPlayerMenu(Stage primaryStage, String label, String color, String font) {
        super(primaryStage, bgString, label, list, color, font);
        TwoPlayer.setBackScene(scene);
        play = TwoPlayer.createScene(primaryStage);
        rule();
    }

    public void setMainMenu(Scene mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setMode() {
        if (Setting.isAnimation == true) {

        } else {

        }
        if (Setting.isColorFul == true) {
            Board.colors = new Color[]{Color.BLACK, Color.ORANGE, Color.GREEN, Color.RED, Color.BROWN, Color.PURPLE, Color.BLUE};
        } else {
            Board.colors = new Color[7];
            for (int i = 0; i < 7; i++) {
                Board.colors[i] = Setting.colorP;
            }
        }
        TwoPlayer.setVirtualPiece(Setting.isVirtualPiece);
        TwoPlayer.setNextPiece(Setting.isNextPiece);
    }

    @Override
    public void rule() {
        Vector<Runnable> role = new Vector<Runnable>(5);
        role.add(() -> {
            primaryStage.setScene(play);
        });
        role.add(() -> {
            System.out.println("EXHAUTED");
        });
        role.add(() -> {
            primaryStage.setScene(mainMenu);
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
