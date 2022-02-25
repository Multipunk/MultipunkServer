package ru.melonhell.cyberpunkserver;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.Array;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.melonhell.cyberpunkserver.ws.WebSocketHandler;

import java.io.IOException;
import java.net.Socket;

@RequiredArgsConstructor
@Component
public class GameLoop extends ApplicationAdapter {
    private final WebSocketHandler webSocketHandler;
    private final Array<String> events = new Array<>();

    @Override
    public void create() {
        webSocketHandler.setConnectListener(session -> {
            events.add(session.getId() + " joined");
        });
        webSocketHandler.setDisconnectListener(session -> {
            events.add(session.getId() + " left");
        });
        webSocketHandler.setMessageListener((session, message) -> {
            events.add(session.getId() + " > " + message);
        });
    }

    @Override
    public void render() {
        for (WebSocketSession webSocketSession : webSocketHandler.getWebSocketSessions()) {
            try {
                for (String event : events) {
                    webSocketSession.sendMessage(new TextMessage(event));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        events.clear();
    }
}
