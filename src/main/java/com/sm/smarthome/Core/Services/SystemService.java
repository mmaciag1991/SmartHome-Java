package com.sm.smarthome.Core.Services;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Core.Providers.SystemInfoProvider;
import com.sm.smarthome.Enums.Other.LanguageE;
import com.sm.smarthome.Models.Data.LanguageModel;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.Locale;

public class SystemService {
    public WeatherService weatherService = new WeatherService();
    public SystemInfoProvider SystemInfoProvider = new SystemInfoProvider();
    public LanguageModel Language;

    Thread mainSystemThread = new Thread(this::mainSystemThreadTask);
    public SystemService(Engine engine){
        Language = new LanguageModel(engine);
        Language.SetLanguage(engine.SettingsProvider.Settings.getGlobalSettings().getLanguage());




//        mainSystemThread.setDaemon(true);
//        mainSystemThread.start();


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

            //weatherService.AskDataPoint();
            try {
                Thread.sleep(3000);
                Language.SetLanguage(LanguageE.EN);
                Thread.sleep(3000);
                Language.SetLanguage(LanguageE.PL);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
