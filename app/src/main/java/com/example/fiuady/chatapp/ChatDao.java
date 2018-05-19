package com.example.fiuady.chatapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ChatDao {

    //Users
    @Query("SELECT * FROM users")
    List<UsersTable> getUsers();

    @Query("SELECT * FROM users WHERE last_name LIKE :last_name") //referencia a un parámetro con autocompletar
    UsersTable getUserByLastName(String last_name);

    @Query("SELECT * FROM users WHERE id in (:ids)")
    List<UsersTable> getUserById(int[] ids);

    @Query("SELECT first_name FROM users WHERE id = :id")
    String getFirstNameById(int id);

    @Query("SELECT last_name FROM users WHERE id = :id")
    String getLastNameById(int id);

    @Query("SELECT phone_number FROM users WHERE id = :id")
    String getPhoneNumberById(int id);

    @Query("SELECT password FROM users WHERE id = :id")
    String getPasswordById(int id);

    @Query("SELECT id FROM users WHERE first_name = :first_name")
    int getIdByFirstName(String first_name);

    @Query("SELECT id FROM users WHERE last_name + first_name = :complete_name")
    int getIdByCompleteName(String complete_name);

    @Query("SELECT id FROM users WHERE phone_number = :phone_number")
    int getIdByPhoneNumber(String phone_number);

    @Query("SELECT MAX(id) FROM users")
    int getMaxIdUsers();

    @Insert
    void InsertUser(UsersTable user);

    @Update
    void UpdateUser(UsersTable user);

    //Groups
    @Query("SELECT name FROM groups WHERE id = :id")
    String getGroupNameById(int id);

    @Query("SELECT creator_id FROM groups WHERE id = :id")
    int getGroupCreatorById(int id);

    @Query("SELECT admin_id FROM groups WHERE id = :id")
    int getGroupAdminById(int id);

    @Query("SELECT COUNT(name) FROM groups WHERE id = :id")
    int checkStartedGroupById(int id); //0 : NOT STARTED; >0 : STARTED

    @Query("SELECT MAX(id) FROM groups")
    int getMaxIdGroups();

    //Messages
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
    String getDateMessageById(int id);

    @Query("SELECT date FROM messages WHERE sender_id = :contact_id AND receiver_id = :my_id OR receiver_id = :contact_id AND sender_id = :my_id ORDER BY id DESC LIMIT 1")
    String getLastDateOfContact(int contact_id, int my_id);

    @Insert
    void InsertMessage(MessagesTable message);

    //GroupMessages
    @Query("SELECT message FROM group_messages WHERE sender_id = :sender_id")
    String getMessageBySenderId(int sender_id);

    @Query("SELECT message FROM group_messages WHERE id = :id")
    String getMessageByIdGroup(int id);

    @Query("SELECT sender_id FROM group_messages WHERE id = :id")
    int getSenderIdByMessageIdGroup(int id);

    @Query("SELECT COUNT(*) FROM group_messages WHERE group_id = :group_id AND sender_id = :my_id")
    int checkStartedGroup(int group_id, int my_id); //0 : NOT STARTED; >0 : STARTED

    @Query("SELECT message FROM group_messages WHERE group_id = :group_id ORDER BY id DESC LIMIT 1")
    String getLastMessageByGroupId(int group_id);

    @Query("SELECT date FROM group_messages WHERE group_id = :group_id ORDER BY id DESC LIMIT 1")
    String getLastDateByGroupId(int group_id);

    @Query("SELECT MAX(id) FROM group_messages")
    int getMaxIdMessageGroup();

    @Query("SELECT sender_id FROM group_messages WHERE message = :message")
    int getSenderIdByMessageGroup(String message);

    @Query("SELECT date FROM group_messages WHERE id = :id")
    String getDateByIdMessageGroup(int id);

    @Query("SELECT date FROM group_messages WHERE sender_id = :sender_id ORDER BY id DESC LIMIT 1")
    String getLastDateOfSenderId(int sender_id);

    @Query("SELECT first_name FROM users WHERE id = :sender_id")
    String getFirstNameByIdGroup(int sender_id);

    @Query("SELECT group_id FROM group_messages WHERE id = :message_id")
    int getGroupIdByMsgId(int message_id);

    @Insert
    void InsertMessage(GroupMessagesTable groupMessage);
}