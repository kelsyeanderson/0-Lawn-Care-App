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
import com.example.lawnwizard.databinding.FragmentSubmitJobBinding;
import com.example.lawnwizard.databinding.FragmentWorkerViewJobBinding;
import com.example.lawnwizard.models.Job;
import com.example.lawnwizard.viewmodels.JobViewModel;
import com.example.lawnwizard.viewmodels.UserViewModel;

public class SubmitJobFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSubmitJobBinding binding = FragmentSubmitJobBinding.inflate(inflater, container, false);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        JobViewModel jobViewModel = new ViewModelProvider(getActivity()).get(JobViewModel.class);
        NavController controller = NavHostFragment.findNavController(this);

        binding.submitJobBackArrow.setOnClickListener((v)->{
            controller.navigate(R.id.action_submitJobFragment_to_workerActiveJobsFragment);
        });

        binding.submitJobButton.setOnClickListener((v) -> {
            userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
                if (user == null) return;
                Job job = jobViewModel.getSelectedJob().getValue();
                job.setCompleted();

                user.setBalance(job.getPay());
                userViewModel.updateUser(user);

                jobViewModel.updateJob(job);
                controller.navigate(R.id.action_submitJobFragment_to_workerHomeFragment);
            });
        });

        return binding.getRoot();

    }
}