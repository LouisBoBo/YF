package com.yssj.ui.activity.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateFormatUtils;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.VipDikouData;
import com.yssj.model.ComModel2;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.ShopCartNewNewActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.adpter.ForceLookAdapter;
import com.yssj.ui.adpter.StaggeredAdapter;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.LeaveDialog;
import com.yssj.ui.dialog.XunBaoDialog;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;
import com.yssj.utils.sqlite.ShopCartDao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HotSaleActivity extends BasicActivity {
    public static int click_num; // 记录点击次数
    public static int num_tongji;// 运营统计的点击次数

    public static int now_type_id_value;
    public static int now_type_id;
    public static int next_type_id;
    public static int next_type_id_value;

    private long recLen; // 30秒 定时时长 （毫秒）
    Timer timer = new Timer();

    private PullToRefreshListView r_list_view;

    private StaggeredAdapter mAdapter;

    private int index = 1;

    private Boolean isTimeout = false;

    private int mType = 1;// 1：初始化数据；2：加载更多数据

    private HashMap<String, Object> map;
    private HashMap<String, String> mapItem;
    private String oldId, idSearch;
    private String id, title;

    private LinearLayout llContaint, lv_kaiqifanbei;

    private HorizontalScrollView hsv_containt;

    // private String checkId;

    private int pageSize = 30;
    private RelativeLayout rl_yuefanbei;

    private boolean isComplete = false;// 当数据少时，下拉刷新 既调用刷新 又调用 加载跟多，

    private LinearLayout llNodata;
    // 所以当每次返回的数据<pageSize的时候
    private boolean isTuijian = false;
    private boolean isSignQiangzhiliulan = false; // 夺宝强制浏览5分钟跳过来的

    private ImageButton img_btn_filter, shop_cart, imgbtn_left_icon_sign, imgbtn_left_icon;

    private String is_new = null, order_by_price = null;

    private TextView tv_title, liulanTime, tv_cart_count_Force;
    private TextView mTitle;//
    private boolean notType = false;
    private VipDikouData vipDikouData;



    // isComplete为true
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoplist_hot_sale);
        long come_time = System.currentTimeMillis();
        come_time_in = DateFormatUtils.format(Long.parseLong(come_time + ""), "yyyy.MM.dd HH:mm:ss");
        // System.out.println("进来的时间="+come_time_in);

        random = Integer.valueOf((int) (Math.random() * 3 + 3));
        click_num = 1;
        num_tongji = 0;// 运营统计的点击次数

        id_new = getIntent().getIntExtra("now_type_id", -1); // 奖励内容
        nextID = getIntent().getIntExtra("now_type_id_value", -1);

        type_id = getIntent().getIntExtra("next_type_id", -1);
        type_id_value = getIntent().getIntExtra("next_type_id_value", -1);

        now_type_id_value = nextID;
        now_type_id = id_new;
        next_type_id = type_id;
        next_type_id_value = type_id_value;

        // Boolean isfirst = SharedPreferencesUtil.getBooleanData(context,
        // "isfirst", true);
        // if (isfirst) {
        // explain();
        // }

        // SignExplain signExplain = new SignExplain(context);
        // signExplain.show();

