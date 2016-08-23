package it.gamerover.collision;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import io.netty.channel.Channel;
import it.gamerover.collision.tinyprotocol.TinyProtocol;

public class CollisionProtocol extends TinyProtocol {

	public CollisionProtocol(Plugin plugin) {
		super(plugin);
	}

	@Override
	public Object onPacketOutAsync(Player reciever, Channel channel, Object packet) {

		try {

			if (packet.getClass() == PACKET_SCOREBOARD_TEAM) {
				
				TeamUtils.changePacketCollisionType(packet);
					
				if (!TeamUtils.getSecurePlayers().contains(reciever)) {
					TeamUtils.getSecurePlayers().add(reciever);
				}
				
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return packet;
	}
	
}
