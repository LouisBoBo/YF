package com.yssj.ui.activity.shopdetails;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.activity.wxapi.WXEntryActivity;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.ItemView;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.MealRecomenView;
import com.yssj.custom.view.MyPopupwindow;
import com.yssj.custom.view.MyPopupwindow.ShopDetailsGetShare;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.custom.view.ShopTopClickView;
import com.yssj.custom.view.ShopTopClickView.OnCheckedLintener;
import com.yssj.custom.view.ShowHoriontalView;
import com.yssj.custom.view.ShowHoriontalView.onClickLintener;
import com.yssj.custom.view.SizeView;
import com.yssj.custom.view.SizeView2;
import com.yssj.data.YDBHelper;
import com.yssj.entity.BaseData;
import com.yssj.entity.Choujiang20Data;
import com.yssj.entity.GoodTuanData;
import com.yssj.entity.GoodsEntity;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ShareShop;
import com.yssj.entity.Shop;
import com.yssj.entity.ShopCart;
import com.yssj.entity.ShopComment;
import com.yssj.entity.ShopOption;
import com.yssj.entity.StockType;
import com.yssj.entity.Store;
import com.yssj.entity.UserInfo;
import com.yssj.entity.VipDikouData;
import com.yssj.entity.VipInfo;
import com.yssj.huanxin.PublicUtil;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelZ;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.photoView.ImagePagerActivity;
import com.yssj.ui.HomeWatcherReceiver;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.OneBuyGroupsDetailsActivity;
import com.yssj.ui.activity.ShopCartNewNewActivity;
import com.yssj.ui.activity.ShopImageActivity;
import com.yssj.ui.activity.SignDrawalLimitActivity;
import com.yssj.ui.activity.classfication.ManufactureActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.main.SignGroupShopActivity;
import com.yssj.ui.activity.view.CircleProgressView;
import com.yssj.ui.activity.vip.MyVipListActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.LingYUANGOUTishiDialog;
import com.yssj.ui.dialog.NewSignCommonDiaolg;
import com.yssj.ui.dialog.PublicToastDialog;
import com.yssj.ui.dialog.XunBaoScollDialog;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.ui.receiver.TaskReceiver;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.ComputeUtil;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.DialogUtils;
import com.yssj.utils.GlideUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.SignCompleteDialogUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.TongjiShareCount;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.WXminiAppUtil;
import com.yssj.utils.YCache;
import com.yssj.utils.YunYingTongJi;
import com.yssj.utils.sqlite.ShopCartDao;

import org.apache.commons.lang.time.DateFormatUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


/***
 * 普通商品展示
 *
 * @author Administrator
 *
 */
public class ShopDetailsActivity extends BasicActivity
        implements OnClickListener, onClickLintener, OnCheckedLintener, ShopDetailsGetShare, LingYUANGOUTishiDialog.ShopDetialClickCallBack {


    public static String submitOrderDiKou = "0.0";


    //    @Bind(R.id.ll_pt_all_header_img)
    LinearLayout llPtAllHeaderImg;
    //    @Bind(R.id.ll_pt_info)
    RelativeLayout llPtInfo;
    //    @Bind(R.id.tv_pt_need_pop_count)
    TextView tvPtNeedPopCount;
    //    @Bind(R.id.tv_pt_time)
    TextView tvPtTime;
    //    @Bind(R.id.tv_pt_new_user)
    TextView tvPtNewUser;


    private boolean fromShouye3; //是否来自首页3
    private VipDikouData mVipDikouData;
    private int freeOrderPage;
    /**
     * freeBuyType：
     * 0，用户自己去商品详情免费领
     * 1，免费领任务
     * 2，购买会员成功后的免费领
     */
    private int freeBuyType;

    @Override
    public void goumaiClick() {
        queryShopQueryAttr(0);
    }

    private Double f_xiang_mongey;

    private TextView shouTVprice;//实际售价


    public static boolean isNewMeal;//是否新特卖商品

    private boolean first_come = true; // 防止来回滑动计算次数
    public static PublicToastDialog shareWaitDialog = null;
    private Timer timer_seven;

    private long recLen;
    private long recLen_meal;

    //	private TimerTask task;
    private TimerTask task_seven;

    private int countCommn;
    private int countMeal;

    private List<ShopOption> tuijianList;
    private String number_sold;
    private RelativeLayout rl_hava_twenty;
    private boolean shopCart;
    private boolean isforcelook;
    private boolean isforcelookMatch;
    private boolean isForceLookLimit;//是否是 浏览次数奖励提现额度
    private boolean isSignActiveShop;// 签到活动商品（仅判断是否是活动商品）
    private boolean isSignActiveShopScan;// 签到活动商品 并且是浏览任务

    private SimpleDateFormat df;
    private int virtual_sales;
    private TextView tv_shop_car_fake;

    private int width, height, heights;
    private Shop shop;
    private TextView tv_shop_car;
    private TextView sign_buy;
    private LinearLayout mSingleBuy, mTwoBuy, mGroupBuy;
    private TextView mSinglePrice, mTwoPrice, mGroupPrice, mTvPeopleCount;
    private LinearLayout mLlActivity;// 活动列表跳过来的展示
    private ImageView /* img_fenx, */  img_addxin;
    private RelativeLayout img_fenx;
    private ImageView img_fenx_old;

    public static int setEva_count_z;
    public static String MealType;
    private LinearLayout img_back;
    private String mImageRadio = "450";


    private TextView header_tv_diKou;
    private ImageView header_iv_wenhao;
    private TextView header_tv_sjprice;
    private TextView header_tv_price, tv_active_discount, tv_yanjia_first_diamond_text, tv_yanjia_first_diamond_price;


    private LinearLayout lin_add_like;
    private RelativeLayout img_cart, img_cart2;
    private ImageView img_cart_old;

    private ImageView lin_contact;
    private RelativeLayout lin_contact_old;

    private SAsyncTask<String, Void, HashMap<String, Object>> aa;
    private SAsyncTask<Void, Void, HashMap<String, Object>> bb;
    private SAsyncTask<Void, Void, HashMap<String, Object>> cc;

    private SAsyncTask<String, Void, ShareShop> pp;


    private LinkedList<HashMap<String, Object>> dataList; //底部拼接的商品列表

    private String[] images;// 普通商品图片

    private String[] imageTag1, imageTag2, imageTag3;// 套餐图片

    private List<HashMap<String, String>> listTitle;

    private int check = 0;
    private RelativeLayout rlBottom;
    private LinearLayout rlTop;

    private MyPopupwindow myPopupwindow;


    private TextView tv_cart_count, tv_cart_count2;
    private TextView tv_time_count_down, tv_time_count_down2;
    private TextView tv_time_count_down_meal;

    private RelativeLayout rl_retain;

    private Context context;

    private StickyListHeadersListView mListView;//整个详情


    private MyAdapter adapter;

    private TaskReceiver oneBuyReceiver;


    private View headerView; //商品详情上部
    private String titleId;// 筛选类别的id
    public static ShopDetailsActivity instance;
    private ImageButton mShuaixuanNew;
    private String signShopDetail;// 判断是值为 "SignShopDetail" 从签到跳转到商品详情页面
    private String sweet_theme_id;// 如果是从帖子详情跳过来的就有此参数
    private int signType;// 代表 几元包邮
    private String signValue;// 签到 商品编号
    private String valueDuo;// 几元夺宝
    private ImageView toDuoBaoIv; // 去夺宝的悬浮按钮
    private ImageView xunBaoIv;

    private LinearLayout redShare;
    private ImageView moneyShare;
    private LinearLayout mNomarBottom, mActivityBottom;
    private RelativeLayout mRlAddShopCart;
    private boolean mIsGroup = false;// 用来判断是否是从拼团广场跳过来的
    private boolean mIsTwoGroup = false;// true时代表发起了2人拼团购买，为了给购买成功后的弹窗用
    private boolean isHot;// 是购物首页热卖的商品
    private String rollCode = "0";
    private String r_code = "";
    private int groupFlag = 0;

    private static int isPause = 0;
    private boolean group_click_flag = false;


    private Intent qqShareIntent /*
     * = ShareUtil.shareMultiplePictureToQZone(
     * ShareUtil.getImage())
     */;
    private Intent wXinShareIntent /*
     * =
     * ShareUtil.shareMultiplePictureToTimeLine(
     * ShareUtil.getImage())
     */;

    private Runnable r, shareRun;
    private String mSupp_label = "";// 供应商名字


    public static VipInfo mVipInfo;

    //购买按钮
    private void setBuyBtn() {
        if (!YJApplication.instance.isLoginSucess()) {
            setBuyBtnTextAndClick();
            return;
        }
        HashMap<String, String> pairsMap = new HashMap<>();
        pairsMap.put("shop_code", code);
        pairsMap.put("t", "1");
        if (fromShouye3) { //免费领任务进来的
            pairsMap.put("page3", "1");
        } else {
            pairsMap.put("page3", "0");
        }


        //查询会员情况
//        YConn.httpPost(instance, YUrl.QUERY_VIP_INFO_FL, pairsMap
//                , new HttpListener<VipInfo>() {
//                    @Override
//                    public void onSuccess(VipInfo vipInfo) {
//                        mVipInfo = vipInfo;
//                        setBuyBtnTextAndClick();
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });

        setBuyBtnTextAndClick();
    }


    @Bind(R.id.btn_buy_left_ll)
    LinearLayout btnBuyLeftLl;//左全部


    @Bind(R.id.btn_buy_left_text_ll)
    LinearLayout btnBuyLeftTextLl;//左全部


    @Bind(R.id.btn_buy_left_top_text)
    TextView btnBuyLeftTopText;   //左上文字
    @Bind(R.id.btn_buy_left_bottom_text)
    TextView btnBuyLeftBottomText; //左下文字

    @Bind(R.id.btn_buy_right_ll)
    LinearLayout btnBuyRightLl;//右全部

    @Bind(R.id.btn_buy_right_text_ll)
    LinearLayout btnBuyRightTextLl;


    @Bind(R.id.btn_buy_right_top_text)
    TextView btnBuyRightTopText; //右上文字
    @Bind(R.id.btn_buy_right_bottom_text)
    TextView btnBuyRightBottomText; //右下文字

    @Bind(R.id.tv_left_rmb)
    TextView tvLeftRmb;
    @Bind(R.id.tv_right_rmb)
    TextView tvRightRmb;


    private double vipPayOnlyBuyPrice;


    private String youhuiDikou = "0.0";

    public static double submitOrderShowShopPrice;


    public static boolean showVipPage;

    public boolean fromKTtask = false;


    private void setBuyBtnTextAndClick() {

        //按钮展示
        btnBuyLeftTopText.setText("");
        btnBuyLeftBottomText.setText("");
        btnBuyRightTopText.setText("0.0");
        btnBuyRightBottomText.setText("立即购买");
        tvLeftRmb.setText("");
        tvRightRmb.setText("");
        btnBuyLeftLl.setBackgroundResource(R.color.shopdetial_buy_btn_left);
        btnBuyRightLl.setBackgroundResource(R.color.shopdetial_buy_btn_right);


        //测试
//        mVipInfo.setVip_free(1);
//        mVipInfo.setVip_page(1);
//        mVipInfo.setIsVip(0);

        //vip_free    1免费领（新用户免费领 或者会员首单（直接去抽奖））   0不是   2有预存中奖次数(提前买了会员并且价格区间是对的，下单后进订单详情)
        //vip_page    0普通用户界面  1会员界面
        //free_page   1下单后去订单详情  没有其他情况

        //vip_free          不是1的时候分为两种情况 ：是会员的话就是去分享免费领，不是会员的话就是新用户免费领
        /**
         * vip_free :分为是会员和不是会员两种情况
         *
         *
         * 是会员：1：可以直接去抽奖
         *        0：其他都是需要分享免费领的
         *
         *
         *
         *
         * 不是会员（包括未登录）：1：新用户免费领
         *          0：新用户免费领已经使用完毕
         *
         *
         *
         */


        //底部按钮分为会员界面和非会员界面（包含未登录）
        if (null == mVipInfo) {
            mVipInfo = new VipInfo();
        }
        if (!YJApplication.instance.isLoginSucess()) {
            showVipPage = false;
        } else {
            if (mVipInfo.getIsVip() == 1) {
                showVipPage = true;
            }
        }


        /**
         * showBuyBtnStyle
         *
         * 1，未登录进来的
         *
         * 2，登录后自己进详情的（非会员）
         *
         * 3，登录后自己进详情的（会员）
         *
         * 4，无
         *
         * 5，开团任务进来的
         *
         * 6 从赚钱免费领任务点进首页3后进入详情
         *
         * 7 渠道审核员只有单独购买
         */
        int showBuyBtnStyle = 7;
//        if (!YJApplication.instance.isLoginSucess()) {
//            showBuyBtnStyle = 1;
//        } else {
//
//            if(YCache.getCacheUser(instance).getReviewers() ==1){
//                showBuyBtnStyle = 7;
//            }else{
//                if (fromShouye3) {
//                    showBuyBtnStyle = 6;
//                } else if (fromKTtask) {
//                    showBuyBtnStyle = 5;
//                } else {
//                    if (CommonUtils.isVip(mVipInfo.getIsVip(), mVipDikouData.getMaxType())) {
//                        showBuyBtnStyle = 3;
//                    } else {
//                        showBuyBtnStyle = 2;
//                    }
//                }
//
//            }
//
//        }

        String groupPriceStr = shop.getAssmble_price();
        double groupPrice = 0.0;
        try {
            groupPrice = Double.parseDouble(groupPriceStr);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //测试
//        mVipInfo.setVip_free(1);
        showBuyBtnStyle = 7;
        switch (showBuyBtnStyle) {
            case 1://未登录进来的
                //展示
                btnBuyLeftTopText.setText("" + new DecimalFormat("#0.0").format(groupPrice));
                //左边是开团
                btnBuyLeftBottomText.setText("一键开团");
                btnBuyLeftTopText.setTextColor(Color.parseColor("#ffffff"));
                btnBuyLeftBottomText.setTextColor(Color.parseColor("#ffffff"));
                tvLeftRmb.setText("¥");

                //点击
                btnBuyRightTextLl.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(instance, LoginActivity.class));

                    }
                });

                btnBuyLeftLl.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(instance, LoginActivity.class));

                    }
                });

                tvLeftRmb.setVisibility(View.VISIBLE);
                tvRightRmb.setVisibility(View.GONE);

                btnBuyRightTextLl.setVisibility(View.VISIBLE);
                btnBuyRightLl.setVisibility(View.GONE);

                btnBuyLeftTextLl.setVisibility(View.GONE);
                btnBuyLeftLl.setVisibility(View.VISIBLE);

                break;
            case 2://登录后自己进详情的（非会员）

                //展示
                btnBuyLeftTopText.setText("" + new DecimalFormat("#0.0").format(groupPrice));
                btnBuyLeftBottomText.setText("一键开团");
                btnBuyLeftTopText.setTextColor(Color.parseColor("#ffffff"));
                btnBuyLeftBottomText.setTextColor(Color.parseColor("#ffffff"));
                tvLeftRmb.setText("¥");


                //点击
                btnBuyLeftLl.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {  //开团

                        //开团用的是1元购
                        queryShopQueryAttrOneBuy();
                    }
                });

                btnBuyRightTextLl.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) { //就是新用户免费领





                            final Intent txcjIntent = new Intent(ShopDetailsActivity.instance, SignDrawalLimitActivity.class);
                            txcjIntent.putExtra("type", 1)
                                    .putExtra("fromFreeBuy", true);
                            HashMap<String, String> map = new HashMap<>();
                            YConn.httpPost(context, YUrl.QUERY_TIQIAN_TXCJ, map, new HttpListener<Choujiang20Data>() {
                                @Override
                                public void onSuccess(Choujiang20Data result) {
                                    if ("1".equals(result.getStatus())) {
                                        if (result.getData().getIs_finish() == 1 && !fromShouye3) {
                                            if (null != SignDrawalLimitActivity.instance) {
                                                SignDrawalLimitActivity.instance.finish();
                                            }
                                            startActivity(txcjIntent);

                                        } else {
                                            if (FreeBuyShareDialog.needShareCount == 0 || freeOrderPage > 0) {//买了半张钻石卡也不用分享
                                                freeBuyClick();
                                                return;
                                            }
                                            FreeBuyShareDialog dialog = new FreeBuyShareDialog(instance);
                                            dialog.show();
                                            dialog.freeBuyShareFinishListener = new FreeBuyShareDialog.FreeBuyShareFinishListener() {
                                                @Override
                                                public void shareSuccess() {
                                                    freeBuyClick();

                                                }
                                            };
                                        }
                                    }
                                }

                                @Override
                                public void onError() {

                                }
                            });

                    }
                });

                tvLeftRmb.setVisibility(View.VISIBLE);
                tvRightRmb.setVisibility(View.GONE);

                btnBuyRightTextLl.setVisibility(View.VISIBLE);
                btnBuyRightLl.setVisibility(View.GONE);

                btnBuyLeftTextLl.setVisibility(View.GONE);
                btnBuyLeftLl.setVisibility(View.VISIBLE);

                break;
            case 3://登录后自己进详情的（会员）  （左边是免费领右边是单独购买）


                btnBuyRightTopText.setText("" + new DecimalFormat("#0.0").format(vipPayOnlyBuyPrice));
//                btnBuyRightBottomText.setText("赚¥" + (ComputeUtil.div(shop.getShop_se_price(), 2, 1) > 50.0 ? 50.0 : ComputeUtil.div(shop.getShop_se_price(), 2, 1)));

                btnBuyRightBottomText.setText("返¥" + new DecimalFormat("#0.0").format(shop.getShop_se_price() * 0.15));

                tvRightRmb.setText("¥");


                //点击
                btnBuyLeftTextLl.setOnClickListener(new OnClickListener() {//免费领
                    @Override
                    public void onClick(View view) {
                        if (FreeBuyShareDialog.needShareCount == 0) {
                            freeBuyClick();
                            return;

                        }

                        //每日只引导一次
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String vipToVipTime = SharedPreferencesUtil.getStringData(context, Pref.SHOPDETAIL_VIP_YINDAO_VIP, "0");
                        long vipToVipTime2 = Long.valueOf(vipToVipTime);
                        if ("0".equals(vipToVipTime) || !df.format(new Date()).equals(df.format(new Date(vipToVipTime2)))) {
                            if (mVipInfo.getMaxType() == 4 && mVipInfo.getDiamondCount() == 1) {//引导皇冠卡

                                instance.startActivity(new Intent(instance, MyVipListActivity.class)
                                        .putExtra("guide_vipType", 5)
                                );
                                SharedPreferencesUtil.saveStringData(context, Pref.SHOPDETAIL_VIP_YINDAO_VIP, System.currentTimeMillis() + "");

                                return;
                            }
                            if (mVipInfo.getMaxType() == 5 && mVipInfo.getCrownCount() == 1) {//引导钻石卡
                                instance.startActivity(new Intent(instance, MyVipListActivity.class)
                                        .putExtra("guide_vipType", 4)
                                );
                                SharedPreferencesUtil.saveStringData(context, Pref.SHOPDETAIL_VIP_YINDAO_VIP, System.currentTimeMillis() + "");

                                return;
                            }
                        }


                        final Intent txcjIntent = new Intent(ShopDetailsActivity.instance, SignDrawalLimitActivity.class);
                        txcjIntent.putExtra("type", 1)
                                .putExtra("fromFreeBuy", true);
                        HashMap<String, String> map = new HashMap<>();
                        YConn.httpPost(context, YUrl.QUERY_TIQIAN_TXCJ, map, new HttpListener<Choujiang20Data>() {
                            @Override
                            public void onSuccess(Choujiang20Data result) {
                                if ("1".equals(result.getStatus())) {
                                    if (result.getData().getIs_finish() == 1 && !fromShouye3) {
                                        if (null != SignDrawalLimitActivity.instance) {
                                            SignDrawalLimitActivity.instance.finish();
                                        }
                                        startActivity(txcjIntent);

                                    } else {
                                        if (FreeBuyShareDialog.needShareCount == 0 || freeOrderPage > 0) {//买了半张钻石卡也不用分享
                                            freeBuyClick();
                                            return;
                                        }


                                        FreeBuyShareDialog dialog = new FreeBuyShareDialog(instance);
                                        dialog.show();
                                        dialog.freeBuyShareFinishListener = new FreeBuyShareDialog.FreeBuyShareFinishListener() {
                                            @Override
                                            public void shareSuccess() {
                                                freeBuyClick();

                                            }
                                        };
                                    }
                                }
                            }

                            @Override
                            public void onError() {

                            }
                        });


                    }
                });

                btnBuyRightLl.setOnClickListener(new OnClickListener() {//单独购买
                    @Override
                    public void onClick(View view) {
                        onlyBuyClick();
                    }
                });
                tvRightRmb.setVisibility(View.VISIBLE);
                tvLeftRmb.setVisibility(View.GONE);

                btnBuyRightTextLl.setVisibility(View.GONE);
                btnBuyRightLl.setVisibility(View.VISIBLE);

                btnBuyLeftTextLl.setVisibility(View.VISIBLE);
                btnBuyLeftLl.setVisibility(View.GONE);

                break;
            case 5://开团任务进来的


                btnBuyRightTopText.setText("" + new DecimalFormat("#0.0").format(groupPrice));
                btnBuyRightBottomText.setText("一键开团");
                tvRightRmb.setText("¥");

                btnBuyRightLl.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        ToastUtil.showShortText2("去开团");

                        //开团用的是1元购
                        queryShopQueryAttrOneBuy();
                    }
                });

                tvRightRmb.setVisibility(View.VISIBLE);
                tvLeftRmb.setVisibility(View.GONE);

                btnBuyRightTextLl.setVisibility(View.GONE);
                btnBuyRightLl.setVisibility(View.VISIBLE);

                btnBuyLeftTextLl.setVisibility(View.GONE);
                btnBuyLeftLl.setVisibility(View.GONE);

                break;
            case 6://从赚钱免费领任务点进首页3后进入详情

                btnBuyRightTextLl.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        freeBuyClick();
                    }
                });
                tvLeftRmb.setVisibility(View.GONE);
                tvRightRmb.setVisibility(View.GONE);

                btnBuyRightTextLl.setVisibility(View.VISIBLE);
                btnBuyRightLl.setVisibility(View.GONE);

                btnBuyLeftTextLl.setVisibility(View.GONE);
                btnBuyLeftLl.setVisibility(View.GONE);

                break;

            case 7://渠道审核员只有单独购买


                btnBuyRightTopText.setText("" + new DecimalFormat("#0.0").format(shop.getShop_se_price()));
                btnBuyRightBottomText.setText("立即购买");
//                btnBuyRightBottomText.setText("赚¥" + (ComputeUtil.div(shop.getShop_se_price(), 2, 1) > 50.0 ? 50.0 : ComputeUtil.div(shop.getShop_se_price(), 2, 1)));

