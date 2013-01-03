package com.gurkensalat.android.xingsync.authenticator;

import com.gurkensalat.android.xingsync.Constants;
import com.gurkensalat.android.xingsync.R;
import com.gurkensalat.android.xingsync.oauth.PrepareRequestTokenActivity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays login screen to the user.
 */
public class AuthenticatorActivity extends AccountAuthenticatorActivity
{
    /** The Intent flag to confirm credentials. */
    public static final String PARAM_CONFIRM_CREDENTIALS = "confirmCredentials";

    /** The Intent extra to store password. */
    public static final String PARAM_PASSWORD            = "password";

    /** The Intent extra to store username. */
    public static final String PARAM_USERNAME            = "username";

    /** The Intent extra to store username. */
    public static final String PARAM_AUTHTOKEN_TYPE      = "authtokenType";

    /** The tag used to log to adb console. */
    static final String        TAG                       = "AuthenticatorActivity";

    /** Sync period in seconds, currently every week */
    // TODO XTH 2012-11-22 make SYNC_PERIOD configurable
    private static final long  SYNC_PERIOD               = 7L * 24L * 60L * 60L;

    private AccountManager     mAccountManager;

    /** Keep track of the login task so can cancel it if requested */
    private UserLoginTask      mAuthTask                 = null;

    /** Keep track of the progress dialog so we can dismiss it */
    private ProgressDialog     mProgressDialog           = null;

    /**
     * If set we are just checking that the user knows their credentials; this
     * doesn't cause the user's password or authToken to be changed on the
     * device.
     */
    private Boolean            mConfirmCredentials       = false;

    /** for posting authentication attempts back to UI thread */
    private final Handler      mHandler                  = new Handler();

    private TextView           mMessage;

    private String             mPassword;

    private EditText           mPasswordEdit;

    /** Was the original caller asking for an entirely new account? */
    protected boolean          mRequestNewAccount        = false;

    private String             mUsername;

