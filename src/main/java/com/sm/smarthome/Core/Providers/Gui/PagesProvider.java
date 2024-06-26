package com.sm.smarthome.Core.Providers.Gui;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonState;
import com.sm.smarthome.Models.Ui.Buttons.MarkButton;
import com.sm.smarthome.Models.Ui.Pages.*;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PagesProvider {

    private Engine engine;
    public SimpleObjectProperty<PageBase> ActivePage = new SimpleObjectProperty<PageBase>();
    public ObservableList<MarkButton> Buttons = FXCollections.observableArrayList();
    public PageBase HomePage;
    public PageBase WeatherPage;
    public PageBase ApplicationsPage;
    public PageBase SetupPage;
    public PagesProvider(Engine engine){
       this.engine = engine;
        Initialize();
    }
    public void Initialize() {
        HomePage = new HomePage(engine);
        WeatherPage = new WeatherPage(engine);
        ApplicationsPage = new ApplicationsPage(engine);
        SetupPage = new SetupPage(engine);
        Buttons.addAll(HomePage.Button, WeatherPage.Button, ApplicationsPage.Button, SetupPage.Button);

        ActivePage.addListener((observable, oldValue, newValue) -> {
            Slide(oldValue, newValue);

            Platform.runLater(() -> {
                if (oldValue != null) oldValue.Button.setInactive();
                newValue.Button.setState(ButtonState.Active);
                try {

                    engine.ControlersProvider.MainViewController.PagePane.getChildren().add(newValue.BodyInstance);
                }catch (Exception ignored){}

            });
        });
    }

    private void Slide(PageBase oldValue, PageBase newValue){

        if (oldValue == null)return;

        newValue.BodyInstance.setVisible(true);

        TranslateTransition slideOldContent = new TranslateTransition(new javafx.util.Duration(500), oldValue.BodyInstance);

        TranslateTransition slideNewContent = new TranslateTransition(new javafx.util.Duration(500), newValue.BodyInstance);
        slideNewContent.setToY(0);
        slideOldContent.setFromY(0);

        if ((oldValue.Index) > (newValue.Index)) {
            slideOldContent.setToY(oldValue.Height);
            slideNewContent.setFromY(-oldValue.Height);
        }else{
            slideOldContent.setToY(-oldValue.Height);
            slideNewContent.setFromY(oldValue.Height);
        }

        slideOldContent.play();
        slideNewContent.setOnFinished(actionEvent -> oldValue.BodyInstance.setVisible(false));
        slideNewContent.setToY(0);
        slideNewContent.play();
    }

    public void SetActivePageByIndex(int index) {
        switch (index) {
            case 0 -> ActivePage.setValue(HomePage);
            case 1 -> ActivePage.setValue(WeatherPage);
            case 2 -> ActivePage.setValue(ApplicationsPage);
            case 3 -> ActivePage.setValue(SetupPage);
        }
    }
}
