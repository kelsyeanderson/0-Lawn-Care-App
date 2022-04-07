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
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.type.LatLng;

public class JobViewModel extends ViewModel{
    MutableLiveData<Job> selectedJob = new MutableLiveData<>();
    ObservableArrayList<Job> jobs = new ObservableArrayList<>();
    ObservableArrayList<Job> activeJobs = new ObservableArrayList<>();
    ObservableArrayList<Job> pastJobs = new ObservableArrayList<>();
    FirebaseFirestore db;

    public JobViewModel() {
        db = FirebaseFirestore.getInstance();
    }

    public ObservableArrayList<Job> getJobs() {
        return jobs;
    }

    public ObservableArrayList<Job> getActiveJobs() {
        return activeJobs;
    }

    public ObservableArrayList<Job> getPastJobs() {
        return pastJobs;
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
                        jobs.addAll(collection.toObjects(Job.class));
                    }
                });
        Log.d("____Load Jobs:", String.valueOf(jobs));
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
                            activeJobs.addAll(collection.toObjects(Job.class));
                        }

                        Log.d("____Load Active Homeowner Jobs:", String.valueOf(activeJobs));
                    });
        }else{
            //TODO: add filter by area code
            db.collection("jobs")
                    .whereEqualTo("deleted", false)
                    .whereEqualTo("completed", false)
                    .whereEqualTo("accepted", false)
                    .get()
                    .addOnCompleteListener((task) -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot collection = task.getResult();
                            activeJobs.addAll(collection.toObjects(Job.class));
                        }

                        Log.d("____Load Active Worker Jobs:", String.valueOf(activeJobs));
                    });
        }
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
