package com.example.projectmerger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class Main extends Application {
    static Stage BaseStage;
    @Override
    public void start(Stage stage) throws IOException {
        BaseStage = stage;
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 810, 622);
        stage.setTitle("file merger!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
      launch();
    }
}