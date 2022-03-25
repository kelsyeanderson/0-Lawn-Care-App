package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentSignupBinding;
import com.example.lawnwizard.databinding.FragmentWorkerHomeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class WorkerHomeFragment extends Fragment {
    FragmentWorkerHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWorkerHomeBinding.inflate(inflater, container, false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        NavController controller = NavHostFragment.findNavController(this);

        binding.imageView2.setOnClickListener((v) -> {
            controller.navigate(R.id.action_workerHomeFragment_to_workerProfileFragment);
        });
        binding.completedJobsCard.setOnClickListener((v) -> {
            controller.navigate(R.id.action_workerHomeFragment_to_workerJobHistoryFragment);
        });
        binding.activeJobsCard.setOnClickListener((v) -> {
            controller.navigate(R.id.action_workerHomeFragment_to_workerActiveJobsFragment);
        });
        binding.availableJobsCard.setOnClickListener((v) -> {
            controller.navigate(R.id.action_workerHomeFragment_to_workerAvailableJobsFragment);
        });

        return binding.getRoot();
    }
}