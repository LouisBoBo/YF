package com.yssj.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.MyListView;
import com.yssj.custom.view.PointSharePopupwindow;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelL;
import com.yssj.ui.activity.infos.ClothesBeanDetailActivity;
import com.yssj.ui.activity.infos.IntergralDetailActivity;
import com.yssj.ui.activity.infos.MyWalletCommonFragmentActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.TixianYinDaoDialog;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.GlideUtils;
import com.yssj.utils.MediaManager;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/***
 * 点赞排名 列表
 */
public class PointLikeRankingActivity extends BasicActivity implements OnClickListener {

    private View img_back;
    private View to_point_like;// 去集赞
    private TextView mTvTimeDays, mTvTimeHours, mTvTimeMinutes, mTvTimeSeconds;// 倒计时TextView
    private TextView balanceTv, limitTv, used_yidou_tv, all_point_tv;// 总金额TextView
    // //可提现
    // 额度TextView
    // //可用衣豆
    // //
    // 累计集赞
    private View to_withdrawal_btn;// 去提现 按钮TextView
    private View limit_det_ll;// 额度明细 按钮TextView
    private View huodongguize_icon, jinrijiangli_icon;// 活动规则 和今日奖励
    private View explain_limit;// 额度说明
    private RankingAdapter mAdapter;
    private MyListView r_list_view;
    private VoiceAdapter mVoiceAdapter;
    private MyListView r_list_view_voice;
    public int width;
    public int height;
    private Context mContext;

    public static PointLikeRankingActivity pointlikerankingactivity;

