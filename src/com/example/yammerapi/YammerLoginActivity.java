package com.example.yammerapi;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class YammerLoginActivity extends Activity {	
	
	private final int YAMMER_OAUTH = 0;

	
	public String scheme = "smartsole";
	WebView mWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yammerlogin);
		
		mWebView = (WebView)findViewById(R.id.webView);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setBuiltInZoomControls(true);

        mWebView.getSettings().setDomStorageEnabled(true);
		mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

		mWebView.setWebViewClient(new WebViewClient() {
	        @Override
	        public void onReceivedError(WebView view, int errorCode,
	                String description, String failingUrl) {
	            // Handle the error
	        }

	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            view.loadUrl(url);
	            Log.e("url", url);
	            Uri uri = Uri.parse(url);
	            if(uri.getScheme().equalsIgnoreCase(scheme))
	            {
	            	Bundle bundle = new Bundle();
	            	bundle.putString("code", uri.getQueryParameter("code"));
	        		getLoaderManager().initLoader(YAMMER_OAUTH, bundle, callbacks);
	            }
	            
	            return true;
	        }
	    });
		
		mWebView.loadUrl(String.format(YammerAPI.LOGIN_URL, YammerAPI.CLIENT_ID));
	}
	
    private LoaderManager.LoaderCallbacks<JSONObject> callbacks = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
        	return new ResultAsyncTaskLoader(YammerLoginActivity.this, args.getString("code"));
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {

        	if(data.has("access_token"))
        	{
        		try {
					if(data.getJSONObject("access_token").has("token"))
					{
						YammerPreference.CachingToken(YammerLoginActivity.this, data.getJSONObject("access_token").getString("token"));
			        	YammerLoginActivity.this.setResult(RESULT_OK);
			        	YammerLoginActivity.this.finish();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	
        	}
            getLoaderManager().destroyLoader(loader.getId());
        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };
    
    private static class ResultAsyncTaskLoader extends AsyncTaskLoader<JSONObject> {

        private JSONObject result;
        private String mCode;
        public ResultAsyncTaskLoader(Context context, String code) {
            super(context);
            mCode = code;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (result != null) {
                deliverResult(result);
            } else {
                forceLoad();
            }
        }

        @Override
        public JSONObject loadInBackground() {
            /**
             * send request to server
             */
            result = 
            		WebServices.SendHttpPost(
            				String.format(YammerAPI.LOGIN_OAUTH, YammerAPI.CLIENT_ID, YammerAPI.CLIENT_SECRET, mCode), new JSONObject(), "");
            return result;
        }
    }
}
