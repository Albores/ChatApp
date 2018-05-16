package com.example.fiuady.chatapp;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

@Entity (tableName = "users")
public class UsersTable {

    @PrimaryKey
    @ColumnInfo (name = "id")
    private int id;

    @ColumnInfo (name = "first_name")
    @NonNull
    private String firstName;

    @ColumnInfo (name = "last_name")
    @NonNull
    private String lastName;

    @ColumnInfo (name = "phone_number")
    @NonNull
    private String phoneNumber;

    @ColumnInfo (name = "password")
    @NonNull
    private String password;

    public UsersTable(int id, @NonNull String firstName, @NonNull String lastName, @NonNull String phoneNumber, @NonNull String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    //Getter
    public int getId() {
        return id;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @NonNull
    public String getPassword() {
        return password;
    }


    //Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(@NonNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(@NonNull String password) {
        this.firstName = password;
    }
}
