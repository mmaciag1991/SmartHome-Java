package com.sm.smarthome.Models.Ui.Stages;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.CustomControls.Keyboard.KeyboardPane;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonState;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Models.Ui.Buttons.TwoStateButton;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import static com.sm.smarthome.Core.Utils.Helpers.AppHeight;
import static com.sm.smarthome.Core.Utils.Helpers.AppWidth;

public class AppStage extends Stage {

    public KeyboardPane KeyboardPane = new KeyboardPane();
    public AppStage(String title, Parent bodyInstance, Engine engine) {


        bodyInstance.prefWidth(AppWidth);
        bodyInstance.prefHeight(AppHeight);
        this.setWidth(AppWidth);
        this.setHeight(AppHeight);
        this.initStyle(StageStyle.UNDECORATED);
        VBox vBox = new VBox();
        TwoStateButton closeButton = new TwoStateButton(MaterialDesign.MDI_CLOSE_BOX, MaterialDesign.MDI_CLOSE_BOX, null, null,  ButtonAction.None,  ButtonAction.None, ButtonSize.Small, ButtonWidthType.Normal, engine, false, ButtonState.Inactive, UserPermissions.Guest, false);
        closeButton.setOnAction(actionEvent -> Platform.runLater(this::close));
        Label label = new Label(title);
        GridPane gridPane = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints(AppWidth/2);
        column1.setHalignment(HPos.RIGHT);
        ColumnConstraints column2 = new ColumnConstraints(AppWidth/2);
        column2.setHalignment(HPos.RIGHT);
        gridPane.getColumnConstraints().addAll(column1, column2);
        gridPane.add(label, 0, 0);
        gridPane.add(closeButton, 1, 0);
        //gridPane.prefWidthProperty().bind(this.widthProperty());
        vBox.getChildren().addAll(gridPane,bodyInstance);


        KeyboardPane.setContent(vBox);
        Scene scene = new Scene(KeyboardPane, AppWidth, AppHeight);
        this.setScene(scene);
        JMetro JMetroThemeManager = new JMetro(Style.DARK);
        JMetroThemeManager.setScene(scene);
        vBox.getStyleClass().add(JMetroStyleClass.BACKGROUND);



    }

}
