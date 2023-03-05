package com.sm.smarthome.Events;

import com.sm.smarthome.Enums.Actions.ButtonAction;
import javafx.event.EventType;

import static com.sm.smarthome.Events.SmEventType.SM_ACTION_EVENT_EVENT_TYPE;

public class ButtonEvent extends SmActionEvent{

    public static final EventType<SmActionEvent> BUTTON_ACTION_EVENT_TYPE = new EventType<>(SM_ACTION_EVENT_EVENT_TYPE, "ButtonActionEvent");

    private final ButtonAction action;

    public ButtonEvent(ButtonAction action) {
        super(BUTTON_ACTION_EVENT_TYPE);
        this.action = action;
    }

    @Override
    public void invokeHandler(ButtonEventHandler handler) {
        handler.onButtonActionEvent(action);
    }

}
