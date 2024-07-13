package com.chatapp.models;

import java.io.IOException;

import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

import com.chatapp.models.dtos.MessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class MessageDecoder implements Decoder.Text<MessageDTO> {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void destroy() {
    }

    @Override
    public void init(final EndpointConfig arg0) {
    }

    @Override
    public MessageDTO decode(final String arg0) throws DecodeException {
        try {
            return objectMapper.readValue(arg0, MessageDTO.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public boolean willDecode(final String arg0) {
        return arg0.contains("accountId") && arg0.contains("message");
    }
}
