package com.gurkensalat.android.xingsync.client;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * Represents a low-level contacts RawContact - or at least the fields of the
 * RawContact that we care about.
 */
final public class RawContact
{

    /** The tag used to log to adb console. **/
    private static final String TAG = "RawContact";

    private final String mUserName;

    private final String mFullName;

    private final String mFirstName;

    private final String mLastName;

    private final String mCellPhone;

    private final String mOfficePhone;

    private final String mHomePhone;

    private final String mEmail;

    private final String mStatus;

    private final String mAvatarUrl;

    private final boolean mDeleted;

    private final boolean mDirty;

    private final long mServerContactId;

    private final long mRawContactId;

    private final long mSyncState;

    public long getServerContactId()
    {
        return mServerContactId;
    }

    public long getRawContactId()
    {
        return mRawContactId;
    }

    public String getUserName()
    {
        return mUserName;
    }

    public String getFirstName()
    {
        return mFirstName;
    }

    public String getLastName()
    {
        return mLastName;
    }

    public String getFullName()
    {
        return mFullName;
    }

    public String getCellPhone()
    {
        return mCellPhone;
    }

    public String getOfficePhone()
    {
        return mOfficePhone;
    }

    public String getHomePhone()
    {
        return mHomePhone;
    }

    public String getEmail()
    {
        return mEmail;
    }

    public String getStatus()
    {
        return mStatus;
    }

    public String getAvatarUrl()
    {
        return mAvatarUrl;
    }

    public boolean isDeleted()
    {
        return mDeleted;
    }

    public boolean isDirty()
    {
        return mDirty;
    }

    public long getSyncState()
    {
        return mSyncState;
    }

    public String getBestName()
    {
        if (!TextUtils.isEmpty(mFullName))
        {
            return mFullName;
        }
        else if (TextUtils.isEmpty(mFirstName))
        {
            return mLastName;
        }
        else
        {
            return mFirstName;
        }
    }

    /**
     * Convert the RawContact object into a JSON string. From the JSONString
     * interface.
     *
     * @return a JSON string representation of the object
     */
    public JSONObject toJSONObject()
    {
        JSONObject json = new JSONObject();

        try
        {
            if (!TextUtils.isEmpty(mFirstName))
            {
                json.put("f", mFirstName);
            }
            if (!TextUtils.isEmpty(mLastName))
            {
                json.put("l", mLastName);
            }
            if (!TextUtils.isEmpty(mCellPhone))
            {
                json.put("m", mCellPhone);
            }
            if (!TextUtils.isEmpty(mOfficePhone))
            {
                json.put("o", mOfficePhone);
            }
            if (!TextUtils.isEmpty(mHomePhone))
            {
                json.put("h", mHomePhone);
            }
            if (!TextUtils.isEmpty(mEmail))
            {
                json.put("e", mEmail);
            }
            if (mServerContactId > 0)
            {
                json.put("i", mServerContactId);
            }
            if (mRawContactId > 0)
            {
                json.put("c", mRawContactId);
            }
            if (mDeleted)
            {
                json.put("d", mDeleted);
            }
        }
        catch (final Exception ex)
        {
            Log.i(TAG, "Error converting RawContact to JSONObject" + ex.toString());
        }

        return json;
    }

    private static String safeGetString(JSONObject object, String key)
    {
        String result = "";

        try
        {
            if (!(object.isNull(key)))
            {
                result = object.getString(key);
            }
        }
        catch (JSONException je)
        {
            Log.e(TAG, "While accessing " + key, je);
        }

        return result;
    }

    public RawContact(String name, String fullName, String firstName, String lastName, String cellPhone, String officePhone,
            String homePhone, String email, String status, String avatarUrl, boolean deleted, long serverContactId,
            long rawContactId, long syncState, boolean dirty)
    {
        mUserName = name;
        mFullName = fullName;
        mFirstName = firstName;
        mLastName = lastName;
        mCellPhone = cellPhone;
        mOfficePhone = officePhone;
        mHomePhone = homePhone;
        mEmail = email;
        mStatus = status;
        mAvatarUrl = avatarUrl;
        mDeleted = deleted;
        mServerContactId = serverContactId;
        mRawContactId = rawContactId;
        mSyncState = syncState;
        mDirty = dirty;
    }

