package com.yssj.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;
import com.yssj.Constants;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.activity.wxapi.WXEntryActivity;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelL;
import com.yssj.ui.activity.infos.MyWalletCommonFragmentActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.ShareTCCompleteDiaolg;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;

import java.text.DecimalFormat;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/***
 * 好友提成奖励
 */
public class FriendCommissionActivity extends BasicActivity implements OnClickListener, WXEntryActivity.WXminiAPPshareListener {

    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.tv4)
    TextView tv4;
    @Bind(R.id.tv1)
    TextView tv1;
    private View imgBack;
    private TextView balanceTv, limitTv;// 总金额TextView
    private View to_withdrawal_btn;// 去提现 按钮TextView
    private View jinrijiangli_icon;// 和今日奖励
    //    private LinearLayout mContainer;
    public int width;
    public int height;
    private Context mContext;

    private boolean mWxInstallFlag;
    // 实现对微信好友的分享：
    private Intent weixinShareIntent;
    // 实现对微信朋友圈的分享：
    private Intent wXinShareIntent;
    private int shareType;

    private double mSumBalance, mLimit;// 总的余额 和 可提现额度
    private String link;
    private View ivWx;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_commission);
        ButterKnife.bind(this);
        context = this;
        AppManager.getAppManager().addActivity(this);
        link = YUrl.YSS_URL_ANDROID_H5 + "/view/activity/mission.html?realm=" + YCache.getCacheUser(mContext).getUser_id();
        initView();
        getRuleText();
        initData();//本页面详情
        StringUtils.initShareText(tv1, tv2, tv3, tv4);

        WXEntryActivity.setWXminiShareListener(this);

    }

    /**
     * 初始化View
     */
    private void initView() {
        mContext = this;
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);

        balanceTv = (TextView) findViewById(R.id.sum_balance_tv);
        balanceTv.getPaint().setFakeBoldText(true);// 设置加粗字体
        limitTv = (TextView) findViewById(R.id.limit_tv);
        limitTv.getPaint().setFakeBoldText(true);// 设置加粗字体
        to_withdrawal_btn = findViewById(R.id.to_withdrawal_btn);
        to_withdrawal_btn.setOnClickListener(this);

        jinrijiangli_icon = findViewById(R.id.jinrijiangli_icon);
        jinrijiangli_icon.setOnClickListener(this);
//        mContainer = (LinearLayout) findViewById(R.id.huodongguize_container);
        ivWx = findViewById(R.id.ll_wxin);
        View ivFriends = findViewById(R.id.ll_wxin_circle);

        //暂时隐藏朋友圈
        ivFriends.setVisibility(View.GONE);

        ivWx.setOnClickListener(this);
        ivFriends.setOnClickListener(this);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;


        try {
            // // 是否安装了微信
            if (WXcheckUtil.isWeChatAppInstalled(mContext)) {
                mWxInstallFlag = true;
            } else {
                mWxInstallFlag = false;
            }
        } catch (Exception e) {
        }


    }


    private void initData() {
        new SAsyncTask<Void, Void, String[]>(FriendCommissionActivity.this, R.string.wait) {

            @Override
            protected String[] doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel2.myWalletInfo(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, String[] result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result != null && result.length > 0) {
                        mSumBalance = Double.parseDouble(result[0]) + Double.parseDouble(result[9]);//金额显示冻结和非冻结余额的总和
                        balanceTv.setText("" + new DecimalFormat("#0.00").format(mSumBalance));

                        mLimit = Double.parseDouble(result[6]) + Double.parseDouble(result[7]);
                        limitTv.setText("" + new DecimalFormat("#0.00").format(mLimit));

                    }
                }
            }

        }.execute();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.to_withdrawal_btn: // 提现按钮

                Intent to_withdrawal_btn_intent = new Intent(context, MyWalletCommonFragmentActivity.class);
                to_withdrawal_btn_intent.putExtra("flag", "withDrawalFragment");
                to_withdrawal_btn_intent.putExtra("alliance", "wallet");
                startActivity(to_withdrawal_btn_intent);
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                break;
            case R.id.jinrijiangli_icon:
                // TODO 今日奖励弹窗
//                jinRiJiangLiDialog();

                startActivity(new Intent(mContext, MyYJactivity.class));
                break;
            case R.id.ll_wxin_circle: // 分享到微信朋友圈

                if (!mWxInstallFlag) {
                    ToastUtil.showLongText(mContext, "您还未安装微信喔！");
                    return;
                }

                shareType = 1;
                ShareUtil.addWXPlatform(mContext);
                getShareContent();
                addShareCount();
                break;
            case R.id.ll_wxin: // 分享到微信好友

                if (!mWxInstallFlag) {
                    ToastUtil.showLongText(mContext, "您还未安装微信喔！");
                    return;
                }
                shareType = 0;
                ShareUtil.addWXPlatform(mContext);
                getShareContent();
                addShareCount();
                break;

            default:
                break;
        }
    }


    /**
     * 今日奖励
     */
