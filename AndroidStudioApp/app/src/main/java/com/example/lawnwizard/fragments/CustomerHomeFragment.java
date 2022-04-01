package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentCustomerHomeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerHomeFragment extends Fragment {
    FragmentCustomerHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCustomerHomeBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);

        binding.imageView2.setOnClickListener((v) -> {
//            binding.imageView2.setEnabled(false);
            controller.navigate(R.id.action_customerHomeFragment_to_costomerProfileFragment);
        });
        binding.completedJobsCard.setOnClickListener((v) -> {
//            binding.completedJobsCard.setEnabled(false);
            controller.navigate(R.id.action_customerHomeFragment_to_customerJobHistoryFragment);
        });
        binding.activeJobsCard.setOnClickListener((v) -> {
//            binding.activeJobsCard.setEnabled(false);
            controller.navigate(R.id.action_customerHomeFragment_to_customerActiveJobsFragment);
        });
        binding.addJobButton.setOnClickListener((v) -> {
//            binding.addJobButton.setEnabled(false);
            controller.navigate(R.id.action_customerHomeFragment_to_customerCreateJobFragment);
        });


        return binding.getRoot();
    }
}