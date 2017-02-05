/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import javafx.scene.Parent;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 *
 * @author SON
 */
public class TriCircle extends Parent {

    public TriCircle() {
        Shape shape1 = Shape.subtract(new Circle(5), new Circle(2));
        shape1.setFill(Color.WHITE);
        Shape shape2 = Shape.subtract(new Circle(5), new Circle(2));
        shape2.setFill(Color.WHITE);
        shape2.setTranslateX(5);
        Shape shape3 = Shape.subtract(new Circle(5), new Circle(2));
        shape3.setFill(Color.WHITE);
        shape3.setTranslateX(2.5);
        shape3.setTranslateY(-5);
        getChildren().addAll(shape1, shape2, shape3);
        setEffect(new GaussianBlur(2));
    }
}
