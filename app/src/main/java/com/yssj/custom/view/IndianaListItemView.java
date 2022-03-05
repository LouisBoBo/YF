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
import com.yssj.model.ComModelL;
import com.yssj.ui.activity.shopdetails.ShopDetailsGroupIndianaActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsIndianaActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.PicassoUtils;

import java.text.DecimalFormat;
import java.util.HashMap;

public class IndianaListItemView extends LinearLayout {

    private Context context;

    private TextView name;

    //	private CheckBox box;

    private ImageView image;
    private TextView mPeoPleCount;
    private ProgressBar mBar;
    private TextView tvPrice;
    private ImageView yiFenIcon;
    private boolean indianaGroups;

    public void setIndianaGroups(boolean indianaGroups) {
        this.indianaGroups = indianaGroups;
    }

    private int width;
    private static int index;//点了第几个条目


    public IndianaListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.indiana_shop_list, this);

        name = (TextView) findViewById(R.id.news_title);
        image = (ImageView) findViewById(R.id.news_pic);
        mPeoPleCount= (TextView) findViewById(R.id.indiana_people_count);
        mBar= (ProgressBar) findViewById(R.id.indiana_progressbar);
        tvPrice = (TextView) findViewById(R.id.indiana_groups_price);
        yiFenIcon = (ImageView) findViewById(R.id.yifen_icon);
        width = context.getResources().getDisplayMetrics().widthPixels;
    }

    public void initView(final HashMap<String, Object> map, final int index) {
        /**这里赋值*/
        this.index = index;
        image.getLayoutParams().height =((width- DP2SPUtil.dp2px(context, 18))/2) * 900 / 600;

        image.setTag(map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic"));
        if (width > 720) {
            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!382", image);
        } else {
            PicassoUtils.initImage(context, map.get("shop_code").toString().substring(1, 4) + "/" + map.get("shop_code").toString() + "/" + (String) map.get("def_pic") + "!280", image);

        }
        if(indianaGroups){
            DecimalFormat df = new java.text.DecimalFormat("#0.0");
            yiFenIcon.setImageResource(R.drawable.indiana_groups_pintuangou);
            tvPrice.setVisibility(View.VISIBLE);
            double shop_price_groups = Double.parseDouble((String) map.get("shop_se_price"));
            tvPrice.setText("¥"+df.format(shop_price_groups)+"元");
        }else{
            yiFenIcon.setImageResource(R.drawable.indiana_one_minuter);
            tvPrice.setVisibility(View.GONE);
        }
        name.setText(Shop.getShopNameStrNew((String) map.get("shop_name")));
        if(indianaGroups){
            mPeoPleCount.setText("已有"+map.get("involved_people_num")+"团正在参与");
        }else{
            mPeoPleCount.setText("已有"+map.get("involved_people_num")+"人正在参与");
        }

        mBar.setMax(Integer.parseInt((String)map.get("active_people_num")));//总人数
        mBar.setProgress(Integer.parseInt((String)map.get("involved_people_num")));//已参与人数
        this.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (IndianaListItemView.this.getVisibility() != View.VISIBLE) {
                    return;
                }
//                addScanDataTo((String) map.get("shop_code"));
                if(indianaGroups){
                    //拼团的夺宝
                    rollTrea((String) map.get("shop_code"));
//                    Intent intent = new Intent(context, ShopDetailsGroupIndianaActivity.class);
//                    intent.putExtra("shop_code", (String) map.get("shop_code"));
//                    intent.putExtra("indianaGroups", indianaGroups);
//                    FragmentActivity activity = (FragmentActivity) context;
//                    activity.startActivity(intent);
//                    activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                }else {
                    Intent intent = new Intent(context, ShopDetailsIndianaActivity.class);
                    intent.putExtra("shop_code", (String) map.get("shop_code"));
                    FragmentActivity activity = (FragmentActivity) context;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                }
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


    /**
     * 拼团夺宝 开团
     * @param shop_code
     */
    private void rollTrea(final String shop_code) {
        new SAsyncTask<Void, Void, ReturnInfo>(((FragmentActivity) context)) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context,
                                                Void... params) throws Exception {
                return ComModelL.getRollTrea(context, shop_code,"1","");
            }
            @Override
            protected boolean isHandleException() {
                return true;
            }
            @Override
            protected void onPostExecute(FragmentActivity context,
                                         ReturnInfo result,Exception e) {
                super.onPostExecute(context, result,e);
//                if (e == null && result != null) {
                    Intent intent = new Intent(context, ShopDetailsGroupIndianaActivity.class);
                    intent.putExtra("shop_code", shop_code);
                    intent.putExtra("indianaGroups", indianaGroups);
                    FragmentActivity activity = (FragmentActivity) context;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
//                }
            }

        }.execute();
    }


}
