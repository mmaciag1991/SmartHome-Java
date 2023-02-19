package com.sm.smarthome.Models.Ui.Pages;

import com.sm.smarthome.Enums.Ui.Pages.PageType;
import com.sm.smarthome.Models.Ui.Buttons.MarkButton;
import javafx.scene.Node;
import javafx.scene.control.Control;

public class PageBase {
    public int Index;
    public String Name;
    public String DisplayName;
    public PageType Type;
    public Node BodyInstance;
    public MarkButton Button;
}
