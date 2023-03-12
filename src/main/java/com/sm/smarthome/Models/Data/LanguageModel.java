package com.sm.smarthome.Models.Data;

import com.sm.smarthome.Enums.Other.LanguageE;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class LanguageModel {
    public ObjectProperty<Locale> Locale = new SimpleObjectProperty<Locale>(java.util.Locale.ENGLISH);

    public ResourceBundle Resources;

    public LanguageModel(){}

    public void SetLanguage(LanguageE locale){
        Locale.setValue(new Locale(locale.name()));
//TODO: Change the language of the application brak polskich znaków
//        Resources = ResourceBundle.getBundle("com/sm/smarthome/Translations/", Locale.get(),new ResourceBundle.Control() {
//            @Override
//            public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
//                String bundleName = toBundleName(baseName, locale);
//                String resourceName = toResourceName(bundleName, "properties");
//                try (InputStream stream = loader.getResourceAsStream(resourceName)) {
//                    return new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
//                }
//            }
//        });
        Resources = ResourceBundle.getBundle("com/sm/smarthome/Translations/", Locale.get()); // the main package is stackoverflow which contains language
    }
}
