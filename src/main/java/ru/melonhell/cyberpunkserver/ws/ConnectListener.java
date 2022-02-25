package ru.melonhell.cyberpunkserver.ws;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.sockjs.transport.session.WebSocketServerSockJsSession;

public interface ConnectListener {
    void handle(WebSocketSession handler);
}
