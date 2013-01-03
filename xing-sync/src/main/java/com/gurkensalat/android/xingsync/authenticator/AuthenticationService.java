package com.gurkensalat.android.xingsync.authenticator;

import com.gurkensalat.android.xingsync.Constants;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Service to handle Account authentication. It instantiates the authenticator
 * and returns its IBinder.
 */
public class AuthenticationService extends Service
{
    /** The tag used to log to adb console. **/
    private final static String TAG = AuthenticationService.class.getName().substring(Constants.PACKAGE_PREFIX_LENGTH);

    private Authenticator mAuthenticator;

    @Override
    public void onCreate()
    {
        Log.v(TAG, "started");
        mAuthenticator = new Authenticator(this);
    }

    @Override
    public void onDestroy()
    {
        Log.v(TAG, "stopped");
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.v(TAG, "getBinder()...  returning the AccountAuthenticator binder for intent " + intent);
        return mAuthenticator.getIBinder();
    }
}
