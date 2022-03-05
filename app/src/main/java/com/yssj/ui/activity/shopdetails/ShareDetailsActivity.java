package com.yssj.ui.activity.shopdetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.infos.MyWalletActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShareDetailsActivity extends BasicActivity implements OnClickListener {
	private LinearLayout mFansContain;
	private List<String> mListFansPic;
	private LinearLayout img_back;
	private TextView tvTitle_base;
	private TextView mTvBalance;
	private TextView mTvFansCount;// 新增粉丝数量
	private TextView mTvFansTwo, mTvFansThree, mTvFansFive, mTvFansEight, mTvFansTwelve, mTvFansFifteen;
	private TextView mTvLookTwo, mTvLookThree, mTvLookFive, mTvLookEight, mTvLookTwelve, mTvLookFifteen;
	private int mFansCount = 0;
	private int mLookCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_share_details);
		mListFansPic = new ArrayList<String>();
		initView();
		initData();
	}

	private void initData() {
		// 查询详情
		queryShareDetails();
	}

	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
	}
	
	/**
	 * 根据粉丝和浏览数量更换图片
	 */
	private void changePic() {
//		mTvFansCount.setText("" + mFansCount);
//		if (10 <= mFansCount && mFansCount < 25) {
//			mTvFansTwo.setBackgroundResource(R.drawable.icon_2monney_lingqu);
//		} else if (mFansCount >= 25 && mFansCount < 50) {
//			mTvFansTwo.setBackgroundResource(R.drawable.icon_2monney_lingqu);
//			mTvFansThree.setBackgroundResource(R.drawable.icon_3monney_lingqu);
//		} else if (mFansCount >= 50 && mFansCount < 90) {
//			mTvFansTwo.setBackgroundResource(R.drawable.icon_2monney_lingqu);
//			mTvFansThree.setBackgroundResource(R.drawable.icon_3monney_lingqu);
//			mTvFansFive.setBackgroundResource(R.drawable.icon_5monney_lingqu);
//		} else if (mFansCount >= 90 && mFansCount < 150) {
//			mTvFansTwo.setBackgroundResource(R.drawable.icon_2monney_lingqu);
//			mTvFansThree.setBackgroundResource(R.drawable.icon_3monney_lingqu);
//			mTvFansFive.setBackgroundResource(R.drawable.icon_5monney_lingqu);
//			mTvFansEight.setBackgroundResource(R.drawable.icon_8monney_lingqu);
//		} else if (mFansCount >= 150 && mFansCount < 225) {
//			mTvFansTwo.setBackgroundResource(R.drawable.icon_2monney_lingqu);
//			mTvFansThree.setBackgroundResource(R.drawable.icon_3monney_lingqu);
//			mTvFansFive.setBackgroundResource(R.drawable.icon_5monney_lingqu);
//			mTvFansEight.setBackgroundResource(R.drawable.icon_8monney_lingqu);
//			mTvFansTwelve.setBackgroundResource(R.drawable.icon_12monney_lingqu);
//		} else if (mFansCount >= 225) {
//			mTvFansTwo.setBackgroundResource(R.drawable.icon_2monney_lingqu);
//			mTvFansThree.setBackgroundResource(R.drawable.icon_3monney_lingqu);
//			mTvFansFive.setBackgroundResource(R.drawable.icon_5monney_lingqu);
//			mTvFansEight.setBackgroundResource(R.drawable.icon_8monney_lingqu);
//			mTvFansTwelve.setBackgroundResource(R.drawable.icon_12monney_lingqu);
//			mTvFansFifteen.setBackgroundResource(R.drawable.icon_15monney_lingqu);
//		}

		if (mLookCount >= 100 && mLookCount < 250) {
			mTvLookTwo.setBackgroundResource(R.drawable.icon_2monney_lingqu);
		} else if (mLookCount >= 250 && mLookCount < 500) {
			mTvLookTwo.setBackgroundResource(R.drawable.icon_2monney_lingqu);
			mTvLookThree.setBackgroundResource(R.drawable.icon_3monney_lingqu);
		} else if (mLookCount >= 500 && mLookCount < 900) {
			mTvLookTwo.setBackgroundResource(R.drawable.icon_2monney_lingqu);
			mTvLookThree.setBackgroundResource(R.drawable.icon_3monney_lingqu);
			mTvLookFive.setBackgroundResource(R.drawable.icon_5monney_lingqu);
		} else if (mLookCount >= 900 && mLookCount < 1500) {
			mTvLookTwo.setBackgroundResource(R.drawable.icon_2monney_lingqu);
			mTvLookThree.setBackgroundResource(R.drawable.icon_3monney_lingqu);
			mTvLookFive.setBackgroundResource(R.drawable.icon_5monney_lingqu);
			mTvLookEight.setBackgroundResource(R.drawable.icon_8monney_lingqu);
		} else if (mLookCount >= 1500 && mLookCount < 2250) {
			mTvLookTwo.setBackgroundResource(R.drawable.icon_2monney_lingqu);
			mTvLookThree.setBackgroundResource(R.drawable.icon_3monney_lingqu);
			mTvLookFive.setBackgroundResource(R.drawable.icon_5monney_lingqu);
			mTvLookEight.setBackgroundResource(R.drawable.icon_8monney_lingqu);
			mTvLookTwelve.setBackgroundResource(R.drawable.icon_12monney_lingqu);
		} else if (mLookCount >= 2250) {
			mTvLookTwo.setBackgroundResource(R.drawable.icon_2monney_lingqu);
			mTvLookThree.setBackgroundResource(R.drawable.icon_3monney_lingqu);
			mTvLookFive.setBackgroundResource(R.drawable.icon_5monney_lingqu);
			mTvLookEight.setBackgroundResource(R.drawable.icon_8monney_lingqu);
			mTvLookTwelve.setBackgroundResource(R.drawable.icon_12monney_lingqu);
			mTvLookFifteen.setBackgroundResource(R.drawable.icon_15monney_lingqu);
		}

	}

	private void initView() {
		mFansContain = (LinearLayout) findViewById(R.id.share_ll_contain);
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("额外奖励详情");
		mTvBalance = (TextView) findViewById(R.id.share_tv_balance);
		mTvBalance.setOnClickListener(this);
		mTvFansCount = (TextView) findViewById(R.id.share_tv_fans_count);

		mTvFansTwo = (TextView) findViewById(R.id.share_fans_two);
		mTvFansThree = (TextView) findViewById(R.id.share_fans_three);
		mTvFansFive = (TextView) findViewById(R.id.share_fans_five);
		mTvFansEight = (TextView) findViewById(R.id.share_fans_eight);
		mTvFansTwelve = (TextView) findViewById(R.id.share_fans_twelve);
		mTvFansFifteen = (TextView) findViewById(R.id.share_fans_fifteen);

		mTvLookTwo = (TextView) findViewById(R.id.share_look_two);
		mTvLookThree = (TextView) findViewById(R.id.share_look_three);
		mTvLookFive = (TextView) findViewById(R.id.share_look_five);
		mTvLookEight = (TextView) findViewById(R.id.share_look_eight);
		mTvLookTwelve = (TextView) findViewById(R.id.share_look_twelve);
		mTvLookFifteen = (TextView) findViewById(R.id.share_look_fifteen);
	}

	// TODO：填充数据
	public void fillFansPic(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			RoundImageButton image = new RoundImageButton(this);
			image.setBackgroundColor(Color.TRANSPARENT);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DP2SPUtil.dp2px(this, 34),
					DP2SPUtil.dp2px(this, 34));
			params.setMargins(0, 0, DP2SPUtil.dp2px(this, 6), 0);
