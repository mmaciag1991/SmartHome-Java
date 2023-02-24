package com.sm.smarthome.Core;

import com.jfoenix.controls.JFXPopup;
import com.sm.smarthome.Core.Providers.ControlersProvider;
import com.sm.smarthome.Core.Providers.Gui.PagesProvider;
import com.sm.smarthome.Core.Providers.Gui.TilesProvider;
import com.sm.smarthome.Core.Providers.Gui.TopBarProvider;
import com.sm.smarthome.Core.Services.ActionEventService;
import com.sm.smarthome.Core.Services.GuiService;
import com.sm.smarthome.Core.Services.SystemService;
import com.sm.smarthome.CustomControls.JFXCustomColorPickerDialog;
import com.sm.smarthome.Enums.Actions.ApplicationAction;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Events.ButtonEvent;
import com.sm.smarthome.Events.SmEventHandler;
import com.sm.smarthome.Models.Data.UserModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

public class Engine {

    public SystemService SystemService = new SystemService();
    public SimpleObjectProperty<UserModel> CurrentUser = new SimpleObjectProperty<UserModel>();
    public GuiService GuiService;
    public PagesProvider PagesProvider;
    public TopBarProvider TopBarProvider;
    public TilesProvider TilesProvider;
    public ActionEventService ActionEventService = new ActionEventService();
    public ControlersProvider ControlersProvider = new ControlersProvider();

    private Stage mainStage;
    public Engine(Stage mainStage){

        this.mainStage = mainStage;

        GuiService = new GuiService(this);

        PagesProvider = new PagesProvider(this);
        TopBarProvider = new TopBarProvider(this);
        TilesProvider = new TilesProvider(this);

        InitializeSmEventHandler(ActionEventService);
    }

    private void InitializeSmEventHandler(ActionEventService actionEventNode){
        actionEventNode.addEventHandler(ButtonEvent.BUTTON_ACTION_EVENT_TYPE, new SmEventHandler() {

            @Override
            public void onButtonActionEvent(ButtonAction action) {

                switch (action){
                    //Pages
                    case ActionHomePage -> SetHomePage();
                    case ActionWeatherPage -> SetWeatherPage();
                    case ActionSetupPage -> SetSetupPage();
                    //Top bar actions
                    case ActionWifiOn, ActionWifiOff -> WifiAction(action);
                    case ActionLogin , ActionLogoff -> ChangeUserAction(action);
                    case ActionThemeDark -> GuiService.SetTheme(Style.DARK);
                    case ActionThemeLight -> GuiService.SetTheme(Style.LIGHT);
                    case ActionAccentColor -> ChangeColorAccent();
                }

            }

            @Override
            public void onApplicationActionEvent(ApplicationAction action) {

            }
        });
    }

    private void SetHomePage(){
        PagesProvider.SetActivePageByIndex(0);
    }
    private void SetWeatherPage(){

        PagesProvider.SetActivePageByIndex(1);
    }
    private void SetSetupPage(){

        PagesProvider.SetActivePageByIndex(2);
    }
    private void WifiAction(ButtonAction action){}
    private void ChangeUserAction(ButtonAction action){

        if (action == ButtonAction.ActionLogin){
            CurrentUser.set(new UserModel(0,"Mateusz", UserPermissions.Administrator));
        }else{
            CurrentUser.set(new UserModel(-1,"Guest", UserPermissions.Guest));
        }
    }

    private void ChangeColorAccent(){
        Platform.runLater(() -> {

            JFXCustomColorPickerDialog jfxCustomColorPickerDialog =  new JFXCustomColorPickerDialog(mainStage.getOwner());
            jfxCustomColorPickerDialog.setCurrentColor(GuiService.AccentColor.getValue());
            jfxCustomColorPickerDialog.getStyleClass().add(JMetroStyleClass.BACKGROUND);
            jfxCustomColorPickerDialog.setOnSave(() -> {});
            jfxCustomColorPickerDialog.ActualColorProperty.addListener((observable, oldValue, newValue) -> {GuiService.AccentColor.setValue(newValue);});
            JMetro JMetroThemeManager = new JMetro(Style.DARK);
            JMetroThemeManager.setScene(jfxCustomColorPickerDialog.customScene);
            JMetroThemeManager.setStyle(Style.DARK);
            jfxCustomColorPickerDialog.customColorProperty.setValue(GuiService.AccentColor.getValue());
            jfxCustomColorPickerDialog.show();



        });
    }



}
