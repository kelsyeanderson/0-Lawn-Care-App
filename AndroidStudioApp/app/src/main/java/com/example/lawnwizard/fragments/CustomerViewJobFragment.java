package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentCustomerViewJobBinding;
import com.example.lawnwizard.databinding.FragmentWorkerViewJobBinding;


public class CustomerViewJobFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCustomerViewJobBinding binding = FragmentCustomerViewJobBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);

        binding.jobBackButton.setOnClickListener((v)->{
            controller.navigate(R.id.action_customerViewJobFragment_to_customerActiveJobsFragment);
        });

        binding.totalTimeText.setOnClickListener((v)-> {
            controller.navigate(R.id.action_customerViewJobFragment_to_customerFeedbackFragment);
        });

        return binding.getRoot();
    }
}