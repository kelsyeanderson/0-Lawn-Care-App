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
import com.example.lawnwizard.databinding.FragmentWorkerViewJobBinding;
import com.example.lawnwizard.viewmodels.JobViewModel;


public class CustomerViewJobFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        JobViewModel jobViewModel = new ViewModelProvider(getActivity()).get(JobViewModel.class);
        FragmentCustomerViewJobBinding binding = FragmentCustomerViewJobBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);

        binding.jobBackButton.setOnClickListener((v)->{
            controller.navigate(R.id.action_customerViewJobFragment_to_customerActiveJobsFragment);
        });

        if(!jobViewModel.getSelectedJob().getValue().isCompleted() || jobViewModel.getSelectedJob().getValue().isFlagged()){
            binding.disputJobButton.setVisibility(View.GONE);
        }

        binding.disputJobButton.setOnClickListener((v)-> {
            controller.navigate(R.id.action_customerViewJobFragment_to_disputFragment);
        });

        binding.textView33.setOnClickListener((v)-> {
            controller.navigate(R.id.action_customerViewJobFragment_to_customerFeedbackFragment);
        });

        return binding.getRoot();
    }
}