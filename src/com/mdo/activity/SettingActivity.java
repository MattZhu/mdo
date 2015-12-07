package com.mdo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.mdo.R;

public class SettingActivity extends BaseActivity implements OnClickListener {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		intiView();
	}

	private boolean isTop = true;

	private int heightToScroll = 0;

	private ScrollView scrollView = null;

	private WebView wv = null;

	@SuppressLint("NewApi")
	private void intiView() {
		initBackBtn();
		
		Button btn=(Button)this.findViewById(R.id.email_btn);
		btn.setOnClickListener(this);
		
		btn=(Button)this.findViewById(R.id.rate_app_btn);
		btn.setOnClickListener(this);
		
		btn=(Button)this.findViewById(R.id.gift_app_btn);
		btn.setOnClickListener(this);
		
		btn=(Button)this.findViewById(R.id.like_on_fb_btn);
		btn.setOnClickListener(this);
		
		wv = (WebView) findViewById(R.id.reference);
		// wv.getSettings()// ¿ÉÓÃJS
		// WebSettings ws = wv.getSettings();
		// ws.setJavaScriptEnabled(true);
		wv.loadUrl("file:///android_asset/references.html");
		// wv.setWebViewClient(new WebViewClient(){
		// });
		WebSettings ws = wv.getSettings();
		// ws.setJavaScriptEnabled(true);
		ws.setBuiltInZoomControls(true);
		if (Build.VERSION.SDK_INT >= 11) {
			ws.setDisplayZoomControls(true);
		}
		wv.setScrollBarStyle(0);
		scrollView = (ScrollView) findViewById(R.id.scroll_view);

		ImageView imageView = (ImageView) findViewById(R.id.right_btn);
		imageView.setImageResource(R.drawable.switch_btn);
		imageView.setVisibility(View.VISIBLE);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isTop) {
					int[] locations = new int[2];
					wv.getLocationOnScreen(locations);
//					Log.d("SettingActivity", "WebView on Screen:" + locations[0]+","+locations[1]);
					heightToScroll = locations[1];
					scrollView.getLocationOnScreen(locations);
//					Log.d("SettingActivity", "scrollView on Screen:" + locations[0]+","+locations[1]);
					
					heightToScroll = heightToScroll - locations[1]+scrollView.getScrollY();
//					Log.d("SettingActivity", "heightToScroll:" + heightToScroll);

					scrollView.smoothScrollTo(0, heightToScroll);
				} else {
					scrollView.smoothScrollTo(0, 0);
				}
				isTop = !isTop;
			}

		});
		// wv.getSettings().setLoadWithOverviewMode(true);
		// wv.getSettings().setUseWideViewPort(true);
	}
	


	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.email_btn:
			tellFreind();
			break;
		case R.id.rate_app_btn:
			rateApp();
			break;
		case R.id.like_on_fb_btn:
			likeOnFaceBook();
			break;
		case R.id.gift_app_btn:
			giftApp();
			break;
		}
		
	}

	private void giftApp() {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.mdo" )));
	}



	private void likeOnFaceBook() {
		// TODO Auto-generated method stub
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/mydoterraoils" )));

	}



	private void tellFreind(){
		Intent myIntent = new Intent(android.content.Intent.ACTION_SEND);
		myIntent.setType("plain/text");
//		myIntent.putExtra(android.content.Intent.EXTRA_EMAIL, reciver);
		myIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out the md¨­ App");

		myIntent.putExtra(android.content.Intent.EXTRA_TEXT, "The md¨­ App available for iPhone, iPad and iPod Touch: The fastest & most efficient reference tool for Certified Pure Therapeutic Grade Oils. Grow your business, educate your downline & customers, and increase your sales volume!\n\nAvailable on the App Store:\nhttp://itunes.apple.com/us/app/?ls=1&mt=8\n\nFind us on Facebook:\nhttp://www.facebook.com/mydoterraoils");
		startActivity(Intent.createChooser(myIntent, "Email"));

	}
	
	private void rateApp() {
		// TODO Auto-generated method stub
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.doterra" )));
	}
}
