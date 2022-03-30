package com.example.lawnwizard.fragments;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
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
import com.google.firebase.firestore.GeoPoint;
import com.google.type.LatLng;

import java.io.IOException;
import java.util.List;

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
        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);

        binding.createJobButton.setOnClickListener((v) -> {
            binding.createJobButton.setEnabled(false);
            if(!foundInputErrors(binding.addressInput , binding.paymentInput, binding.jobDescriptionInput)){
                addJob();

            }else{
                binding.createJobButton.setEnabled(true);
            }
        });

        return binding.getRoot();
    }

    private void addJob(){
        userViewModel.loadUser();
        jobViewModel.loadJobs();
        userViewModel.getUser().observe(getViewLifecycleOwner(), (user -> {
            if (user == null) {
                return;
            }
            GeoPoint loc = getLocationFromAddress(getContext(), binding.addressInput.getText().toString());
            if (loc == null) {
                binding.addressInput.setError("Please enter a valid address");
                return;
            }
            jobViewModel.saveJob(
                    user,
                    binding.jobDescriptionInput.toString(),
                    Integer.parseInt(binding.paymentInput.getText().toString()),
                    loc
            );
            controller.navigate(R.id.action_customerCreateJobFragment_to_customerHomeFragment);
        }));

    }

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

    public GeoPoint getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            System.out.println("lat: " + location.getLatitude() * 1E6 + " lon: " + location.getLongitude() * 1E6);
            System.out.println("lat: " + location.getLatitude() + " lon: " + location.getLongitude());
            p1 = new GeoPoint((double) (location.getLatitude()),
                    (double) (location.getLongitude()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.print("");
        return p1;
    }


}