package com.mdo.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.mdo.R;
import com.mdo.activity.fragment.DisclaimerDialogFragment;

public class DisclaimerActivity extends BaseActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);	
		init();
	}
	
	private void init(){
		initBackBtn();
		WebView wv = (WebView) findViewById(R.id.wv);
		// wv.getSettings()// ø…”√JS
//		WebSettings ws = wv.getSettings();
//		ws.setJavaScriptEnabled(true);
		wv.setScrollBarStyle(0);
		wv.loadUrl("file:///android_asset/disclaimer.html" );
	}
	public void finish() {
		DisclaimerDialogFragment dialog=new DisclaimerDialogFragment(false);
		dialog.show(getSupportFragmentManager(), "disclaimer");

	}
	public void doFinish() {
		super.finish();
	}
}
