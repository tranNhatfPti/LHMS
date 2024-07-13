package com.chatapp.models;

public class Message {

    private int accountId; // sender
    private String message;
    private String avatar;
    private String type;
    private int receiver;

    public Message() {

    }

    public Message(int accountId, String message, String type, int receiver) {
        this.accountId = accountId;
        this.message = message;
        this.type = type;
        this.receiver = receiver;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    
}
