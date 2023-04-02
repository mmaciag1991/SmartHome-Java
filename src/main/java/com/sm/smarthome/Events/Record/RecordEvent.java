package com.sm.smarthome.Events.Record;

import com.sm.smarthome.Enums.Ui.Record.RecordAction;
import javafx.event.EventType;

import static com.sm.smarthome.Events.SmEventType.SM_ACTION_EVENT_EVENT_TYPE;

public class RecordEvent extends RecordActionEvent {

    public static final EventType<RecordActionEvent> RECORD_ACTION_EVENT_TYPE = new EventType<>(SM_ACTION_EVENT_EVENT_TYPE, "RecordActionEvent");

    private final RecordAction action;

    public RecordEvent(RecordAction action) {
        super(RECORD_ACTION_EVENT_TYPE);
        this.action = action;
    }

    @Override
    public void invokeHandler(RecordEventHandler handler) {
        handler.onRecordActionEvent(action);
    }

}
