package com.yssj.ui.activity.shopdetails;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;
import com.yssj.Constants;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.IndianaShopTopClickView;
import com.yssj.custom.view.IndianaShopTopClickView.OnCheckedLintener;
import com.yssj.custom.view.ItemView;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.custom.view.ScaleImageView;
import com.yssj.custom.view.ShowHoriontalView;
import com.yssj.custom.view.ShowHoriontalView.onClickLintener;
import com.yssj.custom.view.ToLoginDialog;
import com.yssj.data.YDBHelper;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.Shop;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelL;
import com.yssj.model.ComModelZ;
import com.yssj.ui.activity.IndianaGroupsDetActivity;
import com.yssj.ui.activity.PinTuanSnatchActivity;
import com.yssj.ui.activity.ShopImageActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.PublicToastDialog;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.WXminiAppUtil;
import com.yssj.utils.YCache;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/***
 * 拼团夺宝详情
 *
 * @author Administrator
 *
 */
public class ShopDetailsGroupIndianaActivity extends BasicActivity
        implements OnClickListener, onClickLintener, OnCheckedLintener {
    private LinearLayout rlBottom;// 控制联系客服与立即参与的显示与隐藏
    private TextView mHeadCountTime;// 倒计时显示
    private List<HashMap<String, Object>> mListTakeRecord;//参与记录
    private List<HashMap<String, Object>> mListGroupRecord;//
    private int mIndianaMoney;// 判断几元夺宝
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
    private int width, height, heights;
    private Shop shop;
    private ImageView img_fenx;
    private TextView tvRecord;
    private TextView tvOldShow;
    private TextView tvIndianaRule;//夺宝规则
    public boolean firstJoin = false;//是否是第一次参与夺宝
    private LinearLayout img_back;
    private ImageView img_cart;
    private RelativeLayout lin_contact, rrr;



    private int needs ; //还剩几人成团


    //    private int mNeedPeoplenum;
//    private int mXuNiPeople;
    private SAsyncTask<String, Void, HashMap<String, Object>> aa;
    private LinkedList<HashMap<String, Object>> dataList;
    private String[] images;// 普通商品图片
    private List<HashMap<String, String>> listTitle;
    private int check = 0;
    private LinearLayout rlTop;
    private TextView tv_cart_count;
    private Context context;
    private StickyListHeadersListView mListView;
    private MyAdapter adapter;
    private View headerView;
    public static ShopDetailsGroupIndianaActivity instance;
    private static boolean isShow;
    private String signShopDetail;// 判断是值为 "SignShopDetail" 从签到跳转到商品详情页面
    private String signValue;// 签到 商品编号
    private static int isPause = 0;
    private String mIssue_code = "";
    private TextView mDetailsSubmit;
    private double mShop_price;
    private double mShop_se_price;
    private Context mContext;
    private String shopPicUrl = "";
    private boolean mWxInstallFlag;
    private boolean isListFlag = false;//true代表拼团夺宝列表跳过来的

    private int mGroupPeopleCount = 3;//需要的成团人数
    private boolean mGuidTakeGroup = false;//引导页跳来的
    private String old_issue_code = "";//往期揭晓跳过来传的期号

    @Override
    protected void onPause() {
        super.onPause();
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
        }
    }

    private static class ItemViewHolder {
        ImageView mBai;
        LinearLayout mListRecord;// 参与记录
        LinearLayout llBottomShow;//最多显示300条提示
        RelativeLayout mTakeHead;
        View mView;
        RoundImageButton headPicture;
        TextView tvName;
        TextView tvTime;
        TextView tvCount, tvStartTime;
        TextView tv_people_name;//
        LinearLayout mLlMessage;// 详情
        View imageGroup;
        View shopItem;
        ItemView left;
        ItemView right;
        ImageView image;
        LinearLayout mLlRule;// 拼团详情
        LinearLayout mListGroup;
        LinearLayout mLlItemHead;
        LinearLayout mLlItemBottom;
        LinearLayout mShareWx;//微信分享
        LinearLayout mShareCircle;//朋友圈分享
        ImageView mHeadPic;
        TextView mTVNeedPeople;
        TextView mTvNoTakeNotice;
        LinearLayout mLlGroupItemBottom;
        View item_group_line;
        GridView mGridView;
        LinearLayout mHeadPicContainer;
        TextView mGroupNum;
        TextView mGroupStatus;
        TextView mWinNummber;//幸运号码

    }

