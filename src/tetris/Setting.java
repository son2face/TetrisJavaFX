/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.util.Vector;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author SON
 */
public class Setting {

    protected boolean busy = false;
    protected TranslateTransition cur, nex;
    protected static int currentItem = 0;
    private String[][] listOption;
    private int[] sizeListOption;
    protected static int[] currentItemY;
    protected Stage primaryStage;
    protected VBox bd1;
    protected String backgroundUrl;
    protected String label;
    protected String color;
    protected Scene scene;
    protected String font;
    protected MenuOptionItem[] menu;
    protected VBox boxMenu;
    public static String[] list = {"WINDOW MODE", "COLOR PIECES", "ANIMATION", "VIRTUAL PIECE", "NEXT PIECE", "HIGH SCORE SIZE", "SAVE AND BACK"};
    protected ImageView backGround;
    protected Group root;
    protected Group root2;
    public static boolean isFullScreen = true;
    public static boolean isAnimation = true;
    public static boolean isColorFul = true;
    public static boolean isVirtualPiece = true;
    public static boolean isNextPiece = true;
    public static Color colorP = Color.RED;
    public static int highScoreSize = 5;
    public static int level = 1;
    Scene mainMenu;

    public Setting(Stage primaryStage, String backgroundUrl, String color, String font) {
        this.primaryStage = primaryStage;
        this.backgroundUrl = backgroundUrl;
        this.color = color;
        this.font = font;
        createScene();
        rule();
    }

