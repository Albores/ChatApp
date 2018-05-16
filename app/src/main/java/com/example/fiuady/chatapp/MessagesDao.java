package com.example.fiuady.chatapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MessagesDao {
    @Query("SELECT * FROM messages")
    List<MessagesTable> getChats();

    @Query("SELECT message FROM messages WHERE id = :id")
    String getMessageById(int id);

    @Query("SELECT message FROM messages WHERE id = :id AND sender_id = :sender_id AND receiver_id = :receiver_id")
    String getMessageByAll(int id, int sender_id, int receiver_id);

    @Query("SELECT sender_id FROM messages WHERE id = :id")
    int getSenderIdByMessageId(int id);

    @Query("SELECT receiver_id FROM messages WHERE id = :id")
    int getReceiverIdByMessageId(int id);

    @Query("SELECT COUNT(id) FROM messages WHERE sender_id = :contact_id OR receiver_id = :contact_id")
    int countMessagesbyContactId(int contact_id);

    @Query("SELECT COUNT(message) FROM messages WHERE sender_id = :contact_id AND receiver_id = :my_id OR receiver_id = :contact_id AND sender_id = :my_id")
    int checkStartedChatWithContact(int contact_id, int my_id); //0 : NOT STARTED; >0 : STARTED

    @Query("SELECT message FROM messages WHERE sender_id = :contact_id AND receiver_id = :my_id OR receiver_id = :contact_id AND sender_id = :my_id ORDER BY id DESC LIMIT 1")
    String getLastMessageOfContact(int contact_id, int my_id);

    @Query("SELECT COUNT(*) FROM messages WHERE sender_id = :contact_id AND receiver_id = :my_id OR receiver_id = :contact_id AND sender_id = :my_id")
    int countMessagesOfContact(int contact_id, int my_id);

    @Query("SELECT MAX(id) FROM messages")
    int getMaxMessagesId();

    @Query("SELECT sender_id FROM messages WHERE message = :message")
    int getSenderIdByMessage(String message);

    @Query("SELECT date FROM messages WHERE id = :id")
    String getDateById(int id);

    @Query("SELECT date FROM messages WHERE sender_id = :contact_id AND receiver_id = :my_id OR receiver_id = :contact_id AND sender_id = :my_id ORDER BY id DESC LIMIT 1")
    String getLastDateOfContact(int contact_id, int my_id);

    @Insert
    void InsertMessage(MessagesTable message);


}
