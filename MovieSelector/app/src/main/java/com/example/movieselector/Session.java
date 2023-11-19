package com.example.movieselector;

public class Session {
    private String sessionID;
    private boolean allUsersFinished;
    public Session(String sessionID,boolean allUsersFinished){
        this.sessionID= sessionID;
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
}
