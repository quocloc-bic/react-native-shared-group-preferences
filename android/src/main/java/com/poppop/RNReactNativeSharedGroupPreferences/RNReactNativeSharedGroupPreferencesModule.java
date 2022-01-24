
package com.poppop.RNReactNativeSharedGroupPreferences;

import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;



public class RNReactNativeSharedGroupPreferencesModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private final String TAG = "RNSharedGroupPrefs";

  public RNReactNativeSharedGroupPreferencesModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNReactNativeSharedGroupPreferencesModule";
  }

  @ReactMethod
  public void isAppInstalledAndroid(String packageName, final Callback callback) {
    PackageManager pm = reactContext.getPackageManager();
    try {
      pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
      callback.invoke(true);
    } catch (Exception e) {
      callback.invoke(false);
    }
  }

  @ReactMethod
  public void setItem(String key, String value, String appGroup, final Callback callback) {
    try {
      String URL = "content://" +  appGroup + ".SharedProvider/data";
      Uri CONTENT_URI = Uri.parse(URL);

      ContentValues values = new ContentValues();
      values.put(SharedDatabase.COL_KEY, key);
      values.put(SharedDatabase.COL_VALUE, value);
      String selectionClause = SharedDatabase.COL_KEY + " LIKE ?";
      String[] selectionArgs = {key};
      Cursor c = reactContext.getContentResolver().query(CONTENT_URI, null, selectionClause, selectionArgs, null);
      if (c.moveToFirst()) {
        reactContext.getContentResolver().update(CONTENT_URI, values, selectionClause, selectionArgs);
      } else {
        reactContext.getContentResolver().insert(CONTENT_URI, values);
      }
      c.close();
      callback.invoke(null, "");
    } catch (Exception e) {
      callback.invoke(null, "");
      Log.e(TAG, e.toString());
    }
  }

  @ReactMethod
  public void getItem(String key, String appGroup, final Callback callback) {
    try {
      String URL = "content://" + appGroup + ".SharedProvider/data";

      Uri CONTENT_URI = Uri.parse(URL);
      String selectionClause = SharedDatabase.COL_KEY + " LIKE ?";
      String[] selectionArgs = {key};
      Cursor c = reactContext.getContentResolver().query(CONTENT_URI, null, selectionClause, selectionArgs, null);
      String jsonString = "";

      int index = c.getColumnIndex(SharedDatabase.COL_VALUE);
      while (c.moveToNext()) {
        jsonString = jsonString + c.getString(index);
      }

      callback.invoke(null, jsonString);
    }catch (Exception e){
      callback.invoke(null, "");
      Log.e(TAG, e.getMessage());
    }
  }
}

