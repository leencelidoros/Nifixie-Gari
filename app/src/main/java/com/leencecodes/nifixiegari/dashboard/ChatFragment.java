package com.leencecodes.nifixiegari.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.leencecodes.nifixiegari.R;
import com.leencecodes.nifixiegari.adapters.ButtonObserver;
import com.leencecodes.nifixiegari.adapters.ChatsRecyclerAdapter;
import com.leencecodes.nifixiegari.adapters.ScrollToBottomObserver;
import com.leencecodes.nifixiegari.databinding.FragmentChatBinding;
import com.leencecodes.nifixiegari.models.ChatMessage;


public class ChatFragment extends Fragment {

    private static final String TAG = "ChatFragment";

    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseRecyclerAdapter adapter;
    LinearLayoutManager manager;

    FragmentChatBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Query query = databaseReference.child("chats")
                .limitToLast(50);

        FirebaseRecyclerOptions<ChatMessage> options = new FirebaseRecyclerOptions.Builder<ChatMessage>()
                .setQuery(query, ChatMessage.class)
                .build();

        adapter = new ChatsRecyclerAdapter(options, firebaseUser.getDisplayName());

        manager = new LinearLayoutManager(getActivity().getApplicationContext());
        manager.setStackFromEnd(true);
        binding.messageRecyclerView.setLayoutManager(manager);
        binding.messageRecyclerView.setAdapter(adapter);

        adapter.registerAdapterDataObserver(
                new ScrollToBottomObserver(binding.messageRecyclerView, (ChatsRecyclerAdapter) adapter, manager)
        );

        binding.messageEditText.addTextChangedListener(new ButtonObserver(binding.sendButton));

        return view;
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

    private void sendMessage(String message, String sender, String time) {
        //ChatMessage message = new ChatMessage("Hello World","Joel Kanyi","18/09/2021 at 5:54AM");
        ChatMessage message1 = new ChatMessage(message, sender, time);
        databaseReference.child("chats").push().setValue(message1);
    }
}