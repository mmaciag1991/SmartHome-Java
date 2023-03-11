package com.sm.smarthome.Models.Ui.Pages;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Core.Providers.Gui.WindowManager;
import com.sm.smarthome.EmbededApps.InternetApp;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Enums.Ui.Catalog.CatalogAction;
import com.sm.smarthome.Enums.Ui.Pages.PageType;
import com.sm.smarthome.Enums.Ui.Record.RecordAction;
import com.sm.smarthome.Events.Catalog.CatalogEvent;
import com.sm.smarthome.Events.Catalog.CatalogEventHandler;
import com.sm.smarthome.Events.Record.RecordEvent;
import com.sm.smarthome.Events.Record.RecordEventHandler;
import com.sm.smarthome.Models.Ui.Buttons.AppStage;
import com.sm.smarthome.Models.Ui.Buttons.MarkButton;
import com.sm.smarthome.Models.Ui.Menu.Catalog.StdCatalog;
import com.sm.smarthome.Models.Ui.Menu.Record;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.Objects;

public class ApplicationsPage extends PageBase{

    WindowManager windowManager;
    public ApplicationsPage(Engine engine){

        windowManager = new WindowManager();

        this.Index = 2;
        this.Name = "Applications Page";
        this.DisplayName = "Applications";
        this.Type = PageType.Main;
        //wytgląd jak tiles oraz jakis window menager888

        this.BodyInstance = windowManager.GetManagerWindowNode();

        StdCatalog setupCatalog = new StdCatalog(MaterialDesign.MDI_SETTINGS, DisplayName, UserPermissions.Guest,engine, windowManager);
        setupCatalog.addEventFilter(CatalogEvent.CATALOG_ACTION_EVENT_TYPE, new CatalogEventHandler() {
            @Override
            public void onCatalogActionEvent(CatalogAction action) {
                switch (action) {
                    case Open -> {
                        Record webView = new Record(MaterialDesign.MDI_WEB, "Web Browser", RecordAction.Open,UserPermissions.Administrator, engine);
                        webView.getRecordButton().getNode().addEventFilter(RecordEvent.RECORD_ACTION_EVENT_TYPE, new RecordEventHandler() {
                            @Override
                            public void onRecordActionEvent(RecordAction action) {
                                if (action == RecordAction.Open) {

                                    InternetApp internetApp = new InternetApp(engine);
                                    AppStage stage = new AppStage("Web Browser", internetApp, engine);
                                    internetApp.SetOwner(stage);
                                    stage.show();
                                }
                            }
                        });
                        setupCatalog.Add(webView);
                    }
                }
            };
        });
        Platform.runLater(setupCatalog::Open);

        this.Button = new MarkButton(MaterialDesign.MDI_APPLICATION, DisplayName,null, ButtonAction.ActionApplicationPage,  ButtonSize.Big, ButtonWidthType.Widthx3, engine, UserPermissions.Guest);
        this.Button.setDisableVisualFocus(true);
    }
}

//trzeba dorobic window menagera dla aplikacji albo wrzucic do setup