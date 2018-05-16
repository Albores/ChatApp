package com.example.fiuady.chatapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface GroupMessagesDao {

    @Query("SELECT message FROM group_messages WHERE sender_id = :sender_id")
    String getMessageBySenderId(int sender_id);

    @Query("SELECT message FROM group_messages WHERE id = :id")
    String getMessageById(int id);

    @Query("SELECT sender_id FROM group_messages WHERE id = :id")
    int getSenderIdByMessageId(int id);

    @Query("SELECT COUNT(*) FROM group_messages WHERE group_id = :group_id AND sender_id = :my_id")
    int checkStartedGroup(int group_id, int my_id); //0 : NOT STARTED; >0 : STARTED

    @Query("SELECT message FROM group_messages WHERE group_id = :group_id ORDER BY id DESC LIMIT 1")
    String getLastMessageByGroupId(int group_id);

    @Query("SELECT date FROM group_messages WHERE group_id = :group_id ORDER BY id DESC LIMIT 1")
    String getLastDateByGroupId(int group_id);

    @Query("SELECT MAX(id) FROM group_messages")
    int getMaxId();

    @Query("SELECT sender_id FROM group_messages WHERE message = :message")
    int getSenderIdByMessage(String message);

    @Query("SELECT date FROM group_messages WHERE id = :id")
    String getDateById(int id);

    @Query("SELECT date FROM group_messages WHERE sender_id = :sender_id ORDER BY id DESC LIMIT 1")
    String getLastDateOfSenderId(int sender_id);

    @Query("SELECT first_name FROM users WHERE id = :sender_id")
    String getFirstNameById(int sender_id);

    @Query("SELECT group_id FROM group_messages WHERE id = :message_id")
    int getGroupIdByMsgId(int message_id);

    @Insert
    void InsertMessage(GroupMessagesTable groupMessage);
}
