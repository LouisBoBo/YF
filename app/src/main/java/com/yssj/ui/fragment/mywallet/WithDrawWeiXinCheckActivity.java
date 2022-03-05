package com.yssj.ui.fragment.mywallet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.yssj.YConstance;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.infos.FundDetailsActivity;
import com.yssj.ui.activity.infos.MyWalletCommonFragmentActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.GetUserABClass;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.wxpay.WxPayUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class WithDrawWeiXinCheckActivity extends BasicActivity implements OnClickListener {
    private Context mContext;
    private TextView mNextStep;
    private LinearLayout img_back;
    private String money = "";
    private String name = "";
//	private String identity = "";

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_withdraw_weixin_check);
        mContext = this;
        money = getIntent().getStringExtra("money");
        name = getIntent().getStringExtra("name");
//		identity = getIntent().getStringExtra("identity");
        initView();
    }

    private void initView() {
        img_back = (LinearLayout) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        mNextStep = (TextView) findViewById(R.id.weixin_check);
        mNextStep.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.weixin_check:
//			openId(SHARE_MEDIA.WEIXIN, 0, (Activity) mContext);

                submit(SharedPreferencesUtil.getStringData(mContext, YConstance.Pref.USER_OPEN_ID, ""));
                break;

        }
    }

    private void submit(final String oppid) {// 微信验证

        new SAsyncTask<String, Void, HashMap<String, Object>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
                    throws Exception {

                return ComModel.WXDepositAdd(context, money, "", name, "", oppid, true);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    GetUserABClass.getUserABclass(mContext);//绑定身份证后重新获取AB类
                    String SucMoney = result.get("money").toString();
                    int flag = Integer.parseInt(result.get("flag").toString());
                    if (flag == 0) { // 提现成功


//						Intent intent = new Intent(mContext, MyWalletCommonFragmentActivity.class);
//						intent.putExtra("flag", "withDrawalFragment");
//						intent.putExtra("allMoney", money);
//						intent.putExtra("SucMoney", SucMoney);
//						intent.putExtra("WXFlag", "WXFlag");
//						intent.putExtra("alliance", "wallet");
//						startActivity(intent);


                        Intent intent = new Intent(context, FundDetailsActivity.class);
                        intent.putExtra("index", 1);
                        startActivity(intent);


                        if (WithDrawWeiXinExplainActivity.mContext != null) {
                            ((Activity) WithDrawWeiXinExplainActivity.mContext).finish();
                            WithDrawWeiXinExplainActivity.mContext = null;
                        }
                        WithDrawWeiXinCheckActivity.this.finish();
                    } else if (flag == 1 || flag == 2 || flag == 3 || flag == 4 || flag == 5 || flag == 6) {// flag==5提现金额不能大于最高提现金额或者小于最低提现金额//6时为未找到该银行卡
                        // String message = result.get("message").toString();
                        // ToastUtil.showShortText(context, message);
                        Intent intent = new Intent(mContext, MyWalletCommonFragmentActivity.class);
                        intent.putExtra("flag", "withDrawalFragment");
                        intent.putExtra("allMoney", money);
                        intent.putExtra("SucMoney", "0.0");
                        intent.putExtra("WXFlag", "WXFlag");
                        intent.putExtra("alliance", "wallet");
                        startActivity(intent);
                        if (WithDrawWeiXinExplainActivity.mContext != null) {
                            ((Activity) WithDrawWeiXinExplainActivity.mContext).finish();
                            WithDrawWeiXinExplainActivity.mContext = null;
                        }
                        WithDrawWeiXinCheckActivity.this.finish();
                    }
                }
            }

        }.execute();
    }

    /**
     * 微信验证
     *
     * @param platform
     * @param type
     * @param activity
     */
    public void openId(final SHARE_MEDIA platform, final int type, final Activity activity) {


        ToastUtil.showShortText2("授权");

//
//		final UMSocialService mController = UMServiceFactory.getUMSocialService("");
//		UMWXHandler wxHandler = new UMWXHandler(WithDrawWeiXinCheckActivity.this, WxPayUtil.APP_ID,
//				WxPayUtil.APP_SECRET);
//		wxHandler.addToSocialSDK();
//		wxHandler.setRefreshTokenAvailable(false);
//		mController.deleteOauth(WithDrawWeiXinCheckActivity.this, null, null);
//
//		mController.doOauthVerify(activity, platform, new UMAuthListener() {
//
//			@Override
//			public void onStart(SHARE_MEDIA platform) {
//				Toast.makeText(WithDrawWeiXinCheckActivity.this, "授权开始", Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onError(SocializeException e, SHARE_MEDIA platform) {
//				Toast.makeText(WithDrawWeiXinCheckActivity.this, "授权失败" + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onComplete(Bundle value, SHARE_MEDIA platform) {
//
//				mController.getPlatformInfo(activity, platform, new UMDataListener() {
//
//					@Override
//					public void onStart() {
//
//					}
//
//					@Override
//					public void onComplete(int status, Map<String, Object> info) {
//						if (info != null) {
//							if (type == 0) {// 微信
//								if (status == 200 && info != null) {
//									final String openid = info.get("openid").toString();
//									submit("" + openid);
//								}
//							}
//						}
//					}
//				});
//			}
//
//			@Override
//			public void onCancel(SHARE_MEDIA platform) {
//
//			}
//		});

    }
}
