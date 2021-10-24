package com.leencecodes.nifixiegari.adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.leencecodes.nifixiegari.R;
import com.leencecodes.nifixiegari.chat.ChatsFragmentDirections;
import com.leencecodes.nifixiegari.models.Chat;
import com.leencecodes.nifixiegari.models.Mechanic;

public class AllChatsAdapter extends FirebaseRecyclerAdapter<Chat, AllChatsAdapter.MessagesViewHolder> {

    private static final String TAG = "AllChatsAdapter";

    public AllChatsAdapter(@NonNull FirebaseRecyclerOptions<Chat> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MessagesViewHolder holder, int position, @NonNull Chat model) {

        DatabaseReference mMessageDatabase = FirebaseDatabase.getInstance().getReference().child("messages").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        final String list_user_id = getRef(position).getKey();
        Query lastMessageQuery = mMessageDatabase.child(list_user_id).limitToLast(1);

        Log.d(TAG, "onBindViewHolder: user_key " + list_user_id);

        //---IT WORKS WHENEVER CHILD OF mMessageDatabase IS CHANGED---
        lastMessageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String data = dataSnapshot.child("chatMessage").getValue().toString();
                String time = dataSnapshot.child("chatTime").getValue().toString();
                holder.setMessage(data);
                holder.setTime(time);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final String userName = dataSnapshot.child("name").getValue().toString();
                holder.setName(userName);


                holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Mechanic mechanic = new Mechanic(userName,"","",list_user_id,"","");
                        NavDirections action = ChatsFragmentDirections.actionChatsFragmentToChatRoomFragment(mechanic);
                        Navigation.findNavController(v).navigate(action);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_row, parent, false);
        return new MessagesViewHolder(view);
    }

    public class MessagesViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        TextView name;
        TextView time;
        CardView card;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.textViewLastMessage);
            name = itemView.findViewById(R.id.textViewSendeeName);
            time = itemView.findViewById(R.id.textViewLastMessageTime);
            card = itemView.findViewById(R.id.card);
        }

        public void setTime(String tme) {
            time.setText(tme);
        }

        public void setMessage(String data) {
            message.setText(data);
        }

        public void setName(String userName) {
            name.setText(userName);
        }
    }
}