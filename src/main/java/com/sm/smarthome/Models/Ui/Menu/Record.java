package com.sm.smarthome.Models.Ui.Menu;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Enums.Ui.Catalog.CatalogAction;
import com.sm.smarthome.Enums.Ui.Record.RecordAction;
import com.sm.smarthome.Events.Catalog.CatalogEvent;
import com.sm.smarthome.Events.Record.RecordEvent;
import com.sm.smarthome.Interfaces.IRecord;
import com.sm.smarthome.Interfaces.IRecordButton;
import com.sm.smarthome.Models.Ui.Buttons.CatalogButton;
import org.kordamp.ikonli.Ikon;

import static com.sm.smarthome.Core.Utils.Helpers.CheckUserPermissions;

public class Record implements IRecord {

    private IRecordButton recordButton;

    private final String UUID = java.util.UUID.randomUUID().toString();

    private final String displayText;
    private final Ikon iconCode;
    private final Engine engine;
    private final UserPermissions permissions;

    private RecordAction defaultRecordAction;
    public Record(Ikon iconCode, String displayText, RecordAction defaultRecordAction, UserPermissions permissions, Engine engine) {
        this.iconCode = iconCode;
        this.displayText = displayText;
        this.defaultRecordAction = defaultRecordAction;
        this.permissions = permissions;
        this.engine = engine;
        InitializeCatalogButton();
    }

    private void InitializeCatalogButton(){
        this.recordButton = new CatalogButton(iconCode, this.displayText, false, this.engine);
        ((CatalogButton)this.recordButton).setOnAction(actionEvent -> {
            if (CheckUserPermissions(this.permissions, engine, recordButton.getNode())) {
                recordButton.getNode().fireEvent(new RecordEvent(defaultRecordAction));
            }
        });
    }

    @Override
    public IRecordButton getRecordButton() {
        return recordButton;
    }
    @Override
    public String getUUID() {
        return UUID;
    }

}
