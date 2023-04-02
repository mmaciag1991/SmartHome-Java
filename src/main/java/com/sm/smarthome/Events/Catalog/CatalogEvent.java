package com.sm.smarthome.Events.Catalog;

import com.sm.smarthome.Enums.Ui.Catalog.CatalogAction;
import javafx.event.EventType;

import static com.sm.smarthome.Events.SmEventType.SM_ACTION_EVENT_EVENT_TYPE;

public class CatalogEvent extends CatalogActionEvent {

    public static final EventType<CatalogActionEvent> CATALOG_ACTION_EVENT_TYPE = new EventType<>(SM_ACTION_EVENT_EVENT_TYPE, "CatalogActionEvent");

    private final CatalogAction action;

    public CatalogEvent(CatalogAction action) {
        super(CATALOG_ACTION_EVENT_TYPE);
        this.action = action;
    }

    @Override
    public void invokeHandler(CatalogEventHandler handler) {
        handler.onCatalogActionEvent(action);
    }

}
