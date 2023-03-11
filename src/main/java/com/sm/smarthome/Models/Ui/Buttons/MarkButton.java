package com.sm.smarthome.Models.Ui.Buttons;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Core.Services.ActionEventService;
import com.sm.smarthome.Core.Utils.Helpers;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonState;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Events.ButtonEvent;
import javafx.application.Platform;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.Ikon;

public class MarkButton extends SimpleButton {

    private ButtonState state = ButtonState.Inactive;

    public MarkButton(Ikon iconCode, String displayText, Object value, ButtonAction action, ButtonSize size, ButtonWidthType widthType, Engine engine, UserPermissions permissions) {
        super(iconCode, displayText, value, action, size, widthType, engine, permissions);
        setState(ButtonState.Inactive);
        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> setState(state));
    }

    @Override
    public void InitializeFireEvent(ActionEventService actionEventNode){
        this.setOnAction(event -> {
            if (CheckUserPermissions(this.permissions)) {
                actionEventNode.fireEvent(new ButtonEvent(MarkButton.this.getAction()));
                setState(ButtonState.Active);
            }
        });
    }

    public void setInactive() {setState(ButtonState.Inactive);}
    public void setState(ButtonState state){
        this.state = state;
        Color color = engine.GuiService.AccentColor.getValue();
        Platform.runLater(() ->{
            switch (state){
                    case Active -> {
                        this.setFocused(false);
                        this.setStyle("-fx-border-color: "+ Helpers.GetRgbaColorToStyleFx(color, .9) +" -fx-border-width: 0 5 0 0;");
                        //this.setEffect(engine.GuiService.GetShadow(engine.GuiService.AccentColor,1.3, 0, 15,0, false));
                    }
                    case Inactive -> {
                        this.setStyle("-fx-border-color: "+ Helpers.GetRgbaColorToStyleFx(color, .3) +" -fx-border-width: 0 5 0 0;");
                        //this.setEffect(engine.GuiService.GetShadow(engine.GuiService.AccentColor,0, 0, 15, 0, false));
                    }
            }
        });
    }
}
