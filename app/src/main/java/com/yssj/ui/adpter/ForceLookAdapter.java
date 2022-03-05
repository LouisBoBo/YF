package com.yssj.ui.adpter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yssj.activity.R;
import com.yssj.custom.view.ForceLookItemView;
import com.yssj.entity.VipDikouData;
import com.yssj.utils.DP2SPUtil;

public class ForceLookAdapter extends BaseAdapter {
    private List<HashMap<String, Object>> mInfos;

    private boolean isMoreShop;//密友圈帖子详情更多商品推荐
    private boolean isVip; //是否是会员
    private VipDikouData vipDikouData;
    private boolean isHomePage3;

    private boolean isNewPT;//是否是新拼团
    public  void setNewPT(boolean mIsNewPT){
       this.isNewPT = mIsNewPT;
    }

    public ForceLookAdapter(Context context, boolean isMoreShop) {
        mInfos = new ArrayList<>();

        this.isMoreShop = isMoreShop;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater layoutInflator = LayoutInflater.from(parent
                    .getContext());
            convertView = layoutInflator.inflate(R.layout.force_look_infos_list, null);

            holder.left = convertView.findViewById(R.id.left);
            holder.right = convertView.findViewById(R.id.right);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.left.setMoreShop(isMoreShop);
        holder.right.setMoreShop(isMoreShop);

        holder.left.setNewPT(isNewPT);
        holder.right.setNewPT(isNewPT);

        int index = position * 2;

        if (isHomePage3) {
            holder.left.initViewHomePage3(mInfos.get(index));

        } else {
            if (isVip) {
                holder.left.initViewVip(mInfos.get(index), vipDikouData);

            } else {
                holder.left.initView(mInfos.get(index));

            }
        }

        if (isHomePage3) {
            if (mInfos.size() > index + 1) {
                holder.right.setVisibility(View.VISIBLE);
                holder.right.initViewHomePage3(mInfos.get(index + 1));

            } else {
                holder.right.setVisibility(View.INVISIBLE);
            }
        } else {
            if (mInfos.size() > index + 1) {
                holder.right.setVisibility(View.VISIBLE);
                if (isVip) {
                    holder.right.initViewVip(mInfos.get(index + 1), vipDikouData);

                } else {
                    holder.right.initView(mInfos.get(index + 1));

                }


            } else {
                holder.right.setVisibility(View.INVISIBLE);
            }
        }





        return convertView;
    }

    class ViewHolder {

        ForceLookItemView left;
        ForceLookItemView right;
    }

    @Override
    public int getCount() {
        return mInfos.size() % 2 == 0 ? mInfos.size() / 2 : mInfos.size() / 2 + 1;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public void addItemLast(List<HashMap<String, Object>> datas) {
        mInfos.addAll(datas);
        this.notifyDataSetChanged();
    }

    public void addItemTop(List<HashMap<String, Object>> datas) {
        mInfos.clear();
        mInfos.addAll(datas);
        this.notifyDataSetChanged();
    }

    public void clearData() {
        mInfos.clear();
        this.notifyDataSetChanged();
    }

    public void setData(List<HashMap<String, Object>> result, boolean isHomePage3) {
        this.isHomePage3 = isHomePage3;
        clearData();
        mInfos.addAll(result);
        this.notifyDataSetChanged();
    }

    public void setDataVip(List<HashMap<String, Object>> result, boolean isVip, VipDikouData vipDikouData) {
        this.isVip = isVip;
        this.vipDikouData = vipDikouData;
        clearData();
        mInfos.addAll(result);
        this.notifyDataSetChanged();
    }


}
