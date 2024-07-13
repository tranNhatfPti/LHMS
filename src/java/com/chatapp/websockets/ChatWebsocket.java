package com.chatapp.websockets;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import com.chatapp.models.MessageDecoder;
import com.chatapp.models.MessageEncoder;
import com.chatapp.models.dtos.FileDTO;
import com.chatapp.models.dtos.MessageDTO;
import com.chatapp.services.ChatServiceAbstract;
import com.chatapp.services.MessageServiceInterface;
import com.chatapp.services.impl.ChatService;
import com.chatapp.services.impl.MessageService;

// gửi đến server với param là accountId
// MessageDecoder: chuyển dổi JSON message thành MessageDTO
@ServerEndpoint(value = "/chat/{accountId}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ChatWebsocket {

    private Session session;
    private int accountId;
    private Queue<FileDTO> fileDTOs = new LinkedList<>();

    private ChatServiceAbstract chatService = ChatService.getInstance();
    private MessageServiceInterface messageService = MessageService.getInstance();

    // khi user kết nối lần đầu đến server
    // khi server run, file này luôn luôn chạy và hàm onOpen() sẵn sàng chờ các kết nối từ user đến
    @OnOpen
    public void onOpen(@PathParam("accountId") int accountId, Session session) {
        if (chatService.register(this)) {
            this.session = session;
            this.accountId = accountId;
            int receiver = 0;
            MessageDTO msgResponse = new MessageDTO(this.accountId, "[P]open", "text", receiver);
            chatService.sendMessageToAllUsers(msgResponse);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {

    }

    // message: là JSON được gửi về websocket.send(JSON.stringify(message))
    // các giá trị trong các trường của JSON message sẽ ứng với các @JsonProperty đã tạo trong MessageDTO
    @OnMessage
    public void onMessage(MessageDTO message, Session session) {
        System.out.println(message.getAccountId() + " " 
                + message.getMessage() + " " 
                + message.getType() + " " 
                + message.getReceiver());
        chatService.sendMessageToOneUser(message, fileDTOs);
        messageService.saveMessage(message);
    }

    @OnMessage
    public void processUploading(ByteBuffer byteBuffer, boolean last, Session session) {
        System.err.println(byteBuffer.array().length);
        chatService.handleFileUpload(byteBuffer, last, fileDTOs);
    }

    @OnClose
    public void onClose(Session session) {
        if (chatService.close(this)) {
            int receiver = 0;
            MessageDTO msgResponse = new MessageDTO(this.accountId, "[P]close", "text", receiver);
            chatService.sendMessageToAllUsers(msgResponse);
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public int getAccountId() {
        return accountId;
    }
  
}
