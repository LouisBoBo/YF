package com.yssj.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;

import com.yssj.ui.fragment.IntergralDetailListFragment;
import com.yssj.ui.fragment.MessageCenterDetailListFragment;

/***
 * 消息中心
 */
public class MessageCenterActivity extends BasicActivity {
	private int page = 0;
	private FragmentManager fm;
	private FragmentTransaction ft;
	private MessageCenterDetailListFragment fFragment;
	private boolean isTiXianMingXi; // 提现额度明细

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_circle_common);
		page = getIntent().getIntExtra("page", 0);

		isTiXianMingXi = getIntent().getBooleanExtra("isTiXianMingXi", false);
		fm = this.getSupportFragmentManager();
		ft = fm.beginTransaction();

		if (page == 2) {
			fFragment = new MessageCenterDetailListFragment(2);
		} else if (page == 1) {
			fFragment = new MessageCenterDetailListFragment(1);
		} else {
			fFragment = new MessageCenterDetailListFragment(0);
		}

		ft.add(R.id.fl_content, fFragment);
		ft.commit();
	}

}
