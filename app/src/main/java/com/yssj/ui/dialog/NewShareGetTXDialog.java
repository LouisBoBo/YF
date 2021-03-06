package com.yssj.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.DeviceConfig;
import com.yssj.Constants;
import com.yssj.YConstance;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.activity.wxapi.WXEntryActivity;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.MatchShop;
import com.yssj.entity.MatchShop.CollocationShop;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelZ;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.shopdetails.NoShareActivity;
import com.yssj.ui.base.BasePager;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.ui.fragment.circles.SignPagerFragment01NewShareTx;
import com.yssj.ui.fragment.circles.SignPagerFragment03;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongjiShareCount;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NewShareGetTXDialog extends Dialog implements OnClickListener, WXEntryActivity.WXminiAPPshareListener {

    private ImageView icon_close;
    // private LinearLayout go_share_to_sign;
    private boolean isWXfriend;  //???????????????????????????
    private Activity mActivity;
    private Context mContext;
    private int now_type_id;
    private LinearLayout iv_qq_share;
    private ImageView iv_wxin_share;
    private int ids;
    private Intent wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
    private final Timer timer = new Timer();
    private TimerTask task;
    private TextView text1, text2, title, tv_liaojie_more;

    public static PublicToastDialog shareWaitDialog = null;
    private String collocation_code;/* = "CS20160919xaQ69M7i" */// ????????????????????????
    private boolean WxCircleIsLink;// ??????????????? ??????????????????????????? false??????????????? true???????????????
    private int shareNum;
    private ViewPager mViewPager;
    private List<BasePager> mListFragment;
    private PagerAdapter mAdapter;
    private LinearLayout mPointContainer;
    private TextView icon_left, icon_right;

    private boolean qqIn = true;// ???????????????QQ
    private boolean wxIn = true;// ?????????????????????


    String str1 = "";
    String str2 = ""; //???????????? ?????????????????????title
    String shop_name = "??????";


    String shareTo = "link";//????????????
    private boolean teshuShare;//????????????????????????????????????
    private String teshuLink = "";//????????????
    private String shareTitle = "????????????????????????????????????????????????IPHONE7"; //?????????
    private String shareContent = "????????????????????????1??????????????????1?????????IPHONE7??????????????????????????????";//?????????
    private UMImage teshuUmImage; //?????????


    public static boolean isShow;
    private String DuobaoShop_code = ""; //???????????????????????????????????????
    private String shareMIniAPPimgPic;//??????????????????????????????????????????
    private String duoBaoSharePic = ""; //??????????????????

    private String H5moneyPic = ""; //?????????????????????


    public NewShareGetTXDialog(Activity activity, Context context, int style, int now_type_id) {
        super(context, style);
        setCanceledOnTouchOutside(true);
        this.now_type_id = now_type_id;
        this.mActivity = activity;
        this.mContext = context;
    }

    @Override
    public void wxMiniShareSuccess() {
        LogYiFu.e("111111111","111111111111111111111111");
        ToastUtil.showDialog(new NewShareTXdialogComplete(mContext, R.style.DialogStyle1));
    }


    class MyPagerAdapter extends PagerAdapter {
        private List<BasePager> pageLists;

        public MyPagerAdapter(List<BasePager> pageLists) {
            this.pageLists = pageLists;
        }

        @Override
        public int getCount() {
            return pageLists.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = ((BasePager) pageLists.get(position)).getRootView();
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(mContext, R.layout.sign_viewpager, null);

        SignListAdapter.doType = 25;
        now_type_id = 5;

        try {
            // ???????????????QQ
            if (!DeviceConfig.isAppInstalled("com.tencent.mobileqq", mContext)) {
                qqIn = false;
            }
        } catch (Exception e) {
        }

        try {
            // // ?????????????????????
            if (!WXcheckUtil.isWeChatAppInstalled(mContext)) {
                wxIn = false;
            }
        } catch (Exception e) {

        }

        setContentView(view);
        setCancelable(false);
        isShow = true;
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        icon_left = (TextView) view.findViewById(R.id.icon_left);
        icon_right = (TextView) view.findViewById(R.id.icon_right);
        mPointContainer = (LinearLayout) view.findViewById(R.id.shop_container);
        mViewPager = (ViewPager) findViewById(R.id.sign_content_viewpager);

        WXEntryActivity.setWXminiShareListener(this);


        try {
            shareTo = SignListAdapter.doValue.split(",")[0].split("=")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (shareTo.equals("spellGroup") || shareTo.equals("indiana") || shareTo.equals("h5money")) {
            teshuShare = true;
//            getShareTitleText();

            //??????????????????
            initShareContent();
        } else {
            teshuShare = false;
        }


        mListFragment = new ArrayList<BasePager>();
//		mListFragment.add(new SignPagerFragment02(mContext));
        mListFragment.add(new SignPagerFragment01NewShareTx(mContext));
        mListFragment.add(new SignPagerFragment03(mContext));
        // mListFragment.add(new SignPagerFragment02());
        // mListFragment.add(new SignPagerFragment03());
        // mAdapter = new PagerAdapter(manager,mListFragment);
        for (int i = 0; i < mListFragment.size(); i++) {
            View viewPoint = new View(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 8),
                    DP2SPUtil.dp2px(mContext, 8));
            viewPoint.setBackgroundResource(R.drawable.sign_check);
            if (i != 0) {
                layoutParams.setMargins(DP2SPUtil.dp2px(mContext, 10), 0, 0, 0);
                viewPoint.setBackgroundResource(R.drawable.sign_uncheck);
            }
            mPointContainer.addView(viewPoint, layoutParams);
        }
        mAdapter = new MyPagerAdapter(mListFragment);
        mViewPager.setAdapter(mAdapter);

        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    icon_left.setVisibility(View.INVISIBLE);
                    icon_right.setVisibility(View.INVISIBLE);
                } else if (position == 1) {
//					icon_left.setVisibility(View.VISIBLE);
//					icon_right.setVisibility(View.VISIBLE);
                    icon_left.setVisibility(View.INVISIBLE);
                    icon_right.setVisibility(View.INVISIBLE);
                }
//				else if (position == 2) {
//					icon_left.setVisibility(View.VISIBLE);
//					icon_right.setVisibility(View.INVISIBLE);
//				}
                for (int i = 0; i < mListFragment.size(); i++) {
                    if (position == i) {
                        mPointContainer.getChildAt(i).setBackgroundResource(R.drawable.sign_check);
                    } else {
                        mPointContainer.getChildAt(i).setBackgroundResource(R.drawable.sign_uncheck);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

        iv_wxin_share = (ImageView) findViewById(R.id.iv_wxin_share);
        iv_qq_share = (LinearLayout) findViewById(R.id.iv_qq_share);
        icon_close = (ImageView) findViewById(R.id.icon_close);
        icon_close.setOnClickListener(this);
        iv_wxin_share.setOnClickListener(this);
        iv_qq_share.setOnClickListener(this);
        shareWaitDialog = new PublicToastDialog(mActivity, R.style.DialogStyle1, "");
        String[] value = SignListAdapter.doValue.split(",");
        if (SignListAdapter.doType == 801 || SignListAdapter.doType == 802 || SignListAdapter.doType == 803
                || SignListAdapter.doType == 8) {// ????????????

            if (value.length > 1) {
                if ("2".equals(value[1])) {
                    WxCircleIsLink = true;
                } else {
                    WxCircleIsLink = false;
                }
            } else {
                WxCircleIsLink = false;
            }
            try {
                shareNum = Integer.parseInt(value[0]);
            } catch (Exception e) {
                shareNum = SignListAdapter.doNum;
            }
            // }
        } else if (SignListAdapter.doType == 701 || SignListAdapter.doType == 702 || SignListAdapter.doType == 703
                || SignListAdapter.doType == 7) {// ????????????
            if (value.length > 2) {
                if ("2".equals(value[2])) {
                    WxCircleIsLink = true;
                } else {
                    WxCircleIsLink = false;

                }
            } else {
                WxCircleIsLink = false;
            }

            try {
                shareNum = Integer.parseInt(value[1]);
            } catch (Exception e) {
                shareNum = SignListAdapter.doNum;
            }
        }
        WxCircleIsLink = true;//???????????????+??????


        getShareTitleText();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_close: // ??????
//			isShow = false;
                this.dismiss();
                break;

            case R.id.iv_wxin_share: // ????????????????????????

                if (!wxIn) {
                    ToastUtil.showShortText(mActivity, "???????????????????????????~");
                } else {
                    shareWaitDialog.show();

                    isWXfriend = false;

                    if (ShareUtil.intentIsAvailable(mActivity, wXinShareIntent)) {

                        ShareUtil.addWXPlatform(mActivity);
                        CreatePic(ids);
                    } else {
                        shareWaitDialog.dismiss();
                        ToastUtil.showShortText(mActivity, "???????????????????????????~");
                    }
//				isShow = false;
                }

                dismiss();

                break;
            case R.id.iv_qq_share: // ?????????QQ---?????????????????????????????????

                if (!qqIn) {
                    ToastUtil.showShortText(mActivity, "??????????????????qq???~");

                } else {
                    isWXfriend = true;
                    shareWaitDialog.show();
//                    ShareUtil.addQQQZonePlatform(mActivity);

                    ShareUtil.addWXPlatform(mContext);

                    CreatePic(ids);
//				isShow = false;
                }

                dismiss();

                break;

            default:
                break;
        }

    }

    @Override
    public void dismiss() {
        isShow = false;
        super.dismiss();
    }

    private void takeTimeSign() {
        task = new TimerTask() {
            @Override
            public void run() {
                sign();
            }
        };
        timer.schedule(task, 6000);
    }

    private int num;

    private void sign() { //????????????????????????


//
//        final String SHARENUMKEY = SignListAdapter.signIndex + "share_num" + YCache.getCacheUser(mActivity).getUser_id();
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String now_time = sdf.format(date);
//        String nowTime = SharedPreferencesUtil.getStringData(mActivity,
//                "share_now_time" + YCache.getCacheUser(mActivity).getUser_id(), "");
//        num = Integer.valueOf(SharedPreferencesUtil.getStringData(mActivity, SHARENUMKEY, "0"));
//
//        if (!now_time.equals(nowTime)) {
//            num = 0;
//        }
//        num++;
//        SharedPreferencesUtil.saveStringData(mActivity, "share_now_time" + YCache.getCacheUser(mActivity).getUser_id(),
//                now_time);
//        SharedPreferencesUtil.saveStringData(mActivity, SHARENUMKEY, "" + num);
//        if (num < shareNum) {// ???????????????????????????
//            if (SignListAdapter.doNum == 1) {// ??????????????????????????? ??????????????? ???????????????????????? ????????????
//                ToastUtil.showShortText(mActivity, "?????????" + (shareNum - num) + "?????????????????????~");
//                return;
//            }
//        }
//        // ??????
//        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) mActivity, 0) {
//
//            @Override
//            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
//                    throws Exception {
//
//                if (now_type_id == 8) { // ???????????????????????????????????????????????????????????????
//
//                    return ComModel2.getSignIn(mActivity, true, true, SignListAdapter.signIndex, SignListAdapter.doClass);
//                } else {
//                    // ??????????????????????????????????????????
//
//                    return ComModel2.getSignIn(mActivity, false, true, SignListAdapter.signIndex, SignListAdapter.doClass);
//                }
//
//            }
//
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            ;
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (e == null && result != null) {
//                    // ???????????????????????????
//                    // chooseDialog();
//                    // SharedPreferencesUtil.saveBooleanData(context,
//                    // "isFirstFenXiang", true);
//                    if (now_type_id == 8) {
//                        String t_time = (String) result.get("t_time");
//
//                        SharedPreferencesUtil.saveStringData(context, Pref.TWOFOLDNESS, "2"); // ??????????????????
//                        SharedPreferencesUtil.saveStringData(context, Pref.END_DATE, t_time + ""); // ????????????????????????
//                        SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, true); // ?????????????????????????????????
//                        SharedPreferencesUtil.saveStringData(context, Pref.IS_OPEN, "1");// ???????????????
//                        // 0????????????
//                        // 1????????????
//
//                    }
//
//                    // new SignFinishDialog(mActivity, now_type_id,
//                    // now_type_id_value, next_type_id, next_type_id_value,
//                    // false, "", false).show();
//                    if (num < shareNum) {
//                        String ss = "";
//                        switch (SignListAdapter.jiangliID) {
//                            case 3:
//                                ss = "????????????";
//                                break;
//                            case 4:
//                                ss = "??????";
//                                break;
//                            case 5:
//                                ss = "???";
//                                break;
//                            case 11:
//                                ss = "?????????";
//                                break;
//                            default:
//                                break;
//                        }
//                        ToastUtil.showShortText(mActivity, "??????????????????" +
//                                // new
//                                // java.text.DecimalFormat("#0.00").format(Double.valueOf(SignListAdapter.jiangliValue)/shareNum)
//                                SignListAdapter.jiangliValue + ss + "?????????" + (shareNum - num) + "????????????~");
//                    } else if (num >= shareNum) {
//                        String ss = "";
//                        switch (SignListAdapter.jiangliID) {
//                            case 3:
//                                ss = "????????????";
//                                NewSignCommonDiaolg dialog3 = new NewSignCommonDiaolg(context, R.style.DialogQuheijiao2,
//                                        "share_sign_finish", new java.text.DecimalFormat("0.##").format(
//                                        Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum) + ss);
//                                dialog3.getWindow().setWindowAnimations(R.style.common_dialog_style);
//                                dialog3.show();
//                                SharedPreferencesUtil.saveStringData(mActivity, SHARENUMKEY, "0");
//                                SharedPreferencesUtil.removeData(mActivity, SHARENUMKEY);
//                                break;
//                            case 4:
//                                ss = "??????";
//                                NewSignCommonDiaolg dialog4 = new NewSignCommonDiaolg(context, R.style.DialogQuheijiao2,
//                                        "share_sign_finish", new java.text.DecimalFormat("0.##").format(
//                                        Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum) + ss);
//                                dialog4.getWindow().setWindowAnimations(R.style.common_dialog_style);
//
//                                dialog4.show();
//                                SharedPreferencesUtil.saveStringData(mActivity, SHARENUMKEY, "0");
//                                SharedPreferencesUtil.removeData(mActivity, SHARENUMKEY);
//                                break;
//                            case 5:
//                                ss = "???";
//                                NewSignCommonDiaolg dialog5 = new NewSignCommonDiaolg(context, R.style.DialogQuheijiao2,
//                                        "share_sign_finish", new java.text.DecimalFormat("0.##").format(
//                                        Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum) + ss);
//                                dialog5.getWindow().setWindowAnimations(R.style.common_dialog_style);
//
//                                dialog5.show();
//                                SharedPreferencesUtil.saveStringData(mActivity, SHARENUMKEY, "0");
//                                SharedPreferencesUtil.removeData(mActivity, SHARENUMKEY);
//                                break;
//                            case 11:// ??????
//                                ss = "?????????";
//                                NewSignCommonDiaolg dialo11 = new NewSignCommonDiaolg(context, R.style.DialogQuheijiao2,
//                                        "share_sign_finish", new java.text.DecimalFormat("0.##").format(
//                                        Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum) + ss);
//                                dialo11.getWindow().setWindowAnimations(R.style.common_dialog_style);
//
//                                dialo11.show();
//                                SharedPreferencesUtil.saveStringData(mActivity, SHARENUMKEY, "0");
//                                SharedPreferencesUtil.removeData(mActivity, SHARENUMKEY);
//                                break;
//                            case 8:// ????????????
//                                NewSignCommonDiaolg dialog8 = new NewSignCommonDiaolg(context, R.style.DialogQuheijiao2,
//                                        "share_sign_fanbei_finish");
//                                dialog8.getWindow().setWindowAnimations(R.style.common_dialog_style);
//
//                                dialog8.show();
//                                SharedPreferencesUtil.saveStringData(mActivity, SHARENUMKEY, "0");
//                                SharedPreferencesUtil.removeData(mActivity, SHARENUMKEY);
//                                break;
//                            case 9:// ????????????
//                                NewSignCommonDiaolg dialog9 = new NewSignCommonDiaolg(context, R.style.DialogQuheijiao2,
//                                        "share_sign_jinbi_finish");
//                                dialog9.getWindow().setWindowAnimations(R.style.common_dialog_style);
//
//                                dialog9.show();
//                                SharedPreferencesUtil.saveStringData(mActivity, SHARENUMKEY, "0");
//                                SharedPreferencesUtil.removeData(mActivity, SHARENUMKEY);
//                                break;
//                            case 10:// ????????????
//                                NewSignCommonDiaolg dialog10 = new NewSignCommonDiaolg(context, R.style.DialogQuheijiao2,
//                                        "share_sign_jinquan_finish");
//                                dialog10.getWindow().setWindowAnimations(R.style.common_dialog_style);
//
//                                dialog10.show();
//                                SharedPreferencesUtil.saveStringData(mActivity, SHARENUMKEY, "0");
//                                SharedPreferencesUtil.removeData(mActivity, SHARENUMKEY);
//                                break;
//
//                            default:
//                                break;
//                        }
//                    }
//
//                } else {
//                    // ToastUtil.showLongText(mActivity, "????????????");
//                }
//
//            }
//
//        }.execute();
    }

    private void CreatePic(int id) {

        shareCommon(id);

    }

    private String link9 = "";

    private void shareCommon(final int id) {

        // ?????????????????????????????????????????????????????????

//        if (now_type_id == 5) {// ???????????????????????????????????????

//            if (SignListAdapter.doType == 801 || SignListAdapter.doType == 802 || SignListAdapter.doType == 803
//                    || SignListAdapter.doType == 8) {// ?????????????????????
//
//                getCollocationCode();
//
//            } else {
        new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mActivity, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                // TODO Auto-generated method stub
                return ComModel2.getShareShopLinkHobbyTX(context, "true");
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result,
                                         Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result.get("status").equals("1")) {

                        String[] picList = result.get("four_pic").split(",");
                        String shop_code = result.get("shop_code");
                        if (shop_code.equals("null") || shop_code == null || shop_code.equals("")) {
                            if (null != shareWaitDialog) {
                                shareWaitDialog.dismiss();
                            }
                            ToastUtil.showShortText(context, "?????????????????????");
                            return;
                        }


                        String supp_label = result.get("supp_label");
                        String shop_se_price = result.get("shop_se_price");
                        shop_name = result.get("shop_name");

                        String four_pic = result.get("four_pic").toString(); //???????????????????????????????????????--???four_pic????????????

                        shareMIniAPPimgPic = YUrl.imgurl + shop_code.substring(1, 4) + "/" + shop_code + "/" + four_pic.split(",")[2] + "!280";


                        if ("".equals(supp_label)) {
                            str1 = newShareTitle.replaceFirst("\\$\\{replace\\}", "??????");
                        } else {
                            str1 = newShareTitle.replaceFirst("\\$\\{replace\\}", supp_label);
                        }
//                                str2 = str1.replaceFirst("\\$\\{replace\\}", "" + new DecimalFormat("#0.0").format(Double.parseDouble(shop_se_price) * 0.5));


                        str2 = str1.replaceFirst("\\$\\{replace\\}", "" + new DecimalFormat("#0.0").format(Math.round(Double.parseDouble(shop_se_price) * 0.5 * 10) * 0.1d));


                        link1 = (String) result.get("link");
                        TongjiShareCount.tongjifenxiangCount();
                        TongjiShareCount.tongjifenxiangwho(shop_code);

                        createSharePic((String) result.get("link"), picList.length > 2 ? picList[2] : "",
                                (String) result.get("shop_se_price"), (String) result.get("shop_code"), null,
                                id);

                    } else if (result.get("status").equals("1050")) {// ??????
                        if (null != shareWaitDialog) {
                            shareWaitDialog.dismiss();
                        }
                        Intent intent = new Intent(context, NoShareActivity.class);
                        intent.putExtra("isNomal", true);
                        context.startActivity(intent); // ?????????????????????
                    } else {
                        if (null != shareWaitDialog) {
                            shareWaitDialog.dismiss();
                        }
                    }
                } else {
                    if (null != shareWaitDialog) {
                        shareWaitDialog.dismiss();
                    }
                }
            }

        }.execute();
    }
