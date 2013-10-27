package com.gurkensalat.android.xingsync;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.FragmentById;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.gurkensalat.android.xingsync.oauth.OAuthButtonsFragment;
import com.gurkensalat.android.xingsync.oauth.OAuthResultsFragment;
import com.gurkensalat.android.xingsync.oauth.OAuthSecretsFragment;
import com.gurkensalat.android.xingsync.oauth.PrepareRequestTokenActivity;
import com.gurkensalat.android.xingsync.oauth.PrepareRequestTokenActivity_;

@EActivity(R.layout.main)
public class HelloAndroidActivity extends Activity
{
	private static String TAG = "xing-sync";

	@Pref
	SyncPrefs_ syncPrefs;

	@FragmentById(R.id.main_layout_fragment_oauth_secrets)
	OAuthSecretsFragment secretsFragment;

	@FragmentById(R.id.main_layout_fragment_oauth_buttons)
	OAuthButtonsFragment buttonsFragment;

	@FragmentById(R.id.main_layout_fragment_oauth_results)
	OAuthResultsFragment resultsFragment;

	@Click(R.id.btn_launch_oauth)
	void launchOAuth(View clickedView)
	{
		Log.i(TAG, "About to launch OAuth dance");
		PrepareRequestTokenActivity_.intent(clickedView.getContext()).start();
	}

	// @Click
	// void myButton()
	// {
	// @ViewById(R.id.myInput)
	// EditText myInput;
	//
	// @ViewById(R.id.myInput)
	// TextView textView;
	//
	// String name = myInput.getText().toString();
	// textView.setText("Hello " + name);
	//
	// Log.i(TAG, "secretsFragment is");
	// if (secretsFragment == null)
	// {
	// Log.i(TAG, "NULL");
	// }
	// else
	// {
	// Log.i(TAG, secretsFragment.toString());
	// }
	// }
}
