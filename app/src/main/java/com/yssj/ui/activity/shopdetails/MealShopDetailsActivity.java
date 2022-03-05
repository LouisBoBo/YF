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

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.ItemView;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.MealRecomenView;
import com.yssj.custom.view.MealShopTopClickView;
import com.yssj.custom.view.MealShopTopClickView.OnCheckedLintener;
import com.yssj.custom.view.MyPopupwindow;
import com.yssj.custom.view.MyPopupwindow.ShopDetailsGetShare;
import com.yssj.custom.view.NewMealOneSharePopupwindow;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.custom.view.ScaleImageView;
import com.yssj.custom.view.ShowHoriontalView;
import com.yssj.custom.view.ShowHoriontalView.onClickLintener;
import com.yssj.custom.view.SizeView;
import com.yssj.custom.view.SizeView2;
import com.yssj.data.YDBHelper;
import com.yssj.entity.GoodsEntity;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ShareShop;
import com.yssj.entity.Shop;
import com.yssj.entity.ShopAttr;
import com.yssj.entity.ShopCart;
import com.yssj.entity.ShopComment;
import com.yssj.entity.ShopOption;
import com.yssj.entity.StockType;
import com.yssj.entity.Store;
import com.yssj.entity.UserInfo;
import com.yssj.entity.VipDikouData;
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
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.ShopCartNewNewActivity;
import com.yssj.ui.activity.ShopImageActivity;
import com.yssj.ui.activity.classfication.ManufactureActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.main.SignGroupShopActivity;
import com.yssj.ui.activity.view.CircleProgressView;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.LingYUANGOUTishiDialog;
import com.yssj.ui.dialog.NewSignCommonDiaolg;
import com.yssj.ui.dialog.PublicToastDialog;
import com.yssj.ui.dialog.XunBaoScollDialog;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.ui.receiver.TaskReceiver;
import com.yssj.utils.ComputeUtil;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.DialogUtils;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


/***
 * 新特卖商品详情展示
 *
 * @author Administrator
 *
 */
