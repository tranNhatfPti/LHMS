package com.chatapp.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.websocket.EncodeException;

import com.chatapp.daos.UserDaoInterface;
import com.chatapp.daos.impl.UserDao;
import com.chatapp.models.dtos.FileDTO;
import com.chatapp.models.dtos.MessageDTO;
import com.chatapp.services.ChatServiceAbstract;
import com.chatapp.services.FileServiceAbstract;
import com.chatapp.websockets.ChatWebsocket;

public class ChatService extends ChatServiceAbstract {

    private static ChatService chatService = null;

    private UserDaoInterface userDaoInterface = UserDao.getInstace();

    private ChatService() {
    }

    public synchronized static ChatService getInstance() {
        if (chatService == null) {
            chatService = new ChatService();
        }
        return chatService;
    }

    // khi user online thì user sẽ kết nối tới server socket và hàm này để lưu các user này
    @Override
    public boolean register(ChatWebsocket chatWebsocket) {
        return chatWebsockets.add(chatWebsocket);
    }

    // khi user offlin sẽ ngắt kết nối tới server và hàm này sẽ xoá socket của user này
    @Override
    public boolean close(ChatWebsocket chatWebsocket) {
        return chatWebsockets.remove(chatWebsocket);
    }

    // kiểm tra xem user có online hay không(hiển thị màu đèn)
    @Override
    public boolean isUserOnline(int accountId) {
        // nếu có user này trong list đang online(đang kết nối tới server) thì return true
        for (ChatWebsocket chatWebsocket : chatWebsockets) {
            if (chatWebsocket.getAccountId() == accountId) {
                return true;
            }
        }
        return false;
    }

    // gửi message tất cả user
    @Override
    public void sendMessageToAllUsers(MessageDTO message) {
        // trong mỗi messageDTO đều có 1 list integer chứa id tất cả user đang online(đang kết nối nới socket server)
        message.setOnlineList(getUsernames());

        // lọc lần lượt tất cả user đang online
        chatWebsockets.stream().forEach(chatWebsocket -> {
            try {
                // gửi object sang màn hình của user đó
                chatWebsocket.getSession().getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    // gửi message tới 1 user
    @Override
    public void sendMessageToOneUser(MessageDTO message, Queue<FileDTO> fileDTOs) {
        if (!message.getType().equals("text")) {
            // destFile: archive/hagiangdz/Screenshot(427).png
            // url: http://localhost:9999/ChatTest/files/hagiangdz/Screenshot(427).png      
            
            // lấy ra tên file(được set từ bên JS)
            String fileName = message.getMessage();
            
            // thay thế toàn bộ space+ -> ""
            fileName = fileName.replaceAll("\\s+", "");
            
            // đường dẫn của file chứa trong folder archive trong server tomcat
            String destFile = FileServiceAbstract.rootLocation.toString() + "/" + message.getAccountId() + "/"
                    + fileName;
            
            File uploadedFile = new File(destFile);
            int sender = message.getAccountId();
            int receiver = message.getReceiver();
            
            // 
            String url = FileServiceAbstract.rootURL + sender + "/" + fileName;

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(uploadedFile, false);
                FileDTO newFileDTO = new FileDTO(fileName, message.getType(), fileOutputStream, sender, receiver, url);
                fileDTOs.add(newFileDTO);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        } else {
            // có id của người muốn gửi
            if (message.getReceiver() != 0) {
                chatWebsockets.stream()
                        // tìm ra user có accountId == id của người muốn gửi
                        .filter(chatWebsocket -> chatWebsocket.getAccountId() == message.getReceiver())
                        .forEach(chatWebsocket -> {
                            try {
                                chatWebsocket.getSession().getBasicRemote().sendObject(message);                    
                            } catch (IOException | EncodeException e) {
                                e.printStackTrace();
                                System.out.println(e.getMessage());
                            }
                        });
            } 
            // không có id người muốn gửi(gửi nhóm)
//            else {
//                List<User> usersGroup = userDaoInterface.findUsersByConversationId(message.getGroupId());
//
//                User sender = usersGroup.stream().filter(u -> u.getUsername().equals(message.getUsername()))
//                        .collect(Collectors.toList()).get(0);
//                message.setAvatar(sender.getAvatar());
//
//                Set<String> usernamesGroup = usersGroup.stream().map(User::getUsername).collect(Collectors.toSet());
//
//                chatWebsockets.stream()
//                        .filter(chatWebsocket -> usernamesGroup.contains(chatWebsocket.getUsername())
//                        && !chatWebsocket.getUsername().equals(message.getUsername()))
//                        .forEach(chatWebsocket -> {
//                            try {
//                                chatWebsocket.getSession().getBasicRemote().sendObject(message);
//                            } catch (IOException | EncodeException e) {
//                                e.printStackTrace();
//                            }
//                        });
//            }
        }
    }

    // xử lí file khi gửi lên
    @Override
    public void handleFileUpload(ByteBuffer byteBuffer, boolean last, Queue<FileDTO> fileDTOs) {
        try {
            if (!last) {
                while (byteBuffer.hasRemaining()) {
                    fileDTOs.peek().getFileOutputStream().write(byteBuffer.get());
                }
            } else {
                if (byteBuffer.array().length > 0) {
                    while (byteBuffer.hasRemaining()) {
                        fileDTOs.peek().getFileOutputStream().write(byteBuffer.get());
                    }
                }
                
                // lưu ảnh vào archive ?
                fileDTOs.peek().getFileOutputStream().flush();
                fileDTOs.peek().getFileOutputStream().close();
                
                System.out.println("Done: " + fileDTOs.peek().getFilename() + " of user: " + fileDTOs.peek().getSender());
                
                String message = "<img src=\"" + fileDTOs.peek().getUrl() + "\" alt=\"\">";
                String typeFile = fileDTOs.peek().getTypeFile();
                if (typeFile.startsWith("audio")) {
                    message = "<audio controls>\r\n" + "  <source src=\"" + fileDTOs.peek().getUrl() + "\" type=\""
                            + typeFile + "\">\r\n" + "</audio>";
                } else if (typeFile.startsWith("video")) {
                    message = "<video width=\"400\" controls>\r\n" + "  <source src=\"" + fileDTOs.peek().getUrl()
                            + "\" type=\"" + typeFile + "\">\r\n" + "</video>";
                } else if (typeFile.startsWith("image")) {
                    message = "<img src=\"" + fileDTOs.peek().getUrl() + "\" alt=\"\">";
                } else {
                    message = "<a href=" + fileDTOs.peek().getUrl() + ">" + fileDTOs.peek().getFilename() + "</a>";
                }
                
                String type = "text";
                int sender = fileDTOs.peek().getSender();
                int receiver = fileDTOs.peek().getReceiver();
                MessageDTO messageResponse = new MessageDTO(sender, message, type, receiver);
                fileDTOs.remove();
                sendMessageToOneUser(messageResponse, fileDTOs);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        ChatService c = new ChatService();
        MessageDTO m = new MessageDTO(6, "haha", "text", 8);
    }
}
