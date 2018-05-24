package com.example.fiuady.chatapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Contact {
    private int id;
    private String username;
    private String password;
    private String status;
    private String avatar;

    public Contact(int id, String username, String password, String status, String avatar) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView rvusernamecontact;
        private TextView rvidcontact;
        private TextView rvavatarcontact;
        private TextView rvstatuscontact;
        private Contact contact;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            rvusernamecontact = itemView.findViewById(R.id.usernamecontact_text);
            rvidcontact = itemView.findViewById(R.id.hided_user_id);
            rvavatarcontact = itemView.findViewById(R.id.avatarcontact_iv);
            rvstatuscontact = itemView.findViewById(R.id.statuscontact_text);
            context = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        public void bind(Contact contact) {
            this.contact = contact;
            rvusernamecontact.setText(contact.getUsername());
            rvidcontact.setText(String.valueOf(contact.getId()));
            rvstatuscontact.setText(contact.getStatus());
            rvavatarcontact.setText(contact.getAvatar());
        }

    }

    private List<Contact> contacts;


    public ContactAdapter(List<Contact> contacts) {
        super();
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rvusers_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}

class Chat {
    private int chatid;
    private String chatname;
    private String chattype;
    private String lastMessage;
    private String date;
    private String participants;
    //private String phoneNumber;
    //private String password;


    public Chat(int chatid, String chatname, String chattype, String lastMessage, String date, String participants) {
        this.chatid = chatid;
        this.chatname = chatname;
        this.chattype = chattype;
        this.lastMessage = lastMessage;
        this.date = date;
        this.participants = participants;
    }

    public int getChatid() {
        return chatid;
    }

    public void setChatid(int chatid) {
        this.chatid = chatid;
    }

    public String getChatname() {
        return chatname;
    }

    public void setChatname(String chatname) {
        this.chatname = chatname;
    }

    public String getChattype() {
        return chattype;
    }

    public void setChattype(String chattype) {
        this.chattype = chattype;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }
}

