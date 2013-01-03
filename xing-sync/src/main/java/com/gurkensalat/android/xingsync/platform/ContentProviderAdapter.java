package com.gurkensalat.android.xingsync.platform;

import com.gurkensalat.android.xingsync.Constants;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Content provider adapter that does nothing
 */
public class ContentProviderAdapter extends ContentProvider
{
    /** The tag used to log to adb console. **/
    private final static String TAG = ContentProviderAdapter.class.getName().substring(Constants.PACKAGE_PREFIX_LENGTH);

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        return 0;
    }

    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        return null;
    }

    @Override
    public boolean onCreate()
    {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return 0;
    }
}
