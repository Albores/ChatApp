package com.example.fiuady.chatapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

@Dao
public interface GroupsDao {

    @Query("SELECT name FROM groups WHERE id = :id")
    String getNameById(int id);

    @Query("SELECT creator_id FROM groups WHERE id = :id")
    int getCreatorById(int id);

    @Query("SELECT admin_id FROM groups WHERE id = :id")
    int getAdminById(int id);

    @Query("SELECT COUNT(name) FROM groups WHERE id = :id")
    int checkStartedGroupById(int id); //0 : NOT STARTED; >0 : STARTED

    @Query("SELECT MAX(id) FROM groups")
    int getMaxId();
}
