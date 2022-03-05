package com.yssj.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.GroupsItemView;
import com.yssj.model.ComModel2;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.GroupsPayDialog;
import com.yssj.ui.dialog.GroupsResultDialog;
import com.yssj.ui.dialog.GroupsShareDialog;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.YCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/***
 * 3.5.2 版本 新的拼团  拼团详情
 */
public class GroupsDetailsActivity extends BasicActivity implements OnClickListener {

    private View img_back;
    private GroupsAdapter mAdapter;
    private ListView r_list_view;
    public int width;
    public int height;
    private View nodataView;
    private TextView tv_no_data;
    private View bottomView;
    private TextView bottomTv;
    private TextView mTvTimeHours, mTvTimeMinutes, mTvTimeSeconds, mTvNeed;// 倒计时TextView
    private int completeStatus;
    private int needs;//还需要多少人参团
    private String r_code;
    private View intivate_need_view,intivate_content_view,bottom_no_groups;
    private boolean isTuanEnd;
    private TextView need_people_tv2,need_people_tv1;
    private long recLenPay;//付款过期时间
    private int is_pay;//是否付过款
    private int n_status;//:0 人数没满 1人满了等付款
    private HashMap<String, String> orderMap;//当前用户参团的商品相关数据

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_details);
        context = this;
        AppManager.getAppManager().addActivity(this);
        //completeStatus 3 赚钱任务拼团完成   completeStatus 4 下单开团成功  completeStatus 5 支付成功
        completeStatus = getIntent().getIntExtra("completeStatus",0);
        isTuanEnd = getIntent().getBooleanExtra("isTuanEnd",false);
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        View headView = LayoutInflater.from(context).inflate(R.layout.groups_details_head_view, null);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        r_list_view = (ListView) findViewById(R.id.r_list_view);
        r_list_view.addHeaderView(headView);
        mTvTimeHours = (TextView) headView.findViewById(R.id.time_tv_hours);
        mTvTimeMinutes = (TextView) findViewById(R.id.time_tv_minutes);
        mTvTimeSeconds = (TextView) findViewById(R.id.time_tv_seconds);
        mTvNeed = (TextView) findViewById(R.id.need_people);//拼团还差人数
        need_people_tv2 = (TextView) headView.findViewById(R.id.need_people_tv2);
        need_people_tv1 = (TextView) headView.findViewById(R.id.need_people_tv1);
        intivate_need_view = headView.findViewById(R.id.intivate_need_view);
        intivate_content_view = headView.findViewById(R.id.intivate_content_view);
        bottom_no_groups = findViewById(R.id.bottom_no_groups);


        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;


        mAdapter = new GroupsAdapter(this);

//		/*
//         *
//		 */
//		List<HashMap<String, Object>> mInfos = new ArrayList<HashMap<String, Object>>();
//		for (int i = 0; i <10 ; i++) {
//			mInfos.add(new HashMap<String, Object>());
//		}
//		mAdapter.setData(mInfos);
//		/*
//		 *
//		 */
        r_list_view.setAdapter(mAdapter);

        nodataView = findViewById(R.id.no_data);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);

        bottomView = findViewById(R.id.id_bottom);
        bottomTv = (TextView) findViewById(R.id.bottom_btn);
        bottomTv.setOnClickListener(this);
        bottomTv.setClickable(false);
