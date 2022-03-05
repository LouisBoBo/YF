package com.yssj.ui.adpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.YConstance;
import com.yssj.activity.R;
import com.yssj.data.YDBHelper;
import com.yssj.ui.activity.classfication.ManufactureActivity;
import com.yssj.ui.activity.main.CrazyShopListActivity;
import com.yssj.ui.activity.main.ForceLookActivity;
import com.yssj.ui.activity.main.ForceLookMadActivity;
import com.yssj.ui.activity.main.ForceLookMatchActivity;
import com.yssj.ui.activity.main.MoreSubjectActivity;
import com.yssj.ui.activity.main.SignActiveShopActivity;
import com.yssj.ui.activity.shopdetails.SpecialTopicDeatilsActivity;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
//import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CrazyShopListAdapter extends BaseAdapter {
    private List<HashMap<String, String>> mInfos;
    // private ImageLoadingListener animateFirstListener = new
    // AnimateFirstDisplayListener();
    private Context context;
    private LayoutInflater mInflater;

    public static int width;


    public CrazyShopListAdapter(Context context) {
        mInfos = new ArrayList<HashMap<String, String>>();
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_crazy_tage, null);
            holder.subject_main_image_iv = (ImageView) convertView.findViewById(R.id.subject_main_image_iv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 设置图片的宽高比 加载图片
//        ViewGroup.LayoutParams lp = holder.subject_main_image_iv.getLayoutParams();
//        lp.width = MoreSubjectActivity.width;
//        lp.height = LayoutParams.WRAP_CONTENT;
//        holder.subject_main_image_iv.setLayoutParams(lp);
//
//
//        int wide = CrazyShopListActivity.width - DP2SPUtil.dp2px(context,20);
//
//        holder.subject_main_image_iv.setMaxWidth(wide);
//        holder.subject_main_image_iv.setMaxHeight(wide*  (200/340)); // 宽高比1.7


        ViewGroup.LayoutParams lp = holder.subject_main_image_iv.getLayoutParams();
        lp.width = CrazyShopListActivity.width - DP2SPUtil.dp2px(context, 20);
        lp.height = lp.width * 200 / 340;
        holder.subject_main_image_iv.setLayoutParams(lp);

        String pic = mInfos.get(position).get("url") + "!560";
        PicassoUtils.initImage(context, pic, holder.subject_main_image_iv);

        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String sql = "select * from shop_group_list where _id = " + mInfos.get(position).get("option_type") + "";
                List<HashMap<String, String>> listData = new YDBHelper(context).query(sql);


                Intent intent;
                String leiTX = "";
                if (listData.size() > 0) {
                    leiTX = listData.get(0).get("value");

                }
                String doIconId = mInfos.get(position).get("option_type") + "";
                if (leiTX.equals("collection=collocation_shop")) {// 搭配
                    intent = new Intent(context, ForceLookMatchActivity.class);
                    intent.putExtra("type", "1");
                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isCrazy", true);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);
                } else if (leiTX.equals("collection=shop_activity")) { // 活动商品
                    intent = new Intent(context, SignActiveShopActivity.class);

                    intent.putExtra("doIconId", doIconId);

                    intent.putExtra("isCrazy", true);


                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);
                } else if (leiTX.equals("collection=browse_shop")) { // 热门推首
                    // ----完全用以前的强制浏览
                    intent = new Intent(context, ForceLookMadActivity.class);

                    intent.putExtra("doIconId", doIconId);

                    intent.putExtra("isCrazy", true);


                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);
                } else if (leiTX.equals("collection=csss_shop")) { // 浏览专题
                    intent = new Intent(context, ForceLookMatchActivity.class);
                    intent.putExtra("type", "2");
                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isCrazy", true);
                    context.startActivity(intent);

                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);


                } else {


                    intent = new Intent(context, ForceLookMadActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("title", "热卖");
                    intent.putExtra("pinJievalue", leiTX);

                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isCrazy", true);


                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);

                }


            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView subject_main_image_iv;

    }

    @Override
    public int getCount() {
        // ToastUtil.showShortText(context, mInfos + "");
        return mInfos.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public void addItemLast(List<HashMap<String, String>> datas) {

        mInfos.addAll(datas);
        this.notifyDataSetChanged();
    }

    public void addItemTop(List<HashMap<String, String>> datas) {
        mInfos.clear();
        mInfos.addAll(datas);
        this.notifyDataSetChanged();
    }

    public void clearData() {
        mInfos.clear();
        this.notifyDataSetChanged();
    }

    public void setData(List<HashMap<String, String>> result) {
        clearData();
        mInfos.addAll(result);
        // LogYiFu.e("转盘", mInfos + "");
        // LogYiFu.e("转盘", mInfos.get(0).get("subPIC").toString());
        this.notifyDataSetChanged();
    }
}