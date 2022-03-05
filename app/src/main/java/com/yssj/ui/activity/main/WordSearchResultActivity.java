package com.yssj.ui.activity.main;

import java.util.HashMap;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.VipDikouData;
import com.yssj.model.ComModel2;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.HomeWatcherReceiver;
import com.yssj.ui.adpter.StaggeredAdapter;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class WordSearchResultActivity extends BasicActivity {

    private PullToRefreshListView r_list_view;

    private StaggeredAdapter mAdapter;
    private boolean isCustomLeable; // 密友圈自定义品牌跳转过来

    private int index = 1;

    private int mType = 1;// 1：初始化数据；2：加载更多数据

    private String words;
    private String class_id;
    private boolean notType;// 从输入框搜索的

    private int pageSize = 10;

    private boolean isComplete = false;// 当数据少时，下拉刷新 既调用刷新 又调用 加载跟多，
    // 所以当每次返回的数据<pageSize的时候
    // isComplete为true
    private View tv_no_data;

    // private ImageButton btn_right;
    private LinearLayout root;

    private String is_hot = null, is_new = "is_new", order_by_price = null;

    private TextView tvNew, tvHot, tvDesc, tvAsc;
    private View horizontal_title_ll;

    private String mTheme_id;

    private String only_id;

    private String leable_name;

    private LinearLayout account_nodata;

    private TextView tv_qin;

    private TextView tv_no_join;

    private Button btn_view_allcircle;
    private VipDikouData vipDikouData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        // aBar.hide();
        // words 输入框中搜索的字符串
        words = getIntent().getStringExtra("words");
        class_id = getIntent().getStringExtra("class_id");
        leable_name = getIntent().getStringExtra("class_id");
        // 是否是输入框搜索
        notType = getIntent().getBooleanExtra("notType", false);
        isCustomLeable = getIntent().getBooleanExtra("isCustomLeable", false);
        if (isCustomLeable) {
            mTheme_id = getIntent().getStringExtra("mTheme_id");
            only_id = getIntent().getStringExtra("only_id");
            leable_name = getIntent().getStringExtra("label_name");
        }
        setContentView(R.layout.result_list_word);
        root = (LinearLayout) findViewById(R.id.root);
        root.setBackgroundColor(Color.WHITE);

        if (isCustomLeable) {
            ((TextView) findViewById(R.id.tvTitle_base)).setText(leable_name);
        } else {
            if (notType) {
                ((TextView) findViewById(R.id.tvTitle_base)).setText("搜索结果");
            } else {
                ((TextView) findViewById(R.id.tvTitle_base)).setText(words);
            }
        }

        findViewById(R.id.img_back).setOnClickListener(this);
        horizontal_title_ll = findViewById(R.id.horizontal_title_ll);
        tvNew = (TextView) findViewById(R.id.create_is_new);
        tvHot = (TextView) findViewById(R.id.create_is_hot);
        tvDesc = (TextView) findViewById(R.id.create_price_desc);
        tvAsc = (TextView) findViewById(R.id.create_price_asc);
        tvHot.setOnClickListener(this);
        tvNew.setOnClickListener(this);
        tvDesc.setOnClickListener(this);
        tvAsc.setOnClickListener(this);

        // btn_right = (ImageButton)findViewById(R.id.img_btn_right);
        // btn_right.setOnClickListener(this);
        // btn_right.setVisibility(View.VISIBLE);

        r_list_view = (PullToRefreshListView) findViewById(R.id.r_list_view);
        r_list_view.setBackgroundColor(Color.parseColor("#f0f0f0"));
        String WordSearchResultActivity = "WordSearchResultActivity";
        String isWhere = "null";
        mAdapter = new StaggeredAdapter(this, WordSearchResultActivity, isWhere);
        r_list_view.setAdapter(mAdapter);
        tv_no_data = findViewById(R.id.tv_no_data);

        account_nodata = (LinearLayout) findViewById(R.id.account_nodata);
        btn_view_allcircle = (Button) findViewById(R.id.btn_view_allcircle);
        btn_view_allcircle.setVisibility(View.GONE);
        tv_qin = (TextView) findViewById(R.id.tv_qin);
        tv_no_join = (TextView) findViewById(R.id.tv_no_join);
        tv_qin.setText("O(∩_∩)O~亲~");
        tv_no_join.setText("暂无相关商品哦~");


        //查询会员情况
        if (YJApplication.instance.isLoginSucess()) {
            //查询抵扣
            HashMap<String, String> pairsMap = new HashMap<>();
            YConn.httpPost(this, YUrl.GETALLDIKOU, pairsMap, new HttpListener<VipDikouData>() {
                @Override
                public void onSuccess(VipDikouData result) {
                    vipDikouData = result;
                    initData(index + "", words, is_new, order_by_price);

                }

                @Override
                public void onError() {
                    initData(index + "", words, is_new, order_by_price);

                }
            });

        }else{
            initData(index + "", words, is_new, order_by_price);

        }


        r_list_view.setMode(Mode.BOTH);
        r_list_view.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mType = 1;
                index = 1;
                initData(index + "", words, is_new, order_by_price);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                index++;
                mType = 2;
                initData(index + "", words, is_new, order_by_price);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        HomeWatcherReceiver.registerHomeKeyReceiver(this);
        SharedPreferencesUtil.saveStringData(WordSearchResultActivity.this, Pref.TONGJI_TYPE, "1020");
    }

    @Override
    protected void onPause() {
        super.onPause();
        HomeWatcherReceiver.unregisterHomeKeyReceiver(this);
    }

    private void initData(String index, String words, final String is_new, final String order_by_price) {





        new SAsyncTask<String, Void, List<HashMap<String, Object>>>(this, 0) {

            @Override
            protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result);
                // listResult.addAll(result);

                if (e != null) {
                    r_list_view.onRefreshComplete();
                } else {

                    if (mType == 1) {
                        mAdapter.setData(result,vipDikouData);
                        r_list_view.onRefreshComplete();
                        if (result.size() == 0) {

                            if (isCustomLeable) {
                                account_nodata.setVisibility(View.VISIBLE);
                            } else {
                                tv_no_data.setVisibility(View.VISIBLE);
                            }

                            r_list_view.setVisibility(View.GONE);
                            horizontal_title_ll.setVisibility(View.GONE);
                        } else {

                            if (isCustomLeable) {
                                account_nodata.setVisibility(View.GONE);
                            } else {
                                tv_no_data.setVisibility(View.GONE);
                            }

                            r_list_view.setVisibility(View.VISIBLE);
                            horizontal_title_ll.setVisibility(View.VISIBLE);
                        }
                    } else if (mType == 2) {
//						mAdapter.addItemLast(result);
//						// r_list_view.onLoadMoreComplete();
                        if (result == null || result.size() == 0) {
                            ToastUtil.showShortText(context, "已没有更多商品了哦~");
                        } else {
                            mAdapter.addItemLast(result);
                            r_list_view.getRefreshableView().smoothScrollBy(100, 20);
                        }
                    }
                    r_list_view.onRefreshComplete();
                }
            }

            @Override
            protected boolean isHandleException() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                // TODO Auto-generated method stub
                /**
                 * String is_hot, String is_new, String order_by_price
                 */
                if (isCustomLeable) {
                    return ComModel2.getCusmtomLeaableShaop(context, pageSize, params[0], mTheme_id, only_id, is_hot,
                            is_new, order_by_price);
                } else {
                    return YJApplication.instance.isLoginSucess()
                            ? ComModel2.getProductListByWord(context, params[0], params[1], pageSize, is_hot, is_new,
                            order_by_price, notType, class_id)
                            : ComModel2.getProductListByWord2(context, params[0], params[1], pageSize, is_hot, is_new,
                            order_by_price, notType, class_id);
                }

            }

        }.execute(index, words);
    }

    private boolean isLou = true;

    // /** 只看楼主和收藏帖子的popupwindow */
    // private class OrderByPopupWindow extends PopupWindow implements
    // OnClickListener {
    // private Context mContext;
    // private LinearLayout create_is_new, create_price_desc, create_price_asc;
    // private String[] names;
    //
    // private TextView is_lou;
    //
    // public OrderByPopupWindow(Context context) {
    // super(context);
    // mContext = context;
    // init();
    // }
    //
    // public OrderByPopupWindow(Context context, String[] names) {
    // super(context);
    // mContext = context;
    // this.names = names;
    // init();
    // }
    //
    // private void init() {
    // setWidth(LayoutParams.WRAP_CONTENT);
    // setHeight(LayoutParams.WRAP_CONTENT);
    // ColorDrawable dw = new ColorDrawable(0x00);
    // setBackgroundDrawable(dw);
    // View view = LayoutInflater.from(mContext).inflate(
    // R.layout.popup_order, null);
    // create_is_new = (LinearLayout) view
    // .findViewById(R.id.create_is_new);
    // create_price_desc = (LinearLayout) view
    // .findViewById(R.id.create_price_desc);
    // create_price_asc = (LinearLayout) view
    // .findViewById(R.id.create_price_asc);
    //
    // create_is_new.setOnClickListener(this);
    // create_price_desc.setOnClickListener(this);
    // create_price_asc.setOnClickListener(this);
    // setContentView(view);
    // setAnimationStyle(R.style.PopupWindowAnimation);
    // setOutsideTouchable(true);
    // setFocusable(true);
    // }
    //
    // @Override
    // public void onClick(View v) {
    // // Intent intent = new Intent();
    // // int enterAnimID = R.anim.slide_right_in;
    // // int exitAnimID = R.anim.slide_left_out;
    // int id = v.getId();
    // if (id == R.id.create_is_new) {
    // is_new = "is_new";
    // order_by_price = null;
    // } else if (id == R.id.create_price_desc) {
    // is_new = null;
    // order_by_price = "desc";
    // }else if(id == R.id.create_price_asc){
    // is_new = null;
    // order_by_price = "asc";
    // }
    //// notType = true;
    // mType = 1;
    // index = 1;
    //// initData(index + "", SearchResultActivity.this.id,
    // level,is_new,order_by_price, notType);
    // initData(index + "", words, is_new, order_by_price);
    // // enterAnimID = R.anim.slide_down_in;
    // // exitAnimID = R.anim.slide_top_in;
    // dismiss();
    // }
    //
    // }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        super.onClick(view);
        switch (view.getId()) {
            case R.id.img_back:// 返回到上一层
                finish();
                break;
            // case R.id.img_btn_right://弹出对话框
            // OrderByPopupWindow popupWindow = new OrderByPopupWindow(this);
            // popupWindow.showAsDropDown(btn_right, 0, 10);
            // break;
            case R.id.create_is_new:// 上新
                tvNew.setTextColor(Color.parseColor("#FF3F8B"));
                tvHot.setTextColor(Color.parseColor("#3E3E3E"));
                tvDesc.setTextColor(Color.parseColor("#3E3E3E"));
                tvAsc.setTextColor(Color.parseColor("#3E3E3E"));
                is_new = "is_new";
                is_hot = null;
                order_by_price = null;
                mType = 1;
                index = 1;
                initData(index + "", words, is_new, order_by_price);
                break;
            case R.id.create_is_hot:// 热销
                tvHot.setTextColor(Color.parseColor("#FF3F8B"));
                tvNew.setTextColor(Color.parseColor("#3E3E3E"));
                tvDesc.setTextColor(Color.parseColor("#3E3E3E"));
                tvAsc.setTextColor(Color.parseColor("#3E3E3E"));
                is_hot = "is_hot";
                is_new = null;
                order_by_price = null;
                mType = 1;
                index = 1;
                initData(index + "", words, is_new, order_by_price);
                break;
            case R.id.create_price_asc:// 价格
                tvAsc.setTextColor(Color.parseColor("#FF3F8B"));
                tvNew.setTextColor(Color.parseColor("#3E3E3E"));
                tvHot.setTextColor(Color.parseColor("#3E3E3E"));
                tvDesc.setTextColor(Color.parseColor("#3E3E3E"));
                is_new = null;
                is_hot = null;
                order_by_price = "asc";
                mType = 1;
                index = 1;
                initData(index + "", words, is_new, order_by_price);
                break;
            case R.id.create_price_desc:// 价格
                tvDesc.setTextColor(Color.parseColor("#FF3F8B"));
                tvNew.setTextColor(Color.parseColor("#3E3E3E"));
                tvHot.setTextColor(Color.parseColor("#3E3E3E"));
                tvAsc.setTextColor(Color.parseColor("#3E3E3E"));
                is_new = null;
                is_hot = null;
                order_by_price = "desc";
                mType = 1;
                index = 1;
                initData(index + "", words, is_new, order_by_price);
                break;
            default:
                break;
        }
    }

}