    private double mSumBalance, mLimit;// 总的余额 和 可提现额度
    private int usedYidou, unUsedYidou;// 可用衣豆 ，冻结衣豆
    private TextView tvRanking01, tvRanking02, tvRanking03;
    private TextView tvcontent01, tvcontent02, tvcontent03;
    private ImageView ivAward01, ivAward02, ivAward03;
    private TextView tvGuiZe1, tvGuiZe2, tvGuiZe3, tvGuiZe4, tvGuiZe4_2, tvGuiZe5, tvGuiZe6, tvGuiZe7;
    private TextView periodsTv;//获奖感言期数
    private View voiceTitleLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_like_ranking);
        AppManager.getAppManager().addActivity(this);
        initView();
        initData();

    }

    /**
     * 初始化View
     */
    private void initView() {
        mContext = this;
        pointlikerankingactivity = this;
        tvRanking01 = (TextView) findViewById(R.id.tv_ranking01);
        tvRanking02 = (TextView) findViewById(R.id.tv_ranking02);
        tvRanking03 = (TextView) findViewById(R.id.tv_ranking03);
        tvcontent01 = (TextView) findViewById(R.id.tv_content01);
        tvcontent02 = (TextView) findViewById(R.id.tv_content02);
        tvcontent03 = (TextView) findViewById(R.id.tv_content03);
        ivAward01 = (ImageView) findViewById(R.id.award_pic01);
        ivAward02 = (ImageView) findViewById(R.id.award_pic02);
        ivAward03 = (ImageView) findViewById(R.id.award_pic03);
        tvGuiZe1 = (TextView) findViewById(R.id.huodongguize_tv1);
        tvGuiZe2 = (TextView) findViewById(R.id.huodongguize_tv2);
        tvGuiZe3 = (TextView) findViewById(R.id.huodongguize_tv3);
        tvGuiZe4 = (TextView) findViewById(R.id.huodongguize_tv4);
        tvGuiZe4_2 = (TextView) findViewById(R.id.huodongguize_tv4_2);
        tvGuiZe5 = (TextView) findViewById(R.id.huodongguize_tv5);
        tvGuiZe6 = (TextView) findViewById(R.id.huodongguize_tv6);
        tvGuiZe7 = (TextView) findViewById(R.id.huodongguize_tv7);


        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        to_point_like = findViewById(R.id.to_point_like_btn);
        to_point_like.setOnClickListener(this);
        balanceTv = (TextView) findViewById(R.id.sum_balance_tv);
        balanceTv.getPaint().setFakeBoldText(true);// 设置加粗字体
        limitTv = (TextView) findViewById(R.id.limit_tv);
        limitTv.getPaint().setFakeBoldText(true);// 设置加粗字体
        used_yidou_tv = (TextView) findViewById(R.id.used_yidou_tv);
        used_yidou_tv.setOnClickListener(this);
        all_point_tv = (TextView) findViewById(R.id.all_point_tv);
        all_point_tv.setOnClickListener(this);
        to_withdrawal_btn = findViewById(R.id.to_withdrawal_btn);
        to_withdrawal_btn.setOnClickListener(this);
        limit_det_ll = findViewById(R.id.limit_det_ll);
        limit_det_ll.setOnClickListener(this);
        r_list_view = (MyListView) findViewById(R.id.r_list_view);
        r_list_view_voice = (MyListView) findViewById(R.id.r_list_view_voice);
        listView1 = (ListView) findViewById(R.id.list_view1);
        huodongguize_icon = findViewById(R.id.huodongguize_icon);
        jinrijiangli_icon = findViewById(R.id.jinrijiangli_icon);
        explain_limit = findViewById(R.id.explain_limit);
        huodongguize_icon.setOnClickListener(this);
        jinrijiangli_icon.setOnClickListener(this);
        explain_limit.setOnClickListener(this);
        mTvTimeDays = (TextView) findViewById(R.id.time_tv_days);// 天
        mTvTimeHours = (TextView) findViewById(R.id.time_tv_hours);// 时
        mTvTimeMinutes = (TextView) findViewById(R.id.time_tv_minutes);// 分
        mTvTimeSeconds = (TextView) findViewById(R.id.time_tv_seconds);// 秒

        periodsTv = (TextView) findViewById(R.id.awards_periods_tv);
        voiceTitleLl = findViewById(R.id.voice_ll);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;

        mAdapter = new RankingAdapter(this);
        mVoiceAdapter = new VoiceAdapter(this);
        /******* 模拟假数据 要删除 ******/
//		List<HashMap<String, String>> mInfos = new ArrayList<HashMap<String, String>>();
//		for (int i = 0; i < 6; i++) {
//			mInfos.add(new HashMap<String, String>());
//		}
//        mVoiceAdapter.setData(mInfos);
        /******* 模拟假数据 要删除 ******/
        r_list_view.setAdapter(mAdapter);
        r_list_view_voice.setAdapter(mVoiceAdapter);
    }


    /**
     * 获取数据
     */
    private void initData() {
        // 倒计时
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
//将小时至0
        cal.set(Calendar.HOUR_OF_DAY, 0);
//将分钟至0
        cal.set(Calendar.MINUTE, 0);
//将秒至0
        cal.set(Calendar.SECOND, 0);
//将毫秒至0
        cal.set(Calendar.MILLISECOND, 0);


        cal.add(Calendar.MONTH, 1);
//在当前月的下一月基础上减去1毫秒
        cal.add(Calendar.MILLISECOND, -1);
//获得当前月最后一天
        long timeInMillis = cal.getTimeInMillis();
        long now = System.currentTimeMillis();
        recLen = timeInMillis - now;
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new MyTimerTask(), 0, 1000);

        getPraiseDetails();//本页面详情
        getHuoDongGuiZeText();
        getPraisePic();//集赞排名奖励图片
        getAwardsVoices();//获奖感言
        getPraiseRanking();//集赞排名
        initLimitAwardsList();//集赞额度奖励
    }

    private void getPraiseDetails() {
        new SAsyncTask<Void, Void, String[]>(PointLikeRankingActivity.this, R.string.wait) {

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
                        mSumBalance = Double.parseDouble(result[0]);
                        // if(isMad){
                        mLimit = Double.parseDouble(result[6]) + Double.parseDouble(result[7]);
                        // }else{
                        // mLimit = Double.parseDouble(result[6]);
                        // }
                        limitTv.setText("" + new DecimalFormat("#0.00").format(mLimit));
                        used_yidou_tv.setText("可用衣豆:" + result[4]);
                        usedYidou = Integer.parseInt(result[4]);
                        // un_used_yidou_tv.setText("冻结衣豆:"+result[5]);
                        unUsedYidou = Integer.parseInt(result[5]);
                        all_point_tv.setText("累计集赞:" + result[8]);

//                        showHuoDongGuize();
                        setBalanceTvText(context);

                    }
                }
            }

        }.execute();
    }

    /**
     * 集赞奖品
     */
    private void getPraisePic() {
        new SAsyncTask<String, Void, List<HashMap<String, String>>>(PointLikeRankingActivity.this, 0) {
            @Override
            protected List<HashMap<String, String>> doInBackground(FragmentActivity context, String... params)
                    throws Exception {

                return ComModel2.praiseRankingList(PointLikeRankingActivity.this);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         List<HashMap<String, String>> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null) {
                    for (int i = 0; i < result.size(); i++) {
                        if (i == 0) {
                            PicassoUtils.initImage(PointLikeRankingActivity.this, result.get(0).get("pic"), ivAward01);
                            tvRanking01.setText("一等奖：" + result.get(0).get("number") + "名");
                            tvcontent01.setText("" + result.get(0).get("content"));
                        } else if (i == 1) {
                            PicassoUtils.initImage(PointLikeRankingActivity.this, result.get(1).get("pic"), ivAward02);
                            tvRanking02.setText("二等奖：" + result.get(1).get("number") + "名");
                            tvcontent02.setText("" + result.get(1).get("content"));
                        } else if (i == 2) {
                            PicassoUtils.initImage(PointLikeRankingActivity.this, result.get(2).get("pic"), ivAward03);
                            tvRanking03.setText("三等奖：" + result.get(2).get("number") + "名");
                            tvcontent03.setText("" + result.get(2).get("content"));
                        }
                    }
                }
            }

        }.execute();
    }

    private void getPraiseRanking() {
        new SAsyncTask<Void, Void, List<HashMap<String, String>>>(PointLikeRankingActivity.this, R.string.wait) {

            @Override
            protected List<HashMap<String, String>> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getPraiseRanking(PointLikeRankingActivity.this);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<HashMap<String, String>> result, Exception e) {
                super.onPostExecute(context, result, e);

                if (null == e && result != null) {
                    mAdapter.setData(result);
                }
                mAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    private MyAdapter adapter1;
    private Timer mTimer1;
    private ListView listView1;// 滚动的ListView
    private List<HashMap<String, String>> mListData1;// 滚动的ListView的数据源

    /**
     * 获取数据 奖励列表 50条数据 一条真实数据 一条虚拟数据穿插
     */
    private void initLimitAwardsList() {
        new SAsyncTask<Void, Void, List<HashMap<String, String>>>(PointLikeRankingActivity.this, R.string.wait) {

            @Override
            protected List<HashMap<String, String>> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getPraiseEctractList(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<HashMap<String, String>> result, Exception e) {
                super.onPostExecute(context, result, e);

                mListData1 = new ArrayList<HashMap<String, String>>();
                if (null == e && result != null) {
                    List<HashMap<String, String>> result1 = result;
                    // 填充额度奖励数据
                    for (int i = 0; i < 50; i++) {
                        if (i < result1.size() * 2) { // 前面result1.size()*2条数据互相穿插数据
                            if (i % 2 == 0) {
                                mListData1.add(result1.get(i / 2));// 添加到总集合
                                // 键值和虚拟添加的键值保持一致
                            } else {
                                addToLimitList();
                            }
                        } else {
                            addToLimitList();
                        }
                    }
                } else {// 接口失败 填充虚拟数据
                    for (int i = 0; i < 50; i++) {
                        addToLimitList();
                    }
                }
                adapter1 = new MyAdapter(PointLikeRankingActivity.this, mListData1);
                listView1.setAdapter(adapter1);
                if (mTimer1 != null) {
                    mTimer1.cancel();
                }
                mTimer1 = new Timer();
                mTimer1.schedule(task1, 20, 20);
            }
        }.execute();
    }

    /**
     * 设置余额 翻倍和未翻倍
     *
     * @param context
     */
    private void setBalanceTvText(FragmentActivity context) {
        int mTwofoldness = Integer.parseInt(SharedPreferencesUtil.getStringData(context, Pref.TWOFOLDNESS, 1 + ""));
        int mIsOpen = Integer.parseInt(SharedPreferencesUtil.getStringData(context, Pref.IS_OPEN, -1 + ""));
        if (mIsOpen == 1) {// 开启了余额翻倍
            balanceTv.setText("" + new DecimalFormat("#0.00").format(mSumBalance * mTwofoldness));
        } else {
            balanceTv.setText("" + new DecimalFormat("#0.00").format(mSumBalance));
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.to_withdrawal_btn: // 提现按钮


//                if (!SharedPreferencesUtil.getBooleanData(this, "isYindaoToast", false)) {
//                    TixianYinDaoDialog.WithDrawListener listener = new TixianYinDaoDialog.WithDrawListener() {
//                        @Override
//                        public void close() {
//                            // TODO Auto-generated method stub
//                        }
//                    };
//                    // 新手1元提现引导
//                    TixianYinDaoDialog dialog = new TixianYinDaoDialog(this, R.style.DialogQuheijiao, 1, listener);
//                    dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
//                    dialog.show();
//
//                    return;
//                }


                int mIsOpen = Integer.parseInt(SharedPreferencesUtil.getStringData(this, Pref.IS_OPEN, -1 + ""));
                if (mIsOpen == 1) {// 开启了余额翻倍
                    ToastUtil.showShortText(this, "余额翻倍期间暂时不可以提现喔~");
                    break;
                }
                Intent to_withdrawal_btn_intent = new Intent(this, MyWalletCommonFragmentActivity.class);
                to_withdrawal_btn_intent.putExtra("flag", "withDrawalFragment");
                to_withdrawal_btn_intent.putExtra("alliance", "wallet");
                startActivity(to_withdrawal_btn_intent);
                break;
            case R.id.all_point_tv:
                // TODO 累计点赞
                break;
            case R.id.used_yidou_tv: // 可用衣豆
                Intent yidou_intent = new Intent(this, ClothesBeanDetailActivity.class);
                yidou_intent.putExtra("pearsCount", usedYidou + "");
                yidou_intent.putExtra("freezeCount", unUsedYidou + "");
                startActivity(yidou_intent);

                break;
            case R.id.limit_det_ll:// 额度明细
                toLimitDet();

                break;
            case R.id.to_point_like_btn:
                // 去集攒
//                Intent intent = new Intent(this, YaoQingFrendsActivity.class);
//                intent.putExtra("isAddLike", true);// YaoQingFrendsActivity 界面中是否是集赞
//                startActivity(intent);
//                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                TongJiUtils.yunYunTongJi("point",908,9,mContext);
                getShareWindow();
                break;
            case R.id.huodongguize_icon:
                // TODO 活动规则弹窗
//                huoDongGuiZeDialog();
                break;
            case R.id.jinrijiangli_icon:
                // TODO 今日奖励弹窗
                jinRiJiangLiDialog();
                break;
            case R.id.explain_limit:
                // TODO 额度说明弹窗
                showLimitShuoming();
                break;
            default:
                break;
        }
    }

    /**
     * 查看额度明细
     */
    private void toLimitDet() {
        Intent limit_det_ll_intent = new Intent(this, IntergralDetailActivity.class);
        limit_det_ll_intent.putExtra("page", 0);
        limit_det_ll_intent.putExtra("isTiXianMingXi", true);
        startActivity(limit_det_ll_intent);
        // overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
    }


    /**
     * 积攒排名列表Adapter
     *
     * @author Administrator
     */
    class RankingAdapter extends BaseAdapter {
        private List<HashMap<String, String>> mInfos;
        private LayoutInflater layoutInflator;
        private Context context;

        public RankingAdapter(Context context) {
            this.context = context;
            mInfos = new ArrayList<HashMap<String, String>>();
            layoutInflator = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = layoutInflator.inflate(R.layout.point_like_rank_list, null);
                holder.head_iv = (ImageView) convertView.findViewById(R.id.ranking_head_iv);
                holder.ranking_ninckname = (TextView) convertView.findViewById(R.id.ranking_ninckname);
                holder.ranking_number = (TextView) convertView.findViewById(R.id.ranking_number);
                holder.ranking_area = (TextView) convertView.findViewById(R.id.ranking_area);
                holder.ranking_tv = (TextView) convertView.findViewById(R.id.ranking_tv);
                holder.ranking_iv = (ImageView) convertView.findViewById(R.id.ranking_iv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position == 0) {
                holder.ranking_iv.setVisibility(View.VISIBLE);
                holder.ranking_tv.setVisibility(View.GONE);
                holder.ranking_iv.setImageResource(R.drawable.jiangzhuang_01);
            } else if (position == 1) {
                holder.ranking_iv.setVisibility(View.VISIBLE);
                holder.ranking_tv.setVisibility(View.GONE);
                holder.ranking_iv.setImageResource(R.drawable.jiangzhuang_02);
            } else if (position == 2) {
                holder.ranking_iv.setVisibility(View.VISIBLE);
                holder.ranking_tv.setVisibility(View.GONE);
                holder.ranking_iv.setImageResource(R.drawable.jiangzhuang_03);
            } else {
                holder.ranking_iv.setVisibility(View.GONE);
                holder.ranking_tv.setVisibility(View.VISIBLE);
                holder.ranking_tv.setText("" + (position + 1));
            }
//            PicassoUtils.initImage(PointLikeRankingActivity.this, mInfos.get(position).get("pic"), holder.head_iv);
            GlideUtils.initRoundImage(Glide.with(PointLikeRankingActivity.this),PointLikeRankingActivity.this, mInfos.get(position).get("pic"), holder.head_iv);
            holder.ranking_ninckname.setText(mInfos.get(position).get("nickname"));
            String location;
            if (mInfos.get(position).get("location").length() > 7) {
                location = mInfos.get(position).get("location").substring(0, 7) + "...";
            } else {
                location = mInfos.get(position).get("location");
            }
            holder.ranking_area.setText("" + location);
            holder.ranking_number.setText(mInfos.get(position).get("point_count"));
            return convertView;
        }

        class ViewHolder {
            ImageView head_iv;
            TextView ranking_ninckname, ranking_number, ranking_area, ranking_tv;// 昵称
            // ，点赞数量
            // ，地区
            // ，
            // 点赞排名
            ImageView ranking_iv;// 点赞排名图标
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

        public void addItemLast(List<HashMap<String, String>> datas) {
            mInfos.addAll(datas);
            this.notifyDataSetChanged();
        }

        public void addItemTop(List<HashMap<String, String>> datas) {
            mInfos.clear();
            mInfos.addAll(datas);
            this.notifyDataSetChanged();
        }

        public void clearData() {
            mInfos.clear();
            this.notifyDataSetChanged();
        }

        public void setData(List<HashMap<String, String>> result) {
            clearData();
            mInfos.addAll(result);
            this.notifyDataSetChanged();
        }
    }


    private long recLen = 0;// 剩余时间
    private Timer timer;

    // if(timer!=null){
    // timer.cancel();
    // }
    // timer = new Timer();
    // timer.schedule(new MyTimerTask(),0, 1000);
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
                            // mTvTime.setText("" + days + "天" + hours + "时" +
                            // minutes + "分" + seconds+"秒");
                            mTvTimeDays.setText(days);
                            mTvTimeHours.setText(hours);
                            mTvTimeMinutes.setText(minutes);
                            mTvTimeSeconds.setText(seconds);
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
                            // mTvTime.setText("00天" + hours + "时" + minutes +
                            // "分" + seconds+"秒");
                            mTvTimeDays.setText("00");
                            mTvTimeHours.setText(hours);
                            mTvTimeMinutes.setText(minutes);
                            mTvTimeSeconds.setText(seconds);
                        }
                    } else if (minute >= 10 && second >= 10) {
                        // mTvTime.setText("00天00时" + minute + "分" +
                        // second+"秒");
                        mTvTimeDays.setText("00");
                        mTvTimeHours.setText("00");
                        mTvTimeMinutes.setText(minute + "");
                        mTvTimeSeconds.setText(second + "");
                    } else if (minute >= 10 && second < 10) {
                        // mTvTime.setText("00天00时" + minute + "分0" +
                        // second+"秒");
                        mTvTimeDays.setText("00");
                        mTvTimeHours.setText("00");
                        mTvTimeMinutes.setText(minute + "");
                        mTvTimeSeconds.setText("0" + second);
                    } else if (minute < 10 && second >= 10) {
                        // mTvTime.setText("00天00时0" + minute + "分" +
                        // second+"秒");
                        mTvTimeDays.setText("00");
                        mTvTimeHours.setText("00");
                        mTvTimeMinutes.setText("0" + minute);
                        mTvTimeSeconds.setText(second + "");
                    } else if (minute < 10 && second < 10) {
                        // mTvTime.setText("00天00时0" + minute + "分0" +
                        // second+"秒");
                        mTvTimeDays.setText("00");
                        mTvTimeHours.setText("00");
                        mTvTimeMinutes.setText("0" + minute);
                        mTvTimeSeconds.setText("0" + second);
                    }

                    if (recLen <= 0) {
                        timer.cancel();
                        mTvTimeDays.setText("00");
                        mTvTimeHours.setText("00");
                        mTvTimeMinutes.setText("00");
                        mTvTimeSeconds.setText("00");
                    }
                }
            });
        }

    }


    /**
     * 添加虚拟数据到 额度奖励集合
     */
    private void addToLimitList() {
        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("nickname", StringUtils.getVirtualName() + "***" + StringUtils.getVirtualName());
//        map1.put("p_name", "集赞获得提现额度");
        map1.put("pic", "defaultcommentimage/" + StringUtils.getDefaultImg());
        map1.put("today_reward",
                "" + (int) (Math.random() * (200 - 10) + 10) + "." + (int) (Math.random() * 10));
        mListData1.add(map1);
    }

    TimerTask task1 = new TimerTask() {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listView1.smoothScrollBy(1, 0);
                }
            });

        }
    };

    /**
     * 滚动的 列表 的 Adapter
     */
    public class MyAdapter extends BaseAdapter {
        private List<HashMap<String, String>> mListData;
        private Context context;

        public MyAdapter(Context context, List<HashMap<String, String>> mListData) {
            super();
            this.mListData = mListData;
            this.context = context;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object getItem(int arg0) {
            return mListData.get(arg0 % (mListData.size()));
        }

        @Override
        public long getItemId(int arg0) {
            return arg0 % (mListData.size());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(PointLikeRankingActivity.this, R.layout.item_withdrawal_limit, null);
                holder.mNameTv = (TextView) convertView.findViewById(R.id.withdrawal_name_tv);
                holder.tv = (TextView) convertView.findViewById(R.id.withdrawal_exp_tv);
                holder.mAwardsTv = (TextView) convertView.findViewById(R.id.withdrawal_awards_tv);
                holder.headIv = (ImageView) convertView.findViewById(R.id.withdrawal_head_iv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            // SetImageLoader.initImageLoader(WithdrawalLimitActivity.this,
            // holder.headIv,
            // mListData.get(position%mListData.size()).get("pic").toString(),
            // "");

//            PicassoUtils.initImage(PointLikeRankingActivity.this,
//                    mListData.get(position % mListData.size()).get("pic").toString(), holder.headIv);

            GlideUtils.initRoundImage(Glide.with(PointLikeRankingActivity.this),PointLikeRankingActivity.this,
                    mListData.get(position % mListData.size()).get("pic").toString(), holder.headIv);

            holder.mNameTv.setText(mListData.get(position % mListData.size()).get("nickname").toString());
            try {
                holder.mAwardsTv.setText("+" + new DecimalFormat("#0.0").format(Double.parseDouble("" + mListData.get(position % mListData.size()).get("today_reward").toString())) + "元");
            } catch (Exception e) {

            }

            holder.tv.setText("集赞获得提现额度");
            return convertView;
        }

    }

    public class ViewHolder {
        TextView mNameTv, tv, mAwardsTv;
        ImageView headIv;
    }

    /**
     * 额度说明弹框
     */
    private void showLimitShuoming() {
        final Dialog dialog = new Dialog(this, R.style.invate_dialog_style);
        View view = View.inflate(this, R.layout.withdrawal_limit_shuoming, null);
        dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);

        // 知道了的点击
        View liebiao2 = view.findViewById(R.id.liebiao2);
        liebiao2.setVisibility(View.VISIBLE);
        liebiao2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // 抽取提现额度点击
        view.findViewById(R.id.gobuy2).setVisibility(View.GONE);
        view.findViewById(R.id.liebiao).setVisibility(View.GONE);
        // // 创建自定义样式dialog
        dialog.setContentView(view,
                new LinearLayout.LayoutParams(DP2SPUtil.dp2px(this, 270), LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.show();
    }

//    /**
//     * 活动规则
//     */
//    private void huoDongGuiZeDialog() {
//        final Dialog dialog = new Dialog(context, R.style.invate_dialog_style);
//        View view = View.inflate(context, R.layout.huodongguize_dialog, null);
//        dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
//        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        // // 创建自定义样式dialog
//        dialog.setContentView(view,
//                new LinearLayout.LayoutParams(DP2SPUtil.dp2px(context, 270), LinearLayout.LayoutParams.MATCH_PARENT));
//        dialog.show();
//    }

    /**
     * 今日奖励
     */
    private void jinRiJiangLiDialog() {
        final Dialog dialog = new Dialog(this, R.style.invate_dialog_style);
        View view = View.inflate(this, R.layout.jingrijiangli_dialog, null);
        dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);

        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final TextView newFriendsTv = (TextView) view.findViewById(R.id.num_new_friends);
        final TextView oldFriendsTv = (TextView) view.findViewById(R.id.num_old_friends);
        final TextView eduTv = (TextView) view.findViewById(R.id.edu_tv);
        // TODO 填充数据

        // // 创建自定义样式dialog
        dialog.setContentView(view,
                new LinearLayout.LayoutParams(DP2SPUtil.dp2px(this, 270), LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.show();

        new SAsyncTask<Void, Void, HashMap<String, String>>(PointLikeRankingActivity.this, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel2.getPraiseMoney(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null) {
                    String oldFriends = result.get("oldFriends");
                    String newFriends = result.get("newFriends");
                    String today_rewards = result.get("today_rewards");
                    newFriendsTv.setText("" + newFriends);
                    oldFriendsTv.setText("" + oldFriends);
                    eduTv.setText("" + new DecimalFormat("#0.00").format(Double.parseDouble("" + today_rewards)));
                }
            }

        }.execute();
    }


    /**
     * 分享
     */
    public void getShareWindow() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);

        PointSharePopupwindow window = new PointSharePopupwindow(this);
        window.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params2 = getWindow().getAttributes();
                params2.alpha = 1f;
                getWindow().setAttributes(params2);
            }
        });

    }

