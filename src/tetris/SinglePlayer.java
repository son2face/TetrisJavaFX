/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import static tetris.MainMenu.name;
import static tetris.MainMenu.score;

/**
 *
 * @author SON
 */
public class SinglePlayer extends Scene {

    private static SinglePlayer current;
    public static Group root;
    private static Board board;
    protected Piece[] pieces;
    protected Timeline timer;
    public final int DELAY = 400;
    private int isProcess = 0;
    private static Scene singlePlayerMenu;
    private static Stage primaryStage;
    private int timeValue = 0;
    private int scoreValue = 0;
    private static VBox attention;
    private boolean isEscape = false;
    protected String[] list = new String[]{"CONTINUE", "SAVE", "LOAD", "MAINMENU"};
    protected MenuItem[] menu;
    protected int currentItem = 0;
    Label tile;
    static BorderPane start;
    static VBox xxx;

    private SinglePlayer(Parent root) {
        super(root);
        attention = new VBox(15);
        pieces = Piece.getPieces();
        this.menu = menu(list, "white", "Times New Roman");
        tile = new Label("PAUSE");
        tile.setTextFill(Color.web("white", .85));
        tile.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 140));
        VBox.setMargin(tile, new Insets(0, 0, 100, 0));
        attention.getChildren().add(tile);
        attention.getChildren().addAll(menu);
        makeAction();
        rule();
    }

    public void play() {
        timer.play();
    }

    protected static ImageView createBackGound(String imgUrl, Scene scene) {
        ImageView backGround = new ImageView(imgUrl);
        backGround.fitHeightProperty().bind(scene.heightProperty());
        backGround.fitWidthProperty().bind(scene.widthProperty());
//        backGround.setVisible(false);
        return backGround;
    }

    public static SinglePlayer createScene(Stage primaryStage) {
        board = new Board(10, 22, Name.name);
//        board.disableVirtualPiece();
        SinglePlayer.root = new Group();
        SinglePlayer result = new SinglePlayer(SinglePlayer.root);
        SinglePlayer.root.getChildren().add(createBackGound("bg1.jpg", result));
        Label startLabel = new Label("Press Enter To Start!");
        startLabel.setTextFill(Color.WHITE);
        startLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        startLabel.setTextAlignment(TextAlignment.CENTER);
        start = new BorderPane(startLabel);
        start.prefHeightProperty().set(board.getBase() * (board.getHeight() - 4));
        start.prefWidthProperty().set(board.getBase() * board.getWidth());
        start.setTranslateX(board.getBase() / 2);
        start.setTranslateY(board.getBase() / 2);
        board.getChildren().add(start);
        xxx = new VBox(board);
        xxx.setAlignment(Pos.CENTER);
        xxx.prefHeightProperty().bind(result.heightProperty());
        xxx.prefWidthProperty().bind(result.widthProperty());
        attention.setAlignment(Pos.CENTER);
        attention.prefHeightProperty().bind(result.heightProperty());
        attention.prefWidthProperty().bind(result.widthProperty());
        attention.setStyle("-fx-background-color: rgba(32,38,44, .8);");
        attention.setVisible(false);
        SinglePlayer.root.getChildren().addAll(xxx, attention);
        SinglePlayer.primaryStage = primaryStage;
        return result;
    }

    private void makeAction() {
        setOnKeyPressed((KeyEvent event) -> {
            if (isEscape == false) {
                if (event.getCode() == KeyCode.ENTER) {
                    tile.setText("PAUSED");
                    start.setVisible(false);
                    play();
                }
                if (event.getCode() == KeyCode.ESCAPE) {
                    menu[0].setActive(true);
                    escape();
                }
                if (timer.getStatus() == Timeline.Status.RUNNING) {
                    if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.NUMPAD8 || event.getCode() == KeyCode.W) {
                        board.rotate();
                    }
                    if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.NUMPAD4 || event.getCode() == KeyCode.A) {
                        board.left();
                    }
                    if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.NUMPAD6 || event.getCode() == KeyCode.D) {
                        board.right();
                    }
                    if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.NUMPAD0 || event.getCode() == KeyCode.SPACE) {
                        try {
                            int score = board.drop(timer, this);
                            for (int i = 1; i <= score; i++) {
                                scoreValue += i;
                            }
                            board.setScore(scoreValue);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SinglePlayer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } else {
                if (event.getCode() == KeyCode.ENTER) {
                    menu[currentItem].activate();
                }
                if (event.getCode() == KeyCode.ESCAPE) {
                    attention.setVisible(false);
                    isEscape = false;
                    timer.play();
                    menu[currentItem].setActive(false);
                    currentItem = 0;
                }
                if (event.getCode() == KeyCode.UP) {
                    if (currentItem > 0) {
                        menu[currentItem].setActive(false);
                        menu[--currentItem].setActive(true);
                    }
                }
                if (event.getCode() == KeyCode.DOWN) {
                    if (currentItem < menu.length - 1) {
                        menu[currentItem].setActive(false);
                        menu[++currentItem].setActive(true);
                    }
                }
            }
        });
        timer = new Timeline(new KeyFrame(Duration.millis(DELAY), event -> {
            try {
                int score = board.down(timer, this);
                for (int i = 1; i <= score; i++) {
                    scoreValue += i;
                }
                timeValue += DELAY;
                board.setTime(timeValue / 1000);
                board.setScore(scoreValue);
            } catch (InterruptedException ex) {
                Logger.getLogger(SinglePlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ));
        timer.setCycleCount(Animation.INDEFINITE);

    }

    public void gameOver() {
        updateHighScore();
        isEscape = true;
        menu[0].setActive(true);
        tile.setText("GAME OVER");
        menu[0].setLabel("New Game");
        menu[0].setOnActivate(() -> {
            timeValue = 0;
            scoreValue = 0;
            board = new Board(10, 22, "Son");
            xxx.getChildren().clear();
            xxx.getChildren().add(board);
            attention.setVisible(false);
            tile.setText("PAUSE");
            isEscape = false;
//            removeEventHandler(EventType.ROOT, eventHandler);
            makeAction();
            timer.play();
        });
        attention.setVisible(true);
    }

    private void updateHighScore() {
        int i = Setting.highScoreSize + 1;
        while (MainMenu.score[i - 1] < scoreValue && i > 1) {
            MainMenu.score[i] = MainMenu.score[i - 1];
            MainMenu.name[i] = MainMenu.name[i - 1];
            i--;
        }
        i--;
        MainMenu.score[i] = scoreValue;
        MainMenu.name[i] = Name.name;
    }

    private void escape() {
        isEscape = true;
        timer.pause();
        attention.setVisible(true);
    }

    public void rule() {
        Vector<Runnable> role = new Vector<Runnable>(5);
        role.add(() -> {
            attention.setVisible(false);
            isEscape = false;
            timer.play();
            menu[currentItem].setActive(false);
            currentItem = 0;
        });
        role.add(() -> {
            saveScene();
        });
        role.add(() -> {
            menu[currentItem].setActive(false);
            currentItem = 0;
            loadScene();
        });
        role.add(() -> {
            primaryStage.setScene(singlePlayerMenu);
//            attention.setVisible(false);
//            isEscape = false;
            menu[currentItem].setActive(false);
            menu[0].setActive(true);
            currentItem = 0;
        });
        setRule(role);
    }

    public void saveScene() {
        File fold = new File("data.txt");
        fold.delete();
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("data.txt", "UTF-8");
            writer.println(timeValue);
            writer.println(scoreValue);
            boolean[][] data = board.getMainBoard().getGrid();
            for (int i = 0; i < board.getHeight(); i++) {
                for (int j = 0; j < board.getWidth(); j++) {
                    writer.print(data[j][i]);
                    writer.print(" ");
                }
                writer.println("\n");
            }
            writer.println(board.getnCurrentPiece());
            writer.println(board.getmCurrentPiece());
            writer.println(board.getnNextPiece());
            writer.println(board.getmNextPiece());
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }

    public void loadScene() {
        Scanner input = null;
        try {
            input = new Scanner(Paths.get("data.txt"));
            int check = 1;
            int timeValue = 0;
            int scoreValue = 0;
            if (input.hasNextInt()) {
                timeValue = input.nextInt();
            } else {
                check = 0;
                System.out.println("a");
            }
            if (input.hasNextInt()) {
                scoreValue = input.nextInt();
            } else {
                check = 0;
                System.out.println("B");
            }
            boolean[][] data = new boolean[board.getWidth()][board.getHeight()];
            for (int i = 0; i < board.getHeight(); i++) {
                if (check == 0) {
                    System.out.println("C");
                    break;
                }
                for (int j = 0; j < board.getWidth(); j++) {
                    if (input.hasNextBoolean()) {
                        data[j][i] = input.nextBoolean();
                    } else {
                        System.out.println("d");
                        check = 0;
                        break;
                    }
                }
            }
            int nCurrentPiece = 0;
            int mCurrentPiece = 0;
            int nNextPiece = 0;
            int mNextPiece = 0;
            if (input.hasNextInt()) {
                nCurrentPiece = input.nextInt();
            } else {
                check = 0;
                System.out.println("e");
            }
            if (input.hasNextInt()) {
                mCurrentPiece = input.nextInt();
            } else {
                check = 0;
                System.out.println("f");
            }
            if (input.hasNextInt()) {
                nNextPiece = input.nextInt();
            } else {
                check = 0;
                System.out.println("g");
            }
            if (input.hasNextInt()) {
                mNextPiece = input.nextInt();
            } else {
                check = 0;
                System.out.println("h");
            }
            if (check == 1) {
                this.timeValue = timeValue;
                this.scoreValue = scoreValue;
                board = new Board(10, 22, Name.name);
                board.setMainBoard(data);
                board.setnCurrentPiece(nCurrentPiece);
                board.setmCurrentPiece(mCurrentPiece);
                board.setnNextPiece(nNextPiece);
                board.setmNextPiece(mNextPiece);
                board.setCurrentPiece();
                board.eraseNextPiece();
                board.setNextPiece();
                board.drawNextPiece();
                xxx.getChildren().clear();
                xxx.getChildren().add(board);
                attention.setVisible(false);
                tile.setText("PAUSE");
                isEscape = false;
                for (int i = 0; i < board.getHeight(); i++) {
                    for (int j = 0; j < board.getWidth(); j++) {
                        if (data[j][i] == true) {
                            board.drawBrick(j, i);
                        }
                    }
                }
                board.getChildren().remove(start);
                board.getChildren().add(start);
                start.setVisible(true);
            } else {

            }
            input.close();
        } catch (IOException ex) {

        }
    }

    public static void setBackScene(Scene scene) {
        singlePlayerMenu = scene;
    }

    private MenuItem[] menu(String[] list, String color, String font) {
        MenuItem[] menu = new MenuItem[list.length];
        for (int i = 0; i < list.length; i++) {
            menu[i] = new MenuItem(list[i], color, font);
        }
        return menu;
    }

    public void setRule(Vector<Runnable> role) {
        for (int i = 0; i < menu.length; i++) {
            menu[i].setOnActivate(role.elementAt(i));
        }
    }

    public static void setVirtualPiece(boolean key) {
        board.setVirtualPiece(key);
    }

    public static void setNextPiece(boolean key) {
        board.setNextPiece(key);
    }

    public static Board getBoard() {
        return board;
    }

}
