package com.example.movieselector;

public class Session {
    private int sessionID;
    private boolean allUsersFinished;
    public Session(int sessionID,boolean allUsersFinished){
        this.sessionID= sessionID;
        this.allUsersFinished= allUsersFinished;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public boolean isAllUsersFinished() {
        return allUsersFinished;
    }

    public void setAllUsersFinished(boolean allUsersFinished) {
        this.allUsersFinished = allUsersFinished;
    }
}
