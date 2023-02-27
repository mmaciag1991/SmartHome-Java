package com.sm.smarthome.Core.Providers.Gui;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.sm.smarthome.Core.Engine;
import eu.hansolo.tilesfx.*;
import eu.hansolo.tilesfx.chart.ChartData;
import eu.hansolo.tilesfx.chart.TilesFXSeries;
import eu.hansolo.tilesfx.tools.Helper;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class TilesProvider {
    private Engine engine;
    private Insets insets = new Insets(4);
    public TilesProvider(Engine engine){
        this.engine = engine;
    }

    public Tile getClockTile() {
        Tile clockTile = TileBuilder.create()
                .skinType(Tile.SkinType.CLOCK)
                .title("Clock Tile")
                .text("Whatever text")
                .dateVisible(true)
                .locale(engine.SystemService.Language.Locale)
                .running(true)
                .build();
        clockTile.setPadding(new Insets(4));
        return clockTile;
    }

    public Tile getProcentageTile(String title, SimpleStringProperty description, SimpleDoubleProperty max, SimpleDoubleProperty value){

        Tile tile = TileBuilder.create()
                .skinType(Tile.SkinType.PERCENTAGE)
                .title(title)
                .unit(Helper.PERCENTAGE)
                .description(description.getValue())
                .maxValue(max.doubleValue())
                .value(value.doubleValue())
                .padding(insets)
                .barColor(engine.GuiService.AccentColor.getValue())
                .build();

        value.addListener((observable, oldValue, newValue) -> tile.setValue(newValue.doubleValue()));
        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> tile.setBarColor(newValue));
        max.addListener((observableValue, number, t1) -> tile.setMaxValue(t1.doubleValue()));
        description.addListener((observableValue, s, t1) -> tile.setDescription(t1));


        return tile;
    }

    public Tile getGuageTile(String title, String unit, SimpleDoubleProperty threshold, SimpleDoubleProperty value){
        Tile gaugeTile = TileBuilder.create()
                .skinType(Tile.SkinType.GAUGE)
                .title(title)
                .unit(unit)
                .threshold(threshold.doubleValue())
                .value(value.doubleValue())
                .thresholdColor(Color.RED)
                .barColor(engine.GuiService.AccentColor.getValue())
                .padding(insets)
                .build();

        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> gaugeTile.setBarColor(newValue));
        threshold.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> gaugeTile.setThreshold(newValue.doubleValue())));
        value.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> gaugeTile.setValue(newValue.doubleValue())));

        return gaugeTile;
    }
    public Tile geSparkLineTile(String title, String unit, SimpleDoubleProperty value){

        Tile tile = TileBuilder.create()
                .skinType(Tile.SkinType.SPARK_LINE)
                .title(title)
                .unit(unit)
                .gradientStops(new Stop(0, Tile.GREEN),
                new Stop(0.5, Tile.YELLOW),
                new Stop(1.0, Tile.RED))
                .padding(insets)
                .strokeWithGradient(true)
                .build();

        value.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setValue(newValue.doubleValue())));

        return tile;
    }
    public Tile getPlusMinusTile(String title, String unit, SimpleStringProperty text, SimpleStringProperty description, SimpleDoubleProperty min, SimpleDoubleProperty max, SimpleDoubleProperty valueOut){

        Tile tile = TileBuilder.create()
                .skinType(Tile.SkinType.PLUS_MINUS)
                .maxValue(max.doubleValue())
                .minValue(min.doubleValue())
                .title(title)
                .text(text.getValue())
                .description(description.getValue())
                .padding(insets)
                .unit(unit)
                .valueColor(engine.GuiService.AccentColor.getValue())
                .unitColor(engine.GuiService.AccentColor.getValue())
                .value(valueOut.doubleValue())
                .build();

        tile.valueProperty().addListener((observableValue, number, t1) -> valueOut.setValue(t1.doubleValue()));

        min.addListener((observableValue, number, t1) -> tile.setMinValue(t1.doubleValue()));
        max.addListener((observableValue, number, t1) -> tile.setMaxValue(t1.doubleValue()));
        text.addListener((observableValue, s, t1) -> tile.setText(t1));
        description.addListener((observableValue, s, t1) -> tile.setText(t1));

        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            tile.setUnitColor(newValue);
            tile.setValueColor(newValue);
        }));

        return tile;
    }
    public Tile getSliderTile (String title, String unit, SimpleStringProperty text, SimpleStringProperty description, SimpleDoubleProperty valueOut){


        Tile tile = TileBuilder.create()
                .skinType(Tile.SkinType.SLIDER)
                .title(title)
                .text(text.getValue())
                .description(description.getValue())
                .unit(unit)
                .barColor(engine.GuiService.AccentColor.getValue())
                .value(valueOut.doubleValue())
                .padding(insets)
                .build();

        tile.valueProperty().addListener((observableValue, number, t1) -> valueOut.setValue(t1.doubleValue()));
        text.addListener((observableValue, s, t1) -> tile.setText(t1));
        description.addListener((observableValue, s, t1) -> tile.setText(t1));
        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setBarColor(newValue)));

        return tile;
    }
    public Tile getSwitchTile  (String title, SimpleStringProperty text, SimpleStringProperty description, SimpleBooleanProperty valueOut){


        Tile tile = TileBuilder.create()
                .skinType(Tile.SkinType.SWITCH)
                .title(title)
                .text(text.getValue())
                .description(description.getValue())
                .padding(insets)
                .build();

        tile.setOnSwitchPressed(e -> valueOut.setValue(true));
        tile.setOnSwitchReleased(e -> valueOut.setValue(false));

        text.addListener((observableValue, s, t1) -> tile.setText(t1));
        description.addListener((observableValue, s, t1) -> tile.setText(t1));
        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setBarColor(newValue)));

        return tile;
    }
    public Tile geCalendarTile(List<ChartData> calendarData){

        Tile tile = TileBuilder.create()
                .skinType(Tile.SkinType.CALENDAR)
                .padding(insets)
                .chartData(calendarData)
                .locale(engine.SystemService.Language.Locale)
                .build();

        return tile;
    }
    public Tile geTile(String title, String unit, SimpleDoubleProperty value){

        Tile tile = null;

        value.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setValue(newValue.doubleValue())));

        return tile;
    }
}
