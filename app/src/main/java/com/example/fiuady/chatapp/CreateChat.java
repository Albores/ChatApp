package com.example.fiuady.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CreateChat extends AppCompatActivity {
    private TextView contact;
    private TextView participants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chat);
        contact = findViewById(R.id.actualcontact_tv);
        participants = findViewById(R.id.participantschat_text);
        Intent intent = getIntent();
        String contactusername = intent.getStringExtra(ChatActivity.EXTRA_HIDED_ID);
        contact.setText(contactusername);
        String parti = contactusername;
        participants.setText(parti);

    }
}
