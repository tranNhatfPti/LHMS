package com.chatapp.services;

import java.util.List;

import jakarta.servlet.http.Part;
import model.InformationOfUser;

public interface UserServiceInterface {
    public List<InformationOfUser> findFriends(int accountId);

    public List<InformationOfUser> findFriendsByKeyWord(int accountId, String keyword);

}
