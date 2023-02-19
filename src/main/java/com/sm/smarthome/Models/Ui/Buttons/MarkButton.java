package com.sm.smarthome.Models.Ui.Buttons;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Core.Services.ActionEventService;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonState;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Events.ButtonEvent;
import org.kordamp.ikonli.Ikon;

public class MarkButton extends SimpleButton {

    private ButtonState state = ButtonState.Inactive;
    public MarkButton(Ikon iconCode, String displayText, ButtonAction action) {
        super(iconCode, displayText, action);
        setInactive();
    }

    public MarkButton(Ikon iconCode, String displayText, Object value, ButtonAction action, ButtonSize size, ButtonWidthType widthType, Engine engine) {
        super(iconCode, displayText, value, action, size, widthType, engine);
        setInactive();
    }

    @Override
    public void InitializeFireEvent(ActionEventService actionEventNode){
        this.setOnAction(event -> {
            actionEventNode.fireEvent(new ButtonEvent(MarkButton.this.getAction()));
            setState(ButtonState.Active);
        });
    }

    public void setInactive() {setState(ButtonState.Inactive);}
    public void setState(ButtonState state){
        this.state = state;
        switch (state){

            case Active -> {
                this.setStyle("-fx-border-color: red; -fx-border-width: 0 5 0 0;");
            }
            case Inactive -> {
                this.setStyle("-fx-border-color: transparent; -fx-border-width: 0 5 0 0;");
            }
        }
    }
}
