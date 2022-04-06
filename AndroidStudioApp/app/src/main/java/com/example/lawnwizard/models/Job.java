package com.example.lawnwizard.models;

import com.google.firebase.firestore.GeoPoint;
import com.google.type.LatLng;

public class Job {
    private String imageURI;
    private User homeowner; //
    private User worker; //
    private String description; //
    private int pay; //
    private GeoPoint location; //
    private boolean accepted = false; //
    private boolean completed = false; //
    private boolean deleted = false;

    public Job(){}

    public Job(User homeowner, String description, int pay, GeoPoint location){
        this.homeowner = homeowner;
        this.description = description;
        this.pay = pay;
        this.location = location;
    }

    public User getHomeowner(){return homeowner;}

    public User getWorker(){ return worker; }

    public void setWorker(User worker){
        this.worker = worker;
    }

    public String getDescription() {
        return description;
    }

    public int getPay() {
        return pay;
    }

    public boolean getDeleted() { return this.deleted; }

    public String getImageURI() { return this.imageURI; }

    public GeoPoint getLocation() {
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
