package com.example.fiuady.chatapp;

import android.app.Activity;
import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


class User {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }


    public User(int id, String firstName, String lastName, String phoneNumber, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}

class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView rvFirstName;
        private TextView rvLastName;
        private TextView rvPhoneNumber;
        private TextView rvId;
        private User user;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            rvFirstName = itemView.findViewById(R.id.first_name_text);
            rvLastName = itemView.findViewById(R.id.last_name_text);
            rvPhoneNumber = itemView.findViewById(R.id.phone_number_text);
            rvId = itemView.findViewById(R.id.hided_user_id);
            context = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //nothing
        }

        public void bind(User users) {
            this.user = users;
            rvFirstName.setText(users.getFirstName());
            rvLastName.setText(users.getLastName());
            rvPhoneNumber.setText(users.getPhoneNumber());
            rvId.setText(String.valueOf(users.getId()));
        }

    }

    private List<User> users;


    public UsersAdapter(List<User> users) {
        super();
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rvusers_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}

class Chat {
    private int contactId;
    private String firstName;
    private String lastName;
    private String lastMessage;
    private String date;
    //private String phoneNumber;
    //private String password;

    public int getContactId() {
        return contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getDate() {
        return date;
    }

    public Chat(int contactId, String firstName, String lastName, String lastMessage, String date) {
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastMessage = lastMessage;
        this.date = date;
    }
}

class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView rvContactId;
        private TextView rvFirstName;
        private TextView rvLastName;
        private TextView rvLastMessage;
        private TextView rvDate;
        private Chat chat;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            rvContactId = itemView.findViewById(R.id.hided_contact_id);
            rvFirstName = itemView.findViewById(R.id.first_name_text);
            rvLastName = itemView.findViewById(R.id.last_name_text);
            rvLastMessage = itemView.findViewById(R.id.last_message_text);
            rvDate = itemView.findViewById(R.id.date_text);

            context = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra(ChatActivity.EXTRA_HIDED_ID, rvContactId.getText().toString());
            intent.putExtra(ChatActivity.EXTRA_SIMPLE_CHAT, true);
            ((Activity) context).startActivityForResult(intent, 0X01);

        }

        public void bind(Chat chats) {
            this.chat = chats;
            rvContactId.setText(String.valueOf(chats.getContactId()));
            rvFirstName.setText(chats.getFirstName());
            rvLastName.setText(chats.getLastName());
            rvLastMessage.setText(chats.getLastMessage());
            rvDate.setText(chats.getDate());
        }

    }

    private List<Chat> chats;

    public ChatsAdapter(List<Chat> chats) {
        super();
        this.chats = chats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rvchats_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(chats.get(position));
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
}

class Message {
    private int id;
    private String message;
    private int sender_id;
    private int receiver_id;
    private String date;

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getSender_id() {
        return sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public String getDate() {
        return date;
    }

    public Message(int id, String message, int sender_id, int receiver_id, String date) {
        this.id = id;
        this.message = message;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.date = date;
    }
}

class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView rvMessage;
        private TextView rvDate;
        private ImageView rvBubble;
        private Message message;
        private Context context;


        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            rvMessage = itemView.findViewById(R.id.rv_message_tv);
            rvDate = itemView.findViewById(R.id.rv_date_tv);
            rvBubble = itemView.findViewById(R.id.chat_bubble);
        }

        public void bind(Message messages) {
            this.message = messages;
            rvMessage.setText(messages.getMessage());
            rvDate.setText(messages.getDate());
            if (message.getSender_id() == ActualUser.id) {
                //rvMessage.setTextColor(Color.BLUE);
                rvMessage.setGravity(Gravity.RIGHT);
                rvBubble.setScaleX(-1);
            } else if (message.getSender_id() != ActualUser.id) {
                //rvMessage.setTextColor(Color.GREEN);
                rvMessage.setGravity(Gravity.LEFT);
                rvBubble.setScaleX(1);
            }
        }
    }

    private List<Message> messages;

    public MessagesAdapter(List<Message> messages) {
        super();
        this.messages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_message, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}

class Group {
    private int id;
    private String name;
    private String lastMessage;
    private String date;
//    private int creatorId;
//    private int adminId;

    public Group(int id, String name, String lastMessage, String date) {
        this.id = id;
        this.name = name;
        this.lastMessage = lastMessage;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getDate() {
        return date;
    }
}

class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView rvId;
        private TextView rvName;
        private TextView rvLastMessage;
        private TextView rvDate;
        private Group group;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            rvId = itemView.findViewById(R.id.hided_group_id);
            rvName = itemView.findViewById(R.id.group_name_text);
            rvLastMessage = itemView.findViewById(R.id.last_group_message_text);
            rvDate = itemView.findViewById(R.id.group_date_text);

