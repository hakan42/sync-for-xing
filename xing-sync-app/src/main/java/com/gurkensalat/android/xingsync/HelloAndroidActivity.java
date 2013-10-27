package com.gurkensalat.android.xingsync;

import android.app.Activity;

import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.FragmentById;
import com.gurkensalat.android.xingsync.oauth.OAuthButtonsFragment;
import com.gurkensalat.android.xingsync.oauth.OAuthResultsFragment;
import com.gurkensalat.android.xingsync.oauth.OAuthSecretsFragment;

@EActivity(R.layout.main)
public class HelloAndroidActivity extends Activity
{
	private static String TAG = "xing-sync";

	@FragmentById(R.id.main_layout_fragment_oauth_secrets)
	OAuthSecretsFragment secretsFragment;

	@FragmentById(R.id.main_layout_fragment_oauth_buttons)
	OAuthButtonsFragment buttonsFragment;

	@FragmentById(R.id.main_layout_fragment_oauth_results)
	OAuthResultsFragment resultsFragment;

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
