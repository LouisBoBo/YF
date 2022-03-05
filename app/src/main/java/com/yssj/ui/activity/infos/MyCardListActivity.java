package com.yssj.ui.activity.infos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.CardListFragment;

/***
 * @author Administrator 我的钱包
 */
public class MyCardListActivity extends BasicActivity {

	private FragmentManager fm;
	private FragmentTransaction ft;
	private CardListFragment fFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_card_list);
		fm = this.getSupportFragmentManager();
		ft = fm.beginTransaction();
		fFragment = new CardListFragment();
//		ft.add(R.id.card_list_fragment, fFragment);
//		ft.commit();
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {

		}

	}

}
