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

import java.util.ArrayList;

public class activity_voting_waitroom extends AppCompatActivity {
    private DatabaseReference reference;
    private String seshID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_waitroom);
        seshID= getIntent().getStringExtra("seshID");
        reference= FirebaseDatabase.getInstance().getReference("Users").child("Session").child(seshID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Session session= snapshot.getValue(Session.class);
                ArrayList<Movie.ResultsDTO> list= session.getSelectedMovies();
                if(session.getTotal()==list.size()){

                    Intent intent= new Intent(activity_voting_waitroom.this, Winner.class);
                    startActivity(intent);
                    finish();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}