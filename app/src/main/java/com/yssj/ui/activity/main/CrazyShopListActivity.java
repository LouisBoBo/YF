package com.yssj.ui.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.data.YDBHelper;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.adpter.CrazyShopListAdapter;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.PicassoUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 专题---更多专题和 购物页——更多供应商
 *
 * @author Administrator
 * @date 2016年10月12日下午3:28:17
 */
public class CrazyShopListActivity extends BasicActivity implements OnClickListener {
    public static ListView r_list_view;
    private CrazyShopListAdapter mAdapter;
    private View img_back;
    private TextView tv_title;
    private int mType = 1;// 1：初始化数据；2：加载更多数据
    private int index = 1;
    private String singvalue;
    private ImageView fight_groups_icon;
    public static boolean isEnd;
    private boolean isforcelookMatch;
    private List<HashMap<String, String>> listDataTop;
    private YDBHelper dbHelp;

    private View mHeadView;// 头部


    public static int width;
    private Context context;

    // 更多供应商

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.crazy_tagelist_activity);
        dbHelp = new YDBHelper(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        initView();
    }

    private void initView() {
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        r_list_view = (ListView) findViewById(R.id.r_list_view);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("疯狂新衣节");


        mHeadView = View.inflate(context, R.layout.xinyijie_taglist_head, null);

        ImageView subject_main_image_iv = (ImageView) mHeadView.findViewById(R.id.subject_main_image_iv);

        PicassoUtils.initBigImage(context, YUrl.imgurl + "sign_in/crazy/crazyPic.jpg", subject_main_image_iv);


        r_list_view.addHeaderView(mHeadView);


        mAdapter = new CrazyShopListAdapter(this);
        r_list_view.setAdapter(mAdapter);

        initData(index);

    }


    @Override
    protected void onResume() {
        super.onResume();
        isEnd = false;
    }

    private void initData(final int index) {


        // shop_code的值为shopGroupList中对应的id


        new SAsyncTask<Void, Void, List<HashMap<String, String>>>((FragmentActivity) context, null, R.string.wait) {
            @Override
            protected List<HashMap<String, String>> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ModQingfeng.getCrazyTagList(context, "6");
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<HashMap<String, String>> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null) {
                    if (result == null || result.size() == 0) {
                        r_list_view.setVisibility(View.GONE);
                    }


                    mAdapter.setData(result);
                }

            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            ;
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

}