//    /**
//     * 自动弹出 活动规则弹框 只需要自动弹出两次
//     */
//    private void showHuoDongGuize() {
//        String HuoDongGuizeTimes = SharedPreferencesUtil.getStringData(context, Pref.POINTHUODONGGUIZE, "0");
//        Long longHuoDongGuizeTimes = Long.valueOf(HuoDongGuizeTimes);
//        if (longHuoDongGuizeTimes < 2) {
//            longHuoDongGuizeTimes++;
//            SharedPreferencesUtil.saveStringData(context, Pref.POINTHUODONGGUIZE, longHuoDongGuizeTimes + "");
//            huoDongGuiZeDialog();
//        }
//    }

    /**
     * \
     * 获得 活动规则相关文案
     */
    private void getHuoDongGuiZeText() {
        String text1 = tvGuiZe1.getText().toString();
        SpannableString textSpan1 = new SpannableString(text1);
        textSpan1.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 6, 12,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvGuiZe1.setText(textSpan1);

        String text2 = tvGuiZe2.getText().toString();
        SpannableString textSpan2 = new SpannableString(text2);
        textSpan2.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 23, 25,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvGuiZe2.setText(textSpan2);

        String text3 = tvGuiZe3.getText().toString();
        SpannableString textSpan3 = new SpannableString(text3);
        textSpan3.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 23, 27,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvGuiZe3.setText(textSpan3);

        String text4_2 = tvGuiZe4_2.getText().toString();
        SpannableString textSpan4_2 = new SpannableString(text4_2);
        textSpan4_2.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 26, 29,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan4_2.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 53, 57,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvGuiZe4_2.setText(textSpan4_2);

        String text6 = tvGuiZe6.getText().toString();
        SpannableString textSpan6 = new SpannableString(text6);
        textSpan6.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 20, 25,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvGuiZe6.setText(textSpan6);
        new SAsyncTask<Void, Void, HashMap<String, String>>(PointLikeRankingActivity.this, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModelL.getContentTextJZJL(YConstance.KeyJT.KEY_JSONTEXT_JZJL);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null) {
                    String n1 = result.get("n1");
                    String n2 = result.get("n2");
                    String n3 = result.get("n3");
                    String n4_1 = result.get("n4-1");
                    String n4_2 = result.get("n4-2");
                    String n6 = result.get("n6");

                    if (!TextUtils.isEmpty(n1)) {
                        String text1 = tvGuiZe1.getText().toString();
                        text1 = text1.replace("马上集赞", n1);
                        tvGuiZe1.setTextColor(Color.WHITE);
                        SpannableString textSpan1 = new SpannableString(text1);
                        textSpan1.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 6, n1.length() + 2 + 6,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvGuiZe1.setText(textSpan1);
                    }

                    if (!TextUtils.isEmpty(n2)) {
                        String text2 = tvGuiZe2.getText().toString();
                        text2 = text2.replace("2元", n2);
                        tvGuiZe2.setTextColor(Color.WHITE);
                        SpannableString textSpan2 = new SpannableString(text2);
                        textSpan2.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 23, n2.length() + 23,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvGuiZe2.setText(textSpan2);
                    }
                    if (!TextUtils.isEmpty(n3)) {
                        String text3 = tvGuiZe3.getText().toString();
                        text3 = text3.replace("0.2元", n3);
                        tvGuiZe3.setTextColor(Color.WHITE);
                        SpannableString textSpan3 = new SpannableString(text3);
                        textSpan3.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 23, n3.length() + 23,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvGuiZe3.setText(textSpan3);
                    }
//                    String text4_2 = tvGuiZe4_2.getText().toString();
//                    text4_2.replaceFirst("100元","200元");
//                    text4_2.replace("400元","500元");
                    String text4_2 = "如你今日邀请了25名微信好友为你点赞，当日你即可获得" +
                            (TextUtils.isEmpty(n4_1) ? "50元" : n4_1) + "首次点赞奖励，外加每日5元点赞奖励。每月轻松得到" +
                            (TextUtils.isEmpty(n4_2) ? "200元" : n4_2) + "提现现金奖励哦。";
                    tvGuiZe4_2.setTextColor(Color.WHITE);
                    SpannableString textSpan4_2 = new SpannableString(text4_2);
                    textSpan4_2.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 26, 26 + n4_1.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textSpan4_2.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), text4_2.length() - 8 - n4_2.length(), text4_2.length() - 8,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvGuiZe4_2.setText(textSpan4_2);

                    if (!TextUtils.isEmpty(n6)) {
                        String text6 = tvGuiZe6.getText().toString();
                        text6 = text6.replace("5000元", n6);
                        tvGuiZe6.setTextColor(Color.WHITE);
                        SpannableString textSpan6 = new SpannableString(text6);
                        textSpan6.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 20, n6.length() + 20,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvGuiZe6.setText(textSpan6);
                    }
                }

            }

        }.execute();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    final HashMap<String, String> map = ComModelL.getContentTextJZJL(YConstance.KeyJT.KEY_JSONTEXT_JZJL);
