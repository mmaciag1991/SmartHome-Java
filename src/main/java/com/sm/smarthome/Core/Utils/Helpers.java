package com.sm.smarthome.Core.Utils;

import com.jfoenix.controls.JFXPopup;
import com.sm.smarthome.Application;
import com.sm.smarthome.Controllers.OtherControls.SimplePopupController;
import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Enums.Ui.Otthers.PopupType;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import jfxtras.styles.jmetro.JMetroStyleClass;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Optional;

public class Helpers {

    public static int AppWidth = 1280;
    public static int AppHeight = 800;
    public static boolean CheckUserPermissions(UserPermissions permissions, Engine engine, Node ownerNode){

        boolean hasPermissions = engine.CurrentUser.getValue().UserPermission.getValue().getValue() >= permissions.getValue();

        if (!hasPermissions)
        {
            JFXPopup jfxPopup = new JFXPopup();
            jfxPopup.getStyleClass().add("background");
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Views/OtherControls/SimplePopup.fxml"));
            Parent parent = null;
            try {
                parent = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            parent.getStyleClass().add(JMetroStyleClass.BACKGROUND);
            SimplePopupController mainViewController = fxmlLoader.getController();
            mainViewController.Initialize(engine.SystemService.Language.Resources.getString("general.message.AccessDenied"), PopupType.Error);
            Pane pane = new Pane(parent);
            jfxPopup.setPopupContent(pane);
            jfxPopup.show(ownerNode);
        }
        return hasPermissions;
    }
    public static Optional<String> ColorName(Color c) {
        for (Field f : Color.class.getDeclaredFields()) {
            //we want to test only fields of type Color
            if (f.getType().equals(Color.class))
                try {
                    if (f.get(null).equals(c))
                        return Optional.of(f.getName().toLowerCase());
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
        }
        return Optional.empty();
    }

    public static DropShadow GetShadow(SimpleObjectProperty<Color> color, double offsetX, double offsetY, double width, double height, boolean ignoreAccentColor){
        DropShadow shadow= new DropShadow();
        shadow.setBlurType(BlurType.THREE_PASS_BOX);
        shadow.setColor(color.getValue());
        shadow.setSpread(0.2);
        shadow.setWidth(width);
        shadow.setHeight(height);
        shadow.setOffsetX(offsetX);
        shadow.setOffsetY(offsetY);

        if (!ignoreAccentColor)
            color.addListener((observable, oldValue, newValue) -> { shadow.setColor(newValue);});


        return shadow;
    }

    public static String getRgbaColorToStyleFx(Color color, double alpha){
        return "rgba(%s, %s, %s, %s);".formatted(color.getRed() * 255, color.getGreen() * 255, color.getBlue() * 255, alpha);
    }

    public static void CloseStageWithFadeOut(Stage stage) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), stage.getScene().getRoot());
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished((ActionEvent event) -> stage.close());
        fadeOut.play();
    }

    public static void Slide(GridPane oldValue, GridPane newValue){

        newValue.setVisible(true);

        TranslateTransition slideOldContent = new TranslateTransition(new javafx.util.Duration(500), oldValue);

        TranslateTransition slideNewContent = new TranslateTransition(new javafx.util.Duration(500), newValue);
        slideNewContent.setToX(0);
        slideOldContent.setFromX(0);

        if (Integer.parseInt(oldValue.getAccessibleRoleDescription()) > Integer.parseInt(newValue.getAccessibleRoleDescription())) {
            slideOldContent.setToX(oldValue.getWidth());
            slideNewContent.setFromX(-oldValue.getWidth());
        }else{
            slideOldContent.setToX(-oldValue.getWidth());
            slideNewContent.setFromX(oldValue.getWidth());
        }

        slideOldContent.play();
        slideNewContent.setOnFinished(actionEvent -> oldValue.setVisible(false));
        slideNewContent.setToX(0);
        slideNewContent.play();
    }
}
