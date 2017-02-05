/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;

/**
 *
 * @author SON
 */
public class Brick extends Parent {

    private static int bigSize = 0;
    RadialGradient gradient1;
    RadialGradient gradient2;

    private Brick(Color color, double x, double y) {
        Color t = color;
        Color t1 = Color.WHITE;
        gradient1 = new RadialGradient(
                0, // focusAngle
                0, // focusDistance
                x + bigSize / 2, // centerX
                y + bigSize / 2, // centerY
                bigSize / 5, // radius
                false, // proportional
                CycleMethod.REFLECT, // cycleMethod
                new Stop(0, t1), // stops
                new Stop(.5, t)
        );
        gradient2 = new RadialGradient(
                0, // focusAngle
                0, // focusDistance
                x + bigSize / 2, // centerX
                y + bigSize / 2, // centerY
                bigSize / 4, // radius
                false, // proportional
                CycleMethod.NO_CYCLE, // cycleMethod
                new Stop(0, t1), // stops
                new Stop(.9, t)
        );
        Rectangle rec = new Rectangle(x, y, bigSize, bigSize);
        rec.setStroke(Color.WHITE);
        rec.setStrokeWidth(bigSize / 100);
        rec.setArcHeight(bigSize / 10);
        rec.setArcWidth(bigSize / 10);
        rec.setFill(gradient1);
        rec.setStrokeType(StrokeType.OUTSIDE);
//        Line x = new Line(0, 0, bigSize, bigSize);
//        x.setStrokeLineCap(StrokeLineCap.ROUND);
//        x.setStroke(Color.WHITE);
//        x.setStrokeWidth(bigSize / 35);
//        x.setRotate(90);
//        x.setOpacity(.5);
//        Line y = new Line(0, 0, bigSize, bigSize);
//        y.setStrokeLineCap(StrokeLineCap.ROUND);
//        y.setStroke(Color.WHITE);
//        y.setStrokeWidth(bigSize / 35);
//        y.setOpacity(.5);
        Rectangle recc = new Rectangle(x + bigSize / 4.5, y + bigSize / 4.5, bigSize * 5 / 9, bigSize * 5 / 9);
        recc.setFill(gradient2);
        recc.setStroke(Color.TRANSPARENT);
        recc.setArcHeight(bigSize / 10);
        recc.setArcWidth(bigSize / 10);
//        Line xx = new Line(bigSize / 4, bigSize / 4, bigSize * 3 / 4, bigSize * 3 / 4);
//        xx.setStroke(Color.WHITE);
//        xx.setStrokeWidth(bigSize / 35);
//        xx.setRotate(90);
//        xx.setOpacity(.5);
//        Line yy = new Line(bigSize / 4, bigSize / 4, bigSize * 3 / 4, bigSize * 3 / 4);
//        yy.setStroke(Color.WHITE);
//        yy.setStrokeWidth(bigSize / 35);
//        yy.setOpacity(.5);
        getChildren().addAll(rec, recc);
    }

    public static void setFactoryBrick(int bigSize) {
        Brick.bigSize = bigSize;
    }

    public static Brick createBrick(Color color, double x, double y) {
        Brick result = new Brick(color, x, y);
        return result;
    }

    public static Brick[] createArrayBrick(int numberElements, Color color, int x, int y) {
        Brick[] result = new Brick[numberElements];
        for (int i = 0; i < numberElements; i++) {
            result[i] = new Brick(color, x, y);
        }
        return result;
    }
}
