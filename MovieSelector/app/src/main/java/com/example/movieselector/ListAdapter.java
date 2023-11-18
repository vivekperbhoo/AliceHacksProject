package com.example.movieselector;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<User> {

    private Activity context;
    private ArrayList<User> users;
    public ListAdapter(Activity context, ArrayList<User>users){
        super(context,R.layout.list_item,users);
        this.context= context;
        this.users=users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);
        View view= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        TextView currentUser= view.findViewById(R.id.user);
        currentUser.setText(user.getName());
        return view;
    }
}
