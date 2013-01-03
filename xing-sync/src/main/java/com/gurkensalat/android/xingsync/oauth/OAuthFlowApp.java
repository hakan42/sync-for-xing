package com.gurkensalat.android.xingsync.oauth;

import oauth.signpost.OAuth;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Contacts.People;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gurkensalat.android.xingsync.Constants;
import com.gurkensalat.android.xingsync.R;
import com.gurkensalat.android.xingsync.client.MeTask;

/**
 * Entry point in the application. Launches the OAuth flow by starting the
 * PrepareRequestTokenActivity
 * 
 */
public class OAuthFlowApp extends Activity
{
    /** The tag used to log to adb console. **/
    private final static String TAG = OAuthFlowApp.class.getName().substring(Constants.PACKAGE_PREFIX_LENGTH);

    private static final int PICK_CONTACT = 0;

    private SharedPreferences prefs;

    /** Keep track of the API call task so can cancel it if requested */
    private MeTask mMeTask = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.i(TAG, "onCreate(" + savedInstanceState + ")");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.oauth_result);
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String token = prefs.getString(OAuth.OAUTH_TOKEN, "NO-TOKEN");
        TextView oauthToken = (TextView) findViewById(R.id.oauth_token);
        oauthToken.setText(token);

        String token_secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "NO-TOKEN-SECRET");
        TextView oauthTokenSecret = (TextView) findViewById(R.id.oauth_token_secret);
        oauthTokenSecret.setText(token_secret);
    }

    public void performApiCallME(View view)
    {
        Log.i(TAG, "performApiCallME(" + view + ")");

        mMeTask = new MeTask(this);
        mMeTask.execute();
    }

    public void showResult(String result)
    {
        Log.i(TAG, "showResult(" + result + ")");

        TextView textView = (TextView) findViewById(R.id.api_call_result);
        textView.setText(result);

        if (result != null)
        {
            finish();
        }
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data)
    {
        Log.i(TAG, "onActivityResult(" + reqCode + ", " + resultCode + ", " + data + ")");

        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode)
        {
        case (PICK_CONTACT):
            if (resultCode == Activity.RESULT_OK)
            {
                Uri contactData = data.getData();
                Cursor c = managedQuery(contactData, null, null, null, null);
                if (c.moveToFirst())
                {
                    String name = c.getString(c.getColumnIndexOrThrow(People.NAME));
                    Log.i(TAG, "Response : " + "Selected contact : " + name);
                }
            }
            break;
        }

        Log.i(TAG, "onActivityResult() finished");
    }

    private void clearCredentials()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final Editor edit = prefs.edit();
        edit.remove(OAuth.OAUTH_TOKEN);
        edit.remove(OAuth.OAUTH_TOKEN_SECRET);
        edit.commit();
    }
}
