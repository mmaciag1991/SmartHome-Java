package com.sm.smarthome.Core.Services;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonState;
import com.sm.smarthome.Models.Ui.Buttons.MarkButton;
import com.sm.smarthome.Models.Ui.Pages.HomePage;
import com.sm.smarthome.Models.Ui.Pages.PageBase;
import com.sm.smarthome.Models.Ui.Pages.SetupPage;
import com.sm.smarthome.Models.Ui.Pages.WeatherPage;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class PagesService {

    private Engine engine;
    public SimpleObjectProperty<PageBase> ActivePage = new SimpleObjectProperty<PageBase>();
    public ObservableList<MarkButton> Buttons = FXCollections.observableArrayList();
    public PageBase HomePage;
    public PageBase WeatherPage;
    public PageBase SetupPage;
    public PagesService(Engine engine){
       this.engine = engine;
        Initialize();
    }
    public void Initialize() {
        HomePage = new HomePage(engine);
        WeatherPage = new WeatherPage(engine);
        SetupPage = new SetupPage(engine);
        Buttons.addAll(HomePage.Button, WeatherPage.Button, SetupPage.Button);
    }

    public void SetActivePageByIndex(int index) {
        switch (index) {
            case 0 -> ActivePage.setValue(HomePage);
            case 1 -> ActivePage.setValue(WeatherPage);
            case 2 -> ActivePage.setValue(SetupPage);
        }
    }
}
