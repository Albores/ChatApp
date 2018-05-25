package com.example.fiuady.chatapp;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CreateChat extends AppCompatActivity {
    private TextView contact;
    private TextView participants;
    private Button createchat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chat);
        contact = findViewById(R.id.actualcontact_tv);
        participants = findViewById(R.id.participantschat_text);
        createchat = findViewById(R.id.chatcreate_btn);
        Intent intent = getIntent();
        String contactusername = intent.getStringExtra(ChatActivity.EXTRA_HIDED_ID);
        contact.setText(contactusername);
        String parti = contactusername;
        participants.setText(parti);
        createchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snack = Snackbar.make(v,"Se ha creado el chat",Snackbar.LENGTH_SHORT);
                snack.show();
            }
        });

    }
}
