package com.example.lawnwizard.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentSubmitJobBinding;
import com.example.lawnwizard.databinding.FragmentWorkerViewJobBinding;
import com.example.lawnwizard.models.Job;
import com.example.lawnwizard.viewmodels.JobViewModel;
import com.example.lawnwizard.viewmodels.UserViewModel;

public class SubmitJobFragment extends Fragment {

    Intent intent;
    int REQUEST_IMAGE_CAPTURE = 1;
    int RESULT_LOAD_IMAGE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSubmitJobBinding binding = FragmentSubmitJobBinding.inflate(inflater, container, false);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        JobViewModel jobViewModel = new ViewModelProvider(getActivity()).get(JobViewModel.class);
        NavController controller = NavHostFragment.findNavController(this);

        binding.ownerEditTextSubmitJob.setText(jobViewModel.getSelectedJob().getValue().getHomeowner());
        int pay = jobViewModel.getSelectedJob().getValue().getPay();
        binding.paymentEditTextSubmitJob.setText(Integer.toString(pay));

        binding.submitJobBackArrow.setOnClickListener((v)->{
            controller.navigate(R.id.action_submitJobFragment_to_workerActiveJobsFragment);
        });

        binding.submissionImage2.setOnClickListener((view) -> {
            intent = new Intent(
                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, RESULT_LOAD_IMAGE);
//            new MaterialAlertDialogBuilder(getActivity())
//                    .setTitle("Choose Image")
//                    .setItems(new CharSequence[]{"From Camera", "From Photos"}, (menuItem, i) -> {
//                        if (i == 0) {
//                            //Camera
//                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//                            String fileName = "JPEG_" + timeStamp + ".jpg";
//
//                            File imageFile = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName);
//                            currentFilePath = imageFile.getAbsolutePath();
//
//                            Uri imageUri = FileProvider.getUriForFile(getActivity(), "com.example.contacts.fileprovider", imageFile);
//
//                            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
////                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                            uriFilePath = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"fname_" +
////                                    String.valueOf(System.currentTimeMillis()) + ".jpg"));
////                            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uriFilePath);
////                            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
//                        } else {
//                            //Photos
//                            intent = new Intent(
//                                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                            startActivityForResult(intent, RESULT_LOAD_IMAGE);
//                        }
//                    }).show();
        });

        binding.submitJobButton.setOnClickListener((v) -> {
            userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
                if (user == null) return;
                Job job = jobViewModel.getSelectedJob().getValue();
                job.setCompleted();

                user.setBalance(job.getPay());
                userViewModel.updateUser(user);

                jobViewModel.updateJob(job);
                controller.navigate(R.id.action_submitJobFragment_to_workerHomeFragment);
            });
        });

        return binding.getRoot();

    }
}