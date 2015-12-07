package com.mdo.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.R.color;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mdo.R;
import com.mdo.domainobject.DaoSession;
import com.mdo.domainobject.Symptom;
import com.mdo.domainobject.SymptomDao;
import com.mdo.utils.AsyncTaskEx;
import com.mdo.utils.DatabaseHelper;
import com.mdo.utils.ListAdapter;
import com.mdo.utils.ListItemViewCreator;

import de.greenrobot.dao.query.QueryBuilder;

public class SymptomSearchResultActivity extends BaseActivity implements
		ListItemViewCreator<Symptom>, OnClickListener, OnItemClickListener {
	private boolean isPro;

	private String appType;

	private DaoSession session;
	private ListView listView;
	private ListAdapter<Symptom> adapter;

	private AutoCompleteTextView editText;

	private Map<String, Integer> index = new LinkedHashMap<String, Integer>();

	protected boolean buildAutoComplete;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_symptom_search_result);
		this.isPro = this.getIntent().getBooleanExtra("isPro", false);
		this.appType = this.getIntent().getStringExtra("appType");
		Log.d("SymptomSearch", "app type is:" + appType);
		init();
		session = DatabaseHelper.getDaoSession(this);

	}

	private void displayIndex() {
		LinearLayout indexLayout = (LinearLayout) findViewById(R.id.list_index);
		indexLayout.removeAllViews();
		TextView textView;
		List<String> indexList = new ArrayList<String>(index.keySet());
		int topBottomPadding=dpToPx(5);
		int fontSize=dpToPx(8);
		if(indexList.size()>15){
			topBottomPadding=dpToPx(1);
			fontSize=dpToPx(6);
		}else if(indexList.size()<8){
			topBottomPadding=dpToPx(10);
		}else{
			topBottomPadding=dpToPx(5);
		}
		for (String index : indexList) {
			textView = (TextView) getLayoutInflater().inflate(
					R.layout.side_index_item, null);
			textView.setText(index);
			textView.setPadding(dpToPx(3), topBottomPadding, dpToPx(3), topBottomPadding);
			textView.setTextSize(fontSize);
			textView.setOnClickListener(this);
			indexLayout.addView(textView);
		}
	}

	
	int dpToPx(int dp)
	{
	    return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
	}
	private void init() {
		initBackBtn();

		adapter = new ListAdapter<Symptom>(this, this);

		listView = (ListView) this.findViewById(R.id.op_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		editText = (AutoCompleteTextView) findViewById(R.id.search_text);

		editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {

					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					query(editText.getText().toString());
					return true;
				}
				return false;
			}

		});
		editText.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				 if (event.getX() > editText.getWidth() - editText.getPaddingRight() - 40) {
					 editText.setText("");
			        }
				return false;
			}});
		query(null);
	}

	private void query(String name) {
		new AsyncTaskEx<String, Void, List<Symptom>>() {
			Set<String> namesOrDescription = new HashSet<String>();

			@Override
			protected List<Symptom> doInBackground(String... params) {
				SymptomDao dao = session.getSymptomDao();
				QueryBuilder<Symptom> qb = dao.queryBuilder();

				if (params[0] != null && params[0].length() > 0) {
					if (!isPro) {
						qb.where(SymptomDao.Properties.IsFree.eq(1),
								SymptomDao.Properties.Database.like("%"
										+ appType + "%"), qb.or(
										SymptomDao.Properties.Name.like("%"
												+ params[0] + "%"),
										SymptomDao.Properties.Description
												.like("%" + params[0] + "%")));

					} else {
						qb.where(SymptomDao.Properties.Database.like("%"
								+ appType + "%"), qb.or(
								SymptomDao.Properties.Name.like("%" + params[0]
										+ "%"),
								SymptomDao.Properties.Description.like("%"
										+ params[0] + "%")));

					}
				} else {
					if (!isPro) {
						qb.where(
								SymptomDao.Properties.IsFree.eq(1),
								SymptomDao.Properties.Database.like("%"
										+ appType + "%"));
					} else {
						qb.where(SymptomDao.Properties.Database.like("%"
								+ appType + "%"));
					}
				}
				qb.orderAsc(SymptomDao.Properties.Name);
				List<Symptom> result = qb.list();
				List<Symptom> r = new ArrayList<Symptom>();
				index.clear();
				char lastName = 0;
				for (Symptom s : result) {
					if(!buildAutoComplete){
						namesOrDescription.add(s.getName());
						String descs[] = s.getDescription().split(" ");
						for (String desc : descs) {
							namesOrDescription.add(desc.trim());
						}
					}
					if (lastName != s.getName().charAt(0)) {
						Symptom s1 = new Symptom();
						String ind = s.getName().toUpperCase().substring(0, 1);
						s1.setName(ind);
						s1.setDescription("!!FAKE_DONOT_DISPLAY!!");
						r.add(s1);
						index.put(ind, r.size() - 1);
					}
					r.add(s);
					lastName = s.getName().toUpperCase().charAt(0);
				}
				buildAutoComplete=true;
				return r;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.doterra.utils.AsyncTaskEx#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(List<Symptom> result) {
				Log.d("SymptomSearch", "find " + result.size()
						+ " record for type:" + appType);
				adapter.setData(result);
				adapter.notifyDataSetChanged();
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						SymptomSearchResultActivity.this,
						android.R.layout.simple_dropdown_item_1line,
						namesOrDescription.toArray(new String[0]));
				editText.setAdapter(adapter);
				displayIndex();

			}

		}.execute(name);

	}

	@Override
	public View createOrUpdateView(Symptom item, View convertView,
			int position, ViewGroup parent) {
		View result;

		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		if (item.getDescription().equals("!!FAKE_DONOT_DISPLAY!!")) {

			result = inflater.inflate(R.layout.search_result_subtitil_item, null);
			TextView testView = (TextView) result.findViewById(R.id.symptom_name);
			testView.setText(item.getName());
			testView.setBackgroundColor(color.white);
			testView.setTextColor(Color.rgb(140, 11, 11));
			testView.setTypeface(null, Typeface.BOLD);

		}else{
			result = inflater.inflate(R.layout.search_result_item, null);
			TextView testView = (TextView) result.findViewById(R.id.symptom_name);

			testView.setText(item.getName());

		}
		return result;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		TextView selectedIndex = (TextView) v;
		listView.setSelection(index.get(selectedIndex.getText()));
	}

	private class MyFocusChangeListener implements OnFocusChangeListener {

		public void onFocusChange(View v, boolean hasFocus) {
			Log.d("SymptomSearch", "search text focus changed=" + hasFocus);
			if (v.getId() == R.id.search_text && !hasFocus) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Symptom s = adapter.getItem(arg2);
		if (!"!!FAKE_DONOT_DISPLAY!!".equals(s.getDescription())) {
			Intent i = new Intent(this, SymptomDetailActivity.class);
			i.putExtra("symptom", s);
			i.putExtra("appType", appType);
			startActivity(i);
		}
	}

	/* (non-Javadoc)
	 * @see com.mdo.activity.BaseActivity#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		query(editText.getText().toString());
	}
	
	
}
