package com.mdo.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mdo.R;
import com.mdo.domainobject.ApplicationType;
import com.mdo.domainobject.DaoSession;
import com.mdo.domainobject.Oil;
import com.mdo.domainobject.OilApplication;
import com.mdo.domainobject.OilApplicationDao;
import com.mdo.domainobject.Symptom;
import com.mdo.domainobject.SymptomApplication;
import com.mdo.domainobject.SymptomApplicationDao;
import com.mdo.utils.AsyncTaskEx;
import com.mdo.utils.DatabaseHelper;
import com.mdo.utils.ListAdapter;
import com.mdo.utils.ListItemViewCreator;

import de.greenrobot.dao.query.QueryBuilder;

public class ApplciationsActivity extends FacebookBaseActivity implements ListItemViewCreator<SymptomApplication> {

	private boolean isSymptomApp;

	private Symptom symptom;

	private Oil oil;

	private OilApplicationDao oilAppDao;

	private DaoSession session;
	
	private ListView listView;

	private ListAdapter<SymptomApplication> symptomAdapter;

	private ListAdapter<OilApplication> oilAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_application);
		isSymptomApp = this.getIntent().getBooleanExtra("isSymptomApp", false);
		session = DatabaseHelper.getDaoSession(this);
		if (isSymptomApp) {
			symptom = (Symptom) this.getIntent().getExtras().get("symptom");
			this.setItemName(symptom.getName());
		} else {
			oil = (Oil) this.getIntent().getExtras().get("oil");
			this.setItemName(oil.getName());

		}
		init();
	}

	private void init() {
		initBackBtn();
		TextView itemName=(TextView)this.findViewById(R.id.item_name);

		listView = (ListView) this.findViewById(R.id.appList);
		if (isSymptomApp) {
			itemName.setText(symptom.getName());
			initSymptApp();
		} else {
			itemName.setText(oil.getName());
			initOilApp();
		}
		
	}

	private void initSymptApp() {
		String appType = this.getIntent().getStringExtra("appType");
		
		symptomAdapter = new ListAdapter<SymptomApplication>(this, this);
		listView.setAdapter(symptomAdapter);
		
		new AsyncTaskEx<String, Void, List<SymptomApplication>>() {

			@Override
			protected List<SymptomApplication> doInBackground(String... params) {
				SymptomApplicationDao dao = session.getSymptomApplicationDao();
				QueryBuilder<SymptomApplication> qb = dao.queryBuilder();

				if (params[0].length() > 0) {
					qb.where(SymptomApplicationDao.Properties.SymId.eq(symptom
							.getId()),
							SymptomApplicationDao.Properties.ApplicationType
									.eq(ApplicationType.valueOf(params[0]).getVal()));
				} else {
					qb.where(SymptomApplicationDao.Properties.SymId.eq(symptom
							.getId()));
				}
				qb.orderAsc(SymptomApplicationDao.Properties.ApplicationType);
				return qb.list();
			}

			/* (non-Javadoc)
			 * @see com.doterra.utils.AsyncTaskEx#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(List<SymptomApplication> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
							List<SymptomApplication> r=new ArrayList<SymptomApplication>();
							int lastAppType=-1;
				for(SymptomApplication sa:result){
					if(lastAppType!=sa.getApplicationType()){
						SymptomApplication app=new SymptomApplication();
						app.setSymbolCode("FAKE_APP");
						app.setDescription(getDesc(sa.getApplicationType()));
						app.setAdditionalSymbols(sa.getAdditionalSymbols());
						r.add(app);
					}
					lastAppType=sa.getApplicationType();
					r.add(sa);
				}
				symptomAdapter.setData(r);
				symptomAdapter.notifyDataSetChanged();
			}
			
			
		}.execute(appType);
	}

	protected String getDesc(Integer applicationType) {
		String result="";
		switch(applicationType){
		case 1:
			result="General Applications";
			break;
		case 2:
			result="Pregnancy Applications";
			break;
		case 3:
			result="Children Applications";
			break;
		case 4:
			result="Animal Applications";
			break;
		}//{} TODO Auto-generated method stub
		return result;
	}

	private void initOilApp() {
		oilAdapter = new ListAdapter<OilApplication>(this, new  ListItemViewCreator<OilApplication>(){

			@Override
			public View createOrUpdateView(OilApplication item,
					View convertView, int position, ViewGroup parent) {
				View result;
				if(item.getSymbolCode().equals("FAKE_APP")){
					LayoutInflater inflater = (LayoutInflater) 
						getSystemService(LAYOUT_INFLATER_SERVICE);
					result = inflater.inflate(R.layout.app_type_item, null);
					TextView t=(TextView)result.findViewById(R.id.app_type_name);
					t.setText(item.getDescription());
					LinearLayout addition =(LinearLayout)result.findViewById(R.id.additional_symbol);
					addImageView(addition,item.getAdditionalSymbols());
				}else{
					LayoutInflater inflater = (LayoutInflater) 
							getSystemService(LAYOUT_INFLATER_SERVICE);
					result = inflater.inflate(R.layout.app_item, null);
					TextView t=(TextView)result.findViewById(R.id.app_desc);
					t.setText(item.getDescription());
					ImageView symbol=(ImageView)result.findViewById(R.id.symbol_image);
					symbol.setImageResource(getImage(item.getSymbolCode()));
				}
				return result;
			}});
		listView.setAdapter(oilAdapter);
		
		new AsyncTaskEx<String, Void, List<OilApplication>>() {

			@Override
			protected List<OilApplication> doInBackground(String... params) {
				OilApplicationDao dao = session.getOilApplicationDao();
				QueryBuilder<OilApplication> qb = dao.queryBuilder();

				
					qb.where(OilApplicationDao.Properties.OilId.eq(oil
							.getId()));
				qb.orderAsc(OilApplicationDao.Properties.ApplicationType);
				return qb.list();
			}

			/* (non-Javadoc)
			 * @see com.doterra.utils.AsyncTaskEx#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(List<OilApplication> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
							List<OilApplication> r=new ArrayList<OilApplication>();
							int lastAppType=-1;
				for(OilApplication sa:result){
					if(lastAppType!=sa.getApplicationType()){
						OilApplication app=new OilApplication();
						app.setSymbolCode("FAKE_APP");
						app.setDescription(getDesc(sa.getApplicationType()));
						app.setAdditionalSymbols(sa.getAdditionalSymbols());
						r.add(app);
					}
					lastAppType=sa.getApplicationType();
					r.add(sa);
				}
				oilAdapter.setData(r);
				oilAdapter.notifyDataSetChanged();
			}
			
			
		}.execute();
	}

	@Override
	public View createOrUpdateView(SymptomApplication item, View convertView,
			int position, ViewGroup parent) {
		View result;
		if(item.getSymbolCode().equals("FAKE_APP")){
			LayoutInflater inflater = (LayoutInflater) this
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			result = inflater.inflate(R.layout.app_type_item, null);
			TextView t=(TextView)result.findViewById(R.id.app_type_name);
			t.setText(item.getDescription());
			LinearLayout addition =(LinearLayout)result.findViewById(R.id.additional_symbol);
			addImageView(addition,item.getAdditionalSymbols());
		}else{
			LayoutInflater inflater = (LayoutInflater) this
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			result = inflater.inflate(R.layout.app_item, null);
			TextView t=(TextView)result.findViewById(R.id.app_desc);
			t.setText(item.getDescription());
			ImageView symbol=(ImageView)result.findViewById(R.id.symbol_image);
			symbol.setImageResource(getImage(item.getSymbolCode()));
		}
		return result;
	}

	private void addImageView(LinearLayout addition, String additionalSymbols) {
		String symbols[]=additionalSymbols.split(" ");
		for(String s:symbols){
			ImageView image=new ImageView(this);
			int width=dpToPx(20);
			int margin=dpToPx(4);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
			layoutParams.setMargins(margin, margin, margin, margin);
			image.setLayoutParams(layoutParams);
			image.setImageResource(getImage(s));
		
			addition.addView(image);
		}
		
	}

	int dpToPx(int dp)
	{
	    return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
	}
	
	private int getImage(String symbolCode) {
		switch(symbolCode){
		case "A":
			return R.drawable.aicon;
		case "D":
			return R.drawable.dicon;
		case "I":
			return R.drawable.iicon;
		case "N":
			return R.drawable.nicon;
		case "S":
			return R.drawable.sicon;
		case "T":
			return R.drawable.ticon;
		
		
		}
		return 0;
	}

}
