package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentSignInBinding;
import com.example.lawnwizard.databinding.FragmentSignupBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SignupFragment extends Fragment {
    FragmentSignupBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        NavController controller = NavHostFragment.findNavController(this);

        binding.signUpButton.setOnClickListener((v) -> {
            auth.createUserWithEmailAndPassword(
                    binding.editEmail.getText().toString(),
                    binding.editPassword.getText().toString()
            ).addOnCompleteListener((task) -> {
                if (task.isSuccessful()) {
                    Log.d("__FIREBASE", "Sign up success");
                    controller.navigate(R.id.action_signupFragment_to_workerHomeFragment);
                } else {
                    Log.d("__FIREBASE", task.getException().toString());
                }
            });
        });

        return binding.getRoot();
    }
}