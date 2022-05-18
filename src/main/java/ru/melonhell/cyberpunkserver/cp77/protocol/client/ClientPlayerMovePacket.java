package ru.melonhell.cyberpunkserver.cp77.protocol.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonAutoDetect
@Getter
@NoArgsConstructor
public class ClientPlayerMovePacket extends ClientPacket{
    private double x;
    private double y;
    private double z;
    private double pitch;
    private double yaw;
}
