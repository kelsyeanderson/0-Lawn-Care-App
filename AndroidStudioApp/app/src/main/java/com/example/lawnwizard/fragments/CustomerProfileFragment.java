package com.example.lawnwizard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentCustomerHomeBinding;
import com.example.lawnwizard.databinding.FragmentCustomerProfileBinding;
import com.example.lawnwizard.models.User;
import com.example.lawnwizard.viewmodels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class CustomerProfileFragment extends Fragment {
    UserViewModel userViewModel;
    FragmentCustomerProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCustomerProfileBinding.inflate(inflater, container, false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        NavController controller = NavHostFragment.findNavController(this);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.loadUser();
        userViewModel.getUser().observe(getViewLifecycleOwner(), (user -> {
            if (user == null) {
                return;
            }

            binding.transferFundsButtonCustomerProfile.setOnClickListener((v) -> {
                if(binding.transferFundsCustomerProfile.getText().toString().equals("")){
                    binding.transferFundsCustomerProfile.setError("Please enter a value");
                }else if(Integer.parseInt(binding.transferFundsCustomerProfile.getText().toString()) < 0){
                    binding.transferFundsCustomerProfile.setError("Please enter a positive number");
                }else{
                    user.setBalance(Integer.parseInt(binding.transferFundsCustomerProfile.getText().toString()));
                    userViewModel.updateUser(user);
                    binding.transferFundsCustomerProfile.setText("");
                }
            });

            binding.nameTextCustomerProfile.setText(user.getName());
            binding.balanceEditTextCustomerProfile.setText(user.getBalance().toString());
            binding.emailEditTextCustomerProfile.setText(Objects.requireNonNull(auth.getCurrentUser()).getEmail());
            binding.phoneEditTextCustomerProfile.setText(user.getPhoneNumber());
        }));

        binding.logoutButtonCustomerProfile.setOnClickListener((v) -> {
            auth.signOut();
            controller.navigate(R.id.action_costomerProfileFragment_to_signInFragment);
        });

        binding.bacbkArrowCustomerProfile.setOnClickListener((v) -> {
            controller.navigate(R.id.action_costomerProfileFragment_to_customerHomeFragment);
        });

        return binding.getRoot();
    }
}