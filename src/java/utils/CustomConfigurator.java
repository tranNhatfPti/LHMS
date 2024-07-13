package utils;

import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import java.util.List;
import java.util.Map;

public class CustomConfigurator extends ServerEndpointConfig.Configurator {
    
    /*
    Để lấy tham số idAccount từ URL khi thiết lập kết nối WebSocket, bạn cần sử dụng ServerEndpointConfig.Configurator 
    để truy cập các tham số URL trong quá trình bắt tay (handshake) của WebSocket
    */
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        Map<String, List<String>> params = request.getParameterMap();
        
        if (params.containsKey("idAccount")) {
            // Lấy giá trị đầu tiên của tham số idAccount
            config.getUserProperties().put("idAccount", params.get("idAccount").get(0));
        }
        
        if(params.containsKey("idtenant")) {
            config.getUserProperties().put("idtenant", params.get("idtenant").get(0));
        }
    }
}
