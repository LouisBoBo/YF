//package com.yssj.ui.activity.infos;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//
//import com.yssj.activity.R;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.ui.fragment.orderinfo.DailyAmountFragment;
//import com.yssj.ui.fragment.orderinfo.DailyRebateFragment;
//import com.yssj.ui.fragment.orderinfo.DailyVisitorFragment;
//
//public class ManSaleDetailsActivity extends BasicActivity implements
//		OnCheckedChangeListener {
//
//	private RadioGroup rg_group;
//	private RadioButton rb_daily_amount, rb_daily_rebate, rb_daily_visitor;
//	private int index;
//
//	private DailyAmountFragment daFrag;
//	private DailyRebateFragment drFrag;
//	private DailyVisitorFragment dvFrag;
//
//	private FragmentManager fm;
//	private LinearLayout xiaos;
//	private FragmentTransaction ft;
//	
//	private TextView tvTitle_base;
//	private LinearLayout img_back;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		getActionBar().hide();
//		index = getIntent().getIntExtra("item", 1);
//		fm = this.getSupportFragmentManager();
//		ft = fm.beginTransaction();
//		initView();
//	}
//
//	private void initView() {
//		setContentView(R.layout.man_sale_details);
//		rg_group = (RadioGroup) findViewById(R.id.rg_group);
//		rg_group.setOnCheckedChangeListener(this);
//		xiaos = (LinearLayout)findViewById(R.id.xiaos);
//		xiaos.setBackgroundColor(Color.WHITE);
//		rb_daily_amount = (RadioButton) findViewById(R.id.rb_daily_amount);
//		rb_daily_rebate = (RadioButton) findViewById(R.id.rb_daily_rebate);
//		rb_daily_visitor = (RadioButton) findViewById(R.id.rb_daily_visitor);
//		
//		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
//		tvTitle_base.setText("销售管理");
//		img_back = (LinearLayout) findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//
//		daFrag = new DailyAmountFragment();
//		drFrag = new DailyRebateFragment();
//		dvFrag = new DailyVisitorFragment();
//		ft.add(R.id.fragment, daFrag);
//		ft.add(R.id.fragment, drFrag);
//		ft.add(R.id.fragment, dvFrag);
//		ft.commitAllowingStateLoss();
//		switch (index) {
//		case 0:
//			rb_daily_amount.setChecked(true);
//			rb_daily_amount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.sale_manager_line);
//			rb_daily_rebate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//			rb_daily_visitor.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//			break;
//		case 1:
//			rb_daily_rebate.setChecked(true);
//			rb_daily_rebate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.sale_manager_line);
//			rb_daily_amount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//			rb_daily_visitor.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//			break;
//		case 2:
//			rb_daily_visitor.setChecked(true);
//			rb_daily_visitor.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.sale_manager_line);
//			rb_daily_amount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//			rb_daily_rebate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	@Override
//	public void onCheckedChanged(RadioGroup arg0, int arg1) {
//		// TODO Auto-generated method stub
//		ft = fm.beginTransaction();
//		switch (arg1) {
//		case R.id.rb_daily_amount:
//			rb_daily_amount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.sale_manager_line);
//			rb_daily_rebate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//			rb_daily_visitor.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//			ft.show(daFrag);
//			ft.hide(drFrag);
//			ft.hide(dvFrag);
//			ft.commitAllowingStateLoss();
//			break;
//		case R.id.rb_daily_rebate:
//			rb_daily_rebate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.sale_manager_line);
//			rb_daily_amount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//			rb_daily_visitor.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//			ft.hide(daFrag);
//			ft.show(drFrag);
//			ft.hide(dvFrag);
//			ft.commitAllowingStateLoss();
//			break;
//		case R.id.rb_daily_visitor:
//			rb_daily_visitor.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.sale_manager_line);
//			rb_daily_amount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//			rb_daily_rebate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//			ft.hide(daFrag);
//			ft.hide(drFrag);
//			ft.show(dvFrag);
//			ft.commitAllowingStateLoss();
//			break;
//		default:
//			break;
//		}
//	}
//	
//	@Override
//	public void onClick(View v) {
//		super.onClick(v);
//		switch (v.getId()) {
//		case R.id.img_back:
//			finish();
//			break;
//
//		}
//	}
//}
