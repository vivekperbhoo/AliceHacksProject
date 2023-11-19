package com.example.movieselector;

import java.util.ArrayList;

public class Session {
    private String sessionID;

    public boolean isHasStarted() {
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
    public Session(){};

    public Session(String sessionID,boolean allUsersFinished,ArrayList<User> sessionUsers){
        this.sessionID= sessionID;
        this.sessionUsers=sessionUsers;
        this.hasStarted=false;
        this.allUsersFinished= allUsersFinished;
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
