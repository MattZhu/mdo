package com.mdo.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.mdo.R;

public class MoreDetailActivity extends FacebookBaseActivity {

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_more);


		init();
	}

	private void init() {
		
		initBackBtn();
		String symptom =this.getIntent().getStringExtra("name");
		String title =this.getIntent().getStringExtra("item");
		String desc =this.getIntent().getStringExtra("itemDesc");
		this.setItemName(symptom);
		TextView symptomName=(TextView)this.findViewById(R.id.symptom_name);
		symptomName.setText(symptom);

		TextView item=(TextView)this.findViewById(R.id.item);
		item.setText(title);
		

		TextView itemDesc=(TextView)this.findViewById(R.id.item_description);
		itemDesc.setText(desc);
	}
}
