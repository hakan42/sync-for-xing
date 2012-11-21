package com.gurkensalat.android.xingsync.activities;

import com.gurkensalat.android.xingsync.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Activity to handle the view-group action. In a real app, this would show a
 * rich view of the group, like members, updates etc.
 */
public class ViewGroupActivity extends Activity
{
    private static final String TAG = "ViewGroupActivity";

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
