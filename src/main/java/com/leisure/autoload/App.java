package com.leisure.autoload;

import com.leisure.autoload.controller.HomeController;
import com.leisure.autoload.utils.AutoLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.*;

/**
 * @author leisure
 * @version 1.0.0
 * @since 2022/10/16 15:58
 */
public class App extends Application {

    public static final double WIDTH = 480;
    public static final double HEIGHT = 480;
    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        AutoLoader.loadScene("homeView", HomeController.WIDTH, HomeController.HEIGHT);
        stage.setOnCloseRequest(windowEvent -> {
            System.exit(0);
        });

        stage.show();
    }
}
