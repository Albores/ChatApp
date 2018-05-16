package com.example.fiuady.chatapp;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

@Entity (tableName = "groups")
public class GroupsTable {
    @PrimaryKey
    @NonNull
    @ColumnInfo (name = "id")
    private int id;

    @ColumnInfo (name = "name")
    @NonNull
    private String name;

    @ColumnInfo (name = "creator_id")
    @NonNull
    private  int creator_id;

    @ColumnInfo (name = "admin_id")
    @NonNull
    private int admin_id;

    public GroupsTable(@NonNull int id, @NonNull String name, @NonNull int creator_id, @NonNull int admin_id) {
        this.id = id;
        this.name = name;
        this.creator_id = creator_id;
        this.admin_id = admin_id;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public int getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(@NonNull int creator_id) {
        this.creator_id = creator_id;
    }

    @NonNull
    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(@NonNull int admin_id) {
        this.admin_id = admin_id;
    }
}