//    private TextView mNeedPeople;

    private class MyAdapter extends BaseAdapter implements StickyListHeadersAdapter {
        int ruleCount = 1;
        double totalHeight;
        double ruleHight;
        double recordHight;
        double groupHight;
        double headHeight;

        @Override
        public int getCount() {
            int count = 0;
            if (check == 0) {// 详情
                count += images.length;
                if (dataList != null) {
                    count += dataList.size() % 2 == 0 ? dataList.size() / 2 : dataList.size() / 2 + 1;
                }
            } else if (check == 1) {// 参与记录
                if (mListTakeRecord != null && mListTakeRecord.size() > 0) {
                    count += mListTakeRecord.size();
                } else {
                    count++;
                }
            } else {// 拼团详情
                if (mListGroupRecord != null && mListGroupRecord.size() > 0) {
                    count += mListGroupRecord.size();
                } else {
                    count++;
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

        @SuppressWarnings("unused")
        @Override
        public View getView(int position, View v, ViewGroup arg2) {
            final ItemViewHolder vh;
            if (v == null) {
                v = LayoutInflater.from(ShopDetailsGroupIndianaActivity.this).inflate(R.layout.indiana_new_group_item, arg2,
                        false);
                vh = new ItemViewHolder();
                v.findViewById(R.id.lln).setBackgroundColor(Color.WHITE);
                vh.mBai = (ImageView) v.findViewById(R.id.indiana_bai);
                // 拼团详情
                vh.mLlRule = (LinearLayout) v.findViewById(R.id.indiana_ll_rule);
                vh.mListGroup = (LinearLayout) v.findViewById(R.id.indiana_listview_group);
                vh.mLlItemHead = (LinearLayout) v.findViewById(R.id.item_group_head);
                vh.mLlItemBottom = (LinearLayout) v.findViewById(R.id.item_group_bottom);
                vh.mShareWx = (LinearLayout) v.findViewById(R.id.ll_wxin);
                vh.mShareCircle = (LinearLayout) v.findViewById(R.id.ll_wxin_circle);
                vh.mHeadPic = (ImageView) v.findViewById(R.id.item_group_head_pic);
                vh.mTVNeedPeople = (TextView) v.findViewById(R.id.item_group_need_people);
                vh.mTvNoTakeNotice= (TextView) v.findViewById(R.id.item_group_no_take_notice);
                vh.mLlGroupItemBottom= (LinearLayout) v.findViewById(R.id.item_group_ll_bottom);
                vh.item_group_line=v.findViewById(R.id.item_group_line);
//                if (position == 0) {
//                    mNeedPeople = (TextView) v.findViewById(R.id.item_group_need_people);
//                }
                vh.mGridView = (GridView) v.findViewById(R.id.item_group_grid);
                vh.mHeadPicContainer = (LinearLayout) v.findViewById(R.id.item_group_container);
                vh.mGroupNum = (TextView) v.findViewById(R.id.item_group_num);
                vh.mGroupStatus = (TextView) v.findViewById(R.id.item_group_status);
                vh.mWinNummber = (TextView) v.findViewById(R.id.item_group_win_num);

                // 参与记录
                vh.mListRecord = (LinearLayout) v.findViewById(R.id.indiana_listview_record);
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
                vh.tv_people_name = (TextView) v.findViewById(R.id.item_indiana_head_people);
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

            if (check == 0) {// 图文详情
                vh.mView.setVisibility(View.GONE);
                vh.mTakeHead.setVisibility(View.GONE);
                vh.mBai.setVisibility(View.GONE);
                vh.mLlRule.setVisibility(View.GONE);
                vh.mLlMessage.setVisibility(View.VISIBLE);
                vh.mListRecord.setVisibility(View.GONE);
                if (position < images.length) {
                    vh.imageGroup.setVisibility(View.VISIBLE);
                    vh.shopItem.setVisibility(View.GONE);
                    vh.image.setTag(shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/"
                            + images[position] + "!450");

                    PicassoUtils.initImage(context, shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code()
                            + "/" + images[position] + "!450", vh.image);

                    final int x = position;
                    vh.image.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ShopDetailsGroupIndianaActivity.this, ShopImageActivity.class);
                            intent.putExtra("url",
                                    shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/" + images[x]);
                            intent.putExtra("signType", mIndianaMoney);
                            intent.putExtra("isIndiana", true);
                            intent.putExtra("groupIndiana", true);
                            intent.putExtra("code", code);
                            intent.putExtra("shop", shop);
                            intent.putExtra("ostatus", mOstatus);
                            intent.putExtra("my_num", my_num);
//                            intent.putExtra("firstJoin", firstJoin);
                            intent.putExtra("needCount", needCount);
                            intent.putExtra("issue_code", issue_code);
                            intent.putExtra("group_number", mGroupPeopleCount);
                            int needPeopleCount = shop.getActive_people_num() - shop.getInvolved_people_num() < 0 ? 0
                                    : shop.getActive_people_num() - shop.getInvolved_people_num();
                            intent.putExtra("need_people", needPeopleCount);
                            startActivityForResult(intent, 1080);
                        }
                    });

                    return v;
                }
                position = (position - images.length) * 2;
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
                vh.mListRecord.setVisibility(View.VISIBLE);
                if (mListTakeRecord != null && mListTakeRecord.size() > 0 && mListTakeRecord.size() - 1 == position && page == 30) {//最后一条时，显示最多只显示最近三百条
                    vh.llBottomShow.setVisibility(View.VISIBLE);
                } else {
                    vh.llBottomShow.setVisibility(View.GONE);
                }
                UserInfo info = YCache.getCacheUserSafe(ShopDetailsGroupIndianaActivity.this.context);

                PicassoUtils.initImage(context, info.getPic(), vh.headPicture);

                RoundImageButton headPicture;
                TextView tvName;
                TextView tvTime;
                TextView tvCount;
                // 填充数据
                if (mListTakeRecord != null && mListTakeRecord.size() > 0) {
                    vh.mListRecord.setVisibility(View.VISIBLE);
                    PicassoUtils.initImage(context, "" + mListTakeRecord.get(position).get("user_head"),
                            vh.headPicture);
                    vh.tvName.setText("" + mListTakeRecord.get(position).get("user_name") );//+ "的团"
                    vh.tvCount.setText("已建" + mListTakeRecord.get(position).get("num"));
                    SpannableStringBuilder builder = new SpannableStringBuilder(vh.tvCount.getText().toString());
                    ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#a8a8a8"));
                    builder.setSpan(redSpan, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    vh.tvCount.setText(builder);
                    vh.tv_people_name.setText("团次");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyy.MM.dd HH:mm");
                    Date date = new Date(Long.parseLong("" + mListTakeRecord.get(position).get("join_time")));
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
                    vh.mBai.getLayoutParams().height = (int) (totalHeight - DP2SPUtil.dp2px(context, 170));
                    vh.mBai.setVisibility(View.VISIBLE);
                }
            } else {// 拼团详情
                vh.mBai.setVisibility(View.GONE);
                vh.mView.setVisibility(View.GONE);
                vh.mTakeHead.setVisibility(View.GONE);
                vh.mLlRule.setVisibility(View.VISIBLE);
                vh.mLlMessage.setVisibility(View.GONE);
                vh.mListRecord.setVisibility(View.GONE);
                vh.mHeadPic.getLayoutParams().width = width;
                vh.mHeadPic.getLayoutParams().height = width / 2;
                PicassoUtils.initImage(mContext, "" + shop.getBanner(),
                        vh.mHeadPic);
                if (mListGroupRecord != null && mListGroupRecord.size() > 0) {
                    vh.mGroupNum.setText("" + (position + 1));
                    if ("0".equals(mListGroupRecord.get(position).get("issue_code"))) {
                        vh.mGroupStatus.setText("团，未满");
                    } else {
                        vh.mGroupStatus.setText("团，已满");
                    }
                    if (!"".equals(mListGroupRecord.get(position).get("u_code")) && !"0".equals(mListGroupRecord.get(position).get("u_code"))) {
                        vh.mWinNummber.setVisibility(View.VISIBLE);
                        vh.mWinNummber.setText("(幸运号码：" + mListGroupRecord.get(position).get("u_code") + ")");
                    } else {
                        vh.mWinNummber.setVisibility(View.GONE);
                    }
                }
                vh.mShareWx.setOnClickListener(new OnClickListener() {//分享微信
                    @Override
                    public void onClick(View v) {
                        if (!mWxInstallFlag) {
                            ToastUtil.showLongText(mContext, "您还未安装微信喔！");
                            return;
                        }
                        shareType = 0;
                        ShareUtil.addWXPlatform(mContext);
//                        getShopLink();
                        getShareTitleText();
                    }
                });

                vh.mShareCircle.setOnClickListener(new OnClickListener() {//分享朋友圈
                    @Override
                    public void onClick(View v) {

                        if (!mWxInstallFlag) {
                            ToastUtil.showLongText(mContext, "您还未安装微信喔！");
                            return;
                        }
                        shareType = 1;
                        ShareUtil.addWXPlatform(mContext);
//                        getShopLink();
                        getShareTitleText();
                    }
                });

                if (position == 0) {
                    vh.mLlItemHead.setVisibility(View.VISIBLE);
                    if(my_num<=0&&(mOstatus==1||mOstatus==2)){
                        vh.mLlGroupItemBottom.setVisibility(View.GONE);
                        vh.mTvNoTakeNotice.setVisibility(View.VISIBLE);
                        vh.item_group_line.setVisibility(View.GONE);
                    }else{
                        vh.mLlGroupItemBottom.setVisibility(View.VISIBLE);
                        vh.mTvNoTakeNotice.setVisibility(View.GONE);
                        vh.item_group_line.setVisibility(View.VISIBLE);
                    }

                } else {
                    vh.mLlItemHead.setVisibility(View.GONE);

                }

                int childViewCount = 3;
                if (mListGroupRecord != null && mListGroupRecord.size() > 0) {
                    vh.mLlItemBottom.setVisibility(View.VISIBLE);
                    if (mOstatus == 1 || mOstatus == 2) {//null != old_issue_code && !"".equals(old_issue_code)
                        vh.mTVNeedPeople.setText("0");
                    } else {
                        vh.mTVNeedPeople.setText("" + (mGroupPeopleCount - ((List<HashMap<String, String>>) mListGroupRecord.get(0).get("user")).size()));


                        needs = mGroupPeopleCount - ((List<HashMap<String, String>>) mListGroupRecord.get(0).get("user")).size();




                    }
                    if (position == mListGroupRecord.size() - 1) {
                        vh.mBai.getLayoutParams().height = DP2SPUtil.dp2px(context, 70);
                        vh.mBai.setVisibility(View.VISIBLE);
                    }
                    if ("0".equals(mListGroupRecord.get(position).get("issue_code"))) {
                        childViewCount = mGroupPeopleCount;
                    } else {
                        childViewCount = ((List<HashMap<String, String>>) mListGroupRecord.get(position).get("user")).size();
                    }
                    //填充数据
                    if (childViewCount < 5) {
                        vh.mGridView.setVisibility(View.GONE);
                        vh.mHeadPicContainer.setVisibility(View.VISIBLE);
                        vh.mHeadPicContainer.removeAllViews();

                        for (int i = 0; i < childViewCount; i++) {
                            View view = View.inflate(mContext, R.layout.item_group_grid, null);
                            TextView mTvShopName = (TextView) view.findViewById(R.id.item_group_shop_name);
                            RoundImageButton mPicHead = (RoundImageButton) view.findViewById(R.id.item_group_head);
                            ImageView mIvGroupHost = (ImageView) view.findViewById(R.id.item_group_host);
                            TextView mTvUserName = (TextView) view.findViewById(R.id.item_group_user_name);
                            List<HashMap<String, String>> userList = (List<HashMap<String, String>>) mListGroupRecord.get(position).get("user");
                            mTvShopName.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtil.showShortText(mContext, "快去分享邀请好友参与吧~");
                                }
                            });
                            if (i == 0) {
                                mIvGroupHost.setVisibility(View.VISIBLE);
                            } else {
                                mIvGroupHost.setVisibility(View.INVISIBLE);
                            }
                            if (i < userList.size()) {
                                mTvShopName.setVisibility(View.GONE);
                                mPicHead.setVisibility(View.VISIBLE);
//                                for (int j = 0; j < userList.size(); j++) {
                                HashMap<String, String> userMap = userList.get(i);
                                PicassoUtils.initImage(ShopDetailsGroupIndianaActivity.this.context, userMap.get("head"),
                                        mPicHead);
                                mTvUserName.setText(userMap.get("nickname"));
//                                }
                            } else {
                                mTvShopName.setVisibility(View.VISIBLE);
                                mPicHead.setVisibility(View.GONE);
                                mTvUserName.setVisibility(View.INVISIBLE);
                                mTvShopName.setText("谁要\n" + shop.getShop_name());
                            }
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            if (childViewCount == 2) {
                                params.setMargins(DP2SPUtil.dip2px(mContext, 20), 0, DP2SPUtil.dip2px(mContext, 20), 0);
                            } else if (childViewCount == 3) {
                                params.setMargins(DP2SPUtil.dip2px(mContext, 15), 0, DP2SPUtil.dip2px(mContext, 15), 0);
                            } else if (childViewCount == 4) {
                                params.setMargins(DP2SPUtil.dip2px(mContext, 10), 0, DP2SPUtil.dip2px(mContext, 10), 0);
                            }
                            params.gravity = Gravity.CENTER;
                            vh.mHeadPicContainer.addView(view, params);
                        }
                    } else {
                        vh.mGridView.setVisibility(View.VISIBLE);
                        vh.mHeadPicContainer.setVisibility(View.GONE);
                        GridAdapter mGridAdapter = new GridAdapter((List<HashMap<String, String>>) (mListGroupRecord.get(position).get("user")), position);
                        vh.mGridView.setAdapter(mGridAdapter);
                    }
                } else {
                    if (mOstatus == 1 || mOstatus == 2) {//null != old_issue_code && !"".equals(old_issue_code)
                        vh.mTVNeedPeople.setText("0");
                    } else {
                        vh.mTVNeedPeople.setText("" + (mGroupPeopleCount - 1));
                    }
                    vh.mLlItemBottom.setVisibility(View.GONE);
//                    vh.mBai.getLayoutParams().height = DP2SPUtil.dp2px(context, 80);
//                    vh.mBai.setVisibility(View.VISIBLE);

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

            }
            return v;
        }

        @Override
        public View getHeaderView(int position, View view, ViewGroup parent) {
            HeaderViewHolder vh;
            if (view == null) {
                vh = new HeaderViewHolder();
                view = LayoutInflater.from(ShopDetailsGroupIndianaActivity.this).inflate(R.layout.indiana_header_item,
                        parent, false);
                vh.topOne = (IndianaShopTopClickView) view.findViewById(R.id.top_one);
                vh.topOne.setText3();

                vh.topTwo = (ShowHoriontalView) view.findViewById(R.id.top_two);
                vh.topOne.setCheckLintener(ShopDetailsGroupIndianaActivity.this);
                vh.topTwo.setOnClickLintener(ShopDetailsGroupIndianaActivity.this);
                vh.title = (TextView) view.findViewById(R.id.title);
                vh.topOne.setBackgroundColor(Color.WHITE);
                vh.title.setBackgroundColor(Color.WHITE);
                vh.topTwo.setBackgroundColor(Color.WHITE);

                vh.topTwo.setList(listTitle);
                view.setTag(vh);
            } else {
                vh = (HeaderViewHolder) view.getTag();
            }
            mTopOne = vh.topOne;
            if (position < images.length) {
                vh.topOne.setVisibility(View.VISIBLE);
                vh.topOne.setIndex(check);
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


            vh.title.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtil.showShortText(ShopDetailsGroupIndianaActivity.this,"11111111111");
                }
            });



            return view;
        }

        @Override
        public long getHeaderId(int position) {
            if (check != 0) {
                return 0;
            }
            if (position < images.length) {

//                if (rlBottom.getVisibility() == View.GONE && isAnim == false) {
//                    rlBottom.clearAnimation();
//                    rlBottom.startAnimation(animationShow);
//                }
                return 0;
            } else {
//                if (rlBottom.getVisibility() == View.VISIBLE && isAnim == false) {
//                    rlBottom.clearAnimation();
//                    rlBottom.startAnimation(animationGone);
//                }
                return 1;
            }

        }
    }

    IndianaShopTopClickView mTopOne;

    private static class HeaderViewHolder {
        IndianaShopTopClickView topOne;
        ShowHoriontalView topTwo;
        TextView title;
    }

