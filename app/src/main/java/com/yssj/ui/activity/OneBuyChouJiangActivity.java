package com.yssj.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.data.YDBHelper;
import com.yssj.entity.BaseData;
import com.yssj.entity.ChouJiangAddOrderAg;
import com.yssj.entity.Order;
import com.yssj.entity.ShouSuChouJiangCountData;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelZ;
import com.yssj.model.ModQingfeng;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.infos.StatusInfoActivity;
import com.yssj.ui.activity.shopdetails.PaymentActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.orderinfo.OrderDetailsActivity;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.DialogUtils;
import com.yssj.utils.GlideUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SimpleCountDownTimer;
import com.yssj.utils.StringUtils;
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
 * 1元抽奖
 */
public class OneBuyChouJiangActivity extends BasicActivity implements OnClickListener {


    @Bind(R.id.img_back)
    LinearLayout imgBack;
    @Bind(R.id.im_scan)
    ImageView imScan;
    @Bind(R.id.id_start_btn)
    ImageView idStartBtn;
    @Bind(R.id.list_view1)
    ListView listView1;
    @Bind(R.id.tv_time)
    TextView tvTime; //3 2 1计时


    private ObjectAnimator scanAnimator; //转动动画


    private boolean isRuning;//是否在转动


    private Handler mHander = new Handler();

    private long allTime = 2000L;//2531

    private String type2 = "";//二级类目
    private String price = "";//单独购买价格
    private String supName = "衣蝠";//品牌
    private String mealShopName = "";//特卖商品名称


    private ArrayList<String> sedLeim = new ArrayList<>();//二级类目集合

    private ArrayList<String> allSubList = new ArrayList<>();//所有品牌集合


    double speed;//1毫秒几度

    private boolean timeOut;

    private int luckyCount = 1;//抽奖剩余次数---初始化接口返回


    private boolean isNewMeal;

    private String orderCode;

    Timer timer;


    private int timeCount = 3;

    private boolean isExitHint = false;

    private String zhoujiangShowOnePrice;

    private boolean canZhongiang;//是否可以中奖


    private Order order; // 订单

    private YDBHelper dbHelp;

    private String shop_name = "";

    private boolean initOrderComplete;//订单初始化是否完成，没有完成不能开始抽奖


    private int firstGroup;
    private Dialog mDialog;
    private SimpleCountDownTimer countDownTimer;
    private Context mContext;
    private int buyCountUseUpCount;
    private boolean mIsVip;
    public int mMaxType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_one_choujiang);
        mContext = this;
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        isExitHint = false;
        dbHelp = new YDBHelper(this);
        Bundle bundle = getIntent().getExtras();
        order = (Order) bundle.getSerializable("order");

        price = order.getList().get(0).getShop_price() + "";
        zhoujiangShowOnePrice = order.getOrder_price() + "";
        orderCode = order.getOrder_code();

        isNewMeal = getIntent().getBooleanExtra("isMeal", false);

        if (isNewMeal) {
            mealShopName = order.getList().get(0).getShop_name(0);
        }
        shop_name = order.getList().get(0).getShop_name(0);

        String sql = "select * from supp_label where _id = " + order.getList().get(0).getSupp_id() + " order by _id";
        List<HashMap<String, String>> listSupp = dbHelp.query(sql);// 品牌
        if (listSupp != null && listSupp.size() > 0) {
            supName = listSupp.get(0).get("name");
        }


        tvTime.setText("3");


//        YConn.httpPost(mContext, YUrl.QUERY_VIP_INFO_FL, new HashMap<String, String>()
//                , new HttpListener<VipInfo>() {
//                    @Override
//                    public void onSuccess(VipInfo vipInfo) {
////                        mIsVip = CommonActivity
//                        initOrder(false);
//
//
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });

        CommonUtils.getVip(mContext, new CommonUtils.GetVipListener() {
            @Override
            public void callBack(boolean isVip, int maxType) {
                mIsVip = isVip;
                mMaxType = maxType;
                initOrder(false);
            }
        });


        initLimitAwardsList();


        //初始化不停的转的动画
        scanAnimator = ObjectAnimator.ofFloat(imScan, "rotation", 0f, 360.0f);
        scanAnimator.setDuration(allTime);
        scanAnimator.setInterpolator(new LinearInterpolator());//不停顿
        scanAnimator.setRepeatCount(-1);//设置动画重复次数
        scanAnimator.setRepeatMode(ValueAnimator.RESTART);//动画重复模式
