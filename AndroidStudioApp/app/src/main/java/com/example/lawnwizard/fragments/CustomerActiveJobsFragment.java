package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lawnwizard.R;
import com.example.lawnwizard.adapters.JobHistoryAdapter;
import com.example.lawnwizard.databinding.FragmentCustomerActiveJobsBinding;
import com.example.lawnwizard.databinding.FragmentCustomerJobHistoryBinding;
import com.example.lawnwizard.models.Job;
import com.example.lawnwizard.viewmodels.JobViewModel;
import com.example.lawnwizard.viewmodels.UserViewModel;

public class CustomerActiveJobsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCustomerActiveJobsBinding binding = FragmentCustomerActiveJobsBinding.inflate(inflater, container, false);
        JobViewModel jobViewModel = new ViewModelProvider(getActivity()).get(JobViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        NavController controller = NavHostFragment.findNavController(this);

        userViewModel.loadUser();
        userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
            if (user == null) return;

            // load transactions
            jobViewModel.loadActiveJobs(user);
            binding.jobListCustomerActiveJobs.setAdapter(
                    new JobHistoryAdapter(
                            jobViewModel.getActiveJobs(),
                            transaction -> {
                                // go to a job when clicked
                                jobViewModel.setSelectedJob(transaction);
                                NavHostFragment.findNavController(this)
                                        .navigate(R.id.action_customerActiveJobsFragment_to_customerViewJobFragment);
                            })
            );
            binding.jobListCustomerActiveJobs.setLayoutManager(new LinearLayoutManager(getContext()));
        });

        binding.backArrowCustomerActiveJobs.setOnClickListener((v)->{
            controller.navigate(R.id.action_customerActiveJobsFragment_to_customerHomeFragment);
        });

        return binding.getRoot();
    }
}