            context = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra(ChatActivity.EXTRA_HIDED_GROUP_ID, rvId.getText().toString());
            intent.putExtra(ChatActivity.EXTRA_SIMPLE_CHAT, false);
            ((Activity) context).startActivityForResult(intent, 0X02);

        }

        public void bind(Group groups) {
            this.group = groups;
            rvId.setText(String.valueOf(groups.getId()));
            rvName.setText(groups.getName());
            rvLastMessage.setText(groups.getLastMessage());
            rvDate.setText(groups.getDate());
        }

    }

    private List<Group> groups;

    public GroupsAdapter(List<Group> groups) {
        super();
        this.groups = groups;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rvgroup_chats_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(groups.get(position));
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}

class GroupMessage {
    private int id;
    private String sender_name;
    private String message;
    private int sender_id;
    private String date;

    public GroupMessage(int id, String sender_name, String message, int sender_id, String date) {
        this.id = id;
        this.sender_name = sender_name;
        this.message = message;
        this.sender_id = sender_id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public String getMessage() {
        return message;
    }

    public int getSender_id() {
        return sender_id;
    }

    public String getDate() {
        return date;
    }
}

class GroupMessagesAdapter extends RecyclerView.Adapter<GroupMessagesAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView rvSenderName;
        private TextView rvMessage;
        private TextView rvDate;
        private ImageView rvBubble;
        private GroupMessage groupMessage;
        private Context context;


        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            rvSenderName = itemView.findViewById(R.id.rv_group_contact_name_tv);
            rvMessage = itemView.findViewById(R.id.rv_group_message_tv);
            rvDate = itemView.findViewById(R.id.rv_group_date_tv);
            rvBubble = itemView.findViewById(R.id.group_chat_bubble);
        }

        public void bind(GroupMessage groupMessages) {
            this.groupMessage = groupMessages;
            rvSenderName.setText(groupMessages.getSender_name());
            rvMessage.setText(groupMessages.getMessage());
            rvDate.setText(groupMessages.getDate());
            if (groupMessages.getSender_id() == ActualUser.id) {
                //rvMessage.setTextColor(Color.BLUE);
                rvMessage.setGravity(Gravity.RIGHT);
                rvBubble.setScaleX(-1);
            } else if (groupMessages.getSender_id() != ActualUser.id) {
                //rvMessage.setTextColor(Color.GREEN);
                rvMessage.setGravity(Gravity.LEFT);
                rvBubble.setScaleX(1);
            }
        }
    }

    private List<GroupMessage> groupMessages;

    public GroupMessagesAdapter(List<GroupMessage> groupMessages) {
        super();
        this.groupMessages = groupMessages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_group_message, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(groupMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return groupMessages.size();
    }
}


public class MainActivity extends AppCompatActivity {

    private final int CHATACTIVITY_BACK_RESULT = 0X01;

    private ChatDatabase db;
    private Button login_btn;
    private Button random_user;
    private EditText user_name;
    private EditText user_pass;
    private int destinyPort;
    private int originPort;
    private int destinyId;

    private boolean checkExistingUser() {
        boolean la = false;
        boolean name_check = false;
        boolean pass_check = false;
        String log_name = user_name.getText().toString();
        String log_pass = user_pass.getText().toString();
        int max_user_id = db.usersDao().getMaxId();

        for (int i = 0; i <= max_user_id; i++) {
            if (log_name.equals(db.usersDao().getFirstNameById(i))) {
                name_check = true;
            }
        }
        for (int i = 0; i <= max_user_id; i++) {
            if (log_pass.equals(db.usersDao().getPasswordById(i))) {
                pass_check = true;
            }
        }
        if (name_check && pass_check) {
            la = true;
        }
        return la;
    }

    private String server_url = "https://api.myjson.com/bins/e684u";


    public JSONObject makingJson(){
        JSONObject js = new JSONObject();
        try {
            js.put("User", user_name.getText());
            js.put("Password", user_pass.getText());
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return js;
    }
    public void sendJsonRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, server_url, makingJson(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MainActivity.this, "Response"+response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(jsonRequest);

}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);

        db = ChatDatabase.getDatabase(MainActivity.this);

        UsersTable uno = db.usersDao().getUserByLastName("Chan");
        uno.setFirstName("Jose");
        db.usersDao().UpdateUser(uno);

        login_btn = findViewById(R.id.login_btn);
        user_name = findViewById(R.id.name_txt);
        user_pass = findViewById(R.id.pass_txt);
        random_user = findViewById(R.id.random_btn);

        random_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int id_random = r.nextInt(db.usersDao().getMaxId() + 1);
                user_name.setText(db.usersDao().getFirstNameById(id_random));
                user_pass.setText(db.usersDao().getPasswordById(id_random));
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkExistingUser()) {
                    ActualUser.id = db.usersDao().getIdByFirstName(user_name.getText().toString());
                    Intent intent = new Intent(MainActivity.this, NavigationMenu.class);
                    makingJson();
                    sendJsonRequest();
                    //intent.putExtra(NavigationMenu.EXTRA_USER_ID, String.valueOf(db.usersDao().getIdByFirstName(user_name.getText().toString())));
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
                user_name.setText("");
                user_pass.setText("");
                //user_name.findFocus();
            }
        });


    }


}