//                  btnBuyRightBottomText.setText("返¥" + new DecimalFormat("#0.0").format(shop.getShop_se_price() * 0.15));
                btnBuyRightBottomText.setVisibility(View.VISIBLE);
                tvRightRmb.setText("¥");

                btnBuyRightLl.setOnClickListener(new OnClickListener() {//单独购买
                    @Override
                    public void onClick(View view) {
                        onlyBuyClick();
                    }
                });
                tvRightRmb.setVisibility(View.VISIBLE);
                tvLeftRmb.setVisibility(View.GONE);

                btnBuyRightTextLl.setVisibility(View.GONE);
                btnBuyRightLl.setVisibility(View.VISIBLE);

                btnBuyLeftTextLl.setVisibility(View.GONE);
                btnBuyLeftLl.setVisibility(View.GONE);






                break;
        }
    }

    //单独购买点击
    private void onlyBuyClick() {
        clickFlag = false;
        if (shop != null) {
            List<StockType> list = shop.getList_stock_type();
            if (list != null && list.size() > 0) {
                showOnlyBuyPopu();// 商品属性选择
            } else {
                new SAsyncTask<String, Void, Shop>(this, null, R.string.wait) {
                    @Override
                    protected Shop doInBackground(FragmentActivity context, String... params) throws Exception {
                        return ComModel.queryShopQueryAttr(ShopDetailsActivity.this, shop, params[0]);
                    }

                    @Override
                    protected void onPostExecute(FragmentActivity context, Shop shop, Exception e) {

                        if (e != null) {// 查询异常
                            Toast.makeText(ShopDetailsActivity.this, "连接超时，请重试", Toast.LENGTH_LONG).show();
                        } else {// 查询商品详情成功，刷新界面
                            if (shop != null) {
                                ShopDetailsActivity.this.shop = shop;
                                showOnlyBuyPopu();// 商品属性选择 （单独购买）
                            }
                        }

                    }


                    @Override
                    protected boolean isHandleException() {
                        return true;
                    }

                }.execute("false");
            }

        } else {
            ToastUtil.showShortText(context, "无效操作");
        }
    }

    //免费领点击
    private void freeBuyClick() {

        clickFlag = false;
        if (shop != null) {
            List<StockType> list = shop.getList_stock_type();
            if (list != null && list.size() > 0) {
                showFreeBuyPopu();// 商品属性选择
            } else {
                new SAsyncTask<String, Void, Shop>(this, null, R.string.wait) {
                    @Override
                    protected Shop doInBackground(FragmentActivity context, String... params) throws Exception {
                        return ComModel.queryShopQueryAttr(ShopDetailsActivity.this, shop, params[0]);
                    }

                    @Override
                    protected void onPostExecute(FragmentActivity context, Shop shop, Exception e) {

                        if (e != null) {// 查询异常
                            Toast.makeText(ShopDetailsActivity.this, "连接超时，请重试", Toast.LENGTH_LONG).show();
                        } else {// 查询商品详情成功，刷新界面
                            if (shop != null) {
                                ShopDetailsActivity.this.shop = shop;
                                showFreeBuyPopu();// 商品属性选择 (免费领)
                            }
                        }

                    }


                    @Override
                    protected boolean isHandleException() {
                        return true;
                    }

                }.execute("false");
            }

        } else {
            ToastUtil.showShortText(context, "无效操作");
        }


    }


    //单独购买商品属性选择对话框
    private void showOnlyBuyPopu() {
        if (shop != null && !this.isFinishing()) {
            final OnlyBuyShopDetailsDialog dlg;
            dlg = new OnlyBuyShopDetailsDialog(this, R.style.DialogStyle, width, height, shop, 0, "-1", "-1", "1", shop.getShop_price());//TODO:_MODIYFI_vipPayOnlyBuyPrice修改为shop.getShop_price()。看代码里vipPayOnlyBuyPrice这个值没有给赋值
            Window window = dlg.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.dlg_down_to_top);
            dlg.show();
            dlg.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface arg0) {
                    // TODO Auto-generated method stub
                    clickFlag = true;
                }
            });

            if (isNewMeal) {


                dlg.callBackShopCartNewMeal = new OnlyBuyShopDetailsDialog.OnCallBackShopCartNewMeal() {

                    @Override
                    public void callBackChooseNewMeal(String color_size, String shop_code, int type, String size, String color, double price, int shop_num,
                                                      int stock_type_id, int stock, String pic, int supp_id, double kickback, int original_price,
                                                      View v) {


                        dlg.dismiss();
                        // clickFlag = true;
                        entity = new GoodsEntity(pic, size, color, shop_num, stock_type_id, stock, supp_id, stock_type_id,
                                price, kickback, original_price);


                        Intent intent;

                        intent = new Intent(ShopDetailsActivity.this, SubmitOnlyBuyShopActivty.class);// 单独购买
                        Bundle bundle = new Bundle();


                        intent.putExtra("shop_num", shop_num);
                        intent.putExtra("buy_shop_code", shop_code);
                        intent.putExtra("shop_pic", pic);
                        intent.putExtra("color_size", color_size);
                        intent.putExtra("onePrice", shop.getAssmble_price());
                        intent.putExtra("advance_sale_days", shop.getAdvance_sale_days());


                        List<ShopCart> listGoods = new ArrayList<ShopCart>();
                        ShopCart shopCart = new ShopCart();
                        shopCart.setSupp_label("" + shop.getSupp_label());
                        shopCart.setShop_code(shop.getShop_code());
                        shopCart.setShop_num(shop_num);
                        shopCart.setSize(size);
                        shopCart.setColor(color);
                        shopCart.setShop_price(shop.getShop_price());
                        shopCart.setShop_se_price(shop.getShop_se_price());
                        shopCart.setOriginal_price(Double.valueOf(original_price));
                        shopCart.setDef_pic(pic);
                        shopCart.setStock_type_id(stock_type_id);
                        shopCart.setSupp_id(shop.getSupp_id());
                        shopCart.setShop_name(shop.getShop_name());
                        shopCart.setCore("0");
                        shopCart.setKickback(0.0);
                        shopCart.setUser_id(YCache.getCacheUser(context).getUser_id());
                        shopCart.setStore_code(YCache.getCacheStore(context).getS_code());
                        listGoods.add(shopCart);

                        bundle.putSerializable("listGoods", (Serializable) listGoods);
                        bundle.putSerializable("vipInfo", (Serializable) mVipInfo);
                        bundle.putBoolean("mIsTwoGroup", mIsTwoGroup);
                        bundle.putString("rollCode", "" + rollCode);

                        bundle.putString("onePrice", shop.getAssmble_price());
                        intent.putExtras(bundle);
                        intent.putExtra("groupFlag", groupFlag);


                        intent.putExtra("vipPayOnlyBuyPrice", vipPayOnlyBuyPrice);
                        intent.putExtra("youhuiDikou", youhuiDikou);


                        if (SubmitOnlyBuyShopActivty.instance != null) {
                            SubmitOnlyBuyShopActivty.instance.finish();
                        }
                        startActivity(intent);
                    }


                };


            } else {

                dlg.callBackShopCart = new OnlyBuyShopDetailsDialog.OnCallBackShopCart() {

                    @Override
                    public void callBackChoose(int type, String size, String color, double price, int shop_num,
                                               int stock_type_id, int stock, String pic, int supp_id, double kickback, int original_price,
                                               View v) {
                        dlg.dismiss();
                        // clickFlag = true;
                        entity = new GoodsEntity(pic, size, color, shop_num, stock_type_id, stock, supp_id, stock_type_id,
                                price, kickback, original_price);


                        Intent intent;

                        intent = new Intent(ShopDetailsActivity.this, SubmitOnlyBuyShopActivty.class);// 单独购买
                        Bundle bundle = new Bundle();

                        List<ShopCart> listGoods = new ArrayList<ShopCart>();
                        ShopCart shopCart = new ShopCart();
                        shopCart.setSupp_label("" + shop.getSupp_label());
                        shopCart.setShop_code(shop.getShop_code());
                        shopCart.setShop_num(shop_num);
                        shopCart.setSize(size);
                        shopCart.setColor(color);
                        shopCart.setShop_price(shop.getShop_price());
                        shopCart.setShop_se_price(shop.getShop_se_price());
                        shopCart.setOriginal_price(Double.valueOf(original_price));
                        shopCart.setDef_pic(pic);
                        shopCart.setStock_type_id(stock_type_id);
                        shopCart.setSupp_id(shop.getSupp_id());
                        shopCart.setShop_name(shop.getShop_name());
                        shopCart.setCore("0");
                        shopCart.setKickback(0.0);
                        shopCart.setUser_id(YCache.getCacheUser(context).getUser_id());
                        shopCart.setStore_code(YCache.getCacheStore(context).getS_code());
                        listGoods.add(shopCart);

                        bundle.putSerializable("listGoods", (Serializable) listGoods);
                        bundle.putSerializable("vipInfo", (Serializable) mVipInfo);
                        bundle.putBoolean("mIsTwoGroup", mIsTwoGroup);
                        bundle.putString("rollCode", "" + rollCode);

                        bundle.putString("onePrice", shop.getAssmble_price());
                        intent.putExtras(bundle);
                        intent.putExtra("groupFlag", groupFlag);


                        intent.putExtra("vipPayOnlyBuyPrice", vipPayOnlyBuyPrice);
                        intent.putExtra("youhuiDikou", youhuiDikou);
                        intent.putExtra("advance_sale_days", shop.getAdvance_sale_days());


                        if (SubmitOnlyBuyShopActivty.instance != null) {
                            SubmitOnlyBuyShopActivty.instance.finish();
                        }
                        startActivity(intent);
                    }


                };
            }


        }

    }


    //免费领商品属性选择对话框
    private void showFreeBuyPopu() {
        if (shop != null && !this.isFinishing()) {
            final FreeBuyShopDetailsDialog dlg;

            dlg = new FreeBuyShopDetailsDialog(this, R.style.DialogStyle, width, height, shop, 0, "-1", "-1", "1", vipPayOnlyBuyPrice);
            Window window = dlg.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.dlg_down_to_top);
            dlg.show();
            dlg.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface arg0) {
                    // TODO Auto-generated method stub
                    clickFlag = true;
                }
            });


            if (isNewMeal) {


                dlg.callBackShopCartNewMeal = new FreeBuyShopDetailsDialog.OnCallBackShopCartNewMeal() {


                    @Override
                    public void callBackChooseNewMeal(String color_size, String shop_code, int type, String size, String color, double price, int shop_num,
                                                      int stock_type_id, int stock, String pic, int supp_id, double kickback, int original_price,
                                                      View v) {


                        dlg.dismiss();
                        // clickFlag = true;
                        entity = new GoodsEntity(pic, size, color, shop_num, stock_type_id, stock, supp_id, stock_type_id,
                                price, kickback, original_price);


                        Intent intent;

                        intent = new Intent(ShopDetailsActivity.this, SubmitFreeBuyShopActivty.class);
                        Bundle bundle = new Bundle();


                        intent.putExtra("shop_num", shop_num);
                        intent.putExtra("freeOrderPage", freeOrderPage);
                        intent.putExtra("buy_shop_code", shop_code);
                        intent.putExtra("shop_pic", pic);
                        intent.putExtra("color_size", color_size);
                        intent.putExtra("onePrice", shop.getAssmble_price());


                        List<ShopCart> listGoods = new ArrayList<ShopCart>();
                        ShopCart shopCart = new ShopCart();
                        shopCart.setSupp_label("" + shop.getSupp_label());
                        shopCart.setShop_code(shop.getShop_code());
                        shopCart.setShop_num(shop_num);
                        shopCart.setSize(size);
                        shopCart.setColor(color);
                        shopCart.setShop_price(shop.getShop_price());
                        shopCart.setShop_se_price(shop.getShop_se_price());
                        shopCart.setOriginal_price(Double.valueOf(original_price));
                        shopCart.setDef_pic(pic);
                        shopCart.setStock_type_id(stock_type_id);
                        shopCart.setSupp_id(shop.getSupp_id());
                        shopCart.setShop_name(shop.getShop_name());
                        shopCart.setCore("0");
                        shopCart.setKickback(0.0);
                        shopCart.setUser_id(YCache.getCacheUser(context).getUser_id());
                        shopCart.setStore_code(YCache.getCacheStore(context).getS_code());
                        listGoods.add(shopCart);

                        bundle.putSerializable("listGoods", (Serializable) listGoods);
                        bundle.putSerializable("vipInfo", (Serializable) mVipInfo);
                        bundle.putBoolean("mIsTwoGroup", mIsTwoGroup);
                        bundle.putString("rollCode", "" + rollCode);

                        bundle.putString("onePrice", shop.getAssmble_price());
                        intent.putExtras(bundle);
                        intent.putExtra("groupFlag", groupFlag);
                        intent.putExtra("freeBuyType", freeBuyType);

                        intent.putExtra("page3", fromShouye3 ? 1 : 0);

                        intent.putExtra("vipPayOnlyBuyPrice", vipPayOnlyBuyPrice);
                        intent.putExtra("youhuiDikou", youhuiDikou);


                        if (SubmitFreeBuyShopActivty.instance != null) {
                            SubmitFreeBuyShopActivty.instance.finish();
                        }
                        startActivity(intent);
                    }


                };


            } else {

                dlg.callBackShopCart = new FreeBuyShopDetailsDialog.OnCallBackShopCart() {

                    @Override
                    public void callBackChoose(int type, String size, String color, double price, int shop_num,
                                               int stock_type_id, int stock, String pic, int supp_id, double kickback, int original_price,
                                               View v) {
                        dlg.dismiss();
                        // clickFlag = true;
                        entity = new GoodsEntity(pic, size, color, shop_num, stock_type_id, stock, supp_id, stock_type_id,
                                price, kickback, original_price);


                        Intent intent;

                        intent = new Intent(ShopDetailsActivity.this, SubmitFreeBuyShopActivty.class);// 免费领
                        Bundle bundle = new Bundle();

                        List<ShopCart> listGoods = new ArrayList<ShopCart>();
                        ShopCart shopCart = new ShopCart();
                        shopCart.setSupp_label("" + shop.getSupp_label());
                        shopCart.setShop_code(shop.getShop_code());
                        shopCart.setShop_num(shop_num);
                        shopCart.setSize(size);
                        shopCart.setColor(color);
                        shopCart.setShop_price(shop.getShop_price());
                        shopCart.setShop_se_price(shop.getShop_se_price());
                        shopCart.setOriginal_price(Double.valueOf(original_price));
                        shopCart.setDef_pic(pic);
                        shopCart.setStock_type_id(stock_type_id);
                        shopCart.setSupp_id(shop.getSupp_id());
                        shopCart.setShop_name(shop.getShop_name());
                        shopCart.setCore("0");
                        shopCart.setKickback(0.0);
                        shopCart.setUser_id(YCache.getCacheUser(context).getUser_id());
                        shopCart.setStore_code(YCache.getCacheStore(context).getS_code());
                        listGoods.add(shopCart);

                        bundle.putSerializable("listGoods", (Serializable) listGoods);
                        intent.putExtra("page3", fromShouye3 ? 1 : 0);
                        intent.putExtra("freeOrderPage", freeOrderPage);


                        bundle.putBoolean("mIsTwoGroup", mIsTwoGroup);
                        bundle.putString("rollCode", "" + rollCode);

                        bundle.putString("onePrice", shop.getAssmble_price());
                        intent.putExtras(bundle);
                        intent.putExtra("groupFlag", groupFlag);
                        intent.putExtra("freeBuyType", freeBuyType);

                        intent.putExtra("vipInfo", (Serializable) mVipInfo);

                        intent.putExtra("vipPayOnlyBuyPrice", vipPayOnlyBuyPrice);
                        intent.putExtra("youhuiDikou", youhuiDikou);


                        if (SubmitFreeBuyShopActivty.instance != null) {
                            SubmitFreeBuyShopActivty.instance.finish();
                        }
                        startActivity(intent);
                    }


                };
            }
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CommonUtils.disableScreenshots(this);

        setContentView(R.layout.activity_new_shop_details);
        ButterKnife.bind(this);
        FreeBuyShareDialog.needShareCount = 2;


        df = new SimpleDateFormat();

        headerView = LayoutInflater.from(ShopDetailsActivity.this)
                .inflate(R.layout.shop_header_nopintuan, null);
        header_tv_diKou = headerView.findViewById(R.id.tv_money_dikous);
        header_iv_wenhao = headerView.findViewById(R.id.iv_wenhao);
        header_tv_sjprice = headerView.findViewById(R.id.tv_sjprice);
        header_tv_price = headerView.findViewById(R.id.tv_price);
        tv_active_discount = headerView.findViewById(R.id.tv_active_discount);
        tv_yanjia_first_diamond_price = headerView.findViewById(R.id.tv_yanjia_first_diamond_price);
        tv_yanjia_first_diamond_text = headerView.findViewById(R.id.tv_yanjia_first_diamond_text);

        //拼团相关
        llPtAllHeaderImg = headerView.findViewById(R.id.ll_pt_all_header_img);
        llPtInfo = headerView.findViewById(R.id.ll_pt_info);
        tvPtNeedPopCount = headerView.findViewById(R.id.tv_pt_need_pop_count);
        tvPtTime = headerView.findViewById(R.id.tv_pt_time);
        tvPtNewUser = headerView.findViewById(R.id.tv_pt_new_user);


        titleCheck = 0;
        mImageRadio = SharedPreferencesUtil.getStringData(ShopDetailsActivity.this, Pref.IMAGE_RADIO, "450");
        signShopDetail = getIntent().getStringExtra("SignShopDetail");
        sweet_theme_id = getIntent().getStringExtra("sweet_theme_id");
        mIsGroup = getIntent().getBooleanExtra("mIsGroup", false);
        isNewMeal = getIntent().getBooleanExtra("isNewMeal", false);
        isHot = getIntent().getBooleanExtra("isHot", false);
        r_code = getIntent().getStringExtra("r_code");
        group_click_flag = getIntent().getBooleanExtra("group_click_flag", false);
        AppManager.getAppManager().addDetailsActivity(this);
        AppManager.getAppManager().addActivity(this);
        isforcelook = getIntent().getBooleanExtra("isforcelook", false);
        isforcelookMatch = getIntent().getBooleanExtra("isforcelookMatch", false);
        isForceLookLimit = getIntent().getBooleanExtra(Pref.ISFORCELOOKLIMIT, false);
        isSignActiveShop = getIntent().getBooleanExtra("isSignActiveShop", false);
        isSignActiveShopScan = getIntent().getBooleanExtra("isSignActiveShopScan", false);
        shareWaitDialog = new PublicToastDialog(this, R.style.DialogStyle1, "");
        freeOrderPage = getIntent().getIntExtra("freeOrderPage", 0);
        freeBuyType = getIntent().getIntExtra("freeBuyType", 0);


        fromShouye3 = getIntent().getBooleanExtra("fromShouye3", false);
        fromKTtask = getIntent().getBooleanExtra("fromKTtask", false);


        if (isforcelook == true || isforcelookMatch
                || (isSignActiveShop && SignListAdapter.doType == 4 && isSignActiveShopScan)) {//// 活动商品并且是浏览个数的任务

            String value = SignListAdapter.doValue;
            String values[] = value.split(",");
            if (values.length > 1) {
                singvalue = values[1];
                if (!Pattern.compile("^\\+?[1-9][0-9]*$").matcher(singvalue).find()) {
                    singvalue = "" + SignListAdapter.doNum;
                }

            } else {
                singvalue = "" + SignListAdapter.doNum;
            }

            xunBaoIv = (ImageView) findViewById(R.id.scoll_xunbao);
            df = new SimpleDateFormat("yyyy-MM-dd");
            String forceLook = SharedPreferencesUtil.getStringData(context, Pref.FORCELOOKSCOLL, "0");
            long forceLookTime = Long.valueOf(forceLook);
            if ("0".equals(forceLook) || !df.format(new Date()).equals(df.format(new Date(forceLookTime)))) {
                XunBaoScollDialog dialog = new XunBaoScollDialog(ShopDetailsActivity.this, xunBaoIv);
                dialog.show();
                SharedPreferencesUtil.saveStringData(context, Pref.FORCELOOKSCOLL, System.currentTimeMillis() + "");
            } else {
                xunBaoIv.setVisibility(View.VISIBLE);
            }

        }
        //新增浏览次数 奖励提现额度
        if (isForceLookLimit && SignListAdapter.doType == 19) {

            String value = SignListAdapter.doValue;
            String values[] = value.split(",");
            if (values.length > 1) {
                singvalue = values[1];
                if (!Pattern.compile("^\\+?[1-9][0-9]*$").matcher(singvalue).find()) {
                    singvalue = "10";
                }

            } else {
                singvalue = "10";
            }

            xunBaoIv = (ImageView) findViewById(R.id.scoll_xunbao);
            df = new SimpleDateFormat("yyyy-MM-dd");
            String forceLook = SharedPreferencesUtil.getStringData(context, Pref.FORCELOOKSCOLL, "0");
            long forceLookTime = Long.valueOf(forceLook);
            if ("0".equals(forceLook) || !df.format(new Date()).equals(df.format(new Date(forceLookTime)))) {
                XunBaoScollDialog dialog = new XunBaoScollDialog(ShopDetailsActivity.this, xunBaoIv);
                dialog.show();
                SharedPreferencesUtil.saveStringData(context, Pref.FORCELOOKSCOLL, System.currentTimeMillis() + "");
            } else {
                xunBaoIv.setVisibility(View.VISIBLE);
            }

        }


        shopCart = getIntent().getBooleanExtra("ShopCart", false);
        titleId = getIntent().getStringExtra("id");

        number_sold = getIntent().getStringExtra("number_sold"); // 取出是否抢完的值

        getWindownPixes();
        context = this;

        YDBHelper helper = new YDBHelper(this);
        String sql = "select * from sort_info where p_id = 0 and is_show = 1 order by sequence";
        listTitle = helper.query(sql);

        code = getIntent().getStringExtra("code");

        signType = getIntent().getIntExtra("signType", 0);
        signValue = getIntent().getStringExtra("valueBao");
        valueDuo = getIntent().getStringExtra("valueDuo");

        instance = this;

        oneBuyReceiver = new TaskReceiver(this) {
            @Override
            public void onReceive(Context context, Intent intent) {

                LogYiFu.e("广播", "接收到了广播");

                if (TaskReceiver.onebuysubmitoderend.equals(intent.getAction())) {
                    LogYiFu.e("广播", "接收到了广播222");
                    queryShopDetails();


                }


            }
        };

        TaskReceiver.regiserReceiver(this, oneBuyReceiver);


        initView();


    }


    private Timer ptTimer;
    private long ptRecLen;

    public class PtTimerTask extends TimerTask {


        @Override
        public void run() {

            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    ptRecLen -= 1000;


                    String hoursStr = "";
                    String minutesStr = "";
                    String secondsStr = "";

                    String hours;
                    String minutes;
                    String seconds;


                    long minute = ptRecLen / 60000;
                    long second = (ptRecLen % 60000) / 1000;
                    if (minute >= 60) {
                        long hour = minute / 60;
                        minute = minute % 60;

                        if (hour < 10) {
                            hours = "0" + hour;
                        } else {
                            hours = "" + hour;
                        }
                        if (minute < 10) {
                            minutes = "0" + minute;
                        } else {
                            minutes = "" + minute;
                        }
                        if (second < 10) {
                            seconds = "0" + second;
                        } else {
                            seconds = "" + second;
                        }
//                        mTvTimeHours.setText(hours);
//                        mTvTimeMinutes.setText(minutes);
//                        mTvTimeSeconds.setText(seconds);


                        hoursStr = hours;
                        minutesStr = minutes;
                        secondsStr = seconds;


//                        tvPtTime.setText("剩余"+hours+":"+minutes+":"+seconds);


//						}
                    } else if (minute >= 10 && second >= 10) {
//                        mTvTimeHours.setText("00");
//                        mTvTimeMinutes.setText(minute + "");
//                        mTvTimeSeconds.setText(second + "");

                        hoursStr = "00";
                        minutesStr = minute + "";
                        secondsStr = second + "";

                    } else if (minute >= 10 && second < 10) {
//                        mTvTimeHours.setText("00");
//                        mTvTimeMinutes.setText(minute + "");
//                        mTvTimeSeconds.setText("0" + second);

                        hoursStr = "00";
                        minutesStr = minute + "";
                        secondsStr = "0" + second;

                    } else if (minute < 10 && second >= 10) {


//                        mTvTimeHours.setText("00");
//                        mTvTimeMinutes.setText("0" + minute);
//                        mTvTimeSeconds.setText(second + "");

                        hoursStr = "00";
                        minutesStr = "0" + minute;
                        secondsStr = second + "";


                    } else if (minute < 10 && second < 10) {
//                        mTvTimeHours.setText("00");
//                        mTvTimeMinutes.setText("0" + minute);
//                        mTvTimeSeconds.setText("0" + second);


                        hoursStr = "00";
                        minutesStr = "0" + minute;
                        secondsStr = "0" + second;
                    }


                    if (ptRecLen <= 0) {
                        ptTimer.cancel();
//                        mTvTimeHours.setText("00");
//                        mTvTimeMinutes.setText("00");
//                        mTvTimeSeconds.setText("00");
//                        setBottomFinishTv();
                        hoursStr = "00";
                        minutesStr = "00";
                        secondsStr = "00";
                        llPtInfo.setVisibility(View.VISIBLE);


                    }

                    tvPtTime.setText("剩余" + hoursStr + ":" + minutesStr + ":" + secondsStr);


                }
            });
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        llPtInfo.setVisibility(View.GONE);
        tvPtNewUser.setVisibility(View.GONE);
        btnBuyLeftLl.setVisibility(View.GONE);
        btnBuyRightLl.setVisibility(View.VISIBLE);
        btnBuyRightTextLl.setVisibility(View.GONE);
        btnBuyLeftTextLl.setVisibility(View.GONE);
        tvRightRmb.setText("");
        tvLeftRmb.setText("");

        btnBuyRightTopText.setText("0.0");
        btnBuyRightBottomText.setText("立即购买");
        btnBuyLeftTopText.setText("");
        btnBuyLeftBottomText.setText("");

        TongJiUtils.TongJi(context, 6 + "");
        LogYiFu.e("TongJiNew", 6 + "");
        HomeWatcherReceiver.registerHomeKeyReceiver(context);
        SharedPreferencesUtil.saveStringData(context, Pref.TONGJI_TYPE, "1050");

//        if (!isShowLingLiJingEnd) {
//            DialogUtils.initJiangLiJin(this, true);
//        }

        if(YJApplication.instance.isLoginSucess()){
            YConn.httpPost(instance, YUrl.GETALLDIKOU, new HashMap<String, String>(), new HttpListener<VipDikouData>() {
                @Override
                public void onSuccess(VipDikouData result) {
                    mVipDikouData = result;
                }

                @Override
                public void onError() {

                }
            });
        }




        queryShopDetails();


//        if (YJApplication.instance.isLoginSucess()) {
//
//
//            //查询有有效的团，如果有就在顶部显示团信息，引导去拼团详情
//            YConn.httpPost(instance, YUrl.QUERY_GOOD_TUAN, new HashMap<String, String>(), new HttpListener<GoodTuanData>() {
//                @Override
//                public void onSuccess(final GoodTuanData goodTuanData) {
//
//
//                    //没有开过团并且不是会员并且不是从首页3进来的，就显卡开团提醒的通栏
//                    if (goodTuanData.getRollNum() == 0 && goodTuanData.getIs_vip() == 0 && !fromShouye3) {
//                        tvPtNewUser.setVisibility(View.VISIBLE);
//                        return;
//                    }
//
//
//                    if (null != goodTuanData.getRoll_code()) {
//
//                        List<String> userPicData = goodTuanData.getUserPic();
//                        llPtAllHeaderImg.removeAllViews();
//                        for (int i = 0; i < userPicData.size(); i++) {
//                            View headerItemView = LayoutInflater.from(context).inflate(R.layout.pt_header_item, null);
//                            ImageView groups_head_iv = headerItemView.findViewById(R.id.groups_head_iv);
//                            ImageView groups_head_tz_icon = headerItemView.findViewById(R.id.groups_head_tz_icon);
//                            GlideUtils.initRoundImage(Glide.with(context), context, userPicData.get(i), groups_head_iv);
//                            if (i == 0) {
//                                groups_head_tz_icon.setVisibility(View.VISIBLE);
//
//
//                                RelativeLayout.LayoutParams lp =
//                                        new RelativeLayout.LayoutParams(DP2SPUtil.dp2px(context, 30), (DP2SPUtil.dp2px(context, 30)));
//                                lp.setMargins(DP2SPUtil.dp2px(context, 18), DP2SPUtil.dp2px(context, 8), 0, 0);
//
//                                groups_head_iv.setLayoutParams(lp);
//
//                            } else {
//                                groups_head_tz_icon.setVisibility(View.GONE);
//
//                                RelativeLayout.LayoutParams lp =
//                                        new RelativeLayout.LayoutParams(DP2SPUtil.dp2px(context, 30), (DP2SPUtil.dp2px(context, 30)));
//                                lp.setMargins(DP2SPUtil.dp2px(context, 5), DP2SPUtil.dp2px(context, 8), 0, 0);
//                                groups_head_iv.setLayoutParams(lp);
//
//
//                            }
//                            llPtAllHeaderImg.addView(headerItemView);
//                        }
//
//                        int needImgCount = 4 - userPicData.size();
//                        for (int i = 0; i < needImgCount; i++) {
//                            View headerItemView = LayoutInflater.from(context).inflate(R.layout.pt_header_item, null, false);
//                            headerItemView.findViewById(R.id.groups_head_tz_icon).setVisibility(View.GONE);
//                            RelativeLayout.LayoutParams lp =
//                                    new RelativeLayout.LayoutParams(DP2SPUtil.dp2px(context, 30), (DP2SPUtil.dp2px(context, 30)));
//                            lp.setMargins(DP2SPUtil.dp2px(context, 5), DP2SPUtil.dp2px(context, 8), 0, 0);
//                            headerItemView.findViewById(R.id.groups_head_iv).setLayoutParams(lp);
//                            llPtAllHeaderImg.addView(headerItemView);
//                        }
//
//                        llPtInfo.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Intent OneBuyintent = new Intent(instance, OneBuyGroupsDetailsActivity.class);
//                                OneBuyintent.putExtra("roll_code", goodTuanData.getRoll_code());
//                                OneBuyintent.putExtra("isMeal", false);
//                                startActivity(OneBuyintent);
//
//                            }
//                        });
//
////                        tvPtNeedPopCount.setText("还差" + goodTuanData.getRnum() + "人拼成");
//
//                        tvPtNeedPopCount.setText(
//                                Html.fromHtml("还差<font color='#ff0000'>" + goodTuanData.getRnum() + "</font>人拼成"));
//
//
//                        ptRecLen = (int) (goodTuanData.getTime() * 1.8);//拼团剩余时间
//
//
//                        if (ptTimer != null) {
//                            ptTimer.cancel();
//                        }
//
//
//                        ptTimer = new Timer();
//                        ptTimer.schedule(new PtTimerTask(), 0, 555);
//
//
////                        tvPtTime.setText("剩余"+"");
//
//
//                        llPtInfo.setVisibility(View.VISIBLE);
//
//                    }
//
//                }
//
//                @Override
//                public void onError() {
//
//                }
//            });
//        }
//
//        if (!isSignActiveShop) {
//            tv_buy_now.setText("0元购全返");
//            tv_buy_now.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//            tv_buy_now.setTextSize(16);
//
//        }

