package ru.melonhell.cyberpunkserver.cp77.protocol.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonAutoDetect
@Getter
@NoArgsConstructor
public class ClientPacket {
    private String packetType;
}
