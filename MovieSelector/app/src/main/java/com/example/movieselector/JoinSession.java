package com.example.movieselector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JoinSession extends AppCompatActivity {
    private ImageButton connectBtn;
    private DatabaseReference sessionRef = FirebaseDatabase.getInstance().getReference("Session");

    private DatabaseReference currentUsersRef = FirebaseDatabase.getInstance().getReference("Current Users");
    private Intent intent;
    private User user;
    private boolean tryingToConnect;

    private EditText seshET;
    private TextView teleTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_session);

        connectBtn = findViewById(R.id.connect_bttn);
        seshET = findViewById(R.id.seshSearch);
        teleTxt = findViewById(R.id.teleText);
        tryingToConnect=false;

        intent = getIntent();

        String username = intent.getStringExtra("Username");

        //find current user in db
        currentUsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    user = child.getValue(User.class);
                    if(user!=null){
                        if(user.getName().equals(username)){
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //find out if session exists
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot child: snapshot.getChildren()){
                            if(child.exists()){
                                Session session = child.getValue(Session.class);
                                if(session!=null){
                                    if(session.getSessionID().equals(seshET.getText().toString().trim())){
                                        if(!session.isHasStarted()){
                                        session.addUser(user);
                                          tryingToConnect=true;
                                        }else{
                                            Toast.makeText(JoinSession.this,"Session already Started",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                                else{
                                    Toast.makeText(JoinSession.this,"Session does not Exist",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}