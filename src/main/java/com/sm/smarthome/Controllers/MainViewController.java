package com.sm.smarthome.Controllers;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Enums.Actions.ApplicationAction;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonState;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Events.ButtonEvent;
import com.sm.smarthome.Events.SmEventHandler;
import com.sm.smarthome.Models.Data.UserModel;
import com.sm.smarthome.Models.Ui.Buttons.MarkButton;
import com.sm.smarthome.Models.Ui.Buttons.SimpleButton;
import com.sm.smarthome.Models.Ui.Buttons.TwoStateButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import org.kordamp.ikonli.fluentui.FluentUiFilledMZ;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class MainViewController {
    @FXML
    private HBox topButtonsBox;
    @FXML
    public VBox LeftButtonsBox;
    @FXML
    private Pane userPane;
    @FXML
    public StackPane PagePane;
    @FXML
    private GridPane topGridPane;

    private ObservableList<SimpleButton> topSimpleButtonObservableList = FXCollections.observableArrayList();


    public MainViewController(){}


    public void Initialize(Engine engine) {


        engine.PagesService.ActivePage.addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (oldValue != null) oldValue.Button.setInactive();
                newValue.Button.setState(ButtonState.Active);
                engine.ControlersProvider.MainViewController.PagePane.getChildren().clear();
                engine.ControlersProvider.MainViewController.PagePane.getChildren().add(newValue.BodyInstance);
                engine.PagesService.Buttons.addAll( new MarkButton(MaterialDesign.MDI_HOME, "DisplayName",null, ButtonAction.ActionHomePage, ButtonSize.Big, ButtonWidthType.Widthx2, engine, UserPermissions.Guest));
            });
        });

        engine.PagesService.Buttons.addListener((ListChangeListener<MarkButton>) c -> {
            while (c.next()) {
                LeftButtonsBox.getChildren().clear();
                LeftButtonsBox.getChildren().addAll(c.getList());
                if (c.wasAdded()) {
                } else if (c.wasRemoved()) {
                }
            }
        });
        LeftButtonsBox.getChildren().addAll(engine.PagesService.Buttons);

        engine.PagesService.Buttons.addAll( new MarkButton(MaterialDesign.MDI_HOME, "DisplayName",null, ButtonAction.ActionHomePage, ButtonSize.Big, ButtonWidthType.Widthx2, engine, UserPermissions.Guest));


        engine.PagesService.SetActivePageByIndex(0);

//            engine.ActionEventService.addEventHandler(ButtonEvent.BUTTON_ACTION_EVENT_TYPE, new SmEventHandler() {
//                @Override
//                public void onButtonActionEvent(ButtonAction action) {}
//                @Override
//                public void onApplicationActionEvent(ApplicationAction action) {}
//            });


            TwoStateButton cameraButton = new TwoStateButton(MaterialDesign.MDI_CAMERA, MaterialDesign.MDI_CAMERA_OFF, "","",  ButtonAction.ActionCameraOn,  ButtonAction.ActionCameraOff, ButtonSize.Small, ButtonWidthType.Normal, engine, false, ButtonState.Inactive, UserPermissions.User);
            TwoStateButton keyboardButton = new TwoStateButton(MaterialDesign.MDI_KEYBOARD, MaterialDesign.MDI_KEYBOARD_OFF, "","",  ButtonAction.ActionKeyboardOn,  ButtonAction.ActionKeyboardOff, ButtonSize.Small, ButtonWidthType.Normal, engine, false, ButtonState.Inactive, UserPermissions.Guest);
            TwoStateButton volumeButton = new TwoStateButton(MaterialDesign.MDI_VOLUME_HIGH, MaterialDesign.MDI_VOLUME_OFF, "","",  ButtonAction.ActionVolumeOn,  ButtonAction.ActionVolumeOff, ButtonSize.Small, ButtonWidthType.Normal, engine, false, ButtonState.Active, UserPermissions.User);
            TwoStateButton wifiButton = new TwoStateButton(MaterialDesign.MDI_WIFI, MaterialDesign.MDI_WIFI_OFF, "ON","OFF",  ButtonAction.ActionWifiOn,  ButtonAction.ActionWifiOff, ButtonSize.Small, ButtonWidthType.Widthx2_5, engine, false, ButtonState.Active, UserPermissions.User);
            TwoStateButton themeButton = new TwoStateButton(MaterialDesign.MDI_THEME_LIGHT_DARK, MaterialDesign.MDI_THEME_LIGHT_DARK, "Dark","Light",  ButtonAction.ActionThemeDark,  ButtonAction.ActionThemeLight, ButtonSize.Small, ButtonWidthType.Widthx2_5, engine, true, ButtonState.Active, UserPermissions.User);

            SimpleButton dateTimeButton = new SimpleButton(MaterialDesign.MDI_TIMER, "Time",null, ButtonAction.ActionDateTimeSetup,  ButtonSize.Small, ButtonWidthType.Widthx5, engine, UserPermissions.Administrator);
            engine.SystemService.SystemDate.addListener((observableValue, s, t1) -> dateTimeButton.setDisplayText(s));

            topSimpleButtonObservableList.addAll(cameraButton,  keyboardButton,volumeButton, wifiButton, themeButton, dateTimeButton);
            topButtonsBox.getChildren().addAll(topSimpleButtonObservableList);

            TwoStateButton userButton = new TwoStateButton(FluentUiFilledMZ.PERSON_DELETE_24, FluentUiFilledMZ.PERSON_ADD_24, "User", "Guest",  ButtonAction.ActionLogin,  ButtonAction.ActionLogoff, ButtonSize.Small, ButtonWidthType.Widthx5, engine, false, ButtonState.Inactive, UserPermissions.Guest);
            engine.CurrentUser.addListener((observable, oldValue, newValue) -> {

                Platform.runLater(() -> {
                    if (newValue.Id.getValue() == -1){
                        userButton.SelectState(ButtonState.Inactive);
                        userButton.setText(newValue.Name.getValue());
                        topGridPane.setEffect(engine.GuiService.GetShadow(engine.GuiService.AccentColor.getValue(),0, 0, 0, 15));
                    }else{

                        userButton.SelectState(ButtonState.Active);
                        userButton.setText(newValue.Name.getValue());
                        topGridPane.setEffect(engine.GuiService.GetShadow(engine.GuiService.AccentColor.getValue(), 0, 1.3, 0, 25));
                    }
                });
            });
            userPane.getChildren().add(userButton);
            engine.CurrentUser.set(new UserModel(-1,"Guest", UserPermissions.Guest));

    }



}