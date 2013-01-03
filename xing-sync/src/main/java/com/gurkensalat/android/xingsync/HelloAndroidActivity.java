package com.gurkensalat.android.xingsync;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public class HelloAndroidActivity extends Activity
{
    /** The tag used to log to adb console. **/
    private final static String TAG = HelloAndroidActivity.class.getName().substring(Constants.PACKAGE_PREFIX_LENGTH);

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState
     *            If the activity is being re-initialized after previously being
     *            shut down then this Bundle contains the data it most recently
     *            supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
     *            is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        // setContentView(R.layout.account_entry);

        Intent intent = new Intent(Settings.ACTION_SYNC_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // To show only our own accounts
        intent.putExtra(Settings.EXTRA_AUTHORITIES, new String[]
        { Constants.PROVIDER_AUTHORITY });

        startActivity(intent);
    }
}
