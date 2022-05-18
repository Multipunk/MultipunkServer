package ru.melonhell.cyberpunkserver.cp77;

import io.netty.channel.Channel;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class Server {
    private final Map<Channel, Player> channelPlayerMap = new HashMap<>();
}
