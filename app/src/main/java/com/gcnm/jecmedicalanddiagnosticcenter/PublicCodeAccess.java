package com.gcnm.jecmedicalanddiagnosticcenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class PublicCodeAccess {

  public String haha() {
    return "nice gumagana";
  }


  public static String uniqueID = null;
  public static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

  @SuppressLint("ApplySharedPref")
  public synchronized static String android_id(Context context) {
    if (uniqueID == null) {
      SharedPreferences sharedPrefs = context.getSharedPreferences(
          PREF_UNIQUE_ID, Context.MODE_PRIVATE);
      uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
      if (uniqueID == null) {
        uniqueID = UUID.randomUUID().toString();
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(PREF_UNIQUE_ID, uniqueID);
        editor.commit();
        editor.apply();
      }
    }
    return uniqueID;
  }

  public static String current_date(Date date) {
    return new SimpleDateFormat("EEE - MMM dd, yyyy", Locale.getDefault()).format(date);
  }
}
