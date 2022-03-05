package com.yssj.ui.activity.infos;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;

import com.yssj.ui.fragment.UsefulCouponsListFragment;
import com.yssj.utils.CommonUtils;

/***
 * @author Administrator 可用的优惠卷
 */
public class UsefulCouponsActivity extends BasicActivity {

	private FragmentManager fm;
	private FragmentTransaction ft;
	private UsefulCouponsListFragment fFragment;
	private double amount;
	private double jinquan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		aBar.hide();
		amount = getIntent().getDoubleExtra("amount", 0);
		jinquan = getIntent().getDoubleExtra("jinquan", 0);
		setContentView(R.layout.activity_circle_common);
		CommonUtils.disableScreenshots(this);
		fm = this.getSupportFragmentManager();
		ft = fm.beginTransaction();
		fFragment = new UsefulCouponsListFragment(amount,jinquan);
		ft.add(R.id.fl_content, fFragment);
		ft.commit();
	}


}
