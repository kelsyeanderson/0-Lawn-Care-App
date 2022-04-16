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
import com.example.lawnwizard.databinding.FragmentCustomerHomeBinding;
import com.example.lawnwizard.viewmodels.JobViewModel;
import com.example.lawnwizard.viewmodels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerHomeFragment extends Fragment {
    FragmentCustomerHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCustomerHomeBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);
        JobViewModel jobViewModel = new ViewModelProvider(getActivity()).get(JobViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        userViewModel.loadUser();
        userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
            if (user == null) return;

            // load transactions
            jobViewModel.loadJobs();
            binding.currentJobListCustomerHome.setText(Integer.toString(jobViewModel.getJobs().size()));
            binding.jobHistoryListCustomerHome.setText(Integer.toString(jobViewModel.getJobs().size()));
        });

        binding.profileButtonCustomerHome.setOnClickListener((v) -> {
//            binding.imageView2.setEnabled(false);
            controller.navigate(R.id.action_customerHomeFragment_to_costomerProfileFragment);
        });
        binding.jobHistoryButtonCustomerHome.setOnClickListener((v) -> {
//            binding.completedJobsCard.setEnabled(false);
            controller.navigate(R.id.action_customerHomeFragment_to_customerJobHistoryFragment);
        });
        binding.currentJobButtonCustomerHome.setOnClickListener((v) -> {
//            binding.activeJobsCard.setEnabled(false);
            controller.navigate(R.id.action_customerHomeFragment_to_customerActiveJobsFragment);
        });
        binding.createJobButtonCustomerHome.setOnClickListener((v) -> {
//            binding.addJobButton.setEnabled(false);
            controller.navigate(R.id.action_customerHomeFragment_to_customerCreateJobFragment);
        });


        return binding.getRoot();
    }
}