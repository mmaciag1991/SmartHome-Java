package com.sm.smarthome.Core;

import com.sm.smarthome.Core.Providers.ControlersProvider;
import com.sm.smarthome.Core.Providers.Gui.PagesProvider;
import com.sm.smarthome.Core.Providers.Gui.TilesProvider;
import com.sm.smarthome.Core.Providers.Gui.TopBarProvider;
import com.sm.smarthome.Core.Services.ActionEventService;
import com.sm.smarthome.Core.Services.GuiService;
import com.sm.smarthome.Core.Services.SystemService;
import com.sm.smarthome.CustomControls.Indicators.CircularProgressIndicator;
import com.sm.smarthome.Models.Data.UserModel;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.atomic.AtomicBoolean;

public class Engine {

    public SystemService SystemService = new SystemService();
    public SimpleObjectProperty<UserModel> CurrentUser = new SimpleObjectProperty<UserModel>();
    public GuiService GuiService;
    public PagesProvider PagesProvider;
    public TopBarProvider TopBarProvider;
    public TilesProvider TilesProvider;
    public ActionEventService ActionEventService;
    public ControlersProvider ControlersProvider = new ControlersProvider();

    private SplashScreen splashScreen = new SplashScreen();

    public Engine(Stage mainStage){
        new Thread(() -> {
                InitializeActionEventService();
                InitializeGuiService(mainStage);
                InitializeTilesProvider();
                InitializePagesProvider();
                InitializeTopBarProvider();

                Platform.runLater(() ->  splashScreen.close());
        }).start();
        splashScreen.showAndWait();
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
            PagesProvider = new PagesProvider(this);
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
            TopBarProvider = new TopBarProvider(this);
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
            TilesProvider = new TilesProvider(this);
        }
        watch.stop();
        System.out.println("Initialized future (" + watch.getTime() + " ms): TilesProvider");

    }

}
