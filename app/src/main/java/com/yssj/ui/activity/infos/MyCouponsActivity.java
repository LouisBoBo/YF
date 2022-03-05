package com.yssj.ui.activity.infos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.CardListFragment;
import com.yssj.ui.fragment.CouponsListFragment;
import com.yssj.utils.CommonUtils;

/***
 * @author Administrator  我的卡券列表
 */
public class MyCouponsActivity extends BasicActivity {

	private FragmentManager fm;
	private FragmentTransaction ft;
	private CouponsListFragment fFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle_common);
		CommonUtils.disableScreenshots(this);
		fm = this.getSupportFragmentManager();
		ft = fm.beginTransaction();
		fFragment = new CouponsListFragment();
		ft.add(R.id.fl_content, fFragment);
		ft.commit();
	}


}
