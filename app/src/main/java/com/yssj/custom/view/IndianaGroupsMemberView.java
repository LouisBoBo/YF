package com.yssj.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yssj.activity.R;
import com.yssj.utils.GlideUtils;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.ToastUtil;

public class IndianaGroupsMemberView extends LinearLayout {
	private Context mContext;
	private ImageView ivHeader;
	private TextView  emptyHeader;
	private TextView tvNickname;
	private ImageView tz_icon;

	public IndianaGroupsMemberView(Context context) {
		super(context);
		mContext = context;
		initView(context);
	}

	public IndianaGroupsMemberView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView(context);
	}

	public IndianaGroupsMemberView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initView(context);
	}

	private void initView(Context context) {
		LayoutInflater.from(context).inflate(
				R.layout.indiana_groups_member_view, this, true);
		ivHeader = (ImageView) this.findViewById(R.id.groups_head_iv);
		tvNickname = (TextView) this.findViewById(R.id.groups_nickname);
		tz_icon = (ImageView) this.findViewById(R.id.groups_head_tz_icon);
		emptyHeader = (TextView) this.findViewById(R.id.groups_head_empty);

	}

	public void setIconViewInvisible(){
		tz_icon.setVisibility(View.INVISIBLE);
	}

	public void setIconViewVisible(){
		tz_icon.setVisibility(View.VISIBLE);
	}


	//没有用户 显示谁要……
	public void setEmptyView(String shopName){
		tvNickname.setVisibility(View.INVISIBLE);
		tz_icon.setVisibility(View.INVISIBLE);
		ivHeader.setVisibility(View.GONE);
		emptyHeader.setVisibility(View.VISIBLE);
		emptyHeader.setText("谁要\n"+shopName);
		emptyHeader.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				ToastUtil.showShortText(mContext,"快去分享邀请好友参与吧~");
			}
		});

	}
	//有用户 显示用户头像和昵称
	public void setHeaderView(String url,String nickname){
		emptyHeader.setVisibility(View.GONE);
		tvNickname.setVisibility(View.VISIBLE);
		ivHeader.setVisibility(View.VISIBLE);
//		PicassoUtils.initImage(mContext, url, ivHeader);
		GlideUtils.initRoundImage(Glide.with(mContext),mContext, url, ivHeader);
		tvNickname.setText(nickname);

	}
}
