package com.sm.smarthome.Enums.Ui.Bottons;

public enum ButtonWidthType {
    Normal(1),
    Widthx1_5(1.5),
    Widthx2(2),
    Widthx2_5(2.5),
    Widthx3(3),
    Widthx4(4),
    Widthx5(5),
    Widthx6(6),
    Widthx7(7),
    Widthx8(8),
    Widthx9(8),
    Widthx10(8);

    private double value;
    private ButtonWidthType(double value){
        this.value = value;
    }
    public double getValue() {
        return value;
    }
}