package com.sm.smarthome.Models.Ui.Pages;

import com.sm.smarthome.Application;
import com.sm.smarthome.Controllers.Pages.HomePageViewController;
import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Enums.Ui.Pages.PageType;
import com.sm.smarthome.Models.Ui.Buttons.MarkButton;
import com.sm.smarthome.Models.Ui.Buttons.RadiusButton;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.chart.ChartData;
import eu.hansolo.tilesfx.skins.BarChartItem;
import eu.hansolo.tilesfx.tools.Helper;
import javafx.animation.TranslateTransition;
import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import jfxtras.styles.jmetro.JMetroStyleClass;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


public class HomePage extends PageBase{
    private final Engine engine;
    private GridPane tilesGridLeft;
    private GridPane tilesGridCenter;
    private GridPane tilesGridRight;

    private final SimpleObjectProperty<GridPane> currentSubPage = new SimpleObjectProperty<GridPane>();


    public HomePage(Engine engine){
        this.engine = engine;
        this.Index = 0;
        this.Name = "Home Page";
        this.DisplayName = "leftButtonsPanel.homeButton";
        this.Type = PageType.Main;
        this.BodyInstance = CreateBodyInstance();
        this.Button = new MarkButton(MaterialDesign.MDI_HOME, DisplayName,null, ButtonAction.ActionHomePage, ButtonSize.Big, ButtonWidthType.Widthx3, engine, UserPermissions.Guest);
        this.Button.setDisableVisualFocus(true);
    }

    private Node CreateBodyInstance(){

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Views/Pages/HomePageView.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        parent.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        HomePageViewController homePageViewController = fxmlLoader.getController();

        this.tilesGridLeft = homePageViewController.getTilesGridLeft();
        this.tilesGridCenter = homePageViewController.getTilesGridCenter();
        this.tilesGridRight= homePageViewController.getTilesGridRight();

        InitializeButtons(homePageViewController);

        currentSubPage.setValue(tilesGridCenter);
        InitializeTilesLeft(tilesGridLeft);tilesGridLeft.setVisible(false);
        InitializeTilesCenter(tilesGridCenter);tilesGridCenter.setVisible(true);
        InitializeRightTiles(tilesGridRight);tilesGridRight.setVisible(false);

        currentSubPage.addListener((observable, oldValue, newValue) -> {
            Slide(oldValue, newValue);
        });


        return parent;
    }

    private void InitializeButtons(HomePageViewController homePageViewController){
        homePageViewController.getButtonsBox().getChildren().addAll(new RadiusButton(ButtonAction.ActionLeftSubHomePage, engine, UserPermissions.Guest), new RadiusButton(ButtonAction.ActionCenterSubHomePage, engine, UserPermissions.Guest), new RadiusButton(ButtonAction.ActionRightSubHomePage, engine, UserPermissions.Guest));
    }

    public void SetLeftSubPage(){
        currentSubPage.setValue(tilesGridLeft);
    }
    public void SetCenterSubPage(){
        currentSubPage.setValue(tilesGridCenter);
    }
    public void SetRightSubPage(){
        currentSubPage.setValue(tilesGridRight);
    }

    private void Slide(GridPane oldValue, GridPane newValue){

        newValue.setVisible(true);

        TranslateTransition slideOldContent = new TranslateTransition(new javafx.util.Duration(500), oldValue);

        TranslateTransition slideNewContent = new TranslateTransition(new javafx.util.Duration(500), newValue);
        slideNewContent.setToX(0);
        slideOldContent.setFromX(0);

        if (Integer.parseInt(oldValue.getAccessibleRoleDescription()) > Integer.parseInt(newValue.getAccessibleRoleDescription())) {
            slideOldContent.setToX(oldValue.getWidth());
            slideNewContent.setFromX(-oldValue.getWidth());
        }else{
            slideOldContent.setToX(-oldValue.getWidth());
            slideNewContent.setFromX(oldValue.getWidth());
        }

        slideOldContent.play();
        slideNewContent.setOnFinished(actionEvent -> oldValue.setVisible(false));
        slideNewContent.setToX(0);
        slideNewContent.play();
    }

