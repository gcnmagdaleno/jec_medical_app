package com.gcnm.jecmedicalanddiagnosticcenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.gcnm.jecmedicalanddiagnosticcenter.ui.main.ServicesTabbed_SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class Services_Tabbed extends AppCompatActivity {
  public WebView webView;

  public SharedPreferences sharedPreference;
  public SharedPreferences.Editor sharedEditor;

  @SuppressLint({"SetJavaScriptEnabled", "CommitPrefEdits"})
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_services_tabbed);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    sharedPreference = getSharedPreferences("__savedData", MODE_PRIVATE);
    sharedEditor = sharedPreference.edit();


    Toast.makeText(this, "Last Activity: " + sharedPreference.getString("strLastActivity", ""), Toast.LENGTH_SHORT).show();

    if (sharedPreference.getString("strLastActivity", "").equals("MainActivity")) {
//      layout_Progressbar.setVisibility(View.GONE);
    } else {
//      layout_Progressbar.setVisibility(View.VISIBLE);
    }


    ServicesTabbed_SectionsPagerAdapter sectionsPagerAdapter = new ServicesTabbed_SectionsPagerAdapter(this, getSupportFragmentManager());
    ViewPager viewPager = findViewById(R.id.view_pager);
    viewPager.setAdapter(sectionsPagerAdapter);
    TabLayout tabs = findViewById(R.id.tabs);
    tabs.setupWithViewPager(viewPager);
    final FloatingActionButton fab = findViewById(R.id.fab);


    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();


      }
    });

  }

  public void __saveData(String strLastActivity) {


    sharedEditor.putString("strLastActivity", strLastActivity);
    sharedEditor.apply();
    sharedEditor.commit();
  }

  @Override
  public void onBackPressed() {
//    startActivity(new Intent(this, MainActivity.class));
//    Services_Tabbed.this.finish();

    startActivity(new Intent(Services_Tabbed.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK));
    Services_Tabbed.this.finish();
    __saveData("Services_Tabbed");
  }

  @Override
  protected void onUserLeaveHint() {

    __saveData("Services_Tabbed");
    super.onUserLeaveHint();
  }

  @Override
  protected void onPause() {

    __saveData("Services_Tabbed");
    super.onPause();
  }
}