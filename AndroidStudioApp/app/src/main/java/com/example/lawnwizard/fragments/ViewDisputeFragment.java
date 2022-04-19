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
import com.example.lawnwizard.databinding.FragmentViewDisputeBinding;
import com.example.lawnwizard.models.Job;
import com.example.lawnwizard.viewmodels.JobViewModel;
import com.example.lawnwizard.viewmodels.UserViewModel;

public class ViewDisputeFragment extends Fragment {
    UserViewModel userViewModel;
    NavController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentViewDisputeBinding binding = FragmentViewDisputeBinding.inflate(inflater, container, false);
        controller = NavHostFragment.findNavController(this);
        JobViewModel jobViewModel = new ViewModelProvider(getActivity()).get(JobViewModel.class);
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        binding.backArrowViewDispute.setOnClickListener((v ->{
            controller.navigate(R.id.action_viewDisputeFragment_to_adminHomeFragment);
        }));

        binding.disputeClaim.setText(jobViewModel.getSelectedJob().getValue().getJobDispute());

        binding.acceptDispute.setOnClickListener((view -> {
            Job job = jobViewModel.getSelectedJob().getValue();
            job.setResolved();
            jobViewModel.updateJob(job);
            reverseTransactions(jobViewModel.getSelectedJob().getValue().getHomeownerID(),
                    jobViewModel.getSelectedJob().getValue().getWorkerID(),
                    jobViewModel.getSelectedJob().getValue().getPay()
            );
            controller.navigate(R.id.action_viewDisputeFragment_to_adminHomeFragment);
        }));

        binding.declineDispute.setOnClickListener((view -> {
            Job job = jobViewModel.getSelectedJob().getValue();
            job.setResolved();
            jobViewModel.updateJob(job);
            controller.navigate(R.id.action_viewDisputeFragment_to_adminHomeFragment);
        }));

        return binding.getRoot();
    }

    private void reverseTransactions(String homeownerID, String workerID, int jobPrice){
        //Load homeowner and add transaction
        userViewModel.loadDifferentUser(homeownerID);
        userViewModel.getDifferentUser().observe(getViewLifecycleOwner(), (homeowner -> {
            if (homeowner == null) {
                return;
            }
            homeowner.addToBalance(jobPrice);
            userViewModel.updateUser(homeowner);

            //load worker and remove money
            userViewModel.loadDifferentUser(workerID);
            userViewModel.getDifferentUser().observe(getViewLifecycleOwner(), (worker -> {
                if (worker == null) {
                    return;
                }
                worker.addToBalance(-jobPrice);
                userViewModel.updateUser(worker);
            }));
        }));
    }
}