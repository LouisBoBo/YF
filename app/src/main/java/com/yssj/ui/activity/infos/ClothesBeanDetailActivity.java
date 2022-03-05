package com.yssj.ui.activity.infos;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.ClothesBeanDetailListFragment;
import com.yssj.ui.fragment.IntergralDetailListFragment;


/***
 * @author Administrator 积分明细
 */
public class ClothesBeanDetailActivity extends BasicActivity {
	private int page = 0;
	private FragmentManager fm;
	private FragmentTransaction ft;
	private ClothesBeanDetailListFragment fFragment;
	private String pearsCount="0";
	private String freezeCount="0";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clothes_bean_details);
		page = getIntent().getIntExtra("page",0);
		pearsCount=getIntent().getStringExtra("pearsCount");
		freezeCount=getIntent().getStringExtra("freezeCount");
		fm = this.getSupportFragmentManager();
		ft = fm.beginTransaction();
		if (page==2) {
			fFragment = new ClothesBeanDetailListFragment(2,pearsCount,freezeCount);
		}else if(page==1) {
			fFragment = new ClothesBeanDetailListFragment(1,pearsCount,freezeCount);
		}else{
			fFragment = new ClothesBeanDetailListFragment(0,pearsCount,freezeCount);
		}
		ft.add(R.id.fl_content, fFragment);
		ft.commit();
	}


}
