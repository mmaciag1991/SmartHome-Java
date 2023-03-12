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
import javafx.scene.paint.Color;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import static com.sm.smarthome.Core.Utils.Helpers.CheckUserPermissions;

public class RadiusButton extends SimpleButton{

    private ButtonState state = ButtonState.Inactive;
    public RadiusButton(ButtonAction action, Engine engine, UserPermissions permissions) {
        super(MaterialDesign.MDI_BORDER_NONE, null, 0, action, ButtonSize.ExtraSmall, ButtonWidthType.Widthx3, engine, permissions);
        setGraphic(null);
        setDefaultButton(true);
        setState(state);
        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> setState(state));
    }
    @Override
    public void InitializeFireEvent(ActionEventService actionEventNode){
        this.setOnAction(event -> {
            if (CheckUserPermissions(this.permissions, engine, this.getNode())) {
                actionEventNode.fireEvent(new ButtonEvent(RadiusButton.this.getAction()));
                setState(ButtonState.Active);
            }
        });
    }
    public void setState(ButtonState state){
        this.state = state;
        Color color = engine.GuiService.AccentColor.getValue();
        Platform.runLater(() ->{
            switch (state){
                case Active -> {
                    this.setFocused(false);
                    this.setStyle("-fx-border-color: "+ Helpers.GetRgbaColorToStyleFx(color, .7) +";"
                            + "-fx-border-style: solid;"
                            + "-fx-border-width: 7;"
                            + "-fx-background-color:"+ Helpers.GetRgbaColorToStyleFx(color, .1) +";"
                            + "-fx-background-radius: 10 10 10 10;"
                            + "-fx-border-radius: 10 10 10 10;");
                    setWidth(80);
                    setHeight(24);
                }
                case Inactive -> {
                    this.setStyle("-fx-border-color: "+ Helpers.GetRgbaColorToStyleFx(color, .4) +";"
                            + "-fx-border-style: solid;"
                            + "-fx-border-width: 7;"
                            + "-fx-background-color:"+ Helpers.GetRgbaColorToStyleFx(color, .1) +";"
                            + "-fx-background-radius: 10 10 10 10;"
                            + "-fx-border-radius: 10 10 10 10;");
                    setWidth(80);
                    setHeight(32);
                }
            }
        });
    }
}
