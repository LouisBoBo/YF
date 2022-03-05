package com.yssj.ui.activity.classfication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.ItemView;
import com.yssj.custom.view.MyListView;
import com.yssj.data.YDBHelper;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.VipDikouData;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.shopdetails.MatchDetailsActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.ToastUtil;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 品牌
 */
public class ManufactureActivity extends BasicActivity implements OnClickListener {
    public static int width;
    private int heights;
    private Context mContext;
    private List<HashMap<String, Object>> dataList;// 商品列表
    private View mBack;
    private PullToRefreshListView mListView;// 推荐商品
    private int mType = 1;// 1：初始化数据；2：加载更多数据
    private int index = 1;
    private DateAdapter mAdapter;
    private String suppleId;
    private ImageView topImageView;
    private TextView contentTV;
    private HashMap<String, String> supple_data;
    private List<HashMap<String, String>> listDataTop;
    private YDBHelper dbHelp;
    private View rlTop;
    private TextView titleTv;
    private VipDikouData mVipDikouData;
    private String mSupp_label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_manufacture);
        mContext = this;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        heights = width * 2 / 3;
        supple_data = (HashMap<String, String>) getIntent().getSerializableExtra("supple_data");
        suppleId = getIntent().getStringExtra("supple_id");
        mSupp_label = getIntent().getStringExtra("supp_label");
        dbHelp = new YDBHelper(mContext);
        listDataTop = new ArrayList<HashMap<String, String>>();
        initView();
        titleTv.setText(mSupp_label);
        titleTv.setVisibility(View.VISIBLE);
    }

    private void initView() {
        mBack = findViewById(R.id.img_back);
        mBack.setOnClickListener(this);
        dataList = new ArrayList<HashMap<String, Object>>();
        mListView = (PullToRefreshListView) findViewById(R.id.r_list_view);
        mListView.setMode(Mode.BOTH);
        rlTop = findViewById(R.id.ll_title);
        rlTop.setBackgroundResource(R.drawable.zhezhao2x);
        titleTv = (TextView) findViewById(R.id.supple_lable_title_tv);
        View headView = getLayoutInflater().inflate(R.layout.manfacture_top_view, null);
        ListView listView = mListView.getRefreshableView();
        listView.addHeaderView(headView);
        topImageView = (ImageView) headView.findViewById(R.id.img_manufacture);
        // 设置图片的宽高比 加载图片
        ViewGroup.LayoutParams lp = topImageView.getLayoutParams();
        lp.width = ManufactureActivity.width;
        lp.height = LayoutParams.WRAP_CONTENT;
        topImageView.setLayoutParams(lp);
        topImageView.setMaxWidth(ManufactureActivity.width);
        topImageView.setMaxHeight(ManufactureActivity.width * 2 / 3); // 宽高比3:2
        contentTV = (TextView) headView.findViewById(R.id.manufacture_tv);
        if (supple_data != null) {
            suppleId = supple_data.get("_id");
//			SetImageLoader.initImageLoader(mContext, topImageView, supple_data.get("pic"), "");


            PicassoUtils.initImage(mContext, supple_data.get("pic"), topImageView);

            contentTV.setText(supple_data.get("remark"));
//            titleTv.setText(supple_data.get("name"));
        } else if (suppleId != null) {
            listDataTop = new ArrayList<HashMap<String, String>>();
            String sql = "select * from supp_label where _id = " + suppleId;
            listDataTop = dbHelp.query(sql);
            if (listDataTop.size() > 0) {
//				 SetImageLoader.initImageLoader(mContext, topImageView, listDataTop.get(0).get("pic"), "");
                PicassoUtils.initImage(mContext, listDataTop.get(0).get("pic"), topImageView);


                contentTV.setText(listDataTop.get(0).get("remark"));
//                titleTv.setText(listDataTop.get(0).get("name"));
            } else {
                topImageView.setVisibility(View.GONE);
                contentTV.setVisibility(View.GONE);
            }
        } else {
            topImageView.setVisibility(View.GONE);
            contentTV.setVisibility(View.GONE);
        }


        if (YJApplication.instance.isLoginSucess()) {
            //查询抵扣
            HashMap<String, String> pairsMap = new HashMap<>();
            YConn.httpPost(this, YUrl.GETALLDIKOU, pairsMap, new HttpListener<VipDikouData>() {

                @Override
                public void onSuccess(VipDikouData result) {
                    mVipDikouData = result;
                    mAdapter = new DateAdapter(mContext);
                    mListView.setAdapter(mAdapter);
                    setListViewRefresh();
                    getShops();
                    setScollListener();
                }

                @Override
                public void onError() {

                }
            });

            return;
        }
        mAdapter = new DateAdapter(mContext);
        mListView.setAdapter(mAdapter);
        setListViewRefresh();
        getShops();
        setScollListener();


    }

    private void setListViewRefresh() {
        mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                index = 1;
                mType = 1;
                getShops();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                index++;
                mType = 2;
                getShops();
            }

        });

    }


    /**
     */
    private void getShops() {
        new SAsyncTask<String, Void, List<HashMap<String, Object>>>(this, 0) {

            @Override
            protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    // 填充热门推荐商品
                    if (mType == 1 && result != null) {
                        mListView.setVisibility(View.VISIBLE);
                        dataList.clear();
                        dataList.addAll(result);
                        mAdapter.notifyDataSetChanged();

                    } else if (mType == 2) {
                        if (result == null || result.size() == 0) {
                            ToastUtil.showShortText(context, "没有更多商品了哦~");
                        } else {
                            dataList.addAll(result);
                            mAdapter.notifyDataSetChanged();
                            mListView.getRefreshableView().smoothScrollBy(200, 10);
                        }
                    }
                }
                mListView.onRefreshComplete();
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
                    throws Exception {

                return ComModel2.getProductListSuppLabel(context, suppleId, index + "", 10);
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

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);

    }

    // 这个地方可以让专题相关商品和热门推荐商品共用
    private class DateAdapter extends BaseAdapter {

        private Context context;

        private int picHeight;

        public DateAdapter(Context context) {
            super();
            this.context = context;
            int dp = DP2SPUtil.dp2px(context, 24);
            picHeight = (context.getResources().getDisplayMetrics().widthPixels - dp) / 2 * 900 / 600;
        }

        @Override
        public int getCount() {
            int count = 0;
            count += dataList.size() % 2 == 0 ? dataList.size() / 2 : dataList.size() / 2 + 1;
            if (count == 0) {
                count = 1;
            }
            return count;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int position, View view, ViewGroup arg2) {
            ItemViews items;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_fragment_adapter, null);
                items = new ItemViews();
                items.left = (com.yssj.custom.view.ItemView) view.findViewById(R.id.left);
                items.left.setHeight(picHeight);
                items.right = (com.yssj.custom.view.ItemView) view.findViewById(R.id.right);
                items.right.setHeight(picHeight);
                items.noData = (TextView) view.findViewById(R.id.noData);
                items.noData.getLayoutParams().height = MatchDetailsActivity.heigth;
                items.data = view.findViewById(R.id.data);
                view.setTag(items);
            } else {
                items = (ItemViews) view.getTag();
            }
            if (dataList.isEmpty()) {
                items.noData.setVisibility(view.VISIBLE);
                items.data.setVisibility(view.GONE);
            } else {
                items.noData.setVisibility(view.GONE);
                items.data.setVisibility(view.VISIBLE);
            }

            position = position * 2;
            boolean mIsVip = false;
            if (YJApplication.instance.isLoginSucess()) {
                mIsVip = CommonUtils.isVip(mVipDikouData.getIsVip(), mVipDikouData.getMaxType());
            }


            if (dataList.size() > position) {
                // String url = (String) dataList.get(position).get("def_pic");
                items.left.setManufacture(true);


                if (mIsVip) {
                    items.left.iniViewVip(dataList.get(position), mVipDikouData);

                } else {
                    items.left.iniView(dataList.get(position));

                }


                items.left.setTag(position);
                items.left.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // YunYingTongJi.yunYingTongJi(context,
                        // 66);//搭配详情页列表下商品图片
                        int position = (Integer) arg0.getTag();
                        addScanDataTo((String) dataList.get(position).get("shop_code"));
                        // ItemMatchDetailsFragment.this.position = position;
                        Intent intent = new Intent(mContext, ShopDetailsActivity.class);
                        intent.putExtra("code", (String) dataList.get(position).get("shop_code"));
                        // context.startActivity(intent);
                        intent.putExtra("shopCarFragment", "shopCarFragment");
                        FragmentActivity activity = (FragmentActivity) mContext;
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                    }
                });
            }
            if (dataList.size() > position + 1) {
                items.right.setVisibility(view.VISIBLE);
                // String url = (String) dataList.get(position +
                // 1).get("def_pic");
                items.right.setManufacture(true);

                if (mIsVip) {
                    items.right.iniViewVip(dataList.get(position + 1), mVipDikouData);

                } else {
                    items.right.iniView(dataList.get(position + 1));

                }


                items.right.setTag(position + 1);
                items.right.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // YunYingTongJi.yunYingTongJi(context, 66);//
                        // 搭配详情页列表下商品图片
                        int position = (Integer) arg0.getTag();
                        // mContext.getSharedPreferences("YSSJ_yf",
                        // Context.MODE_PRIVATE).edit()
                        // .putBoolean("isGoDetail", true).commit();
                        addScanDataTo((String) dataList.get(position).get("shop_code"));
                        // ItemMatchDetailsFragment.this.position = position;
                        Intent intent = new Intent(mContext, ShopDetailsActivity.class);
                        intent.putExtra("code", (String) dataList.get(position).get("shop_code"));
                        // context.startActivity(intent);
                        intent.putExtra("shopCarFragment", "shopCarFragment");

                        FragmentActivity activity = (FragmentActivity) mContext;
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                    }
                });
            } else {
                items.right.setVisibility(View.INVISIBLE);
            }

            return view;
        }

    }

    /*
     * 把浏览过的数据添加进数据库
     */
    private void addScanDataTo(final String shop_code) {
        new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) mContext) {

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

    private static class ItemViews {

        private ItemView left;
        private ItemView right;
        private TextView noData;
        private View data;
    }

    private void setScollListener() {

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int myposition;
            private ImageButton aa2;

            @Override
            public void onScrollStateChanged(AbsListView view, int arg1) {

                View childAt = view.getChildAt(0);
                switch (arg1) {
                    case SCROLL_STATE_TOUCH_SCROLL:// 滚动之前
                        if (childAt == null) {
                            myposition = 0;
                        } else {
                            myposition = -childAt.getTop()
                                    + view.getFirstVisiblePosition() * childAt.getHeight();
                        }
                        break;

                    case SCROLL_STATE_FLING: // 滚动
                        int newPosition = 0;
                        if (childAt == null) {
                            newPosition = 0;
                        } else {
                            newPosition = -childAt.getTop()
                                    + view.getFirstVisiblePosition() * childAt.getHeight();
                        }

                        if (newPosition > myposition) { // 向上滑动

                        } else if (newPosition < myposition) {

                        }
                        break;
                    case SCROLL_STATE_IDLE:

                        break;
                }
            }

            @Override
            public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
                int perHeight = heights / 100;
                float currentY = 0;
                int viewTop = -1;
                aa2 = (ImageButton) findViewById(R.id.imgbtn_left_icon);

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
//                    titleTv.setVisibility(View.GONE);
                    rlTop.setBackgroundResource(R.drawable.zhezhao2x);
                    aa2.setBackgroundResource(R.drawable.icon_fanhui);
                    rlTop.getBackground().setAlpha(255);
                }
                if (Math.abs(currentY) > 0 && Math.abs(currentY) < heights / 2) {
                    aa2.setBackgroundResource(R.drawable.icon_fanhui);
                    titleTv.setVisibility(View.VISIBLE);
                    int i = (int) Math.abs(currentY / heights * 255);

                    if (Math.abs(currentY) == 0) {
                        i = 1;
                    }
                    aa2.getBackground().setAlpha(255 - i);
                    titleTv.setTextColor(Color.argb(255 - i, 62, 62, 62));

                }

                if (Math.abs(currentY) >= heights / 2 && Math.abs(currentY) < heights) {
                    aa2.setBackgroundResource(R.drawable.icon_fanhui_black);
                    titleTv.setVisibility(View.VISIBLE);
                    int i = (int) Math.abs(currentY / heights * 255);

                    if (Math.abs(currentY) == 0) {
                        i = 1;
                    }
                    aa2.getBackground().setAlpha(i);
                    titleTv.setTextColor(Color.argb(i, 62, 62, 62));
                }

                if (Math.abs(currentY) <= heights && Math.abs(currentY) > 0) {
                    rlTop.setBackgroundColor(getResources().getColor(R.color.white));
                    int i = (int) Math.abs(currentY / heights * 255);

                    if (Math.abs(currentY) == 0) {
                        i = 1;
                    }
                    rlTop.getBackground().setAlpha(i);
                    titleTv.setTextColor(Color.argb(i, 62, 62, 62));
                }

                if (Math.abs(currentY) >= heights) {
                    rlTop.getBackground().setAlpha(255);
                    titleTv.setTextColor(Color.argb(255, 62, 62, 62));
                    aa2.setBackgroundResource(R.drawable.icon_fanhui_black);
                }

            }
        });
    }
}
