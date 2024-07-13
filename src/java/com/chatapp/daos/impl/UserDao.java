package com.chatapp.daos.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.chatapp.daos.UserDaoInterface;
import com.chatapp.mappers.impl.UserMapper;
import model.InformationOfUser;

public class UserDao extends GenericDao<InformationOfUser> implements UserDaoInterface {

    private static UserDao instance = null;

    private UserDao() {

    }

    public synchronized static UserDao getInstace() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    // lấy cả những user chưa accept và đã accept
    @Override
    public List<InformationOfUser> findFriends(int accountId) {
        StringBuilder sql = new StringBuilder("select distinct u2.AccountId, u2.FullName, u2.Avatar, u2.Gender");
        sql.append(" from InformationOfUser u1 join Friends f on u1.AccountId = f.receiver");
        sql.append(" join InformationOfUser u2 on u2.AccountId = f.sender");
        sql.append(" where u1.AccountId = ?");
        
        List<InformationOfUser> users = query(sql.toString(), new UserMapper(), accountId);
        return users.stream().filter(u -> u.getAccountID()!=accountId).collect(Collectors.toList());
    }

    @Override
    public List<InformationOfUser> findFriendsByKeyWord(int accounId, String keyWord) {
        StringBuilder sql = new StringBuilder("select distinct u2.AccountId, u2.FullName, u2.Avatar, u2.Gender");
        sql.append(" from InformationOfUser u2 where u2.AccountId != ? and u2.FullName like ?");
        String param = "%" + keyWord + "%";
        
        List<InformationOfUser> users = query(sql.toString(), new UserMapper(), accounId, param);
        
        return users;
    }
    
    public static void main(String[] args) {
        UserDao ud = new UserDao();
        System.out.println(ud.findFriendsByKeyWord(6, "a"));
    }

}
