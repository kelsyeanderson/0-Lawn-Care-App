package com.example.lawnwizard.models;

import com.google.type.LatLng;

import java.util.ArrayList;

public class User {
    private String userID;
    private String name;
    private String role;
    private LatLng prevLocation;
    private Integer balance = 0;
    private ArrayList<String> whitelist = new ArrayList<String>();
    private ArrayList<String> blacklist = new ArrayList<String>();
    private ArrayList<Float> ratings = new ArrayList<>();

    public User(String userID, String name, String role){
        //TODO: Add in prevLocation
        this.userID = userID;
        this.name = name;
        this.role = role;
    }

    public User(){}

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public LatLng getPrevLocation() {
        return prevLocation;
    }

    public Integer getBalance() {
        return balance;
    }

    public Float getRating() {
        Float total = null;
        if (ratings.size() < 1) {
            return null;
        }
        for (int i = 0; i < ratings.size(); i++) {
            total += ratings.get(i);
        }
        Float rating = total / ratings.size();
        return rating;
    }

    public ArrayList<String> getWhitelist() {
        return whitelist;
    }

    public ArrayList<String> getBlacklist() {
        return blacklist;
    }

    public void setBalance(int num) {
        this.balance += num;
        if(this.balance < 0){
            this.balance = 0;
        }
    }
    public ArrayList<Float> getRatings() {
        return ratings;
    }
}

