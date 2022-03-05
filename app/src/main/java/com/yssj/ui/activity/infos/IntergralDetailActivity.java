package com.yssj.ui.activity.infos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;

import com.yssj.ui.fragment.IntergralDetailListFragment;
import com.yssj.ui.fragment.TiXianZhongListFragment;

/***
 * @author Administrator 积分明细
 */
public class IntergralDetailActivity extends BasicActivity {
	private int page = 0;
	private FragmentManager fm;
	private FragmentTransaction ft;
	private IntergralDetailListFragment fFragment;
	private TiXianZhongListFragment TXZFragment;
	private boolean isTiXianMingXi; // 提现额度明细

	private boolean isTXZ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_circle_common);
		page = getIntent().getIntExtra("page", 0);

		isTiXianMingXi = getIntent().getBooleanExtra("isTiXianMingXi", false);
		isTXZ = getIntent().getBooleanExtra("isTXZ", false);
		fm = this.getSupportFragmentManager();
		ft = fm.beginTransaction();


		if(isTXZ){ //
			if (page == 1) {
				TXZFragment = new TiXianZhongListFragment(1, isTiXianMingXi);
			} else {
				TXZFragment = new TiXianZhongListFragment(0, isTiXianMingXi);
			}

		}else{
			if (isTiXianMingXi) {

				if (page == 2) {
					fFragment = new IntergralDetailListFragment(2, isTiXianMingXi);
				} else if (page == 1) {
					fFragment = new IntergralDetailListFragment(1, isTiXianMingXi);
				} else {
					fFragment = new IntergralDetailListFragment(0, isTiXianMingXi);
				}

			} else {
				if (page == 1) {
					fFragment = new IntergralDetailListFragment(1, isTiXianMingXi);
				} else {
					fFragment = new IntergralDetailListFragment(0, isTiXianMingXi);
				}
			}
		}


		if(isTXZ){
			ft.add(R.id.fl_content, TXZFragment);
		}else{
			ft.add(R.id.fl_content, fFragment);

		}
		ft.commit();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
	}
}
