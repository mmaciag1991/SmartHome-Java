package com.sm.smarthome.Models.Ui.Buttons;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.sm.smarthome.Application;
import com.sm.smarthome.Controllers.MainViewController;
import com.sm.smarthome.Controllers.OtherControls.SimplePopupController;
import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Core.Services.ActionEventService;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonNodeType;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Enums.Ui.Otthers.PopupType;
import com.sm.smarthome.Events.ButtonEvent;
import com.sm.smarthome.Interfaces.IButton;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import jfxtras.styles.jmetro.JMetroStyleClass;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class SimpleButton extends JFXButton implements IButton {

    private Ikon iconCode;
    private final String UUID = java.util.UUID.randomUUID().toString();
    private String displayText;
    private Object value;
    private ButtonAction action;
    private ButtonSize size;
    private ButtonWidthType widthType;

    private int width = 32;
    private int height = 32;

    protected FontIcon fontIcon;

    protected Engine engine;
    protected UserPermissions permissions;

    public SimpleButton(Ikon iconCode, String displayText, Object value, ButtonAction action, ButtonSize size, ButtonWidthType widthType, Engine engine, UserPermissions permissions){

        this.engine = engine;
        setIconCode(iconCode);
        setDisplayText(displayText);
        setValue(value);
        setAction(action);
        setWidthType(widthType);
        setSize(size);
        InitializeFireEvent(engine.ActionEventService);
        this.permissions = permissions;
        fontIcon.setIconColor(engine.GuiService.FontColor.getValue());
        engine.GuiService.FontColor.addListener((observable, oldValue, newValue) -> {
            fontIcon.setIconColor(newValue);
        });
    }

    public void InitializeFireEvent(ActionEventService actionEventNode){
        this.setOnAction(event -> {
            if (CheckUserPermissions(this.permissions))
            actionEventNode.fireEvent(new ButtonEvent(SimpleButton.this.getAction()));
        });
    }

    protected boolean CheckUserPermissions(UserPermissions permissions){

        boolean hasPermissions = engine.CurrentUser.getValue().UserPermission.getValue().getValue() >= permissions.getValue();

        if (!hasPermissions)
        {
            JFXPopup jfxPopup = new JFXPopup();
            jfxPopup.getStyleClass().add("background");
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Views/OtherControls/SimplePopup.fxml"));
            Parent parent = null;
            try {
                parent = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            parent.getStyleClass().add(JMetroStyleClass.BACKGROUND);
            SimplePopupController mainViewController = fxmlLoader.getController();
            mainViewController.Initialize("Brak uprawnień", PopupType.Error);
            Pane pane = new Pane(parent);
            jfxPopup.setPopupContent(pane);
            jfxPopup.show(this);
        }
        return hasPermissions;
    }

    public void Repaint(){
        setWidthType(widthType);
    }

    public Ikon getIconCode(){
        return iconCode;
    }
    public void setIconCode(Ikon iconCode) {
        this.iconCode = iconCode;
        fontIcon = new FontIcon(this.iconCode);
        setGraphic(fontIcon);
    }

    public String getUUID() {
        return UUID;
    }

    public String getDisplayText(){
        return displayText;
    }
    public void setDisplayText(String text) {
        displayText = text;
        setText(displayText);
    }

    public Object getValue(){
        return value;
    }
    public void setValue(Object value){
        this.value = value;
    }

    public ButtonAction getAction(){
        return action;
    }
    public void setAction(ButtonAction action) {
        this.action = action;
    }

    public ButtonWidthType getWidthType(){
        return widthType;
    }
    public void setWidthType(ButtonWidthType widthType) {
        this.widthType = widthType;
        if (size != null) setSize(size);
    }

    public ButtonSize getSize(){
        return size;
    }
    public void setSize(ButtonSize size) {
        this.size = size;

        switch (this.size){
            case Big ->{
                width = 64;
                height = 64;
                fontIcon.setIconSize(48);
            }
            case Medium -> {
                width = 48;
                height = 48;
                fontIcon.setIconSize(32);
            }
            case Small -> {
                width = 32;
                height = 32;
                fontIcon.setIconSize(24);
            }
            case ExtraSmall -> {
                width = 24;
                height = 24;
                fontIcon.setIconSize(16);
            }
        }

        if (widthType == null) widthType = ButtonWidthType.Normal;

        width *= widthType.getValue();

        if (widthType == ButtonWidthType.Normal){
            setAlignment(Pos.CENTER);
            setContentDisplay(ContentDisplay.TOP);
        }else{
            setAlignment(Pos.CENTER_LEFT);
            setContentDisplay(ContentDisplay.LEFT);
        }

        setMinSize(width, height);
        setMaxSize(width, height);
    }

    public ButtonNodeType getButtonNodeType(){
        return ButtonNodeType.SimpleButton;
    }

}