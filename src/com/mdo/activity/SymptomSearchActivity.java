package com.mdo.activity;

import java.util.ArrayList;
import java.util.List;

import org.kobjects.base64.Base64;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mdo.R;
import com.mdo.billing.util.IabHelper;
import com.mdo.billing.util.IabResult;
import com.mdo.billing.util.Inventory;
import com.mdo.billing.util.Purchase;
import com.mdo.utils.ListAdapter;
import com.mdo.utils.ListItemViewCreator;

public class SymptomSearchActivity extends BaseActivity implements
		ListItemViewCreator<OpItem>, OnItemClickListener {
	private static final String TAG = "SymptomBilling";

	private ListAdapter<OpItem> adapter;

	private String types[] = { "G", "P", "C", "A", "" };

	private IabHelper mHelper;

	private Inventory inventory;

	private static final String PURCHASE_ITEM_G = "general";

	private static final String PURCHASE_ITEM_P = "pregnancy";

	private static final String PURCHASE_ITEM_C = "children";

	private static final String PURCHASE_ITEM_A = "animal";

	protected static final int BUY_ITEM = 101;

	private boolean isPro = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_symptom_search);
		init();
		Intent serviceIntent = new Intent(
				"com.android.vending.billing.InAppBillingService.BIND");
		serviceIntent.setPackage("com.android.vending");
		if (!isPro) {
			setupInAppBilling();
		}
	}

	private void setupInAppBilling() {
		String base64EncodedPublicKey = Base64
				.encode("-1mdo0_0android1_1billing2_2key_3".getBytes());
		mHelper = new IabHelper(this, base64EncodedPublicKey);

		// enable debug logging (for a production application, you should set
		// this to false).
		mHelper.enableDebugLogging(true);

		// Start setup. This is asynchronous and the specified listener
		// will be called once setup completes.
		Log.d(TAG, "Starting setup.");
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				Log.d(TAG, "Setup finished.");

				if (!result.isSuccess()) {
					// Oh noes, there was a problem.
					Log.e(TAG, "**** Problem setting up in-app billing: "
							+ result.toString());

					// complain("Problem setting up in-app billing: " + result);
					return;
				}

				// Have we been disposed of in the meantime? If so, quit.
				if (mHelper == null)
					return;

				// IAB is fully set up. Now, let's get an inventory of stuff we
				// own.
				Log.d(TAG, "Setup successful. Querying inventory.");
				if (!loadData("loadPurchased")) {
					List<String> moreSkus = new ArrayList<String>();
					moreSkus.add(PURCHASE_ITEM_G);
					moreSkus.add(PURCHASE_ITEM_P);
					moreSkus.add(PURCHASE_ITEM_C);
					moreSkus.add(PURCHASE_ITEM_A);
					mHelper.queryInventoryAsync(true, moreSkus,
							mGotInventoryListener);
				}
			}
		});
	}

	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result,
				Inventory inventory) {
			Log.d(TAG, "Query inventory finished.");

			// Have we been disposed of in the meantime? If so, quit.
			if (mHelper == null)
				return;

			// Is it a failure?
			if (result.isFailure()) {
				Log.e(TAG, "**** Symptom Billing Error: " + result.toString());

				// complain("Failed to query inventory: " + result);
				return;
			}

			Log.d(TAG, "Query inventory was successful.");
			SymptomSearchActivity.this.inventory = inventory;

			/*
			 * Check for items we own. Notice that for each purchase, we check
			 * the developer payload to see if it's correct! See
			 * verifyDeveloperPayload().
			 */

			// Do we have the premium upgrade?
			if (inventory.hasPurchase(PURCHASE_ITEM_G)) {
				saveData(PURCHASE_ITEM_G, true);
			}
			if (inventory.hasPurchase(PURCHASE_ITEM_P)) {
				saveData(PURCHASE_ITEM_P, true);
			}
			if (inventory.hasPurchase(PURCHASE_ITEM_C)) {
				saveData(PURCHASE_ITEM_C, true);
			}
			if (inventory.hasPurchase(PURCHASE_ITEM_A)) {
				saveData(PURCHASE_ITEM_A, true);
			}
			saveData("loadPurchased", true);
			updateUI();
			// setWaitScreen(false);
			Log.d(TAG, "Initial inventory query finished; enabling main UI.");
		}
	};

	private void updateUI() {
		List<OpItem> data = new ArrayList<OpItem>();
		data.add(new OpItem(R.drawable.generalstar, R.string.general));

		data.add(new OpItem(R.drawable.pregnancybaby, R.string.pregnancy));

		data.add(new OpItem(R.drawable.childhouse, R.string.children));

		data.add(new OpItem(R.drawable.animalprint, R.string.animal));

		data.add(new OpItem(R.drawable.viewallcasex, R.string.buy_pro));

		adapter.setData(data);
		adapter.notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v7.app.ActionBarActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "Destroying helper.");
		if (mHelper != null) {
			mHelper.dispose();
			mHelper = null;
		}
	}

	private void init() {
		initBackBtn();
		adapter = new ListAdapter<OpItem>(this, this);
		List<OpItem> data = new ArrayList<OpItem>();
		data.add(new OpItem(R.drawable.generalstar, R.string.general));

		data.add(new OpItem(R.drawable.pregnancybaby, R.string.pregnancy));

		data.add(new OpItem(R.drawable.childhouse, R.string.children));

		data.add(new OpItem(R.drawable.animalprint, R.string.animal));

		if (isPro || loadData("allBought")) {
			data.add(new OpItem(R.drawable.viewallcasex, R.string.all));
		} else {
			data.add(new OpItem(R.drawable.viewallcasex, R.string.buy_pro));
		}
		adapter.setData(data);
		ListView v = (ListView) this.findViewById(R.id.op_list);
		v.setDividerHeight(0);
		v.setAdapter(adapter);
		v.setOnItemClickListener(this);

	}

	@Override
	public View createOrUpdateView(final OpItem item, View convertView,
			int position, ViewGroup parent) {
		View result = null;
		
			LayoutInflater inflater = (LayoutInflater) this
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			result = inflater.inflate(R.layout.search_list_item, null);
		ImageView icon = (ImageView) result.findViewById(R.id.op_item_icon);
		icon.setImageResource(item.getIcon());
		TextView text = (TextView) result.findViewById(R.id.op_item_text);
		text.setText(item.getText());

		ImageView key = (ImageView) result.findViewById(R.id.op_item_key);

		if (isPro || item.getText() == R.string.buy_pro
				|| loadData(getKey(item.getText()))) {
			Log.d("aaaa","position"+position+" getKey:"+getKey(item.getText())+" loadData:"+loadData(getKey(item.getText()))+" item.getText() == R.string.buy_pro:"+( item.getText() == R.string.buy_pro));
			key.setVisibility(View.INVISIBLE);
		} else {
			key.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String key = getKey(item.getText());
					try {
						mHelper.launchPurchaseFlow(SymptomSearchActivity.this, key,
								BUY_ITEM, mPurchaseFinishedListener);
					} catch (Exception e) {
						complain("In App Billing is not support, can't buy this item now. Please make sure the google play service is installed.");
					}
				}
			});
		}
		return result;
	}

	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			Log.d(TAG, "Purchase finished: " + result + ", purchase: "
					+ purchase);

			// if we were disposed of in the meantime, quit.
			if (mHelper == null)
				return;

			if (result.isFailure()) {
				complain("Error purchasing: " + result);
				// setWaitScreen(false);
				return;
			}
			// if (!verifyDeveloperPayload(purchase)) {
			// complain("Error purchasing. Authenticity verification failed.");
			// // setWaitScreen(false);
			// return;
			// }
			saveData(purchase.getSku(), true);

			updateUI();

			Log.d(TAG, "Purchase successful.");
		}
	};

	private String getKey(int id) {
		switch (id) {
		case R.string.general:
			return PURCHASE_ITEM_G;
		case R.string.pregnancy:
			return PURCHASE_ITEM_P;
		case R.string.children:
			return PURCHASE_ITEM_C;
		case R.string.animal:
			return PURCHASE_ITEM_A;
		}
		return null;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (adapter.getItem(arg2).getText() == R.string.buy_pro) {
			buyPro();
		} else {
			Intent i = new Intent(this, SymptomSearchResultActivity.class);
			i.putExtra("appType", types[arg2]);
			i.putExtra("isPro", isPro
					|| loadData(getKey(adapter.getItem(arg2).getText())));
			startActivity(i);
		}
	}

	private void buyPro() {
		// TODO Auto-generated method stub
		startActivity(new Intent(Intent.ACTION_VIEW,
				Uri.parse("market://details?id=com.mdo.pro")));

	}

	void complain(String message) {
		Log.e(TAG, "**** Symptom Billing Error: " + message);
		alert("Error: " + message);
	}

	void alert(String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(this);
		bld.setMessage(message);
		bld.setNeutralButton("OK", null);
		Log.d(TAG, "Showing alert dialog: " + message);
		bld.create().show();
	}

	void saveData(String key, boolean value) {

		/*
		 * WARNING: on a real application, we recommend you save data in a
		 * secure way to prevent tampering. For simplicity in this sample, we
		 * simply store the data using a SharedPreferences.
		 */

		SharedPreferences.Editor spe = getPreferences(MODE_PRIVATE).edit();
		spe.putBoolean(key, value);
		spe.commit();
		// Log.d(TAG, "Saved data: tank = " + String.valueOf(mTank));
	}

	boolean loadData(String key) {
		SharedPreferences sp = getPreferences(MODE_PRIVATE);
		return sp.getBoolean(key, false);
		// Log.d(TAG, "Loaded data: tank = " + String.valueOf(mTank));
	}
}
