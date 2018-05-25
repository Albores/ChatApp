package com.example.fiuady.chatapp;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateChat extends AppCompatActivity {
    private TextView contact;
    private TextView participants;
    private Button createchat;
    private EditText chatname;
    private ChatDatabase db;
    private String type="0";
    private String parti="";
    private String URL_Chats = "https://serverxd.herokuapp.com/api/chats";
    public JSONObject makingSimpleChatJson() {
        JSONObject js = new JSONObject();
        try {
            js.put("name",chatname.getText());
            js.put("type", type);
            js.put("participants", parti);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js;
    }

    public void sendJsonChatRequest(String URL) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, makingSimpleChatJson(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(CreateChat.this, "Response" + response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CreateChat.this, "Response Error", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_create_chat);
        contact = findViewById(R.id.actualcontact_tv);
        participants = findViewById(R.id.participantschat_text);
        createchat = findViewById(R.id.chatcreate_btn);
        chatname = findViewById(R.id.chatname_edtx);
        db = ChatDatabase.getDatabase(CreateChat.this);

        Intent intent = getIntent();
        String contactusername = intent.getStringExtra(ChatActivity.EXTRA_HIDED_ID);
        contact.setText(contactusername);
        parti = contactusername+","+db.chatDao().getUserNameById(0);
        participants.setText(parti);

        createchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snack = Snackbar.make(v, "Se ha creado el chat", Snackbar.LENGTH_SHORT);
                snack.show();
                sendJsonChatRequest(URL_Chats);
            }
        });



    }
}
