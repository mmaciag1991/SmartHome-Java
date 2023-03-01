package com.sm.smarthome;

import com.sm.smarthome.Controllers.MainViewController;
import com.sm.smarthome.Core.Engine;
import eu.hansolo.tilesfx.Section;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.colors.Bright;
import eu.hansolo.tilesfx.colors.Dark;
import eu.hansolo.tilesfx.tools.Helper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Stop;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {


//        Pane p = new Pane();
//
//        Tile tile = TileBuilder.create()
//                .skinType(Tile.SkinType.TIMELINE)
//                .title("Timeline Tile")
//                .unit("mg/dl")
//                .minValue(0)
//                .maxValue(350)
//                .smoothing(true)
//                .lowerThreshold(70)
//                .lowerThresholdColor(Tile.TileColor.RED.color)
//                .threshold(240)
//                .thresholdColor(Tile.TileColor.RED.color)
//                .thresholdVisible(true)
//                .tickLabelColor(Helper.getColorWithOpacity(Tile.FOREGROUND, 0.5))
//                .sections(new Section(0, 70, "Low", Helper.getColorWithOpacity(Dark.RED, 0.1)),
//                        new Section(70, 140, "Ok", Helper.getColorWithOpacity(Bright.GREEN, 0.15)),
//                        new Section(140, 350, "High", Helper.getColorWithOpacity(Dark.RED, 0.1)))
//                .highlightSections(true)
//                .sectionsVisible(true)
//                .textAlignment(TextAlignment.CENTER)
//                .timePeriod(java.time.Duration.ofMinutes(1))
//                .maxTimePeriod(java.time.Duration.ofMinutes(1))
//                .timePeriodResolution(TimeUnit.SECONDS)
//                .numberOfValuesForTrendCalculation(5)
//                .trendVisible(true)
//                .maxTimePeriod(java.time.Duration.ofSeconds(60))
//                .gradientStops(new Stop(0, Dark.RED),
//                        new Stop(0.15, Dark.RED),
//                        new Stop(0.2, Bright.YELLOW_ORANGE),
//                        new Stop(0.25, Bright.GREEN),
//                        new Stop(0.3, Bright.GREEN),
//                        new Stop(0.35, Bright.GREEN),
//                        new Stop(0.45, Bright.YELLOW_ORANGE),
//                        new Stop(0.5, Bright.ORANGE),
//                        new Stop(0.685, Dark.RED),
//                        new Stop(1.0, Dark.RED))
//                .strokeWithGradient(true)
//                .averageVisible(true)
//                .averagingPeriod(60)
//                .timeoutMs(60000)
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