package com.example.lawnwizard.models;

import com.google.firebase.firestore.GeoPoint;
import com.google.type.LatLng;

import java.util.Calendar;
import java.util.Date;

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
    private String createdDate;

    public Job(){}

    public Job(User homeowner, String description, int pay, GeoPoint location, String imageURI){
        Date currentTime = Calendar.getInstance().getTime();
        this.homeowner = homeowner;
        this.description = description;
        this.pay = pay;
        this.location = location;
        this.imageURI = imageURI;
        this.createdDate = currentTime.toString();
    }

    public Job(User homeowner, String description, int pay, GeoPoint location){
        Date currentTime = Calendar.getInstance().getTime();
        this.homeowner = homeowner;
        this.description = description;
        this.pay = pay;
        this.location = location;
        this.createdDate = currentTime.toString();
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

    public String getCreatedDate() { return this.createdDate; }

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
