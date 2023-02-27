package com.sm.smarthome.Controllers.Pages;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class HomePageViewController {
    @FXML
    private GridPane gridPane;

    public HomePageViewController(){}

    public GridPane getGridPane() {
        return gridPane;
    }

    public void slide(Parent parent) {
        Parent root = parent;

        FadeTransition ft = new FadeTransition(Duration.millis(3000), gridPane);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }
}
