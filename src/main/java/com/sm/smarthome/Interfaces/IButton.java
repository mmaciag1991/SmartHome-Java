package com.sm.smarthome.Interfaces;

import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonNodeType;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import org.kordamp.ikonli.Ikon;

public interface IButton {

    Ikon getIconCode();
    void setIconCode(Ikon iconCode);

    String getUUID();

    String getDisplayText();
    void setDisplayText(String text);

    Object getValue();
    void setValue(Object value);

    ButtonAction getAction();
    void setAction(ButtonAction action);

    ButtonWidthType getWidthType();
    void setWidthType(ButtonWidthType widthType);

    ButtonSize getSize();
    void setSize(ButtonSize size);

    ButtonNodeType getButtonNodeType();

}
