package it.gamerover.collision;

import org.bukkit.entity.Player;

public class TeamUtils {

	
	
	public static void sendTeamPacket(Player player) throws Exception {
		
		Class<?> packetTeamClass = Reflections.getPacketTeamClass();
		Object packetTeamObject = packetTeamClass.newInstance();
		
		
		
		
	}
	
}
