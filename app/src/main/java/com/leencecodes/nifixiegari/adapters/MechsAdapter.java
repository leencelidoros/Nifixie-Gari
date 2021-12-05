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
import com.leencecodes.nifixiegari.models.Garage;
import com.leencecodes.nifixiegari.models.Name;

public class MechsAdapter extends ListAdapter<Name, MechsAdapter.MyViewHolder> {


    public MechsAdapter(@NonNull DiffUtil.ItemCallback<Name> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mech_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Name mech = getItem(position);
        holder.textView.setText(mech.getName());
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewNameMech);
        }
    }
}
