
package com.yssj.ui.activity.classfication;

import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.ClassficationFragment;
import com.yssj.ui.fragment.ClassficationNewFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 分类 外层的Activity
 * @author Administrator
 * @date 2016年12月29日下午4:34:21
 */
public class ClassficationActivity extends BasicActivity {
	
	private FragmentManager fm;
	private FragmentTransaction ft;
	private ClassficationNewFragment fFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		setContentView(R.layout.activity_classfication);
		fm = this.getSupportFragmentManager();
		ft = fm.beginTransaction();
		fFragment = ClassficationNewFragment.newInstance("ClassficationNewFragment", this);
		ft.replace(R.id.container_classfication, fFragment,"ClassficationNewFragment");
		ft.commit();
	}
		
}
