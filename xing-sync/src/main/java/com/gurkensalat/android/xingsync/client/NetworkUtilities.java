package com.gurkensalat.android.xingsync.client;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.gurkensalat.android.xingsync.Constants;

/**
 * Provides utility methods for communicating with the server.
 */
final public class NetworkUtilities
{
    /** The tag used to log to adb console. **/
    private final static String TAG = NetworkUtilities.class.getName().substring(Constants.PACKAGE_PREFIX_LENGTH);

    /** POST parameter name for the user's account name */
    public static final String PARAM_USERNAME = "username";

    /** POST parameter name for the user's password */
    public static final String PARAM_PASSWORD = "password";

    /** POST parameter name for the user's authentication token */
    public static final String PARAM_AUTH_TOKEN = "authtoken";

    /** POST parameter name for the client's last-known sync state */
    public static final String PARAM_SYNC_STATE = "syncstate";

    /** POST parameter name for the sending client-edited contact info */
    public static final String PARAM_CONTACTS_DATA = "contacts";

    /** Timeout (in ms) we specify for each http request */
    public static final int HTTP_REQUEST_TIMEOUT_MS = 30 * 1000;

    /** Base URL for the v2 Sample Sync Service */
    public static final String BASE_URL = "https://samplesyncadapter2.appspot.com";

    /** URI for authentication service */
    public static final String AUTH_URI = BASE_URL + "/auth";

    /** URI for sync service */
    public static final String SYNC_CONTACTS_URI = BASE_URL + "/sync";

    // TODO XTH 2012-11-22: Make mockMode settable
    private static boolean mockMode = true;

    private NetworkUtilities()
    {
    }

    /**
     * Configures the httpClient to connect to the URL provided.
     */
    public static HttpClient getHttpClient()
    {
        HttpClient httpClient = new DefaultHttpClient();
        final HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
        HttpConnectionParams.setSoTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
        ConnManagerParams.setTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
        return httpClient;
    }

