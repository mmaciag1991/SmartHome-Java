package com.sm.smarthome.Core.Providers.Gui;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonState;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Interfaces.IButton;
import com.sm.smarthome.Models.Ui.Buttons.SimpleButton;
import com.sm.smarthome.Models.Ui.Buttons.TwoStateButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.kordamp.ikonli.fluentui.FluentUiFilledAL;
import org.kordamp.ikonli.fluentui.FluentUiFilledMZ;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

public class TopBarProvider {

    private Engine engine;

    public TwoStateButton userButton;
    public  TwoStateButton colorAccentButton;

    public ObservableList<IButton> Buttons = FXCollections.observableArrayList();
    public TopBarProvider(Engine engine){
        this.engine = engine;
        Initialize();
    }
    public void Initialize() {
        userButton = new TwoStateButton(FluentUiFilledMZ.PERSON_DELETE_24, FluentUiFilledMZ.PERSON_ADD_24, "User", "Guest",  ButtonAction.ActionLogin,  ButtonAction.ActionLogoff, ButtonSize.Small, ButtonWidthType.Widthx5, engine, false, ButtonState.Inactive, UserPermissions.Guest);
        engine.CurrentUser.addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue.Id.getValue() == -1){
                    userButton.SelectState(ButtonState.Inactive);
                    userButton.setText(newValue.Name.getValue());
                }else{
                    engine.TopBarProvider.userButton.SelectState(ButtonState.Active);
                    engine.TopBarProvider.userButton.setText(newValue.Name.getValue());
                }
            });
        });

        colorAccentButton = new TwoStateButton(FluentUiFilledAL.COLOR_24, FluentUiFilledAL.COLOR_24, "","",  ButtonAction.ActionAccentColor,  ButtonAction.ActionAccentColor, ButtonSize.Small, ButtonWidthType.Normal, engine, false, ButtonState.Inactive, UserPermissions.User);

        TwoStateButton cameraButton = new TwoStateButton(MaterialDesign.MDI_CAMERA, MaterialDesign.MDI_CAMERA_OFF, "","",  ButtonAction.ActionCameraOn,  ButtonAction.ActionCameraOff, ButtonSize.Small, ButtonWidthType.Normal, engine, false, ButtonState.Inactive, UserPermissions.User);
        TwoStateButton keyboardButton = new TwoStateButton(MaterialDesign.MDI_KEYBOARD, MaterialDesign.MDI_KEYBOARD_OFF, "","",  ButtonAction.ActionKeyboardOn,  ButtonAction.ActionKeyboardOff, ButtonSize.Small, ButtonWidthType.Normal, engine, false, ButtonState.Inactive, UserPermissions.Guest);
        TwoStateButton volumeButton = new TwoStateButton(MaterialDesign.MDI_VOLUME_HIGH, MaterialDesign.MDI_VOLUME_OFF, "","",  ButtonAction.ActionVolumeOn,  ButtonAction.ActionVolumeOff, ButtonSize.Small, ButtonWidthType.Normal, engine, false, ButtonState.Active, UserPermissions.User);
        TwoStateButton wifiButton = new TwoStateButton(MaterialDesign.MDI_WIFI, MaterialDesign.MDI_WIFI_OFF, "ON","OFF",  ButtonAction.ActionWifiOn,  ButtonAction.ActionWifiOff, ButtonSize.Small, ButtonWidthType.Widthx2_5, engine, false, ButtonState.Active, UserPermissions.User);
        TwoStateButton themeButton = new TwoStateButton(MaterialDesign.MDI_THEME_LIGHT_DARK, MaterialDesign.MDI_THEME_LIGHT_DARK, "Dark","Light",  ButtonAction.ActionThemeDark,  ButtonAction.ActionThemeLight, ButtonSize.Small, ButtonWidthType.Widthx2_5, engine, true, ButtonState.Active, UserPermissions.User);

        SimpleButton dateTimeButton = new SimpleButton(MaterialDesign.MDI_TIMER, "Time",null, ButtonAction.ActionDateTimeSetup,  ButtonSize.Small, ButtonWidthType.Widthx5, engine, UserPermissions.Administrator);
        engine.SystemService.SystemDate.addListener((observableValue, s, t1) -> dateTimeButton.setDisplayText(s));

        Buttons.addAll(colorAccentButton, cameraButton, keyboardButton, volumeButton, wifiButton, themeButton, dateTimeButton);
    }
}
