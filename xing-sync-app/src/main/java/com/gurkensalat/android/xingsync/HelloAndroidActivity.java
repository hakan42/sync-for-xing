package com.gurkensalat.android.xingsync;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

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

	@Click
	void myButton()
	{
		String name = myInput.getText().toString();
		textView.setText("Hello " + name);
	}
}
