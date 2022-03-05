package com.yssj.ui.activity.myshop;

import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;

public class EditSignActivity extends BasicActivity {
	
	private Spinner sp_choose_item;
	private SimpleAdapter mAdapter;
	
	private List<HashMap<String, String>> listCarType;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_sign_activity);
		initView();
		
	}
	
	private void initView(){
		sp_choose_item = (Spinner) findViewById(R.id.sp_choose_item);
		mAdapter = new SimpleAdapter(this,
				listCarType, R.layout.simple_adapter,
				new String[] { "charge_name" },
				new int[] { R.id.tv_show });
	}
}
