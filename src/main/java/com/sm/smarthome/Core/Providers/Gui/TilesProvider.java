package com.sm.smarthome.Core.Providers.Gui;

import java.util.concurrent.atomic.AtomicReference;

import com.sm.smarthome.Core.Engine;
import eu.hansolo.tilesfx.*;
import eu.hansolo.tilesfx.chart.TilesFXSeries;
import eu.hansolo.tilesfx.tools.Helper;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
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

    public Tile getProcentageTile(String title, String description, double maxValue, SimpleDoubleProperty value){

        Tile tile = TileBuilder.create()
                .skinType(Tile.SkinType.PERCENTAGE)
                .title(title)
                .unit(Helper.PERCENTAGE)
                .description(description)
                .maxValue(maxValue)
                .value(value.doubleValue())
                .padding(insets)
                .barColor(engine.GuiService.AccentColor.getValue())
                .build();

        value.addListener((observable, oldValue, newValue) -> tile.setValue(newValue.doubleValue()));
        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> tile.setBarColor(newValue));

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
    public Tile geSmoothedChartTile(String title, XYChart.Series<String, Number> series){


        Tile tile = TileBuilder.create()
                .skinType(Tile.SkinType.SMOOTHED_CHART)
                .title(title)
                .chartType(Tile.ChartType.AREA)
                .barColor(Color.GOLD)
                .animated(true)
                .smoothing(true)
                .padding(insets)
                .tooltipTimeout(1000)
                .tilesFxSeries(new TilesFXSeries<>(series,
                        Tile.DARK_BLUE,
                        new LinearGradient(0, 0, 0, 1,
                                true, CycleMethod.NO_CYCLE,
                                new Stop(0, Color.BLUE),
                                new Stop(1, Color.YELLOW))))
                .build();

        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setValueColor(newValue)));

        return tile;
    }
    public Tile geTile(String title, String unit, SimpleDoubleProperty value){

        Tile tile = null;

        value.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setValue(newValue.doubleValue())));

        return tile;
    }
}
