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
import com.example.lawnwizard.databinding.FragmentCustomerHomeBinding;
import com.example.lawnwizard.databinding.FragmentWorkerProfileBinding;
import com.example.lawnwizard.viewmodels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class WorkerProfileFragment extends Fragment {
    UserViewModel userViewModel;

    FragmentWorkerProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWorkerProfileBinding.inflate(inflater, container, false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        NavController controller = NavHostFragment.findNavController(this);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        binding.backArrowWorkerProfile.setOnClickListener((v) ->{
            controller.navigate(R.id.action_workerProfileFragment_to_workerHomeFragment);
        });

        binding.logoutButtonWorkerProfile.setOnClickListener((v) -> {
            auth.signOut();
            controller.navigate(R.id.action_workerProfileFragment_to_signInFragment);
        });

        userViewModel.loadUser();

        userViewModel.getUser().observe(getViewLifecycleOwner(), (user -> {
            if (user == null) {
                return;
            }
            binding.nameTextWorkerProfile.setText(user.getName());
            binding.balanceEditTextWorkerProfile.setText(user.getBalance().toString());
            binding.emailEditTextWorkerProfile.setText(Objects.requireNonNull(auth.getCurrentUser()).getEmail());
            binding.phoneEditTextWorkerProfile.setText(user.getPhoneNumber());
            binding.ratingWorkerProfile.getRating();

            binding.transferFundsButtonWorkerProfile.setOnClickListener((v) -> {
                if(binding.transferFundsWorkerProfile.getText().toString().equals("")){
                    binding.transferFundsWorkerProfile.setError("Please enter a value to transfer");
                }else if(Integer.parseInt(binding.transferFundsWorkerProfile.getText().toString()) < 0){
                    binding.transferFundsWorkerProfile.setError("Please enter a positive number");
                }else{
                    user.setBalance(-Integer.parseInt(binding.transferFundsWorkerProfile.getText().toString()));
                    binding.balanceEditTextWorkerProfile.setText(user.getBalance().toString());
                    userViewModel.updateUser(user);
                    binding.transferFundsWorkerProfile.setText("");
                }
            });
        }));

        return binding.getRoot();
    }
}