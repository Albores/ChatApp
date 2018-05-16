package com.example.fiuady.chatapp;

import android.arch.persistence.room.*;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "group_messages")
public class GroupMessagesTable {

    @PrimaryKey
    @NonNull
    @ColumnInfo (name = "id")
    private int id;

    @NonNull
    @ColumnInfo (name = "message")
    private String message;

    @NonNull
    @ColumnInfo (name = "group_id")
    private int group_id;

    @NonNull
    @ColumnInfo (name = "sender_id")
    private int sender_id;

    @NonNull
    @ColumnInfo (name = "date")
    private String date;

    public GroupMessagesTable(@NonNull int id, @NonNull String message, @NonNull int group_id, @NonNull int sender_id, @NonNull String date) {
        this.id = id;
        this.message = message;
        this.group_id = group_id;
        this.sender_id = sender_id;
        this.date = date;
    }

    @NonNull
    public int getId() {
        return id;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    @NonNull
    public int getGroup_id() {
        return group_id;
    }

    @NonNull
    public int getSender_id() {
        return sender_id;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setMessage(@NonNull String message) {
        this.message = message;
    }

    public void setGroup_id(@NonNull int group_id) {
        this.group_id = group_id;
    }

    public void setSender_id(@NonNull int sender_id) {
        this.sender_id = sender_id;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }
}
