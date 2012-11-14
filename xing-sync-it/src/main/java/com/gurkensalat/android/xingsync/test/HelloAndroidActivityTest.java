package com.gurkensalat.android.xingsync.test;

import android.test.ActivityInstrumentationTestCase2;
import com.gurkensalat.android.xingsync.*;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<HelloAndroidActivity> {

    public HelloAndroidActivityTest() {
        super(HelloAndroidActivity.class); 
    }

    public void testActivity() {
        HelloAndroidActivity activity = getActivity();
        assertNotNull(activity);
    }
}

