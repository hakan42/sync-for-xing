package com.gurkensalat.android.xingsync.keys;

public class XingOAuthKeys
{
    /**
     * OAuth consumer key. To be obtained from dev.xing.com
     */
    public static final String CONSUMER_KEY          = "${xingsync.consumer.key}";

    /**
     * OAuth consumer secret. To be obtained from dev.xing.com
     */
    public static final String CONSUMER_SECRET       = "${xingsync.consumer.secret}";

    /**
     * OAuth Request URL. Defined in API documentation at dev.xing.com
     */
    public static final String REQUEST_URL           = "${xingapi.request_token_path}";

    /**
     * OAuth Authorization URL. Defined in API documentation at dev.xing.com
     */
    public static final String AUTHORIZE_URL         = "${xingapi.authorize_path}";

    /**
     * OAuth Token Access URL. Defined in API documentation at dev.xing.com
     */
    public static final String ACCESS_URL            = "${xingapi.access_token_path}";

    /**
     * Blurb.
     */
    public static final String OAUTH_CALLBACK_SCHEME = "x-oauthflow";

    /**
     * Blurb.
     */
    public static final String OAUTH_CALLBACK_HOST   = "callback";

    /**
     * Blurb.
     */
    public static final String OAUTH_CALLBACK_URL    = OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;

    /**
     * Blurb.
     */
    public static final String ENCODING              = "UTF-8";

    /**
     * Blurb.
     */
    public static final String SCOPE                 = "furbl";
}
