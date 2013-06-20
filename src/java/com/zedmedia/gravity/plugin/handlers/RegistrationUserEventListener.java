package com.zedmedia.gravity.plugin.handlers;

import java.io.IOException;
import java.util.Map;

import org.jivesoftware.openfire.event.UserEventListener;
import org.jivesoftware.openfire.user.User;

import com.zedmedia.gravity.plugin.utils.WebService;

public class RegistrationUserEventListener implements UserEventListener {
	// private static final Logger Log = LoggerFactory
	// .getLogger(GravityIQHandler.class);
	private WebService webClient = WebService.getInstance();

	@Override
	public void userCreated(User user, Map<String, Object> params) {
		// TODO Find first and last name
		try {
			webClient.register(user.getUsername(), user.getEmail(),
					user.getName(), user.getName());
		} catch (IOException e) {
			// TODO Stop XMPP registration
		}
	}

	@Override
	public void userDeleting(User arg0, Map<String, Object> arg1) {
	}

	@Override
	public void userModified(User arg0, Map<String, Object> arg1) {
	}

}
