package com.yssj.ui.activity.infos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.FundListFragment;

/***
 * @author Administrator 我的流水
 */
public class FundListActivity extends BasicActivity {

	private FragmentManager fm;
	private FragmentTransaction ft;

	private FundListFragment fFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_fund_list);
		fm = this.getSupportFragmentManager();
		ft = fm.beginTransaction();
		fFragment = new FundListFragment();
		ft.add(R.id.fragment_list, fFragment);
		ft.commit();
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {

		}

	}

}
