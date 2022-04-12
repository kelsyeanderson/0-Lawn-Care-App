package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentCustomerFeedbackBinding;
import com.example.lawnwizard.viewmodels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class CustomerFeedbackFragment extends Fragment {

    UserViewModel userViewModel;

    FragmentCustomerFeedbackBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCustomerFeedbackBinding.inflate(inflater, container, false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        NavController controller = NavHostFragment.findNavController(this);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        binding.backArrow.setOnClickListener((v) ->{
            controller.navigate(R.id.action_customerFeedbackFragment_to_customerViewJobFragment);
        });

        userViewModel.loadUser();

        userViewModel.getUser().observe(getViewLifecycleOwner(), (user -> {
            if (user == null) {
                return;
            }
            binding.workerRatingBar.getRating();
            binding.customerProfileNameText.setText(user.getName());
            binding.customerEmailText.setText(Objects.requireNonNull(auth.getCurrentUser()).getEmail());
            binding.customerPhoneText.setText(user.getName());
        }));

        return binding.getRoot();
    }
}