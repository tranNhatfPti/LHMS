package com.chatapp.models.dtos;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDTO {

    @JsonProperty("accountId") // người gửi
    private int accountId;
    
    @JsonProperty("avatar")
    private String avatar;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("receiver")
    private int receiver;
    
    @JsonProperty("onlineList")
    private Set<Integer> onlineList = new HashSet<Integer>();

    public MessageDTO() {
    }

    @JsonCreator
    public MessageDTO(
            @JsonProperty("accountId") int accountId, 
            @JsonProperty("message") String message,
            @JsonProperty("type") String type, 
            @JsonProperty("receiver") int receiver) {
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Set<Integer> getOnlineList() {
        return onlineList;
    }

    public void setOnlineList(Set<Integer> onlineList) {
        this.onlineList = onlineList;
    }

}
