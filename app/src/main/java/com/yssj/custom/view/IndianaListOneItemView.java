package com.yssj.custom.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.Shop;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.shopdetails.ShopDetailsIndianaActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsMoneyIndianaActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.PicassoUtils;

import java.util.HashMap;

/**
 * 一元夺宝 赢提现额度
 */
public class IndianaListOneItemView extends LinearLayout {

    private Context context;

    private TextView name;

    //	private CheckBox box;

    private ImageView image;
    private TextView mPeoPleCount;
    private ProgressBar mBar;

    private int width;


    public IndianaListOneItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.indiana_shop_one_list, this);

        name = (TextView) findViewById(R.id.news_title);
        image = (ImageView) findViewById(R.id.news_pic);
        mPeoPleCount= (TextView) findViewById(R.id.indiana_people_count);
        mBar= (ProgressBar) findViewById(R.id.indiana_progressbar);

        width = context.getResources().getDisplayMetrics().widthPixels;
    }

    public void initView(final HashMap<String, Object> map, final int index) {
        /**这里赋值*/
        image.getLayoutParams().height = ((width- DP2SPUtil.dp2px(context, 18))/2) * 900 / 600;

        image.setTag(map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic"));
        if (width > 720) {
            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!382", image);
        } else {
            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!280", image);

        }
        name.setText(Shop.getShopNameStrNew((String) map.get("shop_name")));
        mPeoPleCount.setText("已有"+map.get("involved_people_num")+"人正在参与");

        mBar.setMax(Integer.parseInt((String)map.get("active_people_num")));//总人数
        mBar.setProgress(Integer.parseInt((String)map.get("involved_people_num")));//已参与人数
        this.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (IndianaListOneItemView.this.getVisibility() != View.VISIBLE) {
                    return;
                }
//                addScanDataTo((String) map.get("shop_code"));
                Intent intent = new Intent(context, ShopDetailsMoneyIndianaActivity.class);
                intent.putExtra("shop_code", (String) map.get("shop_code"));
                FragmentActivity activity = (FragmentActivity) context;
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
            }
        });

    }

//    /*
//     * 把浏览过的数据添加进数据库
//     */
//    private void addScanDataTo(final String shop_code) {
//        new SAsyncTask<Void, Void, ReturnInfo>(((FragmentActivity) context)) {
//
//            @Override
//            protected ReturnInfo doInBackground(FragmentActivity context,
//                                                Void... params) throws Exception {
//                return ComModel.addMySteps(context, shop_code);
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context,
//                                         ReturnInfo result) {
//                super.onPostExecute(context, result);
//            }
//
//        }.execute();
//    }


}
