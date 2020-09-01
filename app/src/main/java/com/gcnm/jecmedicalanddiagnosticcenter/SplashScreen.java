package com.gcnm.jecmedicalanddiagnosticcenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity {

  public PublicCodeAccess code;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash_screen);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    code = new PublicCodeAccess();

    Toast.makeText(getApplicationContext(), code.haha(), Toast.LENGTH_LONG).show();
    Toast.makeText(getApplicationContext(), PublicCodeAccess.current_date(new Date()), Toast.LENGTH_LONG).show();


    Toast.makeText(getApplicationContext(), PublicCodeAccess.android_id(getApplicationContext()), Toast.LENGTH_LONG).show();


    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        Looper.prepare();
        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.strAdmin2PW) + "\n" + strLoggedIn(), Toast.LENGTH_LONG).show();


        if (strLoggedIn().toUpperCase().equals("YES")) {
          Toast.makeText(getApplicationContext(), "Admin logged in, welcome!", Toast.LENGTH_LONG).show();

          if (isAdmin().toUpperCase().equals("YES")) {
            startActivity(new Intent(SplashScreen.this, ViewAppointments.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            SplashScreen.this.finish();
          } else {
            startActivity(new Intent(SplashScreen.this, Services_ChooseAService.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            SplashScreen.this.finish();
          }

//          startActivity(new Intent(SplashScreen.this, AdminPage_Main.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP));

        } else {
          Toast.makeText(getApplicationContext(), "Not logged in!", Toast.LENGTH_LONG).show();
          startActivity(new Intent(getApplicationContext(), MainActivity.class));
          SplashScreen.this.finish();
        }
      }
    }, 1000);


  }

  @SuppressLint("CommitPrefEdits")
  public String strLoggedIn() {

    SharedPreferences sharedPreference;
    SharedPreferences.Editor sharedEditor;

    sharedPreference = getSharedPreferences("__savedData", MODE_PRIVATE);
    sharedEditor = sharedPreference.edit();


    return String.valueOf(sharedPreference.getString("strLoggedIn", ""));

  }

  @SuppressLint({"CommitPrefEdits", "ApplySharedPref"})
  public String isAdmin() {

    SharedPreferences sharedPreference;
    SharedPreferences.Editor sharedEditor;

    sharedPreference = getSharedPreferences("__savedData", MODE_PRIVATE);
    sharedEditor = sharedPreference.edit();
    sharedEditor.commit();
    sharedEditor.apply();

    return String.valueOf(sharedPreference.getString("isAdmin", ""));

  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    SplashScreen.this.finish();
  }
}