/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 *
 * @author SON
 */
public class OptionItem extends HBox {

    private String color;
    private ImageView c1;
    private ImageView c2;
    private Label label;
    private Runnable script;
    private String font;

    public OptionItem(String name, String color, String font, double sizeFont, double sizeArrow) {
        super(15);
        this.color = color;
        this.font = font;
        setAlignment(Pos.CENTER);
        label = new Label(name);
        label.setTextFill(Color.web(color, .8));
        label.setFont(Font.font(font, FontWeight.BOLD, sizeFont));
        label.setEffect(new GaussianBlur(2));
        c1 = TriAngle.createArrowImageView(sizeArrow);
        c1.setRotate(180);
        c2 = TriAngle.createArrowImageView(sizeArrow);
        getChildren().addAll(c1, label, c2);
        setActive(false);
        setOnActivate(() -> System.out.println(name + " activated"));
    }

    public void setLabel(String label) {
        this.label.setText(label);
    }

    public void setActive(boolean b) {
        setVisible(b);
    }

    public void setOnActivate(Runnable r) {
        script = r;
    }

    public void activate() {
        if (script != null) {
            script.run();
        }
    }

}
