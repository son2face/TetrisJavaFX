/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import com.sun.javafx.application.HostServicesDelegate;
import java.util.Vector;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.util.Duration;

/**
 *
 * @author SON
 */
public class About extends Menu {

    public static String[] bgString = {"bg9.jpg", "bg3.jpg", "bg3.jpg", "bg3.jpg", "bg3.jpg", "bg3.jpg"};
    Text textRef;
    Scene mainMenu;
    private HBox boxMenu;
    public static String[] list = {"FACEBOOK", "BACK"};
    TranslateTransition transTransition1;

    public About(Stage primaryStage, String backgroundUrl, String label, String color, String font) {
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
            Tetris.faceBook();
        });
        role.add(() -> {
            primaryStage.setScene(mainMenu);
            transTransition1.pause();
        });
        setRule(role);
    }

    public void animation() {

    }

    public Text content(String color, String font) {
        String message = "\nNGUYỄN THÀNH SƠN\n\n"
                + "15022886\n\n"
                + "son2face@gmail.com\n\n"
                + "OOP-2015-2016\n\n"
                + "Assignment 2\n\n";
// Reference to the Text
        textRef = new Text(message);

//        textRef.setTextOrigin(VPos.CENTER);
        textRef.setTextAlignment(TextAlignment.CENTER);
        textRef.setFill(Color.web(color));
        textRef.setFont(Font.font(font, FontWeight.BOLD, 24));
// Provides the animated scrolling behavior for the text
        textRef.setLayoutY(400);
        transTransition1 = new TranslateTransition(new Duration(5000), textRef);
        transTransition1.setToY(-1000);
        transTransition1.setInterpolator(Interpolator.LINEAR);
        transTransition1.setCycleCount(Timeline.INDEFINITE);
        return textRef;
    }

    public void setAnimation(boolean check) {
        if (check == true) {
            if (textRef != null) {
                transTransition1.play();
                textRef.setLayoutY(400);
            }
        } else if (textRef != null) {
            transTransition1.stop();
            textRef.setLayoutY(0);
        }
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
        boxMenu = new HBox(150);
        boxMenu.setAlignment(Pos.CENTER);
        Text content = content(color, font);
        Group textGroup = new Group(content);
        textGroup.setClip(new Rectangle(500, 400));
        Group tex = new Group(textGroup);
//        menu.setClip(new Rectangle(200, 200));
        boxMenu.getChildren().addAll(menu);
        mainMenu.getChildren().addAll(tile, tex, boxMenu);
        return mainMenu;
    }

}
