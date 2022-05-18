package ru.melonhell.cyberpunkserver.cp77.protocol.server;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonAutoDetect
@Getter
@RequiredArgsConstructor
public class ServerPlayerMovePacket {
    private final String packetType = "ServerPlayerMovePacket";
    private final int id;
    private final double x;
    private final double y;
    private final double z;
    private final double pitch;
    private final double yaw;
}
