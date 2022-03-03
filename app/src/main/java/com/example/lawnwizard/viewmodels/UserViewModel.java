package com.example.lawnwizard.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lawnwizard.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

public class UserViewModel extends ViewModel{
    MutableLiveData<User> user = new MutableLiveData<>();
    FirebaseFirestore db;
    String docID;

    public UserViewModel() {
        db = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void saveUser(String name, String role) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        User newUser = new User(
                auth.getUid(),
                name,
                role
        );
        db.collection("users").add(newUser).addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                user.setValue(newUser);
            }
        });
    }

    public void loadUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        db.collection("users")
                .whereEqualTo("userID", auth.getUid())
                .get()
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot collection = task.getResult();
                        int i = 0;
                        for(QueryDocumentSnapshot document: collection) {
                            if(i > 0){
                                Log.d("ERROR: ","________TOO MANY DOCUMENTS LOADED___________");
                                break;
                            }
                            DocumentReference doc = document.getReference();
                            docID = doc.getId();
                            i++;
                        }
                        user.setValue(collection.toObjects(User.class).get(0));
                    }
                });
    }

    public void updateUser(String docID, User updateUser) {
        db.collection("users").document(docID).set(updateUser, SetOptions.merge())
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        user.setValue(updateUser);
                    }
                });
    }

}
