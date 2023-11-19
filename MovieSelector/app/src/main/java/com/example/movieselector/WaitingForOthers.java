package com.example.movieselector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WaitingForOthers extends AppCompatActivity {
   private String username;
   private DatabaseReference ref;
   private ValueEventListener value;
   private String seshID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_for_others);
        username= getIntent().getStringExtra("Username");
        seshID= getIntent().getStringExtra("seshID");
        ref= FirebaseDatabase.getInstance().getReference("Users").child("Session").child(seshID);
        value=ref.child("sessionUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        if(child.exists()) {
                            User user = child.getValue(User.class);
                            if(!(user.getHasFinished())){
                                return;
                            }
                        }
                    }
                    Intent intent = new Intent(getApplicationContext(), SwipingMovies.class);
                    intent.putExtra("seshId",seshID);
                    startActivity(intent);
                    finish();
                    removeListener();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void removeListener() {
        ref.removeEventListener(value);
    }
}