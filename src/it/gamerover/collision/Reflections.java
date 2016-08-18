package it.gamerover.collision;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

public class Reflections {

	private static String minecraftPackage = "net.minecraft.server.";
	private static String OBCPackage = MainCollision.getInstance().getServer().getClass().getPackage().getName();
	
	private static Class<?> craftEntityClass = null;
	private static Field craftEntityHandleField = null;
	private static Field playerConnectionField = null;
	
	static {

		try {
			
			minecraftPackage += MainCollision.getInstance().getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
			
			craftEntityClass = getCraftBukkitClass("entity.CraftEntity");
			craftEntityHandleField = getField(craftEntityClass, "entity");
			
			playerConnectionField = getField(getNMSClass("EntityPlayer"), "playerConnection");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}	

	public static <T> Method getNoParmsMethod(Class<T> searchClass, String name) throws Exception {
		
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
	
	public static <T> Method getMethod(Class<T> searchClass, String name, Class<?>... parms) throws Exception {

		Method findMethod = null;

		try {
			findMethod = searchClass.getMethod(name, parms);	
		} catch (Exception ex) {

			findMethod = searchClass.getDeclaredMethod(name, parms);
			findMethod.setAccessible(true);

		}
		return findMethod;


	}
		
	public static <T> Field getField(Class<T> searchClass, String name) throws Exception {

		Field findField = null;

		try {
			findField = searchClass.getField(name);
		} catch (Exception ex) {

			findField = searchClass.getDeclaredField(name);
			findField.setAccessible(true);

		}
		return findField;


	}

	public static Class<?> getCraftBukkitClass(String name) throws Exception {
		return Class.forName(OBCPackage + "." + name);
	}
	
	public static Class<?> getNMSClass(String name) throws Exception {
		return Class.forName(minecraftPackage + "." + name);
	}

	/**
	 * @return PacketPlayOutScoreboardTeam.class from net.minecraft.server.*
	 */
	public static Class<?> getPacketTeamClass() throws Exception {
		return getNMSClass("PacketPlayOutScoreboardTeam");
	}

	/**
	 * This method send packet to player
	 */
	public synchronized static void sendPacket(Player player, Object packetObject) throws Exception {
		
		Object entityPlayer = craftEntityHandleField.get(player);
		Object connection = playerConnectionField.get(entityPlayer);
		Method sendPacketMethod = getNoParmsMethod(connection.getClass(), "sendPacket");
		
		sendPacketMethod.invoke(connection, packetObject);
	}
	
}
