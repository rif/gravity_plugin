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

public class WebService {
	private HttpClient httpclient = null;
	private static WebService instance = null;

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
				"http://dev.seeme.com:6018/credit/api/get_credit/?u=" + userName);
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

	public String createTransaction(String userName) throws IOException {
		HttpPost httpPost = new HttpPost(
				"http://dev.seeme.com:6018/credit/api/create_transaction/");
		List<NameValuePair> nvps1 = new ArrayList<NameValuePair>();
		nvps1.add(new BasicNameValuePair("amount", "3"));
		nvps1.add(new BasicNameValuePair("description", "from java"));
		nvps1.add(new BasicNameValuePair("transaction_class", "award"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps1));
		HttpResponse response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity);
	}
}
