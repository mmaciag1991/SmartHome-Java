package com.sm.smarthome.Core.Providers;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Utils.SnowAnimation;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PowerProvider {
    Engine engine;
    public PowerProvider(Engine engine) {
        this.engine = engine;
    }
    public void TurnOff(Stage stage) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), stage.getScene().getRoot());
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished((ActionEvent event) -> {
            SnowAnimation snowAnimation = new SnowAnimation();
            snowAnimation.createContent(stage);
            TurnoffProcedure();
        });
        fadeOut.play();
    }
    private void TurnoffProcedure(){
        //tudaj procedura wylaczenia
    }
}
