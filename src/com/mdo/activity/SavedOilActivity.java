package com.mdo.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.R.color;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class SavedOilActivity extends FacebookBaseActivity implements ListItemViewCreator<Oil>, OnClickListener, OnItemClickListener {
	private boolean isPro;



	private DaoSession session;
	private ListView listView;
	private ListAdapter<Oil> adapter;
	private OilDao dao;
	private Map<String,Integer> index=new LinkedHashMap<String,Integer>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_saved_oil);
		init();
		session=DatabaseHelper.getDaoSession(this);
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.doterra.activity.BaseActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadData();
		
	}



	private void displayIndex() {
        LinearLayout indexLayout = (LinearLayout) findViewById(R.id.list_index);
 
        TextView textView;
        List<String> indexList = new ArrayList<String>(index.keySet());
        for (String index : indexList) {
            textView = (TextView) getLayoutInflater().inflate(
                    R.layout.side_index_item, null);
            textView.setText(index);
            textView.setOnClickListener(this);
            indexLayout.addView(textView);
        }
    }

	private void init() {
		initBackBtn();
		
		adapter = new ListAdapter<Oil>(this, this);
		
		listView=(ListView)this.findViewById(R.id.op_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	
	public void loadData(){
		
		new AsyncTaskEx<Void,Void,List<Oil> >(){

			@Override
			protected List<Oil>  doInBackground(Void... params) {
				dao=session.getOilDao();
				QueryBuilder<Oil>qb=dao.queryBuilder();
				qb.where(OilDao.Properties.IsSaved.eq(true));
				
				qb.orderAsc(OilDao.Properties.Name);
				List<Oil> result=qb.list();
				List<Oil> r=new ArrayList<Oil>();
				char lastName=0;
				for(Oil s:result){
					if(lastName!=s.getName().charAt(0)){
						Oil s1=new Oil();
						String ind=s.getName().toUpperCase().substring(0,1);
						s1.setName(ind);
						s1.setDescription("!!FAKE_DONOT_DISPLAY!!");
						r.add(s1);
						index.put(ind,r.size()-1);
					}
					r.add(s);
					lastName=s.getName().toUpperCase().charAt(0);
				}
				return r;
			}

			/* (non-Javadoc)
			 * @see com.doterra.utils.AsyncTaskEx#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(List<Oil> result) {
				adapter.setData(result);
				adapter.notifyDataSetChanged();
//				displayIndex();
			}
			
			
		}.execute();
		
	}
	


	@Override
	public View createOrUpdateView(Oil item, View convertView,
			int position, ViewGroup parent) {
		View result;
		
			LayoutInflater inflater = (LayoutInflater) this
					.getSystemService(LAYOUT_INFLATER_SERVICE);
		if(item.getDescription().equals("!!FAKE_DONOT_DISPLAY!!")){
			result = inflater.inflate(R.layout.search_result_item, null);
			TextView testView=(TextView)result.findViewById(R.id.symptom_name);
	
			testView.setText(item.getName());
			testView.setBackgroundColor(color.white);
		}else{
			
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
					Intent i = new Intent(SavedOilActivity.this,
							OilDetailActivity.class);
					i.putExtra("oil", oil);
					startActivity(i);
				}
			});
			
			ImageView saveView =(ImageView) result.findViewById(R.id.save_status);
			if(item.getIsSaved()!=null&&item.getIsSaved()){
				saveView.setImageResource(R.drawable.saved_oil);
				
			}else{
				saveView.setImageResource(R.drawable.not_saved_oil);
				
			}
			saveView.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					ImageView savedView = (ImageView) v;
					SaveOilDialogFragment dialogFragment=new SaveOilDialogFragment(oil,dao,savedView,SavedOilActivity.this);
					dialogFragment.show(getSupportFragmentManager(), "SaveOil");
			
					loadData();
				}
			});
			return result;
			
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

	    public void onFocusChange(View v, boolean hasFocus){

	        if(v.getId() == R.id.search_text && !hasFocus) {

	            InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

	        }
	    }
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		Oil s=adapter.getItem(arg2);
		if(!"!!FAKE_DONOT_DISPLAY!!".equals(s.getDescription())){
			View descView = view.findViewById(R.id.desc);
			if (descView.getVisibility() == View.VISIBLE) {
				descView.setVisibility(View.GONE);
			} else {
				descView.setVisibility(View.VISIBLE);
				this.setItemName(adapter.getItem(arg2).getName());
			}
		}
	}
}
