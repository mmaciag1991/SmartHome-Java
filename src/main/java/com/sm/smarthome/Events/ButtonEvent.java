package com.sm.smarthome.Events;

import com.sm.smarthome.Enums.ButtonAction;
import javafx.event.EventType;

public class ButtonEvent extends SmActionEvent{

    public static final EventType<SmActionEvent> BUTTON_ACTION_EVENT_TYPE = new EventType<>(SM_ACTION_EVENT_EVENT_TYPE, "ButtonActionEvent");

    private final ButtonAction param;

    public ButtonEvent(ButtonAction param) {
        super(BUTTON_ACTION_EVENT_TYPE);
        this.param = param;
    }

    @Override
    public void invokeHandler(SmEventHandler handler) {
        handler.onButtonActionEvent(param);
    }

}