//    private Animation animationGone;
//    private Animation animationShow;

    /**
     * 拼团夺宝 开团
     *
     * @param shop_code
     */
    private void rollTrea(final String shop_code) {
        new SAsyncTask<Void, Void, ReturnInfo>(((FragmentActivity) context)) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context,
                                                Void... params) throws Exception {
                return ComModelL.getRollTrea(context, shop_code, "1", "");
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         ReturnInfo result, Exception e) {
                super.onPostExecute(context, result, e);
                if (!TextUtils.isEmpty(code) || !TextUtils.isEmpty(mShop_code)) {
                    queryShopDetails();
                }

            }

        }.execute();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addDetailsActivity(this);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.indiana_activity_shop_details);
        shareWaitDialog = new PublicToastDialog(this, R.style.DialogStyle1, "");
        number_sold = getIntent().getStringExtra("number_sold"); // 取出是否抢完的值
        isListFlag = getIntent().getBooleanExtra("indianaGroups", false);
        mGuidTakeGroup = getIntent().getBooleanExtra("isCanTuan", false);
        getWindownPixes();
        context = this;
        mContext = this;
        check = 2;
        initView();
        try {
            // // 是否安装了微信
            if (WXcheckUtil.isWeChatAppInstalled(this)) {
                mWxInstallFlag = true;
            } else {
                mWxInstallFlag = false;
            }
        } catch (Exception e) {
        }
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
            HashMap<String, String> listTitle_temp1 = listTitle.get(a);
            HashMap<String, String> listTitle_temp2 = listTitle.get(b);
            if (b > a) {// 上衣在外套前面时就调换
                listTitle.remove(a);
                listTitle.add(a, listTitle_temp2);
                listTitle.remove(b);
                listTitle.add(b, listTitle_temp1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        code = getIntent().getStringExtra("valueDuo");
        mShop_code = getIntent().getStringExtra("shop_code");
        if (mGuidTakeGroup) {
            rollTrea(mShop_code);
        }
        old_issue_code = getIntent().getStringExtra("old_issue_code");
        mIndianaMoney = getIntent().getIntExtra("signType", 2);
        mIssue_code = getIntent().getStringExtra("issue_code");
        virtual_num = getIntent().getStringExtra("virtual_num");
        issue_code = getIntent().getStringExtra("issue_code");
        signShopDetail = getIntent().getStringExtra("SignShopDetail");
        signValue = getIntent().getStringExtra("value");
        instance = this;
        if (!mGuidTakeGroup) {
            if (!TextUtils.isEmpty(code) || !TextUtils.isEmpty(mShop_code)) {
                queryShopDetails();
            }
        }
    }

    private boolean isAnim = false;

    private String code;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.activityStack.remove(this);
        isPause = 1;
        instance = null;
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

    private void initView() {
        mDetailsSubmit = (TextView) findViewById(R.id.indiana_details_submit);
        rlBottom = (LinearLayout) findViewById(R.id.ray_bottom);
        img_cart = (ImageView) findViewById(R.id.img_cart);
        img_cart.setOnClickListener(this);

        rrr = (RelativeLayout) findViewById(R.id.rrr);
        findViewById(R.id.ray_bottom).setBackgroundColor(Color.WHITE);
        tv_cart_count = (TextView) findViewById(R.id.tv_cart_count);// 购物车计数
        lin_contact = (RelativeLayout) findViewById(R.id.lin_contact);
        lin_contact.setOnClickListener(this);

        img_fenx = (ImageView) findViewById(R.id.img_fenx);// 分享
        img_fenx.setOnClickListener(this);
        tvOldShow = (TextView) findViewById(R.id.tv_old_show);
        tvRecord = (TextView) findViewById(R.id.tv_record);
        tvIndianaRule = (TextView) findViewById(R.id.indiana_group_rule);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(width - (DP2SPUtil.dp2px(this, 85)), width * 9 / 6 - (DP2SPUtil.dp2px(this, 115)), DP2SPUtil.dp2px(this, 10), 0);
//        params.topMargin=width*9/6-(DP2SPUtil.dp2px(this,115));
        tvIndianaRule.setLayoutParams(params);
        tvIndianaRule.setVisibility(View.VISIBLE);
        tvIndianaRule.setOnClickListener(this);
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

//        animationGone = AnimationUtils.loadAnimation(ShopDetailsGroupIndianaActivity.this, R.anim.shop_bottom_gone);
//        animationShow = AnimationUtils.loadAnimation(ShopDetailsGroupIndianaActivity.this, R.anim.shop_bottom_show);

//        animationGone.setAnimationListener(new AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//                isAnim = true;
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                rlBottom.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        rlBottom.setVisibility(View.GONE);
//                    }
//                });
//                isAnim = false;
//            }
//        });
//        animationShow.setAnimationListener(new AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//                isAnim = true;
//                rlBottom.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                isAnim = false;
//            }
//        });
    }

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

            ShopDetailsGroupIndianaActivity.this.getSharedPreferences("YSSJ_yf", Context.MODE_PRIVATE).edit()
                    .putBoolean("isGoDetail", true).commit();
            if (YJApplication.instance.isLoginSucess()) {
                addScanDataTo((String) posMap.get("shop_code"));
            }
            Intent intent = new Intent(ShopDetailsGroupIndianaActivity.this, ShopDetailsActivity.class);
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
        new SAsyncTask<Void, Void, ReturnInfo>(ShopDetailsGroupIndianaActivity.this) {

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
                loginDialog = new ToLoginDialog(ShopDetailsGroupIndianaActivity.this);
            }
            loginDialog.setRequestCode(235);
            loginDialog.show();
            return;
        }

        switch (view.getId()) {
            case R.id.lin_contact: {
//
//                Intent intent = new Intent(this, KeFuActivity.class);
//                intent.putExtra("userId", SharedPreferencesUtil.getStringData(context, "kefuNB", "0"));
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("shop", shop);
//                if (null == shop) {
//                    ToastUtil.showShortText(context, "操作无效");
//                    return;
//                }
//                intent.putExtras(bundle);
//                intent.putExtra("isDuoBao", "isDuoBao");
//                startActivity(intent);

                WXminiAppUtil.jumpToWXmini(this);


            }
            break;
            // TODO:
            case R.id.indiana_details_submit://立即参与
//                TongJiUtils.yunYunTongJi("duobao", 1002, 10, ShopDetailsGroupIndianaActivity.this);
                if (needCount <= 0) { //剩余参与人数为0
                    ToastUtil.showShortText(ShopDetailsGroupIndianaActivity.this, "当期活动已结束，正在开奖，请稍后再来。");
                    return;
                }
                if (mOstatus == 4) {
                    ToastUtil.showShortText(ShopDetailsGroupIndianaActivity.this, "当期活动已结束，请留意下期哦。");
                } else {  //去我的夺宝团
                    toMyIndianaGroup();
                }

                break;
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.tv_old_show://往期揭晓
                Intent intent = new Intent(mContext, PinTuanSnatchActivity.class);
                intent.putExtra("index", 1);
                startActivity(intent);
                ((FragmentActivity)
                        mContext).overridePendingTransition(R.anim.slide_left_in,
                        R.anim.slide_match);

                break;
            case R.id.tv_record://夺宝记录
                Intent intent3 = new Intent(mContext, PinTuanSnatchActivity.class);
                intent3.putExtra("index", 0);
                startActivity(intent3);
                ((FragmentActivity)
                        mContext).overridePendingTransition(R.anim.slide_left_in,
                        R.anim.slide_match);
                break;
            case R.id.indiana_group_rule:
                getIndianaRuleDialog();
                break;
            default:
                break;
        }
    }

    public static PublicToastDialog shareWaitDialog = null;

    @Override
    public void onBackPressed() {
        AppManager.getAppManager().removeDetailsActivity((Activity) mContext);
        instance = null;
        if (shop != null) {
            setResult(-1, new Intent().putExtra("isLike", shop.getLike_id() == -1 ? 0 : 1));
        }
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        if (aa != null && !aa.isCancelled()) {
            aa.cancel(true);
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
                List<HashMap<String, Object>> list = ComModelZ.queryGroupIndianaTakeRecord(context, shop.getShop_code(),
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
                } else {//
                    if (list != null && list.size() > 0) {
                        if (mListTakeRecord == null) {
                            mListTakeRecord = new ArrayList<HashMap<String, Object>>();
                        }
                        mListTakeRecord.addAll(list);
                    } else {
                        if (page > 1) {
                            Toast.makeText(ShopDetailsGroupIndianaActivity.this, "已经到底了",
                                    Toast.LENGTH_SHORT).show();
                        }
                        isCheck = true;
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            protected boolean isHandleException() {
                return true;
            }
        }.execute();

    }


    // TODO:
    // 查询拼团记录
    private int rowsGroup = 10, pageGroup = 1;
    private boolean isCheckGroup = false;

    private void queryIndianaGroupRecord() {
        isCheckGroup = true;
        new SAsyncTask<Void, Void, List<HashMap<String,
                Object>>>((FragmentActivity) context, null, R.string.wait) {
            @Override
            protected List<HashMap<String, Object>> doInBackground(FragmentActivity
                                                                           context, Void... params)
                    throws Exception {
                List<HashMap<String, Object>> list = ComModelZ.queryGroupIndianaGroupList(context, shop.getShop_code(),
                        shop.getShop_batch_num(), "" + pageGroup, "" + rowsGroup, mOstatus);
                return list;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         List<HashMap<String, Object>> list, Exception e) {

                isCheckGroup = false;
                if (e != null) {// 查询异常
                    Toast.makeText(context, "连接超时，请重试", Toast.LENGTH_LONG).show();
                    pageGroup--;
                } else {
                    if (list != null && list.size() > 0) {
                        if (mListGroupRecord == null) {
                            mListGroupRecord = new ArrayList<HashMap<String, Object>>();
                        }
                        mListGroupRecord.addAll(list);
                        isCheckGroup = true;
                    } else {
                        if (pageGroup > 1) {
//                            Toast.makeText(ShopDetailsGroupIndianaActivity.this, "已经到底了",
//                                    Toast.LENGTH_SHORT).show();
                        }
//                        isCheckGroup = true;
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            protected boolean isHandleException() {
                return true;
            }
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
                LoadingDialog.show(ShopDetailsGroupIndianaActivity.this);
            }

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                HashMap<String, Object> map;
                if (code != null && !TextUtils.isEmpty(code)) {
                    map = ComModel.queryIndianaShopDetails(ShopDetailsGroupIndianaActivity.this,
                            YCache.getCacheToken(ShopDetailsGroupIndianaActivity.this), params[0], attrDateStr, "", old_issue_code);

                } else {
                    map = ComModel.queryIndianaShopDetails(ShopDetailsGroupIndianaActivity.this,
                            YCache.getCacheToken(ShopDetailsGroupIndianaActivity.this), "", attrDateStr, params[0], old_issue_code);
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
//                        firstJoin = (map.get("countTrea") + "").equals("0");
                        //
                        if (mGuidTakeGroup) {
                            ToastUtil.showShortText(mContext, "参团成功~");
                        }
                        mWinnerHeadPic = (String) map.get("in_head");
                        mWinnerName = (String) map.get("in_name");
                        mIn_code = (String) map.get("in_code");
                        in_uid = (String) map.get("in_uid");
                        rrr.setBackgroundColor(Color.WHITE);
//                        mNeedPeoplenum = (Integer) map.get("num");
//                        mXuNiPeople = (Integer) map.get("virtual_num");
                        shop = (Shop) map.get("shop");
                        if (null != shop) {
                            mGroupPeopleCount = shop.getGroup_number();
                        }
                        if (isCheckGroup == false) {
                            queryIndianaGroupRecord();
                        }
                        issue_code = "" + shop.getShop_batch_num();
                        mOstatus = (Integer) map.get("ostatus");
                        mOtime = (Long) map.get("otime");
                        my_num = (Integer) map.get("my_num");

                        order_status = (Integer) map.get("order_status");

                        codes = (List<String>) map.get("codes");
                        titleCheck = shop.getType1();
                        if (titleCheck >= listTitle.size()) {
                            titleCheck = 0;
                        }

                        // TODO:

                        headerView = LayoutInflater.from(ShopDetailsGroupIndianaActivity.this)
                                .inflate(R.layout.indiana_group_header, null);
                        // 查找id
                        LinearLayout mLlGoon = (LinearLayout) headerView.findViewById(R.id.indiana_head_llgoon);// 控制进行中显示
                        LinearLayout mLlResult = (LinearLayout) headerView.findViewById(R.id.indiana_head_llresult);// 控制结果显示
                        TextView mShaiDan = (TextView) headerView.findViewById(R.id.indiana_head_shaidan);
                        mShaiDan.setText("去领奖");
//                        TextView tvToGetAward = (TextView) headerView.findViewById(R.id.indiana_head_to_get_award);//去领奖
//                        tvToGetAward.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                toPay();//去领奖
//                            }
//                        });
                        mShaiDan.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
//                                Intent intent = new Intent(ShopDetailsGroupIndianaActivity.this, ShaiDanActivity.class);
//                                intent.putExtra("in_code", mIn_code);
//                                intent.putExtra("shop_code", shop.getShop_code());
//                                intent.putExtra("shop_name", shop.getShop_name());
//                                intent.putExtra("issue_code", issue_code);
//                                startActivity(intent);
                                toPay();//去领奖
                            }
                        });
                        mHeadCountTime = (TextView) headerView.findViewById(R.id.indiana_head_countdown);
                        headerView.findViewById(R.id.indiana_head_topimg).getLayoutParams().height = width * 9 / 6;
                        heights = width * 9 / 6;
                        width = ShopDetailsGroupIndianaActivity.this.getResources().getDisplayMetrics().widthPixels;
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
                        TextView mTvWinnerTakeNum = (TextView) headerView.findViewById(R.id.indiana_head_winner_take_num);
                        RoundImageButton mTvWinnerPic = (RoundImageButton) headerView
                                .findViewById(R.id.indiana_head_headpicture);// 获奖者头像
                        TextView mTvNumbers = (TextView) headerView.findViewById(R.id.indiana_head_numbers);// 中奖号码
                        needCount = (shop.getActive_people_num() - shop.getInvolved_people_num()) < 0 ? 0
                                : shop.getActive_people_num() - shop.getInvolved_people_num();

                        // 进行中，还是已揭晓\
                        if (mOstatus == 0 || mOstatus == 1) {
                            mTvState.setText("进行中");
                            mLlGoon.setVisibility(View.VISIBLE);
                            mLlResult.setVisibility(View.GONE);
                            mDetailsSubmit.setText("立即参与");
                            mDetailsSubmit.setBackgroundColor(Color.parseColor("#ff3f8c"));
                            mDetailsSubmit.setClickable(true);
                            mDetailsSubmit.setOnClickListener(ShopDetailsGroupIndianaActivity.this);
                            if (my_num <= 0) {
                                headerView.findViewById(R.id.indiana_head_nonotice).setVisibility(View.VISIBLE);
                                headerView.findViewById(R.id.indiana_head_yesnotice).setVisibility(View.GONE);

//
//                                if (firstJoin) {
//                                    mDetailsSubmit.setText("首次1分钱");
//                                } else {
//                                    mDetailsSubmit.setText("立即参与");
//                                }
                                mDetailsSubmit.setBackgroundColor(Color.parseColor("#ff3f8c"));
                                mDetailsSubmit.setClickable(true);
                                mDetailsSubmit.setOnClickListener(ShopDetailsGroupIndianaActivity.this);
                            } else {
                                headerView.findViewById(R.id.indiana_head_nonotice).setVisibility(View.GONE);
                                headerView.findViewById(R.id.indiana_head_yesnotice).setVisibility(View.VISIBLE);
                                ((TextView) headerView.findViewById(R.id.indiana_head_numtop)).setText("" + my_num);// 我的参与次数
//                                StringBuffer sbf = new StringBuffer();
//                                for (int i = 0; i < codes.size(); i++) {
//                                    String str = codes.get(i);
//                                    String[] strs = str.split(",");
//                                    if (i != codes.size() - 1) {
//                                        for (int j = 0; j < strs.length; j++) {
//                                            sbf.append(strs[j]).append("、");
//                                        }
//                                    } else {
//                                        for (int j = 0; j < strs.length; j++) {
//                                            if (j != strs.length - 1) {
//                                                sbf.append(strs[j]).append("、");
//                                            } else {
//                                                sbf.append(strs[j]);
//                                            }
//                                        }
//                                    }
//                                }
//                                ((TextView) headerView.findViewById(R.id.indiana_head_numstop))
//                                        .setText("参与号码：" + sbf.toString());// 我的参与号码
//                                mDetailsSubmit.setBackgroundColor(Color.parseColor("#c5c5c5"));
//                                mDetailsSubmit.setClickable(false);
                                mDetailsSubmit.setText("再次参与");


                                mDetailsSubmit.setBackgroundColor(Color.parseColor("#ff3f8c"));
                                mDetailsSubmit.setClickable(true);
                                mDetailsSubmit.setOnClickListener(ShopDetailsGroupIndianaActivity.this);
                            }
                            //剩余参与次数为0，且未结束
                            if (needCount == 0) {
                                mDetailsSubmit.setText("正在开奖");
                            }
                        }
//                        else if (mOstatus == 2) {
//                            mLlGoon.setVisibility(View.VISIBLE);
//                            mLlResult.setVisibility(View.GONE);
////                            mHeadCountTime.setText("未满足人数退款");
//                            mTvState.setText("已揭晓");
//                            mTvState.setTextColor(Color.parseColor("#ff3f8c"));
//                            mDetailsSubmit.setBackgroundColor(Color.parseColor("#c5c5c5"));
//                            mDetailsSubmit.setClickable(false);
//                            mDetailsSubmit.setText("已结束");
//                        }
                        else if (mOstatus == 2) {
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
//                            StringBuffer sbf2 = new StringBuffer();
//                            for (int i = 0; i < codes.size(); i++) {
//                                if (i != codes.size() - 1) {
//                                    sbf2.append(codes.get(i)).append("、");
//                                } else {
//                                    sbf2.append(codes.get(i));
//                                }
//                            }
//                            ((TextView) headerView.findViewById(R.id.indiana_head_numsbottom))
//                                    .setText("" + sbf2.toString());// 我的参与号码
                            mTvWinnerName.setText("" + mWinnerName);
                            mTvWinnerTakeNum.setText("" + map.get("in_sum"));
                            PicassoUtils.initImage(ShopDetailsGroupIndianaActivity.this.context, mWinnerHeadPic,
                                    mTvWinnerPic);

                            UserInfo user = YCache.getCacheUser(context);
                            if (!"".equals(in_uid) && in_uid != null && !"null".equals(in_uid)) {
                                if (user.getUser_id() == Integer.parseInt(in_uid)) {
                                    headerView.findViewById(R.id.indiana_head_numbottom).setVisibility(View.GONE);
                                    headerView.findViewById(R.id.indiana_head_tv).setVisibility(View.GONE);
                                    ((TextView) headerView.findViewById(R.id.indiana_head_get_win_notice)).setText("恭喜你，中奖了！");

                                    mShaiDan.setVisibility(View.VISIBLE);
//                                    tvToGetAward.setVisibility(View.VISIBLE);
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
                                .setText("¥" + new DecimalFormat("#0.0").format( Math.round(shop.getShop_se_price()*10)*0.1d));
                        mShop_se_price = shop.getShop_se_price();
                        // 原价
                        String shop_price = "¥" + new DecimalFormat("#0.0").format(shop.getShop_price());
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
                        mHeadCountTime.setText("已有" + shop.getInvolved_people_num() + "团正在参与");
                        int a = (shop.getActive_people_num() - shop.getInvolved_people_num()) < 0 ? 0
                                : shop.getActive_people_num() - shop.getInvolved_people_num();
                        mNeedPeople.setText("" + a);
                        ProgressBar mProgressBar = (ProgressBar) headerView.findViewById(R.id.indiana_head_progressbar);
                        int max = shop.getActive_people_num();
                        int progress = shop.getInvolved_people_num();
                        mProgressBar.setMax(max);
                        mProgressBar.setProgress(progress);
//                        mProgressBar.setProgress(max - current);
                        String def_pic = shop.getDef_pic();
                        if (!TextUtils.isEmpty(def_pic)) {
                            shopPicUrl = shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/" + def_pic
                                    + "!180";
                            PicassoUtils.initImage(instance,
                                    shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/" + def_pic
                                            + "!450",
                                    (ScaleImageView) headerView.findViewById(R.id.indiana_head_topimg));

                            headerView.findViewById(R.id.indiana_head_topimg).setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(ShopDetailsGroupIndianaActivity.this,
                                            ShopImageActivity.class);
                                    intent.putExtra("url", shop.getShop_code().substring(1, 4) + "/"
                                            + shop.getShop_code() + "/" + shop.getDef_pic());
                                    intent.putExtra("signType", mIndianaMoney);
                                    intent.putExtra("isIndiana", true);
                                    intent.putExtra("groupIndiana", true);
                                    intent.putExtra("code", code);
                                    intent.putExtra("shop", shop);
                                    intent.putExtra("ostatus", mOstatus);
                                    intent.putExtra("my_num", my_num);
//                                    intent.putExtra("firstJoin", firstJoin);
                                    intent.putExtra("needCount", needCount);
                                    intent.putExtra("issue_code", issue_code);
                                    intent.putExtra("group_number", mGroupPeopleCount);
                                    int needPeopleCount = shop.getActive_people_num() - shop.getInvolved_people_num() < 0 ? 0
                                            : shop.getActive_people_num() - shop.getInvolved_people_num();
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
                                        LogYiFu.e("check-------", "" + check);
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
                                        if (mTopOne != null) {
//                                            mTopOne.get
                                            mTopOne.setIndex(check);
                                        }
                                        LogYiFu.e("check*****", "" + check);
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
//                                            if (rlBottom.getVisibility() == View.VISIBLE && isAnim == false) {
//                                                rlBottom.clearAnimation();
//                                                rlBottom.startAnimation(animationGone);
//                                            }
                                        } else if (newPosition < myposition) {
//                                            if (rlBottom.getVisibility() == View.GONE && isAnim == false) {
//                                                rlBottom.clearAnimation();
//                                                rlBottom.startAnimation(animationShow);
//                                            }
                                        }
                                        break;
                                    case SCROLL_STATE_IDLE:
                                        LogYiFu.e("check~~~~~~~~", "" + check);
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
                                                    if (page < 31) {
                                                        queryIndianaTakeRecord();
                                                    }
                                                }
                                            } else if (check == 2) {
                                                if (isCheckGroup == false) {
                                                    pageGroup++;
                                                    queryIndianaGroupRecord();
                                                }

                                            }
                                        }

                                        if (isShopTitle) {
                                            break;
                                        }

//                                        if (rlBottom.getVisibility() == View.GONE && isAnim == false) {
//                                            rlBottom.clearAnimation();
//                                            rlBottom.startAnimation(animationShow);
//                                        }

                                        break;
                                }
                            }

                            @Override
                            public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
                                if (mTopOne != null) {
//                                            mTopOne.get
                                    mTopOne.setIndex(check);
                                }
                                int perHeight = heights / 100;
                                int currentY = 0;
                                aa2 = (ImageButton) findViewById(R.id.imgbtn_left_icon);
                                img_cart_top = (ImageView) findViewById(R.id.img_cart);
                                img_fenx_top = (ImageView) findViewById(R.id.img_fenx);

								/* 滚动title渐变的效果 */
                                if (arg1 == 0) {// 当前第一位显示为1
                                    View childAt = arg0.getChildAt(0);// 这个是headerView
                                    if (childAt != null) {
                                        currentY = childAt.getTop();
                                    }

                                } else if (arg1 > 0) {
                                    currentY = heights;
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

            @Override
            protected boolean isHandleException() {
                return true;
            }
        };
        if (code != null && !TextUtils.isEmpty(code)) {
            aa.execute(code);
        } else {
            aa.execute(mShop_code);
        }
    }

    private int titleCheck = 0;// 当前导航栏选择

    @Override
    public void myOnClick(View v) {
        mType = 1;
        index = 1;
        titleCheck = v.getId();
        initData(v.getId(), index + "");
        if (mListView.getFirstVisiblePosition() > images.length) {
            mListView.setSelection(images.length + 1);
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
                        Toast.makeText(ShopDetailsGroupIndianaActivity.this, "已经到底了", Toast.LENGTH_SHORT).show();
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
                page = 1;
                queryIndianaTakeRecord();
            }
        }
        if (check == 2) {
            if ((mListGroupRecord == null || mListGroupRecord.size() == 0) && isCheckGroup == false) {
                pageGroup = 1;
                queryIndianaGroupRecord();
            }
        }
        if (mListView.getFirstVisiblePosition() > 0) {
            mListView.setSelection(1);
        }
//        adapter.notifyDataSetChanged();
    }

    private String number_sold;

    // TODO:
    @Override
    protected void onResume() {
        super.onResume();
        isPause = 0;
        List<String> taskMap = YJApplication.instance.getTaskMap();
        if (taskMap == null) {
            taskMap = new ArrayList<String>();
        }

    }

    // 选择支付弹窗
    private void toMyIndianaGroup() {
        Intent intent = new Intent(mContext, IndianaGroupsDetActivity.class);
        intent.putExtra("shop_code", shop.getShop_code());
        intent.putExtra("issue_code", issue_code);
        intent.putExtra("banner", shop.getBanner());
        intent.putExtra("shop_name", shop.getShop_name());
        intent.putExtra("shop", shop);
        intent.putExtra("group_number", mGroupPeopleCount);
        intent.putExtra("is_ago", mOstatus);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
    }


    /**
     * 去领奖
     */
    private void toPay() {
        Intent intent = new Intent(ShopDetailsGroupIndianaActivity.this, SubmitDuobaoOrderActivity.class);
        String pic = YUrl.imgurl + shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/"
                + shop.getDef_pic();
        intent.putExtra("pic", pic);// 图片
        intent.putExtra("signType", mIndianaMoney);
        intent.putExtra("valueDuo", code);
        intent.putExtra("code", shop.getShop_code());
        intent.putExtra("shop_se_price", mShop_se_price);// 现价
        intent.putExtra("flag", "0");// 现价
        intent.putExtra("shop_price", mShop_price);// 原价
        intent.putExtra("name", shop.getShop_name());// 商品名
        intent.putExtra("shop_num", 1);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
    }

    private void getIndianaRuleContent(final LinearLayout container) {
        new SAsyncTask<Void, Void, List<String>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected List<String> doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModelZ.getIndianaRuleText("ptdbgz");
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null && result.size() > 0) {
                    container.removeAllViews();
                    for (int i = 0; i < result.size(); i++) {

                        TextView tvRule = new TextView(ShopDetailsGroupIndianaActivity.this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        tvRule.setTextSize(14);
                        tvRule.setTextColor(Color.parseColor("#7d7d7d"));
                        tvRule.setText((i + 1) + "." + result.get(i));
                        if (i != 0) {
                            params.setMargins(0, DP2SPUtil.dip2px(ShopDetailsGroupIndianaActivity.this, 16), 0, 0);
                        }
                        container.addView(tvRule, params);
                    }
                }
            }

        }.execute();

    }

    Dialog dialog;

    private void getIndianaRuleDialog() {
        if (dialog == null) {
            dialog = new Dialog(context, R.style.DialogQuheijiao2);
            dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
            dialog.setCanceledOnTouchOutside(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            View view = View.inflate(context, R.layout.dialog_indiana_group_rule, null);
            LinearLayout llContainer = (LinearLayout) view.findViewById(R.id.indiana_ll_container);
            if (llContainer.getChildCount() <= 0) {
                getIndianaRuleContent(llContainer);
            }
            TextView icon_close = (TextView) view.findViewById(R.id.dialog_colse);
            icon_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setContentView(view);
        }
        dialog.show();
    }


    private Intent weixinShareIntent;// 实现对微信好友的分享：
    private Intent wXinShareIntent;// 实现对微信朋友圈的分享：
    private int shareType;//0分享微信，1分享朋友圈

    private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);

    public void getShareTitleText() {
        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModelZ.getShareGroupTitleContent();
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null) {
                 String   mTitle = result.get("title");
                    String text=result.get("text");

                    UserInfo user = YCache.getCacheUserSafe(context);
//                    mTitle = mTitle.replaceFirst("\\$\\{replace\\}", ""+mGroupPeopleCount);
//                    mTitle = mTitle.replaceFirst("\\$\\{replace\\}", ""+new DecimalFormat("#0.0").format( Math.round(shop.getShop_se_price()*10)*0.1d));
//                    mTitle = mTitle.replaceFirst("\\$\\{replace\\}", ""+shop.getShop_name());
//                    text = text.replaceFirst("\\$\\{replace\\}", ""+user.getNickname());



                    mTitle = mTitle.replaceFirst("\\$\\{replace\\}", ""+needs);
                    mTitle = mTitle.replaceFirst("\\$\\{replace\\}", ""+shop.getShop_name());

                    text = text.replaceFirst("\\$\\{replace\\}", ""+user.getNickname());
                    text = text.replaceFirst("\\$\\{replace\\}", ""+needs);



                    share(mTitle,text);
                }
            }

        }.execute();
    }



    /**
     * 分享
     */
    private void share(String shareTitle,String shareContent) {//String shareTitle, String shareContent, String sharePic

//        衣蝠特惠，X人成团就有机会，XXX元团购XXXXXX（X部分写活：包括人数、团购价格、团购的商品名称）
//        XX成功开团，现在报名，马上参团！（XX抓取分享用户的昵称）
        String link = YUrl.YSS_URL_ANDROID_H5 + "view/activity/signDetail.html?r=" + YCache.getCacheUser(mContext).getUser_id()
                + "&i_c=" + issue_code + "&s_c=" + shop.getShop_code();

//        UserInfo user = YCache.getCacheUserSafe(context);
//        String shareTitle = "衣蝠特惠，" + mGroupPeopleCount + "人成团就有机会，" +new DecimalFormat("#0.0").format( Math.round(shop.getShop_se_price()*10)*0.1d)  + "元团购" + shop.getShop_name();
//        String shareContent = user.getNickname() + "成功开团，现在报名，马上参团！";
        wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
        weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());
