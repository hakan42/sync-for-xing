package com.gurkensalat.android.xingsync.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.gurkensalat.android.xingsync.Constants;
import com.gurkensalat.android.xingsync.R;

/**
 * Activity to handle the view-group action. In a real app, this would show a
 * rich view of the group, like members, updates etc.
 */
public class ViewGroupActivity extends Activity
{
    /** The tag used to log to adb console. **/
    private final static String TAG = ViewGroupActivity.class.getName().substring(Constants.PACKAGE_PREFIX_LENGTH);

    private TextView mUriTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_group_activity);

        mUriTextView = (TextView) findViewById(R.id.view_group_uri);
        mUriTextView.setText(getIntent().getDataString());
    }
}
