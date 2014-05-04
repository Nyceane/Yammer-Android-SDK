package com.example.yammerapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

public class WebServices {
	//private static final String TAG = "HttpClient";

	public static JSONArray SendHttpGetArray(String URL, String token)
	{
		try {
			   HttpParams httpParameters = new BasicHttpParams();
			   // Set the timeout in milliseconds until a connection is established.
			   int timeoutConnection = 7000;
			   HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			   // Set the default socket timeout (SO_TIMEOUT) 
			   // in milliseconds which is the timeout for waiting for data.
			   int timeoutSocket = 7000;
			  HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			
		   DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
		   HttpGet httpPostRequest = new HttpGet(URL);
			
		   httpPostRequest.setHeader("Accept", "application/json");
		   httpPostRequest.setHeader("Content-type", "application/json");
		   if(!token.isEmpty())
		   {
			   httpPostRequest.setHeader("Authorization", "Bearer " + token);
		   }
		   //httpPostRequest.setHeader("Accept-Encoding", "gzip"); // only set this parameter if you would like to use gzip compression
			
		   //long t = System.currentTimeMillis();
		   HttpResponse response = (HttpResponse) httpclient.execute(httpPostRequest);
		   //Log.i(TAG, "HTTPResponse received in [" + (System.currentTimeMillis()-t) + "ms]");
			
		   // Get hold of the response entity (-> the data):
		   HttpEntity entity = response.getEntity();
		
		   if (entity != null) {
		   // Read the content stream
		   InputStream instream = entity.getContent();
		   Header contentEncoding = response.getFirstHeader("Content-Encoding");
		   if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
			   instream = new GZIPInputStream(instream);
		   }
			
		   // convert content stream to a String
		   String resultString= convertStreamToString(instream);
		   instream.close();			    
			
		   // Transform the String into a JSONObject
		   JSONArray jsonObjRecv = new JSONArray(resultString);
		   
		   return jsonObjRecv;
		   } 
		}
		catch (Exception e)
		{
		   // More about HTTP exception handling in another tutorial.
		   // For now we just print the stack trace.
		   e.printStackTrace();
		}
		catch (OutOfMemoryError e)
		{
			System.gc();
		}
		return null;
	}
	
	public static JSONArray SendHttpPostArray(String URL, JSONObject jsonObjSend, String token)
	{
		try {
			   HttpParams httpParameters = new BasicHttpParams();
			   // Set the timeout in milliseconds until a connection is established.
			   int timeoutConnection = 7000;
			   HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			   // Set the default socket timeout (SO_TIMEOUT) 
			   // in milliseconds which is the timeout for waiting for data.
			   int timeoutSocket = 7000;
			  HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			
		   DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
		   HttpPost httpPostRequest = new HttpPost(URL);
			
		   StringEntity se;
		   se = new StringEntity(jsonObjSend.toString(), "UTF-8");
			
		   // Set HTTP parameters
		   httpPostRequest.setEntity(se);
		   httpPostRequest.setHeader("Accept", "application/json");
		   httpPostRequest.setHeader("Content-type", "application/json");
		   if(!token.isEmpty())
		   {
			   httpPostRequest.setHeader("Authorization", "Bearer " + token);
		   }
		   //httpPostRequest.setHeader("Accept-Encoding", "gzip"); // only set this parameter if you would like to use gzip compression
			
		   //long t = System.currentTimeMillis();
		   HttpResponse response = (HttpResponse) httpclient.execute(httpPostRequest);
		   //Log.i(TAG, "HTTPResponse received in [" + (System.currentTimeMillis()-t) + "ms]");
			
		   // Get hold of the response entity (-> the data):
		   HttpEntity entity = response.getEntity();
		
		   if (entity != null) {
		   // Read the content stream
		   InputStream instream = entity.getContent();
		   Header contentEncoding = response.getFirstHeader("Content-Encoding");
		   if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
			   instream = new GZIPInputStream(instream);
		   }
			
		   // convert content stream to a String
		   String resultString= convertStreamToString(instream);
		   instream.close();			    
			
		   // Transform the String into a JSONObject
		   JSONArray jsonObjRecv = new JSONArray(resultString);
		   
		   return jsonObjRecv;
		   } 
		}
		catch (Exception e)
		{
		   // More about HTTP exception handling in another tutorial.
		   // For now we just print the stack trace.
		   e.printStackTrace();
		}
		catch (OutOfMemoryError e)
		{
			System.gc();
		}
		return null;
	}
	
	public static JSONObject SendHttpPost(String URL, JSONObject jsonObjSend, String token) {
		  try {		  
			   HttpParams httpParameters = new BasicHttpParams();
			   // Set the timeout in milliseconds until a connection is established.
			   int timeoutConnection = 7000;
			   HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			   // Set the default socket timeout (SO_TIMEOUT) 
			   // in milliseconds which is the timeout for waiting for data.
			   int timeoutSocket = 7000;
			  HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		   DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
		   
		   HttpPost httpPostRequest = new HttpPost(URL);
		
		   StringEntity se;
		   
		   
		   se = new StringEntity(jsonObjSend.toString(), "UTF-8");
		   // Set HTTP parameters
		   httpPostRequest.setEntity(se);
		   if(!token.isEmpty())
		   {
			   httpPostRequest.setHeader("Authorization", "Bearer " + token);
		   }
		   httpPostRequest.setHeader("Content-type", "application/json");
		   httpPostRequest.setHeader("Accept", "application/json");
		   //httpPostRequest.setHeader("Accept-Encoding", "gzip"); // only set this parameter if you would like to use gzip compression
		
		   //long t = System.currentTimeMillis();
		   HttpResponse response = (HttpResponse) httpclient.execute(httpPostRequest);
		   //Log.i(TAG, "HTTPResponse received in [" + (System.currentTimeMillis()-t) + "ms]");
		
		   // Get hold of the response entity (-> the data):
		   HttpEntity entity = response.getEntity();
		
		   if (entity != null) {
		    // Read the content stream
		    InputStream instream = entity.getContent();
		    Header contentEncoding = response.getFirstHeader("Content-Encoding");
		    if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
		     instream = new GZIPInputStream(instream);
		    }
		
		    // convert content stream to a String
		    String resultString= convertStreamToString(instream);
		    instream.close();

		    //Log.e("doh", resultString);
		    // remove wrapping "[" and "]"
		    if(resultString.substring(0, 1).contains("["))	    	
		    	resultString = resultString.substring(1,resultString.length()-1);
		
		    // Transform the String into a JSONObject
		    JSONObject jsonObjRecv = new JSONObject(resultString);
		    // Raw DEBUG output of our received JSON object:
		    //Log.i(TAG,"<jsonobject>\n"+jsonObjRecv.toString()+"\n</jsonobject>");	    
		    
		    return jsonObjRecv;
		   } 
		
		  }
		  catch (Exception e)
		  {
		   // More about HTTP exception handling in another tutorial.
		   // For now we just print the stack trace.
		   e.printStackTrace();
		  }
		  catch (OutOfMemoryError e)
		  {
			 System.gc();
		  }
		  return null;
		 }
	
	private static String convertStreamToString(InputStream is) {
	/*
	 * To convert the InputStream to String we use the BufferedReader.readLine()
	 * method. We iterate until the BufferedReader return null which means
	 * there's no more data to read. Each line will appended to a StringBuilder
	 * and returned as String.
	 * 
	 * (c) public domain: http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android/
	 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return sb.toString();
	}
}
