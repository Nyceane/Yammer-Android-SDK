package com.example.yammerapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class YammerAPI {
	public static final String LOGIN_URL = "https://www.yammer.com/dialog/oauth?client_id=%s";
	public static final String LOGIN_OAUTH = "https://www.yammer.com/oauth2/access_token.json?client_id=%s&client_secret=%s&code=%s";
	public static final String CLIENT_ID = "[your client id]";
	public static final String CLIENT_SECRET = "[your client secret]";
	
	
	public static boolean isLoggedIn(Context context)
	{
		return !YammerPreference.getYammerToken(context).isEmpty();
	}
	
	public static JSONArray getMessage(Context context)
	{
		String url = "https://www.yammer.com/api/v1/messages.json";
		return WebServices.SendHttpGetArray(url, YammerPreference.getYammerToken(context));
	}
	
	public static void postMessage(Context context, String body)
	{
		String url = "https://www.yammer.com/api/v1/messages.json";
		JSONObject json = new JSONObject();
		try {
			json.put("body", body);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WebServices.SendHttpPost(url, json, YammerPreference.getYammerToken(context));
	}
	
	public static void postPrivateMessage(Context context, String body, int toId)
	{
		String url = "https://www.yammer.com/api/v1/messages.json";
		JSONObject json = new JSONObject();
		try {
			json.put("body", body);
			json.put("direct_to_id", toId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WebServices.SendHttpPost(url, json, YammerPreference.getYammerToken(context));
	}
	
	public static JSONArray getUsers(Context context)
	{
		String url = "https://www.yammer.com/api/v1/users.json";
		
		return WebServices.SendHttpGetArray(url, YammerPreference.getYammerToken(context));
	}
}
