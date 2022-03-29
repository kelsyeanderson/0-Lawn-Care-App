package com.example.lawnwizard.viewmodels;

import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lawnwizard.models.Job;
import com.example.lawnwizard.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.type.LatLng;

public class JobViewModel extends ViewModel{
    ObservableArrayList<Job> jobs = new ObservableArrayList<>();
    FirebaseFirestore db;

    public JobViewModel() {
        db = FirebaseFirestore.getInstance();
    }

    public ObservableArrayList<Job> getJobs() {
        return jobs;
    }

    public void saveJob(User user, String description, int pay, LatLng location) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Job newJob = new Job(
                user,
                description,
                pay,
                location
        );
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
        FirebaseAuth auth = FirebaseAuth.getInstance();
        db.collection("jobs")
                .whereEqualTo("deleted", false)
                .get()
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot collection = task.getResult();
                        for(QueryDocumentSnapshot document: collection) {
                            DocumentReference doc = document.getReference();
                        }
                        jobs.addAll(collection.toObjects(Job.class));
                    }
                });
    }

    public void updateJob(String docID, Job updateJob) {
        db.collection("jobs").document(docID).set(updateJob, SetOptions.merge())
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        jobs.add(updateJob);
                    }
                });
    }

}
