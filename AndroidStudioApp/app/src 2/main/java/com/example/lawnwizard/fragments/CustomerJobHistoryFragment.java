package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lawnwizard.R;
import com.example.lawnwizard.adapters.JobHistoryAdapter;
import com.example.lawnwizard.databinding.FragmentCustomerJobHistoryBinding;
import com.example.lawnwizard.viewmodels.JobViewModel;
import com.example.lawnwizard.viewmodels.UserViewModel;

public class CustomerJobHistoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCustomerJobHistoryBinding binding = FragmentCustomerJobHistoryBinding.inflate(inflater, container, false);
        JobViewModel jobViewModel = new ViewModelProvider(getActivity()).get(JobViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
            if (user == null) return;

            // load transactions
            binding.jobs.setAdapter(
                    new JobHistoryAdapter(
                            jobViewModel.getJobs(),
                            transaction -> {
                                // go to a job when clicked
//                                jobViewModel.setSelectedJob(transaction);
//                                NavHostFragment.findNavController(this)
//                                        .navigate();
                            })
            );
            binding.jobs.setLayoutManager(new LinearLayoutManager(getContext()));
        });

        return binding.getRoot();
    }
}