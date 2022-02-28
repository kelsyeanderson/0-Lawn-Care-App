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
import com.google.firebase.auth.FirebaseAuth;

public class SignInFragment extends Fragment {
    FragmentSignInBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        NavController controller = NavHostFragment.findNavController(this);

        if(auth.getCurrentUser() != null) {
            controller.navigate(R.id.action_signInFragment_to_workerHomeFragment);
        }

        binding.logInButton.setOnClickListener((v) -> {
            auth.signInWithEmailAndPassword(
                    binding.email.getText().toString(),
                    binding.password.getText().toString()
            ).addOnCompleteListener((task) -> {
               if (task.isSuccessful()) {
                   controller.navigate(R.id.action_signInFragment_to_workerHomeFragment);
               } else {
                   Log.d("__FIREBASE", task.getException().toString());
               }
            });
        });

        binding.signupBotton.setOnClickListener((v) -> {
            controller.navigate(R.id.action_signInFragment_to_signupFragment);
        });

        return binding.getRoot();
    }
}