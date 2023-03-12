package com.sm.smarthome.Models.Ui.Menu.Catalog;

import com.jfoenix.controls.JFXTextField;
import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Core.Providers.Gui.WindowManager;
import com.sm.smarthome.Core.Utils.Helpers;
import com.sm.smarthome.Enums.Other.UserPermissions;
import com.sm.smarthome.Enums.Ui.Catalog.CatalogAction;
import com.sm.smarthome.Events.Catalog.CatalogEvent;
import com.sm.smarthome.Events.Catalog.CatalogEventHandler;
import com.sm.smarthome.Interfaces.ICatalog;
import com.sm.smarthome.Interfaces.IRecord;
import com.sm.smarthome.Interfaces.IRecordButton;
import com.sm.smarthome.Models.Ui.Buttons.CatalogButton;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.sm.smarthome.Core.Utils.Helpers.CheckUserPermissions;

public class StdCatalog extends BorderPane implements ICatalog {
    private IRecordButton CatalogButton;
    private final JFXTextField searchTextField = new JFXTextField();
    private final GridPane topBar = new GridPane();
    private final HBox actionButtonBar = new HBox();
    private final FlowPane catalogContent = new FlowPane();

    private final String UUID = java.util.UUID.randomUUID().toString();

    private String displayText;
    private Ikon iconCode;
    private FontIcon fontIcon;
    private final Engine engine;
    private final WindowManager windowManager;
    private final UserPermissions permissions;

