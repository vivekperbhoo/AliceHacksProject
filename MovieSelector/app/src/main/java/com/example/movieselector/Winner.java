package com.example.movieselector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class Winner extends AppCompatActivity {
    String seshID;
    ArrayList<Movie.ResultsDTO> selectedMovies;
    ArrayList<Integer> likeList;

    ImageView poster;
    TextView title;
    TextView year;

    ArrayList<Movie.ResultsDTO> winners;
    Movie.ResultsDTO winner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        poster=findViewById(R.id.posterImage);
        title=findViewById(R.id.title);
        year=findViewById(R.id.year);
        seshID= getIntent().getStringExtra("seshID");
        DatabaseReference seshRef = FirebaseDatabase.getInstance().getReference("Users").child("Session").child(seshID);
        seshRef.keepSynced(true);

        seshRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Session session = snapshot.getValue(Session.class);
                    if (session!=null){
                        selectedMovies=session.getSelectedMovies();
                        likeList=session.getLikesList();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        int max=0;
        for (int i=0;i<likeList.size();i++){
            if (likeList.get(i)>max){
                max=likeList.get(i);
            }
        }
        for (int i=0;i<likeList.size();i++){
            if (likeList.get(i)==max){
                winners.add(selectedMovies.get(i));
            }
        }

        Random rand=new Random();
        int randomWinner=rand.nextInt(winners.size());
        winner = winners.get(randomWinner);

        Picasso.get().load("https://image.tmdb.org/t/p/original"+winner.getPosterPath()).into(poster);
        title.setText("Title: "+winner.getOriginalTitle());

        String date = winner.getReleaseDate().substring(0, 4);
        year.setText(date);

    }
}