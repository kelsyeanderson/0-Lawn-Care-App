package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentWorkerAvailableJobsBinding;
import com.example.lawnwizard.databinding.FragmentWorkerJobHistoryBinding;

public class WorkerJobHistoryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentWorkerJobHistoryBinding binding = FragmentWorkerJobHistoryBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);

        binding.workerAvailableJobsBackArrow.setOnClickListener((v)->{
            controller.navigate(R.id.action_workerJobHistoryFragment_to_workerHomeFragment);
        });

        return binding.getRoot();
    }
}