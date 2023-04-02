package com.sm.smarthome;

import com.sm.smarthome.Controllers.MainViewController;
import com.sm.smarthome.Core.Engine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetroStyleClass;
import java.io.IOException;
import static com.sm.smarthome.Core.Utils.Helpers.AppHeight;
import static com.sm.smarthome.Core.Utils.Helpers.AppWidth;

public class Application extends javafx.application.Application {

    //--add-exports
    //javafx.graphics/com.sun.javafx.scene=com.jfoenix
    //--add-exports
    //javafx.controls/com.sun.javafx.scene.control.behavior=com.jfoenix
    //--add-exports
    //javafx.controls/com.sun.javafx.scene.control=com.jfoenix
    //--add-exports
    //javafx.base/com.sun.javafx.binding=com.jfoenix
    //--add-exports
    //javafx.base/com.sun.javafx.event=com.jfoenix
    //--add-exports
    //javafx.graphics/com.sun.javafx.stage=com.jfoenix
    //--add-opens=java.base/java.lang.reflect=com.jfoenix
    //-Dcom.sun.javafx.isEmbedded=true
    //-Dcom.sun.javafx.touch=true
    //-Dprism.order=j2d

    @Override
    public void start(Stage stage) throws IOException {


        Engine engine = new Engine(stage);

        while (!engine.isInitialized){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Views/MainView.fxml"));
        Parent parent = fxmlLoader.load();
        parent.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        MainViewController mainViewController = fxmlLoader.getController();
        engine.ControlersProvider.MainViewController = mainViewController;
        mainViewController.Initialize(engine);
        //Scene scene = new Scene(parent, 1200, 800);
        engine.GuiService.KeyboardPane.setContent(parent);
        Scene scene = new Scene(engine.GuiService.KeyboardPane, AppWidth, AppHeight);
        scene.setFill(Color.BLACK);

        engine.GuiService.JMetroThemeManager.setScene(scene);
        stage.setTitle("Smart Home App");
        stage.setScene(scene);
        stage.getIcons().add(new Image(Application.class.getResourceAsStream("Media/Icons/icon.png")));
        //engine.GuiService.SetTheme(Style.DARK);

        //stage.setFullScreen(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        engine.isInitialized = true;


    }


    public static void main(String[] args) {
        launch();
    }
}