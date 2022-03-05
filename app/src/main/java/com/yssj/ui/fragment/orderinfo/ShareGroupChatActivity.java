package com.yssj.ui.fragment.orderinfo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.activity.wxapi.WXEntryActivity;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.CustomProgressBar;
import com.yssj.data.YDBHelper;
import com.yssj.entity.Order;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DialogUtils;
import com.yssj.utils.GlideUtils;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.StringUtils;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareGroupChatActivity extends BasicActivity {

    @Bind(R.id.img_shop_pic)
    ImageView imgShopPic;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.tv_product_color)
    TextView tvProductColor;
    @Bind(R.id.tv_product_size)
    TextView tvProductSize;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    //    @Bind(R.id.tv_shifu_price)
//    TextView tvShifuPrice;
    @Bind(R.id.tv_yikan)
    TextView tvYikan;
    @Bind(R.id.tv_zaikan)
    TextView tvZaikan;
    @Bind(R.id.cpb_progresbar2)
    CustomProgressBar cpbProgresbar2;
    @Bind(R.id.list_view1)
    ListView listView1;
    @Bind(R.id.tv_share1)
    TextView tvShare1;
    @Bind(R.id.ll_wxin)
    LinearLayout llWxin;
    @Bind(R.id.tv_yikan_end_str)
    TextView tvYikanEndStr;
    private Order order; // 订单

    private boolean isSubmit;


    private Activity mActivity;

    private boolean shareClick = false;

    private String order_code;


    private int needShareCount = 2;//一共需要分享2次

    private YDBHelper dbHelp;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_share_group_chat);
        context = this;
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        Bundle bundle = getIntent().getExtras();
        isSubmit = getIntent().getBooleanExtra("isSubmit", false);
        mActivity = this;
        cpbProgresbar2.setMaxProgress(100);
        dbHelp = new YDBHelper(this);
        initLimitAwardsList();
        tvShare1.setText("分享微信群聊");


        ObjectAnimator animatorX = ObjectAnimator.ofFloat(llWxin, "scaleX", 1, 1.2f, 1);
        animatorX.setRepeatCount(-1);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(llWxin, "scaleY", 1, 1.2f, 1);
        animatorY.setRepeatCount(-1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.setDuration(1100);
        animatorSet.start();


        if (isSubmit) {
            order_code = getIntent().getStringExtra("order_code");
            getOrder();
        } else {
            order = (Order) bundle.getSerializable("order");
            initData();
        }


    }

    private void getOrder() {


        new SAsyncTask<Void, Void, Order>(this, R.string.wait) {

            @Override
            protected Order doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel2.getMyOrderPaysuccss(context, order_code);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(final FragmentActivity context, final Order result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    order = result;
                    initData();


                }
            }

        }.execute();


    }

    private void initData() {
        String picUrl = order.getList().get(0).getShop_code().substring(1, 4) + "/" + order.getList().get(0).getShop_code() + "/"
                + order.getList().get(0).getShop_pic();

        tvShopName.setText(order.getOrder_name());
        PicassoUtils.initImage(this, picUrl, imgShopPic);
        tvPrice.setText("￥" + order.getList().get(0).getOriginal_price());
//        tvShifuPrice.setText("￥" + order.getOrder_price() + "");


        if (null == order.getList().get(0).getColor()) {
            tvProductColor.setVisibility(View.GONE);
        } else {
            tvProductColor.setText("颜色：" + order.getList().get(0).getColor());

        }

        if (null == order.getList().get(0).getSize()) {
            tvProductSize.setVisibility(View.GONE);
        } else {
            tvProductSize.setText("尺寸：" + order.getList().get(0).getSize());

        }

        tvYikanEndStr.setText(Html.fromHtml("，再分享<strong><font color='#ff0000'>" + 2 + "</font></strong>个群即可砍到<strong><font color='#ff0000'>" + 0 + "</font></strong>元"));
        //默认砍掉50%
        tvZaikan.setText((int) (order.getList().get(0).getOriginal_price() * 0.3) + "");
        cpbProgresbar2.setCurProgress(50, 2000);
        ValueAnimator animator = ValueAnimator.ofInt(0, (int) (order.getList().get(0).getOriginal_price() * 0.5));
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tvYikan.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick({R.id.img_back, R.id.ll_order, R.id.ll_wxin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_order:
                startActivity(new Intent(this, ShopDetailsActivity.class).putExtra("code", order.getList().get(0).getShop_code()));
                ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                break;
            case R.id.ll_wxin:
                share();

                break;


        }
    }

    private void share() {

        shareWaitDialog.show();
        shareClick = true;
        String str = YCache.getCacheUser(this).getUser_id() + "," + "ThreePage" + "," + "QRcode";
        String wxMiniPathdUO = "/pages/shouye/redHongBao?scene=" + str;
        String shareMIniAPPimgPic = YUrl.imgurl + "small-iconImages/heboImg/freeling_share199yuan.jpg";
        String shareTitle = "199元购物红包免费抢，多平台可用，快来试试人品吧";
        //分享到微信统一分享小程序
        WXminiAPPShareUtil.shareToWXminiAPP(this, shareMIniAPPimgPic, shareTitle, wxMiniPathdUO, false);
        shareWaitDialog.dismiss();
        WXEntryActivity.setWXminiShareListener(new WXEntryActivity.WXminiAPPshareListener() {
            @Override
            public void wxMiniShareSuccess() {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (shareClick) {
            if (needShareCount == 2) {
                tvShare1.setText("分享群聊(1/2)");
                tvYikanEndStr.setText(Html.fromHtml("，再分享<strong><font color='#ff0000'>" + 1 + "</font><strong>个群即可砍到<font color='#ff0000'>" + 0 + "</font></strong>元"));
                tvZaikan.setText((int) (order.getList().get(0).getOriginal_price() * 0.2) + "");
                cpbProgresbar2.setCurProgress(80, 2000);
                ValueAnimator animator = ValueAnimator.ofInt(0, (int) (order.getList().get(0).getOriginal_price() * 0.8));
                animator.setDuration(2000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        tvYikan.setText(animation.getAnimatedValue().toString());
                    }
                });
                animator.start();
                needShareCount--;
                shareClick = false;

                return;
            }

            if (needShareCount == 1) { //已经全部分享完毕---去抽奖

                tvShare1.setText("分享群聊(2/2)");
                tvZaikan.setText((int) (order.getList().get(0).getOriginal_price() * 0) + "");
                cpbProgresbar2.setCurProgress(100, 2000);
                ValueAnimator animator = ValueAnimator.ofInt(0, (int) (order.getList().get(0).getOriginal_price() * 1));
                animator.setDuration(2000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        tvYikan.setText(animation.getAnimatedValue().toString());
                    }
                });
                animator.start();
                DialogUtils.shareGroupShareCompleteDialog(context);
                needShareCount--;
                shareClick = false;
                return;
            }

        }
    }

    private ArrayList<String> sedLeim = new ArrayList<>();//二级类目集合

    private ArrayList<String> allSubList = new ArrayList<>();//所有品牌集合
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

    private void addToLimitList() {
        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("nname", StringUtils.getVirtualName() + "***" + StringUtils.getVirtualName());


        String ramSubName = allSubList.get((int) (Math.random() * allSubList.size()));////随机品牌
        String ramLeim = sedLeim.get((int) (Math.random() * sedLeim.size()));//随机二级类目
        map1.put("p_name", "免费领走了" + ramSubName + ramLeim);
        map1.put("pic", "defaultcommentimage/" + StringUtils.getDefaultImg());
        //1-500随机数
        int ram500 = (int) (Math.random() * 500) + 1;
        map1.put("num", "原价" + ram500 + ".0元");


        mListData1.add(map1);
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
                convertView = View.inflate(ShareGroupChatActivity.this, R.layout.item_withdrawal_limit, null);
                holder.mNameTv = (TextView) convertView.findViewById(R.id.withdrawal_name_tv);
                holder.tv = (TextView) convertView.findViewById(R.id.withdrawal_exp_tv);
                holder.mAwardsTv = (TextView) convertView.findViewById(R.id.withdrawal_awards_tv);
                holder.headIv = (ImageView) convertView.findViewById(R.id.withdrawal_head_iv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            GlideUtils.initRoundImage(Glide.with(ShareGroupChatActivity.this), ShareGroupChatActivity.this, mListData.get(position % mListData.size()).get("pic").toString(), holder.headIv);

            holder.mNameTv.setText(mListData.get(position % mListData.size()).get("nname").toString());
            holder.mAwardsTv.setText(mListData.get(position % mListData.size()).get("num").toString());
            holder.tv.setText(mListData.get(position % mListData.size()).get("p_name").toString());
            return convertView;
        }


    }

    public class ViewHolder {
        TextView mNameTv, tv, mAwardsTv;
        ImageView headIv;
    }


}
