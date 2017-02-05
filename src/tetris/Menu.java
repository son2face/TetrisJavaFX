/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.util.Arrays;
import java.util.Observable;
import java.util.Vector;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Transition;
import javafx.animation.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author SON
 */
public abstract class Menu {

    protected boolean busy = false;
    protected TranslateTransition cur, nex;
    protected int currentItem = 0;
    protected Stage primaryStage;
    protected VBox bd1;
    protected String[] backgroundUrl;
    protected String label;
    protected String color;
    protected Scene scene;
    protected String font;
    protected MenuItem[] menu;
    protected VBox boxMenu;
    protected String[] list;
    protected ImageView[] backGround;
    protected ImageView nextBG;
    protected Group root;
    protected Group root2;

    public Menu(Stage primaryStage, String[] backgroundUrl, String label, String[] list, String color, String font) {
        this.primaryStage = primaryStage;
        this.label = label;
        this.backgroundUrl = backgroundUrl;
        this.color = color;
        this.font = font;
        this.list = list;
        createScene();
    }

    public Scene createScene() {
        root = new Group();
        root2 = new Group();
        scene = new Scene(root);
        bd1 = createMenu(color, font);
        backGround = new ImageView[backgroundUrl.length];
        for (int i = 0; i < backgroundUrl.length; i++) {
            backGround[i] = createBackGound(backgroundUrl[i]);
        }
        backGround[0].setVisible(true);
        root2.getChildren().addAll(backGround);
        root.getChildren().addAll(root2, bd1);
        root.prefHeight(scene.getHeight());
        root.prefWidth(scene.getWidth());
        root2.prefHeight(scene.getHeight());
        root2.prefWidth(scene.getWidth());
        menu[currentItem].setActive(true);
        setKeyHandle();
        return scene;
    }

    public void setKeyHandle() {
        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.LEFT) {
                if (currentItem > 0 && busy == false) {
                    if (Setting.isAnimation == true) {
                        menu[currentItem].setActive(false);
                        createCurrentAnimation(-1);
                        menu[--currentItem].setActive(true);
//                    backGround[currentItem].setTranslateY(768);
                        createNextAnimation(0);
                        animation();
                    } else {
                        menu[currentItem].setActive(false);
                        menu[--currentItem].setActive(true);
                    }
                }
            }
            if ((event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.RIGHT) && busy == false) {
                if (currentItem < menu.length - 1) {
                    if (Setting.isAnimation == true) {
                        menu[currentItem].setActive(false);
                        createCurrentAnimation(1);
                        menu[++currentItem].setActive(true);
//                    backGround[currentItem].setTranslateY(-768);
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
                    FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), menu[currentItem]);
                    fadeTransition.setFromValue(1.0f);
                    fadeTransition.setToValue(0.3f);
                    fadeTransition.setCycleCount(6);
                    fadeTransition.setAutoReverse(true);
                    fadeTransition.setOnFinished((ab) -> {
                        menu[currentItem].activate();
                    });
                    fadeTransition.playFromStart();
                } else {
                    menu[currentItem].activate();
                }
            }
        });
    }

    public void setRule(Vector<Runnable> role) {
        for (int i = 0; i < menu.length; i++) {
            menu[i].setOnActivate(role.elementAt(i));
        }
    }

    public abstract void animation();

    protected ImageView createBackGound(String imgUrl) {
        ImageView backGround = new ImageView(imgUrl);
        backGround.fitHeightProperty().bind(scene.heightProperty());
        backGround.fitWidthProperty().bind(scene.widthProperty());
        backGround.setVisible(false);
        return backGround;
    }

    public abstract void rule();

    public VBox createMenu(String color, String font) {
        VBox mainMenu = new VBox();
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.prefHeightProperty().bind(scene.heightProperty());
        mainMenu.prefWidthProperty().bind(scene.widthProperty());
        this.menu = menu(list, color, font);
        Label tile = new Label(label);
        tile.setTextFill(Color.web(color, .8));
        tile.setFont(Font.font(font, FontWeight.BOLD, FontPosture.ITALIC, 140));
        VBox.setMargin(tile, new Insets(0, 0, 100, 0));
        boxMenu = new VBox(15);
        boxMenu.getChildren().addAll(menu);
        mainMenu.getChildren().addAll(tile, boxMenu);
        return mainMenu;
    }

    protected void createCurrentAnimation(int tran) {
        cur = new TranslateTransition(Duration.millis(400), backGround[currentItem]);
        cur.setToY(tran * backGround[currentItem].getFitHeight());
        int temp = currentItem;
        cur.setOnFinished((event) -> {
            backGround[temp].setVisible(false);
            backGround[temp].setTranslateY(0);
        });
    }

    protected void createNextAnimation(int tran) {
        nex = new TranslateTransition(Duration.millis(400), backGround[currentItem]);
        nex.setToY(tran * backGround[currentItem].getFitHeight());
        nex.setOnFinished((event) -> {
            busy = false;
        });
    }

    protected MenuItem[] menu(String[] list, String color, String font) {
        MenuItem[] menu = new MenuItem[list.length];
        for (int i = 0; i < list.length; i++) {
            menu[i] = new MenuItem(list[i], color, font);
        }
        return menu;
    }

    public Scene getScene() {
        return scene;
    }

    public Group getRoot() {
        return root;
    }

}
