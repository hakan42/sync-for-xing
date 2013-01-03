package com.gurkensalat.android.xingsync.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.gurkensalat.android.xingsync.Constants;
import com.gurkensalat.android.xingsync.R;

public class Preferences
{
    /** The tag used to log to adb console. **/
    private final static String TAG = Preferences.class.getName().substring(Constants.PACKAGE_PREFIX_LENGTH);

    public static boolean getWiFiOnlyFlag(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(
                Constants.PREFS_NAME, 0);
        return prefs.getBoolean(
                context.getString(R.string.prefs_sync_wifi_only_key), false);
    }

    public static void setWiFiOnlyFlag(Context context, boolean newValue)
    {
        SharedPreferences prefs = context.getSharedPreferences(
                Constants.PREFS_NAME, 0);
        Editor prefsEditor = prefs.edit();
        prefsEditor.putBoolean(
                context.getString(R.string.prefs_sync_wifi_only_key),
                newValue);
        prefsEditor.commit();
    }
}
