package com.sm.smarthome.Interfaces;

import com.sm.smarthome.Core.Providers.Gui.WindowManager;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public interface ICatalog extends IRecord {
    GridPane getTopBar();
    Node getNode();
    void Add(IRecord record);
    void Open();
    void Exit();
    void Reload();
    void Search();
    String getDisplayText();
    WindowManager getWindowManager();
}