//                    ((Activity)mContext).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if(map!=null){
//                                String n1 = map.get("n1");
//                                String n2 = map.get("n2");
//                                String n3 = map.get("n3");
//                                String n4_1 = map.get("n4-1");
//                                String n4_2 = map.get("n4-2");
//                                String n6 = map.get("n6");
//
//                                if (!TextUtils.isEmpty(n1)) {
//                                    String text1 = tvGuiZe1.getText().toString();
//                                    text1 = text1.replace("马上去集赞", n1);
//                                    tvGuiZe1.setTextColor(Color.WHITE);
//                                    SpannableString textSpan1 = new SpannableString(text1);
//                                    textSpan1.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 6, n1.length() + 2 + 6,
//                                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                    tvGuiZe1.setText(textSpan1);
//                                }
//
//                                if (!TextUtils.isEmpty(n2)) {
//                                    String text2 = tvGuiZe2.getText().toString();
//                                    text2 = text2.replace("2元", n2);
//                                    tvGuiZe2.setTextColor(Color.WHITE);
//                                    SpannableString textSpan2 = new SpannableString(text2);
//                                    textSpan2.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 23, n2.length() + 23,
//                                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                    tvGuiZe2.setText(textSpan2);
//                                }
//                                if (!TextUtils.isEmpty(n3)) {
//                                    String text3 = tvGuiZe3.getText().toString();
//                                    text3 = text3.replace("0.2元", n3);
//                                    tvGuiZe3.setTextColor(Color.WHITE);
//                                    SpannableString textSpan3 = new SpannableString(text3);
//                                    textSpan3.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 23, n3.length() + 23,
//                                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                    tvGuiZe3.setText(textSpan3);
//                                }
////                    String text4_2 = tvGuiZe4_2.getText().toString();
////                    text4_2.replaceFirst("100元","200元");
////                    text4_2.replace("400元","500元");
//                                String text4_2 = "如你今日邀请了25名微信好友为你点赞，当日你即可获得" +
//                                        (TextUtils.isEmpty(n4_1) ? "50元" : n4_1) + "首次点赞奖励，外加每日5元点赞奖励。每月轻松得到" +
//                                        (TextUtils.isEmpty(n4_2) ? "200元" : n4_2) + "提现现金奖励哦。";
//                                tvGuiZe4_2.setTextColor(Color.WHITE);
//                                SpannableString textSpan4_2 = new SpannableString(text4_2);
//                                textSpan4_2.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 26, 26 + n4_1.length(),
//                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                textSpan4_2.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), text4_2.length() - 8 - n4_2.length(), text4_2.length() - 8,
//                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                tvGuiZe4_2.setText(textSpan4_2);
//
//                                if (!TextUtils.isEmpty(n6)) {
//                                    String text6 = tvGuiZe6.getText().toString();
//                                    text6 = text6.replace("5000元", n6);
//                                    tvGuiZe6.setTextColor(Color.WHITE);
//                                    SpannableString textSpan6 = new SpannableString(text6);
//                                    textSpan6.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 20, n6.length() + 20,
//                                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                    tvGuiZe6.setText(textSpan6);
//                                }
//
//                            }
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }

    /**
     * 获奖感言
     */
    private void getAwardsVoices() {
        new SAsyncTask<Void, Void, HashMap<String, Object>>(PointLikeRankingActivity.this, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getPraiseVoiceList(PointLikeRankingActivity.this);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);

                if (null == e && result != null) {
                    List<HashMap<String, String>> voice_list = (List<HashMap<String, String>>) result.get("voice_list");
                    if(voice_list!=null&&voice_list.size()>0){
                        voiceTitleLl.setVisibility(View.VISIBLE);
                        r_list_view_voice.setVisibility(View.VISIBLE);
                        mVoiceAdapter.setData(voice_list);
                        String period = voice_list.get(0).get("period");//期数 在列表中
                        period = period.length()==1?"0"+period:period;
                        periodsTv.setText(period+"期");
                    }else{
                       voiceTitleLl.setVisibility(View.GONE);
                       r_list_view_voice.setVisibility(View.GONE);
                    }

                }else{
                    voiceTitleLl.setVisibility(View.GONE);
                    r_list_view_voice.setVisibility(View.GONE);
                }
                mVoiceAdapter.notifyDataSetChanged();
            }
        }.execute();
    }


