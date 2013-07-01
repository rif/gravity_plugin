package com.zedmedia.gravity.plugin.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.xmpp.packet.JID;

public class WebService {
	private HttpClient httpclient = null;
	private static WebService instance = null;

	// user purchased site credit (balance increase)
	public static final String TRANSACTION_CLASS_PURCASE = "purchase";
	// award - user is granted site credit (balance increase)		
	public static final String TRANSACTION_CLASS_AWARD = "award"; 
	// spending - user spent site credit (balance decrease)
	public static final String TRANSACTION_CLASS_SPENDING = "spending";
	// withdrawal - user withdrew site credit (balance decrease)
	public static final String TRANSACTION_CLASS_WITHDRAWAL = "withdrawal";
	// adjustment - adjustment of site credit (increase or decrease)
	public static final String TRANSACTION_CLASS_ADJUSTMENT = "adjustment";

	private WebService() {
		httpclient = new DefaultHttpClient();
	}

	public static WebService getInstance() {
		if (instance == null) {
			instance = new WebService();
		}
		return instance;
	}

	public static WebService release() {
		if (instance == null) {
			instance = new WebService();
		}
		return instance;
	}

	public String getCredit(String userName) throws IOException {
		HttpGet httpGet = new HttpGet(
				"http://dev.seeme.com:6018/credit/api/get_credit/?u="
						+ userName);
		HttpResponse response = httpclient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity);
	}

	public String register(String username, String email, String firstName,
			String lastName) throws IOException {
		HttpPost httpPost = new HttpPost(
				"http://dev.seeme.com:6018/credit/api/register_user/");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("email", email));
		nvps.add(new BasicNameValuePair("first_name", firstName));
		nvps.add(new BasicNameValuePair("last_name", lastName));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		HttpResponse response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity);
	}

	public String createTransaction(String userName, String description,
			String transactionClass, double amount) throws IOException {
		HttpPost httpPost = new HttpPost(
				"http://dev.seeme.com:6018/credit/api/create_transaction/?u="
						+ userName);
		List<NameValuePair> nvps1 = new ArrayList<NameValuePair>();
		nvps1.add(new BasicNameValuePair("amount", "" + amount));
		nvps1.add(new BasicNameValuePair("description", description));
		nvps1.add(new BasicNameValuePair("transaction_class", transactionClass));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps1));
		HttpResponse response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity);
	}
	
	public static String getUsernameFromJID(JID jid) {
		String bare = jid.toBareJID();
		return bare.substring(0, bare.lastIndexOf("@"));
	}
}
