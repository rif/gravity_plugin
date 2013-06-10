package com.zedmedia.gravity.plugin;

import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Packet;

public class GravityPacketInterceptor implements PacketInterceptor {
	private static final Logger Log = LoggerFactory.getLogger(GravityPacketInterceptor.class);
	@Override
	public void interceptPacket(Packet packet, Session session,
			boolean incomming, boolean processed)
			throws PacketRejectedException {
		Log.info("Packet Filter loaded...");
		
		System.out.println("Interceptor: " + packet);

	}

}
