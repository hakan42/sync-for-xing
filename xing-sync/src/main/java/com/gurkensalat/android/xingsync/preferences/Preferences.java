package com.gurkensalat.android.xingsync.preferences;

import com.gurkensalat.android.xingsync.Constants;
import com.gurkensalat.android.xingsync.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Preferences
{
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
