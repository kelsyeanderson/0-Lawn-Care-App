package com.example.lawnwizard.fragments;

import android.location.Address;
import android.location.Geocoder;
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
import com.example.lawnwizard.viewmodels.UserViewModel;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class CustomerViewJobFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        JobViewModel jobViewModel = new ViewModelProvider(getActivity()).get(JobViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        FragmentCustomerViewJobBinding binding = FragmentCustomerViewJobBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);

        if(Objects.requireNonNull(jobViewModel.getSelectedJob().getValue()).getWorker() != null){
            binding.workerEditTextCustomerViewJob.setText(jobViewModel.getSelectedJob().getValue().getWorker());
        }

        GeoPoint p = jobViewModel.getSelectedJob().getValue().getLocation();
        Geocoder geoCoder = new Geocoder(
                getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocation(
                    p.getLatitude(),
                    p.getLongitude(), 1);


            if (addresses.size() > 0)
            {
                Address address = addresses.get(0);
                String result = "";
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }
                sb.append(address.getLocality()).append("\n");
                sb.append(address.getPostalCode()).append("\n");
                sb.append(address.getCountryName());
                result = sb.toString();
                binding.addressEditTextCustomerViewJob.setText(addresses.get(0).getAddressLine(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


//        binding.phoneEditTextCustomerViewJob.setText(userViewModel.getUser().getValue().getEmail());
//
//        binding.emailEditTextCustomerViewJob.setText(jobViewModel.getSelectedJob().getValue().getHomeowner().getEmail());

        binding.backButtonCustomerViewJob.setOnClickListener((v)->{
            controller.navigate(R.id.action_customerViewJobFragment_to_customerActiveJobsFragment);
        });

        if(!jobViewModel.getSelectedJob().getValue().isCompleted() || jobViewModel.getSelectedJob().getValue().isFlagged()){
            binding.disputeButtonCustomerViewJob.setVisibility(View.GONE);
            binding.ratingButtonCustomerViewJob.setVisibility(View.GONE);
        }

        binding.disputeButtonCustomerViewJob.setOnClickListener((v)-> {
            controller.navigate(R.id.action_customerViewJobFragment_to_disputFragment);
        });

        binding.ratingButtonCustomerViewJob.setOnClickListener((v)-> {
            controller.navigate(R.id.action_customerViewJobFragment_to_customerFeedbackFragment);
        });

        return binding.getRoot();
    }
}