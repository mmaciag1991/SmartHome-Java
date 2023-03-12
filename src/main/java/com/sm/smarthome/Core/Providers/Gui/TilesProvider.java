package com.sm.smarthome.Core.Providers.Gui;

import java.text.DecimalFormat;
import java.time.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.CustomControls.HanSolo.TimeControl.TimeControl;
import eu.hansolo.tilesfx.*;
import eu.hansolo.tilesfx.chart.ChartData;
import eu.hansolo.tilesfx.colors.Bright;
import eu.hansolo.tilesfx.colors.ColorSkin;
import eu.hansolo.tilesfx.colors.Dark;
import eu.hansolo.tilesfx.skins.BarChartItem;
import eu.hansolo.tilesfx.tools.Helper;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfxweather.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
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
                .skinType(SkinType.CLOCK)
                .title("Clock Tile")
                .text("Whatever text")
                .dateVisible(true)
                .running(true)
                .build();
        clockTile.setPadding(new Insets(4));
        engine.SystemService.Language.Locale.addListener((observableValue, locale, t1) -> clockTile.setLocale(t1));
        return clockTile;
    }

    public Tile getProcentageTile(String title, SimpleStringProperty description, SimpleDoubleProperty max, SimpleDoubleProperty value){

        Tile tile = TileBuilder.create()
                .skinType(SkinType.PERCENTAGE)
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
                .skinType(SkinType.GAUGE)
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
                .skinType(SkinType.SPARK_LINE)
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
                .skinType(SkinType.PLUS_MINUS)
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
                .skinType(SkinType.SLIDER)
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
                .skinType(SkinType.SWITCH)
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
    public Tile geCalendarTile(SimpleObjectProperty<List<ChartData>> calendarData){

        Tile tile = TileBuilder.create()
                .skinType(SkinType.CALENDAR)
                .padding(insets)
                .chartData(calendarData.get())
                .locale(engine.SystemService.Language.Locale.get())
                .trackColor(Tile.TileColor.LIGHT_GREEN)
                .build();
        engine.SystemService.Language.Locale.addListener((observableValue, locale, t1) -> tile.setLocale(t1));
        calendarData.addListener((observableValue, chartData, t1) -> {
            Platform.runLater(() -> {
                tile.setChartData(t1);
            });
        });
        return tile;
    }
    public Tile getTimeControlTile(SimpleObjectProperty<Duration> duration){

        TimeControl timeControl = new TimeControl();
        timeControl.setBarColor(engine.GuiService.AccentColor.getValue());
        timeControl.setBackgroundColor(Color.TRANSPARENT);
        timeControl.setStartTime(LocalTime.of(0, 0));
        timeControl.setStopTime(LocalTime.of(15, 0));


        Tile tile = TileBuilder.create()
                .skinType(SkinType.CUSTOM)
                .graphic(timeControl)
                .padding(insets)
                .build();


        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> timeControl.setBarColor(newValue)));
        timeControl.durationProperty().addListener(o -> {
            duration.set(timeControl.getDuration());
        });


        return tile;
    }
    public Tile getFluidTile(String title, String text, String unit, SimpleDoubleProperty value, double threshold){

        Tile tile = TileBuilder.create().skinType(SkinType.FLUID)
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
                .skinType(SkinType.TIMER_CONTROL)
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
    public Tile getTimerCountDownTile(String title, String text, SimpleStringProperty description, SimpleObjectProperty<Duration> duration, SimpleBooleanProperty running){

        Tile tile = TileBuilder.create()
                .skinType(SkinType.COUNTDOWN_TIMER)
                .title(title)
                .description(description.getValue())
                .text(text)
                .barColor(engine.GuiService.AccentColor.getValue())
                .timePeriod(duration.getValue())
                .running(running.getValue())
                .padding(insets)
                .build();

        tile.setOnAlarmEvt(e -> {

//            new Thread(() -> {
//                Color tempColor = tile.getBarBackgroundColor();
//                for (int i = 0; i < 10; i++) {
//                    tile.setBackgroundColor(Color.FIREBRICK);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException ex) {
//                        throw new RuntimeException(ex);
//                    }
//                    tile.setBackgroundColor(tempColor);
//                }
//            }).start();
        });

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
                .skinType(SkinType.RADIAL_DISTRIBUTION)
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
                .skinType(SkinType.TIMELINE)
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
                .padding(insets)
                .build();

        chartData.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.addChartData(newValue)));
        minValue.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setMinValue(newValue.doubleValue())));
        maxValue.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setMaxValue(newValue.doubleValue())));
        lowerThreshold.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setLowerThreshold(newValue.doubleValue())));
        threshold.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setThreshold(newValue.doubleValue())));

        return tile;
    }
    public Tile getBarGaugeTile(String title, String text, String unit, SimpleDoubleProperty value, SimpleDoubleProperty minValue, SimpleDoubleProperty maxValue, SimpleDoubleProperty threshold){

        Tile tile = TileBuilder.create()
                .skinType(SkinType.BAR_GAUGE)
                .minValue(minValue.doubleValue())
                .maxValue(maxValue.doubleValue())
                .startFromZero(true)
                .threshold(threshold.doubleValue())
                .thresholdVisible(true)
                .value(value.doubleValue())
                .title(title)
                .unit(unit)
                .text(text)
                .gradientStops(new Stop(0, Bright.BLUE),
                        new Stop(0.1, Bright.BLUE_GREEN),
                        new Stop(0.2, Bright.GREEN),
                        new Stop(0.3, Bright.GREEN_YELLOW),
                        new Stop(0.4, Bright.YELLOW),
                        new Stop(0.5, Bright.YELLOW_ORANGE),
                        new Stop(0.6, Bright.ORANGE),
                        new Stop(0.7, Bright.ORANGE_RED),
                        new Stop(0.8, Bright.RED),
                        new Stop(1.0, Dark.RED))
                .strokeWithGradient(true)
                .animated(true)
                .padding(insets)
                .build();

        value.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setValue(newValue.doubleValue())));
        minValue.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setMinValue(newValue.doubleValue())));
        maxValue.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setMaxValue(newValue.doubleValue())));
        threshold.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setThreshold(newValue.doubleValue())));

        return tile;
    }
    public Tile getRadialPercentageTile(String title, String description, SimpleObjectProperty<List<ChartData>> chartData){

        Tile tile = TileBuilder.create().skinType(SkinType.RADIAL_PERCENTAGE)
                .maxValue(1000)
                .title(title)
                .description(description)
                .textVisible(false)
                .chartData(chartData.get())
                .animated(true)
                .referenceValue(100)
                .value(chartData.getValue().get(0).getValue())
                .descriptionColor(Tile.GRAY)
                .barColor(engine.GuiService.AccentColor.getValue())
                .decimals(0)
                .padding(insets)
                .build();

        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setBarColor(newValue)));
        chartData.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                    tile.setChartData(newValue);
                    tile.setValue(newValue.get(0).getValue());
                }));

        return tile;
    }
    public Tile getDateTile(){

        Tile tile = TileBuilder.create()
                .skinType(SkinType.DATE)
                .padding(insets)
                .build();

        return tile;
    }
    public Tile getFlipTile(SimpleStringProperty flipText, int flipTime, String[] characters){

        Tile tile = TileBuilder.create().skinType(SkinType.FLIP)
                .characters(characters)
                .flipTimeInMS(flipTime)
                .flipText(flipText.getValue())
                .padding(insets)
                .build();

        flipText.addListener((observableValue, s, t1) -> Platform.runLater(() -> tile.setFlipText(t1)));

        return tile;
    }
    public Tile getSmoothAreaChartTile(String title, String unit, String text, SimpleDoubleProperty minValue, SimpleDoubleProperty maxValue, SimpleObjectProperty<List<ChartData>> chartData){

        Tile tile = TileBuilder.create().skinType(SkinType.SMOOTH_AREA_CHART)
                .minValue(minValue.doubleValue())
                .maxValue(maxValue.doubleValue())
                .title(title)
                .unit(unit)
                .text(text)
                //.chartType(ChartType.LINE)
                //.dataPointsVisible(true)
                .chartData(chartData.get())
                .tooltipText("")
                .animated(true)
                .padding(insets)
                .build();

        chartData.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setChartData(newValue)));
        minValue.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setMinValue(newValue.doubleValue())));
        maxValue.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setMaxValue(newValue.doubleValue())));

        return tile;
    }
    public Tile getSparkLineTile(String title, String unit, SimpleDoubleProperty value){

        Tile tile = TileBuilder.create()
                .skinType(SkinType.GAUGE_SPARK_LINE)
                .title(title)
                .unit(unit)
                .animated(true)
                .textVisible(false)
                .averagingPeriod(25)
                .autoReferenceValue(true)
                .barColor(engine.GuiService.AccentColor.getValue())
                .barBackgroundColor(Color.rgb(255, 255, 255, 0.1))
                .sections(new eu.hansolo.tilesfx.Section(0, 33, Tile.LIGHT_GREEN),
                        new eu.hansolo.tilesfx.Section(33, 67, Tile.YELLOW),
                        new eu.hansolo.tilesfx.Section(67, 100, Tile.LIGHT_RED))
                .sectionsVisible(true)
                .highlightSections(true)
                .strokeWithGradient(true)
                .fixedYScale(true)
                .gradientStops(new Stop(0.0, Tile.LIGHT_GREEN),
                        new Stop(0.33, Tile.LIGHT_GREEN),
                        new Stop(0.33,Tile.YELLOW),
                        new Stop(0.67, Tile.YELLOW),
                        new Stop(0.67, Tile.LIGHT_RED),
                        new Stop(1.0, Tile.LIGHT_RED))
                .padding(insets)
                .build();

        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setBarColor(newValue)));
        value.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setValue(newValue.doubleValue())));

        return tile;
    }
    public Tile getStockTile(String title, SimpleDoubleProperty minValue, SimpleDoubleProperty maxValue, SimpleDoubleProperty value){

        Tile tile = TileBuilder.create()
                .skinType(SkinType.STOCK)
                .title(title)
                .minValue(minValue.doubleValue())
                .maxValue(maxValue.doubleValue())
                .averagingPeriod(100)
                .value(value.doubleValue())
                .padding(insets)
                .build();

        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setBarColor(newValue)));
        value.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setValue(newValue.doubleValue())));
        minValue.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setMinValue(newValue.doubleValue())));
        maxValue.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setMaxValue(newValue.doubleValue())));

        return tile;
    }
    public Tile getBarChartTile(String title, String text, SimpleObjectProperty<List<BarChartItem>> itemsList){

        Tile tile = TileBuilder.create()
                .skinType(SkinType.BAR_CHART)
                .title(title)
                .text(text)
                .barChartItems(itemsList.get())
                .decimals(0)
                .padding(insets)
                .build();

        itemsList.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setBarChartItems(newValue)));

        return tile;
    }
    public Tile getDonutChartTile(String title, String text, SimpleObjectProperty<List<ChartData>> chartData){

        Tile tile = TileBuilder.create()
                .skinType(SkinType.DONUT_CHART)
                .title(title)
                .text(text)
                .textVisible(false)
                .chartData(chartData.get())
                .padding(insets)
                .build();

        chartData.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setChartData(newValue)));

        return tile;
    }
    public Tile getWeather1Tile(String title){

        Tile tile = TileBuilder.create()
                .skinType(SkinType.CUSTOM)
                .title("Weather")
                .unit("\u00B0C")
                .minValue(0)
                .maxValue(150)
                .decimals(1)
                .tickLabelDecimals(0)
                .customDecimalFormatEnabled(true)
                .customDecimalFormat(new DecimalFormat("#"))
                .time(ZonedDateTime.now(ZoneId.of("Europe/Berlin")))
                .padding(insets)
                .build();
        WeatherTileSkin weatherTileSkin = new WeatherTileSkin(tile);
        DataPoint today = new DataPoint();
        today.setTime(LocalDateTime.now());
        today.setSummary("Partly Cloudy");
        today.setCondition(ConditionAndIcon.PARTLY_CLOUDY_DAY);
        today.setTemperature(9.65);
        today.setPressure(1020.7);
        today.setHumidity(0.55);
        today.setWindSpeed(15.94);
        today.setTemperatureMin(0);
        today.setTemperatureMax(0);
        today.setSunriseTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(5, 38)));
        today.setSunsetTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(21, 44)));

        weatherTileSkin.setDataPoint(today, Unit.CA);
        tile.setSkin(weatherTileSkin);

        return tile;
    }
    public Tile getWeather2Tile(String title){

        Tile tile = TileBuilder.create()
                .skinType(SkinType.CUSTOM)
                .title(title)
                .padding(insets)
                .build();
        tile.setSkin(new EphemerisTileSkin(tile));

        return tile;
    }
    public Tile geTile(String title, String unit, SimpleDoubleProperty value){

        Tile tile = null;

        value.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> tile.setValue(newValue.doubleValue())));

        return tile;
    }
}
