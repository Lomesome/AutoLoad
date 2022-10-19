package com.leisure.autoload.controller;

import com.leisure.autoload.App;
import com.leisure.autoload.utils.AutoLoader;
import javafx.fxml.FXML;

import java.io.IOException;


/**
 * @author leisure
 * @version 1.0.0
 * @since 2022/10/16 16:21
 */
public class OtherController {

    private static final String TITLE = "JavaFx Fxml 热加载 Demo";

    @FXML
    private void initialize() {
        App.stage.setTitle(TITLE);
    }

    @FXML
    public void onClickBack() throws IOException {
        AutoLoader.loadScene("homeView", App.WIDTH, App.HEIGHT);
    }
}
