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

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {


//        Pane p = new Pane();
//
//        Tile tile = TileBuilder.create()
//                .skinType(Tile.SkinType.BAR_GAUGE)
//                .minValue(0)
//                .maxValue(100)
//                .startFromZero(true)
//                .threshold(80)
//                .thresholdVisible(true)
//                .title("BarGauge Tile")
//                .unit("F")
//                .text("Whatever text")
//                .gradientStops(new Stop(0, Bright.BLUE),
//                        new Stop(0.1, Bright.BLUE_GREEN),
//                        new Stop(0.2, Bright.GREEN),
//                        new Stop(0.3, Bright.GREEN_YELLOW),
//                        new Stop(0.4, Bright.YELLOW),
//                        new Stop(0.5, Bright.YELLOW_ORANGE),
//                        new Stop(0.6, Bright.ORANGE),
//                        new Stop(0.7, Bright.ORANGE_RED),
//                        new Stop(0.8, Bright.RED),
//                        new Stop(1.0, Dark.RED))
//                .strokeWithGradient(true)
//                .animated(true)
//                .build();
//
//        p.getChildren().add(tile);
//
//        Scene sc2 = new Scene(p);
//        stage.setScene(sc2);
//        stage.show();



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

        //stage.setFullScreen(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}