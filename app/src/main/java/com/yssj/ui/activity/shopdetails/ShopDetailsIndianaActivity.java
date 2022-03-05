package com.yssj.ui.activity.shopdetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.IndianaNormalTopClickView;
import com.yssj.custom.view.IndianaNormalTopClickView.OnCheckedLintener;
import com.yssj.custom.view.IndianaPayDialog;
import com.yssj.custom.view.ItemView;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.MyPopupwindow;
import com.yssj.custom.view.MyPopupwindow.ShopDetailsGetShare;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.custom.view.ScaleImageView;
import com.yssj.custom.view.ShowHoriontalView;
import com.yssj.custom.view.ShowHoriontalView.onClickLintener;
import com.yssj.custom.view.ToLoginDialog;
import com.yssj.data.YDBHelper;
import com.yssj.entity.GoodsEntity;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.Shop;
import com.yssj.entity.ShopComment;
import com.yssj.entity.ShopOption;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelZ;
import com.yssj.ui.activity.ShopImageActivity;
import com.yssj.ui.activity.SnatchActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.PublicToastDialog;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.TongjiShareCount;
import com.yssj.utils.WXminiAppUtil;
import com.yssj.utils.YCache;
import com.yssj.utils.sqlite.ShopCartDao;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

//import com.yssj.ui.activity.LeftFragment;

/***
 * 签到夺宝商品展示
 *
 * @author Administrator
 *
 */
