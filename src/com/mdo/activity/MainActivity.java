package com.mdo.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.Policy;
import com.google.android.vending.licensing.ServerManagedPolicy;
import com.mdo.R;
import com.mdo.domainobject.DaoSession;
import com.mdo.utils.DatabaseHelper;
import com.mdo.utils.ListAdapter;
import com.mdo.utils.ListItemViewCreator;

public class MainActivity extends BaseActivity implements
		ListItemViewCreator<OpItem> {

	private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBC"
			+ "gKCAQEAidc3KXfZJ3DPB0rSoc8rqhyzJ8vXbGUrcC1GZ5/sr4gSc6TgmII1phCt/0QKtu2izoJU3"
			+ "SlDRDr9nWCod/2b8/6VK2z7RQw/a75r4u6/Pc8p/q5IAKYFVMIOt0Hicr4u4lY5HTDrDMUYxKcgJ"
			+ "V7rvV8oYuINN9eN/bpNyOp+ZQ2i6ThKTe61RCRXe0Hh5UeTjPParYyhkK6yU9Ryv1onz2QvCzEpH"
			+ "vluJn0LKOMJ5pdX2j/BHARtvugWlWUfWmH27G9tfLwYEg1tKYTlu7krg+14evyrI2ZT+39tu75eW"
			+ "ShhDSt6heZmIrd4FlQcsP8v8NO673OBJgAbzBZPsobZhQIDAQAB";

	private static final byte[] SALT = new byte[] { -46, 61, 30, -125, -103,
			-57, 74, -64, 51, 85, -95, -45, 69, -117, -36, -113, -11, 32, -64,
			89 };

	private ListAdapter<OpItem> adapter;

	private DaoSession session;

	private LicenseCheckerCallback mLicenseCheckerCallback;
	private LicenseChecker mChecker;

	private LicenseCheckDialogFragment licenseCheckDialog;

	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		intiView();

		// Construct the LicenseCheckerCallback. The library calls this when
		// done.
		mLicenseCheckerCallback = new MyLicenseCheckerCallback();

		String deviceId = Secure.getString(getContentResolver(),
				Secure.ANDROID_ID);
		// Construct the LicenseChecker with a Policy.
		mChecker = new LicenseChecker(this.getApplicationContext(), new ServerManagedPolicy(this,
				new AESObfuscator(SALT, getPackageName(), deviceId)),
				BASE64_PUBLIC_KEY // Your public licensing key.
		);

		session = DatabaseHelper.getDaoSession(this);

		mHandler = new Handler();
		doCheck();
		//DataLoadingTask.loadData(session, MainActivity.this, true);

	}

	private void doCheck() {
		licenseCheckDialog = new LicenseCheckDialogFragment();
		licenseCheckDialog.setCancelable(false);
		licenseCheckDialog.show(getSupportFragmentManager(), "checking");
		mChecker.checkAccess(mLicenseCheckerCallback);
	}

	private void intiView() {
		adapter = new ListAdapter<OpItem>(this, this);
		List<OpItem> data = new ArrayList<OpItem>();
		data.add(new OpItem(R.drawable.search_symptom, R.string.search,
				SymptomSearchActivity.class));

		data.add(new OpItem(R.drawable.oil_icon, R.string.oilprod,
				OilSearchActivity.class));

		data.add(new OpItem(R.drawable.saved_oil, R.string.mysaved,
				SavedOilActivity.class));

		data.add(new OpItem(R.drawable.quick_reference, R.string.quickref,
				QuickReferenceActivity.class));

		data.add(new OpItem(R.drawable.about_beaker, R.string.about_cptg,
				AboutCGPTActivity.class));

		data.add(new OpItem(R.drawable.disclaimer_pager, R.string.disclaimer,
				DisclaimerActivity.class));

		data.add(new OpItem(R.drawable.purchase_cart, R.string.purchase_oils,
				PurchaseActivity.class));

		adapter.setData(data);
		ListView v = (ListView) this.findViewById(R.id.op_list);
		v.setDividerHeight(0);
		v.setAdapter(adapter);
		v.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Class<?> activityClass = ((OpItem) arg0.getAdapter().getItem(
						arg2)).getActivityClass();
				if (activityClass != null) {
					Intent i = new Intent(MainActivity.this, activityClass);
					startActivity(i);
				}

			}
		});
		ImageView settings = (ImageView) this.findViewById(R.id.setting);
		settings.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, SettingActivity.class);
				startActivity(i);
			}

		});
		// create dialog and show

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View createOrUpdateView(OpItem item, View convertView, int position,
			ViewGroup parent) {
		View result = null;
		if (convertView != null) {
			result = convertView;
		} else {
			LayoutInflater inflater = (LayoutInflater) this
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			result = inflater.inflate(R.layout.op_list_item, null);
		}
		ImageView icon = (ImageView) result.findViewById(R.id.op_item_icon);
		icon.setImageResource(item.getIcon());
		TextView text = (TextView) result.findViewById(R.id.op_item_text);
		text.setText(item.getText());
		return result;
	}

	public static void showHashKey(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					"com.doterra", PackageManager.GET_SIGNATURES); // Your
																	// package
																	// name here
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.i("KeyHash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {
		} catch (NoSuchAlgorithmException e) {
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mChecker.onDestroy();
	}

	private void allowAccess() {
		mHandler.post(new Runnable() {
			public void run() {
				licenseCheckDialog.dismiss();
				DataLoadingTask.loadData(session, MainActivity.this, true);
			}
		});
	}

	protected Dialog onCreateDialog(int id) {
		final boolean bRetry = id == 1;
		return new AlertDialog.Builder(this)
				.setTitle(R.string.unlicensed_dialog_title)
				.setMessage(
						bRetry ? R.string.unlicensed_dialog_retry_body
								: R.string.unlicensed_dialog_body)
				.setPositiveButton(
						bRetry ? R.string.retry_button : R.string.buy_button,
						new DialogInterface.OnClickListener() {
							boolean mRetry = bRetry;

							public void onClick(DialogInterface dialog,
									int which) {
								if (mRetry) {
									doCheck();
								} else {
									Intent marketIntent = new Intent(
											Intent.ACTION_VIEW,
											Uri.parse("http://market.android.com/details?id="
													+ getPackageName()));
									startActivity(marketIntent);
								}
							}
						})
				.setNegativeButton(R.string.quit_button,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						})
				.setCancelable(false).create();
	}

	private void displayDialog(final boolean showRetry) {
		mHandler.post(new Runnable() {
			public void run() {
				licenseCheckDialog.dismiss();
				showDialog(showRetry ? 1 : 0);
			}
		});
	}

	private class MyLicenseCheckerCallback implements LicenseCheckerCallback {
		public void allow(int reason) {
			if (isFinishing()) {
				// Don't update UI if Activity is finishing.
				return;
			}
			// Should allow user access.
			allowAccess();
		}

		public void dontAllow(int reason) {
			if (isFinishing()) {
				// Don't update UI if Activity is finishing.
				return;
			}
//			allowAccess();
			displayDialog(reason == Policy.RETRY);

		}

		@Override
		public void applicationError(int errorCode) {
			// TODO Auto-generated method stub

		}
	}
}
