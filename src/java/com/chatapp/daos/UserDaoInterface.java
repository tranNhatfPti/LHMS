package com.chatapp.daos;

import java.util.List;
import model.InformationOfUser;

public interface UserDaoInterface extends GenericDaoInterface<InformationOfUser> {
    List<InformationOfUser> findFriends(int accountId);

    List<InformationOfUser> findFriendsByKeyWord(int accountId, String keyword);

}
