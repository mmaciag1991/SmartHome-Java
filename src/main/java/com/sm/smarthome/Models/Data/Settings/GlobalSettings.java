package com.sm.smarthome.Models.Data.Settings;

import com.sm.smarthome.Core.Providers.SettingsProvider;
import com.sm.smarthome.Enums.Other.LanguageE;
import javafx.scene.paint.Color;
import jfxtras.styles.jmetro.Style;

import java.io.Serializable;

public class GlobalSettings  implements Serializable {
    private transient SettingsProvider provider;
    private static final long serialVersionUID = 1L;
    private LanguageE language;
    private Style theme;

    private int redAccentColor;
    private int greenAccentColor;
    private int blueAccentColor;

    public GlobalSettings() {}

    public void setProvider(SettingsProvider provider) {
        this.provider = provider;
    }

    public LanguageE getLanguage() {
        return language;
    }
    public void setLanguage(LanguageE language) {
        this.language = language;
        provider.SaveSettings();
    }
    public Style getTheme() {
        return theme;
    }
    public void setTheme(Style theme) {
        this.theme = theme;
        provider.SaveSettings();
    }
    public Color getAccentColor() {
        return Color.rgb(redAccentColor, greenAccentColor, blueAccentColor);
    }
    public void setAccentColor(Color accentColor) {
        this.redAccentColor = (int) (accentColor.getRed() * 255);
        this.greenAccentColor = (int) (accentColor.getGreen() * 255);
        this.blueAccentColor = (int) (accentColor.getBlue() * 255);
        provider.SaveSettings();
    }
}
