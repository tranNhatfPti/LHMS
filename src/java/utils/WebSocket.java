package utils;

import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(value = "/socket", configurator = CustomConfigurator.class)
public class WebSocket {
    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        String idAccount = (String) config.getUserProperties().get("idAccount");
        session.getUserProperties().put("id", idAccount);
        clients.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        clients.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        synchronized (clients) {
            for (Session client : clients) {
                if (!client.equals(session)) {
                    client.getBasicRemote().sendText(message);
                }
            }
        }
    }

    public static void broadcast(int idtenant, String roomId, String monthYear) throws IOException {
        synchronized (clients) {
            for (Session client : clients) {
                int id = Integer.parseInt((String) client.getUserProperties().get("id"));
                if(id == idtenant){
                    String notice = "notice&" + roomId + "&" + monthYear; 
                    client.getBasicRemote().sendText(notice);
                }           
            }
        }
    }
}
