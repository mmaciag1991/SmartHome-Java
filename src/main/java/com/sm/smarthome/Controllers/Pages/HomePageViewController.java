package com.sm.smarthome.Controllers.Pages;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class HomePageViewController {
    @FXML
    private GridPane gridPane;

    public HomePageViewController(){}

    public GridPane getGridPane() {
        return gridPane;
    }
}
