package com.yssj.ui.fragment;

import com.yssj.ui.base.BaseFragment;

import android.app.Fragment;
import android.os.Bundle;

public abstract class BackHandledFragment extends BaseFragment {
	protected BackHandledInterface mBackHandledInterface;

	/**
	 * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
	 * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
	 * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
	 */
	public abstract boolean onBackPressed();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例
		if (!(getActivity() instanceof BackHandledInterface)) {
			
		} else {
			this.mBackHandledInterface = (BackHandledInterface) getActivity();
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		// 告诉FragmentActivity，当前Fragment在栈顶
		mBackHandledInterface.setSelectedFragment(this);
	}

}
