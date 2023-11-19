package com.example.movieselector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {
    private ImageButton create, join;
    private EditText name;
    private DatabaseReference reference;
    private ArrayList<User> seshUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        join= findViewById(R.id.join_sesh);
        create= findViewById(R.id.create_sesh);
        name= findViewById(R.id.name);
        seshUsers= new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("Users");
         create.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (validateName()){
                     createSession();
                 }
             }
         });
         join.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (validateName()){
                     joinSession();
                 }
             }
         });
    }

    private void joinSession() {
        String username= name.getText().toString();
        User  user= new User(username,false);
        reference.child("Current users").child(user.getName()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent= new Intent(getApplicationContext(),JoinSession.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void createSession() {
        String username= name.getText().toString();
        User user= new User(username,false);
        seshUsers.add(user);
        Session sesh= new Session("session"+user.getName(),false,seshUsers);
        reference.child("Current users").child(user.getName()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                reference.child("Session").child(sesh.getSessionID()).setValue(sesh);
                Toast.makeText(StartActivity.this,"Session Created",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(),CreateSession.class);
                intent.putExtra("Seshname","session"+user.getName());
                startActivity(intent);
                finish();
            }
        });

    }
    private boolean validateName(){
        String namee= name.getText().toString().trim();

        if(namee.isEmpty()){
            name.setError("Enter username to proceed");
            name.requestFocus();
            return false;
        }
        else {
           name.setError(null);
           return true;
        }
    }
}