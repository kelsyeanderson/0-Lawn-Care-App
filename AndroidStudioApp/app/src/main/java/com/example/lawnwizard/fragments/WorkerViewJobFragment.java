package com.example.lawnwizard.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lawnwizard.R;
import com.example.lawnwizard.adapters.JobHistoryAdapter;
import com.example.lawnwizard.databinding.FragmentWorkerJobHistoryBinding;
import com.example.lawnwizard.databinding.FragmentWorkerViewJobBinding;
import com.example.lawnwizard.models.Job;
import com.example.lawnwizard.viewmodels.JobViewModel;
import com.example.lawnwizard.viewmodels.UserViewModel;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class WorkerViewJobFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentWorkerViewJobBinding binding = FragmentWorkerViewJobBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        JobViewModel jobViewModel = new ViewModelProvider(getActivity()).get(JobViewModel.class);

        binding.jobBackButton.setOnClickListener((v)->{
            controller.navigate(R.id.action_workerViewJobFragment_to_workerHomeFragment2);
        });

        binding.textView39.setText(jobViewModel.getSelectedJob().getValue().getDescription());


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
                binding.textView37.setText(addresses.get(0).getAddressLine(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




//        binding.textView41.setText(jobViewModel.getSelectedJob().getValue().getE());

        binding.acceptJobButton.setOnClickListener((v) -> {
            userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
                if (user == null) return;

                Job job = jobViewModel.getSelectedJob().getValue();
                job.setAccepted();
                job.setWorkerID(user.getUserID());
                jobViewModel.updateJob(job);
                controller.navigate(R.id.action_workerViewJobFragment_to_workerHomeFragment2);
            });
        });

        return binding.getRoot();
    }
}