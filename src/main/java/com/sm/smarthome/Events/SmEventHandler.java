package com.sm.smarthome.Events;

import com.sm.smarthome.Enums.ApplicationAction;
import com.sm.smarthome.Enums.ButtonAction;
import javafx.event.EventHandler;

public abstract class SmEventHandler implements EventHandler<SmActionEvent> {

    public abstract void onButtonActionEvent(ButtonAction action);

    public abstract void onApplicationActionEvent(ApplicationAction action);

    @Override
    public void handle(SmActionEvent event) {
        event.invokeHandler(this);
    }

}
