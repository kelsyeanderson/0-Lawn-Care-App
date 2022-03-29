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

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentCustomerCreateJobBinding;
import com.example.lawnwizard.databinding.FragmentSignInBinding;
import com.example.lawnwizard.viewmodels.JobViewModel;
import com.example.lawnwizard.viewmodels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

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




        return binding.getRoot();
    }

//    private void navigateToHome() {
//        viewModel.loadUser();
//        viewModel.getUser().observe(getViewLifecycleOwner(), (user -> {
//            if (user == null) {
//                return;
//            } else {
//                String userRole = user.getRole();
//                if (userRole.equals("Worker")) {
//                    controller.navigate(R.id.action_signInFragment_to_workerHomeFragment);
//                } else if (userRole.equals("Homeowner")) {
//                    controller.navigate(R.id.action_signInFragment_to_customerHomeFragment);
//                } else {
//                    Log.d("------------------------------Sign in error", "Couldnt find user role");
//                }
//            }
//        }));
//    }
}