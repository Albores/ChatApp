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

import org.json.JSONException;
import org.json.JSONObject;

public class CreateChat extends AppCompatActivity {
    private TextView contact;
    private TextView participants;
    private Button createchat;
    private EditText chatname;
    private CheckedTextView simplechat;
    private CheckedTextView grupalchat;
    private ChatDatabase db;
    private String type="";
    private String parti="";

    public JSONObject makingJson() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chat);
        contact = findViewById(R.id.actualcontact_tv);
        participants = findViewById(R.id.participantschat_text);
        createchat = findViewById(R.id.chatcreate_btn);
        chatname = findViewById(R.id.chatname_edtx);
        simplechat = findViewById(R.id.simplechat_cktx);
        grupalchat = findViewById(R.id.grupal_cktx);
        db = ChatDatabase.getDatabase(CreateChat.this);

        Intent intent = getIntent();
        String contactusername = intent.getStringExtra(ChatActivity.EXTRA_HIDED_ID);
        contact.setText(contactusername);
        parti = contactusername+","+db.chatDao().getUserNameById(0);
        participants.setText(parti);

        simplechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type ="0";
                Toast.makeText(CreateChat.this, type, Toast.LENGTH_SHORT).show();
            }
        });
        grupalchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="1";
                Toast.makeText(CreateChat.this, type, Toast.LENGTH_SHORT).show();
            }
        });
        createchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snack = Snackbar.make(v, "Se ha creado el chat", Snackbar.LENGTH_SHORT);
                snack.show();
            }
        });

    }
}
