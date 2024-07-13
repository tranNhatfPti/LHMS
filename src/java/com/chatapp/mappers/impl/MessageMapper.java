package com.chatapp.mappers.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.chatapp.mappers.RowMapperInterface;
import com.chatapp.models.Message;

public class MessageMapper implements RowMapperInterface<Message> {

    @Override
    public Message mapRow(ResultSet resultSet) {
        try {
            Message message = new Message();
            message.setAccountId(resultSet.getInt("sender"));
            message.setMessage(resultSet.getString("message"));
            message.setType(resultSet.getString("message_type").trim());
            message.setReceiver(resultSet.getInt("receiver"));
            try {
                message.setAvatar(resultSet.getString("avatar").trim());
            } catch (SQLException ex) {
                return message;
            }
            return message;
        } catch (SQLException e) {
            return null;
        }
    }
}
