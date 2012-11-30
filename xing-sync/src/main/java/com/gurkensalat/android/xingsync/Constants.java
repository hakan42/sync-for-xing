package com.gurkensalat.android.xingsync;

import android.provider.ContactsContract.Data;

public class Constants
{

    /**
     * Account type string.
     */
    public static final String ACCOUNT_TYPE = "com.gurkensalat.android.xingsync";

    /**
     * Provider authority
     */
    public static final String PROVIDER_AUTHORITY = "com.gurkensalat.android.xingcontacts";

    /**
     * Authtoken type string.
     */
    public static final String AUTHTOKEN_TYPE = "com.gurkensalat.android.xingsync";

    /**
     * What should the default group name be?
     */
    public static final String DEFAULT_GROUP_NAME = "XING";

    /**
     * Do we mock anything at all? Should be a preference item.
     */
    public static final Boolean MOCK_MODE = true;

    /**
     * Do we mock network access? Should be a preference item.
     */
    public static final Boolean MOCK_NETWORK_ACCESS = true;

    /**
     * MIME-type used when storing a profile {@link Data} entry.
     */
    public static final String MIME_PROFILE = "vnd.android.cursor.item/vnd.com.gurkensalat.android.xingsync.profile";

    public static final String DATA_PID = Data.DATA1;

    public static final String DATA_SUMMARY = Data.DATA2;

    public static final String DATA_DETAIL = Data.DATA3;
}
