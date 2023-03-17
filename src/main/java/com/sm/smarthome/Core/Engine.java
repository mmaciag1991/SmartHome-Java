package com.sm.smarthome.Core;

import com.sm.smarthome.Core.Providers.ControlersProvider;
import com.sm.smarthome.Core.Providers.Gui.PagesProvider;
import com.sm.smarthome.Core.Providers.Gui.TilesProvider;
import com.sm.smarthome.Core.Providers.Gui.TopBarProvider;
import com.sm.smarthome.Core.Providers.SettingsProvider;
import com.sm.smarthome.Core.Services.ActionEventService;
import com.sm.smarthome.Core.Services.GuiService;
import com.sm.smarthome.Core.Services.SystemService;
import com.sm.smarthome.Core.SplashScreen.SplashScreen;
import com.sm.smarthome.Models.Data.UserModel;
import com.sm.smarthome.Utils.DisintegrationLogo;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Locale;

public class Engine {

    public boolean isInitialized = false;
    public SettingsProvider SettingsProvider;
    public SystemService SystemService;

    public SimpleObjectProperty<UserModel> CurrentUser = new SimpleObjectProperty<UserModel>();
    public GuiService GuiService;
    public ActionEventService ActionEventService;
    public ControlersProvider ControlersProvider = new ControlersProvider();

    private final SplashScreen splashScreen = new SplashScreen();

    public Engine(Stage mainStage){
//        Thread initThread = new Thread(this::initializationWaiter);
//        initThread.setDaemon(true);
//        initThread.start();
        splashScreen.show();

        InitializeSettingsProvider();
        SystemService = new SystemService(this);

        new Thread(() -> {
                InitializeActionEventService();
                InitializeGuiService(mainStage);
                InitializeTilesProvider();
                InitializePagesProvider();
                InitializeTopBarProvider();

            SystemService.Language.Locale.setValue(Locale.ROOT);
            SystemService.Language.SetLanguage(SettingsProvider.Settings.getGlobalSettings().getLanguage());
            isInitialized = true;

                Platform.runLater(() -> {
                    splashScreen.close();
                    DisintegrationLogo disintegration = new DisintegrationLogo();
                    disintegration.start();
                });
        }).start();



    }
    private void InitializeActionEventService()  {

        String text = "Initializing future: ActionEventService";
        splashScreen.setLoadingTextLabel(text);
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println(text);
        {
            ActionEventService = new ActionEventService(this);
        }
        watch.stop();
        System.out.println("Initialized future (" + watch.getTime() + " ms): ActionEventService");

    }
    private void InitializeSettingsProvider()  {

        String text = "Initializing future: SettingsProvider";
        splashScreen.setLoadingTextLabel(text);
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println(text);
        {
            SettingsProvider = new SettingsProvider();
            SettingsProvider.LoadSettings();
            SettingsProvider.Settings.setSettingsProvider(SettingsProvider);
        }
        watch.stop();
        System.out.println("Initialized future (" + watch.getTime() + " ms): SettingsProvider");

    }
    private void InitializeGuiService(Stage mainStage)  {

        String text = "Initializing future: GuiService";
        splashScreen.setLoadingTextLabel(text);
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println(text);
        {
            GuiService = new GuiService(this, mainStage);
        }
        watch.stop();
        System.out.println("Initialized future (" + watch.getTime() + " ms): GuiService");

    }
    private void InitializePagesProvider()  {

        String text = "Initializing future: PagesProvider";
        splashScreen.setLoadingTextLabel(text);
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println(text);
        {
            GuiService.PagesProvider = new PagesProvider(this);
        }
        watch.stop();
        System.out.println("Initialized future (" + watch.getTime() + " ms): PagesProvider");

    }
    private void InitializeTopBarProvider()  {

        String text = "Initializing future: TopBarProvider";
        splashScreen.setLoadingTextLabel(text);
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println(text);
        {
            GuiService.TopBarProvider = new TopBarProvider(this);
        }
        watch.stop();
        System.out.println("Initialized future (" + watch.getTime() + " ms): TopBarProvider");

    }
    private void InitializeTilesProvider()  {

        String text = "Initializing future: TilesProvider";
        splashScreen.setLoadingTextLabel(text);
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println(text);
        {
            GuiService.TilesProvider = new TilesProvider(this);
        }
        watch.stop();
        System.out.println("Initialized future (" + watch.getTime() + " ms): TilesProvider");

    }

    private void initializationWaiter(){
        while (!isInitialized){
            try {
                Thread.sleep(1005);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        SystemService.Language.Locale.setValue(Locale.ROOT);
        SystemService.Language.SetLanguage(SettingsProvider.Settings.getGlobalSettings().getLanguage());

    }

}
