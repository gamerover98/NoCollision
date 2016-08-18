package it.gamerover.collision;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import it.gamerover.collision.utils.PacketInjector;

public class PlayerListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		PacketInjector.addPlayer(player);
		TeamUtils.sendTeamPacket(player);
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		
		Player player = event.getPlayer();
		PacketInjector.removePlayer(player);
		
	}
	
}
