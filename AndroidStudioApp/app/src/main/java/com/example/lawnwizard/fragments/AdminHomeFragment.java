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
import com.example.lawnwizard.databinding.FragmentAdminHomeBinding;
import com.example.lawnwizard.databinding.FragmentCustomerProfileBinding;
import com.example.lawnwizard.viewmodels.JobViewModel;
import com.example.lawnwizard.viewmodels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class AdminHomeFragment extends Fragment {
    FragmentAdminHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminHomeBinding.inflate(inflater, container, false);
        JobViewModel jobViewModel = new ViewModelProvider(getActivity()).get(JobViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        NavController controller = NavHostFragment.findNavController(this);

        userViewModel.loadUser();
        userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
            if (user == null) return;

            // load transactions
            jobViewModel.loadFlaggedJobs();
            // go to a job when clicked
            binding.jobsAdmin.setAdapter(
                    new JobHistoryAdapter(
                            jobViewModel.getFlaggedJobs(),
                            job -> {
                                jobViewModel.setSelectedJob(job);
                                NavHostFragment.findNavController(this)
                                        .navigate(R.id.action_adminHomeFragment_to_viewDisputeFragment);
                            })
            );
            binding.jobsAdmin.setLayoutManager(new LinearLayoutManager(getContext()));
        });

        binding.logoutImageView.setOnClickListener((v) -> {
            auth.signOut();
            controller.navigate(R.id.action_adminHomeFragment_to_signInFragment);
        });

        return binding.getRoot();
    }
}