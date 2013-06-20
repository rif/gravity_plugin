package com.zedmedia.gravity.plugin.handlers;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.handler.IQHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;

public class GravityIQHandler extends IQHandler {
	private static final Logger Log = LoggerFactory
			.getLogger(GravityIQHandler.class);
	private static final String NAME = "gravity";
	private static final String NAMESPACE = "custom:iq:gravity";
	private IQHandlerInfo info;

	public GravityIQHandler() {
		super("Gravity IQ Handler");
		info = new IQHandlerInfo(NAME, NAMESPACE);
	}

	@Override
	public IQHandlerInfo getInfo() {
		return info;
	}

	@Override
	public IQ handleIQ(IQ packet) throws UnauthorizedException {
		IQ result = IQ.createResultIQ(packet);
		IQ.Type type = packet.getType();
		if (type.equals(IQ.Type.get)) {
			Element childResult = result.setChildElement(NAME, NAMESPACE);
			// Do stuff you according to get and return the query result by
			// adding to result

			childResult.setText("{\"credit\":21.0}");
			Log.info("TESTXXXXX get");

		} else if (type.equals(IQ.Type.set)) {
			result.setChildElement("query", NAMESPACE);
			// Do stuff you according to set and return the query result by
			// adding to result
			Log.info("TESTXXXXX set");
		} else {
			result.setChildElement(packet.getChildElement().createCopy());
			result.setError(PacketError.Condition.not_acceptable);
			Log.info("TESTXXXXX error");
		}
		return result;
	}

}