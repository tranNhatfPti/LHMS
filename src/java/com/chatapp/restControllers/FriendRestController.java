package com.chatapp.restControllers;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.chatapp.daos.impl.FriendDao;
import com.chatapp.models.Friend;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/friend-rest-controller")
public class FriendRestController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public FriendRestController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int sender = Integer.parseInt(request.getParameter("sender"));
        int receiver = Integer.parseInt(request.getParameter("receiver"));

        Friend friend = new FriendDao().findFriend(sender, receiver);
        
        if (friend == null) {
            friend = new Friend(0, 0, 0, false);
        }
        
        // convert thành chuỗi JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(friend);
        System.out.println(json);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter printWriter = response.getWriter();
        printWriter.print(json);
        printWriter.flush();

    }
}
