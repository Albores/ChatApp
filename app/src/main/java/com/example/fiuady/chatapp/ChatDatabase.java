package com.example.fiuady.chatapp;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.*;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {UsersTable.class, MessagesTable.class, GroupsTable.class, GroupMessagesTable.class,ContactTable.class}, version = 1, exportSchema = false)

public abstract class ChatDatabase extends RoomDatabase {

    public abstract ChatDao chatDao();

//    public abstract UsersDao usersDao();
//    public abstract MessagesDao messagesDao();
//    public abstract GroupsDao groupsDao();
//    public abstract GroupMessagesDao groupMessagesDao();

    private static ChatDatabase Instance;

    static ChatDatabase getDatabase(final Context context) {
        if (Instance == null) {
            synchronized (ChatDatabase.class) {
                if (Instance == null) {
                    Instance = Room.databaseBuilder(context, ChatDatabase.class, "ChatDatabase").
                            allowMainThreadQueries().addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            //Valores iniciales de la DB
                            //Chats usuarios
                            db.execSQL("INSERT INTO users(id, username, password, status, avatar) VALUES (0, 'Albores', '1234','Disponible','cosa_chida')");

                            db.execSQL("INSERT INTO messages(id, message, sender_id, receiver_id, date) VALUES (0, 'Jose a Sara', 3, 0, '20180514 at 00:25 AM')");
                            db.execSQL("INSERT INTO messages(id, message, sender_id, receiver_id, date) VALUES (1, 'Marisol a Jose', 4, 3, '20180514 at 00:28 AM')");
                            db.execSQL("INSERT INTO messages(id, message, sender_id, receiver_id, date) VALUES (2, 'Jose a Ger', 3, 1, '20180514 at 00:30 AM')");
                            db.execSQL("INSERT INTO messages(id, message, sender_id, receiver_id, date) VALUES (3, 'Albores a Jose', 2, 3, '20180514 at 00:35 AM')");
                            db.execSQL("INSERT INTO messages(id, message, sender_id, receiver_id, date) VALUES (4, 'Jose a Saul', 3, 50, '20180514 at 00:40 AM')");
                            db.execSQL("INSERT INTO messages(id, message, sender_id, receiver_id, date) VALUES (5, 'Ger a Jose, un mensaje largo para probar formato', 1, 3, '20180514 at 02:46 AM')");

                            db.execSQL("INSERT INTO groups(id, name, creator_id, admin_id) VALUES (0, 'Las Guapas', 0, 0)");
                            db.execSQL("INSERT INTO groups(id, name, creator_id, admin_id) VALUES (1, 'Los Guapetones', 3, 3)");

                            db.execSQL("INSERT INTO group_messages(id, message, group_id, sender_id, date) VALUES (0, 'Sara me ha unido al grupo', 0, 0, '20180514 at 03:56 PM')");
                            db.execSQL("INSERT INTO group_messages(id, message, group_id, sender_id, date) VALUES (1, 'Sara me ha unido al grupo', 0, 4, '20180514 at 03:56 PM')");
                            db.execSQL("INSERT INTO group_messages(id, message, group_id, sender_id, date) VALUES (2, 'Sara me ha unido al grupo', 0, 1, '20180514 at 03:56 PM')");
                            db.execSQL("INSERT INTO group_messages(id, message, group_id, sender_id, date) VALUES (3, 'Jose me ha unido al grupo', 1, 3, '20180514 at 03:57 PM')");
                            db.execSQL("INSERT INTO group_messages(id, message, group_id, sender_id, date) VALUES (4, 'Jose me ha unido al grupo', 1, 2, '20180514 at 03:57 PM')");
                            db.execSQL("INSERT INTO group_messages(id, message, group_id, sender_id, date) VALUES (5, 'Jose me ha unido al grupo', 1, 5, '20180514 at 03:57 PM')");

                        }
                    }).build();


                }
            }
        }
        return Instance;
    }
}