class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView rvchatid;
        private TextView rvchatname;
        private TextView rvchattype;
        private TextView rvLastMessage;
        private TextView rvDate;
        private TextView rvchatparticipants;
        private Chat chat;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            rvchatid = itemView.findViewById(R.id.hided_contact_id);
            rvchatname = itemView.findViewById(R.id.senderusername_text);
            rvchattype = itemView.findViewById(R.id.chattype_text);
            rvLastMessage = itemView.findViewById(R.id.last_message_text);
            rvDate = itemView.findViewById(R.id.date_text);
            rvchatparticipants = itemView.findViewById(R.id.chatparticipants_text);

            context = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra(ChatActivity.EXTRA_HIDED_ID, rvchatid.getText().toString());
            intent.putExtra(ChatActivity.EXTRA_SIMPLE_CHAT, true);
            ((Activity) context).startActivityForResult(intent, 0X01);

        }

        public void bind(Chat chats) {
            this.chat = chats;
            rvchatid.setText(chats.getChatid());
            rvchatname.setText(chats.getChatname());
            rvchattype.setText(chats.getChattype());
            rvLastMessage.setText(chats.getLastMessage());
            rvDate.setText(chats.getDate());
            rvchatparticipants.setText(chats.getParticipants());
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
    private TextView register_tv;
    private Button random_user;
    private EditText user_name;
    private EditText user_pass;
    private int destinyPort;
    private int originPort;
    private int destinyId;
    private GridView gridView;
    private String avatar;
    public String id_server_usuario="0";
    private int previousSelectedPosition = 0;
    private List<Contact> contactsList;

    public class ImageAdapter extends BaseAdapter {

        private Context mContext;
        public ImageView imageView;

        public ImageView getImageView() {
            return imageView;
        }

        // Keep all Images in array
        public Integer[] mThumbIds = {
                R.drawable.avatar_1, R.drawable.avatar_2,
                R.drawable.avatar_3, R.drawable.avatar_4,
                R.drawable.avatar_5, R.drawable.avatar_6

        };

        // Constructor
        public ImageAdapter(Context c) {
            mContext = c;
        }

        public Context getMContext() {
            return mContext;
        }

        @Override
        public int getCount() {
            return mThumbIds.length;
        }

        @Override
        public Object getItem(int position) {
            return mThumbIds[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
            /*
            Crear un nuevo Image View de 90x90
            y con recorte alrededor del centro
             */
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = (ImageView) convertView;
            }

            //Setear la imagen desde el recurso drawable
            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }
//            imageView = new ImageView(mContext);
//            imageView.setImageResource(R.drawable.avatar_2);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
//            return imageView;
    }

    private boolean checkExistingUser() {
        boolean la = false;
        boolean name_check = false;
        boolean pass_check = false;
        String log_name = user_name.getText().toString();
        String log_pass = user_pass.getText().toString();
        int max_user_id = db.chatDao().getMaxIdUsers();

        for (int i = 0; i <= max_user_id; i++) {
            if (log_name.equals(db.chatDao().getUserNameById(i))) {
                name_check = true;
            }
        }
        for (int i = 0; i <= max_user_id; i++) {
            if (log_pass.equals(db.chatDao().getPasswordById(i))) {
                pass_check = true;
            }
        }
        if (name_check && pass_check) {
            la = true;
        }
        return la;
    }

    private String URL_Usuarios = "https://serverxd.herokuapp.com/api/users";
    private String URL_Usuarios_username = "https://serverxd.herokuapp.com/api/usernames";
    private String URL_Usuarios_id = "https://serverxd.herokuapp.com/api/users/";
    private String URL_Validacion_Usuarios = "https://serverxd.herokuapp.com/api/users/validate";

    public JSONObject makingJsonChat(String name,String type) {
        JSONObject js = new JSONObject();
        try {
            js.put("name",name );
            js.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js;
    }

    public JSONObject makingJson() {
        JSONObject js = new JSONObject();
        try {
            js.put("username", user_name.getText().toString().trim());
            js.put("password", user_pass.getText().toString().trim());
            js.put("status", "Disponible");
            js.put("avatar", avatar);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js;
    }

    //    public void receiveAllUsersRequest(String URL) {
//
//        RequestQueue rq = Volley.newRequestQueue(this);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray sts = null;
//                    sts = jsonObject.getJSONArray("category");
//
//                    for (int i = 0; i < sts.length(); i++) {
//                        JSONObject jo = sts.getJSONObject(i);
//                        Category cc = new Category();
//                        cc.setId(jo.getInt("id"));
//                        cc.setName(jo.getString("name"));
//                        ar.add(cc);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        return ar;
//    }


    public void getAllUsersRequest(String URL) {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try {
                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject contacto = response.getJSONObject(i);
                                // Get the current student (json object) data
//                                Contact contact = new Contact(
//                                        contacto.optInt("id"),
//                                        contacto.optString("username"),
//                                        contacto.optString("password"),
//                                        contacto.optString("status"),
//                                        contacto.optString("avatar")
//                                );
                                ContactTable contact = new ContactTable(contacto.optInt("id"),
                                        contacto.optString("username"),
                                        contacto.optString("password"),
                                        contacto.optString("status"),
                                        contacto.optString("avatar"));
                                db.chatDao().InsertContact(contact);
                                // Toast.makeText(MainActivity.this, "contactLista:"+contact, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        queue.add(jsonArrayRequest);
    }

    public void sendJsonUserRequest(String URL) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, makingJson(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(MainActivity.this, "Response" + response, Toast.LENGTH_SHORT).show();
                id_server_usuario = response.optString("id");
                ActualUser.id = Integer.parseInt(id_server_usuario);
                UsersTable user = new UsersTable(0, response.optString("username"), response.optString("password"), response.optString("status"), response.optString("avatar"));
                db.chatDao().UpdateUser(user);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(jsonRequest);
    }

    public void sendJsonUserValidateRequest(String URL) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, makingJson(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if (response.optString("message").equals("Credenciales incorrectas")) {
                    //UsersTable user = new UsersTable(0, user_name.getText().toString(), user_pass.getText().toString(), db.chatDao().getStatusUser(0), db.chatDao().getAvatarUser(0));
                    //UsersTable user = new UsersTable(0, user_name.getText().toString(), user_pass.getText().toString(), db.chatDao().getStatusUser(0), db.chatDao().getAvatarUser(0));
                    // db.chatDao().UpdateUser(user);
                    //Toast.makeText(MainActivity.this, "Response" + response, Toast.LENGTH_SHORT).show();

                }else{
                   //Toast.makeText(MainActivity.this, "Response" + response, Toast.LENGTH_SHORT).show();
                    id_server_usuario = response.optString("id");
                    ActualUser.id = Integer.parseInt(id_server_usuario);
                    //Toast.makeText(MainActivity.this, "id:"+id_server_usuario, Toast.LENGTH_SHORT).show();
                    receiveJsonUserRequest(URL_Usuarios_id);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(jsonRequest);
    }

    public void receiveJsonUserRequest(String URL) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, URL+id_server_usuario, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(MainActivity.this, "Response " + response, Toast.LENGTH_SHORT).show();
                UsersTable user = new UsersTable(0, response.optString("username"), response.optString("password"), response.optString("status"), response.optString("avatar"));
                db.chatDao().UpdateUser(user);
                Intent intent = new Intent(MainActivity.this, NavigationMenu.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
            }
        }) {
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        db = ChatDatabase.getDatabase(MainActivity.this);
        gridView = (GridView) findViewById(R.id.gridview);
        login_btn = findViewById(R.id.login_btn);
        register_tv = findViewById(R.id.logon_btn);
        user_name = findViewById(R.id.name_txt);
        user_pass = findViewById(R.id.pass_txt);
//        UsersTable uno = db.chatDao().getUserByLastName("Chan");
//        uno.setFirstName("Jose");
//        db.chatDao().UpdateUser(uno);

        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageView previousSelectedView = (ImageView) parent.getChildAt(previousSelectedPosition);

                // If there is a previous selected view exists
                if (previousSelectedPosition != -1) {
                    // Set the last selected View to deselect
                    previousSelectedView.setSelected(false);

                    // Set the last selected View background color as deselected item
                    previousSelectedView.setBackgroundColor(Color.WHITE);

                    // Set the last selected View text color as deselected item
                    //previousSelectedView.setBackgroundColor(Color.DKGRAY);
                }
                // Set the current selected view position as previousSelectedPosition
                previousSelectedPosition = position;
                switch (position) {
                    case 0:
                        view.setBackgroundColor(Color.BLUE);
                        avatar = "avatar_0";
                        break;
                    case 1:
                        view.setBackgroundColor(Color.BLUE);
                        avatar = "avatar_1";
                        break;
                    case 2:
                        view.setBackgroundColor(Color.BLUE);
                        avatar = "avatar_2";
                        break;
                    case 3:
                        view.setBackgroundColor(Color.BLUE);
                        avatar = "avatar_3";
                        break;
                    case 4:
                        view.setBackgroundColor(Color.BLUE);
                        avatar = "avatar_4";
                        break;
                    case 5:
                        view.setBackgroundColor(Color.BLUE);
                        avatar = "avatar_5";
                        break;
                }

            }
        });
        for (int i = 0; i < db.chatDao().getMaxIdContacts() + 1; i++) {
            db.chatDao().deleteContacts(i);
        }
        getAllUsersRequest(URL_Usuarios);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendJsonUserValidateRequest(URL_Validacion_Usuarios);
                //receiveJsonUserRequest(URL_Usuarios,id_server_usuario);

//                if (checkExistingUser()) {
//                    ActualUser.id = db.chatDao().getIdByUserName(user_name.getText().toString());
//                    finish();
//                } else {
//                    Toast.makeText(MainActivity.this, "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
//                }
                //user_name.findFocus();
            }
        });

        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersTable user = new UsersTable(0, user_name.getText().toString(), user_pass.getText().toString(), "Disponible", avatar);
                db.chatDao().UpdateUser(user);
                makingJson();
                sendJsonUserRequest(URL_Usuarios);
            }
        });

    }


}
