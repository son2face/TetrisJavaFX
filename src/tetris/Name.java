/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author SON
 */
public class Name {

    public static String name;
    protected Stage primaryStage;
    protected Scene scene;
    protected Group root;
    protected MainMenu mainMenu;
    protected ImageView backGround;
    private static String backGroundUrl = "bg1.jpg";
    protected String color;
    protected String font;

    public Name(Stage primaryStage, String color, String font) {
        this.primaryStage = primaryStage;
        this.color = color;
        this.font = font;
        mainMenu = new MainMenu(primaryStage, "bg1.jpg", "TETRIS", "Yellow", "Times New Roman");
        Tetris.customCursor(mainMenu.scene);
        createScene();
    }

    public Scene createScene() {
        root = new Group();
        scene = new Scene(root);
        backGround = createBackGround(backGroundUrl);
        Label titleLabel = new Label("NAME ");
        titleLabel.setTextFill(Color.web(color, .8));
        titleLabel.setFont(Font.font(font, FontWeight.BOLD, 30));
        TextField titleTextField = new TextField("NO NAME");
        titleTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                name = titleTextField.getText();
                primaryStage.setScene(mainMenu.getScene());
            }
        });

//        titleTextField.setBackground(new Background(new BackgroundFill(Paint.valueOf("FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));
        HBox titleBox = new HBox(titleLabel, titleTextField);
        titleBox.prefWidthProperty().bind(scene.widthProperty());
        titleBox.prefHeightProperty().bind(scene.heightProperty());
        titleBox.setAlignment(Pos.CENTER);
        root.getChildren().addAll(backGround, titleBox);
        root.prefHeight(scene.getHeight());
        root.prefWidth(scene.getWidth());
        return scene;
    }

    protected ImageView createBackGround(String imgUrl) {
        ImageView backGround = new ImageView(imgUrl);
        backGround.fitHeightProperty().bind(scene.heightProperty());
        backGround.fitWidthProperty().bind(scene.widthProperty());
//        backGround.setVisible(false);
        return backGround;
    }
}