//		aBar.hide();
        map = (HashMap<String, Object>) getIntent().getSerializableExtra("condition");
        mapItem = (HashMap<String, String>) getIntent().getSerializableExtra("item");
        if (mapItem != null) {
            idSearch = mapItem.get("_id");
            oldId = idSearch;
        }
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        isTuijian = getIntent().getBooleanExtra("isTuijian", false);

        isSignQiangzhiliulan = getIntent().getBooleanExtra("qiangZhiLiuLan", false);

        // checkId = getIntent().getStringExtra("checkId");

        haisheng = (TextView) findViewById(R.id.haisheng);
        if (id_new == 11) {
            haisheng.setText("亲，2元现金就藏在这些商品详情页里噢，快去领取吧~");
        }
        if (id_new == 12) {
            haisheng.setText("亲，5元现金就藏在这些商品详情页里噢，快去领取吧~");
        }
        if (id_new == 15) {
            haisheng.setText("亲，1元现金就藏在这些商品详情页里噢，快去领取吧~");
        }
        if (id_new == 20) {
            haisheng.setText("亲，3元现金就藏在这些商品详情页里噢，快去领取吧~");
        }

        tv_shuoming = (TextView) findViewById(R.id.tv_shuoming);
        tv_shuoming.setOnClickListener(this);
        mTitle = (TextView) findViewById(R.id.tv_forcelook_title);
        tv_title = (TextView) findViewById(R.id.tv_title);
        liulanTime = (TextView) findViewById(R.id.liulanTime);
        imgbtn_left_icon_sign = (ImageButton) findViewById(R.id.imgbtn_left_icon_sign);
        imgbtn_left_icon = (ImageButton) findViewById(R.id.imgbtn_left_icon);
        lv_kaiqifanbei = (LinearLayout) findViewById(R.id.lv_kaiqifanbei);

        shop_cart = (ImageButton) findViewById(R.id.shop_cart);

        findViewById(R.id.img_back).setOnClickListener(this);
        img_btn_filter = (ImageButton) findViewById(R.id.img_btn_filter);
        img_btn_filter.setOnClickListener(this);
        // 添加搜索条件
        // llContaint = (LinearLayout) findViewById(R.id.ll_containt);
        // addView(llContaint, map);

        rl_yuefanbei = (RelativeLayout) findViewById(R.id.rl_yuefanbei);
        tv_cart_count_Force = (TextView) findViewById(R.id.tv_cart_count_Force);

        r_list_view = (PullToRefreshListView) findViewById(R.id.r_list_view);
        llNodata = (LinearLayout) findViewById(R.id.ll_nodata);
        findViewById(R.id.btn_view_allcircle).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.tv_no_join)).setText("暂无数据");
        r_list_view.setMode(Mode.BOTH);

        int shopCartCount = new ShopCartDao(this).queryCartCount(this);

        if (shopCartCount > 0) {
            tv_cart_count_Force.setText(shopCartCount + "");
            tv_cart_count_Force.setVisibility(View.VISIBLE);
        } else {
            tv_cart_count_Force.setVisibility(View.GONE);
        }

        mAdapter = new StaggeredAdapter(this);
        r_list_view.setAdapter(mAdapter);

        mTitle.setText(title);
        rl_yuefanbei.setVisibility(View.GONE);
        img_btn_filter.setVisibility(View.GONE);
        tv_cart_count_Force.setVisibility(View.GONE);
        shop_cart.setVisibility(View.GONE);

        setListViewRefresh();


        //查询会员情况
        if (YJApplication.instance.isLoginSucess()) {
            //查询抵扣
            HashMap<String, String> pairsMap = new HashMap<>();
            YConn.httpPost(this, YUrl.GETALLDIKOU, pairsMap, new HttpListener<VipDikouData>() {

                @Override
                public void onSuccess(VipDikouData result) {
                    vipDikouData = result;
                    if (isTuijian) {
                        initData(index + "", null, is_new, order_by_price);
                    } else {
                        initData(index + "", map, is_new, order_by_price);
                    }
                }

                @Override
                public void onError() {
                    if (isTuijian) {
                        initData(index + "", null, is_new, order_by_price);
                    } else {
                        initData(index + "", map, is_new, order_by_price);
                    }
                }
            });

        } else {
            if (isTuijian) {
                initData(index + "", null, is_new, order_by_price);
            } else {
                initData(index + "", map, is_new, order_by_price);
            }
        }


    }

    private void setListViewRefresh() {
        r_list_view.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                mType = 1;
                index = 1;
                if (isTuijian) {
                    initData(index + "", null, is_new, order_by_price);
                    return;
                }
                initData(index + "", map, is_new, order_by_price);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                // if (!isComplete) {// 当数据没加载完成 上拉可以加载更多
                // if (SignFragment.doType!=4) {
                index++;
                mType = 2;
                if (isTuijian) {
                    initData(index + "", null, is_new, order_by_price);
                } else {
                    initData(index + "", map, is_new, order_by_price);
                }
                // }else{
                // r_list_view.onRefreshComplete();//强制浏览个数 只获取最前面十件商品
                // }
            }
        });

    }

    private void initData(final String index, final HashMap<String, Object> map, final String is_new,
                          final String order_by_price) {
        // 下面是真数据
        final int pageSize = 10;
        new SAsyncTask<String, Void, List<HashMap<String, Object>>>(this, 0) {
            @Override
            protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
                    throws Exception {

                // return ComModel2.getForceLook(context, pageSize, index);

                if (YJApplication.instance.isLoginSucess()) {
                    return ComModel2.getProductList1(context, index, id + "", "1", title + "", pageSize, false);

                } else {
                    return ComModel2.getProductListUnLogin(context, index, id + "", "1", title + "", pageSize, false);

                }

            }

            @Override
            protected boolean isHandleException() {

                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result, e);

                if (e == null) {
                    if (mType == 1) {
                        if (result == null || result.size() == 0) {
                            llNodata.setVisibility(View.VISIBLE);
                            r_list_view.setVisibility(View.GONE);
                        } else {
                            llNodata.setVisibility(View.GONE);
                            r_list_view.setVisibility(View.VISIBLE);
                        }
                        mAdapter.setData(result, vipDikouData);

                    } else if (mType == 2) {
                        if (result.size() == 0) {
                            ToastUtil.showShortText(context, "已没有更多商品了哦~");
                        } else {
                            mAdapter.addItemLast(result);
                            r_list_view.getRefreshableView().smoothScrollBy(100, 20);
                        }
                    }
                }
                r_list_view.onRefreshComplete();
            }

        }.execute(index);
    }

    private String singvalue;

    @Override
    public void onClick(View arg0) {
        super.onClick(arg0);
        switch (arg0.getId()) {
            case R.id.img_back: // 返回
                onBackPressed();
                break;
            case R.id.img_btn_filter:

                Intent intent = new Intent(this, ShopCartNewNewActivity.class);
                intent.putExtra("where", "0");
                startActivity(intent);
                break;

            case R.id.tv_shuoming:
                // explain();
                new XunBaoDialog(this).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }

    private boolean isLou = true;

    private TextView tv_shuoming;

    public static int random;

    private int id_new;

    private int nextID;

    private TextView haisheng;
    private SimpleDateFormat formatter;
    private int type_id;
    private int type_id_value;
    private String come_time;
    private String come_time_in;

    /**
     * 只看楼主和收藏帖子的popupwindow
     */
    private class OrderByPopupWindow extends PopupWindow implements OnClickListener {
        private Context mContext;
        private LinearLayout create_is_new, create_price_desc, create_price_asc;
        private String[] names;

        private TextView is_lou;

        public OrderByPopupWindow(Context context) {
            super(context);
            mContext = context;
            init();
        }

        private void init() {
            setWidth(LayoutParams.WRAP_CONTENT);
            setHeight(LayoutParams.WRAP_CONTENT);
            ColorDrawable dw = new ColorDrawable(0x00);
            setBackgroundDrawable(dw);
            View view = LayoutInflater.from(mContext).inflate(R.layout.popup_order, null);
            create_is_new = (LinearLayout) view.findViewById(R.id.create_is_new);
            create_price_desc = (LinearLayout) view.findViewById(R.id.create_price_desc);
            create_price_asc = (LinearLayout) view.findViewById(R.id.create_price_asc);

            create_is_new.setOnClickListener(this);
            create_price_desc.setOnClickListener(this);
            create_price_asc.setOnClickListener(this);
            setContentView(view);
            setAnimationStyle(R.style.PopupWindowAnimation);
            setOutsideTouchable(true);
            setFocusable(true);
        }

        @Override
        public void onClick(View v) {
            // Intent intent = new Intent();
            // int enterAnimID = R.anim.slide_right_in;
            // int exitAnimID = R.anim.slide_left_out;
            int id = v.getId();
            if (id == R.id.create_is_new) {
                is_new = "is_new";
                order_by_price = null;
            } else if (id == R.id.create_price_desc) {
                is_new = null;
                order_by_price = "desc";
            } else if (id == R.id.create_price_asc) {
                is_new = null;
                order_by_price = "asc";
            }
            notType = true;
            mType = 1;
            index = 1;
            // initData(index + "", SearchResultActivity.this.id,
            // level,is_new,order_by_price, notType);
            initData(index + "", map, is_new, order_by_price);
            // enterAnimID = R.anim.slide_down_in;
            // exitAnimID = R.anim.slide_top_in;
            dismiss();
        }

    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == 101 && arg1 == 102) {
            CommonUtils.finishActivity(MainMenuActivity.instances);

            Intent intent = new Intent(this, MainMenuActivity.class);
            intent.putExtra("index", 3);
            startActivity(intent);
            finish();
        }
    }

    // private long recLen = (1 * 1000 * 60) / 2; // 30秒 定时时长 （毫秒）
    // Timer timer = new Timer();

    // 倒计时

}
