/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 *
 * @author SON
 */
public class Board extends Group {

    // Make color for border
    private Color t1 = Color.YELLOW;
    private Color t2 = Color.RED;
    private Color t3 = Color.WHITE;
    private Color nextColor;
    private double pX;
    private double pY;
    boolean committed;
    private int width;
    private int height;
    int base = 20;
    private String name;
    Brick[][] grid;
    private Piece currentPiece;
    private int currentX;
    private int currentY;
    private Piece[] pieces;
    private Random random;
    private Piece nextPiece;
    private BoardADT mainBoard;
    private Group virtual;
    private Group nextPieceGroup;
    private VBox controlGroup;
    private Group bg;
    public static Color[] colors = new Color[]{Color.BLACK, Color.ORANGE, Color.GREEN, Color.RED, Color.BROWN, Color.PURPLE, Color.BLUE};
    private Color color;
    private Label time;
    private Label score;
    private Label nameLabel;
    private int nCurrentPiece;
    private int mCurrentPiece;
    private int nNextPiece;
    private int mNextPiece;

    public Board(int width, int height, String name) {
        this.name = name;
        virtual = new Group();
        nextPieceGroup = new Group();
        createControl();
//        nextPieceGroup.setAlignment(Pos.CENTER);
        bg = new Group();
        virtual.setOpacity(.3);
        getChildren().add(bg);
        getChildren().add(virtual);
        this.width = width;
        this.height = height;
        pieces = Piece.getPieces();
        Brick.setFactoryBrick(base);
        pY = base * height - base * 0.5;
        pX = base / 2;
        Rectangle border = createBorder(base / 2, base / 2, base * width, base * (height - 4));
        getChildren().add(border);
        getChildren().add(controlGroup);
        clean();
    }

    private void createControl() {
        nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
        nameLabel.setTextFill(Color.WHITE);
//        VBox.setMargin(nameLabel, new Insets(0, 0, base, 0));
        Label timeLabel = new Label("Time: ");
        timeLabel.setTextFill(Color.WHITE);
        time = new Label();
        time.setTextFill(Color.WHITE);
        time.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        VBox timeBox = new VBox(timeLabel, time);
        Label scoreLabel = new Label("Score: ");
        scoreLabel.setTextFill(Color.WHITE);
        score = new Label();
        score.setTextFill(Color.WHITE);
        score.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        VBox scoreBox = new VBox(scoreLabel, score);
        HBox tre = new HBox(nextPieceGroup);
        tre.setAlignment(Pos.CENTER);
        tre.setPrefSize(base * 4, base * 4);
        controlGroup = new VBox(base, nameLabel, tre, timeBox, scoreBox);
        controlGroup.setTranslateX(base * 12);
        controlGroup.setTranslateY(pX);
    }

    Rectangle createBorder(double x, double y, double width, double height) {
        Rectangle border = new Rectangle(x, y, width, height);
        border.setFill(Color.TRANSPARENT);
        border.setStrokeType(StrokeType.OUTSIDE);
        border.setStrokeWidth(base / 3);
        LinearGradient gradient2 = new LinearGradient(
                20, // focusAngle
                0, // focusDistance
                width / 2, // centerX
                height / 2, // centerY
                // radius
                false, // proportional
                CycleMethod.REPEAT, // cycleMethod
                new Stop(0, t1), // stops
                new Stop(.5, t2),
                new Stop(1, t3)
        );
        border.setStroke(gradient2);
        return border;
    }

    public void setTime(int time) {
        this.time.setText(Integer.toString(time));
    }

    public void setScore(int time) {
        this.score.setText(Integer.toString(time));
    }

    public void createBackGound(Scene scene, String imgUrl) {
        ImageView backGround = new ImageView(imgUrl);
        backGround.fitHeightProperty().bind(scene.heightProperty());
        backGround.fitWidthProperty().bind(scene.widthProperty());
        bg.getChildren().add(backGround);
    }

