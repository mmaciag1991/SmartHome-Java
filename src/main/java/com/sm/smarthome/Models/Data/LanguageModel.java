package com.sm.smarthome.Models.Data;

import com.sm.smarthome.Application;
import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Enums.Other.LanguageE;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class LanguageModel {
    public ObjectProperty<Locale> Locale = new SimpleObjectProperty<Locale>(java.util.Locale.ROOT);

    public ResourceBundle Resources;

    private Engine engine;
    public LanguageModel(Engine engine){
        this.engine = engine;
        Locale.set(new Locale("pl", "PL"));
    }

    public void SetLanguage(LanguageE locale){
        engine.SettingsProvider.Settings.getGlobalSettings().setLanguage(locale);
        Locale.setValue(new Locale(locale.name(), locale.name().toUpperCase()));

        Resources = newBundle("/com/sm/smarthome/Translations/Bundle",  Locale.get());
    }

    public ResourceBundle newBundle(String baseName, Locale locale){
        String resourceName = baseName + "_" + locale.getLanguage()+ "_" + locale.getCountry() + ".properties";
        try (InputStream stream = Application.class.getResourceAsStream(resourceName)) {
            return new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
