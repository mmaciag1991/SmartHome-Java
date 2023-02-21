package com.sm.smarthome.Models.Ui.Buttons;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Core.Services.ActionEventService;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonState;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Events.ButtonEvent;
import com.sm.smarthome.Interfaces.ITwoStateButton;
import javafx.application.Platform;
import javafx.scene.control.Control;
import org.jetbrains.annotations.NotNull;
import org.kordamp.ikonli.Ikon;

public class TwoStateButton extends SimpleButton implements ITwoStateButton {

    private ButtonState state = ButtonState.Inactive;
    private Ikon iconCodeActiveState, iconCodeInactiveState;
    private final String displayTextActiveState, displayTextInactiveState;
    private final ButtonAction actionActiveState, actionInactiveState;
    private final boolean autoSize;

    public TwoStateButton(Ikon iconCodeActiveState, Ikon iconCodeInactiveState, String displayTextActiveState, String displayTextInactiveState, ButtonAction actionActiveState, ButtonAction actionInactiveState, ButtonSize size, ButtonWidthType widthType, Engine engine, boolean autoSize, ButtonState initialState, UserPermissions permissions) {
        super( iconCodeActiveState, displayTextActiveState, null, actionActiveState, size, widthType, engine, permissions);
        this.iconCodeActiveState = iconCodeActiveState;
        this.iconCodeInactiveState = iconCodeInactiveState;
        this.displayTextActiveState = displayTextActiveState;
        this.displayTextInactiveState = displayTextInactiveState;
        this.actionActiveState = actionActiveState;
        this.actionInactiveState = actionInactiveState;
        this.autoSize = autoSize;
        SelectState(initialState);
        getStyleClass().add("check-box");
        this.setStyle("-fx-border-color: transparent; -fx-border-width: 0 0 0 0;");
    }

    /**
     * Inicjalizacja zdarzenia przechwytywania akcji
     * @param actionEventNode obiekt globalny przechwycujacy akcje
     */
    @Override
    public void InitializeFireEvent(ActionEventService actionEventNode){
        Platform.runLater(() -> this.setOnAction(event -> {
            if (CheckUserPermissions(this.permissions)) {
                SelectState(state == ButtonState.Active ? ButtonState.Inactive : ButtonState.Active);
                actionEventNode.fireEvent(new ButtonEvent(TwoStateButton.this.getAction()));
            }
        }));
    }

    /**
     * Ustawia status przycisku
     * @param state Status przycisku aktywny lub nieaktywny
     */
    public void SelectState(@NotNull ButtonState state){
        if (state == ButtonState.Active) {
            setIconCode(iconCodeActiveState);
            setDisplayText(displayTextActiveState);
            setAction(actionActiveState);
        } else {
            setIconCode(iconCodeInactiveState);
            setDisplayText(displayTextInactiveState);
            setAction(actionInactiveState);
        }
        this.state = state;
        Repaint();
        preformAutoSize();
        setValue(state);

            fontIcon.setIconColor(engine.GuiService.FontColor.getValue());
    }

    /**
     * Automatyczne ustawianie szerokosci
     */
    private void preformAutoSize(){
        if (!autoSize) return;
        setMinWidth(Control.USE_COMPUTED_SIZE);
        setMaxWidth(Control.USE_COMPUTED_SIZE);
        setPrefWidth(Control.USE_COMPUTED_SIZE);
    }

    public Ikon getIconCodeActiveState() {
        return iconCodeActiveState;
    }

    public void setIconCodeActiveState(Ikon iconCodeActiveState) {
        this.iconCodeActiveState = iconCodeActiveState;
    }

    public Ikon getIconCodeInactiveState() {
        return iconCodeInactiveState;
    }

    public void setIconCodeInactiveState(Ikon iconCodeInactiveState) {
        this.iconCodeInactiveState = iconCodeInactiveState;
    }

    public ButtonState getState() {
        return state;
    }

    public void setState(ButtonState state) {
        this.state = state;
    }
}
