package com.gurkensalat.android.xingsync;

import com.googlecode.androidannotations.annotations.sharedpreferences.DefaultString;
import com.googlecode.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface SyncPrefs
{
	/**
	 * @see oauth.signpost.OAuth.OAUTH_TOKEN
	 */
	@DefaultString("")
	String oauth_token();

	/**
	 * @see oauth.signpost.OAuth.OAUTH_TOKEN_SECRET
	 */
	@DefaultString("")
	String oauth_token_secret();
}
