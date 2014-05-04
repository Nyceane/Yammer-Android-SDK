package com.example.yammerapi;

import android.content.Context;
import android.content.SharedPreferences;

public class YammerPreference {
	public static String YAMMERTOKEN = "yammer.token";

	public static void CachingToken(Context context, String token)
	{
    	SharedPreferences prefs = context.getSharedPreferences(YAMMERTOKEN, Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
    	editor.putString(YAMMERTOKEN, token);
    	editor.commit();
	}

	public static String getYammerToken(Context context)
	{
    	SharedPreferences prefs = context.getSharedPreferences(YAMMERTOKEN, Context.MODE_PRIVATE);
    	return prefs.getString(YAMMERTOKEN, "");
	}
}
