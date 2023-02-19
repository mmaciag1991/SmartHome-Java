package com.sm.smarthome.Core.Services;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SystemService {
    public SimpleStringProperty SystemDate = new SimpleStringProperty();
    Thread mainSystemThread = new Thread(() -> mainSystemThreadTask());
    public SystemService(){

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
