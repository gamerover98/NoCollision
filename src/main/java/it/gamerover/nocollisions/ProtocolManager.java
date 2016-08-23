package it.gamerover.nocollisions;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comphenix.tinyprotocol.TinyProtocol;

public class ProtocolManager extends TinyProtocol {
    private NoCollisions plugin;

    public ProtocolManager(NoCollisions plugin) {
        this.plugin = plugin;
        super(plugin);
    }

    @Override
    public Object onPacketOutAsync(Player reciever, Channel channel, Object packet) {
        if (packet.getClass() != TeamPacket.PACKET_SCOREBOARD_TEAM) {
            return packet;
        }
        TeamPacket.disableCollisions(packet);
        plugin.addToBlacklist(reveiver.getName());
        return packet;
    }
}
