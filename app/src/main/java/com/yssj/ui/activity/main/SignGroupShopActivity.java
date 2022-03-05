package com.yssj.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ShopCart;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelZ;
import com.yssj.ui.activity.GroupsDetailsActivity;
import com.yssj.ui.adpter.SignGroupShopAdapter;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.SignActiveShopExplainDialog;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 签到活动商品
 *
 * @author Administrator
 * @date 2016年10月12日下午3:28:17
 */
public class SignGroupShopActivity extends BasicActivity implements OnClickListener {
    private PullToRefreshListView r_list_view;
    private SignGroupShopAdapter mAdapter;
    private View img_back;
    private TextView tv_shuoming;
    private int mType = 1;// 1：初始化数据；2：加载更多数据
    private int index = 1;
    private String singvalue;
    private ImageView fight_groups_icon;
    public static final int REQUEST_DETAILS = 1001;//跳到商品详情
    public static final int RESULT_DETAILS = 2001;//跳到商品详情
    public static int seleckFlag = -1;//点击了第几个条目
    public static List<ShopCart> listClick = new ArrayList<ShopCart>();
    private TextView tvHead2;
    private TextView tvHead6;
    private TextView tvHead5;
    public static SignGroupShopActivity instance;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        instance=this;
        listClick.clear();
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_sign_group);
        initView();
    }

    private void initView() {
        instance = this;
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        r_list_view = (PullToRefreshListView) findViewById(R.id.r_list_view);
        r_list_view.setMode(Mode.BOTH);
        tv_shuoming = (TextView) findViewById(R.id.task_explain_tv);
        tv_shuoming.setOnClickListener(this);
        fight_groups_icon = (ImageView) findViewById(R.id.fight_groups_icon);
        fight_groups_icon.setOnClickListener(this);
        mAdapter = new SignGroupShopAdapter(this);
        r_list_view.setAdapter(mAdapter);
        tv_shuoming.setVisibility(View.GONE);
//		if(!SignFragment.isSignComplete&&(SignFragment.doType == 4||SignFragment.doType == 5)){
//			showDialog();
//		}
//		if(SignFragment.doType == 4||SignFragment.doType == 5){//浏览任务时候才有浏览任务说明
//			tv_shuoming.setVisibility(View.VISIBLE);
//		}else{
//			tv_shuoming.setVisibility(View.GONE);
//		}
//		if(SignFragment.doType==4){//强制浏览个数
//			//获取当前任务的需要浏览的次数
//			String value = SignFragment.doValue;
//			String values [] = value.split(",");
//			if(values.length>1){
//				singvalue = values[1];
//				if(!Pattern.compile("^\\+?[1-9][0-9]*$").matcher(singvalue).find()){
//					singvalue=""+SignFragment.doNum;
//				}
//
//			}else{
//				singvalue=""+SignFragment.doNum;
//			}
//		}

        ListView listView = r_list_view.getRefreshableView();
        View view = LayoutInflater.from(SignGroupShopActivity.this).inflate(R.layout.groop_head_view, null);
        tvHead2 = (TextView) view.findViewById(R.id.textView2);
//        tvHead6 = (TextView) view.findViewById(R.id.textView6);
        tvHead5= (TextView) view.findViewById(R.id.textView5);
        String text_51="5、";
        String n1="开团与参团均无需付款，团满后即视为拼团成功，需在规定时间内完成付款，逾期即视为拼团失败。";
        String text_52="拼团费会在5-7个工作日内返还至付款账号，0风险。";
        String text2=text_51+n1+text_52;
        SpannableString textSpan2 = new SpannableString(text2);
        textSpan2.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), text_51.length(), text_51.length() + n1.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvHead5.setText(textSpan2);
        getText();
        listView.addHeaderView(view);
        setListViewRefresh();
        initData(index + "");

    }

    private void setListViewRefresh() {
        r_list_view.setMode(Mode.BOTH);
        r_list_view.setOnRefreshListener(new OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                listClick.clear();
                index = 1;
                mType = 1;
                initData(index + "");
//				r_list_view.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//				r_list_view.onRefreshComplete();
                index++;
                mType = 2;
                initData(index + "");
            }

        });

    }

    /**
     * 每天只显示一次
     */
    private void showDialog() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String taskExplain = SharedPreferencesUtil.getStringData(context, Pref.SIGN_ACTIVE_SHOP_TASK_EXPLAIN, "0");
        long forceLookTime = Long.valueOf(taskExplain);
        if ("0".equals(taskExplain) || !df.format(new Date()).equals(df.format(new Date(forceLookTime)))) {
            new SignActiveShopExplainDialog(context).show();
            SharedPreferencesUtil.saveStringData(context, Pref.SIGN_ACTIVE_SHOP_TASK_EXPLAIN, System.currentTimeMillis() + "");
        }

    }

    private void initData(final String index) {
        final int pageSize = 10;
        new SAsyncTask<String, Void, List<HashMap<String, Object>>>(this, 0) {
            @Override
            protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
                    throws Exception {

                return ComModel2.getSignActiveShopList(context, index, pageSize + "");
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
                    if (result != null) {
                        for (int i = 0; i < result.size(); i++) {
                            HashMap<String, Object> map = result.get(i);
                            map.put("is_click", "0");
                        }
                    }
                    if (mType == 1) {
                        if (result == null || result.size() == 0) {
                            r_list_view.setVisibility(View.GONE);
                        } else {
                            r_list_view.setVisibility(View.VISIBLE);
                        }

                        mAdapter.setData(result);

                    } else if (mType == 2) {
                        if (result == null || result.size() == 0) {
                            ToastUtil.showShortText(context, "已没有更多商品了哦~");
                        }else{
                            mAdapter.addItemLast(result);
                            r_list_view.getRefreshableView().smoothScrollBy(100, 20);
                        }
                    }
                }
                r_list_view.onRefreshComplete();
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
            case R.id.task_explain_tv:
                new SignActiveShopExplainDialog(this).show();
                break;
            case R.id.fight_groups_icon://拼团详情
                Intent intent = new Intent(context, GroupsDetailsActivity.class);
                context.startActivity(intent);
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_DETAILS:
                if (resultCode == RESULT_DETAILS) {
                    mAdapter.modfyClickStats();
                    mAdapter.notifyDataSetChanged();
                    List<ShopCart> listGoods = (List<ShopCart>) data.getExtras().getSerializable("listGoods");
                    listClick.clear();
                    listClick.add(listGoods.get(0));
//                    ToastUtil.showLongText(SignGroupShopActivity.this, "超级拼团购9块9可选两件，你还可以在在选一件！");
                    ToastUtil.showMyToast(SignGroupShopActivity.this, "超级拼团购9块9可选两件，你还可以再选一件！",3000);
                }
                break;

            default:
                break;
        }
    }

    public void getText() {
        new SAsyncTask<Void, Void, HashMap<String, String>>(SignGroupShopActivity.this, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModelZ.getGroupContent();
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null) {

                    String text_21 = "2、获得超级拼团购资格的用户，团长和团员均可在衣蝠当天的";
//                    String text_22 = "";
                    String text_23 = "。再也不怕和好友撞衫啦。";
                    String n1 = "100款活动商品中"+result.get("n1")+"，享受";
                    String n2 = result.get("n2")+"的价格";
//                    String n3 = result.get("n3");
                    String n4 = result.get("n4");

                    String text1 = text_21+n1+n2+text_23;
//                       tvGuiZe1.setTextColor(Color.WHITE);
                    SpannableString textSpan1 = new SpannableString(text1);
                    textSpan1.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), text_21.length(), text_21.length() + n1.length()+n2.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    textSpan1.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), text_21.length() + n1.length() + text_22.length(), text_21.length() + n1.length() + text_22.length() + n2.length(),
//                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    textSpan1.setSpan(new ForegroundColorSpan(Color.
//                                    parseColor("#FFF400")), text_21.length() + n1.length() + text_22.length() + n2.length() + text_23.length(), text_21.length() + n1.length() + text_22.length() + n2.length() + text_23.length() + n3.length(),
//                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvHead2.setText(textSpan1);


//                    if(null!=n4&&!"".equals(n4)&&!"null".equals(n4)) {
//                        String text_51 = "5、参团用户也是在衣蝠当天的";
//                        String text_52 = "，再也不怕和好友撞衫啦。";
//                        String text_53="开团后快把这个超级福利告诉朋友们，邀请她们来参团吧。";
//                        String text2 = text_51 + n4 + text_52+text_53;
//                        SpannableString textSpan2 = new SpannableString(text2);
//                        textSpan2.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), text_51.length(), text_51.length() + n4.length(),
//                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        textSpan2.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), text_51.length()+n4.length()+text_52.length(), text_51.length()+n4.length()+text_52.length()+text_53.length(),
//                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        tvHead5.setText(textSpan2);
//                    }

                }
            }

        }.execute();
    }
}
