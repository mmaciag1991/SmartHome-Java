package com.sm.smarthome.Core.Services;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Core.Providers.PowerProvider;
import com.sm.smarthome.Core.Providers.SystemInfoProvider;
import com.sm.smarthome.Models.Data.LanguageModel;

import java.io.IOException;

public class SystemService {
    public WeatherProvider WeatherProvider = new WeatherProvider();
    public SystemInfoProvider SystemInfoProvider = new SystemInfoProvider();
    public PowerProvider PowerProvider;

    public LanguageModel Language;

    // Thread mainSystemThread = new Thread(this::MainSystemThreadTask);
    public SystemService(Engine engine){
        Language = new LanguageModel(engine);
        Language.SetLanguage(engine.SettingsProvider.Settings.getGlobalSettings().getLanguage());
        PowerProvider = new PowerProvider(engine);
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
//    private void MainSystemThreadTask(){
//        while (mainSystemThread.isAlive()){
//
//            //weatherService.AskDataPoint();
//            try {
//                Thread.sleep(3000);
//                Language.SetLanguage(LanguageE.EN);
//                Thread.sleep(3000);
//                Language.SetLanguage(LanguageE.PL);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

}
