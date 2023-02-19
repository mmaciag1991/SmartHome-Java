package com.sm.smarthome.Controllers;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Enums.Actions.ApplicationAction;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonState;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Events.ButtonEvent;
import com.sm.smarthome.Events.SmEventHandler;
import com.sm.smarthome.Models.Ui.Buttons.SimpleButton;
import com.sm.smarthome.Models.Ui.Buttons.TwoStateButton;
import com.sm.smarthome.Models.Ui.Pages.HomePage;
import com.sm.smarthome.Models.Ui.Pages.PageBase;
import com.sm.smarthome.Models.Ui.Pages.SetupPage;
import com.sm.smarthome.Models.Ui.Pages.WeatherPage;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import org.kordamp.ikonli.fluentui.FluentUiFilledMZ;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class MainViewController {
    @FXML
    private HBox topButtonsBox;
    @FXML
    private VBox leftButtonsBox;
    @FXML
    private Pane userPane;
    @FXML
    private StackPane pagePane;

    private ObservableList<SimpleButton> topSimpleButtonObservableList = FXCollections.observableArrayList();

    public SimpleObjectProperty<PageBase> ActivePage = new SimpleObjectProperty<PageBase>();
    public PageBase HomePage;
    public PageBase WeatherPage;
    public PageBase SetupPage;

    public MainViewController(){}

    public void SetActivePageByIndex(int index){
        switch (index){
            case 0 -> ActivePage.setValue(HomePage);
            case 1 -> ActivePage.setValue(WeatherPage);
            case 2 -> ActivePage.setValue(SetupPage);
        }
    }
    public void Initialize(Engine engine) {
        HomePage = new HomePage(engine);
        WeatherPage = new WeatherPage(engine);
        SetupPage = new SetupPage(engine);

        ActivePage.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) oldValue.Button.setInactive();
            newValue.Button.setState(ButtonState.Active);
            pagePane.getChildren().clear();
            pagePane.getChildren().add(ActivePage.getValue().BodyInstance);
        });

        ActivePage.setValue(HomePage);


            leftButtonsBox.getChildren().addAll(HomePage.Button, WeatherPage.Button, SetupPage.Button);

            engine.ActionEventService.addEventHandler(ButtonEvent.BUTTON_ACTION_EVENT_TYPE, new SmEventHandler() {
                @Override
                public void onButtonActionEvent(ButtonAction action) {}
                @Override
                public void onApplicationActionEvent(ApplicationAction action) {}
            });


            TwoStateButton cameraButton = new TwoStateButton(MaterialDesign.MDI_CAMERA, MaterialDesign.MDI_CAMERA_OFF, "","",  ButtonAction.ActionCameraOn,  ButtonAction.ActionCameraOff, ButtonSize.Small, ButtonWidthType.Normal, engine, false, ButtonState.Inactive);
            TwoStateButton keyboardButton = new TwoStateButton(MaterialDesign.MDI_KEYBOARD, MaterialDesign.MDI_KEYBOARD_OFF, "","",  ButtonAction.ActionKeyboardOn,  ButtonAction.ActionKeyboardOff, ButtonSize.Small, ButtonWidthType.Normal, engine, false, ButtonState.Inactive);
            TwoStateButton volumeButton = new TwoStateButton(MaterialDesign.MDI_VOLUME_HIGH, MaterialDesign.MDI_VOLUME_OFF, "","",  ButtonAction.ActionVolumeOn,  ButtonAction.ActionVolumeOff, ButtonSize.Small, ButtonWidthType.Normal, engine, false, ButtonState.Active);
            TwoStateButton wifiButton = new TwoStateButton(MaterialDesign.MDI_WIFI, MaterialDesign.MDI_WIFI_OFF, "ON","OFF",  ButtonAction.ActionWifiOn,  ButtonAction.ActionWifiOff, ButtonSize.Small, ButtonWidthType.Widthx2_5, engine, false, ButtonState.Active);
            TwoStateButton themeButton = new TwoStateButton(MaterialDesign.MDI_THEME_LIGHT_DARK, MaterialDesign.MDI_THEME_LIGHT_DARK, "Dark","Light",  ButtonAction.ActionThemeDark,  ButtonAction.ActionThemeLight, ButtonSize.Small, ButtonWidthType.Widthx2_5, engine, true, ButtonState.Active);

            SimpleButton dateTimeButton = new SimpleButton(MaterialDesign.MDI_TIMER, "Time",null, ButtonAction.ActionDateTimeSetup,  ButtonSize.Small, ButtonWidthType.Widthx5, engine);
            engine.SystemService.SystemDate.addListener((observableValue, s, t1) -> dateTimeButton.setDisplayText(s));

            topSimpleButtonObservableList.addAll(cameraButton,  keyboardButton,volumeButton, wifiButton, themeButton, dateTimeButton);
            topButtonsBox.getChildren().addAll(topSimpleButtonObservableList);

            TwoStateButton userButton = new TwoStateButton(FluentUiFilledMZ.PERSON_DELETE_24, FluentUiFilledMZ.PERSON_ADD_24, "User", engine.CurrentUser.getValue().Name.getValue(),  ButtonAction.ActionLogin,  ButtonAction.ActionLogoff, ButtonSize.Small, ButtonWidthType.Widthx2_5, engine, true, ButtonState.Inactive);
            engine.CurrentUser.addListener((observable, oldValue, newValue) -> {
                if (newValue.Id.getValue() == -1){
                    userButton.SelectState(ButtonState.Inactive);
                    userButton.setText(newValue.Name.getValue());
                }else{
                    userButton.SelectState(ButtonState.Active);
                    userButton.setText(newValue.Name.getValue());
                }
            });
            userPane.getChildren().add(userButton);

    }
}