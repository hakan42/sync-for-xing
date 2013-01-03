package com.gurkensalat.android.xingsync;

import android.content.Intent;

/**
 * Helper for creating intents
 */
public class Intents
{
    /** The tag used to log to adb console. **/
    private final static String TAG = Intents.class.getName().substring(Constants.PACKAGE_PREFIX_LENGTH);

    /**
     * Prefix for all intents created
     */
    public static final String INTENT_PREFIX = "com.gurkensalat.android.xingsync.";

    /**
     * Prefix for all extra data added to intents
     */
    public static final String INTENT_EXTRA_PREFIX = INTENT_PREFIX + "extra.";

    /**
     * Builder for generating an intent configured with extra data such as an
     * issue, repository, or gist
     */
    public static class Builder
    {
        private Intent intent;

        /**
         * Get built intent
         *
         * @return intent
         */
        public Intent toIntent()
        {
            return intent;
        }
    }
}
