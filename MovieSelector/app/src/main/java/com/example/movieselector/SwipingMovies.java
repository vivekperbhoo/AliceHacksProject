package com.example.movieselector;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import java.util.ArrayList;

public class SwipingMovies extends AppCompatActivity{
    ArrayList<SwipeMovieCardInfo> movieList;
    MovieArrayAdapter movieAdapter;

    int count = 0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_adapter);

        movieList = new ArrayList<SwipeMovieCardInfo>();

        SwipeFlingAdapterView swipeFlingAdapterView = (SwipeFlingAdapterView) findViewById(R.id.card);

        movieAdapter = new MovieArrayAdapter(this, movieList);


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