    /**
     * Connects to the SampleSync test server, authenticates the provided
     * username and password.
     * 
     * @param username
     *            The server account username
     * @param password
     *            The server account password
     * @return String The authentication token returned by the server (or null)
     */
    public static String authenticate(String username, String password)
    {
        if (mockMode == true)
        {
            return "mock-auth-token";
        }

        final HttpResponse resp;
        final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(PARAM_USERNAME, username));
        params.add(new BasicNameValuePair(PARAM_PASSWORD, password));
        final HttpEntity entity;
        try
        {
            entity = new UrlEncodedFormEntity(params);
        }
        catch (final UnsupportedEncodingException e)
        {
            // this should never happen.
            throw new IllegalStateException(e);
        }
        Log.i(TAG, "Authenticating to: " + AUTH_URI);
        final HttpPost post = new HttpPost(AUTH_URI);
        post.addHeader(entity.getContentType());
        post.setEntity(entity);
        try
        {
            resp = getHttpClient().execute(post);
            String authToken = null;
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                InputStream istream = (resp.getEntity() != null) ? resp.getEntity().getContent() : null;
                if (istream != null)
                {
                    BufferedReader ireader = new BufferedReader(new InputStreamReader(istream));
                    authToken = ireader.readLine().trim();
                }
            }
            if ((authToken != null) && (authToken.length() > 0))
            {
                Log.v(TAG, "Successful authentication");
                return authToken;
            }
            else
            {
                Log.e(TAG, "Error authenticating" + resp.getStatusLine());
                return null;
            }
        }
        catch (final IOException e)
        {
            Log.e(TAG, "IOException when getting authtoken", e);
            return null;
        }
        finally
        {
            Log.v(TAG, "getAuthtoken completing");
        }
    }

    /**
     * Perform 2-way sync with the server-side contacts. We send a request that
     * includes all the locally-dirty contacts so that the server can process
     * those changes, and we receive (and return) a list of contacts that were
     * updated on the server-side that need to be updated locally.
     * 
     * @param account
     *            The account being synced
     * @param authtoken
     *            The authtoken stored in the AccountManager for this account
     * @param serverSyncState
     *            A token returned from the server on the last sync
     * @param dirtyContacts
     *            A list of the contacts to send to the server
     * @return A list of contacts that we need to update locally
     */
    public static List<RawContact> syncContacts(Account account, String authtoken, long serverSyncState, List<RawContact> dirtyContacts) throws JSONException, ParseException,
            IOException, AuthenticationException
    {
        Log.d(TAG, "in syncContacts");

        // // Convert our list of User objects into a list of JSONObject
        // List<JSONObject> jsonContacts = new ArrayList<JSONObject>();
        // for (RawContact rawContact : dirtyContacts)
        // {
        // jsonContacts.add(rawContact.toJSONObject());
        // }
        //
        // // Create a special JSONArray of our JSON contacts
        // JSONArray buffer = new JSONArray(jsonContacts);

        // Create an array that will hold the server-side contacts
        // that have been changed (returned by the server).
        final ArrayList<RawContact> serverDirtyList = new ArrayList<RawContact>();

        // // Prepare our POST data
        // final ArrayList<NameValuePair> params = new
        // ArrayList<NameValuePair>();
        // params.add(new BasicNameValuePair(PARAM_USERNAME, account.name));
        // params.add(new BasicNameValuePair(PARAM_AUTH_TOKEN, authtoken));
        // params.add(new BasicNameValuePair(PARAM_CONTACTS_DATA,
        // buffer.toString()));
        // if (serverSyncState > 0)
        // {
        // params.add(new BasicNameValuePair(PARAM_SYNC_STATE,
        // Long.toString(serverSyncState)));
        // }
        // Log.i(TAG, params.toString());
        // HttpEntity entity = new UrlEncodedFormEntity(params);
        //
        // // Send the updated friends data to the server
        // Log.i(TAG, "Syncing to: " + SYNC_CONTACTS_URI);
        // final HttpPost post = new HttpPost(SYNC_CONTACTS_URI);
        // post.addHeader(entity.getContentType());
        // post.setEntity(entity);
        // final HttpResponse resp = getHttpClient().execute(post);
        // final String response = EntityUtils.toString(resp.getEntity());
        // if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
        // {
        // // Our request to the server was successful - so we assume
        // // that they accepted all the changes we sent up, and
        // // that the response includes the contacts that we need
        // // to update on our side...
        // final JSONArray serverContacts = new JSONArray(response);
        // Log.d(TAG, response);
        // for (int i = 0; i < serverContacts.length(); i++)
        // {
        // RawContact rawContact =
        // RawContact.valueOf(serverContacts.getJSONObject(i));
        // if (rawContact != null)
        // {
        // serverDirtyList.add(rawContact);
        // }
        // }
        // }
        // else
        // {
        // if (resp.getStatusLine().getStatusCode() ==
        // HttpStatus.SC_UNAUTHORIZED)
        // {
        // Log.e(TAG, "Authentication exception in sending dirty contacts");
        // throw new AuthenticationException();
        // }
        // else
        // {
        // Log.e(TAG, "Server error in sending dirty contacts: " +
        // resp.getStatusLine());
        // throw new IOException();
        // }
        // }

        if (Constants.MOCK_MODE)
        {
            File storageDirectory = Environment.getExternalStorageDirectory();
            if (storageDirectory != null)
            {
                File cacheDir = storageDirectory;
                cacheDir = new File(cacheDir, "Android");
                cacheDir = new File(cacheDir, "data");
                cacheDir = new File(cacheDir, Constants.class.getPackage().getName());
                cacheDir = new File(cacheDir, "files");
                Log.i(TAG, cacheDir.getAbsolutePath() + " ? " + cacheDir.exists() + " ? " + cacheDir.isDirectory());

                if (cacheDir.isDirectory())
                {
                    Log.i(TAG, cacheDir.getAbsolutePath() + " is a directory");
                    if (cacheDir.canRead())
                    {
                        for (String candidateName : cacheDir.list())
                        {
                            Log.i(TAG, "File to check: " + candidateName);
                            if (candidateName.endsWith(".json"))
                            {
                                try
                                {
                                    if (TextUtils.isDigitsOnly(candidateName.substring(0, 1)) || (candidateName.equals("me.json")))
                                    {
                                        File candidate = new File(cacheDir, candidateName);
                                        Log.i(TAG, "About to read: " + candidate.getAbsolutePath());

                                        FileInputStream stream = new FileInputStream(candidate);
                                        String jsonString = null;
                                        try
                                        {
                                            FileChannel fc = stream.getChannel();

                                            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                                            jsonString = Charset.defaultCharset().decode(bb).toString();
                                        }
                                        finally
                                        {
                                            stream.close();
                                        }

                                        JSONObject jObject = new JSONObject(jsonString);

                                        RawContact rawContact = RawContact.valueOf(jObject);
                                        if (rawContact != null)
                                        {
                                            serverDirtyList.add(rawContact);
                                        }
                                    }
                                }
                                catch (IOException e)
                                {
                                    Log.e(TAG, "While mocking contacts", e);
                                    throw e;
                                }
                            }
                        }
                    }
                    else
                    {
                        Log.e(TAG, "Cannot read " + cacheDir.getAbsolutePath());
                    }
                }
            }
            else
            {
                Log.e(TAG, "External Storage Directory was null");

                JSONObject contact = new JSONObject();

                contact.put("u", "mickymouse");
                contact.put("i", 4711);
                contact.put("f", "Micky");
                contact.put("l", "Mouse");

                RawContact rawContact = RawContact.valueOf(contact);
                if (rawContact != null)
                {
                    serverDirtyList.add(rawContact);
                }
            }
        }

        Log.d(TAG, "Returning " + serverDirtyList.size() + " raw contacts");

        return serverDirtyList;
    }

    /**
     * Download the avatar image from the server.
     * 
     * @param avatarUrl
     *            the URL pointing to the avatar image
     * @return a byte array with the raw JPEG avatar image
     */
    public static byte[] downloadAvatar(final String avatarUrl)
    {
        // If there is no avatar, we're done
        if (TextUtils.isEmpty(avatarUrl))
        {
            return null;
        }

        try
        {
            Log.i(TAG, "Downloading avatar: " + avatarUrl);
            // Request the avatar image from the server, and create a bitmap
            // object from the stream we get back.
            URL url = new URL(avatarUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            try
            {
                final BitmapFactory.Options options = new BitmapFactory.Options();
                final Bitmap avatar = BitmapFactory.decodeStream(connection.getInputStream(), null, options);

                // Take the image we received from the server, whatever format
                // it
                // happens to be in, and convert it to a JPEG image. Note: we're
                // not resizing the avatar - we assume that the image we get
                // from
                // the server is a reasonable size...
                Log.i(TAG, "Converting avatar to JPEG");
                ByteArrayOutputStream convertStream = new ByteArrayOutputStream(avatar.getWidth() * avatar.getHeight() * 4);
                avatar.compress(Bitmap.CompressFormat.JPEG, 95, convertStream);
                convertStream.flush();
                convertStream.close();
                // On pre-Honeycomb systems, it's important to call recycle on
                // bitmaps
                avatar.recycle();
                return convertStream.toByteArray();
            }
            finally
            {
                connection.disconnect();
            }
        }
        catch (MalformedURLException muex)
        {
            // A bad URL - nothing we can really do about it here...
            Log.e(TAG, "Malformed avatar URL: " + avatarUrl);
        }
        catch (IOException ioex)
        {
            // If we're unable to download the avatar, it's a bummer but not the
            // end of the world. We'll try to get it next time we sync.
            Log.e(TAG, "Failed to download user avatar: " + avatarUrl);
        }
        return null;
    }

}
