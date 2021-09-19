package com.leencecodes.nifixiegari.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.leencecodes.nifixiegari.adapters.AllChatsAdapter;
import com.leencecodes.nifixiegari.databinding.FragmentChatsBinding;
import com.leencecodes.nifixiegari.models.Chat;

public class ChatsFragment extends Fragment {

    private FragmentChatsBinding binding;
    private AllChatsAdapter adapter;

    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    private String currentUserID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatsBinding.inflate(inflater, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUserID = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        DatabaseReference mConvDatabase = databaseReference.child("chats").child(currentUserID);

        Query query = mConvDatabase.orderByChild("time_stamp");

        FirebaseRecyclerOptions<Chat> options = new FirebaseRecyclerOptions.Builder<Chat>()
                .setQuery(query, Chat.class)
                .build();

        adapter = new AllChatsAdapter(options);
        binding.chatsRecyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}