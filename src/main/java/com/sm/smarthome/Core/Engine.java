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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

    public Engine(Stage mainStage){

        Stage stage = new Stage();

                CircularProgressIndicator indicator = new CircularProgressIndicator();
                StackPane pane = new StackPane(indicator);
                indicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                Scene scene = new Scene(pane, 1200, 800);

                stage.initStyle(StageStyle.UNDECORATED);
                stage.setTitle("Circular Progress Indicator");
                stage.setScene(scene);





        new Thread(() -> {
//            try {
//                //Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
                InitializeActionEventService();
                InitializeGuiService(mainStage);
                InitializeTilesProvider();
                InitializePagesProvider();
                InitializeTopBarProvider();


            Platform.runLater(() ->  stage.close());

        }).start();
        stage.showAndWait();

    }
    private void InitializeActionEventService()  {

        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("Initializing future: ActionEventService");
        {
            ActionEventService = new ActionEventService(this);
        }
        watch.stop();
        System.out.println("Initialized future (" + watch.getTime() + " ms): ActionEventService");

    }
    private void InitializeGuiService(Stage mainStage)  {

        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("Initializing future: GuiService");
        {
            GuiService = new GuiService(this, mainStage);
        }
        watch.stop();
        System.out.println("Initialized future (" + watch.getTime() + " ms): GuiService");

    }
    private void InitializePagesProvider()  {

        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("Initializing future: PagesProvider");
        {
            PagesProvider = new PagesProvider(this);
        }
        watch.stop();
        System.out.println("Initialized future (" + watch.getTime() + " ms): PagesProvider");

    }
    private void InitializeTopBarProvider()  {

        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("Initializing future: TopBarProvider");
        {
            TopBarProvider = new TopBarProvider(this);
        }
        watch.stop();
        System.out.println("Initialized future (" + watch.getTime() + " ms): TopBarProvider");

    }
    private void InitializeTilesProvider()  {

        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("Initializing future: TilesProvider");
        {
            TilesProvider = new TilesProvider(this);
        }
        watch.stop();
        System.out.println("Initialized future (" + watch.getTime() + " ms): TilesProvider");

    }


}