    Label displayTextLabel = new Label(this.displayText);
    public StdCatalog(Ikon iconCode, String displayText, UserPermissions permissions, Engine engine, WindowManager windowManager){
        this.iconCode = iconCode;
        this.displayText = displayText;
        this.permissions = permissions;
        this.engine = engine;
        this.windowManager = windowManager;
        this.displayTextLabel.setText(displayText);

        if (displayText!= null) {
            try {
                engine.SystemService.Language.Locale.addListener((observableValue, locale, t1) -> {

                    Platform.runLater(() -> {
                        String text = engine.SystemService.Language.Resources.getString(this.displayText);
                        this.CatalogButton.setDisplayText(text);
                        this.displayTextLabel.setText(text);
                    });
                });
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        InitializeCatalogActionButtons();
        InitializeIconLabel();
        Initialize();
        InitializeCatalogButton();
    }
    private void InitializeCatalogActionButtons(){
        searchTextField.setStyle(GetSearchFliedStyle());
        searchTextField.textProperty().addListener((observableValue, s, t1) -> {
            searchTextField.setStyle(GetSearchFliedStyle());
            AtomicBoolean found = new AtomicBoolean(false);
            catalogContent.getChildren().forEach(node -> {
                if (node instanceof IRecordButton) {
                    var buttonDisplayText = ((IRecordButton) node).getDisplayText();
                    if (buttonDisplayText.toLowerCase().contains(t1.toLowerCase())) {
                        node.setVisible(true);
                        node.setManaged(true);
                        found.set(true);
                    }else {
                        node.setVisible(false);
                        node.setManaged(false);
                    }
                }
            });
            if (found.get()) {
                searchTextField.setFocusColor(Color.FORESTGREEN);
            }else {
                searchTextField.setFocusColor(Color.FIREBRICK);
            }
        });
        this.actionButtonBar.getChildren().add(searchTextField);
        searchTextField.setVisible(false);
        searchTextField.setManaged(false);
        CatalogButton search = new CatalogButton(MaterialDesign.MDI_FILE_FIND, null, true, engine);
        search.setOnAction(e -> {this.fireEvent(new CatalogEvent(CatalogAction.Search));});
        this.actionButtonBar.getChildren().add(search);
        CatalogButton exit = new CatalogButton(MaterialDesign.MDI_BACKSPACE, null, true, engine);
        exit.setDisable(true);
        exit.setOnAction(e -> {this.fireEvent(new CatalogEvent(CatalogAction.Exit));});
        windowManager.ExitAvailableProperty.addListener((observableValue, aBoolean, t1) -> exit.setDisable(!t1));
        this.actionButtonBar.getChildren().add(exit);

        this.actionButtonBar.setSpacing(5);
        this.actionButtonBar.setAlignment(Pos.CENTER_RIGHT);
        this.topBar.add(actionButtonBar, 1, 0);
    }

    private String GetSearchFliedStyle(){
        return "-fx-text-fill: "+ Helpers.GetRgbaColorToStyleFx(engine.GuiService.FontColor.getValue(), 1) +";\n" +
                "    -jfx-focus-color: "+ Helpers.GetRgbaColorToStyleFx(engine.GuiService.AccentColor.getValue(),.7) +";\n" +
                "    -jfx-unfocus-color: #BDBDBD;\n" +
                "    -jfx-label-float: true;\n" +
                "    -fx-font-size: 19px;\n" +
                "    -fx-background-color: transparent;\n" +
                "    -fx-border-color: transparent;\n" +
                "    -fx-border-radius: 2px;\n" +
                "    -fx-padding: 8px;";
    }

    private void Initialize(){

        ColumnConstraints column0 = new ColumnConstraints();
        column0.setPercentWidth(60); // ustawienie szerokości pierwszej kolumny na 60% szerokości siatki
        topBar.getColumnConstraints().add(column0);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(40); // ustawienie szerokości pierwszej kolumny na 60% szerokości siatki
        topBar.getColumnConstraints().add(column1);


        topBar.setStyle("-fx-border-color: "+ Helpers.GetRgbaColorToStyleFx(engine.GuiService.AccentColor.getValue(), .7) +" -fx-border-width: 0 0 3 0;");
        this.setTop(topBar);

        catalogContent.setPadding(new Insets(30, 0, 0, 45));
        catalogContent.setHgap(22);
        catalogContent.setVgap(22);
        ScrollPane jfxScrollPane = new ScrollPane();
        jfxScrollPane.setContent(this.catalogContent);
        jfxScrollPane.widthProperty().addListener((observableValue, number, t1) -> catalogContent.setPrefWrapLength(t1.doubleValue() - 45));
        this.setCenter(jfxScrollPane);

        this.addEventFilter(CatalogEvent.CATALOG_ACTION_EVENT_TYPE, new CatalogEventHandler() {
            @Override
            public void onCatalogActionEvent(CatalogAction action) {
                switch (action) {
                    case Exit -> {
                        Exit();
                    }
                    case Reload -> {
                        Reload();
                    }
                    case Search -> {
                        Search();
                    }
                }
            }
        });

        Label urlLabel = new Label();
        urlLabel.setStyle("-fx-font-weight: bold");
        setBottom(urlLabel);
        windowManager.UrlProperty.addListener((observableValue, s, t1) -> urlLabel.setText(t1));
    }

    private void InitializeCatalogButton(){
        this.CatalogButton = new CatalogButton(this.fontIcon.getIconCode(), this.displayText, false, this.engine);
        ((CatalogButton)this.CatalogButton).setOnAction(actionEvent -> {
            if (CheckUserPermissions(this.permissions, engine, CatalogButton.getNode())) Open();
        });
    }
    private void InitializeIconLabel() {
        this.fontIcon = new FontIcon(this.iconCode);
        this.fontIcon.setIconSize(48);
        this.fontIcon.setIconColor(engine.GuiService.FontColor.getValue());
        this.engine.GuiService.FontColor.addListener((observable, oldValue, newValue) -> {
            this.fontIcon.setIconColor(newValue);
        });


        displayTextLabel.setFont(new Font(48));

        HBox leftTopBar = new HBox(fontIcon, displayTextLabel);
        leftTopBar.setSpacing(5);
        leftTopBar.setAlignment(Pos.CENTER_LEFT);
        this.topBar.add(leftTopBar, 0 , 0);
    }
    @Override
    public void Add(IRecord record){
        catalogContent.getChildren().add((Node) record.getRecordButton());
    }

    @Override
    public void Open(){
        this.fireEvent(new CatalogEvent(CatalogAction.Open));
        this.windowManager.AddCatalog(this);
    }

    @Override
    public void Exit(){
        this.windowManager.RemoveCatalog(this);
    }

    @Override
    public void Reload() {

    }

    @Override
    public void Search() {
        
        if (!searchTextField.isVisible()){
            searchTextField.setVisible(true);
            searchTextField.setManaged(true);
            searchTextField.requestFocus();
        } else{
            searchTextField.setVisible(false);
            searchTextField.setManaged(false);
            searchTextField.setText("");
        }
    }

    @Override
    public String getDisplayText() {
        return displayText;
    }

    @Override
    public WindowManager getWindowManager() {
        return windowManager;
    }

    @Override
    public IRecordButton getRecordButton() {
        return CatalogButton;
    }
    @Override
    public Node getNode(){
        return this;
    }
    @Override
    public String getUUID() {
        return this.UUID;
    }
    @Override
    public GridPane getTopBar() {
        return topBar;
    }
}
