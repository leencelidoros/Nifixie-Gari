package com.leencecodes.nifixiegari.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.leencecodes.nifixiegari.R;
import com.leencecodes.nifixiegari.models.ChatMessage;


public class ChatsRecyclerAdapter extends FirebaseRecyclerAdapter<ChatMessage, ChatsRecyclerAdapter.MessagesViewHolder> {

    String currentUserName;

    public ChatsRecyclerAdapter(@NonNull FirebaseRecyclerOptions<ChatMessage> options, String currentUserName) {
        super(options);
        this.currentUserName = currentUserName;
    }

    @Override
    protected void onBindViewHolder(@NonNull MessagesViewHolder holder, int position, @NonNull ChatMessage model) {
        ChatMessage message = model;
        holder.bind(message);
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row, parent, false);
        return new MessagesViewHolder(view);
    }

    public class MessagesViewHolder extends RecyclerView.ViewHolder {
        TextView message;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.messageTextView);
        }

        public void bind(ChatMessage messge) {
            message.setText(messge.getChatMessage());
            setTextColor(messge.getChatSender(), message);
        }

        private void setTextColor(String userName, TextView textView) {
            if (currentUserName == userName && userName != null) {
                textView.setBackgroundResource(R.drawable.message_blue);
                textView.setTextColor(Color.WHITE);
            } else {
                textView.setBackgroundResource(R.drawable.message_gray);
                textView.setTextColor(Color.BLACK);
            }
        }
    }
}