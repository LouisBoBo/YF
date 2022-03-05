package com.yssj.ui.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yssj.activity.R;
import com.yssj.custom.view.NewMealItemView;
import com.yssj.entity.MealShopList;
import com.yssj.entity.VipDikouData;

import java.util.ArrayList;
import java.util.List;


public class NewMealListAdapter extends BaseAdapter {
	List<MealShopList.PListBean.ShopListBean> mInfos;
    private boolean isVip; //是否是会员
    private VipDikouData vipDikouData;

	public NewMealListAdapter() {
		mInfos = new ArrayList<>();


	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater layoutInflator = LayoutInflater.from(parent
					.getContext());
			convertView = layoutInflator.inflate(R.layout.new_meal_list_item, null);
			
			holder.left= convertView.findViewById(R.id.left);
			holder.right= convertView.findViewById(R.id.right);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		int index=position*2;
		
//		holder.left.initView(mInfos.get(index));

//        if(isVip){
            holder.left.initViewVip(mInfos.get(index),vipDikouData,isVip);

//        }else{
//            holder.left.initView(mInfos.get(index));
//
//
//
//        }



		
		if(mInfos.size()>index+1){
			holder.right.setVisibility(View.VISIBLE);



//            if(isVip){
                holder.right.initViewVip(mInfos.get(index + 1),vipDikouData, isVip);
//
//            }else{
//                holder.right.initView(mInfos.get(index + 1));
//
//            }


		}else{
			holder.right.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}
	class ViewHolder {

		NewMealItemView left;
		NewMealItemView right;
	}

	@Override
	public int getCount() {
		return mInfos.size()%2==0?mInfos.size()/2:mInfos.size()/2+1;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public void addItemLast(List<MealShopList.PListBean.ShopListBean> datas) {
		mInfos.addAll(datas);
		this.notifyDataSetChanged();
	}

	public void addItemTop(List<MealShopList.PListBean.ShopListBean> datas) {
		mInfos.clear();
		mInfos.addAll(datas);
		this.notifyDataSetChanged();
	}

	public void clearData() {
		mInfos.clear();
		this.notifyDataSetChanged();
	}

	public void setData(List<MealShopList.PListBean.ShopListBean> result) {
		clearData();
		mInfos.addAll(result);
		this.notifyDataSetChanged();
	}

    public void setDataVip(List<MealShopList.PListBean.ShopListBean> result, boolean isVip, VipDikouData vipDikouData) {
        this.isVip = isVip;
        this.vipDikouData = vipDikouData;
        clearData();
        mInfos.addAll(result);
        this.notifyDataSetChanged();
    }
}
