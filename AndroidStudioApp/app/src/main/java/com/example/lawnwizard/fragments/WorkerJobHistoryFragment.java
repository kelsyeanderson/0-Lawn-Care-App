package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lawnwizard.R;
import com.example.lawnwizard.adapters.JobHistoryAdapter;
import com.example.lawnwizard.databinding.FragmentWorkerAvailableJobsBinding;
import com.example.lawnwizard.databinding.FragmentWorkerJobHistoryBinding;
import com.example.lawnwizard.viewmodels.JobViewModel;
import com.example.lawnwizard.viewmodels.UserViewModel;

public class WorkerJobHistoryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentWorkerJobHistoryBinding binding = FragmentWorkerJobHistoryBinding.inflate(inflater, container, false);
        JobViewModel jobViewModel = new ViewModelProvider(getActivity()).get(JobViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        NavController controller = NavHostFragment.findNavController(this);

        userViewModel.loadUser();
        userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
            if (user == null) return;

            // load jobs
            jobViewModel.loadPastJobs(user);
            binding.jobs.setAdapter(
                    new JobHistoryAdapter(
                            jobViewModel.getPastJobs(),
                            job -> {
                                // go to a job when clicked
                                jobViewModel.setSelectedJob(job);
                                NavHostFragment.findNavController(this)
                                        .navigate(R.id.action_workerJobHistoryFragment_to_workerViewJobFragment);
                            })
            );
            binding.jobs.setLayoutManager(new LinearLayoutManager(getContext()));
        });

        binding.workerAvailableJobsBackArrow.setOnClickListener((v)->{
            controller.navigate(R.id.action_workerJobHistoryFragment_to_workerHomeFragment);
        });

        return binding.getRoot();
    }
}