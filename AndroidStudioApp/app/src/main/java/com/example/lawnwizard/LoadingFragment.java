package com.example.lawnwizard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lawnwizard.databinding.FragmentLoadingBinding;
import com.example.lawnwizard.databinding.FragmentSignInBinding;
import com.example.lawnwizard.viewmodels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class LoadingFragment extends Fragment {
    FragmentLoadingBinding binding;
    UserViewModel viewModel;
    NavController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoadingBinding.inflate(inflater, container, false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        controller = NavHostFragment.findNavController(this);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        if(auth.getCurrentUser() != null) {
            navigateToHome();
        }else{
            controller.navigate(R.id.action_loadingFragment_to_signInFragment);
        }

        return binding.getRoot();
    }

    private void navigateToHome() {
        viewModel.loadUser();
        viewModel.getUser().observe(getViewLifecycleOwner(), (user -> {
            if (user == null) {
                return;
            }
            String userRole = user.getRole();
            if (userRole.equals("Worker")) {
                controller.navigate(R.id.action_loadingFragment_to_workerHomeFragment);
            } else if (userRole.equals("Homeowner")) {
                controller.navigate(R.id.action_loadingFragment_to_customerHomeFragment);
            } else if (userRole.equals("Admin")) {
                controller.navigate(R.id.action_loadingFragment_to_adminHomeFragment);
            } else {
                Log.d("------------------------------Sign in error", "Couldnt find user role");
            }
        }));
    }
}