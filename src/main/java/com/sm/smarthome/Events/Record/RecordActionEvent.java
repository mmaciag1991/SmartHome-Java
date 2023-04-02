package com.sm.smarthome.Events.Record;

import com.sm.smarthome.Events.Catalog.CatalogEventHandler;
import javafx.event.Event;
import javafx.event.EventType;

public abstract  class RecordActionEvent extends Event {
    public RecordActionEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public abstract void invokeHandler(RecordEventHandler handler);

}
