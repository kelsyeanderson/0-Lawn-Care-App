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
import com.example.lawnwizard.databinding.FragmentCustomerActiveJobsBinding;
import com.example.lawnwizard.databinding.FragmentCustomerJobHistoryBinding;
import com.example.lawnwizard.viewmodels.JobViewModel;
import com.example.lawnwizard.viewmodels.UserViewModel;

public class CustomerActiveJobsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCustomerActiveJobsBinding binding = FragmentCustomerActiveJobsBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);

        binding.workerAvailableJobsBackArrow.setOnClickListener((v)->{
            controller.navigate(R.id.action_customerActiveJobsFragment_to_customerHomeFragment);
        });

        return binding.getRoot();
    }
}