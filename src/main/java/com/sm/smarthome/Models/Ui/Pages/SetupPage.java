package com.sm.smarthome.Models.Ui.Pages;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Core.Providers.Gui.WindowManager;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Enums.Ui.Catalog.CatalogAction;
import com.sm.smarthome.Enums.Ui.Pages.PageType;
import com.sm.smarthome.Events.Catalog.CatalogEvent;
import com.sm.smarthome.Events.Catalog.CatalogEventHandler;
import com.sm.smarthome.Models.Ui.Buttons.MarkButton;
import com.sm.smarthome.Models.Ui.Menu.Catalog.StdCatalog;
import javafx.application.Platform;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class SetupPage extends PageBase{

    WindowManager windowManager;
    public SetupPage(Engine engine){

        windowManager = new WindowManager();

        this.Index = 3;
        this.Name = "Setup Page";
        this.DisplayName = "Setup";
        this.Type = PageType.Main;
        //wytgląd jak tiles oraz jakis window menager888

        this.BodyInstance = windowManager.GetManagerWindowNode();
        StdCatalog setupCatalog = new StdCatalog(MaterialDesign.MDI_SETTINGS, DisplayName, UserPermissions.Guest, engine ,windowManager);
        setupCatalog.addEventFilter(CatalogEvent.CATALOG_ACTION_EVENT_TYPE, new CatalogEventHandler() {
            @Override
            public void onCatalogActionEvent(CatalogAction action) {
                switch (action) {
                    case Open -> {

                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_QRCODE, "Qr Code", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_QRCODE, "Qr Code", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_QRCODE, "Qr Code", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_QRCODE, "Qr Code", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_QRCODE, "Qr Code", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_QRCODE, "Qr Code", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_QRCODE, "Qr Code", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_QRCODE, "Qr Code", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_QRCODE, "Qr Code", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_WIFI, "Wifi", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CROWN, "Crown", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_CONSOLE, "Console", UserPermissions.Administrator, engine, windowManager));
                        setupCatalog.Add(new StdCatalog(MaterialDesign.MDI_ANDROID, "Android", UserPermissions.Administrator, engine, windowManager));
                    }
                }
            }
        });
        Platform.runLater(setupCatalog::Open);


        this.Button = new MarkButton(MaterialDesign.MDI_SETTINGS, DisplayName,null, ButtonAction.ActionSetupPage,  ButtonSize.Big, ButtonWidthType.Widthx3, engine, UserPermissions.Guest);
        this.Button.setDisableVisualFocus(true);
    }
}
