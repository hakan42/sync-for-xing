package com.gurkensalat.android.xingsync;

import android.app.Activity;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.FragmentById;
import com.googlecode.androidannotations.annotations.ViewById;
import com.gurkensalat.android.xingsync.oauth.OAuthButtonsFragment;
import com.gurkensalat.android.xingsync.oauth.OAuthSecretsFragment;

@EActivity(R.layout.main)
public class HelloAndroidActivity extends Activity
{
	private static String TAG = "xing-sync";

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after previously being
	 *            shut down then this Bundle contains the data it most recently
	 *            supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
	 *            is null.</b>
	 */
	// @Override
	// public void onCreate(Bundle savedInstanceState)
	// {
	// super.onCreate(savedInstanceState);
	// // Log.i(TAG, "onCreate");
	// // // setContentView(R.layout.main);
	// // setContentView(R.layout.oauth_result);
	// // Log.i(TAG, "after setContentView()");
	// }

	@ViewById(R.id.myInput)
	EditText myInput;

	@ViewById(R.id.myInput)
	TextView textView;

	@FragmentById(R.id.main_layout_fragment_oauth_secrets)
	OAuthSecretsFragment secretsFragment;

	@FragmentById(R.id.main_layout_fragment_oauth_buttons)
	OAuthButtonsFragment buttonsFragment;

	@Click
	void myButton()
	{
		String name = myInput.getText().toString();
		textView.setText("Hello " + name);

		Log.i(TAG, "secretsFragment is");
		if (secretsFragment == null)
		{
			Log.i(TAG, "NULL");
		}
		else
		{
			Log.i(TAG, secretsFragment.toString());
		}
	}
}
