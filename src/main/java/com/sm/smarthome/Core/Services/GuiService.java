package com.sm.smarthome.Core.Services;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

public class GuiService {

    public Stage MainStage;
    public JMetro JMetroThemeManager = new JMetro(Style.DARK);

    public SimpleObjectProperty<Color> FontColor = new SimpleObjectProperty<Color>(Color.TRANSPARENT);
    public GuiService(Stage mainStage){
        this.MainStage = mainStage;
        SetTheme(Style.LIGHT);
    }
    public void SetTheme(Style style){
        switch (style){
            case LIGHT -> {
                FontColor.setValue(Color.BLACK);
            }
            case DARK -> {
                FontColor.setValue(Color.WHITE);
            }
        }
        JMetroThemeManager.setStyle(style);
    }

}