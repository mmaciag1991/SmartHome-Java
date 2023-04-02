package com.sm.smarthome.Models.Data.Settings;

import com.sm.smarthome.Core.Providers.SettingsProvider;
import com.sm.smarthome.Enums.Other.LanguageE;
import javafx.scene.paint.Color;
import jfxtras.styles.jmetro.Style;

import java.io.Serializable;

public class SettingsModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private GlobalSettings globalSettings;

    private transient SettingsProvider settingsProvider;
    public SettingsModel() {
        this.globalSettings = new GlobalSettings();
    }

    public void setSettingsProvider(SettingsProvider settingsProvider) {
        this.settingsProvider = settingsProvider;
        this.globalSettings.setProvider(settingsProvider);
    }

    public void SetDefault(){
        this.globalSettings.setLanguage(LanguageE.PL);
        this.globalSettings.setTheme(Style.LIGHT);
        this.globalSettings.setAccentColor(Color.FIREBRICK);
    }

    public GlobalSettings getGlobalSettings() {
        return globalSettings;
    }

}
