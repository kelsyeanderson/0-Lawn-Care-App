package com.example.lawnwizard.fragments;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lawnwizard.R;
import com.example.lawnwizard.adapters.JobHistoryAdapter;
import com.example.lawnwizard.databinding.FragmentWorkerAvailableJobsBinding;
import com.example.lawnwizard.location.GoogleLocationService;
import com.example.lawnwizard.location.LocationUpdateListener;
import com.example.lawnwizard.models.Job;
import com.example.lawnwizard.viewmodels.JobViewModel;
import com.example.lawnwizard.viewmodels.LocationViewModel;
import com.example.lawnwizard.viewmodels.UserViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.GeoPoint;

import java.util.concurrent.atomic.AtomicReference;

public class WorkerAvailableJobsFragment extends Fragment {
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    private GoogleLocationService googleLocationService;
    private GeoPoint userLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentWorkerAvailableJobsBinding binding = FragmentWorkerAvailableJobsBinding.inflate(inflater, container, false);
        LocationViewModel locationViewModel = new ViewModelProvider(getActivity()).get(LocationViewModel.class);
        JobViewModel jobViewModel = new ViewModelProvider(getActivity()).get(JobViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        NavController controller = NavHostFragment.findNavController(this);

        locationViewModel.loadUserLocation(getContext());

        userViewModel.loadUser();
        userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
            if (user == null) return;

            // load transactions
            jobViewModel.loadAvailableJobs(user);
            binding.jobListWorkerAvailableJobs.setAdapter(
                    new JobHistoryAdapter(
                            jobViewModel.getAvailableJobs(),
                            transaction -> {
                                // go to a job when clicked
                                jobViewModel.setSelectedJob(transaction);
                                NavHostFragment.findNavController(this)
                                        .navigate(R.id.action_workerAvailableJobsFragment_to_workerViewJobFragment);
                            })
            );
            binding.jobListWorkerAvailableJobs.setLayoutManager(new LinearLayoutManager(getContext()));
        });

//        userViewModel.loadUser();
//        userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
//            if (user == null) return;
//
//
//
//            locationViewModel.getUserLocation().observe(getViewLifecycleOwner(), (location) -> {
//                Log.d("-----", "-------");
//                userLocation = location;
//                jobViewModel.loadAvailableJobs(user, getContext(), userLocation);
//                binding.jobListWorkerAvailableJobs.setAdapter(
//                        new JobHistoryAdapter(
//                                jobViewModel.getAvailableJobs(),
//                                transaction -> {
//                                    // go to a job when clicked
//                                    jobViewModel.setSelectedJob(transaction);
//                                    NavHostFragment.findNavController(this)
//                                            .navigate(R.id.action_workerAvailableJobsFragment_to_workerViewJobFragment);
//                                })
//                );
//            });
//            binding.jobListWorkerAvailableJobs.setLayoutManager(new LinearLayoutManager(getContext()));
//        });

        binding.backArrowWorkerAvailableJobs.setOnClickListener((v) -> {
            controller.navigate(R.id.action_workerAvailableJobsFragment_to_workerHomeFragment);
        });

        return binding.getRoot();
    }

//    private GeoPoint getLastLocation() {
//        final GeoPoint[] point = new GeoPoint[1];
//        if (checkPermissions()) {
//            if (isLocationEnabled()) {
//                // check if permissions not granted
//                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return point[0];
//                }
//                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        Location location = task.getResult();
//                        if (location == null) {
//                            requestNewLocationData();
//                        } else {
//                            point[0] = new GeoPoint(location.getLatitude(), location.getLongitude());
//                            Log.d("_____________", String.valueOf(point[0]));
//                        }
//                    }
//                });
//            } else {
//                Toast.makeText(getContext(), "Please turn on your location...", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
//            }
//        } else {
//            requestPermissions();
//        }
//        return point[0];
//    }
//
//    private void requestNewLocationData() {
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(5);
//        mLocationRequest.setFastestInterval(0);
//        mLocationRequest.setNumUpdates(1);
//
//        // setting LocationRequest
//        // on FusedLocationClient
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
//    }
//
//    private LocationCallback mLocationCallback = new LocationCallback() {
//
//        @Override
//        public void onLocationResult(@NonNull LocationResult locationResult) {
//            Location mLastLocation = locationResult.getLastLocation();
//        }
//    };
//
//    private boolean checkPermissions() {
//        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
//                PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED;
//    }
//
//    private void requestPermissions() {
//        ActivityCompat.requestPermissions(getActivity(), new String[]{
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
//    }
//
//    private boolean isLocationEnabled() {
//        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//    }
//    @Override
//    public void
//    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == PERMISSION_ID) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getLastLocation();
//            }
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (checkPermissions()) {
//            getLastLocation();
//        }
//    }
}