//        }


//
//        else {
//
//            if (SignListAdapter.doType == 801 || SignListAdapter.doType == 802 || SignListAdapter.doType == 803
//                    || SignListAdapter.doType == 8) {// ?????????????????????
//
//                getCollocationCode();
//
//            } else {
//                // // ???????????????9??????
//
//
//                new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mActivity, R.string.wait) {
//
//                    @Override
//                    protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
//                            throws Exception {
//                        // TODO Auto-generated method stub
//                        return ComModel2.getShareShopLinkHobby(context, "true");
//                    }
//
//                    @Override
//                    protected boolean isHandleException() {
//                        return true;
//                    }
//
//                    @Override
//                    protected void onPostExecute(FragmentActivity context, HashMap<String, String> result,
//                                                 Exception e) {
//                        // TODO Auto-generated method stub
//                        super.onPostExecute(context, result, e);
//                        if (null == e) {
//                            if (result.get("status").equals("1")) {
//
//                                String[] picList = result.get("shop_pic").split(",");
//                                link9 = result.get("link");
//                                String code = result.get("shop_code");
//
//                                String shop_code = result.get("shop_code");
//                                if (shop_code.equals("null") || shop_code == null || shop_code.equals("")) {
//                                    if (null != shareWaitDialog) {
//                                        shareWaitDialog.dismiss();
//                                    }
//                                    ToastUtil.showShortText(context, "?????????????????????");
//                                    return;
//                                }
//
//                                //
//                                TongjiShareCount.tongjifenxiangCount();
//                                TongjiShareCount.tongjifenxiangwho(shop_code);
//
//
//                                String supp_label = result.get("supp_label");
//                                String shop_se_price = result.get("shop_se_price");
//                                shop_name = result.get("shop_name");
//
//
//                                if ("".equals(supp_label)) {
//                                    str1 = newShareTitle.replaceFirst("\\$\\{replace\\}", "??????");
//                                } else {
//                                    str1 = newShareTitle.replaceFirst("\\$\\{replace\\}", supp_label);
//                                }
//                                str2 = str1.replaceFirst("\\$\\{replace\\}", "" + new DecimalFormat("#0.0").format(Double.parseDouble(shop_se_price) * 0.9));
//
//
//                                // download(null, picList, code, result, shop,
//                                // link,four_pic);
//                                // download9(null, picList, code, result, link9,
//                                // result.get("four_pic"));
//                                getNineBmBg(null, picList, code, result, link9, result.get("four_pic"));
//
//                                //
//                                // createSharePic((String) result.get("link"),
//                                // picList.length > 2 ? picList[2] : "",
//                                // (String) result.get("shop_se_price"),
//                                // (String)
//                                // result.get("shop_code"), null, id);
//
//                            } else if (result.get("status").equals("1050")) {// ??????
//                                if (null != shareWaitDialog) {
//                                    shareWaitDialog.dismiss();
//                                }
//                                Intent intent = new Intent(context, NoShareActivity.class);
//                                intent.putExtra("isNomal", true);
//                                context.startActivity(intent); // ?????????????????????
//                            } else {
//                                if (null != shareWaitDialog) {
//                                    shareWaitDialog.dismiss();
//                                }
//                            }
//
//                            /**
//                             * if (result.get("status").equals("1")) {
//                             * MyLogYiFu.e("pic", result.get("shop_pic"));
//                             * String[] picList =
//                             * result.get("shop_pic").split(","); String link =
//                             * result.get("link"); download(null, picList,
//                             * sc.getShop_code(), result, sc, link); } else if
//                             * (result.get("status").equals("1050")) {// ??????
//                             * Intent intent = new Intent(context,
//                             * NoShareActivity.class);
//                             * intent.putExtra("isNomal", true);
//                             * context.startActivity(intent); // ????????????????????? }
//                             *
//                             */
//                        } else {
//                            if (null != shareWaitDialog) {
//                                shareWaitDialog.dismiss();
//                            }
//                        }
//                    }
//
//                }.execute();
//                //
//            }
//        }
//    }

    private Bitmap bmBg1;

    private void createSharePic(final String link, final String picPath, final String price, final String shop_code,
                                final View v, final int id) {
        new SAsyncTask<Void, Void, Void>((FragmentActivity) mActivity, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            protected Void doInBackground(FragmentActivity context, Void... params) throws Exception {
                // TODO Auto-generated method stub
                Bitmap bmQr = QRCreateUtil.createQrImage(link, 160, 160);// ?????????????????????
                String pic = shop_code.substring(1, 4) + "/" + shop_code + "/" + picPath;
                bmBg1 = downloadPic(pic);

                bmNew = QRCreateUtil.drawNewBitmap1(context, bmBg1, bmQr, price, "1");
                LogYiFu.e("WD", bmNew.getWidth() + "");
                LogYiFu.e("HG", bmNew.getHeight() + "");
                if (WxCircleIsLink && !isWXfriend) {
                    QRCreateUtil.saveBitmap(bmBg1, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");
                } else {
                    QRCreateUtil.saveBitmap(bmNew, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// ?????????????????????
                }
                return super.doInBackground(context, params);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, Void result, Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result, e);
                if (null == e) {
                    file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");
                    // share(file); ????????????????????????????????????
                    if (null != shareWaitDialog) {
                        shareWaitDialog.dismiss();
                    }
                    share(file, shop_code, link, price);

                } else {
                    if (null != shareWaitDialog) {
                        shareWaitDialog.dismiss();
                    }
                }
            }

        }.execute();
    }

    private Bitmap downloadPic(String picPath) {
        try {
            URL url = new URL(YUrl.imgurl + picPath);
            // ????????????
            URLConnection con = url.openConnection();
            // ?????????????????????
            int contentLength = con.getContentLength();
            // System.out.println("?????? :" + contentLength);
            // ?????????
            InputStream is = con.getInputStream();
            // 1K???????????????
            byte[] bs = new byte[8192];
            // ????????????????????????
            int len;
            BitmapDrawable bmpDraw = new BitmapDrawable(is);

            // ???????????????????????????
            is.close();
            return bmpDraw.getBitmap();
        } catch (Exception e) {
            LogYiFu.e("TAG", "????????????");

            e.printStackTrace();
            return null;
        }

    }


    String str4 = ""; //???????????? ?????????????????????title

    private String newShareTitle = "????????????????????????????????????39????????????????????????";
    private String newShareText = "";


    private void share(final File file, final String shop_code, final String link, final String shop_se_price) {


        new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mActivity, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                return ComModelZ.geType2SuppLabe(mActivity, shop_code);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (null != result) {


                        String type2 = result.get("type2");


                        if ("".equals(type2)) {
                            type2 = "??????";
                        }

                        String supp_label = result.get("supp_label_id");//??????

                        String str1;
                        String str2;
                        String str3;
                        if ("".equals(supp_label)) {
                            str1 = newShareTitle.replaceFirst("\\$\\{replace\\}", "??????");
                        } else {
                            str1 = newShareTitle.replaceFirst("\\$\\{replace\\}", supp_label);
                        }
                        str2 = str1.replaceFirst("\\$\\{replace\\}", type2);
                        str3 = str2.replaceFirst("\\$\\{replace\\}", "" + new DecimalFormat("#0.0").format(Math.round(Double.parseDouble(shop_se_price) * 0.5 * 10) * 0.1d));
                        str4 = str3.replaceFirst("\\$\\{replace\\}", "" + new DecimalFormat("#0.0").format(Math.round(Double.parseDouble(shop_se_price) * 0.5 * 10) * 0.1d));


                        if (isWXfriend) {//?????????????????????
//                            WeiXinShareContent wei = new WeiXinShareContent();
                            if (teshuShare) {//???????????????
                                if (shareTo.equals("spellGroup")) {//?????????????????????
//                                    wei.setShareContent("" + shareContent);
//                                    wei.setTitle("" + shareTitle);
//                                    wei.setTargetUrl(teshuLink);
//                                    wei.setShareMedia(teshuUmImage);
//                                    mController.setShareMedia(wei);
//                                    performShareMatch(SHARE_MEDIA.WEIXIN, weixinShareIntent);
                                } else {
                                    if (shareTo.equals("indiana")) { //??????
                                        String wxMiniPath = "/pages/shouye/detail/centsIndianaDetail/centsDetail?shop_code=" + DuobaoShop_code +
                                                "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();
                                        //????????????????????????????????????
                                        WXminiAPPShareUtil.shareToWXminiAPP(mContext, duoBaoSharePic, shareTitle, wxMiniPath, false);
                                    } else if (shareTo.equals("h5money")) {//??????H5?????????
                                        String wxMiniPath = "/pages/shouye/shouye?isShareFlag=true&goto=sign&user_id=" + YCache.getCacheUser(mContext);
                                        WXminiAPPShareUtil.shareToWXminiAPP(mContext, H5moneyPic, shareTitle, wxMiniPath, true);

                                    }

                                }


                            } else { //??????????????????

                                str4 = "??????" +
                                        GuideActivity.oneShopPrice + "?????????" +
                                        supp_label + "??????" + type2 + "???????????????" + shop_se_price + "??????";




                                String wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + shop_code +
                                        "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();
                                //????????????????????????????????????
                                WXminiAPPShareUtil.shareToWXminiAPP(mContext, shareMIniAPPimgPic, str4, wxMiniPathdUO, false);

                            }


//            yunYunTongJi(shop_code, 104, 2);
//            onceShare2(qqShareIntent, "qq??????", file);


                        } else { //??????????????????
                            yunYunTongJi(shop_code, 108, 2);
                            if (teshuShare) {
                                ShareUtil.configPlatforms(mActivity);
//                                UMImage umImage = new UMImage(mActivity, file);
//                                ShareUtil.shareShop(mActivity, umImage);


                                ShareUtil.setShareNewTitleContent(mContext, teshuUmImage, shareContent, teshuLink, shareTitle);

                                mController.postShare(mActivity, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {

                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                        // String showText = platform.toString();
                                        if (eCode == StatusCode.ST_CODE_SUCCESSED) {// ????????????
                                            // MobclickAgent.onEvent(mActivity,
                                            // "sign_sharesuccess");
                                            sign();

                                        } else {

                                        }
                                        SocializeConfig.getSocializeConfig().cleanListeners();// ???????????????????????????
                                        // ?????????????????????????????????
                                    }
                                });
                            } else {
                                if (WxCircleIsLink) {// true ????????????
                                    UMImage umImage = new UMImage(mActivity, file);


//                                int randNum;//0???1??????1??? 0??????1???
//                                Random rand = new Random();
//                                randNum = rand.nextInt(2);
//
//                                if (randNum == 0) {
                                    ShareUtil.setShareNewTitleContent(mContext, umImage, shop_name, link, str4);
//                                } else {
//                                    ShareUtil.setShareContent(mActivity, umImage, "????????????????????????????????????????????????????????????????????????~", link + "&share=true");
//                                }


//                ShareUtil.setShareContent(mActivity, umImage, "????????????????????????????????????????????????????????????????????????~", link + "&share=true");


                                    performShareMatch(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
                                } else {
                                    ShareUtil.configPlatforms(mActivity);
                                    UMImage umImage = new UMImage(mActivity, file);
                                    ShareUtil.shareShop(mActivity, umImage);

                                    mController.postShare(mActivity, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {

                                        @Override
                                        public void onStart() {

                                        }

                                        @Override
                                        public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                            // String showText = platform.toString();
                                            if (eCode == StatusCode.ST_CODE_SUCCESSED) {// ????????????
                                                // MobclickAgent.onEvent(mActivity,
                                                // "sign_sharesuccess");
                                                sign();

                                            } else {

                                            }
                                            SocializeConfig.getSocializeConfig().cleanListeners();// ???????????????????????????
                                            // ?????????????????????????????????
                                        }
                                    });
                                }

                            }


                        }


                    }
                }


            }

        }.execute(shop_code);


    }

    private String[] picListNine;
    private String shop_codeNine;
    private HashMap<String, String> mapInfosNine;
    private String linkNine;
    private String four_picNine;

    /**
     * ???????????????????????????????????????
     */
    public void getNineBmBg(View v, final String[] picList, final String shop_code,
                            final HashMap<String, String> mapInfos, final String link, final String four_pic) {
        new SAsyncTask<Void, Void, String>((FragmentActivity) mActivity, R.string.wait) {
            @Override
            protected boolean isHandleException() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            protected String doInBackground(FragmentActivity context, Void... params) throws Exception {
                // TODO Auto-generated method stub
                return ComModel2.getShareBg(context);

            }

            protected void onPostExecute(FragmentActivity context, String result, Exception e) {
                super.onPostExecute(context, result, e);
                // Bitmap bmNineBg = downloadPic(result);
                // download9(null, picList, shop_code, mapInfos, link,
                // four_pic,bmNineBg);
                picListNine = picList;
                shop_codeNine = shop_code;
                mapInfosNine = mapInfos;
                linkNine = link;
                four_picNine = four_pic;
                getPicture(result);

            }
        }.execute();
    }

    private byte[] picByte;
    private Bitmap nineBitmap;

    public void getPicture(final String picPath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(picPath)) {
                    Message message = new Message();
                    message.what = 99;
                    handle.sendMessage(message);
                } else {

                    try {
                        URL url = new URL(YUrl.imgurl + picPath);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setReadTimeout(10000);

                        if (conn.getResponseCode() == 200) {
                            InputStream fis = conn.getInputStream();
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            byte[] bytes = new byte[1024];
                            int length = -1;
                            while ((length = fis.read(bytes)) != -1) {
                                bos.write(bytes, 0, length);
                            }
                            picByte = bos.toByteArray();
                            bos.close();
                            fis.close();

                            Message message = new Message();
                            message.what = 99;
                            handle.sendMessage(message);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 99) {
                if (picByte != null) {
                    nineBitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
                    download9(null, picListNine, shop_codeNine, mapInfosNine, linkNine, four_picNine, nineBitmap);
                } else {
                    download9(null, picListNine, shop_codeNine, mapInfosNine, linkNine, four_picNine, null);
                }
            }
        }
    };

    private Bitmap bmNew;
    private File file;

    private Intent qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());

    private void download9(View v, final String[] picList, final String shop_code,
                           final HashMap<String, String> mapInfos, final String link, final String four_pic, final Bitmap bmNineBg) {
        final List<String> pics = new ArrayList<String>();

        new SAsyncTask<Void, Void, Void>((FragmentActivity) mActivity, v, R.string.wait) {

            @Override
            protected Void doInBackground(Void... params) {
                // TODO Auto-generated method stub
                File fileDirec = new File(YConstance.savePicPath);
                if (!fileDirec.exists()) {
                    fileDirec.mkdir();
                }
                File[] listFiles = new File(YConstance.savePicPath).listFiles();
                if (listFiles.length != 0) {
                    LogYiFu.e("TAG", "??????????????? ?????????????????????");
                    for (File file : listFiles) {
                        file.delete();
                    }
                }
                // LogYiFu.i("TAG", "piclist=" + picList.length);
                // List<String> pics = new ArrayList<String>();
                for (int j = 0; j < picList.length; j++) {
                    if (!picList[j].contains("reveal_") && !picList[j].contains("detail_")
                            && !picList[j].contains("real_")) {
                        pics.add(picList[j]);
                    }
                }
                int j = pics.size() + 1;
                if (pics.size() > 8) {
                    j = 9;
                }

                // deletePIC(YConstance.savePicPath ,false);
                // deletePIC(YConstance.saveUploadPicPath ,false);
                int nP = j > 5 ? 4 : j - 1;
                for (int i = 0; i < j; i++) {
                    if (i == nP) {
                        /*
                         * ComModel2.saveQRCode(PaymentSuccessActivity.this,
						 * shop_code);
						 */
                        // if (isMeal ||
                        // "SignShopDetail".equals(signShopDetail)) {
                        // Bitmap bm = QRCreateUtil.createZeroImage(link, 500,
                        // 700, mapInfos.get("shop_se_price"),
                        // ShopDetailsActivity.this);// ?????????????????????
                        // QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
                        // MD5Tools.md5(String.valueOf(9)) + ".jpg");// ?????????????????????
                        // } else {
                        // Bitmap bm =
                        // QRCreateUtil.createImage(mapInfos.get("QrLink"), 500,
                        // 700,
                        // mapInfos.get("shop_se_price"), mActivity);// ?????????????????????
                        // ???????????????????????????
                        // Bitmap bmQr =
                        // QRCreateUtil.createQrImage(mapInfos.get("QrLink"),
                        // 250, 250);
                        // Bitmap bm = QRCreateUtil.drawNewBitmapNine(mActivity,
                        // bmQr, mapInfos.get("shop_se_price"),false);
                        Bitmap bmQr = QRCreateUtil.createQrImage(mapInfos.get("QrLink"), 190, 190);
                        Bitmap bm = QRCreateUtil.drawNewBitmapNine2(mActivity, bmQr, bmNineBg);

                        QRCreateUtil.saveBitmap(bm, YConstance.savePicPath, MD5Tools.md5(String.valueOf(i)) + ".jpg");// ?????????????????????
                        // }
                        // downloadPic(mapInfos.get("qr_pic"), 9);
                        continue;
                    }
                    int m = i > 4 ? i - 1 : i;
                    downloadPic(shop_code.substring(1, 4) + "/" + shop_code + "/" + pics.get(m) + "!450", i);
                    bmBg = downloadPic(
                            shop_code.substring(1, 4) + "/" + shop_code + "/" + four_pic.split(",")[2] + "!450");
                }
                return super.doInBackground(params);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, Void result) {
                // TODO Auto-generated method stub
                // showShareDialog();
                // if (instance == null) {
                // return;
                // }
                if (null != context && null != context.getWindow().getDecorView() && !context.isFinishing()) {
                    shareWaitDialog.dismiss();
                    // MyLogYiFu.e("TAG", "????????????=" + shop.getShop_name() +
                    // ",????????????="
                    // + result);
                    // ShareUtil.configPlatforms(context);

                    //

                    // WeiXinShareContent wei = new WeiXinShareContent();
                    // wei.setTitle("??????????????????????????????????????????");
                    // wei.setShareMedia(umImage);
                    // wei.setShareContent("????????????????????????????????????????????????????????????????????????~~");
                    // wei.setTargetUrl(link);
                    // mController.setShareMedia(wei);
                    //
                    if (isWXfriend == true) {
//                        yunYunTongJi(shop_code, 104, 2);


//                        weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());
//                        UMImage umImage = new UMImage(context, bmBg);
//                        WeiXinShareContent wei = new WeiXinShareContent();
//                        wei.setShareMedia(umImage);
//                        ShareUtil.setShareContent(context, umImage, "????????????????????????????????????????????????????????????????????????~", link);
//                        mController.setShareMedia(wei);
//                        performShareMatch(SHARE_MEDIA.WEIXIN, weixinShareIntent);

                        UMImage umImage = new UMImage(context, bmBg);


//                        WeiXinShareContent wei = new WeiXinShareContent();


//                        wei.setShareContent("????????????????????????????????????????????????????????????????????????~");
//                        wei.setTitle(StringUtils.getShareContent(YCache.getCacheUser(mActivity).getNickname()));


//                        wei.setShareContent(shop_name);
//                        wei.setTitle(str2);
//
//                        wei.setTargetUrl(link + "&share=true");
//                        wei.setShareMedia(umImage);
//                        mController.setShareMedia(wei);
//                        performShareMatch(SHARE_MEDIA.WEIXIN, weixinShareIntent);


//                        onceShare2(qqShareIntent, "qq??????", null);
                    } else {
//                        yunYunTongJi(shop_code, 108, 2);
                        if (WxCircleIsLink && ShareUtil.intentIsAvailable(mActivity, wXinShareIntent)) {
                            UMImage umImage = new UMImage(context, bmBg);


//                            int randNum;//0???1??????1??? 0??????1???
//                            Random rand = new Random();
//                            randNum = rand.nextInt(2);

//                            if (randNum == 0) {
                            ShareUtil.setShareNewTitleContent(mContext, umImage, shop_name, link, str2);
//                            } else {
//                                ShareUtil.setShareContent(context, umImage, "????????????????????????????????????????????????????????????????????????~", link + "&share=true");
//                            }


                            performShareMatch(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
                        } else {
                            onceShare3(wXinShareIntent, "??????");
                        }
                    }

                    // ShareUtil.share(ShopDetailsActivity.this);
                    // myPopupwindow.setLink(link);
                    // myPopupwindow.setUmImage(umImage);
                    // myPopupwindow = new MyPopupwindow(context,
                    // shop.getKickback(), umImage, link);
                    // if (ShopDetailsActivity.instance != null) {
                    // myPopupwindow.showAtLocation(context.getWindow()
                    // .getDecorView(), Gravity.BOTTOM, 0, 0);
                    // }
                    // shareTo();
                    super.onPostExecute(context, result);
                }

            }

        }.execute();

    }

    private Intent weixinShareIntent;


    private void onceShare2(Intent intent, String perform, File file) {
        if (ShareUtil.intentIsAvailable(mActivity, intent)) {

            if (now_type_id == 5) {
                performShare(SHARE_MEDIA.QZONE, qqShareIntent);

            } else {
                mActivity.startActivity(intent);
                takeTimeSign();// ????????? ???????????? ????????????
            }

        } else {
            performShare(SHARE_MEDIA.QZONE, qqShareIntent);
        }
    }

    private String link1 = "";

    public void performShare(SHARE_MEDIA platform, final Intent intent) {
        UMImage umImage;

        if (now_type_id == 5) { // ????????????????????????
            umImage = new UMImage(mActivity, bmBg1);
            ShareUtil.setShareContent(mActivity, umImage, "????????????????????????????????????????????????????????????????????????~", link1);
        } else {
            umImage = new UMImage(mActivity, bmBg);// ??????
            ShareUtil.setShareContent(mActivity, umImage, "????????????????????????????????????????????????????????????????????????~", link9);
        }

        mController.postShare(mActivity, platform, new SnsPostListener() {

            @Override
            public void onStart() {
                LogYiFu.e("showText", "asdsafdsf");
                // chooseDialog();
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                String showText = platform.toString();
                LogYiFu.e("showText", showText);

                if (eCode == StatusCode.ST_CODE_SUCCESSED) {

                    sign();
                    // ToastUtil.showShortText(mActivity, "QQ??????????????????");

                } else {

                }
                SocializeConfig.getSocializeConfig().cleanListeners();// ???????????????????????????
                // ?????????????????????????????????

            }
        });
    }

    private Bitmap bmBg;

    private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);

    private void downloadPic(String picPath, int i) {
        try {
            URL url = new URL(YUrl.imgurl + picPath);
            // ????????????
            URLConnection con = url.openConnection();
            // ?????????????????????
            int contentLength = con.getContentLength();
            // ?????????
            InputStream is = con.getInputStream();
            // 1K???????????????
            byte[] bs = new byte[8192];
            // ????????????????????????
            int len;
            // ?????????????????? /sdcard/yssj/
            File file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(i)) + ".jpg");
            if (file.exists()) {
                file.delete();
            }
            LogYiFu.e("TAG", "??????????????????????????????????????????");
            OutputStream os = new FileOutputStream(file);
            // ????????????
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            LogYiFu.i("TAG", "?????????????????????file=" + file.toString());
            // ???????????????????????????
            os.close();
            is.close();
        } catch (Exception e) {
            LogYiFu.e("TAG", "????????????");
            e.printStackTrace();
        }
    }

    private void onceShare3(Intent intent, String perform) {
        if (ShareUtil.intentIsAvailable(mActivity, intent)) {
            mActivity.startActivity(intent);
            takeTimeSign();// ????????? ???????????? ????????????
        }
    }

    private void yunYunTongJi(final String shop_code, final int type, final int tab_type) {
        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mActivity, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel2.getOperator(context, shop_code, type, tab_type);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result, e);

            }

        }.execute();
    }

    ///////////////////////////////////////////// ????????????????????????/////////////////////////////////////////////////

    /**
     * ???????????????????????????????????????
     */
    private void getCollocationCode() {

        shareWaitDialog.show();
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) mActivity, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getCollectionCode(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result, e);
                if (e != null || result == null) {
                    // CenterToast.centerToast(mActivity, "???????????????");
                    shareWaitDialog.dismiss();
                    return;
                } else {
                    collocation_code = (String) result.get("code");
                    TongjiShareCount.tongjifenxiangCount();
                    TongjiShareCount.tongjifenxiangwho(collocation_code);
                    String link = "";
                    if (!((String) result.get("link")).startsWith("http")) {
                        link = "http://" + result.get("link");
                    } else {
                        link = (String) result.get("link");
                    }

                    queryMatchDetails(link);
                }
            }

        }.execute();
    }

    private String mCollocationName;// ????????????????????????
    private List<CollocationShop> mCollocationShopsList;// ????????? ?????????????????????

    /**
     * ????????? ?????????????????? ???????????? ????????????????????????
     */
    private void queryMatchDetails(final String link) {
        new SAsyncTask<String, Void, MatchShop>((FragmentActivity) mActivity, R.string.wait) {

            @Override
            protected MatchShop doInBackground(FragmentActivity context, String... params) throws Exception {
                return ComModel2.getMatchDetails(context, collocation_code);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, MatchShop result, Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result, e);
                if (e != null || result == null) {
                    // CenterToast.centerToast(mActivity, "???????????????");
                    shareWaitDialog.dismiss();
                    return;
                }
                mCollocationName = result.getCollocation_name();
                mCollocationShopsList = result.getCollocation_shop();
                // createPicMatch(collocation_code);
                createSharePicMatch(link, result.getCollocation_pic(), null);
            }

        }.execute();
    }

    // private void createPicMatch(final String code){
    // new SAsyncTask<String, Void, HashMap<String, String>>(
    // (FragmentActivity) mActivity, R.string.wait) {
    //
    // @Override
    // protected HashMap<String, String> doInBackground(
    // FragmentActivity context, String... params)
    // throws Exception {
    // return ComModel2.getMatchShopLinkOrPic(code, mActivity, true);
    // }
    //
    // @Override
    // protected boolean isHandleException() {
    // return true;
    // }
    //
    // @Override
    // protected void onPostExecute(FragmentActivity context,
    // HashMap<String, String> result, Exception e) {
    // super.onPostExecute(context, result, e);
    // if (null == e) {
    //
    // if (result.get("status").equals("1")) {
    // String link = "";
    // if(!result.get("link").startsWith("http")){
    // link ="http://"+result.get("link");
    // }else{
    // link=result.get("link");
    // }
    // createSharePicMatch(link,
    // result.get("pic"),null);
    //
    // } else if (result.get("status").equals("1050")) {// ??????
    // Intent intent = new Intent(context,
    // NoShareActivity.class);
    // intent.putExtra("isNomal", true);
    // context.startActivity(intent); // ?????????????????????
    // }
    // }
    // }
    //
    // }.execute();
    // }

    private void createSharePicMatch(final String link, final String picPath, final View v) {
        new SAsyncTask<Void, Void, Void>((FragmentActivity) mActivity, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            protected Void doInBackground(FragmentActivity context, Void... params) throws Exception {
                // TODO Auto-generated method stub

                bmBg = downloadPic(picPath);
                Bitmap bmQr = QRCreateUtil.createQrImage(link, 160, 160);
                bmNew = QRCreateUtil.drawNewBitmapMatch2(context, bmBg, bmQr, mCollocationName, mCollocationShopsList);
                LogYiFu.e("WD", bmNew.getWidth() + "");
                LogYiFu.e("HG", bmNew.getHeight() + "");

                if (isWXfriend || WxCircleIsLink) {
                    QRCreateUtil.saveBitmap(bmBg, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// ????????????
                    // ?????????????????????????????????
                } else {
                    QRCreateUtil.saveBitmap(bmNew, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// ?????????????????????
                }

                return super.doInBackground(context, params);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, Void result, Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result, e);
                if (null == e) {
                    shareWaitDialog.dismiss();
                    file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");
                    // share(file); ????????????????????????????????????

                    shareMatch(file, link);

                } else {
                    if (null != shareWaitDialog) {
                        shareWaitDialog.dismiss();
                    }
                }
            }

        }.execute();
    }

    private void shareMatch(File file, String link) {
        if (file == null) {
            ToastUtil.showShortText(mActivity, "??????????????????????????????~~");
            return;
        }
        UMImage umImage = new UMImage(mActivity, bmBg);
        if (isWXfriend) {//?????????????????????

//            weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());
//
//            WeiXinShareContent wei = new WeiXinShareContent();
//            wei.setShareContent("????????????????????????????????????????????????????????????????????????~");
//            wei.setTitle(StringUtils.getShareContent(YCache.getCacheUser(mActivity).getNickname()));
//            wei.setTargetUrl(link + "&share=true");
//            wei.setShareMedia(umImage);
//            mController.setShareMedia(wei);
//            performShareMatch(SHARE_MEDIA.WEIXIN, weixinShareIntent);


//            ShareUtil.setShareContent(mActivity, umImage, "????????????????????????????????????????????????????????????????????????~", link);
//            performShareMatch(SHARE_MEDIA.QZONE, qqShareIntent);
            // }

        } else {
            if (WxCircleIsLink) {
                ShareUtil.setShareContent(mActivity, umImage, "????????????????????????????????????????????????????????????????????????~", link + "&share=true");
                performShareMatch(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
            } else {
                ShareUtil.configPlatforms(mActivity);
                // UMImage umImage = new UMImage(mActivity, file);
                ShareUtil.shareShop(mActivity, umImage);
                mController.postShare(mActivity, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                        // String showText = platform.toString();
                        if (eCode == StatusCode.ST_CODE_SUCCESSED) {// ????????????
                            // MobclickAgent.onEvent(mActivity,
                            // "sign_sharesuccess");
                            sign();

                        } else {

                        }
                        SocializeConfig.getSocializeConfig().cleanListeners();// ???????????????????????????
                        // ?????????????????????????????????
                    }
                });
            }

        }

    }

    public void performShareMatch(SHARE_MEDIA platform, final Intent intent) {
        // UMImage umImage = new UMImage(mActivity, bmNew);
        // ShareUtil.setShareContent(mActivity, umImage,
        // "????????????????????????????????????????????????????????????????????????~~", link9);

        mController.postShare(mActivity, platform, new SnsPostListener() {

            @Override
            public void onStart() {
                // chooseDialog();
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                String showText = platform.toString();
                LogYiFu.e("showText", showText);

                if (eCode == StatusCode.ST_CODE_SUCCESSED) {
                    sign();
                } else {

                }
                SocializeConfig.getSocializeConfig().cleanListeners();// ???????????????????????????
                // ?????????????????????????????????

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


    public void getShareTitleText() {
        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModelZ.getShareTitleContent();
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null) {
                    newShareTitle = result.get("title");
                    newShareText = result.get("text");


                }
            }

        }.execute();
    }

    private void initShareContent() {


        //??????????????????
        HashMap<String, Object> map = GuideActivity.textMap.get(shareTo);
        if (null != map && map.size() > 0) {//?????????
            //??????????????????
            shareTitle = map.get("title") + "";
            shareContent = map.get("text") + "";


            H5moneyPic = YUrl.imgurl + map.get("icon");


            teshuUmImage = new UMImage(mContext, YUrl.imgurl + map.get("icon"));
            //??????????????????

            if (shareTo.equals("spellGroup")) {//??????
                teshuLink = YUrl.YSS_URL_ANDROID_H5 + "/view/activity/pack.html?realm=" + YCache.getCacheUser(mContext).getUser_id();
            } else if (shareTo.equals("indiana")) {//??????
                //????????????
                getDuoBaoShareLink();

            } else if (shareTo.equals("h5money")) {//H5?????????
                teshuLink = YUrl.YSS_URL_ANDROID_H5 + "/view/activity/mission.html?realm=" + YCache.getCacheUser(mContext).getUser_id();
            }

        } else {//????????????????????????????????????
            shareTo = "link";
        }


    }

    private void getDuoBaoShareLink() {

        new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                return ComModel2.getsuijishopLink(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    teshuLink = result.get("link");

                    DuobaoShop_code = result.get("shop_code");
                    String def_pic = result.get("def_pic").toString(); //???????????????????????????????????????
                    duoBaoSharePic = YUrl.imgurl + DuobaoShop_code.substring(1, 4) + "/" + DuobaoShop_code + "/" + def_pic + "!280";

                }
            }

        }.execute();

    }
}
