package com.example.movieselector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieArrayAdapter extends ArrayAdapter<SwipeMovieCardInfo> {

    Context context;
    LayoutInflater inflater;
    ArrayList<SwipeMovieCardInfo> movieList;

    public MovieArrayAdapter(Context ctx, ArrayList<SwipeMovieCardInfo> movieList){
        super(ctx,0,movieList);
        this.context=ctx;
        this.movieList= movieList;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=LayoutInflater.from(context).inflate(R.layout.movie_swipe_card, parent, false);

        view.setTag(movieList.get(position).title);

        ImageView posterImage = (ImageView) view.findViewById(R.id.posterImage);
        Picasso.get().load(movieList.get(position).posterPath).into(posterImage);
        //posterPath.setImageResource(movieList.get(position).posterPath);

        TextView year = (TextView) view.findViewById(R.id.year);
        year.setText(Integer.toString(movieList.get(position).year));

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(movieList.get(position).title);

        TextView overview = (TextView) view.findViewById(R.id.overview);
        overview.setText(movieList.get(position).overview);

        return view;
    }

}
