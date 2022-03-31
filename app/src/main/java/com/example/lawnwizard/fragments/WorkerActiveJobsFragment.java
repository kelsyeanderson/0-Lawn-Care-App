package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentCustomerActiveJobsBinding;
import com.example.lawnwizard.databinding.FragmentWorkerActiveJobsBinding;

public class WorkerActiveJobsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentWorkerActiveJobsBinding binding = FragmentWorkerActiveJobsBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);

        binding.workerAvailableJobsBackArrow.setOnClickListener((v)->{
            controller.navigate(R.id.action_workerActiveJobsFragment_to_workerHomeFragment);
        });

        return binding.getRoot();
    }
}