    private void InitializeTilesLeft(GridPane tilesGridPane){
        tilesGridPane.setAccessibleRoleDescription("0");


        SimpleDoubleProperty val1 = new SimpleDoubleProperty(26);
        SimpleDoubleProperty val2 = new SimpleDoubleProperty(26);
        SimpleDoubleProperty val3 = new SimpleDoubleProperty(26);
        SimpleObjectProperty<ChartData> chartDataSimpleObjectProperty = new SimpleObjectProperty<ChartData>();
        SimpleObjectProperty<List<ChartData>> chartDataListSimpleObjectProperty = new SimpleObjectProperty<List<ChartData>>();
        SimpleStringProperty flipText = new SimpleStringProperty();


        Thread thread = new Thread(() -> {
          while (true){
              flipText.setValue(Helper.TIME_00_TO_59[(int)(Math.random() * Helper.TIME_00_TO_59.length - 1)]);
              chartDataSimpleObjectProperty.set(new ChartData("", Math.random() * 300 + 50, Instant.now()));
              ArrayList<ChartData> chartDataArrayList = new ArrayList<ChartData>();
              chartDataArrayList.add(new ChartData("1", Math.random() * 98));
              chartDataArrayList.add(new ChartData("2", Math.random() * 98));
              chartDataArrayList.add(new ChartData("3", Math.random() * 98));
              chartDataArrayList.add(new ChartData("4", Math.random() * 98));
              chartDataArrayList.add(new ChartData("5", Math.random() * 98));
              chartDataArrayList.add(new ChartData("6", Math.random() * 98));
              chartDataListSimpleObjectProperty.set(chartDataArrayList);
              val1.set(Math.random() * 99 + 1);
              val2.set(Math.random() * 99 + 1);
              val3.set(Math.random() * 89 + 1);
              try {
                  Thread.sleep(1900);
              } catch (InterruptedException e) {
                  throw new RuntimeException(e);
              }
          }

        });
        thread.start();

        List<ChartData> glucoseData = new ArrayList<>();
        for (int i = 0 ; i < 288; i++) {
            glucoseData.add(new ChartData("", Math.random() * 300 + 50));
        }


        // grid.add(node, col, row, colSpan, rowSpan)

        tilesGridPane.add(engine.TilesProvider.getClockTile(), 0, 0, 1, 1);
        tilesGridPane.add(engine.TilesProvider.getFluidTile("Fluid", "Text", "Unit", val1, 40), 1, 0, 1, 1);
        tilesGridPane.add(engine.TilesProvider.getTimerControlTile("Timer", "Text", new SimpleBooleanProperty(true)), 2, 0, 1, 1);
        tilesGridPane.add(engine.TilesProvider.getTimelineTile("Timeline", "dl/mg", new SimpleDoubleProperty(0), new SimpleDoubleProperty(350), new SimpleDoubleProperty(70), new SimpleDoubleProperty(240), chartDataSimpleObjectProperty), 3, 0, 1, 1);

        tilesGridPane.add(engine.TilesProvider.getBarGaugeTile("BarGauge", "txt", "F", val3, new SimpleDoubleProperty(0), new SimpleDoubleProperty(350), new SimpleDoubleProperty(70)), 0, 1, 1, 1);
        tilesGridPane.add(engine.TilesProvider.getRadialDistributionTile("RadialChart", "Text", new SimpleStringProperty("Discr"), new SimpleDoubleProperty(0), new SimpleDoubleProperty(400), new SimpleDoubleProperty(70), new SimpleDoubleProperty(140), glucoseData), 1, 1, 2, 2);
        tilesGridPane.add(engine.TilesProvider.getRadialPercentageTile("Radial Percentage", "txt", chartDataListSimpleObjectProperty), 3, 1, 1, 1);

        tilesGridPane.add(engine.TilesProvider.getDateTile(), 0, 2, 1, 2);
        tilesGridPane.add(engine.TilesProvider.getFlipTile(flipText, 1000, Helper.TIME_00_TO_59), 3, 2, 1, 1);

        tilesGridPane.add(engine.TilesProvider.getSmoothAreaChartTile("Smooth Area Chart", "g", "txt", new SimpleDoubleProperty(0), new SimpleDoubleProperty(200), chartDataListSimpleObjectProperty), 1, 3, 3, 1);


    }
    private void InitializeTilesCenter(GridPane tilesGridPane){
        tilesGridPane.setAccessibleRoleDescription("1");

        SimpleDoubleProperty val1 = new SimpleDoubleProperty(26);
        SimpleDoubleProperty val2 = new SimpleDoubleProperty(26);
        SimpleDoubleProperty val3 = new SimpleDoubleProperty(26);

        // AreaChart Data
        XYChart.Series<String, Number> series1 = new XYChart.Series();
        series1.setName("Whatever");
        series1.getData().add(new XYChart.Data("MO", 23));
        series1.getData().add(new XYChart.Data("TU", 21));
        series1.getData().add(new XYChart.Data("WE", 20));
        series1.getData().add(new XYChart.Data("TH", 22));
        series1.getData().add(new XYChart.Data("FR", 24));
        series1.getData().add(new XYChart.Data("SA", 22));
        series1.getData().add(new XYChart.Data("SU", 20));

        //calendar
        ZonedDateTime now          = ZonedDateTime.now();
        SimpleObjectProperty<List<ChartData>> calendarData = new SimpleObjectProperty<List<ChartData>>();

        ArrayList<ChartData> calendarDataList =   new ArrayList<>(10);
        calendarDataList.add(new ChartData("Item 1", now.minusDays(1).toInstant()));
        calendarDataList.add(new ChartData("Item 2", now.plusDays(2).toInstant()));
        calendarDataList.add(new ChartData("Item 3", now.plusDays(10).toInstant()));
        calendarDataList.add(new ChartData("Item 4", now.plusDays(15).toInstant()));
        calendarDataList.add(new ChartData("Item 5", now.plusDays(15).toInstant()));
        calendarDataList.add(new ChartData("Item 6", now.plusDays(20).toInstant()));
        calendarDataList.add(new ChartData("Item 7", now.plusDays(7).toInstant()));
        calendarDataList.add(new ChartData("Item 8", now.minusDays(1).toInstant()));
        calendarDataList.add(new ChartData("Item 9", now.toInstant()));
        calendarDataList.add(new ChartData("Item 10", now.toInstant()));

        calendarData.set(calendarDataList);

        Thread thread = new Thread(() -> {
            while (true){
                val1.set(Math.random() * 99 + 1);
                val2.set(Math.random() * 99 + 1);
                val3.set(Math.random() * 89 + 1);
                series1.getData().forEach(data -> data.setYValue(Math.random() * 99 + 1));
                try {
                    Thread.sleep(1900);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        thread.start();


        tilesGridPane.add(engine.TilesProvider.getTimerCountDownTile("Timer countdown", "Text", new SimpleStringProperty("description"), new SimpleObjectProperty<Duration>(Duration.ofSeconds(10)), new SimpleBooleanProperty(true)), 0, 0, 1, 1);

        tilesGridPane.add(engine.TilesProvider.getProcentageTile("Wilgotność", new SimpleStringProperty("Kuchnia"), new SimpleDoubleProperty(100), val1), 1, 0, 2, 1);
        tilesGridPane.add(engine.TilesProvider.getGuageTile("Temperatura", "℃", val2, val3), 3, 0, 1, 1);
        tilesGridPane.add(engine.TilesProvider.getClockTile(), 0, 1, 2, 1);
        tilesGridPane.add(engine.TilesProvider.geSparkLineTile("SparkLine", "mb", val2), 2, 1, 1, 1);
        tilesGridPane.add(engine.TilesProvider.getSwitchTile("Switch", new SimpleStringProperty("text"), new SimpleStringProperty("description"), new SimpleBooleanProperty()), 3, 1, 1, 1);
        tilesGridPane.add(engine.TilesProvider.getTimeControlTile(new SimpleObjectProperty<>()), 0, 2, 1, 2);
        tilesGridPane.add(engine.TilesProvider.getPlusMinusTile("PlusMinusTile", "g", new SimpleStringProperty("text"), new SimpleStringProperty("description"), new SimpleDoubleProperty(0), new SimpleDoubleProperty(10), new SimpleDoubleProperty(5)), 1, 3, 1, 1);
        tilesGridPane.add(engine.TilesProvider.getSliderTile("SliderTile", "%", new SimpleStringProperty("text"), new SimpleStringProperty("description"), new SimpleDoubleProperty(23)), 1, 2, 1, 1);
        tilesGridPane.add(engine.TilesProvider.geCalendarTile(calendarData), 2, 2, 2, 2);

    }
    public void InitializeRightTiles(GridPane tilesGridPane){
        tilesGridPane.setAccessibleRoleDescription("2");

        SimpleDoubleProperty ramUsage = new SimpleDoubleProperty();
        engine.SystemService.SystemInfoProvider.RamUsage.addListener((observableValue, memoryValue, t1) -> {ramUsage.setValue(t1.getValue());});

        BarChartItem barChartItem1 = new BarChartItem("Kitchen", 47, Tile.BLUE);
        BarChartItem barChartItem2 = new BarChartItem("Room", 43, Tile.RED);
        SimpleObjectProperty<List<BarChartItem>> listSimpleObjectProperty = new SimpleObjectProperty<List<BarChartItem>>();
        ArrayList<BarChartItem> chartData = new ArrayList<BarChartItem>();
        chartData.add(barChartItem1);
        chartData.add(barChartItem2);
        listSimpleObjectProperty.set(chartData);

        ChartData chartData1 = new ChartData("Item 1", 24.0, Tile.GREEN);
        ChartData chartData2 = new ChartData("Item 2", 10.0, Tile.BLUE);
        ChartData chartData3 = new ChartData("Item 3", 12.0, Tile.RED);
        ChartData chartData4 = new ChartData("Item 4", 13.0, Tile.YELLOW_ORANGE);
        SimpleObjectProperty<List<ChartData>> listSimpleObjectPropertya = new SimpleObjectProperty<List<ChartData>>();
        ArrayList<ChartData> chartDataa = new ArrayList<ChartData>();
        chartDataa.add(chartData1);
        chartDataa.add(chartData2);
        chartDataa.add(chartData3);
        chartDataa.add(chartData4);
        listSimpleObjectPropertya.set(chartDataa);


        // grid.add(node, col, row, colSpan, rowSpan)

        tilesGridPane.add(engine.TilesProvider.getSparkLineTile("CPU", "%", engine.SystemService.SystemInfoProvider.CpuLoad), 0, 0, 1, 1);
        tilesGridPane.add(engine.TilesProvider.getStockTile("RAM (%s)".formatted(engine.SystemService.SystemInfoProvider.RamTotal.getValue().getUnit().toString()), new SimpleDoubleProperty(0), new SimpleDoubleProperty(engine.SystemService.SystemInfoProvider.RamTotal.getValue().getValue()), ramUsage), 1, 0, 2, 1);
        tilesGridPane.add(engine.TilesProvider.getDonutChartTile("Donut chart", "text", listSimpleObjectPropertya), 3, 0, 1, 1);
        tilesGridPane.add(engine.TilesProvider.getBarChartTile("Bar chart", "text", listSimpleObjectProperty), 0, 1, 1, 1);
        tilesGridPane.add(engine.TilesProvider.getWeather1Tile("Starachowice"), 1, 1, 2, 1);
        tilesGridPane.add(engine.TilesProvider.getWeather2Tile("Ephemeris"), 1, 2, 2, 1);
    }
}
