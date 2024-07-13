package com.chatapp.services;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.chatapp.models.dtos.FileDTO;
import com.chatapp.models.dtos.MessageDTO;
import com.chatapp.websockets.ChatWebsocket;

public abstract class ChatServiceAbstract {

    // lưu các socket của các user khi kết nối tới server
    protected static final Set<ChatWebsocket> chatWebsockets = new CopyOnWriteArraySet<>();

    public abstract boolean register(ChatWebsocket chatWebsocket);

    public abstract boolean close(ChatWebsocket chatWebsocket);

    public abstract void sendMessageToAllUsers(MessageDTO message);

    public abstract void sendMessageToOneUser(MessageDTO message, Queue<FileDTO> fileDTOs);

    public abstract void handleFileUpload(ByteBuffer byteBuffer, boolean last, Queue<FileDTO> fileDTOs);

    public abstract boolean isUserOnline(int accountId);

    // lấy ra tất cả user đang kết nối tới server(online users)
    protected Set<Integer> getUsernames() {
        Set<Integer> usernames = new HashSet<Integer>();

        // tất cả ChatWebsocket trong chatWebsockets là đang kết nối tới server
        chatWebsockets.forEach(chatWebsocket -> {
            // thêm tất cả username của các user đang online vào list
            usernames.add(chatWebsocket.getAccountId());
        });

        return usernames;
    }
}
