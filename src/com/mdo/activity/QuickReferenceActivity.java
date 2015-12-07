package com.mdo.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mdo.R;
import com.mdo.domainobject.DaoSession;
import com.mdo.domainobject.Oil;
import com.mdo.domainobject.OilApplication;
import com.mdo.domainobject.OilApplicationDao;
import com.mdo.domainobject.OilDao;
import com.mdo.utils.AsyncTaskEx;
import com.mdo.utils.DatabaseHelper;
import com.mdo.utils.ListAdapter;
import com.mdo.utils.ListItemViewCreator;

public class QuickReferenceActivity extends BaseActivity {
	private DaoSession session;

	private ListView listView;
	private ListAdapter<OilRef> adapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quick_reference);
		session = DatabaseHelper.getDaoSession(this);
		init();
	}

	private class OilRef implements Comparable<OilRef>{
		String name;
		String scientificName;
		Set<String> symbols = new HashSet<String>();
		@Override
		public int compareTo(OilRef other) {
			// TODO Auto-generated method stub
			return name.compareTo(other.name);
		}
	}

	private void init() {
		initBackBtn();

		listView = (ListView) findViewById(R.id.ref_list);
		adapter = new ListAdapter<OilRef>(this,
				new ListItemViewCreator<OilRef>() {

					@Override
					public View createOrUpdateView(OilRef item,
							View convertView, int position, ViewGroup parent) {
						View result;

						LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
						result = inflater.inflate(R.layout.oil_ref_item, null);
						TextView testView = (TextView) result
								.findViewById(R.id.oil_name);

						testView.setText(item.name);
						testView.setHorizontallyScrolling(true);
						testView = (TextView) result
								.findViewById(R.id.scientific_name);

						testView.setText(item.scientificName);
						for (String s : item.symbols) {
							View view =result.findViewById(getID(s.trim()));
							if(view!=null)view.setVisibility(View.VISIBLE);
						}
						return result;
					}

					private int getID(String s) {
						int result = 0;
						switch (s) {
						case "A":
							result = R.id.symbol_a;
							break;
						case "T":

							result = R.id.symbol_t;
							break;
						case "I":

							result = R.id.symbol_i;
							break;
						case "N":

							result = R.id.symbol_n;
							break;
						case "S":

							result = R.id.symbol_s;
							break;
						case "D":

							result = R.id.symbol_d;
							break;

						}
						return result;
					}
				});

		listView.setAdapter(adapter);
		new AsyncTaskEx<Void, Void, List<OilRef>>() {

			@Override
			protected List<OilRef> doInBackground(Void... params) {
				OilDao oilDao = session.getOilDao();
				List<Oil> allOils = oilDao.loadAll();
				OilApplicationDao oilApplicationDao = session
						.getOilApplicationDao();
				List<OilApplication> oilApplications = oilApplicationDao
						.loadAll();
				Map<Integer, List<OilApplication>> appMap = new HashMap<Integer, List<OilApplication>>();
				for (OilApplication oilapp : oilApplications) {
					List<OilApplication> list = appMap.get(oilapp.getOilId());
					if (list == null) {
						list = new ArrayList<OilApplication>();
						appMap.put(oilapp.getOilId(), list);
					}
					list.add(oilapp);
				}
				List<OilRef> result = new ArrayList<OilRef>();
				for (Oil oil : allOils) {
					List<OilApplication> oilApp = appMap.get(oil.getId()
							.intValue());
					OilRef ref = new OilRef();
					ref.name = oil.getName();
					ref.scientificName = oil.getScientificName();
					if (oilApp == null) {
						continue;
					}
					for (OilApplication oa : oilApp) {
						ref.symbols.add(oa.getSymbolCode());
						ref.symbols.addAll(Arrays.asList(oa
								.getAdditionalSymbols().split(" ")));
					}
					result.add(ref);
				}
				Collections.sort(result);
				return result;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.doterra.utils.AsyncTaskEx#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(List<OilRef> result) {
				super.onPostExecute(result);
				adapter.setData(result);
				adapter.notifyDataSetChanged();
			}

		}.execute();
	}

}
