package com.sm.smarthome.Core.Services;

import com.sm.smarthome.Core.Providers.SystemInfoProvider;
import com.sm.smarthome.Enums.Other.LanguageE;
import com.sm.smarthome.Models.Data.LanguageModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SystemService {

    public WeatherService weatherService = new WeatherService();
    public SystemInfoProvider SystemInfoProvider = new SystemInfoProvider();
    public LanguageModel Language = new LanguageModel();
    Thread mainSystemThread = new Thread(() -> mainSystemThreadTask());
    public SystemService(){

        Language.SetLanguage(LanguageE.PL);
        mainSystemThread.setDaemon(true);
        mainSystemThread.start();

    }

    public void RunProcess(String cmd){
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(cmd);
            proc.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Task mainSystemThreadTask(){
        while (mainSystemThread.isAlive()){

            weatherService.AskDataPoint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
