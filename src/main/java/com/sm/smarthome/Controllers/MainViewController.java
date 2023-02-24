package com.sm.smarthome.Controllers;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonState;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Interfaces.IButton;
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


        engine.PagesProvider.ActivePage.addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (oldValue != null) oldValue.Button.setInactive();
                newValue.Button.setState(ButtonState.Active);
                engine.ControlersProvider.MainViewController.PagePane.getChildren().clear();
                engine.ControlersProvider.MainViewController.PagePane.getChildren().add(newValue.BodyInstance);
            });
            //engine.PagesProvider.Buttons.addAll( new MarkButton(MaterialDesign.MDI_HOME, "DisplayName",null, ButtonAction.ActionHomePage, ButtonSize.Big, ButtonWidthType.Widthx2, engine, UserPermissions.Guest));
        });

        engine.PagesProvider.Buttons.addListener((ListChangeListener<MarkButton>) c -> {
            while (c.next()) {
                LeftButtonsBox.getChildren().clear();
                LeftButtonsBox.getChildren().addAll(c.getList());
                if (c.wasAdded()) {
                } else if (c.wasRemoved()) {
                }
            }
        });
        LeftButtonsBox.getChildren().addAll(engine.PagesProvider.Buttons);



        engine.PagesProvider.SetActivePageByIndex(0);

//            engine.ActionEventService.addEventHandler(ButtonEvent.BUTTON_ACTION_EVENT_TYPE, new SmEventHandler() {
//                @Override
//                public void onButtonActionEvent(ButtonAction action) {}
//                @Override
//                public void onApplicationActionEvent(ApplicationAction action) {}
//            });

        engine.TopBarProvider.Buttons.addListener((ListChangeListener<IButton>) c -> {
            while (c.next()) {
                LeftButtonsBox.getChildren().clear();
                for (IButton iButton : c.getList()) {
                    LeftButtonsBox.getChildren().add(iButton.getNode());
                }
                if (c.wasAdded()) {
                } else if (c.wasRemoved()) {
                }
            }
        });
        for (IButton button : engine.TopBarProvider.Buttons) {
            topButtonsBox.getChildren().add(button.getNode());
        }



             engine.CurrentUser.addListener((observable, oldValue, newValue) -> {

                Platform.runLater(() -> {
                    if (newValue.Id.getValue() == -1){
                        topGridPane.setEffect(engine.GuiService.GetShadow(engine.GuiService.AccentColor,0, 0, 0, 15, false));
                    }else{

                        topGridPane.setEffect(engine.GuiService.GetShadow(engine.GuiService.AccentColor, 0, 1.3, 0, 25, false));
                    }
                });
            });
            userPane.getChildren().add(engine.TopBarProvider.userButton);
            engine.CurrentUser.set(new UserModel(-1,"Guest", UserPermissions.Guest));

    }



}