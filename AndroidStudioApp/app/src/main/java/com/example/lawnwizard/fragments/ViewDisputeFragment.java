package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentCustomerViewJobBinding;
import com.example.lawnwizard.databinding.FragmentDisputBinding;
import com.example.lawnwizard.databinding.FragmentViewDisputeBinding;
import com.example.lawnwizard.models.Job;
import com.example.lawnwizard.viewmodels.JobViewModel;

public class ViewDisputeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentViewDisputeBinding binding = FragmentViewDisputeBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);
        JobViewModel jobViewModel = new ViewModelProvider(getActivity()).get(JobViewModel.class);

        binding.disputeClaim.setText(jobViewModel.getSelectedJob().getValue().getJobDispute());

        binding.acceptDispute.setOnClickListener((view -> {



            controller.navigate(R.id.action_viewDisputeFragment_to_adminHomeFragment);
        }));

        binding.declineDispute.setOnClickListener((view -> {
            Job job = jobViewModel.getSelectedJob().getValue();
            job.setFlagged(false);
            jobViewModel.updateJob(job);
            controller.navigate(R.id.action_viewDisputeFragment_to_adminHomeFragment);
        }));

        return binding.getRoot();
    }
}