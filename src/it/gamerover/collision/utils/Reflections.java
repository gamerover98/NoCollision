package it.gamerover.collision.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import it.gamerover.collision.MainCollision;

public class Reflections {

	private static String minecraftPackage = "net.minecraft.server.";
	private static String OBCPackage = MainCollision.getInstance().getServer().getClass().getPackage().getName();
	
	private static Class<?> craftEntityClass = null;
	private static Class<?> playerConnectionClass = null;
	
	private static Field craftEntityHandleField = null;
	private static Field playerConnectionField = null;
	private static Method sendPacketMethod = null;
	
	static {

		try {
			
			minecraftPackage += MainCollision.getInstance().getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
			
			craftEntityClass = getCraftBukkitClass("entity.CraftEntity");
			craftEntityHandleField = getField(craftEntityClass, "entity");
			
			playerConnectionClass = getNMSClass("PlayerConnection");
			
			playerConnectionField = getField(getNMSClass("EntityPlayer"), "playerConnection");
			sendPacketMethod = getNoParmsMethod(playerConnectionClass, "sendPacket");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}	

	public static Method getNoParmsMethod(Class<?> searchClass, String name) throws Exception {
		
		for (Method method : searchClass.getMethods()) {
			
			if (method.getName().equals(name)) {
				return method;
			}
			
		}
		
		for (Method method : searchClass.getDeclaredMethods()) {
			
			if (method.getName().equals(name)) {
				
				method.setAccessible(true);
				return method;
				
			}
			
		}
		
		return null;
		
	}
	
	public static Method getMethod(Class<?> searchClass, String name, Class<?>... parms) throws Exception {

		Method findMethod = null;

		try {
			findMethod = searchClass.getMethod(name, parms);	
		} catch (Exception ex) {

			findMethod = searchClass.getDeclaredMethod(name, parms);
			findMethod.setAccessible(true);

		}
		return findMethod;


	}
		
	public static Field getField(Class<?> searchClass, String name) throws Exception {

		Field findField = null;

		try {
			findField = searchClass.getField(name);
		} catch (Exception ex) {

			findField = searchClass.getDeclaredField(name);
			findField.setAccessible(true);

		}
		return findField;


	}

	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Field field, Object instance) throws Exception {
		return (T) field.get(instance);
	}
	
	public static Class<?> getCraftBukkitClass(String name) throws Exception {
		return Class.forName(OBCPackage + "." + name);
	}
	
	public static Class<?> getNMSClass(String name) throws Exception {
		return Class.forName(minecraftPackage + "." + name);
	}

	/**
	 * @return Field playerConnection of EntityPlayer class
	 */
	public static Field getPlayerConnectionField() {
		return Reflections.playerConnectionField;
	}
	
	/**
	 * @return PlayerConnection class from net.minecraft.server.*
	 */
	public static Class<?> getPlayerConnectionClass() {
		return Reflections.playerConnectionClass;
	}
	
	/**
	 * @return PacketPlayOutScoreboardTeam class from net.minecraft.server.*
	 */
	public static Class<?> getPacketTeamClass() throws Exception {
		return getNMSClass("PacketPlayOutScoreboardTeam");
	}

	/**
	 * This method send packet to player
	 */
	public synchronized static void sendPacket(Player player, Object packetObject) throws Exception {
		
		Object connection = playerConnectionField.get(getNMSPlayer(player));
		
		sendPacketMethod.invoke(connection, packetObject);
	}

	public static Object getNMSPlayer(Player player) throws Exception {
		return craftEntityHandleField.get(player);
	}
	
}
