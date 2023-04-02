package com.trays;

import javafx.animation.Transition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Animacja extends Application {

    private Button toggleButton;
    private StackPane contentPane;
    private double contentHeight;

    @Override
    public void start(Stage primaryStage) throws Exception {
        contentPane = new StackPane();
        contentPane.setStyle("-fx-background-color: white;");
        contentPane.getChildren().add(new Rectangle(200, 200, Color.BLUE));

        // Wrap content pane in an AnchorPane to position it at the bottom of the window.
        AnchorPane anchorPane = new AnchorPane(contentPane);
        AnchorPane.setBottomAnchor(contentPane, 0.0);

        toggleButton = new Button("Ukryj");
        toggleButton.setOnAction(event -> toggleContentPane());

        StackPane root = new StackPane(anchorPane, toggleButton);
        Scene scene = new Scene(root, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();

        contentHeight = contentPane.getHeight();
        contentPane.setTranslateY(contentHeight);
    }

    private void toggleContentPane() {
        Transition transition = new Transition() {
            {
                setCycleDuration(Duration.seconds(0.5));
            }

            @Override
            protected void interpolate(double frac) {
                contentPane.setTranslateY(contentHeight - (frac * contentHeight));
            }
        };

        if (contentPane.getTranslateY() == 0) {
            toggleButton.setText("Pokaż");
            transition.setRate(-1.0);
            transition.setOnFinished(event -> contentPane.setVisible(false));
        } else {
            toggleButton.setText("Ukryj");
            contentPane.setVisible(true);
        }

        transition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}