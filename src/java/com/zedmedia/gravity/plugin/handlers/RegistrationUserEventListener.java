package com.zedmedia.gravity.plugin.handlers;

import java.util.Map;

import org.jivesoftware.openfire.event.UserEventListener;
import org.jivesoftware.openfire.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrationUserEventListener implements UserEventListener {
	private static final Logger Log = LoggerFactory
			.getLogger(GravityIQHandler.class);
	@Override
	public void userCreated(User user, Map<String, Object> params) {
		Log.info("Registered: " + user + " params: " + params);
		System.out.println("Registered: " + user + " params: " + params);
	}

	@Override
	public void userDeleting(User arg0, Map<String, Object> arg1) {	
	}

	@Override
	public void userModified(User arg0, Map<String, Object> arg1) {
	}

}
