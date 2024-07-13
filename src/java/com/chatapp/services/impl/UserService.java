package com.chatapp.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import jakarta.servlet.http.Part;

import com.chatapp.daos.UserDaoInterface;
import com.chatapp.daos.impl.UserDao;
import com.chatapp.services.FileServiceAbstract;
import com.chatapp.services.UserServiceInterface;
import model.InformationOfUser;

public class UserService implements UserServiceInterface {

    private static UserService instance = null;

    private UserDaoInterface userDaoInterface = UserDao.getInstace();

    public synchronized static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private UserService() {
        File uploadDir = new File(FileServiceAbstract.rootLocation.toString());
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        System.out.println("Root path: " + uploadDir.getAbsolutePath());
    }

    // hiển thị tất cả friends khi chưa search
    @Override
    public List<InformationOfUser> findFriends(int accountId) {
        List<InformationOfUser> friends = userDaoInterface.findFriends(accountId);
        return friends;
    }

    // hiển thị tất cả friends khi đã search
    public List<InformationOfUser> findFriendsByKeyWord(int accountId, String keyword) {
        List<InformationOfUser> friends = userDaoInterface.findFriendsByKeyWord(accountId, keyword);
        return friends;
    }

}
