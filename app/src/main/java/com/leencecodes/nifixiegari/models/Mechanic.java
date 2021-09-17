package com.leencecodes.nifixiegari.models;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Mechanic {
    String mechanicName;
    String mechanicLocation;
    String mechanicImageUrl;

    public Mechanic() {
    }

    public Mechanic(String mechanicName, String mechanicLocation, String mechanicImageUrl) {
        this.mechanicName = mechanicName;
        this.mechanicLocation = mechanicLocation;
        this.mechanicImageUrl = mechanicImageUrl;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public void setMechanicName(String mechanicName) {
        this.mechanicName = mechanicName;
    }

    public String getMechanicLocation() {
        return mechanicLocation;
    }

    public void setMechanicLocation(String mechanicLocation) {
        this.mechanicLocation = mechanicLocation;
    }

    public String getMechanicImageUrl() {
        return mechanicImageUrl;
    }

    public void setMechanicImageUrl(String mechanicImageUrl) {
        this.mechanicImageUrl = mechanicImageUrl;
    }

    public static DiffUtil.ItemCallback<Mechanic> itemCallback = new DiffUtil.ItemCallback<Mechanic>() {
        @Override
        public boolean areItemsTheSame(@NonNull Mechanic oldItem, @NonNull Mechanic newItem) {
            return oldItem.mechanicName.equals(newItem.mechanicName);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Mechanic oldItem, @NonNull Mechanic newItem) {
            return oldItem.equals(newItem);
        }
    };
}
