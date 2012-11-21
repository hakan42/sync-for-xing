package com.gurkensalat.android.xingsync.activities;

import com.gurkensalat.android.xingsync.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Activity to handle the invite-intent. In a real app, this would look up the
 * user on the network and either connect ("add as friend", "follow") or invite
 * them to the network
 */
public class InviteContactActivity extends Activity
{
    private static final String TAG = "InviteContactActivity";

    private TextView mUriTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_contact_activity);

        mUriTextView = (TextView) findViewById(R.id.invite_contact_uri);
        mUriTextView.setText(getIntent().getDataString());
    }
}
