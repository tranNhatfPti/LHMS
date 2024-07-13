package com.chatapp.controllers;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.chatapp.services.FileServiceAbstract;
import com.chatapp.services.UserServiceInterface;
import com.chatapp.services.impl.UserService;
import model.InformationOfUser;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserServiceInterface userService = UserService.getInstance();

    public LoginController() {
        super();
    }

    // từ filter sang
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (FileServiceAbstract.rootURL.isEmpty() || FileServiceAbstract.rootURL.contains("localhost")) {
            String url = request.getRequestURL().toString();
            FileServiceAbstract.rootURL = url.replaceAll("login", "files/");
            System.out.println(FileServiceAbstract.rootURL);
        }
        InformationOfUser user = (InformationOfUser) request.getSession().getAttribute("user");
        if (user != null) {
            response.sendRedirect("/chat");
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
            rd.forward(request, response);
        }
    }

    // từ login.jsp sang, thay thành LoginServlet
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        InformationOfUser user = userService.findUser(username, password);
//        String destPage = "/ManageLodgingHouse/login";
//        if (user != null) {
//            HttpSession httpSession = request.getSession();
//            httpSession.setAttribute("user", user);
//            destPage = "/ManageLodgingHouse/chat";
//        }
//        response.sendRedirect(destPage);
//    }

}
