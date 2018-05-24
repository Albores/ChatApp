package com.example.fiuady.chatapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "chats")
public class ChatsTable {

    @PrimaryKey
    @NonNull
    @ColumnInfo (name = "id")
    private int id;

    @ColumnInfo (name = "name")
    private String chatname;

    @ColumnInfo (name = "type")
    @NonNull
    private String chattype;

    @ColumnInfo (name = "last_message")
    @NonNull
    private String last_message;

    @ColumnInfo (name = "date")
    @NonNull
    private String date;

    public ChatsTable(@NonNull int id, String chatname, @NonNull String chattype, @NonNull String last_message, @NonNull String date) {
        this.id = id;
        this.chatname = chatname;
        this.chattype = chattype;
        this.last_message = last_message;
        this.date = date;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getChatname() {
        return chatname;
    }

    public void setChatname(String chatname) {
        this.chatname = chatname;
    }

    @NonNull
    public String getChattype() {
        return chattype;
    }

    public void setChattype(@NonNull String chattype) {
        this.chattype = chattype;
    }

    @NonNull
    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(@NonNull String last_message) {
        this.last_message = last_message;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }
}
