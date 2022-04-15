package com.example.lawnwizard.viewmodels;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lawnwizard.location.GoogleLocationService;
import com.example.lawnwizard.location.LocationUpdateListener;
import com.example.lawnwizard.models.Job;
import com.google.firebase.firestore.GeoPoint;

public class LocationViewModel extends ViewModel {
    MutableLiveData<GeoPoint> userLocation = new MutableLiveData<>();
    GoogleLocationService googleLocationService;

    public MutableLiveData<GeoPoint> getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(GeoPoint userLocation) {
        this.userLocation.setValue(userLocation);
    }

    public void loadUserLocation(Context context) {

        googleLocationService = new GoogleLocationService(context, new LocationUpdateListener() {
            @Override
            public void canReceiveLocationUpdates() {
            }

            @Override
            public void cannotReceiveLocationUpdates() {
            }

            @Override
            public void updateLocation(Location location) {
                if (location != null) {
                    GeoPoint p = new GeoPoint(location.getLatitude(), location.getLongitude());
                    Log.d("_____________", p.toString());
                    setUserLocation(p);
                    googleLocationService.stopUpdates();
                }
            }

            @Override
            public void updateLocationName(String localityName, Location location) {
                googleLocationService.stopLocationUpdates();
            }
        });
        googleLocationService.startUpdates();
    }
}
