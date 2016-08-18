package it.gamerover.collision;

import org.bukkit.plugin.java.JavaPlugin;

public class MainCollision extends JavaPlugin {

	private static MainCollision instance;
	
	@Override
	public void onLoad() {
		instance = this;
	}
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new PlayerListener(), instance);
	}

	public static MainCollision getInstance() {
		return MainCollision.instance;
	}
	
}
