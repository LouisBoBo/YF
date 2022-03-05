package com.yssj.ui.activity.shopdetails;

import java.util.HashMap;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.entity.Store;
import com.yssj.entity.UserInfo;
import com.yssj.huanxin.activity.BaseActivity;
import com.yssj.model.ComModel2;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.YCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SweepImageActivity extends BasicActivity implements OnClickListener {
	private RoundImageButton mUserPic;
	private TextView mUserName;
	private ImageView mIvCode;
	private LinearLayout img_back;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sweep_image);
		mContext = this;
		initView();
		initData();
	}

	private void initData() {
		UserInfo info = YCache.getCacheUserSafe(SweepImageActivity.this);
		mUserName.setText(info.getNickname());
//		SetImageLoader.initImageLoader(SweepImageActivity.this, mUserPic, info.getPic(), "");
		PicassoUtils.initImage(mContext, info.getPic(), mUserPic);
		// getCodeLink();
		Store store = YCache.getCacheStoreSafe(mContext);
		String link = YUrl.YSS_URL_ANDROID_H5+"view/download/6.html?realm=" + YCache.getCacheUser(mContext).getUser_id();
		Bitmap bmQr = QRCreateUtil.createQrImage(link, 180, 180);// 得到二维码图片
		mIvCode.setImageBitmap(bmQr);
	}

	private void initView() {
		mUserPic = (RoundImageButton) findViewById(R.id.img_user_pic);
		mUserName = (TextView) findViewById(R.id.img_user_name);
		mIvCode = (ImageView) findViewById(R.id.iv_qr_code);
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.img_back:
			finish();
			break;
		default:
			break;
		}

	}

	public void getCodeLink() {
		new SAsyncTask<Void, Void, HashMap<String, Object>>(this, 0) {

			@Override
			protected void onPostExecute(FragmentActivity mContext, final HashMap<String, Object> result, Exception e) {
				super.onPostExecute(mContext, result, e);

				if (e == null) {

					String link = result.get("link").toString();

					Bitmap bmQr = QRCreateUtil.createQrImage(link, 180, 180);// 得到二维码图片

					mIvCode.setImageBitmap(bmQr);
				} else {
				}

			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
					throws Exception {

				return ComModel2.getInviteFriend(context);
			}

		}.execute();
	}
}
