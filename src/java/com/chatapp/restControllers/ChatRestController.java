package com.chatapp.restControllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.chatapp.models.dtos.MessageDTO;
import com.chatapp.services.MessageServiceInterface;
import com.chatapp.services.impl.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/chat-rest-controller")
public class ChatRestController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private MessageServiceInterface messageServiceInterface = MessageService.getInstance();
    
    public ChatRestController() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int sender = Integer.parseInt(request.getParameter("sender"));
        int receiver = Integer.parseInt(request.getParameter("receiver"));
        List<MessageDTO> messages = messageServiceInterface.getAllMessagesBySenderAndReceiver(sender, receiver);
        
        ObjectMapper objectMapper = new ObjectMapper();
        // convert list thành chuỗi JSON
        String json = objectMapper.writeValueAsString(messages);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // gửi list message đã convert thành chuỗi JSON sang UI
        PrintWriter printWriter = response.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }
    
}
