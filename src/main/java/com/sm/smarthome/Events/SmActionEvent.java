package com.sm.smarthome.Events;

import javafx.event.Event;
import javafx.event.EventType;

public abstract  class SmActionEvent extends Event {

    public static final EventType<SmActionEvent> SM_ACTION_EVENT_EVENT_TYPE = new EventType<>(ANY);


    public SmActionEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public abstract void invokeHandler(SmEventHandler handler);

}
