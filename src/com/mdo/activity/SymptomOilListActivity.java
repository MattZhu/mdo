package com.mdo.activity;

import java.util.List;

import android.content.Intent;
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
import com.mdo.activity.fragment.SaveOilDialogFragment;
import com.mdo.domainobject.DaoSession;
import com.mdo.domainobject.Oil;
import com.mdo.domainobject.OilDao;
import com.mdo.utils.AsyncTaskEx;
import com.mdo.utils.DatabaseHelper;
import com.mdo.utils.ListAdapter;
import com.mdo.utils.ListItemViewCreator;

import de.greenrobot.dao.query.QueryBuilder;

public class SymptomOilListActivity extends BaseActivity implements
		ListItemViewCreator<Oil>, OnItemClickListener {

	private DaoSession session;
	private ListView listView;
	private ListAdapter<Oil> adapter;

	private OilDao dao;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_symptom_oil_list);
		session = DatabaseHelper.getDaoSession(this);
		dao = session.getOilDao();
		init();
	}

	private void init() {
		initBackBtn();

		TextView symptom_name = (TextView) findViewById(R.id.symptom_name);
		symptom_name.setText(this.getIntent().getStringExtra("symptom_name"));
		adapter = new ListAdapter<Oil>(this, this);
		String oil_ids = getIntent().getStringExtra("oil_ids");
		listView = (ListView) this.findViewById(R.id.oil_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		new AsyncTaskEx<String, Void, List<Oil>>() {

			@Override
			protected List<Oil> doInBackground(String... params) {
				OilDao dao = session.getOilDao();

				QueryBuilder<Oil> qb = dao.queryBuilder();
				String[] ids = params[0].split(",");
				qb.where(OilDao.Properties.Id.in(ids));

				return qb.list();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.doterra.utils.AsyncTaskEx#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(List<Oil> result) {
				super.onPostExecute(result);
				adapter.setData(result);
				adapter.notifyDataSetChanged();
			}

		}.execute(oil_ids);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		View descView = view.findViewById(R.id.desc);


		Log.d("SymptomOilList","item  clicked");	
		final Oil oil = adapter.getItem(arg2);
		if (descView.getVisibility() == View.VISIBLE) {
			descView.setVisibility(View.GONE);
		} else {
			descView.setVisibility(View.VISIBLE);
			
		}


	}

	@Override
	public View createOrUpdateView(Oil item, View convertView, int position,
			ViewGroup parent) {
		View result;

		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		result = inflater.inflate(R.layout.oil_list_item, null);
		TextView testView = (TextView) result.findViewById(R.id.oil_name_tv);
		final Oil oil = adapter.getItem(position);
		testView.setText(oil.getName());
		TextView descView = (TextView) result.findViewById(R.id.oil_desc);

		descView.setText(oil.getDescription());

		
		View oilDescView = result.findViewById(R.id.oil_desc);
		oilDescView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("SymptomOilList","oil detail clicked");	
				Intent i = new Intent(SymptomOilListActivity.this,
						OilDetailActivity.class);
				i.putExtra("oil", oil);
				startActivity(i);
			}
		});
		
		ImageView saveView = (ImageView)result.findViewById(R.id.save_status);
		if(item.getIsSaved()!=null&&item.getIsSaved()){
			saveView.setImageResource(R.drawable.saved_oil);
		} else {
			saveView.setImageResource(R.drawable.not_saved_oil);
		}
		saveView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ImageView savedView = (ImageView) v;

				SaveOilDialogFragment dialogFragment=new SaveOilDialogFragment(oil,dao,savedView);
				dialogFragment.show(getSupportFragmentManager(), "SaveOil");
			}
		});
		return result;
	}
}
