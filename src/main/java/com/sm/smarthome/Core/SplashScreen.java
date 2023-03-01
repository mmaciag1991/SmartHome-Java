package com.sm.smarthome.Core;

import com.sm.smarthome.CustomControls.HanSolo.FunIndicator.FunIndicator;
import com.sm.smarthome.CustomControls.Indicators.CircularProgressIndicator;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

public class SplashScreen extends Stage {
    private Label loadingTextLabel = new Label();
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
        StackPane stackPane = new StackPane(pane);
        stackPane.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        Scene scene = new Scene(stackPane, 1200, 800);
        JMetroThemeManager.setScene(scene);
        this.initStyle(StageStyle.UTILITY);
        //this.setFullScreen(true);
        this.setTitle("Initializing");
        this.setScene(scene);
    }

    public void setLoadingTextLabel(String text) {
        Platform.runLater(() -> loadingTextLabel.setText(text));
        Sleep(500);
    }

    private void Sleep(int milis){
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
