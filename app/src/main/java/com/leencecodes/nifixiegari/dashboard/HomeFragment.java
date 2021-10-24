package com.leencecodes.nifixiegari.dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leencecodes.nifixiegari.R;
import com.leencecodes.nifixiegari.adapters.FGaragesAdapter;
import com.leencecodes.nifixiegari.adapters.FMechanicsAdapter;
import com.leencecodes.nifixiegari.auth.LoginActivity;
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

        setHasOptionsMenu(true);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        binding.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_paymentFragment);
            }
        });

        getFeaturedGarages();
        getFeaturedMechanics();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.logout_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.logout == item.getItemId()) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            requireActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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