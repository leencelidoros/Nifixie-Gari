package com.leencecodes.nifixiegari.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.leencecodes.nifixiegari.R;
import com.leencecodes.nifixiegari.models.Mechanic;

public class FMechanicsAdapter extends ListAdapter<Mechanic, FMechanicsAdapter.MyViewHolder> {

    public FMechanicsAdapter(@NonNull DiffUtil.ItemCallback<Mechanic> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mechanics_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Mechanic mechanic = getItem(position);
        Glide.with(holder.imageView.getContext())
                .load(mechanic.getMechanicImageUrl())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fMechanic);
        }
    }
}
