package com.chatapp.mappers.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.chatapp.mappers.RowMapperInterface;
import com.chatapp.models.Friend;

public class FriendMapper implements RowMapperInterface<Friend> {

    @Override
    public Friend mapRow(ResultSet resultSet) {
        try {
            Friend friend = new Friend();
            friend.setSender(resultSet.getInt("sender"));
            friend.setReceiver(resultSet.getInt("receiver"));
            friend.setOwner(resultSet.getInt("owner"));
            friend.setStatus(resultSet.getBoolean("status"));
            return friend;
        } catch (SQLException e) {
            return null;
        }
    }
}