public class ShopDetailsIndianaActivity extends BasicActivity
        implements OnClickListener, onClickLintener, OnCheckedLintener, ShopDetailsGetShare, IndianaPayDialog.IndianaDialogInterface {
    // TODO:mine
    private String TAG = "ShopDetailsIndianaActivity";
    private LinearLayout rlBottom;// 控制联系客服与立即参与的显示与隐藏
    private long recLen = 1000 * 60 * 60;// 倒计时长
    private TextView mHeadCountTime;// 倒计时显示
    Timer timer = new Timer();
    private List<HashMap<String, Object>> mListTakeRecord;
    private HashMap<String, Object> mListTakeRecordNew;
    private ImageView mIvRule;
    //    private LinearLayout mLlRuleContainer;
    private int mIndianaMoney;// 判断几元夺宝
    private String mBaoyouTiao;// 用来跳转到包邮
    private int mHeadPicHeight = 0;// 头部图片高度
    private String mShop_code;// 商品编号
    private int mOstatus;// 奖品状态
    private long mOtime;// 开奖时间、
    private String mIn_code;// 中奖号码
    private String mWinnerName;// 中奖者名字、
    private String mWinnerHeadPic;// 中奖者头像
    public int my_num;// 我的参与次数
    private int order_status;// 判断订单状态：1待付款2代发货3待收货4待评价5已评价(可追加评价)6已完结7延长收货9取消订单
    private List<String> codes = new ArrayList<String>();// 我的参与号码
    private String in_uid;// 得到中奖用户id
    private String virtual_num = "";// 参与人数
    private String issue_code;// 期号


    public int needCount;//开奖剩余人数

    // TODO:mine

    private int width, height, heights;
    private Shop shop;
    private ImageView img_fenx;
    private TextView tvRecord;
    private TextView tvOldShow;
    public boolean firstJoin;//是否是第一次参与夺宝

    public static String MealType;
    private LinearLayout img_back;
    private ImageView img_cart;
    private RelativeLayout lin_contact, rrr;
    private int mNeedPeoplenum;
    private int mXuNiPeople;
    private SAsyncTask<String, Void, HashMap<String, Object>> aa;
    private SAsyncTask<Void, Void, HashMap<String, Object>> bb;
    private SAsyncTask<Void, Void, HashMap<String, Object>> cc;

    private LinkedList<HashMap<String, Object>> dataList;

    private String[] images;// 普通商品图片

    private String[] imageTag1, imageTag2, imageTag3;// 套餐图片

    private List<HashMap<String, String>> listTitle;

    private int check = 0;

    private LinearLayout rlTop;

    private MyPopupwindow myPopupwindow;

    private TextView tv_cart_count;

    private Context context;

    private StickyListHeadersListView mListView;

    // protected LeftFragment mFrag;

    private MyAdapter adapter;

    private View headerView;

    public static ShopDetailsIndianaActivity instance;
    private static boolean isShow;

    private String signShopDetail;// 判断是值为 "SignShopDetail" 从签到跳转到商品详情页面
    private String signValue;// 签到 商品编号
    private static int isPause = 0;

    private Intent qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
    private Intent wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
    private String mIssue_code = "";

    private Runnable r;
    private TextView mDetailsSubmit;
    private double mShop_price;
    private double mShop_se_price;
    private ImageView mHeadPic;
    private Context mContext;

    private String shopPicUrl = "";

    @Override
    protected void onPause() {
        super.onPause();
        // MobclickAgent.onPageEnd("ShopDetailsActivity");
        // MobclickAgent.onPause(this);
        if (newHandler != null) {
            newHandler.removeCallbacks(r);
        }
        isPause = 1;
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);

        if (arg1 == RESULT_OK) {
            if (arg0 == 1080) {
                if (!"SignShopDetail".equals(signShopDetail)) {
                    int count = arg2.getIntExtra("count", shop.getCart_count());
                    shop.setCart_count(count);
                    tv_cart_count.setVisibility(View.VISIBLE);

                    // tv_cart_count.setText(shop.getCart_count() + "");
                }
            }

        } else if (arg0 == 235) {
            mListView.removeHeaderView(headerView);
            if ("SignShopDetail".equals(signShopDetail)) {

            } else {
                queryShopDetails();
            }
        } else if (arg0 == 15502) {
            if (YJApplication.instance.isLoginSucess()) {
                // if ("SignShopDetail".equals(signShopDetail)) {
                // getPshareShop();
                // } else {
                share(shop.getShop_code(), shop);
                // }
            }
        }
    }

    private List<Shop> list;// 套餐内商品集合


    private class MyAdapter extends BaseAdapter implements StickyListHeadersAdapter {
        int ruleCount = 1;
        int recordCount = 4;
        double totalHeight;
        double ruleHight;
        double recordHight;

        // private int i;

        @Override
        public int getCount() {
            int count = 0;
            if (check == 0) {// 详情
                count += images.length;

                if (dataList != null) {
                    count += dataList.size() % 2 == 0 ? dataList.size() / 2 : dataList.size() / 2 + 1;
                }
                // MyLogYiFu.e(TAG, count+"详情");
            } else if (check == 1) {// 参与记录
                if (true) {
                    // count += recordCount;
                    // MyLogYiFu.e(TAG, count + "参与记录");
                    if (mListTakeRecord != null && mListTakeRecord.size() > 0) {
                        count += mListTakeRecord.size();
                    } else {
                        count++;
                    }
                } else {
                    count = 1;
                }
            } else {// 夺宝规则
                count += ruleCount;
                // MyLogYiFu.e(TAG, count+"夺宝规则");
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

        @SuppressWarnings("unused")
        @Override
        public View getView(int position, View v, ViewGroup arg2) {
            final ItemViewHolder vh;
            if (v == null) {
                v = LayoutInflater.from(ShopDetailsIndianaActivity.this).inflate(R.layout.indiana_new_shop_item, arg2,
                        false);
                vh = new ItemViewHolder();
                v.findViewById(R.id.lln).setBackgroundColor(Color.WHITE);
                vh.mBai = (ImageView) v.findViewById(R.id.indiana_bai);


                // 夺宝规则
                vh.mLlRule = (LinearLayout) v.findViewById(R.id.indiana_ll_rule);
                mIvRule = (ImageView) v.findViewById(R.id.indiana_iv_rule);
                vh.mLlRuleContainer = (LinearLayout) v.findViewById(R.id.indiana_ll_rule_container);
                // 参与记录
                vh.img_herad_one = (RoundImageButton) v.findViewById(R.id.img_herad_one);
                vh.img_herad_two = (RoundImageButton) v.findViewById(R.id.img_herad_two);
                vh.img_herad_three = (RoundImageButton) v.findViewById(R.id.img_herad_three);
                vh.img_herad_four = (RoundImageButton) v.findViewById(R.id.img_herad_four);
                vh.img_herad_five = (RoundImageButton) v.findViewById(R.id.img_herad_five);
                vh.tv_num_people = (TextView) v.findViewById(R.id.tv_num_people);
                vh.img_heard_more = (ImageView) v.findViewById(R.id.img_heard_more);
                // if ("".equals(in_uid) || in_uid == null ||
                // "null".equals(in_uid)) {
                vh.tv_num_people.setText("" + (mNeedPeoplenum + mXuNiPeople));
                if (mNeedPeoplenum + mXuNiPeople <= 5) {
                    vh.img_heard_more.setVisibility(View.GONE);
                } else {
                    vh.img_heard_more.setVisibility(View.VISIBLE);
                }
                // } else {
                // vh.tv_num_people.setText("" + virtual_num);1
                // }
                vh.img_herad_one.getLayoutParams().height = (width - DP2SPUtil.dp2px(context, 125)) / 5;
                vh.img_herad_two.getLayoutParams().height = (width - DP2SPUtil.dp2px(context, 125)) / 5;
                vh.img_herad_three.getLayoutParams().height = (width - DP2SPUtil.dp2px(context, 125)) / 5;
                vh.img_herad_four.getLayoutParams().height = (width - DP2SPUtil.dp2px(context, 125)) / 5;
                vh.img_herad_five.getLayoutParams().height = (width - DP2SPUtil.dp2px(context, 125)) / 5;
                vh.mListRecord = (LinearLayout) v.findViewById(R.id.indiana_listview_record);
                vh.mListRecordNew = (LinearLayout) v.findViewById(R.id.indiana_listview_record_new);
                vh.mTakeHead = (RelativeLayout) v.findViewById(R.id.indiana_taake_record);
                vh.tvStartTime = (TextView) v.findViewById(R.id.indiana_taake_minute);
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyy.MM.dd  HH:mm:ss");
                Date startTmie = new Date(shop.getActive_start_time());
                vh.tvStartTime.setText("" + sdf2.format(startTmie) + "开始");
                vh.mView = v.findViewById(R.id.indiana_taake_line);
                vh.headPicture = (RoundImageButton) v.findViewById(R.id.item_indiana_head_headpicture);
                vh.tvName = (TextView) v.findViewById(R.id.item_indiana_head_name);
                vh.tvTime = (TextView) v.findViewById(R.id.item_indiana_head_time);
                vh.tvCount = (TextView) v.findViewById(R.id.item_indiana_head_countpeople);
                vh.llBottomShow = (LinearLayout) v.findViewById(R.id.ll_bottom_show);
                // 详情
                vh.mLlMessage = (LinearLayout) v.findViewById(R.id.item_ll_message);
                vh.imageGroup = v.findViewById(R.id.image_group);
                vh.shopItem = v.findViewById(R.id.item_position);
                vh.image = (ImageView) v.findViewById(R.id.image_position);
                vh.imageGroup.getLayoutParams().height = width * 9 / 6;
                vh.left = (ItemView) v.findViewById(R.id.left);
                vh.left.setHeight(width / 2 * 9 / 6);
                vh.right = (ItemView) v.findViewById(R.id.right);
                vh.right.setHeight(width / 2 * 9 / 6);
                // 得到手机屏幕的高度
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                totalHeight = dm.heightPixels;
                v.setTag(vh);
            } else {
                vh = (ItemViewHolder) v.getTag();
            }

            if (check == 0) {// 详情
                vh.mView.setVisibility(View.GONE);
                vh.mTakeHead.setVisibility(View.GONE);
                vh.mBai.setVisibility(View.GONE);
                vh.mLlRule.setVisibility(View.GONE);
                vh.mLlMessage.setVisibility(View.VISIBLE);
                vh.mListRecord.setVisibility(View.GONE);
                vh.mListRecordNew.setVisibility(View.GONE);
                if (position < images.length) {
                    vh.imageGroup.setVisibility(View.VISIBLE);
                    vh.shopItem.setVisibility(View.GONE);
                    vh.image.setTag(shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/"
                            + images[position] + "!450");
                    // SetImageLoader.initImageLoader(null, vh.image,
                    // shop.getShop_code().substring(1, 4) + "/" +
                    // shop.getShop_code() + "/" + images[position],
                    // "!450");

                    PicassoUtils.initImage(context, shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code()
                            + "/" + images[position] + "!450", vh.image);

                    final int x = position;
                    vh.image.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ShopDetailsIndianaActivity.this, ShopImageActivity.class);
                            intent.putExtra("url",
                                    shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/" + images[x]);
                            // intent.putExtra("shop", shop);
                            // intent.putExtra("isMembers", isMembers);
                            intent.putExtra("signType", mIndianaMoney);
                            intent.putExtra("isIndiana", true);
                            intent.putExtra("code", code);
                            intent.putExtra("shop", shop);
                            intent.putExtra("ostatus", mOstatus);
                            intent.putExtra("my_num", my_num);
                            intent.putExtra("firstJoin", firstJoin);
                            intent.putExtra("needCount", needCount);
                            int needPeopleCount = shop.getActive_people_num() - mNeedPeoplenum - mXuNiPeople < 0 ? 0
                                    : shop.getActive_people_num() - mNeedPeoplenum - mXuNiPeople;
                            intent.putExtra("need_people", needPeopleCount);
                            startActivityForResult(intent, 1080);
                        }
                    });

                    return v;
                }
                position = position - images.length;

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
            } else if (check == 1) {// 参与记录
                // new
                if (position == 0) {
                    vh.mTakeHead.setVisibility(View.VISIBLE);
                    vh.mView.setVisibility(View.VISIBLE);
                } else {
                    vh.mTakeHead.setVisibility(View.GONE);
                    vh.mView.setVisibility(View.GONE);
                }
                vh.mBai.setVisibility(View.GONE);
                vh.mLlRule.setVisibility(View.GONE);
                vh.mLlMessage.setVisibility(View.GONE);
                if (true) {
                    vh.mListRecord.setVisibility(View.VISIBLE);
                    vh.mListRecordNew.setVisibility(View.GONE);
                    if (mListTakeRecord != null && mListTakeRecord.size() > 0 && mListTakeRecord.size() - 1 == position && page == 30) {//最后一条时，显示最多只显示最近三百条
                        vh.llBottomShow.setVisibility(View.VISIBLE);
                    } else {
                        vh.llBottomShow.setVisibility(View.GONE);
                    }
                    UserInfo info = YCache.getCacheUserSafe(ShopDetailsIndianaActivity.this.context);
                    // SetImageLoader.initImageLoader(ShopDetailsIndianaActivity.this.context,
                    // vh.headPicture,
                    // info.getPic(), "");

                    PicassoUtils.initImage(context, info.getPic(), vh.headPicture);

                    // recordHight = vh.mListRecord.getLayoutParams().height;
                    RoundImageButton headPicture;
                    TextView tvName;
                    TextView tvTime;
                    TextView tvCount;
                    // 填充数据
                    if (mListTakeRecord != null && mListTakeRecord.size() > 0) {
                        vh.mListRecord.setVisibility(View.VISIBLE);

                        // SetImageLoader.initImageLoader(ShopDetailsIndianaActivity.this.context,
                        // vh.headPicture,
                        // "" + mListTakeRecord.get(position).get("uhead"), "");

                        PicassoUtils.initImage(context, "" + mListTakeRecord.get(position).get("uhead"),
                                vh.headPicture);

                        vh.tvName.setText("" + mListTakeRecord.get(position).get("nickname"));
                        vh.tvCount.setText("" + mListTakeRecord.get(position).get("num"));
                        SimpleDateFormat sdf = new SimpleDateFormat("yyy.MM.dd HH:mm");
                        Date date = new Date((Long) mListTakeRecord.get(position).get("atime"));
                        vh.tvTime.setText("" + sdf.format(date));

                        ViewTreeObserver vto = vh.mListRecord.getViewTreeObserver();
                        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                vh.mListRecord.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                recordHight = vh.mListRecord.getHeight();
                                vh.mListRecord.getWidth();
                            }
                        });
                        LogYiFu.e(TAG, recordHight + "!!!!");
                        if (mListTakeRecord.size() * recordHight + DP2SPUtil.dp2px(context, 120) - totalHeight < 0
                                && (position == mListTakeRecord.size() - 1)) {
                            vh.mBai.getLayoutParams().height = (int) (totalHeight - mListTakeRecord.size() * recordHight
                                    - DP2SPUtil.dp2px(context, 120));
                            vh.mBai.setVisibility(View.VISIBLE);

                        } else if (position == mListTakeRecord.size() - 1) {
                            vh.mBai.setVisibility(View.VISIBLE);
                            vh.mBai.getLayoutParams().height = DP2SPUtil.dp2px(context, 50);
                        } else {
                            vh.mBai.setVisibility(View.GONE);
                        }
                    } else {
                        vh.mListRecord.setVisibility(View.GONE);
                        vh.mBai.getLayoutParams().height = (int) (totalHeight - DP2SPUtil.dp2px(context, 120));
                        vh.mBai.setVisibility(View.VISIBLE);
                    }
                    // new
                } else {
                    if (mListTakeRecordNew != null && !("".equals(mListTakeRecordNew))) {
                        String head_pic = (String) mListTakeRecordNew.get("head_pic");
                        if (!("".equals(head_pic)) && head_pic != null) {
                            String[] str = head_pic.split(",");
                            for (int i = 0; i < str.length; i++) {
                                if (Integer.parseInt((String) mListTakeRecordNew.get("flag")) == 0) {
                                    if (i == 0) {
                                        // SetImageLoader.initImageLoader(ShopDetailsIndianaActivity.this.context,
                                        // vh.img_herad_one, "" + YUrl.imgurl +
                                        // str[i], "");
                                        PicassoUtils.initImage(context, str[i], vh.img_herad_one);

                                    } else if (i == 1) {
                                        // SetImageLoader.initImageLoader(ShopDetailsIndianaActivity.this.context,
                                        // vh.img_herad_two, "" + YUrl.imgurl +
                                        // str[i], "");
                                        PicassoUtils.initImage(context, str[i], vh.img_herad_two);
                                    } else if (i == 2) {
                                        // SetImageLoader.initImageLoader(ShopDetailsIndianaActivity.this.context,
                                        // vh.img_herad_three, "" + YUrl.imgurl
                                        // + str[i], "");
                                        PicassoUtils.initImage(context, str[i], vh.img_herad_three);
                                    } else if (i == 3) {
                                        // SetImageLoader.initImageLoader(ShopDetailsIndianaActivity.this.context,
                                        // vh.img_herad_four, "" + YUrl.imgurl +
                                        // str[i], "");
                                        PicassoUtils.initImage(context, str[i], vh.img_herad_four);
                                    } else if (i == 4) {
                                        // SetImageLoader.initImageLoader(ShopDetailsIndianaActivity.this.context,
                                        // vh.img_herad_five, "" + YUrl.imgurl +
                                        // str[i], "");
                                        PicassoUtils.initImage(context, str[i], vh.img_herad_five);
                                    }
                                } else {
                                    if (i == 0) {
                                        // SetImageLoader.initImageLoader(ShopDetailsIndianaActivity.this.context,
                                        // vh.img_herad_one, "" + str[i], "");

                                        PicassoUtils.initImage(context, "" + str[i], vh.img_herad_one);

                                    } else if (i == 1) {
                                        // SetImageLoader.initImageLoader(ShopDetailsIndianaActivity.this.context,
                                        // vh.img_herad_two, "" + str[i], "");

                                        PicassoUtils.initImage(context, "" + str[i], vh.img_herad_two);

                                    } else if (i == 2) {
                                        // SetImageLoader.initImageLoader(ShopDetailsIndianaActivity.this.context,
                                        // vh.img_herad_three, "" + str[i], "");

                                        PicassoUtils.initImage(context, "" + str[i], vh.img_herad_three);

                                    } else if (i == 3) {
                                        // SetImageLoader.initImageLoader(ShopDetailsIndianaActivity.this.context,
                                        // vh.img_herad_four, "" + str[i], "");

                                        PicassoUtils.initImage(context, "" + str[i], vh.img_herad_four);
                                    } else if (i == 4) {
                                        // SetImageLoader.initImageLoader(ShopDetailsIndianaActivity.this.context,
                                        // vh.img_herad_five, "" + str[i], "");
                                        PicassoUtils.initImage(context, "" + str[i], vh.img_herad_five);
                                    }
                                }
                            }
                        }
                    }
                    vh.mListRecord.setVisibility(View.GONE);
                    vh.mListRecordNew.setVisibility(View.VISIBLE);
                }

            } else {// 夺宝规则
                vh.mBai.setVisibility(View.GONE);
                vh.mView.setVisibility(View.GONE);
                vh.mTakeHead.setVisibility(View.GONE);
                vh.mLlRule.setVisibility(View.VISIBLE);
                vh.mLlMessage.setVisibility(View.GONE);
                vh.mListRecord.setVisibility(View.GONE);
                vh.mListRecordNew.setVisibility(View.GONE);


                // 添加数据
                // ShopDetailsIndianaActivity.this.context, vh.mIvRule,
                // "duobao/huodongguize.png", "");
//                getPicture();// 加载夺宝规则图片
                if (vh.mLlRuleContainer.getChildCount() <= 0) {
                    getIndianaRuleContent(vh.mLlRuleContainer);
                }
                ViewTreeObserver vto2 = vh.mLlRule.getViewTreeObserver();
                vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        vh.mLlRule.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        ruleHight = vh.mLlRule.getHeight();
                        vh.mLlRule.getWidth();
                    }
                });
                if (ruleHight + DP2SPUtil.dp2px(context, 120) - totalHeight < 0) {
                    vh.mBai.getLayoutParams().height = (int) (totalHeight - ruleHight - DP2SPUtil.dp2px(context, 120));
                    vh.mBai.setVisibility(View.VISIBLE);
                } else {
                    vh.mBai.setVisibility(View.GONE);
                }

            }
            return v;
        }

        @Override
        public View getHeaderView(int position, View view, ViewGroup parent) {
            HeaderViewHolder vh;
            if (view == null) {
                vh = new HeaderViewHolder();
                view = LayoutInflater.from(ShopDetailsIndianaActivity.this).inflate(R.layout.indiana_header_normal_item,
                        parent, false);
                vh.topOne = (IndianaNormalTopClickView) view.findViewById(R.id.top_one);
                vh.topOne.setText2();

                vh.topTwo = (ShowHoriontalView) view.findViewById(R.id.top_two);
                vh.topOne.setCheckLintener(ShopDetailsIndianaActivity.this);
                vh.topTwo.setOnClickLintener(ShopDetailsIndianaActivity.this);
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

            if (position < images.length) {
                vh.topOne.setVisibility(View.VISIBLE);
                vh.topOne.setIndex(check);
                // vh.title.setVisibility(View.GONE);
                vh.title.setText("商品介绍");
                vh.topTwo.setVisibility(View.GONE);
                isShopTitle = false;
                return view;
            }
            isShopTitle = true;
            vh.title.setVisibility(View.VISIBLE);
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
            if (position < images.length) {

                if (rlBottom.getVisibility() == View.GONE && isAnim == false) {
                    // rlBottom.setVisibility(View.VISIBLE);
                    // Animation animation = AnimationUtils.loadAnimation(
                    // ShopDetailsActivity.this,
                    // R.anim.shop_bottom_show);
                    // rlBottom.startAnimation(animation);
                    rlBottom.clearAnimation();
                    rlBottom.startAnimation(animationShow);
                }

                return 0;
            } else {
                if (rlBottom.getVisibility() == View.VISIBLE && isAnim == false) {
                    // Animation animation = AnimationUtils.loadAnimation(
                    // ShopDetailsActivity.this,
                    // R.anim.shop_bottom_gone);
                    // rlBottom.startAnimation(animation);
                    // rlBottom.setVisibility(View.GONE);
                    rlBottom.clearAnimation();
                    rlBottom.startAnimation(animationGone);
                }
                return 1;
            }

        }

    }

    private static class ItemViewHolder {
        ImageView mBai;
        LinearLayout mListRecord;// 参与记录
        LinearLayout mListRecordNew;// 参与记录接受假数据
        LinearLayout llBottomShow;//最多显示300条提示
        RelativeLayout mTakeHead;
        View mView;
        RoundImageButton headPicture;
        TextView tvName;
        TextView tvTime;
        TextView tvCount, tvStartTime;
        LinearLayout mLlMessage;// 详情
        View imageGroup;
        View shopItem;
        ItemView left;
        ItemView right;
        ImageView image;
        LinearLayout mLlRule;// 夺宝规则
        LinearLayout mLlRuleContainer;//规则文字容器

        RoundImageButton img_herad_one;
        RoundImageButton img_herad_two;
        RoundImageButton img_herad_three;
        RoundImageButton img_herad_four;
        RoundImageButton img_herad_five;
        TextView tv_num_people;// 参与人数
        ImageView img_heard_more;
    }

    private static class HeaderViewHolder {

        IndianaNormalTopClickView topOne;

        ShowHoriontalView topTwo;

        TextView title;
    }

    private Animation animationGone;
    private Animation animationShow;

    private boolean isMembers = false;// 是否是会员商品

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getActionBar().hide();
        AppManager.getAppManager().addDetailsActivity(this);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.indiana_activity_shop_details);
        shareWaitDialog = new PublicToastDialog(this, R.style.DialogStyle1, "");
        number_sold = getIntent().getStringExtra("number_sold"); // 取出是否抢完的值
        getWindownPixes();
        context = this;
        mContext = this;
        check = 0;
        initView();

        try {
            YDBHelper helper = new YDBHelper(this);
            String sql = "select * from sort_info where p_id = 0 and is_show = 1 order by sequence";
            listTitle = helper.query(sql);
            int a = 0;
            int b = 0;
            for (int i = 0; i < listTitle.size(); i++) {
                String sort_name = listTitle.get(i).get("sort_name");
                if ("上衣".equals(sort_name)) {
                    a = i;
                } else if ("外套".equals(sort_name)) {
                    b = i;
                }
            }

            HashMap<String, String> listTitle_temp1 = (HashMap<String, String>) listTitle.get(a);
            HashMap<String, String> listTitle_temp2 = (HashMap<String, String>) listTitle.get(b);
            if (b > a) {// 上衣在外套前面时就调换
                listTitle.remove(a);
                listTitle.add(a, listTitle_temp2);
                listTitle.remove(b);
                listTitle.add(b, listTitle_temp1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // initImageLoader();
        code = getIntent().getStringExtra("valueDuo");
        mShop_code = getIntent().getStringExtra("shop_code");
        mBaoyouTiao = getIntent().getStringExtra("valueBao");
        mIndianaMoney = getIntent().getIntExtra("signType", 2);
//        mIn_code = getIntent().getStringExtra("in_code");
//        in_uid = getIntent().getStringExtra("in_uid");
        mIssue_code = getIntent().getStringExtra("issue_code");
        virtual_num = getIntent().getStringExtra("virtual_num");
        issue_code = getIntent().getStringExtra("issue_code");
//        mWinnerName = getIntent().getStringExtra("in_name");
//        mWinnerHeadPic = getIntent().getStringExtra("in_head");
        isMembers = getIntent().getBooleanExtra("isMembers", false);
        signShopDetail = getIntent().getStringExtra("SignShopDetail");
        signValue = getIntent().getStringExtra("value");
        instance = this;
        if (!TextUtils.isEmpty(code) || !TextUtils.isEmpty(mShop_code)) {
            queryShopDetails();
        }
        // else {
        // finish();
        // }
        // }
        if (YJApplication.instance.isLoginSucess()) {
            // queryCartCount();
        } else {
            tv_cart_count.setVisibility(View.GONE);
        }
    }

    public static int shopTask = 0;
    public static int everyDayTask1_2 = 0;

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
        instance = null;
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

    private Handler newHandler;

    private void initView() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int widths = dm.widthPixels;
        mHeadPicHeight = widths - DP2SPUtil.dp2px(context, 77);
        mHeadPic = (ImageView) findViewById(R.id.to_duobao);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT); // , 1是可选写的
        lp.setMargins(mHeadPicHeight + 1, mHeadPicHeight + 7, 0, 0);
        mHeadPic.setLayoutParams(lp);

        mDetailsSubmit = (TextView) findViewById(R.id.indiana_details_submit);
        // mDetailsSubmit.setOnClickListener(this);
        rlBottom = (LinearLayout) findViewById(R.id.ray_bottom);
        // rlTop = (LinearLayout) findViewById(R.id.ray_top);
        // rlTop.setVisibility(View.VISIBLE);
        // rlTop.setBackgroundColor(Color.WHITE);

        img_cart = (ImageView) findViewById(R.id.img_cart);
        img_cart.setOnClickListener(this);

        rrr = (RelativeLayout) findViewById(R.id.rrr);
        // rrr.setBackgroundColor(Color.WHITE);

        findViewById(R.id.ray_bottom).setBackgroundColor(Color.WHITE);
        tv_cart_count = (TextView) findViewById(R.id.tv_cart_count);// 购物车计数
        if (YJApplication.instance.isLoginSucess()) {
            ShopCartDao dao = new ShopCartDao(context);
            if (dao.queryCartCount(context) > 0) {
                tv_cart_count.setVisibility(View.VISIBLE);
                // tv_cart_count.setText("" + dao.queryCartCount(context));

                int count = dao.queryCartCount(context);
                count = count > 99 ? 99 : count;
                tv_cart_count.setText("" + count);

            } else {
                tv_cart_count.setVisibility(View.GONE);
            }
        }
        lin_contact = (RelativeLayout) findViewById(R.id.lin_contact);
        lin_contact.setOnClickListener(this);

        img_fenx = (ImageView) findViewById(R.id.img_fenx);// 分享
        img_fenx.setOnClickListener(this);
        tvOldShow = (TextView) findViewById(R.id.tv_old_show);
        tvRecord = (TextView) findViewById(R.id.tv_record);
        tvOldShow.setOnClickListener(this);
        tvRecord.setOnClickListener(this);

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

        animationGone = AnimationUtils.loadAnimation(ShopDetailsIndianaActivity.this, R.anim.shop_bottom_gone);
        animationShow = AnimationUtils.loadAnimation(ShopDetailsIndianaActivity.this, R.anim.shop_bottom_show);

        animationGone.setAnimationListener(new AnimationListener() {

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
        animationShow.setAnimationListener(new AnimationListener() {

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

            ShopDetailsIndianaActivity.this.getSharedPreferences("YSSJ_yf", Context.MODE_PRIVATE).edit()
                    .putBoolean("isGoDetail", true).commit();
            if (YJApplication.instance.isLoginSucess()) {
                addScanDataTo((String) posMap.get("shop_code"));
            }
            Intent intent = new Intent(ShopDetailsIndianaActivity.this, ShopDetailsActivity.class);
            intent.putExtra("code", (String) posMap.get("shop_code"));
            // context.startActivity(intent);
            intent.putExtra("shopCarFragment", "shopCarFragment");

            startActivity(intent);
            // finish();
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        }
    }

    /*
     * 把浏览过的数据添加进数据库
     */
    private void addScanDataTo(final String shop_code) {
        new SAsyncTask<Void, Void, ReturnInfo>(ShopDetailsIndianaActivity.this) {

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

    private ToLoginDialog loginDialog;

    @Override
    public void onClick(View view) {
        if (YJApplication.instance.isLoginSucess() == false) {
            if (view.getId() == R.id.img_back) {
                onBackPressed();
                return;
            }
            if (loginDialog == null) {
                loginDialog = new ToLoginDialog(ShopDetailsIndianaActivity.this);
            }
            loginDialog.setRequestCode(235);
            loginDialog.show();
            return;
        }

        switch (view.getId()) {
            case R.id.lin_contact:

//                Intent intent = new Intent(this, KeFuActivity.class);
//                intent.putExtra("userId", SharedPreferencesUtil.getStringData(context, "kefuNB", "0"));
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("shop", shop);
//                if (null == shop) {
//                    ToastUtil.showShortText(context, "操作无效");
//                    return;
//                }
//                // intent.putExtra("ISMEMBERS", isMembers);
//                intent.putExtras(bundle);
//                intent.putExtra("isDuoBao", "isDuoBao");
//                startActivity(intent);

                WXminiAppUtil.jumpToWXmini(this);


                break;
            // TODO:
            case R.id.indiana_details_submit://立即参与
                TongJiUtils.yunYunTongJi("duobao", 1002, 10, ShopDetailsIndianaActivity.this);


                if (needCount <= 0) { //剩余参与人数为0
                    ToastUtil.showShortText(ShopDetailsIndianaActivity.this, "当期活动已结束，正在开奖，请稍后再来。");
                    return;
                }

                if (mOstatus == 4) {
                    ToastUtil.showShortText(ShopDetailsIndianaActivity.this, "当期活动已结束，请留意下期哦。");
                } else {
                    showPayDialog();
                }

                break;
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.tv_old_show://往期揭晓
                Intent intent2 = new Intent(mContext, SnatchActivity.class);
                intent2.putExtra("index", 1);
                startActivity(intent2);
                ((FragmentActivity)
                        mContext).overridePendingTransition(R.anim.slide_left_in,
                        R.anim.slide_match);
                break;
            case R.id.tv_record://夺宝记录
                Intent intent3 = new Intent(mContext, SnatchActivity.class);
                intent3.putExtra("index", 0);
                startActivity(intent3);
                ((FragmentActivity)
                        mContext).overridePendingTransition(R.anim.slide_left_in,
                        R.anim.slide_match);
                break;
            case R.id.img_cart:// 购物车
//                Intent intent2 = new Intent(this, ShopCartNewNewActivity.class);
//                startActivityForResult(intent2, 235);
                break;
            case R.id.img_fenx:// 一键分享
                double feedback = 0;
            {
                if (null == shop) {
                    ToastUtil.showShortText(context, "操作无效");
                    return;
                }

                feedback = shop.getKickback();
                // share(shop.getShop_code(), shop);
            }
            showMyPopwindou(ShopDetailsIndianaActivity.this, feedback);
            break;
            default:
                break;
        }
    }

    @Override
    public void intentToConfirm(int takeCount, double countMoney, int flag) {
        Intent intent = new Intent(ShopDetailsIndianaActivity.this, SubmitDuobaoOrderActivity.class);
        String pic = YUrl.imgurl + shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/"
                + (String) shop.getDef_pic();
        intent.putExtra("pic", pic);// 图片
        intent.putExtra("signType", mIndianaMoney);
        intent.putExtra("valueDuo", code);
        intent.putExtra("code", shop.getShop_code());
        if (flag == 1) {//1分钱夺宝
//            intent.putExtra("shop_se_price", 0.1);// 现价
            intent.putExtra("shop_se_price", mShop_se_price);// 现价
            intent.putExtra("flag", "1");// 现价
        } else {
            intent.putExtra("shop_se_price", mShop_se_price);// 现价
            intent.putExtra("flag", "0");// 现价
        }
        intent.putExtra("shop_price", mShop_price);// 原价
        intent.putExtra("name", shop.getShop_name());// 商品名
        intent.putExtra("shop_num", takeCount);

        // list = (List<Shop>) getIntent().getSerializableExtra("list");
        if (indianaPayDialog != null) {
            indianaPayDialog.dismiss();
        }
        startActivity(intent);

        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
    }

    /**
     * 分享套餐
     */
    @Override
    public void getPshareShop() {
        if ("SignShopDetail".equals(signShopDetail)) {
            code = signValue;
        }
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

                    TongjiShareCount.tongjifenxiangCount();
                    TongjiShareCount.tongjifenxiangwho(code);

                    if (result.get("status").equals("1")) {
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
    }

    /**
     * 得到分享的链接
     */
    public void share(final String code, final Shop shop) {

        shareWaitDialog.show();

        ShareUtil.configPlatforms(context);// 配置分享平台参数</br>
        isPause = 1;
        new SAsyncTask<String, Void, HashMap<String, String>>(this, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                return ComModel2.getIndianashopLink(params[0], context, "true");
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
                        TongjiShareCount.tongjifenxiangCount();
                        TongjiShareCount.tongjifenxiangwho((String) result.get("shop_code"));

                        LogYiFu.e("pic", result.get("shop_pic"));
                        String[] picList = result.get("shop_pic").split(",");
                        String four_pic = result.get("four_pic").toString();
                        String link = result.get("link");
                        LogYiFu.e(TAG, "link//" + link);
                        download(null, picList, code, result, shop, link, four_pic);
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

        }.execute(shop.getShop_code());
    }

    public static PublicToastDialog shareWaitDialog = null;

    /**
     * 下载分享的图片
     */
    private void download(View v, final String shop_code, final HashMap<String, Object> mapInfos, final String link) {
        new SAsyncTask<Void, Void, Void>((FragmentActivity) ShopDetailsIndianaActivity.this, v, R.string.wait) {

            @Override
            protected Void doInBackground(Void... params) {
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

                int j = pics.size() + 1;
                if (pics.size() > 8) {
                    j = 9;
                }
                for (int i = 0; i < j; i++) {
                    if (i == j - 1) {
                        /*
                         * ComModel2.saveQRCode(PaymentSuccessActivity.this,
						 * shop_code);
						 */
                        {
                            Bitmap bm = QRCreateUtil.createImage(link, 500, 700, (String) mapInfos.get("shop_se_price"),
                                    ShopDetailsIndianaActivity.this);// 得到二维码图片
                            QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
                                    MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
                        }
                        // downloadPic(mapInfos.get("qr_pic"), 9);
                        break;
                    }
                    downloadPic(pics.get(i) + "!450", i);
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

                    // ShareUtil.configPlatforms(context);

                    UMImage umImage = new UMImage(context, bmBg);
                    ShareUtil.setShareContent(context, umImage, "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);

                    // showPopwindou(link, context, umImage);
                    myPopupwindow.setUmImage(umImage);
                    myPopupwindow.setLink(link);
                    shareTo(shop_code);
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
    private void showMyPopwindou(FragmentActivity context, double feedBack) {
        // myPopupwindow = new MyPopupwindow(context, 0, umImage, link);
        // myPopupwindow = new MyPopupwindow(context,
        // shop.getKickback(), umImage, link);

        myPopupwindow = new MyPopupwindow(context, feedBack, this, shop, false, "SignShopDetail", "ShopDetails", 0,
                "-1");
        if (false || "SignShopDetail".equals(signShopDetail)) {
            myPopupwindow.setGou(true);
        }
        if (ShopDetailsIndianaActivity.instance != null) {
            myPopupwindow.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    private Bitmap bmBg;

    /**
     * 下载分享的图片
     */
    private void download(View v, final String[] picList, final String shop_code,
                          final HashMap<String, String> mapInfos, final Shop shop, final String link, final String four_pic) {
        final List<String> pics = new ArrayList<String>();

        new SAsyncTask<Void, Void, Void>((FragmentActivity) ShopDetailsIndianaActivity.this, v, R.string.wait) {

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
                        // mapInfos.get("QrLink")
                        {
                            // Bitmap bm = QRCreateUtil.createImage(link, 500,
                            // 700, mapInfos.get("shop_se_price"),
                            // ShopDetailsIndianaActivity.this);// 得到二维码图片
                            // QRCreateUtil.saveBitmap(bm,
                            // YConstance.savePicPath,
                            // MD5Tools.md5(String.valueOf(9)) + ".jpg");//
                            // 保存二维码图片
                            // 九宫图二维码新样式
                            Bitmap bmQr = QRCreateUtil.createQrImage(link, 250, 250);
                            Bitmap bm = QRCreateUtil.drawNewBitmapNine(ShopDetailsIndianaActivity.this, bmQr,
                                    (String) mapInfos.get("shop_se_price"), true);
                            QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
                                    MD5Tools.md5(String.valueOf(i)) + ".jpg");// 保存二维码图片
                        }
                        // downloadPic(mapInfos.get("qr_pic"), 9);
                        continue;
                    }
                    int m = i > 4 ? i - 1 : i;
                    downloadPic(shop_code.substring(1, 4) + "/" + shop_code + "/" + pics.get(m) + "!450", i);
                    bmBg = downloadPic(
                            shop_code.substring(1, 4) + "/" + shop_code + "/" + four_pic.split(",")[0] + "!450");
                }
                return super.doInBackground(params);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, Void result) {
                if (instance == null) {
                    return;
                }
                if (null != context && null != context.getWindow().getDecorView() && !context.isFinishing()) {
                    LogYiFu.e("TAG", "宝贝内容=" + shop.getShop_name() + ",宝贝链接=" + result);
                    // ShareUtil.configPlatforms(context);

                    UMImage umImage = new UMImage(context, bmBg);
                    // ShareUtil.setShareContent(context, umImage,
                    // "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~~", link);
                    ShareUtil.setShareContentBaoYou(context, umImage,
                            (int) shop.getShop_se_price() + "元赢iphone6,不中还能退钱，居然有这样的好事", link,
                            (int) shop.getShop_se_price(), 1);
                    // ShareUtil.share(ShopDetailsActivity.this);
                    myPopupwindow.setLink(link);
                    myPopupwindow.setUmImage(umImage);
                    // myPopupwindow = new MyPopupwindow(context,
                    // shop.getKickback(), umImage, link);
                    // if (ShopDetailsActivity.instance != null) {
                    // myPopupwindow.showAtLocation(context.getWindow()
                    // .getDecorView(), Gravity.BOTTOM, 0, 0);
                    // }
                    shareTo(shop_code);
                    super.onPostExecute(context, result);
                }

            }

        }.execute();

    }

    /**
     * @param shop_code
     */
    private void shareTo(String shop_code) {
        shareWaitDialog.dismiss();
        switch (myPopupwindow.getShareId()) {
            case R.id.iv_qq_share:
                if (myPopupwindow.isSecondShare()) {
                    myPopupwindow.onceShare(qqShareIntent, "qq空间");

                    yunYunTongJi(shop_code, 104, 2);
                }
                break;
            case R.id.iv_wxin_share:
                if (myPopupwindow.isSecondShare()) {
                    myPopupwindow.onceShare(wXinShareIntent, "微信");

                    yunYunTongJi(shop_code, 1, 2);
                } else {
                    myPopupwindow.performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
                }
                break;
            case R.id.iv_wxin_circle_share:

                yunYunTongJi(shop_code, 106, 2);
                myPopupwindow.shareToWxin();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AppManager.getAppManager().removeDetailsActivity((Activity) mContext);
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

    private Animation mShakeAnimation;

    // CycleTimes动画重复的次数
    public void shakeAnimation(int CycleTimes) {
        if (null == mShakeAnimation) {
            mShakeAnimation = new TranslateAnimation(0, 5, 0, 5);
            mShakeAnimation.setInterpolator(new CycleInterpolator(5));
            mShakeAnimation.setDuration(3000);
            mShakeAnimation.setRepeatMode(Animation.REVERSE);// 设置反方向执行

        }
    }

    // TODO:
    // 查询参与记录
    private int rows = 10, page = 1;
    private boolean isCheck = false;

    private void queryIndianaTakeRecord() {
        isCheck = true;
        new SAsyncTask<Void, Void, List<HashMap<String,
                Object>>>((FragmentActivity) context, null, R.string.wait) {
            @Override
            protected List<HashMap<String, Object>> doInBackground(FragmentActivity
                                                                           context, Void... params)
                    throws Exception {
                LogYiFu.e(TAG, "page" + page);
                List<HashMap<String, Object>> list =
                        ComModel.queryIndianaTakeRecord(context, shop.getShop_code(),
                                shop.getShop_batch_num(), "" + page, "" + rows);
                return list;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         List<HashMap<String, Object>> list, Exception e) {

                isCheck = false;
                if (e != null) {// 查询异常
                    Toast.makeText(context, "连接超时，请重试", Toast.LENGTH_LONG).show();
                    page--;
                } else {// 查询商品详情成功，刷新界面
                    if (list != null && list.size() > 0) {
                        if (mListTakeRecord == null) {
                            mListTakeRecord = new ArrayList<HashMap<String, Object>>();
                        }
                        mListTakeRecord.addAll(list);
                    } else {
                        if (page > 1) {
                            Toast.makeText(ShopDetailsIndianaActivity.this, "已经到底了",
                                    Toast.LENGTH_SHORT).show();
                        }
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

    private void queryIndianaTakeRecordNew() {
        isCheck = true;
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) context, null, R.string.wait) {
            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                LogYiFu.e(TAG, "page" + page);
                if (mIssue_code != null && !("".equals(mIssue_code))) {
                    HashMap<String, Object> list = ComModel.queryIndianaTakeRecordNew(context, shop.getShop_code(),
                            mIssue_code, "" + 1, "" + rows);
                    return list;
                } else {
                    HashMap<String, Object> list = ComModel.queryIndianaTakeRecordNew(context, shop.getShop_code(),
                            shop.getShop_batch_num(), "" + 1, "" + rows);
                    return list;
                }

            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> list, Exception e) {
                isCheck = false;
                if (e != null) {// 查询异常
                    // Toast.makeText(context, "连接超时，请重试",
                    // Toast.LENGTH_LONG).show();
                    page--;
                } else {// 查询商品详情成功，刷新界面
                    if (list != null && list.size() > 0) {
                        if (mListTakeRecordNew == null) {
                            mListTakeRecordNew = new HashMap<String, Object>();
                        }
                        mListTakeRecordNew = list;
                    } else {
                        if (page > 1) {
                            Toast.makeText(ShopDetailsIndianaActivity.this, "已经到底了", Toast.LENGTH_SHORT).show();
                        }
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

    private int index = 0;
    private int mType = 0;
    private boolean isShopTitle = false;
    private String attrDateStr;// 属性时间戳

    // TODO:shop_head 添加头上部分

    /***
     * 查询夺宝商品详情页
     */

    private void queryShopDetails() {
        aa = new SAsyncTask<String, Void, HashMap<String, Object>>(this, null, R.string.wait) {

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                LoadingDialog.show(ShopDetailsIndianaActivity.this);
            }

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                HashMap<String, Object> map;
                if (code != null && !TextUtils.isEmpty(code)) {
                    map = ComModel.queryIndianaShopDetails(ShopDetailsIndianaActivity.this,
                            YCache.getCacheToken(ShopDetailsIndianaActivity.this), params[0], attrDateStr, "", "");

                } else {
                    map = ComModel.queryIndianaShopDetails(ShopDetailsIndianaActivity.this,
                            YCache.getCacheToken(ShopDetailsIndianaActivity.this), "", attrDateStr, params[0], "");
                }

                return map;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> map, Exception e) {

                if (e != null) {// 查询异常
                    /*
                     * Toast.makeText(ShopDetailsActivity.this, "连接超时，请重试",
					 * Toast.LENGTH_LONG).show();
					 */

                } else {// 查询商品详情成功，刷新界面

                    if (map != null) {
                        firstJoin = (map.get("countTrea") + "").equals("0");
                        //
                        mWinnerHeadPic = (String) map.get("in_head");
                        mWinnerName = (String) map.get("in_name");
                        mIn_code = (String) map.get("in_code");
                        in_uid = (String) map.get("in_uid");
                        rrr.setBackgroundColor(Color.WHITE);
                        mNeedPeoplenum = (Integer) map.get("num");
                        mXuNiPeople = (Integer) map.get("virtual_num");
                        LogYiFu.e(TAG, mNeedPeoplenum + "mNeedPeoplenum");
                        shop = (Shop) map.get("shop");
                        issue_code = "" + shop.getShop_batch_num();
                        mOstatus = (Integer) map.get("ostatus");
                        LogYiFu.e(TAG, "mOstatus" + mOstatus);
                        mOtime = (Long) map.get("otime");
                        my_num = (Integer) map.get("my_num");

                        order_status = (Integer) map.get("order_status");

                        codes = (List<String>) map.get("codes");
                        // codes=str.split(",")[0];
                        titleCheck = shop.getType1();
                        if (titleCheck >= listTitle.size()) {
                            titleCheck = 0;
                        }
                        // if (shop.getCart_count() > 0) {
                        // tv_cart_count.setText(shop.getCart_count() + "");//
                        // 设置购物车数量
                        // tv_cart_count.setVisibility(View.VISIBLE);
                        // } else {
                        // tv_cart_count.setText(0 + "");
                        // tv_cart_count.setVisibility(View.GONE);
                        // }

                        // TODO:

                        headerView = LayoutInflater.from(ShopDetailsIndianaActivity.this)
                                .inflate(R.layout.indiana_shop_header, null);
                        // 查找id
                        LinearLayout mLlGoon = (LinearLayout) headerView.findViewById(R.id.indiana_head_llgoon);// 控制进行中显示
                        LinearLayout mLlResult = (LinearLayout) headerView.findViewById(R.id.indiana_head_llresult);// 控制结果显示
                        TextView mShaiDan = (TextView) headerView.findViewById(R.id.indiana_head_shaidan);
                        // ImageView mBaoyou = (ImageView) headerView
                        // .findViewById(R.id.indiana_head_baoyou);
                        // mHeadPic.setVisibility(View.VISIBLE);
                        mHeadPic.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                Intent intent = new Intent(ShopDetailsIndianaActivity.this, ShopDetailsActivity.class);
                                intent.putExtra("SignShopDetail", "SignShopDetail");
                                intent.putExtra("signType", mIndianaMoney);
                                intent.putExtra("valueBao", mBaoyouTiao);
                                intent.putExtra("valueDuo", code);
                                startActivity(intent);
                                ShopDetailsIndianaActivity.this.finish();
                            }
                        });
                        if (2 == mIndianaMoney) {
                            // mHeadPic.setBackgroundResource(R.drawable.two_yuanbaoyou_indiana);
                            // mBaoyou.setBackgroundResource(R.drawable.two_yuanbaoyou_indiana);
                        } else if (3 == mIndianaMoney) {
                            // mBaoyou.setBackgroundResource(R.drawable.threee_yuanbaoyou_indiana);
                            // mHeadPic.setBackgroundResource(R.drawable.threee_yuanbaoyou_indiana);
                        } else {
                            // mBaoyou.setBackgroundResource(R.drawable.five_yuanbaoyou_indiana);
                            // mHeadPic.setBackgroundResource(R.drawable.five_yuanbaoyou_indiana);
                        }
                        mShaiDan.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                Intent intent = new Intent(ShopDetailsIndianaActivity.this, ShaiDanActivity.class);
                                intent.putExtra("in_code", mIn_code);
                                intent.putExtra("shop_code", shop.getShop_code());
                                intent.putExtra("shop_name", shop.getShop_name());
                                intent.putExtra("issue_code", issue_code);
                                startActivity(intent);
                            }
                        });
                        mHeadCountTime = (TextView) headerView.findViewById(R.id.indiana_head_countdown);
                        headerView.findViewById(R.id.indiana_head_topimg).getLayoutParams().height = width * 9 / 6;
                        heights = width * 9 / 6;
//                        recLen = shop.getActive_end_time() - shop.getSys_time();
//                        timer.schedule(task, 0, 1000);
                        width = ShopDetailsIndianaActivity.this.getResources().getDisplayMetrics().widthPixels;
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
                        RelativeLayout mRlState = (RelativeLayout) headerView.findViewById(R.id.indiana_head_rlstate);
                        TextView mTvState = (TextView) headerView.findViewById(R.id.indiana_head_state);
                        // 揭晓显示
                        TextView mTvOtime = (TextView) headerView.findViewById(R.id.indiana_head_time);// 开奖时间
                        TextView mTvWinnerName = (TextView) headerView.findViewById(R.id.indiana_head_huojiangzhe);// 获奖者姓名
                        RoundImageButton mTvWinnerPic = (RoundImageButton) headerView
                                .findViewById(R.id.indiana_head_headpicture);// 获奖者头像
                        TextView mTvNumbers = (TextView) headerView.findViewById(R.id.indiana_head_numbers);// 中奖号码
                        // TODO:
                        // RoundImageButton headPicture = (RoundImageButton)
                        // headerView
                        // .findViewById(R.id.indiana_head_headpicture);
                        // UserInfo info = YCache
                        // .getCacheUserSafe(ShopDetailsIndianaActivity.this.context);
                        // SetImageLoader.initImageLoader(
                        // ShopDetailsIndianaActivity.this.context,
                        // headPicture, info.getPic(), "");


                        needCount = (shop.getActive_people_num() - mNeedPeoplenum - mXuNiPeople) < 0 ? 0
                                : shop.getActive_people_num() - mNeedPeoplenum - mXuNiPeople;

                        // 进行中，还是已揭晓\
                        if (mOstatus == 0 || mOstatus == 4) {
                            mTvState.setText("进行中");
                            mLlGoon.setVisibility(View.VISIBLE);
                            mLlResult.setVisibility(View.GONE);
                            mDetailsSubmit.setText("立即参与");
                            mDetailsSubmit.setBackgroundColor(Color.parseColor("#ff3f8c"));
                            mDetailsSubmit.setClickable(true);
                            mDetailsSubmit.setOnClickListener(ShopDetailsIndianaActivity.this);
                            if (my_num <= 0) {
                                // mHeadPic.setVisibility(View.VISIBLE);
                                headerView.findViewById(R.id.indiana_head_nonotice).setVisibility(View.VISIBLE);
                                headerView.findViewById(R.id.indiana_head_yesnotice).setVisibility(View.GONE);


                                if (firstJoin) {
                                    mDetailsSubmit.setText("一分钱抽奖");
                                } else {
                                    mDetailsSubmit.setText("立即参与");
                                }

//                                //剩余参与次数未0，且未参与
//                                if(needCount == 0){
//                                    mDetailsSubmit.setText("正在开奖");
//                                }


                                mDetailsSubmit.setBackgroundColor(Color.parseColor("#ff3f8c"));
                                mDetailsSubmit.setClickable(true);
                                mDetailsSubmit.setOnClickListener(ShopDetailsIndianaActivity.this);
                            } else {
//                                mHeadPic.setVisibility(View.GONE);
                                headerView.findViewById(R.id.indiana_head_nonotice).setVisibility(View.GONE);
                                headerView.findViewById(R.id.indiana_head_yesnotice).setVisibility(View.VISIBLE);
                                ((TextView) headerView.findViewById(R.id.indiana_head_numtop)).setText("" + my_num);// 我的参与次数
                                StringBuffer sbf = new StringBuffer();
                                for (int i = 0; i < codes.size(); i++) {
                                    String str = codes.get(i);
                                    String[] strs = str.split(",");
                                    if (i != codes.size() - 1) {
                                        for (int j = 0; j < strs.length; j++) {
                                            sbf.append(strs[j]).append("、");
                                        }
                                    } else {
                                        for (int j = 0; j < strs.length; j++) {
                                            if (j != strs.length - 1) {
                                                sbf.append(strs[j]).append("、");
                                            } else {
                                                sbf.append(strs[j]);
                                            }
                                        }
                                    }
                                }


                                ((TextView) headerView.findViewById(R.id.indiana_head_numstop))
                                        .setText("参与号码：" + sbf.toString());// 我的参与号码
//                                mDetailsSubmit.setBackgroundColor(Color.parseColor("#c5c5c5"));
//                                mDetailsSubmit.setClickable(false);
                                mDetailsSubmit.setText("再次参与");


                                mDetailsSubmit.setBackgroundColor(Color.parseColor("#ff3f8c"));
                                mDetailsSubmit.setClickable(true);
                                mDetailsSubmit.setOnClickListener(ShopDetailsIndianaActivity.this);
                            }


                            //剩余参与次数为0，且未结束
                            if (needCount == 0) {
                                mDetailsSubmit.setText("正在开奖");
                            }


                        } else if (mOstatus == 2) {
                            mHeadPic.setVisibility(View.GONE);
                            mLlGoon.setVisibility(View.VISIBLE);
                            mLlResult.setVisibility(View.GONE);
//                            mHeadCountTime.setText("未满足人数退款");
                            mTvState.setText("已揭晓");
                            mTvState.setTextColor(Color.parseColor("#ff3f8c"));
                            mDetailsSubmit.setBackgroundColor(Color.parseColor("#c5c5c5"));
                            mDetailsSubmit.setClickable(false);
                            mDetailsSubmit.setText("已结束");
                        } else if (mOstatus == 3) {
                            mHeadPic.setVisibility(View.GONE);
                            mLlGoon.setVisibility(View.GONE);
                            mLlResult.setVisibility(View.VISIBLE);
                            mTvState.setText("已揭晓");
                            mTvState.setTextColor(Color.parseColor("#ff3f8c"));
                            mRlState.setBackgroundResource(R.drawable.indiana_shape_result);
                            mDetailsSubmit.setBackgroundColor(Color.parseColor("#c5c5c5"));
                            mDetailsSubmit.setClickable(false);
                            mDetailsSubmit.setText("已结束");
                            SimpleDateFormat sdf3 = new SimpleDateFormat("yyy.MM.dd HH:mm:ss");
                            Date startTmie = new Date(mOtime);
                            mTvOtime.setText("" + sdf3.format(startTmie));
                            mTvNumbers.setText("" + mIn_code);
                            ((TextView) headerView.findViewById(R.id.indiana_head_numbottom)).setText("" + my_num);// 我的参与次数
                            StringBuffer sbf2 = new StringBuffer();
                            for (int i = 0; i < codes.size(); i++) {
                                if (i != codes.size() - 1) {
                                    sbf2.append(codes.get(i)).append("、");
                                } else {
                                    sbf2.append(codes.get(i));
                                }
                            }
                            ((TextView) headerView.findViewById(R.id.indiana_head_numsbottom))
                                    .setText("" + sbf2.toString());// 我的参与号码
                            mTvWinnerName.setText("" + mWinnerName);
                            // SetImageLoader.initImageLoader(ShopDetailsIndianaActivity.this.context,
                            // mTvWinnerPic,
                            // mWinnerHeadPic, "");

                            PicassoUtils.initImage(ShopDetailsIndianaActivity.this.context, mWinnerHeadPic,
                                    mTvWinnerPic);

                            UserInfo user = YCache.getCacheUser(context);
                            if (!"".equals(in_uid) && in_uid != null && !"null".equals(in_uid)) {
                                if (user.getUser_id() == Integer.parseInt(in_uid)) {
                                    mShaiDan.setVisibility(View.VISIBLE);

                                    if (order_status == 5 || order_status == 6) {
                                        mShaiDan.setClickable(false);
                                        mShaiDan.setBackgroundResource(R.drawable.indiana_shape_shaidan_gray);
                                    } else {
                                        mShaiDan.setClickable(true);
                                        mShaiDan.setBackgroundResource(R.drawable.indiana_shape_shaidan);
                                    }
                                } else {
                                    mShaiDan.setVisibility(View.GONE);
                                }
                            } else {
                                mShaiDan.setVisibility(View.GONE);
                            }

                        }

                        // 商品名
                        TextView name = (TextView) headerView.findViewById(R.id.indiana_head_name);
                        name.setText(TextUtils.isEmpty(shop.getShop_name()) ? null : shop.getShop_name());
                        TextView content = (TextView) headerView.findViewById(R.id.indiana_head_namemessage);
                        content.setText("" + shop.getContent());
                        // 现价
                        ((TextView) headerView.findViewById(R.id.indiana_head_nowprice))
                                .setText("¥" + new DecimalFormat("#0.0").format(shop.getShop_se_price()));
                        mShop_se_price = shop.getShop_se_price();
                        // 原价
                        String shop_price = "¥" + new java.text.DecimalFormat("#0.0").format(shop.getShop_price());
                        mShop_price = shop.getShop_price();
                        TextView mOriginal = ((TextView) headerView.findViewById(R.id.indiana_head_originalprice));
                        mOriginal.setText(shop_price);
                        mOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        // 期号
                        TextView mIssue = (TextView) headerView.findViewById(R.id.indiana_head_issue);
                        mIssue.setText("" + shop.getShop_batch_num());
                        // 总人数
                        TextView mTotalPeople = (TextView) headerView.findViewById(R.id.indiana_head_totalpepole);
                        // 剩余人次
                        TextView mNeedPeople = (TextView) headerView.findViewById(R.id.indiana_head_needpepole);


                        mTotalPeople.setText("" + shop.getActive_people_num());
                        mHeadCountTime.setText("已有" + (mNeedPeoplenum + mXuNiPeople) + "人正在参与");
                        int a = (shop.getActive_people_num() - mNeedPeoplenum - mXuNiPeople) < 0 ? 0
                                : shop.getActive_people_num() - mNeedPeoplenum - mXuNiPeople;
                        // if (mIssue_code != null && !("".equals(mIssue_code)))
                        // {
                        // if (Integer.parseInt(mIssue_code) > 0) {
                        // if (a - Integer.parseInt(mIssue_code) < 0) {
                        // mNeedPeople.setText("" + 0);
                        // } else {
                        // mNeedPeople.setText("" + (a -
                        // Integer.parseInt(mIssue_code)));
                        // }
                        // } else {
                        // mNeedPeople.setText("" + a);
                        // }
                        // } else {
                        mNeedPeople.setText("" + a);
                        // }
                        ProgressBar mProgressBar = (ProgressBar) headerView.findViewById(R.id.indiana_head_progressbar);
//                        int max = (int) ((shop.getActive_end_time() - shop.getActive_start_time()) / 1000);
//                        int current = (int) ((shop.getSys_time() - shop.getActive_start_time()) / 1000);
                        int max = shop.getActive_people_num();
                        int progress = mNeedPeoplenum + mXuNiPeople;
                        mProgressBar.setMax(max);
                        mProgressBar.setProgress(progress);
//                        mProgressBar.setProgress(max - current);
                        String def_pic = shop.getDef_pic();
                        if (!TextUtils.isEmpty(def_pic)) {
                            // SetImageLoader.initImageLoader(null,
                            // (ScaleImageView)
                            // headerView.findViewById(R.id.indiana_head_topimg),
                            // shop.getShop_code().substring(1, 4) + "/" +
                            // shop.getShop_code() + "/" + def_pic,
                            // "!450");
                            shopPicUrl = shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/" + def_pic
                                    + "!180";
                            PicassoUtils.initImage(instance,
                                    shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/" + def_pic
                                            + "!450",
                                    (ScaleImageView) headerView.findViewById(R.id.indiana_head_topimg));

                            headerView.findViewById(R.id.indiana_head_topimg).setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(ShopDetailsIndianaActivity.this,
                                            ShopImageActivity.class);
                                    intent.putExtra("url", shop.getShop_code().substring(1, 4) + "/"
                                            + shop.getShop_code() + "/" + shop.getDef_pic());
                                    intent.putExtra("signType", mIndianaMoney);
                                    intent.putExtra("isIndiana", true);
                                    intent.putExtra("isIndiana", true);
                                    intent.putExtra("code", code);
                                    intent.putExtra("shop", shop);
                                    intent.putExtra("ostatus", mOstatus);
                                    intent.putExtra("my_num", my_num);
                                    intent.putExtra("firstJoin", firstJoin);
                                    intent.putExtra("needCount", needCount);
                                    int needPeopleCount = shop.getActive_people_num() - mNeedPeoplenum - mXuNiPeople < 0 ? 0
                                            : shop.getActive_people_num() - mNeedPeoplenum - mXuNiPeople;
                                    intent.putExtra("need_people", needPeopleCount);
                                    startActivityForResult(intent, 1080);
                                }
                            });
                        }

                        mListView.addHeaderView(headerView);
                        mListView.setAdapter(adapter);

                        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                            private int myposition;
                            private ImageButton aa2;
                            private ImageView img_cart_top;
                            private ImageView img_fenx_top;

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

                                        if (newPosition > myposition) { // 向上滑动
                                            if (rlBottom.getVisibility() == View.VISIBLE && isAnim == false) {
                                                rlBottom.clearAnimation();
                                                rlBottom.startAnimation(animationGone);
                                            }
                                        } else if (newPosition < myposition) {
                                            if (rlBottom.getVisibility() == View.GONE && isAnim == false) {
                                                rlBottom.clearAnimation();
                                                rlBottom.startAnimation(animationShow);
                                            }
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

                                            } else if (check == 1) {

                                                if (isCheck == false) {
                                                    page++;
//												queryIndianaTakeRecordNew();
                                                    if (page < 31) {
                                                        queryIndianaTakeRecord();
                                                    }
                                                }
                                            }
                                        }

                                        if (isShopTitle) {
                                            break;
                                        }

                                        if (rlBottom.getVisibility() == View.GONE && isAnim == false) {
                                            rlBottom.clearAnimation();
                                            rlBottom.startAnimation(animationShow);
                                        }

                                        break;
                                }
                            }

                            @Override
                            public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
                                int perHeight = heights / 100;
                                int currentY = 0;
                                int viewTop = -1;
                                aa2 = (ImageButton) findViewById(R.id.imgbtn_left_icon);
                                img_cart_top = (ImageView) findViewById(R.id.img_cart);
                                img_fenx_top = (ImageView) findViewById(R.id.img_fenx);

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
                                    img_cart_top.setBackgroundResource(R.drawable.icon_gouwuche);
                                    img_fenx_top.setBackgroundResource(R.drawable.icon_fenxiang);
                                    aa2.getBackground().setAlpha(255);
                                    img_cart_top.getBackground().setAlpha(255);
                                    img_fenx_top.getBackground().setAlpha(255);
                                    rlTop.getBackground().setAlpha(255);
                                    // mTopView.setVisibility(View.GONE);
                                }
                                if (Math.abs(currentY) > 0 && Math.abs(currentY) < heights / 2) {
                                    aa2.setBackgroundResource(R.drawable.icon_fanhui);
                                    img_cart_top.setBackgroundResource(R.drawable.icon_gouwuche);
                                    img_fenx_top.setBackgroundResource(R.drawable.icon_fenxiang);
                                    int i = (int) Math.abs(currentY);

                                    if (Math.abs(currentY) == 0) {
                                        i = 1;
                                    }
                                    aa2.getBackground().setAlpha(255 - (i / 3));
                                    img_cart_top.getBackground().setAlpha(255 - i * 2 / 5);
                                    img_fenx_top.getBackground().setAlpha(255 - i * 2 / 5);

                                }

                                if (Math.abs(currentY) >= heights / 2 && Math.abs(currentY) < heights) {
                                    aa2.setBackgroundResource(R.drawable.icon_fanhui_black);
                                    img_cart_top.setBackgroundResource(R.drawable.icon_gouwuche_black);
                                    img_fenx_top.setBackgroundResource(R.drawable.icon_fenxiang_black);
                                    int i = (int) Math.abs(currentY) / perHeight;
                                    if (i > 100) {
                                        i = 100;
                                    }
                                    aa2.getBackground().setAlpha(255 * i / 100);
                                    img_cart_top.getBackground().setAlpha(255 * i / 100);
                                    img_fenx_top.getBackground().setAlpha(255 * i / 100);
                                }

                                if (Math.abs(currentY) <= heights && Math.abs(currentY) > 0) {
                                    rlTop.setBackgroundColor(getResources().getColor(R.color.white));
                                    int i = (int) Math.abs(currentY) / perHeight;
                                    if (i > 100) {
                                        i = 100;
                                    }
                                    rlTop.getBackground().setAlpha(255 * i / 100);
                                }

                                if (Math.abs(currentY) >= heights) {
                                    rlTop.getBackground().setAlpha(255);
                                    aa2.setBackgroundResource(R.drawable.icon_fanhui_black);
                                    img_cart_top.setBackgroundResource(R.drawable.icon_gouwuche_black);
                                    img_fenx_top.setBackgroundResource(R.drawable.icon_fenxiang_black);
                                    aa2.getBackground().setAlpha(255);
                                    img_cart_top.getBackground().setAlpha(255);
                                    img_fenx_top.getBackground().setAlpha(255);
                                }

                            }
                        });

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
        if (code != null && !TextUtils.isEmpty(code)) {
            aa.execute(code);
        } else {
            aa.execute(mShop_code);
        }

    }

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

    private GoodsEntity entity;

    private Drawable d;

    boolean flag0 = true, flag1 = true;

    // private AlphaAnimation alphaAnimation2;
    // private AlphaAnimation alphaAnimation1;

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

    private int titleCheck = 0;// 当前导航栏选择

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
        // if (!"SignShopDetail".equals(signShopDetail)) {
        // if (mListView.getFirstVisiblePosition() > (imageTag1.length
        // + imageTag2.length + imageTag3.length)) {
        // mListView.setSelection(imageTag1.length + imageTag2.length
        // + imageTag3.length + 1);
        // }
        // } else {
        if (mListView.getFirstVisiblePosition() > images.length) {
            mListView.setSelection(images.length + 1);
        }
        // }
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
                        Toast.makeText(ShopDetailsIndianaActivity.this, "已经到底了", Toast.LENGTH_SHORT).show();
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
        if (check == 1) {
            if ((mListTakeRecord == null || mListTakeRecord.size() == 0) && isCheck == false) {
//				queryIndianaTakeRecordNew();
                page = 1;
                queryIndianaTakeRecord();
            }
        }
        if (check == 2) {
//            getPicture();
        }
        if (mListView.getFirstVisiblePosition() > 0) {
            mListView.setSelection(1);
        }
        adapter.notifyDataSetChanged();
    }

    private List<ShopOption> tuijianList;
    private String number_sold;

    // TODO:
    // 倒计时
    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
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
                            mHeadCountTime.setText("" + days + "天" + hours + "时" + minutes + "分" + seconds + "秒");
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
                            mHeadCountTime.setText("" + hours + "时" + minutes + "分" + seconds + "秒");
                        }
                    } else if (minute >= 10 && second >= 10) {
                        mHeadCountTime.setText("" + minute + "分" + second + "秒");
                    } else if (minute >= 10 && second < 10) {
                        mHeadCountTime.setText("" + minute + "分0" + second + "秒");
                    } else if (minute < 10 && second >= 10) {
                        mHeadCountTime.setText("0" + minute + "分" + second + "秒");
                    } else {
                        mHeadCountTime.setText("0" + minute + "分0" + second + "秒");
                    }
                    // mRemainningTime.setText("" + recLen);
                    if (recLen < 0) {
                        timer.cancel();
                        if (mOstatus == 2) {
                            mHeadCountTime.setText("未满足人数退款");
                        } else {
                            mHeadCountTime.setText("即将开奖!");
                        }
                        // mRemainningTime.setVisibility(View.GONE);
                    }
                }
            });
        }
    };

    // TODO:
    @Override
    protected void onResume() {
        super.onResume();
        // MobclickAgent.onPageStart("ShopDetailsActivity");
        // MobclickAgent.onResume(this);
        isPause = 0;
        List<String> taskMap = YJApplication.instance.getTaskMap();
        if (taskMap == null) {
            taskMap = new ArrayList<String>();
        }
        if (YJApplication.instance.isLoginSucess()) {
            ShopCartDao dao = new ShopCartDao(context);
            if (dao.queryCartCount(context) > 0) {
                tv_cart_count.setVisibility(View.VISIBLE);
                int count = dao.queryCartCount(context);
                count = count > 99 ? 99 : count;
                tv_cart_count.setText("" + count);
            } else {
                tv_cart_count.setVisibility(View.GONE);
            }
        }


        if (IndianaPayDialog.wxClick) {

            if (!IndianaPayDialog.isCommit) {
                IndianaPayDialog.isCommit = true;
                IndianaPayDialog.submitIndianaShareRecord();
            }


        }


        // int curr = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        // if ((!taskMap.contains("17") ||
        // !YJApplication.instance.isLoginSucess())
        // && curr != getSharedPreferences("month", Context.MODE_PRIVATE)
        // .getInt("month", 0)
        // && (getSharedPreferences("week", Context.MODE_PRIVATE).getInt(
        // "week", 0) == Calendar.getInstance().get(
        // Calendar.DAY_OF_WEEK) || getSharedPreferences("week",
        // Context.MODE_PRIVATE).getInt("week", 0) == 0)) {
        // getSharedPreferences("month", Context.MODE_PRIVATE)
        // .edit()
        // .putInt("month",
        // Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        // .commit();
        // getSharedPreferences("week", Context.MODE_PRIVATE)
        // .edit()
        // .putInt("week",
        // Calendar.getInstance().get(Calendar.DAY_OF_WEEK))
        // .commit();
        // NewPDialog dialog = new NewPDialog(
        // ShopDetailsActivity_DuoBao________.this,
        // R.layout.task_dialog6_1);
        // dialog.setF(new NewPDialog.FinishLintener() {
        // @Override
        // public void onFinishClickLintener() {
        // //
        // final NewPDialog dialogs = new NewPDialog(
        // ShopDetailsActivity_DuoBao________.this,
        // R.layout.task_dialog6_2);
        // dialogs.setF(new NewPDialog.FinishLintener() {
        //
        // @Override
        // public void onFinishClickLintener() {
        // }
        // });
        // dialogs.setL(new NewPDialog.TaskLintener() {
        //
        // @Override
        // public void onOKClickLintener() {
        // isShow = false;
        //
        // // 注册
        // if (YJApplication.instance.isLoginSucess()) {
        //
        // share(code, shop);
        //
        // } else {
        // Intent i = new Intent(
        // ShopDetailsActivity_DuoBao________.this,
        // LoginActivity.class);
        // i.putExtra("login_register", "register");
        // ShopDetailsActivity_DuoBao________.this
        // .startActivityForResult(i, 15502);
        // }
        // }
        //
        // @Override
        // public void onShouZhiClickLintener() {
        // isShow = false;
        // // 注册
        // if (YJApplication.instance.isLoginSucess()) {
        //
        // share(code, shop);
        //
        // } else {
        // Intent i = new Intent(
        // ShopDetailsActivity_DuoBao________.this,
        // LoginActivity.class);
        // i.putExtra("login_register", "register");
        // ShopDetailsActivity_DuoBao________.this
        // .startActivityForResult(i, 15502);
        // }
        // }
        // });
        // dialogs.show();
        // isShow = true;
        // }
        // });
        //
        // dialog.setL(new NewPDialog.TaskLintener() {
        //
        // @Override
        // public void onOKClickLintener() {
        // isShow = false;
        // //
        // final NewPDialog dialogs = new NewPDialog(
        // ShopDetailsActivity_DuoBao________.this,
        // R.layout.task_dialog6_2);
        // dialogs.setF(new NewPDialog.FinishLintener() {
        //
        // @Override
        // public void onFinishClickLintener() {
        // isShow = false;
        // }
        // });
        // dialogs.setL(new NewPDialog.TaskLintener() {
        //
        // @Override
        // public void onOKClickLintener() {
        // isShow = false;
        // // 注册
        // if (YJApplication.instance.isLoginSucess()) {
        //
        // share(code, shop);
        //
        // } else {
        // // Intent i = new
        // // Intent(ShopDetailsActivity.this,
        // // LoginActivity.class);
        // // i.putExtra("login_register", "register");
        // // ShopDetailsActivity.this
        // // .startActivityForResult(i, 15502);
        // dialogs.dismiss();
        // }
        // }
        //
        // @Override
        // public void onShouZhiClickLintener() {
        // isShow = false;
        // // 注册
        // if (YJApplication.instance.isLoginSucess()) {
        //
        // share(code, shop);
        //
        // } else {
        // // Intent i = new
        // // Intent(ShopDetailsActivity.this,
        // // LoginActivity.class);
        // // i.putExtra("login_register", "register");
        // // ShopDetailsActivity.this
        // // .startActivityForResult(i, 15502);
        // dialogs.dismiss();
        // }
        // }
        // });
        // dialogs.show();
        // isShow = true;
        // }
        //
        // @Override
        // public void onShouZhiClickLintener() {
        //
        // }
        // });
        // dialog.show();
        // isShow = true;
        // } else {
        // UserInfo user = YCache.getCacheUser(context);
        // if (user.getHobby() == null || user.getHobby().equals("0")) {
        //
        // newHandler = new Handler();
        // newHandler.postDelayed(r, 60 * 1000);
        // }
        //
        // }

    }

    // 这个方法其实只为获取网络图片（夺宝规则）的宽高，以达到屏幕适配(有待优化代码)
    private byte[] picByte;

    public void getPicture() {
        new Thread(runnables).start();
    }

    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 99) {
                if (picByte != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
                    mIvRule.getLayoutParams().height = width * bitmap.getHeight() / bitmap.getWidth();
                    mIvRule.setImageBitmap(bitmap);
                }
            }
        }
    };

    Runnable runnables = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(YUrl.imgurl + "duobao/huodongguize.png");
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
    };

    // private void queryCartCount() {
    //
    // new SAsyncTask<Void, Void, String>(ShopDetailsIndianaActivity.this,
    // R.string.wait) {
    //
    // @Override
    // protected String doInBackground(FragmentActivity context, Void... params)
    // throws Exception {
    // return ComModel2.getShopCartCount(context);
    // }
    //
    // @Override
    // protected boolean isHandleException() {
    // return true;
    // }
    //
    // @Override
    // protected void onPostExecute(FragmentActivity context, String count,
    // Exception e) {
    // super.onPostExecute(context, count, e);
    // if (e != null || count == null) {
    // return;
    // }
    // if (Integer.parseInt(count) > 0) {
    // tv_cart_count.setVisibility(View.VISIBLE);
    // tv_cart_count.setText(count);
    // } else {
    // tv_cart_count.setVisibility(View.GONE);
    // }
    //
    // }
    // }.execute();
    //
    // }

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

    private IndianaPayDialog indianaPayDialog;

    // 选择支付弹窗
    private void showPayDialog() {
        int needPeopleCount = shop.getActive_people_num() - mNeedPeoplenum - mXuNiPeople < 0 ? 0
                : shop.getActive_people_num() - mNeedPeoplenum - mXuNiPeople;
        indianaPayDialog = new IndianaPayDialog(ShopDetailsIndianaActivity.this, R.style.invate_dialog_style, "" + shopPicUrl, "" + shop.getShop_code(), shop.getShop_se_price(), needPeopleCount, shop.getShop_name(), shop.getDef_pic());
        indianaPayDialog.show();
    }
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {//用来控制popupWindow外面不可点击
//        if (payWinDow != null && payWinDow.isShowing()) {
//            return false;
//        }
//        return super.dispatchTouchEvent(event);
//    }


    //    private void getIndianaRuleContent(final LinearLayout mLlRuleContainer) {
//        new SAsyncTask<Void, Void, List<String>>((FragmentActivity) ShopDetailsIndianaActivity.this, R.string.wait) {
//
//            @Override
//            protected List<String> doInBackground(FragmentActivity context, Void... params) throws Exception {
//                return ComModel2.getIndianaRuleContent(context);
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
//                    mLlRuleContainer.removeAllViews();
//                    for (int i = 0; i < result.size(); i++) {
//
//                        TextView tvRule = new TextView(ShopDetailsIndianaActivity.this);
//                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
//                                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                        tvRule.setTextSize(14);
//                        tvRule.setTextColor(Color.parseColor("#7d7d7d"));
//                        tvRule.setText((i + 1) + "." + result.get(i));
//                        params.setMargins(0, DP2SPUtil.dip2px(ShopDetailsIndianaActivity.this, 16), 0, 0);
//                        mLlRuleContainer.addView(tvRule, params);
//                    }
//                }
//            }
//
//        }.execute();
//
//    }
    private void getIndianaRuleContent(final LinearLayout mLlRuleContainer) {
        new SAsyncTask<Void, Void, List<String>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected List<String> doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModelZ.getIndianaRuleText("dbgz");
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null && result.size() > 0) {
                    mLlRuleContainer.removeAllViews();
                    for (int i = 0; i < result.size(); i++) {
                        TextView tvRule = new TextView(ShopDetailsIndianaActivity.this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        tvRule.setTextSize(14);
                        tvRule.setTextColor(Color.parseColor("#7d7d7d"));
                        tvRule.setText((i + 1) + "." + result.get(i));
                        params.setMargins(0, DP2SPUtil.dip2px(ShopDetailsIndianaActivity.this, 16), 0, 0);
                        mLlRuleContainer.addView(tvRule, params);
                    }
                }
            }

        }.execute();

    }

}