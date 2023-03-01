package com.sm.smarthome.Core.Providers.Gui;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.CustomControls.HanSolo.TimeControl.TimeControl;
import eu.hansolo.tilesfx.*;
import eu.hansolo.tilesfx.chart.ChartData;
import eu.hansolo.tilesfx.chart.TilesFXSeries;
import eu.hansolo.tilesfx.colors.Bright;
import eu.hansolo.tilesfx.colors.ColorSkin;
import eu.hansolo.tilesfx.colors.Dark;
import eu.hansolo.tilesfx.tools.Helper;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.TextAlignment;

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
                .activeColor(engine.GuiService.AccentColor.getValue())
                .padding(insets)
                .build();

        tile.setOnSwitchPressed(e -> valueOut.setValue(true));
        tile.setOnSwitchReleased(e -> valueOut.setValue(false));

        text.addListener((observableValue, s, t1) -> tile.setText(t1));
        description.addListener((observableValue, s, t1) -> tile.setText(t1));
        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setActiveColor(newValue)));

        return tile;
    }
    public Tile geCalendarTile(List<ChartData> calendarData){

        Tile tile = TileBuilder.create()
                .skinType(Tile.SkinType.CALENDAR)
                .padding(insets)
                .chartData(calendarData)
                .locale(engine.SystemService.Language.Locale)
                .trackColor(Tile.TileColor.LIGHT_GREEN)
                .build();

        return tile;
    }
    public Tile getTimeControlTile(SimpleObjectProperty<Duration> duration){

        TimeControl timeControl = new TimeControl();
        timeControl.setBarColor(engine.GuiService.AccentColor.getValue());
        timeControl.setBackgroundColor(Color.TRANSPARENT);
        timeControl.setStartTime(LocalTime.of(0, 0));
        timeControl.setStopTime(LocalTime.of(15, 0));


        Tile tile = TileBuilder.create()
                .skinType(Tile.SkinType.CUSTOM)
                .graphic(timeControl)
                .padding(insets)
                .build();


        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> timeControl.setBarColor(newValue)));
        timeControl.durationProperty().addListener(o -> {
            System.out.println(timeControl.getDuration());
            duration.set(timeControl.getDuration());
        });


        return tile;
    }
    public Tile getFluidTile(String title, String text, String unit, SimpleDoubleProperty value, double threshold){

        Tile tile = TileBuilder.create().skinType(Tile.SkinType.FLUID)
                .title(title)
                .text(text)
                .unit(unit)
                .barColor(engine.GuiService.AccentColor.getValue())
                .threshold(threshold) // triggers the fire and smoke effect
                .value(value.doubleValue())
                .decimals(0)
                .animated(true)
                .padding(insets)
                .build();
        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setBarColor(newValue)));
        value.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setValue(newValue.doubleValue())));

        return tile;
    }

    public Tile getTimerControlTile(String title, String text, SimpleBooleanProperty running){

        Tile tile = TileBuilder.create()
                .skinType(Tile.SkinType.TIMER_CONTROL)
                .title(title)
                .text(text)
                .barColor(engine.GuiService.AccentColor.getValue())
                .secondsVisible(true)
                .dateVisible(true)
                .padding(insets)
                .build();

        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setBarColor(newValue)));
        running.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setRunning(newValue)));

        return tile;
    }
    public Tile getTimerCountDownTile(String title, String text, SimpleStringProperty description, SimpleObjectProperty<Duration> duration,SimpleBooleanProperty running){

        Tile tile = TileBuilder.create()
                .skinType(Tile.SkinType.COUNTDOWN_TIMER)
                .title(title)
                .description(description.getValue())
                .text(text)
                .barColor(engine.GuiService.AccentColor.getValue())
                .timePeriod(duration.getValue())
                .running(running.getValue())
                .padding(insets)
                .build();

        tile.setOnAlarmEvt(e -> tile.setBarColor(Color.FIREBRICK));

        description.addListener((observableValue, s, t1) -> tile.setDescription(t1));
        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setBarColor(newValue)));
        running.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            tile.setTimePeriod(duration.getValue());
            tile.setRunning(newValue);
        }));

        return tile;
    }
    public Tile getRadialDistributionTile(String title, String text, SimpleStringProperty description, SimpleDoubleProperty minValue, SimpleDoubleProperty maxValue, SimpleDoubleProperty lowerThreshold, SimpleDoubleProperty threshold, List<ChartData> glucoseData){

        Tile tile = TileBuilder.create()
                .skinType(Tile.SkinType.RADIAL_DISTRIBUTION)
                .title(title)
                .text(text)
                .description("Description")
                .minValue(minValue.doubleValue())
                .maxValue(maxValue.doubleValue())
                .lowerThreshold(lowerThreshold.doubleValue())
                .threshold(threshold.doubleValue())
                .tickLabelDecimals(0)
                .decimals(0)
                .padding(insets)
                .chartData(glucoseData)
                .barColor(engine.GuiService.AccentColor.getValue())
                .gradientStops(new Stop(0, Helper.getColorWithOpacity(Color.RED, 0.1)),
                        new Stop(0.1375, Helper.getColorWithOpacity(Color.RED, 0.1)),
                        new Stop(0.15625, Helper.getColorWithOpacity(Color.web("#FA711F"), 0.1)),
                        new Stop(0.175, Helper.getColorWithOpacity(ColorSkin.GREEN, 0.1)),
                        new Stop(0.2625, Helper.getColorWithOpacity(ColorSkin.GREEN, 0.1)),
                        new Stop(0.35, Helper.getColorWithOpacity(ColorSkin.GREEN, 0.1)),
                        new Stop(0.3501, Helper.getColorWithOpacity(ColorSkin.YELLOW, 0.1)),
                        new Stop(0.45, Helper.getColorWithOpacity(Color.web("#FA711F"), 0.1)),
                        new Stop(0.6625, Helper.getColorWithOpacity(Color.web("#FA711F"), 0.1)),
                        new Stop(0.875, Helper.getColorWithOpacity(Color.RED, 0.1)),
                        new Stop(1.0, Helper.getColorWithOpacity(Color.RED, 0.1)))
                .strokeWithGradient(true)
                .build();

        description.addListener((observableValue, s, t1) -> tile.setDescription(t1));
        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setBarColor(newValue)));
        minValue.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setMinValue(newValue.doubleValue())));
        maxValue.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setMaxValue(newValue.doubleValue())));
        lowerThreshold.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setLowerThreshold(newValue.doubleValue())));
        threshold.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setThreshold(newValue.doubleValue())));

        return tile;
    }
    public Tile getTimelineTile(String title, String unit, SimpleDoubleProperty minValue, SimpleDoubleProperty maxValue, SimpleDoubleProperty lowerThreshold, SimpleDoubleProperty threshold, SimpleObjectProperty<ChartData> chartData){

        Tile tile = TileBuilder.create()
                .skinType(Tile.SkinType.TIMELINE)
                .title(title)
                .unit(unit)
                .minValue(minValue.doubleValue())
                .maxValue(maxValue.doubleValue())
                .smoothing(true)
                .lowerThreshold(lowerThreshold.doubleValue())
                .lowerThresholdColor(Tile.TileColor.RED.color)
                .threshold(threshold.doubleValue())
                .thresholdColor(Tile.TileColor.RED.color)
                .thresholdVisible(true)
                .tickLabelColor(Helper.getColorWithOpacity(Tile.FOREGROUND, 0.5))
                .sections(new Section(0, 70, "Low", Helper.getColorWithOpacity(Dark.RED, 0.1)),
                        new Section(70, 140, "Ok", Helper.getColorWithOpacity(Bright.GREEN, 0.15)),
                        new Section(140, 350, "High", Helper.getColorWithOpacity(Dark.RED, 0.1)))
                .highlightSections(true)
                .sectionsVisible(true)
                .textAlignment(TextAlignment.CENTER)
                .timePeriod(java.time.Duration.ofMinutes(1))
                .maxTimePeriod(java.time.Duration.ofMinutes(1))
                .timePeriodResolution(TimeUnit.SECONDS)
                .numberOfValuesForTrendCalculation(5)
                .trendVisible(true)
                .maxTimePeriod(java.time.Duration.ofSeconds(60))
                .gradientStops(new Stop(0, Dark.RED),
                        new Stop(0.15, Dark.RED),
                        new Stop(0.2, Bright.YELLOW_ORANGE),
                        new Stop(0.25, Bright.GREEN),
                        new Stop(0.3, Bright.GREEN),
                        new Stop(0.35, Bright.GREEN),
                        new Stop(0.45, Bright.YELLOW_ORANGE),
                        new Stop(0.5, Bright.ORANGE),
                        new Stop(0.685, Dark.RED),
                        new Stop(1.0, Dark.RED))
                .strokeWithGradient(true)
                .averageVisible(true)
                .averagingPeriod(60)
                .timeoutMs(60000)
                .build();

        chartData.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.addChartData(newValue)));

        return tile;
    }
    public Tile geTile(String title, String unit, SimpleDoubleProperty value){

        Tile tile = null;

        value.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setValue(newValue.doubleValue())));

        return tile;
    }
}
