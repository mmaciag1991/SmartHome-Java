package com.sm.smarthome.Events.Catalog;

import javafx.event.Event;
import javafx.event.EventType;

public abstract  class CatalogActionEvent extends Event {
    public CatalogActionEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public abstract void invokeHandler(CatalogEventHandler handler);

}
