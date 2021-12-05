package com.leencecodes.nifixiegari.models;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Name {

    private String name;

    public Name() {
    }

    public Name(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static DiffUtil.ItemCallback<Name> itemCallback = new DiffUtil.ItemCallback<Name>() {
        @Override
        public boolean areItemsTheSame(@NonNull Name oldItem, @NonNull Name newItem) {
            return oldItem.name.equals(newItem.name);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Name oldItem, @NonNull Name newItem) {
            return oldItem.equals(newItem);
        }
    };
}
