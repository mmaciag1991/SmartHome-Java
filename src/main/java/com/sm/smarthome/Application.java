package com.sm.smarthome;

import com.sm.smarthome.Core.Engine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("Smart Home App");
        stage.setScene(scene);

        Engine engine = new Engine(stage);
        MainViewController mainViewController = fxmlLoader.getController();
        mainViewController.setEngine(engine);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}