    private EditText           mUsernameEdit;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle icicle)
    {
        Log.i(TAG, "onCreate(" + icicle + ")");
        super.onCreate(icicle);
        mAccountManager = AccountManager.get(this);
        Log.i(TAG, "loading data from Intent");
        final Intent intent = getIntent();
        mUsername = intent.getStringExtra(PARAM_USERNAME);
        mRequestNewAccount = mUsername == null;
        mConfirmCredentials = intent.getBooleanExtra(PARAM_CONFIRM_CREDENTIALS, false);
        Log.i(TAG, "    request new: " + mRequestNewAccount);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.oauth_activity);
        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, android.R.drawable.ic_dialog_alert);
        // mMessage = (TextView) findViewById(R.id.message);
        // mUsernameEdit = (EditText) findViewById(R.id.username_edit);
        // mPasswordEdit = (EditText) findViewById(R.id.password_edit);
        // if (!TextUtils.isEmpty(mUsername))
        // mUsernameEdit.setText(mUsername);
        // mMessage.setText(getMessage());
    }

    /*
     * {@inheritDoc}
     */
    @Override
    protected Dialog onCreateDialog(int id, Bundle args)
    {
        Log.i(TAG, "onCreateDialog(" + id + ", " + args + ")");
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getText(R.string.ui_activity_authenticating));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            public void onCancel(DialogInterface dialog)
            {
                Log.i(TAG, "user cancelling authentication");
                if (mAuthTask != null)
                {
                    mAuthTask.cancel(true);
                }
            }
        });
        // We save off the progress dialog in a field so that we can dismiss
        // it later. We can't just call dismissDialog(0) because the system
        // can lose track of our dialog if there's an orientation change.
        mProgressDialog = dialog;
        return dialog;
    }

    /**
     * Handles onClick event on the Submit button. Continues the OAuth dance.
     * 
     * The button is configured to call handleRealOAuthCall() in the layout XML.
     * 
     * @param view
     *            The Submit button for which this method is invoked
     */
    public void handleRealOAuthCall(View view)
    {
        Log.i(TAG, "handleRealOAuthCall(" + view + ")");
        startActivity(new Intent().setClass(view.getContext(), PrepareRequestTokenActivity.class));
    }

    /**
     * Handles onClick event on the Submit button. Mocks the OAuth dance.
     * 
     * The button is configured to call handleLogin() in the layout XML.
     * 
     * @param view
     *            The Submit button for which this method is invoked
     */
    public void handleMockOAuthCall(View view)
    {
        Log.i(TAG, "handleMockOAuthCall(" + view + ")");
    }

    /**
     * Handles onClick event on the Submit button. Sends username/password to
     * the server for authentication. The button is configured to call
     * handleLogin() in the layout XML.
     * 
     * @param view
     *            The Submit button for which this method is invoked
     */
    public void handleLogin(View view)
    {
        Log.i(TAG, "handleLogin(" + view + ")");

        if (mRequestNewAccount)
        {
            mUsername = mUsernameEdit.getText().toString();
        }
        mPassword = mPasswordEdit.getText().toString();
        if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword))
        {
            mMessage.setText(getMessage());
        }
        else
        {
            // Show a progress dialog, and kick off a background task to perform
            // the user login attempt.
            showProgress();
            mAuthTask = new UserLoginTask(this);
            mAuthTask.execute();
        }
    }

    /**
     * Called when response is received from the server for confirm credentials
     * request. See onAuthenticationResult(). Sets the
     * AccountAuthenticatorResult which is sent back to the caller.
     * 
     * @param result
     *            the confirmCredentials result.
     */
    private void finishConfirmCredentials(boolean result)
    {
        Log.i(TAG, "finishConfirmCredentials(" + result + ")");
        final Account account = new Account(mUsername, Constants.ACCOUNT_TYPE);
        mAccountManager.setPassword(account, mPassword);
        final Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_BOOLEAN_RESULT, result);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Called when response is received from the server for authentication
     * request. See onAuthenticationResult(). Sets the
     * AccountAuthenticatorResult which is sent back to the caller. We store the
     * authToken that's returned from the server as the 'password' for this
     * account - so we're never storing the user's actual password locally.
     * 
     * @param result
     *            the confirmCredentials result.
     */
    private void finishLogin(String authToken)
    {
        Log.i(TAG, "finishLogin(" + authToken + ")");
        final Account account = new Account(mUsername, Constants.ACCOUNT_TYPE);
        if (mRequestNewAccount)
        {
            mAccountManager.addAccountExplicitly(account, mPassword, null);

            // Set contacts sync for this account.
            ContentResolver.setIsSyncable(account, Constants.PROVIDER_AUTHORITY, 1);
            ContentResolver.setSyncAutomatically(account, Constants.PROVIDER_AUTHORITY, true);
            ContentResolver.addPeriodicSync(account, Constants.PROVIDER_AUTHORITY, new Bundle(), SYNC_PERIOD);
            ContentResolver.setMasterSyncAutomatically(true);
        }
        else
        {
            mAccountManager.setPassword(account, mPassword);
        }
        final Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, mUsername);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Constants.ACCOUNT_TYPE);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Called when the authentication process completes (see attemptLogin()).
     * 
     * @param authToken
     *            the authentication token returned by the server, or NULL if
     *            authentication failed.
     */
    public void onAuthenticationResult(String authToken)
    {
        boolean success = ((authToken != null) && (authToken.length() > 0));
        Log.i(TAG, "onAuthenticationResult(" + success + ")");

        // Our task is complete, so clear it out
        mAuthTask = null;

        // Hide the progress dialog
        hideProgress();

        if (success)
        {
            if (!mConfirmCredentials)
            {
                finishLogin(authToken);
            }
            else
            {
                finishConfirmCredentials(success);
            }
        }
        else
        {
            Log.e(TAG, "onAuthenticationResult: failed to authenticate");
            if (mRequestNewAccount)
            {
                // "Please enter a valid username/password.
                mMessage.setText(getText(R.string.login_activity_loginfail_text_both));
            }
            else
            {
                // "Please enter a valid password." (Used when the
                // account is already in the database but the password
                // doesn't work.)
                mMessage.setText(getText(R.string.login_activity_loginfail_text_pwonly));
            }
        }
    }

    public void onAuthenticationCancel()
    {
        Log.i(TAG, "onAuthenticationCancel()");

        // Our task is complete, so clear it out
        mAuthTask = null;

        // Hide the progress dialog
        hideProgress();
    }

    /**
     * Returns the message to be displayed at the top of the login dialog box.
     */
    private CharSequence getMessage()
    {
        getString(R.string.accounts_label);
        if (TextUtils.isEmpty(mUsername))
        {
            // If no username, then we ask the user to log in using an
            // appropriate service.
            final CharSequence msg = getText(R.string.login_activity_newaccount_text);
            return msg;
        }
        if (TextUtils.isEmpty(mPassword))
        {
            // We have an account but no password
            return getText(R.string.login_activity_loginfail_text_pwmissing);
        }
        return null;
    }

    /**
     * Shows the progress UI for a lengthy operation.
     */
    private void showProgress()
    {
        showDialog(0);
    }

    /**
     * Hides the progress UI for a lengthy operation.
     */
    private void hideProgress()
    {
        if (mProgressDialog != null)
        {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public String getmPassword()
    {
        return mPassword;
    }

    public void setmPassword(String mPassword)
    {
        this.mPassword = mPassword;
    }

    public String getmUsername()
    {
        return mUsername;
    }

    public void setmUsername(String mUsername)
    {
        this.mUsername = mUsername;
    }

    public EditText getmUsernameEdit()
    {
        return mUsernameEdit;
    }

    public void setmUsernameEdit(EditText mUsernameEdit)
    {
        this.mUsernameEdit = mUsernameEdit;
    }
}