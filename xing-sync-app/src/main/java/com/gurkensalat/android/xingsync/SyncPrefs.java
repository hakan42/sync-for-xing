package com.gurkensalat.android.xingsync;

import com.googlecode.androidannotations.annotations.sharedpreferences.DefaultString;
import com.googlecode.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface SyncPrefs
{
	@DefaultString("")
	String oauth_token();

	@DefaultString("")
	String oauth_token_secret();
}
