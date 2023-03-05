package com.sm.smarthome.Interfaces;

import javafx.scene.Node;

public interface IRecord {
    IRecordButton getCatalogButton();
    Node getNode();
    String getUUID();
    void Add(IRecord record);
    void Open();
    void Exit();
    void Edit();
    void Delete();
    void Reload();
    void Search();

}
