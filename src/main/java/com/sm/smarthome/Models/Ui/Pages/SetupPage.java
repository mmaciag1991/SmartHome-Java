package com.sm.smarthome.Models.Ui.Pages;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Enums.Ui.Pages.PageType;
import com.sm.smarthome.Models.Ui.Buttons.MarkButton;
import javafx.scene.control.TextArea;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class SetupPage extends PageBase{
    public SetupPage(Engine engine){
        this.Index = 2;
        this.Name = "Setup Page";
        this.DisplayName = "Setup";
        this.Type = PageType.Main;
        this.BodyInstance = new TextArea("Setup page test ..");
        this.Button = new MarkButton(MaterialDesign.MDI_SETTINGS, DisplayName,null, ButtonAction.ActionSetupPage,  ButtonSize.Big, ButtonWidthType.Widthx2, engine, UserPermissions.Guest);
    }
}
