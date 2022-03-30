package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentCustomerCreateJobBinding;
import com.example.lawnwizard.databinding.FragmentSignInBinding;
import com.example.lawnwizard.viewmodels.JobViewModel;
import com.example.lawnwizard.viewmodels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.type.LatLng;

public class CustomerCreateJobFragment extends Fragment {

    FragmentCustomerCreateJobBinding binding;
    UserViewModel userViewModel;
    JobViewModel jobViewModel;
    NavController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCustomerCreateJobBinding.inflate(inflater, container, false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        controller = NavHostFragment.findNavController(this);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        binding.createJobButton.setOnClickListener((v) -> {
            binding.createJobButton.setEnabled(false);
            if(!foundInputErrors(binding.addressInput , binding.paymentInput, binding.jobDescriptionInput)){
                //addJob();
            }else{
                binding.createJobButton.setEnabled(true);
            }
        });

        return binding.getRoot();
    }

//    private void addJob(){
//        userViewModel.loadUser();
//        jobViewModel.loadJobs();
//        userViewModel.getUser().observe(getViewLifecycleOwner(), (user -> {
//            if (user == null) {
//                return;
//            }
//            jobViewModel.saveJob(
//                    user,
//                    binding.jobDescriptionInput.toString(),
//                    Integer.parseInt(binding.paymentInput.toString()),
//                    //add location here
//            );
//        }));
//    }

    private boolean foundInputErrors(EditText location, EditText payment, EditText description){
        boolean foundError = false;
        if(location.getText().toString().equals("")){
            location.setError("Please enter an address");
            foundError = true;
        }
        if(payment.getText().toString().equals("")){
            payment.setError("Please enter an payment amount");
            foundError = true;
        }
        try {
            Integer.parseInt(payment.getText().toString());
        }catch(NumberFormatException e){
            payment.setError("Please enter a number");
            foundError = true;
        }
        if(description.getText().toString().equals("")){
            description.setError("Please enter a description of the job");
            foundError = true;
        }

        return foundError;
    }


}