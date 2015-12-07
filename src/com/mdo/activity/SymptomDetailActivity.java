package com.mdo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mdo.R;
import com.mdo.domainobject.DaoSession;
import com.mdo.domainobject.Symptom;
import com.mdo.utils.ListAdapter;
import com.mdo.utils.ListItemViewCreator;

public class SymptomDetailActivity extends FacebookBaseActivity implements
		ListItemViewCreator<String>, OnItemClickListener {

	private Symptom symptom;

	private ListView listView;
	private ListAdapter<String> adapter;

	private List<String> data;
	
	private DaoSession session;
	
	private String appType;
	
	private Map<String,Integer> resources=new HashMap<String,Integer>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_symptom_detail);

		symptom = (Symptom) this.getIntent().getExtras().get("symptom");
		appType =getIntent().getStringExtra("appType");
		init();
	}

	private void init() {
		initBackBtn();
		setItemName(symptom.getName());
		TextView symptomName = (TextView) this.findViewById(R.id.symptom_name);
		symptomName.setText(symptom.getName());

		TextView symptomDesc = (TextView) this
				.findViewById(R.id.symptom_description);
		symptomDesc.setText(symptom.getDescription());

		adapter = new ListAdapter<String>(this, this);
		data=new ArrayList<String>();
		data.add("Oils");
		data.add("Applications");

		data.add("Contraindications");
		data.add("Blends");
		data.add("Additinal Products");
		
		resources.put("Oils", R.drawable.oil_icon);
		resources.put("Applications", R.drawable.application);

		resources.put("Contraindications", R.drawable.contraindications);
		resources.put("Blends", R.drawable.blends);
		resources.put("Additinal Products", R.drawable.additionalproducts);
				
		adapter.setData(data);
		listView = (ListView) this.findViewById(R.id.op_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public View createOrUpdateView(String item, View convertView, int position,
			ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View result = inflater.inflate(R.layout.detial_op_item, null);
		TextView textView = (TextView) result.findViewById(R.id.op_item_name);
		textView.setText(adapter.getItem(position));
		
		ImageView icon=(ImageView)result.findViewById(R.id.icon);
		
		icon.setImageResource(getImageResource(adapter.getItem(position)));
		
		return result;
	}

	private int getImageResource(String item) {
		return resources.get(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String item=adapter.getItem(arg2);
		switch (arg2){
		case 0:
			startSymptomOilList(item);
			break;
		case 1:
			startSymptomApp();
			break;
		case 2:
		case 3:
		case 4:
			moreDetailFor(item);
			break;
		}	
		
	}
	
	private void startSymptomApp() {
		
		Intent i = new Intent(this, ApplciationsActivity.class);
		i.putExtra("symptom", symptom);
		i.putExtra("isSymptomApp", true);
		i.putExtra("appType", appType);
		this.startActivity(i);
	}

	private void startSymptomOilList(String item) {
		Intent i = new Intent(this, SymptomOilListActivity.class);
		i.putExtra("symptom_name", symptom.getName());
		String oil_ids=symptom.getPrimaryOils();
		if(isEmpty(symptom.getSecondaryOils())){
			if(isEmpty(oil_ids)){
				oil_ids=symptom.getSecondaryOils();
			}else{
				oil_ids+=","+symptom.getSecondaryOils();
			}
		}
		if(isEmpty(symptom.getTertiaryOils())){
			if(isEmpty(oil_ids)){
				oil_ids=symptom.getTertiaryOils();
			}else{
				oil_ids+=","+symptom.getTertiaryOils();
			}
		}
		i.putExtra("oil_ids", oil_ids);
		startActivity(i);
	}
	
	private boolean isEmpty(String str){
		return str==null||str.trim().length()==0;
	}

	private void moreDetailFor(String item){
		Intent i = new Intent(this, MoreDetailActivity.class);
		i.putExtra("name", symptom.getName());
		i.putExtra("symptom_id", symptom.getId());
		i.putExtra("item", item);
		i.putExtra("itemDesc", getDesc(item));
		startActivity(i);
	}
	
	private String getDesc(String item){
		String result=null;
		switch(item){
		case "Contraindications":
			result=symptom.getContraindications();
			break;
		case "Blends":
			result=symptom.getBlends();
			break;
		case "Additinal Products":
			result=symptom.getAdditionalProducts();
			break;
		}
		return result;
		
	}

}
