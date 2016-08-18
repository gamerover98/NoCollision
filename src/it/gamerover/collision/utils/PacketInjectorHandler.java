package it.gamerover.collision.utils;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import it.gamerover.collision.TeamUtils;

public class PacketInjectorHandler extends ChannelDuplexHandler {

	/**
	 * Change collisionRule in output going team packet
	 */
	@Override
	public void write(ChannelHandlerContext c, Object packet, ChannelPromise promise) throws Exception {
		
		if (packet.getClass() == TeamUtils.getPacketTeamClass()) {
			TeamUtils.changePacketCollisionType(packet);
		}
		
		super.write(c, packet, promise);
	}

	@Override
	public void channelRead(ChannelHandlerContext c, Object m) throws Exception {
		super.channelRead(c, m);
	}

}
