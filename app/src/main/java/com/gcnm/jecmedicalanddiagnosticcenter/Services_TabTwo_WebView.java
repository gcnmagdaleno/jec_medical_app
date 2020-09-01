package com.gcnm.jecmedicalanddiagnosticcenter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class Services_TabTwo_WebView extends AppCompatActivity {

  public WebView webView;

  @SuppressLint("SetJavaScriptEnabled")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_services_tabtwo);

    String strUrl = "file:///android_asset/services.html";
    webView = findViewById(R.id.webView_services);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.loadUrl(strUrl);
  }

}