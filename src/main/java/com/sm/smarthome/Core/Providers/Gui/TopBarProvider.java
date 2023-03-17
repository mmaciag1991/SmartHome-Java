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
import jfxtras.styles.jmetro.Style;
import org.kordamp.ikonli.fluentui.FluentUiFilledAL;
import org.kordamp.ikonli.fluentui.FluentUiFilledMZ;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

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
        userButton = new TwoStateButton(FluentUiFilledMZ.PERSON_DELETE_24, FluentUiFilledMZ.PERSON_ADD_24, "topButtonsPanel.User", "topButtonsPanel.Guest",  ButtonAction.ActionLogin,  ButtonAction.ActionLogoff, ButtonSize.Small, ButtonWidthType.Widthx5, engine, false, ButtonState.Inactive, UserPermissions.Guest, false);
        engine.CurrentUser.addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue.Id.getValue() == -1){
                    userButton.SelectState(ButtonState.Inactive);
                    userButton.setText(newValue.Name.getValue());
                }else{
                    engine.GuiService.TopBarProvider.userButton.SelectState(ButtonState.Active);
                    engine.GuiService.TopBarProvider.userButton.setText(newValue.Name.getValue());
                }
            });
        });

        colorAccentButton = new TwoStateButton(FluentUiFilledAL.COLOR_24, FluentUiFilledAL.COLOR_24, null,null,  ButtonAction.ActionAccentColor,  ButtonAction.ActionAccentColor, ButtonSize.Small, ButtonWidthType.Normal, engine, false, ButtonState.Inactive, UserPermissions.User, true);

        TwoStateButton cameraButton = new TwoStateButton(MaterialDesign.MDI_CAMERA, MaterialDesign.MDI_CAMERA_OFF, null,null,  ButtonAction.ActionCameraOn,  ButtonAction.ActionCameraOff, ButtonSize.Small, ButtonWidthType.Normal, engine, false, ButtonState.Inactive, UserPermissions.Guest, false);
        TwoStateButton keyboardButton = new TwoStateButton(MaterialDesign.MDI_KEYBOARD, MaterialDesign.MDI_KEYBOARD_OFF, null,null,  ButtonAction.ActionKeyboardOn,  ButtonAction.ActionKeyboardOff, ButtonSize.Small, ButtonWidthType.Normal, engine, false, ButtonState.Inactive, UserPermissions.Guest, false);
        engine.GuiService.KeyboardPane.IsShowingKeyboard.addListener((observableValue, aBoolean, t1) -> {
            if (t1){
                keyboardButton.SelectState(ButtonState.Active);
            }else{
                keyboardButton.SelectState(ButtonState.Inactive);
            }
        });

        TwoStateButton volumeButton = new TwoStateButton(MaterialDesign.MDI_VOLUME_HIGH, MaterialDesign.MDI_VOLUME_OFF, null,null,  ButtonAction.ActionVolumeOn,  ButtonAction.ActionVolumeOff, ButtonSize.Small, ButtonWidthType.Normal, engine, false, ButtonState.Active, UserPermissions.User, true);
        TwoStateButton wifiButton = new TwoStateButton(MaterialDesign.MDI_WIFI, MaterialDesign.MDI_WIFI_OFF, "topButtonsPanel.On","topButtonsPanel.Off",  ButtonAction.ActionWifiOn,  ButtonAction.ActionWifiOff, ButtonSize.Small, ButtonWidthType.Widthx2_5, engine, false, ButtonState.Active, UserPermissions.User, true);
        TwoStateButton themeButton = new TwoStateButton(MaterialDesign.MDI_THEME_LIGHT_DARK, MaterialDesign.MDI_THEME_LIGHT_DARK, "topButtonsPanel.Dark","topButtonsPanel.Light",  ButtonAction.ActionThemeDark,  ButtonAction.ActionThemeLight, ButtonSize.Small, ButtonWidthType.Widthx2_5, engine, true, ButtonState.Active, UserPermissions.User, true);
        if (engine.SettingsProvider.Settings.getGlobalSettings().getTheme() == Style.DARK){
            themeButton.SelectState(ButtonState.Active);
        }else {
            themeButton.SelectState(ButtonState.Inactive);
        }

        SimpleButton dateTimeButton = new SimpleButton(MaterialDesign.MDI_TIMER, null,null, ButtonAction.ActionDateTimeSetup,  ButtonSize.Small, ButtonWidthType.Widthx5, engine, UserPermissions.Administrator);
        engine.SystemService.SystemInfoProvider.SystemDate.addListener((observableValue, s, t1) -> Platform.runLater(() -> dateTimeButton.setText(t1)));

        Buttons.addAll(colorAccentButton, cameraButton, keyboardButton, volumeButton, wifiButton, themeButton, dateTimeButton);
    }
}
