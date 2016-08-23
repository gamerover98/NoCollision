package it.gamerover.collision;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class MainCollision extends JavaPlugin {

	private static MainCollision instance;
	private static CollisionProtocol collisionProtocol = null;
	
	@Override
	public void onLoad() {
		instance = this;
	}

	@Override
	public void onEnable() {

		collisionProtocol = new CollisionProtocol(instance);
		getServer().getPluginManager().registerEvents(new PlayerListener(), instance);
	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(instance);
	}

	public static MainCollision getInstance() {
		return MainCollision.instance;
	}

	public static CollisionProtocol getCollisionProtocol() {
		return collisionProtocol;
	}

}
