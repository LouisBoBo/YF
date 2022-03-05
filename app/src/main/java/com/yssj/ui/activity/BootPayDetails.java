package com.yssj.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.infos.StatusInfoActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.GlideUtils;
import com.yssj.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 引导付款详情
 */
public class BootPayDetails extends BasicActivity implements OnClickListener {
    private View viewBack, viewToPay;
    private TextView mTvTime;
    private Context mContext;
    private MyAdapter adapter2;
    private TimerTask task2;
    private Timer mTimer2;
    private List<HashMap<String, String>> mListData2;
    private ListView lv_choujiang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot_pay_details);
        AppManager.getAppManager().addActivity(this);
        mContext = this;
        initView();
    }


    private void initView() {
        viewBack = findViewById(R.id.img_back);
        viewBack.setOnClickListener(this);
        viewToPay = findViewById(R.id.id_bottom);
        viewToPay.setOnClickListener(this);
        mTvTime = (TextView) findViewById(R.id.tv_pay_times);


        lv_choujiang = (ListView) findViewById(R.id.lv_choujiang);
        lv_choujiang.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return true;
            }
        });
//        getSystemTime();
        getTwofoldness();

    }


    @Override
    protected void onResume() {
        super.onResume();

        getSystemTime();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.id_bottom:
                Intent intent = new Intent(this, StatusInfoActivity.class);
                intent.putExtra("index", 1);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                break;
            default:
                break;
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
                    long minute = recLen / 60000;
                    long second = (recLen % 60000) / 1000;
                    if (minute >= 10 && second >= 10) {
                        mTvTime.setText(minute + ":" + second + "");
                    } else if (minute >= 10 && second < 10) {
                        mTvTime.setText(minute + ":0" + second + "");
                    } else if (minute < 10 && second >= 10) {
                        mTvTime.setText("0" + minute + ":" + second + "");
                    } else if (minute < 10 && second < 10) {
                        mTvTime.setText("0" + minute + ":0" + second + "");
                    }

                    if (recLen <= 0) {
                        timer.cancel();
                        mTvTime.setText("00:00");
                    }
                }
            });
        }

    }


    private void getTwofoldness() {
        new SAsyncTask<String, Void, HashMap<String, String>>(BootPayDetails.this, 0) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {

                return ComModel2.getYiDouHalve(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null &&
                        Long.parseLong(result.get("end_date")) > Long.parseLong(result.get("now"))) {
                    recLen = Long.parseLong(result.get("end_date")) -
                            Long.parseLong(result.get("now"));//30分钟
                } else {
                    recLen = 0;
                }

                if (timer != null) {
                    timer.cancel();
                }
                timer = new Timer();
                timer.schedule(new MyTimerTask(), 0, 1000);
            }

        }.execute();
    }

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

                convertView = View.inflate(mContext, R.layout.item_sign_choujiang, null);
                holder.mNameTv = (TextView) convertView.findViewById(R.id.withdrawal_name_tv);
                holder.mAwardsTv = (TextView) convertView.findViewById(R.id.withdrawal_awards_tv);
                holder.tv_danwei = (TextView) convertView.findViewById(R.id.tv_danwei);
                holder.tv_guide_pay = (TextView) convertView.findViewById(R.id.tv_guide_pay);
                holder.headIv = (ImageView) convertView.findViewById(R.id.withdrawal_head_iv);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String type = mListData.get(position % mListData.size()).get("type");
            if (position % 2 == 0) {
                // 为偶数
                convertView.setAlpha(0.7f);
            } else {
                // 为基数
                convertView.setAlpha(1.0f);
            }
            // SetImageLoader.initImageLoader(mContext, holder.headIv,
            // mListData.get(position % mListData.size()).get("pic").toString(),
            // "");

            if (!"1".equals(type)) {
                holder.headIv.setVisibility(View.VISIBLE);
                holder.mNameTv.setVisibility(View.VISIBLE);
                holder.mAwardsTv.setVisibility(View.VISIBLE);
                holder.tv_danwei.setVisibility(View.VISIBLE);
                holder.tv_guide_pay.setVisibility(View.GONE);
//                PicassoUtils.initImage(mContext, mListData.get(position % mListData.size()).get("pic").toString(),
//                        holder.headIv);


                GlideUtils.initRoundImage(Glide.with(mContext),mContext, mListData.get(position % mListData.size()).get("pic").toString(),
                        holder.headIv);

                // xx获得提现额度xx元

                holder.mNameTv.setText(mListData.get(position % mListData.size()).get("nname").toString() + " 抽中");
                holder.mAwardsTv.setText(mListData.get(position % mListData.size()).get("num").toString());
                holder.tv_danwei.setText("元提现额度");
            } else {
                holder.headIv.setVisibility(View.GONE);
                holder.mNameTv.setVisibility(View.GONE);
                holder.mAwardsTv.setVisibility(View.GONE);
                holder.tv_danwei.setVisibility(View.GONE);
                holder.tv_guide_pay.setVisibility(View.VISIBLE);
//                holder.tv_guide_pay.setText(mListData.get(position % mListData.size()).get("oosg"));
                String oosg = mListData.get(position % mListData.size()).get("oosg");
                SpannableString textSpan = new SpannableString(oosg);
                textSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#ea1446")), 0, 2,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#ea1446")), 12, oosg.length() - 2,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.tv_guide_pay.setText(textSpan);
            }


            return convertView;
        }

    }

    public class ViewHolder {
        TextView mNameTv, mAwardsTv, tv_danwei, tv_guide_pay;
        ImageView headIv;
    }

    /**
     * 体现额度奖励列表
     */
    private void initYiDouAwardsList(final String oosg) {
        new SAsyncTask<Void, Void, List<HashMap<String, String>>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected List<HashMap<String, String>> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getTixianEduList(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<HashMap<String, String>> result, Exception e) {
                super.onPostExecute(context, result, e);
                mListData2 = new ArrayList<HashMap<String, String>>();
                if (null == e && result != null) {
                    List<HashMap<String, String>> result2 = result;// 后台获取的集合数据
                    // 衣豆奖励
                    // 填充获得衣豆奖励数据
                    // if(result2.size()<25){
                    for (int i = 0; i < 100; i++) {
                        if (i < result2.size() * 2) {
                            if (i % 2 == 0) {
                                mListData2.add(result2.get(i / 2));// 添加到总集合
                                // 键值和虚拟添加的键值保持一致
                            } else {
                                addToYiDouList();
                            }
                        } else {
                            addToYiDouList();
                        }
                    }


                } else {
                    for (int i = 0; i < 100; i++) {
                        addToYiDouList();
                    }
                }

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("oosg", oosg);//引导付款泡泡文案
                map.put("type", "1");//引导付款泡泡文案标志

                for (int i = 0; i < mListData2.size(); ) {
                    i += 5;
                    mListData2.add(i, map);
                    i += 1;
                }
                initListView();
            }
        }.execute();
    }

    private void initListView() {
        adapter2 = new MyAdapter(mContext, mListData2);
        lv_choujiang.setAdapter(adapter2);

        if (mTimer2 != null) {
            mTimer2.cancel();
            mTimer2.purge();
            mTimer2 = null;
        }

        if (null != task2) {
            task2.cancel();
        }
        task2 = new TimerTask() {
            @Override
            public void run() {
                lv_choujiang.smoothScrollBy(DP2SPUtil.dp2px(mContext, 44), 300);
            }
        };
        mTimer2 = new Timer();
        mTimer2.schedule(task2, 1000, 1000);
    }


    /**
     * 添加虚拟数据到 衣豆奖励集合
     */
    private void addToYiDouList() {
        HashMap<String, String> map2 = new HashMap<String, String>();
        map2.put("nname", StringUtils.getVirtualName() + "***" + StringUtils.getVirtualName());
        // map2.put("p_name", "完成订单获得衣豆");
        map2.put("pic", "defaultcommentimage/" + StringUtils.getDefaultImg());
        map2.put("num", (int) (Math.random() * (1000 - 100) + 10) + "");// 100-999
        mListData2.add(map2);
    }

    /**
     * 获取系统时间
     */
    private void getSystemTime() {
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getSystemTime(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(final FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                long nowTime = (Long) result.get("now");
                String oosg = StringUtils.getGuidePayString(nowTime);
                initYiDouAwardsList(oosg);
            }
        }.execute();
    }


    @Override
    protected void onPause() {

        super.onPause();

//        if (null != mTimer2) {
//            mTimer2.cancel();
//        }
    }
}