//        SignListAdapter.tuanClass=2;
        if(SignListAdapter.tuanClass==1){
            intivate_need_view.setVisibility(View.VISIBLE);
            intivate_content_view.setVisibility(View.VISIBLE);
        }else{
            intivate_need_view.setVisibility(View.GONE);
            intivate_content_view.setVisibility(View.GONE);
        }
        queryInitData();

    }


    /**
     * 获取参团总人数和拼团有效时间
     */
    private int rnum=10;//需要的拼团总人数
    private int validHour=12;//拼团有效时间（小时）
    private void queryInitData(){
        new SAsyncTask<String, Void, HashMap<String, String>>(this, 0) {
            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {

                return ComModel2.queryInitData(context);
            }

            @Override
            protected boolean isHandleException() {

                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null) {
                    rnum = Integer.parseInt(result.get("rnum"));
                    validHour = Integer.parseInt(result.get("validHour"));
                }
                initData();
            }

        }.execute();
    }

    private void initData() {

        new SAsyncTask<String, Void, List<HashMap<String, String>>>(this, 0) {
            @Override
            protected List<HashMap<String, String>> doInBackground(FragmentActivity context, String... params)
                    throws Exception {

                if(SignListAdapter.tuanClass==1){
                    return ComModel2.queryGroupDetailsList(context, SignListAdapter.tuanClass);//1开团
                }else{
//                    return ComModel2.queryGroupDetailsListToCode(context, "170720iuSDeQLq");
                    if("-1".equals(SignListAdapter.pingTuanNum)|| TextUtils.isEmpty(SignListAdapter.pingTuanNum)){
                        return ComModel2.queryGroupDetailsList(context, SignListAdapter.tuanClass);//1开团 2 参团
                    }else{
                        return ComModel2.queryGroupDetailsListToCode(context, SignListAdapter.pingTuanNum);
                    }

                }

            }

            @Override
            protected boolean isHandleException() {

                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<HashMap<String, String>> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null&&result != null&&result.size()>0) {
                    if(SignListAdapter.tuanClass==1){
                        bottomView.setVisibility(View.VISIBLE);
                        bottomTv.setVisibility(View.VISIBLE);
                        bottom_no_groups.setVisibility(View.GONE);
                    }else{
                        bottomView.setVisibility(View.GONE);
                    }
                    r_list_view.setVisibility(View.VISIBLE);
                    nodataView.setVisibility(View.GONE);
                    HashMap<String, String> map0 = new HashMap<String, String>();//第一个拼团发起人

                    if("1".equals(result.get(0).get("type"))){//type 1 拼团发起
                        map0  =  result.get(0);
                    }else{
                        for (int i = 0; i <result.size()-1 ; i++) {
                            if("1".equals(result.get(i).get("type"))){
                                map0  =  result.get(i);
                                break;
                            }

                        }
                    }
                    r_code = map0.get("r_code");//拼团编号
                    needs = rnum-result.size();//拼团还差多少人
                    needs = needs<=0?0:needs;
                    mTvNeed.setText(needs+"");
                    int status = Integer.parseInt( map0.get("status"));

                    if (timer != null) {
                        timer.cancel();
                    }
                    recLen = validHour * 60 * 60 * 1000 + Long.parseLong(map0.get("add_time"))
                    -Long.parseLong(map0.get("now"));//12小时过期时间 拼团过期时间

                    recLenPay =Long.parseLong(map0.get("pay_end_time"))
                            -Long.parseLong(map0.get("now"));//付款过期时间
                    is_pay =Integer.parseInt(map0.get("is_pay"));
                    n_status =Integer.parseInt(map0.get("n_status"));

                    if(n_status == 1|| needs<=0){//人满后 团倒计时改为付款倒计时
                        recLen = recLenPay;
                    }

                    timer = new Timer();
                    timer.schedule(new MyTimerTask(), 0, 1000);

                    mAdapter.setData(result);

                    bottomTv.setClickable(true);
                    if(/*recLen>0&&*/needs<=0){//时间没到 人数已经满 (开团的时候 界面的调整)
                        need_people_tv2.setText("团员已满，付款人数未达到，请稍后。");
                        need_people_tv1.setVisibility(View.GONE);
                        mTvNeed.setVisibility(View.GONE);
                        intivate_content_view.setVisibility(View.GONE);
                        bottomTv.setBackgroundResource(R.drawable.btn_back);
                        bottomTv.setClickable(false);
                    }

                    orderMap = new HashMap<String, String>();
                    boolean isTakegroups = false;//当前用户是否参与了当前拼团
                    if(SignListAdapter.tuanClass==2){
                        for (int i = 0; i <result.size() ; i++) {
                            if((YCache.getCacheUser(context).getUser_id()+"").equals(result.get(i).get("user_id"))){
                                isTakegroups  =  true;
                                orderMap = result.get(i);
                                break;
                            }

                        }
                    }else if(SignListAdapter.tuanClass==1){
                        orderMap = map0;
                    }
                    if(!isTakegroups&&SignListAdapter.tuanClass==2){
                        bottomView.setVisibility(View.VISIBLE);
                        bottomTv.setVisibility(View.GONE);
                        bottom_no_groups.setVisibility(View.VISIBLE);
                        if(recLen<=0||needs<=0){//没有参与当前团的情况下 团人数满 或时间过期
                            showTopDialogEnd();
                        }
                    }else if(SignListAdapter.tuanClass==1){
                        bottomView.setVisibility(View.VISIBLE);
                        bottomTv.setVisibility(View.VISIBLE);
                        bottom_no_groups.setVisibility(View.GONE);
                        showTopDialog(status); //自己开的团 判断拼团结果
                    }
                    if(isTakegroups&&SignListAdapter.tuanClass==2){//在当前团自己有参与的情况下 判断拼团结果
                        showTopDialog(status);
                    }



                }else{
                    bottomView.setVisibility(View.GONE);
                    r_list_view.setVisibility(View.GONE);
                    nodataView.setVisibility(View.VISIBLE);
                    bottom_no_groups.setVisibility(View.GONE);
                    bottomTv.setVisibility(View.GONE);
                    tv_no_data.setText("你还没有参与过任何拼团，快去参团吧~");
//                    new GroupsPayDialog(GroupsDetailsActivity.this,GroupsDetailsActivity.this,recLenPay, orderMap).show();
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
            case R.id.bottom_btn:
                new GroupsShareDialog(this,needs,r_code).show();
                break;
            default:
                break;
        }
    }

    private void showTopDialog(int status) {
        if(completeStatus==5){//支付成功回来 不显示蒙层
            return;
        }

        if(completeStatus==4&&SignListAdapter.tuanClass==1){//开团
            new GroupsShareDialog(this,needs,r_code).show();
        }else {
            if(is_pay == 0 && (n_status == 1 || needs<=0)&&recLen>0){//时间内拼团人数达到 拼团人满
                // 未支付请立即付款  支付时间过期未支付提示未支付
                new GroupsPayDialog(this,this,recLenPay, orderMap).show();

            }else
            if(status!=0 || recLen<=0){

                new GroupsResultDialog(this,this,status,needs,is_pay).show();
                setBottomFinishTv();

            }else {

                if(needs>0&&completeStatus==3&&SignListAdapter.tuanClass==1) {//赚钱任务开团成功 人数满了就没有邀请拼团分享弹框
                     new GroupsShareDialog(this, needs, r_code).show();
                 }

            }
        }

    }

    //来参团时候 拼团已结束
    private void showTopDialogEnd() {
        if(completeStatus==5){//支付成功回来 不显示蒙层
            return;
        }
        if(recLen<=0) {//过期
            new GroupsResultDialog(this,1).show();
        }else if(needs<=0){//人数已满
            new GroupsResultDialog(this,2).show();
        }
    }

    class GroupsAdapter extends BaseAdapter {
        private List<HashMap<String, String>> mInfos;
        private LayoutInflater layoutInflator;
        private Context context;

        public GroupsAdapter(Context context) {
            this.context = context;
            mInfos = new ArrayList<HashMap<String, String>>();
            layoutInflator = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = layoutInflator.inflate(R.layout.groups_item_list, null);
                holder.itemView = (GroupsItemView) convertView.findViewById(R.id.groups_item_view);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.itemView.setItemData(mInfos.get(position));

            return convertView;
        }

        class ViewHolder {
            GroupsItemView itemView;
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
//						if (hour >= 24) {
//							long day = hour / 24;
//							hour = hour % 24;
//							if (day < 10) {
//								days = "0" + day;
//							} else {
//								days = "" + day;
//							}
//							if (hour < 10) {
//								hours = "0" + hour;
//							} else {
//								hours = "" + hour;
//							}
//							if (minute < 10) {
//								minutes = "0" + minute;
//							} else {
//								minutes = "" + minute;
//							}
//							if (second < 10) {
//								seconds = "0" + second;
//							} else {
//								seconds = "" + second;
//							}
//							// mTvTime.setText("" + days + "天" + hours + "时" +
//							// minutes + "分" + seconds+"秒");
////							mTvTimeDays.setText(days);
////							mTvTimeHours.setText(hours);
////							mTvTimeMinutes.setText(minutes);
////							mTvTimeSeconds.setText(seconds);
//						} else {
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
//						}
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
        bottomTv.setClickable(false);
    }
}
