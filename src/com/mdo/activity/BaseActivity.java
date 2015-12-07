package com.mdo.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.mdo.R;

public class BaseActivity extends ActionBarActivity {
	@SuppressLint("NewApi")
	@Override
	protected void onResume() {
		super.onResume();
		if (Build.VERSION.SDK_INT > 15) {
			ActionBar actionBar = this.getSupportActionBar();
			if(actionBar!=null)
			actionBar.hide();
		}
	}
	
	protected void initBackBtn() {
		ImageView backBtn = (ImageView) findViewById(R.id.back_btn);
		if(backBtn!=null){
			backBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}
	
	public void refresh(){
		
	}
}
