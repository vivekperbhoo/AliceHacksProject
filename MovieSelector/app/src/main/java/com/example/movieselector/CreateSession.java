package com.example.movieselector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateSession extends AppCompatActivity {
    private ArrayList<User> users;
    private TextView sesId;
    private DatabaseReference reference;
    private ImageButton start;
    private ListView listView;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);
        this.users= new ArrayList<>();
        start= findViewById(R.id.start_bttn);
        sesId= findViewById(R.id.sess_text);
        listView= findViewById(R.id.list);
        intent=getIntent();
        String seshId= intent.getStringExtra("Seshname");
        sesId.setText(sesId.getText()+" "+seshId);
        ListAdapter listAdapter= new ListAdapter(CreateSession.this,users);
        reference= FirebaseDatabase.getInstance().getReference("Users").child("Session");
        reference.child(seshId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                if(snapshot.exists()){
                    Session sesh= snapshot.getValue(Session.class);
                    if(sesh!=null) {
                        Log.d("sessionusers", sesh.getSessionUsers().get(0).getName());
                        users.addAll(sesh.getSessionUsers());
                    }
                }listAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setAdapter(listAdapter);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSession();
            }
        });

    }

    private void startSession() {
        Intent intent= new Intent(getApplicationContext(),MovieSelector.class);
        startActivity(intent);
        finish();
    }
}