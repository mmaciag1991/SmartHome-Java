package com.sm.smarthome.Models.Data;

import com.sm.smarthome.Enums.Other.LanguageE;

import java.util.Locale;

public class LanguageModel {
    public Locale Locale = java.util.Locale.US;

    public LanguageModel(){}

    public void SetLanguage(LanguageE locale){
        Locale = new Locale(locale.name());
    }
}
