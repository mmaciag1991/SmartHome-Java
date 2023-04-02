package com.sm.smarthome.Events.Record;

import com.sm.smarthome.Enums.Ui.Record.RecordAction;
import com.sm.smarthome.Events.Catalog.CatalogActionEvent;
import javafx.event.EventHandler;

public abstract class RecordEventHandler implements EventHandler<RecordActionEvent> {
    public abstract void onRecordActionEvent(RecordAction action);
    @Override
    public void handle(RecordActionEvent event) {
        event.invokeHandler(this);
    }

}
