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
        NavController controller = NavHostFragment.findNavController(this);


        userViewModel.loadUser();
        userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
            if (user == null) return;

            // load transactions
            jobViewModel.loadPastJobs(user);
            binding.jobListCustomerJobHistory.setAdapter(
                    new JobHistoryAdapter(
                            jobViewModel.getPastJobs(),
                            transaction -> {
                                // go to a job when clicked
                                jobViewModel.setSelectedJob(transaction);
                                NavHostFragment.findNavController(this)
                                        .navigate(R.id.action_customerJobHistoryFragment_to_customerViewJobFragment);
                            })
            );
            binding.jobListCustomerJobHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        });

        binding.clearButtonCustomerJobHistory.setVisibility(View.GONE);

        binding.backArrowCustomerJobHistory.setOnClickListener((v)->{
            controller.navigate(R.id.action_customerJobHistoryFragment_to_customerHomeFragment);
        });

        return binding.getRoot();
    }
}