//			SetImageLoader.initImageLoader(this, image, ""+ list.get(i), "");
			PicassoUtils.initImage(this, ""+ list.get(i), image);
			mFansContain.addView(image, params);
		}
	}

	// TODO:
	// 查询分享额外奖励详情

	private void queryShareDetails() {
		new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) this, null, R.string.wait) {
			@Override
			protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				HashMap<String, Object> list = ComModel.queryShareDetails(context);
				return list;

			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, Object> map, Exception e) {
				if (e != null) {// 查询异常
					ShareDetailsActivity.this.finish();
				} else {// 查询商品详情成功，刷新界面
					if (map != null && map.size() > 0) {
//						if (mListFansPic == null) {
//							mListFansPic = new ArrayList<String>();
//						}
//						mFansCount = (Integer) map.get("fans_count");
						mLookCount = (Integer) map.get("bro_count");
//						mListFansPic = (List<String>) map.get("pic_list");
						changePic();
//						fillFansPic(mListFansPic);

					} else {
						// TODO:
					}
				}

			};

			@Override
			protected boolean isHandleException() {
				return true;
			};
		}.execute();

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.img_back:
			onBackPressed();
			break;
		case R.id.share_tv_balance:
			Intent intent = new Intent(this, MyWalletActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}
}
