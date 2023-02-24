package com.sm.smarthome.Core.Providers.Gui;

import java.util.Locale;

import com.sm.smarthome.Core.Engine;
import eu.hansolo.tilesfx.*;
import eu.hansolo.tilesfx.tools.Helper;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

public class TilesProvider {
    private Engine engine;
    private Insets insets = new Insets(4);
    public TilesProvider(Engine engine){
        this.engine = engine;
    }

    public Tile getClockTile() {
        Tile clockTile = TileBuilder.create()
                .skinType(Tile.SkinType.CLOCK)
                .title("Clock Tile")
                .text("Whatever text")
                .dateVisible(true)
                .locale(engine.SystemService.Language.Locale)
                .running(true)
                .build();
        clockTile.setPadding(new Insets(4));
        return clockTile;
    }

    public Tile getProcentageTile(String title, String description, double maxValue, SimpleDoubleProperty value){

        Tile tile = TileBuilder.create()
                .skinType(Tile.SkinType.PERCENTAGE)
                .title(title)
                .unit(Helper.PERCENTAGE)
                .description(description)
                .maxValue(maxValue)
                .value(value.doubleValue())
                .padding(insets)
                .barColor(engine.GuiService.AccentColor.getValue())
                .build();

        value.addListener((observable, oldValue, newValue) -> tile.setValue(newValue.doubleValue()));
        engine.GuiService.AccentColor.addListener((observable, oldValue, newValue) -> tile.setBarColor(newValue));

        return tile;
    }

    public Tile getGuageTile(String title, String unit, SimpleDoubleProperty threshold, SimpleDoubleProperty value){
        Tile gaugeTile = TileBuilder.create()
                .skinType(Tile.SkinType.GAUGE)
                .title(title)
                .unit(unit)
                .threshold(threshold.doubleValue())
                .value(value.doubleValue())
                .thresholdColor(Color.RED)
                .barColor(Color.FORESTGREEN)
                .build();

        threshold.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> gaugeTile.setThreshold(newValue.doubleValue())));
        value.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> gaugeTile.setValue(newValue.doubleValue())));

        return gaugeTile;
    }
}
