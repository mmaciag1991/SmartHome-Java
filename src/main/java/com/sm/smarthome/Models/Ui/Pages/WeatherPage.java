package com.sm.smarthome.Models.Ui.Pages;

import com.sm.smarthome.Core.Engine;
import com.sm.smarthome.Enums.Actions.ButtonAction;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonSize;
import com.sm.smarthome.Enums.Ui.Bottons.ButtonWidthType;
import com.sm.smarthome.Enums.Ui.Pages.PageType;
import com.sm.smarthome.Models.Ui.Buttons.MarkButton;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class WeatherPage extends PageBase{
    public String Address = "https://embed.windy.com";
    public String Address2 = "https://embed.windy.com/embed2.html?lat=51.917&lon=19.907&detailLat=50.067&detailLon=14.383&width=650&height=450&zoom=10&level=surface&overlay=wind&product=ecmwf&menu=&message=true&marker=&calendar=now&pressure=&type=map&location=coordinates&detail=&metricWind=default&metricTemp=default&radarRange=-1";
    public WeatherPage(Engine engine){
        this.Index = 1;
        this.Name = "Weather Page";
        this.DisplayName = "Weather";
        this.Type = PageType.Main;
        WebView webView = new WebView();
        webView.getEngine().load(Address2);
        //webView.setZoom(1.5);
        this.BodyInstance = webView;
        this.Button = new MarkButton(MaterialDesign.MDI_WEATHER_LIGHTNING_RAINY, DisplayName, null, ButtonAction.ActionWeatherPage, ButtonSize.Big, ButtonWidthType.Widthx2, engine);
    }
}
