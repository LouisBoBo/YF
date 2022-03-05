package com.yssj.ui.activity.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.CustImageGalleryMatch;
import com.yssj.custom.view.MatchNavLeft;
import com.yssj.custom.view.MatchNavRigth;
import com.yssj.custom.view.PointAlarmView;
import com.yssj.custom.view.SnatchScrollList;
import com.yssj.custom.view.SnatchScrollList.MatchOnRefreshLintener;
import com.yssj.custom.view.XListViewMatch;
import com.yssj.entity.Shop;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.shopdetails.MatchDetailsActivity;
import com.yssj.ui.activity.shopdetails.SpecialTopicDeatilsActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.LeaveDialog;
import com.yssj.ui.dialog.XunBaoDialog;
import com.yssj.ui.dialog.XunBaoMatchDialog;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ForceLookMatchActivity extends BasicActivity implements OnClickListener, MatchOnRefreshLintener {
    private View imgBack;
    private Context mContext;
    //	private PullToRefreshListView mListViewMatch;
    private XListViewMatch mListViewMatch;
    private SnatchScrollList mView;
    private TextView mTitle;
    private List<HashMap<String, Object>> mListDatas;
    private MatchAdapter matchAdater;
    public static int width;
    public static int height;
    private String singvalue;//需要浏览的次数
    private String matchType;//1表示搭配 2 表示专题

    private boolean isAddShopcart;//是否是加入购物车

    public static ForceLookMatchActivity instance;

    // isNotScan ture 代表不是浏览的任务 界面不显示浏览相关内容
    private boolean isNotScan;//疯狂新衣节（不是浏览任务）
    private String doIconId;

    private java.text.DecimalFormat pFormate;
    private int mType = 1;// 1：初始化数据；2：加载更多数据
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_scan_task);
        context = this;
        AppManager.getAppManager().addActivity(this);
//		SignListAdapter.doType = 5;
        matchType = getIntent().getStringExtra("type");
        isNotScan = getIntent().getBooleanExtra("isCrazy", false);
        doIconId = getIntent().getStringExtra("doIconId");//分类类目ID


        isAddShopcart = getIntent().getBooleanExtra("isAddShopcart", false);//分类类目ID

        initView();
    }

    private void initView() {
        instance = this;
        mContext = this;
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        mTitle = (TextView) findViewById(R.id.id_match_title);
        if ("1".equals(matchType)) {
            if (!isNotScan && SignListAdapter.doType == 4) {

            } else {
                mTitle.setText("时尚搭配");
                findViewById(R.id.rl_yuefanbei).setVisibility(View.GONE);
            }

        } else if ("2".equals(matchType)) {
            if (!isNotScan && SignListAdapter.doType == 4) {

            } else {
                mTitle.setText("专题");
                findViewById(R.id.rl_yuefanbei).setVisibility(View.GONE);
            }

        }
        findViewById(R.id.tv_shuoming).setOnClickListener(this);
        mView = (SnatchScrollList) findViewById(R.id.matchView);
        mView.setMatchOnRefreshLintener(this);
        mListViewMatch = (XListViewMatch) findViewById(R.id.dataList);
        mListViewMatch.setPullLoadEnable(true);
        pFormate = new DecimalFormat("#0.0");
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        mListDatas = new ArrayList<HashMap<String, Object>>();
        matchAdater = new MatchAdapter(mContext);
        mListViewMatch.setAdapter(matchAdater);

//		addHeadeViewBanner();

        if (!isNotScan && SignListAdapter.doType == 4) {//强制浏览个数
            //获取当前任务的需要浏览的次数
            String value = SignListAdapter.doValue;
            String values[] = value.split(",");
            if (values.length > 1) {
                singvalue = values[1];
                if (!Pattern.compile("^\\+?[1-9][0-9]*$").matcher(singvalue).find()) {
                    singvalue = "" + SignListAdapter.doNum;
                }

            } else {
                singvalue = "" + SignListAdapter.doNum;
            }
        }
        setListViewRefresh();
        queryMatch();
    }

    private int index = 1;

    private void setListViewRefresh() {

//		mListViewMatch.setMode(Mode.BOTH);
//		mListViewMatch.setOnRefreshListener(new OnRefreshListener2<ListView>() {
//
//			@Override
//			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//				index = 1;
//				mType=1;
//				queryMatch();
////				mListViewMatch.onRefreshComplete();
//			}
//
//			@Override
//			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//				if(SignListAdapter.doType==4){//浏览个数的
//					mListViewMatch.onRefreshComplete();
//				}else{
//					mType=2;
//					index++;
//					queryMatch();
//				}
//			}
//
//		});
        mListViewMatch.setXListViewListener(new XListViewMatch.IXListViewListener() {

            @Override
            public void onRefresh() {
                LogYiFu.e("MatchFragment", "onRefresh");
            }

            @Override
            public void onLoadMore() {
                mType = 2;
//				if(SignListAdapter.doType==4){//浏览个数的
////					mListViewMatch.stopLoadMore();
//				}else{
//				}
                index++;
                queryMatch();
            }
        });

    }

    //	public void refresh() {
