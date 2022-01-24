package com.poppop.RNReactNativeSharedGroupPreferences;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

public class SharedDatabase extends SQLiteOpenHelper {
    private static final String DEBUG_TAG = "SharedDatabase";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "shared_data";

    public static final String TABLE_NAME = "data";
    public static final String COL_ID = "_id";
    public static final String COL_KEY = "_key";
    public static final String COL_VALUE = "value";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_KEY + " TEXT NOT NULL, "
            + COL_VALUE + " TEXT);";

    private static final String DB_SCHEMA = CREATE_TABLE;

    public SharedDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public SharedDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SharedDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @TargetApi(Build.VERSION_CODES.P)
    public SharedDatabase(Context context, String name, int version, SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_SCHEMA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DEBUG_TAG, "Upgrading database. Existing contents will be lost. ["
                + oldVersion + "]->[" + newVersion + "]");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}