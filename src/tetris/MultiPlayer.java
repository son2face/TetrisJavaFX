/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.util.Vector;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author SON
 */
public class MultiPlayer extends Menu {

    Scene mainMenu;

    public static String[] list = {"NOMAL", "EXHAUTED", "BACK"};
    public static String[] bgString = {"bg.jpg", "bg3.jpg", "bg3.jpg", "bg3.jpg", "bg3.jpg", "bg3.jpg"};

    public MultiPlayer(Stage primaryStage, String backgroundUrl, String label, String color, String font) {
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
            System.out.println("NOMAL");
        });
        role.add(() -> {
            System.out.println("EXHAUTED");
        });
        role.add(() -> {
            primaryStage.setScene(mainMenu);
        });
        setRule(role);
    }

    public void animation() {

    }
}
