package ru.melonhell.cyberpunkserver.cp77.protocol.server;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonAutoDetect
@Getter
@RequiredArgsConstructor
public class ServerPlayerJoinPacket extends ServerPacket {
    private final String packetType = "ServerPlayerJoinPacket";
    private final int id;
}
