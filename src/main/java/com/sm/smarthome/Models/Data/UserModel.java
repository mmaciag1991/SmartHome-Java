package com.sm.smarthome.Models.Data;

import com.sm.smarthome.Enums.Other.UserPermissions;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserModel {
    public SimpleIntegerProperty Id = new SimpleIntegerProperty(-1);
    public SimpleStringProperty Name = new SimpleStringProperty("Guest");
    public SimpleObjectProperty<UserPermissions> UserPermission = new SimpleObjectProperty<UserPermissions>(UserPermissions.Guest);

    public UserModel(int id, String name, UserPermissions permissions) {
        this.Id.set(id);
        this.Name.setValue(name);
        UserPermission.setValue(permissions);
    }
}