//		index = 1;
//		mType=1;
//		queryMatch();
//	}
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.tv_shuoming:
                new XunBaoDialog(this).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {

        if (isAddShopcart) {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
        } else {
            if (!isNotScan && SignListAdapter.doType == 4 && !SignListAdapter.isSignComplete) {
                countTaskBack();
            } else if (!isNotScan && SignListAdapter.doType == 5 && !SignListAdapter.isSignComplete) {
                timeTaskBack();
            } else {
                super.onBackPressed();
                overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
            }
        }


    }
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if(SignListAdapter.doType == 4){
//			countTaskBack();
//		}else if(SignListAdapter.doType ==5){
//			timeTaskBack();	
//		}else{
//			onBackPressed();
//		}
//		return true;
//	}


    /**
     * 搭配
     */
    private void queryMatch() {
        new SAsyncTask<Void, Void, List<HashMap<String, Object>>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel2.getMatch(context, index + "", 10 + "", matchType);//增加参数1 返回全部是搭配商品
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e != null || result == null) {// 查询异常
                    mView.refreshDone();
                    mListViewMatch.stopLoadMore();
                    return;
                }
//				List<HashMap<String, Object>> dataList = result;
                if (mType == 1) {
                    mListDatas.clear();
                    mListDatas.addAll(result);
                    matchAdater.notifyDataSetChanged();
                    if (!isNotScan && SignListAdapter.doType == 4 && result.size() > 0 && !SignListAdapter.isSignComplete) {//强制浏览个数 并且当前任务没有完成
                        showDialog(result);
                    }
                    mView.refreshDone();
                } else if (mType == 2) {//
                    mListDatas.addAll(result);
//					LogYiFu.e("2222222222222", mListDatas.size()+"");;
                    matchAdater.notifyDataSetChanged();
                    if (result == null || result.size() == 0) {
                        ToastUtil.showShortText(context, "已没有更多商品了哦~");
                    }
                }
                mListViewMatch.stopLoadMore();
            }

        }.execute();
    }

    /**
     * 强制浏览签到寻宝提示框 每天只显示一次
     */
    private void showDialog(List<HashMap<String, Object>> dataList) {
        SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String forceLook = SharedPreferencesUtil.getStringData(context, Pref.FORCELOOKMATCH, "0");
        long forceLookTime = Long.valueOf(forceLook);
        if ("0".equals(forceLook) || !df.format(new Date()).equals(df.format(new Date(forceLookTime)))) {
            List<HashMap<String, Object>> collocation_shop_list =
                    (List<HashMap<String, Object>>) dataList.get(0).get("collocation_shop");
            if (collocation_shop_list == null || collocation_shop_list.size() == 0) {
                return;//有可能是专题商品专题商品 collocation_shop_list为空
            }
            String shop_name = (String) collocation_shop_list.get(0).get("shop_name");
            String shop_x = (String) collocation_shop_list.get(0).get("shop_x");
            String shop_y = (String) collocation_shop_list.get(0).get("shop_y");
            XunBaoMatchDialog dialog = new XunBaoMatchDialog(context, shop_name, shop_x, shop_y, 0);
            dialog.show();
            SharedPreferencesUtil.saveStringData(context, Pref.FORCELOOKMATCH, System.currentTimeMillis() + "");
        }

    }

    private class MatchAdapter extends BaseAdapter {
        private Context context;
        //		private List<HashMap<String, Object>> listData;
        private LayoutInflater mInflater;

        public MatchAdapter(Context context) {
            this.context = context;
//			this.listData = listData;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mListDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mListDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = mInflater.inflate(R.layout.match_fragment_list, null);
                holder.mainImageIv = (ImageView) convertView.findViewById(R.id.main_image_iv);
                holder.mainTitleTv = (TextView) convertView.findViewById(R.id.main_title_tv);
                holder.custMatch = (CustImageGalleryMatch) convertView.findViewById(R.id.match_custom_images);
                holder.mainImageRl = (RelativeLayout) convertView.findViewById(R.id.main_image_rl);
                holder.containsRl = (RelativeLayout) convertView.findViewById(R.id.Match_contains_rl);
                holder.sanjiao = (ImageView) convertView.findViewById(R.id.sanjiao);
                holder.id_divier = convertView.findViewById(R.id.id_divier);

                holder.subjectImageRl = (RelativeLayout) convertView.findViewById(R.id.subject_main_image_rl);
                holder.subjectImageIv = (ImageView) convertView.findViewById(R.id.subject_main_image_iv);
                holder.subjectTv1 = (TextView) convertView.findViewById(R.id.subject_main_tv1);
                holder.subjectTv2 = (TextView) convertView.findViewById(R.id.subject_main_tv2);
                holder.subjectSanjiao = (ImageView) convertView.findViewById(R.id.subject_sanjiao);
                convertView.setTag(holder);

            } else {
                holder = (Holder) convertView.getTag();
            }

            if (position == mListDatas.size() - 1) {
                holder.id_divier.setVisibility(View.GONE);
            } else {
                holder.id_divier.setVisibility(View.VISIBLE);
            }
            if (!isNotScan && SignListAdapter.doType == 4) {
                holder.sanjiao.setVisibility(View.GONE);
                holder.custMatch.setVisibility(View.GONE);
                holder.subjectSanjiao.setVisibility(View.GONE);
            }

            final HashMap<String, Object> datas = mListDatas.get(position);
            final String collocation_code = (String) datas.get("collocation_code");
            final String collocation_pic = (String) datas.get("collocation_pic");
            String collocation_name = (String) datas.get("collocation_name");
            String collocation_name2 = (String) datas.get("collocation_name2");
            String type = (String) datas.get("type");//1或者空 为搭配购，2为专题

            if ("2".equals(type)) {//1或者空 为搭配购，2为专题
                holder.subjectImageRl.setVisibility(View.VISIBLE);
                holder.mainImageRl.setVisibility(View.GONE);

                holder.subjectTv1.setText(collocation_name);
                holder.subjectTv2.setText(collocation_name2);
                holder.subjectTv1.getPaint().setFakeBoldText(true);//设置中文字体加粗
                // 设置图片的宽高比 加载图片
                ViewGroup.LayoutParams lp = holder.subjectImageIv.getLayoutParams();
                lp.width = width;
                lp.height = LayoutParams.WRAP_CONTENT;
                holder.subjectImageIv.setLayoutParams(lp);
                holder.subjectImageIv.setMaxWidth(width);
                holder.subjectImageIv.setMaxHeight(width * 2 / 3); // 宽高比3:2

//				SetImageLoader.initImageLoader(mContext, holder.subjectImageIv, collocation_pic, "!450");//主题图片
                PicassoUtils.initImage(context, collocation_pic + "!450", holder.subjectImageIv);//主题图片
                if (!isNotScan && SignListAdapter.doType == 4) {

                } else {
                    List<HashMap<String, Object>> shop_type_list = (List<HashMap<String, Object>>) datas.get("shop_type_list");
                    holder.custMatch.setData(shop_type_list, type);
                }

                holder.subjectImageIv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SpecialTopicDeatilsActivity.class);
                        intent.putExtra("collocation_code", collocation_code);
                        intent.putExtra("collocation_pic", collocation_pic);
                        if (!isNotScan && SignListAdapter.doType == 4 && !SignListAdapter.isSignComplete) {//强制浏览个数并且是没有完成的任务
                            intent.putExtra("isforcelookMatch", true);
                        }
                        mContext.startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    }
                });

            } else if ("1".equals(type) || "".equals(type)) {//1或者空 为搭配购，2为专题
                holder.subjectImageRl.setVisibility(View.GONE);
                holder.mainImageRl.setVisibility(View.VISIBLE);

                holder.containsRl.removeAllViews();
                //			final HashMap<String, Object> datas = mListDatas.get(position);
                //			String collocation_name = (String) datas.get("collocation_name");
                holder.mainTitleTv.setText(collocation_name);

                //			final String collocation_code = (String) datas.get("collocation_code");
                // 设置图片的宽高比 加载图片
                ViewGroup.LayoutParams lp = holder.mainImageIv.getLayoutParams();
                lp.width = width;
                lp.height = LayoutParams.WRAP_CONTENT;
                holder.mainImageIv.setLayoutParams(lp);
                holder.mainImageIv.setMaxWidth(width);
                holder.mainImageIv.setMaxHeight(width); // 图片高度设置为屏幕的宽度

                //			final String collocation_pic = (String) datas.get("collocation_pic");
//				SetImageLoader.initImageLoader(mContext, holder.mainImageIv, collocation_pic, "!450");
                PicassoUtils.initImage(mContext, collocation_pic + "!450", holder.mainImageIv);
                if (!isNotScan && SignListAdapter.doType == 4) {

                } else {
                    List<HashMap<String, Object>> shop_type_list = (List<HashMap<String, Object>>) datas.get("shop_type_list");
                    holder.custMatch.setData(shop_type_list, type);
                }

                List<HashMap<String, Object>> collocation_shop_list = (List<HashMap<String, Object>>) datas.get("collocation_shop");

                for (int i = 0; i < collocation_shop_list.size(); i++) {

                    RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);

                    RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    String shop_code = (String) collocation_shop_list.get(i).get("shop_code");
                    String shop_name = (String) collocation_shop_list.get(i).get("shop_name");
                    String shop_se_price = (String) collocation_shop_list.get(i).get("shop_se_price");
                    String kickback = (String) collocation_shop_list.get(i).get("kickback");
                    String option_flag = (String) collocation_shop_list.get(i).get("option_flag");
                    String shop_x = (String) collocation_shop_list.get(i).get("shop_x");
                    String shop_y = (String) collocation_shop_list.get(i).get("shop_y");
                    double X = 0.0;
                    double Y = 0.0;
                    if (!TextUtils.isEmpty(shop_y) && !TextUtils.isEmpty(shop_x)) {
                        X = Double.valueOf(shop_x);
                        Y = Double.valueOf(shop_y);
                    }

                    if (i == 0) {
                        X = X == 0 ? 0.45 : X;
                        Y = Y == 0 ? 0.35 : Y;

                        //					Y = Y < 0.2 ? 0.2 : Y;
                        //					Y = Y > 0.75 ? 0.75 : Y;
                        setLeftView(shop_code, option_flag, i, holder, param, param2, shop_name, shop_se_price, kickback, X, Y);
                    } else if (i == 1) {
                        X = X == 0 ? 0.6 : X;
                        Y = Y == 0 ? 0.58 : Y;

                        //					Y = Y < 0.2 ? 0.2 : Y;
                        //					Y = Y > 0.75 ? 0.75 : Y;
                        setRigthView(shop_code, option_flag, i, holder, param, param2, shop_name, shop_se_price, kickback, X, Y);
                    }
                }

                holder.mainImageRl.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //					YunYingTongJi.yunYingTongJi(context, 19);//搭配主图
                        //					if (YJApplication.instance.isLoginSucess()) {
                        //						Intent intent = new Intent(context, MatchDetailsActivity.class);
                        //						intent.putExtra("collocation_code", collocation_code);
                        //						((FragmentActivity) context).startActivity(intent);
                        //					} else {
                        //						Intent intent = new Intent(context, LoginActivity.class);
                        //						intent.putExtra("login_register", "login");
                        //						((FragmentActivity) context).startActivity(intent);
                        //					}
                        Intent intent = new Intent(context, MatchDetailsActivity.class);
                        intent.putExtra("collocation_code", collocation_code);
                        intent.putExtra("collocation_pic", collocation_pic);
                        if (!isNotScan && SignListAdapter.doType == 4 && !SignListAdapter.isSignComplete) {//强制浏览个数并且是没有完成的任务
                            intent.putExtra("isForceLookMatch", true);
                        }
                        mContext.startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    }
                });
            }
            return convertView;
        }

        private void setLeftView(String shop_code, String option_flag, int i, Holder holder,
                                 RelativeLayout.LayoutParams param, RelativeLayout.LayoutParams param2, String shop_name,
                                 String shop_se_price, String kickback, Double X, Double Y) {
            MatchNavLeft matchNavLeft = new MatchNavLeft(mContext, shop_code, 0,
                    !isNotScan && SignListAdapter.doType == 4 && !SignListAdapter.isSignComplete ? true : false);
            matchNavLeft.setTextView(Shop.getShopNameStrNew(shop_name));
            matchNavLeft.measure(0, 0);
            param2.leftMargin = (int) (width * X - matchNavLeft.getMeasuredWidth() - 8);
//			param2.topMargin = (int) (width / 2 + (Y - 0.5) * width * 1.5 - matchNavLeft.getMeasuredHeight() / 2 + 8);
            //图片宽高比为1
            param2.topMargin = (int) (width / 2 + (Y - 0.5) * width - matchNavLeft.getMeasuredHeight() / 2 + 8);
            matchNavLeft.setLayoutParams(param2);// 设置布局参数
            holder.containsRl.addView(matchNavLeft);// RelativeLayout添加子View
            setAlarmPoint(holder, X, Y);
            if ("0".equals(option_flag)) {
                // TODO 有搭配
                TextView tv = new TextView(mContext);
                tv.setBackgroundResource(R.drawable.pricetag);
                tv.setId(i);
                double sPrice = Double.valueOf(shop_se_price);
//				double sKickback =Double.valueOf(kickback);
//				tv.setText("¥" + pFormate.format(sPrice - (int)sKickback));
                tv.setText("¥" + pFormate.format(sPrice * 0.9));//显示九折价格
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(9);
                tv.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                tv.setPadding(2, 0, 2, 8);
                tv.measure(0, 0);
                param.leftMargin = (int) (width * X - tv.getMeasuredWidth() / 2);
                //图片宽高比为1
                param.topMargin = (int) (width / 2 + (Y - 0.5) * width);
                tv.setLayoutParams(param);// 设置布局参数
                holder.containsRl.addView(tv);// RelativeLayout添加子View
                matchNavLeft.getImgCart().setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);

            } else if ("1".equals(option_flag)) {
                // TODO 无搭配
                matchNavLeft.getImgCart().setVisibility(View.GONE);
                ImageView iv = new ImageView(mContext);
                iv.setBackgroundResource(R.drawable.red_point);
                iv.measure(0, 0);
                param.leftMargin = (int) (width * X - iv.getMeasuredWidth() / 2);
                //图片宽高比为1
                param.topMargin = (int) (width / 2 + (Y - 0.5) * width);
                iv.setLayoutParams(param);// 设置布局参数
                holder.containsRl.addView(iv);// RelativeLayout添加子View
            }
        }

        /**
         * 圆点波形闪烁
         *
         * @param holder
         * @param X
         * @param Y
         */
        private void setAlarmPoint(Holder holder, Double X, Double Y) {
            ImageView iv = new ImageView(mContext);
            iv.setBackgroundResource(R.drawable.red_point);
            iv.measure(0, 0);

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(80, 80);
            PointAlarmView pointView = new PointAlarmView(mContext);
            lp.leftMargin = (int) (width * X - 40);// pointView 1/2 位置
            //图片宽高比为1
            lp.topMargin = (int) (width / 2 + (Y - 0.5) * width - 40 + iv.getMeasuredHeight() / 2);
            pointView.setLayoutParams(lp);
            holder.containsRl.addView(pointView);
        }

        private void setRigthView(String shop_code, String option_flag, int i, Holder holder,
                                  RelativeLayout.LayoutParams param, RelativeLayout.LayoutParams param2, String shop_name,
                                  String shop_se_price, String kickback, Double X, Double Y) {

            MatchNavRigth matchNavRigth = new MatchNavRigth(mContext, shop_code, 0,
                    !isNotScan && SignListAdapter.doType == 4 && !SignListAdapter.isSignComplete ? true : false);
            matchNavRigth.setTextView(Shop.getShopNameStrNew(shop_name));
            matchNavRigth.measure(0, 0);
            param2.rightMargin = 10;
            param2.leftMargin = (int) (width * X + 8);
//			param2.topMargin = (int) (width / 2 + (Y - 0.5) * width * 1.5 - matchNavRigth.getMeasuredHeight() / 2 + 8);
            //图片宽高比为1
            param2.topMargin = (int) (width / 2 + (Y - 0.5) * width - matchNavRigth.getMeasuredHeight() / 2 + 8);
            matchNavRigth.setLayoutParams(param2);// 设置布局参数
            holder.containsRl.addView(matchNavRigth);// RelativeLayout添加子View
            setAlarmPoint(holder, X, Y);
            if ("0".equals(option_flag)) {
                // TODO 有搭配
                TextView tv = new TextView(mContext);
                tv.setBackgroundResource(R.drawable.pricetag);
                tv.setId(i + 500);
//				tv.setText("¥" + shop_se_price);
                double sPrice = Double.valueOf(shop_se_price);
//				double sKickback =Double.valueOf(kickback);
//				tv.setText("¥" + pFormate.format(sPrice - (int)sKickback));
                tv.setText("¥" + pFormate.format(sPrice * 0.9));//显示九折价格
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(9);
                tv.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                tv.setPadding(2, 0, 2, 8);
                tv.measure(0, 0);
                param.leftMargin = (int) (width * X - tv.getMeasuredWidth() / 2);
                //图片宽高比为1
                param.topMargin = (int) (width / 2 + (Y - 0.5) * width);
                tv.setLayoutParams(param);// 设置布局参数
                holder.containsRl.addView(tv);// RelativeLayout添加子View
                matchNavRigth.getImgCart().setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);

            } else if ("1".equals(option_flag)) {
                // TODO 无搭配
                matchNavRigth.getImgCart().setVisibility(View.GONE);
                ImageView iv = new ImageView(mContext);
                iv.setBackgroundResource(R.drawable.red_point);
                iv.measure(0, 0);
                param.leftMargin = (int) (width * X - iv.getMeasuredWidth() / 2);
                //图片宽高比为1
                param.topMargin = (int) (width / 2 + (Y - 0.5) * width);
                iv.setLayoutParams(param);// 设置布局参数
                holder.containsRl.addView(iv);// RelativeLayout添加子View
            }

        }

        class Holder {
            CustImageGalleryMatch custMatch;//搭配和专题显示的共同部分 推荐列表
            ImageView mainImageIv, sanjiao;
            TextView mainTitleTv;
            RelativeLayout mainImageRl, containsRl;
            View id_divier;
            /**
             * 专题显示的View
             */
            ImageView subjectImageIv, subjectSanjiao;
            TextView subjectTv1, subjectTv2;
            RelativeLayout subjectImageRl;
        }
    }

    /**
     * 浏览个数任务返回
     */
    private void countTaskBack() {
        String nowTimeForcelookMatch = SharedPreferencesUtil.getStringData(ForceLookMatchActivity.this, "forcelookMatchNowTime" + YCache.getCacheUser(context).getUser_id(), "");
        int forcelookMatchNum = Integer.valueOf(SharedPreferencesUtil.getStringData(ForceLookMatchActivity.this, SignListAdapter.signIndex + "forcelookMatchNum" + YCache.getCacheUser(context).getUser_id(), "0"));
        SimpleDateFormat dfNum = new java.text.SimpleDateFormat("yyyy-MM-dd");
        if (!dfNum.format(new Date()).equals(nowTimeForcelookMatch)) {
            forcelookMatchNum = 0;//不是同一天点击分享任务    或者不是同一个用户 就  或者取出的数据大于浏览次数  计数分享重置
        }
        if (forcelookMatchNum >= Integer.parseInt(singvalue)) {
            finish();
            overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
        } else {
            // Toast.makeText(context, "亲，在逛一会才能领取现金哦",
            // Toast.LENGTH_SHORT).show();
            final LeaveDialog leaveDialog = new LeaveDialog(this);
            leaveDialog.show();

            View btn_left = leaveDialog.findViewById(R.id.btn_left);
            btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (leaveDialog != null) {
                        leaveDialog.dismiss();
                    }
                    finish();
                    overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
                }
            });

        }
    }

    /**
     * 浏览分钟数返回时候的弹窗提示
     */
    private void timeTaskBack() {
        if (SignListAdapter.isForceLookTimeOut) {//分钟数时间到了
            finish();
            overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
        } else {
            final LeaveDialog leaveDialog = new LeaveDialog(this);
            leaveDialog.show();
            leaveDialog.setContentText("你正在进行浏览商品任务，浏览时长还未完成，你可以选择再逛逛当前页面，或者去浏览其它商品，浏览时长达到任务要求即可完成任务喔~");
            leaveDialog.setButtonText("不了，谢谢", "其他商品");
            View btn_left = leaveDialog.findViewById(R.id.btn_left);
            btn_left.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (leaveDialog != null) {
                        leaveDialog.dismiss();
                    }
                    finish();
                    overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
                }
            });
            View btn_right = leaveDialog.findViewById(R.id.btn_right);
            btn_right.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtils.finishActivity(MainMenuActivity.instances);
                    Intent intent = new Intent((Activity) context, MainMenuActivity.class);
                    intent.putExtra("toYf", "toYf");
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onRefreshlintener() {
        index = 1;
        mType = 1;
        queryMatch();
    }


//	private  View  headView;
//	private ImageView imgBanner;
//	private YDBHelper dbHelp;
//	private List<HashMap<String,String>> listData;
//	private void  addHeadeViewBanner(){
//		dbHelp = new YDBHelper(context);
//		headView = LayoutInflater.from(this).inflate(R.layout.view_scan_header,null);
//		mListViewMatch.addHeaderView(headView);
//		imgBanner = (ImageView) headView.findViewById(R.id.img_head);
//		ViewGroup.LayoutParams lp = imgBanner.getLayoutParams();
//		lp.width =width;
//		lp.height =width/2;
//		imgBanner.setLayoutParams(lp);//图片宽高2:1
////		imgBanner.setImageResource(R.drawable.bg_group_shop);
//		listData= new ArrayList<HashMap<String,String>>();
//		String sql = "select * from shop_group_list where _id = "+doIconId;
//		listData =  dbHelp.query(sql);
//		if(listData!=null&&listData.size()>0){
//			String banner = listData.get(0).get("banner");
//			if(!TextUtils.isEmpty(banner)){
//				PicassoUtils.initImage(this,banner+"!450",imgBanner);
//			}
//
//		}
//	}
}
