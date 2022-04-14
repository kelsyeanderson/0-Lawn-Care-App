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
import com.example.lawnwizard.databinding.FragmentWorkerJobHistoryBinding;
import com.example.lawnwizard.databinding.FragmentWorkerViewJobBinding;
import com.example.lawnwizard.models.Job;
import com.example.lawnwizard.viewmodels.JobViewModel;
import com.example.lawnwizard.viewmodels.UserViewModel;

public class WorkerViewJobFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentWorkerViewJobBinding binding = FragmentWorkerViewJobBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        JobViewModel jobViewModel = new ViewModelProvider(getActivity()).get(JobViewModel.class);

        binding.jobBackButton.setOnClickListener((v)->{
            controller.navigate(R.id.action_workerViewJobFragment_to_workerHomeFragment2);
        });

        binding.acceptJobButton.setOnClickListener((v) -> {
            userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
                if (user == null) return;

                Job job = jobViewModel.getSelectedJob().getValue();
                job.setAccepted();
                jobViewModel.updateJob(job);
                controller.navigate(R.id.action_workerViewJobFragment_to_workerHomeFragment2);
            });
        });

        return binding.getRoot();
    }
}