package com.sm.smarthome.EmbededApps;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonState;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Models.Ui.Buttons.TwoStateButton;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.kordamp.ikonli.fluentui.FluentUiFilledAL;
import org.kordamp.ikonli.materialdesign2.MaterialDesignR;

public class InternetApp extends VBox {
    WebView webView = new WebView();
    String url = "https://www.google.com/";
    public InternetApp(Engine engine) {
        super();


        HBox hBox = new HBox();
        hBox.setSpacing(20);

        TextField textFieldAddress = new TextField(url);
        textFieldAddress.setFont(new Font(48));
        textFieldAddress.setPrefWidth(700);
        textFieldAddress.setPrefHeight(64);

        TwoStateButton buttonBack = new TwoStateButton(FluentUiFilledAL.ARROW_PREVIOUS_24, FluentUiFilledAL.ARROW_PREVIOUS_24, null, null,  ButtonAction.None,  ButtonAction.None, ButtonSize.Medium, ButtonWidthType.Normal, engine, false, ButtonState.Inactive, UserPermissions.Guest, false);
        buttonBack.setOnAction(actionEvent ->
                Platform.runLater(() -> {
                    webView.getEngine().executeScript("history.back()");
                    textFieldAddress.setText(webView.getEngine().getLocation());
                }));

        TwoStateButton buttonNext = new TwoStateButton(FluentUiFilledAL.ARROW_NEXT_24, FluentUiFilledAL.ARROW_NEXT_24, null, null,  ButtonAction.None,  ButtonAction.None, ButtonSize.Medium, ButtonWidthType.Normal, engine, false, ButtonState.Inactive, UserPermissions.Guest, false);
        buttonNext.setOnAction(actionEvent ->
                Platform.runLater(() -> {
                    webView.getEngine().executeScript("history.forward()");
                    textFieldAddress.setText(webView.getEngine().getLocation());
                }));

        TwoStateButton buttonReload = new TwoStateButton(MaterialDesignR.RELOAD, MaterialDesignR.RELOAD, null, null,  ButtonAction.None,  ButtonAction.None, ButtonSize.Medium, ButtonWidthType.Normal, engine, false, ButtonState.Inactive, UserPermissions.Guest, false);
        buttonReload.setOnAction(actionEvent ->
                Platform.runLater(() -> {
                    webView.getEngine().reload();
                    textFieldAddress.setText(webView.getEngine().getLocation());
                }));

        TwoStateButton buttonEnter = new TwoStateButton(FluentUiFilledAL.ARROW_ENTER_24, FluentUiFilledAL.ARROW_ENTER_24, null, null,  ButtonAction.None,  ButtonAction.None, ButtonSize.Medium, ButtonWidthType.Normal, engine, false, ButtonState.Inactive, UserPermissions.Guest, false);
        buttonEnter.setOnAction(actionEvent ->
                Platform.runLater(() -> {
                    webView.getEngine().load(textFieldAddress.getText());
                }));



        textFieldAddress.textProperty().addListener((observableValue, s, t1) -> {
            if (!webView.getEngine().getLocation().equals(t1)){

            }
        });
        hBox.getChildren().addAll(buttonBack, buttonNext, buttonReload, textFieldAddress, buttonEnter);

        hBox.setPadding(new Insets(0, 0, 5, 5));

        webView.getEngine().load(url);
        this.getChildren().addAll(hBox, webView);
    }

    public void SetOwner(Stage owner) {
        webView.prefHeightProperty().bind(owner.heightProperty());
        webView.prefWidthProperty().bind(owner.widthProperty());
    }
}
