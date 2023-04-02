package com.sm.smarthome.Interfaces;

import com.sm.smarthome.Enums.Ui.Bottons.ButtonState;
import org.jetbrains.annotations.NotNull;
import org.kordamp.ikonli.Ikon;

public interface ITwoStateButton {
    Ikon getIconCodeActiveState();
    void setIconCodeActiveState(Ikon iconCodeActiveState);
    Ikon getIconCodeInactiveState();
    void setIconCodeInactiveState(Ikon iconCodeInactiveState);
    ButtonState getState();
    void setState(ButtonState state);
    void SelectState(@NotNull ButtonState state);
}
