package com.example.lawnwizard.models;

import com.google.type.LatLng;

public class Job {
    //TODO add photo
    private User user;
    private String description;
    private int pay;
    private LatLng location;
    private boolean accepted = false;
    private boolean completed = false;
    private boolean deleted = false;
    private String docID;

    public Job(User user, String description, int pay, LatLng location){
        this.user = user;
        this.description = description;
        this.pay = pay;
        this.location = location;
    }

    public User getUser(){return user;}

    public String getDescription() {
        return description;
    }

    public void setDocID(String docID){
        this.docID = docID;
    }

    public int getPay() {
        return pay;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeleted(){
        this.deleted = true;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted() {
        this.completed = true;
    }

    public void setAccepted() {
        this.accepted = true;
    }
}
