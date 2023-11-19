package com.example.movieselector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SwipingMovies extends AppCompatActivity {
    ArrayList<SwipeMovieCardInfo> movieList;
    MovieArrayAdapter movieAdapter;

    private String sessionID;
    DatabaseReference database, likesDB;

    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_adapter);

        sessionID = getIntent().getStringExtra("seshID");

        database = FirebaseDatabase.getInstance().getReference("Users").child("Session").child(sessionID).child("selectedMovies");
        likesDB = FirebaseDatabase.getInstance().getReference("Users").child("Session").child(sessionID).child("likesList");

        movieList = new ArrayList<SwipeMovieCardInfo>();

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    SwipeMovieCardInfo movie;
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Movie.ResultsDTO mov = child.getValue(Movie.ResultsDTO.class);

                        String posterpath = "https://image.tmdb.org/t/p/original" + mov.getPosterPath();

                        String date = mov.getReleaseDate().substring(0, 4);
                        int year = Integer.parseInt(date);

                        String title = mov.getTitle();

                        String overview = "Overview: " + mov.getOverview();

                        movie = new SwipeMovieCardInfo(posterpath, year, title, overview);
                        movieList.add(movie);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        SwipeFlingAdapterView swipeFlingAdapterView = (SwipeFlingAdapterView) findViewById(R.id.card);

        movieAdapter = new MovieArrayAdapter(this, movieList);

        // Ask girish about nofity data set change

        swipeFlingAdapterView.setAdapter(movieAdapter);
        swipeFlingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                movieList.remove(0);
                movieAdapter.notifyDataSetChanged();
                count++;
                Log.d("swiping", "Remove" + count);
                // do if statement : if movieList
            }

            @Override
            public void onLeftCardExit(Object o) {

            }

            @Override
            public void onRightCardExit(Object o) {
                FirebaseDatabase.getInstance().getReference("Users").child("Session").child(sessionID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Session session = snapshot.getValue(Session.class);
                            if(session!=null){
                                ArrayList<Integer> likesList = session.getLikesList();
                                int likes = likesList.get(count-1);
                                likesList.set(count-1, ++likes);
                                FirebaseDatabase.getInstance().getReference("Users").child("Session").child(sessionID).setValue(session);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


            @Override
            public void onAdapterAboutToEmpty(int i) {

            }

            @Override
            public void onScroll(float v) {

            }
        });

    }
}