package com.example.movieselector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StartActivity extends AppCompatActivity {
    private ImageButton create, join;
    private EditText name;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        join= findViewById(R.id.join_sesh);
        create= findViewById(R.id.create_sesh);
        name= findViewById(R.id.name);
        reference= FirebaseDatabase.getInstance().getReference("Users");

         create.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(validateName()) {
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
        reference.child(user.getName()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        reference.child(user.getName()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(StartActivity.this,"Session Created",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(),CreateSession.class);
                startActivity(intent);
                finish();

            }
        });

    }
    private Boolean validateName(){
        String namee= name.getText().toString();
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