    public void start() {
        random = new Random();
        nNextPiece = random.nextInt(7);
        mNextPiece = random.nextInt(4);
        nextPiece = pieces[nNextPiece];
        nextColor = colors[nNextPiece];
        drawNextPiece();
        nextBrick();
    }

    public void nextBrick() {
        eraseNextPiece();
        currentPiece = nextPiece;
        nCurrentPiece = nNextPiece;
        mCurrentPiece = mNextPiece;
        nNextPiece = random.nextInt(7);
        mNextPiece = random.nextInt(4);

        color = nextColor;
        nextPiece = pieces[nNextPiece];
        nextColor = colors[nNextPiece];

        for (int j = 0; j < mNextPiece; j++) {
            nextPiece = nextPiece.fastRotation();
        }
        drawNextPiece();
        currentX = (width - currentPiece.getWidth()) / 2;
        currentY = height - 4;
    }

    private void drawPiece(Piece piece, int x, int y) {
        TPoint[] temp = piece.getBody();
        for (int i = 0; i < 4; i++) {
            drawBrick(x + temp[i].x, y + temp[i].y);
        }
    }

    public void drawNextPiece() {
        TPoint[] temp = nextPiece.getBody();
        for (int i = 0; i < 4; i++) {
            nextPieceGroup.getChildren().add(Brick.createBrick(nextColor, base * temp[i].x, -base * temp[i].y));
        }
    }

    public void eraseNextPiece() {
        nextPieceGroup.getChildren().clear();
    }

    private void erasePiece(Piece piece, int x, int y) {
        TPoint[] temp = piece.getBody();
        for (int i = 0; i < 4; i++) {
            eraseBrick(x + temp[i].x, y + temp[i].y);
        }
    }

    public void drawBrick(int x, int y) {
        if (y < height - 4) {
            grid[x][y] = Brick.createBrick(color, pX + base * x, pY - base * (y + 4));
            getChildren().add(grid[x][y]);
        }
    }

    private void eraseBrick(int x, int y) {
        getChildren().remove(grid[x][y]);
    }

    private void drawVitrualPiece(Piece piece, int x, int y) {
        TPoint[] temp = piece.getBody();
        int t = mainBoard.dropHeight(piece, x, y);
        for (int i = 0; i < 4; i++) {
            drawVitrualBrick(x + temp[i].x, t + temp[i].y);
        }
    }

    private void eraseVitrualPiece() {
        virtual.getChildren().clear();
    }

    private void drawVitrualBrick(int x, int y) {
        if (y < height - 4) {
            virtual.getChildren().add(Brick.createBrick(color, pX + base * x, pY - base * (y + 4)));
        }
    }

    int rowFilled;

    public void computeRowFill(Timeline timer) throws InterruptedException {
        timer.pause();
        SequentialTransition seqT = new SequentialTransition();
        rowFilled = 0;
        if (mainBoard.update(currentPiece, currentX, currentY) == true) {
            for (int i = 0; i < currentPiece.getHeight(); i++) {
                if (mainBoard.clearRow(i + currentY - rowFilled) == true) {
                    seqT.getChildren().add(eraseRow(i + currentY));
                    rowFilled++;
                };
            }
        }
        seqT.setOnFinished((event) -> {
            timer.play();
        });
        seqT.play();
    }

    public SequentialTransition eraseRow(int y) {
        final int ex = rowFilled;
        FadeTransition fade = null;
        SequentialTransition seqT = new SequentialTransition();
        for (int i = 0; i < width; i++) {
            fade = new FadeTransition(Duration.millis(30), grid[i][y]);
            fade.setFromValue(1.0f);
            fade.setToValue(0.3f);
            seqT.getChildren().add(fade);
        }
        fade.setOnFinished((event) -> {
            for (int i = 0; i < width; i++) {
                eraseBrick(i, y - ex);
            }
            for (int j = y - ex; j < height - 1; j++) {
                for (int k = 0; k < width; k++) {
                    grid[k][j] = grid[k][j + 1];
                    if (grid[k][j] != null) {
                        grid[k][j].setTranslateY(grid[k][j].translateYProperty().get() + base);
                    }
                }
            }
            for (int k = 0; k < width; k++) {
                grid[k][height - 1] = null;
            }
        });
        return seqT;
    }

