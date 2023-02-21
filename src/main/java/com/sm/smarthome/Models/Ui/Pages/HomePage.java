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

public class HomePage extends PageBase{
    public HomePage(Engine engine){
        this.Index = 0;
        this.Name = "Home Page";
        this.DisplayName = "Home";
        this.Type = PageType.Main;
        this.BodyInstance = new TextArea("Home page test ..");
        this.Button = new MarkButton(MaterialDesign.MDI_HOME, DisplayName,null, ButtonAction.ActionHomePage, ButtonSize.Big, ButtonWidthType.Widthx2, engine, UserPermissions.Guest);
    }
}
