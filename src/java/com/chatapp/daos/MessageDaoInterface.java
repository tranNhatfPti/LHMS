package com.chatapp.daos;

import java.util.List;

import com.chatapp.models.Message;

public interface MessageDaoInterface extends GenericDaoInterface<Message> {

    List<Message> findAllMessagesBySenderAndReceiver(int sender, int receiver);

    void saveMessage(Message message);

//    List<Message> findAllMessagesByConvesationId(Long id);
}
