package com.leencecodes.nifixiegari.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leencecodes.nifixiegari.R;
import com.leencecodes.nifixiegari.adapters.FGaragesAdapter;
import com.leencecodes.nifixiegari.adapters.FMechanicsAdapter;
import com.leencecodes.nifixiegari.databinding.FragmentHomeBinding;
import com.leencecodes.nifixiegari.models.Garage;
import com.leencecodes.nifixiegari.models.Mechanic;

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DatabaseReference databaseReference;

    private FGaragesAdapter garagesAdapter = new FGaragesAdapter(Garage.itemCallback);
    private FMechanicsAdapter mechanicsAdapter = new FMechanicsAdapter(Mechanic.itemCallback);

    private ArrayList<Garage> garageArrayList = new ArrayList<>();
    private ArrayList<Mechanic> mechanicArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        getFeaturedGarages();
        getFeaturedMechanics();

        return view;
    }

    private void getFeaturedGarages() {
        databaseReference.child("featured_garages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot i : snapshot.getChildren()) {
                        Garage garage = i.getValue(Garage.class);
                        garageArrayList.add(garage);
                    }
                    garagesAdapter.submitList(garageArrayList);
                    binding.recyclerViewGarages.setAdapter(garagesAdapter);
                } else {
                    Toast.makeText(requireContext(), "Data Does not Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getFeaturedMechanics() {
        databaseReference.child("featured_mechanics").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot i : snapshot.getChildren()) {
                        Mechanic mechanic = i.getValue(Mechanic.class);
                        mechanicArrayList.add(mechanic);
                    }
                    mechanicsAdapter.submitList(mechanicArrayList);
                    binding.recyclerViewMechanics.setAdapter(mechanicsAdapter);
                } else {
                    Toast.makeText(requireContext(), "Data Does not Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}