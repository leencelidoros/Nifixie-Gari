package com.leencecodes.nifixiegari.models;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class Garage implements Parcelable {
    String garageName;
    String garageLocation;
    String garageImageUrl;
    List<Name> mechs;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.garageName);
        dest.writeString(this.garageLocation);
        dest.writeString(this.garageImageUrl);
    }

    public void readFromParcel(Parcel source) {
        this.garageName = source.readString();
        this.garageLocation = source.readString();
        this.garageImageUrl = source.readString();
    }

    protected Garage(Parcel in) {
        this.garageName = in.readString();
        this.garageLocation = in.readString();
        this.garageImageUrl = in.readString();
    }

    public static final Parcelable.Creator<Garage> CREATOR = new Parcelable.Creator<Garage>() {
        @Override
        public Garage createFromParcel(Parcel source) {
            return new Garage(source);
        }

        @Override
        public Garage[] newArray(int size) {
            return new Garage[size];
        }
    };
}
