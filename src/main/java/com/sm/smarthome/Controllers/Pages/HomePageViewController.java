package com.sm.smarthome.Controllers.Pages;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class HomePageViewController {
    @FXML
    private GridPane tilesGrid;
    @FXML
    private HBox buttonsBox;
    public HomePageViewController(){}

    public GridPane getTilesGrid() {
        return tilesGrid;
    }
    public HBox getButtonsBox(){
        return buttonsBox;
    }

    public void slide(Parent parent) {
        Parent root = parent;

        FadeTransition ft = new FadeTransition(Duration.millis(3000), tilesGrid);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }
}
