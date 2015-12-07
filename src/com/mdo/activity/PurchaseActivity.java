package com.mdo.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mdo.R;

public class PurchaseActivity extends BaseActivity {
	
	private ProgressDialog progressBar;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		init();
	}

	@SuppressLint("NewApi")
	private void init() {
		initBackBtn();
		
		progressBar = ProgressDialog.show(this, null, "Loading.......");

		
		WebView wv = (WebView) findViewById(R.id.wv);
		wv.setWebViewClient(new WebViewClient(){
			public void onPageFinished(WebView view, String url) {
                Log.i("PurchaseActivity", "Finished loading URL: " + url);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }
		});
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
		wv.loadUrl("http://shop.mydoterraoils.com");
	}
}
