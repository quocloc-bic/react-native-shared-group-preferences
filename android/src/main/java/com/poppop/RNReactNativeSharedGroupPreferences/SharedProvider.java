package com.poppop.RNReactNativeSharedGroupPreferences;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class SharedProvider extends ContentProvider {
    private SharedDatabase mDb;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDb = new SharedDatabase(context);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(SharedDatabase.TABLE_NAME);
        Cursor cursor = queryBuilder.query(mDb.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = mDb.getWritableDatabase();
        long rowID = db.insert(SharedDatabase.TABLE_NAME, "", contentValues);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(uri, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLiteException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqlDB = mDb.getWritableDatabase();

        int rowsAffected = sqlDB.delete(SharedDatabase.TABLE_NAME,
                selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase sqlDB = mDb.getWritableDatabase();
        int count = sqlDB.update(SharedDatabase.TABLE_NAME, contentValues, selection, selectionArgs);
        return  count;
    }
}