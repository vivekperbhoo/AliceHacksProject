package com.example.movieselector;

import java.util.ArrayList;

public class Session {
    private String sessionID;
    private ArrayList<Movie.ResultsDTO> selectedMovies=new ArrayList<>();

    public boolean hasStarted() {
        return hasStarted;
    }

    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
    }

    private boolean hasStarted;
    private ArrayList<User> sessionUsers;
    private boolean allUsersFinished;

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

    public Session(String sessionID,boolean allUsersFinished,ArrayList<User> sessionUsers){
        this.sessionID= sessionID;
        this.sessionUsers=sessionUsers;
        this.hasStarted=false;
        this.allUsersFinished= allUsersFinished;
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

    public boolean isAllUsersFinished() {
        return allUsersFinished;
    }

    public void setAllUsersFinished(boolean allUsersFinished) {
        this.allUsersFinished = allUsersFinished;
    }

    public void addUser(User user){

        sessionUsers.add(user);
    }
}
