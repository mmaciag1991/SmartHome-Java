package com.sm.smarthome.Core.Providers.Gui;

import com.sm.smarthome.Interfaces.ICatalog;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Region;

public class WindowManager {
    Tile catalogWindow;
    ObservableList<ICatalog> catalogs = FXCollections.observableArrayList();
    public WindowManager(){
        catalogWindow = TileBuilder.create()
                .skinType(Tile.SkinType.CUSTOM)
                .padding(new Insets(5,-10,5,-10))
                .build();

        catalogs.addListener((ListChangeListener<ICatalog>) change -> {

            try {
                catalogWindow.setGraphic(catalogs.get(catalogs.size() - 1).getNode());
            } catch (Exception e) {

                System.err.println(e);

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
