package com.sm.smarthome.Core.Providers.Gui;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Models.Ui.Buttons.MarkButton;
import com.sm.smarthome.Models.Ui.Pages.*;
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
