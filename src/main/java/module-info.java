module com.sm.smarthome {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.kordamp.ikonli.fluentui;
    requires org.kordamp.bootstrapfx.core;
    requires com.jfoenix;
    requires org.kordamp.ikonli.core;
    requires org.jetbrains.annotations;
    requires org.jfxtras.styles.jmetro;
    requires eu.hansolo.tilesfx;
    requires eu.hansolo.fx.countries;
    requires eu.hansolo.fx.heatmap;
    requires eu.hansolo.toolboxfx;
    requires eu.hansolo.toolbox;
    requires org.apache.commons.lang3;
    requires jdk.management;
    requires json.simple;
    requires java.xml.bind;
    requires java.logging;


    opens com.sm.smarthome to javafx.fxml;
    exports com.sm.smarthome;
    exports com.sm.smarthome.Interfaces;
    opens com.sm.smarthome.Interfaces to javafx.fxml;
    exports com.sm.smarthome.Core;
    opens com.sm.smarthome.Core to javafx.fxml;
    exports com.sm.smarthome.Events;
    opens com.sm.smarthome.Events to javafx.fxml;
    exports com.sm.smarthome.Core.Services;
    opens com.sm.smarthome.Core.Services to javafx.fxml;
    exports com.sm.smarthome.Core.Providers;
    opens com.sm.smarthome.Core.Providers to javafx.fxml;
    exports com.sm.smarthome.Controllers;
    opens com.sm.smarthome.Controllers to javafx.fxml;
    exports com.sm.smarthome.Controllers.OtherControls;
    opens com.sm.smarthome.Controllers.OtherControls to javafx.fxml;
    exports com.sm.smarthome.Core.Providers.Gui;
    opens com.sm.smarthome.Core.Providers.Gui to javafx.fxml;
    exports com.sm.smarthome.Controllers.Pages;
    opens com.sm.smarthome.Controllers.Pages to javafx.fxml;
    exports com.sm.smarthome.CustomControls.Keyboard;
    opens com.sm.smarthome.CustomControls.Keyboard;
    exports com.sm.smarthome.CustomControls.HanSolo.Funmenu;
    opens com.sm.smarthome.CustomControls.HanSolo.Funmenu;


}