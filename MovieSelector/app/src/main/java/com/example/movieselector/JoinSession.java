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
    private DatabaseReference sessionRef = FirebaseDatabase.getInstance().getReference("Users").child("Session");

    private DatabaseReference currentUsersRef = FirebaseDatabase.getInstance().getReference("Users").child("Current users");
    private Intent intent;
    private User user;
    private EditText seshET;
    private TextView teleTxt;
    private Session potentialSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_session);

        connectBtn = findViewById(R.id.connect_bttn);
        seshET = findViewById(R.id.seshSearch);
        teleTxt = findViewById(R.id.teleText);

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



        /*On click for connect button. If the session entered exists, it marks current user as trying to connect*/
        connectBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sessionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            if (child.exists()) {
                                Session session = child.getValue(Session.class);
                                if (session != null) {
                                    if (session.getSessionID().equals(seshET.getText().toString().trim())) {
                                        if (!session.getHasStarted()) {
                                            session.addUser(user);
                                            sessionRef.child(session.getSessionID()).setValue(session);
                                            potentialSession = session;
                                        } else {
                                            Toast.makeText(JoinSession.this, "Session has already Started", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        }
                        if(potentialSession==null){
                            Toast.makeText(JoinSession.this, "Session does not Exist", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        sessionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(potentialSession != null){
                    sessionRef.child(potentialSession.getSessionID()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            potentialSession = snapshot.getValue(Session.class);
                            if(potentialSession.getHasStarted()){
                                Intent intent = new Intent(getApplicationContext(), MovieSelector.class);
                                intent.putExtra("seshID", potentialSession.getSessionID());
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    //do nothing because either user did not press connect or had an invalid session id
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}