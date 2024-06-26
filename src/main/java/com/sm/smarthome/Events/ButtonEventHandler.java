package com.sm.smarthome.Events;

import com.sm.smarthome.Enums.Actions.ApplicationAction;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import javafx.event.EventHandler;

public abstract class ButtonEventHandler implements EventHandler<SmActionEvent> {

    public abstract void onButtonActionEvent(ButtonAction action);

    public abstract void onApplicationActionEvent(ApplicationAction action);

    @Override
    public void handle(SmActionEvent event) {
        event.invokeHandler(this);
    }

}
