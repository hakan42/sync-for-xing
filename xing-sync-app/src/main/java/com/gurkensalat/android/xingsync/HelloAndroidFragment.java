package com.gurkensalat.android.xingsync;

import android.app.Fragment;

import com.googlecode.androidannotations.annotations.EFragment;
import com.gurkensalat.android.xingsync.R;

@EFragment(R.layout.fragment_main_hello_android)
public class HelloAndroidFragment extends Fragment
{
	// public HelloAndroidFragment()
	// {
	// // Each Fragment needs and empty public constructor
	// }

	public void setText(String item)
	{
		// TextView view = (TextView)
		// getView().findViewById(R.id.oauth_token_secret);
		// view.setText(item);
	}
}
