package com.example.lawnwizard.models;
import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Job {
    private String imageURI;
    private String homeowner;
    private String worker;
    private String homeownerID;
    private String workerID;
    private ArrayList<String> hwBlacklist = new ArrayList<String>();
    private ArrayList<String> workerBlacklist = new ArrayList<String>();
    private String description;
    private int pay;
    private GeoPoint location;
    private boolean accepted = false;
    private boolean completed = false;
    private boolean deleted = false;
    private boolean flagged = false;
    private boolean resolved = false;
    private String createdDate;
    private String docID;
    private String jobDispute;

    public Job(){}

    public Job(User homeowner, String description, int pay, GeoPoint location, String imageURI){
        Date currentTime = Calendar.getInstance().getTime();
        this.homeownerID = homeowner.getUserID();
        this.hwBlacklist = homeowner.getBlacklist();
        this.homeowner = homeowner.getName();
        this.description = description;
        this.pay = pay;
        this.location = location;
        this.imageURI = imageURI;
        this.createdDate = currentTime.toString();

//        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
//        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

    }

    public Job(User homeowner, String description, int pay, GeoPoint location){
        Date currentTime = Calendar.getInstance().getTime();
        this.homeownerID = homeowner.getUserID();
        this.hwBlacklist = homeowner.getBlacklist();
        this.homeowner = homeowner.getName();
        this.description = description;
        this.pay = pay;
        this.location = location;
        this.createdDate = currentTime.toString();
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getJobDispute() {
        return jobDispute;
    }

    public void setJobDispute(String jobDispute) {
        this.jobDispute = jobDispute;
    }

    public boolean getResolved() { return this.resolved; }

    public String getHomeowner() { return this.homeowner; }

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

    public String getHomeownerID() { return this.homeownerID; }

    public String getWorkerID() { return this.workerID; }

    public ArrayList<String> getHwBlacklist() { return this.hwBlacklist; }

    public ArrayList<String> getWorkerBlacklist() { return this.workerBlacklist; }

    public String getDocID() { return this.docID; }

    public void setDocID(String docID) { this.docID = docID; }

    public void setWorkerID(String id) { this.workerID = id; }

    public void setWorkerBlacklist(ArrayList<String> list) { this.workerBlacklist = list; }

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

    public void setFlagged(boolean flagged){ this.flagged = flagged;}

    public boolean isFlagged() {
        return flagged;
    }

    public void setCompleted() {
        this.completed = true;
    }

    public void setAccepted() {
        this.accepted = true;
    }

}
