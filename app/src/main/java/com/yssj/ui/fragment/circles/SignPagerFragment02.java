package com.yssj.ui.fragment.circles;

import com.yssj.activity.R;
import com.yssj.ui.activity.shopdetails.ShareDetailsActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.base.BasePager;
import com.yssj.ui.dialog.SignShareShopDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SignPagerFragment02 extends BasePager {
	public SignPagerFragment02(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	private View view;

@Override
public View initView() {
	view = ((Activity) context).getLayoutInflater().inflate(R.layout.sign_viewpager02, null);
		
	return view;
}
@Override
public void initData() {
	// TODO Auto-generated method stub
	
}


}
