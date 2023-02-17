package com.sm.smarthome;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Enums.ApplicationAction;
import com.sm.smarthome.Events.ButtonEvent;
import com.sm.smarthome.Events.SmEventHandler;
import com.sm.smarthome.Models.Ui.SimpleButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import com.sm.smarthome.Enums.ButtonSize;
import com.sm.smarthome.Enums.ButtonWidthType;
import com.sm.smarthome.Enums.ButtonAction;

public class MainViewController {
    @FXML
    private VBox leftButtonsBox;

    private Engine engine;
    ObservableList<SimpleButton> simpleButtonObservableList = FXCollections.observableArrayList();

    public MainViewController(){



        Platform.runLater(() -> {
            simpleButtonObservableList.add(new SimpleButton(MaterialDesign.MDI_HOME, "Home", ButtonAction.ActionHomePage, ButtonSize.Big, ButtonWidthType.Long, engine));
            simpleButtonObservableList.add(new SimpleButton(MaterialDesign.MDI_WEATHER_LIGHTNING_RAINY, "Weather", ButtonAction.ActionWeatherPage, ButtonSize.Big, ButtonWidthType.Long, engine));
            simpleButtonObservableList.add(new SimpleButton(MaterialDesign.MDI_SETTINGS, "Setup", ButtonAction.ActionSetupPage, ButtonSize.Big, ButtonWidthType.Long, engine));

            leftButtonsBox.getChildren().addAll(simpleButtonObservableList);

            engine.ActionEventService.addEventHandler(ButtonEvent.BUTTON_ACTION_EVENT_TYPE, new SmEventHandler() {

                @Override
                public void onButtonActionEvent(ButtonAction action) {

                }

                @Override
                public void onApplicationActionEvent(ApplicationAction action) {

                }
            });
        });

    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}