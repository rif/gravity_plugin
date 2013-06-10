package com.zedmedia.gravity.plugin;

import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.handler.IQHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.IQ;

public class GravityIQHandler extends IQHandler {
	private static final Logger Log = LoggerFactory
			.getLogger(GravityIQHandler.class);

	public GravityIQHandler() {
		super("gravity");
	}

	@Override
	public IQHandlerInfo getInfo() {
		Log.info("iq handler get info log");
		System.out.println("iq handler get info print");
		return null;
	}

	@Override
	public IQ handleIQ(IQ arg0) throws UnauthorizedException {
		// TODO Auto-generated method stub
		return null;
	}

}
