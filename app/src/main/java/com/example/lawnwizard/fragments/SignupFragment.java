package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentSignInBinding;
import com.example.lawnwizard.databinding.FragmentSignupBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class SignupFragment extends Fragment {
    FragmentSignupBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        NavController controller = NavHostFragment.findNavController(this);

        ArrayList<String> list = new ArrayList<>();
        list.add("Worker");
        list.add("Homeowner");
        Spinner dropdown = binding.accountType;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        dropdown.setAdapter(adapter);

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