/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 *
 * @author SON
 */
public class MenuOptionItem extends VBox {

    private String color;
    private String font;
    private MenuItem menu;
    private OptionItem option;

    public MenuOptionItem(String name, String color, String font) {
        super(15);
        this.color = color;
        this.font = font;
        setAlignment(Pos.CENTER);
        menu = new MenuItem(name, color, font);
        option = new OptionItem(name, color, font, 17, 20);
        getChildren().addAll(menu, option);
        setActive(false);
        setOnActivate(() -> System.out.println(name + " activated"));
    }

    public void setLabelOption(String label) {
        this.option.setLabel(label);
    }

    public void setLabelMenu(String label) {
        this.option.setLabel(label);
    }

    public void setActive(boolean b) {
        menu.setActive(b);
        if (option != null) {
            option.setActive(b);
        }
    }

    public void setNullOption() {
        option = null;
    }

    public void setOnActivate(Runnable r) {
        menu.setOnActivate(r);
    }

    public void activate() {
        menu.activate();
    }
}
