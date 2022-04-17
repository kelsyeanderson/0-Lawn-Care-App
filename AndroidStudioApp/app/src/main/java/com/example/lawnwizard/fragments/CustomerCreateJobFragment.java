package com.example.lawnwizard.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.lawnwizard.R;
import com.example.lawnwizard.databinding.FragmentCustomerCreateJobBinding;
import com.example.lawnwizard.databinding.FragmentSignInBinding;
import com.example.lawnwizard.models.Job;
import com.example.lawnwizard.viewmodels.JobViewModel;
import com.example.lawnwizard.viewmodels.UserViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.GeoPoint;
import com.google.type.LatLng;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomerCreateJobFragment extends Fragment {

    FragmentCustomerCreateJobBinding binding;
    UserViewModel userViewModel;
    JobViewModel jobViewModel;
    NavController controller;
    String currentFilePath = "";
    Uri uriFilePath;
    Intent intent;
    int REQUEST_IMAGE_CAPTURE = 1;
    int RESULT_LOAD_IMAGE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCustomerCreateJobBinding.inflate(inflater, container, false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        controller = NavHostFragment.findNavController(this);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);

        binding.backArrowCustomerCreateJob.setOnClickListener((v) -> {
            controller.navigate(R.id.action_customerCreateJobFragment_to_customerHomeFragment);
        });

        binding.jobImageCustomerCreateJob.setOnClickListener((view) -> {
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

        binding.createJobButtonCustomerCreateJob.setOnClickListener((v) -> {
            binding.createJobButtonCustomerCreateJob.setEnabled(false);
            if(!foundInputErrors(binding.addressInputCustomerCreateJob , binding.paymentInputCustomerCreateJob, binding.jobDescriptionInputCustomerCreateJob)){
                addJob();

            } else {
                binding.createJobButtonCustomerCreateJob.setEnabled(true);
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
            GeoPoint loc = getLocationFromAddress(getContext(), binding.addressInputCustomerCreateJob.getText().toString());
            if (loc == null) {
                binding.addressInputCustomerCreateJob.setError("Please enter a valid address");
                return;
            }
            if (uriFilePath == null){
                Job inJob = new Job(user, binding.jobDescriptionInputCustomerCreateJob.getText().toString(),
                        Integer.parseInt(binding.paymentInputCustomerCreateJob.getText().toString()), loc, ""
                );
                jobViewModel.saveJob(inJob);
            } else{
                Job inJob = new Job(
                        user,
                        binding.jobDescriptionInputCustomerCreateJob.getText().toString(),
                        Integer.parseInt(binding.paymentInputCustomerCreateJob.getText().toString()),
                        loc,
                        uriFilePath.toString()
                );
                jobViewModel.saveJob(inJob);
            }
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
            p1 = new GeoPoint((double) (location.getLatitude()),
                    (double) (location.getLongitude()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return p1;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data); comment this unless you want to pass your result to the activity.
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            uriFilePath = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(uriFilePath,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            binding.jobImageCustomerCreateJob.setImageURI(uriFilePath);
        }else if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap bmp = (Bitmap) extras.get("data");
            binding.jobImageCustomerCreateJob.setImageURI(uriFilePath);
        }
    }


}