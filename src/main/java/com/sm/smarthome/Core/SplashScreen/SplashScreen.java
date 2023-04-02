package com.sm.smarthome.Core.SplashScreen;

import com.sm.smarthome.CustomControls.HanSolo.FunIndicator.FunIndicator;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import static com.sm.smarthome.Core.Utils.Helpers.AppHeight;
import static com.sm.smarthome.Core.Utils.Helpers.AppWidth;

public class SplashScreen extends Stage {
    private Label loadingTextLabel = new Label();
    private Scene scene;
    private StackPane stackPane;
    public SplashScreen(){

        JMetro JMetroThemeManager = new JMetro(Style.DARK);
//        CircularProgressIndicator indicator = new CircularProgressIndicator();
//        indicator.setOpacity(.5);
//        indicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        FunIndicator indicator = new FunIndicator();
        indicator.setPrefWidth(400);
        indicator.setPrefHeight(400);
        indicator.start();
        indicator.setFromColor(Color.RED);
        indicator.fromColorProperty().addListener((observableValue, color, t1) -> {
            loadingTextLabel.setTextFill(t1);
        });
        loadingTextLabel.setTextAlignment(TextAlignment.CENTER);
        loadingTextLabel.setFont(new Font(48));
        VBox pane = new VBox(indicator, loadingTextLabel);
        pane.setAlignment(Pos.CENTER);
        stackPane = new StackPane(pane);
        stackPane.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        scene = new Scene(stackPane, AppWidth, AppHeight);
        JMetroThemeManager.setScene(scene);
        this.initStyle(StageStyle.TRANSPARENT);
        //this.setFullScreen(true);
        this.setTitle("Initializing");
        this.setScene(scene);
    }


    public StackPane getStackPane() {
        return stackPane;
    }


    public void setLoadingTextLabel(String text) {
        Platform.runLater(() -> loadingTextLabel.setText(text));
        sleep(50);
    }

    private void sleep(int milis){
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
