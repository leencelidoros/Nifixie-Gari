package com.leencecodes.nifixiegari.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.leencecodes.nifixiegari.R;
import com.leencecodes.nifixiegari.models.Mechanic;

import java.util.ArrayList;

public class SearchMechanicsAdapter extends ListAdapter<Mechanic, SearchMechanicsAdapter.MyViewHolder> {

    public SearchMechanicsAdapter(@NonNull DiffUtil.ItemCallback<Mechanic> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_mechanic_row,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Mechanic mechanic = getItem(position);
        Glide.with(holder.imageView.getContext())
                .load(mechanic.getMechanicImageUrl())
                .into(holder.imageView);

        holder.name.setText(mechanic.getMechanicName());
        holder.location.setText(mechanic.getMechanicLocation());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name;
        TextView location;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewMech);
            name = itemView.findViewById(R.id.textViewMechName);
            location = itemView.findViewById(R.id.textViewMechLocation);
        }
    }
}