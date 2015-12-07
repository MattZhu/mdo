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
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class OilSearchActivity extends BaseActivity implements ListItemViewCreator<Oil>, OnClickListener, OnItemClickListener {
	private boolean isPro;



	private DaoSession session;
	private ListView listView;
	private ListAdapter<Oil> adapter;
	private OilDao dao;
	private Map<String,Integer> index=new LinkedHashMap<String,Integer>();
	
	protected boolean buildAutoComplete;

	private AutoCompleteTextView editText;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_symptom_search_result);
		this.isPro = this.getIntent().getBooleanExtra("isPro", false);
		init();
		session=DatabaseHelper.getDaoSession(this);
		dao=session.getOilDao();
		
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
	
	
	
	/* (non-Javadoc)
	 * @see com.doterra.activity.BaseActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadData(editText.getText().toString());
	}

	private void loadData(String name){
		new AsyncTaskEx<String,Void,List<Oil> >(){

			Set<String> namesOrDescription = new HashSet<String>();
			@Override
			protected List<Oil>  doInBackground(String... params) {
				
				QueryBuilder<Oil>qb=dao.queryBuilder();
				if(params[0]!=null&&params[0].length()>0){
					if(!isPro){
						qb.where(OilDao.Properties.IsFree.eq(1),qb.or(OilDao.Properties.Name.like("%"+params[0]+"%"), OilDao.Properties.Description.like("%"+params[0]+"%")));
					}else{
						qb.where(qb.or(OilDao.Properties.Name.like("%"+params[0]+"%"), OilDao.Properties.Description.like("%"+params[0]+"%")));
					}
				}else{
					if(!isPro){
						qb.where(OilDao.Properties.IsFree.eq(1));
					}
				}
				qb.orderAsc(OilDao.Properties.Name);
				List<Oil> result=qb.list();
				List<Oil> r=new ArrayList<Oil>();
				char lastName=0;
				for(Oil s:result){
					if(!buildAutoComplete){
						namesOrDescription.add(s.getName());
						String descs[] = s.getDescription().split(" ");
						for (String desc : descs) {
							namesOrDescription.add(desc.trim());
						}
					}
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
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						OilSearchActivity.this,
						android.R.layout.simple_dropdown_item_1line,
						namesOrDescription.toArray(new String[0]));
				editText.setAdapter(adapter);
			}
			
			
		}.execute(name);
		
	}

	private void init() {
		initBackBtn();
		
		adapter = new ListAdapter<Oil>(this, this);
		
		listView=(ListView)this.findViewById(R.id.op_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		TextView subTitle=(TextView)findViewById(R.id.sub_title);
		subTitle.setText(R.string.oilprod);
		editText = (AutoCompleteTextView) findViewById(R.id.search_text);

		editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {

					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					loadData(editText.getText().toString());
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

	}



	@Override
	public View createOrUpdateView(Oil item, View convertView,
			int position, ViewGroup parent) {
		View result;
		
			LayoutInflater inflater = (LayoutInflater) this
					.getSystemService(LAYOUT_INFLATER_SERVICE);
		if(item.getDescription().equals("!!FAKE_DONOT_DISPLAY!!")){
			result = inflater.inflate(R.layout.search_result_subtitil_item, null);
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
					Intent i = new Intent(OilSearchActivity.this,
							OilDetailActivity.class);
					i.putExtra("oil", oil);
					startActivity(i);
				}
			});
			
			ImageView saveView =(ImageView)result.findViewById(R.id.save_status);
			boolean saved=oil.getIsSaved()!=null&&oil.getIsSaved();
			if (saved) {
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
		
		
		
		return result;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		TextView selectedIndex = (TextView) v;
		listView.setSelection(index.get(selectedIndex.getText()));
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
				
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.mdo.activity.BaseActivity#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		loadData(editText.getText().toString());
	}
	
	
}
