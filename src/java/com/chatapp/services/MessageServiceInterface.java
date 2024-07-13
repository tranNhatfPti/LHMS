package com.chatapp.services;

import java.util.List;

import com.chatapp.models.dtos.MessageDTO;

public interface MessageServiceInterface {
	public List<MessageDTO> getAllMessagesBySenderAndReceiver(int sender, int receiver);

	public void saveMessage(MessageDTO messageDTO);
}
