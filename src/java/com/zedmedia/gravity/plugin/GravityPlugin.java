package com.zedmedia.gravity.plugin;

import java.io.File;

import org.jivesoftware.openfire.IQRouter;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.handler.IQHandler;
import org.jivesoftware.openfire.interceptor.InterceptorManager;
import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.util.JiveGlobals;

/**
 * Gravity (Message of the Day) plugin.
 * 
 * @author <a href="mailto:ryan@version2software.com">Ryan Graham</a>
 */
public class GravityPlugin implements Plugin {
	private static final String SUBJECT = "plugin.gravity.subject";
	private static final String MESSAGE = "plugin.gravity.message";
	private static final String ENABLED = "plugin." + ".enabled";

	private IQRouter iqRouter;
	private IQHandler gravityHandler;
	private InterceptorManager interceptorManager;
	private PacketInterceptor gravityInterceptor;

	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		gravityHandler = new GravityIQHandler();
		iqRouter = XMPPServer.getInstance().getIQRouter();
		iqRouter.addHandler(gravityHandler);
		interceptorManager = InterceptorManager.getInstance();
		gravityInterceptor = new GravityPacketInterceptor();
		interceptorManager.addInterceptor(gravityInterceptor);

	}

	public void destroyPlugin() {
		iqRouter.removeHandler(gravityHandler);
		gravityHandler = null;
		iqRouter = null;
		gravityInterceptor = null;
		interceptorManager = null;
	}

	public void setSubject(String message) {
		JiveGlobals.setProperty(SUBJECT, message);
	}

	public String getSubject() {
		return JiveGlobals.getProperty(SUBJECT, "Message of the Day");
	}

	public void setMessage(String message) {
		JiveGlobals.setProperty(MESSAGE, message);
	}

	public String getMessage() {
		return JiveGlobals.getProperty(MESSAGE, "Big Brother is watching.");
	}

	public void setEnabled(boolean enable) {
		JiveGlobals.setProperty(ENABLED, enable ? Boolean.toString(true)
				: Boolean.toString(false));
	}

	public boolean isEnabled() {
		return JiveGlobals.getBooleanProperty(ENABLED, false);
	}
}
