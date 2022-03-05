package com.yssj.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.custom.view.NewGroupsItemView;
import com.yssj.entity.Choujiang20Data;
import com.yssj.entity.DJKdetail;
import com.yssj.entity.NewPTcountData;
import com.yssj.entity.NewPTdetailShop;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.shopdetails.PaymentActivity;
import com.yssj.ui.activity.vip.MyVipListActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.OneBuyGroupsResultDialog;
import com.yssj.ui.dialog.OneBuyGroupsShareDialog;
import com.yssj.ui.dialog.OneBuyGroupsShareHasMPKDialog;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.DateUtil;
import com.yssj.utils.DialogUtils;
import com.yssj.utils.GlideUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/***
 * 一元购  拼团详情
 */
public class OneBuyGroupsDetailsActivity extends BasicActivity implements OnClickListener {

    @Bind(R.id.iv_hongbao)
    ImageView ivHongbao;
    private View img_back;
    private GroupsAdapter mAdapter;
    private ListView r_list_view;
    public int width;
    public int height;
    private View nodataView;
    private TextView tv_no_data;
    //    private View bottomView;
    private TextView bottomTv;
    private TextView mTvTimeHours, mTvTimeMinutes, mTvTimeSeconds, mTvNeed;// 倒计时TextView
    //    private int completeStatus;
    private int needs;//还需要多少人参团
    private View intivate_need_view, intivate_content_view, bottom_no_groups;
    private LinearLayout llAllHeaderImg;
    //    private boolean isTuanEnd;
    private TextView need_people_tv2, need_people_tv1;
    //    private long recLenPay;//付款过期时间
    private int is_pay;//是否付过款
//    private int n_status;//:0 人数没满 1人满了等付款

    private boolean isMeal;//是否是特卖商品
    private String roll_code;


    private NewPTcountData.DataBean.OrderBean currentOrder; // 订单


    public static String DPPAYPRICE = "4.9";

    private List<String> userPicData;
    private NewPTcountData newPTcountData;

    public static final int PAY_SUCCESS = 1;
    public static final int BUG_MPK_SUCCESS = 2;
    public static final int GO_TO_PAY = 1009;
    public static final int GO_TO_BUY_MPK = 1000;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pt_detail);
        mContext = this;
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);

        roll_code = getIntent().getStringExtra("roll_code");

        isMeal = getIntent().getBooleanExtra("isMeal", false);

//        Bundle bundle = getIntent().getExtras();
//        order = (Order) bundle.getSerializable("order");
        int i = 1000000;
        initView();
    }

    /**
     * 初始化View
     */

    private void initView() {
        View headView = LayoutInflater.from(mContext).inflate(R.layout.onebuy_groups_details_head_view, null);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        r_list_view = (ListView) findViewById(R.id.r_list_view);
        r_list_view.addHeaderView(headView);
        mTvTimeHours = (TextView) headView.findViewById(R.id.time_tv_hours);
        mTvTimeMinutes = (TextView) findViewById(R.id.time_tv_minutes);
        mTvTimeSeconds = (TextView) findViewById(R.id.time_tv_seconds);
        mTvNeed = (TextView) findViewById(R.id.need_people);//拼团还差人数
        need_people_tv2 = headView.findViewById(R.id.need_people_tv2);
        need_people_tv1 = (TextView) headView.findViewById(R.id.need_people_tv1);
        intivate_need_view = headView.findViewById(R.id.intivate_need_view);
        intivate_content_view = headView.findViewById(R.id.intivate_content_view);

        llAllHeaderImg = headView.findViewById(R.id.ll_all_header_img);


        bottom_no_groups = findViewById(R.id.bottom_no_groups);


        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;


        mAdapter = new GroupsAdapter(this);

        r_list_view.setAdapter(mAdapter);

        nodataView = findViewById(R.id.no_data);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);

//        bottomView = findViewById(R.id.id_bottom);
        bottomTv = (TextView) findViewById(R.id.bottom_btn);
        bottomTv.setOnClickListener(this);
        bottomTv.setClickable(false);


        queryInitData();