    /**
     * Creates and returns an instance of the RawContact from the provided JSON
     * data.
     *
     * @param user
     *            The JSONObject containing user data
     * @return user The new instance of Sample RawContact created from the JSON
     *         data.
     */
    public static RawContact valueOf(JSONObject contact)
    {
        try
        {
            // Log.i(TAG, "Received JSON object " + contact);
            JSONArray contacts = contact.getJSONArray("users");
            // Log.i(TAG, "converted to array: " + contacts);
            if (contacts != null && contacts.length() > 0)
            {
                contact = contacts.getJSONObject(0);

                // Log.i(TAG, contact.toString(2));

                final String userName = safeGetString(contact, "display_name");
                final String serverContactIdString = safeGetString(contact, "id");

                int serverContactId = -1;
                if (!(TextUtils.isEmpty(serverContactIdString)))
                {
                    serverContactId = Integer.parseInt(serverContactIdString.substring(0, serverContactIdString.indexOf("_")));
                }

                // If we didn't get either a username or serverId for the
                // contact, then we can't do anything with it locally...
                if ((userName == null) && (serverContactId <= 0))
                {
                    throw new JSONException("JSON contact missing required 'u' or 'i' fields");
                }

                final int rawContactId = -1;
                // !contact.isNull("c") ? contact.getInt("c") : -1;

                final String firstName = safeGetString(contact, "first_name");
                final String lastName = safeGetString(contact, "last_name");

                String cellPhone = null;
                String officePhone = null;
                String homePhone = null;
                String email = null;

                String status = null;
                // !contact.isNull("s") ? contact.getString("s") : null;
                String avatarUrl = null;
                // !contact.isNull("a") ? contact.getString("a") : null;

                if (!contact.isNull("business_address"))
                {
                    JSONObject address = contact.getJSONObject("business_address");

                    cellPhone = safeGetString(address, "mobile_phone");
                    officePhone = safeGetString(address, "phone");
                    email = safeGetString(address, "email");
                }

                if (!contact.isNull("private_address"))
                {
                    JSONObject address = contact.getJSONObject("private_address");

                    homePhone = safeGetString(address, "phone");
                }

                final boolean deleted = !contact.isNull("d") ? contact.getBoolean("d") : false;
                final long syncState = !contact.isNull("x") ? contact.getLong("x") : 0;

                return new RawContact(userName, null, firstName, lastName, cellPhone, officePhone, homePhone, email, status,
                        avatarUrl, deleted, serverContactId, rawContactId, syncState, false);
            }
        }
        catch (final Exception ex)
        {
            Log.i(TAG, "Error parsing JSON contact object" + ex.toString());
        }
        return null;
    }

    /**
     * Creates and returns RawContact instance from all the supplied parameters.
     */
    public static RawContact create(String fullName, String firstName, String lastName, String cellPhone, String officePhone,
            String homePhone, String email, String status, boolean deleted, long rawContactId, long serverContactId)
    {
        return new RawContact(null, fullName, firstName, lastName, cellPhone, officePhone, homePhone, email, status, null,
                deleted, serverContactId, rawContactId, -1, true);
    }

    /**
     * Creates and returns a User instance that represents a deleted user. Since
     * the user is deleted, all we need are the client/server IDs.
     *
     * @param clientUserId
     *            The client-side ID for the contact
     * @param serverUserId
     *            The server-side ID for the contact
     * @return a minimal User object representing the deleted contact.
     */
    public static RawContact createDeletedContact(long rawContactId, long serverContactId)
    {
        return new RawContact(null, null, null, null, null, null, null, null, null, null, true, serverContactId, rawContactId,
                -1, true);
    }
}
