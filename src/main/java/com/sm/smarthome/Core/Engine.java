package com.sm.smarthome.Core;

import com.sm.smarthome.Core.Providers.ControlersProvider;
import com.sm.smarthome.Core.Services.GuiService;
import com.sm.smarthome.Core.Services.SystemService;
import com.sm.smarthome.Enums.Actions.ApplicationAction;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Core.Services.ActionEventService;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Events.ButtonEvent;
import com.sm.smarthome.Events.SmEventHandler;
import com.sm.smarthome.Models.Data.UserModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.Style;

public class Engine {

    public SystemService SystemService = new SystemService();
    public SimpleObjectProperty<UserModel> CurrentUser = new SimpleObjectProperty<UserModel>();
    public GuiService GuiProvider;
    public ActionEventService ActionEventService = new ActionEventService();
    public ControlersProvider ControlersProvider = new ControlersProvider();

    public Engine(Stage mainStage){

        CurrentUser.set(new UserModel(-1,"Guest", UserPermissions.Guest));

        GuiProvider = new GuiService(mainStage);

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
                    case ActionThemeDark -> GuiProvider.SetTheme(Style.DARK);
                    case ActionThemeLight -> GuiProvider.SetTheme(Style.LIGHT);
                }

            }

            @Override
            public void onApplicationActionEvent(ApplicationAction action) {

            }
        });
    }

    private void SetHomePage(){
        ControlersProvider.mainViewController.SetActivePageByIndex(0);
    }
    private void SetWeatherPage(){
        ControlersProvider.mainViewController.SetActivePageByIndex(1);
    }
    private void SetSetupPage(){
        ControlersProvider.mainViewController.SetActivePageByIndex(2);
    }
    private void WifiAction(ButtonAction action){}
    private void ChangeUserAction(ButtonAction action){

        if (action == ButtonAction.ActionLogin){
            CurrentUser.set(new UserModel(0,"Mateusz", UserPermissions.Administrator));
        }else{
            CurrentUser.set(new UserModel(-1,"Guest", UserPermissions.Guest));
        }
    }



}