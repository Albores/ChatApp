package com.example.fiuady.chatapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "contacts")
public class ContactTable {

    @PrimaryKey
    @ColumnInfo (name = "id")
    private int id;

    @ColumnInfo (name = "username")
    @NonNull
    private String username;

    @ColumnInfo (name = "password")
    @NonNull
    private String password;

    @ColumnInfo (name = "status")
    @NonNull
    private String status;

    @ColumnInfo (name = "avatar")
    @NonNull
    private String avatar;

    public ContactTable(int id, @NonNull String username, @NonNull String password, @NonNull String status, @NonNull String avatar) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    public void setStatus(@NonNull String status) {
        this.status = status;
    }

    @NonNull
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(@NonNull String avatar) {
        this.avatar = avatar;
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
