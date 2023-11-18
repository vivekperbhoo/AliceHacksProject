package com.example.movieselector;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.MovieHolder> {
    private List<Movie.ResultsDTO> movieList;
    private Context context;
    public MovieCardAdapter(List<Movie.ResultsDTO> movieList,Context context){
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
        Movie.ResultsDTO movie= movieList.get(position);
        Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.movie_name.setText("Title "+movie.getOriginalTitle());
        holder.movie_rating.setText("Rating "+movie.getPopularity());
        holder.release_date.setText("Release Date "+movie.getReleaseDate());
        Picasso.get().load("https://image.tmdb.org/t/p/original"+movie.getPosterPath()).into(holder.poster);
        //Add on Click here
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
        ImageButton select_bttn;
        public MovieHolder(View view){
            super(view);
            poster= view.findViewById(R.id.imageView3);
            movie_name= view.findViewById(R.id.titleText);
            movie_rating= view.findViewById(R.id.ratingText);
            release_date= view.findViewById(R.id.dateText);
        }
    }
}
