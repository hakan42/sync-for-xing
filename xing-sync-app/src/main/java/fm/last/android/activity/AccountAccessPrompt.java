/**
 * 
 */
package fm.last.android.activity;

import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.app.Activity;
import android.os.Bundle;

import com.gurkensalat.android.xingsync.R;

/**
 * @author sam
 * 
 */
public class AccountAccessPrompt extends Activity
{
	AccountAuthenticatorResponse response;

	@Override
	public void onCreate(Bundle icicle)
	{
		// super.onCreate(icicle);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// setContentView(R.layout.account_access_prompt);
		// response =
		// getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);
		//
		// Button yes = (Button) findViewById(R.id.yes);
		// yes.setOnClickListener(new OnClickListener()
		// {
		//
		// public void onClick(View v)
		// {
		// String api_key = getIntent().getStringExtra("api_key");
		// String api_secret = getIntent().getStringExtra("api_secret");
		// String user = getIntent().getStringExtra("user");
		// String authToken = getIntent().getStringExtra("authToken");
		//
		// LastFmServer server =
		// LastFmServerFactory.getServer("http://ws.audioscrobbler.com/2.0/",
		// api_key, api_secret);
		//
		// try
		// {
		// Session session = server.getMobileSession(user, authToken);
		// if (session != null)
		// {
		// Bundle result = new Bundle();
		// result.putString(AccountManager.KEY_ACCOUNT_NAME, user);
		// result.putString(AccountManager.KEY_ACCOUNT_TYPE,
		// getString(R.string.ACCOUNT_TYPE));
		// result.putString(AccountManager.KEY_AUTHTOKEN, session.getKey());
		// response.onResult(result);
		// }
		// finish();
		// }
		// catch (IOException e)
		// {
		// e.printStackTrace();
		// }
		// }
		// });
		//
		// Button no = (Button) findViewById(R.id.no);
		// no.setOnClickListener(new OnClickListener()
		// {
		//
		// public void onClick(View v)
		// {
		// response.onError(-1, "Permission denied");
		// finish();
		// }
		//
		// });

		// Session session = server.getMobileSession(user, authToken);
		// if (session != null)
		{
			String user = "gaga-user";
			String key = "gaga-key"; // session.getKey();
			Bundle result = new Bundle();
			result.putString(AccountManager.KEY_ACCOUNT_NAME, user);
			result.putString(AccountManager.KEY_ACCOUNT_TYPE, getString(R.string.ACCOUNT_TYPE));
			result.putString(AccountManager.KEY_AUTHTOKEN, key);
			response.onResult(result);
		}
		finish();
	}
}
