package com.example.movieselector;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.MovieHolder> {
    private ArrayList<Movie> movieList;
    private Context context;
    public MovieCardAdapter(ArrayList<Movie> movieList,Context context){
        this.movieList=movieList;
        this.context= context;
    }
    @NonNull
    @Override
    public MovieCardAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.movie_card,parent,false);
        MovieHolder movieHolder= new MovieHolder(view);
        return movieHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCardAdapter.MovieHolder holder, int position) {
        Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
    public static class MovieHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        TextView movie_name;
        TextView movie_rating;
        TextView release_date;
        public MovieHolder(View view){
            super(view);
        }
    }
}
