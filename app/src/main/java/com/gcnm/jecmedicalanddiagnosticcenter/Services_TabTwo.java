package com.gcnm.jecmedicalanddiagnosticcenter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Services_TabTwo extends Fragment {

  public View view;
  public WebView webView;

  @SuppressLint("SetJavaScriptEnabled")
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.activity_services_tabtwo, container, false);
    String strUrl = "file:///android_asset/services.html";
    webView = view.findViewById(R.id.webView_services);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.setWebViewClient(new WebViewClient());
    webView.setWebChromeClient(new WebChromeClient());
    webView.getSettings().setAllowContentAccess(true);
    webView.getSettings().setAppCacheEnabled(true);
    webView.getSettings().setUseWideViewPort(true);
    webView.loadUrl(strUrl);

    return view;


  }
}