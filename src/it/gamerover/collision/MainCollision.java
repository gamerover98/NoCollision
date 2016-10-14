package it.gamerover.collision;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import it.gamerover.collision.tinyprotocol.TinyProtocol;

public class MainCollision extends JavaPlugin {

	private static MainCollision instance;
	private static TinyProtocol tinyProtocol = null;
	
	@Override
	public void onLoad() {
		instance = this;
	}

	@Override
	public void onEnable() {

		tinyProtocol = new TinyProtocol(instance){};
		getServer().getPluginManager().registerEvents(new PlayerListener(), instance);
	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(instance);
	}

	public static MainCollision getInstance() {
		return MainCollision.instance;
	}

	public static TinyProtocol getTinyProtocol() {
		return tinyProtocol;
	}
	
}
