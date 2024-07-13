package com.chatapp.models;

public class Friend {
    private int sender;
    private int receiver;
    private int owner;
    private boolean status;

    public Friend() {

    }

    public Friend(int sender, int receiver, int owner, boolean status) {
        this.sender = sender;
        this.receiver = receiver;
        this.owner = owner;
        this.status = status;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
