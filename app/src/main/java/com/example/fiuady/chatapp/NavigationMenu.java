package com.example.fiuady.chatapp;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomOpenHelper;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class NavigationMenu extends AppCompatActivity {

    private ChatDatabase db;
    private RecyclerView recyclerContainer;
    private UsersAdapter usersAdapter;
    private ChatsAdapter chatsAdapter;
    private GroupsAdapter groupsAdapter;

    private int my_id = ActualUser.id;
    private int total_users;
    private int total_groups;

    private List<Chat> rv_chats_data = new ArrayList<>();
    private List<Group> rv_groups_data = new ArrayList<>();
    private List<User> rv_users_data = new ArrayList<>();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_chats:
                    fillChatsAdapter();
                    return true;

                case R.id.navigation_groups:
                    fillGroupsAdapter();
                    return true;

                case R.id.navigation_contacts:
                    fillContactsAdapter();
                    return true;
            }
            return false;
        }
    };

    void fillChatsAdapter(){
        rv_chats_data.clear();
        total_users = db.chatDao().getMaxIdUsers() + 1;
        for (int i = 0; i < total_users; i++){
            if(db.chatDao().checkStartedChatWithContact(i, my_id) > 0 && my_id != i) {
                rv_chats_data.add(new Chat(
                        i,
                        db.chatDao().getFirstNameById(i),
                        db.chatDao().getLastNameById(i),
                        db.chatDao().getLastMessageOfContact(i, my_id),
                        db.chatDao().getLastDateOfContact(i, my_id)));
            }
        }
        //Sorting by lastDate of lastMessage received or sent
        Collections.sort(rv_chats_data, new Comparator<Chat>() {
            @Override
            public int compare(Chat o1, Chat o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        chatsAdapter = new ChatsAdapter(rv_chats_data);
        recyclerContainer.setAdapter(chatsAdapter);
    }

    void fillGroupsAdapter(){
        rv_groups_data.clear();
        total_groups = db.chatDao().getMaxIdGroups() + 1;
        for (int i = 0; i < total_groups; i++){
            if(db.chatDao().checkStartedGroup(i, my_id) > 0) {
                rv_groups_data.add(new Group(
                        i,
                        db.chatDao().getGroupNameById(i),
                        db.chatDao().getLastMessageByGroupId(i),
                        db.chatDao().getLastDateByGroupId(i)));
            }
        }
        //Sorting by lastDate of lastMessage received or sent
        Collections.sort(rv_groups_data, new Comparator<Group>() {
            @Override
            public int compare(Group o1, Group o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        groupsAdapter = new GroupsAdapter(rv_groups_data);
        recyclerContainer.setAdapter(groupsAdapter);
    }

    void fillContactsAdapter(){
        //I'm using Users adapter and class to fill the information since I've not created the Contacts table
        total_users = db.chatDao().getMaxIdUsers() + 1;
        rv_users_data.clear();
        for (int i = 0; i < total_users; i++){
            if(i != my_id){
                rv_users_data.add(new User(
                        i,
                        db.chatDao().getFirstNameById(i),
                        db.chatDao().getLastNameById(i),
                        db.chatDao().getPhoneNumberById(i),
                        db.chatDao().getPasswordById(i)));
            }
        }
        usersAdapter = new UsersAdapter((rv_users_data));
        recyclerContainer.setAdapter(usersAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_menu);

        Stetho.initializeWithDefaults(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        db = ChatDatabase.getDatabase(NavigationMenu.this);

        recyclerContainer = findViewById(R.id.recycler_view_container);
        recyclerContainer.setLayoutManager(new LinearLayoutManager(this));

        fillChatsAdapter(); //Starts activity with chatsAdapter





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0X01:
                if (resultCode == RESULT_OK) {
                    fillChatsAdapter();
                }
                break;
            case 0x02:
                if(resultCode == RESULT_OK){
                    //fillGroupsAdapter();
                }
                break;
            default:
                //other activities
                break;
        }
    }


}
