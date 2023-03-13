package com.sm.smarthome.EmbededApps;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonState;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Models.Ui.Buttons.TwoStateButton;
import com.sm.smarthome.Models.Ui.Stages.AppStage;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import org.kordamp.ikonli.fluentui.FluentUiFilledAL;
import org.kordamp.ikonli.materialdesign2.MaterialDesignR;

public class InternetApp extends VBox {
    WebView webView = new WebView();
    AppStage appStage;
    String url = "https://www.google.com/";
    public InternetApp(Engine engine) {
        super();

        webView.getEngine().setJavaScriptEnabled(true);


        WebEngine engineW = webView.getEngine();
        engineW.getLoadWorker().stateProperty().addListener(
                (observable, oldState, newState) -> {
                    if (newState == Worker.State.SUCCEEDED) {
                        JSObject window = (JSObject) engineW.executeScript("window");
                        window.setMember("java", new JavaBridge(webView, appStage));

                        String script =
                               "var inputsList = document.getElementsByTagName('input');"
                                        + "for (var index = 0; index < inputsList.length; ++index) { "
                                        + "inputsList[index].addEventListener(\"focus\",  window.java.sendMessage(\"focus\")); "
                                        + "inputsList[index].addEventListener(\"focusout\", window.java.sendMessage(\"focusout\")); "
                                        + "}";
                        webView.getEngine().executeScript(script);
                    }
                });

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

    public void SetOwner(AppStage owner) {
        //TODO: klawiatura dla webview
        webView.prefHeightProperty().bind(owner.heightProperty());
        webView.prefWidthProperty().bind(owner.widthProperty());
        this.appStage = owner;
        this.appStage.KeyboardPane.setContent(webView);
    }
    public void show() {
        com.sun.javafx.scene.control.skin.FXVK.init(webView);
        com.sun.javafx.scene.control.skin.FXVK.attach(webView);
    }

    public void hide() {
        com.sun.javafx.scene.control.skin.FXVK.detach();
    }
    public class JavaBridge {
        WebView webView;
        AppStage appStage;
        public JavaBridge(WebView webView, AppStage appStage) {
            this.webView = webView;
            this.appStage = appStage;
        }

        public void sendMessage(String message) {
            System.out.println(message);
            if (message.equals("focus")){
                appStage.KeyboardPane.maybeShowKeyboard();
            }else {
                appStage.KeyboardPane.hideKeyboard();

            }
        }
        public void show() {
            com.sun.javafx.scene.control.skin.FXVK.init(webView);
            com.sun.javafx.scene.control.skin.FXVK.attach(webView);
        }

        public void hide() {
            com.sun.javafx.scene.control.skin.FXVK.detach();
        }
    }
}

