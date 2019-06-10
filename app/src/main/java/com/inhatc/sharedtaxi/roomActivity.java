package com.inhatc.sharedtaxi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class roomActivity extends AppCompatActivity {
    ListView lstMessage;
    EditText txtMessage;
    Button btnSend;
    String userName;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private ArrayAdapter<String> mAdapter;
    private String roomId;
    private String where;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_room );
        Intent intent = getIntent();
        roomId = intent.getStringExtra("roomId");
        where = intent.getStringExtra("where");
        lstMessage = (ListView) findViewById(R.id.lstMessage);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
        btnSend = (Button) findViewById(R.id.btnSend);

        userName = intent.getStringExtra("userName");
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        lstMessage.setAdapter(mAdapter);
        Toast.makeText( this,roomId+"/"+where+"/"+userName, Toast.LENGTH_LONG ).show();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatData chatData = new ChatData(userName, txtMessage.getText().toString());
                databaseReference.child(where).child("roomList").child(roomId).child("message").push().setValue(chatData);
                txtMessage.setText("");
            }
        });

        databaseReference.child(where).child("roomList").child(roomId).child("message").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                mAdapter.add(chatData.getUserName() + ": " + chatData.getMessage());
                lstMessage.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
