package com.example.movieselector;


public class User {
    private String name;
    private boolean hasFinished;
    public User(String name, boolean hasFinished){
        this.name= name;
        this.hasFinished=hasFinished;
    }
    public User(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasFinished() {
        return hasFinished;
    }

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }
}
