package com.chatapp.models;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

import com.chatapp.models.dtos.MessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class MessageEncoder implements Encoder.Text<MessageDTO> {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void destroy() {
    }

    @Override
    public void init(final EndpointConfig arg0) {
    }

    @Override
    public String encode(final MessageDTO message) throws EncodeException {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new EncodeException(message, "Unable to encode message", e);
        }
    }
}
