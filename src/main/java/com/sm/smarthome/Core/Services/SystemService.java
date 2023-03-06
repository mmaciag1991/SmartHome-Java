package com.sm.smarthome.Core.Services;

import com.sm.smarthome.Application;
import com.sm.smarthome.Core.Providers.SystemInfoProvider;
import com.sm.smarthome.Enums.Other.LanguageE;
import com.sm.smarthome.Models.Data.LanguageModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.PopupWindow;
import javafx.stage.Window;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SystemService {

    public WeatherService weatherService = new WeatherService();
    public SystemInfoProvider SystemInfoProvider = new SystemInfoProvider();
    public LanguageModel Language = new LanguageModel();
    Thread mainSystemThread = new Thread(() -> mainSystemThreadTask());
    public SystemService(){

        Language.SetLanguage(LanguageE.PL);
        mainSystemThread.setDaemon(true);
        mainSystemThread.start();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    getKeyboardPopup();
                });
            }
        }, 5, 5);

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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private PopupWindow getKeyboardPopup() {
        @SuppressWarnings("deprecation")
        final Iterator<Window> windows = Window.getWindows().iterator();

        while (windows.hasNext()) {
            final Window window = windows.next();
            if (window instanceof PopupWindow) {
                if (window.getScene() != null && window.getScene().getRoot() != null) {
                    Parent root = window.getScene().getRoot();
                    if (root.getChildrenUnmodifiable().size() > 0) {
                        Node popup = root.getChildrenUnmodifiable().get(0);
                        if (popup.lookup(".fxvk") != null) {
                            String vars = Application.class.getResource("Skins/Keyboard/variables.css").toExternalForm();
                            String embed = Application.class.getResource("Skins/Keyboard/embeded.css").toExternalForm();

//                            FXVK vk = (FXVK)popup.lookup(".fxvk");
//                            vk.setMinHeight(400);
                            if (!window.getScene().getStylesheets().contains(embed)) {
                                popup.getScene().getStylesheets().addAll(vars, embed);
                                window.getScene().getStylesheets().addAll(vars, embed);
                            }
                            return (PopupWindow)window;
                        }
                    }
                }
                return null;
            }
        }
        return null;
    }

}
