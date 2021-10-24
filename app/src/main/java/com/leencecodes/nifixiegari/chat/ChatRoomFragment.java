package com.leencecodes.nifixiegari.chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.leencecodes.nifixiegari.R;
import com.leencecodes.nifixiegari.auth.LoginActivity;
import com.leencecodes.nifixiegari.observers.ButtonObserver;
import com.leencecodes.nifixiegari.adapters.ChatsRecyclerAdapter;
import com.leencecodes.nifixiegari.observers.ScrollToBottomObserver;
import com.leencecodes.nifixiegari.databinding.FragmentChatRoomBinding;
import com.leencecodes.nifixiegari.models.ChatMessage;
import com.leencecodes.nifixiegari.models.Mechanic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ChatRoomFragment extends Fragment {

    private static final String TAG = "ChatRoomFragment";

    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseRecyclerAdapter adapter;
    LinearLayoutManager manager;

    private String currentUserID;
    private String otherUserID;

    FragmentChatRoomBinding binding;

    Mechanic mechanic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatRoomBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        setHasOptionsMenu(true);

        mechanic = ChatRoomFragmentArgs.fromBundle(getArguments()).getMechanicDetails();

        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        otherUserID = mechanic.getUniqueUUID();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        databaseReference.child("chats").child(currentUserID).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.hasChild(otherUserID)) {

                            Map chatAddMap = new HashMap();
                            chatAddMap.put("seen", false);
                            chatAddMap.put("time_stamp", ServerValue.TIMESTAMP);

                            Map chatUserMap = new HashMap();
                            chatUserMap.put("chats/" + otherUserID + "/" + currentUserID, chatAddMap);
                            chatUserMap.put("chats/" + currentUserID + "/" + otherUserID, chatAddMap);

                            databaseReference.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError == null) {
                                        Toast.makeText(getContext(), "Successfully Added chats feature", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(getContext(), "Cannot Add chats feature", Toast.LENGTH_SHORT).show();
                                }


                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

        displayMessages();


        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

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


    private void sendMessage() {

        Log.d(TAG, "sendMessage: called");

        if (!TextUtils.isEmpty(binding.messageEditText.getText().toString())) {
            String currentUserReference = "messages/" + currentUserID + "/" + otherUserID;
            String otherUserReference = "messages/" + otherUserID + "/" + currentUserID;

            DatabaseReference userMessagePush = databaseReference.child("messages")
                    .child(currentUserID).child(otherUserID).push();

            String userMessagePushID = userMessagePush.getKey();

            Log.d(TAG, "sendMessage: created key" + userMessagePushID);

            ChatMessage message = new ChatMessage(
                    binding.messageEditText.getText().toString(),
                    firebaseUser.getDisplayName(),
                    new Date().toString()
            );

            Log.d(TAG, "sendMessage: chatMessage" + message);

            Map messageUserMap = new HashMap();
            messageUserMap.put(currentUserReference + "/" + userMessagePushID, message);
            messageUserMap.put(otherUserReference + "/" + userMessagePushID, message);

            databaseReference.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error != null) {
                        Log.d(TAG, "onComplete: Chat message cannot be added to the database" + error.getMessage());
                    } else {
                        Log.d(TAG, "onComplete: Completed" + ref.getKey());
                        Toast.makeText(requireContext(), "Message Sent", Toast.LENGTH_SHORT).show();
                        binding.messageEditText.setText("");
                    }
                }
            });
        } else {
            return;
        }

    }

    private void displayMessages() {
        DatabaseReference messagesReference = databaseReference.child("messages")
                .child(currentUserID).child(otherUserID);

        //.child(Leence ---- "6f3Edn8fHVV2ITVuGh4ggDTJ7If2").child(Joel ----"ydCcJKOoUzSXbD13eubZJ4svG9x2");

        Query query = messagesReference.limitToLast(50);

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
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.verify_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.verify == item.getItemId()) {
            databaseReference.child("users").child(mechanic.getUniqueUUID()).child("isVerified").setValue("true");
            Toast.makeText(requireContext(), "Verify User", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}