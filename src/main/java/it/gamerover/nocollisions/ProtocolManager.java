package it.gamerover.nocollisions;

import org.bukkit.entity.Player;
import com.comphenix.tinyprotocol.TinyProtocol;

import io.netty.channel.Channel;

public class ProtocolManager extends TinyProtocol {
    private NoCollisions plugin;

    public ProtocolManager(NoCollisions plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public Object onPacketOutAsync(Player reciever, Channel channel, Object packet) {
        if (packet.getClass() != TeamPacket.PACKET_SCOREBOARD_TEAM) {
            return packet;
        }
        TeamPacket.disableCollisions(packet);
        plugin.addToBlacklist(reciever.getName());
        return packet;
    }
}
