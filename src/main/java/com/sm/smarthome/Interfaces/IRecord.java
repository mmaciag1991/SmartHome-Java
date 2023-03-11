package com.sm.smarthome.Interfaces;

import com.sm.smarthome.Core.Providers.Gui.WindowManager;
import javafx.scene.Node;

public interface IRecord {
    IRecordButton getRecordButton();
    String getUUID();
}
