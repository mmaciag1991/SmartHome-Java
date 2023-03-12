package com.sm.smarthome.Models.Ui.Buttons;

import com.jfoenix.controls.JFXButton;
import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Interfaces.IRecordButton;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.input.MouseEvent;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class CatalogButton extends JFXButton implements IRecordButton {

    Engine engine;
    public CatalogButton(Ikon iconCode, String displayText, boolean transparent,Engine engine) {
        this.engine = engine;
        FontIcon fontIcon = new FontIcon(iconCode);
        setGraphic(fontIcon);
        setText(displayText);
        setWidth(106);
        setHeight(82);

        setMinWidth(106);
        setMinHeight(82);

        setMaxWidth(106);
        setMaxHeight(82);
        fontIcon.setIconSize(48);
        setAlignment(Pos.CENTER);
        setContentDisplay(ContentDisplay.TOP);
        fontIcon.setIconColor(engine.GuiService.FontColor.getValue());
        engine.GuiService.FontColor.addListener((observable, oldValue, newValue) -> {
            fontIcon.setIconColor(newValue);
        });

        if (transparent){
            setStyle("-fx-background-color:transparent; -fx-border-width: 0;");
            addEventHandler(MouseEvent.MOUSE_ENTERED, (EventHandler<MouseEvent>) e -> setStyle("-fx-background-color:transparent; -fx-border-width: 0"));
            addEventHandler(MouseEvent.MOUSE_EXITED, (EventHandler<MouseEvent>) e -> setStyle("-fx-background-color:transparent; -fx-border-width: 0"));
        }
    }
    @Override
    public Node getNode() {
        return this;
    }

    @Override
    public String getDisplayText() {
        return getText();
    }

    @Override
    public void setDisplayText(String text) {
        setText(text);
    }
}
