package it.gamerover.collision.utils;

import java.lang.reflect.Field;

import org.bukkit.entity.Player;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;

public class PacketInjector {

	private static Class<?> playerConnectionClass = Reflections.getPlayerConnectionClass();
	private static Class<?> networkManagerClass = null;
	
	private static Field playerConnectionField = Reflections.getPlayerConnectionField();
	private static Field networkManagerField = null;
	
	private static Field channelField = null;
	
	static {
		
		try {
			
			networkManagerClass = Reflections.getNMSClass("NetworkManager");
			networkManagerField = Reflections.getField(playerConnectionClass, "networkManager");
			channelField = Reflections.getField(networkManagerClass, "channel");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * Add packet_nocollision channel to player
	 */
	public static void addPlayer(Player player) {
		
		try {
			
			Channel channel = getChannel(getNetworkManager(Reflections.getNMSPlayer(player)));
			ChannelPipeline pipeline = channel.pipeline();
			
			if (pipeline.get("packet_nocollision") == null) {
				
				PacketInjectorHandler injector = new PacketInjectorHandler();
				pipeline.addBefore("packet_handler", "packet_nocollision", injector);
				
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * Remove packet_nocollision channel to player
	 */
	public static void removePlayer(Player player) {
		
		try {
			
			Channel channel = getChannel(getNetworkManager(Reflections.getNMSPlayer(player)));
			ChannelPipeline pipeline = channel.pipeline();
			
			if (pipeline.get("packet_nocollision") != null) {
				pipeline.remove("packet_nocollision");
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	private static Object getNetworkManager(Object handle) throws Exception {
		return Reflections.getFieldValue(networkManagerField, Reflections.getFieldValue(playerConnectionField, handle));
	}
	
	private static Channel getChannel(Object networkManagerInstance) throws Exception {
		return Reflections.getFieldValue(channelField, networkManagerInstance);
	}
	
}
