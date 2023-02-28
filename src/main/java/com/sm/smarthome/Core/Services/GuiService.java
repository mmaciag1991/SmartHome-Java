package com.sm.smarthome.Core.Services;

import com.sm.smarthome.Core.Engine;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.lang.reflect.Field;
import java.util.Optional;

public class GuiService {


    public Stage MainStage;
    public JMetro JMetroThemeManager = new JMetro(Style.DARK);
    public SimpleObjectProperty<Color> FontColor = new SimpleObjectProperty<Color>(Color.TRANSPARENT);
    public SimpleObjectProperty<Color> AccentColor = new SimpleObjectProperty<Color>(Color.rgb(46,71,153));


    public GuiService(Engine engine, Stage mainStage){
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
    public DropShadow GetShadow(SimpleObjectProperty<Color> color, double offsetX, double offsetY, double width, double height, boolean ignoreAccentColor){
        DropShadow shadow= new DropShadow();
        shadow.setBlurType(BlurType.THREE_PASS_BOX);
        shadow.setColor(color.getValue());
        shadow.setSpread(0.2);
        shadow.setWidth(width);
        shadow.setHeight(height);
        shadow.setOffsetX(offsetX);
        shadow.setOffsetY(offsetY);

        if (!ignoreAccentColor)
            color.addListener((observable, oldValue, newValue) -> { shadow.setColor(newValue);});


        return shadow;
    }
    public String GetRgbaColorToStyleFx(Color color, double alpha){
        return "rgba(%s, %s, %s, %s);".formatted(color.getRed() * 255, color.getGreen() * 255, color.getBlue() * 255, alpha);
    }

    public Optional<String> colorName(Color c) {
        for (Field f : Color.class.getDeclaredFields()) {
            //we want to test only fields of type Color
            if (f.getType().equals(Color.class))
                try {
                    if (f.get(null).equals(c))
                        return Optional.of(f.getName().toLowerCase());
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
        }
        return Optional.empty();
    }

}