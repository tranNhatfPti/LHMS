package com.chatapp.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.chatapp.daos.impl.FriendDao;
import com.chatapp.models.Friend;
import com.chatapp.services.UserServiceInterface;
import com.chatapp.services.impl.UserService;
import dal.InformationOfUserDAO;
import model.Account;
import model.InformationOfUser;

@WebServlet("/chat")
public class ChatController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserServiceInterface userService = UserService.getInstance();
    private FriendDao friendDao = new FriendDao();
    private InformationOfUserDAO iud = new InformationOfUserDAO();

    public ChatController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account currentUser = (Account) request.getSession().getAttribute("account");
        int accountId = currentUser.getAccountID();
        InformationOfUser userInfor = iud.getInformationByAccountID(accountId);

        // tìm ra những người đã kết bạn với user này
        List<InformationOfUser> friends = userService.findFriends(accountId);

        request.setAttribute("friends", friends);
        request.setAttribute("user", currentUser);
        request.setAttribute("tagMenu", "chat");
        request.setAttribute("userInfor", userInfor);

        if(currentUser.getRoleId() == 2){
            request.getRequestDispatcher("/view/manager/chat-box.jsp").forward(request, response);
        } else if(currentUser.getRoleId() == 4){
            request.getRequestDispatcher("/view/tenant/chat-box-tenant.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int sender = Integer.parseInt(request.getParameter("sender"));
        int receiver = Integer.parseInt(request.getParameter("receiver"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));
        boolean isAccept = Boolean.parseBoolean(request.getParameter("isAccept"));
        friendDao.saveFriend(isAccept, new Friend(sender, receiver, sender, status));

        response.sendRedirect("/ManageLodgingHouse/chat");
    }
}
