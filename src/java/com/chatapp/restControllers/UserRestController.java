package com.chatapp.restControllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.chatapp.services.UserServiceInterface;
import com.chatapp.services.impl.ChatService;
import com.chatapp.services.impl.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import dal.InformationOfUserDAO;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.InformationOfUser;

@WebServlet("/users-rest-controller")
public class UserRestController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserServiceInterface userServiceInterface = UserService.getInstance();
    private ChatService chatService = ChatService.getInstance();
    private InformationOfUserDAO iud = new InformationOfUserDAO();

    public UserRestController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();

        String receiverId_string = request.getParameter("receiverId");

        // muốn lấy thông tin của user
        if (receiverId_string != null) {
            int receiverId = Integer.parseInt(receiverId_string);

            InformationOfUser inforUser = iud.getInformationByAccountID(receiverId);
            String fullName = inforUser.getFullName();

            response.setContentType("application/text");
            response.setCharacterEncoding("UTF-8");
            out.print(fullName);
            out.flush();
        } 
        // muốn tất cả bạn bè
        else {
            Account accountLogged = (Account) session.getAttribute("account");
            int accountId = accountLogged.getAccountID();

            String keyWord = request.getParameter("keyword");

            // = null
            String conversationId = request.getParameter("conversationId");
            List<InformationOfUser> listUsers = null;
            if (conversationId != null && !conversationId.isEmpty()) {
                Long id = Long.parseLong(conversationId);
//            listUsers = userServiceInterface.getFriendsNotInConversation(userName, keyWord, id);
            } else if (keyWord.isEmpty()) {
                listUsers = userServiceInterface.findFriends(accountId);
            } else {
                listUsers = userServiceInterface.findFriendsByKeyWord(accountId, keyWord);
            }

            // set online status cho user
            for (InformationOfUser user : listUsers) {
                user.setIsOnline(chatService.isUserOnline(user.getAccountID()));
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(listUsers);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter printWriter = response.getWriter();
            printWriter.print(json);
            printWriter.flush();
        }
    }
}