//        setHongbaoAnim();

    }


    private boolean hongbaoAnimStart = false;
    AnimatorSet animatorSet = new AnimatorSet();

    private void setHongbaoAnim() {

        if (hongbaoAnimStart) {
            return;
        }

        ObjectAnimator animatorBigX = ObjectAnimator.ofFloat(ivHongbao, "scaleX", 1, 1.6f);

        animatorBigX.setDuration(800);

        ObjectAnimator animatorBigY = ObjectAnimator.ofFloat(ivHongbao, "scaleY", 1, 1.6f);

        animatorBigY.setDuration(800);


        ObjectAnimator animRot = ObjectAnimator.ofFloat(ivHongbao, "rotation", 0f, -30f, 0f, 30f, 0);
        animRot.setDuration(800);


        ObjectAnimator animatorSmallX = ObjectAnimator.ofFloat(ivHongbao, "scaleX", 1.6f, 1);
        animatorSmallX.setDuration(800);

        ObjectAnimator animatorSmallY = ObjectAnimator.ofFloat(ivHongbao, "scaleY", 1.6f, 1);
        animatorSmallY.setDuration(800);

        AnimatorSet.Builder buildBigX = animatorSet.play(animatorBigX);
        buildBigX.with(animatorBigY);//一起放大


        AnimatorSet.Builder buildSmallX = animatorSet.play(animatorSmallX);
        buildSmallX.with(animatorSmallY);//一起缩小


        //放大后旋转
        buildBigX.before(animRot);


        //旋转后缩小
        buildSmallX.after(animRot);

        hongbaoAnimStart = true;
        //循环播放
        handler.postDelayed(runnable, 100);


    }


    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            animatorSet.start();

            handler.postDelayed(this, 2600);
        }
    };


    /**
     * 获取参团总人数和拼团有效时间
     */
    private int rnum = 10;//需要的拼团总人数
    //    private int validHour = 12;//拼团有效时间（小时）
    private int validMin;

    private void queryInitData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("roll_code", roll_code);
        YConn.httpPost(mContext, YUrl.GROUP_INIT_DATA, map, new HttpListener<NewPTcountData>() {
            @Override
            public void onSuccess(NewPTcountData result) {
                newPTcountData = result;
                rnum = result.getData().getRnum();
                validMin = (int) (result.getData().getValidMin() * 1.8);
                currentOrder = result.getData().getOrder();
                //测试
//                queryMPKdetail();
//                validMin = 50*60*1000;
//                newPTcountData.getData().setFree_num(1);
//                -----------------------------
                DPPAYPRICE = result.getData().getOrder().getPay_money() + "";
                userPicData = result.getData().getUserPicData();
                initData();
            }

            @Override
            public void onError() {

            }
        });


    }

    private NewPTdetailShop.DataBean shop;

    private void initData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("code", roll_code);

        YConn.httpPost(mContext, YUrl.GROUPSQUERYBYROLLTOCODE, map, new HttpListener<NewPTdetailShop>() {
            @Override
            public void onSuccess(NewPTdetailShop newPTdetailShop) {


                r_list_view.setVisibility(View.VISIBLE);
                nodataView.setVisibility(View.GONE);


                HashMap<String, String> map0 = new HashMap<String, String>();//第一个拼团发起人
                for (int i = 0; i < newPTdetailShop.getData().size(); i++) {
                    if (newPTdetailShop.getData().get(i).getType() == 1) {
                        shop = newPTdetailShop.getData().get(i);
                        break;
                    }
                }

                needs = rnum;//拼团还差多少人
                needs = needs <= 0 ? 0 : needs;
                mTvNeed.setText(needs + "");

                if (timer != null) {
                    timer.cancel();
                }
                recLen = validMin;
                timer = new Timer();
                timer.schedule(new MyTimerTask(), 0, 555);
                mAdapter.setData(newPTdetailShop.getData());
                bottomTv.setClickable(true);
                if (/*recLen>0&&*/needs <= 0) {//时间没到 人数已经满 (开团的时候 界面的调整)
//                        need_people_tv2.setText("团员已满，付款人数未达到，请稍后。");
                    need_people_tv1.setVisibility(View.GONE);
                    mTvNeed.setVisibility(View.GONE);
                    intivate_content_view.setVisibility(View.GONE);
                    need_people_tv2.setVisibility(View.GONE);
                }

                if (recLen <= 0) {
                    intivate_need_view.setVisibility(View.GONE);
                }


                isInitComplete = true;
                //处理团成员头像部分
                initHerderImg();
                //处理拼团提示
                initDialog();
                //底部按钮处理
                initBottomBtn();
            }

            @Override
            public void onError() {
            }
        });
    }

    private void initBottomBtn() {
        if (validMin <= 0) {
            setBottomFinishTv();
        } else {
            if (needs <= 0) {
                if (currentOrder.getPay_status() == 1) {//已支付
                    bottomTv.setText("已付款");
                    bottomTv.setBackgroundResource(R.drawable.btn_back);
                    bottomTv.setClickable(false);

                } else {//未支付
                    bottomTv.setText("已成团，请在时效内付款");
                }
            }

        }

    }

    private void initHerderImg() {
        llAllHeaderImg.removeAllViews();
        for (int i = 0; i < userPicData.size(); i++) {
            View headerItemView = LayoutInflater.from(mContext).inflate(R.layout.pt_header_item, null);
            ImageView groups_head_iv = headerItemView.findViewById(R.id.groups_head_iv);
            ImageView groups_head_tz_icon = headerItemView.findViewById(R.id.groups_head_tz_icon);
            GlideUtils.initRoundImage(Glide.with(mContext), mContext, userPicData.get(i), groups_head_iv);
            if (i == 0) {
                groups_head_tz_icon.setVisibility(View.VISIBLE);


                RelativeLayout.LayoutParams lp =
                        new RelativeLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 30), (DP2SPUtil.dp2px(mContext, 30)));
                lp.setMargins(DP2SPUtil.dp2px(mContext, 18), DP2SPUtil.dp2px(mContext, 8), 0, 0);

                groups_head_iv.setLayoutParams(lp);

            } else {
                groups_head_tz_icon.setVisibility(View.GONE);

                RelativeLayout.LayoutParams lp =
                        new RelativeLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 30), (DP2SPUtil.dp2px(mContext, 30)));
                lp.setMargins(DP2SPUtil.dp2px(mContext, 5), DP2SPUtil.dp2px(mContext, 8), 0, 0);
                groups_head_iv.setLayoutParams(lp);


            }
            llAllHeaderImg.addView(headerItemView);
        }

        int needImgCount = 4 - userPicData.size();
        for (int i = 0; i < needImgCount; i++) {
            View headerItemView = LayoutInflater.from(mContext).inflate(R.layout.pt_header_item, null, false);
            headerItemView.findViewById(R.id.groups_head_tz_icon).setVisibility(View.GONE);
            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 30), (DP2SPUtil.dp2px(mContext, 30)));
            lp.setMargins(DP2SPUtil.dp2px(mContext, 5), DP2SPUtil.dp2px(mContext, 8), 0, 0);
            headerItemView.findViewById(R.id.groups_head_iv).setLayoutParams(lp);
            llAllHeaderImg.addView(headerItemView);
        }
    }

    private void initDialog() {

        int free_Number = newPTcountData.getData().getFree_num();

        if (validMin <= 0) {//拼团失败
//            new OneBuyGroupsResultDialog(context, rnum).show();
            //拼团失败进来的弹窗分为有没有免拼卡
            //测试用
//            free_Number = 1;
//            if (free_Number <= 0) {

            boolean isVip = CommonUtils.isVip(newPTcountData.getData().getIsVip(), newPTcountData.getData().getMaxType());

            if (isVip) {
                new OneBuyGroupsResultDialog(mContext, rnum).show();
                return;
            }

            DialogUtils.showPTFailDialog(mContext);

//
//                final Dialog mDialog;
//                final Timer mpkTimer = new Timer();
//
//                LayoutInflater mInflater = LayoutInflater.from(mContext);
//                mDialog = new Dialog(mContext, R.style.invate_dialog_style);
//                View view = mInflater.inflate(R.layout.dialog_pt_mpk, null);
//                view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mpkTimer.cancel();
//                        mDialog.dismiss();
//                    }
//                });
//
//                ImageView iv_hongbao_bg = view.findViewById(R.id.iv_hongbao_bg);
//                iv_hongbao_bg.setImageResource(R.drawable.free_ling_fight_fail_mpk);
//                iv_hongbao_bg.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//
//                        HashMap<String, String> map = new HashMap<>();
//                        YConn.httpPost(mContext, YUrl.QUERY_TIQIAN_TXCJ, map, new HttpListener<Choujiang20Data>() {
//                            @Override
//                            public void onSuccess(Choujiang20Data result) {
//                                if ("1".equals(result.getStatus())) {
//                                    if (result.getData().getIs_finish() == 1) {
//                                        if (null != SignDrawalLimitActivity.instance) {
//                                            SignDrawalLimitActivity.instance.finish();
//                                        }
//
//                                        Intent txcjIntent = new Intent(mContext, SignDrawalLimitActivity.class);
//                                        txcjIntent.putExtra("type", 1)
//                                                .putExtra("fromPT", true);
//
//                                        startActivity(txcjIntent);
//                                        ((FragmentActivity) mContext).overridePendingTransition(
//                                                R.anim.slide_left_in, R.anim.slide_match);
//                                        mpkTimer.cancel();
//                                        mDialog.dismiss();
//                                        finish();
//                                    } else {
//                                        startActivityForResult(new Intent(mContext, MyVipListActivity.class)
//                                                .putExtra("isGuideMPK", true), GO_TO_BUY_MPK);
//                                        mpkTimer.cancel();
//                                        mDialog.dismiss();
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onError() {
//
//                            }
//                        });
//
//
//                    }
//                });
//
//
//                final TextView tv_txk_countdown = view.findViewById(R.id.tv_txk_countdown);
//                long txkRecLen = 30 * 60 * 1000;
//                final long[] reTime = {txkRecLen};
//                tv_txk_countdown.setText(DateUtil.FormatMilliseondToEndTime2(reTime[0]) + "后失效");
//                mpkTimer.schedule(
//                        new TimerTask() {
//                            @Override
//                            public void run() {
//                                ((Activity) mContext).runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        if (reTime[0] <= 0) {
//                                            mpkTimer.cancel();
//                                            mDialog.dismiss();
//                                        } else {
//                                            reTime[0] -= 1000;
//                                            tv_txk_countdown.setText(DateUtil.FormatMilliseondToEndTime2(reTime[0]) + "后失效");
//                                        }
//                                    }
//                                });
//                            }
//                        },
//
//                        0, 1000
//                );
//
//                mDialog.setCanceledOnTouchOutside(false);
//                mDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.MATCH_PARENT));
//                ToastUtil.showDialog(mDialog);


//            } else {
//                showNeedMPK(free_Number);
//            }

        } else if (rnum > 0 && validMin > 0) { //提醒邀请好友参团提示

//            if (newPTcountData.getData().getIsVip() > 0) {
//                new OneBuyGroupsShareDialog(OneBuyGroupsDetailsActivity.this, needs, shop, r_code, isMeal, true).show();//发起拼团邀请
//
//                return;
//            }
//
//            if (free_Number >= 1) {
//                new OneBuyGroupsShareHasMPKDialog(OneBuyGroupsDetailsActivity.this, needs, shop, r_code, isMeal, free_Number).show();//发起拼团邀请
//
//            } else {
            new OneBuyGroupsShareDialog(OneBuyGroupsDetailsActivity.this, needs, shop, roll_code, isMeal, false).show();//发起拼团邀请

//            }

        }

    }

    private boolean isInitComplete;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.bottom_btn:
                if (!isInitComplete) {
                    ToastUtil.showShortText2("正在初始化，请稍后");
                }

                if (validMin > 0 && needs <= 0) {//去支付

//                    ToastUtil.showShortText2("去支付");


                    // 跳转到收银台界面
                    Intent intent = new Intent(OneBuyGroupsDetailsActivity.this, PaymentActivity.class);
                    intent.putExtra("order_code", currentOrder.getOrder_code());
                    intent.putExtra("totlaAccount", currentOrder.getPay_money());
                    intent.putExtra("isMulti", true);
                    intent.putExtra("isOneBuy", true);
                    startActivityForResult(intent, GO_TO_PAY);

                    return;
                }

                if (currentOrder.getPay_status() == 1 || validMin <= 0) {//已支付或者时间已过

                    new OneBuyGroupsResultDialog(mContext, rnum).show();


                    return;
                }

                if (newPTcountData.getData().getFree_num() >= 1) {
                    new OneBuyGroupsShareHasMPKDialog(OneBuyGroupsDetailsActivity.this, needs, shop, roll_code, isMeal, newPTcountData.getData().getFree_num()).show();//发起拼团邀请

                } else {


                    new OneBuyGroupsShareDialog(OneBuyGroupsDetailsActivity.this, needs, shop, roll_code, isMeal, newPTcountData.getData().getIsVip() > 0).show();//发起拼团邀请

                }


//                new OneBuyGroupsResultDialog(this).show();

                break;
            default:
                break;
        }
    }

    @OnClick(R.id.iv_hongbao)
    public void onViewClicked() {
        SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
        Intent intent = new Intent(mContext, CommonActivity.class);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

    }


    class GroupsAdapter extends BaseAdapter {
        private List<NewPTdetailShop.DataBean> mInfos;
        private LayoutInflater layoutInflator;
        private Context context;

        public GroupsAdapter(Context context) {
            this.context = context;
            mInfos = new ArrayList<>();
            layoutInflator = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = layoutInflator.inflate(R.layout.new_groups_item_list, null);
                holder.itemView = convertView.findViewById(R.id.groups_item_view);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.itemView.setOnebuy();
            holder.itemView.setMeal(isMeal);
            holder.itemView.setItemData(mInfos.get(position));

            return convertView;
        }

        class ViewHolder {
            NewGroupsItemView itemView;
        }

        @Override
        public int getCount() {
            return mInfos.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        public void setData(List<NewPTdetailShop.DataBean> result) {
            mInfos.clear();
            mInfos.addAll(result);
            this.notifyDataSetChanged();
        }
    }


    private long recLen = 0;// 剩余时间
    private Timer timer;

    public class MyTimerTask extends TimerTask {

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
                        mTvTimeHours.setText(hours);
                        mTvTimeMinutes.setText(minutes);
                        mTvTimeSeconds.setText(seconds);
                    } else if (minute >= 10 && second >= 10) {
                        mTvTimeHours.setText("00");
                        mTvTimeMinutes.setText(minute + "");
                        mTvTimeSeconds.setText(second + "");
                    } else if (minute >= 10 && second < 10) {
                        mTvTimeHours.setText("00");
                        mTvTimeMinutes.setText(minute + "");
                        mTvTimeSeconds.setText("0" + second);
                    } else if (minute < 10 && second >= 10) {
                        mTvTimeHours.setText("00");
                        mTvTimeMinutes.setText("0" + minute);
                        mTvTimeSeconds.setText(second + "");
                    } else if (minute < 10 && second < 10) {
                        mTvTimeHours.setText("00");
                        mTvTimeMinutes.setText("0" + minute);
                        mTvTimeSeconds.setText("0" + second);
                    }

                    if (recLen <= 0) {
                        timer.cancel();
                        mTvTimeHours.setText("00");
                        mTvTimeMinutes.setText("00");
                        mTvTimeSeconds.setText("00");
                        setBottomFinishTv();
                    }
                }
            });
        }

    }

    public void setBottomFinishTv() {
        bottomTv.setText("拼团已结束");
        bottomTv.setBackgroundResource(R.drawable.btn_back);
        bottomTv.setClickable(true);
    }


    private void queryMPKdetail() {

        HashMap<String, String> map = new HashMap<>();
        map.put("order_code", currentOrder.getOrder_code());
        YConn.httpPost(mContext, YUrl.QUERY_DJK_DETAIL, map, new HttpListener<DJKdetail>() {
            @Override
            public void onSuccess(DJKdetail djKDetail) {
                if (djKDetail.getIsDeliver() == 1) {//此判断订单已经发货，关闭界面
                    finish();
                } else {
//                    if (djKDetail.getFreeCardNum() >= 1) {
//                        showNeedMPK(djKDetail.getFreeCardNum());
//                    }
                }


            }

            @Override
            public void onError() {

            }
        });

    }

    private void showNeedMPK(final int free_Number) {
        final Dialog mDialog;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        mDialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_order_list_has_fhk, null);
        ImageView iv_jia2 = view.findViewById(R.id.iv_jia2);
        ImageView iv_jia3 = view.findViewById(R.id.iv_jia3);
        ImageView iv_jia1 = view.findViewById(R.id.iv_jia1);
        TextView bt1 = view.findViewById(R.id.bt1);
        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);

        view.findViewById(R.id.tv8).setVisibility(View.GONE);
        view.findViewById(R.id.tv9).setVisibility(View.GONE);

        tv1.setText(Html.fromHtml("该商品需要<font color='#FFCF00'>3</font>张免拼卡即可免拼发货"));
        if (free_Number > 0) {
            tv1.setText(Html.fromHtml("商品原价超过<font color='#FFCF00'>119</font>元，需要<font color='#FFCF00'>3</font>张免拼卡即可免拼发货。"));
        }
        tv2.setText(Html.fromHtml("您已有<font color='#FFCF00'>" + free_Number + "</font>张还差<font color='#FFCF00'>" + (3 - free_Number) + "</font>张"));
        iv_jia1.setImageResource(R.drawable.free_fight);
        iv_jia2.setImageResource(R.drawable.free_fight);
        iv_jia3.setImageResource(R.drawable.free_fight);
        bt1.setBackgroundResource(R.drawable.continue_ling_freefightting);
        switch (free_Number) {
            case 1:
                iv_jia2.setImageResource(R.drawable.icon_fahuoka_jia);
                iv_jia3.setImageResource(R.drawable.icon_fahuoka_jia);
                break;
            case 2:
                iv_jia3.setImageResource(R.drawable.icon_fahuoka_jia);
                break;

        }
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();

            }
        });
        view.findViewById(R.id.rl_jia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();

                if (free_Number == 2) {
                    SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
                    Intent intent = new Intent(mContext, CommonActivity.class);
                    intent.putExtra("fromMianOrFaClick", true);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    return;
                }


                HashMap<String, String> map = new HashMap<>();
                YConn.httpPost(mContext, YUrl.QUERY_TIQIAN_TXCJ, map, new HttpListener<Choujiang20Data>() {
                    @Override
                    public void onSuccess(Choujiang20Data result) {
                        if ("1".equals(result.getStatus())) {
                            if (result.getData().getIs_finish() == 1) {
                                if (null != SignDrawalLimitActivity.instance) {
                                    SignDrawalLimitActivity.instance.finish();
                                }

                                Intent txcjIntent = new Intent(mContext, SignDrawalLimitActivity.class);
                                txcjIntent.putExtra("type", 1)
                                        .putExtra("fromPT", true);

                                startActivity(txcjIntent);
                                ((FragmentActivity) mContext).overridePendingTransition(
                                        R.anim.slide_left_in, R.anim.slide_match);
                            } else {
                                startActivityForResult(new Intent(mContext, MyVipListActivity.class)
                                        .putExtra("isGuideMPK", true), GO_TO_BUY_MPK);

                            }
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });


            }
        });

        view.findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                if (free_Number == 2) {
                    SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
                    Intent intent = new Intent(mContext, CommonActivity.class);
                    intent.putExtra("fromMianOrFaClick", true);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    return;
                }

                HashMap<String, String> map = new HashMap<>();
                YConn.httpPost(mContext, YUrl.QUERY_TIQIAN_TXCJ, map, new HttpListener<Choujiang20Data>() {
                    @Override
                    public void onSuccess(Choujiang20Data result) {
                        if ("1".equals(result.getStatus())) {
                            if (result.getData().getIs_finish() == 1) {
                                if (null != SignDrawalLimitActivity.instance) {
                                    SignDrawalLimitActivity.instance.finish();
                                }

                                Intent txcjIntent = new Intent(mContext, SignDrawalLimitActivity.class);
                                txcjIntent.putExtra("type", 1)
                                        .putExtra("fromPT", true);

                                startActivity(txcjIntent);
                                ((FragmentActivity) mContext).overridePendingTransition(
                                        R.anim.slide_left_in, R.anim.slide_match);
                                finish();
                            } else {
                                startActivityForResult(new Intent(mContext, MyVipListActivity.class)
                                        .putExtra("isGuideMPK", true), GO_TO_BUY_MPK);

                            }
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });

            }
        });

        mDialog.setCanceledOnTouchOutside(false);
        mDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ToastUtil.showDialog(mDialog);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GO_TO_PAY:
                if (resultCode == PAY_SUCCESS) {
                    queryInitData();
                } else if (requestCode == 2) {
                    ToastUtil.showShortText2("支付失败");
                }
                break;
            case GO_TO_BUY_MPK:
                if (resultCode == BUG_MPK_SUCCESS) {//买完免拼卡回来
                    //查询免拼卡情况
                    queryMPKdetail();
                }
                break;
        }

    }
}
