package com.example.fiuady.chatapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android.volley.toolbox.StringRequest;

import java.util.List;

@Dao
public interface ChatDao {

    //Users
    @Query("SELECT * FROM users")
    List<UsersTable> getUsers();

    @Query("SELECT * FROM users WHERE id in (:ids)")
    List<UsersTable> getUserById(int[] ids);

    @Query("select username from users where id = :id")
    String getUserNameById(int id);

    @Query("Select id from users where username =:username")
    int getIdByUserName(String username);

    @Query("Select id from users where id = :id")
    int getUserId(int id);

    @Query("SELECT password FROM users WHERE id = :id")
    String getPasswordById(int id);

    @Query("SELECT avatar FROM users WHERE id = :id")
    String getAvatarUser(int id);

    @Query("SELECT status FROM users WHERE id = :id")
    String getStatusUser(int id);

    @Query("SELECT MAX(id) FROM users")
    int getMaxIdUsers();

    @Query("update users set avatar =:nava where id =0")
    void updateAvatar(String nava);

    @Query("update users set status =:nstatus where id =0")
    void updateStatus(String nstatus);

    @Query("update users set password =:npass where id =0")
    void updatePassword(String npass);

    @Query("update users set username =:nusername where id =0")
    void updateUserName(String nusername);

    @Insert
    void InsertUser(UsersTable user);

    @Update
    void UpdateUser(UsersTable user);

    //Contacts
    @Query("SELECT * FROM contacts")
    List<ContactTable> getContacts();

    @Query("SELECT * FROM contacts WHERE id in (:ids)")
    List<ContactTable> getContactById(int[] ids);

    @Query("select username from contacts where id = :id")
    String getUserNameContactById(int id);

    @Query("Select id from contacts where username =:username")
    int getIdByUserNameContact(String username);

    @Query("Select id from users where id = :id")
    int getContactId(int id);

    @Query("SELECT password FROM contacts WHERE id = :id")
    String getPasswordContactById(int id);

    @Query("SELECT avatar FROM contacts WHERE id = :id")
    String getAvatarContact(int id);

    @Query("SELECT status FROM contacts WHERE id = :id")
    String getStatusContact(int id);

    @Query("SELECT MAX(id) FROM contacts")
    int getMaxIdContacts();

    @Insert
    void InsertContact(ContactTable contact);

    @Query("DELETE FROM contacts WHERE id =:id;")
    void deleteContacts(int id);

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

    @Query("SELECT username FROM users WHERE id = :sender_id")
    String getFirstNameByIdGroup(int sender_id);

    @Query("SELECT group_id FROM group_messages WHERE id = :message_id")
    int getGroupIdByMsgId(int message_id);

    @Insert
    void InsertMessage(GroupMessagesTable groupMessage);

    //Chats
    @Query("SELECT * FROM chats")
    List<ChatsTable> getAllChats();

    @Query("SELECT last_message FROM chats WHERE id = :id")
    String getLastMessageByChatId(int id);

    @Query("SELECT chatname FROM chats WHERE id = :id")
    String getChatName(int id);

    @Query("SELECT id FROM chats WHERE id = :id")
    int getChatid(int id);

    @Query("select chattype from chats where id=:id")
    String getTypeChat(int id);

    @Query("SELECT MAX(id) FROM chats")
    int getMaxChats();

    @Query("SELECT date FROM chats WHERE id = :id")
    String getChatDateById(int id);

    @Query("select participants from chats where id=:id")
    String getChatParticipants(int id);

    @Insert
    void InsertChat(ChatsTable chat);

    @Update
    void UpdateChat(ChatsTable chat);
}
