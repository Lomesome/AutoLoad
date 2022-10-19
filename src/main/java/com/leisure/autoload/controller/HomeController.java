package com.leisure.autoload.controller;

import com.leisure.autoload.App;
import com.leisure.autoload.utils.AutoLoader;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * @author leisure
 * @version 1.0.0
 * @since 2022/10/16 16:02
 */
public class HomeController {
    public static final String TITLE = "JavaFx Fxml 热加载 Demo";
    public static final double WIDTH = 480;
    public static final double HEIGHT = 480;

    public void initialize() {
        App.stage.setTitle(TITLE);
    }

    @FXML
    private void onClickHomeBtn() {
        System.out.println("click home!");
    }

    @FXML
    private void onClickOtherBtn() throws IOException {
        AutoLoader.loadScene("pages/otherView", App.WIDTH, App.HEIGHT);
    }
}
