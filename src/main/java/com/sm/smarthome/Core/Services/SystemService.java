package com.sm.smarthome.Core.Services;

import com.sm.smarthome.Enums.Other.LanguageE;
import com.sm.smarthome.Models.Data.LanguageModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SystemService {

    public LanguageModel Language = new LanguageModel();
    public SimpleStringProperty SystemDate = new SimpleStringProperty();
    Thread mainSystemThread = new Thread(() -> mainSystemThreadTask());
    public SystemService(){

        Language.SetLanguage(LanguageE.PL);
        mainSystemThread.setDaemon(true);
        mainSystemThread.start();

    }

    private Task mainSystemThreadTask(){
        while (mainSystemThread.isAlive()){
            Platform.runLater(() -> SystemDate.set(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime())));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
