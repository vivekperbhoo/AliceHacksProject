package com.example.movieselector;

import android.util.Log;

import java.util.ArrayList;

public class Session {
    private String sessionID;
    private boolean hasStarted;
    private ArrayList<User> sessionUsers;
    private boolean allUsersFinished;

    private  int total;
    private ArrayList<Integer> likesList = new ArrayList<>();

    public void setSelectedMovies(ArrayList<Movie.ResultsDTO> selectedMovies) {
        this.selectedMovies = selectedMovies;
    }

    private ArrayList<Movie.ResultsDTO> selectedMovies = new ArrayList<>();;

    public boolean getHasStarted() {
        return hasStarted;
    }

    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
    }

    public ArrayList<User> getSessionUsers() {
        return sessionUsers;
    }

    public void setSessionUsers(ArrayList<User> sessionUsers) {
        this.sessionUsers = sessionUsers;
    }
    public ArrayList<Movie.ResultsDTO> getSelectedMovies(){
        return selectedMovies;
    }
    public Session(){};

    public Session(String sessionID,boolean allUsersFinished,boolean hasStarted, ArrayList<User> sessionUsers){
        this.sessionID= sessionID;
        this.sessionUsers=sessionUsers;
        this.hasStarted=hasStarted;
        this.allUsersFinished= allUsersFinished;
        this.total=0;
    }
    public void selectMovie(Movie.ResultsDTO movie){
        selectedMovies.add(movie);
    }

    public String  getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public boolean getAllUsersFinished() {
        return allUsersFinished;
    }

    public void setAllUsersFinished(boolean allUsersFinished) {
        this.allUsersFinished = allUsersFinished;
    }

    public void addUser(User user){
        sessionUsers.add(user);
    }

    public void addMovie(Movie.ResultsDTO movie) {
        selectedMovies.add(movie);
        int index = selectedMovies.indexOf(movie);
        likesList.add(index, 0);
    }

    public ArrayList<Integer> getLikesList() {
        return likesList;
    }

    public void setLikesList(ArrayList<Integer> likesList) {
        this.likesList = likesList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
