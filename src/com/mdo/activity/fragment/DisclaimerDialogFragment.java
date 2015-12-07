package com.mdo.activity.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.mdo.R;
import com.mdo.activity.DisclaimerActivity;

public class DisclaimerDialogFragment extends DialogFragment {
	private boolean showFullText = true;

	public DisclaimerDialogFragment(boolean showFullText) {
		this.showFullText = showFullText;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.dialog_disclaimer_msg);
		builder.setPositiveButton(R.string.dialog_dicalaimer_agree,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// FIRE ZE MISSILES!
						dialog.dismiss();
						if (!showFullText) {
							DisclaimerActivity a=(DisclaimerActivity)getActivity();
							a.doFinish();
						}
					}
				});
		if (showFullText) {
			builder.setNegativeButton(R.string.dialog_disclaimer_full_text,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							Intent i=new Intent(getActivity(),DisclaimerActivity.class);
							startActivity(i);
						}
					});
		}
		return builder.create();
	}

}
