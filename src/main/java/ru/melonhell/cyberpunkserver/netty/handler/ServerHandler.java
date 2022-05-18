package ru.melonhell.cyberpunkserver.netty.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.melonhell.cyberpunkserver.cp77.Location;
import ru.melonhell.cyberpunkserver.cp77.Player;
import ru.melonhell.cyberpunkserver.cp77.Server;
import ru.melonhell.cyberpunkserver.cp77.protocol.client.ClientPacket;
import ru.melonhell.cyberpunkserver.cp77.protocol.client.ClientPlayerMovePacket;
import ru.melonhell.cyberpunkserver.cp77.protocol.server.ServerPlayerJoinPacket;
import ru.melonhell.cyberpunkserver.cp77.protocol.server.ServerPlayerLeavePacket;
import ru.melonhell.cyberpunkserver.cp77.protocol.server.ServerPlayerMovePacket;

import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private final Server server;
    private final ObjectMapper mapper;

    public ServerHandler(Server server) {
        this.server = server;
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private static int generateId() {
        return (int) (Math.random() * 10000);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws JsonProcessingException {
        ctx.fireChannelActive();
        Player player = new Player(generateId(), new Location());
        Map<Channel, Player> channelPlayerMap = server.getChannelPlayerMap();
        channelPlayerMap.put(ctx.channel(), player);
        ServerPlayerJoinPacket serverPlayerJoinPacket = new ServerPlayerJoinPacket(player.getId());
        for (Channel channel : channelPlayerMap.keySet()) {
            if (!Objects.equals(channel, ctx.channel())) channel.writeAndFlush(mapper.writeValueAsString(serverPlayerJoinPacket) + "\0");
        }
        log.info(player.getId() + " connected. addr: " + ctx.channel().remoteAddress());
//        String remoteAddress = ctx.channel().remoteAddress().toString();
//        ctx.writeAndFlush("Your remote address is " + remoteAddress + ".\r\n");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws JsonProcessingException {
        String stringMessage = (String) msg;
        Map<Channel, Player> channelPlayerMap = server.getChannelPlayerMap();
        Player player = channelPlayerMap.get(ctx.channel());
        log.info(player.getId() + " > " + stringMessage);
        ClientPacket clientPacket = mapper.readValue(stringMessage, ClientPacket.class);
        if (clientPacket.getPacketType().equals("ClientPlayerMovePacket")) {
            ClientPlayerMovePacket clientPlayerMovePacket = mapper.readValue(stringMessage, ClientPlayerMovePacket.class);
            ServerPlayerMovePacket serverPlayerMovePacket = new ServerPlayerMovePacket(player.getId(), clientPlayerMovePacket.getX(), clientPlayerMovePacket.getY(), clientPlayerMovePacket.getZ(), clientPlayerMovePacket.getPitch(), clientPlayerMovePacket.getYaw());
            for (Channel channel : channelPlayerMap.keySet()) {
                if (!Objects.equals(channel, ctx.channel())) channel.writeAndFlush(mapper.writeValueAsString(serverPlayerMovePacket) + "\0");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws JsonProcessingException {
        Map<Channel, Player> channelPlayerMap = server.getChannelPlayerMap();
        Player player = channelPlayerMap.get(ctx.channel());
        channelPlayerMap.remove(ctx.channel());
        ServerPlayerLeavePacket serverPlayerLeavePacket = new ServerPlayerLeavePacket(player.getId());
        for (Channel channel : channelPlayerMap.keySet()) {
            channel.writeAndFlush(mapper.writeValueAsString(serverPlayerLeavePacket) + "\0");
        }
        log.info(player.getId() + " disconnected");
    }
}