//        if (!"SignShopDetail".equals(signShopDetail)) {
//            if (isSignActiveShop) {
//
//                if (group_click_flag) {
//                    mActivityBottom.setVisibility(View.VISIBLE);
//                    mNomarBottom.setVisibility(View.GONE);
//                } else {
//                    tv_buy_now.setText("立即购买");
//                    mRlAddShopCart.setVisibility(View.GONE);
//
//                }
//            } else {
//                setShareAnim();
//                //显示悬浮红包
//                queryBalanceNum();
//            }
//        }


        ShopCartDao dao = new ShopCartDao(context);
        countCommn = dao.queryCartCommonCount(context);
        countMeal = dao.queryCartSpecialCount(context);

        LogYiFu.e("ShopDetailsActivity_onresume", "OK");
        isPause = 0;
        // ////////////////////////////////////////////////////////////////////////////////////////////////////

        List<String> taskMap = YJApplication.instance.getTaskMap();
        if (taskMap == null) {
            taskMap = new ArrayList<String>();
        }
        int curr = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        if ((!taskMap.contains("17") || !YJApplication.instance.isLoginSucess())
                && curr != getSharedPreferences("month", Context.MODE_PRIVATE).getInt("month", 0)
                && (getSharedPreferences("week", Context.MODE_PRIVATE).getInt("week", 0) == Calendar.getInstance()
                .get(Calendar.DAY_OF_WEEK)
                || getSharedPreferences("week", Context.MODE_PRIVATE).getInt("week", 0) == 0)) {
            getSharedPreferences("month", Context.MODE_PRIVATE).edit()
                    .putInt("month", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
            getSharedPreferences("week", Context.MODE_PRIVATE).edit()
                    .putInt("week", Calendar.getInstance().get(Calendar.DAY_OF_WEEK)).commit();
        } else {
            if (YJApplication.instance.isLoginSucess() == false
                    || getSharedPreferences("dian", Context.MODE_PRIVATE).getInt("dian", 0) == curr) {
                shareHandler = new Handler();
                shareHandler.postDelayed(shareRun, 30 * 1000);
                return;
            }
            UserInfo user = YCache.getCacheUser(context);
            if (user.getHobby() == null || user.getHobby().equals("0")) {

                newHandler = new Handler();
                newHandler.postDelayed(r, 60 * 1000);
            }

        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        TongJiUtils.TongJi(context, 106 + "");
        LogYiFu.e("TongJiNew", 106 + "");
        HomeWatcherReceiver.unregisterHomeKeyReceiver(context);
        titleCheck = -1;

        if (newHandler != null) {
            newHandler.removeCallbacks(r);
        }
        if (shareHandler != null) {
            shareHandler.removeCallbacks(shareRun);
        }
        isPause = 1;
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        LogYiFu.e("queryBalanceNum", arg0 + " " + arg1 + " " + arg2);
        if (arg1 == RESULT_OK) {

            ShopCartDao dao = new ShopCartDao(context);
            int count = 0;

            count = dao.queryCartCommonCount(context);
            if (arg0 == 1080) {

                if ("SignShopDetail".equals(signShopDetail)) {

                    tv_cart_count.setVisibility(View.VISIBLE);
                    tv_cart_count.setText(/* shop.getCart_count() */count + "");
                    tv_cart_count2.setVisibility(View.VISIBLE);
                    tv_cart_count2.setText(/* shop.getCart_count() */count + "");
                }
            } else if (arg0 == 233) {// 加入购物车
                double a = entity.getOriginal_price();
                int b = (int) a;
                joinShopCart(entity.getSize(), entity.getColor(), entity.getShop_num(), entity.getStock_type_id(),
                        entity.getPic(), entity.getPrice(), entity.getSupp_id(), entity.getKickback(), b, mListView);

            } else if (arg0 == 234) {// 购买
                Intent intent = new Intent(ShopDetailsActivity.this, SubmitOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("shop", shop);
                intent.putExtras(bundle);
                intent.putExtra("id", entity.getId());
                intent.putExtra("size", entity.getSize());
                intent.putExtra("color", entity.getColor());
                intent.putExtra("shop_num", entity.getShop_num());
                intent.putExtra("stock_type_id", entity.getStock_type_id());
                intent.putExtra("stock", entity.getStock());
                intent.putExtra("price", entity.getPrice());
                intent.putExtra("pic", entity.getPic());
                startActivity(intent);
            } else if (arg0 == 235) {
                // mListView.removeHeaderView(headerView);
                if ("SignShopDetail".equals(signShopDetail)) {
                    // queryShopSign();
                } else {
                    LogYiFu.e("刷新", "登录回来");
                    //先查询新老用户
//                    queryShopDetails();

                }
            } else if (arg0 == 15502) {
                if (YJApplication.instance.isLoginSucess()) {
                    if ("SignShopDetail".equals(signShopDetail)) {
                        getPshareShop();
                    } else {
                        share(shop.getShop_code(), shop);
                    }
                }
            }
        }


    }


    private List<Shop> list;// 套餐内商品集合

    @OnClick({R.id.ll_kefu_red})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_kefu_red:
//                startActivity(new Intent(this, KeFuActivity.class));

                WXminiAppUtil.jumpToWXmini(this);


                break;

        }

    }


    private class MyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        // private int i;

        @Override
        public int getCount() {
            int count = 0;
            if (check == 0) {
                if ("SignShopDetail".equals(signShopDetail)) {

                    count = count + imageTag1.length + imageTag2.length + imageTag3.length;

                } else {
                    count += images.length;
                }
                if (dataList != null) {
                    count += dataList.size() % 2 == 0 ? dataList.size() / 2 : dataList.size() / 2 + 1;
                }
            } else if (check == 1) {
                if ("SignShopDetail".equals(signShopDetail)) {
                    count += list.size() + 1;
                } else {
                    count += 2;
                }
            } else {

                if ("SignShopDetail".equals(signShopDetail)) {// 套餐取消评论区
                    count = tuijianList == null ? 4
                            : (tuijianList.size() % 2 == 0 ? tuijianList.size() / 2 : tuijianList.size() / 2 + 1);
                } else {
                    count += 2;
                    if (listShopComments != null && listShopComments.size() > 0) {
                        count += listShopComments.size();
                    } else {
                        count++;
                    }
                }
            }
            return count;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int position, View v, ViewGroup arg2) {
            ItemViewHolder vh;
            if (v == null) {
                v = LayoutInflater.from(ShopDetailsActivity.this).inflate(R.layout.new_shop_item, arg2, false);
                vh = new ItemViewHolder();
                v.findViewById(R.id.lln).setBackgroundColor(Color.WHITE);
                vh.imageGroup = v.findViewById(R.id.image_group);
                vh.sView2 = (SizeView2) v.findViewById(R.id.size_view2);
                vh.shopItem = v.findViewById(R.id.item_position);
                vh.image = (ImageView) v.findViewById(R.id.image_position);


                vh.image.getLayoutParams().height = width * 9 / 6;
                vh.left = (ItemView) v.findViewById(R.id.left);

                vh.right = (ItemView) v.findViewById(R.id.right);


                vh.sView = (SizeView) v.findViewById(R.id.size_view);
                vh.eView = v.findViewById(R.id.sevalauate_view);
                vh.eView.setBackgroundColor(Color.WHITE);
                vh.lin_nodata = (LinearLayout) v.findViewById(R.id.lin_nodata);

                vh.pb_color_count = (CircleProgressView) v.findViewById(R.id.pb_color_count);
                vh.pb_type_count = (CircleProgressView) v.findViewById(R.id.pb_type_count);
                vh.pb_work_count = (CircleProgressView) v.findViewById(R.id.pb_work_count);
                vh.pb_cost_count = (CircleProgressView) v.findViewById(R.id.pb_cost_count);


                vh.progressContain = (LinearLayout) v.findViewById(R.id.ll_container_progress);
                vh.progressContain.getLayoutParams().height = (width - DP2SPUtil.dp2px(ShopDetailsActivity.this, 100))
                        / 4;
                vh.viewContainer = (LinearLayout) v.findViewById(R.id.container);

                vh.img_user_header = (RoundImageButton) v.findViewById(R.id.img_user_header);
                vh.tv_user = (TextView) v.findViewById(R.id.tv_user);
                vh.tv_evaluate = (TextView) v.findViewById(R.id.tv_evaluate);
                vh.tv_date = (TextView) v.findViewById(R.id.tv_date);
                vh.tv_descri = (TextView) v.findViewById(R.id.tv_descri);
                vh.tv_size_color = (TextView) v.findViewById(R.id.tv_size_color);
                vh.img_container = (LinearLayout) v.findViewById(R.id.img_container);
                vh.tv_one_reply = (TextView) v.findViewById(R.id.tv_one_reply);
                vh.tv_second_judge = (TextView) v.findViewById(R.id.tv_second_judge);
                vh.tv_second_reply = (TextView) v.findViewById(R.id.tv_second_reply);

                vh.lin_second = (LinearLayout) v.findViewById(R.id.lin_second);

                vh.bar = (RatingBar) v.findViewById(R.id.smy_ratingbar);

                vh.evaView = v.findViewById(R.id.evaluate_view);
                vh.youxuanComments = (TextView) v.findViewById(R.id.tv_youxuan_comments);
                vh.newLine = v.findViewById(R.id.v_line);
                vh.bai = v.findViewById(R.id.bai);
                vh.bai.getLayoutParams().height = ShopDetailsActivity.this.height / 3;

                vh.diver = v.findViewById(R.id.diver);

                vh.sizeHint = (ImageView) v.findViewById(R.id.size_hint);
                vh.sizeHint.getLayoutParams().height = width * 2453 / 1080;

                vh.two_container = (LinearLayout) v.findViewById(R.id.two_image_container);
                vh.imageTag = (ImageView) v.findViewById(R.id.meal_tag);

                vh.mealRView = (MealRecomenView) v.findViewById(R.id.meal_r);

                v.setTag(vh);
            } else {
                vh = (ItemViewHolder) v.getTag();
            }

            boolean mIsVip = false;

            if (YJApplication.instance.isLoginSucess() && null != mVipDikouData) {
                mIsVip = CommonUtils.isVip(mVipDikouData.getIsVip(), mVipDikouData.getMaxType());
            }


            if (check == 0) {// 详情

                vh.mealRView.setVisibility(View.GONE);
                vh.sView.setVisibility(View.GONE);
                vh.eView.setVisibility(View.GONE);
                vh.bai.setVisibility(View.GONE);
                vh.diver.setVisibility(View.GONE);
                vh.sizeHint.setVisibility(View.GONE);

                if ("SignShopDetail".equals(signShopDetail)) {// 如果是套餐...

                    if (position < imageTag1.length) {
                        if (position == 0) {
                            vh.imageTag.setVisibility(View.VISIBLE);
                        } else {
                            vh.imageTag.setVisibility(View.GONE);
                        }

                        vh.imageTag.setImageResource(R.drawable.shop_tag_one);

                        vh.imageGroup.setVisibility(View.VISIBLE);
                        vh.shopItem.setVisibility(View.GONE);
                        if (width > 720) {
                            vh.image.setTag(list.get(0).getShop_code().substring(1, 4) + "/"
                                    + list.get(0).getShop_code() + "/" + imageTag1[position] + "!" + mImageRadio);


                            PicassoUtils.initImage(ShopDetailsActivity.this, list.get(0).getShop_code().substring(1, 4)
                                            + "/" + list.get(0).getShop_code() + "/" + imageTag1[position] + "!" + mImageRadio,
                                    vh.image);

                        } else {
                            vh.image.setTag(list.get(0).getShop_code().substring(1, 4) + "/"
                                    + list.get(0).getShop_code() + "/" + imageTag1[position] + "!" + mImageRadio);
                            PicassoUtils.initImage(ShopDetailsActivity.this, list.get(0).getShop_code().substring(1, 4)
                                            + "/" + list.get(0).getShop_code() + "/" + imageTag1[position] + "!" + mImageRadio,
                                    vh.image);
                        }

                        final int x = position;
                        vh.image.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ShopDetailsActivity.this, ShopImageActivity.class);
                                intent.putExtra("url", list.get(0).getShop_code().substring(1, 4) + "/"
                                        + list.get(0).getShop_code() + "/" + imageTag1[x]);
                                intent.putExtra("code", code);
                                intent.putExtra("mealMap", mealMap);
                                intent.putExtra("signValue", signValue);
                                intent.putExtra("signType", signType);
                                intent.putExtra("signShopDetail", signShopDetail);
                                intent.putExtra("supp_label", mSupp_label);
                                intent.putExtra("ShopCart", shopCart);
                                intent.putExtra("number_sold", number_sold);
                                intent.putExtra("isSignActiveShop", isSignActiveShop);

                                startActivityForResult(intent, 1080);
                            }
                        });

                        return v;
                    }
                    if (position < imageTag1.length + imageTag2.length) {
                        if (position == imageTag1.length) {
                            vh.imageTag.setVisibility(View.VISIBLE);
                        } else {
                            vh.imageTag.setVisibility(View.GONE);
                        }

                        vh.imageTag.setImageResource(R.drawable.shop_tag_two);

                        vh.imageGroup.setVisibility(View.VISIBLE);
                        vh.shopItem.setVisibility(View.GONE);
                        if (width > 720) {
                            vh.image.setTag(
                                    list.get(0).getShop_code().substring(1, 4) + "/" + list.get(0).getShop_code() + "/"
                                            + imageTag2[position - imageTag1.length] + "!" + mImageRadio);


                            PicassoUtils.initImage(ShopDetailsActivity.this,
                                    list.get(1).getShop_code().substring(1, 4) + "/" + list.get(1).getShop_code() + "/"
                                            + imageTag2[position - imageTag1.length] + "!" + mImageRadio,
                                    vh.image);

                        } else {
                            vh.image.setTag(
                                    list.get(1).getShop_code().substring(1, 4) + "/" + list.get(1).getShop_code() + "/"
                                            + imageTag2[position - imageTag1.length] + "!" + mImageRadio);


                            PicassoUtils.initImage(ShopDetailsActivity.this,
                                    list.get(1).getShop_code().substring(1, 4) + "/" + list.get(1).getShop_code() + "/"
                                            + imageTag2[position - imageTag1.length] + "!" + mImageRadio,
                                    vh.image);

                        }

                        final int x = position;
                        vh.image.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ShopDetailsActivity.this, ShopImageActivity.class);
                                intent.putExtra("url", list.get(1).getShop_code().substring(1, 4) + "/"
                                        + list.get(1).getShop_code() + "/" + imageTag2[x - imageTag1.length]);
                                intent.putExtra("code", code);
                                intent.putExtra("mealMap", mealMap);
                                intent.putExtra("signValue", signValue);
                                intent.putExtra("signType", signType);
                                intent.putExtra("signShopDetail", signShopDetail);
                                intent.putExtra("supp_label", mSupp_label);
                                intent.putExtra("ShopCart", shopCart);
                                intent.putExtra("number_sold", number_sold);
                                intent.putExtra("isSignActiveShop", isSignActiveShop);
                                startActivityForResult(intent, 1080);
                            }
                        });

                        return v;
                    }

                    if (position < imageTag1.length + imageTag2.length + imageTag3.length) {
                        if (position == imageTag1.length + imageTag2.length) {
                            vh.imageTag.setVisibility(View.VISIBLE);
                        } else {
                            vh.imageTag.setVisibility(View.GONE);
                        }
                        vh.imageTag.setImageResource(R.drawable.shop_tag_three);

                        vh.imageGroup.setVisibility(View.VISIBLE);
                        vh.shopItem.setVisibility(View.GONE);
                        if (width > 720) {
                            vh.image.setTag(list.get(2).getShop_code().substring(1, 4) + "/"
                                    + list.get(2).getShop_code() + "/"
                                    + imageTag3[position - imageTag1.length - imageTag2.length] + "!" + mImageRadio);

                            PicassoUtils.initImage(ShopDetailsActivity.this,
                                    list.get(2).getShop_code().substring(1, 4) + "/" + list.get(0).getShop_code() + "/"
                                            + imageTag3[position - imageTag1.length - imageTag2.length] + "!"
                                            + mImageRadio,
                                    vh.image);

                        } else {
                            vh.image.setTag(list.get(2).getShop_code().substring(1, 4) + "/"
                                    + list.get(2).getShop_code() + "/"
                                    + imageTag3[position - imageTag1.length - imageTag2.length] + "!" + mImageRadio);

                            PicassoUtils.initImage(ShopDetailsActivity.this,
                                    list.get(2).getShop_code().substring(1, 4) + "/" + list.get(0).getShop_code() + "/"
                                            + imageTag3[position - imageTag1.length - imageTag2.length] + "!"
                                            + mImageRadio,
                                    vh.image);

                        }

                        final int x = position;
                        vh.image.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ShopDetailsActivity.this, ShopImageActivity.class);
                                intent.putExtra("url",
                                        list.get(2).getShop_code().substring(1, 4) + "/" + list.get(2).getShop_code()
                                                + "/" + imageTag3[x - imageTag1.length - imageTag2.length]);
                                intent.putExtra("code", code);
                                intent.putExtra("mealMap", mealMap);
                                intent.putExtra("signValue", signValue);
                                intent.putExtra("signType", signType);
                                intent.putExtra("signShopDetail", signShopDetail);
                                intent.putExtra("supp_label", mSupp_label);
                                intent.putExtra("ShopCart", shopCart);
                                intent.putExtra("number_sold", number_sold);
                                intent.putExtra("isSignActiveShop", isSignActiveShop);
                                startActivityForResult(intent, 1080);
                            }
                        });

                        return v;
                    }
                    vh.imageTag.setVisibility(View.GONE);
                    position = position - imageTag1.length - imageTag2.length - imageTag3.length;

                } else {
                    if (position == images.length - 1) {
                        vh.sView2.setContent(shop, false, position);
                        vh.sView2.setVisibility(View.VISIBLE);
                    } else {
                        vh.sView2.setVisibility(View.GONE);

                    }
                    vh.imageTag.setVisibility(View.GONE);
                    if (position < images.length) {
                        vh.imageGroup.setVisibility(View.VISIBLE);
                        vh.shopItem.setVisibility(View.GONE);
                        vh.image.setTag(shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/"
                                + images[position] + "!" + mImageRadio);


                        //不再压缩
                        PicassoUtils.initImage(ShopDetailsActivity.this, shop.getShop_code().substring(1, 4) + "/"
                                + shop.getShop_code() + "/" + images[position], vh.image);

                        final int x = position;
                        vh.image.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ShopDetailsActivity.this, ShopImageActivity.class);
                                intent.putExtra("url", shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code()
                                        + "/" + images[x]);
                                intent.putExtra("shop", shop);
                                intent.putExtra("supp_label", mSupp_label);
                                intent.putExtra("ShopCart", shopCart);
                                intent.putExtra("number_sold", number_sold);
                                intent.putExtra("isSignActiveShop", isSignActiveShop);
                                startActivityForResult(intent, 1080);
                            }
                        });

                        return v;
                    }
                    if (isforcelook == true || isforcelookMatch
                            || (isSignActiveShop && SignListAdapter.doType == 4 && isSignActiveShopScan)
                            || (isForceLookLimit && SignListAdapter.doType == 19)) {// 活动商品并且是浏览个数的任务
                        if (position == images.length) {
                            if (first_come == true) {
                                first_come = false;

                                if (isforcelook) {/// 正价商品
                                    String nowTimeForcelook = SharedPreferencesUtil.getStringData(
                                            ShopDetailsActivity.this,
                                            "forcelookNowTime" + YCache.getCacheUser(context).getUser_id(), "");
                                    forcelookNum = Integer
                                            .valueOf(
                                                    SharedPreferencesUtil
                                                            .getStringData(ShopDetailsActivity.this,
                                                                    SignListAdapter.signIndex + "forcelookNum"
                                                                            + YCache.getCacheUser(context).getUser_id(),
                                                                    "0"));
                                    int aaa = 0;

                                    if (!df.format(new Date()).equals(nowTimeForcelook)) {
                                        forcelookNum = 0;// 不是同一天点击分享任务
                                        // 或者不是同一个用户 就
                                        // 或者取出的数据大于浏览次数
                                        // 重新开始计数分享的次数
                                    }
                                    forcelookNum++;
                                    if (SignListAdapter.doNum > 1) {// 需要奖励分多次发放
                                        sign();
                                    } else {
                                        SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
                                                "forcelookNowTime" + YCache.getCacheUser(context).getUser_id(),
                                                df.format(new Date()));
                                        SharedPreferencesUtil
                                                .saveStringData(ShopDetailsActivity.this,
                                                        SignListAdapter.signIndex + "forcelookNum"
                                                                + YCache.getCacheUser(context).getUser_id(),
                                                        "" + forcelookNum);
                                        if (forcelookNum < Integer.parseInt(singvalue)) {
                                            ToastUtil.showLongText(ShopDetailsActivity.this,
                                                    "再浏览" + (Integer.parseInt(singvalue) - forcelookNum) + "件可完成任务喔~");
                                        } else if (forcelookNum >= Integer.parseInt(singvalue)) {
                                            sign();
                                        }
                                    }

                                } else if (isforcelookMatch) {// 搭配购商品
                                    String nowTimeForcelookMatch = SharedPreferencesUtil.getStringData(
                                            ShopDetailsActivity.this,
                                            "forcelookMatchNowTime" + YCache.getCacheUser(context).getUser_id(), "");
                                    forcelookMatchNum = Integer
                                            .valueOf(
                                                    SharedPreferencesUtil
                                                            .getStringData(ShopDetailsActivity.this,
                                                                    SignListAdapter.signIndex + "forcelookMatchNum"
                                                                            + YCache.getCacheUser(context).getUser_id(),
                                                                    "0"));
                                    if (!df.format(new Date()).equals(nowTimeForcelookMatch)) {
                                        forcelookMatchNum = 0;// 不是同一天点击分享任务
                                        // 或者不是同一个用户
                                        // 就重新开始计数分享的次数
                                    }
                                    forcelookMatchNum++;
                                    if (SignListAdapter.doNum > 1) {// 需要奖励分多次发放
                                        sign();
                                    } else {
                                        SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
                                                "forcelookMatchNowTime" + YCache.getCacheUser(context).getUser_id(),
                                                df.format(new Date()));
                                        SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
                                                SignListAdapter.signIndex + "forcelookMatchNum"
                                                        + YCache.getCacheUser(context).getUser_id(),
                                                "" + forcelookMatchNum);
                                        if (forcelookMatchNum < Integer.parseInt(singvalue)) {
                                            ToastUtil.showLongText(ShopDetailsActivity.this, "再浏览"
                                                    + (Integer.parseInt(singvalue) - forcelookMatchNum) + "件可完成任务喔~");
                                        } else if (forcelookMatchNum >= Integer.parseInt(singvalue)) {
                                            sign();
                                        }
                                    }
                                } else if (isSignActiveShopScan) {// 活动商品浏览
                                    String nowTimeSignActiveShop = SharedPreferencesUtil.getStringData(
                                            ShopDetailsActivity.this,
                                            "signActiveShopNowTime" + YCache.getCacheUser(context).getUser_id(), "");
                                    signActiveShopNum = Integer
                                            .valueOf(
                                                    SharedPreferencesUtil
                                                            .getStringData(ShopDetailsActivity.this,
                                                                    SignListAdapter.signIndex + "signActiveShopNum"
                                                                            + YCache.getCacheUser(context).getUser_id(),
                                                                    "0"));
                                    if (!df.format(new Date()).equals(nowTimeSignActiveShop)) {
                                        signActiveShopNum = 0;// 不是同一天点击分享任务
                                        // 或者不是同一个用户
                                        // 就重新开始计数分享的次数
                                    }
                                    signActiveShopNum++;
                                    if (SignListAdapter.doNum > 1) {// 需要奖励分多次发放
                                        sign();
                                    } else {
                                        SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
                                                "signActiveShopNowTime" + YCache.getCacheUser(context).getUser_id(),
                                                df.format(new Date()));
                                        SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
                                                SignListAdapter.signIndex + "signActiveShopNum"
                                                        + YCache.getCacheUser(context).getUser_id(),
                                                "" + signActiveShopNum);
                                        if (signActiveShopNum < Integer.parseInt(singvalue)) {
                                            ToastUtil.showLongText(ShopDetailsActivity.this, "再浏览"
                                                    + (Integer.parseInt(singvalue) - signActiveShopNum) + "件可完成任务喔~");
                                        } else if (signActiveShopNum >= Integer.parseInt(singvalue)) {
                                            sign();
                                        }
                                    }
                                } else if (isForceLookLimit) {
                                    String nowTimeForcelookLimit = SharedPreferencesUtil.getStringData(
                                            ShopDetailsActivity.this,
                                            "nowTimeForcelookLimit" + YCache.getCacheUser(context).getUser_id(), "");
                                    forcelookLimitNum = Integer
                                            .parseInt(
                                                    SharedPreferencesUtil
                                                            .getStringData(ShopDetailsActivity.this,
                                                                    SignListAdapter.signIndex + Pref.ISFORCELOOKLIMITNUM
                                                                            + YCache.getCacheUser(context).getUser_id(),
                                                                    "0"));
                                    if (!df.format(new Date()).equals(nowTimeForcelookLimit)) {
                                        forcelookLimitNum = 0;// 不是同一天点击分享任务
                                        // 或者不是同一个用户 就
                                        // 或者取出的数据大于浏览次数
                                        // 重新开始计数分享的次数
                                    }

                                    SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
                                            "nowTimeForcelookLimit" + YCache.getCacheUser(context).getUser_id(),
                                            df.format(new Date()));
                                    if (forcelookLimitNum / Integer.parseInt(singvalue) + 1 > SignListAdapter.doNum
                                            || SignListAdapter.isSignComplete) {
                                        //浏览 奖励额度 达到上限
                                        NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(ShopDetailsActivity.this,
                                                R.style.DialogQuheijiao2, Pref.LIULAN_SIGN_UPPER_LIMIT);
                                        dialog.show();
                                        forcelookLimitNum++;
                                        SharedPreferencesUtil
                                                .saveStringData(ShopDetailsActivity.this,
                                                        SignListAdapter.signIndex + Pref.ISFORCELOOKLIMITNUM
                                                                + YCache.getCacheUser(context).getUser_id(),
                                                        "" + forcelookLimitNum);

                                    } else {

                                        if (forcelookLimitNum % Integer.parseInt(singvalue) + 1 < Integer.parseInt(singvalue)) {
                                            ToastUtil.showMyToast(ShopDetailsActivity.this,
                                                    "再浏览" + (Integer.parseInt(singvalue) - (forcelookLimitNum % Integer.parseInt(singvalue) + 1)) + "次即可赢得"
                                                            + SignListAdapter.jiangliValue
                                                            + "元提现额度,继续努力~", 3000);
                                            forcelookLimitNum++;
                                            SharedPreferencesUtil
                                                    .saveStringData(ShopDetailsActivity.this,
                                                            SignListAdapter.signIndex + Pref.ISFORCELOOKLIMITNUM
                                                                    + YCache.getCacheUser(context).getUser_id(),
                                                            "" + forcelookLimitNum);

                                        } else if (forcelookLimitNum % Integer.parseInt(singvalue) + 1 == Integer.parseInt(singvalue)) {
                                            signLimit();
                                        }

                                    }

                                }

                                if (xunBaoIv != null && xunBaoIv.getVisibility() == View.VISIBLE) {
                                    xunBaoIv.setVisibility(View.INVISIBLE);
//									img_balance_lottery 图标在xunBaoIv 下面 xunBaoIv隐藏时候只能用INVISIBLE
                                }

                            }
                        }
                    } // 这些判断是强制浏览滑到最后一张才算一次的
                    position = position - images.length;
                }

                position = position * 2;
                vh.imageGroup.setVisibility(View.GONE);
                vh.shopItem.setVisibility(View.VISIBLE);


                if (mIsVip) {
                    vh.left.iniViewVip(dataList.get(position), mVipDikouData);

                } else {
                    vh.left.iniView(dataList.get(position));

                }


                vh.left.setOnClickListener(new MyOnClick(position));
                if (dataList.size() > position + 1) {
                    vh.right.setVisibility(View.VISIBLE);

                    if (mIsVip) {
                        vh.right.iniViewVip(dataList.get(position + 1), mVipDikouData);

                    } else {
                        vh.right.iniView(dataList.get(position + 1));

                    }


                    vh.right.setOnClickListener(new MyOnClick(position + 1));
                } else {
                    vh.right.setVisibility(View.INVISIBLE);
                }
            } else if (check == 1) {// 尺寸
                vh.mealRView.setVisibility(View.GONE);
                vh.imageTag.setVisibility(View.GONE);
                vh.diver.setVisibility(View.GONE);
                if ("SignShopDetail".equals(signShopDetail)) {

                    if (position < list.size()) {
                        vh.bai.setVisibility(View.GONE);
                        vh.sView.setVisibility(View.VISIBLE);
                        vh.sizeHint.setVisibility(View.GONE);
                        vh.imageGroup.setVisibility(View.GONE);
                        vh.shopItem.setVisibility(View.GONE);
                        vh.eView.setVisibility(View.GONE);
                        vh.sView.setContent(list.get(position), false, position);
                        return v;
                    }

                    if (position == list.size()) {
                        vh.bai.setVisibility(View.VISIBLE);
                        vh.sView.setVisibility(View.GONE);
                        vh.imageGroup.setVisibility(View.GONE);
                        vh.shopItem.setVisibility(View.GONE);
                        vh.eView.setVisibility(View.GONE);
                        vh.sizeHint.setVisibility(View.GONE);
                        // vh.sizeHint.setTag("system/shop_details.png");
                        // SetImageLoader.initImageLoader(null, vh.sizeHint,
                        // "system/shop_details.png", "!450");
                        return v;
                    }
                } else {

                    if (position == 0) {
                        vh.bai.setVisibility(View.GONE);
                        vh.sView.setVisibility(View.VISIBLE);
                        vh.sizeHint.setVisibility(View.GONE);
                        vh.imageGroup.setVisibility(View.GONE);
                        vh.shopItem.setVisibility(View.GONE);
                        vh.eView.setVisibility(View.GONE);
                        vh.sView.setContent(shop, false, position);
                        return v;
                    }

                    if (position == 1) {
                        vh.bai.setVisibility(View.GONE);
                        vh.sView.setVisibility(View.GONE);
                        vh.imageGroup.setVisibility(View.GONE);
                        vh.shopItem.setVisibility(View.GONE);
                        vh.eView.setVisibility(View.GONE);
                        vh.sizeHint.setVisibility(View.VISIBLE);
                        vh.sizeHint.setTag("system/shop_details.png");
                        if (width > 720) {
                            PicassoUtils.initImage(ShopDetailsActivity.this, "system/shop_details.png" + "!450",
                                    vh.sizeHint);
                        } else {
                            PicassoUtils.initImage(ShopDetailsActivity.this, "system/shop_details.png" + "!382",
                                    vh.sizeHint);
                        }

                        return v;
                    }

                }

            } else {// 评论

                if ("SignShopDetail".equals(signShopDetail)) {
                    vh.sView.setVisibility(View.GONE);
                    vh.eView.setVisibility(View.GONE);
                    vh.diver.setVisibility(View.GONE);
                    vh.sizeHint.setVisibility(View.GONE);
                    vh.imageTag.setVisibility(View.GONE);
                    vh.imageGroup.setVisibility(View.GONE);
                    vh.shopItem.setVisibility(View.GONE);
                    if (tuijianList == null) {
                        vh.bai.setVisibility(View.VISIBLE);
                        vh.mealRView.setVisibility(View.GONE);
                    } else {
                        vh.bai.setVisibility(View.GONE);
                        vh.mealRView.setVisibility(View.VISIBLE);
                        position = position * 2;
                        if (position == tuijianList.size() - 1 || position == tuijianList.size() - 2) {
                            vh.mealRView.setData(tuijianList.get(position),
                                    tuijianList.size() == position + 1 ? null : tuijianList.get(position + 1), true);
                        } else {
                            vh.mealRView.setData(tuijianList.get(position),
                                    tuijianList.size() == position + 1 ? null : tuijianList.get(position + 1), false);
                        }
                    }
                } else {
                    vh.mealRView.setVisibility(View.GONE);
                    vh.imageTag.setVisibility(View.GONE);
                    vh.eView.setVisibility(View.VISIBLE);
                    vh.sView.setVisibility(View.GONE);
                    vh.imageGroup.setVisibility(View.GONE);
                    vh.shopItem.setVisibility(View.GONE);
                    vh.sizeHint.setVisibility(View.GONE);
                    if (position == 0) {
                        vh.evaView.setVisibility(View.VISIBLE);
                        vh.viewContainer.setVisibility(View.GONE);
                        vh.lin_nodata.setVisibility(View.GONE);
                        vh.diver.setVisibility(View.GONE);
                        vh.bai.setVisibility(View.GONE);

                        if ("SignShopDetail".equals(signShopDetail)) {
                            if (mealMap.get("eva_count").equals("0")) {
                                // vh.tv_color_count.setText("100%");
                                // vh.tv_type_count.setText("100%");
                                // vh.tv_work_count.setText("100%");
                                // vh.tv_cost_count.setText("100%");

                                vh.pb_color_count.setProgress(100);
                                vh.pb_type_count.setProgress(100);
                                vh.pb_work_count.setProgress(100);
                                vh.pb_cost_count.setProgress(100);
                            } else {
                                float color_count = Float.parseFloat(mealMap.get("color_count").toString());
                                float work_count = Float.parseFloat(mealMap.get("work_count").toString());
                                float type_count = Float.parseFloat(mealMap.get("type_count").toString());
                                float cost_count = Float.parseFloat(mealMap.get("cost_count").toString());
                                float eva_count = Float.parseFloat(mealMap.get("eva_count").toString());


                                vh.pb_color_count.setProgress(eva_count == 0 ? 100
                                        : ((int) (color_count / eva_count * 100) > 100 ? 100
                                        : (int) (color_count / eva_count * 100)));
                                vh.pb_type_count.setProgress(eva_count == 0 ? 100
                                        : ((int) (type_count / eva_count * 100) > 100 ? 100
                                        : (int) (type_count / eva_count * 100)));
                                vh.pb_work_count.setProgress(eva_count == 0 ? 100
                                        : ((int) (work_count / eva_count * 100) > 100 ? 100
                                        : (int) (work_count / eva_count * 100)));
                                vh.pb_cost_count.setProgress(eva_count == 0 ? 100
                                        : ((int) (cost_count / eva_count * 100) > 100 ? 100
                                        : (int) (cost_count / eva_count * 100)));
                            }
                        } else {
                            if (shop.getEva_count() == 0) {
                                // vh.tv_color_count.setText("100%");
                                // vh.tv_type_count.setText("100%");
                                // vh.tv_work_count.setText("100%");
                                // vh.tv_cost_count.setText("100%");

                                vh.pb_color_count.setProgress(100);
                                vh.pb_type_count.setProgress(100);
                                vh.pb_work_count.setProgress(100);
                                vh.pb_cost_count.setProgress(100);
                            } else {


                                vh.pb_color_count.setProgress(shop.getEva_count() == 0 ? 100
                                        : ((int) (shop.getColor_count() / shop.getEva_count() * 100) > 100 ? 100
                                        : (int) (shop.getColor_count() / shop.getEva_count() * 100)));
                                vh.pb_type_count.setProgress(shop.getEva_count() == 0 ? 100
                                        : ((int) (shop.getType_count() / shop.getEva_count() * 100) > 100 ? 100
                                        : (int) (shop.getType_count() / shop.getEva_count() * 100)));
                                vh.pb_work_count.setProgress(shop.getEva_count() == 0 ? 100
                                        : ((int) (shop.getWork_count() / shop.getEva_count() * 100) > 100 ? 100
                                        : (int) (shop.getWork_count() / shop.getEva_count() * 100)));
                                vh.pb_cost_count.setProgress(shop.getEva_count() == 0 ? 100
                                        : ((int) (shop.getCost_count() / shop.getEva_count() * 100) > 100 ? 100
                                        : (int) (shop.getCost_count() / shop.getEva_count() * 100)));
                            }
                        }

                        return v;
                    }
                    if (position == 1 && (listShopComments == null || listShopComments.isEmpty())) {
                        vh.viewContainer.setVisibility(View.GONE);
                        vh.lin_nodata.setVisibility(View.VISIBLE);
                        vh.evaView.setVisibility(View.GONE);
                        vh.bai.setVisibility(View.GONE);
                        vh.diver.setVisibility(View.GONE);
                        return v;
                    }
                    if (position == 2 && (listShopComments == null || listShopComments.isEmpty())) {
                        vh.viewContainer.setVisibility(View.GONE);
                        vh.lin_nodata.setVisibility(View.GONE);
                        vh.evaView.setVisibility(View.GONE);
                        vh.bai.setVisibility(View.VISIBLE);
                        vh.diver.setVisibility(View.GONE);
                        return v;
                    }
                    if (position == 1 && listShopCommentsYouXuan.size() > 0) {
                        vh.youxuanComments.setText("优选点评");
                        vh.youxuanComments.setVisibility(View.VISIBLE);
                        vh.newLine.setVisibility(View.VISIBLE);
                    } else if (position == listShopCommentsYouXuan.size() + 1 && listShopComments != null
                            && listShopComments.size() + 1 != position) {
                        vh.youxuanComments.setText("更多点评");
                        vh.youxuanComments.setVisibility(View.VISIBLE);
                        vh.newLine.setVisibility(View.VISIBLE);
                    } else {
                        vh.youxuanComments.setVisibility(View.GONE);
                        vh.newLine.setVisibility(View.GONE);
                    }
                    position = position - 1;
                    if (position == listShopComments.size()) {
                        vh.viewContainer.setVisibility(View.GONE);
                        vh.lin_nodata.setVisibility(View.GONE);
                        vh.evaView.setVisibility(View.GONE);
                        vh.bai.setVisibility(View.VISIBLE);
                        vh.diver.setVisibility(View.GONE);
                        return v;
                    }
                    ShopComment shopComment = listShopComments.get(position);
                    vh.viewContainer.setVisibility(View.VISIBLE);
                    vh.lin_nodata.setVisibility(View.GONE);
                    vh.evaView.setVisibility(View.GONE);
                    vh.bai.setVisibility(View.GONE);
                    vh.diver.setVisibility(View.VISIBLE);
                    vh.img_user_header.setTag(shopComment.getUser_url());

                    PicassoUtils.initImage(ShopDetailsActivity.this, shopComment.getUser_url(), vh.img_user_header);
                    String user_name = shopComment.getUser_name();
                    if (!TextUtils.isEmpty(user_name)) {

                        if (user_name.length() == 1) {
                            user_name = user_name + "****";
                        }

                        vh.tv_user.setText(user_name);
                    }
                    int comment_type = shopComment.getComment_type();

                    if (comment_type == 1) {
                        vh.tv_evaluate.setText("好评");

                    } else if (comment_type == 2) {
                        vh.tv_evaluate.setText("中评");
                    } else if (comment_type == 3) {
                        vh.tv_evaluate.setText("差评");
                    }

                    vh.bar.setRating(((float) shopComment.getStar()));

                    long add_date = shopComment.getAdd_date();
                    String date = StringUtils.timeToDate(add_date);
                    if (!TextUtils.isEmpty(date)) {
                        vh.tv_date.setText(date);
                    }

                    String content = shopComment.getContent();
                    if (!TextUtils.isEmpty(content)) {
                        vh.tv_descri.setText(content);
                    }

                    String shop_color = shopComment.getShop_color();
                    String shop_size = shopComment.getShop_size();
                    if (!TextUtils.isEmpty(shop_color)) {
                        vh.tv_size_color.setText("颜色：" + shop_color + "  尺码：" + shop_size);
                    }
                    String pic = shopComment.getPic();
                    vh.img_container.removeAllViews();
                    if (!TextUtils.isEmpty(pic)) {
                        LayoutParams params = new LayoutParams(
                                (width - DP2SPUtil.dp2px(ShopDetailsActivity.this, 110)) / 3,
                                (width - DP2SPUtil.dp2px(ShopDetailsActivity.this, 114)) / 3);
                        params.setMargins(0, 0, DP2SPUtil.dp2px(ShopDetailsActivity.this, 8), 0);
                        final String[] picList = pic.split(",");

                        // final ImageView[] img = new
                        // ImageView[picList.length];
                        for (int j = 0; j < picList.length; j++) {
                            ImageView img = new ImageView(ShopDetailsActivity.this);
                            img.setLayoutParams(params);
                            img.setScaleType(ScaleType.CENTER_CROP);
                            // SetImageLoader.initImageLoader(null, img,
                            // picList[j], "!180");
                            PicassoUtils.initImage(ShopDetailsActivity.this, picList[j] + "!180", img);
                            img.setOnClickListener(new ImageOnClickLintener(j, picList));
                            vh.img_container.addView(img);
                        }

                    }

                    if (null != shopComment.getSuppComment()) {
                        vh.tv_one_reply.setVisibility(View.VISIBLE);
                        vh.tv_one_reply.setText(Html.fromHtml(ShopDetailsActivity.this.getString(R.string.tv_supp_reply,
                                shopComment.getSuppComment().get(0).getSupp_content())));
                    } else {
                        vh.tv_one_reply.setVisibility(View.GONE);
                    }

                    if (null != shopComment.getComment()) {

                        vh.lin_second.setVisibility(View.VISIBLE);
                        vh.tv_second_judge.setVisibility(View.VISIBLE);
                        vh.tv_second_judge.setText(Html.fromHtml(ShopDetailsActivity.this
                                .getString(R.string.tv_add_judge, shopComment.getComment().get(0).getContent())));
                        if (null != shopComment.getSuppEndComment() && shopComment.getSuppEndComment().size() > 0) {
                            vh.tv_second_reply.setVisibility(View.VISIBLE);
                            vh.tv_second_reply
                                    .setText(Html.fromHtml(ShopDetailsActivity.this.getString(R.string.tv_supp_reply,
                                            shopComment.getSuppEndComment().get(0).getSupp_content())));
                        } else {
                            vh.tv_second_reply.setVisibility(View.GONE);
                        }
                        String pics = shopComment.getComment().get(0).getPic();
                        if (TextUtils.isEmpty(pics) == false) {
                            final String[] spic = pics.split(",");
                            vh.two_container.setVisibility(View.VISIBLE);
                            vh.two_container.removeAllViews();
                            LayoutParams params = new LayoutParams(
                                    (width - DP2SPUtil.dp2px(ShopDetailsActivity.this, 110)) / 3,
                                    (width - DP2SPUtil.dp2px(ShopDetailsActivity.this, 114)) / 3);
                            params.setMargins(0, 0, DP2SPUtil.dp2px(ShopDetailsActivity.this, 8), 0);
                            for (int j = 0; j < spic.length; j++) {
                                ImageView img = new ImageView(ShopDetailsActivity.this);
                                img.setLayoutParams(params);
                                img.setScaleType(ScaleType.CENTER_CROP);
                                // SetImageLoader.initImageLoader(null, img,
                                // spic[j], "!180");
                                PicassoUtils.initImage(ShopDetailsActivity.this, spic[j] + "!180", img);
                                img.setOnClickListener(new ImageOnClickLintener(j, spic));
                                vh.two_container.addView(img);
                            }
                        } else {
                            vh.two_container.setVisibility(View.GONE);
                        }

                    } else {
                        vh.lin_second.setVisibility(View.GONE);
                        vh.tv_second_judge.setVisibility(View.GONE);
                    }
                }
            }
            return v;
        }

        /**
         * 浏览商品详情的签到接口
         */
        private void sign() {
            String ssType = "";
            switch (SignListAdapter.jiangliID) {
                case 3:
                    ssType = "元优惠券";
                    break;
                case 4:
                    ssType = "积分";
                    break;
                case 5:
                    ssType = "元";
                    break;
                case 11:// 新加 奖励衣豆
                    ssType = "个衣豆";
                    break;

                default:
                    break;
            }
            final String ss = ssType;
            new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) context, 0) {

                @Override
                protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                        throws Exception {

                    return ComModel2.getSignIn(ShopDetailsActivity.this, false, false, SignListAdapter.signIndex,
                            SignListAdapter.doClass);

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
                            SignCompleteDialogUtil.firstClickInGoToZP(instance);
                            return;
                        }

                        if (SignListAdapter.doNum > 1) {// 需要奖励分多次发放
                            if (isforcelook) {// 强制浏览普通商品
                                SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
                                        "forcelookNowTime" + YCache.getCacheUser(context).getUser_id(),
                                        df.format(new Date()));
                                SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, SignListAdapter.signIndex
                                                + "forcelookNum" + YCache.getCacheUser(context).getUser_id(),
                                        "" + forcelookNum);

                                if (forcelookNum < Integer.parseInt(singvalue)) {// 小于要求的浏览次数
                                    ToastUtil.showLongText(ShopDetailsActivity.this,
                                            "浏览完成，奖励"
                                                    // +new
                                                    // java.text.DecimalFormat("#0.00").format(Double.valueOf(SignListAdapter.jiangliValue)/Integer.parseInt(singvalue))
                                                    + SignListAdapter.jiangliValue + ss + ",还有"
                                                    + (Integer.parseInt(singvalue) - forcelookNum) + "次浏览机会喔~");
                                } else if (forcelookNum >= Integer.parseInt(singvalue)) {
                                    NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(ShopDetailsActivity.this,
                                            R.style.DialogQuheijiao2, "liulan_sign_finish",
                                            new DecimalFormat("0.##").format(
                                                    Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum)
                                                    + ss);
                                    dialog.show();

                                }
                            } else if (isforcelookMatch) {// 强制浏览搭配商品
                                SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
                                        "forcelookMatchNowTime" + YCache.getCacheUser(context).getUser_id(),
                                        df.format(new Date()));
                                SharedPreferencesUtil
                                        .saveStringData(ShopDetailsActivity.this,
                                                SignListAdapter.signIndex + "forcelookMatchNum"
                                                        + YCache.getCacheUser(context).getUser_id(),
                                                "" + forcelookMatchNum);

                                if (forcelookMatchNum < Integer.parseInt(singvalue)) {// 小于要求的分享次数
                                    ToastUtil.showLongText(ShopDetailsActivity.this,
                                            "浏览完成，奖励"
                                                    // +new
                                                    // java.text.DecimalFormat("#0.00").format(Double.valueOf(SignListAdapter.jiangliValue)/Integer.parseInt(singvalue))
                                                    + SignListAdapter.jiangliValue + ss + ",还有"
                                                    + (Integer.parseInt(singvalue) - forcelookMatchNum) + "次浏览机会喔~");
                                } else if (forcelookMatchNum >= Integer.parseInt(singvalue)) {
                                    NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(ShopDetailsActivity.this,
                                            R.style.DialogQuheijiao2, "liulan_sign_finish",
                                            new DecimalFormat("0.##").format(
                                                    Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum)
                                                    + ss);
                                    dialog.show();

                                }
                            } else if (isSignActiveShop) {// 强制浏览活动商品

                                SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
                                        "signActiveShopNowTime" + YCache.getCacheUser(context).getUser_id(),
                                        df.format(new Date()));
                                SharedPreferencesUtil
                                        .saveStringData(ShopDetailsActivity.this,
                                                SignListAdapter.signIndex + "signActiveShopNum"
                                                        + YCache.getCacheUser(context).getUser_id(),
                                                "" + signActiveShopNum);

                                if (signActiveShopNum < Integer.parseInt(singvalue)) {// 小于要求的分享次数
                                    ToastUtil.showLongText(ShopDetailsActivity.this,
                                            "浏览完成，奖励"
                                                    // +new
                                                    // java.text.DecimalFormat("#0.00").format(Double.valueOf(SignListAdapter.jiangliValue)/Integer.parseInt(singvalue))
                                                    + SignListAdapter.jiangliValue + ss + ",还有"
                                                    + (Integer.parseInt(singvalue) - signActiveShopNum) + "次浏览机会喔~");
                                } else if (signActiveShopNum >= Integer.parseInt(singvalue)) {

                                    SignCompleteDialogUtil.showSignCompleteLiuLanCount(ShopDetailsActivity.this, SignListAdapter.jiangliValue);


//                                    NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(ShopDetailsActivity.this,
//                                            R.style.DialogQuheijiao2, "liulan_sign_finish",
//                                            new DecimalFormat("0.##").format(
//                                                    Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum)
//                                                    + ss);
//                                    dialog.show();

                                }
                            }

                        } else {

                            SignCompleteDialogUtil.showSignCompleteLiuLanCount(ShopDetailsActivity.this, SignListAdapter.jiangliValue);


                            // 其他奖励 一次性奖励
//                            NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(ShopDetailsActivity.this,
//                                    R.style.DialogQuheijiao2, "liulan_sign_finish", SignListAdapter.jiangliValue + ss);
//                            dialog.show();

                        }
                    }
                }

            }.execute();
        }

        /**
         * 浏览商品详情的签到接口 新增的奖励提现额度浏览任务
         */
        private void signLimit() {
            final String ss = "元提现额度";
            new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) context, 0) {

                @Override
                protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                        throws Exception {

                    return ComModel2.getSignIn(ShopDetailsActivity.this, false, false, SignListAdapter.signIndex,
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


                            SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
                                    "nowTimeForcelookLimit" + YCache.getCacheUser(context).getUser_id(),
                                    df.format(new Date()));


                            String ts = SignListAdapter.jiangliValue + "元提现现金已经发放，到账时间为3-5个工作日,请耐心等待。再浏览"
                                    + (Integer.parseInt(singvalue) + "次可再赢得" + SignListAdapter.jiangliValue + "元提现现金"
                                    + ",继续努力!");

                            ToastUtil.showMyToast(ShopDetailsActivity.this, ts, 6000);


                            forcelookLimitNum++;

                            if (Integer.valueOf(result.get("isNewbie01") + "") == 1) {
                                SignCompleteDialogUtil.firstClickInGoToZP(instance);
                                return;
                            }

                            SharedPreferencesUtil
                                    .saveStringData(ShopDetailsActivity.this,
                                            SignListAdapter.signIndex + Pref.ISFORCELOOKLIMITNUM
                                                    + YCache.getCacheUser(context).getUser_id(),
                                            "" + forcelookLimitNum);

                        }

                    }
                }

            }.execute();
        }

        @Override
        public View getHeaderView(int position, View view, ViewGroup parent) {
            HeaderViewHolder vh;
            if (view == null) {
                vh = new HeaderViewHolder();
                view = LayoutInflater.from(ShopDetailsActivity.this).inflate(R.layout.header_item, parent, false);
                vh.topOne = (ShopTopClickView) view.findViewById(R.id.top_one);
                if ("SignShopDetail".equals(signShopDetail)) {
                    vh.topOne.setText();
                } else {
                    vh.topOne.setText2(setEva_count_z);
                }

                vh.topTwo = (ShowHoriontalView) view.findViewById(R.id.top_two);
                vh.topOne.setCheckLintener(ShopDetailsActivity.this);
                vh.topTwo.setOnClickLintener(ShopDetailsActivity.this);
                vh.title = (TextView) view.findViewById(R.id.title);
                vh.topOne.setBackgroundColor(Color.WHITE);
                vh.title.setBackgroundColor(Color.WHITE);
                vh.topTwo.setBackgroundColor(Color.WHITE);

                // vh.title.setVisibility(view.GONE);
                vh.topTwo.setList(listTitle);
                view.setTag(vh);
            } else {
                vh = (HeaderViewHolder) view.getTag();
            }

            if ("SignShopDetail".equals(signShopDetail)) {

                if (position < imageTag1.length + imageTag2.length + imageTag3.length) {
                    vh.topOne.setVisibility(View.VISIBLE);
                    vh.topOne.setIndex(check);
                    vh.title.setText("商品介绍");
                    vh.topTwo.setVisibility(View.GONE);
                    isShopTitle = false;
                    return view;
                }

            } else {
                if (position < images.length) {
                    vh.topOne.setVisibility(View.VISIBLE);
                    vh.topOne.setIndex(check);
                    vh.title.setText("商品介绍");
                    vh.topTwo.setVisibility(View.GONE);
                    isShopTitle = false;
                    return view;
                }
            }
            isShopTitle = true;
            vh.title.setText("商品推荐");
            vh.topOne.setVisibility(View.GONE);
            vh.topTwo.setVisibility(View.VISIBLE);
            vh.topTwo.setIndex(titleCheck);
            return view;
        }

        @Override
        public long getHeaderId(int position) {
            if (check != 0) {
                return 0;
            }
            if ("SignShopDetail".equals(signShopDetail)) {
                if (position < imageTag1.length + imageTag2.length + imageTag3.length) {


                    return 0;
                } else {


                    return 1;
                }

            } else {
                if (position < images.length) {

                    return 0;
                } else {

                    return 1;
                }

            }
        }

    }

    private static class ItemViewHolder {

        View imageGroup;
        View shopItem;
        ItemView left;
        ItemView right;
        ImageView image, imageTag;

        SizeView sView;
        SizeView2 sView2;
        View eView;
        CircleProgressView pb_color_count, pb_type_count, pb_work_count, pb_cost_count;// 没有色差，
        // 版型好看，
        // 做工不错，
        // 性价比好
        LinearLayout progressContain;// 进度条父容器
        TextView youxuanComments, moreComments;
        View newLine, moreLine;
        // TextView tv_color_count, tv_type_count, tv_work_count, tv_cost_count;

        LinearLayout viewContainer;

        LinearLayout lin_nodata;

        View bai;

        RoundImageButton img_user_header;
        TextView tv_user;
        TextView tv_evaluate;
        TextView tv_date;
        TextView tv_descri;
        TextView tv_size_color;
        LinearLayout img_container;

        TextView tv_one_reply;
        TextView tv_second_judge;
        TextView tv_second_reply;
        LinearLayout lin_second;

        RatingBar bar;

        View evaView;

        View diver;

        ImageView sizeHint;

        LinearLayout two_container;

        MealRecomenView mealRView;
    }

    private class ImageOnClickLintener implements OnClickListener {

        private int position;

        private String[] urls;

        public ImageOnClickLintener(int position, String[] urls) {
            super();
            this.position = position;
            this.urls = urls;
        }

        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(context, ImagePagerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putStringArray("urls", urls);
            bundle.putInt("index", position);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

    private static class HeaderViewHolder {

        ShopTopClickView topOne;

        ShowHoriontalView topTwo;

        TextView title;
    }


    private RelativeLayout rrr;

    private String singvalue;// 强制浏览数量
    private int forcelookNum;// 强制浏览的计数
    private int forcelookMatchNum;// 搭配商品强制浏览的计数
    private int forcelookLimitNum;// 浏览奖励提现额度 强制浏览的计数
    private int signActiveShopNum;// 活动商品强制浏览的计数
    private String mSignGroupsPeopleCount = "10";
    private String mSignGroupsPrice = "9.9";

    //1元购相关
    private LinearLayout ll_kefu_red; //客服
    private LinearLayout ll_onlyshop_red;//单独购买
    private LinearLayout ll_oneshop_red;//1元购买
    private TextView tv_onlyshop_red;
    private TextView tv_onlyshop_price;
    private TextView tv_onlyshop_bt_text;
    private TextView tv_onlyshop_red_text;


    private String pos;

    private HashMap<String, Object> mealMap;

    public static int shopTask = 0;
    public static int everyDayTask1_2 = 0;

    // private Handler handler ;

    /**
     * 查询套餐详情
     *
     * @param code
     */

    // private double sePrice;

    private boolean isAnim = false;

    private String code;


    private void downloadPic(String picPath, int i) {
        try {
            URL url = new URL(YUrl.imgurl + picPath);
            // 打开连接
            URLConnection con = url.openConnection();
            // 获得文件的长度
            int contentLength = con.getContentLength();
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[8192];
            // 读取到的数据长度
            int len;
            // 输出的文件流 /sdcard/yssj/
            File file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(i)) + ".jpg");
            if (file.exists()) {
                file.delete();
            }
            LogYiFu.e("TAG", "多分享选择下载的图片。。。。");
            OutputStream os = new FileOutputStream(file);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            LogYiFu.i("TAG", "下载完毕。。。file=" + file.toString());
            // 完毕，关闭所有链接
            os.close();
            is.close();
        } catch (Exception e) {
            LogYiFu.e("TAG", "下载失败");
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.activityStack.remove(this);
        isPause = 1;

        unregisterReceiver(oneBuyReceiver);
        // instance = null;
    }


    /***
     * 得到屏幕宽度和高度 像素
     */
    private void getWindownPixes() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
    }

    private Handler handler;

    private Handler newHandler;
    private Handler shareHandler;
    private TextView tv_buy_now;
    private TextView tv_fenxiang;


    private void initView() {
        mSinglePrice = (TextView) findViewById(R.id.tv_single_price);
        mTwoPrice = (TextView) findViewById(R.id.tv_two_price);
        mTvPeopleCount = (TextView) findViewById(R.id.group_people_count);
        mGroupPrice = (TextView) findViewById(R.id.tv_group_price);
        mLlActivity = (LinearLayout) findViewById(R.id.activity_ll_two);
        mSingleBuy = (LinearLayout) findViewById(R.id.activity_ll_single_buy);
        mTwoBuy = (LinearLayout) findViewById(R.id.activity_ll_two_buy);
        mGroupBuy = (LinearLayout) findViewById(R.id.activity_ll_group_buy);
        mNomarBottom = (LinearLayout) findViewById(R.id.ll_abc);
        mActivityBottom = (LinearLayout) findViewById(R.id.activity_ll_all_bottom);
        mRlAddShopCart = (RelativeLayout) findViewById(R.id.rl_add_shop_cart);
        mShuaixuanNew = (ImageButton) findViewById(R.id.shaixuan);
        toDuoBaoIv = (ImageView) findViewById(R.id.to_duobao);
        rrr = (RelativeLayout) findViewById(R.id.rrr);
        redShare = (LinearLayout) findViewById(R.id.red_share_ll);
        moneyShare = (ImageView) findViewById(R.id.money_share_iv);
        tv_buy_now = (TextView) findViewById(R.id.tv_buy_now);
        tv_buy_now.setOnClickListener(this);
        tv_fenxiang = (TextView) findViewById(R.id.tv_fenxiang);// 右边的分享
        tv_fenxiang.setOnClickListener(this);


        // rrr.setBackgroundColor(Color.WHITE);
        if (mIsGroup || isSignActiveShop) {
            tv_fenxiang.setVisibility(View.GONE);
        } else {
            tv_fenxiang.setVisibility(View.VISIBLE);
        }

        if (!"SignShopDetail".equals(signShopDetail)) {// 打个标记 这里以后节能改
            rl_hava_twenty = (RelativeLayout) findViewById(R.id.rl_hava_twenty);
            rl_hava_twenty.getBackground().setAlpha(130);
        }

        rlBottom = (RelativeLayout) findViewById(R.id.ray_bottom);

        tv_shop_car_fake = (TextView) findViewById(R.id.tv_shop_car_fake);
        tv_shop_car = (TextView) findViewById(R.id.tv_shop_car);
        if (isSignActiveShop) {
            tv_shop_car_fake.setText("立即购买");
            tv_shop_car.setText("立即购买");
        }
        tv_shop_car.setVisibility(View.GONE);
        tv_shop_car.setOnClickListener(this);
        if (!"SignShopDetail".equals(signShopDetail)) {// 打个标记 这里以后节能改

        }
        sign_buy = (TextView) findViewById(R.id.sign_buy);
        sign_buy.setOnClickListener(this);

        lin_add_like = (LinearLayout) findViewById(R.id.lin_add_like);
        lin_add_like.setOnClickListener(this);

        if ("SignShopDetail".equals(signShopDetail)) {
            img_cart_old = (ImageView) findViewById(R.id.img_cart);
            img_cart_old.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent3 = new Intent(ShopDetailsActivity.this, ShopCartNewNewActivity.class);

                    intent3.putExtra("where", "0");

                    startActivityForResult(intent3, 235);

                }
            });
        } else {
            img_cart = (RelativeLayout) findViewById(R.id.img_cart);
            img_cart2 = (RelativeLayout) findViewById(R.id.img_cart2);
            img_cart.setOnClickListener(this);
            img_cart2.setOnClickListener(this);
        }

        findViewById(R.id.ray_bottom).setBackgroundColor(Color.WHITE);
        tv_cart_count = (TextView) findViewById(R.id.tv_cart_count);// 购物车计数
        tv_cart_count2 = (TextView) findViewById(R.id.tv_cart_count2);// 购物车计数

        if (!"SignShopDetail".equals(signShopDetail)) {
            tv_time_count_down = (TextView) findViewById(R.id.tv_time_count_down);// 购物车数量旁的倒计时
            tv_time_count_down.setVisibility(View.GONE);
            tv_time_count_down2 = (TextView) findViewById(R.id.tv_time_count_down2);// 购物车数量旁的倒计时
            tv_time_count_down2.setVisibility(View.GONE);

            tv_time_count_down_meal = (TextView) findViewById(R.id.tv_time_count_down_meal);// 套餐购物车数量旁的倒计时
            tv_time_count_down_meal.setVisibility(View.GONE);
        }

        if ("SignShopDetail".equals(signShopDetail)) {
            lin_contact_old = (RelativeLayout) findViewById(R.id.lin_contact);
            lin_contact_old.setOnClickListener(this);

        } else {
            lin_contact = (ImageView) findViewById(R.id.lin_contact);
            lin_contact.setOnClickListener(this);
            mShuaixuanNew = (ImageButton) findViewById(R.id.shaixuan);
        }

        if ("SignShopDetail".equals(signShopDetail)) {
            img_fenx_old = (ImageView) findViewById(R.id.img_fenx);// 分享
            img_fenx_old.setOnClickListener(this);
        } else {
            img_fenx = (RelativeLayout) findViewById(R.id.img_fenx);// 分享
            img_fenx.setOnClickListener(this);
        }


        if (!"SignShopDetail".equals(signShopDetail)) {

            ll_bottem = (LinearLayout) findViewById(R.id.ll_bottem);

            rl_retain = (RelativeLayout) findViewById(R.id.rl_retain); // 保留30秒钟的黑色条
            rl_retain.setOnClickListener(this);
            rl_retain.getBackground().setAlpha(204);
        }

        img_addxin = (ImageView) findViewById(R.id.addxin);

        img_back = (LinearLayout) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        ShareActionProvider provider = new ShareActionProvider(this);
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "不错的商品哦，值得你一看，赶紧来吧！！！");
        sendIntent.setType("text/plain");
        sendIntent.setData(Uri.parse(YUrl.QUERY_SHOP_DETAILS));
        provider.setShareIntent(sendIntent);

        mListView = (StickyListHeadersListView) findViewById(R.id.data);
        rlTop = (LinearLayout) findViewById(R.id.ray_top);

        rlTop.setBackgroundResource(R.drawable.zhezhao2x);


        baoyou_animationGone = AnimationUtils.loadAnimation(ShopDetailsActivity.this, R.anim.shop_bottom_gone);
        baoyou_animationShow = AnimationUtils.loadAnimation(ShopDetailsActivity.this, R.anim.shop_bottom_show);

        baoyou_animationGone.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isAnim = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rlBottom.post(new Runnable() {

                    @Override
                    public void run() {
                        rlBottom.setVisibility(View.GONE);
                    }
                });
                isAnim = false;
            }
        });
        baoyou_animationShow.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isAnim = true;
                rlBottom.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnim = false;
            }
        });
    }

    public int imageHeight;

    private List<ShopComment> listShopComments;
    private List<ShopComment> listShopCommentsYouXuan = new ArrayList<ShopComment>();

    /***
     * 刷新界面
     *
     */

    private class MyOnClick implements OnClickListener {

        private int position;

        public MyOnClick(int position) {
            super();
            this.position = position;
        }

        @Override
        public void onClick(View arg0) {
            HashMap<String, Object> posMap = dataList.get(position);

            ShopDetailsActivity.this.getSharedPreferences("YSSJ_yf", Context.MODE_PRIVATE).edit()
                    .putBoolean("isGoDetail", true).commit();
            if (YJApplication.instance.isLoginSucess()) {
                addScanDataTo((String) posMap.get("shop_code"));
            }
            Intent intent = new Intent(ShopDetailsActivity.this, ShopDetailsActivity.class);
            intent.putExtra("code", (String) posMap.get("shop_code"));
            intent.putExtra("shopCarFragment", "shopCarFragment");

            startActivity(intent);
            finish();

            ((FragmentActivity) ShopDetailsActivity.this).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

        }
    }

    /*
     * 把浏览过的数据添加进数据库
     */
    private void addScanDataTo(final String shop_code) {
        new SAsyncTask<Void, Void, ReturnInfo>(ShopDetailsActivity.this) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel.addMySteps(context, shop_code);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                super.onPostExecute(context, result, e);
            }

        }.execute();
    }

    private boolean clickFlag = true;// 加入购物车能否点击的标识

    @Override
    public void onClick(View view) {
        if (YJApplication.instance.isLoginSucess() == false) {
            if (view.getId() == R.id.img_back) {
                onBackPressed();
                return;
            }

            if (LoginActivity.instances != null) {
                LoginActivity.instances.finish();
            }

            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("login_register", "login");
            ((FragmentActivity) context).startActivityForResult(intent, 235);
            return;
        }

        switch (view.getId()) {
            case R.id.lin_contact:
                addLikeShop(null);

                break;

            case R.id.tv_fenxiang:// 右边的分享 一键分享
                // MobclickAgent.onEvent(context, "shopdetailshareclick");
                double feedback = 0;
                if (null == shop) {
                    ToastUtil.showShortText(context, "操作无效");
                    return;
                }

//                showMyPopwindou(ShopDetailsActivity.this, feedback);

                shareShop();


                break;

            case R.id.tv_buy_now:
                //TODO:_MODIFY_不知道购买和签到任务还有关吗？注释掉查询签到任务代码，直接查询属性
                queryShopQueryAttr(0);
               /* if (number_sold != null && number_sold.equals("none")) {
                    return;
                } else {
                    Calendar c = Calendar.getInstance();
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    if (("" + YCache.getCacheToken(context) + "003").equals(SharedPreferencesUtil.getStringData(ShopDetailsActivity.this, Pref.DAY_BUY_TASK_DAYS, ""))) {
                        SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, Pref.DAY_BUY_IS_SHOW, "" + YCache.getCacheToken(context) + day + "true");
                        if (("" + YCache.getCacheToken(ShopDetailsActivity.this) + day + "01").equals(SharedPreferencesUtil.getStringData(context, Pref.DAY_BUY_CLICK_COUNTS, ""))) {
                            SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, Pref.DAY_BUY_CLICK_COUNTS, "" + YCache.getCacheToken(context) + day + "01");
                            queryShopQueryAttr(0);
                        } else {
                            SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, Pref.DAY_BUY_CLICK_COUNTS, "" + YCache.getCacheToken(context) + day + "01");
                            ToastUtil.showDialog(new LingYUANGOUTishiDialog(ShopDetailsActivity.this, R.style.DialogStyle1, true, ShopDetailsActivity.this));
                        }
                    } else {
                        getTaskDays();
                    }


                }*/
                //end

                break;
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.activity_ll_two_buy:// 拼团中自己发起2人团购买
                if (group_click_flag) {
                    rollCode = "1";
                    groupFlag = 1;
                    mIsTwoGroup = true;
                    queryShopQueryAttr(0); // 查询普通商品属性
                } else {
                    ToastUtil.showShortText(ShopDetailsActivity.this, "只能从赚钱任务页参与活动哦~");
                }
                break;
            case R.id.activity_ll_group_buy:// 拼团中组别人的团购买
                groupFlag = 2;
                rollCode = "" + r_code;
                mIsTwoGroup = false;
                queryShopQueryAttr(0); // 查询普通商品属性
                break;
            case R.id.activity_ll_single_buy:// 拼团中单个立即购买

            case R.id.tv_shop_car:// 活动商品立即购买 普通商品 加入购物车
                rollCode = "0";
                mIsTwoGroup = false;
                if (isSignActiveShop) {
                    groupFlag = 0;
                    queryShopQueryAttr(0); // 查询普通商品属性
                } else {
                    if (!clickFlag) {// 正在加入购物车时候的点击不执行
                        break;
                    }

                    int cart_count = shop.getCart_count();
                    if (cart_count >= 20) {
                        ThreeSecond();
                        break;
                    }

                    if (number_sold != null && number_sold.equals("none")) {
                        return;
                    } else {


                        queryShopQueryAttr(1); // 查询普通商品属性

                    }
                }
                break;
            case R.id.sign_buy:
                if (number_sold != null && number_sold.equals("none")) {
                    return;
                } else {
                    // MobclickAgent.onEvent(context, "tobuyclick");
                    // showPopWindow(1);
                    if ("SignShopDetail".equals(signShopDetail)) {
                        if (null == mealMap) {
                            ToastUtil.showShortText(context, "操作无效");
                            return;
                        }
                        queryMealShopAttrs(0, view);
                    } else {
                        queryShopQueryAttr(0);
                    }
                }

                break;
            case R.id.img_cart2:
            case R.id.img_cart:// 购物车
                // MobclickAgent.onEvent(context, "toshopcartclick");
                YunYingTongJi.yunYingTongJi(context, 106);// 商品详情页购物车
                Intent intent2 = new Intent(this, ShopCartNewNewActivity.class);

                intent2.putExtra("where", "0");

                startActivityForResult(intent2, 235);
                break;

            case R.id.lin_add_like: // 加喜好

                addLikeShop(null);

                break;
            case R.id.img_fenx:// 一键分享

                break;
            case R.id.rl_retain:
                // 跳转到购物车结算页面
                YunYingTongJi.yunYingTongJi(context, 107);
                Intent intent = new Intent(ShopDetailsActivity.this, ShopCartNewNewActivity.class);

                intent.putExtra("where", "0");

                startActivity(intent);
                break;
            //1元购相关
            case R.id.ll_kefu_red:
//                Intent intentOne;
//
//                intentOne = new Intent(this, KeFuActivity.class);
//                intentOne.putExtra("userId", SharedPreferencesUtil.getStringData(context, "kefuNB", "0"));
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("shop", shop);
//                if (null == shop) {
//                    ToastUtil.showShortText(context, "操作无效");
//                    return;
//                }
//                intentOne.putExtra("isSignActiveShop", isSignActiveShop);
//                intentOne.putExtras(bundle);
//                startActivity(intentOne);

                WXminiAppUtil.jumpToWXmini(this);


                break;
            case R.id.ll_onlyshop_red:

                if (number_sold != null && number_sold.equals("none")) {
                    return;
                } else {
                    queryShopQueryAttr(0);
                }

                break;
            case R.id.ll_oneshop_red:

//                queryShopQueryAttrOneBuy();

                break;


            default:
                break;
        }
    }

    private void shareShop() {


        try {
            shareWaitDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new SAsyncTask<String, Void, HashMap<String, String>>(this, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                if (isSignActiveShop) {
                    return ComModel2.getActiveShopLink(params[0], context, "true");
                } else {
                    return ComModel2.getShopLink(params[0], context, "true");
                }
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                isPause = 0;

                if (instance == null) {
                    return;
                }
                if (null == e) {
                    if (null != shareWaitDialog) {
                        shareWaitDialog.dismiss();
                    }
                    String four_pic = result.get("four_pic") + "";

                    if (isNewMeal) {
                        String shareMIniAPPimgPic = YUrl.imgurl + shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/" + shop.getDef_pic() + "!280";
                        String wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + shop.getShop_code() +
                                "&isShareFlag=true&user_id=" + YCache.getCacheUser(instance).getUser_id();

                        //分享到微信统一分享小程序
                        WXminiAPPShareUtil.shareShopToWXminiAPP(instance, shop.getShop_name(), shop.getAssmble_price(), shareMIniAPPimgPic, wxMiniPathdUO, false);


                    } else {
                        String shareMIniAPPimgPic = YUrl.imgurl + shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/" + four_pic.split(",")[2] + "!280";
                        String wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + shop.getShop_code() +
                                "&isShareFlag=true&user_id=" + YCache.getCacheUser(instance).getUser_id();
                        //分享到微信统一分享小程序
                        WXminiAPPShareUtil.shareShopToWXminiAPP(instance, shop.getShop_name(), shop.getAssmble_price(), shareMIniAPPimgPic, wxMiniPathdUO, false);

                    }

                    WXEntryActivity.setWXminiShareListener(new WXEntryActivity.WXminiAPPshareListener() {
                        @Override
                        public void wxMiniShareSuccess() {
                            ToastUtil.showShortText(instance, "分享成功");

                        }
                    });


                } else {
                    if (null != shareWaitDialog) {
                        shareWaitDialog.dismiss();
                    }
                }
            }

        }.execute(code);


    }


    /**
     * 获取签到任务天数
     */
    public void getTaskDays() {
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) context, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel2.getSignData(ShopDetailsActivity.this);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null) {

                    String current_date = (String) result.get("current_date");


                    Calendar c = Calendar.getInstance();
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    if ("newbie01".equals(current_date)) {//第一天
                        SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, Pref.DAY_BUY_TASK_DAYS, "" + YCache.getCacheToken(context) + "001");
                        SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, Pref.DAY_BUY_IS_SHOW, "" + YCache.getCacheToken(context) + day + "false");//true不弹
                        ToastUtil.showDialog(new LingYUANGOUTishiDialog(ShopDetailsActivity.this, R.style.DialogStyle1, true, ShopDetailsActivity.this));
                    } else if ("newbie02".equals(current_date)) {//第二天
                        SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, Pref.DAY_BUY_TASK_DAYS, "" + YCache.getCacheToken(context) + "002");
                        if (("" + YCache.getCacheToken(ShopDetailsActivity.this) + day + "01").equals(SharedPreferencesUtil.getStringData(context, Pref.DAY_BUY_CLICK_COUNTS, ""))) {
                            SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, Pref.DAY_BUY_IS_SHOW, "" + YCache.getCacheToken(context) + day + "false");
                            SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, Pref.DAY_BUY_CLICK_COUNTS, "" + YCache.getCacheToken(context) + day + "02");
                            ToastUtil.showDialog(new LingYUANGOUTishiDialog(ShopDetailsActivity.this, R.style.DialogStyle1, true, ShopDetailsActivity.this));
                        } else if (("" + YCache.getCacheToken(ShopDetailsActivity.this) + day + "02").equals(SharedPreferencesUtil.getStringData(context, Pref.DAY_BUY_CLICK_COUNTS, ""))) {
                            SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, Pref.DAY_BUY_IS_SHOW, "" + YCache.getCacheToken(context) + day + "true");
                            SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, Pref.DAY_BUY_CLICK_COUNTS, "" + YCache.getCacheToken(context) + day + "02");
                            queryShopQueryAttr(0);
                        } else {
                            SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, Pref.DAY_BUY_IS_SHOW, "" + YCache.getCacheToken(context) + day + "false");
                            SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, Pref.DAY_BUY_CLICK_COUNTS, "" + YCache.getCacheToken(context) + day + "01");
                            ToastUtil.showDialog(new LingYUANGOUTishiDialog(ShopDetailsActivity.this, R.style.DialogStyle1, true, ShopDetailsActivity.this));
                        }
                    } else {//其他
                        SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, Pref.DAY_BUY_TASK_DAYS, "" + YCache.getCacheToken(context) + "003");
                        SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, Pref.DAY_BUY_IS_SHOW, "" + YCache.getCacheToken(context) + day + "true");
                        if (("" + YCache.getCacheToken(ShopDetailsActivity.this) + day + "01").equals(SharedPreferencesUtil.getStringData(context, Pref.DAY_BUY_CLICK_COUNTS, ""))) {
                            SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, Pref.DAY_BUY_CLICK_COUNTS, "" + YCache.getCacheToken(context) + day + "01");
                            queryShopQueryAttr(0);
                        } else {
                            SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, Pref.DAY_BUY_CLICK_COUNTS, "" + YCache.getCacheToken(context) + day + "01");
                            ToastUtil.showDialog(new LingYUANGOUTishiDialog(ShopDetailsActivity.this, R.style.DialogStyle1, true, ShopDetailsActivity.this));
                        }
                    }
                } else {
                    String onPrice = GuideActivity.oneShopPrice;
                    String mPrice = new DecimalFormat("#0")
                            .format(Double.parseDouble(onPrice));
                    onPrice = new DecimalFormat("#0.0")
                            .format(Double.parseDouble(onPrice));


                    tv_onlyshop_price.setText("¥" + onPrice);
                    tv_onlyshop_bt_text.setText(mPrice + "元购买");
                    if (YJApplication.instance.isLoginSucess()) {
                        setDandugoumai(tv_onlyshop_red);
                    } else {

                    }

                    tv_onlyshop_red_text.setText("赚¥" + (ComputeUtil.div(shop.getShop_se_price(), 2, 1) > 50.0 ? 50.0 : ComputeUtil.div(shop.getShop_se_price(), 2, 1)));

                }

            }

        }.execute();
    }

    /**
     * 分享套餐
     */
    public void getPshareShop() {
        shareWaitDialog.show();

        ShareUtil.configPlatforms(context);// 配置分享平台参数</br>
        isPause = 1;
        new SAsyncTask<String, Void, HashMap<String, Object>>(this, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                return ComModel2.getPshopLink(params[0], context, "true");
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                isPause = 0;
                if (instance == null) {
                    return;
                }
                if (null == e) {
                    if (result.get("status").equals("1")) {

                        TongjiShareCount.tongjifenxiangCount();
                        TongjiShareCount.tongjifenxiangwho(code);


                        String[] picList = ((String) result.get("shop_pic")).split(",");
                        String link = (String) result.get("link");
                        download(null, code, result, link);
                    } else if (result.get("status").equals("1050")) {// 表明

                        if (null != shareWaitDialog) {
                            shareWaitDialog.dismiss();

                        }

                        Intent intent = new Intent(context, NoShareActivity.class);
                        intent.putExtra("isNomal", true);
                        context.startActivity(intent); // 分享已经超过了

                    }
                }
            }

        }.execute(code);
    }

    /**
     * 分享包邮
     */
    public void getPshareSignShop() {

        shareWaitDialog.show();

        ShareUtil.configPlatforms(context);// 配置分享平台参数</br>
        new SAsyncTask<Void, Void, HashMap<String, Object>>(this, R.string.wait) {
            @Override
            protected boolean isHandleException() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                // TODO Auto-generated method stub
                LogYiFu.e("shopDetials", signValue);
                return ComModel2.getSharePShopInfoDduobao(context, signValue, true);

            }

            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null) {
                    if ((Integer) result.get("status") == 1) {
                        // tongjifenxiangCount(); // 统计分享次数
                        // tongjifenxiang((String) result.get("shop_code"));//
                        // 统计谁分享了

                        TongjiShareCount.tongjifenxiangCount();
                        TongjiShareCount.tongjifenxiangwho((String) result.get("shop_code"));

                        // 8 包邮分享

                        String[] picList = ((String) result.get("shop_pic")).split(",");
                        String link = (String) result.get("link") + "&post=true";
                        downloadBao(null, signValue, result, link);
                    } else if (result.get("status").equals("1050")) {// 表明
                        if (null != shareWaitDialog) {
                            shareWaitDialog.dismiss();

                        }
                        Intent intent = new Intent(context, NoShareActivity.class);
                        intent.putExtra("isNomal", true);
                        context.startActivity(intent); // 分享已经超过了

                    }
                }
            }

            ;

        }.execute();
    }

    public static String shareStatus;

    /**
     * 得到分享的链接
     */
    public void share(final String code, final Shop shop) {

        try {
            shareWaitDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ShareUtil.configPlatforms(context);// 配置分享平台参数</br>
        isPause = 1;
        new SAsyncTask<String, Void, HashMap<String, String>>(this, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                if (isSignActiveShop) {
                    return ComModel2.getActiveShopLink(params[0], context, "true");
                } else {
                    return ComModel2.getShopLink(params[0], context, "true");
                }
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                isPause = 0;

                if (instance == null) {
                    return;
                }
                if (null == e) {

                    if (result.get("status").equals("1")) {
                        // 没有分享过

                        TongjiShareCount.tongjifenxiangCount();
                        TongjiShareCount.tongjifenxiangwho(code);

                        // 8 普通商品

                        LogYiFu.e("pic", result.get("shop_pic"));
                        String[] picList = result.get("shop_pic").split(",");
                        String four_pic = result.get("four_pic").toString();
                        String link = result.get("link");
                        getTyepe2SuppLabel(code, link, four_pic);
                    } else if (result.get("status").equals("1050")) {// 表明
                        if (null != shareWaitDialog) {
                            shareWaitDialog.dismiss();

                        }
                        Intent intent = new Intent(context, NoShareActivity.class);
                        intent.putExtra("isNomal", true);
                        context.startActivity(intent); // 分享已经超过了

                    } else {// Dialog消失
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

        }.execute(code);
    }

    /**
     * 下载分享的图片
     */
    private void download(View v, final String shop_code, final HashMap<String, Object> mapInfos, final String link) {

        new SAsyncTask<Void, Void, Void>((FragmentActivity) ShopDetailsActivity.this, v, R.string.wait) {

            @Override
            protected Void doInBackground(Void... params) {
                // TODO Auto-generated method stub
                List<String> shopCodes = (List<String>) mapInfos.get("shopCodes");
                List<HashMap<String, String>> shopPics = (List<HashMap<String, String>>) mapInfos.get("pics");

                File fileDirec = new File(YConstance.savePicPath);
                if (!fileDirec.exists()) {
                    fileDirec.mkdir();
                }
                File[] listFiles = new File(YConstance.savePicPath).listFiles();
                if (listFiles.length != 0) {
                    LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
                    for (File file : listFiles) {
                        file.delete();
                    }
                }
                // LogYiFu.i("TAG", "piclist=" + picList.length);
                List<String> pics = new ArrayList<String>();
                for (int j = 0; j < shopCodes.size(); j++) {
                    String shop_code = shopCodes.get(j);
                    HashMap<String, String> map = shopPics.get(j);
                    String pic = map.get(shop_code);
                    String[] picStrs = pic.split(",");
                    for (int i = 0; i < picStrs.length; i++) {
                        if (!picStrs[i].contains("reveal_") && !picStrs[i].contains("detail_")
                                && !picStrs[i].contains("real_")) {
                            pics.add(shop_code.substring(1, 4) + "/" + shop_code + "/" + picStrs[i]);
                        }
                    }
                }

                /*
                 * for (int j = 0; j < picList.length; j++) { if
                 * (!picList[j].contains("reveal_") &&
                 * !picList[j].contains("detail_") &&
                 * !picList[j].contains("real_")) { pics.add(picList[j]); } }
                 */
                int j = pics.size() + 1;
                if (pics.size() > 8) {
                    j = 9;
                }
                int nP = j > 5 ? 4 : j - 1;
                for (int i = 0; i < j; i++) {
                    if (i == nP) {
                        /*
                         * ComModel2.saveQRCode(PaymentSuccessActivity.this,
                         * shop_code);
                         */

                        Bitmap bm = QRCreateUtil.createImage(link, 500, 700, (String) mapInfos.get("shop_se_price"),
                                ShopDetailsActivity.this);// 得到二维码图片
                        QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
                                MD5Tools.md5(String.valueOf(i)) + ".jpg");// 保存二维码图片
                        // downloadPic(mapInfos.get("qr_pic"), 9);
                        continue;
                    }
                    int m = i > 4 ? i - 1 : i;
                    downloadPic(pics.get(m) + "!450", i);
                    bmBg = downloadPic(mapInfos.get("four_pic") + "!450");
                    LogYiFu.e("热卖分享", mapInfos.get("four_pic") + "!450");
                }
                return super.doInBackground(params);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, Void result) {
                if (instance == null) {
                    return;
                }
                if (null != context && null != context.getWindow().getDecorView()) {


                    UMImage umImage = new UMImage(context, bmBg);
                    ShareUtil.setShareContent(context, umImage, "买了肯定不后悔，数量不多，快来抢购吧~", link);

                    // showPopwindou(link, context, umImage);
                    myPopupwindow.setUmImage(umImage);
                    myPopupwindow.setLink(link);
                    shareTo(shop_code, link, "", "");
                    super.onPostExecute(context, result);
                }

            }

        }.execute();
    }

    /**
     * 下载包邮 分享的图片
     */
    private void downloadBao(View v, final String shop_code, final HashMap<String, Object> mapInfos,
                             final String link) {
        new SAsyncTask<Void, Void, Void>((FragmentActivity) ShopDetailsActivity.this, v, R.string.wait) {

            @Override
            protected Void doInBackground(Void... params) {

                File fileDirec = new File(YConstance.savePicPath);
                if (!fileDirec.exists()) {
                    fileDirec.mkdir();
                }
                File[] listFiles = new File(YConstance.savePicPath).listFiles();
                if (listFiles.length != 0) {
                    LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
                    for (File file : listFiles) {
                        file.delete();
                    }
                }
                List<String> pics = new ArrayList<String>();
                String pic = (String) mapInfos.get("shop_pic");
                String[] picStrs = pic.split(",");
                String shop_code = (String) mapInfos.get("shop_code");
                for (int i = 0; i < picStrs.length; i++) {
                    if (!picStrs[i].contains("reveal_") && !picStrs[i].contains("detail_")
                            && !picStrs[i].contains("real_")) {
                        pics.add(shop_code.substring(1, 4) + "/" + shop_code + "/" + picStrs[i]);
                    }
                }


                int j = pics.size() + 1;
                if (pics.size() > 8) {
                    j = 9;
                }
                int nP = j > 5 ? 4 : j - 1;
                for (int i = 0; i < j; i++) {
                    if (i == nP) {
                        /*
                         * ComModel2.saveQRCode(PaymentSuccessActivity.this,
                         * shop_code);
                         */
                        if ("SignShopDetail".equals(signShopDetail)) {

                            Bitmap bmQr = QRCreateUtil.createQrImage(link, 250, 250);
                            Bitmap bm = QRCreateUtil.drawNewBitmapNine(ShopDetailsActivity.this, bmQr,
                                    (String) mapInfos.get("price"), true);
                            QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
                                    MD5Tools.md5(String.valueOf(i)) + ".jpg");// 保存二维码图片
                        }
                        continue;
                    }
                    int m = i > 4 ? i - 1 : i;
                    downloadPic(pics.get(m) + "!450", i);

                    String baoyouWeixinSharePic = shop_code.substring(1, 4) + File.separator + shop_code
                            + File.separator + mapInfos.get("four_pic");

                    bmBg = downloadPic(baoyouWeixinSharePic + "!450");
                    LogYiFu.e("r热卖分享", mapInfos.get("four_pic") + "!450");
                }
                return super.doInBackground(params);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, Void result) {
                if (instance == null) {
                    return;
                }
                if (null != context && null != context.getWindow().getDecorView()) {

                    UMImage umImage = new UMImage(context, bmBg);
                    if (myPopupwindow.getShareId() == R.id.iv_qq_share) {
                        ShareUtil.setShareContentBaoYou(context, umImage, signType + "元带走心爱的商品。首次签到还能领3元现金哦", link,
                                signType, 0);
                    } else {
                        ShareUtil.setShareContent(context, umImage, "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);
                    }

                    myPopupwindow.setUmImage(umImage);
                    myPopupwindow.setLink(link);
                    shareTo(shop_code, link, "", "");
                    super.onPostExecute(context, result);
                }

            }

        }.execute();
    }

    /**
     * 显示分享的myPopWindow -----已经改成点击就分享
     *
     * @param context
     */
    private void showMyPopwindou(FragmentActivity context, final double feedBack) {
        // myPopupwindow = new MyPopupwindow(context, 0, umImage, link);
        // myPopupwindow = new MyPopupwindow(context,
        // shop.getKickback(), umImage, link);

        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) ShopDetailsActivity.this,
                R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel.ShareLifeGetPic(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);

                myPopupwindow = new MyPopupwindow(false, context, feedBack, ShopDetailsActivity.this, shop, false,
                        signShopDetail, "ShopDetails", signType, "", isSignActiveShop);

                if (result == null) {
//                    MyPopupwindow.iv_img.setImageResource(R.drawable.putongfengxiang1);

                } else if (null == e && result != null && !("".equals(result))) {

                    String mStartPic = (String) result.get("pic");
                    if (mStartPic == null || mStartPic.equals("null") || mStartPic.equals("")) {
                        mStartPic = "-1";
                    } else {
                        mStartPic = (String) result.get("pic");
                    }
                    if (mStartPic.equals("-1")) {
//                        MyPopupwindow.iv_img.setImageResource(R.drawable.putongfengxiang1);
                    } else {
                        // SetImageLoader.initImageLoader(null,
                        // MyPopupwindow.iv_img, mStartPic, "");
                        PicassoUtils.initImage(ShopDetailsActivity.this, mStartPic, MyPopupwindow.iv_img);
                    }

                }

                if ("SignShopDetail".equals(signShopDetail)) {
                    myPopupwindow.setGou(true);
                }
                if (ShopDetailsActivity.instance != null) {
                    myPopupwindow.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

                }

            }

        }.execute();

    }

    private String[] picListNine;
    private String shop_codeNine;
    private HashMap<String, String> mapInfosNine;
    private Shop shopNine;
    private String linkNine;
    private String four_picNine;

    /**
     * 获取九宫图的二维码背景图片
     */
    public void getNineBmBg(View v, final String[] picList, final String shop_code,
                            final HashMap<String, String> mapInfos, final Shop shop, final String link, final String four_pic) {
        new SAsyncTask<Void, Void, String>((FragmentActivity) context, R.string.wait) {
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
                // Bitmap bmNineBg = downloadPic("share/1111.jpg");
                picListNine = picList;
                shop_codeNine = shop_code;
                mapInfosNine = mapInfos;
                shopNine = shop;
                linkNine = link;
                four_picNine = four_pic;
                getPicture(result);

            }
        }.execute();
    }

    private Bitmap bmBg;

    /**
     * 下载分享的图片
     */
    private void download(View v, final String[] picList, final String shop_code,
                          final HashMap<String, String> mapInfos, final Shop shop, final String link, final String four_pic,
                          final Bitmap bmNineBg) {
        final List<String> pics = new ArrayList<String>();
        // shareWaitDialog.show();
        new SAsyncTask<Void, Void, Void>((FragmentActivity) ShopDetailsActivity.this, v, R.string.wait) {

            @Override
            protected Void doInBackground(Void... params) {
                File fileDirec = new File(YConstance.savePicPath);
                if (!fileDirec.exists()) {
                    fileDirec.mkdir();
                }
                File[] listFiles = new File(YConstance.savePicPath).listFiles();

                if (listFiles != null && listFiles.length != 0) {
                    LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
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
                int nP = j > 5 ? 4 : j - 1;
                for (int i = 0; i < j; i++) {
                    if (i == nP) {
                        /*
                         * ComModel2.saveQRCode(PaymentSuccessActivity.this,
                         * shop_code);
                         */
                        if ("SignShopDetail".equals(signShopDetail)) {
                            Bitmap bm = QRCreateUtil.createZeroImage(link, 500, 700, mapInfos.get("shop_se_price"),
                                    ShopDetailsActivity.this);// 得到二维码图片
                            QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
                                    MD5Tools.md5(String.valueOf(i)) + ".jpg");// 保存二维码图片
                        } else {

                            Bitmap bmQr = QRCreateUtil.createQrImage(mapInfos.get("QrLink"), 190, 190);
                            Bitmap bm = QRCreateUtil.drawNewBitmapNine2(ShopDetailsActivity.this, bmQr, bmNineBg);

                            QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
                                    MD5Tools.md5(String.valueOf(i)) + ".jpg");// 保存二维码图片
                        }
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
                if (instance == null) {
                    if (null != shareWaitDialog) {
                        shareWaitDialog.dismiss();
                    }
                    return;
                }
                if (null != context && null != context.getWindow().getDecorView() && !context.isFinishing()) {
                    LogYiFu.e("TAG", "宝贝内容=" + shop.getShop_name() + ",宝贝链接=" + result);
                    UMImage umImage = new UMImage(context, bmBg);
                    ShareUtil.setShareContent(context, umImage, "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);
                    myPopupwindow.setLink(link);
                    myPopupwindow.setUmImage(umImage);
                    shareTo(shop_code, link, "", "");
                    super.onPostExecute(context, result);
                }

            }

        }.execute();

    }

    public void getTyepe2SuppLabel(final String shop_code, final String link, final String four_pic) {
        new SAsyncTask<Void, Void, HashMap<String, String>>(ShopDetailsActivity.this, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModelZ.geType2SuppLabe(ShopDetailsActivity.this, shop_code);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null) {
                    String type2 = result.get("type2");
                    if ("".equals(type2)) {
                        type2 = shop.getShop_name();
                    }
                    String label_id = result.get("supp_label_id");
                    getShareTitleText(code, link, four_pic, type2, label_id);
                } else {
                    getShareTitleText(code, link, four_pic, "", "");

                }
            }

        }.execute();
    }

    public void getShareTitleText(final String shop_code, final String link, final String four_pic, final String type2, final String label_id) {
        new SAsyncTask<Void, Void, HashMap<String, String>>(ShopDetailsActivity.this, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModelZ.getNewShareTitleContent();
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null) {
                    String text = result.get("text");
                    if ("".equals(text)) {
                        text = shop.getShop_name();
                    }
                    String str = result.get("title");
                    String str1;
                    String str2;
                    String str3;
                    String str4;
                    str1 = str.replaceFirst("\\$\\{replace\\}", new DecimalFormat("#0.0").format(Math.round(shop.getShop_se_price() * 0.5 * 10) * 0.1d));
                    str2 = str1.replaceFirst("\\$\\{replace\\}", label_id);
                    str3 = str2.replaceFirst("\\$\\{replace\\}", "" + type2);
                    str4 = str3.replaceFirst("\\$\\{replace\\}", "" + new DecimalFormat("#0.0").format(Math.round(shop.getShop_se_price() * 0.5 * 10) * 0.1d));

                    UMImage umImage = new UMImage(context, YUrl.imgurl + shop_code.substring(1, 4) + "/" + shop_code + "/" + four_pic.split(",")[2] + "!450");

                    ShareUtil.setShareNewTitleContent(context, umImage, "" + text, link, str4);
                    myPopupwindow.setLink(link);
                    myPopupwindow.setUmImage(umImage);
                    shareTo(shop_code, link, str4, text);
                }
            }

        }.execute();
    }


    /**
     * @param shop_code
     */
    private void shareTo(String shop_code, String link, String title, String text) {
        qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
        wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
        // wXinShareIntent.putExtra("Kdescription", "点击链接了解详情"+link);
        if (null != shareWaitDialog) {
            shareWaitDialog.dismiss();
        }
        switch (myPopupwindow.getShareId()) {
            case R.id.iv_qq_share:
                if (myPopupwindow.isSecondShare()) {
                    myPopupwindow.onceShare(qqShareIntent, "qq空间");

                    yunYunTongJi(shop_code, 104, 2);
                }
                break;
            case R.id.iv_wxin_share://分享到朋友圈


                if (myPopupwindow.isSecondShare()) {

                    myPopupwindow.performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);// 链接
                    yunYunTongJi(shop_code, 1, 2);
                    // }
                }

                break;
            case R.id.iv_wxin_circle_share://分享到微信
                yunYunTongJi(shop_code, 106, 2);
                if (!"".equals(title)) {
                    myPopupwindow.shareToWxinNewTitle(title, text);
                } else {
                    myPopupwindow.shareToWxin();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AppManager.getAppManager().removeDetailsActivity(this);
        instance = null;
        if (myPopupwindow != null && myPopupwindow.isShowing()) {
            myPopupwindow.dismiss();
            return;
        }

        if (shop != null) {
            setResult(-1, new Intent().putExtra("isLike", shop.getLike_id() == -1 ? 0 : 1));
        }
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        if (aa != null && !aa.isCancelled()) {
            aa.cancel(true);
        }
        if (bb != null && !bb.isCancelled()) {
            bb.cancel(true);
        }
        if (cc != null && !cc.isCancelled()) {
            cc.cancel(true);
        }
    }

    /***
     * 添加和删除我喜欢的商品
     *
     */
    private void addLikeShop(View v) {
        if (shop != null) {
            // int like_id = shop.getLike_id();
            int like_id = -1;
            String str = SharedPreferencesUtil.getStringData(context, "" + YCache.getCacheUser(context).getUser_id(),
                    "");
            if (str.contains(shop.getShop_code())) {
                like_id = 1;
            } else {
            }

            if (like_id == -1) {// 添加我的喜欢
                LogYiFu.e("like_id  == ", " " + like_id);
                LogYiFu.e("shop_code  == ", " " + shop.getShop_code());
                AlphaAnimation _alphaAnimation0 = new AlphaAnimation(1.0f, 0.2f);
                _alphaAnimation0.setDuration(1500);
                _alphaAnimation0.setFillAfter(true);// 动画执行完的状态显示
                img_addxin.setImageResource(R.drawable.pic_like_animation);
                img_addxin.startAnimation(_alphaAnimation0);
                img_addxin.setVisibility(View.VISIBLE);

                /**
                 * 透明度从不透明变为0.2透明度
                 */
                AlphaAnimation _alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
                _alphaAnimation.setDuration(1500);
                _alphaAnimation.setFillAfter(true);// 动画执行完的状态显示
                img_addxin.startAnimation(_alphaAnimation);
                shakeAnimation(5);
                new SAsyncTask<String, Void, ReturnInfo>(this, v, 0) {

                    @Override
                    protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {

                        return ComModel.addLikeShop(ShopDetailsActivity.this,
                                YCache.getCacheToken(ShopDetailsActivity.this), params[0]);
                    }

                    @Override
                    protected boolean isHandleException() {
                        return true;
                    }

                    @Override
                    protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                        if (null == e) {
                            img_addxin.setVisibility(View.GONE);
                            if (null != result) {
                                Toast.makeText(ShopDetailsActivity.this, "添加我的喜欢成功", Toast.LENGTH_SHORT).show();
                                String str = SharedPreferencesUtil.getStringData(context,
                                        "" + YCache.getCacheUser(context).getUser_id(), "");
                                StringBuffer sb = new StringBuffer(str);
                                sb.append(shop.getShop_code());
                                SharedPreferencesUtil.saveStringData(context,
                                        "" + YCache.getCacheUser(context).getUser_id(), sb.toString());
                                LogYiFu.e("hillo", sb.toString());
                                LogYiFu.e("hillo", shop.getShop_code());
//                                img_xin.setImageResource(R.drawable.hx0);
                                shop.setLike_id(1);
                                if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 6) {
                                    context.getSharedPreferences("EverydayTaskMondayFridayAddLike",
                                            Context.MODE_PRIVATE).edit()
                                            .putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
                                }
                            } else {
                                Toast.makeText(ShopDetailsActivity.this, "添加我的喜欢失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                        super.onPostExecute(context, result, e);
                    }

                }.execute(shop.getShop_code());
            } else {// 删除我的喜欢
                AlphaAnimation _alphaAnimation0 = new AlphaAnimation(1.0f, 0.2f);
                _alphaAnimation0.setDuration(1500);
                _alphaAnimation0.setFillAfter(true);// 动画执行完的状态显示
                img_addxin.setImageResource(R.drawable.cancel_add_star);
                img_addxin.startAnimation(_alphaAnimation0);
                img_addxin.setVisibility(View.VISIBLE);

                /**
                 * 透明度从不透明变为0.2透明度
                 */
                AlphaAnimation _alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
                _alphaAnimation.setDuration(1500);
                _alphaAnimation.setFillAfter(true);// 动画执行完的状态显示
                img_addxin.startAnimation(_alphaAnimation);
                shakeAnimation(5);

                new SAsyncTask<String, Void, ReturnInfo>(this, v, 0) {

                    protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {

                        return ComModel.deleteLikeShop(ShopDetailsActivity.this,
                                YCache.getCacheToken(ShopDetailsActivity.this), params[0]);
                    }

                    @Override
                    protected boolean isHandleException() {
                        return true;
                    }

                    @Override
                    protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                        super.onPostExecute(context, result, e);
                        if (null == e) {
                            img_addxin.setVisibility(View.GONE);
                            if (null != result) {
                                String str = SharedPreferencesUtil.getStringData(context,
                                        "" + YCache.getCacheUser(context).getUser_id(), "");
                                LogYiFu.e("hillo", str);
                                LogYiFu.e("hillo", shop.getShop_code());
                                String str2 = str.replace(shop.getShop_code(), "");
                                LogYiFu.e("hillo", str2);
                                SharedPreferencesUtil.saveStringData(context,
                                        "" + YCache.getCacheUser(context).getUser_id(), str2);
                                Toast.makeText(ShopDetailsActivity.this, "删除我的喜欢成功", Toast.LENGTH_SHORT).show();
//                                img_xin.setImageResource(R.drawable.icon_xihuan);
                                shop.setLike_id(-1);

                            } else {
                                Toast.makeText(ShopDetailsActivity.this, "删除我的喜欢失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }.execute(shop.getShop_code());
            }
        } else {
            ToastUtil.showShortText(context, "操作无效");
        }
    }

    private Animation mShakeAnimation;

    // CycleTimes动画重复的次数
    public void shakeAnimation(int CycleTimes) {
        if (null == mShakeAnimation) {
            mShakeAnimation = new TranslateAnimation(0, 5, 0, 5);
            mShakeAnimation.setInterpolator(new CycleInterpolator(5));
            mShakeAnimation.setDuration(3000);
            mShakeAnimation.setRepeatMode(Animation.REVERSE);// 设置反方向执行

        }
        img_addxin.startAnimation(mShakeAnimation);
    }

    private int index = 0;
    private int mType = 0;
    private View v;

    private boolean isShopTitle = false;

    private String attrDateStr;// 属性时间戳
    private HashMap<String, String> mGoldIconMap;
    private HashMap<String, String> mGoldVoucherMap;

    /**
     * 获取是否开启余额翻倍，金币，金券，用于弹框文案的显示
     */
    private void getGoldIsOpen(final int dikou_int) {
        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) ShopDetailsActivity.this,
                R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                mGoldIconMap = ComModel2.getTwoFoldnessGold(ShopDetailsActivity.this);
                mGoldVoucherMap = ComModel2.getCpgold(ShopDetailsActivity.this);
                return null;
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);

                if (null == e) {
                    if (null != mGoldIconMap && mGoldIconMap.size() > 0
                            && !("".equals(mGoldIconMap.get("twofoldnessGold")))) {
                    }
                    if (mGoldVoucherMap != null && mGoldVoucherMap.size() > 0
                            && "1".equals("" + mGoldVoucherMap.get("is_open"))) {
                        Double.parseDouble(mGoldVoucherMap.get("c_price"));
                    }

                }
            }

        }.execute();
    }

    /***
     * 查询普通商品详情页
     */
    private void queryShopDetails() {

        // attrDateStr = getSharedPreferences(Pref.sync, Context.MODE_PRIVATE)
        // .getString(Pref.sync_attr_date, "");
        aa = new SAsyncTask<String, Void, HashMap<String, Object>>(this, null, R.string.wait) {

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                LoadingDialog.show(ShopDetailsActivity.this);
            }

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                Shop shop;
                HashMap<String, Object> map;


//                if (isNewMeal) {
//                    map = ComModel.queryShopMeal(ShopDetailsActivity.this, params[0]);
//                } else {


                if (YJApplication.instance.isLoginSucess()) {


                    map = ComModel.queryShopDetails(ShopDetailsActivity.this,
                            YCache.getCacheToken(ShopDetailsActivity.this), params[0], attrDateStr, sweet_theme_id);

                } else {
                    map = ComModel.queryShopDetails2(ShopDetailsActivity.this, params[0], attrDateStr);
                }
//                }

                // return shop;
                return map;
            }

            @SuppressLint({"NewApi", "InflateParams", "SimpleDateFormat"})
            @Override
            protected void onPostExecute(final FragmentActivity context, HashMap<String, Object> map, Exception e) {
                showShopDetailsBuyTipsDialog();// 商品详情 购买可抽奖提示 每天只弹出一次
                if (e != null) {// 查询异常


                } else {// 查询商品详情成功，刷新界面


                    mSingleBuy.setOnClickListener(ShopDetailsActivity.this);
                    mTwoBuy.setOnClickListener(ShopDetailsActivity.this);
                    mGroupBuy.setOnClickListener(ShopDetailsActivity.this);
                    rrr.setBackgroundColor(Color.WHITE);
                    tv_shop_car.setVisibility(View.VISIBLE);
                    tv_shop_car_fake.setVisibility(View.GONE);
                    Shop shopd = null;
                    ShareShop shareshop = null;
                    if (map != null) {
                        shopd = (Shop) map.get("shop");
                        shareshop = (ShareShop) map.get("shareshop");
                    }
                    if (shopd != null) {


                        //
                        mSinglePrice.setText("¥" + new DecimalFormat("#0.0").format(shopd.getShop_se_price()));
                        mTwoPrice.setText("¥" + shopd.getRoll_price());
                        mGroupPrice.setText("¥" + shopd.getRoll_price());
                        setEva_count_z = (int) Float.parseFloat(shopd.getEva_count() + ""); // 评价总数

                        LogYiFu.e("评价总数", setEva_count_z + "");

                        shop = shopd;

                        LogYiFu.e("shop.getZeroOrderNum()", shop.getZeroOrderNum() + "");

                        for (int i = 0; i < listTitle.size(); i++) {
                            if ((titleId + "").equals(listTitle.get(i).get("_id"))) {
                                titleCheck = i;
                                break;
                            }
                        }
                        if (titleCheck >= listTitle.size()) {
                            titleCheck = 0;
                        }

                        if (YJApplication.instance.isLoginSucess() == true) {
                            String str = SharedPreferencesUtil.getStringData(context,
                                    "" + YCache.getCacheUser(context).getUser_id(), "");
                            if (str.contains(shop.getShop_code())) {
                            } else {
                            }
                        }


                        width = ShopDetailsActivity.this.getResources().getDisplayMetrics().widthPixels;
                        // 新服务声明
                        headerView.findViewById(R.id.head_ll_new_decleration).setVisibility(View.VISIBLE);
                        TextView supply_name = (TextView) headerView.findViewById(R.id.head_tv_supply);// 供应商
                        LinearLayout ll_supply = (LinearLayout) headerView.findViewById(R.id.ll_supply);
                        final String supp_label_id = shopd.getSupp_label_id();
                        mSupp_label = shopd.getSupp_label();
                        if (TextUtils.isEmpty(supp_label_id) || TextUtils.isEmpty(mSupp_label) || "null".equals(mSupp_label)) {// 没有供应商标签(隐藏标签)
                            ll_supply.setVisibility(View.GONE);
                            mSupp_label = "";
                        } else {
                            ll_supply.setVisibility(View.VISIBLE);
                            supply_name.setText("" + mSupp_label);
                            supply_name.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent = new Intent(ShopDetailsActivity.this, ManufactureActivity.class);
                                    intent.putExtra("supple_id", supp_label_id);
                                    intent.putExtra("supp_label", mSupp_label);
                                    context.startActivity(intent);
                                    ((Activity) context).overridePendingTransition(R.anim.activity_from_right,
                                            R.anim.activity_search_close);
                                }
                            });
                        }

                        adapter = new MyAdapter();
                        String[] imgs = shop.getShop_pic().split(",");
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < imgs.length; i++) {
                            if (imgs[i].contains("reveal_") || imgs[i].contains("real_")
                                    || imgs[i].contains("detail_")) {
                                sb.append(imgs[i] + ",");
                            }
                        }
                        //11111
                        images = sb.toString().substring(0, sb.length() - 1).split(",");


                        final ImageView img_header = headerView.findViewById(R.id.img_header);


                        heights = width * 9 / 6;


                        final String def_pic = shop.getDef_pic();
                        if (!TextUtils.isEmpty(def_pic)) {


                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String url = shop.getShop_code().substring(1, 4) + "/"
                                            + shop.getShop_code() + "/" + def_pic;

                                    String mUrl;
                                    if (!TextUtils.isEmpty(url) && url.contains("http://")) {
                                        mUrl = url;
                                    } else if (!TextUtils.isEmpty(url) && url.contains("https://")) {
                                        mUrl = url;
                                    } else {
                                        mUrl = YUrl.imgurl + url;
                                    }
                                    setHeadImg(mUrl, img_header);


                                }
                            }).start();


                            headerView.findViewById(R.id.img_header).setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ShopDetailsActivity.this, ShopImageActivity.class);
                                    intent.putExtra("url", shop.getShop_code().substring(1, 4) + "/"
                                            + shop.getShop_code() + "/" + shop.getDef_pic());
                                    intent.putExtra("shop", shop);

                                    intent.putExtra("ShopCart", shopCart);
                                    intent.putExtra("supp_label", mSupp_label);
                                    intent.putExtra("number_sold", number_sold);
                                    intent.putExtra("isSignActiveShop", isSignActiveShop);

                                    startActivityForResult(intent, 235);
                                }
                            });
                        }
                        ((TextView) headerView.findViewById(R.id.tv_clothes_name))
                                .setText(TextUtils.isEmpty(shop.getShop_name()) ? null : shop.getShop_name());

                        String num_save = SharedPreferencesUtil.getStringData(context, "num", "1");

                        if (isSignActiveShop) {
                        } else {

                        }
                        String dikou = shop.getKickback() + "";
                        double valueOf = Double.valueOf(dikou);


                        int dikou_int = (int) valueOf;
                        // 判断是否有了余额翻倍，金币或者抵用券

                        if (YJApplication.instance.isLoginSucess()) {
                            getGoldIsOpen(dikou_int);
                        }


                        String xiang_mongey = new DecimalFormat("#0.0").format(shop.getShop_se_price() * 0.1);
                        f_xiang_mongey = Double.valueOf(xiang_mongey);

                        setShareButText();


                        //免费领的处理
                        if (fromShouye3) {
                            tv_yanjia_first_diamond_price.setText("¥"
                                    + new DecimalFormat("#0.0").format(shop.getShop_price()));

                            tv_yanjia_first_diamond_price.setVisibility(View.VISIBLE);
                            tv_yanjia_first_diamond_text.setVisibility(View.VISIBLE);
                            header_tv_price.setVisibility(View.GONE);
                            header_tv_sjprice.setVisibility(View.GONE);
                            header_tv_diKou.setVisibility(View.GONE);
                            header_iv_wenhao.setVisibility(View.GONE);
                            tv_active_discount.setVisibility(View.GONE);
                        } else {

                            //价格界面确定（非会员和未登录显示拼团价，会员显示单独购买价）
                            if (YJApplication.instance.isLoginSucess()) {


                                //查询抵扣
                                HashMap<String, String> pairsMap = new HashMap<>();
                                YConn.httpPost(instance, YUrl.GETALLDIKOU, pairsMap, new HttpListener<VipDikouData>() {
                                    @Override
                                    public void onSuccess(VipDikouData vipDikouData) {
                                        mVipDikouData = vipDikouData;

                                        header_tv_diKou.setText("已抵扣0.0元");
                                        header_tv_sjprice.setText("原价¥"
                                                + new DecimalFormat("#0.0").format(shop.getShop_price()));
                                        header_tv_sjprice.setTextColor(Color.parseColor("#ff3f8b"));
                                        header_tv_sjprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                                        String mOnPrice = "¥" + new DecimalFormat("#0.0")
                                                .format(shop.getShop_se_price());
                                        header_tv_price.setText(mOnPrice);

//                                        if (null != vipDikouData && vipDikouData.getIsVip() <= 0) {//非会员
//                                            header_tv_diKou.setText("已抵扣0.0元");
//                                            header_tv_sjprice.setText("原价¥"
//                                                    + new DecimalFormat("#0.0").format(shop.getShop_price()));
//                                            header_tv_sjprice.setTextColor(Color.parseColor("#ff3f8b"));
//                                            header_tv_sjprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//
//                                            String mOnPrice = "¥" + new DecimalFormat("#0.0")
//                                                    .format(shop.getShop_se_price());
//
//
//                                            header_tv_price.setText(mOnPrice);
//                                        } else {
//                                            double vipPrice;
//                                            double prices = shop.getShop_se_price();
//
//                                            double sePrice = prices * 0.95;
//                                            double dikou = prices * 0.9;
//
//                                            submitOrderShowShopPrice = prices;
//
//
//                                            if (vipDikouData.getMaxType() == 6) {
//                                                submitOrderShowShopPrice = sePrice;
//                                            }
//
//
//                                            if (vipDikouData.getOne_not_use_price() >= dikou) {
//                                                if (vipDikouData.getMaxType() == 6) {
//                                                    vipPrice = sePrice - dikou;
//                                                } else {
//                                                    vipPrice = prices - dikou;
//
//                                                }
//
//                                                submitOrderDiKou = new DecimalFormat("#0.00").format(dikou);
//
//                                                header_tv_diKou.setText("已抵扣" + new DecimalFormat("#0.00").format(dikou) + "元");
//                                                youhuiDikou = dikou + "";
//                                            } else {
//
//                                                if (vipDikouData.getMaxType() == 6) {
//                                                    vipPrice = sePrice - vipDikouData.getOne_not_use_price();
//                                                } else {
//                                                    vipPrice = prices - vipDikouData.getOne_not_use_price();
//
//                                                }
//                                                submitOrderDiKou = vipDikouData.getOne_not_use_price() + "";
//                                                header_tv_diKou.setText("已抵扣" + vipDikouData.getOne_not_use_price() + "元");
//
//                                                youhuiDikou = vipDikouData.getOne_not_use_price() + "";
//
//                                            }
//
//
//                                            header_tv_price.setText("¥" + new DecimalFormat("#0.0").format(vipPrice)); //中间价格
//                                            vipPayOnlyBuyPrice = vipPrice;
//
//
//                                            header_tv_sjprice.setText("原价¥"
//                                                    + new DecimalFormat("#0.0").format(shop.getShop_price()));//中间原价
//                                            header_tv_sjprice.setTextColor(Color.parseColor("#ff3f8b"));
//                                            header_tv_sjprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//
//                                        }


                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });


                            } else {
                                header_tv_diKou.setText("已抵扣0.0元");
                                header_tv_sjprice.setText("原价¥"
                                        + new DecimalFormat("#0.0").format(shop.getShop_price()));
                                header_tv_sjprice.setTextColor(Color.parseColor("#ff3f8b"));
                                header_tv_sjprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                                String mOnPrice = "¥" + new DecimalFormat("#0.0")
                                        .format(shop.getShop_se_price());
                                header_tv_price.setText(mOnPrice);

                            }

                            double discount = (shop.getShop_se_price() / shop.getShop_price()) * 10;
                            if (discount < 0.1) {
                                discount = 0.1;
                            }
                            tv_active_discount.setText(new DecimalFormat("#0.0").format(discount) + "折");

                        }


                        header_tv_diKou.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                DialogUtils.getDiKouDialogNewOrder(ShopDetailsActivity.this);
                            }
                        });


                        header_iv_wenhao.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                header_tv_diKou.performClick();

                            }
                        });


                        mListView.setOnTouchListener(new OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                int action = event.getAction();
                                switch (action) {
                                    case MotionEvent.ACTION_DOWN:

                                        break;

                                    case MotionEvent.ACTION_MOVE:

                                        break;
                                    case MotionEvent.ACTION_UP:
                                        break;
                                    default:
                                        break;
                                }

                                return false;

                            }
                        });
                        mListView.removeHeaderView(headerView);
                        mListView.addHeaderView(headerView);
                        mListView.setAdapter(adapter);

                        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                            private int myposition;
                            private ImageButton aa2;
                            private TextView tvTitle;

                            @Override
                            public void onScrollStateChanged(AbsListView view, int arg1) {

                                View childAt = view.getChildAt(0);
                                switch (arg1) {
                                    case SCROLL_STATE_TOUCH_SCROLL:// 滚动之前
                                        if (isShopTitle) {
                                            break;
                                        }
                                        if (childAt == null) {
                                            myposition = 0;
                                        } else {
                                            myposition = -childAt.getTop()
                                                    + view.getFirstVisiblePosition() * childAt.getHeight();
                                        }
                                        break;

                                    case SCROLL_STATE_FLING: // 滚动
                                        if (isShopTitle) {
                                            break;
                                        }
                                        int newPosition = 0;
                                        if (childAt == null) {
                                            newPosition = 0;
                                        } else {
                                            newPosition = -childAt.getTop()
                                                    + view.getFirstVisiblePosition() * childAt.getHeight();
                                        }


                                        break;
                                    case SCROLL_STATE_IDLE:
                                        if (view.getLastVisiblePosition() == view.getCount() - 1) {
                                            if (check == 0) {
                                                if (isInit == false) {
                                                    index++;
                                                    mType = 2;
                                                    initData(titleCheck, index + "");
                                                }

                                            } else if (check == 2) {

                                                if (isCheck == false) {
                                                    page++;
                                                    querySelCommentByShop();
                                                }
                                            }
                                        }

                                        if (isShopTitle) {
                                            break;
                                        }

                                        if (ll_bottem.getVisibility() == View.GONE && isAnim == false) {

                                        }

                                        break;
                                }
                            }

                            @Override
                            public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
                                int perHeight = heights / 100;
                                float currentY = 0;
                                int viewTop = -1;
                                aa2 = (ImageButton) findViewById(R.id.imgbtn_left_icon);
                                tvTitle = (TextView) findViewById(R.id.tv_title_details);
                                /* 滚动title渐变的效果 */
                                if (arg1 == 0) {// 当前第一位显示为1
                                    View childAt = arg0.getChildAt(0);// 这个是headerView
                                    if (childAt != null) {
                                        currentY = childAt.getTop();
                                        viewTop = childAt.getMeasuredHeight() + childAt.getTop();
                                    }

                                } else if (arg1 > 0) {
                                    currentY = heights;
                                    viewTop = rlTop.getHeight();
                                }

                                if (currentY == 0) {
                                    rlTop.setBackgroundResource(R.drawable.zhezhao2x);
                                    aa2.setBackgroundResource(R.drawable.icon_fanhui);
                                    tvTitle.setAlpha(0);
                                    mShuaixuanNew.setBackgroundResource(R.drawable.icon_shaixuan_white_new);
                                    lin_contact.setBackgroundResource(R.drawable.icon_xihuan_white);

                                    rlTop.getBackground().setAlpha(255);
                                }
                                float alpha = currentY / (float) heights;
                                tvTitle.setAlpha(Math.abs(alpha));
                                if (Math.abs(currentY) > 0 && Math.abs(currentY) < heights / 2) {
                                    aa2.setBackgroundResource(R.drawable.icon_fanhui);
                                    mShuaixuanNew.setBackgroundResource(R.drawable.icon_shaixuan_white_new);
                                    lin_contact.setBackgroundResource(R.drawable.icon_xihuan_white);
                                    // img_cart_top
                                    // .setBackgroundResource(R.drawable.icon_gouwuche);
                                    // img_fenx_top
                                    // .setBackgroundResource(R.drawable.icon_fenxiang);
                                    int i = (int) Math.abs(currentY / heights * 255);

                                    if (Math.abs(currentY) == 0) {
                                        i = 1;
                                    }
                                    aa2.getBackground().setAlpha(255 - i);
                                    mShuaixuanNew.getBackground().setAlpha(255 - i);
                                    lin_contact.getBackground().setAlpha(255 - i);


                                }

                                if (Math.abs(currentY) >= heights / 2 && Math.abs(currentY) < heights) {
                                    aa2.setBackgroundResource(R.drawable.icon_fanhui_black);
                                    mShuaixuanNew.setBackgroundResource(R.drawable.icon_shaixuan_new);
                                    lin_contact.setBackgroundResource(R.drawable.icon_xihuan);

                                    int i = (int) Math.abs(currentY / heights * 255);

                                    if (Math.abs(currentY) == 0) {
                                        i = 1;
                                    }

                                    aa2.getBackground().setAlpha(i);
                                    mShuaixuanNew.getBackground().setAlpha(i);
                                    lin_contact.getBackground().setAlpha(i);

                                }

                                if (Math.abs(currentY) <= heights && Math.abs(currentY) > 0) {
                                    rlTop.setBackgroundColor(getResources().getColor(R.color.white));
                                    int i = (int) Math.abs(currentY / heights * 255);

                                    if (Math.abs(currentY) == 0) {
                                        i = 1;
                                    }
                                    rlTop.getBackground().setAlpha(i);
                                }

                                if (Math.abs(currentY) >= heights) {
                                    rlTop.getBackground().setAlpha(255);
                                    aa2.setBackgroundResource(R.drawable.icon_fanhui_black);
                                    tvTitle.setAlpha(1);
                                    mShuaixuanNew.setBackgroundResource(R.drawable.icon_shaixuan_new);
                                    lin_contact.setBackgroundResource(R.drawable.icon_xihuan);

                                }

                            }
                        });

                        findViewById(R.id.search).setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                // toggle();
                            }
                        });
                        findViewById(R.id.shaixuan).setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {

                            }
                        });

                        ////
                        ////
                        // 购物车倒计时
                        // long c_time = shop.getC_time();

                        String time_c = SharedPreferencesUtil.getStringData(context, Pref.SHOPCART_COMMON_TIME, "0");
                        long c_time = Long.valueOf(time_c).longValue();

                        // long s_time_fuwuqi = shop.getS_time();
                        long s_time_fuwuqi = System.currentTimeMillis();

                        if (c_time - s_time_fuwuqi > 0) {// 正数代表加入了购物车 显示倒计时
                            // tv_time_count_down.setVisibility(View.VISIBLE);

                            String c_time_cart = DateFormatUtils.format(
                                    /* Long.parseLong(shop.getC_time() + "") */c_time, "yyyy-MM-dd HH:mm:ss");
                            String s_time = DateFormatUtils.format(
                                    Long.parseLong(/* shop.getS_time() */System.currentTimeMillis() + ""),
                                    "yyyy-MM-dd HH:mm:ss");


                        } else {// 负数代表没有加入购物车
                        }
                        if (YJApplication.instance.isLoginSucess()) {

                            ShopCartDao dao = new ShopCartDao(context);
                            // int count = dao.queryCartCount(context);
                            int count = 0;

                            count = dao.queryCartCommonCount(context);
                            if (/* shop.getCart_count() */count > 0) {
                                count = count > 99 ? 99 : count;
                                tv_cart_count.setText(/* shop.getCart_count() */count + "");// 设置购物车数量
                                tv_cart_count2
                                        .setText(/* shop.getCart_count() */count + "");// 设置购物车数量
                                tv_cart_count.setVisibility(View.VISIBLE);
                                tv_cart_count2.setVisibility(View.VISIBLE);

                                if ((c_time - s_time_fuwuqi) <= 0) {
                                    tv_time_count_down.setText("00:00");

                                    tv_time_count_down2.setText("00:00");

                                    tv_time_count_down_meal.setVisibility(View.GONE); // 显示倒计时
                                } else {

                                    tv_time_count_down_meal.setVisibility(View.GONE); // 显示倒计时
                                }

                                if (countCommn == 0) {
                                    tv_time_count_down.setVisibility(View.GONE);
                                    tv_time_count_down2.setVisibility(View.GONE);
                                }

                                if (countMeal == 0) {
                                    tv_time_count_down_meal.setVisibility(View.GONE);
                                }

                            } else {
                                tv_cart_count.setText(0 + "");
                                tv_cart_count.setVisibility(View.GONE);
                                tv_cart_count2.setText(0 + "");
                                tv_cart_count2.setVisibility(View.GONE);
                                tv_time_count_down.setVisibility(View.GONE); // TODO
                                tv_time_count_down2.setVisibility(View.GONE); // TODO
                                tv_time_count_down_meal.setVisibility(View.GONE); // 显示倒计时消失
                            }
                        }
                        if (tv_time_count_down.getVisibility() == View.GONE) {
                            rl_retain.setVisibility(View.GONE);
                        }
                    }

                    if (shareshop == null || isSignActiveShop) {
                    } else {
                        tv_shop_car.setVisibility(View.VISIBLE);
                        tv_shop_car_fake.setVisibility(View.GONE);

                        int count = shareshop.getCount();
                        List<HashMap<String, Object>> user_list = shareshop.getUser_list();

                        Double random = shop.getRandom();

                        String format = new DecimalFormat("#0").format(count * random);
                        int ii = Integer.valueOf(format).intValue();
                        Double get_money = ii * f_xiang_mongey;
                        String last_money = new DecimalFormat("#0.0").format(get_money);

                        for (int i = user_list.size() - 1; i >= 0; i--) {
                            HashMap<String, Object> hashMap = user_list.get(i);
                            String pic = hashMap.get("pic").toString();

                            if (!pic.contains("http")) {
                                pic = YUrl.imgurl + pic;
                            }

                        }

                    }

                    //商品详情查询完毕---设置购买按钮
                    setBuyBtn();


                }

            }


            @Override
            protected boolean isHandleException() {
                return true;
            }

        };
        aa.execute(code);

    }


    private void getDIKOU(final TextView diKou) {


        new SAsyncTask<Void, Void, String>(ShopDetailsActivity.this, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected String doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel2.getALLDikouKeyong(context);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, String result, Exception e) {
                if (null == e) {
                    diKou.setText("余额可抵扣"
                            + new DecimalFormat("#0.0").format(Double.parseDouble(result)) + "元");
                }

                super.onPostExecute(context, result, e);
            }

        }.execute();


    }


    private void setDandugoumai(final TextView tv_onlyshop_red) {


        new SAsyncTask<Void, Void, String>(ShopDetailsActivity.this, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected String doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel2.getALLDikouKeyong(context);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, String result, Exception e) {
                if (null == e) {

                    OnBuyDikouprice = Double.parseDouble(result);
                    double price = shop.getShop_se_price() - Double.parseDouble(result);
                    if (price <= 0) {
                        price = 0;
                    }

                    tv_onlyshop_red.setText("¥" + new DecimalFormat("#0.0").format(price));

                }

                super.onPostExecute(context, result, e);
            }

        }.execute();


    }

    private void setShareButText() {






        tv_fenxiang.setText("卖赚" + new DecimalFormat("#0.0").format(shop.getShop_se_price() * 0.15) + "元");

        if(YJApplication.instance.isLoginSucess() && YCache.getCacheUser(instance).getReviewers() == 1){
            tv_fenxiang.setVisibility(View.GONE);
        }else{
            tv_fenxiang.setVisibility(View.VISIBLE);

        }



        //查询抵扣
//        HashMap<String, String> pairsMap = new HashMap<>();
//        YConn.httpPost(instance, YUrl.GETALLDIKOU, pairsMap, new HttpListener<VipDikouData>() {
//            @Override
//            public void onSuccess(VipDikouData vipDikouData) {
//                if (vipDikouData.getIs_open() == 1) {
//                    tv_fenxiang.setText("为他人分享");
//                }
//
//            }
//
//            @Override
//            public void onError() {
//
//            }
//
//        });

    }

    public static double OnBuyDikouprice;//1元购可抵扣金额


    private void queryMealShopAttrs(final int i, View v) {
        clickFlag = false;
        final StringBuffer sb = new StringBuffer();
        for (int j = 0; j < list.size(); j++) {
            sb.append(list.get(j).getShop_code());
            if (j != list.size() - 1) {
                sb.append(",");
            }
        }
        new SAsyncTask<Void, Void, List<StockType>>(this, v, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected List<StockType> doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel2.getShopListAttrs(context, sb.toString());
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<StockType> result, Exception e) {
                if (null == e) {
                    List<StockBean> listStocks = new ArrayList<StockBean>();
                    List<StockType> listStock1 = result;
                    for (int j = 0; j < listStock1.size(); j++) {
                        if ("SignShopDetail".equals(signShopDetail)) {
                            // 签到特卖商品 任何商品类型 价格固定为几元包邮
                            listStock1.get(j).setPrice(list.get(0).getShop_se_price());
                        }
                        StockType type = listStock1.get(j);
                        StockBean bean = new StockBean();
                        bean.setStock_type_id(type.getId());
                        bean.setShopCode(type.getShop_code());
                        // bean.setColor(type.getColor_id());
                        bean.setSupp_id(type.getSupp_id());
                        listStocks.add(bean);
                    }
                    for (int s = 0; s < list.size(); s++) {
                        List<StockType> listStock = new ArrayList<StockType>();
                        for (int k = 0; k < result.size(); k++) {
                            if (list.get(s).getShop_code().equals(result.get(k).getShop_code())) {
                                listStock.add(result.get(k));
                            }
                        }
                        list.get(s).setList_stock_type(listStock);
                    }

                }

                super.onPostExecute(context, result, e);
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
                        if (null != shareWaitDialog) {
                            shareWaitDialog.dismiss();
                        }
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
                    download(null, picListNine, shop_codeNine, mapInfosNine, shopNine, linkNine, four_picNine,
                            nineBitmap);
                } else {
                    download(null, picListNine, shop_codeNine, mapInfosNine, shopNine, linkNine, four_picNine, null);
                }
            }
        }
    };

    /////////////////////
    private Bitmap downloadPic(String picPath) {
        try {
            URL url = new URL(YUrl.imgurl + picPath);
            // 打开连接
            URLConnection con = url.openConnection();
            // 获得文件的长度
            int contentLength = con.getContentLength();
            // System.out.println("长度 :" + contentLength);
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[8192];
            // 读取到的数据长度
            int len;
            BitmapDrawable bmpDraw = new BitmapDrawable(is);

            // 完毕，关闭所有链接
            is.close();
            return bmpDraw.getBitmap();
        } catch (Exception e) {
            LogYiFu.e("TAG", "下载失败");
            e.printStackTrace();
            return null;
        }
    }


    private void setHeadImg(String picPath, final ImageView img_header) {
        try {
//            URL url = new URL(picPath);
//            // 打开连接
//            URLConnection con = url.openConnection();
//            // 获得文件的长度
//            int contentLength = con.getContentLength();
//            // System.out.println("长度 :" + contentLength);
//            // 输入流
//            InputStream is = con.getInputStream();
//            // 1K的数据缓冲
//            byte[] bs = new byte[8192];
//            // 读取到的数据长度
//            int len;
//            final BitmapDrawable bmpDraw = new BitmapDrawable(is);
//
//            // 完毕，关闭所有链接
//            is.close();
//
//            final Bitmap bitmap = bmpDraw.getBitmap();
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if(bitmap != null) {
//                        int w = bitmap.getWidth(); // 得到图片的宽，高
//                        int cropHeight = w * 7 / 6;
//                        Bitmap cropBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, cropHeight, null, false);
//                        img_header.setImageBitmap(cropBitmap);
//                    }
//                }
//            });


//            PicassoUtils.initImage(context,picPath,img_header);


            img_header.setImageBitmap(getImage(picPath));

        } catch (Exception e) {
            LogYiFu.e("TAG", "下载失败");
            e.printStackTrace();
        }
    }

    public static Bitmap getImage(String path) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5*1000);
            // 连接设置获取数据流
            conn.setDoInput(true);
            // 不使用缓存
            conn.setUseCaches(false);
            //conn.connect();
            InputStream in = conn.getInputStream();
            // 解析得到图片
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            return bitmap;
        }
    }
    /***
     * 查找商品属性
     */
    public void queryShopQueryAttr(final int i) {
        clickFlag = false;
        if (shop != null) {
            List<StockType> list = shop.getList_stock_type();
            if (list != null && list.size() > 0) {
                showPopWindow(i);
            } else {
                new SAsyncTask<String, Void, Shop>(this, null, R.string.wait) {
                    @Override
                    protected Shop doInBackground(FragmentActivity context, String... params) throws Exception {
                        return ComModel.queryShopQueryAttr(ShopDetailsActivity.this, shop, params[0]);
                    }

                    @Override
                    protected void onPostExecute(FragmentActivity context, Shop shop, Exception e) {

                        if (e != null) {// 查询异常
                            Toast.makeText(ShopDetailsActivity.this, "连接超时，请重试", Toast.LENGTH_LONG).show();
                        } else {// 查询商品详情成功，刷新界面
                            if (shop != null) {
                                ShopDetailsActivity.this.shop = shop;
                                showPopWindow(i);// 商品属性选择
                            }
                        }

                    }

                    ;

                    @Override
                    protected boolean isHandleException() {
                        return true;
                    }

                    ;
                }.execute("false");
            }

        } else {
            ToastUtil.showShortText(context, "无效操作");
        }
    }


    /***
     * 查找商品属性(1元购买)
     */
    public void queryShopQueryAttrOneBuy() {
        clickFlag = false;
        if (shop != null) {
            List<StockType> list = shop.getList_stock_type();
            if (list != null && list.size() > 0) {
                showPopWindowOneBuy();// 商品属性选择
            } else {
                new SAsyncTask<String, Void, Shop>(this, null, R.string.wait) {
                    @Override
                    protected Shop doInBackground(FragmentActivity context, String... params) throws Exception {
                        return ComModel.queryShopQueryAttr(ShopDetailsActivity.this, shop, params[0]);
                    }

                    @Override
                    protected void onPostExecute(FragmentActivity context, Shop shop, Exception e) {

                        if (e != null) {// 查询异常
                            Toast.makeText(ShopDetailsActivity.this, "连接超时，请重试", Toast.LENGTH_LONG).show();
                        } else {// 查询商品详情成功，刷新界面
                            if (shop != null) {
                                ShopDetailsActivity.this.shop = shop;
                                showPopWindowOneBuy();// 商品属性选择
                            }
                        }

                    }


                    @Override
                    protected boolean isHandleException() {
                        return true;
                    }

                }.execute("false");
            }

        } else {
            ToastUtil.showShortText(context, "无效操作");
        }
    }


    private GoodsEntity entity;


    /****
     * 弹出底部对话框(1元购买)
     *
     */
    private void showPopWindowOneBuy() {
        if (shop != null && !this.isFinishing()) {
            final OnBuyShopDetailsDialog dlg;

            dlg = new OnBuyShopDetailsDialog(this, R.style.DialogStyle, width, height, shop, 0, false, "-1", "-1", "1");
            Window window = dlg.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.dlg_down_to_top);
            dlg.show();
            dlg.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface arg0) {
                    // TODO Auto-generated method stub
                    clickFlag = true;
                }
            });
            dlg.callBackShopCart = new OnBuyShopDetailsDialog.OnCallBackShopCart() {

                @Override
                public void callBackChoose(int type, String size, String color, double price, int shop_num,
                                           int stock_type_id, int stock, String pic, int supp_id, double kickback, int original_price,
                                           View v) {
                    dlg.dismiss();
                    // clickFlag = true;
                    entity = new GoodsEntity(pic, size, color, shop_num, stock_type_id, stock, supp_id, stock_type_id,
                            price, kickback, original_price);


                    Intent intent;

                    intent = new Intent(ShopDetailsActivity.this, OneBuySubmitShopActivty.class);//新拼团
                    Bundle bundle = new Bundle();

                    List<ShopCart> listGoods = new ArrayList<ShopCart>();
                    ShopCart shopCart = new ShopCart();
                    shopCart.setSupp_label("" + shop.getSupp_label());
                    shopCart.setShop_code(shop.getShop_code());
                    shopCart.setShop_num(shop_num);
                    shopCart.setSize(size);
                    shopCart.setColor(color);
                    shopCart.setShop_price(shop.getShop_price());
                    shopCart.setShop_se_price(shop.getShop_se_price());
                    shopCart.setOriginal_price(Double.valueOf(original_price));
                    shopCart.setDef_pic(pic);
                    shopCart.setStock_type_id(stock_type_id);
                    shopCart.setSupp_id(shop.getSupp_id());
                    shopCart.setShop_name(shop.getShop_name());
                    shopCart.setCore("0");
                    shopCart.setKickback(0.0);
                    shopCart.setUser_id(YCache.getCacheUser(context).getUser_id());
                    shopCart.setStore_code(YCache.getCacheStore(context).getS_code());
                    listGoods.add(shopCart);

                    bundle.putSerializable("listGoods", (Serializable) listGoods);
                    bundle.putBoolean("mIsTwoGroup", mIsTwoGroup);
                    bundle.putString("rollCode", "" + rollCode);

                    bundle.putString("onePrice", shop.getAssmble_price());
                    intent.putExtras(bundle);
                    intent.putExtra("groupFlag", groupFlag);

                    if (OneBuySubmitShopActivty.instance != null) {
                        OneBuySubmitShopActivty.instance.finish();
                    }
                    startActivity(intent);
                }


            };
        }
    }


    private void showPopWindow(int i) {
        if (shop != null && !this.isFinishing()) {
            final ShopDetailsDialog dlg;
            if (groupFlag == 1 || groupFlag == 2) {// 代表是组团购买的
                dlg = new ShopDetailsDialog(this, R.style.DialogStyle, width, height, shop, i, true, "-1", "-1", "1");// 增加最后一个boolean值是为了显示属性时的价格显示为组团价格；true代表组团购买
            } else {
                dlg = new ShopDetailsDialog(this, R.style.DialogStyle, width, height, shop, i, false, "-1", "-1", "1");
            }
            Window window = dlg.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.dlg_down_to_top);
            dlg.show();
            dlg.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface arg0) {
                    // TODO Auto-generated method stub
                    clickFlag = true;
                }
            });
            dlg.callBackShopCart = new ShopDetailsDialog.OnCallBackShopCart() {

                @Override
                public void callBackChoose(int type, String size, String color, double price, int shop_num,
                                           int stock_type_id, int stock, String pic, int supp_id, double kickback, int original_price,
                                           View v) {
                    dlg.dismiss();
                    // clickFlag = true;
                    entity = new GoodsEntity(pic, size, color, shop_num, stock_type_id, stock, supp_id, stock_type_id,
                            price, kickback, original_price);

                    if (type == 1) {// 购买 活动商品直接购买


                        Intent intent;
                        if (isSignActiveShop && SignGroupShopActivity.listClick.size() == 0 && groupFlag != 0) {
                            intent = new Intent();
                        } else {
                            intent = new Intent(ShopDetailsActivity.this, SubmitMultiShopActivty.class);// 购买
                        }                                                                            // 活动商品直接购买
                        Bundle bundle = new Bundle();

                        List<ShopCart> listGoods = new ArrayList<ShopCart>();
                        ShopCart shopCart = new ShopCart();
                        shopCart.setSupp_label("" + shop.getSupp_label());
                        shopCart.setShop_code(shop.getShop_code());
                        shopCart.setShop_num(shop_num);
                        shopCart.setSize(size);
                        shopCart.setColor(color);
                        shopCart.setShop_price(shop.getShop_price());
                        if (isSignActiveShop && (groupFlag == 1 || groupFlag == 2)) {
                            shopCart.setShop_group_price(shop.getRoll_price());
                        }
                        shopCart.setShop_se_price(shop.getShop_se_price());
                        shopCart.setOriginal_price(Double.valueOf(original_price));
                        shopCart.setDef_pic(pic);
                        shopCart.setStock_type_id(stock_type_id);
                        shopCart.setSupp_id(shop.getSupp_id());
                        shopCart.setShop_name(shop.getShop_name());
                        shopCart.setCore("0");
                        shopCart.setKickback(0.0);
                        shopCart.setUser_id(YCache.getCacheUser(context).getUser_id());
                        shopCart.setStore_code(YCache.getCacheStore(context).getS_code());
                        listGoods.add(shopCart);

                        bundle.putSerializable("listGoods", (Serializable) listGoods);

                        bundle.putBoolean("mIsTwoGroup", mIsTwoGroup);
                        bundle.putString("rollCode", "" + rollCode);
                        intent.putExtras(bundle);
                        intent.putExtra("groupFlag", groupFlag);
                        boolean flag2 = false;
                        int position = 0;
                        if (isSignActiveShop && SignGroupShopActivity.listClick.size() == 0 && groupFlag != 0) {//拼团购选择第一件商品
                            setResult(SignGroupShopActivity.RESULT_DETAILS, intent);
                            ShopDetailsActivity.this.finish();
                        } else {
                            if (isSignActiveShop && groupFlag != 0) {//活动商品，X人成团
                                for (int j = 0; j < SignGroupShopActivity.listClick.size(); j++) {//拼团购判断是修改其中的商品，还是直接添加商品
                                    if (("" + shop.getShop_code()).equals("" + SignGroupShopActivity.listClick.get(j).getShop_code())) {
                                        flag2 = true;
                                        position = j;
                                        break;
                                    }
                                }
                                if (flag2) {//修改商品
                                    SignGroupShopActivity.listClick.add(position, shopCart);
                                    SignGroupShopActivity.listClick.remove(position + 1);
                                } else {//添加商品
                                    if (SignGroupShopActivity.listClick.size() == 2) {
                                        SignGroupShopActivity.listClick.remove(1);
                                    }
                                    SignGroupShopActivity.listClick.add(shopCart);
                                }
                                listGoods.clear();
                                listGoods.addAll(SignGroupShopActivity.listClick);
                                ShopDetailsActivity.instance.finish();
                                intent.putExtra("mSignGroupsPrice", Double.parseDouble(mSignGroupsPrice));
                            } else {
                            }
                            if (SubmitMultiShopActivty.instance != null) {
                                SubmitMultiShopActivty.instance.finish();
                            }
                            startActivity(intent);
                        }
                    } else {// 加入购物车

                        if (TextUtils.isEmpty(pic)) {
                            pic = shop.getDef_pic();
                        }

                        joinShopCart(size, color, shop_num, stock_type_id, pic, price, supp_id, kickback,
                                original_price, v);
                        // clickFlag = true;
                    }
                }
            };
        }
    }


    /***
     * 加入购物车
     *
     * @param size
     * @param color
     * @param shop_num
     * @param v
     */
    // private Drawable d;
    int id_dao;

    private void joinShopCart(final String size, final String color, final int shop_num, final int stock_type_id,
                              final String pic, final double realPrice, final int supplyId, final double kickback,
                              final int original_price, final View v) {
        final ShopCartDao dao = new ShopCartDao(context);

        addAnimLayout();// 添加动画布局 小圆点

        List<ShopCart> list = dao.findAll();
        // List<ShopCart> list_invalid = dao.findAll_invalid();
        boolean id_flag = false;
        // boolean id_flag_invalid = false;// 失效列表有
        int shop_num_old = 0;
        // int shop_num_old_invalid = 0;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (("" + stock_type_id).equals("" + list.get(i).getStock_type_id())) {
                    id_flag = true;
                    id_dao = list.get(i).getId();
                    shop_num_old = list.get(i).getShop_num();
                    break;
                }
            }
        }

        if (!id_flag) {// 需要获取id
            if (shop_num > 2) {
                // clickFlag = true;
                rootView.removeView(pointRoot);
                ToastUtil.showShortText(context, "抱歉，数量有限，最多只能购买2件噢！");
            } else {
                new SAsyncTask<String, Void, HashMap<String, Object>>(this, v, R.string.wait) {

                    @Override
                    protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
                            throws Exception {

                        return ComModel2.getShopCartData(ShopDetailsActivity.this, 1);

                    }

                    @Override
                    protected boolean isHandleException() {
                        return true;
                    }

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    ;

                    @Override
                    protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result,
                                                 Exception e) {

                        if (null == e) {
                            int id = Integer.parseInt((String) result.get("id"));
                            addCart(size, color, shop_num, stock_type_id, pic, realPrice, supplyId, kickback,
                                    original_price, v, id);
                            UserInfo user = YCache.getCacheUser(ShopDetailsActivity.this);
                            Store store = YCache.getCacheStore(ShopDetailsActivity.this);
                            boolean hh = dao.add(shop.getShop_code(), size, color,
                                    Integer.parseInt(String.valueOf(shop_num)),
                                    Integer.parseInt(String.valueOf(stock_type_id)), pic, user.getUser_id(),
                                    shop.getShop_name(), store.getS_code(), "" + shop.getShop_price(),
                                    "" + shop.getShop_se_price(), supplyId + "", "" + kickback, "" + original_price, 0,
                                    null, null, null, null, null, id, null, 0, mSupp_label);


                        } else {
                            // clickFlag = true;
                            rootView.removeView(pointRoot);
                        }

                        super.onPostExecute(context, result, e);
                    }

                }.execute();
            }

        } else {// 不需要获取购物车id
            if (id_flag) {// 只有有效的
                if (shop_num + shop_num_old > 2) {
                    // clickFlag = true;
                    rootView.removeView(pointRoot);
                    ToastUtil.showShortText(context, "抱歉，数量有限，最多只能购买2件噢！");
                } else {
                    addCart(size, color, shop_num, stock_type_id, pic, realPrice, supplyId, kickback, original_price, v,
                            id_dao);
                    dao.modify("" + stock_type_id, shop_num + shop_num_old);
                }
            }

        }
    }

    /**
     * @param size
     * @param color
     * @param shop_num
     * @param stock_type_id
     * @param pic
     * @param realPrice
     * @param supplyId
     * @param kickback
     * @param original_price
     * @param v
     */
    private void addCart(final String size, final String color, final int shop_num, final int stock_type_id,
                         final String pic, final double realPrice, final int supplyId, final double kickback,
                         final int original_price, View v, final int id) {
        new SAsyncTask<String, Void, ReturnInfo>(this, v, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {
                UserInfo user = YCache.getCacheUser(ShopDetailsActivity.this);
                Store store = YCache.getCacheStore(ShopDetailsActivity.this);

                return ComModel.joinShopCart(ShopDetailsActivity.this, params[0], params[1], params[2], params[3],
                        params[4], "" + user.getUser_id(), YCache.getCacheToken(ShopDetailsActivity.this), realPrice,
                        shop, store.getS_code(), supplyId + "", kickback, original_price, id, mSupp_label);

            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {

                if (null == e && null != result && result.getStatus().equals("1")) {

                    int needValue = 1;
                    try {
                        needValue = Integer.parseInt(SignListAdapter.doValueShopCart);
                    } catch (Exception e2) {
                    }
                    int signNumber = 1;// 1，代表一次性奖励，大于1代表多次奖励
                    try {
                        signNumber = SignListAdapter.doNumShopCart;
                    } catch (Exception e2) {
                    }
                    double jiangliValue = 5.0;
                    try {
                        jiangliValue = Double.parseDouble(SignListAdapter.jiangliValueShopCart);
                    } catch (Exception e2) {
                    }

                    String mNotice = "元";
                    int jiangliType = 5;
                    try {
                        jiangliType = SignListAdapter.jiangliIDShopCart;
                    } catch (Exception e2) {
                    }
                    switch (jiangliType) {
                        case 3:
                            mNotice = "元优惠券";
                            break;
                        case 4:
                            mNotice = "积分";
                            break;
                        case 5:
                            mNotice = "元";
                            break;

                        default:
                            break;
                    }
                    String qiandao_time = SharedPreferencesUtil.getStringData(ShopDetailsActivity.this,
                            "qiandao_time" + YCache.getCacheUser(ShopDetailsActivity.this).getUser_id(), "");
                    String qiandao_num = SharedPreferencesUtil.getStringData(ShopDetailsActivity.this,
                            "qiandao_num" + YCache.getCacheUser(ShopDetailsActivity.this).getUser_id(), "0");
                    // ShopCartDao dao=new
                    // ShopCartDao(ShopDetailsActivity.this);
                    int now_num = Integer.parseInt(qiandao_num) + shop_num;
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String now_time = sdf.format(date);
                    if (qiandao_time.equals(now_time)) {
                        SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
                                "qiandao_num" + YCache.getCacheUser(ShopDetailsActivity.this).getUser_id(),
                                "" + now_num);
                        if (now_num >= needValue) {
                            sign_shopcart(true, 0);
                            SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
                                    "qiandao_time" + YCache.getCacheUser(ShopDetailsActivity.this).getUser_id(), "-1");
                        } else {
                            // if(signNumber>1||jiangliType==3){
                            ToastUtil.showShortText(context, "再加" + (needValue - now_num) + "件商品可完成任务喔~");
                        }
                    }
                    SharedPreferencesUtil.saveBooleanData(context, "undo_view4", true);// 加入购物车时候
                    // 主界面的购物车数量设置为重新显示
                    int cartCount = 0;
                    if (result.getIsCart() == 0) {
                        cartCount = shop.getCart_count() + 1;
                    } else {
                        cartCount = shop.getCart_count();
                    }
                    shop.setCart_count(cartCount);
                    setAnim();// 加入购物车抛物线动画


                } else {
                    // clickFlag = true;
                    rootView.removeView(pointRoot);
                    ShopCartDao dao = new ShopCartDao(context);
                    List<ShopCart> list = dao.findAll();
                    int shop_num_old = 0;
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            if (stock_type_id == list.get(i).getStock_type_id()) {
                                shop_num_old = list.get(i).getShop_num();
                                break;
                            }
                        }
                    }
                    dao.modify("" + stock_type_id, shop_num_old - shop_num);
                }
                super.onPostExecute(context, result, e);
            }

        }.execute(size, color, String.valueOf(shop_num), String.valueOf(stock_type_id), pic);
    }


    class TimeCount extends CountDownTimer {
        private TextView tv = null;

        public TimeCount(long millisInFuture, long countDownInterval, TextView tv) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
            this.tv = tv;

        }

        @Override
        public void onFinish() {// 计时完毕时触发
            try {
                tv.setText("打折结束");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            try {
                long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
                long nh = 1000 * 60 * 60;// 一小时的毫秒数
                long nm = 1000 * 60;// 一分钟的毫秒数
                long ns = 1000;// 一秒钟的毫秒数
                long diff = millisUntilFinished;
                long day = diff / nd;// 计算差多少天
                long hour = diff % nd / nh;// 计算差多少小时
                long min = diff % nd % nh / nm;// 计算差多少分钟
                long sec = diff % nd % nh % nm / ns;// 计算差多少秒//输出结果

                tv.setText("距打折时间还有" + day + "天    " + hour + "小时" + min + "分" + sec + "秒");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int titleCheck = -1;// 当前导航栏选择

    @Override
    public void myOnClick(View v) {

        mType = 1;
        index = 1;
        titleCheck = v.getId();
        initData(v.getId(), index + "");
        if ("SignShopDetail".equals(signShopDetail)) {
            if (mListView.getFirstVisiblePosition() > (imageTag1.length + imageTag2.length + imageTag3.length)) {
                mListView.setSelection(imageTag1.length + imageTag2.length + imageTag3.length + 1);
            }
        } else {
            if (mListView.getFirstVisiblePosition() > images.length) {
                mListView.setSelection(images.length + 1);
            }
        }
    }

    private boolean isInit = false;

    private void initData(final int position, final String index) {
        isInit = true;
        new SAsyncTask<String, Void, List<HashMap<String, Object>>>(this, 0) {

            @Override
            protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (dataList == null) {
                        dataList = new LinkedList<HashMap<String, Object>>();
                    }
                    if (mType == 1) {
                        dataList.clear();
                    }
                    if (mType == 2 && (result == null || result.isEmpty())) {
                        Toast.makeText(ShopDetailsActivity.this, "已经到底了", Toast.LENGTH_SHORT).show();
                        // myListView.onRefreshComplete();
                        return;
                    }
                    dataList.addAll(result);
                    adapter.notifyDataSetChanged();
                    isInit = false;
                }
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                return ComModel2.getProductListUnLogin(context, index, listTitle.get(position).get("_id"),
                        String.valueOf(1), listTitle.isEmpty() ? null : listTitle.get(position).get("sort_name"),
                        Integer.parseInt("30"), false);
            }

        }.execute();
    }

    @Override
    public void onCheck(int index) {

        if (check == index) {
            return;
        }
        check = index;
        adapter.notifyDataSetChanged();
        if (check == 2) {
            if ("SignShopDetail".equals(signShopDetail)) {
                if (tuijianList == null) {
                    queryTuijian();
                }
                return;
            }
            if (listShopComments == null && isCheck == false) {
                querySelCommentByShop();
            }

        }

        if (mListView.getFirstVisiblePosition() > 0) {
            mListView.setSelection(1);
        }

    }

    private int rows = 10, page = 1;
    private boolean isCheck = false;

    private void querySelCommentByShop() {
        // if (page == 1) {
        // rows = 5;
        // } else {
        rows = 10;
        // }
        isCheck = true;
        new SAsyncTask<Void, Void, List<ShopComment>>((FragmentActivity) context, null, R.string.wait) {

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                LoadingDialog.show(ShopDetailsActivity.this);
            }

            @Override
            protected List<ShopComment> doInBackground(FragmentActivity context, Void... params) throws Exception {
                if (page == 1) {
                    List<ShopComment> list_youxuan = ComModel.queryShopEvaluate((FragmentActivity) context, "" + 1,
                            "" + rows, "" + code, true);
                    if (list_youxuan != null && list_youxuan.size() > 0 && listShopCommentsYouXuan.size() == 0) {
                        listShopCommentsYouXuan.addAll(list_youxuan);
                    }
                }
                List<ShopComment> list = ComModel.queryShopEvaluate((FragmentActivity) context, "" + page, "" + rows,
                        "" + code, false);
                return list;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<ShopComment> list, Exception e) {
                isCheck = false;
                if (e != null) {// 查询异常
                    Toast.makeText(context, "连接超时，请重试", Toast.LENGTH_LONG).show();
                    page--;
                } else {// 查询商品详情成功，刷新界面
                    rrr.setBackgroundColor(Color.WHITE);
                    if (list != null && list.size() > 0) {
                        if (listShopComments == null) {
                            listShopComments = new ArrayList<ShopComment>();
                        }
                        if (page == 1 && listShopCommentsYouXuan.size() > 0) {
                            listShopComments.addAll(listShopCommentsYouXuan);
                        }
                        listShopComments.addAll(list);
                    } else {
                        // Toast.makeText(ShopDetailsActivity.this, "已经到底了",
                        // Toast.LENGTH_SHORT).show();
                        isCheck = true;
                    }
                    adapter.notifyDataSetChanged();
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

    private void queryTuijian() {
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) context, null, R.string.wait) {
            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel2.getMainTuijianData(context, "2");
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null) {
                    tuijianList = (List<ShopOption>) result.get("centShops");
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            ;
        }.execute();
    }

    // px转成dp
    public int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    // 购物车用的
    private void jisuan() {
        recLen -= 1000;
        String days;
        String hours;
        String minutes;
        String seconds;
        long minute = recLen / 60000;
        long second = (recLen % 60000) / 1000;
        if (minute >= 60) {
            long hour = minute / 60;
            minute = minute % 60;
            if (hour >= 24) {
                long day = hour / 24;
                hour = hour % 24;
                if (day < 10) {
                    days = "0" + day;
                } else {
                    days = "" + day;
                }
                if (hour < 10) {
                    hours = "0" + hour;
                } else {
                    hours = "" + hour;
                }
                if (minute < 10) {
                    minutes = "0" + minute;
                } else {
                    minutes = "" + minute;
                }
                if (second < 10) {
                    seconds = "0" + second;
                } else {
                    seconds = "" + second;
                }
                tv_time_count_down.setText("" + days + "天" + hours + "时" + minutes + "分" + seconds + "秒");
                tv_time_count_down2.setText("" + days + "天" + hours + "时" + minutes + "分" + seconds + "秒");
            } else {
                if (hour < 10) {
                    hours = "0" + hour;
                } else {
                    hours = "" + hour;
                }
                if (minute < 10) {
                    minutes = "0" + minute;
                } else {
                    minutes = "" + minute;
                }
                if (second < 10) {
                    seconds = "0" + second;
                } else {
                    seconds = "" + second;
                }
                tv_time_count_down.setText("" + hours + "时" + minutes + "分" + seconds + "秒");
                tv_time_count_down2.setText("" + hours + "时" + minutes + "分" + seconds + "秒");
            }
        } else if (minute >= 10 && second >= 10) {
            tv_time_count_down.setText("" + minute + ":" + second + "");
            tv_time_count_down2.setText("" + minute + ":" + second + "");
        } else if (minute >= 10 && second < 10) {
            tv_time_count_down.setText("" + minute + ":0" + second + "");
            tv_time_count_down2.setText("" + minute + ":0" + second + "");
        } else if (minute < 10 && second >= 10) {
            tv_time_count_down.setText("0" + minute + ":" + second + "");
            tv_time_count_down2.setText("0" + minute + ":" + second + "");
        } else {
            tv_time_count_down.setText("0" + minute + ":0" + second + "");
            tv_time_count_down2.setText("0" + minute + ":0" + second + "");
        }
        if (recLen <= 0) {
            // System.out.println("取消的");
            tv_time_count_down.setText("00:00");
            // tv_time_count_down.setVisibility(View.VISIBLE);
            tv_time_count_down2.setText("00:00");
            // tv_time_count_down2.setVisibility(View.VISIBLE);
            tv_time_count_down_meal.setVisibility(View.GONE);
            tv_cart_count.setVisibility(View.VISIBLE);// 数量小圆点消失
            tv_cart_count2.setVisibility(View.VISIBLE);// 数量小圆点消失

        }
    }


    private void ThreeSecond() {
        rl_hava_twenty.setVisibility(View.VISIBLE);
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    rl_hava_twenty.setVisibility(View.GONE);
                    // rl_retain.getBackground().setAlpha(0);
                }
            }

            ;
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60 * 1000);
                    // rl_retain.setVisibility(View.GONE);
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void yunYunTongJi(final String shop_code, final int type, final int tab_type) {
        new SAsyncTask<Void, Void, HashMap<String, String>>(this, R.string.wait) {

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

    // 加入购物车动画
    private RelativeLayout img_cart_cart;

    private void setAnim() {
        if (signShopDetail == null || !signShopDetail.equals("SignShopDetail")) {
            img_cart_cart = (RelativeLayout) this.findViewById(R.id.img_cart_cart);
        }
        // 获取起点坐标
        int[] location1 = new int[2];
        mCartPoint.getLocationInWindow(location1);
        int x1 = location1[0];
        int y1 = location1[1];
        // 获取终点坐标，最近拍摄的坐标
        int[] location2 = new int[2];
        tv_cart_count.getLocationInWindow(location2);
        int x2 = location2[0];
        int y2 = location2[1];
        // int[] location3 = new int[2];
        int x3 = (x1 + x2) / 2;
        int y3 = y2 - 90;
        // 两个位移动画
        TranslateAnimation translateAnimationX = new TranslateAnimation(0, x3 - x1, 0, 0); // 横向动画
        final TranslateAnimation translateAnimationX2 = new TranslateAnimation(x3 - x1, x2 - x1, 0, 0); // 横向动画
        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, y3 - y1); // 竖向动画
        final TranslateAnimation translateAnimationY2 = new TranslateAnimation(0, 0, y3 - y1, y2 - y1); // 竖向动画
        translateAnimationX.setInterpolator(new LinearInterpolator()); // 横向动画设为匀速运动
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX2.setInterpolator(new LinearInterpolator()); // 横向动画设为匀速运动
        translateAnimationX2.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationY.setInterpolator(new AccelerateDecelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        // translateAnimationY.setRepeatMode(Animation.REVERSE);
        translateAnimationY2.setInterpolator(new AccelerateInterpolator()); // 竖向动画设为开始结尾处加速，中间迅速
        translateAnimationY2.setRepeatCount(0);// 动画重复执行的次数
        // 组合动画
        final AnimationSet anim = new AnimationSet(false);
        final AnimationSet anim2 = new AnimationSet(false);
        anim.setFillAfter(false); // 动画结束不停留在最后一帧
        anim2.setFillAfter(false); // 动画结束不停留在最后一帧
        anim.addAnimation(translateAnimationX);
        anim.addAnimation(translateAnimationY);

        anim2.addAnimation(translateAnimationX2);
        anim2.addAnimation(translateAnimationY2);
        anim2.setDuration(400);// 动画的执行时间
        anim2.setStartOffset(0);

        anim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mCartPoint.startAnimation(anim2);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });
        anim2.setAnimationListener(new AnimationListener() { // 抛物线动画结束后
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                pointRoot.removeAllViews();
                mCartPoint.setVisibility(View.INVISIBLE);
                tv_cart_count.setVisibility(View.VISIBLE);// 抛物线动画结束后 即可显示数量小圆点
                setAnim2();
            }
        });

        // 播放
        mCartPoint.setVisibility(View.VISIBLE);
        anim.setDuration(400);// 动画的执行时间
        anim.setStartOffset(100);
        mCartPoint.startAnimation(anim);
    }

    /**
     * 购物车圆点缩放动画
     */
    protected void setAnim2() {
        ScaleAnimation animation = new ScaleAnimation(1.0f, 1.4f, 1.0f, 1.4f);
        animation.setRepeatCount(0);// 动画重复执行的次数
        animation.setDuration(400);// 动画的执行时间
        animation.setFillAfter(false); // 动画结束不停留在最后一帧
        animation.setStartOffset(0);
        tv_cart_count.startAnimation(animation);
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // clickFlag = true;
                img_cart_cart.clearAnimation();
                // 显示立即结算
                // showRetain();
                queryCartCountAdd();
            }

        });
    }

    private RelativeLayout rootView;
    private ImageView mCartPoint;
    private RelativeLayout pointRoot;
    private LinearLayout ll_bottem;
    private Animation baoyou_animationShow;
    private Animation baoyou_animationGone;

    /**
     * 添加动画布局
     */
    private void addAnimLayout() {
        rootView = (RelativeLayout) findViewById(R.id.rrr);
        pointRoot = new RelativeLayout(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        pointRoot.setBackgroundColor(Color.TRANSPARENT);
        pointRoot.setLayoutParams(params);
        rootView.addView(pointRoot);

        mCartPoint = new ImageView(context);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(DP2SPUtil.dp2px(context, 12),
                DP2SPUtil.dp2px(context, 12));
        params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params2.bottomMargin = DP2SPUtil.dp2px(context, 36);
//		params2.rightMargin = DP2SPUtil.dp2px(context, 165);
        params2.rightMargin = width / 2 - 20;
        mCartPoint.setLayoutParams(params2);
        mCartPoint.setBackgroundResource(R.drawable.red_point_bg);
        pointRoot.addView(mCartPoint);
        mCartPoint.setVisibility(View.VISIBLE);

    }

    /**
     * 添加购物车时候查询 购物车数量 并显示倒计时
     */
    private void queryCartCountAdd() {

        ShopCartDao dao = new ShopCartDao(context);
        // int count = dao.queryCartCount(context);
        int count = 0;

        count = dao.queryCartCommonCount(context);
        if (/* cartCount */count > 0) {
            count = count > 99 ? 99 : count;
            tv_cart_count.setText(count + "");
            tv_cart_count2.setText(count + "");
        } else {

        }

        Long sTime = new Date().getTime();// 系统当前时间

        Long sDeadline = false
                ? Long.valueOf(SharedPreferencesUtil.getStringData(context, Pref.SHOPCART_MEAL_TIME, "0"))
                : Long.valueOf(SharedPreferencesUtil.getStringData(context, Pref.SHOPCART_COMMON_TIME, "0"));// 商品过期时间

        if (sDeadline == 0) {// 表示还没有存到本地

            SharedPreferencesUtil.saveStringData(context, Pref.SHOPCART_COMMON_TIME, sTime + 30 * 1000 * 60 + "");

        }
        tv_time_count_down.setVisibility(View.GONE);
        tv_time_count_down2.setVisibility(View.GONE);
        tv_time_count_down_meal.setVisibility(View.GONE);
        // }
        // }
        // }.execute();
    }

    private void setShareAnim() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String shareAnim = SharedPreferencesUtil.getStringData(context, Pref.SHAREANIM, "0");
        long shareAnimTime = Long.valueOf(shareAnim);
        boolean isRoate = "0".equals(shareAnim) || !df.format(new Date()).equals(df.format(new Date(shareAnimTime)));
        if (!isRoate) {
            return;
        }
        RotateAnimation ani1 = new RotateAnimation(0f, 35f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        ScaleAnimation ani2 = new ScaleAnimation(1.0f, 0.85f, 1.0f, 0.85f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        final AnimationSet set = new AnimationSet(context, null);
        ani1.setDuration(270);
        ani1.setRepeatMode(Animation.REVERSE);
        // ani1.setRepeatCount(1);
        ani1.setFillAfter(false);
        // ani1.setStartOffset(1500);
        ani2.setDuration(270);
        ani2.setRepeatMode(Animation.RESTART);
        // ani2.setRepeatCount(Integer.MAX_VALUE);
        ani2.setFillAfter(false);
        // ani2.setStartOffset(1500);

        set.addAnimation(ani1);
        set.addAnimation(ani2);
        set.setStartOffset(600);
        // redShare.setAnimation(set);
        redShare.startAnimation(set);

        final RotateAnimation ani3 = new RotateAnimation(-12f, 10f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        ani3.setDuration(55);
        ani3.setRepeatMode(Animation.REVERSE);
        ani3.setRepeatCount(2);
        ani3.setFillAfter(true);
        final RotateAnimation ani4 = new RotateAnimation(-6f, 6f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        ani4.setDuration(45);
        ani4.setRepeatMode(Animation.REVERSE);
        ani4.setRepeatCount(1);
        ani4.setFillAfter(false);

        ani1.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moneyShare.startAnimation(ani3);
                set.setStartOffset(1300);
            }
        });
        ani3.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moneyShare.startAnimation(ani4);
            }
        });
        ani4.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                redShare.startAnimation(set);
            }
        });
    }


    public void getSignJoinShopCartDialog(Double jiangliValue) {
        final Dialog signDialog = new Dialog(ShopDetailsActivity.this, R.style.invate_dialog_style);
        View view = View.inflate(ShopDetailsActivity.this, R.layout.dialog_sign_join_shopcart, null);
        TextView mTvJiangli = (TextView) view.findViewById(R.id.dialog_sign_jiangli);
        String mNotice = "元";
        int jiangliType = 5;
        try {
            jiangliType = SignListAdapter.jiangliIDShopCart;
        } catch (Exception e2) {
        }
        switch (jiangliType) {
            case 3:
                mNotice = "元优惠券";
                break;
            case 4:
                mNotice = "积分";
                break;
            case 5:
                mNotice = "元";
                break;
            case 11:
                mNotice = "个衣豆";
                break;
            default:
                break;
        }
        String s = "";
        String str = "";
        if (jiangliType == 11) {
            str = new DecimalFormat("#0").format(jiangliValue);
            s = new DecimalFormat("#0").format(jiangliValue) + mNotice + "奖励已存入你的账户，如果喜欢这件美衣就带它回家吧~";
        } else {
            str = "" + jiangliValue;
            s = jiangliValue + mNotice + "奖励已存入你的账户，如果喜欢这件美衣就带它回家吧~";
        }
        String l = str + mNotice;
        mTvJiangli.setText(s);
        SpannableStringBuilder builder = new SpannableStringBuilder(mTvJiangli.getText().toString());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#ff3f8b"));
        builder.setSpan(redSpan, 0, l.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvJiangli.setText(builder);
        TextView mClose = (TextView) view.findViewById(R.id.tv_close);
        mClose.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                signDialog.dismiss();
            }
        });
        Button liebiao = (Button) view.findViewById(R.id.dialog_liebiao);
        liebiao.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ShopDetailsActivity.this, ShopCartNewNewActivity.class);
                startActivity(intent);
                signDialog.dismiss();


            }
        });
        Button gobuy = (Button) view.findViewById(R.id.gobuy2);
        gobuy.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                if (null != CommonActivity.instance) {
                    CommonActivity.instance.finish();
                }


                // 跳至赚钱

                SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, "commonactivityfrom", "sign");

                Intent intent = new Intent(ShopDetailsActivity.this, CommonActivity.class);
                intent.putExtra("isTastComplete", true);
                startActivity(intent);
                ShopDetailsActivity.this.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                signDialog.dismiss();

                finish();


            }
        });
        signDialog.setContentView(view);
        signDialog.setCancelable(true);
        signDialog.setCanceledOnTouchOutside(false);
        signDialog.show();
    }

    private void sign_shopcart(final boolean flag, final int num) {
        // 签到
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) ShopDetailsActivity.this, 0) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel2.getSignIn(ShopDetailsActivity.this, false, false,
                        SharedPreferencesUtil.getStringData(context, YConstance.ADD_TO_SHOPCART, ""),
                        SignListAdapter.doClass);

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
                        SignCompleteDialogUtil.firstClickInGoToZP(instance);
                        return;
                    }

                    int needValue = 1;
                    try {
                        needValue = Integer.parseInt(SignListAdapter.doValueShopCart);
                    } catch (Exception e2) {
                    }
                    int signNumber = 1;// 1，代表一次性奖励，大于1代表多次奖励
                    try {
                        signNumber = SignListAdapter.doNumShopCart;
                    } catch (Exception e2) {
                    }
                    double jiangliValue = 0.0;
                    try {
                        jiangliValue = Double.parseDouble(SignListAdapter.jiangliValueShopCart);
                    } catch (Exception e2) {
                    }
                    if (flag) {
                        getSignJoinShopCartDialog(signNumber * jiangliValue);
                        SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
                                "qiandao_time" + YCache.getCacheUser(ShopDetailsActivity.this).getUser_id(), "-1");
                    } else {
                        String mNotice = "元";
                        int jiangliType = 5;
                        try {
                            jiangliType = SignListAdapter.jiangliIDShopCart;
                        } catch (Exception e2) {
                        }
                        switch (jiangliType) {
                            case 3:
                                mNotice = "元优惠券";
                                break;
                            case 4:
                                mNotice = "积分";
                                break;
                            case 5:
                                mNotice = "元";
                                break;
                            case 11:
                                mNotice = "个衣豆";
                                break;
                            default:
                                break;
                        }
                        ToastUtil.showShortText(context, "加入成功奖励" + jiangliValue + mNotice + "，还有" + num + "次机会喔~");
                    }
                } else {
                    // ToastUtil.showLongText(mContext, "未知错误");
                }

            }

        }.execute();
    }


    /**
     * 每天第一次进详情 提示购买 可参与抽奖弹框
     */
    private void shopDetailsBuyTipsDialog() {
        final Dialog dialog = new Dialog(context, R.style.DialogQuheijiao2);
        final View view = View.inflate(context, R.layout.shop_details_buy_tips_dialog, null);
        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_yellow).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "Y", 0.0F, 0.1F, 0.2F, 0.3F, 0.4F, 0.5F, 0.6F, 0.7F, 0.8F, 0.9F, 1.0F).setDuration(500);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                view.setAlpha(cVal);
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            }
        });

        // // 创建自定义样式dialog
        dialog.setContentView(view, new LayoutParams(DP2SPUtil.dp2px(context, 270),
                LayoutParams.MATCH_PARENT));
        dialog.show();
    }

    /**
     * 商品详情 购买可抽奖提示 每天只弹出一次*
     *
     * @date 2017年6月5日上午11:39:11
     */
    private void showShopDetailsBuyTipsDialog() {
        boolean isMad = SharedPreferencesUtil.getBooleanData(this, Pref.ISMADMONDAY, false);
        if (!isMad) {
            return;
        }
        if (!isSignActiveShop) {
            tv_buy_now.setText("0元购全返");
            tv_buy_now.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_buy_now.setTextSize(16);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String shopDetailsBuyTips = SharedPreferencesUtil.getStringData(context, Pref.SHOPDETAILSBUYTIPS, "0");
        long shopDetailsBuyTipsTimes = Long.valueOf(shopDetailsBuyTips);
        if ("0".equals(shopDetailsBuyTips) || !df.format(new Date()).equals(df.format(new Date(shopDetailsBuyTipsTimes)))) {
            shopDetailsBuyTipsDialog();
            SharedPreferencesUtil.saveStringData(context, Pref.SHOPDETAILSBUYTIPS, System.currentTimeMillis() + "");
        }

    }

    public void getGroupInitData() {
        new SAsyncTask<String, Void, HashMap<String, String>>(ShopDetailsActivity.this, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                return ComModel2.queryInitData(ShopDetailsActivity.this);
            }

            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null) {
                    String price = result.get("DPPAYPRICE");
                    String count = result.get("rnum");
                    try {
                        double price2 = Double.parseDouble(price);
                        if (price2 > 0) {
                            mSignGroupsPrice = price;
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    try {
                        double count2 = Integer.parseInt(count);
                        if (count2 > 0) {
                            mSignGroupsPeopleCount = count;
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
//					mTwoPrice.setText("￥"+new DecimalFormat("#0.0").format(Double.parseDouble(mSignGroupsPrice)));
                    mTvPeopleCount.setText(mSignGroupsPeopleCount + "人成团");
                }
            }

        }.execute();
    }


    private View img_balance_lottery;

    private void queryBalanceNum() {
        img_balance_lottery = findViewById(R.id.img_balance_lottery);
        if (YJApplication.instance.isLoginSucess() || YJApplication.isLogined) {
            img_balance_lottery.setClickable(false);
            PublicUtil.getBalanceNum(this, img_balance_lottery, false);
        } else {
            img_balance_lottery.setOnClickListener(this);
            img_balance_lottery.setVisibility(View.VISIBLE);
            //onClick 中未登录点击去登陆
        }
    }


}
