package com.example.lawnwizard.adapters;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lawnwizard.databinding.JobCardBinding;
import com.example.lawnwizard.models.Job;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class JobHistoryAdapter extends RecyclerView.Adapter<JobHistoryAdapter.ViewHolder> {
    ObservableArrayList<Job> jobs;
    OnJobSelectedListener listener;

    public JobHistoryAdapter(ObservableArrayList<Job> jobs, OnJobSelectedListener listener) {
        this.jobs = jobs;
        Log.d("---", String.valueOf(jobs.size()));
        this.listener = listener;
        jobs.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Job>>() {
            @Override
            public void onChanged(ObservableList sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        JobCardBinding binding = JobCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getBinding().userName.setText(jobs.get(position).getHomeowner());
        holder.itemView.setOnClickListener(view -> {
            this.listener.onSelected(jobs.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        JobCardBinding binding;
        public ViewHolder(@NonNull JobCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public JobCardBinding getBinding() { return binding; }
    }

    public static interface OnJobSelectedListener {
        public void onSelected(Job job);
    }

    //TODO: make sure this works
    public String fromGeoPoint(Context context, GeoPoint point) {
        Geocoder coder = new Geocoder(
                context , Locale.getDefault());
        String add = "";
        try {
            List<Address> addresses = coder.getFromLocation(
                    point.getLatitude(),
                    point.getLongitude(), 1);


            if (addresses.size() > 0)
            {
                for (int i=0; i<addresses.get(0).getMaxAddressLineIndex();
                     i++)
                    add += addresses.get(0).getAddressLine(i) + "\n";
            }

//            Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return add;
    }
}
