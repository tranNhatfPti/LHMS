package com.chatapp.mappers.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.chatapp.mappers.RowMapperInterface;
import model.InformationOfUser;

public class UserMapper implements RowMapperInterface<InformationOfUser> {

    @Override
    public InformationOfUser mapRow(ResultSet rs) {
        try {
            InformationOfUser user = new InformationOfUser();
            
            user.setAccountID(rs.getInt("AccountId"));
            user.setFullName(rs.getString("FullName"));
            user.setAvatar(rs.getString("Avatar"));
            user.setGender(rs.getInt("Gender"));
            
            return user;
        } catch (SQLException e) {
            return null;
        }
    }
}
