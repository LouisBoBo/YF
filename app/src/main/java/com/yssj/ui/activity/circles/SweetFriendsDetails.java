package com.yssj.ui.activity.circles;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import com.bumptech.glide.Glide;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.DeviceConfig;
import com.yssj.Constants;
import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.CustImageGalleryIntimate;
import com.yssj.custom.view.FlowLayout;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.data.DBService;
import com.yssj.data.YDBHelper;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.model.ModQingfeng;
import com.yssj.pubu.PLA_AbsListView;
import com.yssj.pubu.PLA_AbsListView.OnScrollListener;
import com.yssj.pubu.XListView;
import com.yssj.pubu.XListView.IXListViewListener;
import com.yssj.spl.PuBuAdapter;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.classfication.ManufactureActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.main.FilterResultActivity;
import com.yssj.ui.activity.main.ForceLookActivity;
import com.yssj.ui.activity.main.WordSearchResultActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.NewSignCommonDiaolg;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.GlideUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.SignCompleteDialogUtil;
import com.yssj.utils.TimeUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SweetFriendsDetails extends BasicActivity
        implements OnClickListener, TextWatcher, OnLayoutChangeListener, IXListViewListener {
    private com.yssj.pubu.XListView mListView;
    private ImageView mTopMore;
    private RelativeLayout mRlMore;
    private EditText mEtContent;
    private TextView mSendComment;
    private LinearLayout mBack;
    private boolean isForceLook;// ?????????????????????--????????????
    private boolean isForceLookLimit;// ?????????????????????--?????????????????????
    private View mHeadView;// ??????
    private Context mContext;
    // private MyAdapter mAdapter;
    private List<HashMap<String, Object>> mListDatas;
    PuBuAdapter puAdapter;
    private ImageView scoll_xunbao;
    // private List<ShopComment> listAllComments;// ??????????????????
    // private List<ShopComment> listHostComments;// ????????????
    private boolean mIsNoneFlag = false;// ????????????????????? true?????????????????????false?????????
    private boolean mAllCommentBottomFlag = false;// true???????????????????????????
    private boolean mHostCommentBottomFlag = false;// true???????????????????????????
    private boolean mFistClickHost = false;// true???????????????????????????????????????
    private boolean mLoveFlag = false;// true ??????????????????
    private int mLoveCount = 100;// ???????????????
    private boolean isAttentionFlag = false;// true ???????????????
    private String mTheme_id = "";// ??????id
    private boolean mQqInstallFlag = true;// true???????????????qq
    private boolean mWxInstallFlag = true;// true?????????????????????
    private boolean mQqZone = true;// true???????????????QQ??????
    private int mHightEtComment;// ??????????????????????????????
    private HashMap<String, List<HashMap<String, Object>>> mMapHotRecomment;// ???????????????????????????
    private List<HashMap<String, Object>> mListHotComment = new ArrayList<HashMap<String, Object>>();// ????????????
    private List<HashMap<String, Object>> mListNewComment = new ArrayList<HashMap<String, Object>>();// ????????????
    private List<HashMap<String, Object>> mListAllComment = new ArrayList<HashMap<String, Object>>();// ????????????
    private List<HashMap<String, Object>> mListHostComment = new ArrayList<HashMap<String, Object>>();// ????????????????????????
    private List<HashMap<String, Object>> mListHostHotComment = new ArrayList<HashMap<String, Object>>();// ????????????????????????
    private List<HashMap<String, Object>> mListHostNewComment = new ArrayList<HashMap<String, Object>>();// ????????????????????????
    private List<HashMap<String, Object>> mListRecommend = new ArrayList<HashMap<String, Object>>();// ????????????
    private HashMap<String, Object> mapDetails = new HashMap<String, Object>();// ???????????????
    private String mTags;
    private String user_id;
    private int screenHeight;// ???????????????
    private String mUser_id1;// ????????????id
    private String mUser_id2;// ???????????????id
    private boolean isHotComment = false;// true??????????????????

    public static SweetFriendsDetails instance;


    // H5??????
    String link = "https://www.baidu.com";
    private boolean isComment;// ???????????????????????? ??????????????????
    private boolean isFirstCome = true;
    private LayoutInflater mInflater;
    private CustImageGalleryIntimate custGallery;// ????????????
    private int mScreenWidth;
    private LinearLayout share_ll;
    private boolean isClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        curPager = 1;
        setContentView(R.layout.activity_sweet_friends_details);
        AppManager.getAppManager().addActivity(this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        // listAllComments = new ArrayList<ShopComment>();
        mTheme_id = getIntent().getStringExtra("theme_id");
        user_id = getIntent().getStringExtra("user_id");
        isForceLook = getIntent().getBooleanExtra("isSign", false);//??????X?????????
        isForceLookLimit = getIntent().getBooleanExtra("isTixian", false);//??????X????????? ???????????????
        mInflater = LayoutInflater.from(mContext);

        instance = this;
        try {
            // ???????????????QQ
            if (DeviceConfig.isAppInstalled("com.tencent.mobileqq", mContext)) {
                mQqInstallFlag = true;
            } else {
                mQqInstallFlag = false;
            }
        } catch (Exception e) {
        }

        try {
            // // ?????????????????????
            if (WXcheckUtil.isWeChatAppInstalled(mContext)) {
                mWxInstallFlag = true;
            } else {
                mWxInstallFlag = false;
            }
        } catch (Exception e) {
        }
        try {
            // // ???????????????QQ???????????????
            if (DeviceConfig.isAppInstalled("com.tencent.sc.activity.SplashActivity", mContext)) {
                mQqZone = true;
            } else {
                mQqZone = false;
            }
        } catch (Exception e) {
        }
        if (YJApplication.instance.isLoginSucess()) {
            link = YUrl.YSS_URL_ANDROID_H5 + "/views/topic/detail.html?theme_id=" + mTheme_id + "&realm="
                    + YCache.getCacheUser(mContext).getUser_id();

        } else {
            link = YUrl.YSS_URL_ANDROID_H5 + "/views/topic/detail.html?theme_id=" + mTheme_id;

        }

        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        isComment = getIntent().getBooleanExtra("isComment", false);
        initView();
        initData();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        TongJiUtils.TongJi(mContext, 109 + "");
        LogYiFu.e("TongJiNew", 109 + "");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        TongJiUtils.TongJi(mContext, 9 + "");
        LogYiFu.e("TongJiNew", 9 + "");
    }

    private void initData() {
        // ??????????????????---??????????????????
        queryHotComment(true);
    }

    private void initView() {
        mTopMore = (ImageView) findViewById(R.id.sweet_iv_more);
        mRlMore = (RelativeLayout) findViewById(R.id.sweet_rl_more);
        mRlMore.setOnClickListener(this);
        mEtContent = (EditText) findViewById(R.id.sweet_et_content);

        scoll_xunbao = (ImageView) findViewById(R.id.scoll_xunbao);// ????????????

        if (isForceLook || isForceLookLimit) {
            scoll_xunbao.setVisibility(View.VISIBLE);
        } else {
            scoll_xunbao.setVisibility(View.GONE);

        }

        if (isComment) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    InputMethodManager inputManager = (InputMethodManager) mEtContent.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(mEtContent, 0);
                }

            }, 600);
        }
        LinearLayout mRoot_view = (LinearLayout) findViewById(R.id.issue_rootview);
        // mHightEtComment = mEtContent.getHeight();
        mEtContent.addTextChangedListener(this);
        mRoot_view.addOnLayoutChangeListener(this);
        // mEtContent.setOnFocusChangeListener(new OnFocusChangeListener() {
        //
        // @Override
        // public void onFocusChange(View v, boolean hasFocus) {
        // if(hasFocus){
        // ToastUtil.showLongText(mContext, "?????????");
        // }else{
        // ToastUtil.showLongText(mContext, "????????????");
        // }
        //
        // }
        // });
        mSendComment = (TextView) findViewById(R.id.sweet_tv_send);
        mSendComment.setOnClickListener(this);
        mBack = (LinearLayout) findViewById(R.id.sweet_img_back);
        mBack.setOnClickListener(this);
        mListView = (com.yssj.pubu.XListView) findViewById(R.id.sweet_data);
        mListDatas = new ArrayList<HashMap<String, Object>>();
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
        // mHeadView=LayoutInflater.from(mContext).inflate(R.layout.sweet_head_view,
        // null);
        // initFootView();
        // initHeadView();// ??????????????????mHeadView??????

        mHeadView = View.inflate(mContext, R.layout.sweet_head_view, null);
        mListView.addHeaderView(mHeadView);
        // ??????headerView
        // mListView.addHeaderView(View.inflate(mContext,
        // R.layout.dialog_version_error, null));

        // XListView.mFooterView.setVisibility(View.GONE);
    }

    public void initHeadView() {
        ImageView head_share_friends;// ?????????
        ImageView head_share_qq;// QQ??????
        ImageView head_share_xinlang;// ??????
        ImageView mShareMore;
        ImageView mHeadPic;
        ImageView mGradePic;
        TextView mName;
        TextView mTime;
        TextView mLocation;
        TextView tv_haituijian;
        TextView mContent;
        FlowLayout mLlTagContain;// ????????????
        LinearLayout mLlImgContain;// ????????????
        final TextView mAttention;// ??????
        LinearLayout containerComments;// ??????
        LinearLayout ll_pl;// ????????????

        TextView tv_allPL;// ??????X?????????

        head_share_friends = (ImageView) mHeadView.findViewById(R.id.head_share_friends);
        head_share_qq = (ImageView) mHeadView.findViewById(R.id.head_share_qq);
        head_share_xinlang = (ImageView) mHeadView.findViewById(R.id.head_share_xinlang);
        mShareMore = (ImageView) mHeadView.findViewById(R.id.head_share_more);
        mHeadPic = (ImageView) mHeadView.findViewById(R.id.head_round_img);
        mGradePic = (ImageView) mHeadView.findViewById(R.id.head_iv_grade);
        mName = (TextView) mHeadView.findViewById(R.id.head_tv_name);
        mTime = (TextView) mHeadView.findViewById(R.id.head_tv_time);
        mLocation = (TextView) mHeadView.findViewById(R.id.head_tv_location);
        mContent = (TextView) mHeadView.findViewById(R.id.head_tv_content);
        tv_haituijian = (TextView) mHeadView.findViewById(R.id.tv_haituijian);
        mAttention = (TextView) mHeadView.findViewById(R.id.head_tv_attention);
        tv_allPL = (TextView) mHeadView.findViewById(R.id.tv_allPL);
        mLlTagContain = (FlowLayout) mHeadView.findViewById(R.id.head_ll_tag_contain);
        mLlImgContain = (LinearLayout) mHeadView.findViewById(R.id.head_ll_img_contain);
        ll_pl = (LinearLayout) mHeadView.findViewById(R.id.ll_pl);
        share_ll = (LinearLayout) mHeadView.findViewById(R.id.share_ll);
        // ??????
        containerComments = (LinearLayout) mHeadView.findViewById(R.id.container_comments);
        custGallery = (CustImageGalleryIntimate) mHeadView.findViewById(R.id.intimate_custom_images);
        if (mapDetails.size() > 0) {
            // SetImageLoader.initImageLoader(mContext, mHeadPic, (String)
            // mapDetails.get("head_pic"), "");
//			PicassoUtils.initImage(mContext, (String) mapDetails.get("head_pic"), mHeadPic);
            GlideUtils.initRoundImage(Glide.with(mContext), mContext, (String) mapDetails.get("head_pic"), mHeadPic);

            if ("1".equals((String) mapDetails.get("v_ident"))) {
                mGradePic.setImageResource(R.drawable.v_red);
            } else if ("2".equals((String) mapDetails.get("v_ident"))) {
                mGradePic.setImageResource(R.drawable.v_blue);
            }
            if (((String) mapDetails.get("nickname")).length() > 8) {
                mName.setText("" + ((String) mapDetails.get("nickname")).substring(0, 8) + "...");
            } else {
                mName.setText("" + (String) mapDetails.get("nickname"));
            }
            // ??????
            String send_time = (String) mapDetails.get("send_time");
            long longSendTime = 0;
            try {
                longSendTime = Long.parseLong(send_time);
            } catch (NumberFormatException e) {
                longSendTime = 0;
            }
            if (longSendTime != 0) {
                String showSendTime = TimeUtils.showSendTime(longSendTime);
                mTime.setText(showSendTime);
            }
            // ??????
            String location = (String) mapDetails.get("location");
            if (!TextUtils.isEmpty(location)) {
                if (location.length() > 6) {
                    location = location.substring(0, 6) + "...";
                }
                mLocation.setText(location);
            } else {
                mLocation.setText("????????????");
            }
            String strContent;
            if ("2".equals(mapDetails.get("theme_type"))) {
                strContent = "#" + mapDetails.get("title") + "#";
                mContent.setText(strContent + (String) mapDetails.get("content"));
                SpannableStringBuilder builder = new SpannableStringBuilder(mContent.getText().toString());

                // ForegroundColorSpan ?????????????????????BackgroundColorSpan??????????????????
                ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
                builder.setSpan(redSpan, 0, strContent.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mContent.setText(builder);
            } else {
                if ("".equals((String) mapDetails.get("content"))) {
                    mContent.setVisibility(View.GONE);
                }
                mContent.setText((String) mapDetails.get("content"));
            }
            // ??????
            if (!"1".equals(mapDetails.get("theme_type"))) {
                if ("3".equals(mapDetails.get("theme_type"))) {// ??????
                    List<String> tagId = (List<String>) mapDetails.get("tags");
                    mLlTagContain.removeAllViews();
                    YDBHelper dbHelp = new YDBHelper(mContext);
                    if (tagId != null) {
                        for (int i = 0; i < tagId.size(); i++) {
                            String sql = "select * from friend_circle_tag where _id = " + tagId.get(i);
                            final String tag_id = tagId.get(i);
                            List<HashMap<String, String>> listTag = dbHelp.query(sql);
                            if (listTag != null && listTag.size() > 0) {
                                final String tagName = listTag.get(0).get("name");
                                String tagType = listTag.get(0).get("type");//// 1???????????????2???????????????
                                // final TextView tv = new TextView(mContext);
                                // LinearLayout.LayoutParams params = new
                                // LinearLayout.LayoutParams(
                                // LayoutParams.WRAP_CONTENT,
                                // LayoutParams.WRAP_CONTENT);
                                // params.setMargins(DP2SPUtil.dp2px(mContext,
                                // 10), 0, 0, 0);
                                TextView tv = (TextView) mInflater.inflate(R.layout.search_label_tv, mLlTagContain,
                                        false);
                                tv.setTextSize(12);
                                tv.setText(tagName);
                                // tv.setPadding(DP2SPUtil.dp2px(mContext, 6),
                                // DP2SPUtil.dp2px(mContext, 3),
                                // DP2SPUtil.dp2px(mContext, 6),
                                // DP2SPUtil.dp2px(mContext, 3));
                                // if ("1".equals(tagType)) {// 1???????????? ????????????
                                // tv.setText(tagName);
                                // tv.setBackgroundResource(R.drawable.shape_pink_intimate);
                                // tv.setTextColor(Color.parseColor("#FF3F8B"));
                                // } else {
                                // tv.setText(tagName);
                                // tv.setBackgroundResource(R.drawable.shape_gray_intimate);
                                // tv.setTextColor(Color.parseColor("#7d7d7d"));
                                // }
                                tv.setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(mContext, CommonActivity.class);
                                        intent.putExtra("tag", "" + tag_id);
                                        intent.putExtra("tagName", tagName);
                                        startActivity(intent);
                                        ((Activity) mContext).overridePendingTransition(R.anim.activity_from_right,
                                                R.anim.activity_search_close);
                                    }
                                });
                                // mLlTagContain.addView(tv, params);
                                mLlTagContain.addView(tv);
                            }
                        }
                    }
                } else if ("2".equals(mapDetails.get("theme_type"))) {// ??????

                    List<String> tagId = (List<String>) mapDetails.get("tags");
                    YDBHelper dbHelp2 = new YDBHelper(mContext);
                    if (tagId != null) {
                        for (int i = 0; i < tagId.size(); i++) {
                            String sql2 = "select * from friend_circle_tag where _id = " + tagId.get(i);
                            final String tag_id = tagId.get(i);
                            List<HashMap<String, String>> listTag = dbHelp2.query(sql2);
                            if (listTag != null && listTag.size() > 0) {
                                final String tagName = listTag.get(0).get("name");
                                String tagType = listTag.get(0).get("type");//// 1???????????????2???????????????
                                // final TextView tv = new TextView(mContext);
                                // LinearLayout.LayoutParams params = new
                                // LinearLayout.LayoutParams(
                                // LayoutParams.WRAP_CONTENT,
                                // LayoutParams.WRAP_CONTENT);
                                // params.setMargins(DP2SPUtil.dp2px(mContext,
                                // 10), 0, 0, 0);
                                // tv.setTextSize(14);
                                // tv.setPadding(DP2SPUtil.dp2px(mContext, 6),
                                // DP2SPUtil.dp2px(mContext, 3),
                                // DP2SPUtil.dp2px(mContext, 6),
                                // DP2SPUtil.dp2px(mContext, 3));
                                // if ("1".equals(tagType)) {// 1???????????? ????????????
                                // tv.setText(tagName);
                                // tv.setBackgroundResource(R.drawable.shape_pink_intimate);
                                // tv.setTextColor(Color.parseColor("#FF3F8B"));
                                // } else {
                                // tv.setText(tagName);
                                // tv.setBackgroundResource(R.drawable.shape_gray_intimate);
                                // tv.setTextColor(Color.parseColor("#7d7d7d"));
                                // }
                                TextView tv = (TextView) mInflater.inflate(R.layout.search_label_tv, mLlTagContain,
                                        false);
                                tv.setTextSize(12);
                                tv.setText(tagName);
                                tv.setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(mContext, CommonActivity.class);
                                        intent.putExtra("tag", "" + tag_id);
                                        intent.putExtra("tagName", tagName);
                                        startActivity(intent);
                                        ((Activity) mContext).overridePendingTransition(R.anim.activity_from_right,
                                                R.anim.activity_search_close);
                                    }
                                });
                                mLlTagContain.addView(tv);
                            }
                        }
                    }

                    String styleId = (String) mapDetails.get("style");
                    final String supp_label_id = (String) mapDetails.get("supp_label_id");
                    YDBHelper dbHelp = new YDBHelper(this);
                    final List<HashMap<String, String>> listStyle = dbHelp
                            .query("select * from tag_info where p_id=2 and is_show=1 and _id= " + styleId
                                    + " order by sequence");// ??????
                    String strStyle = "";
                    if (listStyle != null && listStyle.size() > 0) {
                        strStyle = listStyle.get(0).get("attr_name");
                    }
                    final String str_Style = strStyle;
                    String sql = "select * from supp_label where _id = " + supp_label_id + " order by _id";
                    final List<HashMap<String, String>> listSLevel = dbHelp.query(sql);// ??????
                    String strBrand = "";
                    if (listSLevel != null && listSLevel.size() > 0) {
                        strBrand = listSLevel.get(0).get("name");
                    }
                    for (int j = 0; j < 2; j++) {
                        // final TextView tv = new TextView(mContext);
                        // LinearLayout.LayoutParams params = new
                        // LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                        // LayoutParams.WRAP_CONTENT);
                        // params.setMargins(DP2SPUtil.dp2px(mContext, 10), 0,
                        // 0, 0);
                        // tv.setTextSize(14);
                        // tv.setPadding(DP2SPUtil.dp2px(mContext, 6),
                        // DP2SPUtil.dp2px(mContext, 3),
                        // DP2SPUtil.dp2px(mContext, 6),
                        // DP2SPUtil.dp2px(mContext, 3));
                        TextView tv = (TextView) mInflater.inflate(R.layout.search_label_tv, mLlTagContain, false);
                        tv.setTextSize(12);
                        if (j == 0) {
                            tv.setText("" + strStyle);
                        } else {
                            tv.setText("" + strBrand);
                        }
                        final int position = j;
                        tv.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                if (position == 0) {// ??????
                                    HashMap<String, String> map = listStyle.get(0);
                                    HashMap<String, Object> mapRequest = new HashMap<String, Object>();
                                    mapRequest.put("style", map);

                                    Intent intent = new Intent(mContext, FilterResultActivity.class);
                                    intent.putExtra("shop_name", str_Style);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("condition", mapRequest);
                                    // bundle.putString("id", 6 + "");// ??????????????????
                                    // bundle.putString("title", "??????");
                                    intent.putExtras(bundle);
                                    ((FragmentActivity) mContext).startActivity(intent);
                                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.activity_from_right,
                                            R.anim.activity_search_close);
                                } else {// ??????
                                    Intent intent = new Intent(mContext, ManufactureActivity.class);
                                    intent.putExtra("supple_id", supp_label_id);
                                    ((FragmentActivity) mContext).startActivity(intent);
                                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.activity_from_right,
                                            R.anim.activity_search_close);
                                }

                            }
                        });
                        // tv.setBackgroundResource(R.drawable.shape_pink_intimate);
                        // tv.setTextColor(Color.parseColor("#FF3F8B"));
                        if (j == 1) {
                            if (!"??????".equals(strBrand) && !"".equals(tv.getText())) {
                                mLlTagContain.addView(tv);
                            }
                        } else {
                            if (!"".equals(tv.getText())) {
                                mLlTagContain.addView(tv);
                            }
                        }
                    }

                } else {
                    // 3.4.5????????????????????????????????????????????????
                    YDBHelper dbHelp = new YDBHelper(mContext);
                    mLlTagContain.setVisibility(View.VISIBLE);
                    final List<HashMap<String, Object>> shop_list = (List<HashMap<String, Object>>) mapDetails
                            .get("shop_list");
                    List<HashMap<String, Object>> supp_label_list = (List<HashMap<String, Object>>) mapDetails
                            .get("supp_label_list");
                    LinkedHashMap<String, String> hasmapSupple1 = new LinkedHashMap<String, String>();// ????????????
                    // ??????
                    LinkedHashMap<String, String> hasmapSupple2 = new LinkedHashMap<String, String>();// ???????????????
                    // ??????
                    final LinkedHashMap<String, String> hasmapSuppleOnly2 = new LinkedHashMap<String, String>();// ???????????????
                    // only_id
                    LinkedHashMap<String, String> hasmapStyle = new LinkedHashMap<String, String>();
                    // ??????????????? label_id ???????????? 0???????????????????????????????????? 1 ?????????????????????????????????
                    final LinkedHashMap<String, String> hasmapRept = new LinkedHashMap<String, String>();
                    for (int i = 0; i < supp_label_list.size(); i++) {
                        HashMap<String, Object> supp_label_hashmap = supp_label_list.get(i);
                        String label_id = (String) supp_label_hashmap.get("label_id");
                        String label_type = (String) supp_label_hashmap.get("label_type");
                        String only_id = (String) supp_label_hashmap.get("only_id");
                        String style = (String) supp_label_hashmap.get("style");
                        if ("1".equals(label_type)) {
                            if (!hasmapSupple1.containsKey(label_id)) {// ????????????
                                // ????????????????????????
                                // ?????????????????????
                                hasmapSupple1.put(label_id + "", label_id);
                            }
                        } else {
                            if (!hasmapSupple2.containsKey(label_id)) {
                                hasmapSupple2.put(label_id + "", label_id);
                                hasmapSuppleOnly2.put(label_id + "", only_id);
                                hasmapRept.put(label_id + "", "0");
                            } else {
                                hasmapRept.put(label_id + "", "1");// ?????????ID ????????????
                            }
                        }
                        if (!hasmapStyle.containsKey(style)) {
                            hasmapStyle.put(style + "", style);
                        }
                    }
                    // ???????????????????????????
                    for (int i = 0; i < 3; i++) {
                        LinkedHashMap<String, String> hashMap = null;
                        if (i == 0) {
                            hashMap = hasmapSupple1;
                        } else if (i == 1) {
                            hashMap = hasmapSupple2;
                        } else if (i == 2) {
                            hashMap = hasmapStyle;
                        }
                        Iterator<Entry<String, String>> iterator = hashMap.entrySet().iterator();
                        for (int j = 0; j < hashMap.size(); j++) {
                            Map.Entry<String, String> entry = iterator.next();
                            String id = entry.getKey();
                            if (i == 0 || i == 1) {// ??????
                                final boolean isUserdefined = i == 1 ? true : false;// ????????????????????????
                                final String supp_label_id = id;
                                String sql = "select * from supp_label where _id = " + supp_label_id + " order by _id";
                                final List<HashMap<String, String>> listSupp = dbHelp.query(sql);// ??????
                                if (listSupp != null && listSupp.size() > 0) {
                                    final String strBrand = listSupp.get(0).get("name");
                                    if (!"??????".equals(strBrand)) {// ???????????????
                                        TextView childView = (TextView) mInflater.inflate(R.layout.search_label_tv,
                                                mLlTagContain, false);
                                        childView.setText(strBrand);
                                        childView.setTextSize(12);
                                        mLlTagContain.addView(childView);
                                        childView.setOnClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (isUserdefined) {
                                                    if ("1".equals(hasmapRept.get(supp_label_id))) {// ?????????????????????only_id
                                                        // ????????????
                                                        // ???????????????
                                                        if (shop_list != null && shop_list.size() >= 20) {
                                                            Intent intent2 = new Intent(mContext,
                                                                    ForceLookActivity.class);
                                                            intent2.putExtra("title", "????????????");
                                                            intent2.putExtra("isMoreShop", true);
                                                            intent2.putExtra("themeId", mTheme_id);
                                                            mContext.startActivity(intent2);
                                                            ((Activity) mContext).overridePendingTransition(
                                                                    R.anim.slide_left_in, R.anim.slide_match);
                                                        }
                                                    } else {
                                                        Intent intent = new Intent();
                                                        intent = new Intent(mContext, WordSearchResultActivity.class);
                                                        intent.putExtra("isCustomLeable", true);
                                                        intent.putExtra("mTheme_id", mTheme_id);
                                                        intent.putExtra("only_id",
                                                                hasmapSuppleOnly2.get(supp_label_id));
                                                        intent.putExtra("label_name", strBrand);
                                                        mContext.startActivity(intent);
                                                        ((FragmentActivity) mContext).overridePendingTransition(
                                                                R.anim.slide_left_in, R.anim.slide_match);
                                                    }
                                                    return;
                                                }
                                                Intent intent = new Intent(mContext, ManufactureActivity.class);
                                                intent.putExtra("supple_id", supp_label_id);
                                                ((FragmentActivity) mContext).startActivity(intent);
                                                ((FragmentActivity) mContext).overridePendingTransition(
                                                        R.anim.slide_left_in, R.anim.slide_match);
                                            }
                                        });
                                    }
                                }
                            } else if (i == 2) {// ??????
                                final String styleId = id;
                                final List<HashMap<String, String>> listStyle = dbHelp
                                        .query("select * from tag_info where p_id = 2 and is_show = 1 and _id = "
                                                + styleId + " order by sequence");// p_id
                                // =
                                // 2
                                // ??????
                                if (listStyle != null && listStyle.size() > 0) {
                                    final String strStyle = listStyle.get(0).get("attr_name");
                                    TextView childView = (TextView) mInflater.inflate(R.layout.search_label_tv,
                                            mLlTagContain, false);
                                    childView.setText(strStyle);
                                    childView.setTextSize(12);
                                    mLlTagContain.addView(childView);
                                    childView.setOnClickListener(new OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            HashMap<String, String> map = listStyle.get(0);
                                            HashMap<String, Object> mapRequest = new HashMap<String, Object>();
                                            mapRequest.put("style", map);
                                            Intent intent = new Intent(mContext, FilterResultActivity.class);
                                            intent.putExtra("shop_name", strStyle);
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("condition", mapRequest);
                                            intent.putExtras(bundle);
                                            ((FragmentActivity) mContext).startActivity(intent);
                                            ((FragmentActivity) mContext).overridePendingTransition(
                                                    R.anim.slide_left_in, R.anim.slide_match);
                                        }
                                    });
                                }
                            }
                        }
                    }
                    List<String> tagIds = (List<String>) mapDetails.get("tags");
                    for (int i = 0; i < tagIds.size(); i++) {
                        String sql = "select * from friend_circle_tag where _id = " + tagIds.get(i);
                        List<HashMap<String, String>> listTag = dbHelp.query(sql);
                        if (listTag != null && listTag.size() > 0) {
                            mLlTagContain.setVisibility(View.VISIBLE);
                            TextView childView = (TextView) mInflater.inflate(R.layout.search_label_tv, mLlTagContain,
                                    false);
                            final String tagName = listTag.get(0).get("name");
                            final String tagId = listTag.get(0).get("_id");
                            String tagType = listTag.get(0).get("type");//// 1???????????????2???????????????
                            childView.setText(tagName);
                            childView.setTextSize(12);
                            childView.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(mContext, CommonActivity.class);
                                    intent.putExtra("tag", tagId);
                                    intent.putExtra("tagName", tagName);
                                    ((FragmentActivity) mContext).startActivity(intent);
                                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in,
                                            R.anim.slide_match);
                                }
                            });
                            mLlTagContain.addView(childView);
                        }
                    }
                }
            } else {// ?????????????????????

                List<String> tagId = (List<String>) mapDetails.get("tags");
                if (tagId != null && tagId.size() > 0) {
                    mLlTagContain.removeAllViews();
                    YDBHelper dbHelp = new YDBHelper(mContext);
                    if (tagId != null) {
                        for (int i = 0; i < tagId.size(); i++) {
                            String sql = "select * from friend_circle_tag where _id = " + tagId.get(i);
                            final String tag_id = tagId.get(i);
                            List<HashMap<String, String>> listTag = dbHelp.query(sql);
                            if (listTag != null && listTag.size() > 0) {
                                final String tagName = listTag.get(0).get("name");
                                // final TextView tv = new TextView(mContext);
                                // LinearLayout.LayoutParams params = new
                                // LinearLayout.LayoutParams(
                                // LayoutParams.WRAP_CONTENT,
                                // LayoutParams.WRAP_CONTENT);
                                // params.setMargins(DP2SPUtil.dp2px(mContext,
                                // 10), 0, 0, 0);
                                // tv.setTextSize(14);
                                // tv.setPadding(DP2SPUtil.dp2px(mContext, 6),
                                // DP2SPUtil.dp2px(mContext, 3),
                                // DP2SPUtil.dp2px(mContext, 6),
                                // DP2SPUtil.dp2px(mContext, 3));
                                // tv.setText(tagName);
                                // tv.setBackgroundResource(R.drawable.shape_pink_intimate);
                                // tv.setTextColor(Color.parseColor("#FF3F8B"));
                                TextView tv = (TextView) mInflater.inflate(R.layout.search_label_tv, mLlTagContain,
                                        false);
                                tv.setTextSize(12);
                                tv.setText(tagName);
                                tv.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(mContext, CommonActivity.class);
                                        intent.putExtra("tag", "" + tag_id);
                                        intent.putExtra("tagName", tagName);
                                        intent.putExtra("theme_id", mTheme_id);
                                        startActivity(intent);
                                        ((Activity) mContext).overridePendingTransition(R.anim.activity_from_right,
                                                R.anim.activity_search_close);
                                    }
                                });
                                mLlTagContain.addView(tv);
                            }
                        }
                    }
                }
                // else {
                // //
                // final TextView tv = new TextView(mContext);
                // LinearLayout.LayoutParams params = new
                // LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                // LayoutParams.WRAP_CONTENT);
                // params.setMargins(DP2SPUtil.dp2px(mContext, 10), 0, 0, 0);
                // tv.setTextSize(14);
                // tv.setPadding(DP2SPUtil.dp2px(mContext, 6),
                // DP2SPUtil.dp2px(mContext, 3),
                // DP2SPUtil.dp2px(mContext, 6), DP2SPUtil.dp2px(mContext, 3));
                // tv.setText("????????????");
                // tv.setBackgroundResource(R.drawable.shape_pink_intimate);
                // tv.setTextColor(Color.parseColor("#FF3F8B"));
                // mLlTagContain.addView(tv, params);
                // tv.setOnClickListener(new OnClickListener() {
                //
                // @Override
                // public void onClick(View v) {
                // YDBHelper dbHelp = new YDBHelper(mContext);
                // String sql = "select * from friend_circle_tag where name =
                // '????????????' and type = 1";// ????????????????????????
                // final List<HashMap<String, String>> tagIds =
                // dbHelp.query(sql);
                // Intent intent = new Intent(mContext, CommonActivity.class);
                // intent.putExtra("tag", tagIds.get(0).get("_id"));
                // intent.putExtra("tagName", "????????????");
                // intent.putExtra("theme_id", mTheme_id);
                // ((FragmentActivity) mContext).startActivity(intent);
                // ((FragmentActivity)
                // mContext).overridePendingTransition(R.anim.activity_from_right,
                // R.anim.activity_search_close);
                //
                // }
                // });
                // }
            }
            // // ????????????
            // for (int i = 0; i < 3; i++) {
            // final TextView textView = new TextView(mContext);
            // LinearLayout.LayoutParams params = new
            // LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
            // LayoutParams.WRAP_CONTENT);
            // params.setMargins(DP2SPUtil.dp2px(mContext, 10), 0, 0, 0);
            // textView.setText("??????" + i);
            // textView.setPadding(DP2SPUtil.dp2px(mContext, 6),
            // DP2SPUtil.dp2px(mContext, 3),
            // DP2SPUtil.dp2px(mContext, 6), DP2SPUtil.dp2px(mContext, 3));
            // textView.setTextSize(14);
            // if (i == 0) {
            // textView.setBackgroundResource(R.drawable.shape_pink_intimate);
            // textView.setTextColor(Color.parseColor("#ff3f8b"));
            // } else {
            // textView.setBackgroundResource(R.drawable.shape_gray_intimate);
            // textView.setTextColor(Color.parseColor("#7d7d7d"));
            // }
            // mLlTagContain.addView(textView, params);
            // }
            // ????????????
            String pics = "";
            StringBuffer sb = new StringBuffer();
            if (!"1".equals(mapDetails.get("theme_type"))) {
                pics = (String) mapDetails.get("pics");
            } else {
                List<HashMap<String, Object>> shop_list = (List<HashMap<String, Object>>) mapDetails.get("shop_list");
                for (int i = 0; i < shop_list.size(); i++) {
                    String shop_code = shop_list.get(i).get("shop_code").toString();
                    String url = shop_code.substring(1, 4) + File.separator + shop_code + File.separator
                            + shop_list.get(i).get("def_pic").toString();
                    sb.append(url).append(",");
                }
                pics = sb.toString();
            }
            String[] picArray;
            if (null != pics && !"".equals(pics)) {
                picArray = pics.split(",");
                if (picArray != null) {
                    for (int i = 0; i < picArray.length; i++) {
                        final int position = i;
                        if (!"1".equals(mapDetails.get("theme_type"))) {
                            if (i == 0) {
                                RelativeLayout relativeLayout = new RelativeLayout(mContext);
                                ImageView imageView = new ImageView(mContext);
                                imageView.setAdjustViewBounds(true);
                                imageView.setScaleType(ScaleType.FIT_CENTER);
                                String[] split = picArray[i].split(":");
                                float picRadio = 3 / 4;
                                try {
                                    picRadio = Float.parseFloat(split[1]);
                                } catch (Exception e) {
                                    picRadio = 3 / 4;
                                    // TODO: handle exception
                                }
                                int imageHeigth = (int) (mScreenWidth / picRadio);
                                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                        LayoutParams.MATCH_PARENT, imageHeigth);
                                sharePic = "myq/theme/" + mapDetails.get("user_id") + "/" + split[0];

                                String lll = "myq/theme/" + mapDetails.get("user_id") + "/" + split[0] + "!450";

                                PicassoUtils.initBigImage(mContext,
                                        "myq/theme/" + mapDetails.get("user_id") + "/" + split[0] + "!450", imageView);

                                relativeLayout.addView(imageView, layoutParams);
                                List<HashMap<String, Object>> supp_label_list = (List<HashMap<String, Object>>) mapDetails
                                        .get("supp_label_list");
                                if (supp_label_list != null) {
                                    for (int j = 0; j < supp_label_list.size(); j++) {
                                        HashMap<String, Object> supp_label_hashmap = supp_label_list.get(j);
                                        String label_name = (String) supp_label_hashmap.get("label_name");
                                        String label_x = (String) supp_label_hashmap.get("label_x");
                                        String label_y = (String) supp_label_hashmap.get("label_y");
                                        // ??????ID
                                        final String label_id = (String) supp_label_hashmap.get("label_id");
                                        final String label_type = (String) supp_label_hashmap.get("label_type");
                                        final String only_id = (String) supp_label_hashmap.get("only_id");
                                        final String shop_code = (String) supp_label_hashmap.get("shop_code");

                                        // // 1?????????
                                        // final String type1 = (String)
                                        // supp_label_hashmap.get("type1");
                                        // // 2?????????
                                        // final String type2 = (String)
                                        // supp_label_hashmap.get("type2");
                                        // // ??????
                                        // final String style = (String)
                                        // supp_label_hashmap.get("style");
                                        if (!TextUtils.isEmpty(shop_code)) {

                                            //?????????????????????

                                            TextView tv = new TextView(mContext);
                                            tv.setSingleLine();
                                            // if(!"1".equals(label_type)){
                                            YDBHelper dbHelp = new YDBHelper(mContext);
                                            if (!TextUtils.isEmpty(label_id)) {

                                                String sql2 = "select * from supp_label where _id = " + label_id
                                                        + " order by _id";
                                                final List<HashMap<String, String>> listSupp = dbHelp.query(sql2);// ??????
                                                if (listSupp != null && listSupp.size() > 0) {
                                                    label_name = listSupp.get(0).get("name");
                                                    tv.setText("" + label_name);
                                                } else {
                                                    tv.setText("????????????");
                                                }
                                            } else {
                                                tv.setText("????????????");
                                            }

                                            tv.setTextColor(Color.parseColor("#ffffff"));
                                            tv.setTextSize(12);
                                            tv.setGravity(Gravity.CENTER);
                                            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(
                                                    LayoutParams.WRAP_CONTENT, DP2SPUtil.dp2px(mContext, 46));

                                            int picWidth = mScreenWidth;
                                            int picHeight = (int) (picWidth / picRadio);
                                            int tagLeft = (int) (picWidth * Float.parseFloat(label_x));
                                            int tagTop = (int) (picHeight * Float.parseFloat(label_y));
                                            String direction = (String) supp_label_hashmap.get("direction");
                                            if ("0".equals(direction)) {
                                                tv.setBackgroundResource(R.drawable.left_shop_buy);
                                            } else {
                                                tv.setBackgroundResource(R.drawable.right_shop_buy);
                                            }
                                            if (label_x.contains("-")) {// ??????
//												tv.setPadding(DP2SPUtil.dp2px(mContext, 6), 0,
//														DP2SPUtil.dp2px(mContext, 10), 0);
                                                layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                                if (label_y.contains("-")) {
                                                    layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                                                    layoutParams2.setMargins(0, 0, Math.abs(tagLeft), Math.abs(tagTop));
                                                } else {
                                                    layoutParams2.setMargins(0, tagTop, Math.abs(tagLeft), 0);
                                                }
                                            } else {
//												tv.setPadding(DP2SPUtil.dp2px(mContext, 10), 0,
//														DP2SPUtil.dp2px(mContext, 6), 0);
                                                if (label_y.contains("-")) {
                                                    layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                                                    layoutParams2.setMargins(tagLeft, 0, 0, Math.abs(tagTop));
                                                } else {
                                                    layoutParams2.setMargins(tagLeft, tagTop, 0, 0);
                                                }
                                            }

                                            tv.setOnClickListener(new OnClickListener() {

                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent(mContext, ShopDetailsActivity.class);
//													intent.putExtra("code","CAAX1713545452");
                                                    intent.putExtra("code", shop_code);
//													intent.putExtra("sweet_theme_id", "" + mTheme_id);
                                                    mContext.startActivity(intent);
                                                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                                                }
                                            });

                                            relativeLayout.addView(tv, layoutParams2);

                                        } else {

                                            //??????????????????
                                            TextView tv = new TextView(mContext);
                                            tv.setSingleLine();
                                            // if(!"1".equals(label_type)){
                                            YDBHelper dbHelp = new YDBHelper(mContext);
                                            if (supp_label_hashmap.containsKey("label_id")) {

                                                String sql2 = "select * from supp_label where _id = " + label_id
                                                        + " order by _id";
                                                final List<HashMap<String, String>> listSupp = dbHelp.query(sql2);// ??????
                                                if (listSupp != null && listSupp.size() > 0) {
                                                    label_name = listSupp.get(0).get("name");
                                                    tv.setText("" + label_name);
                                                } else {
                                                    tv.setVisibility(View.GONE);
                                                }
                                            } else {
                                                tv.setVisibility(View.GONE);
                                            }
                                            // }
                                            // else{
                                            // tv.setText(""+label_name);
                                            // }
                                            tv.setTextColor(Color.parseColor("#ffffff"));
                                            tv.setTextSize(12);
                                            tv.setGravity(Gravity.CENTER);
                                            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(
                                                    LayoutParams.WRAP_CONTENT, DP2SPUtil.dp2px(mContext, 35));

                                            int picWidth = mScreenWidth;
                                            int picHeight = (int) (picWidth / picRadio);
                                            int tagLeft = (int) (picWidth * Float.parseFloat(label_x));
                                            int tagTop = (int) (picHeight * Float.parseFloat(label_y));
                                            String direction = (String) supp_label_hashmap.get("direction");
                                            if ("0".equals(direction)) {
                                                tv.setBackgroundResource(R.drawable.issue_tag);
                                            } else {
                                                tv.setBackgroundResource(R.drawable.issue_tag_right);
                                            }
                                            if (label_x.contains("-")) {// ??????
                                                tv.setPadding(DP2SPUtil.dp2px(mContext, 6), 0,
                                                        DP2SPUtil.dp2px(mContext, 10), 0);
                                                layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                                if (label_y.contains("-")) {
                                                    layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                                                    layoutParams2.setMargins(0, 0, Math.abs(tagLeft), Math.abs(tagTop));
                                                } else {
                                                    layoutParams2.setMargins(0, tagTop, Math.abs(tagLeft), 0);
                                                }
                                            } else {
                                                tv.setPadding(DP2SPUtil.dp2px(mContext, 10), 0,
                                                        DP2SPUtil.dp2px(mContext, 6), 0);
                                                if (label_y.contains("-")) {
                                                    layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                                                    layoutParams2.setMargins(tagLeft, 0, 0, Math.abs(tagTop));
                                                } else {
                                                    layoutParams2.setMargins(tagLeft, tagTop, 0, 0);
                                                }
                                            }

                                            final String name = label_name;
                                            tv.setOnClickListener(new OnClickListener() {

                                                @Override
                                                public void onClick(View v) {
                                                    if (!"1".equals(label_type)) {
                                                        // ????????????????????????????????????????????????----????????????ID?????????ID??????
                                                        // ???????????????????????????

                                                        Intent intent = new Intent();
                                                        intent = new Intent(mContext, WordSearchResultActivity.class);
                                                        intent.putExtra("isCustomLeable", true);
                                                        intent.putExtra("mTheme_id", mTheme_id);
                                                        intent.putExtra("only_id", only_id);
                                                        intent.putExtra("label_name", name);
                                                        mContext.startActivity(intent);
                                                        ((Activity) mContext).overridePendingTransition(
                                                                R.anim.activity_from_right, R.anim.activity_search_close);

                                                    } else {
                                                        Intent intent = new Intent(mContext, ManufactureActivity.class);
                                                        intent.putExtra("supple_id", "" + label_id);
                                                        ((FragmentActivity) mContext).startActivity(intent);
                                                        ((FragmentActivity) mContext).overridePendingTransition(
                                                                R.anim.slide_left_in, R.anim.slide_match);
                                                    }
                                                }
                                            });
                                            // if(tagLeft<mScreenWidth/2){
                                            // tv.setBackgroundResource(R.drawable.issue_tag);
                                            // }else{
                                            // tv.setBackgroundResource(R.drawable.issue_tag_right);
                                            // }

                                            relativeLayout.addView(tv, layoutParams2);
                                        }
                                    }
                                }
                                mLlImgContain.addView(relativeLayout, layoutParams);
                            } else {
                                ImageView imageView = new ImageView(mContext);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                                if (i > 0) {
                                    layoutParams.setMargins(0, DP2SPUtil.dp2px(mContext, 10), 0, 0);
                                }
                                imageView.setAdjustViewBounds(true);
                                imageView.setScaleType(ScaleType.FIT_CENTER);
                                String[] split = picArray[i].split(":");

                                if (i == 0) {
                                    sharePic = "myq/theme/" + mapDetails.get("user_id") + "/" + split[0];
                                }

                                // SetImageLoader.initImageLoader(mContext,
                                // imageView,
                                // "myq/theme/" + mapDetails.get("user_id") +
                                // "/" +
                                // split[0], "!450");

                                PicassoUtils.initImage(mContext,
                                        "myq/theme/" + mapDetails.get("user_id") + "/" + split[0] + "!450", imageView);

                                mLlImgContain.addView(imageView, layoutParams);
                            }
                        } else {
                            final List<HashMap<String, Object>> shop_list = (List<HashMap<String, Object>>) mapDetails
                                    .get("shop_list");
                            View view = View.inflate(mContext, R.layout.sweet_image, null);
                            ImageView ivPic = (ImageView) view.findViewById(R.id.sweet_image);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                            if (i > 0) {
                                layoutParams.setMargins(0, DP2SPUtil.dp2px(mContext, 10), 0, 0);
                            }
                            TextView tvName = (TextView) view.findViewById(R.id.sweet_name);
                            TextView tvPrice = (TextView) view.findViewById(R.id.sweet_price);
                            TextView tvOldPrice = (TextView) view.findViewById(R.id.sweet_old_price);
                            TextView tvBuy = (TextView) view.findViewById(R.id.sweet_to_buy);

                            if (i == 0) {
                                sharePic = picArray[i];
                            }

                            // SetImageLoader.initImageLoader(mContext, ivPic,
                            // picArray[i], "!450");
                            PicassoUtils.initBigImage(mContext, picArray[i] + "!450", ivPic);

                            if ("1".equals("" + shop_list.get(i).get("shop_status"))) {
                                tvBuy.setBackgroundResource(R.drawable.shape_public_all_c5c5c5);
                            }
                            tvName.setText((String) shop_list.get(i).get("shop_name"));
                            tvPrice.setText("" + shop_list.get(i).get("shop_se_price"));
                            tvOldPrice.setText("" + shop_list.get(i).get("shop_price"));
                            tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // ????????????
                            ivPic.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(mContext, ShopDetailsActivity.class);
                                    intent.putExtra("code", (String) shop_list.get(position).get("shop_code"));
                                    intent.putExtra("sweet_theme_id", "" + mTheme_id);
                                    ((Activity) mContext).startActivity(intent);
                                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                                            R.anim.slide_left_out);

                                }
                            });
                            mLlImgContain.addView(view, layoutParams);
                        }
                        // if (!"1".equals(mapDetails.get("theme_type"))) {
                        //
                        // } else {
                        // SetImageLoader.initImageLoader(mContext, imageView,
                        // picArray[i], "!450");
                        // }
                        // if (i == 0) {
                        // SetImageLoader.initImageLoader(mContext, imageView,
                        // "/myq/theme/12345/1487834163216_0.jpg", "!450");
                        // } else if (i == 1) {
                        // SetImageLoader.initImageLoader(mContext, imageView,
                        // "/myq/theme/12345/1487834163216_1.jpg", "!450");
                        // } else if (i == 2) {
                        // SetImageLoader.initImageLoader(mContext, imageView,
                        // "/myq/theme/12345/1487834163216_2.jpg", "!450");
                        // } else {
                        // SetImageLoader.initImageLoader(mContext, imageView,
                        // "/myq/theme/12345/1487834163216_3.jpg", "!450");
                        // }
                    }
                }
            }
            // ????????????
            List<HashMap<String, Object>> shop_list = (List<HashMap<String, Object>>) mapDetails.get("shop_list");
            if (!"1".equals(mapDetails.get("theme_type")) && shop_list != null && shop_list.size() > 0) {
                custGallery.setDataSweetDet(shop_list, mTheme_id, true);
                custGallery.setVisibility(View.VISIBLE);
            } else {
                custGallery.setVisibility(View.GONE);
            }
            if ("1".equals((String) mapDetails.get("attention_status"))) {// ???????????????
                isAttentionFlag = true;
                mAttention.setBackgroundResource(R.drawable.icon_attention);
                // mAttention.setText("?????????");
                // mAttention.setTextColor(Color.parseColor("#c5c5c5"));
            } else {
                isAttentionFlag = false;
            }
            if (YJApplication.instance.isLoginSucess()) {

                if (("" + YCache.getCacheUser(mContext).getUser_id()).equals((String) mapDetails.get("user_id"))) {
                    mAttention.setVisibility(View.GONE);
                }
            }
            mAttention.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (!YJApplication.instance.isLoginSucess()) {// ????????????????????????????????????
                        toLogin();
                        return;
                    }
                    if (isAttentionFlag) {
                        addOrDelAttention((String) mapDetails.get("user_id"), "2", mAttention);// ??????????????????
                        return;
                    }
                    addOrDelAttention((String) mapDetails.get("user_id"), "1", mAttention);// ????????????
                }
            });
            mShareMore.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    getShareMoreWindow();
                }
            });

            // ??????????????????
            head_share_friends.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) { //????????????????????????

                    if (!mWxInstallFlag) {
                        ToastUtil.showShortText(mContext, "????????????????????????~");
                    } else {

                        ShareUtil.addWXPlatform(mContext);
                        if (sharePic.equals("")) {
                            // ???????????????????????????????????????APP???????????????????????????
                            wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
                            SharedPreferencesUtil.saveStringData(mContext, "messageSubSub",
                                    mapDetails.get("content") + "");
                            ShareUtil.setShareContent(mContext,
                                    new UMImage(mContext, R.drawable.gerenzhongxin_morentouxiang_bg),
                                    mapDetails.get("content") + "", link);
                            performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);

                        } else {
                            createSharePic(link, "", 2);
                        }
                    }
                }
            });
            // ?????????QQ??????
            head_share_qq.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mQqInstallFlag && !mQqZone) {
                        ToastUtil.showShortText(mContext, "???????????????QQ???QQ?????????~");
                    } else {

                        ShareUtil.addQQQZonePlatform(mContext);
                        if (sharePic.equals("")) {
                            // ???????????????????????????????????????APP???????????????????????????
                            qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
                            ShareUtil.setShareContentFriend(mContext,
                                    new UMImage(mContext, R.drawable.gerenzhongxin_morentouxiang_bg),
                                    mapDetails.get("content") + "", link, "");
                            performShare(SHARE_MEDIA.QZONE, qqShareIntent);
                        } else {
                            createSharePic(link, "", 1);
                        }
                    }
                }
            });
            // ???????????????
            head_share_xinlang.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sharePic.equals("")) {
                        SinaShareContent sinaShareContent = new SinaShareContent();
                        sinaShareContent.setShareContent(mapDetails.get("content") + "" + "\t" + link);
                        sinaShareContent
                                .setShareImage(new UMImage(mContext, R.drawable.gerenzhongxin_morentouxiang_bg));
                        mController.setShareMedia(sinaShareContent);
                        performShare(SHARE_MEDIA.SINA, weiBoShareIntent);
                    } else {
                        createSharePic(link, "", 3);
                        createSharePic(link, "", 3);
                    }
                }
            });

            puAdapter = new PuBuAdapter(mContext, mListDatas, isForceLook, isForceLookLimit);
            mListView.setAdapter(puAdapter);

            // ????????????

            if (mListAllComment != null && mListAllComment.size() > 0) {
                ll_pl.setVisibility(View.VISIBLE);
                tv_allPL.setText("??????" + mapDetails.get("comment_count") + "?????????");
                ll_pl.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, AllEvaluateActivity.class);
                        intent.putExtra("theme_id", mTheme_id);
                        if (mapDetails.size() > 0) {
                            intent.putExtra("user_id", (String) mapDetails.get("user_id"));
                            intent.putExtra("applaud_status", (String) mapDetails.get("applaud_status"));
                            intent.putExtra("applaud_num", (String) mapDetails.get("applaud_num"));
                            intent.putExtra("comment_count", (String) mapDetails.get("comment_count"));
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("hot_comment", (Serializable) mListHotComment);
                            intent.putExtras(bundle);
                        }
                        startActivity(intent);

                        ((FragmentActivity)
                                mContext).overridePendingTransition(R.anim.slide_left_in,
                                R.anim.slide_match);


                    }
                });
                addComments(containerComments, mListAllComment);
            } else {
                ll_pl.setVisibility(View.GONE);
            }

            // ?????????????????????????????????
            initXGTJ(tv_haituijian, true);

        }
    }

    private int curPager;

    private int la = 0; // 1???????????? ----------2??????????????????

    // ????????????????????????
    private void initXGTJ(final TextView tv_haituijian, final Boolean isFirst) {

        new SAsyncTask<Void, Void, List<HashMap<String, Object>>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                LoadingDialog.show((FragmentActivity) mContext);
            }

            @Override
            protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ModQingfeng.getFridendDetislMore(context, mTheme_id + "", curPager + "");

            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
                if (e != null) {// ????????????
                    // ptrstgv.onRefreshComplete();
                    return;
                }
                // ??????????????????
                List<HashMap<String, Object>> dataList = result;
                if (dataList != null) {
                    if (dataList.size() == 0) { // ???????????????
                        if (isFirst) {
                            tv_haituijian.setVisibility(View.GONE);
                        }

                        XListView.hasData = false;
                        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
                                LayoutParams.FILL_PARENT);
                        layoutParam.setMargins(0, 0, 0, DP2SPUtil.px2dip(mContext, -280));
                        mListView.setLayoutParams(layoutParam);
                        if (curPager > 1) {
                            ToastUtil.showShortText(mContext, "????????????????????????");
                        }

                    } else {// ?????????
                        XListView.hasData = true;
                        mListDatas.addAll(dataList);
                        puAdapter.notifyDataSetChanged();
                        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
                                LayoutParams.FILL_PARENT);
                        layoutParam.setMargins(0, 0, 0, DP2SPUtil.px2dip(mContext, -280));
                        mListView.setLayoutParams(layoutParam);

                    }
                } else { // ????????????

                }

                mListView.stopLoadMore();
                super.onPostExecute(context, result, e);
            }

        }.execute();

    }

    String sharePic = "";
    private Bitmap bmBg;
    private File file;

    // i: 1-QQ 2-?????? 3-??????
    private void createSharePic(final String link, final String picPath, final int i) {
        new SAsyncTask<Void, Void, Void>((FragmentActivity) this, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            protected Void doInBackground(FragmentActivity context, Void... params) throws Exception {
                // TODO Auto-generated method stub

                // String downLoadPic = subList.get(0).get("show_pic")+"";

                bmBg = downloadPic(sharePic);
                if (bmBg != null) {
                    QRCreateUtil.saveBitmap(bmBg, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// ????????????
                } else {
                    // ??????????????????
                    QRCreateUtil.saveBitmap(
                            BitmapFactory.decodeResource(getResources(), R.drawable.gerenzhongxin_morentouxiang_bg),
                            YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// ????????????
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
                    share(file, link, i);
                }
            }

        }.execute();
    }

    private Intent wXinShareIntent;
    private Intent qqShareIntent;
    private Intent weiBoShareIntent;
    private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);

    private void share(File file, String link, int i) {
        UMImage umImage;
        switch (i) {
            case 1:// qq??????
                qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
                if (file == null) {
                    ToastUtil.showShortText(this, "??????????????????????????????~~");
                    return;
                }
                umImage = new UMImage(this, file);
                ShareUtil.setShareContentFriend(this, umImage, mapDetails.get("content") + "", link, "");
                performShare(SHARE_MEDIA.QZONE, qqShareIntent);

                // ShareUtil.setShareContent(mActivity, umImage,
                // "????????????????????????????????????????????????????????????????????????~", link);
                // performShare(SHARE_MEDIA.QZONE, qqShareIntent);

                break;
            case 2:// ??????????????? ----????????????????????????
                // ????????????????????????


                String shareMIniAPPimgPicWai = YUrl.imgurl + sharePic + "!280";
                String wxMiniPathdUOWai = "/pages/shouye/detail/sweetFriendsDetail/friendsDetail?theme_id=" + mTheme_id +
                        "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();
                //????????????????????????????????????
                WXminiAPPShareUtil.shareToWXminiAPP(mContext, shareMIniAPPimgPicWai, mapDetails.get("content") + "", wxMiniPathdUOWai, false);


//
//			wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
//			SharedPreferencesUtil.saveStringData(this, "messageSubSub", mapDetails.get("content") + "");
//			if (file == null) {
//				ToastUtil.showShortText(this, "??????????????????????????????~~");
//				return;
//			}
//			umImage = new UMImage(this, file);
//			ShareUtil.setShareContent(this, umImage, mapDetails.get("content") + "", link);
//			performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);


                break;
            case 3:// ??????
                // mController.getConfig().setSsoHandler(new SinaSsoHandler());
                // weiBoShareIntent =
                // ShareUtil.shareMultiplePictureToSina(ShareUtil.getImage());
                // ShareUtil.setShareContent(context, new UMImage(context,
                // R.drawable.wodexihao_fengge_rixi), messageSub,
                // "http://www.cnblogs.com/wt616/archive/2011/06/20/2085368.html");
                //
                // performShare(SHARE_MEDIA.SINA, weiBoShareIntent);

                // ???????????????????????????
                SinaShareContent sinaShareContent = new SinaShareContent();
                sinaShareContent.setShareContent(mapDetails.get("content") + "" + "\t" + link);
                sinaShareContent.setShareImage(new UMImage(this, file));
                mController.setShareMedia(sinaShareContent);
                performShare(SHARE_MEDIA.SINA, weiBoShareIntent);

                // ????????????????????????????????????---????????????

                // weiBoShareIntent =
                // ShareUtil.shareMultiplePictureToSina(ShareUtil.getImage());
                // ShareUtil.setSinaShareContent(mController, context, new
                // UMImage(context, R.drawable.wodexihao_fengge_rixi),
                // messageSub,
                // "http://www.cnblogs.com/wt616/archive/2011/06/20/2085368.html",
                // true);
                // context.startActivity(weiBoShareIntent);
                break;

            case 4:// ????????????----?????????
//			WeiXinShareContent wei = new WeiXinShareContent();
//			wei.setShareContent("??????APP");
//			wei.setTitle(mapDetails.get("content") + "");
//			wei.setTargetUrl(link);
//			wei.setShareMedia(new UMImage(mContext, file));
//			mController.setShareMedia(wei);
//			performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);


                String shareMIniAPPimgPic = YUrl.imgurl + sharePic + "!280";
                String wxMiniPathdUO = "/pages/shouye/detail/sweetFriendsDetail/friendsDetail?theme_id=" + mTheme_id +
                        "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();
                //????????????????????????????????????
                WXminiAPPShareUtil.shareToWXminiAPP(mContext, shareMIniAPPimgPic, mapDetails.get("content") + "", wxMiniPathdUO, false);

                break;

            case 5:// qq??????
//                QQShareContent qq = new QQShareContent();
//                qq.setShareContent("??????APP");
//                qq.setTitle(mapDetails.get("content") + "");
//                qq.setTargetUrl(link);
//                qq.setShareMedia(new UMImage(mContext, file));
//                mController.setShareMedia(qq);
//                performShare(SHARE_MEDIA.QQ, qqShareIntent);
                break;

            default:
                break;
        }

    }

    private Bitmap downloadPic(String picPath) {
        try {
            URL url = new URL(YUrl.imgurl + picPath);
            // https://yssj-real-test.b0.upaiyun.com/collocationShop/2016-07-13/Pg3holtx.jpg
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

    public void performShare(SHARE_MEDIA platform, final Intent intent) {
        mController.postShare(this, platform, new SnsPostListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                String showText = platform.toString();
                if (eCode == StatusCode.ST_CODE_SUCCESSED) {

                } else {

                }
            }

        });
    }

    private Intent weixinShareIntent;

    @Override
    public void onBackPressed() {
        // setResult(14342);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sweet_img_back:
                // setResult(14342);
                finish();
                break;
            case R.id.iv_wxin_circle_share:// ????????????

                if (!mWxInstallFlag) {
                    ToastUtil.showShortText(mContext, "????????????????????????~");
                } else {
                    if (window != null) {
                        WindowManager.LayoutParams params2 = getWindow().getAttributes();
                        params2.alpha = 1f;
                        getWindow().setAttributes(params2);
                        window.dismiss();
                    }
                    ShareUtil.addWXPlatform(mContext);
                    weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());
                    if (sharePic.equals("")) {


//					WeiXinShareContent wei = new WeiXinShareContent();
//					// wei.setTitle("??????????????????????????????????????????");
//					wei.setShareContent("??????APP");
//					wei.setTitle(mapDetails.get("content") + "");
//					wei.setTargetUrl(link);
//					wei.setShareMedia(new UMImage(mContext, R.drawable.gerenzhongxin_morentouxiang_bg));
//					mController.setShareMedia(wei);
//					performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);


                        String shareMIniAPPimgPic = YUrl.imgurl + sharePic + "!280";
                        String wxMiniPathdUO = "/pages/shouye/detail/sweetFriendsDetail/friendsDetail?theme_id=" + mTheme_id +
                                "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();
                        //????????????????????????????????????
                        WXminiAPPShareUtil.shareToWXminiAPP(mContext, shareMIniAPPimgPic, mapDetails.get("content") + "", wxMiniPathdUO, false);


                    } else {
                        createSharePic(link, "", 4);
                    }
                }
                break;
            case R.id.iv_wxin_share:// ???????????????

                if (!mWxInstallFlag) {
                    ToastUtil.showShortText(mContext, "????????????????????????~");
                } else {
                    if (window != null) {
                        WindowManager.LayoutParams params2 = getWindow().getAttributes();
                        params2.alpha = 1f;
                        getWindow().setAttributes(params2);
                        window.dismiss();
                    }
                    ShareUtil.addWXPlatform(mContext);
                    if (sharePic.equals("")) {
                        // ???????????????????????????????????????APP???????????????????????????

                        wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
                        SharedPreferencesUtil.saveStringData(mContext, "messageSubSub", mapDetails.get("content") + "");
                        ShareUtil.setShareContent(mContext,
                                new UMImage(mContext, R.drawable.gerenzhongxin_morentouxiang_bg),
                                mapDetails.get("content") + "", link);
                        performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);

                    } else {
                        createSharePic(link, "", 2);
                    }
                }
                break;
            case R.id.iv_qq_share:// QQ??????
                if (!mQqInstallFlag) {
                    ToastUtil.showShortText(mContext, "???????????????QQ???~");
                } else {
                    if (window != null) {
                        WindowManager.LayoutParams params2 = getWindow().getAttributes();
                        params2.alpha = 1f;
                        getWindow().setAttributes(params2);
                        window.dismiss();
                    }
                    ShareUtil.addQQQZonePlatform(mContext);
                    qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
                    if (sharePic.equals("")) {
//                        QQShareContent qq = new QQShareContent();
//                        qq.setShareContent("??????APP");
//                        qq.setTitle(mapDetails.get("content") + "");
//                        qq.setTargetUrl(link);
//                        qq.setShareMedia(new UMImage(mContext, R.drawable.gerenzhongxin_morentouxiang_bg));
//                        mController.setShareMedia(qq);
//                        performShare(SHARE_MEDIA.QQ, qqShareIntent);

                    } else {
                        createSharePic(link, "", 5);
                    }
                }
                break;
            case R.id.iv_qqkone_share:// QQ??????
                if (!mQqInstallFlag && !mQqZone) {
                    ToastUtil.showShortText(mContext, "???????????????QQ???QQ?????????~");
                } else {
                    if (window != null) {
                        WindowManager.LayoutParams params2 = getWindow().getAttributes();
                        params2.alpha = 1f;
                        getWindow().setAttributes(params2);
                        window.dismiss();
                    }
                    ShareUtil.addQQQZonePlatform(mContext);
                    if (sharePic.equals("")) {
                        // ???????????????????????????????????????APP???????????????????????????
                        qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
                        ShareUtil.setShareContentFriend(mContext,
                                new UMImage(mContext, R.drawable.gerenzhongxin_morentouxiang_bg),
                                mapDetails.get("content") + "", link, "");
                        performShare(SHARE_MEDIA.QZONE, qqShareIntent);
                    } else {
                        createSharePic(link, "", 1);
                    }
                }
                break;
            case R.id.iv_weibo_share:// ??????
                if (window != null) {
                    window.dismiss();
                    WindowManager.LayoutParams params2 = getWindow().getAttributes();
                    params2.alpha = 1f;
                    getWindow().setAttributes(params2);
                }
                if (sharePic.equals("")) {
                    SinaShareContent sinaShareContent = new SinaShareContent();
                    sinaShareContent.setShareContent(mapDetails.get("content") + "" + "\t" + link);
                    sinaShareContent.setShareImage(new UMImage(mContext, R.drawable.gerenzhongxin_morentouxiang_bg));
                    mController.setShareMedia(sinaShareContent);
                    performShare(SHARE_MEDIA.SINA, weiBoShareIntent);
                } else {
                    createSharePic(link, "", 3);
                }
                break;
            case R.id.sweet_rl_more:
                getMoreWindow();
                break;
            case R.id.sweet_tv_send:
                if (!isClick) {
                    isClick = true;
                    if (!YJApplication.instance.isLoginSucess()) {// ????????????????????????????????????
                        toLogin();
                        isClick = false;
                        return;
                    }
                    if ("????????????????????????~".equals(mEtContent.getHint().toString())) {// ???????????????
                        if (mEtContent.getText().toString() == null || "".equals(mEtContent.getText().toString().trim())) {
                            isClick = false;
                            return;
                        }
                        // ShopComment comment = new ShopComment();
                        // comment.setContent(mEtContent.getText().toString().trim());
                        // comment.setAdd_date(System.currentTimeMillis());
                        // comment.setUser_name(YCache.getCacheUser(mContext).getNickname());
                        // comment.setUser_url(YCache.getCacheUser(mContext).getPic());
                        // mListNewComment.add(0, comment);
                        // mAdapter.notifyDataSetChanged();
                        getCity();
                        // commentTieZi(mTheme_id, (String)
                        // mapDetails.get("user_id"),
                        // mEtContent.getText().toString(), "?????????????????????");
                    } else {// ???????????????
                        commentIn(mUser_id1, mUser_id2, mEtContent.getText().toString());
                    }
                }
                break;
            default:
                break;
        }
    }

    private PopupWindow window;

    /**
     * ??????????????????
     */
    public void getShareMoreWindow() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;

        getWindow().setAttributes(params);
        View view = LayoutInflater.from(mContext).inflate(R.layout.sweet_popup_share, null);
        ImageView ivWx = (ImageView) view.findViewById(R.id.iv_wxin_circle_share);
        ImageView ivFriends = (ImageView) view.findViewById(R.id.iv_wxin_share);
        ImageView ivQQ = (ImageView) view.findViewById(R.id.iv_qq_share);
        ImageView ivQQKonw = (ImageView) view.findViewById(R.id.iv_qqkone_share);
        ImageView ivWeiBo = (ImageView) view.findViewById(R.id.iv_weibo_share);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancal);
        window = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        window.setTouchable(true);
        window.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        ivWx.setOnClickListener(this);
        ivFriends.setOnClickListener(this);
        ivQQ.setOnClickListener(this);
        ivQQKonw.setOnClickListener(this);
        ivWeiBo.setOnClickListener(this);

        tvCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                WindowManager.LayoutParams params2 = getWindow().getAttributes();
                params2.alpha = 1f;
                getWindow().setAttributes(params2);
                window.dismiss();

            }
        });
    }

    /**
     * ??????????????????
     */
    @SuppressLint("RtlHardcoded")
    public void getMoreWindow() {
        int width = getResources().getDisplayMetrics().widthPixels;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;

        getWindow().setAttributes(params);
        View view = LayoutInflater.from(mContext).inflate(R.layout.sweet_window_more, null);
        TextView tvCancel = (TextView) view.findViewById(R.id.window_cancel);
        TextView tvReport = (TextView) view.findViewById(R.id.window_report);// ????????????
        TextView tvShield = (TextView) view.findViewById(R.id.window_shield);// ????????????
        final PopupWindow window = new PopupWindow(view, DP2SPUtil.dp2px(mContext, 150),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setTouchable(true);
        window.showAsDropDown(mTopMore, 0, 0);
        tvCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                WindowManager.LayoutParams params2 = getWindow().getAttributes();
                params2.alpha = 1f;
                getWindow().setAttributes(params2);
                window.dismiss();

            }
        });
        tvReport.setOnClickListener(new OnClickListener() {// ????????????

            @Override
            public void onClick(View v) {
                WindowManager.LayoutParams params2 = getWindow().getAttributes();
                params2.alpha = 1f;
                getWindow().setAttributes(params2);
                window.dismiss();
                getReportDialog();
            }
        });
        tvShield.setOnClickListener(new OnClickListener() {// ????????????

            @Override
            public void onClick(View v) {
                WindowManager.LayoutParams params2 = getWindow().getAttributes();
                params2.alpha = 1f;
                getWindow().setAttributes(params2);
                // TODO Auto-generated method stub

            }
        });
    }

    private int mReportItem = 0;

    public void getReportDialog() {

        final Dialog reportDialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = View.inflate(mContext, R.layout.sweet_report_dialog, null);
        final ImageView ivAd = (ImageView) view.findViewById(R.id.sweet_dialog_ad);
        final ImageView ivSexy = (ImageView) view.findViewById(R.id.sweet_dialog_sexy);
        final ImageView ivHit = (ImageView) view.findViewById(R.id.sweet_dialog_hit);
        final ImageView ivOther = (ImageView) view.findViewById(R.id.sweet_dialog_other);
        Button btnCancel = (Button) view.findViewById(R.id.sweet_dialog_cancel);
        Button btnReport = (Button) view.findViewById(R.id.sweet_dialog_report);
        reportDialog.setContentView(view, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        reportDialog.setCanceledOnTouchOutside(false);
        reportDialog.show();
        ivAd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mReportItem = 0;
                ivAd.setImageResource(R.drawable.youhuiquan_selected);
                ivSexy.setImageResource(R.drawable.youhuiquan_default);
                ivHit.setImageResource(R.drawable.youhuiquan_default);
                ivOther.setImageResource(R.drawable.youhuiquan_default);
            }
        });
        ivSexy.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mReportItem = 1;
                ivAd.setImageResource(R.drawable.youhuiquan_default);
                ivSexy.setImageResource(R.drawable.youhuiquan_selected);
                ivHit.setImageResource(R.drawable.youhuiquan_default);
                ivOther.setImageResource(R.drawable.youhuiquan_default);
            }
        });
        ivHit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mReportItem = 2;
                ivAd.setImageResource(R.drawable.youhuiquan_default);
                ivSexy.setImageResource(R.drawable.youhuiquan_default);
                ivHit.setImageResource(R.drawable.youhuiquan_selected);
                ivOther.setImageResource(R.drawable.youhuiquan_default);
            }
        });
        ivOther.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mReportItem = 3;
                ivAd.setImageResource(R.drawable.youhuiquan_default);
                ivSexy.setImageResource(R.drawable.youhuiquan_default);
                ivHit.setImageResource(R.drawable.youhuiquan_default);
                ivOther.setImageResource(R.drawable.youhuiquan_selected);
            }
        });
        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                reportDialog.dismiss();

            }
        });
        btnReport.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                reportDialog.dismiss();
                String[] str = {"??????", "??????", "????????????", "??????(??????/?????????)"};
                sweetReportTieZi(str[mReportItem], mTheme_id);
            }
        });
    }

    private int mClick = 0;// 0???????????????????????????1????????????????????????

    // private class MyAdapter extends BaseAdapter implements
    // StickyListHeadersAdapter {
    //
    // @Override
    // public int getCount() {
    // // TODO Auto-generated method stub
    // if (mClick == 1) {
    // if (mListHostComment.size() == 0) {
    // mIsNoneFlag = true;
    // return 1;
    // }
    // mIsNoneFlag = false;
    // return mListHostComment.size();
    // }
    // if (mListAllComment.size() == 0) {
    // mIsNoneFlag = true;
    // return 1;
    // }
    // mIsNoneFlag = false;
    // return mListAllComment.size();
    // }
    //
    // @Override
    // public Object getItem(int position) {
    // // TODO Auto-generated method stub
    // return null;
    // }
    //
    // @Override
    // public long getItemId(int position) {
    // // TODO Auto-generated method stub
    // return position;
    // }
    //
    // @Override
    // public View getView(final int position, View convertView, ViewGroup
    // parent) {
    // // TODO Auto-generated method stub
    //
    // final ViewHolder holder;
    // if (convertView == null) {
    // // convertView=View.inflate(mContext, R.layout.sweet_item,
    // // null);
    // convertView = LayoutInflater.from(mContext).inflate(R.layout.sweet_item,
    // parent, false);
    // holder = new ViewHolder();
    // holder.mLlHot = (LinearLayout)
    // convertView.findViewById(R.id.item_ll_hot);
    // holder.mLlNew = (LinearLayout)
    // convertView.findViewById(R.id.item_ll_new);
    // holder.mNoneComment = (RelativeLayout)
    // convertView.findViewById(R.id.item_rl_none_comment);
    // holder.mHaveComment = (LinearLayout)
    // convertView.findViewById(R.id.item_ll__comment);
    // holder.mHeadPic = (RoundImageButton)
    // convertView.findViewById(R.id.item_head_img);
    // holder.mGradePic = (ImageView)
    // convertView.findViewById(R.id.item_iv_grade);
    // holder.mName = (TextView) convertView.findViewById(R.id.item_tv_name);
    // holder.mLocation = (TextView)
    // convertView.findViewById(R.id.item_tv_location);
    // holder.mTime = (TextView) convertView.findViewById(R.id.item_tv_time);
    // holder.mContent = (TextView) convertView.findViewById(R.id.item_content);
    // holder.mZanCount = (TextView)
    // convertView.findViewById(R.id.item_zan_count);
    // holder.mContain = (LinearLayout)
    // convertView.findViewById(R.id.item_ll_contain);
    // holder.mZanIcon = (TextView)
    // convertView.findViewById(R.id.item_zan_icon);
    // // if (mClick == 1) {
    // // holder.mNoneComment.setVisibility(View.VISIBLE);
    // // holder.mHaveComment.setVisibility(View.GONE);
    // // } else {
    // // holder.mNoneComment.setVisibility(View.GONE);
    // // holder.mHaveComment.setVisibility(View.VISIBLE);
    // // }
    // convertView.setTag(holder);
    // } else {
    // holder = (ViewHolder) convertView.getTag();
    // }
    // {
    // if (mClick == 0) {
    // if (mListAllComment.size() == 0) {
    // holder.mNoneComment.setVisibility(View.VISIBLE);
    // holder.mHaveComment.setVisibility(View.GONE);
    // } else {
    // holder.mNoneComment.setVisibility(View.GONE);
    // holder.mHaveComment.setVisibility(View.VISIBLE);
    // }
    // } else {
    // if (mListHostComment.size() == 0) {
    // holder.mNoneComment.setVisibility(View.VISIBLE);
    // holder.mHaveComment.setVisibility(View.GONE);
    // } else {
    // holder.mNoneComment.setVisibility(View.GONE);
    // holder.mHaveComment.setVisibility(View.VISIBLE);
    // }
    // }
    // if (!mIsNoneFlag) {
    // String outPic = "";// ??????
    // String outGrade = "";// ??????
    // final String outName;// ??????
    // String outLocation = "";// ??????
    // String outTime = "";// ??????
    // final String outZanCount;// ????????????
    // String outContent = "";// ??????
    // final String outUserId;// ??????id
    // final String outCommentId;// ??????id
    // final String outHostUserId;// ????????????id
    // // List<String> outZanUserId;// ????????????id??????
    // List<HashMap<String, Object>> outSonList = new ArrayList<HashMap<String,
    // Object>>();// ?????????
    // String outZanStatus;
    // if (mClick == 0) {
    // // shopComment = listAllComments.get(position);
    // outPic = (String) mListAllComment.get(position).get("head_pic");
    // outGrade = (String) mListAllComment.get(position).get("v_ident");
    // outName = (String) mListAllComment.get(position).get("nickname");
    // outLocation = (String) mListAllComment.get(position).get("location");
    // outTime = (String) mListAllComment.get(position).get("send_time");
    // outZanCount = (String) mListAllComment.get(position).get("applaud_num");
    // outContent = (String) mListAllComment.get(position).get("content");
    // outUserId = (String) mListAllComment.get(position).get("user_id");
    // outCommentId = (String) mListAllComment.get(position).get("comment_id");
    // outHostUserId = (String)
    // mListAllComment.get(position).get("base_user_id");
    // // outZanUserId = (List<String>)
    // // mListAllComment.get(position).get("applaud_user_list");
    // outSonList = (List<HashMap<String, Object>>)
    // mListAllComment.get(position).get("replies_list");
    // outZanStatus = (String)
    // mListAllComment.get(position).get("comments_applaud_status");
    // } else {
    // outPic = (String) mListHostComment.get(position).get("head_pic");
    // outGrade = (String) mListHostComment.get(position).get("v_ident");
    // outName = (String) mListHostComment.get(position).get("nickname");
    // outLocation = (String) mListHostComment.get(position).get("location");
    // outTime = (String) mListHostComment.get(position).get("send_time");
    // outZanCount = (String) mListHostComment.get(position).get("applaud_num");
    // outContent = (String) mListHostComment.get(position).get("content");
    // outUserId = (String) mListHostComment.get(position).get("user_id");
    // outCommentId = (String) mListHostComment.get(position).get("comment_id");
    // outHostUserId = (String)
    // mListHostComment.get(position).get("base_user_id");
    // // outZanUserId = (List<String>)
    // // mListHostComment.get(position).get("applaud_user_list");
    // outSonList = (List<HashMap<String, Object>>)
    // mListHostComment.get(position).get("replies_list");
    // outZanStatus = (String)
    // mListHostComment.get(position).get("comments_applaud_status");
    // }
    //
    // if (mClick == 0 && position == 0 && mListHotComment.size() > 0) {
    // holder.mLlHot.setVisibility(View.VISIBLE);
    // holder.mLlNew.setVisibility(View.GONE);
    // } else if (mClick == 0 && mListHotComment.size() > 0 && position ==
    // mListHotComment.size()) {
    // holder.mLlHot.setVisibility(View.GONE);
    // holder.mLlNew.setVisibility(View.VISIBLE);
    // } else if (mClick == 1 && position == 0 && mListHostHotComment.size() >
    // 0) {
    // holder.mLlHot.setVisibility(View.VISIBLE);
    // holder.mLlNew.setVisibility(View.GONE);
    // } else if (mClick == 1 && mListHostHotComment.size() > 0
    // && position == mListHostHotComment.size()) {
    // holder.mLlHot.setVisibility(View.GONE);
    // holder.mLlNew.setVisibility(View.VISIBLE);
    // } else {
    // holder.mLlHot.setVisibility(View.GONE);
    // holder.mLlNew.setVisibility(View.GONE);
    // }
    // holder.mContain.removeAllViews();
    // // int count = position + 1;
    // // count = count > 10 ? 10 : count;
    // for (int i = 0; i < outSonList.size(); i++) {// ???????????????
    // final String inCommentId;// ???????????????id
    // final String inName;// ??????????????????
    // final String inUserId;// ????????????id
    // String inOtherName;// ?????????????????????
    // final String inOtherUserId;// ???????????????id
    // String inTime;// ??????
    // String inContent;// ??????
    // // if(mClick==0){
    // inCommentId = (String) outSonList.get(i).get("replies_id");
    // inName = (String) outSonList.get(i).get("send_nickname");
    // inUserId = (String) outSonList.get(i).get("send_user_id");
    // inOtherName = (String) outSonList.get(i).get("receive_nickname");
    // inOtherUserId = (String) outSonList.get(i).get("receive_user_id");
    // inTime = (String) outSonList.get(i).get("send_time");
    // inContent = (String) outSonList.get(i).get("content");
    // // }
    //
    // TextView textView = new TextView(mContext);
    // LinearLayout.LayoutParams params = new
    // LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
    // LinearLayout.LayoutParams.WRAP_CONTENT);
    // params.setMargins(DP2SPUtil.dp2px(mContext, 10), 0, 0, 0);
    // if (i == 0 && outSonList.size() > 1) {
    // textView.setPadding(0, DP2SPUtil.dp2px(mContext, 10), 0,
    // DP2SPUtil.dp2px(mContext, 5));
    // } else if (i == 0) {
    // textView.setPadding(0, DP2SPUtil.dp2px(mContext, 10), 0,
    // DP2SPUtil.dp2px(mContext, 10));
    // } else if (i == outSonList.size() - 1) {
    // textView.setPadding(0, DP2SPUtil.dp2px(mContext, 5), 0,
    // DP2SPUtil.dp2px(mContext, 10));
    // } else {
    // textView.setPadding(0, DP2SPUtil.dp2px(mContext, 5), 0,
    // DP2SPUtil.dp2px(mContext, 5));
    // }
    // // ds.setColor()????????????span?????????????????????????????????????????????????????????
    // // ????????????????????????(HighLightColor)??????TextView????????????
    // // Android4.0???????????????????????????????????????????????????????????????????????????????????????????????????????????????
    // textView.setHighlightColor(getResources().getColor(android.R.color.transparent));
    // SpannableString spanableInfo;
    // String inName2 = inName;
    // String inOtherName2 = inOtherName;
    // if (inUserId.equals(outHostUserId)) {
    // inName2 = "??????";
    // }
    // if (inOtherUserId.equals(outHostUserId)) {
    // inOtherName2 = "??????";
    // }
    // if (!"".equals(inOtherName2)) {
    // spanableInfo = new SpannableString(inName2 + "??????" + inOtherName2 + ": " +
    // inContent);
    // spanableInfo.setSpan(new Clickable(clickListener2), inName2.length() + 2,
    // inName2.length() + 2 + inOtherName2.length(),
    // Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    // } else {
    // spanableInfo = new SpannableString(inName2 + ": " + inContent);
    // }
    // spanableInfo.setSpan(new Clickable(clickListener), 0, inName2.length(),
    // Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    // textView.setText(spanableInfo);
    // textView.setTextColor(Color.parseColor("#7d7d7d"));
    // textView.setTextSize(14);
    // textView.setMovementMethod(LinkMovementMethod.getInstance());
    // holder.mContain.addView(textView, params);
    // textView.setOnClickListener(new OnClickListener() {
    //
    // @Override
    // public void onClick(View v) {
    // if (!YJApplication.instance.isLoginSucess()) {
    // return;
    // }
    // if (!("" +
    // YCache.getCacheUser(mContext).getUser_id()).equals(outHostUserId)
    // && !("" + YCache.getCacheUser(mContext).getUser_id()).equals(inUserId)
    // && !("" + YCache.getCacheUser(mContext).getUser_id()).equals("" +
    // outUserId)) {// ?????????????????????????????????????????????????????????
    // return;
    // }
    // if (position < mListHotComment.size()) {
    // isHotComment = true;
    // } else {
    // isHotComment = false;
    // }
    // // if (clickFlag) {
    // // clickFlag = false;
    // // return;
    // // }
    // mUser_id1 = outCommentId;
    // mUser_id2 = inUserId;
    // InputMethodManager imm = (InputMethodManager) getSystemService(
    // Context.INPUT_METHOD_SERVICE);
    // mEtContent.requestFocus();
    // // mEtContent.performClick();
    // imm.showSoftInput(mEtContent, InputMethodManager.SHOW_IMPLICIT);
    // String inName3 = inName;
    // if (inUserId.equals(outHostUserId)) {
    // inName3 = "??????";
    // }
    // mEtContent.setHint("??????" + inName3);
    //
    // }
    // });
    // }
    //
    // // SetImageLoader.initImageLoader(mContext, holder.mHeadPic,
    // // outPic, "");
    // PicassoUtils.initImage(mContext, outPic, holder.mHeadPic);
    // if ("1".equals(outGrade)) {
    // holder.mGradePic.setVisibility(View.VISIBLE);
    // holder.mGradePic.setImageResource(R.drawable.v_red);
    // } else if ("2".equals((String) mapDetails.get("v_ident"))) {
    // holder.mGradePic.setVisibility(View.VISIBLE);
    // holder.mGradePic.setImageResource(R.drawable.v_blue);
    // } else {
    // holder.mGradePic.setVisibility(View.GONE);
    // }
    // if (!TextUtils.isEmpty(outName)) {
    //
    // // if (outName.length() == 1) {
    // // outName = outName + "****";
    // // }
    // if (outUserId.equals(outHostUserId)) {
    // holder.mName.setText("??????");
    // } else {
    // holder.mName.setText(outName);
    // }
    // }
    // if (!TextUtils.isEmpty(outTime)) {
    // long longSendTime = 0;
    // try {
    // longSendTime = Long.parseLong(outTime);
    // } catch (NumberFormatException e) {
    // longSendTime = 0;
    // }
    // if (longSendTime != 0) {
    // String showSendTime = TimeUtils.showSendTime(longSendTime);
    // holder.mTime.setText(showSendTime);
    // }
    // }
    // // ??????
    // if (!TextUtils.isEmpty(outLocation)) {
    // if (outLocation.length() > 6) {
    // outLocation = outLocation.substring(0, 6) + "...";
    // }
    // holder.mLocation.setText(outLocation);
    // } else {
    // holder.mLocation.setText("????????????");
    // }
    // if (!TextUtils.isEmpty(outContent)) {
    // holder.mContent.setText(outContent);
    // }
    // // boolean zan_Flag = false;
    // // for (int i = 0; i < outZanUserId.size(); i++) {
    // // if (("" +
    // //
    // YCache.getCacheUser(mContext).getUser_id()).equals(outZanUserId.get(i)))
    // // {
    // // zan_Flag = true;
    // // break;
    // // }
    // // }
    // if ("1".equals(outZanStatus)) {
    // holder.mZanIcon.setBackgroundResource(R.drawable.sweet_icon_zan_pre);
    // } else {
    // holder.mZanIcon.setBackgroundResource(R.drawable.sweet_icon_zan);
    // }
    // // ????????????
    // holder.mZanCount.setText(outZanCount);
    // holder.mZanIcon.setOnClickListener(new OnClickListener() {
    //
    // @Override
    // public void onClick(View v) {
    // if (!YJApplication.instance.isLoginSucess()) {// ????????????????????????????????????
    // toLogin();
    // return;
    // }
    // if (LimitDoubleClicked.isFastDoubleClick500())
    // return;
    // // final List<String> outZanUser_Id;// ????????????id??????
    // final String inZanStatus;
    // boolean zanFlag = false;
    // if (mClick == 0) {
    // // outZanUser_Id = (List<String>)
    // // mListAllComment.get(position).get("applaud_user_list");
    // inZanStatus = (String)
    // mListAllComment.get(position).get("comments_applaud_status");
    // } else {
    // // outZanUser_Id = (List<String>)
    // // mListHostComment.get(position).get("applaud_user_list");
    // inZanStatus = (String)
    // mListHostComment.get(position).get("comments_applaud_status");
    // }
    // // for (int i = 0; i < outZanUser_Id.size(); i++) {
    // // if (("" +
    // //
    // YCache.getCacheUser(mContext).getUser_id()).equals(outZanUser_Id.get(i)))
    // // {
    // // zanFlag = true;
    // // break;
    // // }
    // // }
    // if ("0".equals(inZanStatus)) {
    // dianZan(outCommentId, "2", mTheme_id, holder.mZanIcon, holder.mZanCount,
    // position);
    //
    // } else {
    // removeZan(outCommentId, "2", mTheme_id, holder.mZanIcon,
    // holder.mZanCount, position);
    //
    // }
    // }
    // });
    // convertView.setOnClickListener(new OnClickListener() {
    //
    // @Override
    // public void onClick(View v) {
    // if (YJApplication.instance.isLoginSucess()) {
    // if (!("" + YCache.getCacheUser(mContext).getUser_id()).equals(outUserId)
    // && !("" +
    // YCache.getCacheUser(mContext).getUser_id()).equals(outHostUserId)) {
    // return;
    // }
    // }
    // InputMethodManager imm = (InputMethodManager) getSystemService(
    // Context.INPUT_METHOD_SERVICE);
    // mEtContent.requestFocus();
    // // mEtContent.performClick();
    // imm.showSoftInput(mEtContent, InputMethodManager.SHOW_IMPLICIT);
    // mEtContent.setHint("??????" + outName);
    // mUser_id1 = outCommentId;
    // mUser_id2 = outUserId;
    // }
    //
    // });
    // }
    // }
    //
    // return convertView;
    // }
    //
    // @Override
    // public View getHeaderView(int position, View convertView, ViewGroup
    // parent) {
    // // View view = View.inflate( R.layout.sweet_top, parent,false);
    // View view = LayoutInflater.from(mContext).inflate(R.layout.sweet_top,
    // parent, false);
    // final TextView allComment = (TextView)
    // view.findViewById(R.id.top_tv_all);
    // final TextView hostComment = (TextView)
    // view.findViewById(R.id.top_tv_host);
    // final TextView tvIconLove = (TextView)
    // view.findViewById(R.id.top_tv_icon_love);
    // final TextView tvLoveCount = (TextView)
    // view.findViewById(R.id.top_tv_love_count);
    // final View line1 = view.findViewById(R.id.top_line1);
    // final View line2 = view.findViewById(R.id.top_line2);
    // if (mClick == 1) {
    // line1.setVisibility(View.INVISIBLE);
    // line2.setVisibility(View.VISIBLE);
    // allComment.setTextColor(Color.parseColor("#7d7d7d"));
    // hostComment.setTextColor(Color.parseColor("#3e3e3e"));
    // } else {
    // line1.setVisibility(View.VISIBLE);
    // line2.setVisibility(View.INVISIBLE);
    // allComment.setTextColor(Color.parseColor("#3e3e3e"));
    // hostComment.setTextColor(Color.parseColor("#7d7d7d"));
    // }
    // if (mapDetails.size() > 0) {
    // if ("1".equals((String) mapDetails.get("applaud_status"))) {// ???????????????
    // mLoveFlag = true;
    // } else {
    // mLoveFlag = false;
    // }
    // if (mLoveFlag) {
    // tvIconLove.setBackgroundResource(R.drawable.sweet_icon_xihuan_pre);
    // } else {
    // tvIconLove.setBackgroundResource(R.drawable.sweet_icon_xihuan);
    // }
    //
    // mLoveCount = Integer.parseInt((String) mapDetails.get("applaud_num"));
    // tvLoveCount.setText("" + mLoveCount);
    // }
    // tvIconLove.setOnClickListener(new OnClickListener() {
    //
    // @Override
    // public void onClick(View v) {
    // if (!YJApplication.instance.isLoginSucess()) {// ????????????????????????????????????
    // toLogin();
    // return;
    // }
    // if (!mLoveFlag) {
    // dianZan(mTheme_id, "1", mTheme_id, tvIconLove, tvLoveCount, 0);
    // } else {
    // removeZan(mTheme_id, "1", mTheme_id, tvIconLove, tvLoveCount, 0);
    // }
    // mListView.smoothScrollBy(1, 1);
    // mListView.smoothScrollBy(-1, 1);
    // }
    // });
    // if (mapDetails.size() > 0) {
    // allComment.setText("???????????? (" + (String) mapDetails.get("comment_count") +
    // ")");
    // }
    // allComment.setOnClickListener(new OnClickListener() {
    //
    // @Override
    // public void onClick(View v) {
    // if (mClick == 0) {
    // return;
    // }
    // mClick = 0;
    // mAdapter.notifyDataSetChanged();
    // if (mAllCommentBottomFlag) {
    // }
    // allComment.setTextColor(Color.parseColor("#3e3e3e"));
    // hostComment.setTextColor(Color.parseColor("#7d7d7d"));
    // line1.setVisibility(View.VISIBLE);
    // line2.setVisibility(View.INVISIBLE);
    // // mIsNoneFlag = false;
    //
    // }
    // });
    // hostComment.setOnClickListener(new OnClickListener() {
    //
    // @Override
    // public void onClick(View v) {
    // if (mClick == 1) {
    // return;
    // }
    // mClick = 1;
    // mAdapter.notifyDataSetChanged();
    // if (mHostCommentBottomFlag) {// ????????????
    // } else if (mListHostComment.size() == 0) {
    // queryHostCommentByShop();
    // }
    // // mFistClickHost = true;
    // allComment.setTextColor(Color.parseColor("#7d7d7d"));
    // hostComment.setTextColor(Color.parseColor("#3e3e3e"));
    // line1.setVisibility(View.INVISIBLE);
    // line2.setVisibility(View.VISIBLE);
    // // mIsNoneFlag = true;
    //
    // }
    // });
    // return view;
    // }
    //
    // @Override
    // public long getHeaderId(int position) {
    // // TODO Auto-generated method stub
    // return 1;
    // }
    //
    // }

    class ViewHolder {
        private RelativeLayout mNoneComment;// ?????????????????????????????????
        private LinearLayout mHaveComment;// ??????????????????
        private RoundImageButton mHeadPic;// ??????
        private ImageView mGradePic;
        private TextView mName;// ???????????????
        private TextView mLocation;// ???????????????
        private TextView mTime;// ????????????
        private TextView mContent;// ????????????
        private TextView mZanCount;// ????????????
        private LinearLayout mContain;// ???????????????
        private TextView mZanIcon;// ????????????
        private LinearLayout mLlHot;// ????????????
        private LinearLayout mLlNew;// ????????????
    }

    private boolean clickFlag = false;
    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // clickFlag = true;
            // Toast.makeText(SweetFriendsDetails.this, "????????????....??????",
            // Toast.LENGTH_SHORT).show();
        }
    };
    private OnClickListener clickListener2 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // clickFlag = true;
            // Toast.makeText(SweetFriendsDetails.this, "????????????....??????",
            // Toast.LENGTH_SHORT).show();
        }
    };

    class Clickable extends ClickableSpan {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener l) {
            mListener = l;
        }

        /**
         * ????????????????????????
         */
        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }

        /**
         * ????????????updateDrawState?????? ???????????????TextView??????????????????,??????????????????...
         */
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.parseColor("#ff3f8b"));
        }
    }

    /**
     * ?????????????????????????????????
     */
    private void queryHotComment(final boolean isFristFlag) {
        // if (page == 1) {
        // rows = 5;
        // } else {
        // }
        new SAsyncTask<Void, Void, HashMap<String, List<HashMap<String, Object>>>>((FragmentActivity) mContext, null,
                R.string.wait) {

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                LoadingDialog.show(SweetFriendsDetails.this);
            }

            @Override
            protected HashMap<String, List<HashMap<String, Object>>> doInBackground(FragmentActivity context,
                                                                                    Void... params) throws Exception {
                mMapHotRecomment = ComModel2.getHotCommentList((FragmentActivity) context, mTheme_id, "" + user_id);
                return mMapHotRecomment;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, List<HashMap<String, Object>>> map,
                                         Exception e) {
                // isCheck = false;
                if (e != null) {// ????????????
                    Toast.makeText(context, "????????????????????????", Toast.LENGTH_LONG).show();
                    // page--;
                } else {// ???????????????????????????????????????
                    // rrr.setBackgroundColor(Color.WHITE);

                    if (map != null && map.size() > 0) {
                        mListHotComment = map.get("hot_comments");
                        mListAllComment.addAll(mListHotComment);
                        mListRecommend = map.get("related_recommended");
                        if (map.get("post_details").size() > 0) {
                            mapDetails = map.get("post_details").get(0);
                            // initHeadView();// ??????????????????mHeadView??????
                        }
                    }
                    // ????????????
                    queryNewComment();

                }

            }

            ;

            @Override
            protected boolean isHandleException() {
                return true;
            }

            ;
        }.execute();

    }

    /**
     * ?????????????????? ????????????
     */
    private void addComments(LinearLayout containerComments, List<HashMap<String, Object>> mListAllComment) {

        // ??????
        // // outName = (String) mListAllComment.get(position).get("nickname");
        // //??????
        // // outContent = (String)
        // mListAllComment.get(position).get("content");
        //

        containerComments.removeAllViews();
        containerComments.setVisibility(View.VISIBLE);
        int length = mListAllComment.size() < 5 ? mListAllComment.size() : 5;// ???????????????????????????
        SpannableString ssComment;
        for (int i = 0; i < length; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.intimate_list_comments_layout, null);

            String nickname = mListAllComment.get(i).get("nickname") + "";

            // ????????????ID
            String outUserId = (String) mListAllComment.get(i).get("user_id");
            // ??????ID
            String outCommentId = (String) mListAllComment.get(i).get("base_user_id");
            if (outUserId.equals(outCommentId)) {
                nickname = "??????";
            }

            String comment = nickname + ": " + mListAllComment.get(i).get("content");
            ssComment = new SpannableString(comment);
            ssComment.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3F8B")), 0, nickname.length() + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv.setText(ssComment);

            containerComments.addView(tv);
        }

    }

    private int rows = 10, page = 1;
    private boolean isCheck = false;

    /**
     * ??????????????????
     */
    private void queryNewComment() {
        // if (page == 1) {
        // rows = 5;
        // } else {
        rows = 10;
        // }
        isCheck = true;
        new SAsyncTask<Void, Void, List<HashMap<String, Object>>>((FragmentActivity) mContext, null, R.string.wait) {

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                LoadingDialog.show(SweetFriendsDetails.this);
            }

            @Override
            protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                List<HashMap<String, Object>> list = ComModel2.getNewCommentList((FragmentActivity) context, mTheme_id,
                        "" + page, "" + rows);
                return list;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> list, Exception e) {
                isCheck = false;
                if (e != null) {// ????????????
                    Toast.makeText(context, "????????????????????????", Toast.LENGTH_LONG).show();
                    page--;
                } else {
                    // ????????????
                    if (list != null && list.size() > 0) {
                        if (page == 1) {
                            mListAllComment.clear();
                            mListNewComment.clear();
                            mListAllComment.addAll(mListHotComment);
                        }

                        if (list != null && list.size() > 0) {
                            for (int i = 0; i < list.size(); i++) {
                                if ("0".equals(list.get(i).get("status"))) {
                                    mListNewComment.add(list.get(i));
                                    mListAllComment.add(list.get(i));
                                } else if (YCache.getCacheUser(mContext) != null && ("" + list.get(i).get("user_id")).equals("" + YCache.getCacheUser(mContext).getUser_id())) {
                                    mListNewComment.add(list.get(i));
                                    mListAllComment.add(list.get(i));
                                }
                            }
                        }
//						mListNewComment.addAll(list);
//						mListAllComment.addAll(list);

                    }

                }

                initHeadView();// ??????????????????mHeadView??????
                if (isForceLook || isForceLookLimit) {
                    scollScanSign();
                }

            }

            ;

            @Override
            protected boolean isHandleException() {
                return true;
            }

            ;
        }.execute();

    }

    private int rows2 = 10, page2 = 1;
    private boolean isCheck2 = false;

    /**
     * ????????????????????????
     */
    // private void queryHostCommentByShop() {
    // // if (page == 1) {
    // // rows = 5;
    // // } else {
    // rows2 = 10;
    // // }
    // isCheck2 = true;
    // new SAsyncTask<Void, Void, HashMap<String, List<HashMap<String,
    // Object>>>>((FragmentActivity) mContext, null,
    // R.string.wait) {
    //
    // @Override
    // protected void onPreExecute() {
    // // TODO Auto-generated method stub
    // super.onPreExecute();
    // LoadingDialog.show(SweetFriendsDetails.this);
    // }
    //
    // @Override
    // protected HashMap<String, List<HashMap<String, Object>>>
    // doInBackground(FragmentActivity context,
    // Void... params) throws Exception {
    // HashMap<String, List<HashMap<String, Object>>> map =
    // ComModel2.getHostCommentList(
    // (FragmentActivity) context, mTheme_id, (String)
    // mapDetails.get("user_id"), "" + page2,
    // "" + rows2);
    // return map;
    // }
    //
    // @Override
    // protected void onPostExecute(FragmentActivity context, HashMap<String,
    // List<HashMap<String, Object>>> map,
    // Exception e) {
    // isCheck2 = false;
    // if (e != null) {// ????????????
    // Toast.makeText(context, "????????????????????????", Toast.LENGTH_LONG).show();
    // page2--;
    // } else {// ???????????????????????????????????????
    // // rrr.setBackgroundColor(Color.WHITE);
    // if ((map != null && map.size() > 0 && page2 == 1
    // && (map.get("list_hot").size() > 0 || map.get("list_new").size() > 0))
    // || (map != null && map.size() > 0 && page2 > 1 &&
    // map.get("list_new").size() > 0)) {
    // if (page2 == 1) {
    // mListHostComment.clear();
    // mListHostHotComment.clear();
    // mListHostNewComment.clear();
    // }
    // if (mListHostComment == null) {
    // mListHostComment = new ArrayList<HashMap<String, Object>>();
    // }
    // if (page2 == 1) {
    // mListHostHotComment.addAll(map.get("list_hot"));// ????????????
    // mListHostComment.addAll(mListHostHotComment);
    // }
    // mListHostNewComment.addAll(map.get("list_new"));// ????????????
    // mListHostComment.addAll(mListHostNewComment);
    // } else {
    // mHostCommentBottomFlag = true;
    // if (page2 > 1) {
    // Toast.makeText(mContext, "???????????????", Toast.LENGTH_SHORT).show();
    // }
    // isCheck2 = true;
    // }
    // if (mAdapter != null) {
    // mAdapter.notifyDataSetChanged();
    // }
    // }
    //
    // };
    //
    // @Override
    // protected boolean isHandleException() {
    // return true;
    // };
    // }.execute();
    //
    // }
    public void getCity() {
        // ?????????????????????
        new SAsyncTask<Void, Void, UserInfo>(this, R.string.wait) {

            @Override
            protected UserInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel.queryUserInfo(context);
                // return YCache.getCacheUser(context);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, UserInfo result) {
                super.onPostExecute(context, result);
                String city = "";
                try {
                    city = DBService.getIntance().queryAreaNameById(Integer.parseInt(result.getProvince()))
                            + DBService.getIntance().queryAreaNameById(Integer.parseInt(result.getCity()));
                } catch (NumberFormatException e) {

                }
                commentTieZi(mTheme_id, (String) mapDetails.get("user_id"), mEtContent.getText().toString(), city);
            }

        }.execute();
    }

    /**
     * ????????????
     *
     * @param this_id ??????id?????????id
     * @param type:1  ?????????2 ?????? ?????? theme_id ????????????id
     */
    public void dianZan(final String this_id, final String type, final String theme_id, final TextView tvIconLove,
                        final TextView tvLoveCount, final int position) {
        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, 0) {

            protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {

                return ComModel2.sweetZan(mContext, this_id, type, theme_id);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void onPostExecute(FragmentActivity mContext, ReturnInfo result, Exception e) {
                super.onPostExecute(mContext, result, e);
                if (null == e && result != null && "1".equals("" + result.getStatus())) {
                    if ("1".equals(type)) {// ??????
                        mLoveFlag = true;
                        mLoveCount++;
                        tvLoveCount.setText("" + mLoveCount);
                        tvIconLove.setBackgroundResource(R.drawable.sweet_icon_xihuan_pre);
                        mapDetails.put("applaud_status", "1");
                        mapDetails.put("applaud_num", "" + mLoveCount);
                    } else if ("2".equals(type)) {// ??????
                        int count;
                        if (mClick == 0) {
                            count = Integer.parseInt((String) mListAllComment.get(position).get("applaud_num")) + 1;
                        } else {
                            count = Integer.parseInt((String) mListHostComment.get(position).get("applaud_num")) + 1;
                        }
                        // String user_ids = "" +
                        // YCache.getCacheUser(mContext).getUser_id();
                        if (mClick == 0) {
                            // ((List<String>)
                            // mListAllComment.get(position).get("applaud_user_list")).add(user_ids);
                            mListAllComment.get(position).put("comments_applaud_status", "1");
                        } else {
                            // ((List<String>)
                            // mListHostComment.get(position).get("applaud_user_list")).add(user_ids);
                            mListHostComment.get(position).put("comments_applaud_status", "1");
                        }
                        tvIconLove.setBackgroundResource(R.drawable.sweet_icon_zan_pre);
                        // zanFlag = true;
                        tvLoveCount.setText("" + count);
                        if (mClick == 0) {
                            mListAllComment.get(position).put("applaud_num", "" + count);
                        } else {
                            mListHostComment.get(position).put("applaud_num", "" + count);
                        }
                    }
                }
            }

        }.execute();
    }

    /**
     * ???????????????
     *
     * @param this_id ??????id?????????id
     * @param type:1  ?????????2 ?????? ?????? theme_id ????????????id
     */
    public void removeZan(final String this_id, final String type, final String theme_id, final TextView tvIconLove,
                          final TextView tvLoveCount, final int position) {
        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, 0) {

            protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {

                return ComModel2.sweetRemoveZan(mContext, this_id, type, theme_id);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void onPostExecute(FragmentActivity mContext, ReturnInfo result, Exception e) {
                super.onPostExecute(mContext, result, e);
                if (null == e && result != null && "1".equals(result.getStatus())) {
                    if ("1".equals(type)) {// ??????
                        mLoveFlag = false;
                        mLoveCount--;
                        tvLoveCount.setText("" + mLoveCount);
                        tvIconLove.setBackgroundResource(R.drawable.sweet_icon_xihuan);
                        mapDetails.put("applaud_status", "0");
                        mapDetails.put("applaud_num", "" + mLoveCount);
                    } else if ("2".equals(type)) {
                        int count;
                        if (mClick == 0) {
                            count = Integer.parseInt((String) mListAllComment.get(position).get("applaud_num")) - 1;
                        } else {
                            count = Integer.parseInt((String) mListHostComment.get(position).get("applaud_num")) - 1;
                        }
                        if (mClick == 0) {
                            // ((List<String>)
                            // mListAllComment.get(position).get("applaud_user_list"))
                            // .remove("" +
                            // YCache.getCacheUser(mContext).getUser_id());
                            mListAllComment.get(position).put("comments_applaud_status", "0");
                        } else {
                            // ((List<String>)
                            // mListHostComment.get(position).get("applaud_user_list"))
                            // .remove("" +
                            // YCache.getCacheUser(mContext).getUser_id());
                            mListHostComment.get(position).put("comments_applaud_status", "0");
                        }
                        // outZanUserId.remove("" +
                        // YCache.getCacheUser(mContext).getUser_id());
                        tvIconLove.setBackgroundResource(R.drawable.sweet_icon_zan);
                        // zanFlag = false;
                        tvLoveCount.setText("" + count);
                        if (mClick == 0) {
                            mListAllComment.get(position).put("applaud_num", "" + count);
                        } else {
                            mListHostComment.get(position).put("applaud_num", "" + count);
                        }
                    }
                }
            }

        }.execute();
    }

    /**
     * @param theme_id     ??????id
     * @param base_user_id ??????id
     * @param content      ??????
     * @param location     ??????
     */
    public void commentTieZi(final String theme_id, final String base_user_id, final String content,
                             final String location) {
        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, 0) {

            protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {

                return ComModel2.sweetCommentTieZi(mContext, theme_id, base_user_id, content, location);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity mContext, ReturnInfo result, Exception e) {
                super.onPostExecute(mContext, result, e);
                isClick = false;
                if (null == e && result != null && "1".equals(result.getStatus())) {
                    ToastUtil.showLongText(mContext, "????????????");
                    mEtContent.setText("");
                    if (mClick == 0) {
                        page = 1;
                        // queryNewComment();
                    } else {
                        page2 = 1;
                        // queryHostCommentByShop();
                    }
                }
            }

        }.execute();
    }

    /**
     * @param comment_id      ??????id
     * @param receive_user_id ???????????????id?????????????????????????????????????????????
     * @param content         ??????
     */
    public void commentIn(final String comment_id, final String receive_user_id, final String content) {
        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, 0) {

            protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {

                return ComModel2.sweetCommentIn(mContext, comment_id, receive_user_id, content);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity mContext, ReturnInfo result, Exception e) {
                super.onPostExecute(mContext, result, e);
                isClick = false;
                if (null == e && result != null && "1".equals(result.getStatus())) {
                    ToastUtil.showLongText(mContext, "????????????");
                    mEtContent.setText("");
                    if (mClick == 0) {
                        if (!isHotComment) {
                            page = 1;
                            // queryNewComment();
                        } else {
                            // queryHotComment(false);
                        }
                    } else {
                        page2 = 1;
                        // queryHostCommentByShop();
                    }
                }
            }

        }.execute();
    }

    /**
     * ???????????????????????????
     *
     * @param friend_user_id ???????????????id
     * @param type           1????????????2????????? ?????????????????????
     */
    public void addOrDelAttention(final String friend_user_id, final String type, final TextView mAttention) {
        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, 0) {

            protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {

                return ComModel2.sweetAttention(mContext, friend_user_id, type);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity mContext, ReturnInfo result, Exception e) {
                super.onPostExecute(mContext, result, e);
                if (null == e && result != null && "1".equals(result.getStatus())) {
                    if ("2".equals(type)) {// ????????????
                        isAttentionFlag = false;
                        mAttention.setBackgroundResource(R.drawable.icon_add_friends);
                        // mAttention.setText("+????????????");
                        // mAttention.setTextColor(Color.parseColor("#ff3f8b"));
                    } else {
                        isAttentionFlag = true;
                        mAttention.setBackgroundResource(R.drawable.icon_attention);
                        // mAttention.setText("?????????");
                        // mAttention.setTextColor(Color.parseColor("#c5c5c5"));
                    }
                }
            }

        }.execute();
    }

    /**
     * ??????????????????
     * <p>
     * content ??????
     * <p>
     * theme_id ??????id
     */
    public void sweetReportTieZi(final String content, final String theme_id) {
        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, 0) {

            protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {

                return ComModel2.sweetReport(mContext, content, theme_id);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity mContext, ReturnInfo result, Exception e) {
                super.onPostExecute(mContext, result, e);
                if (null == e) {
                    if ("1".equals(result.getStatus())) {
                        ToastUtil.showShortText(mContext, "????????????");
                    }
                }
            }

        }.execute();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!"".equals(mEtContent.getText().toString().trim())) {
            mSendComment.setBackgroundResource(R.drawable.indiana_shape_shaidan);
        } else {
            mSendComment.setBackgroundResource(R.drawable.sweet_shape_send);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight,
                               int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > screenHeight / 4)) {
            // Toast.makeText(mContext, "????????????????????????...",
            // Toast.LENGTH_SHORT).show();

        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > screenHeight / 4)) {
            if ("".equals(mEtContent.getText().toString().trim())) {
                mEtContent.setHint("????????????????????????~");
            }
            // Toast.makeText(mContext, "????????????????????????...",
            // Toast.LENGTH_SHORT).show();
        }
        // if (mEtContent.getHeight() - mHightEtComment > 100) {
        // Toast.makeText(mContext, "????????????????????????...", Toast.LENGTH_SHORT).show();
        // } else {
        // Toast.makeText(mContext, "????????????????????????...", Toast.LENGTH_SHORT).show();
        // }
    }

    /**
     * ?????????
     */
    private void toLogin() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra("login_register", "login");
        intent.putExtra("sweet_friend", "sweet_friend");
        ((FragmentActivity) mContext).startActivity(intent);
        ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
    }

    private String singvalue;// ??????????????????
    private int forcelookTopicNum;// ?????????????????????
    private int forcelookLimitTopicNum;// ???????????????????????? ?????????????????????;
    private SimpleDateFormat df;

    /**
     * ????????????????????????
     */
    private void scollScanSign() {

        mListView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(PLA_AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(PLA_AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Point p = new Point();
                getWindowManager().getDefaultDisplay().getSize(p);
                int screenWidth = p.x;
                int screenHeight = p.y;
                Rect rect = new Rect(0, 0, screenWidth, screenHeight);
                int[] location = new int[2];
                share_ll.getLocationInWindow(location);
                LogYiFu.e("locations", location[1] + "");
                if (share_ll.getLocalVisibleRect(rect) && isFirstCome) {
                    // ???????????????????????????
                    isFirstCome = false;

                    scoll_xunbao.setVisibility(View.GONE);//??????????????????????????????

                    df = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    singvalue = SignListAdapter.doValueCD;
                    int singvalueInt = 1;
                    try {
                        singvalueInt = Integer.parseInt(singvalue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    final int singvalueIntFinal = singvalueInt;
                    if (isForceLook) {
                        // ???????????????
                        String nowTimeForcelookTopic = SharedPreferencesUtil.getStringData(mContext,
                                "forcelookTopicNowTime" + YCache.getCacheUser(mContext).getUser_id(), "");
                        forcelookTopicNum = Integer
                                .parseInt(SharedPreferencesUtil.getStringData(mContext, SignListAdapter.signIndex
                                        + "forcelookTopicNum" + YCache.getCacheUser(mContext).getUser_id(), "0"));
                        if (!df.format(new Date()).equals(nowTimeForcelookTopic)) {
                            forcelookTopicNum = 0;// ?????????????????????????????????
                            // ??????????????????????????? ???
                            // ???????????????????????????????????????
                            // ?????????????????????????????????
                        }
                        forcelookTopicNum++;
                        if (SignListAdapter.doNumCD > 1) {// ???????????????????????????
                            sign(singvalueIntFinal);
                        } else {
                            SharedPreferencesUtil.saveStringData(mContext,
                                    "forcelookTopicNowTime" + YCache.getCacheUser(mContext).getUser_id(),
                                    df.format(new Date()));
                            SharedPreferencesUtil
                                    .saveStringData(mContext,
                                            SignListAdapter.signIndex + "forcelookTopicNum"
                                                    + YCache.getCacheUser(mContext).getUser_id(),
                                            "" + forcelookTopicNum);
                            if (forcelookTopicNum < singvalueIntFinal) {
                                ToastUtil.showLongText(mContext,
                                        "?????????" + (singvalueIntFinal - forcelookTopicNum) + "?????????????????????~");
                            } else if (forcelookTopicNum >= singvalueIntFinal) {
                                sign(singvalueIntFinal);
                            }
                        }

                    } else if (isForceLookLimit) {
                        String nowTimeForcelookLimitTopic = SharedPreferencesUtil.getStringData(
                                mContext,
                                "nowTimeForcelookLimitTopic" + YCache.getCacheUser(mContext).getUser_id(), "");
                        forcelookLimitTopicNum = Integer.parseInt(SharedPreferencesUtil.getStringData(mContext, SignListAdapter.signIndex
                                + YConstance.Pref.ISFORCELOOKLIMITNUM_TOPIC
                                + YCache.getCacheUser(mContext).getUser_id(), "0"));
                        if (!df.format(new Date()).equals(nowTimeForcelookLimitTopic)) {
                            forcelookLimitTopicNum = 0;// ?????????????????????????????????
                            // ??????????????????????????? ???
                            // ???????????????????????????????????????
                            // ?????????????????????????????????
                        }

                        SharedPreferencesUtil.saveStringData(mContext,
                                "nowTimeForcelookLimitTopic" + YCache.getCacheUser(mContext).getUser_id(),
                                df.format(new Date()));
                        if (forcelookLimitTopicNum / singvalueIntFinal + 1 > SignListAdapter.doNumCD
                                || SignListAdapter.isSignComplete) {
                            //?????? ???????????? ????????????
                            NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(mContext,
                                    R.style.DialogQuheijiao2, YConstance.Pref.LIULAN_SIGN_UPPER_LIMIT);
                            dialog.show();
                            forcelookLimitTopicNum++;
                            SharedPreferencesUtil
                                    .saveStringData(mContext,
                                            SignListAdapter.signIndex + YConstance.Pref.ISFORCELOOKLIMITNUM_TOPIC
                                                    + YCache.getCacheUser(mContext).getUser_id(),
                                            "" + forcelookLimitTopicNum);

                        } else {
                            //???????????????????????????????????????????????? ???0????????? forcelookLimitTopicNum = 0
                            if (forcelookLimitTopicNum % singvalueIntFinal + 1 < singvalueIntFinal) {
                                ToastUtil.showMyToast(mContext,
                                        "?????????" + (singvalueIntFinal - (forcelookLimitTopicNum % singvalueIntFinal + 1)) + "???????????????"
                                                + SignListAdapter.jiangliValueCD
                                                + "???????????????,????????????~", 3000);
                                forcelookLimitTopicNum++;
                                SharedPreferencesUtil
                                        .saveStringData(mContext,
                                                SignListAdapter.signIndex + YConstance.Pref.ISFORCELOOKLIMITNUM_TOPIC
                                                        + YCache.getCacheUser(mContext).getUser_id(),
                                                "" + forcelookLimitTopicNum);

                            } else if (forcelookLimitTopicNum % singvalueIntFinal + 1 == singvalueIntFinal) {
                                signLimit(singvalueIntFinal);
                            }

                        }
                    }

                }
            }
        });
    }

    private void sign(final int singvalueIntFinal) {
        String ssType = "";
        switch (SignListAdapter.jiangliIDCD) {
            case 3:
                ssType = "????????????";
                break;
            case 4:
                ssType = "??????";
                break;
            case 5:
                ssType = "???";
                break;
            case 11:// ?????? ????????????
                ssType = "?????????";
                break;

            default:
                break;
        }
        final String ss = ssType;
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) mContext, 0) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                String intdex = SharedPreferencesUtil.getStringData(context, YConstance.LIULANCHUANDAINDEX, "");
                return ComModel2.getSignIn(mContext, false, false, intdex, SignListAdapter.doClass);
            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null) {


                    if (Integer.valueOf(result.get("isNewbie01") + "") == 1) {
                        SignCompleteDialogUtil.firstClickInGoToZP(mContext);
                        return;
                    }


                    SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");

                    if (SignListAdapter.doNumCD > 1) {// ???????????????????????????

                        SharedPreferencesUtil.saveStringData(mContext,
                                "forcelookTopicNowTime" + YCache.getCacheUser(mContext).getUser_id(),
                                df.format(new Date()));
                        SharedPreferencesUtil.saveStringData(mContext, SignListAdapter.signIndex + "forcelookTopicNum"
                                + YCache.getCacheUser(context).getUser_id(), "" + forcelookTopicNum);

                        if (forcelookTopicNum < singvalueIntFinal) {// ???????????????????????????
                            ToastUtil.showLongText(mContext, "?????????????????????" + SignListAdapter.jiangliValueCD + ss + ",??????"
                                    + (singvalueIntFinal - forcelookTopicNum) + "??????????????????~");
                        } else if (forcelookTopicNum >= singvalueIntFinal) {
                            NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao2,
                                    "liulan_sign_chuanda_finish",
                                    new java.text.DecimalFormat("0.##").format(
                                            Double.parseDouble(SignListAdapter.jiangliValueCD) * SignListAdapter.doNumCD)
                                            + ss);
                            dialog.show();

                        }
                    } else {
                        // ???????????? ???????????????
                        NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao2,
                                "liulan_sign_chuanda_finish", SignListAdapter.jiangliValueCD + ss);
                        dialog.show();
                    }
                }
            }

        }.execute();

    }

    /**
     * ?????????????????? ???????????????
     */
    private void signLimit(final int singvalueIntFinal) {

        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) mContext, 0) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel2.getSignIn(mContext, false, false, SignListAdapter.signIndex,
                        SignListAdapter.doClass);

            }

            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null) {
                    if (isForceLookLimit) {

                        if (Integer.valueOf(result.get("isNewbie01") + "") == 1) {
                            SignCompleteDialogUtil.firstClickInGoToZP(mContext);
                            return;
                        }


                        SharedPreferencesUtil.saveStringData(mContext,
                                "nowTimeForcelookLimitTopic" + YCache.getCacheUser(context).getUser_id(),
                                df.format(new Date()));


                        String ts = SignListAdapter.jiangliValueCD + "?????????????????????????????????????????????3-5????????????,???????????????????????????"
                                + (singvalueIntFinal + "???????????????" + SignListAdapter.jiangliValueCD + "???????????????"
                                + ",????????????!");

                        ToastUtil.showMyToast(mContext, ts, 6000);


//						ToastUtil.showMyToast(mContext,
//								SignListAdapter.jiangliValueCD +"???????????????????????????????????????????????????"
//										+ (singvalueIntFinal + "???????????????" + SignListAdapter.jiangliValueCD
//										+ "???????????????,????????????~"), 6000);


                        forcelookLimitTopicNum++;
                        SharedPreferencesUtil
                                .saveStringData(mContext,
                                        SignListAdapter.signIndex + YConstance.Pref.ISFORCELOOKLIMITNUM_TOPIC
                                                + YCache.getCacheUser(context).getUser_id(),
                                        "" + forcelookLimitTopicNum);
                    }

                }
            }

        }.execute();
    }

    @Override
    public void onRefresh() {
        curPager = 1;
        mListView.stopRefresh();

        // TODO Auto-generated method stub
        // curPager = 1;
        // la = 1;
        // getIntimateList();
    }

    @Override
    public void onLoadMore() {
        // TODO Auto-generated method stub
        la = 2;
        curPager++;
        initXGTJ(null, false);

    }
}
