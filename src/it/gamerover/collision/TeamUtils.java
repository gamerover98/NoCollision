package it.gamerover.collision;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.entity.Player;

public class TeamUtils {

	private static Class<?> packetTeamClass = null;
	private static Field nameField = null;
	private static Field modeField = null;
	private static Field collisionRuleField = null;
	private static Field playersField = null;
	
	static {
		
		try {
			
			packetTeamClass = Reflections.getPacketTeamClass();
			
			nameField = Reflections.getField(packetTeamClass, "a");
			modeField = Reflections.getField(packetTeamClass, "i");
			collisionRuleField = Reflections.getField(packetTeamClass, "f");
			playersField = Reflections.getField(packetTeamClass, "h");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public synchronized static void sendTeamPacket(Player player) {
		
		try {
			
			Object packetTeamObject = packetTeamClass.newInstance();
			
			nameField.set(packetTeamObject, UUID.randomUUID().toString().substring(0, 15));
			modeField.set(packetTeamObject, 0);
			collisionRuleField.set(packetTeamObject, "never");
			playersField.set(packetTeamObject, Arrays.asList(player.getName()));
			
			Reflections.sendPacket(player, packetTeamObject);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
}