    public void left() {
        currentX--;
        int result = mainBoard.place(currentPiece, currentX, currentY);
        if (result < 1) {
            erasePiece(currentPiece, currentX + 1, currentY);
            eraseVitrualPiece();
            drawPiece(currentPiece, currentX, currentY);
            drawVitrualPiece(currentPiece, currentX, currentY);
        } else {
            currentX++;
        }
    }

    public void right() {
        currentX++;
        int result = mainBoard.place(currentPiece, currentX, currentY);
        if (result < 1) {
            erasePiece(currentPiece, currentX - 1, currentY);
            eraseVitrualPiece();
            drawPiece(currentPiece, currentX, currentY);
            drawVitrualPiece(currentPiece, currentX, currentY);
        } else {
            currentX--;
        }
    }

    public void rotate() {
        Piece next = currentPiece.fastRotation();
        int result = mainBoard.place(next, currentX, currentY);
        if (result < 1) {
            erasePiece(currentPiece, currentX, currentY);
            eraseVitrualPiece();
            drawPiece(next, currentX, currentY);
            drawVitrualPiece(next, currentX, currentY);
            currentPiece = next;
        } else if (result == 1) {
            int result2 = mainBoard.place(next, currentX + currentPiece.getWidth() - next.getWidth(), currentY);
            if (result2 < 1) {
                erasePiece(currentPiece, currentX, currentY);
                eraseVitrualPiece();
                drawPiece(next, currentX + currentPiece.getWidth() - next.getWidth(), currentY);
                drawVitrualPiece(next, currentX + currentPiece.getWidth() - next.getWidth(), currentY);
                currentX = currentX + currentPiece.getWidth() - next.getWidth();
                currentPiece = next;
            }
        }
    }

    public int drop(Timeline timer, SinglePlayer singlePlayer) throws InterruptedException {
        erasePiece(currentPiece, currentX, currentY);
        currentY = mainBoard.dropHeight(currentPiece, currentX, currentY);
        drawPiece(currentPiece, currentX, currentY);
        eraseVitrualPiece();
        mainBoard.place(currentPiece, currentX, currentY);
        computeRowFill(timer);
        if (mainBoard.getMaxHeight() > mainBoard.getHeight() - 4) {
            timer.stop();
            singlePlayer.gameOver();
        }
        nextBrick();
        return rowFilled;
    }

    public int drop(Timeline timer, Exhauted singlePlayer) throws InterruptedException {
        erasePiece(currentPiece, currentX, currentY);
        currentY = mainBoard.dropHeight(currentPiece, currentX, currentY);
        drawPiece(currentPiece, currentX, currentY);
        eraseVitrualPiece();
        mainBoard.place(currentPiece, currentX, currentY);
        computeRowFill(timer);
        if (mainBoard.getMaxHeight() > mainBoard.getHeight() - 4) {
            timer.stop();
            singlePlayer.gameOver();
        }
        nextBrick();
        return rowFilled;
    }

    public int drop(Timeline timer, TwoPlayer singlePlayer) throws InterruptedException {
        erasePiece(currentPiece, currentX, currentY);
        currentY = mainBoard.dropHeight(currentPiece, currentX, currentY);
        drawPiece(currentPiece, currentX, currentY);
        eraseVitrualPiece();
        mainBoard.place(currentPiece, currentX, currentY);
        computeRowFill(timer);
        if (mainBoard.getMaxHeight() > mainBoard.getHeight() - 4) {
            singlePlayer.gameOver();
        }
        nextBrick();
        return rowFilled;
    }

    public void clean() {
        if (currentPiece != null) {
            erasePiece(currentPiece, currentX, currentY);
        }
        mainBoard = new BoardADT(width, height);
        grid = new Brick[width][height];
        eraseNextPiece();
        eraseVitrualPiece();
        start();
    }

