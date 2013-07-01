package com.zedmedia.gravity.plugin.handlers;

import java.io.IOException;
import java.util.List;

import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.roster.Roster;
import org.jivesoftware.openfire.roster.RosterItem;
import org.jivesoftware.openfire.roster.RosterManager;
import org.jivesoftware.openfire.session.Session;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import org.xmpp.packet.PacketExtension;

import com.zedmedia.gravity.plugin.utils.GroupInfo;
import com.zedmedia.gravity.plugin.utils.WebService;

public class GravityPacketInterceptor implements PacketInterceptor {
	private static final String ELEMENT_NAME = "gravity";
	private static final String NAMESPACE = "gravity:expected_price";
	private static final Logger Log = LoggerFactory
			.getLogger(GravityPacketInterceptor.class);
	private RosterManager rosterManager = XMPPServer.getInstance()
			.getRosterManager();
	private WebService webClient = WebService.getInstance();

	@Override
	public void interceptPacket(Packet packet, Session session,
			boolean incomming, boolean processed)
			throws PacketRejectedException {
		if (incomming && !processed) {
			Log.info(packet.toXML());
		}
		if (incomming && !processed && packet instanceof Message) {
			Message m = (Message) packet;
			if (m.getType() != Message.Type.chat) {
				return; // not interested
			}
			Log.info("MESSAGE: " + m.toXML());
			PacketExtension ext = m.getExtension(ELEMENT_NAME, NAMESPACE);
			double expectedPrice = Double.parseDouble(ext.getElement()
					.getText());
			Roster roster;
			try {
				roster = rosterManager.getRoster(m.getTo().toBareJID());
				RosterItem rosterItem = roster.getRosterItem(m.getFrom());
				List<String> groups = rosterItem.getGroups();
				if (groups.size() > 0) {
					GroupInfo gi = new GroupInfo(groups.get(0));
					if (gi.getFee() == expectedPrice) {
						// create credit transaction
						String desc = "Message to "
								+ WebService.getUsernameFromJID(m.getTo());
						try {
							webClient.createTransaction(
									WebService.getUsernameFromJID(m.getFrom()),
									desc, WebService.TRANSACTION_CLASS_SPENDING,
									gi.getFee());
						} catch (IOException e) {							
							e.printStackTrace();
						}
						return;
					} else {
						// park message and send warning
					}
				} else {
					// the from user is not placed in any group
				}
			} catch (UserNotFoundException e) {
				// user roster was not found or the from user is not in the
				// roster
			}
		}
	}

}
