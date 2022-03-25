package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentCustomerHomeBinding;
import com.example.lawnwizard.databinding.FragmentWorkerProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

public class WorkerProfileFragment extends Fragment {
    FragmentWorkerProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWorkerProfileBinding.inflate(inflater, container, false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        NavController controller = NavHostFragment.findNavController(this);

        binding.workerLogoutButton.setOnClickListener((v) -> {
            auth.signOut();
            controller.navigate(R.id.action_workerProfileFragment_to_signInFragment);
        });

        return binding.getRoot();
    }
}