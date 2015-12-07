package com.mdo.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import com.mdo.R;

public class LicenseCheckDialogFragment extends DialogFragment {	
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		ProgressDialog dialog=ProgressDialog.show(this.getActivity(), "Checking License...", null);
		
		dialog.setContentView(R.layout.progress_dialog);
		TextView message=(TextView)dialog.findViewById(R.id.message);
		message.setText(R.string.checking_license);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		return dialog;
	}
	
}