//    private String  musicPath = "https://yssj-real-test.b0.upaiyun.com/test.wav";

    /**
     * 获奖感言 列表adapter
     *
     * @author Administrator
     */
    class VoiceAdapter extends BaseAdapter {
        private final int mMinItemWith;
        private final int mMaxItemWith;
        private List<HashMap<String, String>> mInfos;
        private LayoutInflater layoutInflator;
        private Context context;
        private View viewanim;

        public VoiceAdapter(Context context) {
            this.context = context;
            mInfos = new ArrayList<HashMap<String, String>>();
            layoutInflator = LayoutInflater.from(context);
            // 获取系统宽度
            WindowManager wManager = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wManager.getDefaultDisplay().getMetrics(outMetrics);
            mMaxItemWith = (int) (outMetrics.widthPixels * 0.36f);
            mMinItemWith = (int) (outMetrics.widthPixels * 0.14f);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = layoutInflator.inflate(R.layout.point_like_voice_list, null);
                holder.head_iv = (ImageView) convertView.findViewById(R.id.ranking_head_iv);
                holder.ranking_ninckname = (TextView) convertView.findViewById(R.id.ranking_ninckname);
                holder.ranking_area = (TextView) convertView.findViewById(R.id.ranking_area);
                holder.voice_ll = convertView.findViewById(R.id.voice_ll);
                holder.voice_time = (TextView) convertView.findViewById(R.id.voice_time);
                holder.awards_tv = (TextView) convertView.findViewById(R.id.awards_tv);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
//            PicassoUtils.initImage(PointLikeRankingActivity.this, mInfos.get(position).get("pic"), holder.head_iv);
            GlideUtils.initRoundImage(Glide.with(PointLikeRankingActivity.this),PointLikeRankingActivity.this, mInfos.get(position).get("pic"), holder.head_iv);
            holder.ranking_ninckname.setText(mInfos.get(position).get("nickname"));
            String location;
            if (mInfos.get(position).get("location").length() > 7) {
                location = mInfos.get(position).get("location").substring(0, 7) + "...";
            } else {
                location = mInfos.get(position).get("location");
            }
            holder.ranking_area.setText("" + location);

            String ranking = mInfos.get(position).get("ranking");
            String content = mInfos.get(position).get("content");
            String  path = mInfos.get(position).get("audio");
            final String audioPath = path.startsWith("http")?path: YUrl.imgurl+path;

            if("1".equals(ranking)){
                holder.awards_tv.setText("获得一等奖："+ content);
            }else if("2".equals(ranking)){
                holder.awards_tv.setText("获得二等奖："+ content);
            }else if("3".equals(ranking)){
                holder.awards_tv.setText("获得三等奖："+ content);
            }

            if(TextUtils.isEmpty(path)){
                holder.voice_time.setVisibility(View.GONE);
                holder.voice_ll.setVisibility(View.GONE);
            }else{
                holder.voice_time.setVisibility(View.VISIBLE);
                holder.voice_ll.setVisibility(View.VISIBLE);
            }
            int der_times = 0;//秒
            try {
                der_times = Integer.parseInt(mInfos.get(position).get("audioLength"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
//            String audioLength = mInfos.get(position).get("audioLength");
//            try {
//                String audioLengths [] = audioLength.split(":");
//                int hour =Integer.parseInt(audioLengths[0]);
//                int min =Integer.parseInt(audioLengths[1]);
//                int sec =Integer.parseInt(audioLengths[2]);
//                der_times = hour*3600+min*60+sec;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            holder.voice_time.setText(der_times+"″");
            if(der_times<=0){
                holder.voice_time.setVisibility(View.GONE);
            }else{
                holder.voice_time.setVisibility(View.VISIBLE);
            }

            ViewGroup.LayoutParams lParams=holder.voice_ll.getLayoutParams();
            if(der_times<=1){
                lParams.width=mMinItemWith;
            }else if(der_times>1&&der_times<10){
                lParams.width=(int) (mMinItemWith+(mMaxItemWith-mMinItemWith)/9f*(der_times-1));
            }else if(der_times>=10){
                lParams.width=mMaxItemWith;
            }
            holder.voice_ll.setLayoutParams(lParams);

            holder.voice_ll.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 播放动画
                    if (viewanim!=null) {//让第二个播放的时候第一个停止播放
                        viewanim.setBackgroundResource(R.drawable.voice_anim);
                        viewanim=null;
                    }
                    viewanim = view.findViewById(R.id.id_recorder_anim);
                    if(MediaManager.isPlaying&&MediaManager.position == position){

                    }else{
                        //开始播放 动画
                        viewanim.setBackgroundResource(R.drawable.point_wards_voice_play);
                        AnimationDrawable drawable = (AnimationDrawable) viewanim.getBackground();
                        drawable.start();
                    }



                    MediaManager.playSound(viewanim,position,audioPath,
                            new MediaPlayer.OnCompletionListener() {

                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    MediaManager.release();
                                    viewanim.setBackgroundResource(R.drawable.voice_anim);
                                }
                            });

                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView head_iv;
            TextView ranking_ninckname, ranking_area;// 昵称
            View voice_ll;
            TextView voice_time,awards_tv;
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

        public void setData(List<HashMap<String, String>> result) {
            mInfos.clear();
            mInfos.addAll(result);
            this.notifyDataSetChanged();
        }

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MediaManager.pause();
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if(mTimer1!=null){
            mTimer1.cancel();
        }
        if(task1!=null){
            task1.cancel();
        }
        super.onDestroy();
        MediaManager.release();
    }

    @Override
    public void onBackPressed() {
        if(mTimer1!=null){
            mTimer1.cancel();
        }
        if(task1!=null){
            task1.cancel();
        }
        super.onBackPressed();


    }



}
