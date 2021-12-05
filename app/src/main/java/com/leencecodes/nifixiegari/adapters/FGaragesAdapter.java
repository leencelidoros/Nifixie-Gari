package com.leencecodes.nifixiegari.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.leencecodes.nifixiegari.R;
import com.leencecodes.nifixiegari.dashboard.HomeFragmentDirections;
import com.leencecodes.nifixiegari.models.Garage;
import com.leencecodes.nifixiegari.util.ItemClickListener;

public class FGaragesAdapter extends ListAdapter<Garage, FGaragesAdapter.MyViewHolder> {

    //ItemClickListener itemClickListener;

    public FGaragesAdapter(@NonNull DiffUtil.ItemCallback<Garage> diffCallback/*, ItemClickListener itemClickListener*/) {
        super(diffCallback);
        //this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.garages_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Garage garage = getItem(position);
        Glide.with(holder.imageView.getContext())
                .load(garage.getGarageImageUrl())
                .fitCenter()
                .into(holder.imageView);

        Garage garage1 = new Garage(garage.getGarageName(), garage.getGarageLocation(), garage.getGarageImageUrl());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = HomeFragmentDirections.actionHomeFragmentToGarageDetailsFragment(garage1);
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fGarage);
        }
    }
}
