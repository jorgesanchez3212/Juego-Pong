package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var root = new Vista();
        var scene = new Scene(root,500,500);
        var stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}