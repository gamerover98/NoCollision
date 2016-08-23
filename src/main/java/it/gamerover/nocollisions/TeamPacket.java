package it.gamerover.nocollisions;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.comphenix.tinyprotocol.Reflection;

public class TeamPacket {
    // Packet
    public static final Class<?> PACKET_SCOREBOARD_TEAM = Reflection.getMinecraftClass("PacketPlayOutScoreboardTeam");

	// Packet fields
	private static Field nameField = null;
	private static Field modeField = null;
	private static Field collisionRuleField = null;
	private static Field playersField = null;

	// Get fields
	static {
		try {
			nameField = ReflectionUtils.getField(PACKET_SCOREBOARD_TEAM, "a");
			modeField = ReflectionUtils.getField(PACKET_SCOREBOARD_TEAM, "i");
			collisionRuleField = ReflectionUtils.getField(PACKET_SCOREBOARD_TEAM, "f");
			playersField = ReflectionUtils.getField(PACKET_SCOREBOARD_TEAM, "h");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

    public static Object generateNoCollisionsPacket(String playername) {
        return generatePacket(UUID.randomUUID().toString(), 0, Arrays.asList(playername), "never");
    }

	public static Object generatePacket(String name, int mode, List<String> players, String collisionRule) {
	    Object teamPacket = null;
	    try {
            // Create packet instance
            teamPacket = PACKET_SCOREBOARD_TEAM.newInstance();
            // Set packet fields
            nameField.set(teamPacket, name.substring(0, 15));
            modeField.set(teamPacket, mode);
            playersField.set(teamPacket, players);
            collisionRuleField.set(teamPacket, collisionRule);
        } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
	    return teamPacket;
	}

	// Set the collision rule to "never" for an existing packet
    public static void disableCollisions(Object packet) {
        try {
            collisionRuleField.set(packet, "never");
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