//        String link = YUrl.YSS_URL_ANDROID_H5 + "view/activity/pack.html?realm=" + YCache.getCacheUser(mContext).getUser_id() + "&r_code=";
        UMImage umImage = new UMImage(mContext, YUrl.imgurl + shopPicUrl);

        if (shareType == 1) {// 微信好友朋友圈
            SharedPreferencesUtil.saveStringData(mContext, "messageSubSub", shareTitle);
            ShareUtil.setShareContent(mContext, umImage, shareTitle, link);
            performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
        } else if (shareType == 0) {// 微信好友
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

                } else {

                }
                SocializeConfig.getSocializeConfig().cleanListeners();// 清空友盟回调监听器
                // 避免任务接口多次被调用


            }
        });
    }

    public class GridAdapter extends BaseAdapter {
        private List<HashMap<String, String>> mGridList = new ArrayList<HashMap<String, String>>();
        private int currentPosition;

        public GridAdapter(List<HashMap<String, String>> mGridList, int currentPosition) {
            this.mGridList = mGridList;
            this.currentPosition = currentPosition;
        }

        public int getCount() {
            if ("0".equals(mListGroupRecord.get(currentPosition).get("issue_code"))) {
                return mGroupPeopleCount;
            } else {
                return mGridList.size();
            }
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            GroupItemView viewHolder;

            if (convertView == null) {
                viewHolder = new GroupItemView();
                convertView = View.inflate(mContext, R.layout.item_group_grid, null);
                viewHolder.mTvShopName = (TextView) convertView.findViewById(R.id.item_group_shop_name);
                viewHolder.mPicHead = (RoundImageButton) convertView.findViewById(R.id.item_group_head);
                viewHolder.mIvGroupHost = (ImageView) convertView.findViewById(R.id.item_group_host);
                viewHolder.mTvUserName = (TextView) convertView.findViewById(R.id.item_group_user_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (GroupItemView) convertView.getTag();
            }
            if (position == 0) {
                viewHolder.mIvGroupHost.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mIvGroupHost.setVisibility(View.INVISIBLE);
            }
            if (position < mGridList.size()) {
                viewHolder.mTvShopName.setVisibility(View.GONE);
                viewHolder.mPicHead.setVisibility(View.VISIBLE);
                viewHolder.mTvUserName.setText(mGridList.get(position).get("nickname"));
                PicassoUtils.initImage(context, mGridList.get(position).get("head"), viewHolder.mPicHead);
            } else {
                viewHolder.mTvShopName.setVisibility(View.VISIBLE);
                viewHolder.mPicHead.setVisibility(View.GONE);
                viewHolder.mTvShopName.setText("谁要\n" + shop.getShop_name());
                viewHolder.mTvUserName.setVisibility(View.INVISIBLE);
                viewHolder.mTvShopName.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showShortText(mContext, "快去分享邀请好友参与吧~");
                    }
                });
            }

            return convertView;
        }

        class GroupItemView {
            TextView mTvShopName;
            RoundImageButton mPicHead;
            ImageView mIvGroupHost;
            TextView mTvUserName;
        }
    }
}