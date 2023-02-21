package com.sm.smarthome.Core.Services;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonState;
import com.sm.smarthome.Models.Ui.Pages.HomePage;
import com.sm.smarthome.Models.Ui.Pages.PageBase;
import com.sm.smarthome.Models.Ui.Pages.SetupPage;
import com.sm.smarthome.Models.Ui.Pages.WeatherPage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

public class GuiService {


    public JMetro JMetroThemeManager = new JMetro(Style.DARK);
    public SimpleObjectProperty<Color> FontColor = new SimpleObjectProperty<Color>(Color.TRANSPARENT);
    public SimpleObjectProperty<Color> AccentColor = new SimpleObjectProperty<Color>(Color.RED);


    public GuiService(Engine engine){
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
    public DropShadow GetShadow(Color color, double offsetX, double offsetY, double width, double height){
        DropShadow shadow= new DropShadow();
        shadow.setBlurType(BlurType.THREE_PASS_BOX);
        shadow.setColor(color);
        shadow.setSpread(0.2);
        shadow.setWidth(width);
        shadow.setHeight(height);
        shadow.setOffsetX(offsetX);
        shadow.setOffsetY(offsetY);

        return shadow;
    }
    public String GetRgbaColorToStyleFx(Color color, double alpha){
        return "rgba(%s, %s, %s, %s);".formatted(color.getRed() * 255, color.getGreen() * 255, color.getBlue() * 255, alpha);
    }

}