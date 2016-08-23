package it.gamerover.nocollisions;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class NoCollisions extends JavaPlugin {
	private ProtocolManager protocolManager;
	private Set<String> blacklist;

	@Override
	public void onEnable() {
		blacklist = new HashSet<>();
		// Register listeners and protocol manager
        protocolManager = new ProtocolManager(this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
	}

    public boolean isInBlacklist(String name) {
        return blacklist.contains(name);
    }

	public void addToBlacklist(String name) {
	    blacklist.add(name);
	}

    public void removeFromBlacklist(String name) {
        blacklist.remove(name);
    }

    public void sendNoCollisionPacket(Player player) {
        String playername = player.getName();
        if(isInBlacklist(playername)) {
            return;
        }
        protocolManager.sendPacket(player, TeamPacket.generateNoCollisionsPacket(playername));
    }
}
