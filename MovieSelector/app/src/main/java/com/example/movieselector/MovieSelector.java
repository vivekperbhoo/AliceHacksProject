package com.example.movieselector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

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
    private List<Movie.ResultsDTO> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_selector);
        seshText= findViewById(R.id.search_text);
        movies= new ArrayList<>();

        recyclerView= findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MovieCardAdapter cardAdapter= new MovieCardAdapter(movies,this);
        recyclerView.setAdapter(cardAdapter);

        searchView= findViewById(R.id.search);
        searchView.clearFocus();

        Retrofit retrofit= new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        MovieSearchInterface movieSearch= retrofit.create(MovieSearchInterface.class);
        
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
        String tag= (String) view.getTag();
        for(Movie.ResultsDTO kok: movies) {
            if (kok.getId().equals(tag)) {
                System.out.println("oko");
            }

        }
    }
}