    public int down(Timeline timer, SinglePlayer singlePlayer) throws InterruptedException {
        currentY--;
        int result = mainBoard.place(currentPiece, currentX, currentY);
        if (result < 1) {
            erasePiece(currentPiece, currentX, currentY + 1);
            eraseVitrualPiece();
            drawPiece(currentPiece, currentX, currentY);
            drawVitrualPiece(currentPiece, currentX, currentY);
        } else {
            currentY++;
            eraseVitrualPiece();
            computeRowFill(timer);
            if (mainBoard.getMaxHeight() > mainBoard.getHeight() - 4) {
                timer.stop();
                singlePlayer.gameOver();
            }
            nextBrick();
            drawVitrualPiece(currentPiece, currentX, currentY);
            return rowFilled;
        }
        return 0;
    }

    public int down(Timeline timer, Exhauted singlePlayer) throws InterruptedException {
        currentY--;
        int result = mainBoard.place(currentPiece, currentX, currentY);
        if (result < 1) {
            erasePiece(currentPiece, currentX, currentY + 1);
            eraseVitrualPiece();
            drawPiece(currentPiece, currentX, currentY);
            drawVitrualPiece(currentPiece, currentX, currentY);
        } else {
            currentY++;
            eraseVitrualPiece();
            computeRowFill(timer);
            if (mainBoard.getMaxHeight() > mainBoard.getHeight() - 4) {
                timer.stop();
                singlePlayer.gameOver();
            }
            nextBrick();
            drawVitrualPiece(currentPiece, currentX, currentY);
            return rowFilled;
        }
        return 0;
    }

    public int down(Timeline timer, TwoPlayer singlePlayer) throws InterruptedException {
        currentY--;
        int result = mainBoard.place(currentPiece, currentX, currentY);
        if (result < 1) {
            erasePiece(currentPiece, currentX, currentY + 1);
            eraseVitrualPiece();
            drawPiece(currentPiece, currentX, currentY);
            drawVitrualPiece(currentPiece, currentX, currentY);
        } else {
            currentY++;
            eraseVitrualPiece();
            computeRowFill(timer);
            if (mainBoard.getMaxHeight() > mainBoard.getHeight() - 4) {
                timer.stop();
                singlePlayer.gameOver();
            }
            nextBrick();
            drawVitrualPiece(currentPiece, currentX, currentY);
            return rowFilled;
        }
        return 0;
    }

    public void setVirtualPiece(boolean key) {
        virtual.setVisible(key);
    }

    public void setNextPiece(boolean key) {
        nextPieceGroup.setVisible(key);
    }

    public int getBase() {
        return base;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setName(String name) {
        nameLabel.setText(name);
    }

    public BoardADT getMainBoard() {
        return mainBoard;
    }

    public int getnCurrentPiece() {
        return nCurrentPiece;
    }

    public void setnCurrentPiece(int nCurrentPiece) {
        this.nCurrentPiece = nCurrentPiece;
    }

    public int getmCurrentPiece() {
        return mCurrentPiece;
    }

    public void setmCurrentPiece(int mCurrentPiece) {
        this.mCurrentPiece = mCurrentPiece;
    }

    public int getnNextPiece() {
        return nNextPiece;
    }

    public void setnNextPiece(int nNextPiece) {
        this.nNextPiece = nNextPiece;
    }

    public int getmNextPiece() {
        return mNextPiece;
    }

    public void setmNextPiece(int mNextPiece) {
        this.mNextPiece = mNextPiece;
    }

    public void setMainBoard(boolean[][] data) {
        this.mainBoard.setGrid(data);
    }

    public void setCurrentPiece() {
        currentPiece = pieces[nCurrentPiece];
        color = colors[nCurrentPiece];
        for (int j = 0; j < mCurrentPiece; j++) {
            currentPiece = currentPiece.fastRotation();
        }
        currentX = (width - currentPiece.getWidth()) / 2;
        currentY = height - 4;
    }

    public void setNextPiece() {
        nextPiece = pieces[nNextPiece];
        nextColor = colors[nNextPiece];
        for (int j = 0; j < mNextPiece; j++) {
            nextPiece = nextPiece.fastRotation();
        }
    }

}
