package com.chatapp.daos.impl;

import java.util.List;

import com.chatapp.mappers.impl.FriendMapper;
import com.chatapp.models.Friend;

public class FriendDao extends GenericDao<Friend> {

    public void saveFriend(boolean isAccept, Friend friend) {
        int sender = friend.getSender();
        int receiver = friend.getReceiver();
        int owner = friend.getOwner();
        Boolean status = friend.isStatus();
        StringBuilder sql1 = new StringBuilder();
        StringBuilder sql2 = new StringBuilder();
        
        // nhận lời mời kết bạn
        if (isAccept) {
            sql1.append("update friends set status=? where sender = ? and receiver = ?");
            sql2.append("update friends set status=? where sender = ? and receiver = ?");
            save(sql1.toString(), status, sender, receiver);
            save(sql2.toString(), status, receiver, sender);
        } 
        // gửi lời mời kết bạn
        else {
            StringBuilder sqlCheckExist = new StringBuilder();
            sqlCheckExist.append("select * from friends");
            sqlCheckExist.append(" where sender = ? and receiver = ?");
            List<Friend> friends = query(sqlCheckExist.toString(), new FriendMapper(), sender, receiver);
            if (friends.isEmpty()) {
                sql1.append("insert into friends values(?,?,?,?)");
                sql2.append("insert into friends values(?,?,?,?)");

                save(sql1.toString(), sender, receiver, owner, status);
                save(sql2.toString(), receiver, sender, owner, status);
            }
        }
    }

    public Friend findFriend(int sender, int receiver) {
        StringBuilder sql = new StringBuilder(
                "select sender,receiver, owner, status from friends where sender=? and receiver=?");

        List<Friend> friends = query(sql.toString(), new FriendMapper(), sender, receiver);
        return friends.isEmpty() ? null : friends.get(0);
    }
}
