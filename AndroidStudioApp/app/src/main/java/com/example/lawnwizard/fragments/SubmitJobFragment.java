package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentSubmitJobBinding;
import com.example.lawnwizard.databinding.FragmentWorkerViewJobBinding;

public class SubmitJobFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSubmitJobBinding binding = FragmentSubmitJobBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);

        binding.submitJobBackArrow.setOnClickListener((v)->{
            controller.navigate(R.id.action_submitJobFragment_to_workerActiveJobsFragment);
        });

        return binding.getRoot();
    }
}