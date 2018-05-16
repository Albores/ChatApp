package com.example.fiuady.chatapp;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

@Entity (tableName = "messages")
public class MessagesTable {

    @PrimaryKey
    @NonNull
    @ColumnInfo (name = "id")
    private int id;

    @ColumnInfo (name = "message")
    private String message;

    @ColumnInfo (name = "sender_id")
    @NonNull
    private int sender_id;

    @ColumnInfo (name = "receiver_id")
    @NonNull
    private int receiver_id;

    @ColumnInfo (name = "date")
    @NonNull
    private String date;

    public MessagesTable(@NonNull int id, @NonNull String message, @NonNull int sender_id, @NonNull int receiver_id, @NonNull String date){
        this.id = id;
        this.message = message;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.date = date;
    }


    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @NonNull
    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(@NonNull int sender_id) {
        this.sender_id = sender_id;
    }

    @NonNull
    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(@NonNull int receiver_id) {
        this.receiver_id = receiver_id;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }


}
