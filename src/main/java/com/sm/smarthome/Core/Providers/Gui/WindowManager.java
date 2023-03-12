package com.sm.smarthome.Core.Providers.Gui;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Interfaces.ICatalog;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Region;

public class WindowManager {
    Tile catalogWindow;
    public SimpleStringProperty UrlProperty = new SimpleStringProperty("/");
    ObservableList<ICatalog> catalogs = FXCollections.observableArrayList();

    public SimpleBooleanProperty ExitAvailableProperty = new SimpleBooleanProperty(false);
    public WindowManager(Engine engine){
        catalogWindow = TileBuilder.create()
                .skinType(Tile.SkinType.CUSTOM)
                .padding(new Insets(5,-10,5,-10))
                .build();



        catalogs.addListener((ListChangeListener<ICatalog>) change -> {

            try {
                String url = "";
                for (ICatalog catalog : catalogs) {
                    url += engine.SystemService.Language.Resources.getString(catalog.getDisplayText()) + "/";
                }
                UrlProperty.setValue(url);
                catalogWindow.setGraphic(catalogs.get(catalogs.size() - 1).getNode());
            } catch (Exception e) {

                System.err.println(e);

            }
            if (catalogs.size() > 1){
                ExitAvailableProperty.setValue(true);
            }else{
                ExitAvailableProperty.setValue(false);
            }
            if (catalogs.size() == 0)
                Platform.runLater(() -> catalogWindow.setGraphic(new Region()));
        });
    }
    public void AddCatalog(ICatalog catalog){
        catalogs.add(catalog);
    }
    public void RemoveCatalog(ICatalog catalog) {
        catalogs.remove(catalog);
    }
    public void RemoveCatalog(String UUID){
        ICatalog catalogToRemove = null;
        for (ICatalog catalog : catalogs) {
            if (catalog.getUUID().equals(UUID)) {
                catalogToRemove = catalog;
            }
        }
        if (catalogToRemove == null) return;
        RemoveCatalog(catalogToRemove);
    }

    public Node GetManagerWindowNode(){
        return catalogWindow;
    }
}