public class MealShopDetailsActivity extends BasicActivity
        implements OnClickListener, onClickLintener, OnCheckedLintener, ShopDetailsGetShare, LingYUANGOUTishiDialog.ShopDetialClickCallBack {
    private boolean isdown = false;

    @Override
    public void goumaiClick() {
        queryShopQueryAttr();
    }

    private int num;
    private Double f_xiang_mongey;

    private TextView shouTVprice;//实际售价

//    public static boolean isNewUser = false; //是否是新用户


    public static List<ShopAttr> mealAttrList;


    public static boolean isNewMeal;//是否新特卖商品

    private boolean first_come = true; // 防止来回滑动计算次数
    public static PublicToastDialog shareWaitDialog = null;
    private Timer timer;
    private Timer timer_seven;
    private Timer timer_meal;
    private boolean first_meal = false;

    private long recLen;
    private long recLen_seven;
    private long recLen_meal;

    //	private TimerTask task;
    private TimerTask task_seven;
    private TimerTask task_meal;

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
    private ImageView img_cart_new;

    private int width, height, heights;
    private Shop shop;
    private TextView tv_shop_car, tv_buy, sign_buy;
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
    // private ImageLoadingListener animateFirstListener = new
    // AnimateFirstDisplayListener();

    // private SlidingMenu sm;

    private LinearLayout lin_add_like;
    private RelativeLayout img_cart, img_cart2;
    private ImageView img_cart_old;

    private ImageView lin_contact;
    private RelativeLayout lin_contact_old;

    private SAsyncTask<String, Void, HashMap<String, Object>> aa;
    private SAsyncTask<Void, Void, HashMap<String, Object>> bb;
    private SAsyncTask<Void, Void, HashMap<String, Object>> cc;

    private SAsyncTask<String, Void, ShareShop> pp;

    // private DisplayImageOptions options;

    private LinkedList<HashMap<String, Object>> dataList;

    private String[] images;// 普通商品图片

    private String[] imageTag1, imageTag2, imageTag3;// 套餐图片

    private List<HashMap<String, String>> listTitle;

    private int check = 0;
    private RelativeLayout rlBottom;
    private LinearLayout rlTop;

    private MyPopupwindow myPopupwindow;

    // private static int num; // 说明框出现次数

    private TextView tv_cart_count, tv_cart_count2;
    private TextView tv_time_count_down, tv_time_count_down2;
    private TextView tv_time_count_down_meal;

    private RelativeLayout rl_retain;

    private Context context;

    private StickyListHeadersListView mListView;//整个详情

    // protected LeftFragment mFrag;

    private MyAdapter adapter;

    private TaskReceiver oneBuyReceiver;


    private View headerView; //商品详情上部
    private String titleId;// 筛选类别的id
    public static MealShopDetailsActivity instance;
    private static boolean isShow;
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

    @Override
    protected void onPause() {
        super.onPause();
        TongJiUtils.TongJi(context, 106 + "");
        LogYiFu.e("TongJiNew", 106 + "");
        HomeWatcherReceiver.unregisterHomeKeyReceiver(context);
        titleCheck = -1;
        // SharedPreferencesUtil.saveStringData(context, Pref.TONGJI_TYPE,
        // "-1");
        // MobclickAgent.onPageEnd("ShopDetailsActivity");
        // MobclickAgent.onPause(this);
        if (newHandler != null) {
            newHandler.removeCallbacks(r);
        }
        if (shareHandler != null) {
            shareHandler.removeCallbacks(shareRun);
        }
        // YJApplication.getLoader().stop();
        isPause = 1;
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        LogYiFu.e("queryBalanceNum", arg0 + " " + arg1 + " " + arg2);
        if (arg1 == RESULT_OK) {

            // queryShopDetails();
            // queryShopMeal();
            ShopCartDao dao = new ShopCartDao(context);
            // int count = dao.queryCartCount(context);
            int count = 0;
            if (isMeal) {
                count = dao.queryCartSpecialCount(context);
            } else {
                count = dao.queryCartCommonCount(context);
            }
            if (arg0 == 1080) {

                if (!isMeal && !"SignShopDetail".equals(signShopDetail)) {
                    // int count = arg2.getIntExtra("count",
                    // shop.getCart_count());
                    // shop.setCart_count(count);
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
                Intent intent = new Intent(MealShopDetailsActivity.this, SubmitOrderActivity.class);
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
                if (isMeal) {
                } else if ("SignShopDetail".equals(signShopDetail)) {
                    // queryShopSign();
                } else {
                    LogYiFu.e("刷新", "登录回来");
                    //先查询新老用户
                    queryShopDetails(true);

                }
            } else if (arg0 == 15502) {
                if (YJApplication.instance.isLoginSucess()) {
                    if (isMeal || "SignShopDetail".equals(signShopDetail)) {
                        getPshareShop();
                    } else {
                        share(shop.getShop_code(), shop);
                    }
                }
            }
        }


    }


    private List<Shop> list;// 套餐内商品集合

    private class MyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        // private int i;

        @Override
        public int getCount() {
            int count = 0;
            if (check == 0) {
                if (isMeal || "SignShopDetail".equals(signShopDetail)) {

                    count = count + imageTag1.length + imageTag2.length + imageTag3.length;

                } else {
                    count += images.length;
                }
                if (dataList != null) {
                    count += dataList.size() % 2 == 0 ? dataList.size() / 2 : dataList.size() / 2 + 1;
                }
            } else if (check == 1) {
                if (isMeal || "SignShopDetail".equals(signShopDetail)) {
                    count += list.size() + 1;
                } else {
                    count += 2;
                }
            } else {

                if (isMeal || isMembers || "SignShopDetail".equals(signShopDetail)) {// 套餐取消评论区
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
                v = LayoutInflater.from(MealShopDetailsActivity.this).inflate(R.layout.new_shop_item, arg2, false);
                vh = new ItemViewHolder();
                v.findViewById(R.id.lln).setBackgroundColor(Color.WHITE);
                vh.imageGroup = v.findViewById(R.id.image_group);
                vh.sView2 = (SizeView2) v.findViewById(R.id.size_view2);
                vh.shopItem = v.findViewById(R.id.item_position);
                vh.image = (ImageView) v.findViewById(R.id.image_position);


                vh.image.getLayoutParams().height = width * 9 / 6;
                vh.left = (ItemView) v.findViewById(R.id.left);

//                vh.left.setHeight(width / 2 * 9 / 6);
                vh.right = (ItemView) v.findViewById(R.id.right);

//                vh.right.setHeight(width / 2 * 9 / 6);


                vh.sView = (SizeView) v.findViewById(R.id.size_view);
                vh.eView = v.findViewById(R.id.sevalauate_view);
                vh.eView.setBackgroundColor(Color.WHITE);
                vh.lin_nodata = (LinearLayout) v.findViewById(R.id.lin_nodata);

                vh.pb_color_count = (CircleProgressView) v.findViewById(R.id.pb_color_count);
                vh.pb_type_count = (CircleProgressView) v.findViewById(R.id.pb_type_count);
                vh.pb_work_count = (CircleProgressView) v.findViewById(R.id.pb_work_count);
                vh.pb_cost_count = (CircleProgressView) v.findViewById(R.id.pb_cost_count);

                // vh.tv_color_count = (TextView)
                // v.findViewById(R.id.tv_color_count);
                // vh.tv_type_count = (TextView)
                // v.findViewById(R.id.tv_type_count);
                // vh.tv_work_count = (TextView)
                // v.findViewById(R.id.tv_work_count);
                // vh.tv_cost_count = (TextView)
                // v.findViewById(R.id.tv_cost_count);
                vh.progressContain = (LinearLayout) v.findViewById(R.id.ll_container_progress);
                vh.progressContain.getLayoutParams().height = (width - DP2SPUtil.dp2px(MealShopDetailsActivity.this, 100))
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
                vh.bai.getLayoutParams().height = MealShopDetailsActivity.this.height / 3;

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

            if (check == 0) {// 详情

                vh.mealRView.setVisibility(View.GONE);
                vh.sView.setVisibility(View.GONE);
                vh.eView.setVisibility(View.GONE);
                vh.bai.setVisibility(View.GONE);
                vh.diver.setVisibility(View.GONE);
                vh.sizeHint.setVisibility(View.GONE);

                if (isMeal || "SignShopDetail".equals(signShopDetail)) {// 如果是套餐...

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
                            // SetImageLoader.initImageLoader(null, vh.image,
                            // list.get(0).getShop_code().substring(1, 4)
                            // + "/" + list.get(0).getShop_code() + "/" +
                            // imageTag1[position], "!450");

                            PicassoUtils.initImage(MealShopDetailsActivity.this, list.get(0).getShop_code().substring(1, 4)
                                            + "/" + list.get(0).getShop_code() + "/" + imageTag1[position] + "!" + mImageRadio,
                                    vh.image);

                        } else {
                            vh.image.setTag(list.get(0).getShop_code().substring(1, 4) + "/"
                                    + list.get(0).getShop_code() + "/" + imageTag1[position] + "!" + mImageRadio);
                            PicassoUtils.initImage(MealShopDetailsActivity.this, list.get(0).getShop_code().substring(1, 4)
                                            + "/" + list.get(0).getShop_code() + "/" + imageTag1[position] + "!" + mImageRadio,
                                    vh.image);
                        }

                        final int x = position;
                        vh.image.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MealShopDetailsActivity.this, ShopImageActivity.class);
                                intent.putExtra("url", list.get(0).getShop_code().substring(1, 4) + "/"
                                        + list.get(0).getShop_code() + "/" + imageTag1[x]);
                                intent.putExtra("code", code);
                                intent.putExtra("mealMap", mealMap);
                                intent.putExtra("isMeal", isMeal);
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
                            // SetImageLoader.initImageLoader(null,
                            // vh.image, list.get(1).getShop_code().substring(1,
                            // 4) + "/"
                            // + list.get(1).getShop_code() + "/" +
                            // imageTag2[position - imageTag1.length],
                            // "!450");
                            //

                            PicassoUtils.initImage(MealShopDetailsActivity.this,
                                    list.get(1).getShop_code().substring(1, 4) + "/" + list.get(1).getShop_code() + "/"
                                            + imageTag2[position - imageTag1.length] + "!" + mImageRadio,
                                    vh.image);

                        } else {
                            vh.image.setTag(
                                    list.get(1).getShop_code().substring(1, 4) + "/" + list.get(1).getShop_code() + "/"
                                            + imageTag2[position - imageTag1.length] + "!" + mImageRadio);
                            // SetImageLoader.initImageLoader(null,
                            // vh.image, list.get(1).getShop_code().substring(1,
                            // 4) + "/"
                            // + list.get(1).getShop_code() + "/" +
                            // imageTag2[position - imageTag1.length],
                            // "!382");

                            PicassoUtils.initImage(MealShopDetailsActivity.this,
                                    list.get(1).getShop_code().substring(1, 4) + "/" + list.get(1).getShop_code() + "/"
                                            + imageTag2[position - imageTag1.length] + "!" + mImageRadio,
                                    vh.image);

                        }

                        final int x = position;
                        vh.image.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MealShopDetailsActivity.this, ShopImageActivity.class);
                                intent.putExtra("url", list.get(1).getShop_code().substring(1, 4) + "/"
                                        + list.get(1).getShop_code() + "/" + imageTag2[x - imageTag1.length]);
                                intent.putExtra("code", code);
                                intent.putExtra("mealMap", mealMap);
                                intent.putExtra("isMeal", isMeal);
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
                            // SetImageLoader.initImageLoader(null, vh.image,
                            // list.get(2).getShop_code().substring(1, 4) + "/"
                            // + list.get(2).getShop_code() + "/"
                            // + imageTag3[position - imageTag1.length -
                            // imageTag2.length],
                            // "!450");
                            //
                            PicassoUtils.initImage(MealShopDetailsActivity.this,
                                    list.get(2).getShop_code().substring(1, 4) + "/" + list.get(0).getShop_code() + "/"
                                            + imageTag3[position - imageTag1.length - imageTag2.length] + "!"
                                            + mImageRadio,
                                    vh.image);

                        } else {
                            vh.image.setTag(list.get(2).getShop_code().substring(1, 4) + "/"
                                    + list.get(2).getShop_code() + "/"
                                    + imageTag3[position - imageTag1.length - imageTag2.length] + "!" + mImageRadio);
                            // SetImageLoader.initImageLoader(null, vh.image,
                            // list.get(2).getShop_code().substring(1, 4) + "/"
                            // + list.get(0).getShop_code() + "/"
                            // + imageTag3[position - imageTag1.length -
                            // imageTag2.length],
                            // "!382");
                            //
                            PicassoUtils.initImage(MealShopDetailsActivity.this,
                                    list.get(2).getShop_code().substring(1, 4) + "/" + list.get(0).getShop_code() + "/"
                                            + imageTag3[position - imageTag1.length - imageTag2.length] + "!"
                                            + mImageRadio,
                                    vh.image);

                        }

                        final int x = position;
                        vh.image.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MealShopDetailsActivity.this, ShopImageActivity.class);
                                intent.putExtra("url",
                                        list.get(2).getShop_code().substring(1, 4) + "/" + list.get(2).getShop_code()
                                                + "/" + imageTag3[x - imageTag1.length - imageTag2.length]);
                                intent.putExtra("code", code);
                                intent.putExtra("mealMap", mealMap);
                                intent.putExtra("isMeal", isMeal);
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
                        vh.sView2.setContent(shop, isMeal, position);
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
                        // SetImageLoader.initImageLoader(null, vh.image,
                        // shop.getShop_code().substring(1, 4) + "/"
                        // + shop.getShop_code() + "/" + images[position],
                        // "!450");


//                        PicassoUtils.initImage(ShopDetailsActivity.this, shop.getShop_code().substring(1, 4) + "/"
//                                + shop.getShop_code() + "/" + images[position] + "!" + mImageRadio, vh.image);

                        //不再压缩
                        PicassoUtils.initImage(MealShopDetailsActivity.this, shop.getShop_code().substring(1, 4) + "/"
                                + shop.getShop_code() + "/" + images[position], vh.image);

                        final int x = position;
                        vh.image.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MealShopDetailsActivity.this, ShopImageActivity.class);
                                intent.putExtra("url", shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code()
                                        + "/" + images[x]);
                                intent.putExtra("shop", shop);
                                intent.putExtra("isMembers", isMembers);
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
                                            MealShopDetailsActivity.this,
                                            "forcelookNowTime" + YCache.getCacheUser(context).getUser_id(), "");
                                    forcelookNum = Integer
                                            .valueOf(
                                                    SharedPreferencesUtil
                                                            .getStringData(MealShopDetailsActivity.this,
                                                                    SignListAdapter.signIndex + "forcelookNum"
                                                                            + YCache.getCacheUser(context).getUser_id(),
                                                                    "0"));
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
                                        SharedPreferencesUtil.saveStringData(MealShopDetailsActivity.this,
                                                "forcelookNowTime" + YCache.getCacheUser(context).getUser_id(),
                                                df.format(new Date()));
                                        SharedPreferencesUtil
                                                .saveStringData(MealShopDetailsActivity.this,
                                                        SignListAdapter.signIndex + "forcelookNum"
                                                                + YCache.getCacheUser(context).getUser_id(),
                                                        "" + forcelookNum);
                                        if (forcelookNum < Integer.parseInt(singvalue)) {
                                            ToastUtil.showLongText(MealShopDetailsActivity.this,
                                                    "再浏览" + (Integer.parseInt(singvalue) - forcelookNum) + "件可完成任务喔~");
                                        } else if (forcelookNum >= Integer.parseInt(singvalue)) {
                                            sign();
                                        }
                                    }

                                } else if (isforcelookMatch) {// 搭配购商品
                                    String nowTimeForcelookMatch = SharedPreferencesUtil.getStringData(
                                            MealShopDetailsActivity.this,
                                            "forcelookMatchNowTime" + YCache.getCacheUser(context).getUser_id(), "");
                                    forcelookMatchNum = Integer
                                            .valueOf(
                                                    SharedPreferencesUtil
                                                            .getStringData(MealShopDetailsActivity.this,
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
                                        SharedPreferencesUtil.saveStringData(MealShopDetailsActivity.this,
                                                "forcelookMatchNowTime" + YCache.getCacheUser(context).getUser_id(),
                                                df.format(new Date()));
                                        SharedPreferencesUtil.saveStringData(MealShopDetailsActivity.this,
                                                SignListAdapter.signIndex + "forcelookMatchNum"
                                                        + YCache.getCacheUser(context).getUser_id(),
                                                "" + forcelookMatchNum);
                                        if (forcelookMatchNum < Integer.parseInt(singvalue)) {
                                            ToastUtil.showLongText(MealShopDetailsActivity.this, "再浏览"
                                                    + (Integer.parseInt(singvalue) - forcelookMatchNum) + "件可完成任务喔~");
                                        } else if (forcelookMatchNum >= Integer.parseInt(singvalue)) {
                                            sign();
                                        }
                                    }
                                } else if (isSignActiveShopScan) {// 活动商品浏览
                                    String nowTimeSignActiveShop = SharedPreferencesUtil.getStringData(
                                            MealShopDetailsActivity.this,
                                            "signActiveShopNowTime" + YCache.getCacheUser(context).getUser_id(), "");
                                    signActiveShopNum = Integer
                                            .valueOf(
                                                    SharedPreferencesUtil
                                                            .getStringData(MealShopDetailsActivity.this,
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
                                        SharedPreferencesUtil.saveStringData(MealShopDetailsActivity.this,
                                                "signActiveShopNowTime" + YCache.getCacheUser(context).getUser_id(),
                                                df.format(new Date()));
                                        SharedPreferencesUtil.saveStringData(MealShopDetailsActivity.this,
                                                SignListAdapter.signIndex + "signActiveShopNum"
                                                        + YCache.getCacheUser(context).getUser_id(),
                                                "" + signActiveShopNum);
                                        if (signActiveShopNum < Integer.parseInt(singvalue)) {
                                            ToastUtil.showLongText(MealShopDetailsActivity.this, "再浏览"
                                                    + (Integer.parseInt(singvalue) - signActiveShopNum) + "件可完成任务喔~");
                                        } else if (signActiveShopNum >= Integer.parseInt(singvalue)) {
                                            sign();
                                        }
                                    }
                                } else if (isForceLookLimit) {
                                    String nowTimeForcelookLimit = SharedPreferencesUtil.getStringData(
                                            MealShopDetailsActivity.this,
                                            "nowTimeForcelookLimit" + YCache.getCacheUser(context).getUser_id(), "");
                                    forcelookLimitNum = Integer
                                            .parseInt(
                                                    SharedPreferencesUtil
                                                            .getStringData(MealShopDetailsActivity.this,
                                                                    SignListAdapter.signIndex + Pref.ISFORCELOOKLIMITNUM
                                                                            + YCache.getCacheUser(context).getUser_id(),
                                                                    "0"));
                                    if (!df.format(new Date()).equals(nowTimeForcelookLimit)) {
                                        forcelookLimitNum = 0;// 不是同一天点击分享任务
                                        // 或者不是同一个用户 就
                                        // 或者取出的数据大于浏览次数
                                        // 重新开始计数分享的次数
                                    }

                                    SharedPreferencesUtil.saveStringData(MealShopDetailsActivity.this,
                                            "nowTimeForcelookLimit" + YCache.getCacheUser(context).getUser_id(),
                                            df.format(new Date()));
                                    if (forcelookLimitNum / Integer.parseInt(singvalue) + 1 > SignListAdapter.doNum
                                            || SignListAdapter.isSignComplete) {
                                        //浏览 奖励额度 达到上限
                                        NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(MealShopDetailsActivity.this,
                                                R.style.DialogQuheijiao2, Pref.LIULAN_SIGN_UPPER_LIMIT);
                                        dialog.show();
                                        forcelookLimitNum++;
                                        SharedPreferencesUtil
                                                .saveStringData(MealShopDetailsActivity.this,
                                                        SignListAdapter.signIndex + Pref.ISFORCELOOKLIMITNUM
                                                                + YCache.getCacheUser(context).getUser_id(),
                                                        "" + forcelookLimitNum);

                                    } else {

                                        if (forcelookLimitNum % Integer.parseInt(singvalue) + 1 < Integer.parseInt(singvalue)) {
                                            ToastUtil.showMyToast(MealShopDetailsActivity.this,
                                                    "再浏览" + (Integer.parseInt(singvalue) - (forcelookLimitNum % Integer.parseInt(singvalue) + 1)) + "次即可赢得"
                                                            + SignListAdapter.jiangliValue
                                                            + "元提现额度,继续努力~", 3000);
                                            forcelookLimitNum++;
                                            SharedPreferencesUtil
                                                    .saveStringData(MealShopDetailsActivity.this,
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

                vh.left.iniView(dataList.get(position));
                vh.left.setOnClickListener(new MyOnClick(position));
                if (dataList.size() > position + 1) {
                    vh.right.setVisibility(View.VISIBLE);
                    vh.right.iniView(dataList.get(position + 1));
                    vh.right.setOnClickListener(new MyOnClick(position + 1));
                } else {
                    vh.right.setVisibility(View.INVISIBLE);
                }
            } else if (check == 1) {// 尺寸
                vh.mealRView.setVisibility(View.GONE);
                vh.imageTag.setVisibility(View.GONE);
                vh.diver.setVisibility(View.GONE);
                if (isMeal || "SignShopDetail".equals(signShopDetail)) {

                    if (position < list.size()) {
                        vh.bai.setVisibility(View.GONE);
                        vh.sView.setVisibility(View.VISIBLE);
                        vh.sizeHint.setVisibility(View.GONE);
                        vh.imageGroup.setVisibility(View.GONE);
                        vh.shopItem.setVisibility(View.GONE);
                        vh.eView.setVisibility(View.GONE);
                        vh.sView.setContent(list.get(position), isMeal, position);
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
                        vh.sView.setContent(shop, isMeal, position);
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
                            // SetImageLoader.initImageLoader(null, vh.sizeHint,
                            // "system/shop_details.png", "!450");

                            PicassoUtils.initImage(MealShopDetailsActivity.this, "system/shop_details.png" + "!450",
                                    vh.sizeHint);

                        } else {
                            PicassoUtils.initImage(MealShopDetailsActivity.this, "system/shop_details.png" + "!382",
                                    vh.sizeHint);
                            // SetImageLoader.initImageLoader(null, vh.sizeHint,
                            // "system/shop_details.png", "!382");
                        }

                        return v;
                    }

                }

            } else {// 评论

                if (isMeal || isMembers || "SignShopDetail".equals(signShopDetail)) {
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

                        if (isMeal || "SignShopDetail".equals(signShopDetail)) {
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

                                // MyLogYiFu.e("评价总数",eva_count+"");

                                // vh.tv_color_count
                                // .setText(eva_count == 0 ? "100%" : (int)
                                // (color_count * 100 / eva_count) + "%");
                                // // System.out.println("***************dan1" +
                                // // color_count);
                                // // System.out.println("***************zong1"
                                // +
                                // // eva_count);
                                // vh.tv_type_count
                                // .setText(eva_count == 0 ? "100%" : (int)
                                // (type_count * 100 / eva_count) + "%");
                                // vh.tv_work_count
                                // .setText(eva_count == 0 ? "100%" : (int)
                                // (work_count * 100 / eva_count) + "%");
                                // vh.tv_cost_count
                                // .setText(eva_count == 0 ? "100%" : (int)
                                // (cost_count * 100 / eva_count) + "%");

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

                                // vh.tv_color_count.setText(shop.getEva_count()
                                // == 0 ? "100%"
                                // : (int) (shop.getColor_count() /
                                // shop.getEva_count() * 100) + "%");
                                // // System.out.println("***************dan" +
                                // // shop.getColor_count());
                                // // System.out.println("***************zong" +
                                // // shop.getEva_count() * 100);
                                // vh.tv_type_count.setText(shop.getEva_count()
                                // == 0 ? "100%"
                                // : (int) (shop.getType_count() /
                                // shop.getEva_count() * 100) + "%");
                                // vh.tv_work_count.setText(shop.getEva_count()
                                // == 0 ? "100%"
                                // : (int) (shop.getWork_count() /
                                // shop.getEva_count() * 100) + "%");
                                // vh.tv_cost_count.setText(shop.getEva_count()
                                // == 0 ? "100%"
                                // : (int) (shop.getCost_count() /
                                // shop.getEva_count() * 100) + "%");
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
                    // if(position==3&&listShopComments!=null&&listShopComments.size()!=position){
                    // vh.diver.setVisibility(View.GONE);
                    // }else{
                    vh.diver.setVisibility(View.VISIBLE);
                    // }
                    vh.img_user_header.setTag(shopComment.getUser_url());
                    // SetImageLoader.initImageLoader(ShopDetailsActivity.this,
                    // vh.img_user_header,
                    // shopComment.getUser_url(), "");

                    PicassoUtils.initImage(MealShopDetailsActivity.this, shopComment.getUser_url(), vh.img_user_header);
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
                                (width - DP2SPUtil.dp2px(MealShopDetailsActivity.this, 110)) / 3,
                                (width - DP2SPUtil.dp2px(MealShopDetailsActivity.this, 114)) / 3);
                        params.setMargins(0, 0, DP2SPUtil.dp2px(MealShopDetailsActivity.this, 8), 0);
                        final String[] picList = pic.split(",");

                        // final ImageView[] img = new
                        // ImageView[picList.length];
                        for (int j = 0; j < picList.length; j++) {
                            ImageView img = new ImageView(MealShopDetailsActivity.this);
                            img.setLayoutParams(params);
                            img.setScaleType(ScaleType.CENTER_CROP);
                            // SetImageLoader.initImageLoader(null, img,
                            // picList[j], "!180");
                            PicassoUtils.initImage(MealShopDetailsActivity.this, picList[j] + "!180", img);
                            img.setOnClickListener(new ImageOnClickLintener(j, picList));
                            vh.img_container.addView(img);
                        }

                    }

                    if (null != shopComment.getSuppComment()) {
                        vh.tv_one_reply.setVisibility(View.VISIBLE);
                        vh.tv_one_reply.setText(Html.fromHtml(MealShopDetailsActivity.this.getString(R.string.tv_supp_reply,
                                shopComment.getSuppComment().get(0).getSupp_content())));
                    } else {
                        vh.tv_one_reply.setVisibility(View.GONE);
                    }

                    if (null != shopComment.getComment()) {

                        vh.lin_second.setVisibility(View.VISIBLE);
                        vh.tv_second_judge.setVisibility(View.VISIBLE);
                        vh.tv_second_judge.setText(Html.fromHtml(MealShopDetailsActivity.this
                                .getString(R.string.tv_add_judge, shopComment.getComment().get(0).getContent())));
                        if (null != shopComment.getSuppEndComment() && shopComment.getSuppEndComment().size() > 0) {
                            vh.tv_second_reply.setVisibility(View.VISIBLE);
                            vh.tv_second_reply
                                    .setText(Html.fromHtml(MealShopDetailsActivity.this.getString(R.string.tv_supp_reply,
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
                                    (width - DP2SPUtil.dp2px(MealShopDetailsActivity.this, 110)) / 3,
                                    (width - DP2SPUtil.dp2px(MealShopDetailsActivity.this, 114)) / 3);
                            params.setMargins(0, 0, DP2SPUtil.dp2px(MealShopDetailsActivity.this, 8), 0);
                            for (int j = 0; j < spic.length; j++) {
                                ImageView img = new ImageView(MealShopDetailsActivity.this);
                                img.setLayoutParams(params);
                                img.setScaleType(ScaleType.CENTER_CROP);
                                // SetImageLoader.initImageLoader(null, img,
                                // spic[j], "!180");
                                PicassoUtils.initImage(MealShopDetailsActivity.this, spic[j] + "!180", img);
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

                    return ComModel2.getSignIn(MealShopDetailsActivity.this, false, false, SignListAdapter.signIndex,
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
                            SignCompleteDialogUtil.firstClickInGoToZP(MealShopDetailsActivity.this);
                            return;
                        }

                        if (SignListAdapter.doNum > 1) {// 需要奖励分多次发放
                            if (isforcelook) {// 强制浏览普通商品
                                SharedPreferencesUtil.saveStringData(MealShopDetailsActivity.this,
                                        "forcelookNowTime" + YCache.getCacheUser(context).getUser_id(),
                                        df.format(new Date()));
                                SharedPreferencesUtil.saveStringData(MealShopDetailsActivity.this, SignListAdapter.signIndex
                                                + "forcelookNum" + YCache.getCacheUser(context).getUser_id(),
                                        "" + forcelookNum);

                                if (forcelookNum < Integer.parseInt(singvalue)) {// 小于要求的浏览次数
                                    ToastUtil.showLongText(MealShopDetailsActivity.this,
                                            "浏览完成，奖励"
                                                    // +new
                                                    // java.text.DecimalFormat("#0.00").format(Double.valueOf(SignListAdapter.jiangliValue)/Integer.parseInt(singvalue))
                                                    + SignListAdapter.jiangliValue + ss + ",还有"
                                                    + (Integer.parseInt(singvalue) - forcelookNum) + "次浏览机会喔~");
                                } else if (forcelookNum >= Integer.parseInt(singvalue)) {
                                    NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(MealShopDetailsActivity.this,
                                            R.style.DialogQuheijiao2, "liulan_sign_finish",
                                            new DecimalFormat("0.##").format(
                                                    Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum)
                                                    + ss);
                                    dialog.show();

                                }
                            } else if (isforcelookMatch) {// 强制浏览搭配商品
                                SharedPreferencesUtil.saveStringData(MealShopDetailsActivity.this,
                                        "forcelookMatchNowTime" + YCache.getCacheUser(context).getUser_id(),
                                        df.format(new Date()));
                                SharedPreferencesUtil
                                        .saveStringData(MealShopDetailsActivity.this,
                                                SignListAdapter.signIndex + "forcelookMatchNum"
                                                        + YCache.getCacheUser(context).getUser_id(),
                                                "" + forcelookMatchNum);

                                if (forcelookMatchNum < Integer.parseInt(singvalue)) {// 小于要求的分享次数
                                    ToastUtil.showLongText(MealShopDetailsActivity.this,
                                            "浏览完成，奖励"
                                                    // +new
                                                    // java.text.DecimalFormat("#0.00").format(Double.valueOf(SignListAdapter.jiangliValue)/Integer.parseInt(singvalue))
                                                    + SignListAdapter.jiangliValue + ss + ",还有"
                                                    + (Integer.parseInt(singvalue) - forcelookMatchNum) + "次浏览机会喔~");
                                } else if (forcelookMatchNum >= Integer.parseInt(singvalue)) {
                                    NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(MealShopDetailsActivity.this,
                                            R.style.DialogQuheijiao2, "liulan_sign_finish",
                                            new DecimalFormat("0.##").format(
                                                    Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum)
                                                    + ss);
                                    dialog.show();

                                }
                            } else if (isSignActiveShop) {// 强制浏览活动商品

                                SharedPreferencesUtil.saveStringData(MealShopDetailsActivity.this,
                                        "signActiveShopNowTime" + YCache.getCacheUser(context).getUser_id(),
                                        df.format(new Date()));
                                SharedPreferencesUtil
                                        .saveStringData(MealShopDetailsActivity.this,
                                                SignListAdapter.signIndex + "signActiveShopNum"
                                                        + YCache.getCacheUser(context).getUser_id(),
                                                "" + signActiveShopNum);

                                if (signActiveShopNum < Integer.parseInt(singvalue)) {// 小于要求的分享次数
                                    ToastUtil.showLongText(MealShopDetailsActivity.this,
                                            "浏览完成，奖励"
                                                    // +new
                                                    // java.text.DecimalFormat("#0.00").format(Double.valueOf(SignListAdapter.jiangliValue)/Integer.parseInt(singvalue))
                                                    + SignListAdapter.jiangliValue + ss + ",还有"
                                                    + (Integer.parseInt(singvalue) - signActiveShopNum) + "次浏览机会喔~");
                                } else if (signActiveShopNum >= Integer.parseInt(singvalue)) {
                                    NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(MealShopDetailsActivity.this,
                                            R.style.DialogQuheijiao2, "liulan_sign_finish",
                                            new DecimalFormat("0.##").format(
                                                    Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum)
                                                    + ss);
                                    dialog.show();

                                }
                            }

                        } else {
                            // 其他奖励 一次性奖励
                            NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(MealShopDetailsActivity.this,
                                    R.style.DialogQuheijiao2, "liulan_sign_finish", SignListAdapter.jiangliValue + ss);
                            dialog.show();

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

                    return ComModel2.getSignIn(MealShopDetailsActivity.this, false, false, SignListAdapter.signIndex,
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
                                SignCompleteDialogUtil.firstClickInGoToZP(MealShopDetailsActivity.this);
                                return;
                            }

                            SharedPreferencesUtil.saveStringData(MealShopDetailsActivity.this,
                                    "nowTimeForcelookLimit" + YCache.getCacheUser(context).getUser_id(),
                                    df.format(new Date()));

//							if (forcelookLimitNum/Integer.parseInt(singvalue) +1 <= SignListAdapter.doNum) {// 小于要求的浏览次数


                            String ts = SignListAdapter.jiangliValue + "元提现现金已经发放，到账时间为3-5个工作日,请耐心等待。再浏览"
                                    + (Integer.parseInt(singvalue) + "次可再赢得" + SignListAdapter.jiangliValue + "元提现现金"
                                    + ",继续努力!");

                            ToastUtil.showMyToast(MealShopDetailsActivity.this, ts, 6000);


//
//                            ToastUtil.showMyToast(ShopDetailsActivity.this,
//                                    SignListAdapter.jiangliValue + ss + "已经存入您的余额，再浏览"
//                                            + (Integer.parseInt(singvalue) + "次可再赢得" + SignListAdapter.jiangliValue + ss
//                                            + ",继续努力~"), 6000);


                            forcelookLimitNum++;
                            SharedPreferencesUtil
                                    .saveStringData(MealShopDetailsActivity.this,
                                            SignListAdapter.signIndex + Pref.ISFORCELOOKLIMITNUM
                                                    + YCache.getCacheUser(context).getUser_id(),
                                            "" + forcelookLimitNum);
//							} else if (forcelookLimitNum/Integer.parseInt(singvalue) +1 > SignListAdapter.doNum) {
//								NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(ShopDetailsActivity.this,
//										R.style.DialogQuheijiao2, Pref.LIULAN_SIGN_UPPER_LIMIT);
//								dialog.show();
//								forcelookLimitNum++;
//								SharedPreferencesUtil
//										.saveStringData(ShopDetailsActivity.this,
//												SignListAdapter.signIndex + Pref.ISFORCELOOKLIMITNUM
//														+ YCache.getCacheUser(context).getUser_id(),
//												"" + forcelookLimitNum);
//
//							}
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
                view = LayoutInflater.from(MealShopDetailsActivity.this).inflate(R.layout.meal_header_item, parent, false);
                vh.topOne = (MealShopTopClickView) view.findViewById(R.id.top_one);
                if (isMeal || isMembers || "SignShopDetail".equals(signShopDetail)) {
                    vh.topOne.setText();
                } else {
                    vh.topOne.setText2(setEva_count_z);
                }

                vh.topTwo = (ShowHoriontalView) view.findViewById(R.id.top_two);
                vh.topOne.setCheckLintener(MealShopDetailsActivity.this);
                vh.topTwo.setOnClickLintener(MealShopDetailsActivity.this);
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

            if (isMeal || "SignShopDetail".equals(signShopDetail)) {

                if (position < imageTag1.length + imageTag2.length + imageTag3.length) {
                    vh.topOne.setVisibility(View.VISIBLE);
                    vh.topOne.setIndex(check);
                    vh.title.setText("商品详情");
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
            if (isMeal || "SignShopDetail".equals(signShopDetail)) {
                if (position < imageTag1.length + imageTag2.length + imageTag3.length) {
                    if (isMeal) {

//						if (ll_bottem.getVisibility() == View.GONE && isAnim == false) {
//
//							ll_bottem.clearAnimation();
//							ll_bottem.startAnimation(animationShow);
//							// rl_retain.startAnimation(animationShow);
//						}
                    }

                    if ("SignShopDetail".equals(signShopDetail)) {

//						if (rlBottom.getVisibility() == View.GONE && isAnim == false) {
//
//							rlBottom.clearAnimation();
//							rlBottom.startAnimation(baoyou_animationShow);
//
//						}
                    }
                    return 0;
                } else {
                    if (isMeal) {
//						if (ll_bottem.getVisibility() == View.VISIBLE && isAnim == false) {
//
//							ll_bottem.clearAnimation();
//							ll_bottem.startAnimation(animationGone);
//
//							// rl_retain.startAnimation(animationGone);
//						}
                        return 1;
                    } else {
//						if (rlBottom.getVisibility() == View.VISIBLE && isAnim == false) {
//
//							rlBottom.clearAnimation();
//							rlBottom.startAnimation(baoyou_animationGone);
//
//						}
                        return 1;
                    }
                }

            } else {
                if (position < images.length) {

//					if (rlBottom.getVisibility() == View.GONE && isAnim == false) {
//						rlBottom.clearAnimation();
//						rlBottom.startAnimation(animationShow);
//					}

                    return 0;
                } else {
//					if (rlBottom.getVisibility() == View.VISIBLE && isAnim == false) {
//						rlBottom.clearAnimation();
//						rlBottom.startAnimation(animationGone);
//					}
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

        MealShopTopClickView topOne;

        ShowHoriontalView topTwo;

        TextView title;
    }

//	private Animation animationGone; // 上拉隐藏底部按钮
//	private Animation animationShow;// 下拉显示底部按钮

    private boolean isMembers = false;// 是否是会员商品

    private RelativeLayout rrr;

    private String singvalue;// 强制浏览数量
    private int forcelookNum;// 强制浏览的计数
    private int forcelookMatchNum;// 搭配商品强制浏览的计数
    private int forcelookLimitNum;// 浏览奖励提现额度 强制浏览的计数
    private int signActiveShopNum;// 活动商品强制浏览的计数
    private String mSignGroupsPeopleCount = "10";
    private String mSignGroupsPrice = "9.9";

    //1元购相关
    private RelativeLayout ll_bottom_oneshop;
    private LinearLayout ll_kefu_red; //客服
    private LinearLayout ll_onlyshop_red;//单独购买
    private LinearLayout ll_oneshop_red;//1元购买
    private TextView tv_onlyshop_red;
    private TextView tv_onlyshop_price;
    private TextView tv_onlyshop_bt_text;
    private TextView tv_onlyshop_red_text;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        titleCheck = 0;
        mImageRadio = SharedPreferencesUtil.getStringData(MealShopDetailsActivity.this, Pref.IMAGE_RADIO, "450");
        signShopDetail = getIntent().getStringExtra("SignShopDetail");
        sweet_theme_id = getIntent().getStringExtra("sweet_theme_id");
        mIsGroup = getIntent().getBooleanExtra("mIsGroup", false);
        isNewMeal = getIntent().getBooleanExtra("isNewMeal", false);
        isHot = getIntent().getBooleanExtra("isHot", false);
        r_code = getIntent().getStringExtra("r_code");
        group_click_flag = getIntent().getBooleanExtra("group_click_flag", false);
        AppManager.getAppManager().addDetailsActivity(this);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_shop_details_new_meal);
        isforcelook = getIntent().getBooleanExtra("isforcelook", false);
        isforcelookMatch = getIntent().getBooleanExtra("isforcelookMatch", false);
        isForceLookLimit = getIntent().getBooleanExtra(Pref.ISFORCELOOKLIMIT, false);
        isSignActiveShop = getIntent().getBooleanExtra("isSignActiveShop", false);
        isSignActiveShopScan = getIntent().getBooleanExtra("isSignActiveShopScan", false);
        shareWaitDialog = new PublicToastDialog(this, R.style.DialogStyle1, "");
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
                XunBaoScollDialog dialog = new XunBaoScollDialog(MealShopDetailsActivity.this, xunBaoIv);
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
                XunBaoScollDialog dialog = new XunBaoScollDialog(MealShopDetailsActivity.this, xunBaoIv);
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
        initView();
        if (number_sold != null) {
            if (number_sold.equals("none")) {
                tv_shop_car.setTextColor(getResources().getColor(R.color.white_white));
                tv_shop_car.setBackgroundColor(getResources().getColor(R.color.gray_white));

            } else {
                tv_shop_car.setTextColor(getResources().getColor(R.color.white_white));
                tv_shop_car.setBackgroundColor(getResources().getColor(R.color.zero_shop_choice));
            }
        }

        YDBHelper helper = new YDBHelper(this);
        String sql = "select * from sort_info where p_id = 0 and is_show = 1 order by sequence";
        listTitle = helper.query(sql);
        // int a = 0;
        // int b = 0;
        // for (int i = 0; i < listTitle.size(); i++) {
        // String sort_name = listTitle.get(i).get("sort_name");
        // if ("上衣".equals(sort_name)) {
        // a = i;
        // } else if ("外套".equals(sort_name)) {
        // b = i;
        // }
        // }
        // try {
        // HashMap<String, String> listTitle_temp1 = (HashMap<String, String>)
        // listTitle.get(a);
        // HashMap<String, String> listTitle_temp2 = (HashMap<String, String>)
        // listTitle.get(b);
        // if (b > a) {// 上衣在外套前面时就调换
        // listTitle.remove(a);
        // listTitle.add(a, listTitle_temp2);
        // listTitle.remove(b);
        // listTitle.add(b, listTitle_temp1);
        // }
        // } catch (Exception e) {
        // // TODO: handle exception
        // }
        // initImageLoader();
        code = getIntent().getStringExtra("code");
        isMeal = getIntent().getBooleanExtra("isMeal", false);
        isMembers = getIntent().getBooleanExtra("isMembers", false);

        signType = getIntent().getIntExtra("signType", 0);
        signValue = getIntent().getStringExtra("valueBao");
        valueDuo = getIntent().getStringExtra("valueDuo");

        img_cart_new = (ImageView) findViewById(R.id.img_cart_new);
        // if(isSignActiveShop || isSignActiveShopScan){
        // headerView =
        // LayoutInflater.from(ShopDetailsActivity.this).inflate(R.layout.shop_header,
        // null);
        // }else{
        // headerView =
        // LayoutInflater.from(ShopDetailsActivity.this).inflate(R.layout.shop_header_nopintuan,
        // null);
        // }
        instance = this;
        if (isMeal) {// 套餐
            LinearLayout ll_left_left = (LinearLayout) findViewById(R.id.ll_left_left);
            LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT, 2);
            ll_left_left.setLayoutParams(lp);
            LinearLayout ll_left = (LinearLayout) findViewById(R.id.ll_left);
            LayoutParams lp2 = new LayoutParams(0, DP2SPUtil.dp2px(context, 49), 2);
            ll_left.setLayoutParams(lp2);
            lin_add_like.setVisibility(View.GONE);
            pos = getIntent().getStringExtra("pos");
            findViewById(R.id.divider).setVisibility(View.GONE);
            // rl_heaed_all.setVisibility(View.GONE);
        } else if ("SignShopDetail".equals(signShopDetail)) {

            // queryShopSign();
            tv_shop_car.setVisibility(View.GONE);
            // tv_buy.setText("分享购买");
            lin_add_like.setVisibility(View.GONE);
            // tv_buy.setVisibility(View.GONE);
            sign_buy.setVisibility(View.VISIBLE);
            // rl_heaed_all.setVisibility(View.GONE);
        } else {
            lin_add_like.setVisibility(View.VISIBLE);
            if (isMembers) {
                tv_shop_car.setVisibility(View.GONE);
                // tv_buy.setText("分享购买");
                lin_add_like.setVisibility(View.GONE);
                img_fenx.setVisibility(View.GONE);
                img_cart.setVisibility(View.GONE);
                // rl_heaed_all.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(code)) {
                LogYiFu.e("STARTTIME", System.currentTimeMillis() + "");
                queryShopDetails(false);
//				if(isSignActiveShop&&group_click_flag){
//					//拼团商品不显示悬浮红包
//				}else{
//					//显示悬浮红包
//					queryBalanceNum();
//				}

            } else {
                finish();
            }
        }


        //1元购相关
        rlBottom.setVisibility(View.GONE);


        tv_onlyshop_red = (TextView) findViewById(R.id.tv_onlyshop_red);
        tv_onlyshop_price = (TextView) findViewById(R.id.tv_onlyshop_price);
        tv_onlyshop_bt_text = (TextView) findViewById(R.id.tv_onlyshop_bt_text);
        tv_onlyshop_red_text = (TextView) findViewById(R.id.tv_onlyshop_red_text);

        ll_kefu_red = (LinearLayout) findViewById(R.id.ll_kefu_red);
        ll_onlyshop_red = (LinearLayout) findViewById(R.id.ll_onlyshop_red);
        ll_oneshop_red = (LinearLayout) findViewById(R.id.ll_oneshop_red);

//        if (!GuideActivity.show1yuan) {
//            ll_oneshop_red.setVisibility(View.GONE);
//        }


        ll_kefu_red.setOnClickListener(this);
        ll_onlyshop_red.setOnClickListener(this);
        ll_oneshop_red.setOnClickListener(this);


        shareRun = new Runnable() {

            @Override
            public void run() {
                if (!YJApplication.instance.isLoginSucess()) {
                    return;
                }
                if (isPause == 1) {
                    return;
                }
                if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == MealShopDetailsActivity.this
                        .getSharedPreferences("EveryDayShareAm", Context.MODE_PRIVATE).getInt("day", 0)) {
                    return;
                }
                if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == MealShopDetailsActivity.this
                        .getSharedPreferences("EveryDaySharePm", Context.MODE_PRIVATE).getInt("day", 0)) {
                    return;
                }
                List<String> taskMap = YJApplication.instance.getTaskMap();
                if (taskMap == null) {
                    taskMap = new ArrayList<String>();
                }
                if (taskMap.contains("7") || taskMap.contains("8")) {
                    return;
                }
                if (isShow) {
                    return;
                }

                // 获取当前时间
                // String currentTime = DateFormat.format("HH", new Date())
                // .toString();
                // int hour = Integer.parseInt(currentTime);
                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                // 每日上午分享一次
                if ((hour > 7 || hour == 7) && hour < 14) {
                }

            }

        };

        r = new Runnable() {

            @Override
            public void run() {
            }
        };

        if (isSignActiveShop && (YJApplication.instance.isLoginSucess() == true)) {
            getGroupInitData();
        }

        //1元购分享成功回调
        NewMealOneSharePopupwindow.setBuyShareCompleteListener(new NewMealOneSharePopupwindow.OneBuyShareCompleteListener() {
            @Override
            public void oneBuyShareComplete() {
//                queryShopQueryAttr(0); // 查询普通商品属性
//                queryShopQueryAttrOneBuy();
            }
        });

        oneBuyReceiver = new TaskReceiver(this) {
            @Override
            public void onReceive(Context context, Intent intent) {

                LogYiFu.e("广播", "接收到了广播");

                if (TaskReceiver.onebuysubmitoderend.equals(intent.getAction())) {
                    LogYiFu.e("广播", "接收到了广播222");
                    queryShopDetails(true);


                }


            }
        };

        TaskReceiver.regiserReceiver(this, oneBuyReceiver);


    }

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

    private boolean isMeal = false;// 是否是套餐

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

    // private void initImageLoader() {
    //
    // options = new
    // DisplayImageOptions.Builder().showImageOnLoading(R.drawable.image_default)
    // .showImageForEmptyUri(R.drawable.ic_empty).cacheInMemory(false).bitmapConfig(Bitmap.Config.RGB_565)
    // .imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).considerExifParams(true)
    // // .displayer(new FadeInBitmapDisplayer(35))
    // .build();
    //
    // }

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

    @Override
    protected void onResume() {
        super.onResume();
        TongJiUtils.TongJi(context, 6 + "");
        LogYiFu.e("TongJiNew", 6 + "");
        HomeWatcherReceiver.registerHomeKeyReceiver(context);
        SharedPreferencesUtil.saveStringData(context, Pref.TONGJI_TYPE, "1050");
        if (!isSignActiveShop) {
//			Calendar c = Calendar.getInstance();
//			int day = c.get(Calendar.DAY_OF_MONTH);
//
//			if (!("" + YCache.getCacheToken(ShopDetailsActivity.this) + day+"true").equals(SharedPreferencesUtil.getStringData(ShopDetailsActivity.this, YConstance.Pref.DAY_BUY_IS_SHOW, ""))) {
            tv_buy_now.setText("0元购全返");
            tv_buy_now.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_buy_now.setTextSize(16);
//			}else{
//				boolean isMad= SharedPreferencesUtil.getBooleanData(this, Pref.ISMADMONDAY, false);
//				if(!isMad) {
//					tv_buy_now.setText("立即购买");
//				}else{
//					tv_buy_now.setText("购买即赢千元大奖");
//				}
//
//			}
        }
        if (!"SignShopDetail".equals(signShopDetail)) {
            if (isSignActiveShop) {
//				if (mIsGroup) {// 拼团广场跳过来的
//					mGroupBuy.setVisibility(View.VISIBLE);
//					mLlActivity.setVisibility(View.GONE);
//				} else {// 活动商品列表跳过来的
//					mGroupBuy.setVisibility(View.GONE);
//					mLlActivity.setVisibility(View.VISIBLE);
//				}
                if (group_click_flag) {
                    mActivityBottom.setVisibility(View.VISIBLE);
                    mNomarBottom.setVisibility(View.GONE);
                } else {
                    tv_buy_now.setText("立即购买");
                    mRlAddShopCart.setVisibility(View.GONE);

                }

            } else if (isMeal) {
                redShare.setVisibility(View.GONE);
                moneyShare.setVisibility(View.GONE);
            } else {
                setShareAnim();
                //显示悬浮红包
//					img_balance_lottery.setVisibility(View.GONE);
                queryBalanceNum();
            }
        }
        if (isMeal) {
        }

        ShopCartDao dao = new ShopCartDao(context);
        countCommn = dao.queryCartCommonCount(context);
        countMeal = dao.queryCartSpecialCount(context);

        LogYiFu.e("ShopDetailsActivity_onresume", "OK");
        isPause = 0;
        // ////////////////////////////////////////////////////////////////////////////////////////////////////
        if (isMeal) {
            shareHandler = new Handler();
            shareHandler.postDelayed(shareRun, 30 * 1000);
        }
        if (isMeal || isMembers) {
            return;
        }

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


        ll_bottom_oneshop = (RelativeLayout) findViewById(R.id.ll_bottom_oneshop);


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
        // rlTop = (LinearLayout) findViewById(R.id.ray_top);
        // rlTop.setVisibility(View.VISIBLE);
        // rlTop.setBackgroundColor(Color.WHITE);

        tv_shop_car_fake = (TextView) findViewById(R.id.tv_shop_car_fake);
        tv_shop_car = (TextView) findViewById(R.id.tv_shop_car);
        if (isSignActiveShop) {
            tv_shop_car_fake.setText("立即购买");
            tv_shop_car.setText("立即购买");
        }
        tv_shop_car.setVisibility(View.GONE);
        tv_shop_car.setOnClickListener(this);
        // tv_buy = (TextView) findViewById(R.id.tv_buy);
        // tv_buy.setOnClickListener(this);
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
                    // MobclickAgent.onEvent(context, "toshopcartclick");
                    Intent intent3 = new Intent(MealShopDetailsActivity.this, ShopCartNewNewActivity.class);
                    if (isMeal) {
                        intent3.putExtra("where", "1");
                    } else {
                        intent3.putExtra("where", "0");
                    }

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

//        img_xin = (ImageView) findViewById(R.id.img_xin);// 加心

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

        // rlTop.setBackgroundColor(-1);
        // rlTop.getBackground().setAlpha(0);

        // rl_heaed_all = (RelativeLayout) findViewById(R.id.rl_heaed_all);

//		animationGone = AnimationUtils.loadAnimation(ShopDetailsActivity.this, R.anim.shop_bottom_gone);
//		animationShow = AnimationUtils.loadAnimation(ShopDetailsActivity.this, R.anim.shop_bottom_show);

//		animationGone.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//				isAnim = true;
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				ll_bottem.post(new Runnable() {
//
//					@Override
//					public void run() {
//						ll_bottem.setVisibility(View.GONE);
//					}
//				});
//				isAnim = false;
//			}
//		});
//		animationShow.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//				isAnim = true;
//				ll_bottem.setVisibility(View.VISIBLE);
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				isAnim = false;
//			}
//		});

        baoyou_animationGone = AnimationUtils.loadAnimation(MealShopDetailsActivity.this, R.anim.shop_bottom_gone);
        baoyou_animationShow = AnimationUtils.loadAnimation(MealShopDetailsActivity.this, R.anim.shop_bottom_show);

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

            MealShopDetailsActivity.this.getSharedPreferences("YSSJ_yf", Context.MODE_PRIVATE).edit()
                    .putBoolean("isGoDetail", true).commit();
            if (YJApplication.instance.isLoginSucess()) {
                addScanDataTo((String) posMap.get("shop_code"));
            }
            Intent intent = new Intent(MealShopDetailsActivity.this, MealShopDetailsActivity.class);
            intent.putExtra("code", (String) posMap.get("shop_code"));
            // context.startActivity(intent);
            intent.putExtra("shopCarFragment", "shopCarFragment");

            startActivity(intent);
            finish();

            ((FragmentActivity) MealShopDetailsActivity.this).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

//			overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        }
    }

    /*
     * 把浏览过的数据添加进数据库
	 */
    private void addScanDataTo(final String shop_code) {
        new SAsyncTask<Void, Void, ReturnInfo>(MealShopDetailsActivity.this) {

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
            // if (loginDialog == null) {
            // loginDialog = new ToLoginDialog(ShopDetailsActivity.this);
            // }
            // loginDialog.setRequestCode(235);
            // loginDialog.show();

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


//                if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//                    Intent intent = new Intent(this, KeFuActivity.class);
//                    intent.putExtra("userId", SharedPreferencesUtil.getStringData(context, "kefuNB", "0"));
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("mealMap", mealMap);
//                    if (null == mealMap) {
//                        ToastUtil.showShortText(context, "操作无效");
//                        return;
//                    }
//                    bundle.putDouble("price", Double.parseDouble(mealMap.get("price").toString()));
//                    intent.putExtras(bundle);
//                    intent.putExtra("code", code);
//                    intent.putExtra("signShopDetail", signShopDetail);
//                    intent.putExtra("signValue", signValue);
//                    startActivity(intent);
//                } else {
//
//                    Intent intent = new Intent(this, KeFuActivity.class);
//                    intent.putExtra("userId", SharedPreferencesUtil.getStringData(context, "kefuNB", "0"));
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("shop", shop);
//                    if (null == shop) {
//                        ToastUtil.showShortText(context, "操作无效");
//                        return;
//                    }
//                    intent.putExtra("ISMEMBERS", isMembers);
//                    intent.putExtra("isSignActiveShop", isSignActiveShop);
//                    intent.putExtras(bundle);
//
//                    startActivity(intent);
//
//                }
                break;

            case R.id.tv_fenxiang:// 右边的分享 一键分享
                // MobclickAgent.onEvent(context, "shopdetailshareclick");
                double feedback = 0;
                if (null == shop) {
                    ToastUtil.showShortText(context, "操作无效");
                    return;
                }

                String a = new DecimalFormat("#0.0").format(shop.getShop_se_price() * 0.1);
                feedback = Double.valueOf(a);


                showMyPopwindou(MealShopDetailsActivity.this, feedback);
                break;

            case R.id.tv_buy_now:


                break;
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.activity_ll_two_buy:// 拼团中自己发起2人团购买

                break;
            case R.id.activity_ll_group_buy:// 拼团中组别人的团购买

                break;
            case R.id.activity_ll_single_buy:// 拼团中单个立即购买

            case R.id.tv_shop_car:// 活动商品立即购买 普通商品 加入购物车

                break;
            case R.id.sign_buy:

                break;
            case R.id.img_cart2:
            case R.id.img_cart:// 购物车
                // MobclickAgent.onEvent(context, "toshopcartclick");
                YunYingTongJi.yunYingTongJi(context, 106);// 商品详情页购物车
                Intent intent2 = new Intent(this, ShopCartNewNewActivity.class);
                if (isMeal) {
                    intent2.putExtra("where", "1");
                } else {
                    intent2.putExtra("where", "0");
                }

                startActivityForResult(intent2, 235);
                break;

            case R.id.lin_add_like: // 加喜好
                // MobclickAgent.onEvent(context, "addlikeclick");
                if (isMeal) {

                } else {
                    addLikeShop(null);
                    // Intent intent = new
                    // Intent(this,com.yssj.ui.activity.DisplayOrderDetialsActivity.class);
                    // startActivity(intent);
                }
                break;
            case R.id.img_fenx:// 一键分享
                // MobclickAgent.onEvent(context, "shopdetailshareclick");
                // double feedback = 0;
                // if (isMeal || "SignShopDetail".equals(signShopDetail)) {
                // // getShareShop();
                // if (null == mealMap) {
                // ToastUtil.showShortText(context, "操作无效");
                // return;
                // }
                // // getPshareShop();
                // } else {
                // if (null == shop) {
                // ToastUtil.showShortText(context, "操作无效");
                // return;
                // }
                //
                // // feedback = shop.getKickback();8
                // String a = new
                // DecimalFormat("#0.0").format(shop.getShop_se_price() * 0.1);
                // feedback = Double.valueOf(a);
                // // share(shop.getShop_code(), shop);
                // }
                // // 清除分享动画 并保存时间(活动商品点击分享 因没有动画 不清楚 也不保存当前时间值)
                // if (redShare != null && moneyShare != null && !isSignActiveShop
                // && !isMeal) {
                // redShare.clearAnimation();
                // moneyShare.clearAnimation();
                // SharedPreferencesUtil.saveStringData(context, Pref.SHAREANIM,
                // System.currentTimeMillis() + "");
                // }
                //
                // showMyPopwindou(ShopDetailsActivity.this, feedback);
                break;
            case R.id.rl_retain:
                // 跳转到购物车结算页面
                YunYingTongJi.yunYingTongJi(context, 107);
                Intent intent = new Intent(MealShopDetailsActivity.this, ShopCartNewNewActivity.class);
                if (isMeal) {
                    intent.putExtra("where", "1");
                } else {
                    intent.putExtra("where", "0");
                }

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
//                intentOne.putExtra("ISMEMBERS", isMembers);
//                intentOne.putExtra("isSignActiveShop", isSignActiveShop);
//                intentOne.putExtras(bundle);
//                startActivity(intentOne);

                WXminiAppUtil.jumpToWXmini(this);


                break;
            case R.id.ll_onlyshop_red:

                if (number_sold != null && number_sold.equals("none")) {
                    return;
                } else {
                    queryShopQueryAttr();
                }

                break;
            case R.id.ll_oneshop_red:

//                ToastUtil.showShortText(ShopDetailsActivity.this, "是否为新用户" + isNewUser);
//                if (isNewUser) {
//                    showMyPopwindou(ShopDetailsActivity.this, 0);

                queryShopQueryAttrOneBuy();


//                NewMealOneSharePopupwindow ospd = new NewMealOneSharePopupwindow(MealShopDetailsActivity.this, shop, "0");
//                ospd.showAtLocation(MealShopDetailsActivity.this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

//                }


                break;

            // case R.id.rl_retain_top:
            // //TODO 跳转到购物车结算页面
            // Intent intent1 = new
            // Intent(ShopDetailsActivity.this,ShopCartNewNewActivity.class);
            // startActivity(intent1);
            default:
                break;
        }
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
                        // MyLogYiFu.e("pic", (String) result.get("shop_pic"));
                        //
                        // tongjifenxiangCount(); // 统计分享次数
                        // tongjifenxiang(code);// 统计谁分享了

                        TongjiShareCount.tongjifenxiangCount();
                        TongjiShareCount.tongjifenxiangwho(code);

                        // 8 特卖的统计

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

                    // MyLogYiFu.e("统计分享过的用户",result+"");
                    if (result.get("status").equals("1")) {
                        // 没有分享过

                        TongjiShareCount.tongjifenxiangCount();
                        TongjiShareCount.tongjifenxiangwho(code);

                        // 8 普通商品

                        LogYiFu.e("pic", result.get("shop_pic"));
                        String[] picList = result.get("shop_pic").split(",");
                        String four_pic = result.get("four_pic").toString();
                        String link = result.get("link");
                        // download(null, picList, code, result, shop, link,
                        // four_pic);
//						getNineBmBg(null, picList, code, result, shop, link, four_pic);
//						getShareTitleText(code,link,four_pic);
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

        new SAsyncTask<Void, Void, Void>((FragmentActivity) MealShopDetailsActivity.this, v, R.string.wait) {

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
                        if (isMeal) {
                            // Bitmap bm = QRCreateUtil.createZeroImage(link,
                            // 500, 700,
                            // (String) mapInfos.get("shop_se_price"),
                            // ShopDetailsActivity.this);// 得到二维码图片
                            // 九宫图二维码新样式
                            Bitmap bmQr = QRCreateUtil.createQrImage(link, 250, 250);
                            Bitmap bm = QRCreateUtil.drawNewBitmapNine(MealShopDetailsActivity.this, bmQr,
                                    (String) mapInfos.get("shop_se_price"), true);
                            QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
                                    MD5Tools.md5(String.valueOf(i)) + ".jpg");// 保存二维码图片
                        } else {
                            Bitmap bm = QRCreateUtil.createImage(link, 500, 700, (String) mapInfos.get("shop_se_price"),
                                    MealShopDetailsActivity.this);// 得到二维码图片
                            QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
                                    MD5Tools.md5(String.valueOf(i)) + ".jpg");// 保存二维码图片
                        }
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
                    // qqShareIntent =
                    // ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
                    // wXinShareIntent =
                    // ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
                    // ShareUtil.configPlatforms(context);

                    UMImage umImage = new UMImage(context, bmBg);
                    // ShareUtil.setShareContent(context, umImage,
                    // "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);
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
        new SAsyncTask<Void, Void, Void>((FragmentActivity) MealShopDetailsActivity.this, v, R.string.wait) {

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
                // LogYiFu.i("TAG", "piclist=" + picList.length);
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
                        if ("SignShopDetail".equals(signShopDetail)) {
                            // Bitmap bm = QRCreateUtil.createZeroImage(link,
                            // 500, 700, (String) mapInfos.get("price"),
                            // ShopDetailsActivity.this);// 得到二维码图片
                            // QRCreateUtil.saveBitmap(bm,
                            // YConstance.savePicPath,
                            // MD5Tools.md5(String.valueOf(9)) + ".jpg");//
                            // 保存二维码图片
                            // 九宫图二维码新样式
                            Bitmap bmQr = QRCreateUtil.createQrImage(link, 250, 250);
                            Bitmap bm = QRCreateUtil.drawNewBitmapNine(MealShopDetailsActivity.this, bmQr,
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
     * 显示分享的myPopWindow
     *
     * @param context
     */
    private void showMyPopwindou(FragmentActivity context, final double feedBack) {
        // myPopupwindow = new MyPopupwindow(context, 0, umImage, link);
        // myPopupwindow = new MyPopupwindow(context,
        // shop.getKickback(), umImage, link);

        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) MealShopDetailsActivity.this,
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

                myPopupwindow = new MyPopupwindow(true, context, feedBack, MealShopDetailsActivity.this, shop, isMeal,
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
                        PicassoUtils.initImage(MealShopDetailsActivity.this, mStartPic, MyPopupwindow.iv_img);
                    }

                }

                if (isMeal || "SignShopDetail".equals(signShopDetail)) {
                    myPopupwindow.setGou(true);
                }
                if (MealShopDetailsActivity.instance != null) {
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
        new SAsyncTask<Void, Void, Void>((FragmentActivity) MealShopDetailsActivity.this, v, R.string.wait) {

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
                        if (isMeal || "SignShopDetail".equals(signShopDetail)) {
                            Bitmap bm = QRCreateUtil.createZeroImage(link, 500, 700, mapInfos.get("shop_se_price"),
                                    MealShopDetailsActivity.this);// 得到二维码图片
                            QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
                                    MD5Tools.md5(String.valueOf(i)) + ".jpg");// 保存二维码图片
                        } else {
                            // Bitmap bm =
                            // QRCreateUtil.createImage(mapInfos.get("QrLink"),
                            // 500, 700,
                            // mapInfos.get("shop_se_price"),
                            // ShopDetailsActivity.this);// 得到二维码图片
                            // 九宫图二维码的样式
                            // Bitmap bmQr =
                            // QRCreateUtil.createQrImage(mapInfos.get("QrLink"),
                            // 250, 250);
                            // Bitmap bm =
                            // QRCreateUtil.drawNewBitmapNine(ShopDetailsActivity.this,
                            // bmQr,
                            // mapInfos.get("shop_se_price"), false);
                            Bitmap bmQr = QRCreateUtil.createQrImage(mapInfos.get("QrLink"), 190, 190);
                            Bitmap bm = QRCreateUtil.drawNewBitmapNine2(MealShopDetailsActivity.this, bmQr, bmNineBg);

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
                // showShareDialog();
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
//                    getShareTitleText(shop_code,link,"");
                }

            }

        }.execute();

    }

    public void getTyepe2SuppLabel(final String shop_code, final String link, final String four_pic) {
        new SAsyncTask<Void, Void, HashMap<String, String>>(MealShopDetailsActivity.this, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModelZ.geType2SuppLabe(MealShopDetailsActivity.this, shop_code);
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
                }
            }

        }.execute();
    }

    public void getShareTitleText(final String shop_code, final String link, final String four_pic, final String type2, final String label_id) {
        new SAsyncTask<Void, Void, HashMap<String, String>>(MealShopDetailsActivity.this, R.string.wait) {

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

//                    UMImage umImage = new UMImage(context, bmBg);
                    shareFour_pic = four_pic;
                    UMImage umImage = new UMImage(context, YUrl.imgurl + shop_code.substring(1, 4) + "/" + shop_code + "/" + shop.getDef_pic() + "!450");
//                    Random random=new Random();
//                   int a= random.nextInt(2);
//                    if(a==0) {//分享以前的标题
//                        ShareUtil.setShareContent(context, umImage, "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);
//                    }else{//分享新标题
                    ShareUtil.setShareNewTitleContent(context, umImage, "" + text, link, str4);
//                    }
                    myPopupwindow.setLink(link);
                    myPopupwindow.setUmImage(umImage);
                    shareTo(shop_code, link, str4, text);
                }
            }

        }.execute();
    }

    public static String shareFour_pic = "";

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
                if (isMeal || "SignShopDetail".equals(signShopDetail)) {
                    if (myPopupwindow.isSecondShare()) {
                        myPopupwindow.onceShare(wXinShareIntent, "微信");
                        yunYunTongJi(shop_code, 1, 2);
                    }
                } else {
                    // boolean WxCircleFlag =
                    // SharedPreferencesUtil.getBooleanData(context,
                    // Pref.RECORD_WXCIRCLE, false);
                    if (myPopupwindow.isSecondShare()) {
                        // if (WxCircleFlag) {
                        // SharedPreferencesUtil.saveBooleanData(context,
                        // Pref.RECORD_WXCIRCLE, false);
                        // myPopupwindow.onceShare(wXinShareIntent, "微信");// 九宫图
                        // yunYunTongJi(shop_code, 1, 2);
                        // } else {
                        // SharedPreferencesUtil.saveBooleanData(context,
                        // Pref.RECORD_WXCIRCLE, true);
                        myPopupwindow.performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);// 链接
                        yunYunTongJi(shop_code, 1, 2);
                        // }
                    }
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
        if (isMeal || "SignShopDetail".equals(signShopDetail)) {
            super.onBackPressed();
        } else {
            if (shop != null) {
                setResult(-1, new Intent().putExtra("isLike", shop.getLike_id() == -1 ? 0 : 1));
            }
            super.onBackPressed();
        }
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
//                img_xin.setImageResource(R.drawable.hx0);
                like_id = 1;
            } else {
//                img_xin.setImageResource(R.drawable.icon_xihuan);
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

                        return ComModel.addLikeShop(MealShopDetailsActivity.this,
                                YCache.getCacheToken(MealShopDetailsActivity.this), params[0]);
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
                                Toast.makeText(MealShopDetailsActivity.this, "添加我的喜欢成功", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(MealShopDetailsActivity.this, "添加我的喜欢失败", Toast.LENGTH_SHORT).show();
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

                        return ComModel.deleteLikeShop(MealShopDetailsActivity.this,
                                YCache.getCacheToken(MealShopDetailsActivity.this), params[0]);
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
                                Toast.makeText(MealShopDetailsActivity.this, "删除我的喜欢成功", Toast.LENGTH_SHORT).show();
//                                img_xin.setImageResource(R.drawable.icon_xihuan);
                                shop.setLike_id(-1);

                            } else {
                                Toast.makeText(MealShopDetailsActivity.this, "删除我的喜欢失败", Toast.LENGTH_SHORT).show();
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
        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) MealShopDetailsActivity.this,
                R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                mGoldIconMap = ComModel2.getTwoFoldnessGold(MealShopDetailsActivity.this);
                mGoldVoucherMap = ComModel2.getCpgold(MealShopDetailsActivity.this);
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
    private void queryShopDetails(final boolean isLoginBack) {

        // attrDateStr = getSharedPreferences(Pref.sync, Context.MODE_PRIVATE)
        // .getString(Pref.sync_attr_date, "");
        aa = new SAsyncTask<String, Void, HashMap<String, Object>>(this, null, R.string.wait) {

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                LoadingDialog.show(MealShopDetailsActivity.this);
            }

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                Shop shop;
                HashMap<String, Object> map;


                if (YJApplication.instance.isLoginSucess()) {


                    map = ComModel.queryShopDetails(MealShopDetailsActivity.this,
                            YCache.getCacheToken(MealShopDetailsActivity.this), params[0], attrDateStr, sweet_theme_id);

                } else {
                    map = ComModel.queryShopDetails2(MealShopDetailsActivity.this, params[0], attrDateStr);
                }

                // return shop;
                return map;
            }

            @SuppressLint({"NewApi", "InflateParams", "SimpleDateFormat"})
            @Override
            protected void onPostExecute(final FragmentActivity context, HashMap<String, Object> map, Exception e) {
                showShopDetailsBuyTipsDialog();// 商品详情 购买可抽奖提示 每天只弹出一次
                if (e != null) {// 查询异常
                    /*
                     * Toast.makeText(ShopDetailsActivity.this, "连接超时，请重试",
					 * Toast.LENGTH_LONG).show();
					 */

                } else {// 查询商品详情成功，刷新界面


                    mSingleBuy.setOnClickListener(MealShopDetailsActivity.this);
                    mTwoBuy.setOnClickListener(MealShopDetailsActivity.this);
                    mGroupBuy.setOnClickListener(MealShopDetailsActivity.this);
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
                        mSinglePrice.setText("￥" + new DecimalFormat("#0.0").format(shopd.getShop_se_price()));
                        mTwoPrice.setText("￥" + shopd.getRoll_price());
                        mGroupPrice.setText("￥" + shopd.getRoll_price());
                        setEva_count_z = (int) Float.parseFloat(shopd.getEva_count() + ""); // 评价总数

                        LogYiFu.e("评价总数", setEva_count_z + "");

                        shop = shopd;


                        //确定新老用户
//                        isNewUser = shop.getZeroOrderNum() <= 0;
                        LogYiFu.e("shop.getZeroOrderNum()", shop.getZeroOrderNum() + "");

                        // titleCheck = shop.getType1();
                        for (int i = 0; i < listTitle.size(); i++) {
                            if ((titleId + "").equals(listTitle.get(i).get("_id"))) {
                                titleCheck = i;
                                break;
                            }
                        }
                        if (titleCheck >= listTitle.size()) {
                            titleCheck = 0;
                        }

                        // if (shop.getLike_id() == -1) {
                        // img_xin.setImageResource(R.drawable.icon_xihuan);
                        // } else {// 加心
                        // img_xin.setImageResource(R.drawable.hx0);
                        // }
                        if (YJApplication.instance.isLoginSucess() == true) {
                            String str = SharedPreferencesUtil.getStringData(context,
                                    "" + YCache.getCacheUser(context).getUser_id(), "");
                            if (str.contains(shop.getShop_code())) {
//                                img_xin.setImageResource(R.drawable.hx0);
                            } else {
//                                img_xin.setImageResource(R.drawable.icon_xihuan);
                            }
                        }


                        // headerView =
                        // LayoutInflater.from(ShopDetailsActivity.this).inflate(R.layout.shop_header,
                        // null);
                        //没有拼团商品了
//                            if (isSignActiveShop || isSignActiveShopScan) {
//                                headerView = LayoutInflater.from(ShopDetailsActivity.this).inflate(R.layout.shop_header,
//                                        null);
//                            } else {
//                                headerView = LayoutInflater.from(ShopDetailsActivity.this)
//                                        .inflate(R.layout.shop_header_nopintuan, null);
//                            }

                        headerView = LayoutInflater.from(MealShopDetailsActivity.this)
                                .inflate(R.layout.shop_header_nopintuan_meal, null);

                        width = MealShopDetailsActivity.this.getResources().getDisplayMetrics().widthPixels;
                        headerView.findViewById(R.id.meal_name).setVisibility(View.GONE);
                        // 新服务声明
                        headerView.findViewById(R.id.head_ll_new_decleration).setVisibility(View.VISIBLE);
                        headerView.findViewById(R.id.head_ll_old_decleration).setVisibility(View.GONE);
                        // final HashMap<String, String> mapSupply =
                        // (HashMap<String, String>) map
                        // .get("supplier_label");
                        TextView supply_name = (TextView) headerView.findViewById(R.id.head_tv_supply);// 供应商
                        LinearLayout ll_supply = (LinearLayout) headerView.findViewById(R.id.ll_supply);
                        final String supp_label_id = shopd.getSupp_label_id();
                        mSupp_label = shopd.getSupp_label();
                        // if (null == mapSupply ||
                        // "".equals(mapSupply.get("name"))) {//
                        // 没有供应商标签(隐藏标签)
                        if (TextUtils.isEmpty(supp_label_id) || TextUtils.isEmpty(mSupp_label)) {// 没有供应商标签(隐藏标签)
                            ll_supply.setVisibility(View.GONE);
                            mSupp_label = "";
                        } else {
                            ll_supply.setVisibility(View.VISIBLE);
                            // mSupp_label = "" + mapSupply.get("name") +
                            // "制造商出品";
//                                supply_name.setText("" + mSupp_label + "制造商出品");
                            supply_name.setText("" + mSupp_label);


                            supply_name.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    // getSupplyExplainDialog("" +
                                    // mapSupply.get("name"),
                                    // "" + mapSupply.get("content1"), "" +
                                    // mapSupply.get("content2"));
                                    Intent intent = new Intent();
                                    intent = new Intent(MealShopDetailsActivity.this, ManufactureActivity.class);
                                    intent.putExtra("supple_id", supp_label_id);
                                    context.startActivity(intent);
                                    ((Activity) context).overridePendingTransition(R.anim.activity_from_right,
                                            R.anim.activity_search_close);
                                }
                            });
                        }

                        // 7天倒计时
                        if (isSignActiveShop || isHot) {
                            headerView.findViewById(R.id.ll_active_sold).setVisibility(View.VISIBLE);
                            virtual_sales = shopd.getVirtual_sales();
                            // if(virtual_sales!=0){
                            ((TextView) headerView.findViewById(R.id.tv_sold)).setText("已售" + virtual_sales + "件/");
                            // }
                            String randomStr = SharedPreferencesUtil.getStringData(context,
                                    Pref.SIGN_ACTIVE_SHOP_LEFT + code, "");
                            if ("".equals(randomStr)) {
                                int random = (int) (Math.random() * (25 - 3) + 3);// 生成3到25之间的随机数
                                SharedPreferencesUtil.saveStringData(context, Pref.SIGN_ACTIVE_SHOP_LEFT + code,
                                        "" + random);
                                randomStr = random + "";
                            }
                            ((TextView) headerView.findViewById(R.id.tv_sold_left)).setText("仅剩" + randomStr + "件");
                        } else {

                            // ShareShop shareShop = new ShareShop();
                            // Long count = shareShop.getCount();
                            // System.out.println("这里数量对不对="+shop.getCount());
                            ///////////////
                            if (timer_seven != null) {
                                timer_seven.cancel();
                            }

                            task_seven = new TimerTask() {
                                @Override
                                public void run() {

                                    runOnUiThread(new Runnable() { // UI
                                        // thread
                                        @Override
                                        public void run() {
                                        }
                                    });
                                }
                            };
                            timer_seven = new Timer();
                            // recLen_seven =
                            // 7*24*60*60*1000;//60*24*5*20*1000

                            timer_seven.schedule(task_seven, 0, 1000); // 显示倒计时
                            String audit_time = shop.getAudit_time(); // 七天倒计时时间

                            if (!audit_time.contains("-")) {

                                try {
                                    String date_new = DateFormatUtils.format(Long.parseLong(audit_time),
                                            "yyyy-MM-dd HH:mm:ss");

                                    SimpleDateFormat df_seven = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date date = df_seven.parse(date_new);

                                    Calendar calendar = new GregorianCalendar();
                                    calendar.setTime(date);
                                    calendar.add(calendar.DATE, 7);// 把日期往后增加7天.整数往后推,负数往前移动
                                    date = calendar.getTime(); // 这个时间就是日期往后推7天的结果

                                    String format = df_seven.format(date);

                                    long service_time = shop.getS_time();
                                    String time_sys = DateFormatUtils.format(Long.parseLong(service_time + ""),
                                            "yyyy-MM-dd HH:mm:ss");

                                    Date d1 = df_seven.parse(format);
                                    Date d2 = df_seven.parse(time_sys);
                                    long diff = d1.getTime() - d2.getTime();// 这样得到的差值是毫秒级别

                                    recLen_seven = diff;// 60*24*5*20*1000
                                } catch (ParseException e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }
                            }
                        }
                        headerView.findViewById(R.id.one_position).setBackgroundColor(Color.WHITE);
                        adapter = new MyAdapter();
                        String[] imgs = shop.getShop_pic().split(",");
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < imgs.length; i++) {
                            if (imgs[i].contains("reveal_") || imgs[i].contains("real_")
                                    || imgs[i].contains("detail_")) {
                                sb.append(imgs[i] + ",");
                            }
                        }

                        images = sb.toString().substring(0, sb.length() - 1).split(",");
                        headerView.findViewById(R.id.img_header).getLayoutParams().height = width * 9 / 6;
                        heights = width * 9 / 6;
                        String def_pic = shop.getDef_pic();
                        if (!TextUtils.isEmpty(def_pic)) {
                            // SetImageLoader.initImageLoader(null,
                            // (ScaleImageView)
                            // headerView.findViewById(R.id.img_header),
                            // shop.getShop_code().substring(1, 4) + "/" +
                            // shop.getShop_code() + "/" + def_pic,
                            // "!450");

//                                PicassoUtils.initImage(
//                                        ShopDetailsActivity.this, shop.getShop_code().substring(1, 4) + "/"
//                                                + shop.getShop_code() + "/" + def_pic + "!450",
//                                        (ScaleImageView) headerView.findViewById(R.id.img_header));
                            //不再压缩
                            PicassoUtils.initImage(
                                    MealShopDetailsActivity.this, shop.getShop_code().substring(1, 4) + "/"
                                            + shop.getShop_code() + "/" + def_pic,
                                    (ScaleImageView) headerView.findViewById(R.id.img_header));

                            headerView.findViewById(R.id.img_header).setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MealShopDetailsActivity.this, ShopImageActivity.class);
                                    intent.putExtra("url", shop.getShop_code().substring(1, 4) + "/"
                                            + shop.getShop_code() + "/" + shop.getDef_pic());
                                    intent.putExtra("shop", shop);
                                    intent.putExtra("isMembers", isMembers);

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

                        num = Integer.valueOf(num_save).intValue();

                        if (isSignActiveShop) {
                        } else {

                        }
                        // String dikou = new
                        // DecimalFormat("#0").format(shop.getKickback());
                        String dikou = shop.getKickback() + "";
                        double valueOf = Double.valueOf(dikou);

                        // tv_fenxiang.setText(
                        // "分享赚" + new
                        // DecimalFormat("#0.0").format(shop.getShop_se_price()
                        // * 0.1) + "元");

                        int dikou_int = (int) valueOf;
                        // 判断是否有了余额翻倍，金币或者抵用券

                        if (YJApplication.instance.isLoginSucess()) {
                            getGoldIsOpen(dikou_int);
                        }

                        if (isSignActiveShop) {

                        } else {

                        }

                        String xiang_mongey = new DecimalFormat("#0.0").format(shop.getShop_se_price() * 0.1);
                        f_xiang_mongey = Double.valueOf(xiang_mongey);

                        if (isMembers) {

                        } else {
                            int a = (int) shop.getKickback();
                        }

                        if (isSignActiveShop) {
//								double shopSePrice = shop.getShop_se_price();
                            double shopGroupPrice;
                            if (group_click_flag) {
                                shopGroupPrice = shop.getRoll_price();
                            } else {
                                shopGroupPrice = shop.getShop_se_price();
                            }
                            double shopPrice = shop.getShop_price();

                            ((TextView) headerView.findViewById(R.id.tv_price))
                                    .setText("¥" + shopGroupPrice);

                            String shop_price = "专柜价¥" + new DecimalFormat("#0.0").format(shopPrice);
                            if (!TextUtils.isEmpty(shop_price)) {
                                ToastUtil.addStrikeSpan((TextView) headerView.findViewById(R.id.tv_sjprice),
                                        shop_price);
                            }
                            double discount = (shopGroupPrice / shopPrice) * 10;
                            double save = shopPrice - shopGroupPrice;
                            if (discount < 0.1) {
                                discount = 0.1;
                            }
                            headerView.findViewById(R.id.tv_active_discount).setVisibility(View.VISIBLE);
                            headerView.findViewById(R.id.tv_active_save).setVisibility(View.VISIBLE);
                            ((TextView) headerView.findViewById(R.id.tv_active_discount))
                                    .setText(new DecimalFormat("#0.0").format(discount) + "折");
                            ((TextView) headerView.findViewById(R.id.tv_active_save))
                                    .setText("立省" + new DecimalFormat("#0.0").format(save) + "元");
                        } else {


                            double shopSePrice = shop.getShop_se_price();
                            double shopPrice = shop.getShop_price();
                            double discount = (shopSePrice / shopPrice) * 10;
                            headerView.findViewById(R.id.tv_active_discount).setVisibility(View.VISIBLE);
                            TextView tv_discount = ((TextView) headerView.findViewById(R.id.tv_active_discount));
                            tv_discount.setVisibility(View.GONE);
//                                tv_discount.getPaint().setFakeBoldText(true);//加粗
//                                tv_discount.setText(new DecimalFormat("#0.0").format(discount) + "折");


                            if (!isLoginBack) {
                                shouTVprice = (TextView) headerView.findViewById(R.id.tv_price);

                            }

                            if (GuideActivity.show1yuan) {


                                if (isLoginBack) { //登录回来的已经查询过新老用户

                                    LogYiFu.e("登录回来", "11111111");


//                                        if (isNewUser) { //新用户
//
//
//                                            tv_onlyshop_price.setText("¥0.0");
//                                            tv_onlyshop_bt_text.setText("0元购买");
//
//
//                                            shouTVprice
//                                                    .setText("¥0.0元");
//
//
//                                        } else { //老用户


                                    String onPrice = shop.getApp_shop_group_price();


                                    onPrice = new DecimalFormat("#0.0")
                                            .format(Double.parseDouble(onPrice));


                                    tv_onlyshop_price.setText("¥" + onPrice);//1元购买上面的


//                                        tv_onlyshop_bt_text.setText(GuideActivity.oneShopPrice + "元购买");//底部1元购买
                                    tv_onlyshop_bt_text.setText(shop.getReturnOneText() + "");//底部1元购买


                                    //详情内显示支付价格
                                    shouTVprice
                                            .setText("¥" + onPrice + "元");

//                                        }


                                    if (YJApplication.instance.isLoginSucess()) {
                                        setDandugoumai(tv_onlyshop_red);
                                    } else {
                                        tv_onlyshop_red.setText("¥" + new DecimalFormat("#0.0").format(shop.getShop_se_price()));

                                    }


//                                        tv_onlyshop_red.setText("¥" + new DecimalFormat("#0.0").format(shop.getShop_se_price()));
                                    tv_onlyshop_red_text.setText("赚￥" + (ComputeUtil.div(shop.getShop_se_price(), 2, 1) > 50.0 ? 50.0 : ComputeUtil.div(shop.getShop_se_price(), 2, 1)));


                                } else {  //不是登录回来的

                                    LogYiFu.e("登录回来", "444444");


                                    String onePrice = new DecimalFormat("#0.0")
                                            .format(Double.parseDouble(shop.getApp_shop_group_price()));


                                    if (YJApplication.instance.isLoginSucess() || YJApplication.isLogined) {


//                                            if (isNewUser) { //新用户
//
//
//                                                tv_onlyshop_price.setText("¥0.0");
//                                                tv_onlyshop_bt_text.setText("0元购买");
//
//
//                                                shouTVprice
//                                                        .setText("¥0.0元");
//
//
//                                            } else {


                                        String onPrice = shop.getApp_shop_group_price();


                                        onPrice = new DecimalFormat("#0.0")
                                                .format(Double.parseDouble(onPrice));


                                        tv_onlyshop_price.setText("¥" + onPrice);//1元购买上面的


                                        tv_onlyshop_bt_text.setText(shop.getReturnOneText() + "");//底部1元购买

                                        LogYiFu.e("刷新", "777" + onPrice);


                                        //详情内显示支付价格
                                        shouTVprice
                                                .setText("¥" + onPrice + "元");

//                                            }

                                        if (YJApplication.instance.isLoginSucess()) {
                                            setDandugoumai(tv_onlyshop_red);
                                        } else {
                                            tv_onlyshop_red.setText("¥" + new DecimalFormat("#0.0").format(shop.getShop_se_price()));

                                        }


//                                            tv_onlyshop_red.setText("¥" + new DecimalFormat("#0.0").format(shop.getShop_se_price()));
                                        tv_onlyshop_red_text.setText("赚￥" + (ComputeUtil.div(shop.getShop_se_price(), 2, 1) > 50.0 ? 50.0 : ComputeUtil.div(shop.getShop_se_price(), 2, 1)));


                                    } else { //未登录

                                        LogYiFu.e("登录回来", "6666");


                                        shouTVprice
                                                .setText("¥" + onePrice + "元"); //实际售价
                                        String mOnPrice = new DecimalFormat("#0.0")
                                                .format(Double.parseDouble(shop.getApp_shop_group_price()));

                                        tv_onlyshop_price.setText("¥" + mOnPrice); //一元上面
                                        tv_onlyshop_bt_text.setText(shop.getReturnOneText() + ""); //1元下面


//                                        onyuanPrice
//                                                .setText("¥0.0元");

                                        tv_onlyshop_red.setText("¥" + new DecimalFormat("#0.0").format(shop.getShop_se_price()));
                                        tv_onlyshop_red_text.setText("赚￥" + (ComputeUtil.div(shop.getShop_se_price(), 2, 1) > 50.0 ? 50.0 : ComputeUtil.div(shop.getShop_se_price(), 2, 1)));
                                    }

                                }


                            } else {
//                                    tv_onlyshop_red.setText("¥" + new DecimalFormat("#0.0").format(shop.getShop_se_price()));

                                if (YJApplication.instance.isLoginSucess()) {
                                    setDandugoumai(tv_onlyshop_red);
                                } else {
                                    tv_onlyshop_red.setText("¥" + new DecimalFormat("#0.0").format(shop.getShop_se_price()));

                                }


                                tv_onlyshop_red_text.setText("赚￥" + (ComputeUtil.div(shop.getShop_se_price(), 2, 1) > 50.0 ? 50.0 : ComputeUtil.div(shop.getShop_se_price(), 2, 1)));
                                shouTVprice
                                        .setText("¥" + new DecimalFormat("#0.0").format(shop.getShop_se_price()));
                            }


                            final TextView diKou = (TextView) headerView.findViewById(R.id.tv_money_dikous);
                            final ImageView iv_wenhao = (ImageView) headerView.findViewById(R.id.iv_wenhao);
                            diKou.setVisibility(View.VISIBLE);

//                                if (YJApplication.instance.isLoginSucess()) {
//                                    getDIKOU(diKou);
//
//                                } else {
//                                    diKou.setText("余额可抵扣0.0元");
//                                }
//
////                                try {
////                                    diKou.setText("余额已抵扣"
////                                            + new DecimalFormat("#0.0").format((shop.getShop_se_price()) * 0.1) + "元");
////                                } catch (Exception e2) {
////                                }
//
//
//                                diKou.setOnClickListener(new OnClickListener() {
//
//                                    @Override
//                                    public void onClick(View v) {
//                                        DialogUtils.getDiKouDialogNew(MealShopDetailsActivity.this, "余额抵扣说明", false,true);
//                                    }
//                                });
//
//
//                                iv_wenhao.setOnClickListener(new OnClickListener() {
//
//                                    @Override
//                                    public void onClick(View v) {
//
//                                        DialogUtils.getDiKouDialogNew(MealShopDetailsActivity.this, "余额抵扣说明", false,true);
//
//                                    }
//                                });

                            if (YJApplication.instance.isLoginSucess()) { //会员界面

                                //查询抵扣
                                HashMap<String, String> pairsMap = new HashMap<>();
                                YConn.httpPost(instance, YUrl.GETALLDIKOU, pairsMap, new HttpListener<VipDikouData>() {
                                    @Override
                                    public void onSuccess(VipDikouData vipDikouData) {

                                        if (null != vipDikouData && vipDikouData.getIsVip() > 0) {//会员
                                            double vipPrice;
                                            double prices = shop.getShop_se_price();

                                            double sePrice = prices * 0.95;
                                            double dikou = prices * 0.9;

                                            if (vipDikouData.getOne_not_use_price() >= dikou) {
                                                if (vipDikouData.getMaxType() == 6) {
                                                    vipPrice = sePrice - dikou;
                                                } else {
                                                    vipPrice = prices - dikou;

                                                }
                                                diKou.setText("已抵扣" + dikou + "元");

                                            } else {

                                                if (vipDikouData.getMaxType() == 6) {
                                                    vipPrice = sePrice - vipDikouData.getOne_not_use_price();
                                                } else {
                                                    vipPrice = prices - vipDikouData.getOne_not_use_price();

                                                }
                                                diKou.setText("已抵扣" + vipDikouData.getOne_not_use_price() + "元");


                                            }

                                            ((TextView) headerView.findViewById(R.id.tv_price)).setText("¥" + new java.text.DecimalFormat("#0.0").format(vipPrice));


                                        } else {//非会员


                                            String mOnPrice = "¥" + new DecimalFormat("#0.0")
                                                    .format(Double.parseDouble(shop.getApp_shop_group_price()));
                                            ((TextView) headerView.findViewById(R.id.tv_price)).setText(mOnPrice);
                                            diKou.setText("已抵扣0.0元");

                                        }
                                        TextView tvprice = headerView.findViewById(R.id.tv_sjprice);
                                        tvprice.setText("原价¥"
                                                + new DecimalFormat("#0.0").format(shop.getShop_price()));

                                        diKou.setOnClickListener(new OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                DialogUtils.getDiKouDialogNew(MealShopDetailsActivity.this, "余额抵扣说明", false, true);
                                            }
                                        });


                                        iv_wenhao.setOnClickListener(new OnClickListener() {

                                            @Override
                                            public void onClick(View v) {

                                                DialogUtils.getDiKouDialogNew(MealShopDetailsActivity.this, "余额抵扣说明", false, true);

                                            }
                                        });


                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });


                            } else {
                                diKou.setText("已抵扣0.0元");
                                TextView tvprice = headerView.findViewById(R.id.tv_sjprice);
                                tvprice.setText("原价¥"
                                        + new DecimalFormat("#0.0").format(shop.getShop_price()));

                                String mOnPrice = "¥" + new DecimalFormat("#0.0")
                                        .format(Double.parseDouble(shop.getApp_shop_group_price()));
                                ((TextView) headerView.findViewById(R.id.tv_price)).setText(mOnPrice);

                            }


                            String shop_price;

//                                if (GuideActivity.show1yuan) {
//                                    shop_price = "原价¥"
//                                            + new DecimalFormat("#0.0").format(shop.getShop_se_price());
//                                } else {
//                                    shop_price = "专柜价¥"
//                                            + new DecimalFormat("#0.0").format(shop.getShop_price());
//                                }

                            shop_price = "原价¥"
                                    + new DecimalFormat("#0.0").format(shop.getShop_price());

                            ((TextView) headerView.findViewById(R.id.tv_sjprice)).setTextColor(Color.parseColor("#ff3f8b"));
                            ((TextView) headerView.findViewById(R.id.tv_sjprice)).setTextSize(15);

                            if (!TextUtils.isEmpty(shop_price)) {
//                                    ToastUtil.addStrikeSpan((TextView) headerView.findViewById(R.id.tv_sjprice),
//                                            shop_price);


                                TextView tvprice = (TextView) headerView.findViewById(R.id.tv_sjprice);
                                tvprice.setText(shop_price);

                                if (GuideActivity.show1yuan) {
                                    tvprice.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                                    shouTVprice.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                                }
                            }


                        }


//                            if (isNewUser) {
//                                tv_onlyshop_price.setText("¥0.0");
//                                tv_onlyshop_bt_text.setText("0元购买");
//
//                            } else {
//
//                                String onPrice = GuideActivity.oneShopPrice;
//                                String mPrice = new java.text.DecimalFormat("#0")
//                                        .format(Double.parseDouble(onPrice));
//                                onPrice = new java.text.DecimalFormat("#0.0")
//                                        .format(Double.parseDouble(onPrice));
//
//
//                                tv_onlyshop_price.setText("¥" + onPrice);
//                                tv_onlyshop_bt_text.setText(mPrice + "元购买");
//
//                            }

//
//                            tv_onlyshop_red.setText("¥" + new DecimalFormat("#0.0").format(shop.getShop_se_price() ));
//                            tv_onlyshop_red_text.setText("单独购买");


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

//										if (newPosition > myposition) { // 向上滑动
//											if (ll_bottem.getVisibility() == View.VISIBLE && isAnim == false) {
//												ll_bottem.clearAnimation();
//												// rlBottom.startAnimation(animationGone);
//												ll_bottem.startAnimation(animationGone);
//											}
//										} else if (newPosition < myposition) {
//											if (ll_bottem.getVisibility() == View.GONE && isAnim == false) {
//												ll_bottem.clearAnimation();
//												ll_bottem.startAnimation(animationShow);
//
//											}
//										}
                                        break;
                                    case SCROLL_STATE_IDLE:
                                        if (view.getLastVisiblePosition() == view.getCount() - 1) {
                                            if (check == 0) {
                                                if (isInit == false) {
                                                    index++;
                                                    mType = 2;
                                                    initData(titleCheck, index + "");
                                                }

                                            } else if (check == 2 && !isMembers) {

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

//											ll_bottem.clearAnimation();
                                            // rlBottom.startAnimation(animationShow);
//											ll_bottem.startAnimation(animationShow);

                                            // pingyi();
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
                                    // mTopView.setVisibility(View.GONE);
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
//                                        tvTitle.setAlpha(1-currentY / heights);
                                    mShuaixuanNew.getBackground().setAlpha(255 - i);
                                    lin_contact.getBackground().setAlpha(255 - i);
                                    // img_cart_top.getBackground()
                                    // .setAlpha(255 - i * 2 / 5);
                                    // img_fenx_top.getBackground()
                                    // .setAlpha(255 - i * 2 / 5);

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
//                                        tvTitle.setAlpha(currentY / heights);
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

//							try {
//
//								SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//								Date d1 = df1.parse(c_time_cart);
//								Date d2 = df1.parse(s_time);
//								long diff = d1.getTime() - d2.getTime();
//								recLen = diff;
//
//								if (timer != null) {
//									timer.cancel();
//								}
//								task = new TimerTask() {
//									@Override
//									public void run() {
//
//										runOnUiThread(new Runnable() { // UI
//																		// thread
//											@Override
//											public void run() {
//												jisuan();
//											}
//										});
//									}
//								};
//								timer = new Timer();
//								timer.schedule(task, 0, 1000); // 显示倒计时
//
//							} catch (ParseException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
                        } else {// 负数代表没有加入购物车
                        }
                        if (YJApplication.instance.isLoginSucess()) {

                            ShopCartDao dao = new ShopCartDao(context);
                            // int count = dao.queryCartCount(context);
                            int count = 0;
                            if (isMeal) {
                                count = dao.queryCartSpecialCount(context);
                            } else {
                                count = dao.queryCartCommonCount(context);
                            }
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
                    // 11111111111111

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

                        // for (int i = 0; i < user_list.size(); i++) {
                        for (int i = user_list.size() - 1; i >= 0; i--) {
                            HashMap<String, Object> hashMap = user_list.get(i);
                            String pic = hashMap.get("pic").toString();

                            if (!pic.contains("http")) {
                                pic = YUrl.imgurl + pic;
                            }

                        }

                    }

                }

            }

            ;

            @Override
            protected boolean isHandleException() {
                return true;
            }

            ;
        };
        aa.execute(code);

    }

    private void getDIKOU(final TextView diKou) {


        new SAsyncTask<Void, Void, String>(MealShopDetailsActivity.this, R.string.wait) {

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


        new SAsyncTask<Void, Void, String>(MealShopDetailsActivity.this, R.string.wait) {

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

    public static double OnBuyDikouprice;//1元购可抵扣金额


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

    /***
     * 查找商品属性
     */
    public void queryShopQueryAttr() {//0为单独购买
        clickFlag = false;
        if (shop != null) {
            List<StockType> list = shop.getList_stock_type();
            if (list != null && list.size() > 0) {
                showOnlyBuyDialog();
            } else {
                new SAsyncTask<String, Void, Shop>(this, null, R.string.wait) {
                    @Override
                    protected Shop doInBackground(FragmentActivity context, String... params) throws Exception {
                        return ComModel.queryShopQueryAttr(MealShopDetailsActivity.this, shop, params[0]);
                    }

                    @Override
                    protected void onPostExecute(FragmentActivity context, Shop shop, Exception e) {

                        if (e != null) {// 查询异常
                            Toast.makeText(MealShopDetailsActivity.this, "连接超时，请重试", Toast.LENGTH_LONG).show();
                        } else {// 查询商品详情成功，刷新界面
                            if (shop != null) {
                                MealShopDetailsActivity.this.shop = shop;
                                showOnlyBuyDialog();// 商品属性选择
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
                        return ComModel.queryShopQueryAttr(MealShopDetailsActivity.this, shop, params[0]);
                    }

                    @Override
                    protected void onPostExecute(FragmentActivity context, Shop shop, Exception e) {

                        if (e != null) {// 查询异常
                            Toast.makeText(MealShopDetailsActivity.this, "连接超时，请重试", Toast.LENGTH_LONG).show();
                        } else {// 查询商品详情成功，刷新界面
                            if (shop != null) {
                                MealShopDetailsActivity.this.shop = shop;
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
     * 弹出底部对话框
     *
     * @param i
     */


    int p_id_dao;

    /**
     * 套餐加入购物车
     */
    private void mealJoinShopCart(final String p_code, final int shop_num, final String p_seq,
                                  final List<StockBean> catJson, final String p_type, final String postage, final double shop_price,
                                  final double shop_se_price, final int supp_id, final String def_pic) {
        final ShopCartDao dao = new ShopCartDao(context);
        // if (dao.queryCartSpecialCount(context) + shop_num > 20) {
        // ToastUtil.showShortText(context, "购物车最多允许加入20件有效商品");
        // return;
        // }
        // clickFlag = false;// 正在加入购物车 暂时取消点击
        addAnimLayout();// 添加动画布局 小圆点

        final List<ShopCart> list = dao.findAll();
        final List<ShopCart> list_invalid = dao.findAll_invalid();
        boolean p_flag_dao = false;
        boolean p_flag_dao_invalid = false;
        int p_dao_num = 0;
        int p_dao_num_invalid = 0;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (p_seq.equals(list.get(i).getP_s_t_id())) {
                    p_flag_dao = true;
                    p_dao_num = list.get(i).getShop_num();
                    p_id_dao = list.get(i).getId();
                    break;
                }
            }
        }
        if (list_invalid != null) {
            for (int i = 0; i < list_invalid.size(); i++) {
                if (p_seq.equals(list_invalid.get(i).getP_s_t_id())) {
                    p_flag_dao_invalid = true;
                    p_dao_num_invalid = list_invalid.get(i).getShop_num();
                    p_id_dao = list_invalid.get(i).getId();
                    break;
                }
            }
        }
        if (!p_flag_dao && !p_flag_dao_invalid) {// 获取id
            new SAsyncTask<String, Void, HashMap<String, Object>>(this, v, R.string.wait) {

                @Override
                protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
                        throws Exception {

                    return ComModel2.getShopCartData(MealShopDetailsActivity.this, 1);

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
                protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {

                    if (null == e) {

                        int id = Integer.parseInt((String) result.get("id"));
                        addMealCart(p_code, shop_num, p_seq, catJson, p_type, postage, shop_price, shop_se_price,
                                supp_id, id);
                        StringBuffer mShop_code_p = new StringBuffer();
                        StringBuffer mColor_p = new StringBuffer();
                        for (int i = 0; i < catJson.size(); i++) {
                            StockBean bean = catJson.get(i);
                            if (i < catJson.size() - 1) {
                                mShop_code_p.append(bean.getShop_code()).append(",");
                                mColor_p.append(bean.getColor()).append(",");
                            } else {
                                mShop_code_p.append(bean.getShop_code());
                                mColor_p.append(bean.getColor());
                            }
                        }

                        boolean hh = dao.add(null, null, null, Integer.parseInt(String.valueOf(shop_num)), 0,
                                (String) mealMap.get("def_pic"), 0, null, null, "" + shop_price, "" + shop_se_price,
                                supp_id + "", "" + 0, "" + 0, 1, p_code, postage, p_seq, mShop_code_p.toString(),
                                mColor_p.toString(), id, p_seq.split(",").length > 1 ? "超值套餐" : "超值单品", 0, mSupp_label);
                        // 特卖过期时间
                        SharedPreferencesUtil.saveStringData(context, Pref.SHOPCART_MEAL_TIME,
                                new Date().getTime() + 30 * 1000 * 60 + "");

                        // setAnim();

                    } else {
                        // clickFlag = true;
                        rootView.removeView(pointRoot);
                    }

                    super.onPostExecute(context, result, e);
                }

            }.execute();
        } else {// 不需要获取id
            if (p_flag_dao && !p_flag_dao_invalid) {// 只有有效的
                if (p_dao_num + shop_num > 2) {
                    // clickFlag = true;
                    rootView.removeView(pointRoot);
                    ToastUtil.showShortText(context, "抱歉，数量有限，最多只能购买2件噢！");
                } else {
                    addMealCart(p_code, shop_num, p_seq, catJson, p_type, postage, shop_price, shop_se_price, supp_id,
                            p_id_dao);
                    dao.p_modify(p_seq, p_dao_num + shop_num);
                }
            } else if (!p_flag_dao && p_flag_dao_invalid) {// shixiao
                if (p_dao_num_invalid + shop_num > 2) {
                    // clickFlag = true;
                    rootView.removeView(pointRoot);
                    ToastUtil.showShortText(context, "抱歉，数量有限，最多只能购买2件噢！");
                } else {
                    addMealCart(p_code, shop_num, p_seq, catJson, p_type, postage, shop_price, shop_se_price, supp_id,
                            p_id_dao);
                    StringBuffer mShop_code_p = new StringBuffer();
                    StringBuffer mColor_p = new StringBuffer();
                    for (int i = 0; i < catJson.size(); i++) {
                        StockBean bean = catJson.get(i);
                        if (i < catJson.size() - 1) {
                            mShop_code_p.append(bean.getShop_code()).append(",");
                            mColor_p.append(bean.getColor()).append(",");
                        } else {
                            mShop_code_p.append(bean.getShop_code());
                            mColor_p.append(bean.getColor());
                        }
                    }
                    boolean hh = dao.add(null, null, null, Integer.parseInt(String.valueOf(shop_num)), 0,
                            (String) mealMap.get("def_pic"), 0, null, null, "" + shop_price, "" + shop_se_price,
                            supp_id + "", "" + 0, "" + 0, 1, p_code, postage, p_seq, mShop_code_p.toString(),
                            mColor_p.toString(), p_id_dao, p_seq.split(",").length > 1 ? "超值套餐" : "超值单品", 0,
                            mSupp_label);
                    SharedPreferencesUtil.saveStringData(context, Pref.SHOPCART_MEAL_TIME,
                            new Date().getTime() + 30 * 1000 * 60 + "");
                }

            } else {
                // clickFlag = true;
                rootView.removeView(pointRoot);
                ToastUtil.showShortText(context, "抱歉，数量有限，最多只能购买2件噢！");
            }
            // setAnim();
        }
    }

    /**
     * @param p_code
     * @param shop_num
     * @param p_seq
     * @param catJson
     * @param p_type
     * @param postage
     * @param shop_price
     * @param shop_se_price
     * @param supp_id
     */

    private void addMealCart(final String p_code, final int shop_num, final String p_seq, final List<StockBean> catJson,
                             final String p_type, final String postage, final double shop_price, final double shop_se_price,
                             final int supp_id, final int id) {
        new SAsyncTask<Void, Void, ReturnInfo>(this, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {

                return ComModel2.mealJoinShopCart(context, p_code, shop_num, p_seq, catJson, p_type, postage,
                        shop_price, shop_se_price, supp_id, id);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // if (img == null) {
                // img = new ImageView(context);
                // img.setLayoutParams(new LayoutParams(100, 100));
                // }
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                if (null == e) {
                    SharedPreferencesUtil.saveBooleanData(context, "undo_view4", true);// 加入购物车时候
                    // 主界面的购物车数量设置为重新显示
                    // ToastUtil.showShortText(context, result.getMessage());
                    int cartCount = 0;
                    if (result.getIsCart() == 0) {
                        cartCount = Integer.parseInt(mealMap.get("cart_count").toString()) + 1;
                    } else {
                        cartCount = Integer.parseInt(mealMap.get("cart_count").toString());
                    }

                    ShopCartDao dao = new ShopCartDao(MealShopDetailsActivity.this);

                    List<ShopCart> list2 = dao.findAll();
                    boolean p_flag_dao2 = false;
                    int p_dao_num = 0;
                    if (list2 != null) {
                        for (int i = 0; i < list2.size(); i++) {
                            if (p_seq.equals(list2.get(i).getP_s_t_id())) {
                                p_flag_dao2 = true;
                                p_dao_num = list2.get(i).getShop_num();
                                p_id_dao = list2.get(i).getId();
                                break;
                            }
                        }
                    }

                    LogYiFu.e("zzqdao", "1" + list.get(0).getShop_name() + "___" + list.get(0).getShop_name());
                    mealMap.put("cart_count", cartCount);
                    setAnim();

                    first_meal = true;
                    // queryShopMeal();
                } else {
                    // clickFlag = true;
                    rootView.removeView(pointRoot);
                    ShopCartDao dao = new ShopCartDao(context);
                    final List<ShopCart> list = dao.findAll();
                    int p_dao_num = 0;
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            if (p_seq.equals(list.get(i).getP_s_t_id())) {
                                p_dao_num = list.get(i).getShop_num();
                                break;
                            }
                        }
                    }
                    dao.p_modify(p_seq, p_dao_num - shop_num);
                }

                super.onPostExecute(context, result, e);
            }

        }.execute();
    }

    /****
     * 弹出底部对话框(1元购买)
     *
     */
    private void showPopWindowOneBuy() {
        if (shop != null && !this.isFinishing()) {
            final NewMealShopDetailsDialog dlg;

            dlg = new NewMealShopDetailsDialog(this, true, R.style.DialogStyle, width, height, shop, false, "-1", "-1", "1");
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
            dlg.callBackShopCart = new NewMealShopDetailsDialog.OnCallBackShopCart() {

                @Override
                public void callBackChoose(boolean isOnebuy, String color_size, String shop_code, int type, String size, String color, double price, int shop_num,
                                           int stock_type_id, int stock, String pic, int supp_id, double kickback, int original_price,
                                           View v) {
                    dlg.dismiss();
                    // clickFlag = true;
                    entity = new GoodsEntity(pic, size, color, shop_num, stock_type_id, stock, supp_id, stock_type_id,
                            price, kickback, original_price);


                    // Intent intent = new
                    // Intent(ShopDetailsActivity.this,
                    // SubmitOrderActivity.class);
                    Intent intent;
                    if (isOnebuy) {
                        intent = new Intent(MealShopDetailsActivity.this, NewMealOneBuySubmitShopActivty.class);// 1元购购买

                    } else {
                        intent = new Intent(MealShopDetailsActivity.this, NewMealSubmitOrderActivity.class);// 购买

                    }
                    Bundle bundle = new Bundle();
                    intent.putExtra("shop_num", shop_num);
                    intent.putExtra("buy_shop_code", shop_code);
                    intent.putExtra("shop_pic", shop.getDef_pic());
                    intent.putExtra("color_size", color_size);
                    intent.putExtra("onePrice", shop.getApp_shop_group_price());


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
                    if (isSignActiveShop) {
//                        bundle.putBoolean("isSignActiveShop", true);
                    }
//							if(isSignActiveShop&&groupFlag!=0) {
//								bundle.putBoolean("isSignActiveShop", true);
//							}else{
//								bundle.putBoolean("isSignActiveShop", false);
//							}
                    bundle.putBoolean("mIsTwoGroup", mIsTwoGroup);
                    bundle.putString("rollCode", "" + rollCode);
                    intent.putExtras(bundle);
                    intent.putExtra("groupFlag", groupFlag);
                    boolean flag2 = false;
                    int position = 0;
                    if (isSignActiveShop && SignGroupShopActivity.listClick.size() == 0 && groupFlag != 0) {//拼团购选择第一件商品
                        setResult(SignGroupShopActivity.RESULT_DETAILS, intent);
                        MealShopDetailsActivity.this.finish();
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
                            MealShopDetailsActivity.instance.finish();
                            intent.putExtra("mSignGroupsPrice", Double.parseDouble(mSignGroupsPrice));
                        } else {
//									listGoods.add(shopCart);
                        }
                        if (SubmitMultiShopActivty.instance != null) {
                            SubmitMultiShopActivty.instance.finish();
                        }
                        startActivity(intent);
                    }
                }


            };
        }
    }


    private void showOnlyBuyDialog() {//0为单独购买
        if (shop != null && !this.isFinishing()) {
            final NewMealShopDetailsDialog dlg;

            dlg = new NewMealShopDetailsDialog(this, false, R.style.DialogStyle, width, height, shop, false, "-1", "-1", "1");
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
            dlg.callBackShopCart = new NewMealShopDetailsDialog.OnCallBackShopCart() {

                @Override
                public void callBackChoose(boolean isOnebuy, String color_size, String shop_code, int type, String size, String color, double price, int shop_num,
                                           int stock_type_id, int stock, String pic, int supp_id, double kickback, int original_price,
                                           View v) {
                    dlg.dismiss();
                    // clickFlag = true;
                    entity = new GoodsEntity(pic, size, color, shop_num, stock_type_id, stock, supp_id, stock_type_id,
                            price, kickback, original_price);


                    Intent intent = new Intent(MealShopDetailsActivity.this, NewMealSubmitOrderActivity.class);// 购买
                    Bundle bundle = new Bundle();
                    intent.putExtra("shop_num", shop_num);
                    intent.putExtra("buy_shop_code", shop_code);
                    intent.putExtra("shop_pic", shop.getDef_pic());
                    intent.putExtra("color_size", color_size);


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
                    if (isSignActiveShop) {
//                        bundle.putBoolean("isSignActiveShop", true);
                    }
//							if(isSignActiveShop&&groupFlag!=0) {
//								bundle.putBoolean("isSignActiveShop", true);
//							}else{
//								bundle.putBoolean("isSignActiveShop", false);
//							}
                    bundle.putBoolean("mIsTwoGroup", mIsTwoGroup);
                    bundle.putString("rollCode", "" + rollCode);
                    intent.putExtras(bundle);
                    intent.putExtra("groupFlag", groupFlag);
                    boolean flag2 = false;
                    int position = 0;
                    if (isSignActiveShop && SignGroupShopActivity.listClick.size() == 0 && groupFlag != 0) {//拼团购选择第一件商品
                        setResult(SignGroupShopActivity.RESULT_DETAILS, intent);
                        MealShopDetailsActivity.this.finish();
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
                            MealShopDetailsActivity.instance.finish();
                            intent.putExtra("mSignGroupsPrice", Double.parseDouble(mSignGroupsPrice));
                        } else {
//									listGoods.add(shopCart);
                        }
                        if (SubmitMultiShopActivty.instance != null) {
                            SubmitMultiShopActivty.instance.finish();
                        }
                        startActivity(intent);
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

                        return ComModel2.getShopCartData(MealShopDetailsActivity.this, 1);

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
                            UserInfo user = YCache.getCacheUser(MealShopDetailsActivity.this);
                            Store store = YCache.getCacheStore(MealShopDetailsActivity.this);
                            boolean hh = dao.add(shop.getShop_code(), size, color,
                                    Integer.parseInt(String.valueOf(shop_num)),
                                    Integer.parseInt(String.valueOf(stock_type_id)), pic, user.getUser_id(),
                                    shop.getShop_name(), store.getS_code(), "" + shop.getShop_price(),
                                    "" + shop.getShop_se_price(), supplyId + "", "" + kickback, "" + original_price, 0,
                                    null, null, null, null, null, id, null, 0, mSupp_label);
                            // 普通商品过期时间
                            // SharedPreferencesUtil.saveStringData(context,
                            // Pref.SHOPCART_COMMON_TIME,
                            // new Date().getTime() + 30 * 1000 * 60 + "");

                            // setAnim();

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
                UserInfo user = YCache.getCacheUser(MealShopDetailsActivity.this);
                Store store = YCache.getCacheStore(MealShopDetailsActivity.this);

                return ComModel.joinShopCart(MealShopDetailsActivity.this, params[0], params[1], params[2], params[3],
                        params[4], "" + user.getUser_id(), YCache.getCacheToken(MealShopDetailsActivity.this), realPrice,
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
                    String qiandao_time = SharedPreferencesUtil.getStringData(MealShopDetailsActivity.this,
                            "qiandao_time" + YCache.getCacheUser(MealShopDetailsActivity.this).getUser_id(), "");
                    String qiandao_num = SharedPreferencesUtil.getStringData(MealShopDetailsActivity.this,
                            "qiandao_num" + YCache.getCacheUser(MealShopDetailsActivity.this).getUser_id(), "0");
                    // ShopCartDao dao=new
                    // ShopCartDao(ShopDetailsActivity.this);
                    int now_num = Integer.parseInt(qiandao_num) + shop_num;
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String now_time = sdf.format(date);
                    if (qiandao_time.equals(now_time)) {
                        SharedPreferencesUtil.saveStringData(MealShopDetailsActivity.this,
                                "qiandao_num" + YCache.getCacheUser(MealShopDetailsActivity.this).getUser_id(),
                                "" + now_num);
                        if (now_num >= needValue) {
                            sign_shopcart(true, 0);
                            SharedPreferencesUtil.saveStringData(MealShopDetailsActivity.this,
                                    "qiandao_time" + YCache.getCacheUser(MealShopDetailsActivity.this).getUser_id(), "-1");
                        } else {
                            // if(signNumber>1||jiangliType==3){
                            ToastUtil.showShortText(context, "再加" + (needValue - now_num) + "件商品可完成任务喔~");
                            // }else{
                            // ToastUtil.showShortText(context, "加入成功奖励"+new
                            // java.text.DecimalFormat("#0.00").format((1/signNumber)*jiangliValue)+mNotice+"，还有"+(needValue-now_num)+"次机会喔~");
                            // sign_shopcart(false,needValue-now_num);
                            // }
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
        // mType = 1;
        // index1 = 1;
        // // mIndex = v.getId();
        // position = v.getId();
        // initData(v.getId(), index1 + "");
        mType = 1;
        index = 1;
        titleCheck = v.getId();
        initData(v.getId(), index + "");
        if (isMeal || "SignShopDetail".equals(signShopDetail)) {
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
                        Toast.makeText(MealShopDetailsActivity.this, "已经到底了", Toast.LENGTH_SHORT).show();
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
            if (isMeal || isMembers || "SignShopDetail".equals(signShopDetail)) {
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
                LoadingDialog.show(MealShopDetailsActivity.this);
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
        // refreshCouldIndicator(activity, null, null);
        // translateAnimationX.setDuration(800);
        // translateAnimationY.setDuration(400);
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
        if (isMeal) {
            count = dao.queryCartSpecialCount(context);
        } else {
            count = dao.queryCartCommonCount(context);
        }
        if (/* cartCount */count > 0) {
            count = count > 99 ? 99 : count;
            tv_cart_count.setText(count + "");
            tv_cart_count2.setText(count + "");
        } else {

        }

        Long sTime = new Date().getTime();// 系统当前时间

        Long sDeadline = isMeal
                ? Long.valueOf(SharedPreferencesUtil.getStringData(context, Pref.SHOPCART_MEAL_TIME, "0"))
                : Long.valueOf(SharedPreferencesUtil.getStringData(context, Pref.SHOPCART_COMMON_TIME, "0"));// 商品过期时间

        if (sDeadline == 0) {// 表示还没有存到本地
            if (isMeal) {
                SharedPreferencesUtil.saveStringData(context, Pref.SHOPCART_MEAL_TIME, sTime + 30 * 1000 * 60 + "");
            } else {
                SharedPreferencesUtil.saveStringData(context, Pref.SHOPCART_COMMON_TIME, sTime + 30 * 1000 * 60 + "");
            }
            sDeadline = sTime + 30 * 60 * 1000;
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

    // public void getSupplyExplainDialog(String title, String content1, String
    // content2) {
    // final Dialog explainDialog = new Dialog(ShopDetailsActivity.this,
    // R.style.invate_dialog_style);
    // View view = View.inflate(ShopDetailsActivity.this,
    // R.layout.dialog_shop_details_supply_explian, null);
    // TextView mTitle = (TextView)
    // view.findViewById(R.id.dialog_details_title);
    // mTitle.setText(title + "制造商");
    // TextView mContent = (TextView)
    // view.findViewById(R.id.dialog_details_content);
    // mContent.setText(content1);
    // TextView mExplain = (TextView)
    // view.findViewById(R.id.dialog_details_explain);
    // mExplain.setText(content2);
    // TextView mClose = (TextView) view.findViewById(R.id.dialog_details_know);
    // mClose.setOnClickListener(new OnClickListener() {
    //
    // @Override
    // public void onClick(View v) {
    // explainDialog.dismiss();
    // }
    // });
    // explainDialog.setContentView(view);
    // explainDialog.setCancelable(true);
    // explainDialog.setCanceledOnTouchOutside(false);
    // explainDialog.show();
    // }

    public void getSignJoinShopCartDialog(Double jiangliValue) {
        final Dialog signDialog = new Dialog(MealShopDetailsActivity.this, R.style.invate_dialog_style);
        View view = View.inflate(MealShopDetailsActivity.this, R.layout.dialog_sign_join_shopcart, null);
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


                Intent intent = new Intent(MealShopDetailsActivity.this, ShopCartNewNewActivity.class);
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

                SharedPreferencesUtil.saveStringData(MealShopDetailsActivity.this, "commonactivityfrom", "sign");

                Intent intent = new Intent(MealShopDetailsActivity.this, CommonActivity.class);
                intent.putExtra("isTastComplete", true);
                startActivity(intent);
                ((Activity) MealShopDetailsActivity.this).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

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
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) MealShopDetailsActivity.this, 0) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel2.getSignIn(MealShopDetailsActivity.this, false, false,
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
                        SharedPreferencesUtil.saveStringData(MealShopDetailsActivity.this,
                                "qiandao_time" + YCache.getCacheUser(MealShopDetailsActivity.this).getUser_id(), "-1");
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
     * 抵扣说明弹窗NEW
     */
//    public void getDiKouDialogNew() {
//        LayoutInflater mInflater = LayoutInflater.from(ShopDetailsActivity.this);
//        final Dialog deleteDialog = new Dialog(ShopDetailsActivity.this, R.style.invate_dialog_style);
//        View view = mInflater.inflate(R.layout.dialog_dikou_explian_new, null);
//        ImageView iv_close = (ImageView) view.findViewById(R.id.iv_close);
//        iv_close.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                deleteDialog.dismiss();
//            }
//        });
//        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
//        btn_cancel.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                deleteDialog.dismiss();
//            }
//        });
//        deleteDialog.setCanceledOnTouchOutside(false);
//        deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT));
//
//        ToastUtil.showDialog(deleteDialog);
////        deleteDialog.show();
//
//    }


    /**
     * 抵扣说明弹窗
     */
    public void getDiKouDialog() {
        LayoutInflater mInflater = LayoutInflater.from(MealShopDetailsActivity.this);
        final Dialog deleteDialog = new Dialog(MealShopDetailsActivity.this, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_dikou_explian, null);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


//				SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, "commonactivityfrom", "sign");
//				ShopDetailsActivity.this.startActivity(new Intent(ShopDetailsActivity.this, CommonActivity.class));


                if (GuideActivity.hasSign) {
                    // 跳至赚钱
                    SharedPreferencesUtil.saveStringData(MealShopDetailsActivity.this, "commonactivityfrom", "sign");
                    context.startActivity(new Intent(MealShopDetailsActivity.this, CommonActivity.class));
                } else {
                    // 跳至首页
                    Intent intent2 = new Intent((Activity) MealShopDetailsActivity.this, MainMenuActivity.class);
                    intent2.putExtra("toHome", "toHome");
                    context.startActivity(intent2);
                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                }


                deleteDialog.dismiss();
            }
        });
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });
        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.addContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        deleteDialog.show();

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
//			Calendar c = Calendar.getInstance();
//			int day = c.get(Calendar.DAY_OF_MONTH);
//			if (!("" + YCache.getCacheToken(ShopDetailsActivity.this) + day+"true").equals(SharedPreferencesUtil.getStringData(ShopDetailsActivity.this, YConstance.Pref.DAY_BUY_IS_SHOW, ""))) {
            tv_buy_now.setText("0元购全返");
            tv_buy_now.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_buy_now.setTextSize(16);
//			}else{
//				tv_buy_now.setText("购买即赢千元大奖");
//			}
        }
//		tv_buy_now.setText("购买即赢千元大奖");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String shopDetailsBuyTips = SharedPreferencesUtil.getStringData(context, Pref.SHOPDETAILSBUYTIPS, "0");
        long shopDetailsBuyTipsTimes = Long.valueOf(shopDetailsBuyTips);
        if ("0".equals(shopDetailsBuyTips) || !df.format(new Date()).equals(df.format(new Date(shopDetailsBuyTipsTimes)))) {
            shopDetailsBuyTipsDialog();
            SharedPreferencesUtil.saveStringData(context, Pref.SHOPDETAILSBUYTIPS, System.currentTimeMillis() + "");
        }
//		if ("0".equals(shopDetailsBuyTips) || !DateUtil.isSameWeek(new Date(),new Date(shopDetailsBuyTipsTimes))) {
//            shopDetailsBuyTipsDialog();
//            SharedPreferencesUtil.saveStringData(context, Pref.SHOPDETAILSBUYTIPS, System.currentTimeMillis() + "");
//       }

    }

    public void getGroupInitData() {
        new SAsyncTask<String, Void, HashMap<String, String>>(MealShopDetailsActivity.this, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                return ComModel2.queryInitData(MealShopDetailsActivity.this);
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
//		boolean isMad= SharedPreferencesUtil.getBooleanData(this, Pref.ISMADMONDAY, false);
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