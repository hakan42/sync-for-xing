package com.gurkensalat.android.xingsync.preferences;

import com.gurkensalat.android.xingsync.Constants;
import com.gurkensalat.android.xingsync.R;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;

public class AccountPreferences extends PreferenceActivity
{
    /** The tag used to log to adb console. **/
    private final static String TAG = AccountPreferences.class.getName().substring(Constants.PACKAGE_PREFIX_LENGTH);

    private boolean shouldForceSync = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        getPreferenceManager().setSharedPreferencesName(Constants.PREFS_NAME);
        addPreferencesFromResource(R.xml.advanced_preferences);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.i(TAG, "onPause(): shouldForceSync == " + shouldForceSync);
        if (shouldForceSync)
        {
            // AccountAuthenticatorService.resyncAccount(this);
            Log.i(TAG, "onPause(): shouldForceSync true");
        }
    }

    Preference.OnPreferenceChangeListener syncToggle = new Preference.OnPreferenceChangeListener()
    {
        public boolean onPreferenceChange(Preference preference, Object newValue)
        {
            shouldForceSync = true;
            return true;
        }
    };
}