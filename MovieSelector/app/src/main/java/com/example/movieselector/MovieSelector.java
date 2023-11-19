package com.example.movieselector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieSelector extends AppCompatActivity {
    private RecyclerView recyclerView;
    private static String url="https://api.themoviedb.org";
    private SearchView searchView;
    private ImageButton select_bttn;
    private TextView seshText;
    private String sessionID;
    private Session session;
    private List<Movie.ResultsDTO> movies;
    DatabaseReference seshRef = FirebaseDatabase.getInstance().getReference("Users").child("Session");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_selector);
        seshText= findViewById(R.id.search_text);
        movies= new ArrayList<>();
        sessionID = getIntent().getStringExtra("seshID");

        recyclerView= findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MovieCardAdapter cardAdapter= new MovieCardAdapter(movies,this);
        recyclerView.setAdapter(cardAdapter);

        searchView= findViewById(R.id.search);
        searchView.clearFocus();

        Retrofit retrofit= new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        MovieSearchInterface movieSearch= retrofit.create(MovieSearchInterface.class);


        //look for sesh obj
        seshRef.child(sessionID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    session = snapshot.getValue(Session.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movies.clear();
                Call<Movie> call= movieSearch.getMovies(query);
                call.enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        Movie movie=response.body();
                        List<Movie.ResultsDTO> movieList=movie.getResults();
                        movies.addAll(movieList);
                        cardAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {

                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                seshText.setVisibility(View.GONE);
                return true;
            }
        });
    }

    public void selectMovie(View view) {
        String tag = view.getTag().toString();
        for(Movie.ResultsDTO movie: movies) {
            if (movie.getId().toString().equals(tag)) {
                session.addMovie(movie);
                ArrayList<Movie.ResultsDTO> list = session.getSelectedMovies();
                seshRef.child(sessionID).child("selectedMovies").setValue(list);

                Intent intent = new Intent(getApplicationContext(), WaitingForOthers.class);
                intent.putExtra("seshID", sessionID);
                startActivity(intent);
                finish();
            }
        }
    }
}