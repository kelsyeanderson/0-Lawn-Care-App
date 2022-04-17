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
import com.example.lawnwizard.databinding.FragmentDisputeBinding;
import com.example.lawnwizard.models.Job;
import com.example.lawnwizard.viewmodels.JobViewModel;
import com.example.lawnwizard.viewmodels.UserViewModel;

public class DisputeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentDisputeBinding binding = FragmentDisputeBinding.inflate(inflater, container, false);
        JobViewModel jobViewModel = new ViewModelProvider(getActivity()).get(JobViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        NavController controller = NavHostFragment.findNavController(this);


        userViewModel.loadUser();
        userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
            if (user == null) return;
        });

        binding.disputeButton.setOnClickListener((view -> {
            Job job = jobViewModel.getSelectedJob().getValue();
            job.setFlagged(true);
            job.setJobDispute(binding.editTextTextPersonName.getText().toString());
            jobViewModel.updateJob(job);
            controller.navigate(R.id.action_disputFragment_to_customerViewJobFragment);
        }));




        return binding.getRoot();
    }
}