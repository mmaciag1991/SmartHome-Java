package com.sm.smarthome.Enums.Other;

public enum UserPermissions {
    Guest(0),
    User(1),
    Administrator(2),
    Service(3);

    private int value;
    private UserPermissions(int value){
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
