package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentSignupBinding;
import com.example.lawnwizard.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.example.lawnwizard.viewmodels.UserViewModel;


import java.util.ArrayList;

public class SignupFragment extends Fragment {
    FragmentSignupBinding binding;
    UserViewModel viewmodel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        NavController controller = NavHostFragment.findNavController(this);
        viewmodel = new ViewModelProvider(this).get(UserViewModel.class);

        ArrayList<String> list = new ArrayList<>();
        list.add("Worker");
        list.add("Homeowner");
        Spinner dropdown = binding.accountTypeSignUp;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        dropdown.setAdapter(adapter);

        binding.signUpButtonSignUp.setOnClickListener((v) -> {
            if (!foundInputErrors(binding.nameEditTextSignUp, binding.emailEditTextSignUp, binding.phoneEditTextSignUp, binding.passwordEditTextSignUp, binding.confirmPasswordEditTextSignUp)) {
                auth.createUserWithEmailAndPassword(
                        binding.emailEditTextSignUp.getText().toString(),
                        binding.passwordEditTextSignUp.getText().toString()
                ).addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        Log.d("__FIREBASE", "Sign up success");
                        viewmodel.saveUser(binding.nameEditTextSignUp.getText().toString(), dropdown.getSelectedItem().toString());
                        if (dropdown.getSelectedItem().toString().equals("Worker")) {
                            controller.navigate(R.id.action_signupFragment_to_workerHomeFragment);
                        } else {
                            controller.navigate(R.id.action_signupFragment_to_customerHomeFragment);
                        }

                    } else {
                        Log.d("__FIREBASE", task.getException().toString());
                    }
                });
            }
        });

        return binding.getRoot();
    }

    private boolean foundInputErrors(EditText nameField, EditText emailField, EditText confirmEmailField,
                                     EditText passwordField, EditText confirmPasswordField){
        boolean foundError = false;
        if(nameField.getText().toString().equals("")){
            nameField.setError("Please enter your full name");
            foundError = true;
        }
        if(!passwordField.getText().toString().matches(confirmPasswordField.getText().toString())){
            passwordField.setError("Passwords do not match");
            confirmPasswordField.setError("Passwords do not match");
            foundError = true;
        }
        if(!emailField.getText().toString().matches(confirmEmailField.getText().toString())){
            emailField.setError("Emails do not match");
            confirmEmailField.setError("Emails do not match");
            foundError = true;
        }
        if(passwordField.getText().toString().length() < 8){
            passwordField.setError("Password must be at least 8 characters");
            foundError = true;
        }
        if(confirmPasswordField.getText().toString().length() < 8){
            confirmPasswordField.setError("Password must be at least 8 characters");
            foundError = true;
        }
        if(!emailField.getText().toString().contains("@")){
            emailField.setError("Please enter a valid email");
            foundError = true;
        }
        if(emailField.getText().toString().matches("")){
            emailField.setError("Please enter an email");
            foundError = true;
        }
        if(confirmEmailField.getText().toString().matches("")){
            confirmEmailField.setError("Please enter an email");
            foundError = true;
        }
        if(passwordField.getText().toString().matches("")){
            passwordField.setError("Please enter a password");
            foundError = true;
        }
        if(confirmPasswordField.getText().toString().matches("")){
            confirmPasswordField.setError("Please enter a password");
            foundError = true;
        }

        return foundError;
    }
}