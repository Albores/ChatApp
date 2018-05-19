package com.example.fiuady.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    public final static String EXTRA_STARTED_CHAT_ACTIVITY = "EXTRA_STARTED_CHAT_ACTIVITY";
    public final static String EXTRA_HIDED_ID = "EXTRA_HIDED_ID";
    public final static String EXTRA_HIDED_GROUP_ID = "EXTRA_HIDED_GROUP_ID";
    public final static String EXTRA_SIMPLE_CHAT = "EXTRA_SIMPLE_CHAT";

    private ChatDatabase db;
    private EditText toSendText;
    private Button sendButton;
    //private Button jButton;
    private TextView tvContactName;
    private RecyclerView rvChat;
    private MessagesAdapter messagesAdapter;
    private GroupMessagesAdapter groupMessagesAdapter;
    private int contactId;
    private int groupId;
    //private boolean simpleChat;
    private int messages_qty;
    //private Message msg;
    private List<Message> rv_messages_data = new ArrayList<>();
    private List<Message> new_rv_messages_data = new ArrayList<>();

    private List<GroupMessage> rv_group_messages_data = new ArrayList<>();
    private List<GroupMessage> new_rv_group_messages_data = new ArrayList<>();

    private String server_url = "https://api.myjson.com/bins/e684u";
    private String json_message;

    private boolean backPressed = false;

    public void receiveMessageJsonRequest(){
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, server_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_message = response.getString("message");
//                    int json_sender_id = response.getInt("sender_id");
//                    int json_receiver_id = response.getInt("receiver_id");
                    MessagesTable json_received_msg = new MessagesTable(
                            db.chatDao().getMaxMessagesId() + 1,
                            json_message,
                            contactId,
                            ActualUser.id,
                            getCurrentDate());
                    db.chatDao().InsertMessage(json_received_msg);
                    refreshChatAdapter();
                    rvChat.scrollToPosition(rv_messages_data.size() - 1);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChatActivity.this,"Error " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(jsonObjectRequest);
    }

    private String getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd 'at' HH:mm a");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }

    private boolean checkAcceptableField(){
        boolean lala = true;
        for(int i = 0; i < ActualUser.unacceptableFields.length; i++){
            if(ActualUser.unacceptableFields[i].equals(toSendText.getText().toString())){
                lala = false; break;
            }
            else {lala = true;}
        }
        return lala;
    }

    void fillChatAdapter(){
        messages_qty = db.chatDao().getMaxMessagesId() + 1;
        for (int i = 0; i < messages_qty; i++){
            //if(db.messagesDao().getSenderIdByMessageId(i) == contactId || db.messagesDao().getReceiverIdByMessageId(i) == contactId){
            int sender_id = db.chatDao().getSenderIdByMessageId(i);
            int receiver_id = db.chatDao().getReceiverIdByMessageId(i);
            if((sender_id == contactId && receiver_id == ActualUser.id) || (receiver_id == contactId && sender_id == ActualUser.id)) {
                String msg = db.chatDao().getMessageById(i);
                rv_messages_data.add(new Message(
                        i,
                        msg,
                        db.chatDao().getSenderIdByMessageId(i),
                        db.chatDao().getReceiverIdByMessageId(i),
                        db.chatDao().getDateMessageById(i)));
            }
        }
        messagesAdapter = new MessagesAdapter(rv_messages_data);
        rvChat.setAdapter(messagesAdapter);
        rvChat.scrollToPosition(rv_messages_data.size() - 1);
    }

    void refreshChatAdapter(){
        rv_messages_data.clear();
        new_rv_messages_data.clear();
        messages_qty = db.chatDao().getMaxMessagesId() + 1;
        for (int i = 0; i < messages_qty; i++){
            int sender_id = db.chatDao().getSenderIdByMessageId(i);
            int receiver_id = db.chatDao().getReceiverIdByMessageId(i);
            if((sender_id == contactId && receiver_id == ActualUser.id) || (receiver_id == contactId && sender_id == ActualUser.id)) {
                String msg2 = db.chatDao().getMessageById(i);
                new_rv_messages_data.add(new Message(
                        i,
                        msg2,
                        db.chatDao().getSenderIdByMessageId(i),
                        db.chatDao().getReceiverIdByMessageId(i),
                        db.chatDao().getDateMessageById(i)));
            }
        }
        rv_messages_data.addAll(new_rv_messages_data);
        messagesAdapter.notifyDataSetChanged();
    }

    void fillGroupAdapter(){
        messages_qty = db.chatDao().getMaxIdMessageGroup() + 1;
        for (int i = 0; i < messages_qty; i++){
            if(db.chatDao().getGroupIdByMsgId(i) == groupId) {
                String msg = db.chatDao().getMessageById(i);
                rv_group_messages_data.add(new GroupMessage(
                        i,
                        db.chatDao().getFirstNameById(db.chatDao().getSenderIdByMessageId(i)) + " " + db.chatDao().getLastNameById(db.chatDao().getSenderIdByMessageId(i)),
                        msg,
                        db.chatDao().getSenderIdByMessageId(i),
                        db.chatDao().getDateByIdMessageGroup(i)));
            }
        }
        groupMessagesAdapter = new GroupMessagesAdapter(rv_group_messages_data);
        rvChat.setAdapter(groupMessagesAdapter);
        rvChat.scrollToPosition(rv_group_messages_data.size() - 1);
    }

    void refreshGroupAdapter(){
        rv_group_messages_data.clear();
        new_rv_group_messages_data.clear();
        messages_qty = db.chatDao().getMaxIdMessageGroup() + 1;
        for (int i = 0; i < messages_qty; i++){
            if(db.chatDao().getGroupIdByMsgId(i) == groupId) {
                String gp_msg2 = db.chatDao().getMessageById(i);
                rv_group_messages_data.add(new GroupMessage(
                        i,
                        db.chatDao().getFirstNameById(db.chatDao().getSenderIdByMessageId(i)) + " " + db.chatDao().getLastNameById(db.chatDao().getSenderIdByMessageId(i)),
                        gp_msg2,
                        db.chatDao().getSenderIdByMessageId(i),
                        db.chatDao().getDateByIdMessageGroup(i)));
            }
        }
        rv_group_messages_data.addAll(new_rv_group_messages_data);
        groupMessagesAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toSendText = findViewById(R.id.to_send_text_contact_chat);
        sendButton = findViewById(R.id.to_send_button_contact_chat);
        //jButton = findViewById(R.id.json_button);
        tvContactName = findViewById(R.id.tv_contact_name);
        rvChat = findViewById(R.id.recycler_view_contact_chat);
        rvChat.setLayoutManager(new LinearLayoutManager(this));

        db = ChatDatabase.getDatabase(ChatActivity.this);
        Intent intent = getIntent();
        final boolean simpleChat = intent.getBooleanExtra(ChatActivity.EXTRA_SIMPLE_CHAT, true);

        if(simpleChat){
            final String received_hided_id = intent.getStringExtra(ChatActivity.EXTRA_HIDED_ID);
            contactId = Integer.parseInt(received_hided_id);
            tvContactName.setText(db.chatDao().getFirstNameById(contactId));
            fillChatAdapter();//Getting data from database and setting to AdapterChats then recyclerView
        }
        else {
            final String received_hided_group_id = intent.getStringExtra(ChatActivity.EXTRA_HIDED_GROUP_ID);
            groupId = Integer.parseInt(received_hided_group_id);
            tvContactName.setText(db.chatDao().getGroupNameById(groupId));
            fillGroupAdapter();
        }


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAcceptableField() && !toSendText.getText().toString().equals("Json")){
                    if(simpleChat){
                        MessagesTable msg = new MessagesTable(
                                db.chatDao().getMaxMessagesId() + 1,
                                toSendText.getText().toString(),
                                ActualUser.id,
                                contactId,
                                getCurrentDate());
                        db.chatDao().InsertMessage(msg);
                        refreshChatAdapter();
                        rvChat.scrollToPosition(rv_messages_data.size() - 1);
                    }
                    else {
                        GroupMessagesTable gp_msg = new GroupMessagesTable(
                                db.chatDao().getMaxIdMessageGroup() + 1,
                                toSendText.getText().toString(),
                                groupId,
                                ActualUser.id,
                                getCurrentDate());
                        db.chatDao().InsertMessage(gp_msg);
                        refreshGroupAdapter();
                        rvChat.scrollToPosition(rv_group_messages_data.size() - 1);
                    }
                }
                else if(toSendText.getText().toString().equals("Json")){
                    receiveMessageJsonRequest();
                }
                else { Toast.makeText(ChatActivity.this,"Escribe un mensaje",Toast.LENGTH_LONG).show();}

                toSendText.setText("");

            }



        });


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (!backPressed) {
            Toast.makeText(this, "Presionar BACK de nuevo para cerrar", Toast.LENGTH_SHORT).show();
            backPressed = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressed = false;
                }
            }, 2000);
        }
        else{
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            super.onBackPressed();
        }
    }
}
