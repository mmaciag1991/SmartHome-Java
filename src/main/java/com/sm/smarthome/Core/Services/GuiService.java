package com.sm.smarthome.Core.Services;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.CustomControls.HanSolo.Funmenu.FunMenu;
import com.sm.smarthome.CustomControls.Keyboard.KeyboardPane;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonState;
import com.sm.smarthome.Events.ButtonEvent;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.kordamp.ikonli.fluentui.FluentUiRegularMZ;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class GuiService {

    public FunMenu MainMenu;
    public KeyboardPane KeyboardPane = new KeyboardPane();
    public Stage MainStage;
    public JMetro JMetroThemeManager = new JMetro(Style.DARK);
    public SimpleObjectProperty<Color> FontColor = new SimpleObjectProperty<Color>(Color.TRANSPARENT);
    public SimpleObjectProperty<Color> AccentColor = new SimpleObjectProperty<Color>(Color.BLACK);


    public GuiService(Engine engine, Stage mainStage){
        this.MainMenu = new FunMenu();
        this.MainStage = mainStage;

        engine.SystemService.Language.Locale.addListener((observableValue, locale, t1) -> KeyboardPane.getKeyboardView().loadKeyboard(t1));

        AccentColor.addListener((observableValue, color, t1) -> {
            KeyboardPane.getKeyboardView().setAccentColor(t1);
            MainMenu.setButtonColor(t1.darker());
            MainMenu.setMenuColor(t1);
        });
        InitMainMenu(engine);

        Platform.runLater(() -> {
            SetTheme(engine.SettingsProvider.Settings.getGlobalSettings().getTheme());
        });

        AccentColor.set(engine.SettingsProvider.Settings.getGlobalSettings().getAccentColor());
        AccentColor.addListener((observableValue, color, t1) -> {
            engine.SettingsProvider.Settings.getGlobalSettings().setAccentColor(t1);
        });
    }

    public void SetTheme(Style style){
        switch (style){
            case LIGHT -> {
                FontColor.setValue(Color.BLACK);
                KeyboardPane.getKeyboardView().SetTheme(false);
            }
            case DARK -> {
                FontColor.setValue(Color.WHITE);
                KeyboardPane.getKeyboardView().SetTheme(true);
            }
        }
        JMetroThemeManager.setStyle(style);
    }

    private void InitMainMenu(Engine engine){

        MainMenu.setPrefSize(400, 40);
        MainMenu.setItem1IconCode(FluentUiRegularMZ.POWER_28);
        MainMenu.setItem2IconCode(MaterialDesign.MDI_GROUP);
        MainMenu.setItem3IconCode(MaterialDesign.MDI_GMAIL);
        MainMenu.setItem4IconCode(MaterialDesign.MDI_ACCOUNT);

        MainMenu.setOnItem1MousePressed(e -> System.out.println("Icon 1 pressed"));
        MainMenu.setOnItem2MousePressed(e -> System.out.println("Icon 2 pressed"));
        MainMenu.setOnItem3MousePressed(e -> System.out.println("Icon 3 pressed"));
        MainMenu.setOnItem4MousePressed(e -> engine.ActionEventService.fireEvent(new ButtonEvent(ButtonAction.ActionLogin)));

        MainMenu.setIconColor(Color.WHITE);
    }

}