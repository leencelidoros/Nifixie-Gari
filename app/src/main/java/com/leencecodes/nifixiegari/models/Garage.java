package com.leencecodes.nifixiegari.models;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Garage {
    String garageName;
    String garageLocation;
    String garageImageUrl;

    public Garage() {
    }

    public Garage(String garageName, String garageLocation, String garageImageUrl) {
        this.garageName = garageName;
        this.garageLocation = garageLocation;
        this.garageImageUrl = garageImageUrl;
    }

    public String getGarageName() {
        return garageName;
    }

    public void setGarageName(String garageName) {
        this.garageName = garageName;
    }

    public String getGarageLocation() {
        return garageLocation;
    }

    public void setGarageLocation(String garageLocation) {
        this.garageLocation = garageLocation;
    }

    public String getGarageImageUrl() {
        return garageImageUrl;
    }

    public void setGarageImageUrl(String garageImageUrl) {
        this.garageImageUrl = garageImageUrl;
    }

    public static DiffUtil.ItemCallback<Garage> itemCallback = new DiffUtil.ItemCallback<Garage>() {
        @Override
        public boolean areItemsTheSame(@NonNull Garage oldItem, @NonNull Garage newItem) {
            return oldItem.garageName.equals(newItem.garageName);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Garage oldItem, @NonNull Garage newItem) {
            return oldItem.equals(newItem);
        }
    };
}
