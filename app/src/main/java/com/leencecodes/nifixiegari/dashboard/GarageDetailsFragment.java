package com.leencecodes.nifixiegari.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.leencecodes.nifixiegari.R;
import com.leencecodes.nifixiegari.adapters.FMechanicsAdapter;
import com.leencecodes.nifixiegari.adapters.MechsAdapter;
import com.leencecodes.nifixiegari.databinding.FragmentGarageDetailsBinding;
import com.leencecodes.nifixiegari.models.Garage;
import com.leencecodes.nifixiegari.models.Mechanic;
import com.leencecodes.nifixiegari.models.Name;

import java.util.ArrayList;


public class GarageDetailsFragment extends Fragment {

    private FragmentGarageDetailsBinding binding;
    private DatabaseReference databaseReference;

    private MechsAdapter mechsAdapter = new MechsAdapter(Name.itemCallback);
    private ArrayList<Name> mechsArrayList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGarageDetailsBinding.inflate(inflater, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("featured_garages");

        assert getArguments() != null;

        Garage garage = GarageDetailsFragmentArgs.fromBundle(getArguments()).getGarageDetails();

        Glide.with(binding.imageViewGarage)
                .load(garage.getGarageImageUrl())
                .fitCenter()
                .into(binding.imageViewGarage);

        binding.textViewGName.setText(garage.getGarageName());
        binding.textViewGLocation.setText(garage.getGarageLocation());

        getGarageMechs(garage.getGarageName());

        return binding.getRoot();
    }

    private void getGarageMechs(String garageName){
        databaseReference.child(garageName).child("mechs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot i : snapshot.getChildren()) {
                        Name name = i.getValue(Name.class);
                        mechsArrayList.add(name);
                    }
                    mechsAdapter.submitList(mechsArrayList);
                    binding.garageMechsRecycler.setAdapter(mechsAdapter);
                }else{
                    Toast.makeText(requireContext(), "No Mechanics Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}