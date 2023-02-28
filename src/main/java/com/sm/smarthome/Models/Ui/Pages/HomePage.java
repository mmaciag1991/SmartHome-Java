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
import eu.hansolo.tilesfx.TimeSection;
import eu.hansolo.tilesfx.TimeSectionBuilder;
import eu.hansolo.tilesfx.chart.ChartData;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import jfxtras.styles.jmetro.JMetroStyleClass;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends PageBase{
    private Engine engine;
    private Parent parent;
    private HomePageViewController homePageViewController;
    public HomePage(Engine engine){
        this.engine = engine;
        this.Index = 0;
        this.Name = "Home Page";
        this.DisplayName = "Home";
        this.Type = PageType.Main;
        this.BodyInstance = CreateBodyInstance();
        this.Button = new MarkButton(MaterialDesign.MDI_HOME, DisplayName,null, ButtonAction.ActionHomePage, ButtonSize.Big, ButtonWidthType.Widthx2, engine, UserPermissions.Guest);
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

        this.parent = parent;
        this.homePageViewController = homePageViewController;

        InitializeButtons(homePageViewController);

        InitializeTiles();

        return parent;
    }

    private void InitializeButtons(HomePageViewController homePageViewController){
        homePageViewController.getButtonsBox().getChildren().addAll(new RadiusButton(ButtonAction.ActionLeftSubHomePage, engine, UserPermissions.Guest), new RadiusButton(ButtonAction.ActionCenterSubHomePage, engine, UserPermissions.Guest), new RadiusButton(ButtonAction.ActionRightSubHomePage, engine, UserPermissions.Guest));
    }

    public void SetLeftSubPage(){
        homePageViewController.slide(parent);
        homePageViewController.getTilesGrid().getChildren().clear();
    }
    public void SetCenterSubPage(){
        homePageViewController.slide(parent);
        homePageViewController.getTilesGrid().getChildren().clear();
        InitializeTiles();
    }
    public void SetRightSubPage(){
        homePageViewController.slide(parent);
        homePageViewController.getTilesGrid().getChildren().clear();
    }

    private void InitializeTiles(){
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
        List<ChartData> calendarData = new ArrayList<>(10);
        calendarData.add(new ChartData("Item 1", now.minusDays(1).toInstant()));
        calendarData.add(new ChartData("Item 2", now.plusDays(2).toInstant()));
        calendarData.add(new ChartData("Item 3", now.plusDays(10).toInstant()));
        calendarData.add(new ChartData("Item 4", now.plusDays(15).toInstant()));
        calendarData.add(new ChartData("Item 5", now.plusDays(15).toInstant()));
        calendarData.add(new ChartData("Item 6", now.plusDays(20).toInstant()));
        calendarData.add(new ChartData("Item 7", now.plusDays(7).toInstant()));
        calendarData.add(new ChartData("Item 8", now.minusDays(1).toInstant()));
        calendarData.add(new ChartData("Item 9", now.toInstant()));
        calendarData.add(new ChartData("Item 10", now.toInstant()));


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

        GridPane tilesGridPane = homePageViewController.getTilesGrid();

        //tilesGridPane.add(engine.TilesProvider.getClockTile(), 0, 0, 1, 1);
        //tilesGridPane.add(engine.TilesProvider.getFluidTile("Fluid", "Text", "Unit", val1, 40), 0, 0, 1, 1);
        //tilesGridPane.add(engine.TilesProvider.getTimerControlTile("Timer", "Text", new SimpleBooleanProperty(true)), 0, 0, 1, 1);
        tilesGridPane.add(engine.TilesProvider.getTimerCountDownTile("Timer countdown", "Text", new SimpleStringProperty("description"), new SimpleObjectProperty<Duration>(Duration.ofSeconds(90)), new SimpleBooleanProperty(true)), 0, 0, 1, 1);

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
}
