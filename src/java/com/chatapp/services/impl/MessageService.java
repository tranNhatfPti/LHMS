package com.chatapp.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.chatapp.daos.MessageDaoInterface;
import com.chatapp.daos.impl.MessageDao;
import com.chatapp.models.Message;
import com.chatapp.models.dtos.MessageDTO;
import com.chatapp.services.FileServiceAbstract;
import com.chatapp.services.MessageServiceInterface;

public class MessageService implements MessageServiceInterface {

    private static MessageService instance = null;

    private MessageDaoInterface messageDaoInterface = MessageDao.getInstance();

    public synchronized static MessageService getInstance() {
        if (instance == null) {
            instance = new MessageService();
        }
        return instance;
    }

    public Message convertToEntity(MessageDTO messageDTO) {
        int accountId = messageDTO.getAccountId();
        String message = messageDTO.getMessage();
        String type = messageDTO.getType();
        int receiver = messageDTO.getReceiver();
        Message messageEntity = new Message(accountId, message, type, receiver);
        return messageEntity;
    }

    private MessageDTO convertToDTO(Message messageEntity) {
        int accountId = messageEntity.getAccountId();
        String type = messageEntity.getType();
        String message = messageEntity.getMessage();
        if (!type.equals("text")) {
            message = FileServiceAbstract.toTagHtml(type, accountId, message);
        }
        int receiver = messageEntity.getReceiver();
        MessageDTO messageDTO = new MessageDTO(accountId, message, type, receiver);
        return messageDTO;
    }

    @Override
    public List<MessageDTO> getAllMessagesBySenderAndReceiver(int sender, int receiver) {
        List<Message> listMessages = messageDaoInterface.findAllMessagesBySenderAndReceiver(sender, receiver);
        List<MessageDTO> listMessageDTOs = new ArrayList<MessageDTO>();
        listMessages.stream().forEach(msg -> {
            MessageDTO messageDTO = convertToDTO(msg);
            listMessageDTOs.add(messageDTO);
        });
        return listMessageDTOs;
    }

    @Override
    public void saveMessage(MessageDTO messageDTO) {
        Message messageEntity = convertToEntity(messageDTO);
        messageDaoInterface.saveMessage(messageEntity);
    }

}
