package com.mdo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mdo.R;
import com.mdo.domainobject.Oil;
import com.mdo.utils.ImageLoadTask;
import com.mdo.utils.ListAdapter;
import com.mdo.utils.ListItemViewCreator;

public class OilDetailActivity extends FacebookBaseActivity implements ListItemViewCreator<String>, OnItemClickListener {
	private Oil oil;
	
	private ListView listView;
	private ListAdapter<String> adapter;

	private List<String> data;
	

	private Map<String,Integer> resources=new HashMap<String,Integer>();
	
	
	
	/**
	 * @return the oil
	 */
	public Oil getOil() {
		return oil;
	}

	/**
	 * @param oil the oil to set
	 */
	public void setOil(Oil oil) {
		this.oil = oil;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oil_detail);
//		session = DatabaseHelper.(this);
		oil=(Oil)this.getIntent().getExtras().get("oil");
		init();
	}

	private void init() {
		initBackBtn();
		this.setItemName(oil.getName());
		TextView oilName=(TextView)findViewById(R.id.oil_name);
		oilName.setText(oil.getName());
		TextView scientificName=(TextView)findViewById(R.id.scientific_name);
		scientificName.setText(oil.getScientificName());
		
		TextView oilDesc=(TextView)findViewById(R.id.oil_description);
		oilDesc.setText(oil.getDescription());
		oilDesc.setMovementMethod(new ScrollingMovementMethod());
		
		adapter = new ListAdapter<String>(this, this);
		data=new ArrayList<String>();
		
		data.add("Uses");
		
		data.add("Applications");

		data.add("Classification");
		data.add("Systems Affected");
		data.add("Aromatic Affects");
		data.add("Saftey Precautions");
		
		resources.put("Uses", R.drawable.uses_icon);
		resources.put("Applications", R.drawable.application);

		resources.put("Classification", R.drawable.classification);
		resources.put("Systems Affected", R.drawable.systems_affected_icon);
		resources.put("Aromatic Affects", R.drawable.aromatic_affects);
		resources.put("Saftey Precautions", R.drawable.safety_precautions);
				
		adapter.setData(data);
		listView = (ListView) this.findViewById(R.id.op_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		
		if(oil.getImageURL()!=null&&oil.getImageURL().length()>0){
			
			ImageView icon=(ImageView)findViewById(R.id.right_btn);
			icon.setVisibility(View.VISIBLE);
			ImageLoadTask loadImage=new ImageLoadTask(icon, this);
			loadImage.execute(oil.getImageURL(),"/oilImage",oil.getId()+".png");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		String item=adapter.getItem(arg2);
		switch (arg2){
		case 0:
			moreDetailFor(item);
			break;
		case 1:
			startOilApp();
			break;
		case 2:
		case 3:
		case 4:
		case 5:
			moreDetailFor(item);
			break;
		}	
	}
	
private void startOilApp() {
		
		Intent i = new Intent(this, ApplciationsActivity.class);
		i.putExtra("oil", oil);
		i.putExtra("isSymptomApp", false);
		this.startActivity(i);
	}

	private void moreDetailFor(String item){
		Intent i = new Intent(this, MoreDetailActivity.class);
		i.putExtra("name", oil.getName());
		i.putExtra("symptom_id", oil.getId());
		i.putExtra("item", item);
		i.putExtra("itemDesc", getDesc(item));
		startActivity(i);
	}

	
	private String getDesc(String item){
		String result=null;
		switch(item){
		case "Uses":
			result=oil.getUses();
			break;
		case "Classification":
			result=oil.getClassification();
			break;
		case "Systems Affected":
			result=oil.getSystemsAffected();
			break;
		case "Aromatic Affects":
			result=oil.getAromaticAffects();
			break;
		case "Saftey Precautions":
			result=oil.getSafetyPrecautions();
			break;
		}
		return result;
		
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

}
