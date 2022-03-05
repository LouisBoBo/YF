//package com.yssj.ui.activity.myshop;
//
//import android.os.Bundle;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.RadioGroup;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//
//import com.yssj.activity.R;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.ui.fragment.myshop.MyCustomFragment;
//import com.yssj.ui.fragment.myshop.MyshopFragment;
//
//public class MyShopActivity extends BasicActivity implements
//		OnCheckedChangeListener {
//
//	private View barView;
//	private ImageView img_back;
//	private Button btn_preview;
//
//	private RadioGroup rg_titles;
//
//	private FragmentManager fm;
//	private FragmentTransaction ft;
//
//	private MyshopFragment myshopFragment;
//	private MyCustomFragment myCustomFragment;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_myshop);
//		fm = this.getSupportFragmentManager();
//		ft = fm.beginTransaction(); 
//		setCustomActionBar();
//		initFragment();
//	}
//
//	/****
//	 * 自定义actionbar
//	 * 
//	 * @param layout
//	 */
//	private void setCustomActionBar() {
//		aBar.setDisplayHomeAsUpEnabled(true);
//		aBar.setDisplayShowCustomEnabled(true);
//		aBar.setDisplayShowTitleEnabled(false);// 隐藏Title
//		aBar.setDisplayShowHomeEnabled(false);// 隐藏Home logo
//		if (barView == null) {
//			barView = View.inflate(this, R.layout.bar_my_shop, null);
//		}
//		aBar.setCustomView(barView);
//
//		img_back = (ImageView) barView.findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//		btn_preview = (Button) barView.findViewById(R.id.btn_preview);
//		btn_preview.setOnClickListener(this);
//
//		rg_titles = (RadioGroup) findViewById(R.id.rg_titles);
//		rg_titles.setOnCheckedChangeListener(this);
//	}
//
//	/**
//	 * 初始化Fragment
//	 */
//	private void initFragment() {
//		myshopFragment = new MyshopFragment();
//		myCustomFragment = new MyCustomFragment();
//		ft.add(R.id.fragment, myshopFragment);
//		ft.show(myshopFragment);
//		ft.commitAllowingStateLoss();
//	}
//
//	@Override
//	public void onClick(View v) {
//		super.onClick(v);
//		switch (v.getId()) {
//		case R.id.img_back:// 返回按钮
//			this.finish();
//			break;
//		case R.id.btn_preview:// 预览按钮
//
//			break;
//		default:
//			break;
//		}
//	}
//
//	@Override
//	public void onCheckedChanged(RadioGroup arg0, int arg1) {
//		// TODO Auto-generated method stub
//		ft = fm.beginTransaction();
//		if (arg1 == R.id.rb_my_shop) {
//			if (!myshopFragment.isAdded()) {
//				ft.add(R.id.fragment, myshopFragment);
//			}
//			ft.show(myshopFragment);
//			if (myCustomFragment.isAdded()) {
//				ft.hide(myCustomFragment);
//			}
//			ft.commit();
//		} else if (arg1 == R.id.rb_my_custom) {
//			if (!myCustomFragment.isAdded()) {
//				ft.add(R.id.fragment, myCustomFragment);
//			}
//			ft.show(myCustomFragment);
//			if (myshopFragment.isAdded()) {
//				ft.hide(myshopFragment);
//			}
//			ft.commit();
//		}
//	}
//}
