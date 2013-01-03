package com.gurkensalat.android.xingsync.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.gurkensalat.android.xingsync.keys.XingOAuthKeys;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public abstract class AbstractXINGApiTask extends AsyncTask<Void, Void, String>
{
    /**
     * 
     */
    private final static String TAG = AbstractXINGApiTask.class.getName();

    protected OAuthConsumer getConsumer(SharedPreferences prefs)
    {
        Log.i(TAG, "getConsumer()");

        String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
        String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(XingOAuthKeys.CONSUMER_KEY, XingOAuthKeys.CONSUMER_SECRET);
        consumer.setTokenWithSecret(token, secret);
        return consumer;
    }

    protected String doGet(String url, OAuthConsumer consumer) throws Exception
    {
        Log.i(TAG, "doGet(" + url + ", " + consumer + ")");

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        Log.i(TAG, "Requesting URL : " + url);
        consumer.sign(request);
        HttpResponse response = httpclient.execute(request);
        Log.i(TAG, "Statusline : " + response.getStatusLine());
        InputStream data = response.getEntity().getContent();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(data));
        String responeLine;
        StringBuilder responseBuilder = new StringBuilder();
        while ((responeLine = bufferedReader.readLine()) != null)
        {
            responseBuilder.append(responeLine);
        }
        Log.i(TAG, "Response : " + responseBuilder.toString());
        return responseBuilder.toString();
    }

}
