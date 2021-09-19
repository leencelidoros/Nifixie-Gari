package com.leencecodes.nifixiegari.models;


import android.os.Parcel;
import android.os.Parcelable;

public class ChatMessage implements Parcelable {
    String chatMessage;
    String chatSender;
    String chatTime;

    public ChatMessage() {
    }

    public ChatMessage(String chatMessage, String chatSender, String chatTime) {
        this.chatMessage = chatMessage;
        this.chatSender = chatSender;
        this.chatTime = chatTime;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public String getChatSender() {
        return chatSender;
    }

    public void setChatSender(String chatSender) {
        this.chatSender = chatSender;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.chatMessage);
        dest.writeString(this.chatSender);
        dest.writeString(this.chatTime);
    }

    public void readFromParcel(Parcel source) {
        this.chatMessage = source.readString();
        this.chatSender = source.readString();
        this.chatTime = source.readString();
    }

    protected ChatMessage(Parcel in) {
        this.chatMessage = in.readString();
        this.chatSender = in.readString();
        this.chatTime = in.readString();
    }

    public static final Parcelable.Creator<ChatMessage> CREATOR = new Parcelable.Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel source) {
            return new ChatMessage(source);
        }

        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };
}