///

        speed = (double) 360 / allTime;

        LogYiFu.e("所需转速", speed + "");


    }

    //始化抽奖订单数据
    private void initOrder(final boolean isClickShuoMingStart) {
        initOrderComplete = false;
        canZhongiang = false;


        HashMap map = new HashMap<String, String>();
        map.put("order_code", orderCode);

        YConn.httpPost(this, YUrl.INIT_ONEBUY_ORDER, map, new HttpListener<ShouSuChouJiangCountData>() {
            @Override
            public void onSuccess(ShouSuChouJiangCountData result) {
                firstGroup = result.getFirstGroup();
                luckyCount = result.getRemainder();

                if (isClickShuoMingStart) {
                    startZhuan();
                } else {
                    getZhongJangStatus();

                }

            }

            @Override
            public void onError() {

            }
        });


//        new SAsyncTask<Void, Void, Integer>(this, R.string.wait) {
//
//            @Override
//            protected Integer doInBackground(FragmentActivity context, Void... params)
//                    throws Exception {
//
//                return ComModel2.initChoujiangOrder(context, orderCode);
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, Integer result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (null == e) {
//
//
//                    if (result != -1) {//初始化完成获取是否可以中奖
//
//                        luckyCount = result;
//                        initOrderComplete = true;
//                        if (isClickShuoMingStart) {
//                            startZhuan();
//                        } else {
//                            getZhongJangStatus();
//
//                        }
//                    }
//
//
//                }
//
//
//            }
//
//        }.execute();

    }

    private void getZhongJangStatus() {


        new SAsyncTask<Void, Void, Boolean>(this, R.string.wait) {

            @Override
            protected Boolean doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ModQingfeng.getOneBuyWhetherZhongjiang(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, Boolean result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    canZhongiang = result;//拿到是否可以中奖后开始旋转
                    initOrderComplete = true;


                    //弹出抽奖说明(自动弹出)
                    shouJiangShuoming(false);

                }
            }

        }.execute();
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if (luckyCount != 0) {
            ToastUtil.showShortText(this, "您的免费领次数尚未用完哦。");

            return;
        }
        if (!isRuning) {
            if (isExitHint) {
                finish();
            } else {
                DialogUtils.getDiKouDialogNew(this, "本次未抢中哦", true, false);
                isExitHint = true;
            }
        } else {
            ToastUtil.showShortText(this, "现在不能返回哦~");
            return;
        }
    }

    private void startZhuan() {//开始旋转动画


        timeCount = 3; //重置计时

        tvTime.setText(timeCount + "");
        tvTime.setVisibility(View.VISIBLE);

        isRuning = true;
        scanAnimator.start();//旋转动画开始
        idStartBtn.setImageResource(R.drawable.zhizhen_stop);
        if (null != timer) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();

        //倒计时开始
        timeOut = false;
        timer.schedule(new TimeCoutTimeTask(), 1000, 1000);       // timeTask
    }


    @SuppressLint("NewApi")
    @OnClick({R.id.img_back, R.id.id_start_btn, R.id.tv_guize})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.tv_guize:
                if (luckyCount < 0) {
                    ToastUtil.showShortText2("请稍后");
                    return;
                }
                //点击弹出
                shouJiangShuoming(true);
                break;
            case R.id.id_start_btn: //开始、停、再来一次的按钮

                if (!timeOut) { //倒计时未结束时不能点
                    return;
                }


                if (!initOrderComplete) {
                    ToastUtil.showShortText2("初始化尚未完成，请稍后再试");
                    return;
                }

                if (!isRuning) {//没有转 ---此时用户能点到的就是再抽一次


                    choujiangOnceSubitOrder();//再抽一次下单


                } else { //正在转--点击的后开始停止动画---停止动画的判断


                    isRuning = false;


                    //获取动画执行的程度
                    float af = scanAnimator.getAnimatedFraction();

                    //中奖区域度数（356（不含）-5(不含)）


                    //获取点击时转盘度数
                    float curretAg = af * 360;

                    //判断
                    if (curretAg >= 3 && curretAg <= 350) {//此时未到中奖区域，直接停止动画
                        LogYiFu.e("所需", "未到中奖区域");
                        //停止转的动画
                        scanAnimator.pause();//暂停动画
                        mHander.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                idStartBtn.setImageResource(R.drawable.zhizhen_con);
                                showChoujinagCompleteDialog();
                            }
                        }, 200);
                    } else {//到中奖区域


                        if (canZhongiang) {//可以中奖


                            if (curretAg > 350) {

                                double needAg;//转到0度需要旋转的角度
                                needAg = 360 - curretAg;

                                LogYiFu.e("中奖了转到0度所需角度", needAg + "");


                                //计算需要旋转的时间
                                double mNeetTime = needAg / speed;


                                LogYiFu.e("中奖了转到0度所需所需时间", mNeetTime + "");

                                mHander.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        //停止转的动画
                                        scanAnimator.pause();//暂停动画
                                        mHander.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                idStartBtn.setImageResource(R.drawable.zhizhen_con);
                                                showZhongJangDialog();//中奖提示
                                            }
                                        }, 200);


                                    }
                                }, (long) mNeetTime);

                            } else {
                                //停止转的动画
                                scanAnimator.pause();//暂停动画
                                mHander.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        idStartBtn.setImageResource(R.drawable.zhizhen_con);
                                        showZhongJangDialog();
                                    }
                                }, 200);
                            }

                            return;

                        }
                        //到了中奖区域但是不能中奖
                        LogYiFu.e("所需当前度数", curretAg + "");
                        double needAg;//转到10度需要旋转的角度
                        if (curretAg > 350) {
                            needAg = 360 - curretAg + 10;
                        } else {
                            needAg = 10 - curretAg;
                        }
                        LogYiFu.e("所需角度", needAg + "");


                        //计算需要旋转的时间
                        double mNeetTime = needAg / speed;
                        //启动定时任务mNeetTime后停止旋转动画
                        //停止转的动画
                        LogYiFu.e("到10°所需时间", mNeetTime + "");


                        long zNeedTime = (long) mNeetTime + 1;


                        LogYiFu.e("所需时间", zNeedTime + "");

                        mHander.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                //停止转的动画
                                scanAnimator.pause();//暂停动画
                                mHander.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        idStartBtn.setImageResource(R.drawable.zhizhen_con);
                                        showChoujinagCompleteDialog();
                                    }
                                }, 200);


                            }
                        }, zNeedTime);

                    }


                }

                break;
        }
    }

    private void showZhongJangDialog() {//中奖了

        //通知后台中奖了，引导进入订单详情
        new SAsyncTask<Void, Void, Order>(this, R.string.wait) {

            @Override
            protected Order doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel2.feedBackOneBuyChoujiangZJ(context, orderCode, firstGroup);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(final FragmentActivity context, final Order result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {


                    if (isNewMeal) {
                        if (null != countDownTimer) {
                            countDownTimer.cancel();
                        }
                        if (null != mDialog) {
                            mDialog.dismiss();
                        }
                        mDialog = new Dialog(context, R.style.invate_dialog_style);
                        mDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
                        mDialog.setCanceledOnTouchOutside(false);
                        View view = View.inflate(context, R.layout.one_choujiang_chouzhong, null);
                        ((TextView) view.findViewById(R.id.tv2)).setText(Html.fromHtml("恭喜以<font color='#FDCC21'><strong>" + zhoujiangShowOnePrice + "元</strong></font>的价格买走了价值<font color='#FDCC21'><strong>" + price + "元</strong></font>的<font color='#FDCC21'><strong>" + mealShopName + "</strong></font>"));

                        // 知道了
                        view.findViewById(R.id.btn_yellow).setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {


                                Intent intent = new Intent(context,
                                        OrderDetailsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("order", result);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                mDialog.dismiss();
                                finish();


                            }
                        });

                        // // 创建自定义样式dialog
                        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(context, 270),
                                LinearLayout.LayoutParams.MATCH_PARENT));
                        mDialog.show();

                    } else {//普通商品需要查询二级类目和品牌
                        getType2SupName(result);

                    }


                }
            }

        }.execute();


    }

    private void getType2SupName(final Order order) {


        new SAsyncTask<String, Void, HashMap<String, String>>(this, R.string.wait) {
            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                return ComModelZ.geType2SuppLabe(OneBuyChouJiangActivity.this, order.getList().get(0).getShop_code());
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }


            @Override
            protected void onPostExecute(final FragmentActivity context, final HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null) {
                    type2 = result.get("type2");
                    supName = result.get("supp_label_id");

                    if (null != countDownTimer) {
                        countDownTimer.cancel();
                    }
                    if (null != mDialog) {
                        mDialog.dismiss();
                    }
                    mDialog = new Dialog(context, R.style.invate_dialog_style);
                    mDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
                    mDialog.setCanceledOnTouchOutside(false);
                    View view = View.inflate(context, R.layout.one_choujiang_chouzhong, null);

                    ((TextView) view.findViewById(R.id.tv2)).setText(Html.fromHtml("恭喜以<font color='#FDCC21'><strong>" + zhoujiangShowOnePrice + "元</strong></font>的价格买走了价值<font color='#FDCC21'><strong>" + price + "元</strong></font>的<font color='#FDCC21'><strong>" + supName + type2 + "</strong></font>"));


                    // 知道了
                    view.findViewById(R.id.btn_yellow).setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {


                            Intent intent = new Intent(context,
                                    OrderDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("order", order);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            mDialog.dismiss();
                            finish();


                        }
                    });

                    // // 创建自定义样式dialog
                    mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(context, 270),
                            LinearLayout.LayoutParams.MATCH_PARENT));
                    mDialog.show();


                }

            }

        }.execute();


    }


    private void shouJiangShuoming(final boolean isClickShuoMingStart) {
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = View.inflate(mContext, R.layout.onechoujiang_shuoming, null);
        mDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);

        TextView tv1 = (TextView) view.findViewById(R.id.tv1);
        TextView tv2 = (TextView) view.findViewById(R.id.tv2);
        TextView tv3 = (TextView) view.findViewById(R.id.tv3);
        TextView tv4 = (TextView) view.findViewById(R.id.tv4);


        tv1.setText(Html.fromHtml("1.点下方<font color='#FDCC21'><strong>\"开始\"</strong></font>，转盘指针开始转动。"));
        tv2.setText(Html.fromHtml("2.点转盘中央<font color='#FDCC21'><strong>\"停\"</strong></font>，如转盘指针<font color='#FDCC21'><strong>停在指针中央处</strong></font>，即成功领走商品。"));
        tv3.setText(Html.fromHtml("3.本轮你有<font color='#FDCC21'><strong>" + luckyCount
                + "次</strong></font>点停机会。未使用完退出本页面会导致<font color='#FDCC21'><strong>次数清0</strong></font>，切记。"));

        buyCountUseUpCount = luckyCount;
        //开始的点击
        view.findViewById(R.id.gobuy2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { //开始按钮--刚进来可以直接抽


                if (initOrderComplete) {
                    startZhuan();

                } else {
                    //初始化订单
                    initOrder(isClickShuoMingStart);
                }


//
//                if (!isClick) {
//                    //初始化订单
////                    initOrder();
//
//                    startZhuan();
//
//                }


                mDialog.dismiss();
            }
        });

        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.show();
    }


    /**
     * 获取数据  奖励列表   额度
     */
    private void initLimitAwardsList() {


        //所有的二级类目


        String sql = "select * from sort_info where is_show = 1 order by _id";
        List<HashMap<String, String>> sed = dbHelp.query(sql);
        if (sed.size() > 0) {
            for (int i = 0; i < sed.size(); i++) {
                HashMap<String, String> mMap = sed.get(i);
                for (int j = 0; j < mMap.size(); j++) {
                    sedLeim.add(mMap.get("sort_name"));
                }
            }
        }


        String sqlSub = "select * from supp_label where type = 1 order by _id";
        List<HashMap<String, String>> listSub = dbHelp.query(sqlSub);


        if (listSub.size() > 0) {
            for (int i = 0; i < listSub.size(); i++) {
                HashMap<String, String> mMap = listSub.get(i);
                for (int j = 0; j < mMap.size(); j++) {
                    allSubList.add(mMap.get("name"));
                }
            }
        }


        for (int i = 0; i < 50; i++) {
            addToLimitList();
        }

        adapter1 = new MyAdapter(this, mListData1);
        listView1.setAdapter(adapter1);
        if (mTimer1 != null) {
            mTimer1.cancel();
        }
        mTimer1 = new Timer();
        mTimer1.schedule(task1, 20, 20);
    }

    private MyAdapter adapter1;
    private Timer mTimer1;
    private ArrayList<HashMap<String, String>> mListData1 = new ArrayList<>();

    private TimerTask task1 = new TimerTask() {

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


    private void addToLimitList() {
        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("nname", StringUtils.getVirtualName() + "***" + StringUtils.getVirtualName());


        String ramSubName = allSubList.get((int) (Math.random() * allSubList.size()));////随机品牌
        String ramLeim = sedLeim.get((int) (Math.random() * sedLeim.size()));//随机二级类目

        String[] allOnePrice = {"9.9", "19.9", "29.9"};
//        map1.put("p_name", allOnePrice[(int) (Math.random() * 3)] + "元买走了" + ramSubName + ramLeim);
        map1.put("p_name", "免费领走了" + ramSubName + ramLeim);


        map1.put("pic", "defaultcommentimage/" + StringUtils.getDefaultImg());

        //1-500随机数
        String ram500 = StringUtils.getRandomInt(100, 400) + ".0";
        map1.put("num", "原价" + ram500 + "元");


        mListData1.add(map1);
    }

    @OnClick(R.id.tv_time)
    public void onViewClicked() {

    }


    /**
     * 滚动的 奖励列表  的 Adapter
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
                convertView = View.inflate(OneBuyChouJiangActivity.this, R.layout.item_withdrawal_limit, null);
                holder.mNameTv = (TextView) convertView.findViewById(R.id.withdrawal_name_tv);
                holder.tv = (TextView) convertView.findViewById(R.id.withdrawal_exp_tv);
                holder.mAwardsTv = (TextView) convertView.findViewById(R.id.withdrawal_awards_tv);
                holder.headIv = (ImageView) convertView.findViewById(R.id.withdrawal_head_iv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            GlideUtils.initRoundImage(Glide.with(OneBuyChouJiangActivity.this), OneBuyChouJiangActivity.this, mListData.get(position % mListData.size()).get("pic").toString(), holder.headIv);

            holder.mNameTv.setText(mListData.get(position % mListData.size()).get("nname").toString());
            holder.mAwardsTv.setText(mListData.get(position % mListData.size()).get("num").toString());


            String pName = mListData.get(position % mListData.size()).get("p_name").toString();
            if (pName.length() > 14) {
                pName = pName.substring(0, 12) + "...";
            }

            holder.tv.setText(pName);
            return convertView;
        }


    }

    public class ViewHolder {
        TextView mNameTv, tv, mAwardsTv;
        ImageView headIv;
    }


    /**
     * 没有抽中
     */
    private void showChoujinagCompleteDialog() {

        luckyCount--;


        //通知后台未中奖
        new SAsyncTask<Void, Void, Boolean>(this, R.string.wait) {

            @Override
            protected Boolean doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel2.feedBackOneBuyChoujiang(context, orderCode, firstGroup);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, Boolean result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {

                    if (luckyCount == 0) {//抽奖次数为0时单独弹出一个


                        if (mIsVip) {//花钱买次数

                            HashMap<String, String> map = new HashMap<>();
                            map.put("order_code", orderCode);
                            YConn.httpPost(mContext, YUrl.QUERY_CJ_BUY_COUNT, map, new HttpListener<BaseData>() {
                                @Override
                                public void onSuccess(BaseData result) {

                                    if (result.getVipFreeDrawNum() > 0) {


                                        if (null != countDownTimer) {
                                            countDownTimer.cancel();
                                        }
                                        if (null != mDialog) {
                                            mDialog.dismiss();
                                        }

                                        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
                                        View view = View.inflate(mContext, R.layout.dialog_withdraw_chouzhong_txed_buy_count, null);

                                        TextView tv_money = view.findViewById(R.id.tv_money);
                                        TextView tv1 = view.findViewById(R.id.tv1);
                                        TextView tv2 = view.findViewById(R.id.tv2);
                                        TextView tv3 = view.findViewById(R.id.tv3);
                                        TextView bt1 = view.findViewById(R.id.bt1);
                                        TextView bt2 = view.findViewById(R.id.bt2);
                                        TextView tv_time = view.findViewById(R.id.tv_time);
                                        TextView tv_shixiao = view.findViewById(R.id.tv_shixiao);
                                        tv_shixiao.setText("后失效");
                                        tv_money.setText("很遗憾，未领中！");
                                        tv_money.setTextSize(20);
                                        bt1.getPaint().setFakeBoldText(true);
                                        view.findViewById(R.id.rl_close).setVisibility(View.INVISIBLE);

                                        tv1.setVisibility(View.GONE);


                                        Spanned sd2 = Html.fromHtml("您的<font color='#FDCC21'><strong>" +
                                                buyCountUseUpCount + "次"
                                                + "</strong></font>机会已用完。可以支付<font color='#FDCC21'><strong>"
                                                + result.getVipFreeDrawMoney() + "元</strong></font>增加<font color='#FDCC21'><strong>"
                                                + result.getVipFreeDrawNum() + "次</strong></font>免费领机会，领中立即<font color='#FDCC21'><strong>免费发货</strong></font>!"
                                        );
                                        tv2.setText(sd2);

                                        Spanned sd3 = Html.fromHtml("支付的<font color='#FDCC21'><strong>"
                                                + result.getVipFreeDrawMoney() + "元全额返还</strong></font>至您衣蝠钱包的购物余额，可以用来购买任意商品！等于白送。"
                                        );
                                        tv3.setText(sd3);
                                        tv3.setVisibility(View.VISIBLE);


                                        long countTime = 9 * 60 * 1000;

                                        countDownTimer = new SimpleCountDownTimer(countTime, tv_time).setOnFinishListener(new SimpleCountDownTimer.OnFinishListener() {
                                            @Override
                                            public void onFinish() {
                                                if (null != mDialog) {
                                                    mDialog.dismiss();
                                                }
                                            }
                                        });
                                        countDownTimer.start();

                                        bt1.setText("支付" + result.getVipFreeDrawMoney() + "元增加" + result.getVipFreeDrawNum() + "次机会");
                                        bt2.setText("离开");


                                        bt1.setOnClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(View view) {//买次数下单
                                                submitBuyCount();


                                            }
                                        });


                                        bt2.setOnClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                startActivity(
                                                        new Intent(OneBuyChouJiangActivity.this, StatusInfoActivity.class)
                                                                .putExtra("index", 0)
                                                                .putExtra("isNewUserFirstOrder", true)


                                                );
                                                finish();

                                            }
                                        });


                                        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                                                LinearLayout.LayoutParams.MATCH_PARENT));
                                        mDialog.setCancelable(false);
                                        mDialog.show();

                                        return;
                                    }

                                    startActivity(
                                            new Intent(OneBuyChouJiangActivity.this, StatusInfoActivity.class)
                                                    .putExtra("index", 0)
                                                    .putExtra("isNewUserFirstOrder", true));
                                    finish();


                                }

                                @Override
                                public void onError() {

                                }
                            });


                            return;
                        }
                        startActivity(
                                new Intent(OneBuyChouJiangActivity.this, StatusInfoActivity.class)
                                        .putExtra("index", 0)
                                        .putExtra("isNewUserFirstOrder", true));
                        finish();
                        return;


                    }
                    if (null != countDownTimer) {
                        countDownTimer.cancel();
                    }
                    if (null != mDialog) {
                        mDialog.dismiss();
                    }
                    mDialog = new Dialog(context, R.style.invate_dialog_style);
                    mDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
                    mDialog.setCanceledOnTouchOutside(false);
                    View view = View.inflate(context, R.layout.one_choujiang_no_chouzhong, null);

                    TextView tv_choujiang_count = (TextView) view.findViewById(R.id.tv_choujiang_count);
                    tv_choujiang_count.setText(Html.fromHtml("您还有<font color='#FDCC21'><strong>" + luckyCount + "次</strong></font>机会。"));

                    view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                        }
                    });
                    // 再来一次
                    view.findViewById(R.id.btn_yellow).setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            choujiangOnceSubitOrder();
                            mDialog.dismiss();

                        }
                    });

                    // // 创建自定义样式dialog
                    mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(context, 270),
                            LinearLayout.LayoutParams.MATCH_PARENT));
                    mDialog.show();


                }
            }

        }.execute();


    }

    private void submitBuyCount() {


        HashMap<String, String> map = new HashMap<>();
        map.put("order_code", orderCode);

        YConn.httpPost(mContext, YUrl.SUBMIT_CJ_BUY_COUNT, map, new HttpListener<BaseData>() {
            @Override
            public void onSuccess(BaseData result) {
                if (null != result.getDraw_code()) {

                    // 跳转到收银台支付界面
                    Intent intent = new Intent(OneBuyChouJiangActivity.this, PaymentActivity.class);
                    intent.putExtra("order_code", result.getDraw_code());
                    intent.putExtra("totlaAccount", result.getPrice());
                    intent.putExtra("isBuyCoujiangCount", true);

                    OneBuyChouJiangActivity.this.startActivityForResult(intent, 1003);
                }

            }

            @Override
            public void onError() {

            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mTimer1 != null) {
            mTimer1.cancel();
        }
        if (task1 != null) {
            task1.cancel();
        }
    }

    class TimeCoutTimeTask extends TimerTask {


        @Override
        public void run() {
            timeCount--;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    if (timeCount == 0) { //计时结束
                        timeOut = true;
                        tvTime.setVisibility(View.GONE);
                        timer.cancel();
                    } else {
                        tvTime.setText(timeCount + "");

                    }


                }
            });
        }
    }


    //再抽一次下单
    private void choujiangOnceSubitOrder() {


        if (luckyCount > 0) {//次数未用完--直接开始抽
            startZhuan();
            return;
        }
        initOrderComplete = false;

        HashMap<String, String> pairsMap = new HashMap<>();
        pairsMap.put("order_code", orderCode);
        YConn.httpPost(this, YUrl.ONEBUY_SUBMIT_AG, pairsMap, new HttpListener<ChouJiangAddOrderAg>() {
            @Override
            public void onSuccess(ChouJiangAddOrderAg result) {
                if (result.getTri() == 0) {
                    zhoujiangShowOnePrice = result.getPrice() + "";
                    orderCode = result.getOrder_code() + "";
                    initOrder(false);
                } else {
                    BasicActivity.goToGuideVipOrToMyVipList(OneBuyChouJiangActivity.this, result.getVip_type());
                    finish();
                }
            }

            @Override
            public void onError() {
            }
        });

    }

    public static final int BUY_COUNT_SUCCESS = 1005;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 1003 && resultCode == BUY_COUNT_SUCCESS) {//再次下单支付成功--弹出说明
            initOrderComplete = false;
            canZhongiang = false;


            HashMap map = new HashMap<String, String>();
            map.put("order_code", orderCode);

            YConn.httpPost(this, YUrl.INIT_ONEBUY_ORDER_BUG_COUNT, map, new HttpListener<ShouSuChouJiangCountData>() {
                @Override
                public void onSuccess(ShouSuChouJiangCountData result) {
                    firstGroup = result.getFirstGroup();
                    luckyCount = result.getRemainder();


                    getZhongJangStatusBuyCount();


                }

                @Override
                public void onError() {

                }
            });

        }
    }


    private void getZhongJangStatusBuyCount() {


        new SAsyncTask<Void, Void, Boolean>(this, R.string.wait) {

            @Override
            protected Boolean doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ModQingfeng.getOneBuyWhetherZhongjiang(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, Boolean result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    canZhongiang = result;//拿到是否可以中奖后开始旋转
                    initOrderComplete = true;


                    //弹出抽奖说明(自动弹出)
                    shouJiangShuomingBuyCount();

                }
            }

        }.execute();
    }


    private void shouJiangShuomingBuyCount() {

        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }


        mDialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = View.inflate(mContext, R.layout.onechoujiang_shuoming, null);
        mDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);

        TextView tv1 = (TextView) view.findViewById(R.id.tv1);
        TextView tv2 = (TextView) view.findViewById(R.id.tv2);
        TextView tv3 = (TextView) view.findViewById(R.id.tv3);
        TextView tv4 = (TextView) view.findViewById(R.id.tv4);


        tv1.setText(Html.fromHtml("1.点下方<font color='#FDCC21'><strong>\"开始\"</strong></font>，转盘指针开始转动。"));
        tv2.setText(Html.fromHtml("2.点转盘中央<font color='#FDCC21'><strong>\"停\"</strong></font>，如转盘指针<font color='#FDCC21'><strong>停在指针中央处</strong></font>，即成功领走商品。"));
        tv3.setText(Html.fromHtml("3.本轮你有<font color='#FDCC21'><strong>" + luckyCount
                + "次</strong></font>点停机会。未使用完退出本页面会导致<font color='#FDCC21'><strong>次数清0</strong></font>，切记。"));

        buyCountUseUpCount = luckyCount;
        //开始的点击
        view.findViewById(R.id.gobuy2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startZhuan();
                mDialog.dismiss();
            }
        });

        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.show();
    }

}
