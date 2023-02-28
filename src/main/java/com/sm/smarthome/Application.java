package com.sm.smarthome;

import com.sm.smarthome.Controllers.MainViewController;
import com.sm.smarthome.Core.Engine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {

        Engine engine = new Engine(stage);

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Views/MainView.fxml"));
        Parent parent = fxmlLoader.load();
        parent.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        MainViewController mainViewController = fxmlLoader.getController();
        engine.ControlersProvider.MainViewController = mainViewController;
        mainViewController.Initialize(engine);
        Scene scene = new Scene(parent, 1200, 800);
        engine.GuiService.JMetroThemeManager.setScene(scene);
        stage.setTitle("Smart Home App");
        stage.setScene(scene);
        stage.getIcons().add(new Image(Application.class.getResourceAsStream("Media/Icons/icon.png")));
        engine.GuiService.SetTheme(Style.DARK);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}