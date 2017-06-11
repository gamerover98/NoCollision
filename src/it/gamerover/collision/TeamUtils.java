package it.gamerover.collision;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.entity.Player;

import lombok.Getter;

public class TeamUtils {

	@Getter private static ArrayList<Player> securePlayers = new ArrayList<Player>();
	
	private static Class<?> packetTeamClass = Reflection.getMinecraftClass("PacketPlayOutScoreboardTeam");
	private static Field nameField = null;
	private static Field modeField = null;
	private static Field collisionRuleField = null;
	private static Field playersField = null;

	static {

		try {

			nameField = Reflection.getField(packetTeamClass, "a");
			modeField = Reflection.getField(packetTeamClass, "i");
			collisionRuleField = Reflection.getField(packetTeamClass, "f");
			playersField = Reflection.getField(packetTeamClass, "h");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public synchronized static void sendTeamPacket(Player player) {

		try {

			Object packetTeamObject = packetTeamClass.newInstance();

			nameField.set(packetTeamObject, UUID.randomUUID().toString().substring(0, 15));
			modeField.set(packetTeamObject, 0);
			playersField.set(packetTeamObject, Arrays.asList(player.getName()));

			changePacketCollisionType(packetTeamObject);
			
			if (!getSecurePlayers().contains(player)) {
				MainCollision.getTinyProtocol().sendPacket(player, packetTeamObject);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void changePacketCollisionType(Object packetTeamObject) throws Exception {
		collisionRuleField.set(packetTeamObject, "never");
	}

	public static Class<?> getPacketTeamClass() {
		return TeamUtils.packetTeamClass;
	}

}
