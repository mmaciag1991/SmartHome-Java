package com.sm.smarthome;

import com.sm.smarthome.Controllers.MainViewController;
import com.sm.smarthome.Core.Engine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        Engine engine = new Engine(stage);

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Views/MainView.fxml"));
        Parent parent = fxmlLoader.load();
        parent.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        MainViewController mainViewController = fxmlLoader.getController();
        engine.ControlersProvider.mainViewController = mainViewController;
        mainViewController.Initialize(engine);
        Scene scene = new Scene(parent, 1200, 800);
        engine.GuiProvider.JMetroThemeManager.setScene(scene);
        stage.setTitle("Smart Home App");
        stage.setScene(scene);
        stage.getIcons().add(new Image(Application.class.getResourceAsStream("Media/Icons/icon.png")));
        engine.GuiProvider.SetTheme(Style.DARK);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}