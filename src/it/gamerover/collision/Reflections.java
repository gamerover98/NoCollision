package it.gamerover.collision;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflections {

	private static String minecraftPackage = "net.minecraft.server.";

	static {

		minecraftPackage += MainCollision.getInstance().getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

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

	public static Class<?> getPacketTeamClass() throws Exception {
		return getNMSClass("PacketPlayOutScoreboardTeam");
	}
	
	public static Class<?> getNMSClass(String name) throws Exception {
		return Class.forName(minecraftPackage + "." + name);
	}

}
