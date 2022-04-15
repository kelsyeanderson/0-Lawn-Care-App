package com.example.lawnwizard.viewmodels;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.provider.Telephony;
import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lawnwizard.models.Job;
import com.example.lawnwizard.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.type.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class JobViewModel extends ViewModel{
    MutableLiveData<Job> selectedJob = new MutableLiveData<>();
    ObservableArrayList<Job> jobs = new ObservableArrayList<>();
    ObservableArrayList<Job> activeJobs = new ObservableArrayList<>();
    ObservableArrayList<Job> pastJobs = new ObservableArrayList<>();
    ObservableArrayList<Job> availableJobs = new ObservableArrayList<>();
    ObservableArrayList<Job> flaggedJobs = new ObservableArrayList<>();
    FirebaseFirestore db;


    public JobViewModel() {
        db = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<Job> getSelectedJob(){ return this.selectedJob; }

    public ObservableArrayList<Job> getJobs() {
        return jobs;
    }

    public ObservableArrayList<Job> getActiveJobs() {
        return activeJobs;
    }

    public ObservableArrayList<Job> getPastJobs() {
        return pastJobs;
    }

    public ObservableArrayList<Job> getAvailableJobs() {
        return availableJobs;
    }

    public ObservableArrayList<Job> getFlaggedJobs() {
        return flaggedJobs;
    }

    public void setSelectedJob(Job selectedJob) {this.selectedJob.setValue(selectedJob);}

    public void saveJob(Job newJob) {
        db.collection("jobs").add(newJob).addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                Log.d("__DATABASE", "New user sent to database");
                jobs.add(newJob);
            } else {
                Log.d("__DATABASE", task.getException().toString());
            }
        });
    }

    public void loadJobs() {
        jobs.clear();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        db.collection("jobs")
                .whereEqualTo("deleted", false)
                .get()
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot collection = task.getResult();
                        int i =0;
                        for(QueryDocumentSnapshot document: collection){
                            DocumentReference doc = document.getReference();
                            Job job = collection.toObjects(Job.class).get(i);
                            job.setDocID(doc.getId());
                            updateJob(job);
                            i++;
                        }
                        jobs.addAll(collection.toObjects(Job.class));
                    }
                });
        Log.d("____Load Jobs:", String.valueOf(jobs));
    }

    public void loadFlaggedJobs() {
        flaggedJobs.clear();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        db.collection("jobs")
                .whereEqualTo("deleted", false)
                .whereEqualTo("completed", true)
                .whereEqualTo("flagged", true)
                .get()
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot collection = task.getResult();
                        int i =0;
                        for(QueryDocumentSnapshot document: collection){
                            DocumentReference doc = document.getReference();
                            Job job = collection.toObjects(Job.class).get(i);
                            job.setDocID(doc.getId());
                            updateJob(job);
                            i++;
                        }
                        flaggedJobs.addAll(collection.toObjects(Job.class));
                    }
                });
        Log.d("____Load Flagged Jobs:", String.valueOf(flaggedJobs));
    }

    public void loadPastJobs(User user) {
        pastJobs.clear();
        if(user.getRole().equals("Homeowner")) {
            db.collection("jobs")
                    .whereEqualTo("deleted", false)
                    .whereEqualTo("completed", true)
                    .whereEqualTo("homeownerID", user.getUserID())
                    .get()
                    .addOnCompleteListener((task) -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot collection = task.getResult();
                            int i =0;
                            for(QueryDocumentSnapshot document: collection){
                                DocumentReference doc = document.getReference();
                                Job job = collection.toObjects(Job.class).get(i);
                                job.setDocID(doc.getId());
                                updateJob(job);
                                i++;
                            }
                            pastJobs.addAll(collection.toObjects(Job.class));
                        }
                        Log.d("____Load Past Homeowner Jobs:", String.valueOf(pastJobs));
                    });
        }else{
            db.collection("jobs")
                    .whereEqualTo("deleted", false)
                    .whereEqualTo("completed", true)
                    .whereEqualTo("workerID", user.getUserID())
                    .get()
                    .addOnCompleteListener((task) -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot collection = task.getResult();
                            int i =0;
                            for(QueryDocumentSnapshot document: collection){
                                DocumentReference doc = document.getReference();
                                Job job = collection.toObjects(Job.class).get(i);
                                job.setDocID(doc.getId());
                                updateJob(job);
                                i++;
                            }
                            pastJobs.addAll(collection.toObjects(Job.class));
                        }
                        Log.d("____Load Past Worker Jobs:", String.valueOf(pastJobs));
                    });
        }
    }

    public void loadActiveJobs(User user) {
        activeJobs.clear();
        if(user.getRole().equals("Homeowner")) {
            db.collection("jobs")
                    .whereEqualTo("deleted", false)
                    .whereEqualTo("completed", false)
                    .whereEqualTo("homeownerID", user.getUserID())
                    .get()
                    .addOnCompleteListener((task) -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot collection = task.getResult();
                            int i =0;
                            for(QueryDocumentSnapshot document: collection){
                                DocumentReference doc = document.getReference();
                                Job job = collection.toObjects(Job.class).get(i);
                                job.setDocID(doc.getId());
                                updateJob(job);
                                i++;
                            }
                            activeJobs.addAll(collection.toObjects(Job.class));
                        }

                        Log.d("____Load Active Homeowner Jobs:", String.valueOf(activeJobs));
                    });
        }else{
            db.collection("jobs")
                    .whereEqualTo("deleted", false)
                    .whereEqualTo("completed", false)
                    .whereEqualTo("accepted", false)
                    .whereEqualTo("workerID", user.getUserID())
                    .get()
                    .addOnCompleteListener((task) -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot collection = task.getResult();
                            int i =0;
                            for(QueryDocumentSnapshot document: collection){
                                DocumentReference doc = document.getReference();
                                Job job = collection.toObjects(Job.class).get(i);
                                job.setDocID(doc.getId());
                                updateJob(job);
                                i++;
                            }
                            activeJobs.addAll(collection.toObjects(Job.class));
                        }

                        Log.d("____Load Active Worker Jobs:", String.valueOf(activeJobs));
                    });
        }
    }

    public void loadAvailableJobs(User user, Context context, GeoPoint geoPoint) {
        Log.d("___JobViewModel: ", "loadAvailableJobs started");
        if (geoPoint == null) { return; }
        String userZip = getZipCode(context, geoPoint.getLatitude(), geoPoint.getLongitude());
        ArrayList<Job> allAvailable = new ArrayList<>();
        availableJobs.clear();
        db.collection("jobs")
                .whereEqualTo("deleted", false)
                .whereEqualTo("completed", false)
                .whereEqualTo("accepted", false)
                .get()
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot collection = task.getResult();
                        int i =0;
                        for(QueryDocumentSnapshot document: collection){
                            DocumentReference doc = document.getReference();
                            Job job = collection.toObjects(Job.class).get(i);
                            job.setDocID(doc.getId());
                            updateJob(job);
                            i++;
                        }
                        allAvailable.addAll(collection.toObjects(Job.class));
                    }
                    Log.d("___Load Available Worker Jobs: ", String.valueOf(availableJobs));
                    for (Job job : allAvailable) {
                        GeoPoint jobLoc = job.getLocation();
                        String jobZip = getZipCode(context, jobLoc.getLatitude(), jobLoc.getLongitude());
                        if (jobZip.equals(userZip)) {
                            availableJobs.add(job);
                        }
                    }
                });
    }

    public void updateJob(Job updateJob) {
        db.collection("jobs").document(updateJob.getDocID()).set(updateJob, SetOptions.merge())
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        jobs.add(updateJob);
                    }
                });
    }

    public String getZipCode (Context c, double lat, double lng) {
        String fullAdd = null;
        String locality = null;
        String zip = null;
        String country = null;
        try {
            Geocoder geocoder = new Geocoder(c, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat,lng,1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                fullAdd = address.getAddressLine(0);
                locality = address.getLocality();
                zip = address.getPostalCode();
                country = address.getCountryName();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zip;
    }


}
