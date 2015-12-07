package com.mdo.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.mdo.R;

public class AboutCGPTActivity extends BaseActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		init();
	}

	@SuppressLint("NewApi")
	private void init() {
		initBackBtn();
		WebView wv = (WebView) findViewById(R.id.wv);
		// wv.getSettings()// ¿ÉÓÃJS
		WebSettings ws = wv.getSettings();
		// ws.setJavaScriptEnabled(true);
		ws.setBuiltInZoomControls(true);
		if (Build.VERSION.SDK_INT >= 11) {
			ws.setDisplayZoomControls(true);
		}
		wv.setScrollBarStyle(0);
		wv.getSettings().setLoadWithOverviewMode(true);
		wv.getSettings().setUseWideViewPort(true);
		wv.loadUrl("file:///android_asset/about.html");
	}
}