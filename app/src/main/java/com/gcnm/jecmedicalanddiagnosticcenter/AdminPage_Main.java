package com.gcnm.jecmedicalanddiagnosticcenter;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPage_Main extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_page__main);


  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    startActivity(new Intent(AdminPage_Main.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP));
    AdminPage_Main.this.finish();

  }

}