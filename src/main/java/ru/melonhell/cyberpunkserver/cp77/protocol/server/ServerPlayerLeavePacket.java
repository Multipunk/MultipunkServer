package ru.melonhell.cyberpunkserver.cp77.protocol.server;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonAutoDetect
@Getter
@RequiredArgsConstructor
public class ServerPlayerLeavePacket {
    private final String packetType = "ServerPlayerLeavePacket";
    private final int id;
}
