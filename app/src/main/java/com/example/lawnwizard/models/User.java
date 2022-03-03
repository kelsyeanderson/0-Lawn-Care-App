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

    public User(String userID, String name, String role){
        //TODO: Add in prevLocation
        this.userID = userID;
        this.name = name;
        this.role = role;
    }

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

    public ArrayList<String> getWhitelist() {
        return whitelist;
    }

    public ArrayList<String> getBlacklist() {
        return blacklist;
    }
}

