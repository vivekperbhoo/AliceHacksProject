package com.example.movieselector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateSession extends AppCompatActivity {
    private ArrayList<User> users;
    private DatabaseReference reference;
    private ImageButton start;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);
        this.users= new ArrayList<>();
        start= findViewById(R.id.button);
        listView= findViewById(R.id.list);
        reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.child("Current Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot child: snapshot.getChildren()) {
                        if(child.exists()){
                            User user = child.getValue(User.class);
                            if(user!=null){
                                users.add(user);
                            }
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ListAdapter listAdapter= new ListAdapter(CreateSession.this,users);
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