    public Scene createScene() {
        root = new Group();
        root2 = new Group();
        scene = new Scene(root);
        createListOption();
        bd1 = createMenu(scene, color, font);
        backGround = createBackGound(backgroundUrl);
        backGround.setVisible(true);
        root2.getChildren().add(backGround);
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
            if (event.getCode() == KeyCode.UP) {
                if (currentItem > 0) {
                    menu[currentItem].setActive(false);
                    menu[--currentItem].setActive(true);
//                    menu[currentItem].setLabelOption(listOption[currentItem][currentItemY[currentItem] % sizeListOption[currentItem]]);
                }
            }
            if (event.getCode() == KeyCode.DOWN) {
                if (currentItem < menu.length - 1) {
                    menu[currentItem].setActive(false);
                    menu[++currentItem].setActive(true);
//                    menu[currentItem].setLabelOption(listOption[currentItem][currentItemY[currentItem] % sizeListOption[currentItem]]);
                }
            }
            if (event.getCode() == KeyCode.LEFT) {
                if (currentItem < menu.length - 1) {
                    currentItemY[currentItem]--;
                    menu[currentItem].setLabelOption(listOption[currentItem][(sizeListOption[currentItem] + currentItemY[currentItem] % sizeListOption[currentItem]) % sizeListOption[currentItem]]);
                }
            }
            if (event.getCode() == KeyCode.RIGHT) {
                if (currentItem < menu.length - 1) {
                    currentItemY[currentItem]++;
                    menu[currentItem].setLabelOption(listOption[currentItem][(sizeListOption[currentItem] + currentItemY[currentItem] % sizeListOption[currentItem]) % sizeListOption[currentItem]]);
                }
            }
            if (event.getCode() == KeyCode.ENTER && currentItem == menu.length - 1) {
                menu[currentItem].activate();
            }
        });
    }

    public void setRule(Vector<Runnable> role) {
        for (int i = 0; i < menu.length; i++) {
            menu[i].setOnActivate(role.elementAt(i));
        }
    }

    protected ImageView createBackGound(String imgUrl) {
        ImageView backGround = new ImageView(imgUrl);
        backGround.fitHeightProperty().bind(scene.heightProperty());
        backGround.fitWidthProperty().bind(scene.widthProperty());
        backGround.setVisible(false);
        return backGround;
    }

    public VBox createMenu(Scene scene, String color, String font) {
        VBox mainMenu = new VBox();
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.prefHeightProperty().bind(scene.heightProperty());
        mainMenu.prefWidthProperty().bind(scene.widthProperty());
        this.menu = menu(list, color, font);
        boxMenu = new VBox(15);
        boxMenu.getChildren().addAll(menu);
        mainMenu.getChildren().addAll(boxMenu);
        return mainMenu;
    }

    private void update() {
        if (currentItemY[0] % sizeListOption[0] == 0) {
            if (isFullScreen == false) {
                isFullScreen = true;
                primaryStage.hide();
                primaryStage = new Stage();
                primaryStage.initStyle(StageStyle.TRANSPARENT);
                primaryStage.setMaximized(true);
                primaryStage.show();
                primaryStage.setTitle("TETRIS");

                MainMenu test = new MainMenu(primaryStage, "bg1.jpg", "TETRIS", "Yellow", "Times New Roman");
//        Scene play = SinglePlayer.createScene(primaryStage);
                mainMenu = test.scene;
            }
        } else if (isFullScreen == true) {
            isFullScreen = false;
            primaryStage.hide();
            primaryStage = new Stage();
            primaryStage.initStyle(StageStyle.DECORATED);
            primaryStage.setMaximized(false);
            primaryStage.show();
            primaryStage.setTitle("TETRIS");

            MainMenu test = new MainMenu(primaryStage, "bg1.jpg", "TETRIS", "Yellow", "Times New Roman");
//        Scene play = SinglePlayer.createScene(primaryStage);
            mainMenu = test.scene;
        }
        if (currentItemY[1] % sizeListOption[1] == 0) {
            isColorFul = true;
        } else {
            isColorFul = false;
        }
        if (currentItemY[2] % sizeListOption[2] == 0) {
            isAnimation = true;
        } else {
            isAnimation = false;
        }
        if (currentItemY[3] % sizeListOption[3] == 0) {
            isVirtualPiece = true;
        } else {
            isVirtualPiece = false;
        }
        if (currentItemY[4] % sizeListOption[4] == 0) {
            isNextPiece = true;
        } else {
            isNextPiece = false;
        }
        if (currentItemY[5] % sizeListOption[5] == 0) {
            highScoreSize = 5;
        } else if (currentItemY[5] % sizeListOption[5] == 1) {
            highScoreSize = 10;
        } else if (currentItemY[5] % sizeListOption[5] == 1) {
            highScoreSize = 15;
        } else {
            highScoreSize = 20;
        }
    }

    private void createListOption() {
        listOption = new String[list.length][10];
        sizeListOption = new int[list.length];
        if (currentItemY == null) {
            currentItemY = new int[list.length];
        }
        listOption[0][0] = "FULL SCREEN";
        listOption[0][1] = "WINDOW";
        sizeListOption[0] = 2;
        listOption[1][0] = "COLORFUL";
        listOption[1][1] = "MONO COLOR";
        sizeListOption[1] = 2;
        listOption[2][0] = "ON";
        listOption[2][1] = "OFF";
        sizeListOption[2] = 2;
        listOption[3][0] = "SHOW";
        listOption[3][1] = "HIDE";
        sizeListOption[3] = 2;
        listOption[4][0] = "SHOW";
        listOption[4][1] = "HIDE";
        sizeListOption[4] = 2;
        listOption[5][0] = "5";
        listOption[5][1] = "10";
        listOption[5][2] = "15";
        listOption[5][3] = "20";
        sizeListOption[5] = 4;
    }

    public void rule() {
        Vector<Runnable> role = new Vector<Runnable>(5);
        role.add(() -> {
            System.out.println("NOMAL");
        });
        role.add(() -> {
            System.out.println("EXHAUTED");
        });
        role.add(() -> {
            System.out.println("EXHAUTED");
        });
        role.add(() -> {
            System.out.println("EXHAUTED");
        });
        role.add(() -> {
            System.out.println("EXHAUTED");
        });
        role.add(() -> {
            System.out.println("EXHAUTED");
        });
        role.add(() -> {
            update();
            primaryStage.setScene(mainMenu);
        });
        setRule(role);
    }

    public void setMainMenu(Scene mainMenu) {
        this.mainMenu = mainMenu;
    }

    protected MenuOptionItem[] menu(String[] list, String color, String font) {
        MenuOptionItem[] menu = new MenuOptionItem[list.length];
        for (int i = 0; i < list.length; i++) {
            menu[i] = new MenuOptionItem(list[i], color, font);
            menu[i].setLabelOption(listOption[i][0]);
        }
        menu[list.length - 1].setNullOption();
        return menu;
    }

    public Scene getScene() {
        return scene;
    }

    public Group getRoot() {
        return root;
    }
}
