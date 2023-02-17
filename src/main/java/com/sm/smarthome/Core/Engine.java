package com.sm.smarthome.Core;

import com.sm.smarthome.Core.Services.GuiService;
import com.sm.smarthome.Enums.ApplicationAction;
import com.sm.smarthome.Enums.ButtonAction;
import com.sm.smarthome.Core.Services.ActionEventService;
import com.sm.smarthome.Events.ButtonEvent;
import com.sm.smarthome.Events.SmEventHandler;
import javafx.stage.Stage;

public class Engine {

    public GuiService GuiProvider;
    public ActionEventService ActionEventService = new ActionEventService();

    public Engine(Stage mainStage){

        GuiProvider = new GuiService(mainStage);

        InitializeSmEventHandler(ActionEventService);
    }

    private void InitializeSmEventHandler(ActionEventService actionEventNode){
        actionEventNode.addEventHandler(ButtonEvent.BUTTON_ACTION_EVENT_TYPE, new SmEventHandler() {

            @Override
            public void onButtonActionEvent(ButtonAction action) {

            }

            @Override
            public void onApplicationActionEvent(ApplicationAction action) {

            }
        });
    }

}
