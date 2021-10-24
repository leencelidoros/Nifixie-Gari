package com.leencecodes.nifixiegari.models;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Mechanic implements Parcelable {
    String mechanicName;

    public Mechanic(String mechanicName, String mechanicLocation, String mechanicImageUrl, String uniqueUUID, String accountType, String isVerified) {
        this.mechanicName = mechanicName;
        this.mechanicLocation = mechanicLocation;
        this.mechanicImageUrl = mechanicImageUrl;
        this.uniqueUUID = uniqueUUID;
        this.accountType = accountType;
        this.isVerified = isVerified;
    }

    String mechanicLocation;
    String mechanicImageUrl;
    String uniqueUUID;
    String accountType;
    String isVerified;


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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mechanicName);
        dest.writeString(this.mechanicLocation);
        dest.writeString(this.mechanicImageUrl);
        dest.writeString(this.uniqueUUID);
        dest.writeString(this.accountType);
        dest.writeString(this.isVerified);
    }

    public void readFromParcel(Parcel source) {
        this.mechanicName = source.readString();
        this.mechanicLocation = source.readString();
        this.mechanicImageUrl = source.readString();
        this.uniqueUUID = source.readString();
        this.accountType = source.readString();
        this.isVerified = source.readString();
    }

    public Mechanic() {
    }

    protected Mechanic(Parcel in) {
        this.mechanicName = in.readString();
        this.mechanicLocation = in.readString();
        this.mechanicImageUrl = in.readString();
        this.uniqueUUID = in.readString();
        this.accountType = in.readString();
        this.isVerified = in.readString();
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

    public String getUniqueUUID() {
        return uniqueUUID;
    }

    public void setUniqueUUID(String uniqueUUID) {
        this.uniqueUUID = uniqueUUID;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public static final Parcelable.Creator<Mechanic> CREATOR = new Parcelable.Creator<Mechanic>() {
        @Override
        public Mechanic createFromParcel(Parcel source) {
            return new Mechanic(source);
        }

        @Override
        public Mechanic[] newArray(int size) {
            return new Mechanic[size];
        }
    };
}
