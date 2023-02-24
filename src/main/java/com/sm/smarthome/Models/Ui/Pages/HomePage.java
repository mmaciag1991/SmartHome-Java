package com.sm.smarthome.Models.Ui.Pages;

import com.sm.smarthome.Application;
import com.sm.smarthome.Controllers.Pages.HomePageViewController;
import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Enums.Ui.Otthers.PopupType;
import com.sm.smarthome.Enums.Ui.Pages.PageType;
import com.sm.smarthome.Models.Ui.Buttons.MarkButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import jfxtras.styles.jmetro.JMetroStyleClass;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.io.IOException;
import java.util.Random;

public class HomePage extends PageBase{
    private Engine engine;
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

        InitializeTiles(homePageViewController.getGridPane());

        return parent;
    }

    private void InitializeTiles(GridPane tilesGridPane){
        SimpleDoubleProperty val1 = new SimpleDoubleProperty(26);
        SimpleDoubleProperty val2 = new SimpleDoubleProperty(26);
        SimpleDoubleProperty val3 = new SimpleDoubleProperty(26);
        Thread thread = new Thread(() -> {
          while (true){
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

        tilesGridPane.add(engine.TilesProvider.getClockTile(), 0, 0, 1, 1);
        tilesGridPane.add(engine.TilesProvider.getProcentageTile("Wilgotność", "Kuchnia", 100, val1), 1, 0, 2, 1);
        tilesGridPane.add(engine.TilesProvider.getGuageTile("Temperatura", "℃", val2, val3), 3, 0, 1, 1);
        tilesGridPane.add(engine.TilesProvider.getClockTile(), 0, 1, 2, 1);

    }
}
