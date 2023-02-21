package com.sm.smarthome.Controllers.OtherControls;

import com.sm.smarthome.Enums.Ui.Otthers.PopupType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.fluentui.FluentUiFilledAL;
import org.kordamp.ikonli.fluentui.FluentUiFilledMZ;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.materialdesign2.MaterialDesignR;

public class SimplePopupController {
    @FXML
    private GridPane gridPane;

    public SimplePopupController(){}

    public void Initialize(String textMessage, PopupType popupType){

        Platform.runLater(() -> {
            FontIcon fontIcon = new FontIcon();
            fontIcon.setIconSize(64);
            switch (popupType) {

                case Information -> {
                    fontIcon.setIconColor(Color.ROYALBLUE);
                    fontIcon.setIconCode(FluentUiFilledAL.INFO_24);
                }
                case Warning -> {
                    fontIcon.setIconColor(Color.GOLDENROD);
                    fontIcon.setIconCode(FluentUiFilledMZ.WARNING_24);
                }
                case Error -> {
                    fontIcon.setIconColor(Color.FIREBRICK);
                    fontIcon.setIconCode(FluentUiFilledAL.ERROR_CIRCLE_24);
                }
            }
            gridPane.add(fontIcon, 0, 0);

            Label label = new Label(textMessage);
            gridPane.add(label,1,0);
        });
    }

}
