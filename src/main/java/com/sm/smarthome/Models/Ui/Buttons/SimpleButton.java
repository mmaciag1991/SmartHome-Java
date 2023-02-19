package com.sm.smarthome.Models.Ui.Buttons;

import com.jfoenix.controls.JFXButton;
import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Core.Services.ActionEventService;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonNodeType;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Events.ButtonEvent;
import com.sm.smarthome.Interfaces.IButton;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

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

    private FontIcon fontIcon;

    public SimpleButton(Ikon iconCode, String displayText, ButtonAction action){

        setIconCode(iconCode);
        setDisplayText(displayText);
        setAction(action);
        setWidthType(ButtonWidthType.Normal);
        setSize(ButtonSize.Small);

    }
    public SimpleButton(Ikon iconCode, String displayText, Object value, ButtonAction action, ButtonSize size, ButtonWidthType widthType, Engine engine){

        setIconCode(iconCode);
        setDisplayText(displayText);
        setValue(value);
        setAction(action);
        setWidthType(widthType);
        setSize(size);
        InitializeFireEvent(engine.ActionEventService);
        engine.GuiProvider.FontColor.addListener((observable, oldValue, newValue) -> {
            fontIcon.setIconColor(newValue);
        });
    }

    public void InitializeFireEvent(ActionEventService actionEventNode){
        this.setOnAction(event -> actionEventNode.fireEvent(new ButtonEvent(SimpleButton.this.getAction())));
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