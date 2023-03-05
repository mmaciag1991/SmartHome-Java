package com.sm.smarthome.Core.Services;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.CustomControls.ColorPicker.JFXCustomColorPickerDialog;
import com.sm.smarthome.Enums.Actions.ApplicationAction;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Events.ButtonEvent;
import com.sm.smarthome.Events.ButtonEventHandler;
import com.sm.smarthome.Models.Data.UserModel;
import com.sm.smarthome.Models.Ui.Pages.HomePage;
import javafx.application.Platform;
import javafx.scene.shape.Line;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

public class ActionEventService extends Line {

    private Engine engine;
    public ActionEventService(Engine engine) {

        this.engine = engine;
        addEventHandler(ButtonEvent.BUTTON_ACTION_EVENT_TYPE, new ButtonEventHandler() {

            @Override
            public void onButtonActionEvent(ButtonAction action) {

                switch (action) {
                    //Pages
                    case ActionHomePage -> SetHomePage();
                    case ActionWeatherPage -> SetWeatherPage();
                    case ActionSetupPage -> SetSetupPage();
                    //HomePage subpages buttons actions
                    case ActionLeftSubHomePage -> ((HomePage)engine.PagesProvider.HomePage).SetLeftSubPage();
                    case ActionCenterSubHomePage -> ((HomePage)engine.PagesProvider.HomePage).SetCenterSubPage();
                    case ActionRightSubHomePage -> ((HomePage)engine.PagesProvider.HomePage).SetRightSubPage();
                    //Top bar actions
                    case ActionWifiOn, ActionWifiOff -> WifiAction(action);
                    case ActionLogin, ActionLogoff -> ChangeUserAction(action);
                    case ActionThemeDark -> engine.GuiService.SetTheme(Style.DARK);
                    case ActionThemeLight -> engine.GuiService.SetTheme(Style.LIGHT);
                    case ActionAccentColor -> ChangeColorAccent();
                }
            }

            @Override
            public void onApplicationActionEvent(ApplicationAction action) {

            }
        });
    }


    private void SetHomePage(){
        engine.PagesProvider.SetActivePageByIndex(0);
    }
    private void SetWeatherPage(){

        engine.PagesProvider.SetActivePageByIndex(1);
    }
    private void SetSetupPage(){

        engine.PagesProvider.SetActivePageByIndex(2);
    }
    private void WifiAction(ButtonAction action){
        switch (action){
            case ActionWifiOn -> engine.SystemService.RunProcess("sudo ifconfig wlan0 on");
            case ActionWifiOff -> engine.SystemService.RunProcess("sudo ifconfig wlan0 down");
        }
    }
    private void ChangeUserAction(ButtonAction action){

        if (action == ButtonAction.ActionLogin){
            engine.CurrentUser.set(new UserModel(0,"Mateusz", UserPermissions.Administrator));
        }else{
            engine.CurrentUser.set(new UserModel(-1,"Guest", UserPermissions.Guest));
        }
    }

    private void ChangeColorAccent(){
        Platform.runLater(() -> {

            JFXCustomColorPickerDialog jfxCustomColorPickerDialog =  new JFXCustomColorPickerDialog(engine.GuiService.MainStage.getOwner());
            jfxCustomColorPickerDialog.setCurrentColor(engine.GuiService.AccentColor.getValue());
            jfxCustomColorPickerDialog.getStyleClass().add(JMetroStyleClass.BACKGROUND);
            jfxCustomColorPickerDialog.setOnSave(() -> {});
            jfxCustomColorPickerDialog.ActualColorProperty.addListener((observable, oldValue, newValue) -> {
                engine.GuiService.AccentColor.setValue(newValue);
            });
            JMetro JMetroThemeManager = new JMetro(Style.DARK);
            JMetroThemeManager.setScene(jfxCustomColorPickerDialog.customScene);
            JMetroThemeManager.setStyle(Style.DARK);
            jfxCustomColorPickerDialog.customColorProperty.setValue(engine.GuiService.AccentColor.getValue());
            jfxCustomColorPickerDialog.show();

        });
    }
}
