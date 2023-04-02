package com.sm.smarthome.Events;

import javafx.event.Event;
import javafx.event.EventType;

public abstract  class SmActionEvent extends Event {
    public SmActionEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public abstract void invokeHandler(ButtonEventHandler handler);

}
