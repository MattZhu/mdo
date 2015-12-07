package com.mdo.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.mdo.R;
import com.mdo.activity.BaseActivity;
import com.mdo.activity.DataLoadingTask;
import com.mdo.activity.FacebookBaseActivity;
import com.mdo.activity.SavedOilActivity;
import com.mdo.activity.SettingActivity;

public class SymptomDetailBottomFragment extends Fragment implements OnClickListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_symptom_detail_bottom, null);
		initView(view);
		return view;

	}

	private void initView(View view) {
		View v=view.findViewById(R.id.saved_book);
		v.setOnClickListener(this);
		v=view.findViewById(R.id.setting_icon);
		v.setOnClickListener(this);
		v=view.findViewById(R.id.refresh);
		v.setOnClickListener(this);
		v=view.findViewById(R.id.share_to_fb);
		v.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.saved_book:
			Intent i=new Intent(this.getActivity(),SavedOilActivity.class);
			this.getActivity().startActivity(i);
			break;
		case R.id.setting_icon:

			Intent settingIntent=new Intent(this.getActivity(),SettingActivity.class);
			this.getActivity().startActivity(settingIntent);
			break;
		case R.id.refresh:
			DataLoadingTask.refresh((BaseActivity) this.getActivity());
			break;
		case R.id.share_to_fb:
			FacebookBaseActivity activity=(FacebookBaseActivity)this.getActivity();
			activity.shareToFB("");
		}
		
	}

}
