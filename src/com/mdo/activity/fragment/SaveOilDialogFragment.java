package com.mdo.activity.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ImageView;

import com.mdo.R;
import com.mdo.activity.SavedOilActivity;
import com.mdo.domainobject.Oil;
import com.mdo.domainobject.OilDao;

public class SaveOilDialogFragment extends DialogFragment {

	private OilDao dao;
	private Oil oil;
	private ImageView view;
	private SavedOilActivity saveOilActivity;

	public SaveOilDialogFragment(Oil oil, OilDao dao, ImageView view,SavedOilActivity saveOilActivity) {
		this.dao = dao;
		this.oil = oil;
		this.view = view;
		this.saveOilActivity=saveOilActivity;
	}
	
	
	public SaveOilDialogFragment(Oil oil, OilDao dao) {
		this.dao = dao;
		this.oil = oil;
	}
	
	public SaveOilDialogFragment(Oil oil, OilDao dao, ImageView view) {
		this.dao = dao;
		this.oil = oil;
		this.view = view;
	}


	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		if (oil.getIsSaved() == null || !oil.getIsSaved()) {
			builder.setMessage(R.string.save_oil_msg);
		} else {
			builder.setMessage(R.string.un_save_oil_msg);
		}
		builder.setPositiveButton("YES", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				boolean saved = oil.getIsSaved() != null && oil.getIsSaved();
				if(view!=null){
					if (saved) {
						view.setImageResource(R.drawable.not_saved_oil);
					} else {
						view.setImageResource(R.drawable.saved_oil);
					}
				}
				oil.setIsSaved(!saved);
				dao.update(oil);
				dialog.dismiss();
				if(saveOilActivity!=null){
					saveOilActivity.loadData();
				}
			}

		});
		builder.setNegativeButton("NO", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}

		});
		return builder.create();
	}
}
