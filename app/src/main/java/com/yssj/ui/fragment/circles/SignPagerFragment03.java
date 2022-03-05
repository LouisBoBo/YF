package com.yssj.ui.fragment.circles;

import java.util.Random;

import com.yssj.activity.R;
import com.yssj.ui.activity.shopdetails.ShareDetailsActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.base.BasePager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SignPagerFragment03 extends BasePager {
	private ImageView mImage;
	public SignPagerFragment03(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private View view;

	@Override
	public View initView() {
		view = ((Activity) context).getLayoutInflater().inflate(R.layout.sign_viewpager03, null);
		mImage=(ImageView) view.findViewById(R.id.img_fenxiang);
		int[] imgs={};
		mImage.setImageResource(imgs[new Random().nextInt(5)]);
		return view;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
