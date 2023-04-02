package com.sm.smarthome.Controllers;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Core.Utils.Helpers;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Interfaces.IButton;
import com.sm.smarthome.Models.Data.UserModel;
import com.sm.smarthome.Models.Ui.Buttons.MarkButton;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class MainViewController {
    @FXML
    private HBox topButtonsBox;
    @FXML
    public VBox LeftButtonsBox;
    @FXML
    private VBox LeftButtonsBoxHeader;
    @FXML
    private SVGPath LeftButtonPaneArrow;
    @FXML
    private Pane userPane;
    @FXML
    public StackPane PagePane;
    @FXML
    private GridPane topGridPane;
    @FXML
    private AnchorPane anchorPane;


    public MainViewController(){}


    public void Initialize(Engine engine) {



        try {
            Platform.runLater(()->anchorPane.getChildren().add(engine.GuiService.MainMenu));

        }catch (Exception ignored){}

        engine.GuiService.MainMenu.widthProperty().addListener((observableValue, number, t1) -> {
            Platform.runLater(() -> AnchorPane.setLeftAnchor(engine.GuiService.MainMenu, (anchorPane.getWidth() - t1.doubleValue()) / 2));
        });


        engine.GuiService.PagesProvider.Buttons.addListener((ListChangeListener<MarkButton>) c -> {
            while (c.next()) {
                LeftButtonsBox.getChildren().clear();
                LeftButtonsBox.getChildren().addAll(c.getList());
                if (c.wasAdded()) {
                } else if (c.wasRemoved()) {
                }
            }
        });
        LeftButtonsBox.getChildren().addAll(engine.GuiService.PagesProvider.Buttons);



        engine.GuiService.PagesProvider.SetActivePageByIndex(0);

//            engine.ActionEventService.addEventHandler(ButtonEvent.BUTTON_ACTION_EVENT_TYPE, new SmEventHandler() {
//                @Override
//                public void onButtonActionEvent(ButtonAction action) {}
//                @Override
//                public void onApplicationActionEvent(ApplicationAction action) {}
//            });

        engine.GuiService.TopBarProvider.Buttons.addListener((ListChangeListener<IButton>) c -> {
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
        for (IButton button : engine.GuiService.TopBarProvider.Buttons) {
            topButtonsBox.getChildren().add(button.getNode());
        }



             engine.CurrentUser.addListener((observable, oldValue, newValue) -> {

                Platform.runLater(() -> {
                    if (newValue.Id.getValue() == -1){
                        topGridPane.setEffect(Helpers.GetShadow(engine.GuiService.AccentColor,0, 0, 0, 15, false));
                    }else{

                        topGridPane.setEffect(Helpers.GetShadow(engine.GuiService.AccentColor, 0, 1.3, 0, 25, false));
                    }
                });
            });
            userPane.getChildren().add(engine.GuiService.TopBarProvider.userButton);
            engine.CurrentUser.set(new UserModel(-1,"Guest", UserPermissions.Guest));


            LeftButtonsBox.widthProperty().addListener((observableValue, number, t1) -> {
                LeftButtonsBox.setTranslateX(-(t1.doubleValue() - 30));
                LeftButtonsBoxHeader.setPrefWidth(t1.doubleValue());
            });

            LeftButtonsBoxHeader.widthProperty().addListener((observableValue, number, t1) -> LeftButtonsBoxHeader.setTranslateX(-(t1.doubleValue() - 30)));
            LeftButtonsBoxHeader.setOnMouseClicked(mouseEvent -> toggleLeftButtonsBox());
    }

    private void toggleLeftButtonsBox() {


        TranslateTransition openNav = new TranslateTransition(new Duration(350), LeftButtonsBox);
        TranslateTransition openNav2 = new TranslateTransition(new Duration(350), LeftButtonsBoxHeader);
        openNav.setToX(0);
        openNav2.setToX(0);
        TranslateTransition closeNav = new TranslateTransition(new Duration(350), LeftButtonsBox);
        TranslateTransition closeNav2 = new TranslateTransition(new Duration(350), LeftButtonsBoxHeader);

        RotateTransition arrow = new RotateTransition(new Duration(550), LeftButtonPaneArrow);
        arrow.setAxis(Rotate.Z_AXIS);

        if(LeftButtonsBox.getTranslateX()!=0){
            LeftButtonsBox.setDisable(false);
            openNav.play();
        }else{
            LeftButtonsBox.setDisable(true);
            closeNav.setToX(-(LeftButtonsBox.getWidth() - 30));
            closeNav.play();
        }

        if(LeftButtonsBoxHeader.getTranslateX()!=0){
            openNav2.play();
            arrow.setByAngle(180);
            arrow.play();

        }else{
            closeNav2.setToX(-(LeftButtonsBoxHeader.getWidth() - 30));
            arrow.setByAngle(180);
            arrow.play();
            closeNav2.play();
        }

    }

}