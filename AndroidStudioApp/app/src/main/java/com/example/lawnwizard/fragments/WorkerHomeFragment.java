package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentSignupBinding;
import com.example.lawnwizard.databinding.FragmentWorkerHomeBinding;
import com.example.lawnwizard.viewmodels.JobViewModel;
import com.example.lawnwizard.viewmodels.UserViewModel;
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
        JobViewModel jobViewModel = new ViewModelProvider(getActivity()).get(JobViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        userViewModel.loadUser();
        userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
            if (user == null) return;

            // load transactions
            jobViewModel.loadJobs();
            binding.workCurrentJobsCount.setText(Integer.toString(jobViewModel.getJobs().size()));
            binding.workPastJobsCount.setText(Integer.toString(jobViewModel.getJobs().size()));
        });

        binding.imageView3.setOnClickListener((v) -> {
            controller.navigate(R.id.action_workerHomeFragment_to_workerProfileFragment);
        });
        binding.historyJobsCard.setOnClickListener((v) -> {
            controller.navigate(R.id.action_workerHomeFragment_to_workerJobHistoryFragment);
        });
        binding.currentJobsCard.setOnClickListener((v) -> {
            controller.navigate(R.id.action_workerHomeFragment_to_workerActiveJobsFragment);
        });
        binding.addJobButton2.setOnClickListener((v) -> {
            controller.navigate(R.id.action_workerHomeFragment_to_workerAvailableJobsFragment);
        });

        return binding.getRoot();
    }
}