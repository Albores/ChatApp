package com.example.fiuady.chatapp;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

@Entity (tableName = "users")
public class UsersTable {

    @PrimaryKey
    @ColumnInfo (name = "id")
    private int id;

    @ColumnInfo (name = "username")
    @NonNull
    private String username;

    @ColumnInfo (name = "password")
    @NonNull
    private String password;

    public UsersTable(int id, @NonNull String username, @NonNull String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