//    private void jinRiJiangLiDialog() {
//
//        new SAsyncTask<Void, Void, HashMap<String, String>>(FriendCommissionActivity.this, R.string.wait) {
//
//            @Override
//            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
//                return ComModelL.getTadayPraiseMoney(context);
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (null == e && result != null) {
//
//                    ToastUtil.showDialog( new ShareTCtishiDiaog(mContext,result,R.style.invate_dialog_style));
//                }
//            }
//
//        }.execute();
//    }


    /**
     * \
     * 获得 活动规则相关文案
     */
    private void getRuleText() {

//        new SAsyncTask<Void, Void, List<String>>((FragmentActivity) mContext, R.string.wait) {
//
//            @Override
//            protected List<String> doInBackground(FragmentActivity context, Void... params) throws Exception {
//                return ComModelZ.getIndianaRuleText("hytc_hdgz");
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, List<String> result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (null == e && result != null && result.size() > 0) {
//                    mContainer.removeAllViews();
//                    LinearLayout.LayoutParams containerParams  = (LinearLayout.LayoutParams) mContainer.getLayoutParams();
//                    containerParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                    mContainer.setLayoutParams(containerParams);
//
//                    for (int i = 0; i < result.size(); i++) {
//
//                        TextView tvRule = new TextView(mContext);
//                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
//                                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                        tvRule.setTextSize(14);
//                        tvRule.setTextColor(Color.parseColor("#3E3E3E"));
//                        tvRule.setText((i + 1) + "." + result.get(i));
//                        if (i != 0) {
//                            params.setMargins(0, DP2SPUtil.dip2px(mContext, 15), 0, 0);
//                        }
//                        mContainer.addView(tvRule, params);
//                    }
//                }
//            }
//
//        }.execute();


    }


    private void getShareContent() {
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params) throws Exception {
//                return ComModelZ.getShareGroupTitleContent();
                return ComModelL.getContentText("hytc_h5_fx");
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null) {
                    HashMap<String, Object> m = (HashMap<String, Object>) result.get("hytc_h5_fx");
                    if (m != null && m.size() > 0) {
                        String mTitle = (String) m.get("title");
                        String text = (String) m.get("text");
                        String icon = (String) m.get("icon");
                        share(mTitle, text, icon);
                    } else {
                        share(shareTitle, shareContent, "");
                    }


//                    UserInfo user = YCache.getCacheUserSafe(context);
//                    mTitle = mTitle.replaceFirst("\\$\\{replace\\}", "0");
//                    mTitle = mTitle.replaceFirst("\\$\\{replace\\}", "0");
//
//                    text = text.replaceFirst("\\$\\{replace\\}", ""+user.getNickname());
//                    text = text.replaceFirst("\\$\\{replace\\}", "0");

                } else {
                    share(shareTitle, shareContent, "");
                }
            }

        }.execute();

    }

    private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);
    private String shareTitle = "我在衣蝠花5分钟做任务已赚了《可提现》20.5元现金。爽呆！";
    private String shareContent = "真实可提现，非优惠券。快来！";

    private void share(String shareTitle, String shareContent, String sharePic) {

//        String sharePic = shop_code.substring(1, 4) + File.separator + shop_code + File.separator + def_pic+"!180";

        wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
        weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());
        UMImage umImage = new UMImage(mContext, sharePic.startsWith("http") ? sharePic : YUrl.imgurl + sharePic);

        if (shareType == 1) {// 微信好友朋友圈
            SharedPreferencesUtil.saveStringData(mContext, "messageSubSub", shareTitle);
            ShareUtil.setShareContent(mContext, umImage, shareTitle, link);
            performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
        } else if (shareType == 0) {// 微信好友


            String wxMiniPath = "/pages/shouye/shouye?isShareFlag=true&goto=sign&user_id=" + YCache.getCacheUser(mContext).getUser_id();
            //分享到微信统一分享小程序
            WXminiAPPShareUtil.shareToWXminiAPP(mContext, "", shareTitle, wxMiniPath, true);


//
//            WeiXinShareContent wei = new WeiXinShareContent();
//            wei.setShareContent(shareContent);
//            wei.setTitle(shareTitle);
//            wei.setTargetUrl(link);
//            wei.setShareMedia(umImage);
//            mController.setShareMedia(wei);
//            performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);
        }

    }

    public void performShare(SHARE_MEDIA platform, final Intent intent) {

        mController.postShare(mContext, platform, new SocializeListeners.SnsPostListener() {

            @Override
            public void onStart() {
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                if (eCode == StatusCode.ST_CODE_SUCCESSED) {

                    //TODO 继续分享
                    ToastUtil.showDialog(new ShareTCCompleteDiaolg(context, R.style.invate_dialog_style, ivWx));

                } else {

                }
                SocializeConfig.getSocializeConfig().cleanListeners();// 清空友盟回调监听器
                // 避免任务接口多次被调用


            }
        });
    }


    private void addShareCount() {
        new SAsyncTask<Void, Void, ReturnInfo>(FriendCommissionActivity.this, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModelL.fcAddShareCount(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null) {

                }
            }

        }.execute();

    }


    @Override
    public void wxMiniShareSuccess() {
        ToastUtil.showDialog(new ShareTCCompleteDiaolg(context, R.style.invate_dialog_style, ivWx));

    }
}
