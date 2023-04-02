package com.sm.smarthome.Events.Catalog;

import com.sm.smarthome.Enums.Ui.Catalog.CatalogAction;
import javafx.event.EventHandler;

public abstract class CatalogEventHandler implements EventHandler<CatalogActionEvent> {
    public abstract void onCatalogActionEvent(CatalogAction action);
    @Override
    public void handle(CatalogActionEvent event) {
        event.invokeHandler(this);
    }

}
