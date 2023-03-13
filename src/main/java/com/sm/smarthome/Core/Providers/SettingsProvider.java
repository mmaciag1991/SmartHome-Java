package com.sm.smarthome.Core.Providers;

import java.io.*;

import com.sm.smarthome.Models.Data.Settings.SettingsModel;

public class SettingsProvider {

    private static final String SETTINGS_FILE = "settings.smPar";
    public SettingsModel Settings = new SettingsModel();

    public SettingsProvider() {
        Settings = new SettingsModel();
    }

    // metoda zapisująca ustawienia do pliku
    public void SaveSettings() {
        try {
            FileOutputStream fileOut = new FileOutputStream(SETTINGS_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Settings);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // metoda wczytująca ustawienia z pliku
    public void LoadSettings() {
        File file = new File(SETTINGS_FILE);

        if (!file.exists()) {
            getDefaultSettings();
            SaveSettings();
        }

            SettingsModel settings = null;
        try {
            FileInputStream fileIn = new FileInputStream(SETTINGS_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            settings = (SettingsModel) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Settings = settings;
    }

    // metoda zwracająca domyślne ustawienia
    public void getDefaultSettings() {
        Settings.SetDefault();
    }

    // przykładowe wykorzystanie
    public static void main(String[] args) {
        SettingsProvider provider = new SettingsProvider();
        provider.LoadSettings();
        if (provider.Settings == null) {
            provider.getDefaultSettings();
        }
    }
}
