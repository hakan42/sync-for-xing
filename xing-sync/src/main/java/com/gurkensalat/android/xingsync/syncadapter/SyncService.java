package com.gurkensalat.android.xingsync.syncadapter;

import com.gurkensalat.android.xingsync.Constants;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Service to handle Account sync. This is invoked with an intent with action
 * ACTION_AUTHENTICATOR_INTENT. It instantiates the syncadapter and returns its
 * IBinder.
 */
public class SyncService extends Service
{
    /** The tag used to log to adb console. **/
    private final static String TAG = SyncService.class.getName().substring(Constants.PACKAGE_PREFIX_LENGTH);

    private static final Object sSyncAdapterLock = new Object();

    private static SyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate()
    {
        synchronized (sSyncAdapterLock)
        {
            if (sSyncAdapter == null)
            {
                sSyncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
