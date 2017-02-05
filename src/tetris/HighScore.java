/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author SON
 */
public class HighScore extends Menu {

    private HBox boxMenu;
    public static String[] bgString = {"bg.jpg", "bg3.jpg", "bg3.jpg", "bg3.jpg", "bg3.jpg", "bg3.jpg"};
    Scene mainMenu;

    public static String[] list = {"CLEAR", "BACK"};

    public HighScore(Stage primaryStage, String backgroundUrl, String label, String color, String font) {
        super(primaryStage, bgString, label, list, color, font);
        rule();
    }

    public void setMainMenu(Scene mainMenu) {
        this.mainMenu = mainMenu;
    }

    @Override
    public void rule() {
        Vector<Runnable> role = new Vector<Runnable>(5);
        role.add(() -> {
            MainMenu.score = new int[30];
            MainMenu.name = new String[30];
            for (int i = 0; i < 30; i++) {
                MainMenu.name[i] = "-----";
            }
            updateMenu(scene, color, font);
        });
        role.add(() -> {
//        primaryStage.setScene(null);
            MainMenu.recordHighScore();
            primaryStage.setScene(mainMenu);
        });
        setRule(role);
    }

    public void animation() {

    }

    @Override
    public VBox createMenu(String color, String font) {
        VBox mainMenu = new VBox();
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.prefHeightProperty().bind(scene.heightProperty());
        mainMenu.prefWidthProperty().bind(scene.widthProperty());
        menu = menu(list, color, font);
        Label tile = new Label(label);
        tile.setTextFill(Color.web(color, .8));
        tile.setFont(Font.font(font, FontWeight.BOLD, FontPosture.ITALIC, 100));
        VBox.setMargin(tile, new Insets(0, 0, 100, 0));
        boxMenu = new HBox(50);
        boxMenu.setAlignment(Pos.CENTER);
        VBox content = content(color, font);
//        menu.setClip(new Rectangle(200, 200));
        boxMenu.getChildren().addAll(menu);
        VBox.setMargin(content, new Insets(0, 0, 100, 0));
        mainMenu.getChildren().addAll(tile, content, boxMenu);
        return mainMenu;
    }

    public void updateMenu(Scene scene, String color, String font) {
        bd1.getChildren().clear();
        menu = menu(list, color, font);
        Label tile = new Label(label);
        tile.setTextFill(Color.web(color, .8));
        tile.setFont(Font.font(font, FontWeight.BOLD, FontPosture.ITALIC, 100));
        VBox.setMargin(tile, new Insets(0, 0, 100, 0));
        boxMenu = new HBox(50);
        boxMenu.setAlignment(Pos.CENTER);
        VBox content = content(color, font);
//        menu.setClip(new Rectangle(200, 200));
        boxMenu.getChildren().addAll(menu);
        VBox.setMargin(content, new Insets(0, 0, 100, 0));
        bd1.getChildren().addAll(tile, content, boxMenu);
        rule();
    }

    public VBox content(String color, String font) {
        VBox result = new VBox(50);
//        result.prefWidthProperty().bind(scene.widthProperty());
        result.setAlignment(Pos.CENTER);
        result.getChildren().add(new Group(contentItem("NAME", "SCORE", color, font)));
        for (int i = 0; i < Setting.highScoreSize; i++) {
            result.getChildren().add(new Group(contentItem(MainMenu.name[i], Integer.toString(MainMenu.score[i]), color, font)));
        }
        return result;
    }

    public HBox contentItem(String name, String score, String color, String font) {
        Label nameLabel = new Label(name);
//        nameLabel.setTextAlignment(TextAlignment.LEFT);
        nameLabel.setTextFill(Color.web(color));
        nameLabel.setFont(Font.font(font, FontWeight.BOLD, 24));
        nameLabel.setPrefWidth(400);
        Label scoreLabel = new Label(score);
//        scoreLabel.setTextAlignment(TextAlignment.LEFT);
        scoreLabel.setPrefWidth(100);
        scoreLabel.setTextFill(Color.web(color));
        scoreLabel.setFont(Font.font(font, FontWeight.BOLD, 24));
        return new HBox(nameLabel, scoreLabel);
    }
}
